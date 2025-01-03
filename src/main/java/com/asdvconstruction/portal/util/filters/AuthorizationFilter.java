package com.asdvconstruction.portal.util.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * @author Michael C. Herrera
 */
@SuppressWarnings("EmptyMethod")
@WebFilter("/portal/*")
public class AuthorizationFilter implements Filter {

    /**
     * Called by the web container to indicate to a filter that it is being placed into service.
     * <p>
     * The servlet container calls the init method exactly once after instantiating the filter. The init method must
     * complete successfully before the filter is asked to do any filtering work. The container will ensure that actions
     * performed in the {@code init} method will be visible to any threads that subsequently call the {@code doFilter}
     * method according to the rules in JSR-133 (i.e. there is a 'happens before' relationship between init and
     * doFilter).
     * <p>
     * The web container cannot place the filter into service if the init method either
     * <p>
     * <ol>
     *     <li>Throws a ServletException</li>
     *     <li>Does not return within a time period defined by the web container</li>
     * </ol>
     *
     * @param filterConfig object containing the filter's configuration and initialization parameters
     * @throws ServletException if an exception has occurred that interferes with the filter's normal operation
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        Filter.super.init(filterConfig);
    }

    /**
     * The {@code doFilter} method of the Filter is called by the container each time a request/response pair is passed
     * through the chain due to a client request for a resource at the end of the chain. The FilterChain passed in to
     * this method allows the Filter to pass on the request and response to the next entity in the chain.
     * <p>
     * A typical implementation of this method would follow the following pattern:
     * <p>
     * <ol>
     *     <li>Examine the request</li>
     *     <li>Optionally wrap the request object with a custom implementation to filter content or headers for input
     *     filtering</li>
     *     <li>Optionally wrap the response object with a custom implementation to filter content or headers for
     *     output filtering</li>
     *     <li><ul>
     *         <li><strong>Either</strong> invoke the next entity in the chain using the FilterChain object ({@code
     *         chain.doFilter()}),</li>
     *         <li><strong>or</strong> not pass on the request/response pair to the next entity in the filter chain
     *         to block the request processing</li>
     *     </ul></li>
     *     <li>Directly set headers on the response after invocation of the next entity in the filter chain.</li>
     * </ol>
     *
     * @param servletRequest  the {@code ServletRequest} object contains the client's request
     * @param servletResponse the {@code ServletResponse} object contains the filter's response
     * @param filterChain     the {@code FilterChain} for invoking the next filter or the resource
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with the filter's normal operation
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/index.xhtml");
        } else
            filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Called by the web container to indicate to a filter that it is being taken out of service.
     * <p>
     * This method is only called once all threads within the filter's doFilter method have exited or after a timeout
     * period has passed. After the web container calls this method, it will not call the doFilter method again on this
     * instance of the filter.
     * <p>
     * This method gives the filter an opportunity to clean up any resources that are being held (for example, memory,
     * file handles, threads) and make sure that any persistent state is synchronized with the filter's current state in
     * memory.
     */
    @Override
    public void destroy() {

        Filter.super.destroy();
    }
}
