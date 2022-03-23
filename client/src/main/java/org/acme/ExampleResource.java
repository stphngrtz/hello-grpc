package org.acme;

import io.quarkus.grpc.GrpcClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.time.Duration;

@Path("/hello")
public class ExampleResource {

    @GrpcClient
    HelloGrpc hello;

    @GET
    @Path("/{name}")
    public String hello(@PathParam("name") String name) {
        return hello.sayHello(HelloRequest.newBuilder().setName(name).build())
                .onItem().transform(HelloReply::getMessage)
                .await().atMost(Duration.ofSeconds(5)); // should be using quarkus-resteasy-reactive instead of quarkus-resteasy
    }
}
