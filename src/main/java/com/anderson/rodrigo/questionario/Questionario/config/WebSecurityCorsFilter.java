package com.anderson.rodrigo.questionario.Questionario.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class WebSecurityCorsFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain)
        throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;

        final HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers",
            "Cache-Control, Pragma, Origin, Authorization, Content-Type, X-Requested-With, Accept");
        res.setHeader("Access-Control-Expose-Headers", "Authorization");

        if (req.getMethod().equalsIgnoreCase("OPTIONS")) {
            return;
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}

