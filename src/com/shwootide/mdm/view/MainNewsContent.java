package com.shwootide.mdm.view;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import com.ab.activity.AbActivity;
import com.shwootide.mdm.R;
import com.shwootide.mdm.service.ExitApplication;
import com.shwootide.mdm.tools.ActivityTools;

public class MainNewsContent extends AbActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ExitApplication.getInstance().addActivity(this);
		String content=getIntent().getStringExtra("data");
		setContentView(R.layout.myweb);
		 ActivityTools.headView(MainNewsContent.this,"����");
		WebView veb=(WebView) findViewById(R.id.myweb);
		DisplayMetrics  dm = new DisplayMetrics();    
		getWindowManager().getDefaultDisplay().getMetrics(dm);    
		int screenWidth = dm.widthPixels;              
		int screenHeight = dm.heightPixels;
		
		//veb.setLayoutParams(new LayoutParams(screenWidth,screenHeight));
//		veb.getSettings().setUseWideViewPort(true);
//		veb.getSettings().setLoadWithOverviewMode(true);
//		veb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//		  WebSettings webSettings =veb.getSettings();
//		   webSettings.setJavaScriptEnabled(true);
//		   webSettings.setBuiltInZoomControls(true); 
//		   setZoomControlGone(veb);
		
		WebSettings webSettings = veb.getSettings(); 
		webSettings.setJavaScriptEnabled(true);
		//���ÿ��Է����ļ�
		webSettings.setAllowFileAccess(true);
		//���ÿ���֧������
		webSettings.setSupportZoom(true);
		//����Ĭ�����ŷ�ʽ�ߴ���far
		webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
		//���ó������Ź���
		webSettings .setBuiltInZoomControls(true);
		//����ͼƬ����Ӧ
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		//veb.setInitialScale(5);
		webSettings.setDefaultFontSize(15);
		veb.getSettings().setDefaultTextEncodingName("UTF-8");
		   //veb.setMovementMethod(ScrollingMovementMethod.getInstance());
		   //Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/B.TTF");
				   //veb.setTypeface(tf);

//		    String mRequest = new String(content.getBytes("GBK"),"UTF-8");
//		    veb.loadData(mRequest, "text/html","UTF-8") ;
		    veb.loadDataWithBaseURL(null, content, "text/html",
                            "utf-8", null);
		
		
			
			
	
//		   ����mRequest��HttpClient�����õ���ҳ��html���ݣ������������html��ҳת�����룬�ӡ�8859_1��ת���ɡ�UTF-8����
		  //���ñ���
//		  mWebView.getSettings().setDefaultTextEncodingName("utf-8");

		
	}
	public void setZoomControlGone(View view) {
		  Class classType;
		  Field field;
		  try {
		   classType = WebView.class;
		   field = classType.getDeclaredField("mZoomButtonsController");
		   field.setAccessible(true);
		   ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(view);
		   mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
		   try {
		    field.set(view, mZoomButtonsController);
		   } catch (IllegalArgumentException e) {
		    e.printStackTrace();
		   } catch (IllegalAccessException e) {
		    e.printStackTrace();
		   }
		  } catch (SecurityException e) {
		   e.printStackTrace();
		  } catch (NoSuchFieldException e) {
		   e.printStackTrace();
		  }
		 }
}
