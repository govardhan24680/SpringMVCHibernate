package com.journaldev.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ServiceAop {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceAop.class);
	
	
	//"execution(* com.journaldev.spring.service.*.*(..))"
	//com.journaldev.spring.service.className.methodName
	//@Before//com.journaldev.spring.service.*.methodName2
	//@Before//com.journaldev.spring.service.className2.*
	//@Before//com.journaldev.spring.*.className2.methodName2
	
	//ServiceAop - Aspect
	//@Before-logBeforeAllMethods- Advice
	//com.journaldev.spring.service.*.*(..)  point cut
	//above expression selected methods(addPerson) called join points
	
	
	@Before("execution(* com.journaldev.spring.service.*.*(..))")
	public void logBeforeAllMethods(JoinPoint joinPoint) {
	    String methodName = joinPoint.getSignature().getName();
	    //System.out.println(" Calling====================================== method: " + methodName);
	    logger.info("{} method started", methodName);
	}
	
	 // 2. @After — runs after the method completes (success or failure)
    @After("execution(* com.journaldev.spring.service.*.addPerson(..))")
    public void logAfter(JoinPoint jp) {
        System.out.println("Finished method:====================================== " + jp.getSignature().getName());
    }

    // 3. @AfterReturning — only if method returns normally
    @AfterReturning(
        pointcut = "execution(* com.journaldev.spring.service.*.listPersons(..))",
        returning = "result")
    public void logAfterReturning(JoinPoint jp, Object result) {
        System.out.println("[@AfterReturning] Method:====================================== " + jp.getSignature().getName() + ", returned: " + result);
    }

    // 4. @AfterThrowing — runs if method throws exception
    @AfterThrowing(
        pointcut = "execution(* com.journaldev.spring.service.PersonService.listPersonsForTestAop(..))",
        throwing = "ex")
    public void logAfterThrowing(JoinPoint jp, Throwable ex) {
        System.out.println("[@AfterThrowing] Method:====================================== " + jp.getSignature().getName() + " threw: " + ex.getMessage());
    }

    // 5. @Around — surrounds the method (before and after)
    @Around("execution(* com.journaldev.spring.service.*.*(..))")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        System.out.println("[@Around] Before method: " + methodName);
        Object result = pjp.proceed();  // actual method call
        System.out.println("[@Around] After method:====================================== " + methodName);
        return result;
    }

}
