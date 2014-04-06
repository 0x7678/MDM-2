package com.shwootide.mdm.view;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
//DeviceAdminReceiver����չ��BroadcastReceiver
public class MyDeviceAdminReceiver extends DeviceAdminReceiver{
	// ������ص�
    public static String PREF_PASSWORD_QUALITY = "password_quality";
    // ����ĳ���
    public static String PREF_PASSWORD_LENGTH = "password_length";

    public static String PREF_MAX_FAILED_PW = "max_failed_pw";
    
	
	/**
     * ��ȡ�豸�洢����ֵ
     * 
     * @param context
     * @return
     */
	public static SharedPreferences getDevicePreference(Context context) {
        return context.getSharedPreferences(
                MyDeviceAdminReceiver.class.getName(), 0);
    }
	
	void showToast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
	
	@Override
    public void onEnabled(Context context, Intent intent) {
        // TODO Auto-generated method stub
        showToast(context, "�豸��������");
    }
	
	@Override
    public void onDisabled(Context context, Intent intent) {
        // TODO Auto-generated method stub
        showToast(context, "�豸����������");
    }
	@Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        // TODO Auto-generated method stub
        return "����һ����ѡ����Ϣ�������йؽ�ֹ�û�������";
    }
	
	 @Override
	    public void onPasswordChanged(Context context, Intent intent) {
	        // TODO Auto-generated method stub
	        showToast(context, "�豸�������뼺���ı�");
	    }
	 
	 @Override
	    public void onPasswordFailed(Context context, Intent intent) {
	        // TODO Auto-generated method stub
	        showToast(context, "�豸�����ı�����ʧ��");
	    }
	 @Override
	    public void onPasswordSucceeded(Context context, Intent intent) {
	        // TODO Auto-generated method stub
	        showToast(context, "�豸�����ı�����ɹ�");
	    }

	
	

}
