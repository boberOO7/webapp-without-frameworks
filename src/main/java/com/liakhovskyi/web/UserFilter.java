package com.liakhovskyi.web;

import com.liakhovskyi.Factory;
import com.liakhovskyi.model.Role;
import com.liakhovskyi.model.User;
import com.liakhovskyi.service.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class UserFilter implements Filter {

    private Set<String> openUri = new HashSet<>();

    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        openUri.add("/servlet/login");
        openUri.add("/servlet/register");
        userService = Factory.getUserServiceImpl(Factory.getUserDaoImpl(Factory.getConnection()));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Cookie[] cookies = req.getCookies();

        if (openUri.contains(req.getRequestURI())) {
            processRequest(request, response, chain);
        } else  {
            Optional<User> user = Stream.of(cookies)
                    .filter(c -> c.getName().equals("Mate_Application"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .flatMap(userService::findByToken);

            if (req.getRequestURI().startsWith("/servlet/admin")) {
                boolean isAuthorized =  user.map(u -> u.getRoles().stream()
                        .filter(r -> r.getRoleName().equals(Role.RoleName.ADMIN)))
                        .isPresent();

                if (isAuthorized) {
                    processRequest(request, response, chain);
                } else {
                    dispatch(request, response, "notAllowed");
                }

            } else {
                if (user.isPresent()) {
                    processRequest(request, response, chain);
                } else {
                    dispatch(request, response, "login");
                }
            }
        }
    }

    @Override
    public void destroy() {

    }

    private void dispatch(ServletRequest request, ServletResponse response, String viewName) throws ServletException, IOException {
        request.getRequestDispatcher(String.format("/WEB-INF/views/%s.jsp", viewName)).forward(request, response);
    }

    private void processRequest(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
}
