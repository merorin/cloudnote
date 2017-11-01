package merorin.cloud.cloudnote.common;

/**
 * Description: 权限等级枚举类
 *
 * @author guobin On date 2017/10/27.
 * @version 1.0
 * @since jdk 1.8
 */
public enum AuthLevelEnum {

    /**
     * 游客
     */
    VISITOR(0),

    /**
     * 普通用户
     */
    ORDINARY(1),

    /**
     * 会员用户
     */
    MEMBER(2),

    /**
     * 管理者
     */
    ADMIN(3),

    /**
     * 超级权限用户
     */
    ROOT(4);

    private final int value;

    AuthLevelEnum(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static AuthLevelEnum valueOf(final int value){
        switch (value) {
            case 1:
                return ORDINARY;
            case 2:
                return MEMBER;
            case 3:
                return ADMIN;
            case 4:
                return ROOT;
            default:
                return VISITOR;
        }
    }
}
