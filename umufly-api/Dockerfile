FROM registry.gitlab.com/umugit/atica/desarrollo/common/imagenes/jdk11:master

ARG DEPENDENCY
ARG APP_VERSION
ARG APP_DATE
ARG APP_BUILD
ARG APP_NAME
ARG APP_URL

LABEL org.label-schema.schema-version="1.0"
LABEL org.label-schema.version=${APP_VERSION}
LABEL org.label-schema.vendor="Universidad de Murcia"
LABEL org.label-schema.name=${APP_NAME}
LABEL org.label-schema.description="${APP_NAME}: API Rest"
LABEL org.label-schema.build-date=${APP_DATE}
LABEL org.label-schema.vcs-url=${APP_URL}
LABEL org.label-schema.vcs-ref=${APP_BUILD}


COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8080/tcp

ENTRYPOINT ["java","-cp","/app:/app/lib/*","es.um.atica.UmuflyApiApplication"]

