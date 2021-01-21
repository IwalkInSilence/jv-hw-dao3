package core.controllers.car;

import core.lib.Injector;
import core.model.Car;
import core.model.Manufacturer;
import core.service.CarService;
import core.service.ManufacturerService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateCarController extends HttpServlet {
    private static final Injector injector =
            Injector.getInstance("core");
    private final CarService carService = (CarService) injector.getInstance(CarService.class);
    private final ManufacturerService manufacturerService =
            (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/car/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String model = req.getParameter("model");
        String name = req.getParameter("name");
        String country = req.getParameter("country");
        Car car = new Car(model, manufacturerService.create(new Manufacturer(name, country)));
        carService.create(car);
        resp.sendRedirect(req.getContextPath() + "/car/all");
    }
}
