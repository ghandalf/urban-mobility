#!/bin/bash

###
## Use to stars current docker containers
##
##
## Author: Ghandalf
###

application=$0
command=$1
args=$2

function start() {
    echo -e "\n\tStarting containers...";

	for i in "${images[@]}"; do
	    case $i in 
	        dockerhelp/docker-oracle-ee-18c)    
	            docker run --name docker-oracle18c -d -p 8090:8090 -p 1523:1523 -v ${oracle18_data}:/opt/app/oracle18 $i
    	        echo -e "\n\t Oracle 18c started with data: ${oracle18_data}\n";
            	;;
        	sath89/oracle-12c)    
            	# docker run -d -p 8080:8080 -p 1521:1521 --name docker-oracle -v /data/app/db/OracleDataDocker/oracle:/u01/app/oracle sath89/oracle-12c
            	docker --name docker-oracle12c run -d -p 8080:8080 -p 1521:1521 -v ${oracle12_data}:/opt/app/oracle $i
            	echo -e "\n\t Oracle 12c started with data: ${oracle12_data}\n";
            	;;
        	postgres)
            	#docker run -d -p 5432:5432 --name docker-postgres -v ${postgres_data} -e POSTGRES_PASSWORD=postgres postgres
            	#docker run -d -p 5432:5432 --name docker-postgres -v postgresql-data:/data/app/db/PostgreSQLDataDocker/postgresql -e POSTGRES_DB=UrbanMobility -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres postgres
            	docker run --name docker-postgres -d -p 5432:5432 -v ${postgres_data}:/opt/app/postgres -e POSTGRES_DB=UrbanMobility -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres $i
            	#docker start 0a3e218c1b36
            	echo -e "\n\t PostgreSQL started with data:${postgres_data}\n";
            	;;
        	redis)
            	docker run --name docker-redis -d -p 6379:6379 -v ${redis_data}:/opt/app/redis $i
            	echo -e "\n\t Redis started\n";
            	;;
	    esac  
    done
}

###
# Create analytic network if it doesn't exist
# This network will be use for the communication in between analytics containers.
##
function createDockerNetwork() {
    
    local result=`docker network ls | grep ${analytic_network} | awk {'printf "%s\n", $2'}`
    # echo -e "RESULT: $result";
    if [ $result ]; then
        echo -e "The docker network [${analytic_network}] already exists.";
    else 
        echo -e "Creating docker network [${analytic_network}]";
        docker network create ${analytic_network} --driver=bridge
    fi
}

function removeDockerNetwork() {
    docker network rm ${analytic_network}
}

###
# Start the analytice containers link together thru a bridge network
##
function startAnalytic() {

    createDockerNetwork;
    
    local result;
    for i in "${kibana_images[@]}"; do
        
        containerName=`echo $i | awk -F'/' {'printf $3'} | awk -F':' {'printf $1'}`
        result=`docker container ls -q -f name=$containerName`
        
        if [ $result ]; then 
            echo -e "\n\tContainer $containerName is running will be stop, remove and restart it";
            docker stop $containerName;
            docker rm $containerName;
        fi

        # echo -e "\n Starting container [$containerName]";
        case $containerName in
            elasticsearch)
                # see: httpans://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html
                docker run --name $containerName -p 9200:9200 -p 9300:9300 -d --network ${analytic_network} $i
                ;;
            kibana)
                docker run --name $containerName -p 5601:5601 -d --network ${analytic_network} $i;
                ;;
        esac
    done
}

function startSwarm() {
	docker swarm leave --force
	docker swarm init;
	# to clean up: docker swarm leave --force
	
	#To add a worker to this swarm, run the following command:
    	# docker swarm join --token SWMTKN-1-4otvtlk254dnaog6gvoka1ketgcacvxvw2dohueu16fkwgjr0g-bwk926il697dtyxrx8q23o2hx 10.10.157.38:2377
	# To add a manager to this swarm, run 'docker swarm join-token manager' and follow the instructions.
	docker stack deploy -c container/docker-compose.prod.yml analytic-stack
}

function updateDockerCompose() {
	sudo curl -L "https://github.com/docker/compose/releases/download/${compose_version}/docker-compose-$(uname -s)-$(uname -m)" -o /usr/bin/docker-compose
}

function ports() {
    netstat -na | grep -e 5601 -e 5602 -e 5603 -e 5604 -e 5605 -e 5606 -e 5607 -e 5608 -e 5609 -e 9200
}

