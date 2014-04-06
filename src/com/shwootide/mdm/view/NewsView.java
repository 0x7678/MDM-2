package com.shwootide.mdm.view;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.shwootide.mdm.R;
import com.shwootide.mdm.pojo.News;
import com.shwootide.mdm.service.ExitApplication;
import com.shwootide.mdm.tools.ActivityTools;
import com.shwootide.mdm.tools.WebService;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsView extends ListActivity{
	
	private String TAG = "NewsView";
	ListView  listView ;
	List<News> listApp = new ArrayList<News>();
	AppAdapter appAdapter = new AppAdapter();
	
	
	DataLoadTask dlt = null;
	ProgressDialog dialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		ExitApplication.getInstance().addActivity(this);
		setContentView(R.layout.news_view);
		ActivityTools.headView(NewsView.this,"������Ѷ");
		
		//���� ͼƬ�����⣬ʱ�䣬���ӣ�����ʹ��webview���ʣ���������ͨ��������ʽ�������������˸����Ū��ֱ�ӷ���html���Ʋ��Ǻܺ�
		//������ͼƬ��СҪ�и����ƣ�ͼƬ�����ֵ�����������ݿ⵱��
		//�������ŵ�ʱ����η�������μ���ͼƬ������һ����ͼ�ĵ����£��ֻ��˽���Ҳ���ԣ�
		//��̨�����������ʱ������������ֺ����ͼƬ����������ӣ������ӱȽϺÿ��ƣ�ͼƬ���Ƴߴ�ʹ�С �Լ�������һ�����������������ͼ��
		//ͼƬ������ӱ��⣬���֣����ӣ����Ǻ�̨��չʾ��ֻ�ֻ���չʾ��
		//һ��һ������ӿ��ܲ��Ǻܺ�
		//��̨���ܻ�������⣿
		
//		WebView webView = new WebView(NewsView.this);//ͨ��webview�������ݣ����Բ������ַ�ʽ������������
		
		
		if(dialog == null)
		{
			dialog = ProgressDialog.show(NewsView.this, "��ȴ�...", "���ڼ������ݣ����Ժ�...",true);
		}
		dlt = new DataLoadTask();//��̨���������е�ͼƬ���ӵ�ַ����������һ�����߳�������ͼƬ�����ǹ��Ʋ��Ǻ�����
		dlt.execute("GetAppList","android");//����������ʵĺ������ƺ���Ӧ����
		listView = getListView();
		listView.setAdapter(appAdapter);// listView���������ҳ�����ж����磬û����ʹӱ���ץȡ���ݣ�����û�����ݾ���ʾ�������ұ��������ݣ������ݾ���ʾ��ǰ��������״̬�±������ݣ�
		//�����������£��ȼ�Ȿ��DB�����Ƿ��Ѿ������������ż�¼������У�˵���Ѿ����������ˣ�ֱ�Ӵӱ���ץȡ�����û�оʹ��������أ�
		//����ͼƬ��ʽ�������첽���أ���ֹ�ȴ�ʱ��̫��
		//cnki.sgst.cn
		
		
		
		
	}
	
	class AppAdapter extends BaseAdapter
	{
		public List<News> listApp = new ArrayList<News>();
		public AppAdapter()
		{}
		public AppAdapter(List<News> listApp)
		{
			this.listApp = listApp;//��ʼ��
		}

		@Override
		public int getCount() {
			return listApp.size();//����list����
		}

		@Override
		public Object getItem(int position) {
			return listApp.get(position);//����listԪ��
		}

		@Override
		public long getItemId(int position) {
			return position;//����ID
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view  = convertView;
			ViewHolder holder;
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.news_list_item,
						null);
				holder = new ViewHolder();
				holder.img = (ImageView)view.findViewById(R.id.img);
				holder.title = (TextView)view.findViewById(R.id.title);
				holder.time = (TextView)view.findViewById(R.id.time);// 6026 
				
				view.setTag(holder);
			}else
			{
				holder = (ViewHolder) view.getTag();
			}
			//��Ƭ��������
//			holder.img.setText(listApp.get(position).getApplicationName());//��Ƭ��ʱ�Ȳ�Ҫ���������Կ������ӽ�ȥ
			holder.title.setText(listApp.get(position).getTitle());
			//holder.time.setText(listApp.get(position).getTime());
			return view;
		}
		
		class ViewHolder {
			ImageView img;
			TextView title ,time ;//��listģ���ļ���Ӧ
			}
		
	}
	
	//����Ӧ�ó�����ص�����
		class DataLoadTask extends AsyncTask<String,Integer,String>
		{

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				String functionName = params[0];
				HashMap<String,String> hmParam = new HashMap<String,String>();
				hmParam.put("companyName", "");
				hmParam.put("appSysType", "");
				String strJson = WebService.getRemoteInfo(NewsView.this, functionName, hmParam);
				Log.e(TAG,strJson);
				return strJson;
			}
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(dialog != null)
					dialog.dismiss();
				if(result.equals("-99")){
				    Toast.makeText(NewsView.this, "����������⣬���������Ƿ����ӡ�", 1).show();
				    return;
				}
				
				Gson gson = new Gson();
				JsonReader reader = new JsonReader(new StringReader(result));
				reader.setLenient(true);
				listApp = gson.fromJson(reader, new TypeToken<List<News>>() {}.getType());
				appAdapter = new AppAdapter(listApp);
				listView.setAdapter(appAdapter);
				
				
			}
		}
		

}
