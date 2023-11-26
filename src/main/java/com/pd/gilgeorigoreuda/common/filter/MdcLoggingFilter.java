package com.pd.gilgeorigoreuda.common.filter;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MdcLoggingFilter implements Filter {

    private static final String REQUEST_ID = "REQUEST_ID";

    @Override
    public void doFilter(
            final ServletRequest request,
            final ServletResponse response,
            final FilterChain chain
    ) throws IOException, ServletException {
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
        chain.doFilter(request, response);
        MDC.clear();
    }

}
