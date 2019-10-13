package CreditChinaCrawler.Service;


import CreditChinaCrawler.Bean.sxmdBean;
import cn.hutool.core.util.IdUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;


/**
 * @author: 覃义雄
 * @dateTime: 2019/10/11 16:56
 * @project_Name: PersonalPractice
 * @Name: SxmdListService
 * @Describe：失信名单模拟下载
 */
public class SxmdListService {
    public static final SxmdListService me = new SxmdListService();

    //重点关注名单
    //第一层：https://www.creditchina.gov.cn/api/credit_info_search?objectType=2&page=1&pageSize=10&creditType=4
    //第二次：https://www.creditchina.gov.cn/api/record_param?encryStr=cG45LGlzLGp5eQ==&creditType=4&dataSource=0&pageNum=1&pageSize=10
    //https://www.creditchina.gov.cn/api/credit_info_detail?encryStr=bXJnLHA7dXRiMA%3D%3D%0A

    //失信黑名单：
    //第一层：https://www.creditchina.gov.cn/api/credit_info_search?objectType=2&page=2&pageSize=10&creditType=8
    //第二层：https://www.creditchina.gov.cn/api/record_param?encryStr=bWZncHA7azF2ew==&creditType=8&dataSource=0&pageNum=1&pageSize=10

    private final static String url1 = "https://www.creditchina.gov.cn/api/credit_info_search";
    private final static String url2 = "https://www.creditchina.gov.cn/api/record_param";
    private final static String url3 = "https://www.creditchina.gov.cn/api/credit_info_detail";
    private final static String UrlParam_Page = "page";
    private final static String UrlParam_PageNum = "pageNum";
    private final static String UrlParam_creditType = "creditType";
    private final static String UrlParam_encryStr = "encryStr";
    private final static String UrlParam_objectType = "objectType";
    private final static String UrlParam_dataSource = "dataSource";
    private final static String UrlParam_pageSize = "pageSize";
    private final static String UrlParam_keyword = "keyword";
    private final static String UrlParam__ = "_"; //时间戳

    private final static String[] dataTypeList = {"异常名录","非法集资名单（企业）","失信黑名单-法人","安全生产黑名单","拖欠农民工工资黑名单",""};
    private final static String tableName = "CG_CREDIT_LIST";

    //获取第一层数据
    public void getSxmdList(HashMap<String,Object> paramMap,int page){
        String jsonStr;
        String creditType = paramMap.get(UrlParam_creditType).toString();
        String name = "";
        String idCardOrOrgCode = "";
        String encryStr = "";
        for(int i = 1;i <= page; i++){
            paramMap.put(UrlParam_Page,i);
            jsonStr = HttpUtil.get(url1,paramMap);

            if(!JSONUtil.isJson(jsonStr)){
                continue;
            }

            JSONObject data = JSONUtil.parseObj(jsonStr).getJSONObject("data");

            JSONArray results = data.getJSONArray("results");

            for (Object result : results) {
                JSONObject res = JSONUtil.parseObj(result);

                name = res.getStr("name");
                idCardOrOrgCode = res.getStr("idCardOrOrgCode");

                HashMap<String, Object> paramMap2 = new HashMap<>();
                encryStr = res.get("encryStr").toString().replace("\n", "");
                paramMap2.put(UrlParam_dataSource,0);
                paramMap2.put(UrlParam_encryStr,encryStr);
                paramMap2.put(UrlParam_pageSize,10);
                paramMap2.put(UrlParam_creditType,creditType);
                paramMap2.put(UrlParam_PageNum,1);

                sxmdBean sxdata;
                sxdata = getEnterpriseDetail(encryStr,creditType,name,idCardOrOrgCode);

                //int totalCount = getTotalPageCount2(paramMap2);
                int totalCount = 1;

                getSxmdDetail(paramMap2,totalCount,sxdata);

            }


        }
    }

