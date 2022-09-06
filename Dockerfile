FROM openjdk:11
WORKDIR /usr/src/adpay
COPY target/adpay-0.0.1-SNAPSHOT.jar /usr/src/adpay
CMD java -Dlogging.level.com.fab.adpay=DEBUG -Dsun.net.client.defaultConnectTimeout=30000 -Dsun.net.client.defaultReadTimeout=30000 -jar adpay-0.0.1-SNAPSHOT.jar
