package com.mainlab.Task;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;



public class TaskThreadFactory implements ThreadFactory{
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;
    private static final String POOL_NAME_DEFAULT = "TaskDefaultPool";
    
    
    public TaskThreadFactory(String poolName) {
    	if (poolName == null || "".equals(poolName.trim())) {
    		poolName = POOL_NAME_DEFAULT;
    	}
        SecurityManager s = System.getSecurityManager();
        group = (s != null)? s.getThreadGroup() :
                             Thread.currentThread().getThreadGroup();
        namePrefix = poolName +
                      poolNumber.getAndIncrement() +
                     "-thread-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                              namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}

