package com.shwootide.mdm.view;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.sqlite.Table;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.http.AbFileHttpResponseListener;
import com.ab.http.AbHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.shwootide.mdm.R;
import com.shwootide.mdm.pojo.Attachments;
import com.shwootide.mdm.service.ExitApplication;
import com.shwootide.mdm.tools.ActivityTools;

@Table(name = "file")
public class MainNoticeView extends AbActivity {
	String name;
	String title;
	String attachments;
	String AnnounceContent;
	String time;
	int max = 100;
	int progress = 0;
	FinalDb db;
	String path=null;
	private AbHttpUtil mAbHttpUtil = null;
	Gson json = new Gson();
	List<Attachments> listatt = new ArrayList<Attachments>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ExitApplication.getInstance().addActivity(this);
		setContentView(R.layout.mainnotice);
		ActivityTools.headView(MainNoticeView.this, "����");
		mAbHttpUtil = AbHttpUtil.getInstance(this);
		db = FinalDb.create(this);
		Intent intent = getIntent();
		name = intent.getStringExtra("name");
		AnnounceContent = intent.getStringExtra("AnnounceContent");
		title = intent.getStringExtra("title");
		time = intent.getStringExtra("time");
		attachments = intent.getStringExtra("attachments");
		initView();
	}

	public void initView() {
		
		JsonReader jsonReader = new JsonReader(new StringReader(attachments));
		jsonReader.setLenient(true);
		listatt = json.fromJson(jsonReader, new TypeToken<List<Attachments>>() {
		}.getType());
		TextView tv_one = (TextView) findViewById(R.id.mainnotice_tv_one);
		TextView tv_two = (TextView) findViewById(R.id.mainnotice_tv_two);
		TextView tv_three = (TextView) findViewById(R.id.mainnotice_tv_three);
		TextView tv_four = (TextView) findViewById(R.id.mainnotice_tv_four);
		ListView lv = (ListView) findViewById(R.id.mainnotice_linear);

		tv_one.setText(title);
		tv_two.setText(time);
		tv_three.setText(name);
		tv_four.setText(AnnounceContent);
		lv.setAdapter(new MyAdapter(listatt));
		
		
		final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		 View prompt_noticeview=mInflater.inflate(R.layout.prompt_noticeview, null);
		
		 final CheckBox checkb=(CheckBox) prompt_noticeview.findViewById(R.id.checkbox);
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
				builder.setView(prompt_noticeview);
				builder.setTitle("��ʾ..");
				builder.setCancelable(false);
				final AlertDialog dialog=builder.create();
			dialog.setButton("ȷ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(checkb.isChecked()){
						preferences.edit().putBoolean("checkbox",true).commit();
					}else{
						preferences.edit().putBoolean("checkbox",false).commit();
					}
					Log.i("ceshi", ".........."+path);
					startActivity(openFile(path));
					dialog.dismiss();
				}
			});
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 boolean checkbox =preferences.getBoolean("checkbox", false);
				String where = "url=\"" + listatt.get(arg2).getDownloadUrl()
						+ "\"";
				List<com.shwootide.mdm.pojo.File> file = db.findAllByWhere(
						com.shwootide.mdm.pojo.File.class, where);
				if (file.size() == 0) {
					Log.i("ceshi", "..........");
					fileDownloader(arg1, arg2);
				} else {
				    path = file.get(0).getPath();
				    Log.i("ceshi", ".........."+path);
					if(!checkbox){
						dialog.show();
					}else{		
					    try {
						startActivity(openFile(path));
					    } catch (Exception e) {
						showToast("�ܱ�Ǹ������ֻ��Ͽ���û�д򿪸��ļ���Ӧ�ã�");
					    }	
					}
				}

			}
			
		});


			
//				Intent intent = new Intent(); 

				//���������Activity
//				 intent.setComponent(new ComponentName("com.demo.filebrowser",
//				 "com.demo.filebrowser.FileBrowser")); 
//				//����1 ��ʾ���ݵ��ļ��������path,����2��ʾ����
//				intent.setDataAndType(Uri.parse("file:///mnt/sdcard/Android"), "directory");
//				 startActivity(intent);
				 

				//�ļ���������������ļ���������������Ĵ����߼����С� 

