package entity;

import designImpl.CatalogDesignImpl;
import util.Inputmethods;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Product implements Serializable, Comparable<Product>{
    private static final long serialVersionUID = 1L;

    private Catalog catalog;
    private int productId;
    private String productName;
    private double productPrice;
    private String description;
    private int stock;
    private int catalogId;
    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt;
    private boolean status = true;

    public Product() {
    }

    public Product(int productId, String productName, double productPrice, String description, int stock, int catalogId, LocalDate createdAt, LocalDate updatedAt, boolean status) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
        this.stock = stock;
        this.catalogId = catalogId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
        this.catalogId = catalog.getCatalogId(); // Đồng bộ hóa catalogId
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public void inputData() {
        while (true) {
            System.out.print("Nhập tên sản phẩm: ");
            String inputName = Inputmethods.getString();
            if (inputName != null && !inputName.trim().isEmpty()) {
                this.productName = inputName;
                break;
            } else {
                System.err.println("Tên sản phẩm không được để trống. Vui lòng nhập lại.");
            }
        }

        // Validate giá sản phẩm lớn hơn 0 và không để trống
        while (true) {
            System.out.print("Nhập giá sản phẩm (lớn hơn 0): ");
            Double inputPrice = Inputmethods.getDouble();
            if (inputPrice == null) {
                System.err.println("Giá sản phẩm không được để trống. Vui lòng nhập lại.");
            } else if (inputPrice > 0) {
                this.productPrice = inputPrice;
                break;
            } else {
                System.err.println("Giá sản phẩm phải lớn hơn 0. Vui lòng nhập lại.");
            }
        }


        while (true) {
            System.out.print("Nhập vào mô tả: ");
            String inputDescription = Inputmethods.getString();
            if (inputDescription != null && !inputDescription.trim().isEmpty()) {
                this.description = inputDescription;
                break;
            } else {
                System.err.println("Mô tả không được để trống. Vui lòng nhập lại.");
            }
        }

        // Validate số lượng sản phẩm lớn hơn 0
        while (true) {
            System.out.print("Nhập số lượng sản phẩm (lớn hơn 0): ");
            Integer inputStock = Inputmethods.getInteger();

            if (inputStock == null) {
                System.err.println("Số lượng sản phẩm không được để trống. Vui lòng nhập lại.");
            } else if (inputStock > 0) {
                this.stock = inputStock;
                break;
            } else {
                System.err.println("Số lượng sản phẩm phải lớn hơn 0. Vui lòng nhập lại.");
            }
        }

        // Hiển thị danh sách danh mục và chọn danh mục hợp lệ
        System.out.println("Danh sách danh mục:");
        List<Catalog> catalogs = CatalogDesignImpl.getCatalogList();
        for (Catalog catalog : catalogs) {
            System.out.printf("ID: %-3s | Name: %-15s |\n", catalog.getCatalogId(), catalog.getCatalogName());
        }
        while (true) {
            System.out.print("Vui lòng chọn ID danh mục: ");
            int id = Inputmethods.getInteger();
            // Lấy danh mục dựa trên ID từ danh sách danh mục
            Catalog selectedCatalog = CatalogDesignImpl.getCatalogList().stream()
                    .filter(catalog -> catalog.getCatalogId() == id)
                    .findFirst()
                    .orElse(null);

            if (selectedCatalog != null) {
                this.setCatalog(selectedCatalog); // Gán danh mục cho sản phẩm
                break;
            } else {
                System.err.println("Không có danh mục với ID đó, vui lòng chọn lại.");
            }
        }
    }
        @Override
        public int compareTo (Product o){
            return Double.compare(o.getProductPrice(), this.productPrice);
        }


        @Override
        public String toString () {
            return "ID: " + productId + " | Name: " + productName + " | Price: " + productPrice +
                    " | Description: " + description + " | Stock: " + stock +
                    "\nCatalog's name: " + (catalog != null ? catalog.getCatalogName() : "N/A") ;
//                    " | Status: " + (status ? "Bán" : "Không bán");
        }
    }
