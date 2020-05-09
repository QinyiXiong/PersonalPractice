package influxdb;

import influxdb.bean.LOGINFO;
import influxdb.util.DateUtil;
import influxdb.util.InfluxDBConnection;
import influxdb.util.InfluxDBUtil;
import net.sf.json.JSONObject;
import sun.security.krb5.KrbCryptoException;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: 覃义雄
 * @dateTime: 2020/5/9 10:34 上午
 * @project_Name: personalPractice
 * @Name: influxdbtest
 * @Describe： 测试 influxdb
 */
public class influxdbtest {

    public void insertRobotLog(LOGINFO loginfo) {
        InfluxDBConnection influxDBConnection = null;

        try {

            influxDBConnection = InfluxDBUtil.getConn();
            Map<String, String> tags = new HashMap<String, String>();
            tags.put("F_GUID", UUID.randomUUID().toString());
            tags.put("TDATE", loginfo.getDATE());
            tags.put("TPROCESSNAME", loginfo.getPROCESSNAME());
            tags.put("TMACHINEIP", loginfo.getMACHINEIP());
            Map<String, Object> fields = new HashMap<String, Object>();
            fields.put("DATE", Integer.valueOf(loginfo.getDATE()));
            fields.put("PROCESSNAME", loginfo.getPROCESSNAME());
            fields.put("PROCESSVERSION", loginfo.getPROCESSVERSION());
            fields.put("MACHINEIP", loginfo.getMACHINEIP());
            fields.put("MACHINENAME", loginfo.getMACHINENAME());
            fields.put("LOGLEVEL", loginfo.getLOGLEVEL());
            fields.put("LOGCONTENT", loginfo.getLOGCONTENT());
            fields.put("F_CRDATE", DateUtil.nowDate("yyyy-MM-dd HH:mm:ss"));
            fields.put("F_CHDATE", DateUtil.nowDate("yyyy-MM-dd HH:mm:ss"));
            fields.put("BUSINESS", loginfo.getBUSINESS());

            influxDBConnection.insert("LOG_INFO", tags, fields);
        } catch (KrbCryptoException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        influxdbtest influx = new influxdbtest();
        LOGINFO loginfo = new LOGINFO();
        String slogcontent = "发票代码:12345678;发票号码:87654321;认证状态:已提交认证";
        String sloglevel = "success";

        JSONObject BUSINESS = new JSONObject();
        BUSINESS.put("ExecutionPeriod","202004");
        BUSINESS.put("Completed",999);
        BUSINESS.put("Unfinished",1);
        loginfo.setBUSINESS(BUSINESS.toString());

//        String date1 = "20200501";
//        String date2 = "20200502";
        String date3 = "20200503";
        String date4 = "20200504";
        String date5 = "20200509";
        loginfo.setLOGCONTENT(slogcontent);
        loginfo.setLOGLEVEL(sloglevel);

        String MACHINEIP = "http://localhost:8089/TaxZzs/rest/brobot/invoicecertifybrobot?ID=12345678";
        String PROCESSNAME = "invoicecertifybrobotflow";
        loginfo.setMACHINEIP(MACHINEIP);
        loginfo.setMACHINENAME("测试测试");
        loginfo.setPROCESSNAME(PROCESSNAME);
        loginfo.setPROCESSVERSION("1.0");
        loginfo.setDATE(date5);

        for(int i = 0;i<500;i++){
            influx.insertRobotLog(loginfo);
            System.out.println(i);
        }

        for(int i = 0;i<60;i++){
            loginfo.setLOGLEVEL("error");
            loginfo.setLOGCONTENT("发票认证机器人执行失败，程序异常：虚拟认证异常测试");
            influx.insertRobotLog(loginfo);
            System.out.println(i);
        }

    }

    // 5月1 600 5月2 520 5月9 560
}
