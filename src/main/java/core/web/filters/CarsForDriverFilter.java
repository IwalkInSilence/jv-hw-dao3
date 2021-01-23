package core.web.filters;

import core.lib.Injector;
import core.model.Car;
import core.service.CarService;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CarsForDriverFilter implements Filter {
    private static final Injector injector =
            Injector.getInstance("core");
    private static final String DRIVER_ID = "driver_id";
    private final CarService carService =
            (CarService) injector.getInstance(CarService.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Long driverId = (Long) req.getSession().getAttribute(DRIVER_ID);
        List<Car> allByDriver = carService.getAllByDriver(driverId);
        req.setAttribute("cars", allByDriver);
        req.getRequestDispatcher("/WEB-INF/views/car/all.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {

    }
}
