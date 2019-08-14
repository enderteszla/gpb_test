package gpb_test.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileTruncater {
	public static void truncate(String filePath) throws FileTruncateException {
		Path path = Paths.get(filePath);
		try {
			Files.deleteIfExists(path);
			Files.createFile(path);
		} catch (IOException cause) {
			throw new FileTruncateException(filePath);
		}
	}
}
