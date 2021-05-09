package com.system.dispatch.controllers;

import com.system.dispatch.models.Place;
import com.system.dispatch.repositories.PlaceRepository;
import com.system.dispatch.utils.dijkstra.Graph;
import com.system.dispatch.utils.dijkstra.GraphCalculationService;
import com.system.dispatch.utils.dijkstra.PlaceToGraphAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ShortestPathController {

    private final PlaceRepository placeRepository;
    private final PlaceToGraphAdapter placeToGraphAdapter;

    public ShortestPathController(PlaceRepository placeRepository, PlaceToGraphAdapter placeToGraphAdapter) {
        this.placeRepository = placeRepository;
        this.placeToGraphAdapter = placeToGraphAdapter;
    }

    @RequestMapping("/shortestPath")
    public String calculateShortestPath() {
        List<Place> places = getPlaces();

        Graph graph = this.placeToGraphAdapter.createGraphFromPlaces(places);
        GraphCalculationService.calculateShortestPathFromSource(graph, graph.getNodes().iterator().next());

        return "home";
    }

    private List<Place> getPlaces() {
        Iterable<Place> placeIterable = placeRepository.findAll();
        List<Place> places = new ArrayList<>();

        for (Place place : placeIterable) {
            places.add(place);
        }

        return places;
    }
}
