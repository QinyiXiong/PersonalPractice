package CreditChinaCrawler.Bean;

/**
 * @author: 覃义雄
 * @dateTime: 2019/10/15 14:06
 * @project_Name: PersonalPractice
 * @Name: sxmdDetailBean
 * @Describe：
 */
public class sxmdDetailBean {
    private String F_GUID; //guid
    private String F_ZTLX; //主体类型（法人）
    private String F_ZZJGDM; //组织机构代码/身份证号
    private String F_XZXK; //行政许可（计数）
    private String F_XZCF; //行政处罚（计数）
    private String F_SXHMD; //守信红名单（计数）
    private String F_HMD; //失信黑名单（计数）
    private String F_ZDGZMD; //重点关注名单（计数）
    private String F_FROM; //数据来源 eg:信用中国

    public String getF_GUID() {
        return F_GUID;
    }

    public void setF_GUID(String f_GUID) {
        F_GUID = f_GUID;
    }

    public String getF_ZTLX() {
        return F_ZTLX;
    }

    public void setF_ZTLX(String f_ZTLX) {
        F_ZTLX = f_ZTLX;
    }

    public String getF_ZZJGDM() {
        return F_ZZJGDM;
    }

    public void setF_ZZJGDM(String f_ZZJGDM) {
        F_ZZJGDM = f_ZZJGDM;
    }

    public String getF_XZXK() {
        return F_XZXK;
    }

    public void setF_XZXK(String f_XZXK) {
        F_XZXK = f_XZXK;
    }

    public String getF_XZCF() {
        return F_XZCF;
    }

    public void setF_XZCF(String f_XZCF) {
        F_XZCF = f_XZCF;
    }

    public String getF_SXHMD() {
        return F_SXHMD;
    }

    public void setF_SXHMD(String f_SXHMD) {
        F_SXHMD = f_SXHMD;
    }

    public String getF_HMD() {
        return F_HMD;
    }

    public void setF_HMD(String f_HMD) {
        F_HMD = f_HMD;
    }

    public String getF_ZDGZMD() {
        return F_ZDGZMD;
    }

    public void setF_ZDGZMD(String f_ZDGZMD) {
        F_ZDGZMD = f_ZDGZMD;
    }

    public String getF_FROM() {
        return F_FROM;
    }

    public void setF_FROM(String f_FROM) {
        F_FROM = f_FROM;
    }
}
