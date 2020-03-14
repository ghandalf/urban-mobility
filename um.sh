#!/bin/bash

current_dir=$(pwd);

jdk_dir="/data/app/programs/java";
maven_current_settings=${HOME}/.m2/settings.xml
maven_my_current_settings=${HOME}/.m2/my.settings.xml
maven_original_settings=${HOME}/.m2/original.settings.xml

resources_dir="scripts/resources";
libs_dir="scripts/libs";

commands=("mvn" "docker" "test"); # "run" "srv"  "project" "fast" "update" "generate" "get"
mvn_actions=("compile" "enforcer" "package" "run" "test" "update-report" "update-version" "validate");
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

###
# mvn will use the machine JAVA_HOME, I need to force mvn to use the one define in the pom.xml by java.current.version
##
function getProjectJavaCurrentVersion() {

	local java_current_version=$(mvn help:evaluate -Dexpression=java.current.version -q -DforceStdout);
	if [[ "${java_current_version}" =~ "11" ]] || [[ "${java_current_version}" =~ "13" ]] || [[ "${java_current_version}" =~ "14" ]]; then
		java_current_version="jdk-${java_current_version}";
	else
		java_current_version="jdk${java_current_version}";
	fi
	echo ${java_current_version};
}

function setUp() {
	loadResources;
	loadLibraries;

    echo -e "${n1t}${Green}Set up JAVA_HOME and maven settings from [${Yellow}${maven_current_settings}${Green}] to [${Yellow}${maven_my_current_settings}${Green}].${Color_Off}";

    export JAVA_HOME="${jdk_dir}/$(getProjectJavaCurrentVersion)";
    echo -e "${tabs2}${Yellow}JAVA_HOME=${Cyan}${JAVA_HOME}${Yellow}${Color_Off}";
    echo -e "${tabs2}${Yellow}MAVEN_VERSION=";
    while read -r line; do
        echo -e "${tabs3}${Cyan}${line}";
    done < <(mvn --version);
    echo -ne "${Color_Off}";
    
    if [ -f ${maven_current_settings} ]; then
        mv ${maven_current_settings} ${maven_original_settings};
        mv ${maven_my_current_settings} ${maven_current_settings};
    fi
    echo -e "${tab}${Green}Set up of JAVA_HOME and maven settings from [${Yellow}${maven_current_settings}${Green}] to [${Yellow}${maven_my_current_settings}${Green}] ${Yellow}done${Color_Off}${nl}";
}

function tearDown() {
	echo -e "${n1t}${Green}Rolling back maven settings from [${Yellow}${maven_current_settings}${Green}] to [${Yellow}${maven_my_current_settings}${Green}]${Color_Off}";

    if [ -f ${maven_current_settings} ]; then
        mv ${maven_current_settings} ${maven_my_current_settings};
        mv ${maven_original_settings} ${maven_current_settings};
    fi
    
    echo -e "${tab}${Green}Rolling back maven settings from [${Yellow}${maven_current_settings}${Green}] to [${Yellow}${maven_my_current_settings}${Green}] ${Yellow}done${Color_Off}${nl}";
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

# This function must be called after setUpDocker
function setUpDatabases() { 
#	driver: org.postgresql.Driver
#	url: jdbc:postgresql://localhost:5432/UrbanMobility
#	username: urbanadmin
#	password: Urb@n@dm!n


}

###
# This function will manage, compile, clean, install relate to maven process 
##
function mavenManagement() {
	local action=$1;
	local class=$2; # Could be classTest or ClassTest#functionToTest
	local new_version=${class}; # Use the same parameter
	local begin;
	local parallel_compilation=""; #"-T 8C";
	
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
	
	setUp;
	
	echo -e "${n1t}${Yellow}Maven execution of [${Cyan}${action}${Yellow}].${Color_Off}";
	
	case ${action} in
		compile)
            local succeed=false;
            while ! ${succeed}; do           
                echo -ne "${n1t}${Green}Do you want to execute the tests <y|n>?${Color_Off} ";
                read answer;
                case "${answer}" in
                    "y"|"Y")
                        echo -e "${tab}${Yellow}The command will be [${Cyan}mvn -f pom.xml ${parallel_compilation} clean install${Yellow}].${Color_Off}${nl}";
                        begin=$(date +%s);
                        mvn -f pom.xml -X ${parallel_compilation} enforcer:display-info clean install;
                        succeed=true;
                    ;;
                    "n"|"N")
                        echo -e "${tab}${Yellow}The command will be [${Cyan}mvn -f pom.xml ${parallel_compilation} clean install -DskipTests${Yellow}].${Color_Off}${nl}";
                        begin=$(date +%s);
                        mvn -f pom.xml ${parallel_compilation} enforcer:display-info clean install -DskipTests;
                        succeed=true;
                        ;;
                    *)
                    echo -e "${tabs2}${Red}Possible answers are [y|Y|n|N] !!!${Color_Off}";
                esac
            done
            ;;
		enforcer)
            begin=$(date +%s);
            mvn ${parallel_compilation} enforcer:display-info; 
            ;;
		package)
            begin=$(date +%s);
            mvn ${parallel_compilation} clean package -DskipTests; 
            ;;
		run)		
		    begin=$(date +%s);
            setUpDocker;
			cd ws-api
			mvn ${parallel_compilation} spring-boot:run
			cd ${current_dir};
			tearDownDocker; 
			;;
		test)		
            begin=$(date +%s);
            if [[ ${#class} -gt 0 ]]; then
                mvn ${parallel_compilation} test -Dtest=${class};
            else 
                mvn ${parallel_compilation} test;
            fi; 
            ;;
		update-report)
            begin=$(date +%s);
            mvn ${parallel_compilation} versions:dependency-updates-report;
            mvn ${parallel_compilation} versions:plugin-updates-report;
            mvn ${parallel_compilation} versions:property-updates-report;
            ;;
		update-version)
            # see: https://www.mojohaus.org/versions-maven-plugin/usage.html
            if [[ ${#new_version} -gt 0 ]]; then
                begin=$(date +%s);
                mvn ${parallel_compilation} versions:set -DnewVersion=${new_version};
            else 
                echo -e "${n1t}${Red}You need to provide the new version${Color_Off}";
                usage; exit 12;
            fi ;;
		validate) 	
            begin=$(date +%s);
            mvn clean validate; 
            ;;
	esac
	echo -e "${tabs2}${Yellow}The command tooks [${Cyan}$(($(date +%s)-${begin}))${Yellow}] seconds.${Color_Off}${nl}";
	echo -e "${tab}${Yellow}Maven execution ${Green}done.${Color_Off}";
	tearDown;
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

    setUp;
    
    echo -e "${tab}${Yellow}Docker execution.${Color_Off}";
    
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
    
    tearDown;
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
    exit 12;
}

loadResources;
loadLibraries;
loadDockerEnv;
inputValidation ${command};
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
