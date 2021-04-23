FROM java:8
VOLUME /tmp
ADD mes-1.0.0.jar rmes.jar
EXPOSE 7080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/rmes.jar"]