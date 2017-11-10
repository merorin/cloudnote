package merorin.cloud.cloudnote.utils;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Description: 封装字符串的工具类
 *
 * @author guobin On date 2017/11/7.
 * @version 1.0
 * @since jdk 1.8
 */
public class StringUtils {

    /**
     * 校验手机号的正则表达式
     */
    private static Pattern mobilePhoneRegex;

    static {
        String pattern = "^(1)\\d{10}$";
        mobilePhoneRegex = Pattern.compile(pattern);
    }

    /**
     * 字符串格式化工具,参数必须以{0}之类的样式标示出来.大括号中的数字从0开始。
     * <p>
     * @param source 源字符串
     * @param params 需要替换的参数列表,写入时会调用每个参数的toString().
     * <p>
     * @return 替换完成的字符串。如果原始字符串为空或者参数为空那么将直接返回原始字符串。
     */
    public static String replaceArgs(String source, Object... params) {

        return Optional.ofNullable(source).filter(src -> !src.isEmpty())
                .map(src -> Optional.ofNullable(params)
                        .filter(paramArr -> paramArr.length > 0)
                        .map(paramArr -> StringUtils.doStrReplace(paramArr, src))
                        .orElse(src)
                ).orElse(source);
    }

    /**
     * 判断一个字符串是否为手机号
     * @param mp 传入的手机号
     * @return 判断结果
     */
    public static boolean isMobilePhone(String mp) {
        return Optional.ofNullable(mp)
                .map(phone -> mobilePhoneRegex.matcher(phone).matches())
                .orElse(false);
    }

    /**
     * 字符串替换
     * @param args 参数数组
     * @param src 原字符串
     * @return 进行替换后的字符串
     */
    private static String doStrReplace(Object[] args, String src) {
        String target = src;
        for (int i = 0 ; i < args.length ; i++) {
            String argStr = Optional.ofNullable(args[i])
                    .map(Object::toString)
                    .orElse("null");

            String replaceStr = "{" + i + "}";
            target = target.replace(replaceStr, argStr);
        }

        return target;
    }
}
