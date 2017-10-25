package merorin.cloud.cloudnote.po.request.user;

import merorin.cloud.cloudnote.request.CommonRequest;

import java.util.Date;

/**
 * Description: user的po层请求集
 *
 * @author guobin On date 2017/10/25.
 * @version 1.0
 * @since jdk 1.8
 */
public class UserDomainRequest extends CommonRequest {

    /**
     * 唯一数据库id
     */
    private String id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户手机
     */
    private String mobilePhone;

    /**
     * 电子邮箱地址
     */
    private String emailAddress;

    /**
     * 密码
     */
    private String password;

    /**
     * 上次登录的ip地址
     */
    private String lastLoginIp;

    /**
     * 性别
     */
    private String gender;

    /**
     * 生日
     */
    private Date birthday;

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
}
