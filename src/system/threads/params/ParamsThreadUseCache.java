package system.threads.params;

import java.util.concurrent.CountDownLatch;

import system.cache.Cache;
import system.threads.ThreadUpdateCache;

public class ParamsThreadUseCache {
	public String typeElementCache;
	public Cache cache;
	public ThreadUpdateCache threadUpdateCache;	
	public CountDownLatch latchRun;
	public CountDownLatch latchStop;
	public CountDownLatch latchStopPool;
	public int maxTimeSleepBetweenUseCacheInMilliseconds;	
	public int percentUseElementOfCache;
}
