#!/bin/bash

###
# This script could be use to compile, deploy and create a dist 
#	You need to install git bash on Windows machine
#		see: https://git-scm.com/downloads
#
# Make sure that the env directory exist at the same level of the service.sh file
#		env
#			local.properties
#		service.sh 
#
# author: Francis Ouellet
# email: Ouellet.Francis@gmail.com
# 
##

types=("mvn" "srv" "docker" "project" "fast" "update" "generate" "get")
currentDir=`pwd`
rootDir=../
jsInputDir=src/main/webapp
jsOutputDir=dist

source ./env/colors.properties


type=$1
command=$2

function sourceOsEnvironnement() {
	osType=`uname`;
	echo -e "\n\t\t Current os:${Green}${osType} ${Color_Off}\n";
	
	case "${osType}" in
		"Darwin" | "Linux")
			source ./env/local.linux.properties;
		;;
		"MINGW64_NT-10.0")
			source ./env/local.win.properties;
#			export CATALINA_HOME=${primary_application_server_path}
		;;
		*)
			echo -e "\n\t\t ${Red} We can't find this os:${osType} ${Color_Off}\n";
			exit; 
	esac
}

###
# Will return 0 (true) if type is in the list 1 (false) otherwise
##
function isInTypes() {
	local currentType=$1
	for value in "${types[@]}"; do
		if [ "$value" == "${type}" ]; then
			return 0;
		fi
	 done
	 return 1;
}

###
# We want to avoid unnecessary work 
# if the first input is not in the list we exit
##
function inputValidation() {
	if ! isInTypes $1; then
		usage;
		exit
	fi
}


###
# This function will manage, compile, clean, install relate to maven process 
##
function mvnCommand() {
	cd ${application_path}
	echo -e "\n\t\t ${Green} dir:${application_path} ${Color_Off}\n"
	
	case ${command} in
		atdd) mvn clean validate -Dwebdriver.chrome.driver=${webdriver} ;;
		package) mvn package ;;
		compile) mvn compile ;;
		install) mvn clean install ;;
		clean) mvn clean ;;
		test) mvn test ;;
		skiptest) mvn clean install -Dmaven.test.skip=true ;;
		*) usage ;;	
	esac
	cd ${currentDir}
}

