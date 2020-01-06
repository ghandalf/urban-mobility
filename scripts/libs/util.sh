#!/bin/bash

###
# Make sure that we call an existing value in the list provided
##
function isInList() {
    local value=$1; 
    shift;
    local container=($@);

    for item in "${container[@]}"; do
		if [[ "${value}" == "${item}" ]]; then
            return 0;
        fi
    done
    return 1;
}

function whichEnvironment() { 
	local os=$(uname);
	echo ${os};
}

