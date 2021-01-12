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
        return getAll().set(manufacturer.getId().intValue() - 1, manufacturer);
    }

    @Override
    public boolean delete(Long id) {
        return getAll().removeIf(m -> m.getId().equals(id));
    }
}
