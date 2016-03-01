package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.NotFoundException;
import com.example.entity.Widget;
import com.example.repository.WidgetRepository;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class WidgetController {

    @Autowired
    private WidgetRepository widgetRepository;

    @JsonView(com.example.entity.Widget.class)
    @RequestMapping(value = "/api/widget/{id}", method = RequestMethod.GET, produces = "application/json")
    public Widget getWidgetById(@PathVariable("id") Long id) {
        Widget widget = widgetRepository.findOne(id);
        if (widget == null) {
            throw new NotFoundException(id.toString());
        }

        return widget;
    }

    @RequestMapping(value = "/api/widget/add/{name}/{type}/{source}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(com.example.entity.Widget.class)
    public Long addWidget(@PathVariable("name") String name, @PathVariable("type") String type, @PathVariable("source") String source) {
        Widget widget = new Widget();
        widget.setName(name);
        widget.setWidgetType(type);
        widget.setDataSource(source);

        Widget newWidget = widgetRepository.save(widget);
        if (newWidget == null ) {
            throw new NotFoundException("Can not add new Widget");
        }

        return newWidget.getId();
    }

    @RequestMapping(value = "/api/widget/update/{id}/{name}/{type}/{source}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(com.example.entity.Widget.class)
    public Widget updateWidget(@PathVariable("id") Long id, @PathVariable("name") String name, @PathVariable("type") String type, @PathVariable("source") String source) {
        Widget widget = widgetRepository.findOne(id);
        if (widget == null) {
            throw new NotFoundException(id.toString());
        }

        widget.setName(name);
        widget.setWidgetType(type);
        widget.setDataSource(source);
        Widget updatedWidget = widgetRepository.save(widget);
        if (updatedWidget == null ) {
            throw new NotFoundException("Can not update Widget");
        }

        return updatedWidget;
    }

    @RequestMapping(value = "/api/widget/delete/{id}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(com.example.entity.Widget.class)
    public Boolean deleteWidget(@PathVariable("id") Long id) {
        try {
            widgetRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(id.toString());
        }

        return true;
    }
}
