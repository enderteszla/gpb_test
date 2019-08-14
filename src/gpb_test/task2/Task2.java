package gpb_test.task2;

import gpb_test.common.FileTruncateException;
import gpb_test.common.TooFewArgumentsException;

import java.util.Arrays;

public class Task2 {
	public static void main(String[] args) throws TooFewArgumentsException, FileTruncateException {
		if(args.length < 3) {
			throw new TooFewArgumentsException(3, args.length);
		}
		String outDatesFilePath = args[0];
		String outMerchantsFilePath = args[1];
		String[] inFileNames = Arrays.copyOfRange(args, 2, args.length);

		Task2Executor executor = new Task2Executor();
		for (String inFileName : inFileNames) {
			executor.apply(inFileName);
		}

		executor.save(outDatesFilePath, outMerchantsFilePath);
	}
}
