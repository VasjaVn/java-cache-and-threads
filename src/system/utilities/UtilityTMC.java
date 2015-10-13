package system.utilities;

import java.util.Random;

import org.apache.log4j.Logger;

import system.cache.Cache;
import system.cache.TypeElementCache;
import system.utilities.params.ParamsUtilityTMC;

enum VariantModifyCache {
	REMOVE,
	ADD
}

public class UtilityTMC {
	private static final Logger LOG = Logger.getLogger(UtilityClass.getName());
	
	private static final Integer ADD_ELEMENT_INT = 999;
	private static final Character ADD_ELEMENT_CHR = '@';
	private static final String ADD_ELEMENT_STR = "@";
	
	public static void modifyCache(ParamsUtilityTMC paramsUtilityTMC) {
		Cache cache = paramsUtilityTMC.cache;
		String typeElementCache = paramsUtilityTMC.typeElementCache;
		int precentAddElementToCache = paramsUtilityTMC.percentAddElementToCache;
		String nameThread = paramsUtilityTMC.nameThread;
		long timeSleepBetweenModifyCacheInMilliseconds = paramsUtilityTMC.timeSleepBetweenModifyCacheInMillisecondds;
		
		switch (variantModifyCache(precentAddElementToCache)) {
			case REMOVE:
				removeElementFromCache(cache);
				LOG.debug("[" + nameThread + "]: " + timeSleepBetweenModifyCacheInMilliseconds + " ms. - REMOVE ELEMENT FROM CACHE.");
				break;
				
			case ADD:
				addElementToCache(cache, typeElementCache);
				LOG.debug("[" + nameThread + "]: " + timeSleepBetweenModifyCacheInMilliseconds + " ms. - ADD ELEMENT TO CACHE.");
				break;
		}
	}
	
	private static VariantModifyCache variantModifyCache(int precentAddElementToCache) {
		VariantModifyCache variantModifyCache = VariantModifyCache.REMOVE;
		
		int randPercent = new Random().nextInt(99) + 1; // 1% .. 100%		
		if (randPercent <= precentAddElementToCache) {
			variantModifyCache = VariantModifyCache.ADD;
		}
		
		return variantModifyCache;
	} 
	
	private static void removeElementFromCache(Cache cache) {
		cache.remove();
	}
	
	private static void addElementToCache(Cache cache, String typeElementCache) {
		switch (typeElementCache) {
			case TypeElementCache.INT:
				cache.add(ADD_ELEMENT_INT);
				break;
			
			case TypeElementCache.CHR:
				cache.add(ADD_ELEMENT_CHR);
				break;
			
			case TypeElementCache.STR:
				cache.add(ADD_ELEMENT_STR);
				break;
			
			default:
				cache.add(ADD_ELEMENT_INT);
		}
	}
}
