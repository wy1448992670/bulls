package com.goochou.p2b.model.idGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

public class OrderIdGenerator {
	private static final Logger logger = Logger
			.getLogger(OrderIdGenerator.class);

	private String prefix = "";
	private int max = 99999;
	private String date_exp = "yyMMddHHmmsss";

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getDate_exp() {
		return date_exp;
	}

	public void setDate_exp(String date_exp) {
		this.date_exp = date_exp;
	}

	private SimpleDateFormat format = new SimpleDateFormat(date_exp);

	public OrderIdGenerator() {
	}

	public OrderIdGenerator(String prefix, int max, String date_exp) {
		this.prefix = prefix;
		this.max = max;
		this.date_exp = date_exp;
	}

	

	public synchronized String next() {
		String pre = this.prefix;
		String dateStr = this.format.format(new Date());
		String num = pre + dateStr;
		String sequence = new Random().nextInt(max) + 1 + "";
		for (int i = sequence.length(); i < String.valueOf(max+"").length(); i++) {
			sequence = "0" + sequence;
		}
		num += sequence;
		return num;
	}

	public static void main(String[] args) {
		OrderIdGenerator s = new OrderIdGenerator();
		System.out.println(s.next());
	}
}
