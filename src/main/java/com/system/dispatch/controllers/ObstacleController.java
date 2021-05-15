package com.system.dispatch.controllers;

import com.system.dispatch.models.Obstacle;
import com.system.dispatch.models.Segment;
import com.system.dispatch.repositories.ObstacleRepository;
import com.system.dispatch.repositories.SegmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
public class ObstacleController {

    private final SegmentRepository segmentRepository;

    private final ObstacleRepository obstacleRepository;

    @Autowired
    public ObstacleController(SegmentRepository segmentRepository, ObstacleRepository obstacleRepository) {
        this.segmentRepository = segmentRepository;
        this.obstacleRepository = obstacleRepository;
    }

    @GetMapping("/obstacle/create")
    public String create(Model model) {
        Iterable<Segment> segments = segmentRepository.findAll();

        model.addAttribute("segments", segments);
        model.addAttribute("obstacle", new Obstacle());

        return "obstacle/obstacleForm";
    }

    @PostMapping("/obstacle/create")
    public String createPost(Model model, @Valid @ModelAttribute("obstacle") Obstacle obstacle, @RequestParam Integer segmentId, @RequestParam String expiration) {
        Optional<Segment> optionalSegment = segmentRepository.findById(segmentId);
        obstacle.setExpirationTime(parseStringTime(expiration));

        if (optionalSegment.isPresent()) {
            obstacleRepository.save(obstacle);
            Segment segment = optionalSegment.get();
            segment.setObstacle(obstacle);

            segmentRepository.save(segment);
        }

        model.addAttribute("success", "Obstacle saved successfully!");

        return "home";
    }

    private LocalDateTime parseStringTime(String dt) {
        return LocalDateTime.parse(dt, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
