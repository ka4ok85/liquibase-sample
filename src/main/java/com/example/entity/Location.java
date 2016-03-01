package com.example.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "widget_location")
@AssociationOverrides({ @AssociationOverride(name = "pk.workspace", joinColumns = @JoinColumn(name = "workspace_id") ),
@AssociationOverride(name = "pk.widget", joinColumns = @JoinColumn(name = "widget_id") ) })
public class Location {

    private LocationID pk = new LocationID();

    @JsonView(com.example.entity.Location.class)
    @Column(name = "row_number", nullable = false)
    private int rowNumber;

    @JsonView(com.example.entity.Location.class)
    @Column(name = "column_number", nullable = false)
    private int columnNumber;

    @EmbeddedId
    public LocationID getPk() {
        return pk;
    }

    public void setPk(LocationID pk) {
        this.pk = pk;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    @Transient
    public Workspace getWorkspace() {
        return getPk().getWorkspace();
    }

    public void setWorkspace(Workspace workspace) {
        getPk().setWorkspace(workspace);
    }

    @Transient
    public Widget getWidget() {
        return getPk().getWidget();
    }

    public void setWidget(Widget widget) {
        getPk().setWidget(widget);
    }

    @Override
    public String toString() {
        return "Location [rowNumber=" + rowNumber + ", columnNumber=" + columnNumber + "]";
    }


}
