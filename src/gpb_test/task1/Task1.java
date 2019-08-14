package gpb_test.task1;

import java.util.Arrays;

public class Task1 {
	public static void main(String[] args) throws TooFewArgumentsException, GenerationException {
		if(args.length < 3) {
			throw new TooFewArgumentsException(3, args.length);
		}
		String inputFilePath = args[0];
		int transactionsCount = Integer.parseInt(args[1]);
		String[] fileNames = Arrays.copyOfRange(args, 2, args.length);

		boolean hasRemainder = transactionsCount % fileNames.length > 0;
		int fullFilesCount = hasRemainder ? fileNames.length - 1 : fileNames.length;

		int fileSize = transactionsCount / fullFilesCount;

		Task1Executor executor = new Task1Executor(inputFilePath);
		for (int i = 0; i < fullFilesCount; i ++) {
			executor.generateAndSave(fileNames[i], fileSize, fileSize * i);
		}
		if(hasRemainder) {
			executor.generateAndSave(fileNames[fullFilesCount], transactionsCount - fullFilesCount * fileSize, fileSize * fullFilesCount);
		}
	}
}
