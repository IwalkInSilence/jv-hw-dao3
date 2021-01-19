package core;

import core.lib.Injector;
import core.model.Car;
import core.model.Driver;
import core.model.Manufacturer;
import core.service.CarService;
import core.service.DriverService;
import core.service.ManufacturerService;

public class Application {
    private static final Injector injector =
            Injector.getInstance(Application.class.getPackageName());

    public static void main(String[] args) {
        ManufacturerService manufacturerService = (ManufacturerService)
                injector.getInstance(ManufacturerService.class);
        Manufacturer mercedes = new Manufacturer("Mercedes", "Germany");
        Manufacturer volvo = new Manufacturer("Volvo", "Sweden");
        Manufacturer audi = new Manufacturer("Audi", "Germany");
        audi.setId(1L);
        Manufacturer ferrari = new Manufacturer("Ferrari", "Italy");
        manufacturerService.create(mercedes);
        manufacturerService.create(volvo);
        manufacturerService.create(ferrari);
        System.out.println(manufacturerService.get(3L));
        System.out.println(manufacturerService.getAll());
        System.out.println(manufacturerService.update(audi));
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(2L);
        System.out.println(manufacturerService.getAll());

        Driver driverBob = new Driver("Bob", "15052102");
        Driver driverAlice = new Driver("Alice", "23152316");
        Driver driverJohn = new Driver("John", "12632455");
        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);
        driverService.create(driverBob);
        driverService.create(driverAlice);
        driverService.create(driverJohn);
        System.out.println(driverService.getAll());
        Driver driverKate = new Driver("Kate", "55470364");
        driverKate.setId(2L);
        driverService.update(driverKate);
        System.out.println(driverService.getAll());
        driverService.delete(1L);
        System.out.println(driverService.getAll());
        System.out.println(driverService.get(2L));

        Car toyotaCamry = new Car("Camry", manufacturerService.create(
                new Manufacturer("Toyota", "Japan")));
        Car mercedesGelandewagen = new Car("Gelandewagen", mercedes);
        Car audiA8 = new Car("A8", audi);
        Car volvoXC90 = new Car("XC90", volvo);
        Car ferrari488 = new Car("488", ferrari);
        CarService carService = (CarService) injector.getInstance(CarService.class);
        carService.create(toyotaCamry);
        carService.create(audiA8);
        carService.create(volvoXC90);
        carService.create(ferrari488);
        carService.create(mercedesGelandewagen);
        carService.addDriverToCar(driverAlice, ferrari488);
        carService.addDriverToCar(driverBob, mercedesGelandewagen);
        carService.addDriverToCar(driverJohn, toyotaCamry);
        carService.addDriverToCar(driverKate, audiA8);
        carService.addDriverToCar(driverJohn, ferrari488);
        carService.addDriverToCar(driverJohn, mercedesGelandewagen);
        System.out.println(carService.getAll());
        System.out.println(carService.getAllByDriver(3L));
        System.out.println(toyotaCamry);
        carService.removeDriverFromCar(driverJohn, toyotaCamry);
        System.out.println(toyotaCamry);
        System.out.println(carService.getAll());
        System.out.println(carService.get(2L));
        carService.delete(1L);
        System.out.println(carService.getAll());
        toyotaCamry.setId(2L);
        carService.update(toyotaCamry);
        System.out.println(carService.getAll());
    }
}
