package core.controllers.manufacturer;

import core.lib.Injector;
import core.service.ManufacturerService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteManufactureController extends HttpServlet {
    private static final Injector injector =
            Injector.getInstance("core");
    private final ManufacturerService manufacturerService =
            (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String stringId = req.getParameter("id");
        Long id = Long.valueOf(stringId);
        manufacturerService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/manufacturer");
    }
}
