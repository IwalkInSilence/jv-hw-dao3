package core.controllers.driver;

import core.lib.Injector;
import core.model.Car;
import core.model.Driver;
import core.service.CarService;
import core.service.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddDriverToCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("core");
    private static final String DRIVER_ID = "driver_id";
    private static final String CAR_ID = "car_id";
    private final CarService carService =
            (CarService) injector.getInstance(CarService.class);
    private final DriverService driverService =
            (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/car/addDriver.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long driverId = Long.valueOf(request.getParameter(DRIVER_ID));
        Long carId = Long.valueOf(request.getParameter(CAR_ID));
        Car car = carService.get(carId);
        Driver driver = driverService.get(driverId);
        carService.addDriverToCar(driver, car);
        System.out.println(car);
        response.sendRedirect(request.getContextPath() + "/cars");
    }
}
