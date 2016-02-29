package com.example.entity;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "workspaces")
//public class Workspace extends AbstractPersistable<Long> {
public class Workspace implements Persistable<Long> {
    private static final long serialVersionUID = -6021427610331391553L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @JsonView(com.example.entity.Workspace.class)
    @Column(name = "name", unique = false, nullable = false, length = 255)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.workspace", cascade={CascadeType.PERSIST, CascadeType.MERGE})
    Set<Location> WidgetLocations = new HashSet<Location>();

    public Workspace() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Location> getWidgetLocations() {
        return WidgetLocations;
    }

    public void setWidgetLocations(Set<Location> widgetLocations) {
        WidgetLocations = widgetLocations;
    }

    @Override
    public String toString() {
        return "Workspace [name=" + name + "]";
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
@Table(name = "workspaces")
//public class Workspace extends AbstractPersistable<Long> {
public class Workspace<PK extends Serializable> implements Persistable<PK> {
    private static final long serialVersionUID = -6021427610331391553L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private PK id;

    @JsonView(com.example.entity.Workspace.class)
    @Column(name = "name", unique = true, nullable = false, length = 255)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.workspace", cascade={CascadeType.PERSIST, CascadeType.MERGE})
    Set<Location> WidgetLocations = new HashSet<Location>();

    public Workspace() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Location> getWidgetLocations() {
        return WidgetLocations;
    }

    public void setWidgetLocations(Set<Location> widgetLocations) {
        WidgetLocations = widgetLocations;
    }

    @Override
    public String toString() {
        return "Workspace [name=" + name + "]";
    }

    @Override
    public PK getId() {
        return id;
    }

    @Override
    @Transient
    public boolean isNew() {
        return null == getId();
    }
*/
}
