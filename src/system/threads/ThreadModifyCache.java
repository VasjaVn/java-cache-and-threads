package system.threads;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import system.threads.params.ParamsThreadModifyCache;
import system.utilities.UtilityClass;
import system.utilities.UtilityTMC;
import system.utilities.params.ParamsUtilityTMC;

public class ThreadModifyCache extends Thread {
	private static final Logger LOG = Logger.getLogger(UtilityClass.getName());
	
	private volatile boolean flagStopThread;
	
	private ParamsThreadModifyCache paramsThreadModifyCache;
	private long timeSleepBetweenModifyCacheInMilliseconds;

	private ParamsUtilityTMC paramsUtilityTMC = new ParamsUtilityTMC();

	public ThreadModifyCache(ParamsThreadModifyCache paramsThreadModifyCache) {
		super(paramsThreadModifyCache.nameThread);
		this.paramsThreadModifyCache = paramsThreadModifyCache;
		paramsUtilityTMC.nameThread = paramsThreadModifyCache.nameThread;
		paramsUtilityTMC.cache = paramsThreadModifyCache.cache;
		paramsUtilityTMC.typeElementCache = paramsThreadModifyCache.typeElementCache;
		paramsUtilityTMC.percentAddElementToCache = paramsThreadModifyCache.percentAddElementToCache;
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
		LOG.debug("["+ getName() +"]: Start.");		
		while ( true ) {
			sleepThread();
			if ( flagStopThread ) break;
			modifyCache();
		}
		LOG.debug("["+ getName() +"]: Terminated.");
		paramsThreadModifyCache.latchStop.countDown();
	}

	private void waitOnRun() {
		try {
			paramsThreadModifyCache.latchRun.await();
		} catch (InterruptedException ignore) {
			// NOP
		}
	}
	
	private void sleepThread() {
		int maxTimeSleepBetweenModifyCacheInMilliseconds = paramsThreadModifyCache.maxTimeSleepBetweenModifyCacheInMilliseconds;
		timeSleepBetweenModifyCacheInMilliseconds = (long)new Random().nextInt(maxTimeSleepBetweenModifyCacheInMilliseconds);
		try {
			TimeUnit.MILLISECONDS.sleep(timeSleepBetweenModifyCacheInMilliseconds);
		} catch (InterruptedException ignore) {
			//NOP
		}
	}

	private void modifyCache() {
		paramsUtilityTMC.timeSleepBetweenModifyCacheInMillisecondds = timeSleepBetweenModifyCacheInMilliseconds;  
		UtilityTMC.modifyCache(paramsUtilityTMC);
	}
}