    public sxmdBean getEnterpriseDetail(String encryStr,String creditType,String name,String idCardOrOrgCode) {

        sxmdBean sxdata = new sxmdBean();
        sxdata.setF_GUID(IdUtil.simpleUUID());
        sxdata.setF_MDLB(creditType);
        sxdata.setF_ZTLX(name);
        sxdata.setF_ZCH(idCardOrOrgCode);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("encryStr",encryStr);
        String jsonStr = "";
        jsonStr = HttpUtil.get(url3,paramMap);

        if(!JSONUtil.isJson(jsonStr)){
            getEnterpriseDetail(encryStr,creditType,name,idCardOrOrgCode);
            return sxdata;
        }

        JSONObject data = JSONUtil.parseObj(jsonStr).getJSONObject("result");
        sxdata.setF_SHXYDM(data.getStr("creditCode"));
        sxdata.setF_SXQYDZ(data.getStr("dom"));

        return sxdata;


    }

    //获取第二层数据
    public void getSxmdDetail(HashMap<String,Object> paramMap,int pageNum,sxmdBean sxdata){
        String jsonStr = "";
        for(int i = 1; i <= pageNum; i++){
            paramMap.put(UrlParam_PageNum,i);
            jsonStr = HttpUtil.get(url2,paramMap);

            if(!JSONUtil.isJson(jsonStr)){
                continue;
            }

            JSONObject data = JSONUtil.parseObj(jsonStr);
            JSONArray result = data.getJSONArray("result");

            for (Object res : result) {
                JSONObject objres = JSONUtil.parseObj(res);
                insertData(objres,sxdata);
            }


        }
    }

