package com.shwootide.mdm.view;


import java.util.HashMap;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import com.ab.activity.AbActivity;
import com.shwootide.mdm.R;
import com.shwootide.mdm.devadmin.MyAdmin;
import com.shwootide.mdm.service.ExitApplication;
import com.shwootide.mdm.tools.ActivityTools;
import com.shwootide.mdm.tools.PreferenceUtils;
import com.shwootide.mdm.tools.WebService;

public class LogView extends AbActivity {
    public static String TAG = "LogView";
    Button flow, admin, test, wifi, lockphoto,exit;
    private static WifiManager wifiManager;
    private Button open;
    MyApplication application;
    BluetoothAdapter mBluetoothAdapter;
	ComponentName componentName;
	DevicePolicyManager devicePolicyManager;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	super.onCreate(savedInstanceState);
	application=(MyApplication) getApplication();
	ExitApplication.getInstance().addActivity(this);
	devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
	componentName = new ComponentName(
		getApplicationContext(), MyAdmin.class);
	setContentView(R.layout.log_text);
	ActivityTools.headView(LogView.this, "����");
	wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	flow = (Button) findViewById(R.id.flow);
	admin = (Button) findViewById(R.id.admin);
	boolean activation=PreferenceUtils.getPrefBoolean(this, "activation", false);
	if(activation){
	    admin.setText("ע������");
	}else{
	    admin.setText("����");
	}
	flow.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		Intent intent = new Intent();
		intent.setClass(LogView.this, Showmain.class);
		startActivity(intent);
	    }
	});
	admin.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		// ����Ȩ��
		
		// �жϸ�����Ƿ���ϵͳ����Ա��Ȩ��
		boolean isAdminActive = devicePolicyManager
			.isAdminActive(componentName);

		if (isAdminActive) {
		    devicePolicyManager.removeActiveAdmin(componentName);
		    MyTask myTask = new MyTask();
		    myTask.execute("DeactivateDevice");
		    admin.setText("����");
		    PreferenceUtils.setPrefBoolean(LogView.this, "activation",false);
		} else {
		    Intent intent = new Intent();
		    // ָ����������
		    intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		    // ָ�����ĸ������Ȩ
		    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
			    componentName);
		    startActivity(intent);
		}
	    }
	});

	lockphoto = (Button) findViewById(R.id.lockphoto);
	lockphoto.setOnClickListener(new OnClickListener() {

	    @SuppressLint("NewApi")
	    @Override
	    public void onClick(View v) {
		Intent intent=new Intent(LogView.this,EmaStoreView.class);
		startActivity(intent);
	    }
	});

	wifi = (Button) findViewById(R.id.wifi);
	wifi.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
//		Animation anim = AnimationUtils.loadAnimation(LogView.this,R.anim.flip_horizontal_in);
//		wifi.startAnimation(anim);
		startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
	    }
	});
	test = (Button) findViewById(R.id.test);
	test.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		Intent intent=new Intent(LogView.this,ProductIntroduceView.class);
		startActivity(intent);
	    }
	});
	open = (Button) findViewById(R.id.open);
	open.setOnClickListener(new OnClickListener(){
	    @Override
	    public void onClick(View v) {
		Intent intent=new Intent(LogView.this,UpdateVersionsView.class);
		startActivity(intent);
	    }
	});
	exit=(Button) findViewById(R.id.perfectexit);
		exit.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
			AlertDialog.Builder alertDialog=new AlertDialog.Builder(LogView.this);
			alertDialog.setTitle("�˳���ʾ..");
			alertDialog.setMessage("�㽫��ȫ�˳�Ӧ�ó��򡣡���ȷ��Ҫ��������");
			alertDialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
				ExitApplication.getInstance().exit();
			    }
			});
			alertDialog.setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
			    
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			
			    }
			});
			alertDialog.create().show();
		    }
		});
		Button logrecord=(Button) findViewById(R.id.logrecord);
		logrecord.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
			Intent intent=new Intent(LogView.this,MainLogView.class);
			startActivity(intent);
		    }
		});
    }

    // wifi�ȵ㿪��
    public boolean setWifiApEnabled(boolean enabled, String name,
	    String password) {
	if (enabled) { // disable WiFi in any case
	    // wifi���ȵ㲻��ͬʱ�򿪣����Դ��ȵ��ʱ����Ҫ�ر�wifi
	    wifiManager.setWifiEnabled(false);
	}
	try {
	    // �ȵ�������� ��������ֱ����Ч
	    WifiConfiguration apConfig = new WifiConfiguration();
	    // �����ȵ������(���������ֺ���ӵ������ʲô��)
	    apConfig.SSID = ""; // �ȵ�����
	    // �����ȵ������
	    apConfig.preSharedKey = "";
	    // ͨ��������������ȵ�
	    java.lang.reflect.Method method = wifiManager.getClass().getMethod(
		    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
	    // �����ȵ��״̬

	    return (Boolean) method.invoke(wifiManager, apConfig, enabled);
	} catch (Exception e) {
	    return false;
	}
    }

    class MyTask extends AsyncTask<String, Integer, String> {

	@Override
	protected String doInBackground(String... params) {
	    HashMap<String, String> hmParam = new HashMap<String, String>();
	    hmParam.put("IMEI",PreferenceUtils.getPrefString(LogView.this, "imei", ""));
	    String remoteInfo = WebService.getRemoteInfo(LogView.this,
		    params[0], hmParam);
	    Log.i("doInBackground", remoteInfo);
	    return null;
	}

	@Override
	protected void onPostExecute(String result) {

	    super.onPostExecute(result);
	    if (devicePolicyManager.isAdminActive(componentName)) {
        	
            }else{
        	showToast("ע���ɹ���");
            }
	}
    }

 
    @Override
    protected void onResume() {
        super.onResume();
            if (devicePolicyManager.isAdminActive(componentName)) {
        	MyTask myTask = new MyTask();
        	myTask.execute("UpdateClientId");
        	 admin.setText("ע������");
        	 PreferenceUtils.setPrefBoolean(LogView.this, "activation",true);
            }      
    }
    
}
