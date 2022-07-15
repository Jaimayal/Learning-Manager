package com.jaimayal.learningmanager.presentation;

import com.jaimayal.learningmanager.business.Resource;
import com.jaimayal.learningmanager.business.ResourceService;
import com.jaimayal.learningmanager.business.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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
        model.addAttribute("resources", resourceService.findAllUnfinishedResources());
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

        long resourceId = Long.parseLong(form.get("id"));

        if (form.containsKey("edit")) {
            // TODO implement edit
//            return "redirect:/edit?id=" + resourceId;
        } else if (form.containsKey("delete")) {
            // TODO implement delete
            boolean success = resourceService.deleteResourceById(resourceId);
            if (!success) {
                model.addAttribute("message", "Failed to delete resource");
                return "error";
            }

            return "redirect:/manage";
        } else if (form.containsKey("toggle") || form.containsKey("finish")) {
            boolean success = resourceService.toggleFinishStatusById(resourceId);
            if (!success) {
                model.addAttribute("message", "Invalid operation over resource with id " + resourceId);
                return "error";
            }

            return "redirect:/manage";
        } else {
            model.addAttribute("message", "Invalid operation");
            return "error";
        }

        return "redirect:/";
    }

//    @GetMapping("/edit/{id}")
//    public String edit(@PathVariable(required = false) Long id, Model model) {
//        if (id == null) {
//            model.addAttribute("message", "Invalid resource");
//            return "error";
//        }
//
//        Optional<Resource> resourceOptional = resourceService.findResourceById(id.get());
//        if (resourceOptional.isEmpty()) {
//            model.addAttribute("message", "Resource with id " + id.get() + " not found");
//            return "error";
//        }
//
//        Resource resource = resourceOptional.get();
//        model.addAttribute("resource", resource);
//        model.addAttribute("types", ResourceType.values());
//        return "edit";
//    }
}
