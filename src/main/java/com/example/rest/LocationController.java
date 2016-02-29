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
    @JsonView(com.example.entity.Widget.class)
    public Long placeWidget(@PathVariable("workspaceId") Long workspaceId, @PathVariable("widgetId") Long widgetId, @PathVariable("rowNumber") int rowNumber, @PathVariable("columnNumber") int columnNumber) {
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

        //return newLocation.getPk().getId();
        
        workspace.getWidgetLocations().add(newLocation);
        workspaceRepository.save(workspace);

        //workspace.getWidgetLocations()
System.out.println(newLocation.getPk());
System.out.println(newLocation.getPk().getId());
        return newLocation.getPk().getId();
        
        /*
Comment newComment = new Comment();
		newComment.setComment(commentJsonWrapper.getComment());
		newComment.setCommentDate(currentDateime);
		newComment.setUser(user);
		newComment.setTicket(ticket);
		commentRepository.save(newComment);
		
		// update ticket
		ticket.getUserComments().add(newComment);
		ticket.setTicketUpdated(currentDateime);
		ticketRepository.save(ticket); 
 */
        
        
/*
        Widget widget = new Widget();
        widget.setName(name);
        widget.setWidgetType(type);

        Widget newWidget = widgetRepository.save(widget);
        if (newWidget == null ) {
            throw new NotFoundException("Can not add new Widget");
        }

        return newWidget.getId();
*/
    }
/*
    @JsonView(com.example.entity.Widget.class)
    @RequestMapping(value = "/api/widget/{id}", method = RequestMethod.GET, produces = "application/json")
    public Widget getWidgetById(@PathVariable("id") Long id) {
        Widget widget = widgetRepository.findOne(id);
        if (widget == null) {
            throw new NotFoundException(id.toString());
        }

        return widget;
    }

    @RequestMapping(value = "/api/widget/add/{name}/{type}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(com.example.entity.Widget.class)
    public Long addWidget(@PathVariable("name") String name, @PathVariable("type") String type) {
        Widget widget = new Widget();
        widget.setName(name);
        widget.setWidgetType(type);

        Widget newWidget = widgetRepository.save(widget);
        if (newWidget == null ) {
            throw new NotFoundException("Can not add new Widget");
        }

        return newWidget.getId();
    } 
 */

}
