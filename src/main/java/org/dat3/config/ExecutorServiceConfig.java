package org.dat3.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceConfig {
    private static final int threadCount = 4;
    private ExecutorService executorService;
    public ExecutorService getExecutorService(){
        if (executorService == null){
            executorService = Executors.newFixedThreadPool(threadCount);
        }
        return executorService;
    }
    public void shutdownExecutorService(){
        if (executorService != null && !executorService.isShutdown()){
            executorService.shutdown();
        }
    }

}
