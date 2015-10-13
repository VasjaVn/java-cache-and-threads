package system;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import system.cache.Cache;
import system.config.Config;
import system.pool.PoolThreadsUseCache;
import system.pool.params.ParamsPoolThreadsUseCache;
import system.threads.ThreadModifyCache;
import system.threads.ThreadUpdateCache;
import system.threads.params.ParamsThreadModifyCache;
import system.threads.params.ParamsThreadNotifyUpdateCache;
import system.threads.params.ParamsThreadUpdateCache;
import system.threads.params.ParamsThreadUseCache;
import system.utilities.UtilityClass;

public class SystemTestCache {
	private static final Logger LOG = Logger.getLogger(UtilityClass.getName());
	
	private ParamsThreadNotifyUpdateCache paramsThreadNotifyUpdateCache;
	private ParamsThreadUpdateCache paramsThreadUpdateCache;
	private ParamsPoolThreadsUseCache paramsPoolThreadsUseCache;
	private ParamsThreadUseCache paramsThreadUseCache;
	private ParamsThreadModifyCache paramsThreadModifyCache;
	
	private CountDownLatch latchRun;
	private CountDownLatch latchStop;
	private Cache cache;
	private ThreadUpdateCache threadUpdateCache;
	private PoolThreadsUseCache poolThreadsUseCache;
	private ThreadModifyCache threadModifyCache;
	
	private int timeOutTestCacheInMinutes;
	private long timeUpdateCacheInMilliseconds;
	private String typeElementCache;
	private int countThreadsUseCache;
	private int maxTimeSleepBetweenUseCacheInMilliseconds;
	private int timeWaitResponseUpdateCacheInSeconds;
	private int countGenerateElements;
	private boolean flagUseThreadModifyCache;
	private int maxTimeSleepBetweenModifyCacheInMilliseconds;
	private int percentAddElementToCache;
	private int percentUseElementOfCache;
	
	public SystemTestCache() {
		paramsThreadNotifyUpdateCache = new ParamsThreadNotifyUpdateCache();
		paramsThreadUpdateCache = new ParamsThreadUpdateCache();
		paramsPoolThreadsUseCache = new ParamsPoolThreadsUseCache();
		paramsThreadUseCache = new ParamsThreadUseCache();
		paramsThreadModifyCache = new ParamsThreadModifyCache();
		
		paramsPoolThreadsUseCache.paramsThreadUseCache = paramsThreadUseCache;	
	}
	
	public boolean initialize() {
		if(!Config.initialize()) {
			return false;
		}
		
		readParamsFromConfig();
		
		/*2 = threadUpdateCache and threadNotifyUpdateCache(using in Cache)*/
		int countThreadsOfSystem = countThreadsUseCache + 2;
		if ( flagUseThreadModifyCache ) {
			// threadModifyCache
			countThreadsOfSystem++;
		}
		latchRun = new CountDownLatch(1);
		latchStop = new CountDownLatch(countThreadsOfSystem);			

		initializeCache();
		inititializeThreadUpdateCache();
		initializePoolThreadsUseCache();
		if ( flagUseThreadModifyCache ) {
			initializeThreadModifyCache();
		}
				
		return true;
	}
	
	public void start() {
		cache.initializeThreadNotifyUpdateCache();
		poolThreadsUseCache.startThreadsInSuspendState();
		threadUpdateCache.startThreadInSuspendState();
		if ( flagUseThreadModifyCache ) {
			threadModifyCache.startThreadInSuspendState();
		}
		
		runAllThreads();
	}
	
	public void stop() {
		cache.unInitializeThreadNotifyUpdateCache();
		poolThreadsUseCache.stopThreads();
		threadUpdateCache.stopThread();
		if ( flagUseThreadModifyCache ) {
			threadModifyCache.stopThread();
		}
		
		waitTerminationAllThreads();
	}
	
	public void timeOutTestingCache() {
		try {
			TimeUnit.MINUTES.sleep(timeOutTestCacheInMinutes);
		} catch (InterruptedException ignore) {
			// NOP
		}		
	}
	
	private void runAllThreads() {
		LOG.debug("Run all threads.");
		latchRun.countDown();
	}
	
	private void waitTerminationAllThreads() {
		try {
			latchStop.await();
		} catch (InterruptedException ignore) {
			// NOP
		}
	}
	
