package ktb.hackathon.ktbgratitudediary.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Configuration
public class AopConfig {

    @Before("execution(* ktb.hackathon.ktbgratitudediary.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            String requestURL = request.getRequestURI();
            String methodType = request.getMethod();

            log.info("HTTP Request - {} {}", methodType, requestURL);
        }
    }
}
