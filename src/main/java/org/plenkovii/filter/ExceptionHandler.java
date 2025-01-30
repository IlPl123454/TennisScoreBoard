package org.plenkovii.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionHandler implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            httpRequest.setAttribute("errorMessage", e.getMessage());
            httpRequest.getRequestDispatcher("/errorPage.jsp").forward(servletRequest, servletResponse);
        }
    }
}
