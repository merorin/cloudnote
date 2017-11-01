package merorin.cloud.cloudnote.fcq.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Description: fcq线程工厂
 *
 * @author guobin On date 2017/11/1.
 * @version 1.0
 * @since jdk 1.8
 */
public class FcqThreadFactory implements ThreadFactory {

    /**
     * 标记线程id
     */
    private final AtomicLong number;

    /**
     * 线程工厂的名字
     */
    private final String name;

    FcqThreadFactory(String name) {
        this.number = new AtomicLong();
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread newThread = Executors.defaultThreadFactory().newThread(r);
        newThread.setName(name + "-" + number.getAndIncrement());
        //所有线程都是守护线程
        newThread.setDaemon(true);

        return newThread;
    }
}
