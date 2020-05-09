package influxdb.bean;

import java.io.Serializable;

/**
 * 日志信息表
 * @author
 *
 */
public class LOGINFO implements Serializable{
	
	private static final long serialVersionUID = 837507498838404933L;
	
	private String F_GUID;
	private String PROCESSNAME;//流程名称
	private String PROCESSVERSION;//流程版本
	private String MACHINENAME;//机器名称
	private String LOGLEVEL; //日志等级
	private String LOGCONTENT; //日志内容
	private String MACHINEIP; //机器IP或者URL
	private String F_CRDATE; //创建时间
	private String F_CHDATE; //更新时间
	private String DATE; //日期 yyyyMMdd
	private String CREATETIME; //日期 yyyy-MM-dd HH:mm:ss
	private String BUSINESS; // 新增的业务字段 主要用来存放一些业务信息 以json串形式存储


	public String getBUSINESS() {
		return BUSINESS;
	}

	public void setBUSINESS(String BUSINESS) {
		this.BUSINESS = BUSINESS;
	}

	public String getPROCESSNAME() {
		return PROCESSNAME;
	}
	public void setPROCESSNAME(String pROCESSNAME) {
		PROCESSNAME = pROCESSNAME;
	}
	public String getPROCESSVERSION() {
		return PROCESSVERSION;
	}
	public void setPROCESSVERSION(String pROCESSVERSION) {
		PROCESSVERSION = pROCESSVERSION;
	}
	public String getLOGLEVEL() {
		return LOGLEVEL;
	}
	public void setLOGLEVEL(String lOGLEVEL) {
		LOGLEVEL = lOGLEVEL;
	}
	public String getLOGCONTENT() {
		return LOGCONTENT;
	}
	public void setLOGCONTENT(String lOGCONTENT) {
		LOGCONTENT = lOGCONTENT;
	}
	public String getMACHINEIP() {
		return MACHINEIP;
	}
	public void setMACHINEIP(String mACHINEIP) {
		MACHINEIP = mACHINEIP;
	}
	public String getF_GUID() {
		return F_GUID;
	}
	public void setF_GUID(String f_GUID) {
		F_GUID = f_GUID;
	}
	public String getMACHINENAME() {
		return MACHINENAME;
	}
	public void setMACHINENAME(String mACHINENAME) {
		MACHINENAME = mACHINENAME;
	}
	public String getF_CRDATE() {
		return F_CRDATE;
	}
	public void setF_CRDATE(String f_CRDATE) {
		F_CRDATE = f_CRDATE;
	}
	public String getF_CHDATE() {
		return F_CHDATE;
	}
	public void setF_CHDATE(String f_CHDATE) {
		F_CHDATE = f_CHDATE;
	}
	public String getDATE() {
		return DATE;
	}
	public void setDATE(String dATE) {
		DATE = dATE;
	}
	public String getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}


	@Override
	public String toString() {
		return "LOGINFO{" +
				"F_GUID='" + F_GUID + '\'' +
				", PROCESSNAME='" + PROCESSNAME + '\'' +
				", PROCESSVERSION='" + PROCESSVERSION + '\'' +
				", MACHINENAME='" + MACHINENAME + '\'' +
				", LOGLEVEL='" + LOGLEVEL + '\'' +
				", LOGCONTENT='" + LOGCONTENT + '\'' +
				", MACHINEIP='" + MACHINEIP + '\'' +
				", F_CRDATE='" + F_CRDATE + '\'' +
				", F_CHDATE='" + F_CHDATE + '\'' +
				", DATE='" + DATE + '\'' +
				", CREATETIME='" + CREATETIME + '\'' +
				", BUSINESS='" + BUSINESS + '\'' +
				'}';
	}
}
