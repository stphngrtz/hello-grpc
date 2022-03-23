package org.acme;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

@GrpcService
public class RouteGuideService implements RouteGuide {

    @Override
    public Multi<Feature> listFeatures(Rectangle request) {
        return Multi.createFrom()
                .range(1, 6)
                .map(i -> Feature.newBuilder().setName("Feature #" + i).setLocation(Point.newBuilder().setLatitude(i).setLongitude(i * 10)).build())
                .onItem().call(i -> Uni.createFrom().nullItem().onItem().delayIt().by(Duration.ofMillis(300)));
    }

    @Override
    public Uni<RouteSummary> recordRoute(Multi<Point> request) {
        return request.collect()
                .in(
                        RouteSummary::newBuilder,
                        (builder, point) -> builder.setPointCount(builder.getPointCount() + 1)
                )
                .map(RouteSummary.Builder::build);
    }

    @Override
    public Multi<RouteNote> routeChat(Multi<RouteNote> request) {
        return request.map(requestRouteNote -> RouteNote.newBuilder()
                .setLocation(requestRouteNote.getLocation())
                .setMessage("Oh, I'd love to visit this location, too!")
                .build()
        );
    }
}
