package by.f1oating.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Stream;

@WebFilter("*")
public class SessionFilter implements Filter {

    private final String[] urls = new String[]{"/login", "/registration"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if(isLoggedIn(req) || isOnTheUrlsPage(req)){
            chain.doFilter(request, response);
        }else{
            resp.sendRedirect("/login");
        }
    }

    private boolean isLoggedIn(HttpServletRequest req){
        return req.getSession().getAttribute("user") != null;
    }

    private boolean isOnTheUrlsPage(HttpServletRequest req){
        String uri = req.getRequestURI();
        return Stream.of(urls).anyMatch(uri::contains);
    }
}
