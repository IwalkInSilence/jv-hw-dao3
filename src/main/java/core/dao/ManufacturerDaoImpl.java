package core.dao;

import core.db.Storage;
import core.lib.Dao;
import core.model.Manufacturer;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Storage.addManufacturer(manufacturer);
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return getAll().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Manufacturer> getAll() {
        return Storage.manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Manufacturer updateManufacturer = getAll().stream()
                .filter(m -> m.getId().equals(manufacturer.getId()))
                .findFirst()
                .get();
        updateManufacturer.setCountry(manufacturer.getCountry());
        updateManufacturer.setName(manufacturer.getName());
        return updateManufacturer;
    }

    @Override
    public boolean delete(Long id) {
        Manufacturer manufacturer = getAll()
                .stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .get();
        return Storage.manufacturers.remove(manufacturer);
    }
}
