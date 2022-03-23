package org.acme;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;

@GrpcService
public class GreeterGrpcService extends GreeterGrpcGrpc.GreeterGrpcImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) { // default grpc API, see HelloGrpcService for mutiny style
        String message = "Hello " + request.getName() + "!";
        responseObserver.onNext(HelloReply.newBuilder().setMessage(message).build());
        responseObserver.onCompleted();
    }
}
