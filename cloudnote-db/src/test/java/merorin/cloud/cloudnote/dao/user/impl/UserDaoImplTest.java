package merorin.cloud.cloudnote.dao.user.impl;

import merorin.cloud.cloudnote.base.BaseTest;
import merorin.cloud.cloudnote.common.AuthLevelEnum;
import merorin.cloud.cloudnote.common.GenderEnum;
import merorin.cloud.cloudnote.common.ResultConstant;
import merorin.cloud.cloudnote.dao.user.UserDao;
import merorin.cloud.cloudnote.fcq.io.param.FcqFunctionParam;
import merorin.cloud.cloudnote.po.data.user.UserPO;
import merorin.cloud.cloudnote.po.request.CommonDomainRequest;
import merorin.cloud.cloudnote.po.result.CommonDomainResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Description: User dao的测试类
 *
 * @author guobin On date 2017/10/27.
 * @version 1.0
 * @since jdk 1.8
 */
public class UserDaoImplTest extends BaseTest {

    private static final String DB_DATA_PATH = "/user-data.json";

    @Resource
    private UserDao userDao;

    private List<UserPO> dataList;

    @Before
    public void setUp() throws Exception {
        this.dataList = this.prepareDbData(DB_DATA_PATH, UserPO.class);
    }

    @After
    public void tearDown() throws Exception {
        List<String> ids = this.dataList.stream().map(UserPO::getId).collect(Collectors.toList());
        this.rollback(ids, UserPO.class);
    }

    @Test
    public void getById() throws Exception {
        Integer index = new Random().nextInt(this.dataList.size());
        UserPO user = this.dataList.get(index);
        CommonDomainResult<UserPO> result = Optional.ofNullable(user)
                .map(UserPO::getId)
                .map(this.userDao::getById)
                .orElse(new CommonDomainResult<>());
        assertEquals(ResultConstant.Code.SUCCESS, result.getCode());
        assertEquals(user, result.getValue());
    }

    @Test
    public void listByRequest() throws Exception {
        CommonDomainRequest<UserPO> request = new CommonDomainRequest<>();
        request.setNeedPaging(false);
        UserPO userParam = new UserPO();
        userParam.setGender(GenderEnum.FEMALE);
        request.setValue(userParam);
        CommonDomainResult<UserPO> result = this.userDao.listByRequest(request);
        assertEquals(ResultConstant.Code.SUCCESS, result.getCode());
        List<UserPO> expectedList = this.dataList.stream()
                .filter(user -> GenderEnum.FEMALE.equals(user.getGender()))
                .collect(Collectors.toList());
        assertEquals(expectedList, result.getValues());
    }

    @Test
    public void countByRequest() throws Exception {
        CommonDomainRequest<UserPO> request = new CommonDomainRequest<>();
        request.setNeedPaging(false);
        UserPO userParam = new UserPO();
        userParam.setGender(GenderEnum.FEMALE);
        userParam.setAuthLevel(AuthLevelEnum.ADMIN);
        request.setValue(userParam);
        CommonDomainResult<UserPO> result = this.userDao.countByRequest(request);
        assertEquals(ResultConstant.Code.SUCCESS, result.getCode());
        long expectedCount = this.dataList.stream()
                .filter(user -> GenderEnum.FEMALE.equals(user.getGender()) && AuthLevelEnum.ADMIN.equals(user.getAuthLevel()))
                .count();
        assertEquals(expectedCount, result.getTotalCount());
    }

    @Test
    public void saveUser() throws Exception {
        UserPO user = this.buildPO();
        CommonDomainResult<UserPO> result = this.userDao.saveUser(user);
        assertEquals(ResultConstant.Code.SUCCESS, result.getCode());
        if (result.isSuccess()) {
            this.dataList.add(user);
        }
    }

    @Test
    public void removeByRequest() throws Exception {
        CommonDomainRequest<UserPO> request = new CommonDomainRequest<>();
        request.setNeedPaging(false);
        UserPO userParam = new UserPO();
        userParam.setGender(GenderEnum.FEMALE);
        userParam.setAuthLevel(AuthLevelEnum.ADMIN);
        request.setValue(userParam);
        //进行删除
        CommonDomainResult<UserPO> delResult = this.userDao.removeByRequest(request);
        assertEquals(ResultConstant.Code.SUCCESS, delResult.getCode());
        List<UserPO> expectedList = this.dataList.stream()
                .filter(user -> !GenderEnum.FEMALE.equals(user.getGender()) || !AuthLevelEnum.ADMIN.equals(user.getAuthLevel()))
                .collect(Collectors.toList());
        //查询删除之后数据库中所剩的数据
        CommonDomainRequest<UserPO> listRequest = new CommonDomainRequest<>();
        listRequest.setNeedPaging(false);
        CommonDomainResult<UserPO> listResult = this.userDao.listByRequest(listRequest);
        assertEquals(expectedList, listResult.getValues());
    }

    @Test
    public void updateById() throws Exception {
        Integer index = new Random().nextInt(this.dataList.size());
        UserPO user = this.dataList.get(index);
        CommonDomainResult<UserPO> result = Optional.ofNullable(user)
                .map(value -> {
                    value.setPassword("ThisIsNewPassword");
                    value.setLastLoginIp("192.168.1.100");
                    return value;
                })
                .map(this.userDao::updateById)
                .orElse(new CommonDomainResult<>());
        assertEquals(ResultConstant.Code.SUCCESS, result.getCode());
        assertEquals(user, result.getValue());
    }

    /**
     * 构造一条PO
     * @return 构造出来的结果
     */
    private UserPO buildPO() {
        UserPO user = new UserPO();

        user.setName("魔法师");
        user.setAccount("the_only_wizard");
        user.setPassword("wizard");
        user.setMobilePhone("13959530001");
        user.setBirthday(LocalDate.of(2012,1,2));
        user.setEmailAddress("wizard@qq.com");
        user.setLastLoginIp("192.168.1.2");
        user.setVerifyCode("1001");
        user.setGender(GenderEnum.FEMALE);
        user.setAuthLevel(AuthLevelEnum.ROOT);

        return user;
    }

}