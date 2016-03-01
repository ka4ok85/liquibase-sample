package com.example.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "widgets")
public class Widget implements Persistable<Long> {

    private static final long serialVersionUID = 1061338702674330808L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @JsonView(com.example.entity.Widget.class)
    @Column(name = "name", unique = false, nullable = false, length = 255)
    private String name;

    @JsonView(com.example.entity.Widget.class)
    @Column(name = "widget_type", unique = false, nullable = false, length = 255)
    private String widgetType;

    @JsonView(com.example.entity.Widget.class)
    @Column(name = "data_source", unique = false, nullable = false, length = 255)
    private String dataSource;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.widget", cascade={CascadeType.PERSIST, CascadeType.MERGE})
    Set<Location> WidgetLocations = new HashSet<Location>();

    public Widget() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(String widgetType) {
        this.widgetType = widgetType;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Set<Location> getWidgetLocations() {
        return WidgetLocations;
    }

    public void setWidgetLocations(Set<Location> widgetLocations) {
        WidgetLocations = widgetLocations;
    }

    @Override
    public String toString() {
        return "Widget [name=" + name + ", widgetType=" + widgetType + "]";
    }

    @Override
    public Long getId() {
        return id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    @Override
    @Transient
    public boolean isNew() {
        return null == getId();
    }

/*
@Entity
@Table(name = "widgets")
public class Widget extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 1061338702674330808L;

    @JsonView(com.example.entity.Widget.class)
    @Column(name = "name", unique = true, nullable = false, length = 255)
    private String name;

    @JsonView(com.example.entity.Widget.class)
    @Column(name = "widget_type", unique = false, nullable = false, length = 255)
    private String widgetType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.widget", cascade={CascadeType.PERSIST, CascadeType.MERGE})
    Set<Location> WidgetLocations = new HashSet<Location>();

    public Widget() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(String widgetType) {
        this.widgetType = widgetType;
    }

    public Set<Location> getWidgetLocations() {
        return WidgetLocations;
    }

    public void setWidgetLocations(Set<Location> widgetLocations) {
        WidgetLocations = widgetLocations;
    }

    @Override
    public String toString() {
        return "Widget [name=" + name + ", widgetType=" + widgetType + "]";
    }
*/
}
