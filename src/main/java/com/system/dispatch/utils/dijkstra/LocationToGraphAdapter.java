package com.system.dispatch.utils.dijkstra;

import com.system.dispatch.models.Obstacle;
import com.system.dispatch.models.Location;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LocationToGraphAdapter {

    public Graph createGraphFromLocations(List<Location> locations) {
        Map<String, Node> nodesMap = locations.stream()
                .map(location -> new Node(location.getName(), location))
                .collect(Collectors.toMap(Node::getName, node -> node));

        Graph graph = new Graph();

        for (Location location : locations) {
            Node from = nodesMap.get(location.getName());

            List<Pair<Node, Integer>> to = location.getFirstLocationSegments()
                    .stream()
                    .map(segment -> Pair.of(nodesMap.get(segment.getSecondLocation().getName()), calculateTravelTime(segment)))
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
