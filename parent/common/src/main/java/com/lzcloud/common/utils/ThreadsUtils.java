package com.lzcloud.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 线程相关工具类.
 *
 * @author lz
 * @since 2018-12-03
 */
@Slf4j
public class ThreadsUtils {

    private ExecutorService executorService;

    public ExecutorService getExecutorService() {
        return getExecutorService(null);
    }

    public synchronized ExecutorService getExecutorService(Integer num) {
        if (executorService == null || executorService.isShutdown()) {
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            num = num == null ? availableProcessors * 2 : num;
            log.info("active thread num:" + num);
            this.executorService = Executors.newFixedThreadPool(num, (r) -> {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            });
        }
        return executorService;
    }

    public void shutdown() {
        //多线程处理日志写入
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    public void shutdown(long timeout) {
        try {
            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdown();
                if (!executorService.awaitTermination(timeout, TimeUnit.SECONDS)) {
                    // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                    executorService.shutdownNow();
                }
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();
            log.error("awaitTermination exception", e.getMessage());
            executorService.shutdownNow();
        }
    }


    public <T, R> List<R> executeFutures(List<T> list, Function<T, R> execute) {
        return executeFutures(list, execute, false);
    }

    public <T, R> List<R> executeFutures(List<T> list, Function<T, R> execute, boolean isShutdown) {
        return executeFutures(list, execute, isShutdown, null);
    }

    public <T, R> List<R> executeFutures(List<T> list, Function<T, R> execute, Integer threadNum) {
        return executeFutures(list, execute, false, threadNum);
    }

    public <T, R> List<R> executeFutures(List<T> list, Function<T, R> execute, boolean isShutdown, Integer threadNum) {
        if (execute == null) {
            return null;
        }
        List<R> rs = null;
        try {
            List<CompletableFuture<R>> executeFutures =
                    list.parallelStream().filter(e -> e != null)
                            .map(e -> CompletableFuture.supplyAsync(() -> {
                                        try {
                                            return execute.apply(e);
                                        } catch (Exception ex) {
                                            log.error("executeFutures error:{}", ex.getMessage(), ex);
                                            return null;
                                        }
                                    }
                                    , getExecutorService(threadNum)))
                            .collect(Collectors.toList());
            rs = executeFutures.stream()
                    .map(CompletableFuture::join).filter(e -> e != null)
                    .collect(Collectors.toList());
        } catch (RejectedExecutionException e) {
            log.error("Shutting down");
        } finally {
            if (isShutdown) {
                shutdown();
            }
        }
        return rs;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        List<Integer> rs = new ThreadsUtils().executeFutures(list, (e) -> {
            System.out.println(e);
            return e;
        });
        System.out.println(rs);
    }
}
