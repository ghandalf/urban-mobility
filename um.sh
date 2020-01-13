#!/bin/bash

current_dir=$(pwd);
links_dir="/data/links";
jdk_dir="/data/app/programs/java";
jdk_needed="/data/app/programs/java/jdk-13.0.1";
jdk_path_file="${links_dir}/jdk.original.link";
resources_dir="scripts/resources";
libs_dir="scripts/libs";

commands=("mvn" "docker" "test"); # "run" "srv"  "project" "fast" "update" "generate" "get"
mvn_actions=("c" "ci" "e" "p" "rp" "r" "t" "v" "va");
docker_actions=("build" "start" "stop" "connect" "clean");
containers=("postgres")
prefixes=("ghandalf");
command=$1;
module=$2;

function loadResources() {
	for file in ${resources_dir}/*; do
		source ${file};
	done
}

function loadLibraries() { 
	for file in ${libs_dir}/*; do
		source ${file};
	done
}

function loadDockerEnv() { 
    source .env;
}

function inputValidation() {
	local command=$1;
	
	result=$(isInList ${command} ${commands[@]});
	if [[ ! $? -eq 0 ]] ; then
		usage;
		exit 12;
	fi
}

function setUp() {
	loadResources;
	loadLibraries;

    if [ -h ${links_dir}/jdk ]; then
	    echo -e "${n1t}${Green}Set up jdk to [${Cyan}${jdk_needed}${Green}]${Color_Off}";
        cd ${links_dir};
        echo "jdk_path=$(realpath jdk)" > ${jdk_path_file}; 
        rm -f jdk;
        ln -s ${jdk_needed} jdk;
        cd ${current_dir};
        echo -e "${tab}${Green}Set up jdk to [${Cyan}${jdk_needed}${Green}] ${Yellow}done${Color_Off}${nl}";
    fi
    
}

function tearDown() {
	# JDK config
    if [ -f ${jdk_path_file} ]; then
        source ${jdk_path_file};
		echo -e "${n1t}${Green}Rolling back jdk to [${Cyan}${jdk_path}${Green}]${Color_Off}";
        cd ${links_dir};
        rm -f jdk;
        ln -s ${jdk_path} jdk;
        rm -f ${jdk_path_file};
        cd ${current_dir};
        echo -e "${tab}${Green}Rolling back jdk to [${Cyan}${jdk_path}${Green}] ${Yellow}done${Color_Off}${nl}";
    fi
    
}

function setUpDocker() { 
    echo -e "${n1t}${Green}Starting postgres container${Color_Off}";
    /usr/bin/docker info >/dev/null 2>&1;
    if [[ ! $? -eq 0  ]]; then 
    	sudo service docker start; 
    fi
    docker-compose -f docker-compose.yml up -d;
    # sudo ls -la /var/lib/docker/
    # /var/lib/postgresql/data
    #psql -h localhost -p 5432 -U postgres --password ; # gh@nd@lf!
    
    echo -e "${tab}${Green}Starting postgres container ${Yellow}done${Color_Off}${nl}";
}

function stearDownDocker() { 
    echo -e "${n1t}${Green}Shutdown postgres container${Color_Off}";
    # Postgres config
    docker-compose -f docker-compose.yml down;
    echo -e "${tab}${Green}Shutdown postgres container ${Yellow}done${Color_Off}${nl}";
}

###
# This function will manage, compile, clean, install relate to maven process 
##
function mavenManagement() {
	local action=$1;
	local class=$2; # Could be classTest or ClassTest#functionToTest
	local new_version=${class}; # Use the same parameter
	
	if [[ ${#action} -eq 0 ]]; then
        echo -e "${n1t}${Red}You must provide the action from this list [${Cyan}${mvn_actions[@]}${Red}].${Color_Off}";
        usage;
        exit 12;
    fi
	result=$(isInList ${action} "${mvn_actions[@]}"); 
	if [[ ! $? -eq 0 ]] ; then
		echo -e "${n1t}${Red}The action [${Cyan}${action}${Red}] [${Cyan}${mvn_actions[@]}${Red}]${Color_Off}";
		usage;
		exit 12;
	fi
		
	echo -e "${n1t}${Yellow}Maven execution of [${Cyan}${action}${Yellow}].${Color_Off}";
	
	case ${action} in
		c)		mvn clean compile; ;;
		ci)		mvn clean install -DskipTests; ;;
		e)		mvn enforcer:display-info; ;;
		p)		mvn clean package -DskipTests; ;;
		r)		setUpDocker;
				cd ws-api
				mvn spring-boot:run
				cd ${current_dir};
				tearDownDocker; ;;
		rp)		mvn versions:dependency-updates-report;
				mvn versions:plugin-updates-report;
				mvn versions:property-updates-report; ;;
		t)		if [[ ${#class} -gt 0 ]]; then
					mvn test -Dtest=${class};
				else 
					mvn test;
				fi ;;
		v) 		# see: https://www.mojohaus.org/versions-maven-plugin/usage.html
				if [[ ${#new_version} -gt 0 ]]; then
					mvn versions:set -DnewVersion=${new_version};
				else 
					echo -e "${n1t}${Red}You need to provide the new version${Color_Off}";
					usage; exit 12;
				fi ;;
		va) 	mvn clean validate; ;;
	esac
	echo -e "${n1t}${Yellow}Maven execution ${Green}done.${Color_Off}";
}

function dockerManagement() {
    local action=$1;
    local container=$2;
    local prefix=$3;
    local isRootUser=$3; # true|false
    local from=$3;
    local to=$4

    # echo -e "dockerManagement 1 action=${action},container=${container},  prefix=${prefix}, isRootUser=${isRootUser}"
    if [[ ${#action} -eq 0 ]]; then
        echo -e "${n1t}${Red}You must provide the action from this list [${Cyan}${docker_actions[@]}${Red}].${Color_Off}";
        usage;
        exit 12;
    fi
    result=$(isInList ${action} "${docker_actions[@]}"); 
    if [[ ! $? -eq 0 ]] ; then
        echo -e "${n1t}${Red}The action [${Cyan}${action}${Red}] must be from this list [${Cyan}${docker_actions[@]}${Red}]${Color_Off}";
        usage;
        exit 12;
    fi

    # validation
    case ${action} in
        connect|build) 
            if [[ ${#container} -eq 0 ]]; then
                echo -e "${n1t}${Red}You must provide the container name.${Color_Off}";
                usage;
                exit 12;
            fi
            result=$(isInList ${container} "${containers[@]}"); 
            if [[ ! $? -eq 0 ]] ; then
                echo -e "${n1t}${Red}The container [${Cyan}${container}${Red}] must be from this list [${Cyan}${containers[@]}${Red}]${Color_Off}";
                usage;
                exit 12;
            fi
        ;;
    esac
    
    echo -e "${n1t}${Yellow}Docker execution of [${Cyan}${action}${Yellow}].${Color_Off}";
    
    case ${action} in
        connect) 
            if [ ${#isRootUser} == 0 ]; then
                echo -e "${n1t}${Red}You need to tell us if you want to connect as root user or not.${Color_Off}";
                usage; exit 12;
            else 
                case ${isRootUser} in 
                    "true"|"false") 
                        docker.manager ${action} ${container} ${prefix} ${isRootUser};
                        ;;
                    *)
                        echo -e "${n1t}${Red}Possible values for isRootUser are [${Cyan}true${Red}|${Cyan}false${Red}].${Color_Off}";
                        usage; exit 12;
                        ;; 
                esac
            fi
            ;;
        build)  
            if [[ ${#prefix} -eq 0 ]]; then
                echo -e "${n1t}${Red}You must provide the prefix use to build the container.${Color_Off}";
                usage;
                exit 12;
            fi
            result=$(isInList ${prefix} "${prefixes[@]}"); 
            if [[ ! $? -eq 0 ]] ; then
                echo -e "${n1t}${Red}The prefix [${Cyan}${prefix}${Red}] must be from this list [${Cyan}${prefixes[@]}${Red}]${Color_Off}";
                usage;
                exit 12;
            fi
            docker.manager ${action} ${container} ${prefix} ${isRootUser}; 
            ;;
        start|stop|clean)  docker.manager ${action}; ;;
        copy) 
            if [[ ${#from} -eq 0 ]]; then
                echo -e "${n1t}${Red}You must provide from the ${container} where we copy the file.${Color_Off}";
                usage;
                exit 12;
            fi
            if [[ ${#to} -eq 0 ]]; then
                echo -e "${n1t}${Red}You must provide where in the host we copy the file.${Color_Off}";
                usage;
                exit 12;
            fi
            docker.manager ${action} ${from} ${to}; ;;
    esac
    echo -e "${tab}${Yellow}Docker execution ${Green}done.${Color_Off}";
}

function generate() {

    case ${module} in
        domain) [${Cyan}${}${Red}] [${Cyan}${mvn_actions[@]}${Red}]

            # Domain
            javac --module-path \
            /home/ghandalf/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.9.7/jackson-annotations-2.9.7.jar:/home/ghandalf/.m2/repository/org/springframework/spring-context/5.1.0.RELEASE/spring-context-5.1.0.RELEASE.jar:/home/ghandalf/.m2/repository/javax/persistence/javax.persistence-api/2.2/javax.persistence-api-2.2.jar \
            -d generated-modules \
            domain/src/main/java/module-info.java  \
            domain/src/main/java/ca/ghandalf/urban/mobility/domain/*.java
        ;;
        handler)

            # Handler
            javac --module-path \
            /home/ghandalf/.m2/repository/ca/ghandalf/urban/mobility/domain/0.0.9/domain-0.0.9.jar:/home/ghandalf/.m2/repository/org/springframework/spring-context/5.1.0.RELEASE/spring-context-5.1.0.RELEASE.jar \
            -d generated-modules \
            handler/src/main/java/module-info.java  \
            handler/src/main/java/ca/ghandalf/urban/mobility/handler/*.java
        ;;
        repository)

            # Repository
            javac --module-path \
            /home/ghandalf/.m2/repository/ca/ghandalf/urban/mobility/domain/0.0.9/domain-0.0.9.jar:\
            /home/ghandalf/.m2/repository/ca/ghandalf/urban/mobility/handler/0.0.9/handler-0.0.9.jar:\
            /home/ghandalf/.m2/repository/org/springframework/data/spring-data-jpa/2.1.0.RELEASE/spring-data-jpa-2.1.0.RELEASE.jar:\
            /home/ghandalf/.m2/repository/org/springframework/data/spring-data-commons/2.1.0.RELEASE/spring-data-commons-2.1.0.RELEASE.jar:\
            /home/ghandalf/.m2/repository/org/springframework/spring-context/5.1.0.RELEASE/spring-context-5.1.0.RELEASE.jar:\
            /home/ghandalf/.m2/repository/javax/persistence/javax.persistence-api/2.2/javax.persistence-api-2.2.jar:\
            /home/ghandalf/.m2/repository/org/postgresql/postgresql/42.2.5/postgresql-42.2.5.jar \
            -d generated-modules \
            repository/src/main/java/module-info.java \
            repository/src/main/java/ca/ghandalf/urban/mobility/repository/*.java
        ;;
        service)
            echo -e "\n\t\t Service not implemented yet....";
        ;;
        rest)
            echo -e "\n\t\t Rest-api not implemented yet....";
        ;;
        *)
            usage;
        ;;
    esac

}

function show() {
    jdeps generated-modules/
}

function run() { 
	setUpDocker;
	local ws_dir=ws-api;

	mvn enforcer:display-info;
	mvn clean install -DskipTests;
	cd ${ws_dir};
	mvn spring-boot:run;
	
	tearDownDocker;
	cd ${current_dir};
}

function usage() {
    echo -e "${n1t}usage";
    echo -e "${tab}$0${Yellow} <${Cyan}command${Yellow}> <${Cyan}action${Yellow}> <${Cyan}class${Yellow}|${Cyan}prefix${Yellow}>${Color_Off}";
    echo -e "${tabs2}where ${Yellow}<${Cyan}command${Yellow}> must be in this group [${Cyan}${commands[@]}${Yellow}]${Color_Off}";
    echo -e "${tabs2}${Yellow}whith the command <${Cyan}mvn${Yellow}> uses those parameters [${Cyan}${mvn_actions[@]}${Yellow}]${Color_Off}";
    echo -e "${tabs2}${Yellow}whith the command <${Cyan}docker${Yellow}> uses those parameters [${Cyan}${docker_actions[@]}${Yellow}]${Color_Off}";
    echo -e "${nl}";
    tearDown;
}

loadResources;
loadLibraries;
loadDockerEnv;
inputValidation ${command};
setUp;
case ${command} in
	mvn)
		action=$2; class=$3;
		mavenManagement ${action} ${class}; ;;
	docker)
	    action=$2; container=$3; prefix=$4; isRootUser=$4;
        dockerManagement ${action} ${container} ${prefix} ${isRootUser}; ;;
    test)
        # declare containers="$(docker images)";
        while IFS= read -r line; do
            if [[ $line =~  "<none>" ]]; then
                # echo "line= $line"
                image_id=$(echo $line | awk -F' ' '{print $3}');
                echo "image= ${image_id}"
                docker rmi ${image_id}
            fi
        done < <(docker images);

            # IFS=' ' read  -r -a container <<< "${containers[$i]}";
        #     IFS=' ' read  -r -a container <<< "${image[${count}]}";
        #         images=$(docker images | awk -F' ' '{print $3}')  
        # echo -e "$(docker images | awk -F' ' '{print $1, $3}' | grep none)";
        #     IFS=' ' read -r -a value <<< ${values[@]};
        #     echo -e "${value[1]}";
        ;;
#    build)
#        mvn clean install -DskipTests; 
#        ;;
#    run)
#        run; 
#        ;;
#    gen)
#        generate;
#    ;;
#    show)
#       show; 
#    ;;
    *) usage; ;;
esac 
tearDown;
