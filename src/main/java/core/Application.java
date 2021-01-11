package core;

import core.lib.Injector;
import core.model.Manufacturer;
import core.service.ManufacturerService;

public class Application {
    private static final Injector injector = Injector.getInstance(Application.class.getPackageName());

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService)
                injector.getInstance(ManufacturerService.class);
        Manufacturer mercedes = new Manufacturer("Mercedes", "Germany");
        Manufacturer volvo = new Manufacturer("Volvo", "Sweden");
        Manufacturer audi = new Manufacturer("Audi", "Germany");
        Manufacturer ferrari = new Manufacturer("Ferrari", "Italy");
        manufacturerService.create(mercedes);
        manufacturerService.create(volvo);
        manufacturerService.create(ferrari);
        System.out.println(manufacturerService.get(3L));
        System.out.println(manufacturerService.getAll().toString());
        audi.setId(1L);
        manufacturerService.update(audi);
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(2L);
        System.out.println(manufacturerService.getAll());
    }
}
