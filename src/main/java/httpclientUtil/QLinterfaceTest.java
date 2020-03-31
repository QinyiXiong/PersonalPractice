package httpclientUtil;


import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: pe_qyx
 * @dateTime: 2020/3/30 4:30 下午
 * @project_Name: PersonalPractice
 * @Name: QLinterfaceTest
 */
public class QLinterfaceTest {

    private List<JSONObject> createHeadRequsetInfo(int m_Table_Type_Name) throws SQLException {
        List<JSONObject> reList = new ArrayList<JSONObject>();
        String curDate = TaxUtil.getCurrentTimeNum().substring(0, 8);
        StringBuffer sb = new StringBuffer();
        sb.append(
                "SELECT * FROM (SELECT F_JC,F_SJCODE,DWZD_BH FROM SW_XZQH S INNER JOIN DWZD_NSQY N ON S.F_XZQHBM=N.F_XZQHBM where dwzd_bh like '10020%' ");
        sb.append(" union ");
        sb.append(
                "SELECT F_JC,F_SJCODE,DWZD_BH FROM SW_XZQH S INNER JOIN DWZD_NSQY N ON S.F_XZQHBM=N.F_XZQHBM where dwzd_bh='100101')");
        sb.append(
                " WHERE DWZD_BH NOT IN (SELECT DWZD_BH FROM QLSH_MODEL_SJTBLOG where F_TYPE='FCCX'  AND  F_STATE=1  AND to_char(F_CRDATE+0,'yyyymmdd')='");
        sb.append(curDate);
        sb.append("' )");

        List<Entity> list = Db.use().query(sb.toString());
//        List<Record> list = Db.find(sb.toString());

        Map<String, Object> commData = ReportUtil.getCommBean();
        for (int i = 0; i < list.size(); i++) {
            Entity r = list.get(i);
            JSONObject jo = new JSONObject();
            String DWZD_BH = r.getStr("DWZD_BH");
            String dwbh = DWZD_BH.substring(0, DWZD_BH.length() - 2);
            JSONObject comData = new JSONObject();
            if ("100101".equals(DWZD_BH)) { // 上市
                comData.put("m_Tax_account", (String) commData.get("SS_NSRSBH"));
                comData.put("m_password", (String) commData.get("SS_NSRPWD"));
                comData.put("m_zgswjg", "");

                jo.put("m_Company_Info", comData);
                jo.put("F_SWJGMC", r.getStr("F_JC"));
                jo.put("DWZD_BH", r.getStr("DWZD_BH"));
            }else if ("100102".equals(DWZD_BH)) {// 上市-张店腈纶厂
                comData.put("m_Tax_account", (String) commData.get("SS2_NSRSBH"));
                comData.put("m_password", (String) commData.get("SS2_NSRPWD"));
                comData.put("m_zgswjg", "");
                jo.put("m_Company_Info", comData);

                jo.put("F_SWJGMC", r.getStr("F_JC"));
                jo.put("DWZD_BH", r.getStr("DWZD_BH"));
            } else if ("1002".equals(dwbh)) {// 存续
                comData.put("m_Tax_account", (String) commData.get("CX_NSRSBH"));
                comData.put("m_password", (String) commData.get("CX_NSRPWD"));
                // 覃
                JSONObject zgswjg = ReportUtil.getSwjgByGSDMGJ(r.getStr("DWZD_BH"));
                comData.put("m_ZhuGuan_ShuiWu_JiGuan_Info", zgswjg);
                comData.put("m_zgswjg", r.getStr("F_SJCODE"));

                jo.put("m_Company_Info", comData);
                jo.put("F_SWJGMC", r.getStr("F_JC"));
                jo.put("DWZD_BH", r.getStr("DWZD_BH"));
            }
            // 公共部分
            // head
            JSONObject head = new JSONObject();
            Date date = new Date();
            Long tl = date.getTime();
            head.put("m_HTTP_Msg_Type", 3); //有疑问 查询接口是1还是3 覃
            head.put("m_TimeChuo", tl);
            head.put("m_md5", null);
            jo.put("m_Http_Msg_Head", head);
            // 其他
            jo.put("m_Taxation_Type", 14);
            jo.put("m_Table_Type_Name", m_Table_Type_Name);

//			jo.put("m_TuDiBianHao", null);
//			jo.put("m_TuDiShiYongZhengHa", null);
//			jo.put("m_TuDiName", null);
//			jo.put("m_DiHao", null);
//			jo.put("m_SuoShuZhuGuan_ShuiWuSuo", null);
            reList.add(jo);
        }
        return reList;
    }

