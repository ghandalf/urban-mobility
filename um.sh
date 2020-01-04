#!/bin/bash

current_dir=$(pwd);
links_dir="/data/links";
jdk_dir="/data/app/programs/java";
jdk_needed="/data/app/programs/java/jdk-13.0.1";
jdk_path_file="${links_dir}/jdk.original.link";

command=$1
module=$2

function setUp() {
    if [ -h ${links_dir}/jdk ]; then
	    echo -e "${n1t}${Green}Set up jdk to [${Cyan}${jdk_needed}${Green}]${Color_Off}";
        cd ${links_dir};
        echo "jdk_path=$(realpath jdk)" > ${jdk_path_file}; 
        rm -f jdk;
        ln -s ${jdk_needed} jdk;
        cd ${current_dir};
        echo -e "${tab}${Green}Set up jdk to [${Cyan}${jdk_needed}${Green}] ${Yellow}done${Color_Off}${nl}";
    fi
    
    echo -e "${n1t}${Green}Starting postgres container${Color_Off}";
    /usr/bin/docker info >/dev/null 2>&1;
    if [[ ! $? -eq 0  ]]; then 
    	sudo service docker start; 
    fi
    docker-compose -f docker-compose.yml up -d;
    echo -e "${tab}${Green}Starting postgres container ${Yellow}done${Color_Off}${nl}";
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
    
    echo -e "${n1t}${Green}Shutdown postgres container${Color_Off}";
    # Postgres config
    docker-compose -f docker-compose.yml down;
    echo -e "${tab}${Green}Shutdown postgres container ${Yellow}done${Color_Off}${nl}";
}

function generate() {

    case ${module} in
        domain)

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
	local ws_dir=ws-api;

	mvn enforcer:display-info;
	mvn clean install -DskipTests;
	cd ${ws_dir};
	mvn spring-boot:run;
	
	cd ${current_dir};
}

function usage() {
    echo -e "\n";
    echo -e "\t\t$0 command=<build|gen|show> project=<domain|repository|handler|service|rest>";
    echo -e "\n";
}

source scripts/resources/colors.properties;
source scripts/resources/formatter.properties;
setUp;
case ${command} in 
    build)
        mvn clean install -DskipTests; 
        ;;
    run)
        run; 
        ;;
    gen)
        generate;
    ;;
    show)
       show; 
    ;;
    *)
        usage;
    ;;
esac 
tearDown;
