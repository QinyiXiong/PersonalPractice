//package httpclientUtil;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.UUID;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.jsoup.helper.StringUtil;
//
//import com.xpsoft.toolkit.sql.JDBResourceKit;
//
//public class TaxUtil {
//	public static String getCurrentTimeNum() {
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//		return df.format(new Date());
//	}
//
//	public static String getCurrentDate() {
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
//		return df.format(new Date());
//	}
//
//	/**
//	 * 生成GUID
//	 *
//	 * @return
//	 */
//	public static String getGUID() {
//		UUID uuid = UUID.randomUUID();
//		return uuid.toString().toUpperCase().replace("-", "");
//	}
//
//	/**
//	 * 校验身份证号是否合法
//	 *
//	 * @param
//	 * @return
//	 */
//	public static boolean isValidityCard(String idCard) {
//		if (idCard == null || "".equals(idCard)) {
//			return false;
//		}
//
//		// 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
//		String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|"
//				+ "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
//		// 假设18位身份证号码:41000119910101123X 410001 19910101 123X
//		// ^开头
//		// [1-9] 第一位1-9中的一个 4
//		// \\d{5} 五位数字 10001（前六位省市县地区）
//		// (18|19|20) 19（现阶段可能取值范围18xx-20xx年）
//		// \\d{2} 91（年份）
//		// ((0[1-9])|(10|11|12)) 01（月份）
//		// (([0-2][1-9])|10|20|30|31)01（日期）
//		// \\d{3} 三位数字 123（第十七位奇数代表男，偶数代表女）
//		// [0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
//		// $结尾
//		// 假设15位身份证号码:410001910101123 410001 910101 123
//		// ^开头 //[1-9] 第一位1-9中的一个 4
//		// \\d{5} 五位数字 10001（前六位省市县地区）
//		// \\d{2} 91（年份）
//		// ((0[1-9])|(10|11|12)) 01（月份）
//		// (([0-2][1-9])|10|20|30|31)01（日期）
//		// \\d{3} 三位数字 123（第十五位奇数代表男，偶数代表女），15位身份证不含X
//		// $结尾
//		boolean matches = idCard.matches(regularExpression);
//
//		// 判断第18位校验值
//		if (matches) {
//			if (idCard.length() == 18) {
//				try {
//					char[] charArray = idCard.toCharArray();
//					// 前十七位加权因子
//					int[] idCardWi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
//					// 这是除以11后，可能产生的11位余数对应的验证码
//					String[] idCardY = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
//					int sum = 0;
//					for (int i = 0; i < idCardWi.length; i++) {
//						int current = Integer.parseInt(String.valueOf(charArray[i]));
//						int count = current * idCardWi[i];
//						sum += count;
//					}
//					char idCardLast = charArray[17];
//					int idCardMod = sum % 11;
//					if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
//						return true;
//					} else {
//						System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() + "错误,正确的应该是:"
//								+ idCardY[idCardMod].toUpperCase());
//						return false;
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					System.out.println("异常:" + idCard);
//					return false;
//				}
//			}
//		}
//		return matches;
//	}
//
//	public static String numberToPercent(Double num) {
//		NumberFormat percentFormat = NumberFormat.getPercentInstance();
//		percentFormat.setMaximumFractionDigits(2); // 最大小数位数
//		return percentFormat.format(num);
//	}
//
//	/**
//	 * 获取某年某月的第一天 @Title:getFisrtDayOfMonth @Description: @param:@param
//	 * year @param:@param month @param:@return @return:String @throws
//	 */
//	public static String getFisrtDayOfMonth(int year, int month, String format) {
//		Calendar cal = Calendar.getInstance();
//		// 设置年份
//		cal.set(Calendar.YEAR, year);
//		// 设置月份
//		cal.set(Calendar.MONTH, month - 1);
//		// 获取某月最小天数
//		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
//		// 设置日历中月份的最小天数
//		cal.set(Calendar.DAY_OF_MONTH, firstDay);
//		// 格式化日期
//		SimpleDateFormat sdf = null;
//		if (StringUtil.isBlank(format)) {
//			sdf = new SimpleDateFormat("yyyy-MM-dd");
//		} else {
//			sdf = new SimpleDateFormat(format);
//		}
//		String firstDayOfMonth = sdf.format(cal.getTime());
//		return firstDayOfMonth;
//	}
//
//	/**
//	 * 获取某月的最后一天 @Title:getLastDayOfMonth @Description: @param:@param
//	 * year @param:@param month @param:@return @return:String @throws
//	 */
//	public static String getLastDayOfMonth(int year, int month, String format) {
//		Calendar cal = Calendar.getInstance();
//		// 设置年份
//		cal.set(Calendar.YEAR, year);
//		// 设置月份
//		cal.set(Calendar.MONTH, month - 1);
//		// 获取某月最大天数
//		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//		// 设置日历中月份的最大天数
//		cal.set(Calendar.DAY_OF_MONTH, lastDay);
//		// 格式化日期
//		SimpleDateFormat sdf = null;
//		if (StringUtil.isBlank(format)) {
//			sdf = new SimpleDateFormat("yyyy-MM-dd");
//		} else {
//			sdf = new SimpleDateFormat(format);
//		}
//		String lastDayOfMonth = sdf.format(cal.getTime());
//		return lastDayOfMonth;
//	}
//
//	/**
//	 * 获取当前月的第一天 20190101
//	 *
//	 * @return
//	 */
//	public static String getFirstDay() {
//		String timeStr = "";
//		Calendar cale = null;
//		cale = Calendar.getInstance();
//		int year = cale.get(Calendar.YEAR);
//		int month = cale.get(Calendar.MONTH) + 1;
//		String monthStr = String.valueOf(month);
//		if (monthStr.length() < 2) {
//			monthStr = "0" + monthStr;
//		}
//		timeStr = String.valueOf(year) + monthStr + "01";
//		return timeStr;
//	}
//
//	/**
//	 * 根据 年、月 获取对应的月份 的 天数
//	 */
//	public static int getDaysByYearMonth(String kjqj) {
//		Calendar a = Calendar.getInstance();
//		int year = Integer.parseInt(kjqj.substring(0, 4));
//		int month = Integer.parseInt(kjqj.substring(4, 6));
//		a.set(Calendar.YEAR, year);
//		a.set(Calendar.MONTH, month - 1);
//		a.set(Calendar.DATE, 1);
//		a.roll(Calendar.DATE, -1);
//		int maxDate = a.get(Calendar.DATE);
//		return maxDate;
//	}
//
//	public static String getFullMonth() {
//		Date date = new Date();
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
//		String time = format.format(date);
//		return time;
//	}
//
//	// 获取字符串中的第一组数值串
//	public static String getNumStr(String str) {
//		Pattern pattern = Pattern.compile("\\d+");
//		Matcher matcher = pattern.matcher(str);
//		String numstr = "";
//		if (matcher.find()) {
//			numstr = matcher.group(0);
//
//		}
//
//		return numstr;
//	}
//
//	/**
//	 * 对象转BigDecimal
//	 *
//	 * @param value
//	 * @return
//	 */
//	public static BigDecimal getBigDecimal(Object value) {
//		BigDecimal ret = null;
//		if (value != null) {
//			if (value instanceof BigDecimal) {
//				ret = (BigDecimal) value;
//			} else if (value instanceof String) {
//				ret = new BigDecimal((String) value);
//			} else if (value instanceof BigInteger) {
//				ret = new BigDecimal((BigInteger) value);
//			} else if (value instanceof Number) {
//				ret = new BigDecimal(((Number) value).doubleValue());
//			} else {
//				throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass()
//						+ " into a BigDecimal.");
//			}
//			return ret;
//		} else {
//			return new BigDecimal(0d);
//		}
//	}
//
//	/**
//	 * 小数点精度
//	 *
//	 * @param x
//	 * @param Number
//	 * @return
//	 */
//	public static double setNumberPrecision(double x, int Number) {
//		String p = "#########0";
//		if (Number == 0) {
//			p = "#########0";
//		} else {
//			p = "#########0.";
//			for (int i = 0; i < Number; i++) {// for的巧妙运用
//				p = p + "0";
//			}
//		}
//		DecimalFormat f = new DecimalFormat(p);
//		double s = Double.parseDouble(f.format(x));
//		return s;
//	}
//
//	/**
//	 * 获取精度
//	 */
//	public static String setScale(Object newScale, int model) {
//		double d = Double.parseDouble(newScale.toString());
//		return String.format("%." + model + "f", d);
//	}
//
//	/**
//	 * 正则表达式判断是否为Double型
//	 *
//	 * @param str
//	 * @return
//	 */
//	public static boolean isDouble(String str) {
//
//		Pattern pattern = Pattern.compile("^[-\\+]?\\d+(\\.\\d*)?|\\.\\d+$");
//
//		return pattern.matcher(str).matches();
//
//	}
//
//	public static String getLast12Months(int i) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//		Calendar c = Calendar.getInstance();
//		c.setTime(new Date());
//		c.add(Calendar.MONTH, -i);
//		Date m = c.getTime();
//		return sdf.format(m);
//	}
//
//	/**
//	 * 时间戳转换成日期格式字符串
//	 *
//	 * @param seconds
//	 *            精确到秒的字符串
//	 * @param formatStr
//	 * @return
//	 */
//	public static String timeStamp2Date(String seconds, String format) {
//		if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
//			return "";
//		}
//		if (format == null || format.isEmpty())
//			format = "yyyy-MM-dd HH:mm:ss";
//		SimpleDateFormat sdf = new SimpleDateFormat(format);
//		return sdf.format(new Date(Long.valueOf(seconds + "000")));
//	}
//
//	/**
//	 * 日期格式字符串转换成时间戳
//	 *
//	 * @param date
//	 *            字符串日期
//	 * @param format
//	 *            如：yyyy-MM-dd HH:mm:ss
//	 * @return
//	 */
//	public static String date2TimeStamp(String date_str, String format) {
//		try {
//			SimpleDateFormat sdf = new SimpleDateFormat(format);
//			return String.valueOf(sdf.parse(date_str).getTime() / 1000);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
//
//	/**
//	 * 获取上一个月
//	 *
//	 * @return
//	 */
//	public static String getLastMonth() {
//		Calendar cal = Calendar.getInstance();
//		cal.add(cal.MONTH, -1);
//		SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
//		String lastMonth = dft.format(cal.getTime());
//		return lastMonth;
//	}
//
//	/**
//	 * 获取当前月
//	 *
//	 * @return
//	 */
//	public static String getCurrentMonth() {
//		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
//		String curMonth = dft.format(cal.getTime());
//		return curMonth;
//	}
//
//	/**
//	 * 修改计提待办列表中的同步字段F_EXT1
//	 *
//	 * @param conn
//	 * @param tbzt
//	 * @param sz
//	 * @param gsdm
//	 * @param kjqj
//	 * @throws Exception
//	 */
//	public static void updateJTDB(Connection conn, String tbzt, String sz, String gsdm, String kjqj) throws Exception {
//		PreparedStatement pstmt = null;
//		StringBuffer sql = new StringBuffer();
//		try {
//			conn.setAutoCommit(false);
//			// 计提待办表里修改为已同步不能再次同步
//			String dwbh = "";
//			if ("1620".equals(gsdm) || "中国石油化工股份有限公司齐鲁分公司".equals(gsdm)) {
//				dwbh = "1001";
//			}
//			if ("5780".equals(gsdm) || "中国石化集团资产经营管理有限公司齐鲁石化分公司".equals(gsdm)) {
//				dwbh = "1002";
//			}
//			sql.setLength(0);
//			sql.append(" update QLSH_REL_JTDB set F_EXT1 = ? where  F_SZ=? and F_GSDM=? and F_KJQJ=? ");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString(1, tbzt);
//			pstmt.setString(2, sz);
//			pstmt.setString(3, dwbh);
//			pstmt.setString(4, kjqj);
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			JDBResourceKit.release(pstmt);
//		}
//
//	}
//
//	/**
//	 * 修改计提待办列表中计提状态
//	 *
//	 * @param conn
//	 * @param jtzt
//	 * @param sz
//	 * @param gsdm
//	 * @param kjqj
//	 * @throws Exception
//	 */
//	public static void updateJTZT(Connection conn, String jtzt, String sz, String gsdm, String kjqj, double se)
//			throws Exception {
//		PreparedStatement pstmt = null;
//		StringBuffer sql = new StringBuffer();
//		try {
//			conn.setAutoCommit(false);
//			// 计提待办表里修改为已同步不能再次同步
//			String dwbh = "";
//			if ("1620".equals(gsdm) || "中国石油化工股份有限公司齐鲁分公司".equals(gsdm)) {
//				dwbh = "1001";
//			}
//			if ("5780".equals(gsdm) || "中国石化集团资产经营管理有限公司齐鲁石化分公司".equals(gsdm)) {
//				dwbh = "1002";
//			}
//			sql.setLength(0);
//			sql.append(" update QLSH_REL_JTDB set F_JTZT = ?,F_SE=? where  F_SZ=? and F_GSDM=? and F_KJQJ=? ");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString(1, jtzt);
//			pstmt.setDouble(2, se);
//			pstmt.setString(3, sz);
//			pstmt.setString(4, dwbh);
//			pstmt.setString(5, kjqj);
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			JDBResourceKit.release(pstmt);
//		}
//
//	}
//
//	public static void getJtdbTbzt(Connection conn, String sz, String gsdm, String kjqj) throws Exception {
//		PreparedStatement pstmt = null;
//		StringBuffer sql = new StringBuffer();
//		try {
//			conn.setAutoCommit(false);
//			// 计提待办表里修改为已同步不能再次同步
//			String dwbh = "";
//			if ("1620".equals(gsdm) || "中国石油化工股份有限公司齐鲁分公司".equals(gsdm)) {
//				dwbh = "1001";
//			}
//			if ("5780".equals(gsdm) || "中国石化集团资产经营管理有限公司齐鲁石化分公司".equals(gsdm)) {
//				dwbh = "1002";
//			}
//			sql.setLength(0);
//			sql.append(" select F_EXT1 from  QLSH_REL_JTDB  where  F_SZ=? and F_GSDM=? and F_KJQJ=? ");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString(1, sz);
//			pstmt.setString(2, dwbh);
//			pstmt.setString(3, kjqj);
//			ResultSet rs = pstmt.executeQuery();
//			if (rs.next()) {
//				String tbzt = rs.getString("F_EXT1");
//				if (!tbzt.equals("0")) {
//					throw new Exception("该税务期间数据已经同步，不予许再次同步！");
//				}
//			} else {
//				throw new Exception("获取计提待办列表数据失败，请联系管理员！");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			JDBResourceKit.release(pstmt);
//		}
//
//	}
//
//	/**
//	 * 根据当前期间获取上一个期间
//	 */
//	public static String getLastQjByQj(String dqqj) {
//		String timeStr = "";
//		int dqqj_year = Integer.parseInt(dqqj.substring(0, 4));
//		int dqqj_mon = Integer.parseInt(dqqj.substring(4, 6));
//		String monStr = "";
//		if (dqqj_mon == 1) {
//			timeStr = String.valueOf(dqqj_year - 1) + "12";
//		} else {
//			if (dqqj_mon < 10) {
//				monStr = "0" + String.valueOf(dqqj_mon - 1);
//			} else {
//				monStr = String.valueOf(dqqj_mon - 1);
//			}
//			timeStr = dqqj.substring(0, 4) + monStr;
//		}
//		return timeStr;
//	}
//
//	/**
//	 * 根据当前期间获取上一个期间  20201月返回202000
//	 */
//	public static String getLastMonth(String dqqj) {
//		String timeStr = "";
//		int dqqj_year = Integer.parseInt(dqqj.substring(0, 4));
//		int dqqj_mon = Integer.parseInt(dqqj.substring(4, 6));
//		String monStr = "";
//
//			if (dqqj_mon < 10) {
//				monStr = "0" + String.valueOf(dqqj_mon - 1);
//			} else {
//				monStr = String.valueOf(dqqj_mon - 1);
//			}
//			timeStr = dqqj.substring(0, 4) + monStr;
//		return timeStr;
//	}
//
//	/**
//	 * 根据当前期间获取上上一年该期间，去年同期月份
//	 */
//	public static String getLastYearByQj(String dqqj) {
//		String timeStr = "";
//		int dqqj_year = Integer.parseInt(dqqj.substring(0, 4));
//		timeStr = String.valueOf(dqqj_year - 1) + dqqj.substring(4, 6);
//		return timeStr;
//	}
//
//	/**
//	 * 根据当前期间获取上上一年最后一个月，去年最后一个月份例如201905 返回201812
//	 */
//	public static String getLastYMByQj(String dqqj) {
//		String timeStr = "";
//		int dqqj_year = Integer.parseInt(dqqj.substring(0, 4));
//		timeStr = String.valueOf(dqqj_year - 1);
//		return timeStr;
//	}
//}
