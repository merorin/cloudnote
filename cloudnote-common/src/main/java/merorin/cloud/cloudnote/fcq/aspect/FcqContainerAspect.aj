package merorin.cloud.cloudnote.fcq.aspect;

import merorin.cloud.cloudnote.fcq.io.result.FcqProcessResult;
import merorin.cloud.cloudnote.utils.StringUtils;
import merorin.cloud.cloudnote.validate.core.Validator;
import merorin.cloud.cloudnote.validate.io.ValidateResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: fcq中container中的aspect类
 *              作为参数校验的拦截类
 *
 * @author guobin On date 2017/11/2.
 * @since jdk 1.8
 * @version 1.0
 */
@Aspect
public aspect FcqContainerAspect {

    private static final Logger LOG = LoggerFactory.getLogger(FcqContainerAspect.class);

    /**
     * 定义container的切点
     * @see merorin.cloud.cloudnote.fcq.core.impl.FcqDataExecutorContainer
     * @see merorin.cloud.cloudnote.fcq.core.impl.FcqQueueProcessorContainer
     */
    @Pointcut(value = "execution(public * merorin.cloud.cloudnote.fcq.core.impl..*Container..*(..))")
    private void containerPointcut() {}

    /**
     * 在container中的public方法执行以前进行filter
     * @param point 执行点
     * @return 处理结果
     */
    @Around("containerPointcut()")
    public FcqProcessResult filter(ProceedingJoinPoint point) {
        FcqProcessResult result;
        Object[] params = point.getArgs();

        ValidateResponse validateResponse = Validator.validate(params);
        if (validateResponse.isSuccess()) {
            try {
                result = (FcqProcessResult) point.proceed();
            } catch (Throwable throwable) {
                String errMsg = StringUtils.replaceArgs("Error occurred in FcqContainerAspect! Exception is {0}.", throwable.getMessage());
                LOG.error(errMsg);
                result = FcqProcessResult.error(errMsg);
            }
        } else {
            result = FcqProcessResult.error(validateResponse.errorsToString());
        }

        return result;
    }
}
