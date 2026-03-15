package course.springaop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(2)
public class LoggingAspect {

    /**
     * Logs every call to public methods in the service layer
     * before the actual method execution.
     */
    @Before("within(course.springaop.service..*)")
    public void logServiceInvocation(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        log.info("[BEFORE] Invoking service method {} with args={}", methodName, args);
    }

    /**
     * Logs successful return values from service layer methods.
     */
    @AfterReturning(pointcut = "within(course.springaop.service..*)", returning = "result")
    public void logServiceReturn(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("[AFTER RETURNING] Method {} returned {}", methodName, result);
    }

    /**
     * Logs thrown exceptions from the controller layer.
     * This clearly shows AOP handling errors outside of business logic.
     */
    @AfterThrowing(pointcut = "within(course.springaop.controller..*)", throwing = "ex")
    public void logControllerException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("[AFTER THROWING] Exception in controller method {}: {}", methodName, ex.getMessage(), ex);
    }
}

