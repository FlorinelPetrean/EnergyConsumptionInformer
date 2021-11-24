FROM maven:3.6.3-jdk-11 AS builder

COPY ./src/ /root/src
COPY ./pom.xml /root/
COPY ./checkstyle.xml /root/
WORKDIR /root
RUN mvn package
RUN java -Djarmode=layertools -jar /root/target/EnergyUtilityPlatform-0.0.1-SNAPSHOT.jar list
RUN java -Djarmode=layertools -jar /root/target/EnergyUtilityPlatform-0.0.1-SNAPSHOT.jar extract
RUN ls -l /root

FROM openjdk:11.0.6-jre

ENV TZ=UTC
ENV DB_IP=ec2-54-228-99-58.eu-west-1.compute.amazonaws.com
ENV DB_PORT=5432
ENV DB_USER=wqpbyihwrpexor
ENV DB_PASSWORD=ae89f89167d391d2e2ef72e5f45d933a8d25382b7fa84ba5438f74e49644a00a
ENV DB_DBNAME=d1gutrkdpd57qh


COPY --from=builder /root/dependencies/ ./
COPY --from=builder /root/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /root/spring-boot-loader/ ./
COPY --from=builder /root/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]