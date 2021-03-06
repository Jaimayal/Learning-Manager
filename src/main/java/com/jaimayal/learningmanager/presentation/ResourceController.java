package com.jaimayal.learningmanager.presentation;

import com.jaimayal.learningmanager.business.ResourceService;
import com.jaimayal.learningmanager.business.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Controller
public class ResourceController {
    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute(
                "resources",
                resourceService.findAllNonFinishedResources());

        model.addAttribute(
                "resourceTypes",
                ResourceType.values());
        return "index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("resourceTypes", ResourceType.values());
        return "add";
    }

    @PostMapping("/add")
    public String add(@RequestParam Map<String, String> form, Model model) {
        String name = form.get("name");
        String author = form.get("author");
        String url = form.get("url");
        String type = form.get("type");
        String status = form.get("status");

        boolean success = resourceService.addResource(
                name,
                author,
                url,
                type,
                status);

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
    public String updateResource(@RequestParam Map<String, String> form,
                                 Model model) {
        Long id = form.get("id") == null ?
                null : Long.parseLong(form.get("id"));
        String name = form.get("name");
        String author = form.get("author");
        String url = form.get("url");
        String type = form.get("type");

        boolean success = resourceService.updateResourceById(
                id,
                name,
                author,
                url,
                type);

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
            model.addAttribute(
                    "resource",
                    resourceService.findResourceById(id).orElse(null));

            model.addAttribute(
                    "resourceTypes",
                    ResourceType.values());
            return "edit";
        }

        model.addAttribute("message", "Resource not found");
        return "error";
    }

    @GetMapping("/finished")
    public String completed(Model model) {
        model.addAttribute(
                "resources",
                resourceService.findAllFinishedResources());
        return "history";
    }

    @GetMapping("/manage")
    public String manage(Model model) {
        model.addAttribute(
                "resources",
                resourceService.findAllResources());
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

        if (!form.containsKey("id") ||
                form.get("id").isBlank() || !form.get("id").matches("\\d+")) {
            model.addAttribute("message", "Invalid resource id");
            return "error";
        }

        long resourceId = Long.parseLong(form.get("id"));
        String action = switch (form.get("action")) {
            case "Edit" -> "redirect:/edit/" + resourceId;
            case "Delete" -> "forward:/delete/" + resourceId;
            case "Update Status", "Mark Completed", "Start" ->
                    "forward:/status/" + resourceId;
            default -> {
                model.addAttribute("message", "Invalid action");
                yield "error";
            }
        };

        return action;
    }

    @PostMapping("/status/{id}")
    public String updateResourceStatus(@PathVariable Long id,
                                       @RequestParam
                                       Optional<String> optionalStatus,
                                       @RequestParam Map<String, String> form,
                                       Model model) {
        String status = optionalStatus.orElseGet(() -> form.get("status"));
        boolean success = resourceService.updateResourceStatusById(id, status);

        if (!success) {
            model.addAttribute("message", "Error changing status");
            return "error";
        }

        return "redirect:/";
    }
}

