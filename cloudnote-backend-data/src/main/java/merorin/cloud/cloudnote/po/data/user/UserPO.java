package merorin.cloud.cloudnote.user;

import com.alibaba.fastjson.JSON;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
     * 数据的创建时间,不能参与到业务层算法之中
     */
    @Field("create_time")
    private Date createTime;

    /**
     * 最后更新时间,不能参与到业务层算法之中
     */
    @Field("last_update_time")
    private Date lastUpdateTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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
        return Objects.equals(id, userPO.id) &&
                Objects.equals(name, userPO.name) &&
                Objects.equals(mobilePhone, userPO.mobilePhone) &&
                Objects.equals(emailAddress, userPO.emailAddress) &&
                Objects.equals(password, userPO.password) &&
                Objects.equals(lastLoginIp, userPO.lastLoginIp) &&
                Objects.equals(gender, userPO.gender) &&
                Objects.equals(birthday, userPO.birthday) &&
                Objects.equals(createTime, userPO.createTime) &&
                Objects.equals(lastUpdateTime, userPO.lastUpdateTime);
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
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (lastUpdateTime != null ? lastUpdateTime.hashCode() : 0);
        return result;
    }
}
