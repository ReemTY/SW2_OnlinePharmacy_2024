package com.FinalPharma.finalPharma.aspect;


import org.apache.commons.lang3.StringUtils;  
import org.aspectj.lang.JoinPoint;  
import org.aspectj.lang.ProceedingJoinPoint;  
import org.aspectj.lang.annotation.Around;  
import org.aspectj.lang.annotation.Aspect;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
import org.springframework.stereotype.Component;  
@Aspect  
@Component  


    public class LoggingAspect {

        Logger log = LoggerFactory.getLogger(LoggingAspect.class);  
        @Around(value = "execution(* com.FinalPharma.finalPharma.service..*(..))")  
        public Object logTime(ProceedingJoinPoint JoinPoint) throws Throwable {  
            long startTime = System.currentTimeMillis();  
            StringBuilder sb = new StringBuilder("KPI:");  
            sb.append("[").append(JoinPoint.getKind()).append("]\tfor: ").append(JoinPoint.getSignature())  
                .append("\twithArgs: ").append("(").append(StringUtils.join(JoinPoint.getArgs(), ",")).append(")");  
            sb.append("\ttook: ");  
            Object returnValue = JoinPoint.proceed();  
            log.info(sb.append(System.currentTimeMillis() - startTime).append(" ms.").toString());  
            return returnValue;  
        }  
    }  
    
