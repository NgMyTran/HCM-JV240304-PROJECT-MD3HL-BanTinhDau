package entity;

import util.Inputmethods;

import java.io.Serializable;

public class Catalog implements Serializable {
    private int catalogId;
    private  String catalogName;
    private String catalogDescription;
    private boolean catalogIsActive;

    public Catalog(){}
    public Catalog(int catalogId, String catalogName, boolean catalogIsActive, String catalogDescription) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.catalogDescription = catalogDescription;
        this.catalogIsActive = true;
    }

    public String getDescription() {
        return catalogDescription;
    }

    public void setDescription(String description) {
        this.catalogDescription = description;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int id) {
        this.catalogId = id;
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

    public void inputData() {
        System.out.print("Nhập tên danh mục: ");
        this.catalogName = Inputmethods.getString();
        System.out.print("Nhập mô tả danh mục: ");
        this.catalogDescription = Inputmethods.getString();
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + catalogId +
                ", catalogName='" + catalogName + '\'' +
                ", catalogIsActive=" + catalogIsActive +
                '}';
    }
}