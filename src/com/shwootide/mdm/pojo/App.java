package com.shwootide.mdm.pojo;

public class App {
	//����Ӧ�õĻ�����Ϣ������Ӧ�����ƣ�Ӧ�����ͣ�Ӧ�ð汾����װ�ֻ���Ͱ汾��Ϣ
//	private String appName;
//	private String appType;
//	private String appPackageName;
//	public String getAppName() {
//		return appName;
//	}
//	public void setAppName(String appName) {
//		this.appName = appName; 
//	}
//	public String getAppType() {
//		return appType;
//	}
//	public void setAppType(String appType) {
//		this.appType = appType;
//	}
//	public String getAppPackageName() {
//		return appPackageName;
//	}
//	public void setAppPackageName(String appPackageName) {
//		this.appPackageName = appPackageName;
//	}
	
	private String ApplicationName;//Ӧ������
	private String ApplicationType;//Ӧ������
	private String DownloadUrl;//apk��ַ
	private String PackageName;//apk�����������������Ӧ�ó���װ���
	private int id;
	
	public int getId() {
	    return id;
	}
	public void setId(int id) {
	    this.id = id;
	}
	public String getPackageName() {
		return PackageName;
	}
	public void setPackageName(String packageName) {
		PackageName = packageName;
	}
	public String getDownloadUrl() {
		return DownloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		DownloadUrl = downloadUrl;
	}
	public String getApplicationName() {
		return ApplicationName;//ֱ�ӷ���Ӧ������
	}
	public void setApplicationName(String applicationName) {
		ApplicationName = applicationName;
	}
	public String getApplicationType() {
		return ApplicationType;
	}
	public void setApplicationType(String applicationType) {
		ApplicationType = applicationType;
	}
	

}
