FROM amazoncorretto:17

COPY target/backend-1.0.jar target/backend-1.0.jar
COPY src/main/resources/cp-national-datafile.json src/main/resources/cp-national-datafile.json
COPY src/main/resources/MsCode_json.json src/main/resources/MsCode_json.json

EXPOSE 2223

ENTRYPOINT ["java", "-jar", "/target/backend-1.0.jar"]