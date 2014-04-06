package com.shwootide.mdm.view;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.tsz.afinal.FinalDb;
import android.app.ProgressDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.ab.activity.AbActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.shwootide.mdm.R;
import com.shwootide.mdm.common.Configuration;
import com.shwootide.mdm.pojo.AfficheView;
import com.shwootide.mdm.pojo.App;
import com.shwootide.mdm.pojo.FlowrRecord;
import com.shwootide.mdm.pojo.News;

import com.shwootide.mdm.pojo.Result;
import com.shwootide.mdm.service.ExitApplication;
import com.shwootide.mdm.tools.Constants;
import com.shwootide.mdm.tools.PreferenceUtils;
import com.shwootide.mdm.tools.WebService;

public class LoginView extends AbActivity {

    private ProgressDialog dialog;
    private DataLoadTask dlt = null;
    private Button btnLogin;
    private EditText txtPwd, txtUserName, txtcompanyname;
    public Context context;
    ComponentName componentName;
    FinalDb db;
    DevicePolicyManager devicePolicyManager;
    Gson json = new Gson();

    protected void onCreate(Bundle savedInstanceState) {
	this.context = LoginView.this;
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	super.onCreate(savedInstanceState);
	ExitApplication.getInstance().addActivity(this);
	setContentView(R.layout.login_view);
	if (!PreferenceUtils.getPrefString(this, "imei", "").equals("")) {
	    TelephonyManager tm = (TelephonyManager) this
		    .getSystemService(Context.TELEPHONY_SERVICE);
	    String IMEI = tm.getDeviceId();
	    PreferenceUtils.setPrefString(this, "imei", IMEI);
	}
	InitView();
	Configuration.IMEI_IS_SIGN = false;
	db = FinalDb.create(this);
	Log.i("size", db.findAll(FlowrRecord.class).size() + ".........");
	if (db.findAll(FlowrRecord.class).size() == 0) {
	    Date date = new Date(System.currentTimeMillis());
	    SimpleDateFormat dateFormat = new SimpleDateFormat(
		    "yyyy-MM-dd HH:mm:ss");
	    String firsttime = dateFormat.format(date);
	    FlowrRecord flows = new FlowrRecord();
	    flows.setStartTime(firsttime);
	    flows.setEndTime("000000");
	    db.save(flows);
	}
    }

