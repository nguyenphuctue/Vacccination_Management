package com.training.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertData {
	
	public static String dateToString(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String stringDate = formatter.format(date);
		return stringDate;
	}
	
	public static Date stringToDate(String stringDate) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = formatter.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		return os.toByteArray();
	}

}
