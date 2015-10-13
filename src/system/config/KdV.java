package system.config;

public class KdV {
	//system.timeTestInMinutes
	public static final String KEY_SYSTEM_TIME_TEST_IN_MINUTES       = "system.timeTestInMinutes";
	public static final String DEF_VALUE_SYSTEM_TIME_TEST_IN_MINUTES = "1";	
	
	//system.cache.timeUpdate.minutes
	public static final String KEY_SYSTEM_CACHE_TIME_UPDATE_MINUTES       = "system.cache.timeUpdate.minutes";
	public static final String DEF_VALUE_SYSTEM_CACHE_TIME_UPDATE_MINUTES = "0";

	//system.cache.timeUpdate.seconds
	public static final String KEY_SYSTEM_CACHE_TIME_UPDATE_SECONDS       = "system.cache.timeUpdate.seconds";
	public static final String DEF_VALUE_SYSTEM_CACHE_TIME_UPDATE_SECONDS = "2";

	//system.cache.timeUpdate.milliseconds
	public static final String KEY_SYSTEM_CACHE_TIME_UPDATE_MILLISECONDS       = "system.cache.timeUpdate.milliseconds";
	public static final String DEF_VALUE_SYSTEM_CACHE_TIME_UPDATE_MILLISECONDS = "500";

	//system.cache.typeElement
	public static final String KEY_SYSTEM_CACHE_TYPE_ELEMENT       = "system.cache.typeElement";
	public static final String DEF_VALUE_SYSTEM_CACHE_TYPE_ELEMENT = "INT";
	
	//system.thread.useCache.count
	public static final String KEY_SYSTEM_THREAD_USE_CACHE_COUNT       = "system.thread.useCache.count";
	public static final String DEF_VALUE_SYSTEM_THREAD_USE_CACHE_COUNT = "10";
	
	//system.thread.useCache.maxTimeSleepBetweenUseCacheInMilliseconds
	public static final String KEY_SYSTEM_THREAD_USE_CACHE_MTSBUCIM       = "system.thread.useCache.maxTimeSleepBetweenUseCacheInMilliseconds";
	public static final String DEF_VALUE_SYSTEM_THREAD_USE_CACHE_MTSBUCIM = "500";	
	
	//system.thread.useCache.percentUseElementCache
	public static final String KEY_SYSTEM_THREAD_USE_CACHE_PERCENT_USE_ELEMENT_CACHE       = "system.thread.useCache.percentUseElementCache";
	public static final String DEF_VALUE_SYSTEM_THREAD_USE_CACHE_PERCENT_USE_ELEMENT_CACHE = "50";		
	
	//system.thread.updateCache.timeWaitResponseInSeconds
	public static final String KEY_SYSTEM_THREAD_UPDATE_CACHE_TWRIS       = "system.thread.updateCache.timeWaitResponseInSeconds";
	public static final String DEF_VALUE_SYSTEM_THREAD_UPDATE_CACHE_TWRIS = "1";
	
	//system.thread.updateCache.countGenerateElements
	public static final String KEY_SYSTEM_THREAD_UPDATE_CACHE_COUNT_GENERATE_ELEMENTS       = "system.thread.updateCache.countGenerateElements";
	public static final String DEF_VALUE_SYSTEM_THREAD_UPDATE_CACHE_COUNT_GENERATE_ELEMENTS = "5";
	
	//system.thread.modifyCache.use
	public static final String KEY_SYSTEM_THREAD_MODIFY_CACHE_USE       = "system.thread.modifyCache.use";
	public static final String DEF_VALUE_SYSTEM_THREAD_MODIFY_CACHE_USE = "FALSE";		
	
	//system.thread.modifyCache.maxTimeSleepBetweenModifyCacheInMilliseconds
	public static final String KEY_SYSTEM_THREAD_MODIFY_CACHE_MTSBMCIM       = "system.thread.modifyCache.maxTimeSleepBetweenModifyCacheInMilliseconds";
	public static final String DEF_VALUE_SYSTEM_THREAD_MODIFY_CACHE_MTSBMCIM = "1500";
	
	//system.thread.modifyCache.percentAddElementToCache
	public static final String KEY_SYSTEM_THREAD_MODIFY_CACHE_PERCENT_ADD_ELEMENT_TO_CACHE       = "system.thread.modifyCache.percentAddElementToCache";
	public static final String DEF_VALUE_SYSTEM_THREAD_MODIFY_CACHE_PERCENT_ADD_ELEMENT_TO_CACHE = "50";		
	
}
