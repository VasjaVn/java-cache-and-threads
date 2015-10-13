package main;

import org.apache.log4j.Logger;

import system.SystemTestCache;
import system.utilities.UtilityClass;

public class Main {
	private static final Logger LOG = Logger.getLogger(UtilityClass.getName());

	public static void main(String[] args) {
		SystemTestCache systemTestCache = new SystemTestCache();		
		LOG.debug("................................INITIALIZE_TESTING_CACHE...............................");
		systemTestCache.initialize();
		LOG.debug("..................................START_TESTING_CACHE..................................");
		systemTestCache.start();		
		systemTestCache.timeOutTestingCache();		
		systemTestCache.stop();
		LOG.debug("..................................STOP_TESTING_CACHE..................................");
	}
	
}
