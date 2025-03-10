package course.springaop.aop;

import course.springaop.entity.Book;
import course.springaop.util.CustomResponse;
import course.springaop.util.CustomStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MyAspect {

    // в качестве аргумента: ProceedingJoinPoint, если коротко то этот объект позволяет получить доступ к сигнатуре вызываемого метода и аргументом который будет с ним передано
    @Around("Pointcuts.allAddMethods()")
    public Object aroundAddingAdvice(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature(); // получим MethodSignature
        Book book = null;

        if (methodSignature.getName().equals("addBook")) { // если имя метода равна addBook
            Object[] arguments = joinPoint.getArgs(); // то собери все аргументы которые передавались с этим методом
            // далее мы эти аргументы переберём
            for (Object arg : arguments) {
                if (arg instanceof Book) { // если аргумент будет являтся аргументом класса Book, то Залогируй действия
                    book = (Book) arg; // получим сам аргумент книгу
                    log.info("Попытка добавить книгу с названием {}", book.getTitle()); // залогируем в консоль
                }
            }
        }

        Object result = null; // создадим объект result который будет принимать возвращенное значение из метода addBook()
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            result = new CustomResponse<>(null, CustomStatus.EXCEPTION);
        }

        log.info("Книга с названием {} добавлена", book.getTitle());
        return result;
    }

    @Around("Pointcuts.allGetMethods()")
    public Object aroundGettingAdvice(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String title = null;

        if (methodSignature.getName().equals("getAll")) {
            log.info("Попытка получить все книги");
        } else if (methodSignature.getName().equals("getBookByTitle")) {
            Object[] arguments = joinPoint.getArgs();
            for (Object arg : arguments) {
                if (arg instanceof String) {
                    title = (String) arg;
                    log.info("Пытаемся получить книгу с названием {}", title);
                }
            }
        }

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            result = new CustomResponse<>(null, CustomStatus.EXCEPTION);
        }

        if (methodSignature.getName().equals("getAll")) {
            log.info("Все книги получены");
        } else if (methodSignature.getName().equals("getBookByTitle")) {
            log.info("Книга с названием {} получена", title);
        }

        return result;
    }
}
