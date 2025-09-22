localhost: 
----------

http://localhost:8080/api/ping

Swagger:
http://localhost:8080/swagger-ui/index.html
or
http://localhost:8080/swagger-ui.html
or, with WebConfig.java:
http://localhost:8080/swagger-ui


raw spec (JSON):
http://localhost:8080/v3/api-docs


---------
cd backend
mvn clean verify
mvn spring-boot:run

# Try the API:
curl -s http://localhost:8080/api/tasks | jq .

Create a task:
--------------
curl -s -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"My first task","description":"Hello world","status":"TODO"}' | jq .
