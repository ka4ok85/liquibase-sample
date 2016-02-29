package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.NotFoundException;
import com.example.entity.Workspace;
import com.example.repository.WorkspaceRepository;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class WorkspaceController {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @JsonView(com.example.entity.Workspace.class)
    @RequestMapping(value = "/api/workspace/{id}", method = RequestMethod.GET, produces = "application/json")
    public Workspace getWorkspaceById(@PathVariable("id") Long id) {
        Workspace workspace = workspaceRepository.findOne(id);
        if (workspace == null) {
            throw new NotFoundException(id.toString());
        }

        return workspace;
    }

    @RequestMapping(value = "/api/workspace/add/{name}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(com.example.entity.Workspace.class)
    public Long addWorkspace(@PathVariable("name") String name) {
        Workspace workspace = new Workspace();
        workspace.setName(name);

        Workspace newWorkspace = workspaceRepository.save(workspace);
        if (newWorkspace == null ) {
            throw new NotFoundException("Can not add new Workspace");
        }

        return newWorkspace.getId();
    }

    @RequestMapping(value = "/api/workspace/update/{id}/{name}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(com.example.entity.Workspace.class)
    public Workspace updateWorkspace(@PathVariable("id") Long id, @PathVariable("name") String name) {
        Workspace workspace = workspaceRepository.findOne(id);
        if (workspace == null) {
            throw new NotFoundException(id.toString());
        }

        workspace.setName(name);
        Workspace updatedWorkspace = workspaceRepository.save(workspace);
        if (updatedWorkspace == null ) {
            throw new NotFoundException("Can not update Workspace");
        }

        return updatedWorkspace;
    }

    @RequestMapping(value = "/api/workspace/delete/{id}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(com.example.entity.Workspace.class)
    public Boolean deleteWorkspace(@PathVariable("id") Long id) {
        try {
            workspaceRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(id.toString());
        }

        return true;
    }
}
