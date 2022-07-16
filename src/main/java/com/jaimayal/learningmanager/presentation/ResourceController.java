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

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        boolean success = resourceService.deleteResourceById(id);

        if (!success) {
            model.addAttribute("message", "Error deleting resource");
            return "error";
        }

        return "redirect:/manage";
    }

    @PostMapping("/edit")
    public String updateResource(@RequestParam Map<String, String> form, Model model) {
        Long id = form.get("id") == null ? null : Long.parseLong(form.get("id"));
        String name = form.get("name");
        String description = form.get("description");
        String url = form.get("url");
        String type = form.get("type");

        boolean success = resourceService.updateResourceById(id, name, description, url, type);

        if (!success) {
            model.addAttribute("message", "Error updating resource");
            return "error";
        }

        return "redirect:/manage";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        boolean exists = resourceService.existsResourceById(id);
        if (exists) {
            model.addAttribute("resource", resourceService.findResourceById(id).orElse(null));
            return "edit";
        }

        model.addAttribute("message", "Resource not found");
        return "error";
    }

    @GetMapping("/finished")
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
        if (!form.containsKey("action") || form.get("action").isBlank()) {
            model.addAttribute("message", "Invalid action");
            return "error";
        }

        if ("Add".equals(form.get("action"))) {
            return "forward:/add";
        }

        if (!form.containsKey("id") || form.get("id").isBlank() || !form.get("id").matches("\\d+")) {
            model.addAttribute("message", "Invalid resource id");
            return "error";
        }

        long resourceId = Long.parseLong(form.get("id"));
        String action = switch (form.get("action")) {
            case "Edit" -> "redirect:/edit/" + resourceId;
            case "Delete" -> "forward:/delete/" + resourceId;
            case "Toggle" -> "forward:/status/" + resourceId;
            default -> {
                model.addAttribute("message", "Invalid action");
                yield "error";
            }
        };

        return action;
    }

    @PostMapping("/status/{id}")
    public String updateResourceStatus(@PathVariable Long id, @RequestParam Map<String, String> form, Model model) {
        boolean success = resourceService.updateResourceStatusById(id, form.get("status"));

        if (!success) {
            model.addAttribute("message", "Error changing status");
            return "error";
        }

        return "redirect:/manage";
    }
}

