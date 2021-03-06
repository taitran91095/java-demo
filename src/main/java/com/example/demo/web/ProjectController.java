package com.example.demo.web;


import javax.validation.Valid;

import com.example.demo.domain.Project;
import com.example.demo.service.MapValidationErrorsService;
import com.example.demo.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorsService mapValidationErrorsService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project , BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorsService.MapValidationErrorsService(result);
        if(errorMap!=null)return errorMap;

        Project newProject = projectService.saveProject(project);

        return new ResponseEntity<Project>(newProject, HttpStatus.CREATED);
    }

    @GetMapping("model")
    public ResponseEntity<?> getModel(){
               return new ResponseEntity<Project>(new Project(), HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){
        Project project = projectService.findProjectByIdentifier(projectId);

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }
    
    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
    	return projectService.findAllProjects();
    }
    
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId){
    	projectService.deleteProjectByIdentifier(projectId);
    	return new ResponseEntity<String>("Project '"+projectId+"' is deleted",HttpStatus.OK);
    }
}
