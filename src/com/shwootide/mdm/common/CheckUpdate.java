package com.shwootide.mdm.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ab.http.AbFileHttpResponseListener;
import com.ab.http.AbHttpUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class CheckUpdate {
    private AbHttpUtil mAbHttpUtil = null;
	 private Handler handler = new Handler();	
	private int newVerCode = 0;
	private String newVerName = "";
	private static final String TAG = "Update";
	private Context mContext ;
	public ProgressDialog pBar;
	public CheckUpdate(Context c,String url)
	{
		this.mContext = c;
		mAbHttpUtil = AbHttpUtil.getInstance(c);
		 doNewVersionUpdate(url);
	}

	 private void doNewVersionUpdate(final String url) {
	         StringBuffer sb = new StringBuffer();
	         sb.append("ȷ����װ�����?");
	         Dialog dialog = new AlertDialog.Builder(this.mContext)
	                         .setTitle("��ʾ...")
	                         .setMessage(sb.toString())
	                         // ��������
	                         .setPositiveButton("ȷ��",// ����ȷ����ť
	                                         new DialogInterface.OnClickListener() {

	                                                 @Override
	                                                 public void onClick(DialogInterface dialog,
	                                                                 int which) {
	                                                         pBar = new ProgressDialog(mContext);
	                                                         pBar.setTitle("��������");
	                                                         pBar.setMessage("���Ժ�...");
	                                                         pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	                                                         downFile(url);
	                                                         dialog.dismiss();
	                                                 }

	                                         })
	                         .setNegativeButton("��",
	                                         new DialogInterface.OnClickListener() {
	                                                 public void onClick(DialogInterface dialog,
	                                                                 int whichButton) {
	                                                         // ���"ȡ��"��ť֮���˳�����
	                                                     dialog.dismiss();    
	                                                	 //this.finish();
	                                                 }
	                                         }).create();// ����
	         // ��ʾ�Ի���
	         dialog.show();
	         
	         
	 }

	 void downFile(final String url) {
	         pBar.show();
	         Log.i("downFile",url);
	         mAbHttpUtil.get(url, new AbFileHttpResponseListener(url) {
			@Override
			public void onSuccess(int statusCode, File file) {
			    pBar.dismiss();
			    Intent intent = new Intent(Intent.ACTION_VIEW); 
	        		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
	        		 intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive"); 
	        		mContext.startActivity(intent);
			}
			// ��ʼִ��ǰ
			@Override
			public void onStart() {
				
			}
			// ʧ�ܣ�����
			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
			    pBar.dismiss();
			    Log.i("downFile",statusCode+"");
			    Toast.makeText(mContext, "����ʧ��", 1).show();
			}

			// ���ؽ���
			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				
			}

			// ��ɺ���ã�ʧ�ܣ��ɹ�
			public void onFinish() {

			};

		});

	 }




	
	

}
