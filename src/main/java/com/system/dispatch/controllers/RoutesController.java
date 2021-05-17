package com.system.dispatch.controllers;

import com.system.dispatch.models.Location;
import com.system.dispatch.repositories.LocationRepository;
import com.system.dispatch.utils.dijkstra.Graph;
import com.system.dispatch.utils.dijkstra.GraphCalculationService;
import com.system.dispatch.utils.dijkstra.Node;
import com.system.dispatch.utils.dijkstra.LocationToGraphAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RoutesController {

    private final LocationRepository locationRepository;
    private final LocationToGraphAdapter locationToGraphAdapter;

    public RoutesController(LocationRepository locationRepository, LocationToGraphAdapter locationToGraphAdapter) {
        this.locationRepository = locationRepository;
        this.locationToGraphAdapter = locationToGraphAdapter;
    }

    @PostMapping("/routes/calculate")
    public String calculateShortestPath(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (routeToSameLocation(request)) {
            redirectAttributes.addFlashAttribute("error", "Can not calculate route to the same location");

            return "redirect:/routes/calculate";
        }

        Optional<Location> toLocation = locationRepository.findById(Integer.valueOf(request.getParameter("to")));
        Graph graph = this.locationToGraphAdapter.createGraphFromLocations(getLocations());

        Optional<Node> fromNode = graph.getNodes().stream()
                .filter(node -> node.getLocation().getId().equals(Integer.valueOf(request.getParameter("from"))))
                .findFirst();

        if (fromNode.isPresent() && toLocation.isPresent()) {
            GraphCalculationService.calculateShortestPathFromSource(graph, fromNode.get());
            Optional<Node> toNode = graph.getNodes().stream().filter(node -> node.getLocation().equals(toLocation.get())).findFirst();

            model.addAttribute("toNode", toNode.get());
        } else {
            model.addAttribute("error", "Could not find selected locations");

            return "route/routeForm";
        }

        return "route/displayRoute";
    }

    private boolean routeToSameLocation(HttpServletRequest request) {
        return request.getParameter("from").equals(request.getParameter("to"));
    }

    @GetMapping("/routes/calculate")
    public String routesForm(Model model) {
        List<Location> locations = getLocations();
        model.addAttribute("locations", locations);

//        Graph graph = this.placeToGraphAdapter.createGraphFromPlaces(places);
//        GraphCalculationService.calculateShortestPathFromSource(graph, graph.getNodes().iterator().next());

        return "route/routeForm";
    }

    private List<Location> getLocations() {
        Iterable<Location> locationIterable = locationRepository.findAll();
        List<Location> locations = new ArrayList<>();

        for (Location location : locationIterable) {
            locations.add(location);
        }

        return locations;
    }
}
