package gpb_test.common;

public class FileTruncateException extends Exception {
	FileTruncateException(String filePath) {
		super(String.format("Cannot access target file %s: cannot neither delete nor create it, perhaps insufficient permissions", filePath));
	}
}
