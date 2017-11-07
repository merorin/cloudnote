package merorin.cloud.cloudnote.utils;

import java.io.*;

/**
 * Description: 用于类克隆的工具类
 *
 * @author guobin On date 2017/10/25.
 * @version 1.0
 * @since jdk 1.8
 */
public class CloneUtils {

    /**
     * 深度拷贝一份数据
     * @param src 源数据
     * @param <T> 数据的类型
     * @return 拷贝出来的数据
     * @throws Exception 抛出的异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T cloneFrom(T src) throws Exception {
        //为空直接返回
        if (src == null) {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out;
        ObjectInputStream in;
        T dist;

        out = new ObjectOutputStream(baos);
        out.writeObject(src);
        out.flush();

        in = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        dist = (T) in.readObject();

        out.close();
        in.close();
        baos.close();

        return dist;
    }
}
