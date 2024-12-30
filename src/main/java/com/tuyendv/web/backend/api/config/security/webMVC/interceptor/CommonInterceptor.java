package com.tuyendv.web.backend.api.config.security.webMVC.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class CommonInterceptor implements HandlerInterceptor {

    //handle before to controller, if return false => request not be continued
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        log.info(" * Request URI : {}", request.getRequestURI());

        //get auth info
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //handle what you need here
//        example: if isStudent = true and disagree with the policy => return FORBIDDEN
//        boolean isStudent = auth.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals("ROLE_STUDENT"));
//
//        if (isStudent) {
//        formUserDetailsService: service check policy student by you write
//            Boolean agreeToPolicy = formUserDetailsService.loadAgreeToPolicyStudent(auth.getName());
//            if (! agreeToPolicy) {
//                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                response.getWriter().write("You must agree to the policy to use the system.");
//                return false;
//            }
//        }

        return true;
    }

    //handle after out controller and before send to FE
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}
