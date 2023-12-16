package com.success.project.kindacoffee.controller;

import com.success.project.kindacoffee.entities.manufacturing.Robot;
import com.success.project.kindacoffee.services.manufacturing.RobotService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/robots")
public class RobotController {

    private final RobotService service;

    public RobotController(RobotService service) {
        this.service = service;
    }

    @GetMapping(value = "/all")
    public String getAllRobots(Model model) {
        List<Robot> listOfAllRobots = service.findAll();
        model.addAttribute("robots", listOfAllRobots);
        return "robots";
    }

    @GetMapping(value = "/single")
    public String getSingleRobotById(Model model, @RequestParam Integer id) {
        Optional<Robot> foundedRobot = service.find(id);
        if (foundedRobot.isPresent()) {
            model.addAttribute("robot", foundedRobot.get());
            model.addAttribute("manufacturingProcess", foundedRobot.get()
                    .getManufacturingProcessList());
        }
        return "singleRobot";
    }

}
