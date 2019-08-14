package gpb_test.common;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Transaction {
	private final static String SEPARATOR = ";";
	private final static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DAY_FORMAT.toPattern() + SEPARATOR + TIME_FORMAT.toPattern());

	private final Date date;

	private final String merchant;

	private final int number;

	private final BigDecimal amount;

	public Transaction(Date date, String merchant, int number, BigDecimal amount) {
		this.date = date;
		this.merchant = merchant;
		this.number = number;
		this.amount = amount;
	}

	public String getDay() {
		return DAY_FORMAT.format(date);
	}

	public String getTime() {
		return TIME_FORMAT.format(date);
	}

	public Date getDate() {
		return date;
	}

	public String getMerchant() {
		return merchant;
	}

	public Integer getNumber() {
		return number;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return number + SEPARATOR + DATE_FORMAT.format(date) + SEPARATOR + merchant + SEPARATOR + amount;
	}

	public static Transaction parseString(String raw) throws WrongTransactionFormatException {
		String[] pieces = raw.split(SEPARATOR);
		if (pieces.length != 5) {
			throw new WrongTransactionFormatException(raw, "transaction must have exactly 5 properties");
		}

		int number;
		try {
			number = Integer.parseInt(pieces[0]);
		} catch (NumberFormatException cause) {
			throw new WrongTransactionFormatException(raw, "invalid number format");
		}
		Date date;
		try {
			date = DATE_FORMAT.parse(pieces[1] + SEPARATOR + pieces[2]);
		} catch (ParseException cause) {
			throw new WrongTransactionFormatException(raw, "invalid date or time format");
		}
		String merchant = pieces[3];
		BigDecimal amount;
		try {
			amount = new BigDecimal(pieces[4]);
		} catch (NumberFormatException cause) {
			throw new WrongTransactionFormatException(raw, "invalid amount format");
		}
		return new Transaction(date, merchant, number, amount);
	}
}
