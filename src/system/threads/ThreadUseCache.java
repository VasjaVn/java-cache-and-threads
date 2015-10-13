package system.threads;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import system.cache.StateCache;
import system.threads.params.ParamsThreadUseCache;
import system.utilities.UtilityClass;
import system.utilities.UtilityTUSC;
import system.utilities.params.ParamsUtilityTUSC;

public class ThreadUseCache extends Thread {
	private static final Logger LOG = Logger.getLogger(UtilityClass.getName());
	
	private ParamsThreadUseCache paramsThreadUseCache;
	private ParamsUtilityTUSC paramsUtilityTUSC;
	
	private long timeSleepBetweenUseCacheInMilliseconds;
	
	private volatile boolean flagStopThread;	
	
	public ThreadUseCache(String nameThread, ParamsThreadUseCache paramsThreadUseCache) {
		super(nameThread);
		this.paramsThreadUseCache = paramsThreadUseCache;
		
		paramsUtilityTUSC = new ParamsUtilityTUSC();
		paramsUtilityTUSC.nameThread = nameThread;
		paramsUtilityTUSC.typeElementCache = paramsThreadUseCache.typeElementCache;
	}
	
	public void startThreadInSuspendState() {
		flagStopThread = false;
		start();		
	}
	
	public void stopThread() {
		flagStopThread = true;
	}

	@Override
	public void run() {
		waitOnRun();
		LOG.debug("[" + getName() + "]: Start." );
		
		while ( true ) {
			emulateWorkBetweenUseCache();
			
			if (flagStopThread) break;
			
			if ( isNeedUpdateCache() ) {
				updateCache();
			} else {
				useCache();
			}
		}		
		
		LOG.debug("[" + getName() + "]: Terminated." );
		paramsThreadUseCache.latchStopPool.countDown();
		paramsThreadUseCache.latchStop.countDown();
	}
	
	private void waitOnRun() {
		try {
			paramsThreadUseCache.latchRun.await();
		} catch (InterruptedException e) {
			LOG.warn("Interrupted Latch Of Run!", e);
		}			
	}
	
	private boolean isNeedUpdateCache() {
		return paramsThreadUseCache.cache.isCacheNeedUpdate();
	}
	
	private void updateCache() {
		LOG.debug("[" + getName() + "]: REQUEST UPDATE CACHE.");
		paramsThreadUseCache.threadUpdateCache.requestUpdateCache();
	}
	
	private void useCache() {
		int randPercentUseElementOfCache = new Random().nextInt(99) + 1; // 1% .. 100%
		if ( randPercentUseElementOfCache <= paramsThreadUseCache.percentUseElementOfCache ) {
			useElementOfCache();
		} else {
			useAllElementsOfCache();
		}
	}
	
	private void useAllElementsOfCache() {
		StateCache stateCache = new StateCache();
		
		paramsUtilityTUSC.element = null;
		paramsUtilityTUSC.listElements = paramsThreadUseCache.cache.getElements(stateCache);
		paramsUtilityTUSC.timeSleepBetweenUseCacheInMilliseconds = timeSleepBetweenUseCacheInMilliseconds;
		
		switch ( stateCache.sc ) {
			case EMPTY:
				UtilityTUSC.printEmptyCache(paramsUtilityTUSC);
				break;
				
			case NEW_DATA:
				paramsUtilityTUSC.isOldData = false;
				UtilityTUSC.printAllElementsOfCache(paramsUtilityTUSC);
				break;
				
			case OLD_DATA:
				paramsUtilityTUSC.isOldData = true;
				UtilityTUSC.printAllElementsOfCache(paramsUtilityTUSC);
				break;

			// Not using.
			case FULL:
				break;
		}
	}

	private void useElementOfCache() {
		StateCache stateCache = new StateCache();
		
		paramsUtilityTUSC.element = paramsThreadUseCache.cache.getElement(stateCache);
		paramsUtilityTUSC.listElements = null;
		paramsUtilityTUSC.timeSleepBetweenUseCacheInMilliseconds = timeSleepBetweenUseCacheInMilliseconds;

		switch ( stateCache.sc ) {
			case EMPTY:
				UtilityTUSC.printEmptyCache(paramsUtilityTUSC);
				break;
				
			case NEW_DATA:
				paramsUtilityTUSC.isOldData = false;
				UtilityTUSC.printElementOfCache(paramsUtilityTUSC);
				break;
				
			case OLD_DATA:
				paramsUtilityTUSC.isOldData = true;
				UtilityTUSC.printElementOfCache(paramsUtilityTUSC);
				break;

			// Not using.
			case FULL:
				break;
		}
	}

	private void emulateWorkBetweenUseCache() {
		int maxTimeSleepBetweenUseCacheInMilliseconds = paramsThreadUseCache.maxTimeSleepBetweenUseCacheInMilliseconds;
		timeSleepBetweenUseCacheInMilliseconds = (long)new Random().nextInt(maxTimeSleepBetweenUseCacheInMilliseconds);
		try {
			TimeUnit.MILLISECONDS.sleep(timeSleepBetweenUseCacheInMilliseconds);
		} catch (InterruptedException ignore) {
			//NOP
		}
	}
}
