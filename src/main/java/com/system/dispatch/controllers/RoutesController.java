package com.system.dispatch.controllers;

import com.system.dispatch.models.Place;
import com.system.dispatch.repositories.PlaceRepository;
import com.system.dispatch.utils.dijkstra.Graph;
import com.system.dispatch.utils.dijkstra.GraphCalculationService;
import com.system.dispatch.utils.dijkstra.Node;
import com.system.dispatch.utils.dijkstra.PlaceToGraphAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RoutesController {

    private final PlaceRepository placeRepository;
    private final PlaceToGraphAdapter placeToGraphAdapter;

    public RoutesController(PlaceRepository placeRepository, PlaceToGraphAdapter placeToGraphAdapter) {
        this.placeRepository = placeRepository;
        this.placeToGraphAdapter = placeToGraphAdapter;
    }

    @PostMapping("/routes/calculate")
    public String calculateShortestPath(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (routeToSameLocation(request)) {
            redirectAttributes.addFlashAttribute("error", "Can not calculate route to the same location");

            return "redirect:/routes/calculate";
        }

        Optional<Place> toPlace = placeRepository.findById(Integer.valueOf(request.getParameter("to")));
        Graph graph = this.placeToGraphAdapter.createGraphFromPlaces(getPlaces());

        Optional<Node> fromNode = graph.getNodes().stream()
                .filter(node -> node.getPlace().getId().equals(Integer.valueOf(request.getParameter("from"))))
                .findFirst();

        if (fromNode.isPresent() && toPlace.isPresent()) {
            GraphCalculationService.calculateShortestPathFromSource(graph, fromNode.get());
            Optional<Node> toNode = graph.getNodes().stream().filter(node -> node.getPlace().equals(toPlace.get())).findFirst();

            model.addAttribute("toNode", toNode.get());
        } else {
            model.addAttribute("error", "Could not find selected places");

            return "route/routeForm";
        }

        return "route/displayRoute";
    }

    private boolean routeToSameLocation(HttpServletRequest request) {
        return request.getParameter("from").equals(request.getParameter("to"));
    }

    @GetMapping("/routes/calculate")
    public String routesForm(Model model) {
        List<Place> places = getPlaces();
        model.addAttribute("places", places);

//        Graph graph = this.placeToGraphAdapter.createGraphFromPlaces(places);
//        GraphCalculationService.calculateShortestPathFromSource(graph, graph.getNodes().iterator().next());

        return "route/routeForm";
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
