package merorin.cloud.cloudnote.aspect;

import com.alibaba.fastjson.JSON;
import merorin.cloud.cloudnote.common.ResultConstant;
import merorin.cloud.cloudnote.po.data.user.UserPO;
import merorin.cloud.cloudnote.po.request.DomainRequest;
import merorin.cloud.cloudnote.po.result.DomainResult;
import merorin.cloud.cloudnote.validate.core.FuncValidator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: Dao层的通用拦截面
 *
 * @author guobin On date 2017/11/9.
 * @version 1.0
 * @since jdk 1.8
 */
public class UserDaoAspect {

//    private static final Logger LOG = LoggerFactory.getLogger(UserDaoAspect.class);
//
//    /**
//     * 对传入的数据进行校验
//     * 校验传入对象为{@code UserPO}的方法
//     * @param point 连接点
//     * @return 返回的结果
//     */
//    public DomainResult<UserPO> validatePO(ProceedingJoinPoint point) {
//        final DomainResult<UserPO> result;
//        Object[] params = point.getArgs();
//        if (params.length > 0) {
//            result = FuncValidator.of((UserPO) params[0])
//                    .notNull(UserPO::getAccount, "account为空.")
//                    .notNull(UserPO::getName, "name为空.")
//                    .notNull(UserPO::getPassword, "password为空")
//                    .success(() -> this.proceedIfSuccess(point))
//                    .error(this::processIfError)
//                    .withValidation();
//        } else {
//            LOG.error("Invalid input!No params for input!");
//            result = new DomainResult<>(ResultConstant.Code.ERROR, "参数错误.");
//        }
//
//        return result;
//    }
//
//    /**
//     * 对传入的数据进行校验
//     * 校验传入对象为{@code DomainRequest<UserPO>}的方法
//     * @param point 连接点
//     * @return 返回结果
//     */
//    @SuppressWarnings("unchecked")
//    public DomainResult<UserPO> validateRequest(ProceedingJoinPoint point) {
//        final DomainResult<UserPO> result;
//        Object[] params = point.getArgs();
//        if (params.length > 0) {
//            result = FuncValidator.of((DomainRequest<UserPO>) params[0])
//                    .success(() -> this.proceedIfSuccess(point))
//                    .error(this::processIfError)
//                    .withValidation();
//        } else {
//            LOG.error("Invalid input!No params for input!");
//            result = new DomainResult<>(ResultConstant.Code.ERROR, "参数错误.");
//        }
//
//        return result;
//    }
//
//    /**
//     * 验证成功的处理函数
//     * @param point 连接点
//     * @return 处理结果
//     */
//    @SuppressWarnings("unchecked")
//    private DomainResult<UserPO> proceedIfSuccess(ProceedingJoinPoint point) {
//        DomainResult<UserPO> proceedRes;
//        try {
//            proceedRes = (DomainResult<UserPO>) point.proceed();
//        } catch (Throwable throwable) {
//            LOG.error("Error occurred in UserDaoAspect! Exception is {}.", throwable.getMessage());
//            proceedRes = new DomainResult<>(ResultConstant.Code.ERROR, ResultConstant.Message.ERROR);
//        }
//        return proceedRes;
//    }
//
//    /**
//     * 验证失败的处理函数
//     * @param validator 验证器
//     * @return 处理结果
//     */
//    private DomainResult<UserPO> processIfError(FuncValidator validator) {
//        return new DomainResult<>(ResultConstant.Code.ERROR, JSON.toJSONString(validator.getErrors()));
//    }
}
