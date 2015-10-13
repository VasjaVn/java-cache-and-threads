package system.utilities;

import java.util.Random;

public class UtilityGenINT {
	private static final int boundInteger = 1000;

	public static int generate() {
		return new Random().nextInt(boundInteger);
	}
}
