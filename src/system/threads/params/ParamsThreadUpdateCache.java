package system.threads.params;

import java.util.concurrent.CountDownLatch;

import system.cache.Cache;

public class ParamsThreadUpdateCache {
	public String nameThread;
	public Cache cache;
	public String typeElementCache;
	public int countGenerateElements;
	public long timeWaitResponseUpdateCacheInSeconds;
	public CountDownLatch latchRun;
	public CountDownLatch latchStop;
}
