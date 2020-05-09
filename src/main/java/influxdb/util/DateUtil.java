package influxdb.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author
 *
 */
public class DateUtil {
	/**
	 * 获取当前日期
	 * 
	 * @param format 日期格式 例：yyyyMMdd
	 * @return
	 */
	public static String nowDate(String format) {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return date.format(formatter);
	}

	/**
	 * @description:获取本周的开始时间
	 * @param pattern 时间格式
	 * @return 本周的开始时间
	 * @author:
	 * @createTime:2020年4月29日 下午4:28:12
	 */
	public static String getWeekStart(String pattern)
	{
		// 当周开始时间
		Calendar currentDate = Calendar.getInstance();
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		if (pattern == null || pattern.equals(""))
		{
			pattern = "yyyyMMdd";
		}

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(currentDate.getTime());
	}

	/**
	 * @description:获取当前月第一天
	 * @param pattern 时间格式
	 * @return 当前月第一天
	 * @author:
	 * @createTime:2020年4月29日 下午4:28:12
	 */
	public static String getMonthStart(String pattern)
	{
		//获取当前月第一天
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.MONTH, 0);
		currentDate.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天

		if (pattern == null || pattern.equals(""))
		{
			pattern = "yyyyMMdd";
		}

		SimpleDateFormat format = new SimpleDateFormat(pattern);

		return format.format(currentDate.getTime());
	}

	/**
	 * 获取当前天
	 */
	public static String getCurrentDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}

	/**
	 * 获取n天前的日期
	 * 30,"yyyyMMdd"
	 * @return
	 */
	public static String getDateBefore(int n,String format){
		String dateStr = "";
		SimpleDateFormat sdf1 = new SimpleDateFormat(format);
		Calendar calc =Calendar.getInstance();
		calc.setTime(new Date());
		calc.add(calc.DATE, -n);
		Date minDate = calc.getTime();
		dateStr = sdf1.format(minDate);

		return dateStr;
	}

}
