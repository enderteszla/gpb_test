package gpb_test.task1;

import gpb_test.common.FileTruncateException;
import gpb_test.common.TooFewArgumentsException;

import java.util.Arrays;

public class Task1 {
	public static void main(String[] args) throws TooFewArgumentsException, GenerationException, FileTruncateException {
		if(args.length < 3) {
			throw new TooFewArgumentsException(3, args.length);
		}
		String inputFilePath = args[0];
		int transactionsCount = Integer.parseInt(args[1]);
		String[] outFileNames = Arrays.copyOfRange(args, 2, args.length);

		boolean hasRemainder = transactionsCount % outFileNames.length > 0;
		int fullFilesCount = hasRemainder ? outFileNames.length - 1 : outFileNames.length;

		int fileSize = transactionsCount / fullFilesCount;

		Task1Executor executor = new Task1Executor(inputFilePath);
		for (int i = 0; i < fullFilesCount; i ++) {
			executor.generateAndSave(outFileNames[i], fileSize, fileSize * i);
		}
		if(hasRemainder) {
			executor.generateAndSave(outFileNames[fullFilesCount], transactionsCount - fullFilesCount * fileSize, fileSize * fullFilesCount);
		}
	}
}
