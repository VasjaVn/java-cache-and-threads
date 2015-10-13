package system.threads.params;

import java.util.concurrent.CountDownLatch;

import system.cache.Cache;

public class ParamsThreadModifyCache {
	public String nameThread;
	public CountDownLatch latchRun;
	public CountDownLatch latchStop;
	public Cache cache;
	public String typeElementCache;
	public int maxTimeSleepBetweenModifyCacheInMilliseconds;
	public int percentAddElementToCache;
}
