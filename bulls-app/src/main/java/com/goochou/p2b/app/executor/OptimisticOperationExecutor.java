package com.goochou.p2b.app.executor;

import com.goochou.p2b.service.exceptions.LockFailureException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class OptimisticOperationExecutor implements Ordered {
    private final int order = 100;
    private final int maxRetryTimes = 10;

    @Override
    public int getOrder() {
        return this.order;
    }

    // 针对并发时的乐观锁失败重试3次
    @Around("execution(* com.goochou.p2b.service.*.*(..))")
    public Object doOptimisticOperation(ProceedingJoinPoint pjp) throws Throwable {
        int retryTimes = 0;
        LockFailureException lockFailureException = null;
        do {
            retryTimes++;
            try {
                return pjp.proceed();
            } catch (LockFailureException ex) {
                lockFailureException = ex;
            }
        } while (retryTimes < maxRetryTimes);

        throw lockFailureException;
    }

}
