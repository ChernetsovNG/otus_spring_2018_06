FROM anapsix/alpine-java
MAINTAINER Nikita Chernetsov (n.chernetsov86@gmail.com)
WORKDIR /
COPY build/libs/library-info-app-*.jar library-info-app.jar
EXPOSE 8080
EXPOSE 27017
CMD java -jar library-info-app.jar