    @Test
    public void sendA(int m_Table_Type_Name,String Aurl) throws SQLException, InterruptedException {
        List<JSONObject> jList = createHeadRequsetInfo(m_Table_Type_Name); // 去掉了原来的qtgsdm

        for (int i = 0; i < jList.size(); i++) {
            JSONObject params = jList.get(i);
            System.out.println(params.toString());
//			String result = HttpRequestUtil.httpPostJson(HXConstant.HX_URL, params.toString());
            // http://60.210.111.138:8050/taxService/sendData/FangChanTuDi/Caiji/Select/CaiJiMainInfo
            String reurl = "http://60.210.111.138:8050/" + Aurl;
            String result = HttpRequestUtil.httpPostJson(reurl, params.toString());
            System.out.println(result);
            JSONObject rJson = JSONObject.fromObject(result);
            String code = rJson.getString("state");
            String md5Str = rJson.getString("value");

            if (code.length() == 0 || md5Str.length() == 0) {
                System.out.println("呵呵，接口为空。你被耍了～");
            }else{
                sendB(md5Str,10,m_Table_Type_Name);
            }
            Thread.sleep(3000);

        }
    }

    @Test
    public void sendB(String md5Str, int num, int typeName){
        if (num != 0) {

            Date date = new Date();
            long timeChuo = date.getTime();

            JSONObject params = new JSONObject();
            // params.put("m_QiYe_Name", 0);

            JSONObject headData = new JSONObject();
            headData.put("m_HTTP_Msg_Type", 2);// 获取结果，用2
            headData.put("m_TimeChuo", timeChuo);
            headData.put("m_md5", md5Str);
            params.put("m_Http_Msg_Head", headData);
            // params.put("m_Table_Type_Name", null);
            params.put("m_Table_Type_Name", typeName);
            try {
                Thread.sleep(1000); // 先等待相应1秒

                System.out.println("发送B消息："+params);

		        String reurl = "http://60.210.111.138:8050/taxService/sendData/FangChanTuDi/Caiji/Select/TuDi/WeiHuYingShui";
//				String reurl = HXConstant.HX_URL + "sendData/FangChanTuDi/Caiji/Select/CaiJiMainInfo";
                String result = HttpRequestUtil.httpPostJson("http://60.210.111.138:8050/taxService/", params.toString());
//				System.out.println("根据md5,获取的详细信息" + result);
                JSONObject rJson = JSONObject.fromObject(result);
                String state = rJson.getString("state");

                if ("1".equals(state)) {
                    String str = rJson.getString("value");
                    // str.replace("\r\n", "").replace("\"", "");
                    str.replace("\r\n", "").replace("\\", "");
                    // String reanlis = str.replace("\r\n", "").replace("\\",
                    // "").replace("[", "").replace("]", "");
                    System.out.println(str);
                    JSONObject DATA =  JSONObject.fromObject(str);
                    System.out.println(DATA);
                } else {
                    Thread.sleep(10000);
                    sendB(md5Str, --num, typeName);
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
        }
    }

    public static void main(String[] args) throws SQLException, InterruptedException {
        QLinterfaceTest ql = new QLinterfaceTest();
//        ql.sendA(100,"taxService/sendData/FangChanTuDi/Caiji/Select/CaiJiMainInfo");
        // 5.9.1房产土地申报 往期未申报的数据查询
        ql.sendA(90,"taxService/sendData/FangChanTuDi/ShenBao/Select/WangQiWeiShenBao");
        // 5.9.2 城镇土地使用税和房产税的纳税申报表 和 减免税明细申报表 查询
        ql.sendA(91,"taxService/sendData/FangChanTuDi/ShenBao/Select/TdFcNaShuiShenBaoAndJianMianMingXi");

        // 5.9.3 房产土地确认申报 （可不敢随便）
//        ql.sendA(92,"taxService/sendData/FangChanTuDi/ShenBao/QueRenShenBao");
    }
}
