#Pull image from docker hub. To get full Docker Images test results, create account and check https://snyk.io/docker-images/
#Current image vulnerability report https://snyk.io/test/docker/openjdk%3A19-alpine3.16
FROM amazoncorretto:19-alpine

#Update Alpine Packages. Also Alpine will clean up packages automatically. 
#RUN apk update
#RUN apk upgrade -U -a

#Update Debian Packages. 
#RUN apt update && apt upgrade -y 
#RUN apt dist-upgrade -y

# Dont run Java in PID 1
#RUN apk add dumb-init

#Run the application as a non-root user - For Alpine OS
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /usr/src/adpay

#Run the application as a non-root user - For Debian OS
#RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
#RUN addgroup --system javauser && adduser --no-create-home --ingroup javauser javauser
#RUN chown -R spring:spring /usr/src/adpay
#USER spring

#Copy JAR to Linux
COPY target/adpay-0.0.1-SNAPSHOT.jar /usr/src/adpay


#Run Adpay
CMD java -Dlogging.level.com.fab.adpay=DEBUG -Dsun.net.client.defaultConnectTimeout=30000 -Dsun.net.client.defaultReadTimeout=30000 -jar adpay-0.0.1-SNAPSHOT.jar