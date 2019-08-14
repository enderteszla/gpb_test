package gpb_test.common;

public class TooFewArgumentsException extends Exception {
	public TooFewArgumentsException(int validCount, int count) {
		super(String.format("Program takes not less than %d arguments, got %d", validCount, count));
	}
}
