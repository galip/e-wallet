# e-wallet
e-wallet demo for KN
# Pull from docker hub

docker pull galipp/ewallet.jar

# Swagger
http://localhost:9090/swagger-ui/index.html
http://localhost:9090/dev

# Run docker
# Dev;
docker run -e "SPRING_PROFILES_ACTIVE=dev" -p 9090:8080 galipp/ewallet.jar
# Test;
docker run -e "SPRING_PROFILES_ACTIVE=test" -p 9091:8081 galipp/ewallet.jar
# Prod;
docker run -e "SPRING_PROFILES_ACTIVE=prod" -p 9092:8082 galipp/ewallet.jar
# H2 for DEV
http://localhost:9090/h2-console

db url : jdbc:h2:mem:ewallet-dev
username:sa
password:

# H2 FOR TEST
http://localhost:9091/h2-console

db url : jdbc:h2:mem:ewallet-test
username:sa
password:

# H2 FOR PROD
http://localhost:9092/h2-console

db url : jdbc:h2:mem:ewallet-prod
username:sa
password:
