FROM anapsix/alpine-java:8_jdk

#setup MSK timezone
RUN apk update && \
    apk add tzdata sox && \
    cp /usr/share/zoneinfo/Europe/Moscow /etc/localtime && \
    echo "Europe/Moscow" >  /etc/timezone && \
    apk del tzdata

WORKDIR /app
ADD ./target/calling-campaign-management-system.jar .

CMD ["java", "-Xms256m", "-Xmx8G", "-Djava.net.preferIPv4Stack=true", "-jar", "calling-campaign-management-system.jar"]

EXPOSE 8093