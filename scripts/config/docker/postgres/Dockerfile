FROM alpine

LABEL Author="Francis Ouellet <Ouellet.Francis@gmail.com>"
LABEL Description="Base on postgres offical image"

ENV CONTAINER_IMAGE ghandalf/postgres

ENV LANG en_US.utf8
ENV PORT 5432
ENV APP_HOME /usr/share/postgres
ENV PG_MAJOR 12
ENV PG_MINOR 1
ENV PG_VERSION ${PG_MAJOR}.${PG_MINOR}

# Container configuration
RUN apk add --no-cache --virtual .fecth-deps ca-certificates openssl tar --update bash
RUN apk add --no-cache --virtual .build-deps gcc g++ make \ 
    libc-dev libedit-dev libxml2-dev libxslt-dev linux-headers llvm8-dev clang
    # perl bison flex
    # readline readline-dev

RUN mkdir -p ${APP_HOME}/install
RUN cd ${APP_HOME}/install \
    && wget https://ftp.postgresql.org/pub/source/v${PG_VERSION}/postgresql-${PG_VERSION}.tar.gz.sha256 \
    && wget https://ftp.postgresql.org/pub/source/v${PG_VERSION}/postgresql-${PG_VERSION}.tar.gz

RUN cd ${APP_HOME}/install; \
    result=$(sha256sum -c postgresql-12.1.tar.gz.sha256 | awk -F ':' '{print $2}'); \
    result=${result//' '/}; \
    if [[ "${result}" == "OK" ]]; then tar xf postgresql-12.1.tar.gz; else exit 1; fi; 

RUN cd ${APP_HOME}/install/postgresql-12.1; \
    ./configure --prefix=${APP_HOME}; \
    make; make install;

RUN apk add --no-cache --virtual .build-deps busybox-suid

# Create group and system user
RUN addgroup -S postgres; \
    adduser --gecos '' -G postgres -S postgres --home ${APP_HOME}; 

RUN mkdir ${APP_HOME}/data;
RUN chown -R postgres:postgres ${APP_HOME}; \
    ls -ld ${APP_HOME}/data; \
    ls -la ${APP_HOME};

COPY ./scripts/config/docker/postgres/system/service.sh ${APP_HOME}/
RUN chmod 0755 ${APP_HOME}/service.sh; \
    chown -R postgres:postgres ${APP_HOME}/service.sh;

RUN mkdir -p /var/log/postgresql; \
    chown -R postgres:postgres /var/log/postgresql;
RUN mkdir -p /var/run/postgresql; \
    chown -R postgres:postgres /var/run/postgresql;

USER postgres
RUN ${APP_HOME}/bin/initdb -D ${APP_HOME}/data

# Add remote connection
# RUN sed -i "/# Allow replication connections/i host    all             all             0.0.0.0/0               md5" ${APP_HOME}/data/pg_hba.conf
RUN sed -i "/# Allow replication connections/i host    all             all             0.0.0.0/0               trust" ${APP_HOME}/data/pg_hba.conf
# And add listen_addresses
RUN sed -i "/#listen_addresses/i listen_addresses='*'" ${APP_HOME}/data/postgresql.conf
#log_directory = 'log'
RUN sed -i "/#log_directory/i log_directory = '/var/log/postgresql'" ${APP_HOME}/data/postgresql.conf
# data directory
RUN sed -i "/#data_directory/i data_directory = '/usr/share/postgres/data'" ${APP_HOME}/data/postgresql.conf

RUN cat ${APP_HOME}/data/pg_hba.conf | grep -e 'local   all' -e 'host    all' -e '0.0.0.0/0';
RUN cat ${APP_HOME}/data/postgresql.conf | grep 'listen_addresses'; \
    cat ${APP_HOME}/data/postgresql.conf | grep 'log_directory'; \
    cat ${APP_HOME}/data/postgresql.conf | grep 'data_directory';

# Start postgresql
# RUN /usr/share/postgres/bin/pg_ctl -D /usr/share/postgres/data -l logfile start;
RUN /usr/share/postgres/bin/pg_ctl -D /usr/share/postgres/data  start;

# WORK
# RUN ls -la /var/run/
# RUN /usr/local/pgsql/bin/createdb UrbanMobility;
# RUN /usr/local/pgsql/bin/psql UrbanMobility; \
#     create user urbanadmin with password 'Urb@n@dm!n'; \
#     grant all privileges on database UrbanMobility to urbanadmin;
# RUN /usr/local/pgsql/bin/psql test

#COPY ./config/jenkins/system/usr/share/jenkins/.bashrc ${APP_HOME}/
# RUN psql -f 

# RUN rm -rf ${APP_HOME}/install;

EXPOSE ${PORT}

# ENTRYPOINT ["/usr/share/postgres/service.sh", "start"]
ENTRYPOINT ["/bin/bash"]

# performing post-bootstrap initialization ... sh: locale: not found
# 2020-01-12 19:33:36.683 UTC [10] WARNING:  no usable system locales were found
# ok
# syncing data to disk ... ok

# Success. You can now start the database server using:

#     /usr/share/postgres/bin/pg_ctl -D /usr/share/postgres/data -l logfile start

# initdb: warning: enabling "trust" authentication for local connections
# You can change this by editing pg_hba.conf or using the option -A, or
# --auth-local and --auth-host, the next time you run initdb.


# checking for bison... no
# configure: WARNING:
# *** Without Bison you will not be able to build PostgreSQL from Git nor
# *** change any of the parser definition files.  You can obtain Bison from
# *** a GNU mirror site.  (If you are using the official distribution of
# *** PostgreSQL then you do not need to worry about this, because the Bison
# *** output is pre-generated.)
# checking for flex... no
# configure: WARNING:
# *** Without Flex you will not be able to build PostgreSQL from Git nor
# *** change any of the scanner definition files.  You can obtain Flex from
# *** a GNU mirror site.  (If you are using the official distribution of
# *** PostgreSQL then you do not need to worry about this because the Flex
# *** output is pre-generated.)
# checking for perl... no
# configure: WARNING:
# *** Without Perl you will not be able to build PostgreSQL from Git.
# *** You can obtain Perl from any CPAN mirror site.
# *** (If you are using the official distribution of PostgreSQL then you do not
# *** need to worry about this, because the Perl output is pre-generated.)

