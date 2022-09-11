# e-wallet
e-wallet demo for KN
# Pull from docker hub

docker pull galipp/ewallet:latest

# Swagger
http://localhost:9090/swagger-ui/index.html
http://localhost:9090/dev

# Run docker
Please keep in mind that if you have  Apple M1 or M2, ARM64 (aarch64) then add the "platform linux/amd64" to your docker run command.

# Dev
docker run -e "SPRING_PROFILES_ACTIVE=dev" -p 9090:8080 galipp/ewallet

docker run --platform linux/amd64  -e "SPRING_PROFILES_ACTIVE=dev" -p 9090:8080 galipp/ewallet

# Test
docker run -e "SPRING_PROFILES_ACTIVE=test" -p 9091:8081 galipp/ewallet

docker run --platform linux/amd64 -e "SPRING_PROFILES_ACTIVE=test" -p 9091:8081 galipp/ewallet

# Prod
docker run -e "SPRING_PROFILES_ACTIVE=prod" -p 9092:8082 galipp/ewallet

docker run --platform linux/amd64 -e "SPRING_PROFILES_ACTIVE=prod" -p 9092:8082 galipp/ewallet

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


# Authentication
Authentication for the specified user and password. 
In this project; 
username: admin
password: 12345678

http://localhost:8080/authenticate

Request:
{
	"userName" : "admin",
	"password" : "12345678"
}

You need to create the token like  in the below. This is sample response:

{
    "jwtResponse": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2Mjg1Mzc4OCwiaWF0IjoxNjYyODM1Nzg4fQ.MlLLHMTwvhkClZI17b0HJQH-xvKgt1HvDMzQE3zMZdc"
}
