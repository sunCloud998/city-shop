FROM java:8
VOLUME /tmp
ADD shop-api-0.0.1.jar /shop-api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/shop-api.jar"]