package system.utilities;

public class UtilityGenSTR {
	private static final int countCharactersInString = 3;
	
	public static String generate() {
		StringBuilder builder = new StringBuilder();
		for ( int i = 0; i < countCharactersInString; i++ ) {
			builder.append(UtilityGenCHR.generate());
		}
		return builder.toString();
	}
}
