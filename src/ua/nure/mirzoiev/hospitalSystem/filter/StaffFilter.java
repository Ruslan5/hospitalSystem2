package ua.nure.mirzoiev.hospitalSystem.filter;

import org.apache.log4j.Logger;

import ua.nure.mirzoiev.hospitalSystem.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "StaffFilter", urlPatterns = "/staff/*")
public class StaffFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(StaffFilter.class);
    private static final String DOCTOR = Role.DOCTOR.getName();
    private static final String NURSE = Role.NURSE.getName();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache"); 
        response.setDateHeader("Expires", 0);
        HttpSession session = req.getSession();

        String clientRole = String.valueOf(session.getAttribute("role"));

        if (clientRole.equals(DOCTOR) || clientRole.equals(NURSE)) {
            LOG.info("Person role: " + clientRole + " logged in");
            filterChain.doFilter(req, servletResponse);
        } else {
            LOG.warn("Access denied. Trying to access staff page");
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            resp.sendRedirect(servletRequest.getServletContext().getContextPath() + "/logout");
        }
    }

    @Override
    public void destroy() {

    }
}