	private void readParamsFromConfig() {
		timeOutTestCacheInMinutes = Config.System.getTimeOutTestCacheInMinutes();
		timeUpdateCacheInMilliseconds = Config.Cache.getTimeUpdateInMilliseconds();
		typeElementCache = Config.Cache.getTypeElement();
		countThreadsUseCache = Config.Thread.getCountThreadsUseCache();
		maxTimeSleepBetweenUseCacheInMilliseconds = Config.Thread.getMaxTimeSleepBetweenUseCacheInMilliseconds();
		timeWaitResponseUpdateCacheInSeconds = Config.Thread.getTimeWaitRespUpdateCacheInSeconds();
		countGenerateElements = Config.Thread.getCountGenerateElements();
		flagUseThreadModifyCache = Config.Thread.getFlagUseThreadModifyCache();
		maxTimeSleepBetweenModifyCacheInMilliseconds = Config.Thread.getMaxTimeSleepBetweenModifyCacheInMilliseconds();
		percentAddElementToCache = Config.Thread.getPercentAddElementToCache();
		percentUseElementOfCache = Config.Thread.getPercentUseElemenCache();		
	}
	
	private void initializeCache() {
		initParamsThreadNotifyUpdateCache();
		cache = new Cache(typeElementCache, paramsThreadNotifyUpdateCache);
	}
	
	private void initializePoolThreadsUseCache() {
		initParamsPoolThreadsUseCache();
		poolThreadsUseCache = new PoolThreadsUseCache(paramsPoolThreadsUseCache);		
	}
	
	private void inititializeThreadUpdateCache() {
		initParamsThreadUpdateCache();
		threadUpdateCache = new ThreadUpdateCache(paramsThreadUpdateCache);
	}

	private void initializeThreadModifyCache() {
		initParamsThreadModifyCache();
		threadModifyCache = new ThreadModifyCache(paramsThreadModifyCache);		
	}
	
	private void initParamsPoolThreadsUseCache() {
		paramsPoolThreadsUseCache.countThreadsUseCache = countThreadsUseCache;
		paramsPoolThreadsUseCache.paramsThreadUseCache.typeElementCache = typeElementCache;
		paramsPoolThreadsUseCache.paramsThreadUseCache.cache = cache;
		paramsPoolThreadsUseCache.paramsThreadUseCache.threadUpdateCache = threadUpdateCache;
		paramsPoolThreadsUseCache.paramsThreadUseCache.latchRun = latchRun;
		paramsPoolThreadsUseCache.paramsThreadUseCache.latchStop = latchStop;
		paramsPoolThreadsUseCache.paramsThreadUseCache.maxTimeSleepBetweenUseCacheInMilliseconds = maxTimeSleepBetweenUseCacheInMilliseconds;
		paramsPoolThreadsUseCache.paramsThreadUseCache.percentUseElementOfCache = percentUseElementOfCache;
	}
	
	private void initParamsThreadUpdateCache() {
		paramsThreadUpdateCache.nameThread = "Thrd_UpdateCache";
		paramsThreadUpdateCache.cache = cache;
		paramsThreadUpdateCache.typeElementCache = typeElementCache;
		paramsThreadUpdateCache.countGenerateElements = countGenerateElements;
		paramsThreadUpdateCache.timeWaitResponseUpdateCacheInSeconds = timeWaitResponseUpdateCacheInSeconds;
		paramsThreadUpdateCache.latchRun = latchRun;
		paramsThreadUpdateCache.latchStop = latchStop;
	}
	
	private void initParamsThreadNotifyUpdateCache() {
		paramsThreadNotifyUpdateCache.nameThread = "Thrd_NotifyUpdateCache";
		paramsThreadNotifyUpdateCache.latchRun = latchRun;
		paramsThreadNotifyUpdateCache.latchStop = latchStop;
		paramsThreadNotifyUpdateCache.timeUpdateCasheInMilliseconds = timeUpdateCacheInMilliseconds;
	}
	
	private void initParamsThreadModifyCache() {
		paramsThreadModifyCache.nameThread = "Thrd_ModifyCache";
		paramsThreadModifyCache.latchRun = latchRun;
		paramsThreadModifyCache.latchStop = latchStop;
		paramsThreadModifyCache.cache = cache;
		paramsThreadModifyCache.typeElementCache = typeElementCache;
		paramsThreadModifyCache.maxTimeSleepBetweenModifyCacheInMilliseconds = maxTimeSleepBetweenModifyCacheInMilliseconds;
		paramsThreadModifyCache.percentAddElementToCache = percentAddElementToCache;
	}


}
