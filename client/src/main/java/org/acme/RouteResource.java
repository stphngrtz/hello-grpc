package org.acme;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;

@Path("/route")
public class RouteResource {

    @GrpcClient
    RouteGuide routeGuide;

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_PLAIN)
    public Multi<String> listFeatures() {
        return routeGuide.listFeatures(Rectangle.getDefaultInstance())
                .map(feature -> feature.getName() + " at " + feature.getLocation());
    }

    @GET
    @Path("/record")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> recordRoute() {
        return routeGuide.recordRoute(
                        Multi.createFrom()
                                .range(1, 4)
                                .map(i -> Point.newBuilder().setLatitude(i).setLongitude(i * 10).build())
                                .onItem().call(i -> Uni.createFrom().nullItem().onItem().delayIt().by(Duration.ofMillis(500)))
                )
                .map(summary -> "A total of " + summary.getPointCount() + " points have been visited! Great!");
    }

    @GET
    @Path("/chat")
    @Produces(MediaType.TEXT_PLAIN)
    public Multi<String> routeChat() {
        return routeGuide.routeChat(
                        Multi.createFrom()
                                .range(1, 4)
                                .map(i -> RouteNote.newBuilder().setLocation(Point.newBuilder().setLatitude(i).setLongitude(i * 10)).build())
                                .onItem().call(i -> Uni.createFrom().nullItem().onItem().delayIt().by(Duration.ofMillis(500)))
                )
                .map(note -> "Location: " + note.getLocation() + " - Message: " + note.getMessage());
    }
}