function show() {
    echo -e "\n\tDocker container installed";
    docker images
    echo -e "\n\tDocker running";
    docker ps 
    echo -e "\n\tDocker network";
    docker network ls
    echo -e "\n\tDocker service";
    docker service ls
	echo -e "\n\tValidate the container/docker-compose.yml file";
	docker-compose -f container/docker-compose.yml config;
	echo -e "\n\tValidate the container/docker-compose.prod.yml file";
	docker-compose -f container/docker-compose.prod.yml config;
    echo -e "\n";
}

function stop() {
    echo -e "\n\tStopping all containers\n";
    docker container stop $(docker ps -aq)
    echo -e "\n\tAll containers stopped\n";   
}

function cleanUp() {

    echo -e "\n\t Remove background running containers\n"
    docker stop $(docker ps -aq)
    docker rm $(docker ps -aq)
	docker network rm ${analytic_network}
	docker swarm leave --force
	
    echo -e "\n\t Containers removed \n"
    docker ps -aq
    
    echo -e "\n\t Network removed \n"
    docker network ls
    
    echo -e "\n";
}

function loadResources() {
	echo -e "\n\t\t $0 load resources...";
	if [ -f ./container/docker.properties ]; then
		source ./container/docker.properties;
	else
		echo -e "\n\t\tYou need to provide the file docker.properties under container directory...";
		echo -e "\n";
	fi
}

function pull() {
    for i in "${images[@]}"; do
        docker pull $i;
    done
    for i in "${kibana_images[@]}"; do
        docker pull $i;
    done
}

function curl() {
    echo "CURL $1";
    case $1 in
        post)
            curl -v POST -H "Origin: http://localhost:8020" -H "Content-Type: application/json" -d "{}" http://localhost:8020/agency/agencies
            curl -v -X OPTIONS -H "Access-Control-Request-Method: POST" -H "Origin: http://localhost:8020" -H "Access-Control-Request-Headers: Content-Type" http://localhost:8020/agency/agencies
            ;;
        get)
        	curl -v -X GET -H "Origin: http://localhost:8020" -H "Content-Type: application/json" http://localhost:8020/agency/agencies
        	curl -v -X GET -H "Origin: http://localhost:8020" -H "Content-Type: application/json" http://localhost:8020/agency/read/76ed93a9-f1b4-4ea8-a402-e1a36e286451
            ;; 
        *)
            echo -e "\n\t Please provide a command: <post|get|put|delete|options> \n";
            ;;
    esac
}

function createUser() {
	psql -h localhost -p 5432 -d UrbanMobility -f ./scripts/postgresql/CreateDB.sql -U postgres
	psql -h localhost -p 5432 -d UrbanMobility -f ./scripts/postgresql/CreateUser.sql -U postgres 
}

function dropTables() { 
	psql -h localhost -p 5432 -d UrbanMobility -f ./scripts/postgresql/DropTables.sql -U postgres
}

function createTables() { 
	psql -h localhost -p 5432 -d UrbanMobility -f ./scripts/postgresql/CreateTables.sql -U postgres
}

function insertData() { 
	psql -h localhost -p 5432 -d UrbanMobility -f ./scripts/postgresql/InsertData.sql -U postgres
}

# password: postgres
function db() { 
	case $1 in 
		pull)
			pull
			;;
		create)
			createTables;
			;;
		insert)
			insertData;
			;;
		drop)
			dropTables;
			;;
		user)
			createUser;
			;;
		startSwarm)
			startSwarm;
			;;
		*)
            echo -e "\n\t Please provide a command: <create|insert|drop|user> \n";
            ;;
	esac
}

function usage() {
    echo -e "\n\tUsage:";
    echo -e "\t\t$0 <pull|clean|db|start|show|stop>";
    echo -e "\n";
}

function finish() { 
	echo -e "\n\t\tUse to clean resources before we live\n";
}
trap finish EXIT;

loadResources;

case ${command} in
	pull)
		pull;
		;;
    clean)
        cleanUp;
        ;;
	db)
		db $2;
		;;
    start)
        start $2;
        ;;
	startAnalytic)
		startAnalytic;
		;;
	startSwarm)
		startSwarm;
		;;
    show)
        show;
        ;;
    stop)
        stop;
        ;;
    curl)
        curl $2;
        ;;
	updateDockerCompose)
		updateDockerCompose;
		;;
    *) usage;
esac


# docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres
# docker run --name dock-postgres -e POSTGRES_PASSWORD=apirest -d postgres

#program_dir=/data/app/programs/tor/tor-browser_en-US/
#program=start-tor-browser.desktop

#cd $program_dir
#./$program 
#cd $current_dir

