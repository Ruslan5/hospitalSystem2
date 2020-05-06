package ua.nure.mirzoiev.hospitalSystem.filter;

import org.apache.log4j.Logger;

import ua.nure.mirzoiev.hospitalSystem.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = "/admin/*")
public class AdminFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(AdminFilter.class);
    private static final String ADMIN = Role.ADMIN.getName();

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
        req.setCharacterEncoding("UTF-8");
        String clientRole = String.valueOf(session.getAttribute("role"));

        if (clientRole.equals(ADMIN)) {
            LOG.info("Person role: " + clientRole + " logged in");
            filterChain.doFilter(req, servletResponse);
        } else {
            LOG.warn("Access denied. Trying to access admin page");
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            resp.sendRedirect(servletRequest.getServletContext().getContextPath() + "/logout");
        }
    }

    @Override
    public void destroy() {

    }
}
