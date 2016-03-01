package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.NotFoundException;
import com.example.entity.Location;
import com.example.entity.Widget;
import com.example.entity.Workspace;
import com.example.repository.LocationRepository;
import com.example.repository.WidgetRepository;
import com.example.repository.WorkspaceRepository;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private WidgetRepository widgetRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @RequestMapping(value = "/api/placeWidget/{workspaceId}/{widgetId}/{rowNumber}/{columnNumber}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(com.example.entity.Location.class)
    public Location placeWidget(@PathVariable("workspaceId") Long workspaceId, @PathVariable("widgetId") Long widgetId, @PathVariable("rowNumber") int rowNumber, @PathVariable("columnNumber") int columnNumber) {
        Workspace workspace = workspaceRepository.findOne(workspaceId);
        if (workspace == null) {
            throw new NotFoundException(workspaceId.toString());
        }

        Widget widget = widgetRepository.findOne(widgetId);
        if (widget == null) {
            throw new NotFoundException(widgetId.toString());
        }

        Location newLocation = new Location();
        newLocation.setWorkspace(workspace);
        newLocation.setWidget(widget);
        newLocation.setColumnNumber(columnNumber);
        newLocation.setRowNumber(rowNumber);
        newLocation = locationRepository.save(newLocation);

        workspace.getWidgetLocations().add(newLocation);
        workspaceRepository.save(workspace);

        return newLocation;
    }
}
