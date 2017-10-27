package merorin.cloud.cloudnote.po.data.user;

import com.alibaba.fastjson.JSON;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

/**
 * Description: 用户的持久化对象
 *
 * @author guobin On date 2017/10/24.
 * @version 1.0
 * @since jdk 1.8
 */
@Document(collection = "elan_user")
public class UserPO implements Serializable {

    private static final long serialVersionUID = 4100555050465495591L;

    /**
     * 唯一数据库id
     */
    @Id
    private String id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户手机
     */
    @Indexed
    @Field("mobile_phone")
    private String mobilePhone;

    /**
     * 电子邮箱地址
     */
    @Field("email_address")
    private String emailAddress;

    /**
     * 密码
     */
    private String password;

    /**
     * 上次登录的ip地址
     */
    @Field("last_login_ip")
    private String lastLoginIp;

    /**
     * 性别
     */
    private String gender;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 数据的创建时间,不能参与到业务层算法之中
     */
    @Field("gmt_create")
    private LocalTime createTime;

    /**
     * 最后更新时间,不能参与到业务层算法之中
     */
    @Field("gmt_modified")
    private LocalTime lastUpdateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public LocalTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalTime createTime) {
        this.createTime = createTime;
    }

    public LocalTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserPO userPO = (UserPO) o;

        return (id != null ? id.equals(userPO.id) : userPO.id == null)
                && (name != null ? name.equals(userPO.name) : userPO.name == null)
                && (mobilePhone != null ? mobilePhone.equals(userPO.mobilePhone) : userPO.mobilePhone == null)
                && (emailAddress != null ? emailAddress.equals(userPO.emailAddress) : userPO.emailAddress == null)
                && (password != null ? password.equals(userPO.password) : userPO.password == null)
                && (lastLoginIp != null ? lastLoginIp.equals(userPO.lastLoginIp) : userPO.lastLoginIp == null)
                && (gender != null ? gender.equals(userPO.gender) : userPO.gender == null)
                && (birthday != null ? birthday.equals(userPO.birthday) : userPO.birthday == null)
                && (verifyCode != null ? verifyCode.equals(userPO.verifyCode) : userPO.verifyCode == null)
                && (createTime != null ? createTime.equals(userPO.createTime) : userPO.createTime == null)
                && (lastUpdateTime != null ? lastUpdateTime.equals(userPO.lastUpdateTime) : userPO.lastUpdateTime == null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (mobilePhone != null ? mobilePhone.hashCode() : 0);
        result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (lastLoginIp != null ? lastLoginIp.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (verifyCode != null ? verifyCode.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (lastUpdateTime != null ? lastUpdateTime.hashCode() : 0);
        return result;
    }
}
