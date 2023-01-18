package com.nabin.dptm.requesthandlers;

import com.nabin.dptm.tenancy.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Narendra
 * @version 1.0
 * @since 2023-01-18
 */
@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    /**
     * Used to handle request before it is processed. With this method we can add common functionalities to the request.
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("In preHandle we are Intercepting the Request");

        String requestURI = request.getRequestURI();
        String tenantID = request.getHeader("X-TenantID");

        log.info("RequestURI:: {} || Search for X-TenantID  :: {}", requestURI, tenantID);

        if (tenantID == null) {
            response.getWriter().write("X-TenantID not present in the Request Header");
            response.setStatus(400);
            return false;
        }

        TenantContext.setCurrentTenant(tenantID);
        return true;
    }

    /**
     * After request is handled
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }
}
