package com.ccbfm.journal.bearer;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.concurrent.TimeUnit;

@Aspect
public class JournalAspect {
    private static final String TAG = "JournalAspect";

    /**
     * 方法
     */
    @Pointcut("execution(@com.ccbfm.journal.annotation.Journal * *(..))")
    public void method() {
    }

    /**
     * 构造方法
     */
    @Pointcut("execution(@com.ccbfm.journal.annotation.Journal *.new(..))")
    public void constructor() {
    }

    @Around("method() || constructor()")
    public Object journalExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        startMethod(joinPoint);
        long startTime = System.nanoTime();
        Object object = joinPoint.proceed();
        long endTime = System.nanoTime();
        long useTime = TimeUnit.NANOSECONDS.toMillis((endTime - startTime));

        endMethod(joinPoint, object, useTime);
        return object;
    }

    private static void startMethod(ProceedingJoinPoint joinPoint) {

    }

    private static void endMethod(ProceedingJoinPoint joinPoint, Object object, long useTime) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        Class clazz = codeSignature.getDeclaringType();
        String methodName = codeSignature.getName();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();

        Thread thread = Thread.currentThread();
        StackTraceElement[] stackTraceElements = thread.getStackTrace();
        StringBuilder methodBuilder = new StringBuilder();
        if (stackTraceElements.length > 5) {
            methodBuilder.append(stackTraceElements[5]);
            if (parameterNames.length > 0) {
                methodBuilder.append(" (");
                BearerTools.appendParameters(methodBuilder, parameterNames, parameterValues);
                methodBuilder.append(")");
            }
            if (stackTraceElements.length > 6) {
                BearerTools.handleMessage(methodBuilder, stackTraceElements[6].toString());
            }
        } else {
            methodBuilder.append(clazz.getName()).append(".").append(methodName).append("(");
            if (parameterNames.length > 0) {
                BearerTools.appendParameters(methodBuilder, parameterNames, parameterValues);
            }
            methodBuilder.append(")");
        }

        StringBuilder messageBuilder = new StringBuilder();
        boolean hasReturnType = codeSignature instanceof MethodSignature
                && ((MethodSignature) codeSignature).getReturnType() != void.class;
        messageBuilder.append(thread.getName());
        messageBuilder.append(" [").append(useTime).append("ms] ");
        if (hasReturnType) {
            messageBuilder.append(object);
        } else {
            messageBuilder.append("void");
        }

        StringBuilder allBuilder = new StringBuilder();
        BearerTools.handleTop(allBuilder);
        BearerTools.handleMessage(allBuilder, methodBuilder.toString());
        BearerTools.handleMessage(allBuilder, messageBuilder.toString());
        BearerTools.handleBottom(allBuilder);
        Log.v(TAG, allBuilder.toString());
    }

}
