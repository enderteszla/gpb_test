package gpb_test.common;

public class WrongTransactionFormatException extends Exception {
	public WrongTransactionFormatException(String raw, String details) {
		super(String.format("Wrong transaction format (%s): %s", details, raw));
	}
}