//				Intent intent = getIntent();
//				 String type = intent.getType();
//				 Uri uri = intent.getData();
//				 String data = uri == null ? null : Uri.decode(uri.toString());
//				 if (type != null) {
//				 if (type.indexOf("directory") == 0) { 
//				    // ���type���ʹ��ݹ�������directory�����ָ�����ļ�·��
//				if(data.startsWith("file:///")){
//				 data = data.substring("file://".length());
//				 }
//				 mCurrentPath = data;//��¼���ݹ�����·��Ϊ �ļ��������ǰ·��
//				} 
//				 }


				
	
	
	
	}

	class MyAdapter extends BaseAdapter {
		private List<Attachments> listatt = new ArrayList<Attachments>();

		public MyAdapter(List<Attachments> listatt) {
			this.listatt = listatt;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listatt.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return listatt.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			Holder holder;
			if (view == null) {
				holder = new Holder();
				view = mInflater.inflate(R.layout.mainnotice_item, null);
				holder.tv_one = (TextView) view
						.findViewById(R.id.mainnotice_item_tv_one);
				holder.tv_two = (TextView) view
						.findViewById(R.id.mainnotice_item_tv_two);
				holder.tv_three = (TextView) view
						.findViewById(R.id.mainnotice_item_tv_three);
				view.setTag(holder);
				holder.progress = (ProgressBar) view
						.findViewById(R.id.received_progress);
			} else {
				holder = (Holder) view.getTag();
			}
			holder.tv_one.setText(listatt.get(position).getName());
			holder.tv_two.setText(listatt.get(position).getSize());
			String where = "url=\"" + listatt.get(position).getDownloadUrl()
					+ "\"";
			List<com.shwootide.mdm.pojo.File> file = db.findAllByWhere(
					com.shwootide.mdm.pojo.File.class, where);
			if (file.size() != 0) {
				holder.progress.setProgress(100);
				holder.tv_three.setVisibility(View.VISIBLE);
				holder.tv_three.setText("�������");
			}
			return view;
		}

	}

	class Holder {
		TextView tv_one, tv_two, tv_three;
		ProgressBar progress;

	}

	public void fileDownloader(View view, int position) {
		final ProgressBar progressBar = (ProgressBar) view
				.findViewById(R.id.received_progress);
		final TextView tv = (TextView) view
				.findViewById(R.id.mainnotice_item_tv_three);
		final String url = listatt.get(position).getDownloadUrl();
		Log.i("ceshi", url);
		mAbHttpUtil.get(url, new AbFileHttpResponseListener(url) {
			@Override
			public void onSuccess(int statusCode, File file) {
				String path = file.getAbsolutePath();
				com.shwootide.mdm.pojo.File myFile = new com.shwootide.mdm.pojo.File();
				myFile.setPath(path);
				Log.i("path", path);
				myFile.setUrl(url);
				db.save(myFile);
				tv.setText("�������");
				tv.setVisibility(View.VISIBLE);
			}

			// ��ʼִ��ǰ
			@Override
			public void onStart() {
				tv.setText("��������");
				tv.setVisibility(View.VISIBLE);
				progressBar.setMax(max);
				progressBar.setProgress(progress);

			}

			// ʧ�ܣ�����
			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
			    Log.i("ceshi", statusCode+"");
				tv.setText("����ʧ��");
				tv.setVisibility(View.VISIBLE);
				Log.i("ceshi", "...����ʧ��....");
			}

			// ���ؽ���
			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				progressBar.setProgress(bytesWritten / (totalSize / max));
			}

			// ��ɺ���ã�ʧ�ܣ��ɹ�
			public void onFinish() {

			};

		});

	};

	public Intent openFile(String filePath) {

		File file = new File(filePath);

		if (!file.exists()){
			Log.i("file","11111111");
			
			return null;
		}
		if ((file == null)){
			Log.i("file","2222222222");
			
			return null;
		}
		if (file.isDirectory()){
			Log.i("file","33333333");
			
			return null;
		}
		
		/* ȡ����չ�� */
		String end = file
				.getName()
				.substring(file.getName().lastIndexOf(".") + 1,
						file.getName().length()).toLowerCase();
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			return getAudioFileIntent(filePath);
		} else if (end.equals("3gp") || end.equals("mp4")) {
			return getAudioFileIntent(filePath);
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			return getImageFileIntent(filePath);
		} else if (end.equals("apk")) {
			return getApkFileIntent(filePath);
		} else if (end.equals("ppt")) {
			return getPptFileIntent(filePath);
		} else if (end.equals("xls")) {
			return getExcelFileIntent(filePath);
		} else if (end.equals("doc")) {
			return getWordFileIntent(filePath);
		} else if (end.equals("pdf")) {
			return getPdfFileIntent(filePath);
		} else if (end.equals("chm")) {
			return getChmFileIntent(filePath);
		} else if (end.equals("txt")) {
			return getTextFileIntent(filePath, false);
		} else {
			return getAllIntent(filePath);
		}
		
	}

	// Android��ȡһ�����ڴ�APK�ļ���intent
	public static Intent getAllIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "*/*");
		return intent;
	}

	// Android��ȡһ�����ڴ�APK�ļ���intent
	public static Intent getApkFileIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		return intent;
	}

	// Android��ȡһ�����ڴ�VIDEO�ļ���intent
	public static Intent getVideoFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	// Android��ȡһ�����ڴ�AUDIO�ļ���intent
	public static Intent getAudioFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	// Android��ȡһ�����ڴ�Html�ļ���intent
	public static Intent getHtmlFileIntent(String param) {

		Uri uri = Uri.parse(param).buildUpon()
				.encodedAuthority("com.android.htmlfileprovider")
				.scheme("content").encodedPath(param).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	// Android��ȡһ�����ڴ�ͼƬ�ļ���intent
	public static Intent getImageFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	// Android��ȡһ�����ڴ�PPT�ļ���intent
	public static Intent getPptFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	// Android��ȡһ�����ڴ�Excel�ļ���intent
	public static Intent getExcelFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	// Android��ȡһ�����ڴ�Word�ļ���intent
	public static Intent getWordFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	// Android��ȡһ�����ڴ�CHM�ļ���intent
	public static Intent getChmFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/x-chm");
		return intent;
	}

	// Android��ȡһ�����ڴ��ı��ļ���intent
	public static Intent getTextFileIntent(String param, boolean paramBoolean) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (paramBoolean) {
			Uri uri1 = Uri.parse(param);
			intent.setDataAndType(uri1, "text/plain");
		} else {
			Uri uri2 = Uri.fromFile(new File(param));
			intent.setDataAndType(uri2, "text/plain");
		}
		return intent;
	}

	// Android��ȡһ�����ڴ�PDF�ļ���intent
	public static Intent getPdfFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}

	
