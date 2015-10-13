package system.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import system.pool.params.ParamsPoolThreadsUseCache;
import system.threads.ThreadUseCache;
import system.threads.params.ParamsThreadUseCache;
import system.utilities.UtilityClass;

import org.apache.log4j.Logger;

public class PoolThreadsUseCache {
	private static final Logger LOG = Logger.getLogger(UtilityClass.getName());
	
	private int countThreadsUseCache;
	private List<ThreadUseCache> listThreadsUseCache;	
	
	private ParamsThreadUseCache paramsThreadUseCache;
	
	private CountDownLatch latchStopPool;
	
	public PoolThreadsUseCache(ParamsPoolThreadsUseCache paramsPoolThreadsUseCache) {
		paramsThreadUseCache = paramsPoolThreadsUseCache.paramsThreadUseCache;
		countThreadsUseCache = paramsPoolThreadsUseCache.countThreadsUseCache;		
		latchStopPool = new CountDownLatch(countThreadsUseCache);
		paramsThreadUseCache.latchStopPool = latchStopPool;
		listThreadsUseCache = new ArrayList<>(countThreadsUseCache);
				
		for ( int indexThread = 0; indexThread < countThreadsUseCache; indexThread++ ) {
			String nameThread = new String("ThrdUseCache #" + (indexThread + 1));
			listThreadsUseCache.add(new ThreadUseCache(nameThread, paramsThreadUseCache));
		}
	}
	
	public void startThreadsInSuspendState() {
		for ( ThreadUseCache threadUseCache : listThreadsUseCache ) {
			threadUseCache.startThreadInSuspendState();
		}	
		LOG.debug("Pool of threads (" + countThreadsUseCache + ") were started in suspend state.");
	}
	
	public void stopThreads() {
		for ( ThreadUseCache threadUseCache : listThreadsUseCache ) {
			threadUseCache.stopThread();
		}
		waitStopThreadsPool();
		LOG.debug("Pool of threads (" + countThreadsUseCache + ") were terminated.");
	}
	
	private void waitStopThreadsPool() {
		try {
			latchStopPool.await();
		} catch (InterruptedException ignore) {
			// NOP
		}
	}
}
