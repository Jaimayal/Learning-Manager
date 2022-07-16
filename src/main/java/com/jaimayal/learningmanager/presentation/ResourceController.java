package com.jaimayal.learningmanager.presentation;

import com.jaimayal.learningmanager.business.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("resources", resourceService.findAllInProgressResources());
        return "index";
    }

    @GetMapping("/history")
    public String completed(Model model) {
        model.addAttribute("resources", resourceService.findAllFinishedResources());
        return "history";
    }

    @GetMapping("/manage")
    public String manage(Model model) {
        model.addAttribute("resources", resourceService.findAllResources());
        return "manage";
    }

    @PostMapping("/manage")
    public String manage(@RequestParam Map<String, String> form, Model model) {
        if (!form.containsKey("id") || form.get("id").isBlank() || !form.get("id").matches("\\d+")) {
            model.addAttribute("message", "Invalid resource id");
            return "error";
        }

        if (!form.containsKey("action") || form.get("action").isBlank()) {
            model.addAttribute("message", "Invalid action");
            return "error";
        }

        long resourceId = Long.parseLong(form.get("id"));
        String action = switch(form.get("action")) {
            case "Edit" -> "redirect:/edit/" + resourceId;
            case "Delete" -> "forward:/delete/" + resourceId;
            case "Toggle" -> "forward:/finish/" + resourceId;
            case "Add" -> "forward:/add";
            default -> {
                model.addAttribute("message", "Invalid action");
                yield "error";
            }
        };

        return action;
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        // TODO implement edit
        return "redirect:/";
    }

    @PostMapping("/add")
    public String add(@RequestParam Map<String, String> form, Model model) {
        String name = form.get("name");
        String description = form.get("description");
        String url = form.get("url");
        String type = form.get("type");
        String status = form.get("status");

        boolean success = resourceService.addResource(name, description, url, type, status);

        if (!success) {
            model.addAttribute("message", "Error adding resource");
            return "error";
        }

        return "redirect:/manage";
    }

    @PutMapping("/finish/{id}")
    public String changeStatus(@PathVariable Long id, Model model) {
        boolean success = resourceService.setFinishedStatusById(id);

        if (!success) {
            model.addAttribute("message", "Error changing status");
            return "error";
        }

        return "redirect:/manage";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        boolean success = resourceService.deleteResourceById(id);

        if (!success) {
            model.addAttribute("message", "Error deleting resource");
            return "error";
        }

        return "redirect:/manage";
    }
}
