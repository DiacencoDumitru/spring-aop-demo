package course.springaop.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    // срабатывает: любой модификатор доступа; находится в пакете course.springaop.service.BookService.get*(...) начинается этот метод на "get" и принимает любое количество аргументов
    // тело метода не нужно
    @Pointcut("execution(* course.springaop.service.BookService.get*(..))")
    public void allGetMethods() {}

    // реагирует на все методы начинающийся на add
    @Pointcut("execution(* course.springaop.service.BookService.add*(..))")
    public void allAddMethods() {}
}
