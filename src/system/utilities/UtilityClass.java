package system.utilities;

public class UtilityClass {
	public static String getName() {
		try {
			throw new Throwable();
		} catch (Throwable e) {
			return e.getStackTrace()[1].getClassName();
		}
	}
}
