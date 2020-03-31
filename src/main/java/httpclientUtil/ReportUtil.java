package httpclientUtil;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import net.sf.json.JSONObject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportUtil {
	/**
	 * 获取税务参数
	 * @return
	 */
	public static Map<String,Object>  getCommBean() throws SQLException {
//		List<Record> list = Db.find("SELECT F_CSBH,F_VALUE FROM SW_DCT_SWCS");
		List<Entity> list = Db.use().query("SELECT F_CSBH,F_VALUE FROM SW_DCT_SWCS");
		Map<String,Object> result = new HashMap<String,Object>();
		for(int i=0;i<list.size();i++){
			Entity r = list.get(i);
			result.put(r.getStr("F_CSBH"), r.getStr("F_VALUE"));
		}
		return result;
	}
	/**
	 * 根据公司代码获取税务机关code
	 * @return
	 */
//	public static List<String>  getSwjgByGSDM(String gsdm){
//		String cStr = gsdm=="1001"?"F_SSCODE":"F_CXCODE";
//		List<Record> list = Db.find("SELECT "+cStr+" FROM DWZD_NSQY WHERE DWZD_BH LIKE '"+gsdm+"%' AND DWZD_MX='1'");
//		List<String> rList = new ArrayList<String>();
//		for(int i=0;i<list.size();i++){
//			Record r = list.get(i);
//			rList.add(r.getStr(cStr));
//		}
//		return rList;
//	}
//	/**
//	 * 根据公司代码获取税务机关code方法改进
//	 * 该方法仅适用于接口的m_zgswjg参数赋值
//	 * 直接把
//	 * @return
//	 */
	public static JSONObject getSwjgByGSDMGJ(String gsdm) throws SQLException {


		Entity sjr = Db.use().queryOne(
				"SELECT NVL(F_SJCODE,' ') F_SJCODE,F_NSRMC,F_SJNSJGMC,F_SJTYPE,F_ZCDZ FROM SW_XZQH WHERE F_XZQHBM IN (SELECT F_XZQHBM FROM DWZD_NSQY where DWZD_BH='"
						+ gsdm + "')");
		JSONObject swjgData = new JSONObject();
		swjgData.put("m_id",sjr.getStr("F_SJCODE"));
		swjgData.put("m_NaShuiRen_Name",sjr.getStr("F_NSRMC"));
		swjgData.put("m_ZhuGuan_ShuiWu_JiGuan",sjr.getStr("F_SJNSJGMC"));
		swjgData.put("m_NaShuiRen_State","");

		swjgData.put("m_KeZheng_ZhuTi_DengJi_LeiXing",sjr.getStr("F_SJTYPE"));
		swjgData.put("m_ZhuCe_DiZhi",sjr.getStr("F_ZCDZ"));

		return swjgData;
	}
//	/**
//	 * 获取税率数目
//	 * @return
//	 */
//	public static Map<String,Double>  getSLCollection(String tax){
//		List<Record> list = Db.find("select F_KEY,F_SL from QLSH_DCT_SLSM WHERE F_SZ='"+tax+"'");
//		Map<String,Double> result = new HashMap<String,Double>();
//		for(int i=0;i<list.size();i++){
//			Record r = list.get(i);
//			result.put(r.getStr("F_KEY"), r.getBigDecimal("F_SL")==null?0.0d:r.getBigDecimal("F_SL").doubleValue());
//		}
//		return result;
//	}
//
//	/**
//	 * 获取税种申报状态
//	 * @param tax:XFS消费税;YHS印花税;SZY水资源税;FCS房产税;TDS土地税
//	 * @param dwbh  1001上市  1002存续
//	 * @param swqj 税务期间
//	 * @return true 已冻结不能取消计提   false 未冻结
//	 */
//	public static boolean getSBFreezd(String tax, String dwbh, String swqj){
//		String bbrq = null;
//		if("SZY".equals(tax)||"FCS".equals(tax)||"TDS".equals(tax)){ //季度申报
//			int year = Integer.parseInt(swqj.substring(0, 4));
//			int mon = Integer.parseInt(swqj.substring(4, 6));
//			int smon = mon - (mon-1)%3;
//			if(smon==10){
//				bbrq = year+""+smon;
//			}else{
//				bbrq = year+"0"+smon;
//			}
//		}else{
//			bbrq = swqj;
//		}
//
//		List<Record> lr = Db.find("SELECT BB_SBZT FROM RPT_BBSCRZ WHERE DWZD_BH LIKE '"+dwbh+"%' AND BBZD_BH LIKE '"+tax+"%' AND BBZD_DATE LIKE '"+bbrq+"%' AND BB_VER='1.0'");
//		for(int i=0; i<lr.size(); i++){
//			Record r = lr.get(i);
//			String sbzt = r.getStr("BB_SBZT");
//			if("3".equals(sbzt)||"4".equals(sbzt)){ //如果该税种有存在当前申报中的报表 则冻结
//				return true;
//			}
//		}
//
//		return false;
//	}
	
	
	
	
}
