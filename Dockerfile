FROM makanab/utilityshell

COPY target/mechwar-0.0.1-SNAPSHOT.jar /usr/share/java/mechwar.jar
USER root
RUN chmod a+rx /usr/share/java/mechwar.jar
USER 999
CMD ["java", "-jar", "/usr/share/java/mechwar.jar"]