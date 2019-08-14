package gpb_test.task2;

import gpb_test.common.FileTruncateException;
import gpb_test.common.FileTruncater;
import gpb_test.common.Transaction;
import gpb_test.common.WrongTransactionFormatException;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class Task2Executor {
	private final Map<String, BigDecimal> dateStats = new HashMap<>();
	private final Map<String, BigDecimal> merchantStats = new HashMap<>();

	private void mergeDateStat(Map<String, BigDecimal> dateStat) {
		dateStat.forEach((key, value) -> dateStats.put(key, dateStats.getOrDefault(key, BigDecimal.ZERO).add(value)));
	}

	private void mergeMerchantStat(Map<String, BigDecimal> merchantStat) {
		merchantStat.forEach((key, value) -> merchantStats.put(key, merchantStats.getOrDefault(key, BigDecimal.ZERO).add(value)));
	}

	private void write(FileWriter writer, Map.Entry<String, BigDecimal> record) throws ProcessingException {
		try {
			writer.write(record.getKey());
			writer.write(";");
			writer.write(record.getValue().toString());
			writer.write("\n");
		} catch (IOException cause) {
			throw new ProcessingException("Exception when writing to target file");
		}
	}

	void apply(String inFilePath) throws WrongTransactionFormatException, ProcessingException {
		try {
			mergeDateStat(Files.lines(Paths.get(inFilePath)).map(Transaction::parseString)
					.collect(Collectors.groupingBy(Transaction::getDay, Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add))));
			mergeMerchantStat(Files.lines(Paths.get(inFilePath)).map(Transaction::parseString)
					.collect(Collectors.groupingBy(Transaction::getMerchant, Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add))));
		} catch (IOException cause) {
			throw new ProcessingException(String.format("Exception when trying to open input file %s", inFilePath));
		}
	}

	void save(String outDatesFilePath, String outMerchantsFilePath) throws FileTruncateException {
		FileTruncater.truncate(outDatesFilePath);
		FileTruncater.truncate(outMerchantsFilePath);
		try (FileWriter writer = new FileWriter(outDatesFilePath)) {
			dateStats.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(e -> write(writer, e));
		} catch (IOException cause) {
			throw new ProcessingException(String.format("Exception when writing to target file %s", outDatesFilePath));
		}
		try (FileWriter writer = new FileWriter(outMerchantsFilePath)) {
			merchantStats.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).forEach(e -> write(writer, e));
		} catch (IOException cause) {
			throw new ProcessingException(String.format("Exception when writing to target file %s", outMerchantsFilePath));
		}
	}
}