//
//public static String readDOC(String path) {
//                // ������������ȡdoc�ļ�
//                FileInputStream in;
//                String text = null;
////                Environment.getExternalStorageDirectory().getAbsolutePath()+ "/aa.doc")
//                try {
//                        in = new FileInputStream(new File(path));
//                        int a= in.available();
//                        WordExtractor extractor = null;
//                        // ����WordExtractor
//                        extractor = new WordExtractor();
//                        // ��doc�ļ�������ȡ
//                        text = extractor.extractText(in);
//                        System.out.println("�����õ��Ķ���"+text);
//                } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                } catch (Exception e) {
//                        e.printStackTrace();
//                }
//                if (text == null) {
//                        text = "�����ļ���������";
//                }
//                return text;
//        }
//
////����xls :
//
//public static String readXLS(String path) {
//                String str = "";
//                try {
//                        Workbook workbook = null;
//                        workbook = Workbook.getWorkbook(new File(path));
//                        Sheet sheet = workbook.getSheet(0);
//                        Cell cell = null;
//                        int columnCount = sheet.getColumns();
//                        int rowCount = sheet.getRows();
//                        for (int i = 0; i < rowCount; i++) {
//                                for (int j = 0; j < columnCount; j++) {
//                                        cell = sheet.getCell(j, i);
//                                        String temp2 = "";
//                                        if (cell.getType() == CellType.NUMBER) {
//                                                temp2 = ((NumberCell) cell).getValue() + "";
//                                        } else if (cell.getType() == CellType.DATE) {
//                                                temp2 = "" + ((DateCell) cell).getDate();
//                                        } else {
//                                                temp2 = "" + cell.getContents();
//                                        }
//                                        str = str + "  " + temp2;
//                                }
//                                str += "\n";
//                        }
//                        workbook.close();
//                } catch (Exception e) {
//                }
//                if (str == null) {
//                        str = "�����ļ���������";
//                }
//                return str;
//        }
//
////����docx :
//
//public static String readDOCX(String path) {
//                String river = "";
//                try {
//                        ZipFile xlsxFile = new ZipFile(new File(path));
//                        ZipEntry sharedStringXML = xlsxFile.getEntry("word/document.xml");
//                        InputStream inputStream = xlsxFile.getInputStream(sharedStringXML);
//                        XmlPullParser xmlParser = Xml.newPullParser();
//                        xmlParser.setInput(inputStream, "utf-8");
//                        int evtType = xmlParser.getEventType();
//                        while (evtType != XmlPullParser.END_DOCUMENT) {
//                                switch (evtType) {
//                                case XmlPullParser.START_TAG:
//                                        String tag = xmlParser.getName();
//                                        System.out.println(tag);
//                                        if (tag.equalsIgnoreCase("t")) {
//                                                river += xmlParser.nextText() + "\n";
//                                        }
//                                        break;
//                                case XmlPullParser.END_TAG:
//                                        break;
//                                default:
//                                        break;
//                                }
//                                evtType = xmlParser.next();
//                        }
//                } catch (ZipException e) {
//                        e.printStackTrace();
//                } catch (IOException e) {
//                        e.printStackTrace();
//                } catch (XmlPullParserException e) {
//                        e.printStackTrace();
//                }
//                if (river == null) {
//                        river = "�����ļ���������";
//                }
//
//                return river;
//        }
//
////����xlsx :
//
//public String readXLSX(String path) {
//                String str = "";
//                String v = null;
//                boolean flat = false;
//                List<String> ls = new ArrayList<String>();
//                try {
//                        ZipFile xlsxFile = new ZipFile(new File(path));
//                        ZipEntry sharedStringXML = xlsxFile
//                                        .getEntry("xl/sharedStrings.xml");
//                        InputStream inputStream = xlsxFile.getInputStream(sharedStringXML);
//                        XmlPullParser xmlParser = Xml.newPullParser();
//                        xmlParser.setInput(inputStream, "utf-8");
//                        int evtType = xmlParser.getEventType();
//                        while (evtType != XmlPullParser.END_DOCUMENT) {
//                                switch (evtType) {
//                                case XmlPullParser.START_TAG:
//                                        String tag = xmlParser.getName();
//                                        if (tag.equalsIgnoreCase("t")) {
//                                                ls.add(xmlParser.nextText());
//                                        }
//                                        break;
//                                case XmlPullParser.END_TAG:
//                                        break;
//                                default:
//                                        break;
//                                }
//                                evtType = xmlParser.next();
//                        }
//                        ZipEntry sheetXML = xlsxFile.getEntry("xl/worksheets/sheet1.xml");
//                        InputStream inputStreamsheet = xlsxFile.getInputStream(sheetXML);
//                        XmlPullParser xmlParsersheet = Xml.newPullParser();
//                        xmlParsersheet.setInput(inputStreamsheet, "utf-8");
//                        int evtTypesheet = xmlParsersheet.getEventType();
//                        while (evtTypesheet != XmlPullParser.END_DOCUMENT) {
//                                switch (evtTypesheet) {
//                                case XmlPullParser.START_TAG:
//                                        String tag = xmlParsersheet.getName();
//                                        if (tag.equalsIgnoreCase("row")) {
//                                        } else if (tag.equalsIgnoreCase("c")) {
//                                                String t = xmlParsersheet.getAttributeValue(null, "t");
//                                                if (t != null) {
//                                                        flat = true;
//                                                        System.out.println(flat + "��");
//                                                } else {
//                                                        System.out.println(flat + "û��");
//                                                        flat = false;
//                                                }
//                                        } else if (tag.equalsIgnoreCase("v")) {
//                                                v = xmlParsersheet.nextText();
//                                                if (v != null) {
//                                                        if (flat) {
//                                                                str += ls.get(Integer.parseInt(v)) + "  ";
//                                                        } else {
//                                                                str += v + "  ";
//                                                        }
//                                                }
//                                        }
//                                        break;
//                                case XmlPullParser.END_TAG:
//                                        if (xmlParsersheet.getName().equalsIgnoreCase("row")
//                                                        && v != null) {
//                                                str += "\n";
//                                        }
//                                        break;
//                                }
//                                evtTypesheet = xmlParsersheet.next();
//                        }
//                        System.out.println(str);
//                } catch (ZipException e) {
//                        e.printStackTrace();
//                } catch (IOException e) {
//                        e.printStackTrace();
//                } catch (XmlPullParserException e) {
//                        e.printStackTrace();
//                }
//                if (str == null) {
//                        str = "�����ļ���������";
//                }
//                showToast(str);
//                return str;
//        }
//
//
//
////����pptx :
//
//
//public static String readPPTX(String path) {
//                List<String> ls = new ArrayList<String>();
//                String river = "";
//                ZipFile xlsxFile = null;
//                try {
//                        xlsxFile = new ZipFile(new File(path));// pptx���ն�ȡzip��ʽ��ȡ
//                } catch (ZipException e1) {
//                        e1.printStackTrace();
//                } catch (IOException e1) {
//                        e1.printStackTrace();
//                }
//                try {
//                        ZipEntry sharedStringXML = xlsxFile.getEntry("[Content_Types].xml");// �ҵ����������ݵ��ļ�
//                        InputStream inputStream = xlsxFile.getInputStream(sharedStringXML);// ���õ��ļ���
//                        XmlPullParser xmlParser = Xml.newPullParser();// ʵ����pull
//                        xmlParser.setInput(inputStream, "utf-8");// �����Ž�pull��
//                        int evtType = xmlParser.getEventType();// �õ���ǩ���͵�״̬
//                        while (evtType != XmlPullParser.END_DOCUMENT) {// ѭ����ȡ��
//                                switch (evtType) {
//                                case XmlPullParser.START_TAG: // �жϱ�ǩ��ʼ��ȡ
//                                        String tag = xmlParser.getName();// �õ���ǩ
//                                        if (tag.equalsIgnoreCase("Override")) {
//                                                String s = xmlParser
//                                                                .getAttributeValue(null, "PartName");
//                                                if (s.lastIndexOf("/ppt/slides/slide") == 0) {
//                                                        ls.add(s);
//                                                }
//                                        }
//                                        break;
//                                case XmlPullParser.END_TAG:// ��ǩ��ȡ����
//                                        break;
//                                default:
//                                        break;
//                                }
//                                evtType = xmlParser.next();// ��ȡ��һ����ǩ
//                        }
//                } catch (ZipException e) {
//                        e.printStackTrace();
//                } catch (IOException e) {
//                        e.printStackTrace();
//                } catch (XmlPullParserException e) {
//                        e.printStackTrace();
//                }
//                for (int i = 1; i < (ls.size() + 1); i++) {// ������6�Żõ�Ƭ
//                        river += "��" + i + "�š�������������������������������" + "\n";
//                        try {
//                                ZipEntry sharedStringXML = xlsxFile.getEntry("ppt/slides/slide"
//                                                + i + ".xml");// �ҵ����������ݵ��ļ�
//                                InputStream inputStream = xlsxFile
//                                                .getInputStream(sharedStringXML);// ���õ��ļ���
//                                XmlPullParser xmlParser = Xml.newPullParser();// ʵ����pull
//                                xmlParser.setInput(inputStream, "utf-8");// �����Ž�pull��
//                                int evtType = xmlParser.getEventType();// �õ���ǩ���͵�״̬
//                                while (evtType != XmlPullParser.END_DOCUMENT) {// ѭ����ȡ��
//                                        switch (evtType) {
//                                        case XmlPullParser.START_TAG: // �жϱ�ǩ��ʼ��ȡ
//                                                String tag = xmlParser.getName();// �õ���ǩ
//                                                if (tag.equalsIgnoreCase("t")) {
//                                                        river += xmlParser.nextText() + "\n";
//                                                }
//                                                break;
//                                        case XmlPullParser.END_TAG:// ��ǩ��ȡ����
//                                                break;
//                                        default:
//                                                break;
//                                        }
//                                        evtType = xmlParser.next();// ��ȡ��һ����ǩ
//                                }
//                        } catch (ZipException e) {
//                                e.printStackTrace();
//                        } catch (IOException e) {
//                                e.printStackTrace();
//                        } catch (XmlPullParserException e) {
//                                e.printStackTrace();
//                        }
//                }
//                if (river == null) {
//                        river = "�����ļ���������";
//                }
//                return river;
//        }

}
