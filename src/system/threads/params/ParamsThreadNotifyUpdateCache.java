package system.threads.params;

import java.util.concurrent.CountDownLatch;

public class ParamsThreadNotifyUpdateCache {
	public String nameThread;
	public long timeUpdateCasheInMilliseconds;
	public CountDownLatch latchRun;
	public CountDownLatch latchStop;
}
