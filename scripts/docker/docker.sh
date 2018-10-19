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
    echo -e "\n\tStarting containers $1";

    case $1 in 
        oracle)    
            # see: https://hub.docker.com/r/sath89/oracle-12c/
            # Oracle created for Fresche Solutions
            # -d will start the container in detached mode
            # -p dedicated ports 8080 will be used by http://localhost:8080/apex tools and 1521 by databases sqlplus command line or tools
            # Login on http://localhost:8080/apex asked for user: admin and new password::Ghandalf+12
            docker run -d -p 8080:8080 -p 1521:1521 --name docker-oracle -v /data/app/db/OracleDataDocker/oracle:/u01/app/oracle sath89/oracle-12c
            echo -e "\n\t Oracle started\n";
            ;;
        postgres)
            # see: https://hub.docker.com/r/library/postgres/
            #docker run -d -p 5432:5432 --name docker-postgres -v /data/app/db/PostgreSQLDataDocker/postgresql -e POSTGRES_PASSWORD=postgres postgres
            #docker run -d -p 5432:5432 --name docker-postgres -v postgresql-data:/data/app/db/PostgreSQLDataDocker/postgresql -e POSTGRES_DB=UrbanMobility -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres postgres
            docker start 0a3e218c1b36
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
            docker run -d -p 6379:6379 --name docker-redis -v /data/app/db/RedisDataDocker/redis:/u01/app/redis redis
            echo -e "\n\t Redis started\n";
            ;;
        *)
            echo -e "\n\t Please provide a container: <oracle|postgres|redis> \n";
            ;;
    esac
}

function show() {
    echo -e "\n\tDocker container installed";
    docker images
    echo -e "\n\tDocker running";
    docker ps 
    echo -e "\n";
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
    docker rm $(docker ps -aq)
    echo -e "\n\t Containers removed \n"
    docker ps -a

}

function test() {
    echo "Etre: $1"
    case $1 in
        tt)
            echo -e "TTTTTTTTT";
            ;;
        *)
            echo -e "*****";
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

# password: postgres
function db() { 
	case $1 in 
		create)
			createTables;
			;;
		drop)
			dropTables;
			;;
		user)
			createUser;
			;;
		*)
            echo -e "\n\t Please provide a command: <create|drop|user> \n";
            ;;
	esac
}

case ${command} in
    clean)
        cleanUp;
        ;;
	db)
		db $2;
		;;
    start)
        start $2;
        ;;
    show)
        show;
        ;;
    stop)
        stop;
        ;;
    test)
        test $2;
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

