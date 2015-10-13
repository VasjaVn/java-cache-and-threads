package system.threads;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import system.threads.params.ParamsThreadUpdateCache;
import system.utilities.UtilityClass;
import system.utilities.UtilityTUPC;

public class ThreadUpdateCache extends Thread {
	private static final Logger LOG = Logger.getLogger(UtilityClass.getName());

	private Object lock = new Object();
	
	private volatile boolean flagStopThread;	
	private volatile boolean flagRequestUpdateCache;
	
	private ParamsThreadUpdateCache paramsThreadUpdateCache; 
	
	public ThreadUpdateCache(ParamsThreadUpdateCache paramsThreadUpdateCache) {
		super(paramsThreadUpdateCache.nameThread);
		this.paramsThreadUpdateCache = paramsThreadUpdateCache;		
	}

	public void startThreadInSuspendState() {
		flagStopThread = false;
		start();
		LOG.debug("Thread of update cache was started in suspend state.");
	}
	
	public void stopThread() {
		flagStopThread = true;
		requestUpdateCache();
	}
	
	public void requestUpdateCache() {
		synchronized( lock ) {
			flagRequestUpdateCache = true;
			lock.notify();
		}
	}
	
	@Override
	public void run() {
		waitOnRun();
		LOG.debug("[" + paramsThreadUpdateCache.nameThread + "]: Start.");
		
		while ( !flagStopThread ) {
			waitRequestUpdateCache();
			if ( flagStopThread ) { break; }
			updateCache();
		}
		
		LOG.debug("[" + paramsThreadUpdateCache.nameThread + "]: Terminated.");
		paramsThreadUpdateCache.latchStop.countDown();
	}
	
	private void waitRequestUpdateCache() {
		synchronized( lock ) {
			while( !flagRequestUpdateCache ) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					LOG.warn("Interrupted waiting request of update Cache!");
				}
			}
			flagRequestUpdateCache = false;
		}
	}
	
	private void waitOnRun() {
		try {
			paramsThreadUpdateCache.latchRun.await();
		} catch (InterruptedException ignore) {
			// NOP
		}
	}
	
	private void updateCache() {
		setFlagProccessUpdateCacheOfCache();
		updateCacheHelper();
		resetFlagProccessUpdateCacheOfCache();		
	}
	
	private void updateCacheHelper() {
		emulateWorkGetDataForCache();
		paramsThreadUpdateCache.cache.update( 
				UtilityTUPC.generateElements4Cache(
						paramsThreadUpdateCache.typeElementCache, 
						paramsThreadUpdateCache.countGenerateElements ) );
	}

	private void setFlagProccessUpdateCacheOfCache() {
		paramsThreadUpdateCache.cache.setFlagProccessUpdateCache();
		LOG.debug("[" + paramsThreadUpdateCache.nameThread + "]: IN PROCCESSING UPDATING CACHE...");
	}
	
	private void resetFlagProccessUpdateCacheOfCache() {
		paramsThreadUpdateCache.cache.resetFlagProccessUpdateCache();
		if ( !flagStopThread ) {
			LOG.debug("[" + paramsThreadUpdateCache.nameThread + "]: COMPLETED UPDATE CACHE.");			
		}
	}
	
	private void emulateWorkGetDataForCache() {
		try {
			TimeUnit.SECONDS.sleep(paramsThreadUpdateCache.timeWaitResponseUpdateCacheInSeconds);
		} catch (InterruptedException ignore) {
			//NOP
		}
	}
}
