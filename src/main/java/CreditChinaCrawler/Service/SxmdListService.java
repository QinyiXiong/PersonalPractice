package CreditChinaCrawler.Service;


import CreditChinaCrawler.Bean.sxmdDetailBean;
import CreditChinaCrawler.Bean.sxmdListBean;
import cn.hutool.core.util.IdUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
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

    //https://www.creditchina.gov.cn/api/pub_permissions_name?name=%E5%AE%89%E5%BA%86%E4%B8%87%E5%8D%8E%E6%B2%B9%E5%93%81%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&page=1&pageSize=10
    //https://www.creditchina.gov.cn/api/pub_penalty_name?name=%E5%AE%89%E5%BA%86%E4%B8%87%E5%8D%8E%E6%B2%B9%E5%93%81%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&page=1&pageSize=10

    //重点关注名单
    //第一层：https://www.creditchina.gov.cn/api/credit_info_search?objectType=2&page=1&pageSize=10&creditType=4
    //第二次：https://www.creditchina.gov.cn/api/record_param?encryStr=cG45LGlzLGp5eQ==&creditType=4&dataSource=0&pageNum=1&pageSize=10
    //https://www.creditchina.gov.cn/api/credit_info_detail?encryStr=bXJnLHA7dXRiMA%3D%3D%0A


    //失信黑名单：
    //第一层：https://www.creditchina.gov.cn/api/credit_info_search?objectType=2&page=2&pageSize=10&creditType=8
    //第二层：https://www.creditchina.gov.cn/api/record_param?encryStr=bWZncHA7azF2ew==&creditType=8&dataSource=0&pageNum=1&pageSize=10

    private final static String url1 = "https://www.creditchina.gov.cn/api/credit_info_search"; //第一层
    private final static String url2 = "https://www.creditchina.gov.cn/api/record_param"; //第二层 黑名单，红名单，重点关注名单
    private final static String url3 = "https://www.creditchina.gov.cn/api/credit_info_detail"; //第二层detail
    private final static String url4 = "https://www.creditchina.gov.cn/api/pub_permissions_name"; //第二层 行政许可
    private final static String url5 = "https://www.creditchina.gov.cn/api/pub_penalty_name"; //第二层 行政处罚

    private final static String UrlParam_Page = "page";
    private final static String UrlParam_PageNum = "pageNum";
    private final static String UrlParam_creditType = "creditType";
    private final static String UrlParam_encryStr = "encryStr";
    private final static String UrlParam_objectType = "objectType";
    private final static String UrlParam_dataSource = "dataSource";
    private final static String UrlParam_pageSize = "pageSize";
    private final static String UrlParam_keyword = "keyword";
    private final static String UrlParam__ = "_"; //时间戳
    private final static String UrlParam_name = "name";

    private final static String[] dataTypeList = {"异常名录","非法集资名单（企业）","失信黑名单-法人","安全生产黑名单","拖欠农民工工资黑名单",""};
    private final static String[] mdTypeList = {"2","4","8"};
    private final static String tableName1 = "CG_CREDIT_LIST";
    private final static String tableName2 = "CG_CREDIT_DETAIL";
    private final static String tableName3 = "CG_CREDIT_LIST_HISTORY";
    private final static String tableName4 = "CG_CREDIT_DETAIL_HISTORY";

    private final static String F_FROM = "信用中国";

    private final static String user_Agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36";
    private final static String Accept = "application/json, text/javascript, */*; q=0.01";
    private final static String Content_Type ="application/json";

    //获取第一层数据
    public void getSxmdList(HashMap<String,Object> paramMap,int page){
        String jsonStr;
//        String creditType = paramMap.get(UrlParam_creditType).toString();
        String name = "";
        String idCardOrOrgCode = "";
        String encryStr = "";
        try {
            for(int i = 1;i <= page; i++){
                paramMap.put(UrlParam_Page,i);
                //jsonStr = HttpUtil.get(url1,paramMap);
                Thread.sleep(1000);
                jsonStr = HttpGet(url1,paramMap);

                JSONObject data = JSONUtil.parseObj(jsonStr).getJSONObject("data");

                JSONArray results = data.getJSONArray("results");

                for (Object result : results) {
                    JSONObject res = JSONUtil.parseObj(result);

                    name = res.getStr("name");
                    idCardOrOrgCode = res.getStr("idCardOrOrgCode");

                    HashMap<String, Object> paramMap2 = new HashMap<String, Object>();
                    //encryStr = res.get("encryStr").toString().replace("\n", "");
                    encryStr = res.getStr("encryStr");

                    paramMap2.put(UrlParam_dataSource,0);
                    paramMap2.put(UrlParam_encryStr,encryStr);
                    paramMap2.put(UrlParam_pageSize,10);
                    paramMap2.put(UrlParam_PageNum,1);

                    String F_GUID = IdUtil.simpleUUID();
                    sxmdDetailBean detailBean = new sxmdDetailBean();
                    detailBean.setF_FROM(F_FROM);
                    detailBean.setF_ZTLX(name);
                    detailBean.setF_ZZJGDM(idCardOrOrgCode);
                    detailBean.setF_GUID(F_GUID);

                    getEnterpriseDetail(encryStr,F_GUID); //获取信息概览

                    HashMap<String, Object> paramMap3 = new HashMap<String, Object>();
                    paramMap3.put(UrlParam_name,name);
                    paramMap3.put(UrlParam_Page,1);
                    paramMap3.put(UrlParam_pageSize,10);

                    int licenseCount = getXZLicense(F_GUID,paramMap3,getTotalPageCount3(paramMap3,url4)); //获取行政许可
                    detailBean.setF_XZXK(String.valueOf(licenseCount));

                    int punishCount = getXZPunish(F_GUID,paramMap3,getTotalPageCount3(paramMap3,url5)); //获取行政处罚
                    detailBean.setF_XZCF(String.valueOf(punishCount));

                    for (String s : mdTypeList) {
                        paramMap2.put(UrlParam_creditType,s);
                        int totalCount = getTotalPageCount2(paramMap2); //获取数据总页数
                        int mdCount = getSxmdList(paramMap2,totalCount,F_GUID); //获取红名单，黑名单，重点关注名单

                        if(s.equals("2")){ //守信红名单
                            detailBean.setF_SXHMD(String.valueOf(mdCount));
                        }else if(s.equals("4")){ //重点关注名单
                            detailBean.setF_ZDGZMD(String.valueOf(mdCount));
                        }else if(s.equals("8")){ //黑名单
                            detailBean.setF_HMD(String.valueOf(mdCount));
                        }

                    }

                    try {
                        doInsertCreditDetail(detailBean);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getXZPunish(String f_guid,HashMap<String,Object> paramMap,int pageNum) {
        String jsonStr = "";
        int totalCount = 0;
        for(int i = 1; i <= pageNum; i++){
            paramMap.put(UrlParam_Page,i);
            //jsonStr = HttpUtil.get(url2,paramMap);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jsonStr = HttpGet(url5,paramMap);

            JSONObject data = JSONUtil.parseObj(jsonStr).getJSONObject("result");
            JSONArray result = data.getJSONArray("results");

            totalCount = data.getInt("totalCount");

            for (Object res : result) {
                JSONObject objres = JSONUtil.parseObj(res);
                sxmdListBean sxdata = new sxmdListBean();
                sxdata.setF_GUID(f_guid);
                sxdata.setF_MDLB("3");
                sxdata.setF_SJLB("行政处罚");
                sxdata.setF_FROM(F_FROM);
                insertData(objres,sxdata);
            }
        }

        return totalCount;
    }

    public int getXZLicense(String f_guid,HashMap<String,Object> paramMap,int pageNum) {
        String jsonStr = "";
        int totalCount = 0;
        for(int i = 1; i <= pageNum; i++){
            paramMap.put(UrlParam_Page,i);
            //jsonStr = HttpUtil.get(url2,paramMap);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jsonStr = HttpGet(url4,paramMap);

            JSONObject data = JSONUtil.parseObj(jsonStr).getJSONObject("result");
            JSONArray result = data.getJSONArray("results");

            totalCount = data.getInt("totalCount");

            for (Object res : result) {
                JSONObject objres = JSONUtil.parseObj(res);
                sxmdListBean sxdata = new sxmdListBean();
                sxdata.setF_GUID(f_guid);
                sxdata.setF_MDLB("1");
                sxdata.setF_SJLB("行政许可");
                sxdata.setF_FROM(F_FROM);
                insertData(objres,sxdata);
            }
        }

        return totalCount;
    }

    public void getEnterpriseDetail(String encryStr, String F_GUID) {

        sxmdListBean sxdata = new sxmdListBean();
        sxdata.setF_GUID(F_GUID);
        sxdata.setF_MDLB("0"); //信息概览
        sxdata.setF_SJLB("基本信息"); //信息概览
        sxdata.setF_FROM(F_FROM);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("encryStr",encryStr);
        String jsonStr = "";
        //jsonStr = HttpUtil.get(url3,paramMap);

        jsonStr = HttpGet(url3,paramMap);

        JSONObject data = JSONUtil.parseObj(jsonStr).getJSONObject("result");

        insertData(data,sxdata);

    }

    //获取第二层数据
    public int getSxmdList(HashMap<String,Object> paramMap, int pageNum, String F_GUID){
        String jsonStr = "";
        int totalCount = 0;

        for(int i = 1; i <= pageNum; i++){
            paramMap.put(UrlParam_PageNum,i);
            //jsonStr = HttpUtil.get(url2,paramMap);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jsonStr = HttpGet(url2,paramMap);

            JSONObject data = JSONUtil.parseObj(jsonStr);
            JSONArray result = data.getJSONArray("result");

            totalCount = data.getInt("totalCount");

            for (Object res : result) {
                JSONObject objres = JSONUtil.parseObj(res);
                sxmdListBean sxdata = new sxmdListBean();
                sxdata.setF_GUID(F_GUID);
                sxdata.setF_FROM(F_FROM);
                sxdata.setF_MDLB(paramMap.get(UrlParam_creditType).toString());
                sxdata.setF_SJLB(objres.getStr("数据类别"));
                insertData(objres,sxdata);
            }
        }

        return totalCount;
    }

    public void insertData(JSONObject data, sxmdListBean sxdata){

        String dataType = "";
        dataType = sxdata.getF_SJLB();
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
        }else if("基本信息".equals(dataType)){ //基本信息
            insertBasicInfo(data,sxdata);
        }else if("行政处罚".equals(dataType)){//行政处罚
            insertXZPunish(data,sxdata);
        }else if("行政许可".equals(dataType)){//行政许可
            insertXZLisence(data,sxdata);
        }else if("A级纳税人".equals(dataType)){ //A级纳税人
            insertTaxpayerA(data,sxdata);
        }
        else {
            try {
                doInsertCreditList(sxdata);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void insertTaxpayerA(JSONObject data, sxmdListBean sxdata) {

        sxdata.setF_XH(data.getStr("序号"));
        sxdata.setF_SJLY(data.getStr("数据来源"));
        sxdata.setF_ZXYJWH(data.getStr("文件名"));
        sxdata.setF_ZXGXRQ(data.getStr("最新更新日期"));
        sxdata.setF_BZXRMC(data.getStr("纳税人名称"));
        sxdata.setF_RKSJ(data.getStr("评价年度"));


        try {
            doInsertCreditList(sxdata);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertXZLisence(JSONObject data, sxmdListBean sxdata) {

        sxdata.setF_XH(data.getStr("lately_MODIFY_TIME"));//F_XH
        sxdata.setF_ZZJGDM(data.getStr("typeVersion")); //F_ZZJGDM
        sxdata.setF_MDMC(data.getStr("xkDfbm"));//F_MDMC
        sxdata.setF_FRDB(data.getStr("xkFr")); //F_FRDB
        sxdata.setF_RKSJ(data.getStr("xkJdrq")); //F_RKSJ
        sxdata.setF_FBSJ(data.getStr("xkJzq")); //F_FBSJ
        sxdata.setF_LRYY(data.getStr("xkNr")); //F_LRYY
        sxdata.setF_ZXGXRQ(data.getStr("xkSjc"));//F_ZXGXRQ
        sxdata.setF_NRLY(data.getStr("xkSplb"));//F_NRLY
        sxdata.setF_ZXYJWH(data.getStr("xkWsh")); //F_ZXYJWH
        sxdata.setF_LRBM(data.getStr("xkXzjg")); //F_LRBM
        sxdata.setF_ZM(data.getStr("xkYxq")); //F_ZM
        sxdata.setF_AH(data.getStr("xk_JDRQ")); //F_AH
        sxdata.setF_PJZCJG(data.getStr("xk_LYDM")); //F_PJZCJG
        sxdata.setF_DYMC(data.getStr("xk_LYDW")); //F_DYMC
        sxdata.setF_BZ(data.getStr("xk_NR")); //F_BZ
        sxdata.setF_LXQK(data.getStr("xk_WSH")); //F_LXQK
        sxdata.setF_JTQX(data.getStr("xk_XKBH")); //F_JTQX
        sxdata.setF_LASJ(data.getStr("xk_XKJG")); //F_LASJ
        sxdata.setF_YLXBF(data.getStr("xk_XKWS")); //F_YLXBF
        sxdata.setF_WLXBF(data.getStr("xk_XKZS")); //F_WLXBF
        sxdata.setF_SJJE(data.getStr("xk_YXQZ")); //F_SJJE
        sxdata.setF_DBRZCH(data.getStr("xk_YXQZI")); //F_DBRZCH
        sxdata.setF_ZXFY(data.getStr("xk_ZT")); //F_ZXFY

        try {
            doInsertCreditList(sxdata);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertXZPunish(JSONObject data, sxmdListBean sxdata) {

        sxdata.setF_XH(data.getStr("lately_MODIFY_TIME"));//F_XH
        sxdata.setF_ZZJGDM(data.getStr("typeVersion")); //F_ZZJGDM
        sxdata.setF_MDMC(data.getStr("cfCflb1"));//F_MDMC
        sxdata.setF_FRDB(data.getStr("cfCfmc")); //F_FRDB
        sxdata.setF_RKSJ(data.getStr("cfFr")); //F_RKSJ
        sxdata.setF_FBSJ(data.getStr("cfJdrq")); //F_FBSJ
        sxdata.setF_LRYY(data.getStr("cfSy")); //F_LRYY
        sxdata.setF_ZXGXRQ(data.getStr("cfJg"));//F_ZXGXRQ
        sxdata.setF_NRLY(data.getStr("cfQx"));//F_NRLY
        sxdata.setF_ZXYJWH(data.getStr("cfSjc")); //F_ZXYJWH
        sxdata.setF_LRBM(data.getStr("cfYj")); //F_LRBM
        sxdata.setF_AH(data.getStr("cfWsh")); //F_AH
        sxdata.setF_PJZCJG(data.getStr("cfXzjg")); //F_PJZCJG
        sxdata.setF_DYMC(data.getStr("cf_CFJG")); //F_DYMC
        sxdata.setF_BZ(data.getStr("cf_CFLB")); //F_BZ
        sxdata.setF_LXQK(data.getStr("cf_GSJZQ")); //F_LXQK
        sxdata.setF_JTQX(data.getStr("cf_JDRQ")); //F_JTQX
        sxdata.setF_LASJ(data.getStr("cf_NR")); //F_LASJ
        sxdata.setF_YLXBF(data.getStr("cf_NR_FK")); //F_YLXBF
        sxdata.setF_WLXBF(data.getStr("cf_NR_WFFF")); //F_WLXBF
        sxdata.setF_SJJE(data.getStr("cf_NR_ZKDX")); //F_SJJE
        sxdata.setF_DBRZCH(data.getStr("cf_SJLY")); //F_DBRZCH
        sxdata.setF_ZXFY(data.getStr("cf_SJLYDM")); //F_ZXFY
        sxdata.setF_SHXYDM(data.getStr("cf_SY")); //F_SHXYDM
        sxdata.setF_ZTLX(data.getStr("cf_WFXW")); //F_ZTLX
        sxdata.setF_ZCH(data.getStr("cf_WSH")); //F_ZCH
        sxdata.setF_CFYJ(data.getStr("cf_YJ")); //F_CFYJ
        sxdata.setF_YXQ(data.getStr("cf_YXQ")); //F_YXQ
        sxdata.setF_FLOWNO(data.getStr("flowno")); //F_FLOWNO
        sxdata.setF_RPST(data.getStr("repairState")); //F_RPST
        sxdata.setF_ZM(data.getStr("id")); //F_ZM


        try {
            doInsertCreditList(sxdata);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertBasicInfo(JSONObject data, sxmdListBean sxdata) {
        sxdata.setF_SHXYDM(data.getStr("creditCode"));
        sxdata.setF_SXQYDZ(data.getStr("dom"));
        sxdata.setF_ZCH(data.getStr("regno"));
        sxdata.setF_FRDB(data.getStr("legalPerson"));
        sxdata.setF_FBSJ(data.getStr("esdate"));
        sxdata.setF_QYLX(data.getStr("enttype"));
        sxdata.setF_LRBM(data.getStr("regorg"));

        try {
            doInsertCreditList(sxdata);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUnpayWagesBlackList(JSONObject data, sxmdListBean sxdata) {
        sxdata.setF_LRYY(data.getStr("列入名单事由"));
        sxdata.setF_FBSJ(data.getStr("列入日期"));
        sxdata.setF_MDMC(data.getStr("名单名称"));
        sxdata.setF_QYMC(data.getStr("对象名称"));
        sxdata.setF_SJLY(data.getStr("数据来源"));
        sxdata.setF_FRDB(data.getStr("法人代表"));
        sxdata.setF_SJJE(data.getStr("涉及金额"));
        sxdata.setF_PJZCJG(data.getStr("认定部门"));

        try {
            doInsertCreditList(sxdata);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSafetyProductBlackList(JSONObject data, sxmdListBean sxdata) {
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
            doInsertCreditList(sxdata);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDishonestBlackListFR(JSONObject data, sxmdListBean sxdata) {
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
            doInsertCreditList(sxdata);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insertIllegalEnterpriseJZ(JSONObject data, sxmdListBean sxdata) {
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
            doInsertCreditList(sxdata);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertExceptionlist(JSONObject data, sxmdListBean sxdata) {
        sxdata.setF_QYMC(data.getStr("企业名称"));
        sxdata.setF_PJZCJG(data.getStr("列入决定机关名称"));
        sxdata.setF_LRYY(data.getStr("列入经营异常名录原因类型名称"));
        sxdata.setF_SJLY(data.getStr("数据来源"));
        sxdata.setF_ZXGXRQ(data.getStr("最新更新日期"));
        sxdata.setF_FRDB(data.getStr("法定代表人"));
        sxdata.setF_DBRZCH(data.getStr("注册号"));
        sxdata.setF_FBSJ(data.getStr("设立日期"));

        try {
            doInsertCreditList(sxdata);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doInsertCreditDetail(sxmdDetailBean sxdata) throws SQLException{
        Db.use().insert(Entity.create(tableName2)
                .set("F_GUID",sxdata.getF_GUID())
                .set("F_ZTLX",sxdata.getF_ZTLX())
                .set("F_ZZJGDM",sxdata.getF_ZZJGDM())
                .set("F_XZXK",sxdata.getF_XZXK())
                .set("F_XZCF",sxdata.getF_XZCF())
                .set("F_SXHMD",sxdata.getF_SXHMD())
                .set("F_HMD",sxdata.getF_HMD())
                .set("F_ZDGZMD",sxdata.getF_ZDGZMD())
                .set("F_FROM",sxdata.getF_FROM())
        );
    }

    public void doInsertCreditList(sxmdListBean sxdata) throws SQLException {
        Db.use().insert(
                Entity.create(tableName1)
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
                        .set("F_QYLX",sxdata.getF_QYLX())
                        .set("F_FROM",sxdata.getF_FROM())
                        .set("F_CFYJ",sxdata.getF_CFYJ())
                        .set("F_YXQ",sxdata.getF_YXQ())
                        .set("F_FLOWNO",sxdata.getF_FLOWNO())
                        .set("F_RPST",sxdata.getF_RPST())
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

    //获取第二层名单类数据总页数  totalPageCount
    public int getTotalPageCount2(HashMap<String,Object> paramMap){
        int totalPageCount = 1;
        try {
            String jsonStr = HttpGet(url2,paramMap);
            JSONObject data = JSONUtil.parseObj(jsonStr);
            totalPageCount = data.getInt("totalPageCount");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return totalPageCount;
        }

    }

    //获取第二层行政许可 或 行政处罚 数据总页数  totalPageCount
    public int getTotalPageCount3(HashMap<String,Object> paramMap,String url){
        int totalPageCount = 1;
        try {
            String jsonStr = HttpGet(url,paramMap);
            JSONObject data = JSONUtil.parseObj(jsonStr).getJSONObject("result");
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
        List<Integer> creditTypeList = new ArrayList<Integer>();
        creditTypeList.add(4); // 重点关注名单
        creditTypeList.add(8); // 失信黑名单
        creditTypeList.add(2); //红名单
        HashMap<String, Object> paramMap;
        Date date = new Date();
        String timestamp = String.valueOf(date.getTime());
        System.out.println(timestamp);
        for (Integer s : creditTypeList) {
            paramMap = new HashMap();
            paramMap.put(UrlParam_creditType,s);
            paramMap.put(UrlParam_keyword,"");
            paramMap.put(UrlParam_objectType,2);
            paramMap.put(UrlParam_pageSize,10);
            paramMap.put(UrlParam_Page,1);
            paramMap.put(UrlParam__,timestamp);


//            String jsonstr = ss.HttpGet(url1,paramMap);
//            System.out.println(jsonstr);

//            totalPageCount1 = ss.getTotalPageCount1(paramMap);
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

    public String HttpGet(String url,HashMap<String,Object> paramMap){
        //String urlw = "https://www.creditchina.gov.cn/api/credit_info_search?objectType=2&page=1&pageSize=10&creditType=8&_=1571015632203";


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String jsonStr = HttpRequest.get(url)
                .header(Header.USER_AGENT, user_Agent)//头信息，多个头信息多次调用此方法即可
                .header(Header.ACCEPT, Accept)
                .header(Header.CONTENT_TYPE, Content_Type)
                // .header(Header.REFERER, Referer)
                .form(paramMap)
                .timeout(20000)//超时，毫秒
                .execute().body();

        System.out.println(jsonStr);

        if(!JSONUtil.isJson(jsonStr)){
            jsonStr = HttpGet(url,paramMap);
        }
        return jsonStr;
    }

}
