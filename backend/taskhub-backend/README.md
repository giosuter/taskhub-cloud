Quick smoke tests (terminal)
----------------------------

# 1) OpenAPI JSON (expect: 200 + JSON)
curl -i http://localhost:8080/taskhub/v3/api-docs

# 2) Swagger UI entry (expect: 302 redirect to /taskhub/swagger-ui/index.html)
curl -i http://localhost:8080/taskhub/swagger-ui.html

# 3) Actuator health (expect: 200 {"status":"UP"})
curl -i http://localhost:8080/taskhub/actuator/health

# 4) Ping (expect: 200 "pong")
curl -i http://localhost:8080/taskhub/api/ping


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
