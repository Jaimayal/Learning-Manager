package com.jaimayal.learningmanager.presentation;

import com.jaimayal.learningmanager.business.Resource;
import com.jaimayal.learningmanager.business.ResourceService;
import com.jaimayal.learningmanager.business.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class ResourceController {
    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("resources", resourceService.findAllUnfinishedResources());
        return "index";
    }

    @GetMapping("/completed")
    public String completed(Model model) {
        model.addAttribute("resources", resourceService.findAllFinishedResources());
        return "completed";
    }

    @GetMapping("/manage")
    public String manage(Model model) {
        model.addAttribute("resources", resourceService.findAllResources());
        return "manage";
    }

//    @PostMapping("/manage")
//    public String manage(@RequestBody Map<String, String> form) {
//        form.get("operation")
//        return "manage";
//    }
}
