#!/bin/sh

command=$1;


case ${command} in
    start)  
        echo -e "Starting postgresql database"; 
        /usr/share/postgres/bin/pg_ctl -D /usr/share/postgres/data -l logfile start;
        ;;
    stop)   
        echo -e "Stoping postgresql database"; 
        usr/share/postgres/bin/pg_ctl -D /usr/share/postgres/data -l logfile stop;
        ;;
    status)
        echo -e "Status postgresql database"; 
        usr/share/postgres/bin/pg_ctl -D /usr/share/postgres/data -l logfile status;
        ;;
esac