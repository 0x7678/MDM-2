package com.shwootide.mdm.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.shwootide.mdm.pojo.MyFile;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ApkSearchUtils {
	
	private static int INSTALLED = 0; // ��ʾ�Ѿ���װ���Ҹ��������apk�ļ���һ���汾
	    private static int UNINSTALLED = 1; // ��ʾδ��װ
	    private static int INSTALLED_UPDATE =2; // ��ʾ�Ѿ���װ���汾����������汾Ҫ�ͣ����Ե����ť����
	 
	    private Context context;
	    public static List<MyFile> myFiles = new ArrayList<MyFile>();
	 
	    public List<MyFile> getMyFiles() {
	        return myFiles;
	    }
	 
	    public void setMyFiles(List<MyFile> myFiles) {
	        this.myFiles = myFiles;
	    }
	 
	    public ApkSearchUtils(Context context) {
	        super();
	        this.context = context;
	    }
	    
	    /**
	         * @param args
	         *            ���õݹ��˼�룬�ݹ�ȥ��ÿ��Ŀ¼�����apk�ļ�
	         */
	        public void FindAllAPKFile(File file) {
	     
	            // �ֻ��ϵ��ļ�,Ŀǰֻ�ж�SD���ϵ�APK�ļ�
	            // file = Environment.getDataDirectory();
	            // SD���ϵ��ļ�Ŀ¼
	            if (file.isFile()) {
	                String name_s = file.getName();
	                MyFile myFile = new MyFile();
	                String apk_path = null;
	                // MimeTypeMap.getSingleton()
	                if (name_s.toLowerCase().endsWith(".apk")) {
	                    apk_path = file.getAbsolutePath();// apk�ļ��ľ���·��,���ǾͶ���ΪSD��
	                    // System.out.println("----" + file.getAbsolutePath() + "" +
	                    // name_s);
	                    PackageManager pm = context.getPackageManager();
	                    PackageInfo packageInfo = pm.getPackageArchiveInfo(apk_path, PackageManager.GET_ACTIVITIES);
	                    ApplicationInfo appInfo = packageInfo.applicationInfo;
	     
	                     
	                     /**��ȡapk��ͼ�� */
	                    appInfo.sourceDir = apk_path;
	                    appInfo.publicSourceDir = apk_path;
	                    Drawable apk_icon = appInfo.loadIcon(pm);
	                    myFile.setApk_icon(apk_icon);
	                    /** �õ����� */
	                    String packageName = packageInfo.packageName;
	                    myFile.setPackageName(packageName);
	                    /** apk�ľ���·�� */
	                    myFile.setFilePath(file.getAbsolutePath());
	                    /** apk�İ汾���� String */
	                    String versionName = packageInfo.versionName;
	                    myFile.setVersionName(versionName);
	                    /** apk�İ汾���� int */
	                    int versionCode = packageInfo.versionCode;
	                    myFile.setVersionCode(versionCode);
	                    /**��װ��������*/
	                    int type = doType(pm, packageName, versionCode);
	                    myFile.setInstalled(type);
	                     
	                    Log.i("ok", "��������:"+String.valueOf(type)+"\n" + "------------------���Ǵ���ķָ���-------------------");
	                    myFiles.add(myFile);
	                }
	                // String apk_app = name_s.substring(name_s.lastIndexOf("."));
	            } else {
	                File[] files = file.listFiles();
	                if (files != null && files.length > 0) {
	                    for (File file_str : files) {
	                        FindAllAPKFile(file_str);
	                    }
	                }
	            }
	        }
	     
	        /*
	         * �жϸ�Ӧ���Ƿ����ֻ����Ѿ���װ���������¼����������
	         * 1.δ��װ�����ʱ��ťӦ���ǡ���װ�������ť���а�װ
	         * 2.�Ѱ�װ����ť��ʾ���Ѱ�װ�� ����ж�ظ�Ӧ��
	         * 3.�Ѱ�װ�����ǰ汾�и��£���ť��ʾ�����¡� �����ť�Ͱ�װӦ��
	         */
	         
	        /**
	         * �жϸ�Ӧ�����ֻ��еİ�װ���
	         * @param pm                   PackageManager 
	         * @param packageName  Ҫ�ж�Ӧ�õİ���
	         * @param versionCode     Ҫ�ж�Ӧ�õİ汾��
	         */
	        public int doType(PackageManager pm, String packageName, int versionCode) {
	            List<PackageInfo> pakageinfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
	            for (PackageInfo pi : pakageinfos) {
	                String pi_packageName = pi.packageName;
	                int pi_versionCode = pi.versionCode;
	                //������������ϵͳ�Ѿ���װ����Ӧ���д���
	                if(packageName.endsWith(pi_packageName)){
	                    //Log.i("test","��Ӧ�ð�װ����");
	                    if(versionCode==pi_versionCode){
	                        Log.i("test","�Ѿ���װ�����ø��£�����ж�ظ�Ӧ��");
	                        return INSTALLED;
	                    }else if(versionCode>pi_versionCode){
	                        Log.i("test","�Ѿ���װ���и���");  
	                        return INSTALLED_UPDATE;
	                    }
	                }
	            }
	            Log.i("test","δ��װ��Ӧ�ã����԰�װ");   
	            return UNINSTALLED;
	        }



}