    private void InitView() {
	txtPwd = (EditText) findViewById(R.id.txt_pwd);
	txtUserName = (EditText) findViewById(R.id.txt_username);
	txtcompanyname = (EditText) findViewById(R.id.txt_companyname);
	findViewById(R.id.ib_down_arrowtwo).setOnClickListener(
		new OnClickListener() {
		    @Override
		    public void onClick(View v) {
			showSelectNumberDialog(txtUserName);
		    }
		});
	findViewById(R.id.ib_down_arrow).setOnClickListener(
		new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			showSelectNumberDialog(txtcompanyname);
		    }
		});
	btnLogin = (Button) findViewById(R.id.btn_login);
	btnLogin.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		String username = txtUserName.getText().toString().trim();
		if (username == null || username.equals("")) {
		    handler.sendEmptyMessage(100);
		    return;
		}
		String pwd = txtPwd.getText().toString().trim();
		if (pwd == null || pwd.equals("")) {
		    handler.sendEmptyMessage(50);
		    return;
		}
		String IMEI = null;
		TelephonyManager tm = (TelephonyManager) LoginView.this
			.getSystemService(TELEPHONY_SERVICE);
		IMEI = tm.getDeviceId();

		PreferenceUtils.setPrefString(getApplicationContext(),
			Constants.imei, IMEI);
		if (IMEI == null) {
		    Toast.makeText(LoginView.this, "IMEI���벻���ڣ��޷����е�½У��!",
			    Toast.LENGTH_SHORT).show();
		    return;
		}
		if (!(username != null && pwd != null)) {
		    Toast.makeText(LoginView.this, "��ȷ���ʺź�������Ч!",
			    Toast.LENGTH_SHORT).show();
		    return;
		} else {
		    if (dialog == null) {
			dialog = ProgressDialog.show(LoginView.this, "��ȴ�..",
				"���ݴ����У���ȴ�..", true);
		    }
		    dlt = new DataLoadTask();// Я���û��� ,���룬imei��
		    Log.i("MANUFACTURER", android.os.Build.MANUFACTURER);
		    dlt.execute("LoginService", username, pwd, IMEI,
			    android.os.Build.MANUFACTURER);
		}
	    }
	});

    }

    class DataLoadTask extends AsyncTask<String, Integer, String> {

	@Override
	protected String doInBackground(String... params) {
	    // TODO Auto-generated method stub
	    String functionName = params[0];

	    HashMap<String, String> hmParam = new HashMap<String, String>();
	    hmParam.put("userName", params[1]);
	    hmParam.put("IMEI", params[3]);
	    hmParam.put("password", params[2]);
	    hmParam.put("deviceName", params[4]);
	    String strJson = WebService.getRemoteInfo(LoginView.this,
		    functionName, hmParam);
	    return strJson;
	}

	@Override
	protected void onPostExecute(String result) {
	    super.onPostExecute(result);
	    if (dialog != null)
		dialog.dismiss();
	    if (result.equals("-99")) {
		Toast.makeText(LoginView.this, Configuration.NETWORKUNCONNECT,
			Toast.LENGTH_SHORT).show();
	    } else {
		List<Result> listResult = new ArrayList<Result>();
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new StringReader(result));
		reader.setLenient(true);
		listResult = gson.fromJson(reader,
			new TypeToken<List<Result>>() {
			}.getType());
		String ret = listResult.get(0).getCode();
		if (ret.equals("001")) {
		    // û�еǼǵ��豸���޷��ڿͻ��˵�½�ɹ�
		    Toast.makeText(LoginView.this, "���豸��������û�еǼǣ�����ϵ����Ա�Ǽ�",
			    Toast.LENGTH_SHORT).show();
		} else if (ret.equals("002")) {
		    Toast.makeText(LoginView.this, "�û���������", Toast.LENGTH_SHORT)
			    .show();
		} else if (ret.equals("003")) {
		    Toast.makeText(LoginView.this, "�������", Toast.LENGTH_SHORT)
			    .show();
		} else if (ret.equals("500")) {
		    Toast.makeText(LoginView.this, "����������", Toast.LENGTH_SHORT)
			    .show();
		} else {
		    handler.sendEmptyMessage(10);

		    db.deleteByWhere(News.class, null);
		    db.deleteByWhere(App.class, null);
		    db.deleteByWhere(AfficheView.class, null);
		    db.deleteByWhere(com.shwootide.mdm.pojo.Log.class, null);
		    Intent intent = new Intent(LoginView.this, MainView.class);
		    startActivity(intent);
		}
	    }

	}
    }

    class MyTask extends AsyncTask<String, Integer, String> {

	@Override
	protected String doInBackground(String... params) {
	    List<FlowrRecord> list = db.findAll(FlowrRecord.class);
	    Log.i("size", list.size() + ".........");
	    List<FlowrRecord> listtwo = new ArrayList<FlowrRecord>();
	    for (int i = 0; list.size() > i; i++) {
		Log.i("closetime", list.get(i).getEndTime() + ".......");
		if (!list.get(i).getEndTime().equals("000000")) {
		    listtwo.add(list.get(i));
		}
	    }
	    String flowInfo = json.toJson(listtwo);
	    HashMap<String, String> hmParam = new HashMap<String, String>();
	    hmParam.put("IMEI",
		    PreferenceUtils.getPrefString(context, "imei", ""));
	    hmParam.put("flowInfo", flowInfo);
	    Log.i("flowInfo", flowInfo);
	    String strJson = WebService.getRemoteInfo(LoginView.this,
		    "UpdateFlow", hmParam);
	    return null;
	}
    }

    MyHandler handler = new MyHandler();

    class MyHandler extends Handler {
	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		Toast.makeText(LoginView.this, "��˾������Ϊ�գ�", 1).show();
		break;
	    case 100:
		Toast.makeText(LoginView.this, "�û�������Ϊ�գ�", 1).show();
		break;
	    case 50:
		Toast.makeText(LoginView.this, "���벻��Ϊ�գ�", 1).show();
		break;
	    case 10:
		MyTask myTask = new MyTask();
		myTask.execute();
		break;
	    default:
		break;
	    }
	}
    }


    private List<String> numbers;
    private NumbersAdapter adapter;
    private PopupWindow popupWindow;

    /**
     * ����ѡ�����Ի���
     */
    private void showSelectNumberDialog(final EditText et) {
	numbers = getNumbers(et);

	ListView lv = new ListView(this);
	lv.setBackgroundResource(R.drawable.icon_spinner_listview_background);
	// ���ع�����
	lv.setVerticalScrollBarEnabled(false);
	// ��listViewû�зָ���
	lv.setDividerHeight(0);
	lv.setDivider(null);
	lv.setOnItemClickListener(new OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		    long arg3) {

		String number = numbers.get(arg2);
		et.setText(number);

		popupWindow.dismiss();

	    }
	});

	adapter = new NumbersAdapter();
	lv.setAdapter(adapter);

	popupWindow = new PopupWindow(lv, et.getWidth() - 4, 200);
	// ���õ���ⲿ���Ա��ر�
	popupWindow.setOutsideTouchable(true);
	popupWindow.setBackgroundDrawable(new BitmapDrawable());

	// ����popupWindow���Եõ�����
	// �������Ѳ�������֪��������֪�����������񽻣������񽻲�������֪��������֪�������ô���...

	popupWindow.setFocusable(true);

	popupWindow.showAsDropDown(et, 2, -5); // ��ʾ

    }

    private List<String> getNumbers(EditText et) {
	List<String> numbers = new ArrayList<String>();
	if (et.equals(txtcompanyname)) {
	    for (int i = 0; i < 30; i++) {
		numbers.add("Company" + i);
	    }
	} else if (et.equals(txtUserName)) {
	    for (int i = 0; i < 30; i++) {
		numbers.add("Admin" + i);
	    }
	}

	return numbers;
    }

    class NumbersAdapter extends BaseAdapter {
	@Override
	public int getCount() {
	    return numbers.size();
	}

	@Override
	public Object getItem(int position) {
	    return numbers.get(position);
	}
	@Override
	public long getItemId(int position) {
	    return position;
	}
	//��ǰ��ְ��ʿ�����ص�����ѧ���裬ѧ��������������������Ǹ�ʿ����
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    NumberViewHolder mHolder = null;
	    if (convertView == null) {
		mHolder = new NumberViewHolder();
		convertView = LayoutInflater.from(LoginView.this).inflate(
			R.layout.item_spinner_numbers, null);
		mHolder.tvNumber = (TextView) convertView
			.findViewById(R.id.tv_number);
		mHolder.ibDelete = (ImageButton) convertView
			.findViewById(R.id.ib_delete);
		convertView.setTag(mHolder);
	    } else {
		mHolder = (NumberViewHolder) convertView.getTag();
	    }
	    mHolder.tvNumber.setText(numbers.get(position));
	    mHolder.ibDelete.setTag(position);
	    mHolder.ibDelete.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
		    int index = (Integer) v.getTag();
		    numbers.remove(index);
		    adapter.notifyDataSetChanged();
		    if (numbers.size() == 0) {
			popupWindow.dismiss();
		    }
		}
	    });
	    return convertView;
	}
    }

    public class NumberViewHolder {
	public TextView tvNumber;
	public ImageButton ibDelete;
    }
}
