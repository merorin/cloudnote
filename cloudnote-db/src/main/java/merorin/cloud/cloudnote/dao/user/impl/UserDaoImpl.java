package merorin.cloud.cloudnote.dao.user.impl;

import merorin.cloud.cloudnote.common.ResultConstant;
import merorin.cloud.cloudnote.dao.user.UserDao;
import merorin.cloud.cloudnote.po.data.user.UserPO;
import merorin.cloud.cloudnote.po.request.user.UserDomainRequest;
import merorin.cloud.cloudnote.po.result.user.UserDomainResult;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Description: user类的dao层操作实现类
 *
 * @author guobin On date 2017/10/25.
 * @version 1.0
 * @since jdk 1.8
 */
public class UserDaoImpl implements UserDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public UserDomainResult getById(String id) {

        return Optional.ofNullable(id).map((idVal) -> {
            final UserDomainResult result = new UserDomainResult();
            try {
                final UserPO user = this.mongoTemplate.findById(idVal, UserPO.class);

                List<UserPO> list = Optional.ofNullable(user)
                        .map(Collections::singletonList)
                        .orElse(Collections.emptyList());
                result.setValues(list);
                result.setCount(list.size());
                result.setTotalCount(list.size());
                result.setCode(ResultConstant.Code.SUCCESS);
                String message = list.isEmpty() ? ResultConstant.Message.NOT_FOUND : ResultConstant.Message.SUCCESS;
                result.setMessage(message);
            } catch (Exception ex) {
                result.setMessage(ResultConstant.Message.ERROR);
                result.setExceptionMsg(ex.getMessage());
            }

            return result;
        }).orElse(new UserDomainResult(ResultConstant.Code.ERROR, ResultConstant.Message.MISSING_PARAM));
    }

    @Override
    public UserDomainResult listByRequest(UserDomainRequest request) {

        return Optional.ofNullable(request).map((req) -> {
            final UserDomainResult result = new UserDomainResult();

            //设置查询条件
            final Query query = this.buildCommonQuery(req);

            try {
                int totalCount = 0;
                //判断是否要进行分页
                if (req.isNeedPaging()) {
                    totalCount = (int) this.mongoTemplate.count(query, UserPO.class);
                    query.skip(req.getPage() * req.getPageSize());
                    query.limit(req.getPageSize());
                }

                List<UserPO> list = this.mongoTemplate.find(query, UserPO.class);

                result.setCode(ResultConstant.Code.SUCCESS);
                result.setValues(list);
                result.setCount(list.size());
                totalCount = Math.max(totalCount, list.size());
                result.setTotalCount(totalCount);
                String message = list.isEmpty() ? ResultConstant.Message.NOT_FOUND : ResultConstant.Message.SUCCESS;
                result.setMessage(message);
            } catch (Exception ex) {
                result.setMessage(ResultConstant.Message.ERROR);
                result.setExceptionMsg(ex.getMessage());
            }

            return result;
        }).orElse(new UserDomainResult(ResultConstant.Code.ERROR, ResultConstant.Message.MISSING_PARAM));
    }

    @Override
    public UserDomainResult countByRequest(UserDomainRequest request) {
        return Optional.ofNullable(request).map((req) -> {
            final UserDomainResult result = new UserDomainResult();

            //设置查询条件
            final Query query = this.buildCommonQuery(req);

            try {
                int count = (int) this.mongoTemplate.count(query, UserPO.class);

                result.setCode(ResultConstant.Code.SUCCESS);
                result.setCount(count);
                result.setTotalCount(count);
                result.setMessage(ResultConstant.Message.SUCCESS);
            } catch (Exception ex) {
                result.setMessage(ResultConstant.Message.ERROR);
                result.setExceptionMsg(ex.getMessage());
            }

            return result;
        }).orElse(new UserDomainResult(ResultConstant.Code.ERROR, ResultConstant.Message.MISSING_PARAM));
    }

    @Override
    public UserDomainResult saveUser(UserPO user) {

        return Optional.ofNullable(user)
                .filter(param -> param.getName() != null && param.getMobilePhone() != null && param.getPassword() != null)
                .map(value -> {
                    final UserDomainResult result = new UserDomainResult();

                    final LocalDateTime now = LocalDateTime.now();
                    value.setCreateTime(now);
                    value.setLastUpdateTime(now);

                    try {
                        this.mongoTemplate.insert(value);
                        result.addValue(value);
                        result.setCode(ResultConstant.Code.SUCCESS);
                        result.setMessage(ResultConstant.Message.SUCCESS);
                        result.setCount(1);
                        result.setTotalCount(1);
                    } catch (Exception ex) {
                        result.setExceptionMsg(ex.getMessage());
                    }

                    return result;
                })
                .orElse(new UserDomainResult(ResultConstant.Code.ERROR, ResultConstant.Message.MISSING_PARAM));
    }

    @Override
    public UserDomainResult removeByRequest(UserDomainRequest request) {

        return Optional.ofNullable(request)
                .map(req -> {
                    final UserDomainResult result = new UserDomainResult();

                    final Query query = this.buildCommonQuery(req);

                    try {
                        List<UserPO> list = this.mongoTemplate.findAllAndRemove(query, UserPO.class);
                        result.setValues(list);
                        result.setTotalCount(list.size());
                        result.setCode(ResultConstant.Code.SUCCESS);
                        result.setCount(list.size());
                        if (list.isEmpty()) {
                            result.setMessage(ResultConstant.Message.NOT_FOUND);
                        } else {
                            result.setMessage(ResultConstant.Message.SUCCESS);
                        }
                    } catch (Exception ex) {
                        result.setExceptionMsg(ex.getMessage());
                    }

                    return result;
                })
                .orElse(new UserDomainResult(ResultConstant.Code.ERROR, ResultConstant.Message.MISSING_PARAM));
    }

    @Override
    public UserDomainResult updateById(UserPO user) {

        return Optional.ofNullable(user)
                .filter(param -> param.getId() != null)
                .map(value -> {
                    final UserDomainResult result = new UserDomainResult();

                    final Update update = new Update();
                    update.currentDate("gmt_modified");

                    Optional.ofNullable(value.getName())
                            .ifPresent((item) -> update.set("name", item));
                    Optional.ofNullable(value.getAccount())
                            .ifPresent((item) -> update.set("account", item));
                    Optional.ofNullable(value.getMobilePhone())
                            .ifPresent((item) -> update.set("mobile_phone", item));
                    Optional.ofNullable(value.getPassword())
                            .ifPresent((item) -> update.set("password", item));
                    Optional.ofNullable(value.getGender())
                            .ifPresent((item) -> update.set("gender", item));
                    Optional.ofNullable(value.getBirthday())
                            .ifPresent((item) -> update.set("birthday", item));
                    Optional.ofNullable(value.getEmailAddress())
                            .ifPresent((item) -> update.set("email_address", item));
                    Optional.ofNullable(value.getLastLoginIp())
                            .ifPresent((item) -> update.set("last_login_ip", item));
                    Optional.ofNullable(value.getVerifyCode())
                            .ifPresent((item) -> update.set("verify_code", item));
                    Optional.ofNullable(value.getAuthLevel())
                            .ifPresent((item) -> update.set("auth_level", item));

                    final Query query = new Query(Criteria.where("_id").is(value.getId()));
                    try {
                        //返回更新后的文档
                        FindAndModifyOptions options = new FindAndModifyOptions();
                        options.returnNew(true);
                        UserPO newUser = this.mongoTemplate.findAndModify(query, update, options, UserPO.class);
                        result.addValue(newUser);
                        result.setCode(ResultConstant.Code.SUCCESS);
                        result.setMessage(ResultConstant.Message.SUCCESS);
                        result.setCount(1);
                        result.setTotalCount(1);
                    } catch (Exception ex) {
                        result.setExceptionMsg(ex.getMessage());
                    }

                    return result;
                })
                .orElse(new UserDomainResult(ResultConstant.Code.ERROR, ResultConstant.Message.MISSING_PARAM));
    }

    /**
     * 构造一个user的通用query类
     * @param req 传入的请求
     * @return 构造出来的query类
     */
    private Query buildCommonQuery(UserDomainRequest req){
        final Query query = new Query();

        Optional.ofNullable(req.getId())
                .ifPresent((value) -> query.addCriteria(Criteria.where("_id").is(value)));
        Optional.ofNullable(req.getAccount())
                .ifPresent((value) -> query.addCriteria(Criteria.where("account").is(value)));
        Optional.ofNullable(req.getName())
                .ifPresent((value) -> query.addCriteria(Criteria.where("name").is(value)));
        Optional.ofNullable(req.getMobilePhone())
                .ifPresent((value) -> query.addCriteria(Criteria.where("mobile_phone").is(value)));
        Optional.ofNullable(req.getEmailAddress())
                .ifPresent((value) -> query.addCriteria(Criteria.where("email_address").is(value)));
        Optional.ofNullable(req.getPassword())
                .ifPresent((value) -> query.addCriteria(Criteria.where("password").is(value)));
        Optional.ofNullable(req.getLastLoginIp())
                .ifPresent((value) -> query.addCriteria(Criteria.where("last_login_ip").is(value)));
        Optional.ofNullable(req.getGender())
                .ifPresent((value) -> query.addCriteria(Criteria.where("gender").is(value)));
        Optional.ofNullable(req.getBirthday())
                .ifPresent((value) -> query.addCriteria(Criteria.where("birthday").is(value)));
        Optional.ofNullable(req.getAuthLevel())
                .ifPresent((value) -> query.addCriteria(Criteria.where("auth_level").is(value)));

        return query;
    }
}
