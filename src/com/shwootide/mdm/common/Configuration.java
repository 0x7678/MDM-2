package com.shwootide.mdm.common;



public class Configuration {
	public static boolean initFlag = false;
	public static final String DBSchemaPath = "schema";
	public static final String DatabaseFile = "shwootide_mdm.db";
	//public static final String ServerHost = "http://mdm.mypatroller.com"; //����Ƿ���Զ�̷�������
	//public static final String ServerHost = "http://192.168.0.194";
	public static final String ServerIP = "211.144.201.245";//����IMͨ�ŵĹ���
	public static final String DOWNLOAD_ATTACHMENT_URL = "http://enterprise.mypatroller.com/public/download/";
	public static final String DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
	
//	public static final String ServerHost = "http://192.168.0.121:8083/";
	public static final String ServerStart = "http://";
	public static final String ServerHost = ".app.mypatroller.com/"; //����Ƿ�������ַ��������Ҫ�ٸĵĲ���
	public static final String UPDATE_SERVER = "http://app.mdm.mypatroller.com/mdmapp/";//����apk���·��
	public static final String UPDATE_VER = "ver.json";
	public static final String UPDATE_APKNAME = "mdm.apk";
	public static final String UPDATE_SAVENAME = "mdm.apk";
	
	public static final String CHANNEL_NAME = "C50a211ee5906b";
	
	public static final String postImageUrl = ServerHost+"/Webservices/AndroidUploadImage.ashx";
	public static final String postHeadUrl = ServerHost+"/Webservices/AndroidUploadHead.ashx"; //�ϴ�����Ƭ��post·��
	public static final String getImageUrl = ServerHost+"/webservices/imagehandler.ashx?url="+ServerHost; 
	public static final String getImageWidth = "&d=";
	public static final String getImageHeighth = "&h=";
	public static final String localImagesPath = "/mypatroller/images/";
	public static final int SEND_PORT_NO = 8955; //�����˿�
	public static final int LISTENING_PORT_NO = 8956; //�����˿�
	public static final int DatabaseVersion = 1;
	/**�ֺŷָ���*/
	public static final String SemicolonSeparator = ";";
	/**���ŷָ���*/
	public static final String CommaSeparator = ",";
	public static int oldVersion = -1;
	public static final int PAGE_COUNT = 10;
	public static boolean isNetworkAvailable = false;
	public static boolean isLastLoadWeiboListFinish = true;
	public static final String SETTING_INFOS = "SETTING";
	public static final String USERID = "USERID";
	public static final String USERNAME = "USERNAME";
	public static final String EMPLOYEENAME = "EMPLOYEENAME";
	public static final String PWD = "PASSWORD";
	public static final String COMPANYNAME = "COMPANYNAME";
	public static final String DEVICEID = "DEVICEID";
	public static final String ECNAME = "ECNAME";
	public static final String TEAMCODE = "TEAMCODE";
	public static final String GPS = "GPSSTATUS";
	public static final String GPSSTATUS = "GPS";
	public static final String LOCALLOGIN = "LOCALLOGIN";
	public static final String LOGINSTATUS = "LOGINSTATUS";
	
	//��½��Ϣ���湦�ܣ��ƶ��������ݣ�������������
	public static final String LOGIN_INFOS = "LOGININFOS";
	public static final String LOGIN_CLOUDS_ECNAME = "LOGIN_CLOUDS_ECNAME";// 
	public static final String LOGIN_CLOUDS_NAME = "LOGIN_CLOUDS_NAME";
	public static final String LOGIN_CLOUDS_PWD = "LOGIN_CLOUDS_PWD";//
	public static final String LOGIN_LOCAL_IP = "LOGIN_LOCAL_IP";
	public static final String LOGIN_LOCAL_NAME = "LOGIN_LOCAL_NAME";
	public static final String LOGIN_LOCAL_PWD = "LOGIN_LOCAL_PWD";
	
	
	public static final String LOGINS_TYPE = "0";//1�����ƶ˵�½�� 2�����ص�½
	public static final String LOGIN_LOGOUT_FLAG = "0";//��½֮ǰΪ0,��½�ɹ�Ϊ1,�˳���Ϊ0,ע����Ϊ0
	public static int width = 480 ;
	public static int height = 800;
	
	public static boolean debugIsOn = true;//�����汾��ʱ���޸�Ϊfalse,ƽʱ�����޸�Ϊtrue
	
	
	public static final String NETWORKUNCONNECT="�������Ӳ����ã����Ժ�����";
	
	public static boolean IMEI_IS_SIGN = false;
	
	
	
}

