package system.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import system.utilities.UtilityClass;

public class Config {
	private static final Logger LOG = Logger.getLogger(UtilityClass.getName());
	
	private static final String CONFIG_FILE_NAME = "system.conf";
	
	private static Properties properties = new Properties();
	
	public static boolean initialize() {
		boolean isSuccessInitialize = false;
		if ( loadConfigFile() ) {
			readPropertiesFromConfigFile();
			isSuccessInitialize = true;			
		}
		return isSuccessInitialize;
	}
		
	private static boolean loadConfigFile() {
		boolean isSuccessLoading = false;
		
		try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_NAME)) {
			properties.load(fileInputStream);
			isSuccessLoading = true;
			LOG.debug("Config file \"" + CONFIG_FILE_NAME + "\" was loaded.");			
		} catch (FileNotFoundException e) {
			LOG.error("Cann't found config file: " + CONFIG_FILE_NAME + ".", e);
		} catch (IOException e) {
			LOG.error("Cann't load config file: " + CONFIG_FILE_NAME + ".", e);
		}

		return isSuccessLoading;		
	}
	
	private static void readPropertiesFromConfigFile() {
		readPropertyTimeOutTestingCacheInMinutes();
		readPropertyTimeUpdateCacheInMilliseconds();
		readPropertyTypeElementCache();
		readPropertyCountThreadsUseCache();
		readPropertyPercentUseElementCache();
		readPropertyMaxTimeSleepBetweenUseCacheInMilliseconds();
		readPropertyTimeWaitRespUpdateCacheInSeconds();
		readPropertyCountGenerateElements();
		readPropertyFlagUseThreadModifyCache();
		readPropertyPercentAddElementToCache();
		readPropertyMaxTimeSleepBetweenModifyCacheInMilliseconds();
	}
	
	
	private static void readPropertyTimeOutTestingCacheInMinutes() {
		final int defTimeOutTestCacheInMinutes = 1;
		String strTimeOutTestCacheInMinutes = properties.getProperty(KdV.KEY_SYSTEM_TIME_TEST_IN_MINUTES, KdV.DEF_VALUE_SYSTEM_TIME_TEST_IN_MINUTES);
		try {
			Config.System.timeOutTestCacheInMinutes = new Integer(strTimeOutTestCacheInMinutes).intValue();
		} catch (NumberFormatException exc) {
			Config.System.timeOutTestCacheInMinutes = defTimeOutTestCacheInMinutes;
		}
	}

	private static void readPropertyTimeUpdateCacheInMilliseconds() {
		final long defMinutes = 0; 
		long minutes;
		String strMinutes = properties.getProperty(KdV.KEY_SYSTEM_CACHE_TIME_UPDATE_MINUTES, KdV.DEF_VALUE_SYSTEM_CACHE_TIME_UPDATE_MINUTES);		
		try {
			minutes = new Long(strMinutes).longValue();
		} catch (NumberFormatException exc) {
			minutes = defMinutes; 
		}
		
		final long defSeconds = 1;
		long seconds;
		String strSeconds = properties.getProperty(KdV.KEY_SYSTEM_CACHE_TIME_UPDATE_SECONDS, KdV.DEF_VALUE_SYSTEM_CACHE_TIME_UPDATE_SECONDS);
		try {
			seconds = new Long(strSeconds).longValue();
		} catch (NumberFormatException exc) {
			seconds = defSeconds;
		}
		
		final long defMilliseconds = 500;
		long milliseconds;
		String strMilliseconds = properties.getProperty(KdV.KEY_SYSTEM_CACHE_TIME_UPDATE_MILLISECONDS,  KdV.DEF_VALUE_SYSTEM_CACHE_TIME_UPDATE_MILLISECONDS);
		try {
			milliseconds = new Long(strMilliseconds).longValue();
		} catch (NumberFormatException exc) {
			milliseconds = defMilliseconds;
		}
		
		Cache.timeUpdateInMilliseconds = TimeUnit.MINUTES.toMillis(minutes) +
				                         TimeUnit.SECONDS.toMillis(seconds) +
				                         TimeUnit.MILLISECONDS.toMillis(milliseconds);		
	}
	
	private static void readPropertyTypeElementCache() {
		Cache.typeElement = properties.getProperty(KdV.KEY_SYSTEM_CACHE_TYPE_ELEMENT, KdV.DEF_VALUE_SYSTEM_CACHE_TYPE_ELEMENT);
	}
	
	private static void readPropertyCountThreadsUseCache() {
		final int defCountThreadsUseCache = 10;
		String strCountThreadsUseCache = properties.getProperty(KdV.KEY_SYSTEM_THREAD_USE_CACHE_COUNT, KdV.DEF_VALUE_SYSTEM_THREAD_USE_CACHE_COUNT);
		try {
			Thread.countThreadsUseCache = new Integer(strCountThreadsUseCache).intValue();
		} catch (NumberFormatException exc) {
			Thread.countThreadsUseCache = defCountThreadsUseCache;
		} 
	}
	
	private static void readPropertyPercentUseElementCache() {
		final int defPercentUseElementCache = 50;
		String strPercentUseElementCache = properties.getProperty(KdV.KEY_SYSTEM_THREAD_USE_CACHE_PERCENT_USE_ELEMENT_CACHE, KdV.DEF_VALUE_SYSTEM_THREAD_USE_CACHE_PERCENT_USE_ELEMENT_CACHE);
		try {
			Thread.percentUseElementCache = new Integer(strPercentUseElementCache).intValue();
			if ( Thread.percentUseElementCache > 100 || Thread.percentUseElementCache < 0) {
				Thread.percentUseElementCache = defPercentUseElementCache;
				LOG.warn("Property \"" + KdV.KEY_SYSTEM_THREAD_USE_CACHE_PERCENT_USE_ELEMENT_CACHE + "\"=" + strPercentUseElementCache + "% IS NOT BETWEEN 0 AND 100. Was set default value: " + defPercentUseElementCache + "%");
			}
		} catch (NumberFormatException exc) {
			Thread.percentUseElementCache = defPercentUseElementCache;
			LOG.warn("Property \"" + KdV.KEY_SYSTEM_THREAD_USE_CACHE_PERCENT_USE_ELEMENT_CACHE + "\"=" + strPercentUseElementCache + " IS NOT NUMBER. Was set default value: " + defPercentUseElementCache + "%");
		}
	}
	
	private static void readPropertyMaxTimeSleepBetweenUseCacheInMilliseconds() {
		final int defMaxTimeSleepBetweenUseCacheInMilliseconds = 5000;
		String strMaxTimeSleepBetweenUseCacheInMilliseconds = properties.getProperty(KdV.KEY_SYSTEM_THREAD_USE_CACHE_MTSBUCIM, KdV.DEF_VALUE_SYSTEM_THREAD_USE_CACHE_MTSBUCIM);
		try {
			Thread.maxTimeSleepBetweenUseCacheInMilliseconds = new Integer(strMaxTimeSleepBetweenUseCacheInMilliseconds).intValue();
		} catch (NumberFormatException exc) {
			Thread.maxTimeSleepBetweenUseCacheInMilliseconds = defMaxTimeSleepBetweenUseCacheInMilliseconds;
		}
	}
	
	private static void readPropertyTimeWaitRespUpdateCacheInSeconds() {
		final int defTimeWaitRespUpdateCacheInSeconds = 2;
		String strTimeWaitRespUpdateCacheInSeconds = properties.getProperty(KdV.KEY_SYSTEM_THREAD_UPDATE_CACHE_TWRIS, KdV.DEF_VALUE_SYSTEM_THREAD_UPDATE_CACHE_TWRIS);
		try {
			Thread.timeWaitRespUpdateCacheInSeconds = new Integer(strTimeWaitRespUpdateCacheInSeconds).intValue();
		} catch (NumberFormatException exc) {
			Thread.timeWaitRespUpdateCacheInSeconds = defTimeWaitRespUpdateCacheInSeconds;
		}
	}
	
	private static void readPropertyCountGenerateElements() {
		final int defCountGenerateElements = 4;
		String strCountGenerateElements = properties.getProperty(KdV.KEY_SYSTEM_THREAD_UPDATE_CACHE_COUNT_GENERATE_ELEMENTS, KdV.DEF_VALUE_SYSTEM_THREAD_UPDATE_CACHE_COUNT_GENERATE_ELEMENTS);
		try {
			Thread.countGenerateElements = new Integer(strCountGenerateElements).intValue();
		} catch (NumberFormatException exc) {
			Thread.countGenerateElements = defCountGenerateElements;
		}
	}

	private static void readPropertyFlagUseThreadModifyCache() {
		String strFlagUseThreadModifyCache = properties.getProperty(KdV.KEY_SYSTEM_THREAD_MODIFY_CACHE_USE, KdV.DEF_VALUE_SYSTEM_THREAD_MODIFY_CACHE_USE);
		if ("TRUE".equals(strFlagUseThreadModifyCache)) {
			Thread.flagUseThreadModifyCache = true;			
		} else {
			Thread.flagUseThreadModifyCache = false;
		}
	}
	
	private static void readPropertyMaxTimeSleepBetweenModifyCacheInMilliseconds() {
		final int defMaxTimeSleepBetweenModifyCacheInMilliseconds = 3000;
		String strMaxTimeSleepBetweenModifyCacheInMilliseconds = properties.getProperty(KdV.KEY_SYSTEM_THREAD_MODIFY_CACHE_MTSBMCIM, KdV.DEF_VALUE_SYSTEM_THREAD_MODIFY_CACHE_MTSBMCIM);
		try {
			Thread.maxTimeSleepBetweenModifyCacheInMilliseconds = new Integer(strMaxTimeSleepBetweenModifyCacheInMilliseconds).intValue();
		} catch (NumberFormatException exc) {
			Thread.maxTimeSleepBetweenModifyCacheInMilliseconds = defMaxTimeSleepBetweenModifyCacheInMilliseconds;
		}
	}
	
	private static void readPropertyPercentAddElementToCache() {
		final int defPercentAddElementToCache = 50;
		String strPercentAddElementToCache = properties.getProperty(KdV.KEY_SYSTEM_THREAD_MODIFY_CACHE_PERCENT_ADD_ELEMENT_TO_CACHE,  KdV.DEF_VALUE_SYSTEM_THREAD_MODIFY_CACHE_PERCENT_ADD_ELEMENT_TO_CACHE);
		try {
			Thread.percentAddElementToCache = new Integer(strPercentAddElementToCache).intValue();
			if ( Thread.percentAddElementToCache > 100 || Thread.percentAddElementToCache < 0) {
				Thread.percentAddElementToCache = defPercentAddElementToCache;
				LOG.warn("Property \"" + KdV.KEY_SYSTEM_THREAD_MODIFY_CACHE_PERCENT_ADD_ELEMENT_TO_CACHE + "\"=" + strPercentAddElementToCache + "% IS NOT BETWEEN 0 AND 100. Was set default value: " + defPercentAddElementToCache + "%");
			}
		} catch (NumberFormatException exc) {
			Thread.percentAddElementToCache = defPercentAddElementToCache;
			LOG.warn("Property \"" + KdV.KEY_SYSTEM_THREAD_MODIFY_CACHE_PERCENT_ADD_ELEMENT_TO_CACHE + "\"=" + strPercentAddElementToCache + " IS NOT NUMBER. Was set default value: " + defPercentAddElementToCache + "%");
		}
	}
	
	public static final class System {
		private static int timeOutTestCacheInMinutes;
		
		public static int getTimeOutTestCacheInMinutes() {
			return timeOutTestCacheInMinutes;
		}
	}
	
	public static final class Cache {
		private static long timeUpdateInMilliseconds;		
		private static String typeElement;
		
		public static long getTimeUpdateInMilliseconds() {
			return timeUpdateInMilliseconds;
		}
		
		public static String getTypeElement() {
			return typeElement;
		}
	}
	

	public static final class Thread {
		private static int countThreadsUseCache;
		private static int maxTimeSleepBetweenUseCacheInMilliseconds;
		private static int percentUseElementCache;
		
		private static int timeWaitRespUpdateCacheInSeconds;		
		private static int countGenerateElements;
		
		private static boolean flagUseThreadModifyCache;
		private static int maxTimeSleepBetweenModifyCacheInMilliseconds;
		private static int percentAddElementToCache;
		
		public static int getCountThreadsUseCache() {
			return countThreadsUseCache;
		}
		
		public static int getMaxTimeSleepBetweenUseCacheInMilliseconds() {
			return maxTimeSleepBetweenUseCacheInMilliseconds;
		}
		
		public static int getPercentUseElemenCache() {
			return percentUseElementCache;
		}
		
		public static int getTimeWaitRespUpdateCacheInSeconds() {
			return timeWaitRespUpdateCacheInSeconds;
		}
		
		public static int getCountGenerateElements() {
			return countGenerateElements;
		}

		public static boolean getFlagUseThreadModifyCache() {
			return flagUseThreadModifyCache;
		}

		public static int getMaxTimeSleepBetweenModifyCacheInMilliseconds() {
			return maxTimeSleepBetweenModifyCacheInMilliseconds;
		}
		
		public static int getPercentAddElementToCache() {
			return percentAddElementToCache;
		}
	}

}
