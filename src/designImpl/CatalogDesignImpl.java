package designImpl;

import design.IDesign;
import entity.Catalog;
import util.IOFile;

import java.util.List;
import java.util.Optional;

public class CatalogDesignImpl implements IDesign<Catalog, Integer> {

    private static List<Catalog> catalogList;
    static {
        catalogList = IOFile.readFromFile(IOFile.CATALOG_PATH);
    }

    @Override
    public List<Catalog> getAll() {
        return catalogList;
    }

    @Override
    public void save(Catalog catalog) {
        if (catalog.getId() == 0) {
            // New catalog, assign a new ID
            catalog.setId(getNewId());
            catalogList.add(catalog);
        } else {
            // Existing catalog, update the information
            for (int i = 0; i < catalogList.size(); i++) {
                if (catalogList.get(i).getId() == catalog.getId()) {
                    catalogList.set(i, catalog);
                    break;
                }
            }
        }
        IOFile.writeToFile(catalogList, IOFile.CATALOG_PATH);
    }

    @Override
    public Catalog findById(Integer id) {
        return catalogList.stream()
                .filter(catalog -> catalog.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Integer id) {
        catalogList.removeIf(catalog -> catalog.getId() == id);
        IOFile.writeToFile(catalogList, IOFile.CATALOG_PATH);
    }

    // Method to get a new ID for a catalog
    private int getNewId() {
        return catalogList.stream()
                .mapToInt(Catalog::getId)
                .max()
                .orElse(0) + 1;
    }
}
