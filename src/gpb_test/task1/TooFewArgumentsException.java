package gpb_test.task1;

public class TooFewArgumentsException extends Exception {
	public TooFewArgumentsException(int validCount, int count) {
		super(String.format("Program takes exactly %d arguments, got %d", validCount, count));
	}
}
