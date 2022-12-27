package com.ead.course.controllers;

import com.ead.course.dto.ModuleDto;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.ModuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<ModuleModel> createModule(@PathVariable String courseId,
                                                    @RequestBody @Valid ModuleDto moduleDto) {
      return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.createModule(courseId, moduleDto))  ;
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<String> deleteModule(@PathVariable String courseId,
                                               @PathVariable String moduleId) {
        moduleService.deleteModuleController(courseId, moduleId);
        return ResponseEntity.ok("Module deleted");
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<ModuleModel> updateModule(@PathVariable String courseId,
                                                    @PathVariable String moduleId,
                                                    @RequestBody @Valid ModuleDto moduleDto) {
        return ResponseEntity.ok(moduleService.updateModule(courseId, moduleId, moduleDto));
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<List<ModuleModel>> getAllModules(@PathVariable String courseId) {
        return ResponseEntity.ok(moduleService.getAllModules(courseId));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<ModuleModel> getModuleById(@PathVariable String courseId,
                                                     @PathVariable String moduleId) {
        return ResponseEntity.ok(moduleService.getModuleById(courseId, moduleId));
    }
}