###
# 
# Useful method to deploy, undeploy, start, and stop an application under the given server
#
##
function serverCommand() {	
	cd ${application_path}	

	case ${command} in
		deploy) 
			echo -e "\n\t\t application path:${Green}${application_path} ${Color_Off}\n";
			cp -n ${application_to_deploy} ${primary_application_server_deploy_to}/
			echo -e "\n\t Webapps directoty:: `ls -la ${primary_application_server_deploy_to}/`"
		  	;;
		undeploy)
			rm -rf ${primary_application_server_path}/log/*
			if [ -f ${primary_application_server_deploy_to}/${application_name}.war ]; then
				rm -rf ${primary_application_server_deploy_to}/${application_name}
				rm -rf ${primary_application_server_deploy_to}/${application_name}.war
			fi
			if [ -d ${primary_application_server_path}/work/Catalina/localhost/${application_name} ]; then
				rm -rf ${primary_application_server_path}/work/Catalina/localhost/${application_name}
			fi
			echo -e "\n\t Webapps directoty:: `ls -la ${primary_application_server_deploy_to}/`"
			echo -e "\n\t Log directory:: `ls -la ${primary_application_server_path}/log/`"
			echo -e "\n\t Work directory:: `ls -la ${primary_application_server_path}/work/Catalina/localhost/`"
			;;
		start)
			case ${osType} in 
				"MINGW64_NT-10.0")
					start ${primary_application_server_path}/bin/startup.bat
					;;
				*)
					${primary_application_server_path}/bin/catalina.sh run
					;;
			esac
			;;
		stop)
			${primary_application_server_path}/bin/shutdown.sh
			;;
		*) usage ;;	
	esac
	cd ${currentDir}
}

###
#
# Prerequisite: 
# Have the 200GB Oracle Data files
# Process:
# Add these Docker commands 
# docker run --name redis -p 6379:6379 -v $REDIS_DIR:/data -d redis  
# docker run -d -p 8021:8081 -p 1521:1521 --name <name> –v ORACLE_DATA_DIR:/u01/app/oracle sath89/oracle-12c# 
##
function dockerCommand() {
	
	case ${command} in
		install)
			# Check if docker image exists
			ora=`docker images | grep ${ORACLE_IMAGE_NAME} | awk '{print $1}'`
			
			if [ ${ora} ] && [ "${ora}" == "${ORACLE_IMAGE_NAME}" ]; then
				echo "    We have an image ${ora}, checking if it is running..."
				ora=`docker ps -f name=${ORACLE_DATA_NAME} | grep ${ORACLE_DATA_NAME} | awk '{print $13}'`
				
				if [ -z ${ora} ]; then
					echo "    Starting ${ORACLE_DATA_NAME}"
					docker start ${ORACLE_DATA_NAME}
				else 
					echo "    ${ORACLE_DATA_NAME} is already running..."
				fi
			else
				echo "    We don't have an image ${ORACLE_IMAGE_NAME}, we are creating it"
				docker run -d -p 8021:8081 -p 1521:1521 --name ${ORACLE_DATA_NAME} –v ${ORACLE_DATA_DIR}:/u01/app/oracle ${ORACLE_IMAGE_NAME}
			fi
			
			redis=`docker images | grep ${REDIS_IMAGE_NAME} | awk '{print $1}'`
			
			if [ ${redis} ] && [ "${redis}" == "${REDIS_IMAGE_NAME}" ]; then
				echo "    We have an image ${redis}, checking if it is running..."
				redis=`docker ps -f name=${REDIS_DATA_NAME} | grep ${REDIS_DATA_NAME} | awk '{print $12}'`
				
				if [ -z ${redis} ]; then
					echo "    Starting ${REDIS_DATA_NAME}"
					docker start ${REDIS_DATA_NAME}
				else 
					echo "    ${REDIS_DATA_NAME} is already running..."
				fi
			else
				echo "    We don't have an image ${REDIS_IMAGE_NAME}, we are creating it"
				docker run --name ${REDIS_DATA_NAME} -p 6379:6379 -v ${REDIS_DATA_DIR}:/data -d ${REDIS_IMAGE_NAME}
			fi
			docker ps
		  	;;
		start)
			# We assume that docker images are already installed
			ora=`docker ps -f name=${ORACLE_DATA_NAME} | grep ${ORACLE_DATA_NAME} | awk '{print $13}'`
			if [ -z ${ora} ]; then
				echo "    Starting ${ORACLE_DATA_NAME}..."
				docker start ${ORACLE_DATA_NAME}
			fi
			redis=`docker ps -f name=${REDIS_DATA_NAME} | grep ${REDIS_DATA_NAME} | awk '{print $12}'`
			if [ -z ${redis} ]; then
				echo "    Starting ${REDIS_DATA_NAME}..."			
				docker start ${REDIS_DATA_NAME}
			fi
			;;
		stop)
			# We assume that docker images are already installed
			#ora=`docker ps -f name=${ORACLE_DATA_NAME} | grep ${ORACLE_DATA_NAME} | awk '{print $13}'`
			ora=`docker ps -f name=${ORACLE_DATA_NAME}`
			if [ ${ora} ]; then
				echo "    Stopping ${ORACLE_DATA_NAME}..."		
				docker stop ${ORACLE_DATA_NAME}
			else
				echo "    Container ${ORACLE_DATA_NAME} is already stopped..."
			fi
			#redis=`docker ps -f name=${REDIS_DATA_NAME} | grep ${REDIS_DATA_NAME} | awk '{print $12}'`
			redis=`docker ps -f name=${REDIS_DATA_NAME}`
			if [ -z ${redis} ]; then
				echo "    Stopping ${REDIS_DATA_NAME}..."			
				docker stop ${REDIS_DATA_NAME}
			else
				echo "    Container ${REDIS_DATA_NAME} is already stopped..."
			fi
			;;
		process)
			docker ps
			;;
		*) usage ;;	
	esac
}

function e2eTest() {
	npm test -- --params.baseUrl="${project_url}"
	cd ${currentDir}
}

function fullDeployAndTest() {
	
	case ${command} in
		test) e2eTest ;;
		deploy) 
			command=undeploy
			serverCommand
			command=skiptest
			mvnCommand
			if [ "$?" -ne 0 ]; then
				echo "Fail to compile please review the error message in the appropriate terminal : $?"
				exit 1;
			else 
				echo "Proceed with the deployment : $?"
				command=deploy
				serverCommand
				command=start
				serverCommand
			fi		
		;;
		*) usage ;;
	esac
}

function usage() {
	echo " "
	echo -e "    usage:"
	echo -e "      ${0} <type> <options> where type in [${types[@]}]"
	echo -e "        ${0} mvn <option> in [package, compile, install, clean, test, skiptest]";
	echo -e "        ${0} srv <option> in [deploy, undeploy, start, stop]";
	echo -e "        ${0} docker <option> in [install, start, stop, process]";
	echo -e "        ${0} project <option> in [test, pull, deploy] will update the test main projects";
	echo -e "        		where: ${Green}<project test> will execute the e2e test on the url given in local.properties file ${Color_Off}";
	echo -e "        		where: ${Green}<project deploy> will clean tomcat server build main projet, deploy the war and start the server ${Color_Off}";
	echo -e "\n"
}

function updateSource() {
	cd ${rootDir}
	
	echo -e "${Green} Update all sub projects\n ${Color_Off}"
	for app in "${apps[@]}"; do
		echo -e "${Yellow}git pull on ${Green}${app} ${Color_Off}"
		git fetch && git pull
		echo -e "\n"
	done

	cd ${currentDir}
}

function generateJavafromProto() {
	cd ${rootDir}
	
	echo -e "${Green} Generate domain object from protobuf tools\n ${Color_Off}"
	
	protoc --proto_path=proto/cities/Montreal --java_out=domain/src/main/java/ agency.proto
	# gtfs-realtime.proto 
	# java_outer_classname="domain/src/main/java/ca/ghandalf/urban/mobility/domain/FeedMessage" 
	# TranslatedString.proto java_outer_classname = "domain/src/main/java/TranslatedString" \
	# VehiculeDescriptor.proto \
	# TripDescriptor.proto \
	# EntitySelector.proto \ # look for TripDescriptor.proto
	
	# TripUpdate.proto FeedEntity.proto FeedHeader.proto FeedMessage.proto VehiculePosition.proto
	
	cd ${currentDir}
}

function get() {
	
	case ${command} in
		Montreal) 
			wget -P ../repository/src/main/resources/cities/${command}/ http://www.stm.info/sites/default/files/gtfs/gtfs_stm.zip
		 	cd ../repository/src/main/resources/cities/${command} 
		 	unzip gtfs_stm.zip
		 	rm -rf gtfs_stm.zip
		 	;;
		*) usage ;;
	esac
	
	cd ${currentDir}		
}

SUCCESS=
sourceOsEnvironnement
inputValidation $type
case $type in
	mvn) mvnCommand ;;
	js) mvnscriptCommand ;;
	srv) serverCommand ;;
	docker) dockerCommand ;;
	project) fullDeployAndTest ;;
	fast) 
		command=skiptest
		mvnCommand
		command=undeploy
		serverCommand
		command=deploy
		serverCommand
		command=start
		serverCommand
		;;
	update) updateSource ;;
	generate) generateJavafromProto ;;
	get) get ;;
	*) usage ;;
esac
	