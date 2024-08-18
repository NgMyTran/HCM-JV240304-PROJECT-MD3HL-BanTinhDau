package designImpl;

import design.IDesign;
import entity.Catalog;
import entity.Product;
import util.IOFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CatalogDesignImpl implements IDesign<Catalog, Integer> {
    private static List<Catalog> catalogList;
    private static List<Catalog> deletedCatalogs = IOFile.readFromFile(IOFile.DELETED_CATALOG_PATH);

    public static List<Catalog> getCatalogList() {
        return catalogList;
    }

    static {
        catalogList = IOFile.readFromFile(IOFile.CATALOG_PATH);
    }

    @Override
    public List<Catalog> getAll() {
        return catalogList;
    }

    @Override
    public void save(Catalog catalog) {
        if (catalog.isStatus()) {// Chỉ lưu danh mục nếu nó còn hoạt động
            if (findById(catalog.getCatalogId()) == null) {
                catalog.setCatalogId(getNewId());
                catalog.setStatus(true);
                catalogList.add(catalog);
            } else {
                for (int i = 0; i < catalogList.size(); i++) {
                    if (catalogList.get(i).getCatalogId() == catalog.getCatalogId()) {
                        catalogList.set(i, catalog);
                        break;
                    }
                }
            }
            IOFile.writeToFile(catalogList, IOFile.CATALOG_PATH);
        }
    }

    @Override
    public Catalog findById(Integer id) {
        for (Catalog c : catalogList) {
            if (c.getCatalogId() == id) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        if (findById(id) != null) {
            catalogList.remove(findById(id));
            IOFile.writeToFile(catalogList, IOFile.CATALOG_PATH);
        } else {
            System.err.println("Không có mục lục này");
        }
    }

//    public void deleteAndSave(Integer id) {
//        Catalog catalog = findById(id);
//        if (catalog != null) {
//            catalogList.remove(catalog);
////            catalog.setCatalogId(getNewId());
//            IOFile.writeToFile(catalogList, IOFile.CATALOG_PATH); // Cập nhật danh sách khách hàng hiện tại
//            // Lưu khách hàng đã xóa vào file
//            deletedCatalogs.add(catalog);
//            IOFile.writeToFileDeletedList(deletedCatalogs, IOFile.DELETED_CATALOG_PATH);
//
//        } else {
//            System.err.println("Không có khách hàng này");
//        }
//    }
public void deleteAndSave(Integer id) {
    Catalog catalog = findById(id);
    if (catalog != null) {
        // Check if there are products associated with this catalog
        List<Product> products = IOFile.readFromFile(IOFile.PRODUCT_PATH);
        long productCount = products.stream()
                .filter(p -> p.getCatalogId() == id)
                .count();

        if (productCount > 0) {
            System.err.println("Danh mục này không thể xóa vì còn sản phẩm liên kết.");
        } else {
            catalogList.remove(catalog);
            List<Catalog> deletedCatalogs = IOFile.readFromFile(IOFile.DELETED_CATALOG_PATH);
            if (deletedCatalogs == null) {
                deletedCatalogs = new ArrayList<>();
            }
            deletedCatalogs.add(catalog);
            IOFile.writeToFileDeletedList(deletedCatalogs, IOFile.DELETED_CATALOG_PATH);
            IOFile.writeToFile(catalogList, IOFile.CATALOG_PATH);
            System.out.println("Danh mục " + catalog.getCatalogId() + " đã được xóa và lưu vào file.");
        }
    } else {
        System.err.println("Không có danh mục này.");
    }
}

    public int getNewId() {
        int idMax = 0;
      for (Catalog c : catalogList) {
          if (c.getCatalogId() > idMax) {
              idMax = c.getCatalogId();
          }
      }
        return idMax + 1;
    }

    public void updateCatalogIds() {
        List<Catalog> updatedCatalogList = new ArrayList<>();
        int newId = 1; // Starting ID

        for (Catalog catalog : catalogList) {
            catalog.setCatalogId(newId++);
            updatedCatalogList.add(catalog);
        }

        catalogList = updatedCatalogList;
        IOFile.writeToFile(catalogList, IOFile.CATALOG_PATH); // Save updated list to file
    }

    public void restoreCatalogIfExists(String catalogName) {
        for (Catalog deletedCatalog : deletedCatalogs) {
            if (deletedCatalog.getCatalogName().equalsIgnoreCase(catalogName) && deletedCatalog.getDescription().equalsIgnoreCase(catalogName)) {
                deletedCatalogs.remove(deletedCatalog);
                catalogList.add(deletedCatalog);
                IOFile.writeToFile(catalogList, IOFile.CATALOG_PATH);
                IOFile.writeToFileDeletedList(deletedCatalogs, IOFile.DELETED_CATALOG_PATH);
                System.out.println("Danh mục " + catalogName + " đã được khôi phục.");
                return;
            }
        }
        System.out.println("Không có danh mục nào bị xóa với tên " + catalogName);
    }
}