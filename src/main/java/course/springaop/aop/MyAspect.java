package course.springaop.aop;

import course.springaop.entity.Book;
import course.springaop.util.CustomResponse;
import course.springaop.util.CustomStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Order;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
@Order(1)
public class MyAspect {

    @Around("Pointcuts.allAddMethods()")
    public Object aroundAddingAdvice(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();

        Book book = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Book) {
                book = (Book) arg;
                break;
            }
        }

        if (book != null) {
            log.info("Attempt to invoke '{}' for book with title='{}'", methodName, book.getTitle());
        } else {
            log.info("Attempt to invoke '{}' without Book argument", methodName);
        }

        long start = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Error during '{}' execution: {}", methodName, e.getMessage(), e);
            return new CustomResponse<>(null, CustomStatus.EXCEPTION);
        }
        long duration = System.currentTimeMillis() - start;

        if (book != null) {
            log.info("Method '{}' for book with title='{}' successfully finished in {} ms", methodName, book.getTitle(), duration);
        } else {
            log.info("Method '{}' successfully finished in {} ms", methodName, duration);
        }

        return result;
    }

    @Around("Pointcuts.allGetMethods()")
    public Object aroundGettingAdvice(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();

        String title = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof String) {
                title = (String) arg;
                break;
            }
        }

        if ("getAll".equals(methodName)) {
            log.info("Attempt to get all books");
        } else if ("getBookByTitle".equals(methodName) && title != null) {
            log.info("Attempt to get book by title='{}'", title);
        } else {
            log.info("Invoke get-method '{}'", methodName);
        }

        long start = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("Error during '{}' execution: {}", methodName, e.getMessage(), e);
            return new CustomResponse<>(null, CustomStatus.EXCEPTION);
        }
        long duration = System.currentTimeMillis() - start;

        if ("getAll".equals(methodName)) {
            log.info("All books successfully fetched in {} ms", duration);
        } else if ("getBookByTitle".equals(methodName) && title != null) {
            log.info("Book with title='{}' successfully fetched in {} ms", title, duration);
        } else {
            log.info("Get-method '{}' successfully finished in {} ms", methodName, duration);
        }

        return result;
    }
}
