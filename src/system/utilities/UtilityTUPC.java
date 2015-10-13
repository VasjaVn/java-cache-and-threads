package system.utilities;

import java.util.ArrayList;
import java.util.List;

import system.cache.TypeElementCache;

public class UtilityTUPC {
	public static List<Object> generateElements4Cache(String typeElementCache, int countGenerateElements) {
		List<Object> listResult = null;
		
		switch( typeElementCache ) {
			case TypeElementCache.INT:
				listResult = generateElements4CacheINT(countGenerateElements);
				break;
				
			case TypeElementCache.CHR:
				listResult = generateElements4CacheCHR(countGenerateElements);
				break;
				
			case TypeElementCache.STR:
				listResult = generateElements4CacheSTR(countGenerateElements);
				break;
				
			default:
				listResult = generateElements4CacheINT(countGenerateElements);
		}
		
		return listResult;		
	}
	
	private static List<Object> generateElements4CacheINT(int countGenerateElements) {
		List<Object> listElements = new ArrayList<>(countGenerateElements);
		
		for ( int i = 0; i < countGenerateElements; i++ ) {
			listElements.add((Integer)UtilityGenINT.generate());
		}

		return listElements;		
	}

	private static List<Object> generateElements4CacheCHR(int countGenerateElements) {
		List<Object> listElements = new ArrayList<>(countGenerateElements);
		
		for ( int i = 0; i < countGenerateElements; i++ ) {
			listElements.add((Character)UtilityGenCHR.generate());
		}

		return listElements;		
	}

	private static List<Object> generateElements4CacheSTR(int countGenerateElements) {
		List<Object> listElements = new ArrayList<>(countGenerateElements);
		
		for ( int i = 0; i < countGenerateElements; i++ ) {
			listElements.add((String)UtilityGenSTR.generate());
		}

		return listElements;		
	}
}
