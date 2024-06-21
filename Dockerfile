FROM  maven:3-jdk-8
WORKDIR /dsi-esprit-userService
COPY . .
RUN mvn clean install
EXPOSE 8082

CMD mvn spring-boot:run