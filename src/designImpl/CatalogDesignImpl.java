package designImpl;

import design.IDesign;
import entity.Catalog;
import util.IOFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CatalogDesignImpl implements IDesign<Catalog, Integer> {

    private static List<Catalog> catalogList;

    static {
        catalogList = IOFile.readFromFile(IOFile.CATALOG_PATH);
        if (catalogList == null|| catalogList.isEmpty()) {
            catalogList = new ArrayList<>(); // Khởi tạo danh sách nếu file không tồn tại hoặc rỗng
        }
    }
//public CatalogDesignImpl() {
//    catalogList = IOFile.readFromFile(IOFile.CATALOG_PATH);
//    System.out.println("du lieu trong catalogList  ");
//    System.out.println();
//};



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
    public Catalog findById(Integer integer) {
        for (Catalog c : catalogList) {
            if (c.getCatalogId() == integer) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void delete(Integer integer) {
        if (findById(integer) != null) {
            catalogList.remove(findById(integer));
            IOFile.writeToFile(catalogList, IOFile.CATALOG_PATH);
        } else {
            System.err.println("Không có mục lục này");
        }
    }

    public int getNewId() {
        int idMax = 0;
        for (int i=0; i< catalogList.size() ;i++) {
            Catalog c = catalogList.get(i);
            if (c.getCatalogId() > idMax) {
                idMax = c.getCatalogId();
            }
        }
        return idMax + 1;
    }


}