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
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/manage")
    public String manage(@RequestParam Map<String, String> form, Model model) {
        if (!form.containsKey("id") || form.get("id").isBlank() || !form.get("id").matches("\\d+")) {
            model.addAttribute("message", "Invalid resource id");
            return "error";
        }

        long resourceId = Long.parseLong(form.get("id"));

        if (form.containsKey("edit")) {
            // TODO implement edit
        } else if (form.containsKey("delete")) {
            // TODO implement delete
        } else if (form.containsKey("toggle") || form.containsKey("finish")) {
            boolean success = resourceService.toggleFinishStatusById(resourceId);
            if (success) {
                return "redirect:/";
            }

            model.addAttribute("message", "Invalid operation over resource with id " + resourceId);
            return "error";
        } else {
            return "redirect:/manage";
        }

        return "redirect:/";
    }

//    @GetMapping("/edit/{id}")
//    public String edit(@RequestParam long id, Model model) {
//        Resource resource = resourceService.findResourceById(id);
//        if (resource == null) {
//            return "redirect:/manage";
//        }
//        model.addAttribute("resource", resource);
//        return "edit";
//    }
}
