FROM postgres

LABEL Author="Francis Ouellet <Ouellet.Francis@gmail.com>"
LABEL Description="Base on postgres offical image"

ENV CONTAINER_IMAGE ghandalf/postgres
ENV PORT 5432

RUN mkdir -p /usr/share/postgresql/scripts
COPY ./scripts/config/docker/postgresql/sql/CreateSchema.sql /usr/share/postgresql/scripts/CreateSchema.sql
#RUN chmod 0644 ${APP_HOME}/.bash_profile
#COPY ./config/jenkins/system/usr/share/jenkins/.bashrc ${APP_HOME}/
# RUN psql -f 


EXPOSE ${PORT}

ENTRYPOINT ["/usr/share/jenkins/service.sh", "start"]
