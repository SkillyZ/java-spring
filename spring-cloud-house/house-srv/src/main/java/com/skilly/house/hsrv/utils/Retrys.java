package com.mooc.house.hsrv.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * retry管理器
 * @author wanghongfeng
 *
 */
public final class Retrys {
  private static final Logger logger = LoggerFactory.getLogger(Retrys.class);
  private static final int  defaultRetryCount = 3;
  private static final long sleepTimeMis = 10;
  
  private Retrys(){}

  private static final RetryPolicy defaultRetryPolicy  = new CountRetryPolicy(defaultRetryCount);
  
  
  public static   class CountRetryPolicy implements RetryPolicy{
    
    protected static final int FACTOR = 5;
    
    protected int retryCnt;
    
    protected List<Class<?>> allowExceptionClazzList;
    
    public  CountRetryPolicy(int retryCount) {
       this.retryCnt = retryCount;
    }
    
    /**
     * 比如允许1000次，即允许连续10秒的失败窗口
     * 允许60000次，即允许连续10分钟的失败窗口
     * @param retryCount
     * @param allowExceptionClazzList
     */
    public  CountRetryPolicy(int retryCount,List<Class<?>> allowExceptionClazzList) {
      this.retryCnt = retryCount;
      this.allowExceptionClazzList = allowExceptionClazzList;
   }
    
    /**
     * 网络异常会重试,运行时异常以及中断异常及其他异常都不会重试
     * 是否重试
     */
    @Override
    public boolean takeException(Throwable exception) {
      if (exception instanceof ConnectException || exception.getCause() instanceof ConnectException) {
        return true;
      }
      if (exception instanceof SocketException || exception.getCause() instanceof SocketException) {
        return true;
      }
      if (exception instanceof SocketTimeoutException || exception.getCause() instanceof SocketTimeoutException) {
        return true;
      }
      if (allowExceptionClazzList != null && !allowExceptionClazzList.isEmpty() ) {
        for (Class<?> allowExClazz : allowExceptionClazzList) {
          if (allowExClazz.isAssignableFrom(exception.getClass()) || (exception.getCause() != null && allowExClazz.isAssignableFrom(exception.getCause().getClass()))) {
            return true;
          }
        }
      }
      if (exception instanceof InterruptedException || exception.getCause() instanceof InterruptedException) {
        Thread.currentThread().interrupt();//中断异常,由于catch住导致中断位归零,所以要再次中断
      }
      logger.error("[retry loop encounter exception ] "+exception.getMessage(), exception);
      return false;
    }
    
    @Override
    public boolean allowRetry(int loopCount) {//记得sleep一下
      if (retryCnt > loopCount) {
        try {
          TimeUnit.MILLISECONDS.sleep(sleepTimeMis + FACTOR * loopCount);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          return false;
        }
        return true;
      }
      return false;
    }
  }
  
  public interface RetryPolicy {
    /**
     * 
     * @param retryCount 重试次数
     * @param elapsedTimeMs 持续时间
     * @return
     */
    public boolean allowRetry(int retryCount);

    /**
     * 
     * @param exception
     * @return
     */
    public boolean takeException(Throwable exception);

  }
  
  /**
   * 使用默认retry策略的retry方法
   * @param call
   * @return
   * @throws Exception
   */
  public static <T> T retry(Callable<T> call){
    return retry(defaultRetryPolicy, call);
  }
  
  /**
   * 自定义retry策略
   * @param policy
   * @param call
   * @return
   * @throws Exception
   */
  public static <T> T retry(RetryPolicy policy, Callable<T> call) {
    T result = null;
    int retryCount = 0;
    while (policy.allowRetry(++retryCount)) {
      try {
        result = call.call();
      } catch (Exception e) {
        if (!policy.takeException(e)) {//can rethrow exception
          break;
        }else {
          continue;
        }
      }
      break;
    }
    return result;
  }

}
