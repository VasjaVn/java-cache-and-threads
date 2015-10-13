package system.utilities;

import java.util.Random;

public class UtilityGenCHR {
	private static final String alphabetEnglish = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
	private static final int countCharacters = alphabetEnglish.length();
	
	public static char generate() {
		int indexCharacter = new Random().nextInt(countCharacters);
		return alphabetEnglish.charAt(indexCharacter);
	}
}
