package cz.muni.fi.pa165.currency;

import javax.inject.Named;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Named
@Aspect
public class TimeAspect {
    @Around("execution(public * *(..))")
    public Object getMethodTime(ProceedingJoinPoint joinPoint) throws Throwable {
        System.err.println("Calling method: " + joinPoint.getSignature());
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long finish = System.currentTimeMillis();
        long took = start - finish;
        System.err.println("The method took " + took/1000000000 + "s");
        return result;
    }
}
