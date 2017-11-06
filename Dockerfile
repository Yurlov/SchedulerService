FROM openjdk:8
EXPOSE 8080
ADD /target/SchedulerService.jar SchedulerService.jar
ENTRYPOINT ["java","-jar","SchedulerService.jar"]
