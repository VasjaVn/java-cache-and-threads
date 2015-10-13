package system.utilities;

import org.apache.log4j.Logger;

import system.cache.TypeElementCache;
import system.utilities.params.ParamsUtilityTUSC;

public class UtilityTUSC {
	private static final Logger LOG = Logger.getLogger(UtilityClass.getName());
	
	public static void printEmptyCache(ParamsUtilityTUSC paramsUtilityTUSC) {
		LOG.debug("["+ paramsUtilityTUSC.nameThread +"]: " + paramsUtilityTUSC.timeSleepBetweenUseCacheInMilliseconds + " ms. - { cache: () }");
	}
	
	public static void printAllElementsOfCache(ParamsUtilityTUSC paramsUtilityTUSC) {
		
		switch ( paramsUtilityTUSC.typeElementCache ) {
			case TypeElementCache.INT:
				printAllElementsOfCacheINT(paramsUtilityTUSC);
				break;
				
			case TypeElementCache.CHR:
				printAllElementsOfCacheCHR(paramsUtilityTUSC);
				break;
				
			case TypeElementCache.STR:
				printAllElementsOfCacheSTR(paramsUtilityTUSC);
				break;
				
			default:
				printAllElementsOfCacheINT(paramsUtilityTUSC);				
		}
	}
	
	public static void printElementOfCache(ParamsUtilityTUSC paramsUtilityTUSC) {
		
		switch ( paramsUtilityTUSC.typeElementCache ) {
			case TypeElementCache.INT:
				printElementOfCacheINT(paramsUtilityTUSC);
				break;
				
			case TypeElementCache.CHR:
				printElementOfCacheCHR(paramsUtilityTUSC);
				break;
				
			case TypeElementCache.STR:
				printElementOfCacheSTR(paramsUtilityTUSC);
				break;
				
			default:
				printElementOfCacheINT(paramsUtilityTUSC);				
		}
	}

	private static void printAllElementsOfCacheINT(ParamsUtilityTUSC paramsUtilityTUSC) {
		StringBuilder builder = new StringBuilder("[" + paramsUtilityTUSC.nameThread + "]: "+ paramsUtilityTUSC.timeSleepBetweenUseCacheInMilliseconds +" ms. - { cache: (");
		int size = paramsUtilityTUSC.listElements.size();
		int i = 0;
	
		for ( Object element : paramsUtilityTUSC.listElements ) {
			builder.append("" + (Integer)element);
			if ( ++i < size ) {
				builder.append(", ");
			}
		}
		builder.append(")");
		if ( paramsUtilityTUSC.isOldData ) {
			builder.append("*");
		}
		builder.append(" }");
		
		LOG.debug(builder.toString());
	}

	private static void printAllElementsOfCacheCHR(ParamsUtilityTUSC paramsUtilityTUSC) {
		StringBuilder builder = new StringBuilder("[" + paramsUtilityTUSC.nameThread + "]: "+ paramsUtilityTUSC.timeSleepBetweenUseCacheInMilliseconds +" ms. - { cache: (");
		int size = paramsUtilityTUSC.listElements.size();
		int i = 0;
	
		for ( Object element : paramsUtilityTUSC.listElements ) {
			builder.append("\'" + (Character)element + "\'");
			if ( ++i < size ) {
				builder.append(", ");
			}
		}
		builder.append(")");
		if ( paramsUtilityTUSC.isOldData ) {
			builder.append("*");
		}
		builder.append(" }");
		
		LOG.debug(builder.toString());
	}

	private static void printAllElementsOfCacheSTR(ParamsUtilityTUSC paramsUtilityTUSC) {
		StringBuilder builder = new StringBuilder("[" + paramsUtilityTUSC.nameThread + "]: "+ paramsUtilityTUSC.timeSleepBetweenUseCacheInMilliseconds +" ms. - { cache: (");
		int size = paramsUtilityTUSC.listElements.size();
		int i = 0;
	
		for ( Object element : paramsUtilityTUSC.listElements ) {
			builder.append("\"" + (String)element + "\"");
			if ( ++i < size ) {
				builder.append(", ");
			}
		}
		builder.append(")");
		if ( paramsUtilityTUSC.isOldData ) {
			builder.append("*");
		}
		builder.append(" }");
		
		LOG.debug(builder.toString());
	}

	private static void printElementOfCacheINT(ParamsUtilityTUSC paramsUtilityTUSC) {
		StringBuilder builder = new StringBuilder("[" + paramsUtilityTUSC.nameThread + "]: "+ paramsUtilityTUSC.timeSleepBetweenUseCacheInMilliseconds +" ms. - { element = ");
		builder.append((Integer)paramsUtilityTUSC.element);
		if ( paramsUtilityTUSC.isOldData ) {
			builder.append("*");
		}
		builder.append(" }");
		
		LOG.debug(builder.toString());
	}

	private static void printElementOfCacheCHR(ParamsUtilityTUSC paramsUtilityTUSC) {
		StringBuilder builder = new StringBuilder("[" + paramsUtilityTUSC.nameThread + "]: "+ paramsUtilityTUSC.timeSleepBetweenUseCacheInMilliseconds +" ms. - { element = ");
		builder.append("\'" + (Character)paramsUtilityTUSC.element + "\'");
		if ( paramsUtilityTUSC.isOldData ) {
			builder.append("*");
		}
		builder.append(" }");
		
		LOG.debug(builder.toString());
	}

	private static void printElementOfCacheSTR(ParamsUtilityTUSC paramsUtilityTUSC) {
		StringBuilder builder = new StringBuilder("[" + paramsUtilityTUSC.nameThread + "]: "+ paramsUtilityTUSC.timeSleepBetweenUseCacheInMilliseconds +" ms. - { element = ");
		builder.append("\"" + (String)paramsUtilityTUSC.element + "\"");
		if ( paramsUtilityTUSC.isOldData ) {
			builder.append("*");
		}
		builder.append(" }");
		
		LOG.debug(builder.toString());
	}

}
