package com.asdvconstruction.portal.util.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Michael C. Herrera
 */
@SuppressWarnings("EmptyMethod")
@WebFilter("/resources/*")
public class TemplateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access to this resource is denied.");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

