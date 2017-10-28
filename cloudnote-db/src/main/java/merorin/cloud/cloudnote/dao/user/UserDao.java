package merorin.cloud.cloudnote.dao.user;

import merorin.cloud.cloudnote.po.data.user.UserPO;
import merorin.cloud.cloudnote.request.CommonRequest;
import merorin.cloud.cloudnote.result.CommonResult;

/**
 * Description: user对象的dao层操作接口
 * Implements are show as below:
 * @see merorin.cloud.cloudnote.dao.user.impl.UserDaoImpl
 *
 * @author guobin On date 2017/10/25.
 * @version 1.0
 * @since jdk 1.8
 */
public interface UserDao {

    /**
     * 根据id来获取一条用户数据
     * @param id 请求查询的id
     * @return 查询得到的结果
     */
    CommonResult<UserPO> getById(String id);

    /**
     * 根据条件来获取多条数据
     * @param request 请求集合
     * @return 查询得到的结果
     */
    CommonResult<UserPO> listByRequest(CommonRequest<UserPO> request);

    /**
     * 根据条件统计符合条件的数据数目
     * @param request 请求集合
     * @return 查询得到的结果
     */
    CommonResult<UserPO> countByRequest(CommonRequest<UserPO> request);

    /**
     * 保存一条用户数据
     * @param user 用户
     * @return 处理结果
     */
    CommonResult<UserPO> saveUser(UserPO user);

    /**
     * 根据条件来删除数据
     * @param request 请求的数据
     * @return 处理结果
     */
    CommonResult<UserPO> removeByRequest(CommonRequest<UserPO> request);

    /**
     * 根据id来更新一条数据
     * @param user 需要进行更新的数据
     * @return 处理结果
     */
    CommonResult<UserPO> updateById(UserPO user);
}