    public void insertData(JSONObject data,sxmdBean sxdata){

        String dataType = data.getStr("数据类别");
        sxdata.setF_SJLB(dataType);

        //"异常名录","非法集资名单（企业）","失信黑名单-法人","安全生产黑名单","拖欠农民工工资黑名单",""
        if("异常名录".equals(dataType)){
            insertExceptionlist(data,sxdata); //异常名录
        }else if("非法集资名单（企业）".equals(dataType)){
            insertIllegalEnterpriseJZ(data,sxdata); //非法集资名单（企业）
        }else if("失信黑名单-法人".equals(dataType)){
            insertDishonestBlackListFR(data,sxdata); //失信黑名单-法人
        }else if("安全生产黑名单".equals(dataType)){
            insertSafetyProductBlackList(data,sxdata); //安全生产黑名单
        }else if("拖欠农民工工资黑名单".equals(dataType)){
            insertUnpayWagesBlackList(data,sxdata); //拖欠农民工工资黑名单
        }
        else {
            try {
                doInsertDatabase(sxdata);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void insertUnpayWagesBlackList(JSONObject data, sxmdBean sxdata) {
        sxdata.setF_LRYY(data.getStr("列入名单事由"));
        sxdata.setF_FBSJ(data.getStr("列入日期"));
        sxdata.setF_MDMC(data.getStr("名单名称"));
        sxdata.setF_QYMC(data.getStr("对象名称"));
        sxdata.setF_SJLY(data.getStr("数据来源"));
        sxdata.setF_FRDB(data.getStr("法人代表"));
        sxdata.setF_SJJE(data.getStr("涉及金额"));
        sxdata.setF_PJZCJG(data.getStr("认定部门"));

        try {
            doInsertDatabase(sxdata);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSafetyProductBlackList(JSONObject data, sxmdBean sxdata) {
        sxdata.setF_BZXRMC(data.getStr("主要负责人"));
        sxdata.setF_LRBM(data.getStr("信息报送机关"));
        sxdata.setF_QYMC(data.getStr("单位名称"));
        sxdata.setF_LRYY(data.getStr("失信行为简况"));
        sxdata.setF_XH(data.getStr("序号"));
        sxdata.setF_SJLY(data.getStr("数据来源"));
        sxdata.setF_ZXGXRQ(data.getStr("最新更新日期"));
        sxdata.setF_DYMC(data.getStr("注册地址"));
        sxdata.setF_NRLY(data.getStr("纳入理由"));
        sxdata.setF_SHXYDM(data.getStr("统一社会信用代码"));

        try {
            doInsertDatabase(sxdata);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDishonestBlackListFR(JSONObject data, sxmdBean sxdata) {
        sxdata.setF_FRDB(data.getStr("企业法人姓名"));
        sxdata.setF_PJZCJG(data.getStr("作出执行依据单位"));
        sxdata.setF_FBSJ(data.getStr("发布时间"));
        sxdata.setF_DYMC(data.getStr("地域名称"));
        sxdata.setF_JTQX(data.getStr("失信被执行人具体情形"));
        sxdata.setF_BZXRMC(data.getStr("失信被执行人名称"));
        sxdata.setF_YLXBF(data.getStr("已履行部分"));
        sxdata.setF_ZXYJWH(data.getStr("执行依据文号"));
        sxdata.setF_ZXFY(data.getStr("执行法院"));
        sxdata.setF_SJLY(data.getStr("数据来源"));
        sxdata.setF_ZXGXRQ(data.getStr("最新更新日期"));
        sxdata.setF_WLXBF(data.getStr("未履行部分"));
        sxdata.setF_AH(data.getStr("案号"));
        sxdata.setF_LRYY(data.getStr("法律生效文书确定的义务"));
        sxdata.setF_LASJ(data.getStr("立案时间"));
        sxdata.setF_LXQK(data.getStr("被执行人的履行情况"));

        try {
            doInsertDatabase(sxdata);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insertIllegalEnterpriseJZ(JSONObject data, sxmdBean sxdata) {
        sxdata.setF_RKSJ(data.getStr("入库时间"));
        sxdata.setF_LRBM(data.getStr("列入部门"));
        sxdata.setF_PJZCJG(data.getStr("判决作出机构"));
        sxdata.setF_BZ(data.getStr("备注"));
        sxdata.setF_XH(data.getStr("序号"));
        sxdata.setF_SJLY(data.getStr("数据来源"));
        sxdata.setF_AH(data.getStr("案号"));
        sxdata.setF_FRDB(data.getStr("法定代表人"));
        sxdata.setF_DYMC(data.getStr("省份"));
        sxdata.setF_ZZJGDM(data.getStr("组织机构代码"));
        sxdata.setF_ZM(data.getStr("罪名"));

        try {
            doInsertDatabase(sxdata);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertExceptionlist(JSONObject data,sxmdBean sxdata) {
        sxdata.setF_QYMC(data.getStr("企业名称"));
        sxdata.setF_PJZCJG(data.getStr("列入决定机关名称"));
        sxdata.setF_LRYY(data.getStr("列入经营异常名录原因类型名称"));
        sxdata.setF_SJLY(data.getStr("数据来源"));
        sxdata.setF_ZXGXRQ(data.getStr("最新更新日期"));
        sxdata.setF_FRDB(data.getStr("法定代表人"));
        sxdata.setF_DBRZCH(data.getStr("注册号"));
        sxdata.setF_FBSJ(data.getStr("设立日期"));

        try {
            doInsertDatabase(sxdata);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doInsertDatabase(sxmdBean sxdata) throws SQLException {
        Db.use().insert(
                Entity.create(tableName)
                        .set("F_GUID", sxdata.getF_GUID())
                        .set("F_MDLB",sxdata.getF_MDLB())
                        .set("F_ZTLX", sxdata.getF_ZTLX())
                        .set("F_ZCH",sxdata.getF_ZCH())
                        .set("F_FRDB",sxdata.getF_FRDB())
                        .set("F_LRYY",sxdata.getF_LRYY())
                        .set("F_SJLB",sxdata.getF_SJLB())
                        .set("F_SJLY",sxdata.getF_SJLY())
                        .set("F_XH",sxdata.getF_XH())
                        .set("F_ZZJGDM",sxdata.getF_ZZJGDM())
                        .set("F_ZM",sxdata.getF_ZM())
                        .set("F_AH",sxdata.getF_AH())
                        .set("F_PJZCJG",sxdata.getF_PJZCJG())
                        .set("F_DYMC",sxdata.getF_DYMC())
                        .set("F_LRBM",sxdata.getF_LRBM())
                        .set("F_BZ",sxdata.getF_BZ())
                        .set("F_RKSJ",sxdata.getF_RKSJ())
                        .set("F_ZXYJWH",sxdata.getF_ZXYJWH())
                        .set("F_LXQK",sxdata.getF_LXQK())
                        .set("F_JTQX",sxdata.getF_JTQX())
                        .set("F_FBSJ",sxdata.getF_FBSJ())
                        .set("F_LASJ",sxdata.getF_LASJ())
                        .set("F_YLXBF",sxdata.getF_YLXBF())
                        .set("F_WLXBF",sxdata.getF_WLXBF())
                        .set("F_ZXGXRQ",sxdata.getF_ZXGXRQ())
                        .set("F_SJJE",sxdata.getF_SJJE())
                        .set("F_QYMC",sxdata.getF_QYMC())
                        .set("F_BZXRMC",sxdata.getF_BZXRMC())
                        .set("F_ZXFY",sxdata.getF_ZXFY())
                        .set("F_SHXYDM",sxdata.getF_SHXYDM())
                        .set("F_NRLY",sxdata.getF_NRLY())
                        .set("F_MDMC",sxdata.getF_MDMC())
                        .set("F_SXQYDZ",sxdata.getF_SXQYDZ())
                        .set("F_DBRZCH",sxdata.getF_DBRZCH())
        );
    }

    //获取第一层数据总页数  totalPageCount
    public int getTotalPageCount1(HashMap<String,Object> paramMap){
        int totalPageCount = 1;
        try {
            String jsonStr = HttpUtil.get(url1,paramMap);
            JSONObject data = JSONUtil.parseObj(jsonStr).getJSONObject("data");
            totalPageCount = data.getInt("totalPageCount");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return totalPageCount;
        }

    }

    //获取第二层数据总页数  totalPageCount
    public int getTotalPageCount2(HashMap<String,Object> paramMap){
        int totalPageCount = 1;
        try {
            String jsonStr = HttpUtil.get(url2,paramMap);
            JSONObject data = JSONUtil.parseObj(jsonStr);
            totalPageCount = data.getInt("totalPageCount");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return totalPageCount;
        }

    }

    public static void main(String[] args){
        int totalPageCount1 = 0;
        SxmdListService ss = new SxmdListService();
        List<Integer> creditTypeList = new ArrayList<>();
        creditTypeList.add(4); // 重点关注名单
        creditTypeList.add(8); //失信黑名单
        HashMap<String, Object> paramMap;
        Date date = new Date();
        String timestamp = String.valueOf(date.getTime());
        System.out.println(timestamp);
        for (Integer s : creditTypeList) {
            paramMap = new HashMap<>();
            paramMap.put(UrlParam_creditType,s);
            paramMap.put(UrlParam_keyword,"");
            paramMap.put(UrlParam_objectType,2);
            paramMap.put(UrlParam_pageSize,10);
            paramMap.put(UrlParam_Page,1);
            paramMap.put(UrlParam__,timestamp);
            //totalPageCount1 = ss.getTotalPageCount1(paramMap);
            ss.getSxmdList(paramMap,5);
        }

//        try {
//            Db.use().insert(
//                    Entity.create("CG_CREDIT_LIST")
//                            .set("F_GUID", "unitTestUser")
//                            .set("F_ZTLX", "我爱北京天安门")
//            );
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

}
