package merorin.cloud.cloudnote.fcq.util;

import merorin.cloud.cloudnote.fcq.core.AbstractFcqQueueProcessor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Description: fcq获取spring bean的工具类
 *
 * @author guobin On date 2017/11/1.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        FcqBeanUtils.applicationContext = applicationContext;
    }

    /**
     * 获取一个fcq队列管理者的bean实例
     * @param beanName bean的名字
     * @return 得到的bean
     */
    static AbstractFcqQueueProcessor getBean(String beanName) throws Exception {
        return (AbstractFcqQueueProcessor) applicationContext.getBean(beanName);
    }
}
