package com.system.dispatch.utils.dijkstra;

import com.system.dispatch.models.Obstacle;
import com.system.dispatch.models.Place;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlaceToGraphAdapter {

    public Graph createGraphFromPlaces(List<Place> places) {
        Map<String, Node> nodesMap = places.stream()
                .map(place -> new Node(place.getName(), place))
                .collect(Collectors.toMap(Node::getName, node -> node));

        Graph graph = new Graph();

        for (Place place : places) {
            Node from = nodesMap.get(place.getName());

            List<Pair<Node, Integer>> to = place.getFirstLocationSegments()
                    .stream()
                    .map(segment -> Pair.of(nodesMap.get(segment.getSecondPlace().getName()), calculateTravelTime(segment)))
                    .collect(Collectors.toList());

            from.addDestinations(to);

            graph.addNode(from);
        }

        return graph;
    }

    private Integer calculateTravelTime(com.system.dispatch.models.Segment segment) {
        Obstacle obstacle = segment.getObstacle();
        int multiplier = 1;

        if (obstacle != null && obstacle.getExpirationTime().isAfter(LocalDateTime.now())) {
            multiplier = 10;
        }

        return segment.getTravelTime() * multiplier;
    }
}
