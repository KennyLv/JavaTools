package com.java.tools.datetime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeZoneConvert {

	public static void main(String[] args) {
		SimpleDateFormat foo = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println("本地当前时间:" + foo.format(new Date()));

		Calendar gc = GregorianCalendar.getInstance();
		System.out.println("GregorianCalendar 当前时区：" + gc.getTimeZone().getID() + "  ");
		System.out.println("GregorianCalendar 时间:" + gc.getTime());
		System.out.println("GregorianCalendar 时间:" + new Date(gc.getTimeInMillis()));

		// 当前系统默认时区的时间：
		Calendar calendar = new GregorianCalendar();
		System.out.println("本地系统默认时区：" + calendar.getTimeZone().getID() + "  ");
		System.out.println("本地系统默认时间：" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
		// 美国洛杉矶时区
		TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
		// 时区转换
		calendar.setTimeZone(tz);
		System.out.println("----美国洛杉矶时区-------");
		System.out.print("时区：" + calendar.getTimeZone().getID() + "  ");
		System.out.println("时间：" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));

		System.out.println("----UTC: Coordinated Universal Time 世界标准时间-------");
		// 1、取得本地时间：
		Calendar cal = Calendar.getInstance();
		// cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		// 2、取得时间偏移量：
		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		int dstOffset = cal.get(Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		// 之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。
		System.out.println(
				"UTC (时差-" + zoneOffset / 3600000 + "小时;夏令时-" + dstOffset + "):" + new Date(cal.getTimeInMillis()));

		System.out.println("----GMT: Greenwich Mean Time 格林尼治标准时间-------");
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeZone(TimeZone.getTimeZone("GMT"));
		System.out.println("GMT getTime:" + calendar.getTime());
		System.out.println("GMT getTimeInMillis:" + calendar.getTimeInMillis());

		System.out.println("-----------");
		Date t = new Date();
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		System.out.println("Default:" + df1.format(t));
		df1.setTimeZone(TimeZone.getTimeZone("UTC"));
		System.out.println("UTC:" + df1.format(t));
	}
}
