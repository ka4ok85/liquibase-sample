package com.example.entity;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

@Embeddable
public class LocationID implements Persistable<Long> {
    private static final long serialVersionUID = 9036301906172917822L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Workspace workspace;
    private Widget widget;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @ManyToOne
    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    @ManyToOne
    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    @Override
    public String toString() {
        return "LocationID [id=" + id + ", workspace=" + workspace + ", widget=" + widget + "]";
    }

    @Override
    @Transient
    public boolean isNew() {
        return null == getId();
    }


/*
public class LocationID implements java.io.Serializable {
//public class Widget implements Persistable<Long> {
    private static final long serialVersionUID = 9036301906172917822L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Workspace workspace;
    private Widget widget;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    @ManyToOne
    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    @Override
    public String toString() {
        return "LocationID [id=" + id + ", workspace=" + workspace + ", widget=" + widget + "]";
    }
*/
}