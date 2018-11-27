#/bin/bash
while ! nc -z $MONGO_HOST $PORT; do sleep 3; done
java -Xms256m -Xmx8G -Djava.net.preferIPv4Stack=true ${JAVA_OPTS} -jar call-task-service.jar --logging.level.tech.ivoice=$LOG_LEVEL_APP  --logging.level.root=$LOG_LEVEL_ROOT