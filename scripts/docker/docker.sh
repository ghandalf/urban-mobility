#!/bin/bash

###
## Use to stars current docker containers
##
##
## Author: Ghandalf
###

command=$1

function usage() {
    echo -e "\n\tUsage:";
    echo -e "\t\t$0 <clean|db|start|show|stop>";
    echo -e "\n";
}


function start() {
    echo -e "\n\tStarting containers";

	for i in "${images[@]}"; do
    case $i in 
        sath89/oracle-12c)    
            # see: https://hub.docker.com/r/sath89/oracle-12c/
            # Oracle created for Fresche Solutions
            # -d will start the container in detached mode
            # -p dedicated ports 8080 will be used by http://localhost:8080/apex tools and 1521 by databases sqlplus command line or tools
            # Login on http://localhost:8080/apex asked for user: admin and new password::Ghandalf+12
            # docker run -d -p 8080:8080 -p 1521:1521 --name docker-oracle -v /data/app/db/OracleDataDocker/oracle:/u01/app/oracle sath89/oracle-12c
            docker run -d -p 8080:8080 -p 1521:1521 --name docker-oracle -v ${oracle12_data}:/u01/app/oracle $i
            echo -e "\n\t Oracle 12 started\n";
            ;;
        dockerhelp/docker-oracle-ee-18c)    
            # see: https://hub.docker.com/r/sath89/oracle-12c/
            # Oracle created for Fresche Solutions
            # -d will start the container in detached mode
            # -p dedicated ports 8080 will be used by http://localhost:8080/apex tools and 1521 by databases sqlplus command line or tools
            # Login on http://localhost:8080/apex asked for user: admin and new password::Ghandalf+12
            # docker run -d -p 8080:8080 -p 1521:1521 --name docker-oracle -v /data/app/db/OracleDataDocker/oracle:/u01/app/oracle sath89/oracle-12c
            docker run -d -p 8090:8090 -p 1523:1523 --name docker-oracle18 -v ${oracle18_data}:/u01/app/oracle18 $i
            echo -e "\n\t Oracle 18 started\n";
            ;;
        postgres)
            # see: https://hub.docker.com/r/library/postgres/
            #docker run -d -p 5432:5432 --name docker-postgres -v /data/app/db/PostgreSQLDataDocker/postgresql -e POSTGRES_PASSWORD=postgres postgres
            #docker run -d -p 5432:5432 --name docker-postgres -v postgresql-data:/data/app/db/PostgreSQLDataDocker/postgresql -e POSTGRES_DB=UrbanMobility -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres postgres
            docker run -d -p 5432:5432 --name docker-postgres -v ${postgres_data}:/u01/app/postgres -e POSTGRES_DB=UrbanMobility -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres $i
            #docker start 0a3e218c1b36
            echo -e "\n\t PostgreSQL started\n";
            # Connect directly on command line to postgresql
            # docker run -it --rm --link docker-postgres:postgres postgres psql -h postgres -U postgres
            # psql -h 127.0.0.1 -p 5432 -U postgres
            # psql -h 127.0.0.1 -p 5432 -U urbanadmin
 			# psql -h 127.0.0.1 -p 5432 -U postgres
            # psql -h 127.0.0.1 -U urbanadmin Urb@n@dmnetstat -na | grep 8080
            # psql -h 127.0.0.1 -U urbanadmin -p Urb@n@dmnetstat -na | grep 8080
            # psql -h 127.0.0.1 -U urbanadmin -p 5432
            # psql -h localhost -U postgres 
            
            # createUser;
            ;;
        redis)
            # see: https://hub.docker.com/_/redis/
            #docker run -d -p 6379:6379 --name docker-redis -v /data/app/db/RedisDataDocker/redis:/u01/app/redis redis
            docker run -d -p 6379:6379 --name docker-redis -v ${redis_data}:/u01/app/redis $i
            echo -e "\n\t Redis started\n";
            ;;
        *)
            echo -e "\n\t Please provide a container: <oracle|postgres|redis> \n";
            ;;
    esac  
    done
}


function show() {
    echo -e "\n\tDocker container installed";
    docker images
    echo -e "\n\tDocker running";
    docker ps 
    echo -e "\n";
}

function startImages() {
	length="${#imagesId[@]}";
	for (( i=0; i<$length; i++)); do
		docker start "${imagesId[$i]}"
		#echo -e "${imagesId[$i]}"
	done
}

function stop() {
    echo -e "\n\tStopping all containers\n";
    docker stop $(docker ps -aq)
    echo -e "\n\tAll containers stopped\n";   
}

function cleanUp() {
    echo -e "\t\n Remove unused volumes\n"; 
    # docker volume rm $(docker volume ls -f dangling=true);
    #docker volume ls
    #docker volume prune
    docker volume ls
    
    echo -e "\n\t Remove background running containers\n"
    docker stop $(docker ps -aq)
    docker rm $(docker ps -aq)
    echo -e "\n\t Containers removed \n"
    docker ps -a

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
		*)
            echo -e "\n\t Please provide a command: <create|insert|drop|user> \n";
            ;;
	esac
}

function loadResources() {
	echo -e "\n\t\t $0 load resources...";
	if [ -f `pwd`/scripts/docker/container/docker.properties ]; then
		source `pwd`/scripts/docker/container/docker.properties;
	else
		echo -e "\n\t\tYou need to provide the file docker.properties under container directory...";
	fi
}


function pull() {
	for i in "${images[@]}"; do
		echo "$i"
		docker pull $i;
	done
}

function pullAnalytic() {
	for i in "${imagesAnalytic[@]]}"; do
		docker pull $i;
	done
}

###
# Before we start analytics container we need to create a docker network
# https://stackoverflow.com/questions/40373400/docker-compose-yml-for-elasticsearch-and-kibana
##
function startAnalytic() {
	# Create the network for communication in between containers
	#docker network create analytic-net --driver=bridge
	
	for i in "${imagesAnalytic[@]]}"; do
		currentName=`echo $i | awk -F'/' {'printf $3'} | awk -F':' {'printf $1'}`

		case $currentName in
			elasticsearch)
				docker run --name $currentName -p 9200:9200 -p 9300:9300 -d --network analytic-net $i
				#docker run --name elasticsearch -p 9200:9200 -p 9300:9300 -d --network analytic-net docker.elastic.co/elasticsearch/elasticsearch:6.4.3
				;;
			kibana)
				docker run --name $currentName -p 5601:5601 -d --network analytic-net $i 
				;;
		esac
		echo -e "\n\t $currentName started\n";
	done
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
	pullAnalytic)
		pullAnalytic;
		;;
	startAnalytic)
		startAnalytic;
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
    startImages)
    	startImages;
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
    *) usage;
esac


# docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres
# docker run --name dock-postgres -e POSTGRES_PASSWORD=apirest -d postgres

#program_dir=/data/app/programs/tor/tor-browser_en-US/
#program=start-tor-browser.desktop

#cd $program_dir
#./$program 
#cd $current_dir

