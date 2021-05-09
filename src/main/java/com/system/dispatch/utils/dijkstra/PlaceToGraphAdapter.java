package com.system.dispatch.utils.dijkstra;

import com.system.dispatch.models.Place;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlaceToGraphAdapter {

    public Graph createGraphFromPlaces(List<Place> places) {
        Map<String, Node> nodesMap = places.stream()
                .map(place -> new Node(place.getName()))
                .collect(Collectors.toMap(Node::getName, node -> node));

        Graph graph = new Graph();

        for (Place place : places) {
            Node from = nodesMap.get(place.getName());

            List<Pair<Node, Integer>> to = place.getFirstLocationSegments()
                    .stream()
                    .map(segment -> Pair.of(nodesMap.get(segment.getSecondPlace().getName()), segment.getTravelTime()))
                    .collect(Collectors.toList());

            from.addDestinations(to);

            graph.addNode(from);
        }

        return graph;
    }
}
