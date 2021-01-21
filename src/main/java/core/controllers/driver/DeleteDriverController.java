package core.controllers.driver;

import core.lib.Injector;
import core.service.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteDriverController extends HttpServlet {
    private static final Injector injector =
            Injector.getInstance("core");
    private final DriverService driverService =
            (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String driverId = req.getParameter("id");
        Long id = Long.valueOf(driverId);
        driverService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/driver/all");
    }
}
