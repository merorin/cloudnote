package merorin.cloud.cloudnote.po.data.user;

import com.alibaba.fastjson.JSON;
import merorin.cloud.cloudnote.common.AuthLevelEnums;
import merorin.cloud.cloudnote.common.GenderEnums;
import merorin.cloud.cloudnote.po.data.BasePO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Description: 用户的持久化对象
 *
 * @author guobin On date 2017/10/24.
 * @version 1.0
 * @since jdk 1.8
 */
@Document(collection = "elan_user")
public class UserPO extends BasePO {

    private static final long serialVersionUID = 4100555050465495591L;

    /**
     * 唯一数据库id
     */
    @Id
    private String id;

    /**
     * 账号
     */
    @Indexed
    private String account;

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
    private GenderEnums gender;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 验证码
     */
    @Field("verify_code")
    private String verifyCode;

    /**
     * 权限等级
     */
    @Field("auth_level")
    private AuthLevelEnums authLevel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public GenderEnums getGender() {
        return gender;
    }

    public void setGender(GenderEnums gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public AuthLevelEnums getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(AuthLevelEnums authLevel) {
        this.authLevel = authLevel;
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
        if (!(o instanceof UserPO)) {
            return false;
        }
        UserPO userPO = (UserPO) o;
        return Objects.equals(id, userPO.id) &&
                Objects.equals(account, userPO.account) &&
                Objects.equals(name, userPO.name) &&
                Objects.equals(mobilePhone, userPO.mobilePhone) &&
                Objects.equals(emailAddress, userPO.emailAddress) &&
                Objects.equals(password, userPO.password) &&
                Objects.equals(lastLoginIp, userPO.lastLoginIp) &&
                gender == userPO.gender &&
                Objects.equals(birthday, userPO.birthday) &&
                Objects.equals(verifyCode, userPO.verifyCode) &&
                authLevel == userPO.authLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, name, mobilePhone, emailAddress, password, lastLoginIp, gender, birthday, verifyCode, authLevel);
    }
}
