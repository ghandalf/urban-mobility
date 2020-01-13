#!/bin/bash

###
# Build container
# The prefix must be the company name or a project name
##
function build() {
	local container=$1;
	local prefix=$2;
	
	echo -e "${n1t}${Yellow}Building the container [${Cyan}${prefix}/${container}${Yellow}].${Color_Off}";
	echo -e "current dir=$(pwd)"
    for item in scripts/config/docker/*; do
        current_container=$(basename ${item});
        echo -e "item=$item ::: current_container=$current_container container=$container";
        if [[ "${current_container}" == "${container}" ]]; then
            # echo -e "They are equals";
            if [[ -f ${item}/Dockerfile ]]; then
                docker build --rm -f ./${item}/Dockerfile -t ${prefix}/${container}:${CONTAINERS_VERSION} -t ${prefix}/${container}:latest . ;
            fi
        fi
    done
	
	echo -e "${n1t}${Yellow}Building the container [${Cyan}${prefix}/${container}${Yellow}] ${Green}done.${Color_Off}";
}
	
function docker.manager() {
    local action=$1;
    local container=$2;
    local prefix=$3;
    local isRootUser=$3; # true|false
    local from=$4;
    local to=$5;

    case ${action} in
        connect) 
            if [[ "${isRootUser}" == "true" ]]; then
                docker exec -u 0 -it ${container} /bin/bash;
            else
			    docker exec -it ${container} /bin/bash;
            fi
            ;;
        build)
            build ${container} ${prefix};
            ;;
        start)  docker-compose -f docker-compose.yml up -d; ;;
        stop)   docker-compose -f docker-compose.yml down; ;;
        clean)  
            result=$(docker ps -aq);
            if [ ! ${#result} -eq 0 ]; then
                docker stop $(docker ps -aq); docker rm $(docker ps -aq); 
            else
                echo -e "${tabs2}${Yellow}Nothing to do all containers are ${Green}down.${Color_Off}";
            fi
            # clean images that have <none> value at REPOSITORY column
            while IFS= read -r line; do
                if [[ "${line}" =~  "<none>" ]]; then
                    image_id=$(echo ${line} | awk -F' ' '{print $3}');
                    echo -e "${tabs2}${Yellow}Remove the image id [${Cyan}${image_id}${Yellow}].${Color_Off}";
                    docker rmi ${image_id};
                fi
            done < <(docker images);
            ;;
        copy)
            docker cp ${container}:/${from} ${to};
            # docker image prune -a --filter "until=24h";
            ;;
    esac
}
