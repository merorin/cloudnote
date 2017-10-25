package merorin.cloud.cloudnote.common;

/**
 * Description: 封装返回结果的常量集合
 *
 * @author guobin On date 2017/10/25.
 * @version 1.0
 * @since jdk 1.8
 */
public class ResultConstant {

    /**
     * 封装result的返回码
     */
    public static class Code {

        /**
         * 成功返回码
         */
        public static final int SUCCESS = 200;

        /**
         * 失败返回码
         */
        public static final int ERROR = 400;

        /**
         * 没有权限返回码
         */
        public static final int UN_AUTH = 600;
    }

    /**
     * 封装result的通用返回信息
     */
    public static class Message {

        /**
         * 成功的返回信息
         */
        public static final String SUCCESS = "操作成功";

        /**
         * 失败的返回信息
         */
        public static final String ERROR = "操作失败";

        /**
         * 没有查到记录的返回信息
         */
        public static final String NOT_FOUND = "查无记录";

        /**
         *
         */
        public static final String MISSING_PARAM = "缺少参数";
    }
}
