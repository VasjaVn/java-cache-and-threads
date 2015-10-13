package system.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

import system.threads.params.ParamsThreadNotifyUpdateCache;
import system.utilities.UtilityClass;

public class Cache {
	private static final Logger LOG = Logger.getLogger(UtilityClass.getName());
	
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Lock readLock = lock.readLock();
	private Lock writeLock = lock.writeLock();
		
	private String typeElementCache;
	private List<Object> listElements;
	
	private AtomicBoolean flagNeedUpdateCache;
	private AtomicBoolean flagProccessUpdateCache;

	private ParamsThreadNotifyUpdateCache paramsThreadNotifyUpdateCache;
	private ThreadNotifyUpdateCache threadNotifyUpdateCache;

	public Cache(String typeElementCache, ParamsThreadNotifyUpdateCache paramsThreadNotifyUpdateCache) {
		this.typeElementCache = typeElementCache;
		this.paramsThreadNotifyUpdateCache = paramsThreadNotifyUpdateCache;
		
		listElements = new ArrayList<>();
		flagNeedUpdateCache = new AtomicBoolean();
		flagProccessUpdateCache = new AtomicBoolean();

	}

	public void initializeThreadNotifyUpdateCache() {
		threadNotifyUpdateCache = new ThreadNotifyUpdateCache(paramsThreadNotifyUpdateCache);
		threadNotifyUpdateCache.startThreadInSuspendState();
	}
	
	public void unInitializeThreadNotifyUpdateCache() {
		threadNotifyUpdateCache.stopThread();
	}
	
	public List<Object> getElements(StateCache stateCache) {
		try {
			readLock.lock();

			if ( listElements.isEmpty() ) {
				stateCache.sc = StateCache.SC.EMPTY;
			} else if ( flagProccessUpdateCache.get() ) {
				stateCache.sc = StateCache.SC.OLD_DATA;
			} else {
				stateCache.sc = StateCache.SC.NEW_DATA;
			}
			
			return listElements;
		} finally {
			readLock.unlock();
		}
	}
	
	public Object getElement(StateCache stateCache) {
		try {
			readLock.lock();
			if ( listElements.isEmpty() ) {
				stateCache.sc = StateCache.SC.EMPTY;
				return new Object();
			} else if ( flagProccessUpdateCache.get() ) {
				stateCache.sc = StateCache.SC.OLD_DATA;
			} else {
				stateCache.sc = StateCache.SC.NEW_DATA;
			}
			int size = listElements.size();
			return listElements.get(new Random().nextInt(size));
		} finally {
			readLock.unlock();
		}		
	}
	
	public void update(List<Object> newList) {
		try {
			writeLock.lock();
			listElements = new ArrayList<>(newList);			
		} finally {
			writeLock.unlock();
		}
	}
	
	public void remove() {
		try {
			writeLock.lock();
			if ( !listElements.isEmpty() ) {
				int size = listElements.size();
				listElements.remove(new Random().nextInt(size));
			}
		} finally {
			writeLock.unlock();
		}
	}
	
	public void add(Object element) {
		try {
			writeLock.lock();
			
			switch ( typeElementCache ) {
				case TypeElementCache.INT:
					listElements.add((Integer)element);
					break;
					
				case TypeElementCache.CHR:
					listElements.add((Character)element);
					break;

				case TypeElementCache.STR:
					listElements.add((String)element);
					break;
					
				default:						
			}
		} finally {
			writeLock.unlock();
		}
	}

	public boolean isCacheNeedUpdate() {
		return flagNeedUpdateCache.compareAndSet(true, false);
	}
	
	public void setFlagProccessUpdateCache() {
		flagProccessUpdateCache.set(true);
	}
	
	public void resetFlagProccessUpdateCache() {
		flagProccessUpdateCache.set(false);
	}
	
	private void setFlagNeedUpdateCache() {
		flagNeedUpdateCache.set(true);
	}
	
//	private void resetFlagNeedUpdateCache() {
//		flagNeedUpdateCache.set(false);
//	}
	
	private class ThreadNotifyUpdateCache extends Thread {
		private ParamsThreadNotifyUpdateCache paramsThreadNotifyUpdateCache;
		
		private volatile boolean flagStopThread;
		
		public ThreadNotifyUpdateCache(ParamsThreadNotifyUpdateCache paramsThreadNotifyUpdateCache) {
			super(paramsThreadNotifyUpdateCache.nameThread);
			this.paramsThreadNotifyUpdateCache = paramsThreadNotifyUpdateCache;
		}
		
		public void startThreadInSuspendState() {
			start();
			LOG.debug("Thread of notify update cache was started in suspend state.");
		}
		
		public void stopThread() {
			flagStopThread = true;
		}
		
		@Override
		public void run() {
			waitOnRun();
			LOG.debug("[" + getName() + "]: Start.");
			while ( true ) {
				sleepThread();
				if ( flagStopThread ) break;
				setFlagNeedUpdateCache();
			}
			LOG.debug("[" + getName() + "]: Terminated.");
			paramsThreadNotifyUpdateCache.latchStop.countDown();
		}
		
		private void sleepThread() {
			try {
				TimeUnit.MILLISECONDS.sleep(paramsThreadNotifyUpdateCache.timeUpdateCasheInMilliseconds);
			} catch (InterruptedException exc) {
				LOG.warn("Interrupted sleep thread!", exc);
			}			
		}
		
		private void waitOnRun() {
			try {
				paramsThreadNotifyUpdateCache.latchRun.await();
			} catch (InterruptedException ignore) {
				//NOP
			}
		}
	} 
}