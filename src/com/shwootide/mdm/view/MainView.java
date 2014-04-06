package com.shwootide.mdm.view;


import net.tsz.afinal.FinalDb;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.ab.activity.AbActivity;
import com.igexin.slavesdk.MessageManager;
import com.shwootide.mdm.R;
import com.shwootide.mdm.devadmin.MyAdmin;
import com.shwootide.mdm.service.ExitApplication;
import com.shwootide.mdm.tools.APKTools;

public class MainView extends AbActivity implements OnClickListener {
    private String TAG = "MainView";
    private Button bt001, bt002, bt003, bt004;
    private LinearLayout ll001, ll002, ll003, ll004;
    FinalDb db;
    private long mExitTime;
    ComponentName componentName;

	DevicePolicyManager devicePolicyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub

	requestWindowFeature(Window.FEATURE_NO_TITLE);
	super.onCreate(savedInstanceState);
	ExitApplication.getInstance().addActivity(this);
	db = FinalDb.create(this);
	setContentView(R.layout.main_new);
	devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
	componentName = new ComponentName(
		getApplicationContext(), MyAdmin.class);
	boolean isAdminActive = devicePolicyManager
		.isAdminActive(componentName);
	if (!isAdminActive) {
	    showDialog("��ʾ..","���Ӧ����δ����뼤��Ӧ�ã�",new DialogInterface.OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which) {
		    Intent intent = new Intent();
		    // ָ����������
		    intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		    // ָ�����ĸ������Ȩ
		    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
			    componentName);
		    startActivity(intent);  
		}
	    });
	} 
	initView();
	// ���Կ��Լ�⣬����Ӧ�õ�����
	if (APKTools.isAppInstalled(MainView.this, "com.shwootide.mdm")) {
	    Log.e(TAG, "�Ѿ���װ��mdm");
	} else {
	    Log.e(TAG, "��û�а�װmdm");
	}

	// ֹͣ��������Ϣ����SDK����
	MessageManager.getInstance().stopService(this.getApplicationContext());
	// ���³�ʼ����������Ϣ����sdk
	MessageManager.getInstance().initialize(this.getApplicationContext());

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK) {
	    if ((System.currentTimeMillis() - mExitTime) > 2000) {
		Toast.makeText(this, "�ٰ�һ�γ���תΪ��̨����", Toast.LENGTH_SHORT).show();
		mExitTime = System.currentTimeMillis();
	    } else {
		Intent intent = new Intent(Intent.ACTION_MAIN);

		//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // ����Ƿ�������ã��������new
								// task��ʶ

		intent.addCategory(Intent.CATEGORY_HOME);

		startActivity(intent);

	    }
	    return true;
	}
	return super.onKeyDown(keyCode, event);
    }

    public void initView() {
	// bt001 = (Button)findViewById(R.id.bt001);
	ll001 = (LinearLayout) findViewById(R.id.ll001);
	ll002 = (LinearLayout) findViewById(R.id.ll002);
	ll003 = (LinearLayout) findViewById(R.id.ll003);
	ll004 = (LinearLayout) findViewById(R.id.ll004);
	ll001.setOnClickListener(this);
	ll002.setOnClickListener(this);
	ll003.setOnClickListener(this);
	ll004.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
	// TODO Auto-generated method stub
	Intent intent;
	switch (v.getId()) {
	case R.id.ll001:
	    v.setClickable(false);
	    intent = new Intent(MainView.this, NewsContentView.class);
	    startActivity(intent);
	    v.setClickable(true);
	    break;

	case R.id.ll002:
	    // clickAfter(bt002);
	    v.setClickable(false);
	    intent = new Intent(MainView.this, AppManagerView.class);
	    startActivity(intent);
	    v.setClickable(true);
	    break;
	case R.id.ll003:
	    v.setClickable(false);
	    // clickAfter(bt003);
	    intent = new Intent(MainView.this, MoreView.class);
	    startActivity(intent);
	    v.setClickable(true);
	    break;
	case R.id.ll004:
	    v.setClickable(false);
	    // clickAfter(bt004);
	    intent = new Intent(MainView.this, LogView.class);
	    startActivity(intent);
	    v.setClickable(true);
	    break;
	}

    }

    @Override
    protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
    }

}
