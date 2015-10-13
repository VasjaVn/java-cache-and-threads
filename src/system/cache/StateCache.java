package system.cache;

public class StateCache {
	public SC sc;
	
	public enum SC {
		EMPTY,
		OLD_DATA,
		NEW_DATA,
		FULL
	}
}
