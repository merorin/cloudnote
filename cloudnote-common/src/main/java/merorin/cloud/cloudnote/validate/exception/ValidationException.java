package merorin.cloud.cloudnote.validate.exception;

/**
 * Description: 验证的异常
 *
 * @author guobin On date 2017/11/7.
 * @version 1.0
 * @since jdk 1.8
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -80852070073012997L;

    public ValidationException(String message) {
        super(message);
    }
}
