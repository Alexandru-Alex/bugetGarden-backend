package com.example.bugetGarden.utils;

import com.google.common.base.Stopwatch;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.StopWatch;

import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FetchUtils {

    public static RetryTemplate retryable(String action){
        var maxRetries =3;
        var retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(maxRetries);

        var fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(5);

        var retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.registerListener(retryListener(action,maxRetries));
        return retryTemplate;
    }

    private static RetryListener retryListener(String action, int maxRetries){
        return new RetryListener() {
            @Override
            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable e) {
                log.error("{}: Failed after {} of {}", action,context.getRetryCount(),maxRetries,e);
            }
        };
    }

    public static <T> T timing(Object loggingObject,String action, ThrowingCallable<T> callable){
        return (T) timing(loggingObject.getClass(),action,callable);
    }

    public static  <T> T timing(Class<?> loggingClass,String action, ThrowingCallable<T> callable){
        Logger log = LoggerFactory.getLogger(loggingClass);
        Stopwatch stopWatch = Stopwatch.createStarted();
        try{
            log.debug("STARTED : {}", action);
            T response = (T) callable.call();
            log.debug("SUCCEEDED: {} in {} ms", action, elapsedMillis(stopWatch));
            return response;
        }catch (Throwable e){
            log.debug("FAILED {} in {} ms", action, elapsedMillis(stopWatch));
            logExceptionIfDebug(log,action,e);
            Throwables.throwIfUnchecked(e);
            throw  new RuntimeException(e);
        }
    }
    private static void logExceptionIfDebug(Logger log, String action, Throwable e){
        if(log.isDebugEnabled()){
            log.error("FAILURE when {}", action, e);
        }
    }
    private static String elapsedMillis(Stopwatch stopwatch){
        long elapsedMillis= stopwatch.elapsed(TimeUnit.MILLISECONDS);
        return NumberFormat.getIntegerInstance().format(elapsedMillis);
    }

    public interface ThrowingCallable<T>{
        T call() throws Throwable;
    }
}
