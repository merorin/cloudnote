package merorin.cloud.cloudnote.log;


import merorin.cloud.cloudnote.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 通用的日志管理者
 *
 * @author guobin On date 2017/11/13.
 * @version 1.0
 * @since jdk 1.8
 */
public class CommonLogger {

    /**
     * slf日志记录者
     */
    private final Logger logger;

    private CommonLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    /**
     * 获取日志的记录者
     * @param clazz 对应的类名
     * @return 得到的日志记录者
     */
    public static CommonLogger getLogger(Class<?> clazz) {
        return new CommonLogger(clazz);
    }

    /**
     * 记录日志
     * @param message 传入的信息
     */
    public void info(String message) {
        if (this.logger.isInfoEnabled()) {
            this.logger.info(message);
        }
    }

    /**
     * 记录日志,根据传入的参数以及{@code baseMsg}自动生成最终打印的日志
     * @param baseMsg 传入的基础信息,包含占位符
     * @param params 占位符对应的参数
     */
    public void info(String baseMsg, Object... params) {
        if (this.logger.isInfoEnabled()) {
            this.logger.info(StringUtils.replaceArgs(baseMsg, params));
        }
    }

    /**
     * 记录调试日志
     * @param message 传入的日志信息
     */
    public void debug(String message) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(message);
        }
    }

    /**
     * 记录调试日志,根据传入的参数以及{@code baseMsg}自动生成最终打印的日志
     * @param baseMsg 传入的基础信息,包含占位符
     * @param params 占位符对应的参数
     */
    public void debug(String baseMsg, Object... params) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(StringUtils.replaceArgs(baseMsg, params));
        }
    }

    /**
     * 记录调试日志
     * @param message 传入的日志信息
     */
    public void error(String message) {
        if (this.logger.isErrorEnabled()) {
            this.logger.error(message);
        }
    }

    /**
     * 记录错误日志,根据传入的参数以及{@code baseMsg}自动生成最终打印的日志
     * @param baseMsg 传入的基础信息,包含占位符
     * @param params 占位符对应的参数
     */
    public void error(String baseMsg, Object... params) {
        if (this.logger.isErrorEnabled()) {
            this.logger.error(StringUtils.replaceArgs(baseMsg, params));
        }
    }
}
