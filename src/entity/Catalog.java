package entity;

import util.Inputmethods;

import java.io.Serializable;

public class Catalog implements Serializable {
    private int id;
    private String catalogName, description;
    private boolean catalogIsActive;

    public Catalog(int id, String catalogName, boolean catalogStatus) {
        this.id = id;
        this.catalogName = catalogName;
        this.catalogIsActive = true;
    }

    public Catalog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public boolean isStatus() {
        return catalogIsActive;
    }

    public void setStatus(boolean catalogIsActive) {
        this.catalogIsActive = catalogIsActive;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", catalogName='" + catalogName + '\'' +
                ", catalogIsActive=" + catalogIsActive +
                '}';
    }
}
