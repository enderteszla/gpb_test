package gpb_test.task1;

import gpb_test.common.FileTruncateException;
import gpb_test.common.FileTruncater;
import gpb_test.common.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

class Task1Executor {
	private final static BigDecimal fromAmount = new BigDecimal("10000.12"); // new BigDecimal(10000.12) would lead to a precision loss
	private final static BigDecimal toAmount = new BigDecimal("100000.50"); // redundant property, just to make editing more comfortable
	private final static BigDecimal amountWindow = toAmount.subtract(fromAmount);

	private final Date to;
	private final long millisecondsAYear; // volatile number: 365*24*36*10^5 in a non-leap year, 366*24*36*10^5 in a leap year
	private final String[] merchants;
	private final Random random;

	Task1Executor(String merchantsFile) throws GenerationException {
		Calendar calendar = getToDate();
		to = calendar.getTime();
		calendar.add(Calendar.YEAR, -1);
		Date from = calendar.getTime();
		millisecondsAYear = to.getTime() - from.getTime();
		merchants = getMerchants(merchantsFile);
		random = new Random();
	}

	void generateAndSave(String filePath, Integer count, Integer initialNumber) throws GenerationException, FileTruncateException {
		FileTruncater.truncate(filePath);
		try (FileWriter writer = new FileWriter(filePath)) {
			for (int i = 0; i < count; i ++) {
				writer.write(new Transaction(randomDate(), randomMerchant(), initialNumber + i, randomAmount()).toString());
				writer.write("\n");
			}
		} catch (IOException cause) {
			throw new GenerationException(String.format("Exception when writing to target file %s", filePath));
		}
	}

	private static Calendar getToDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	private String[] getMerchants(String file) throws GenerationException {
		try {
			return Files.lines(Paths.get(file)).toArray(String[]::new);
		} catch (IOException cause) {
			throw new GenerationException(String.format("Exception when trying to open merchants containing source file %s", file));
		}
	}

	private Date randomDate() {
		return new Date(to.getTime() - (long) (random.nextDouble() * millisecondsAYear));
	}

	private BigDecimal randomAmount() {
		return fromAmount.add(new BigDecimal(random.nextDouble()).multiply(amountWindow)).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	private String randomMerchant() {
		return merchants[random.nextInt(merchants.length)];
	}
}
