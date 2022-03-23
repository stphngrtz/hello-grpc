# Hello gRPC

Long time no see! Well well, quite some time has passed since my last *hello ...* repository. Today I wanted to give gRPC with Quarkus a try, so why not share it with whoever finds it useful or interesting. Here we are, lets go.

I've split the example into three projects.
- `model` that has the `.proto` files
- `server` that implements the gRPC services
- `client` that calls the gRPC services

Here is how to build and run the example:

```bash
mvn install -f model
mvn package -f server && java -jar target/quarkus-app/quarkus-run.jar
mvn package -f client && java -jar target/quarkus-app/quarkus-run.jar
```

The server starts at port 9000, the client at port 8080.

```bash
curl http://localhost:8080/hello/stephan
```

The `/route` path of the client comes with some more advanced examples.

```bash
# server to client stream
curl http://localhost:8080/route/list
# client to server stream
curl http://localhost:8080/route/record
# bidirectional stream
curl http://localhost:8080/route/chat
```