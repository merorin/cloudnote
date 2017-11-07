package merorin.cloud.cloudnote.fcq.io.common;

/**
 * Description: fcq result的常量
 *
 * @author guobin On date 2017/11/3.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqResultConstant {

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
