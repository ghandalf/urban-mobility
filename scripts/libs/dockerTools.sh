#!/bin/bash

###
# Build container
# The prefix must be the company name or a project name
##
function build() {
	local prefix=$1;
	local container=$2;
	
	#echo -e "${n1t}${Yellow}Building the container [${Cyan}${container}${Yellow}].${Color_Off}";
	
    for item in scripts/config/docker/*; do
        current_container=$(basename ${item});
        #echo -e "item=$item ::: current_container=$current_container container=$container";
        if [[ "${current_container}" == "${container}" ]]; then
            # echo -e "They are equals";
            if [[ -f ${item}/Dockerfile ]]; then
                #echo -e "We have a Dockerfile";
                #echo -e "docker build --rm -f ./${item}/Dockerfile -t ${prefix}/${container}:${CONTAINERS_VERSION} -t ${prefix}/${container}:latest .";
                #echo -e "directory == $(pwd)";
                docker build --rm -f ./${item}/Dockerfile -t ${prefix}/${container}:${CONTAINERS_VERSION} -t ${prefix}/${container}:latest . ;
            fi
        fi
    done
	
	#echo -e "${n1t}${Yellow}Building the container [${Cyan}${container}${Yellow}] ${Green}done.${Color_Off}";
}
	
#	local size=${#args[*]}; 
#	local prefix=${args[0]}
	
#	if [[ $size > 0 ]] ; then
#		for item in ${args[*]}; do
#			if [[ $item == $prefix ]]; then
#				continue;
#			fi
#echo $item
#			if [[ $item == "application" ]]; then
#				# curl ${nexus_cfna_url} -o config/application/system/cfna-deployment-6.0.0-SNAPSHOT.tar.gz
#				. ./deploy.sh retrieveLatestSnapshot config/application/system
#			fi
#echo "./config/$item/Dockerfile -t ${prefix}/$item:${CONTAINERS_VERSION} -t ${prefix}/$item:latest";
#			docker build --rm -f ./config/$item/Dockerfile -t ${prefix}/$item:${CONTAINERS_VERSION} -t ${prefix}/$item:latest .
#
#			if [ -f config/application/system/cfna-deployment-6.0.0-SNAPSHOT.tar.gz ]; then
#				rm -rf config/application/system/cfna-deployment-6.0.0-SNAPSHOT.tar.gz
#			fi
#		done
#	else
#		echo -e "\n${tab}${BRed}Nothing to build, list length: $size. ${Color_Off}\n";
#	fi


