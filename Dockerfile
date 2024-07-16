FROM  maven:3-jdk-8
WORKDIR /dsi-esprit-userService
COPY . .
RUN ["mvn", "install", "-Dmaven.test.skip=true"]
EXPOSE 8082

CMD mvn spring-boot:run