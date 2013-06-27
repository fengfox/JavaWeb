package com.fengs.co_workercontact;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;





import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	class ContactInfo
	{
		public String ID;
		public String Name;
		public String EmpNo;
		public String PostMark;
		public String Phone;
		public String OfficePhone;
		public String Email;
		public ContactInfo() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}
	public static final String TABLE_NAME="t_EmpBase2";
	
	//声明变量
	
	
	
	//声明控件相关
	Button btn_ResearchByName;
	EditText et_Name;
	
	//声明listview相关变量
	ListView lv_maindetail=null;
	ArrayList<HashMap<String,Object>> listItem;
	SimpleAdapter listItemAdapter;
	//申明数据库相关变量
	SQLiteOpenHelper sqlhelper;
	ArrayList<ContactInfo> contactinfos,tmpinfo;
	ContactInfo contactinfo;
	SQLiteDatabase myDb=null;
	DBHelper myDbhelper=null;
	Context myContext=null;
	Cursor cursor=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Toast toast = Toast.makeText(getApplicationContext(),
			     "输入工号或姓名或手机或办公电话或职位进行查询信息", Toast.LENGTH_LONG);
			   toast.setGravity(Gravity.CENTER, 0, 0);
			 
			   toast.show();
		
		
		copyDataBase();
		//listview绑定
		lv_maindetail=(ListView)findViewById(R.id.LV_MainDetail);
		//生成对象
	
		contactinfos=new ArrayList<ContactInfo>();
		myContext=this;
		myDbhelper=DBHelper.getInstance(myContext);
		myDb=myDbhelper.getReadableDatabase();
		cursor=find();
		btn_ResearchByName=(Button)findViewById(R.id.BTN_Research);
		btn_ResearchByName.setOnClickListener(new ResearchClick());
		et_Name=(EditText)findViewById(R.id.ET_Research);
		
		
		listItem=new ArrayList<HashMap<String,Object>>();
		lv_maindetail.setOnItemClickListener(new ItemClick());
		listItemAdapter = new SimpleAdapter(this,listItem,R.layout.lv_maindetail_items,new String[]{"TV_ID","TV_EmpNo","TV_Name","TV_Post"},
    			new int[]{R.id.TV_ID,R.id.TV_EmpNo,R.id.TV_Name,R.id.TV_Post});
		lv_maindetail.setAdapter(listItemAdapter);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
	public Cursor find()
	{
		Cursor cursor=myDb.query(TABLE_NAME,null,null,null,null,null,null);
		if(cursor!=null)
		{
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	public class ResearchClick implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(et_Name.getText().toString().trim().equals(""))
			{
				
			}
			else
			{
			listItem.clear();
			contactinfos.clear();
			listItemAdapter.notifyDataSetChanged();
			cursor=ResearchByNEP(et_Name.getText().toString().trim());
			if(cursor!=null)
			{
				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
				{
					contactinfo=new ContactInfo();
					contactinfo.Email=cursor.getString(cursor.getColumnIndex("Email"));
					contactinfo.EmpNo=cursor.getString(cursor.getColumnIndex("EmpNo"));
					contactinfo.ID=cursor.getString(cursor.getColumnIndex("ID"));
					contactinfo.Name=cursor.getString(cursor.getColumnIndex("Name"));
					contactinfo.OfficePhone=cursor.getString(cursor.getColumnIndex("OfficePhone"));
					contactinfo.PostMark=cursor.getString(cursor.getColumnIndex("PostMark"));
					contactinfo.Phone=cursor.getString(cursor.getColumnIndex("Phone"));
					contactinfos.add(contactinfo);
				}
				for(int i=0;i<contactinfos.size();i++)
				{
					contactinfo=new ContactInfo();
					HashMap<String,Object> map=new HashMap<String,Object>();
					contactinfo=contactinfos.get(i);
					map.put("TV_ID",contactinfo.ID);
					map.put("TV_EmpNo", contactinfo.EmpNo);
					map.put("TV_Name", contactinfo.Name);
					map.put("TV_Post", contactinfo.PostMark);
					listItem.add(map);
				}
				listItemAdapter.notifyDataSetChanged();
			}
			}
		}

	}




	
	
	
	//点击ListView里的Item事件
	public class ItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			
			String strID;
			HashMap<String,Object> map=new HashMap<String,Object>();
			map=listItem.get(arg2);
			strID=map.get("TV_ID").toString().trim();
			for(int i=0;i<contactinfos.size();i++)
			{
				contactinfo=new ContactInfo();
				contactinfo=contactinfos.get(i);
				if(contactinfo.ID.equals(strID))
				{
					intent.putExtra("intentID",contactinfo.ID);
					intent.putExtra("intentEmail", contactinfo.Email);
					intent.putExtra("intentName", contactinfo.Name);
					intent.putExtra("intentPostMark", contactinfo.PostMark);
					intent.putExtra("intentPhone", contactinfo.Phone);
					intent.putExtra("intentOfficePhone", contactinfo.OfficePhone);
					intent.putExtra("intentEmpNo", contactinfo.EmpNo);
					break;
				}
			}
			
			intent.setClass(MainActivity.this, com.fengs.co_workercontact.UserDetailActivity.class);
		
			
			startActivity(intent);
		}

	}
     public Cursor ResearchByName(String string) {
		Cursor tmpcursor;
		tmpcursor=myDb.rawQuery("select * from t_EmpBase2 where Name like '%"+string+"%'",null);
		return tmpcursor;
	}
     
     private Cursor ResearchByNEP(String strNEP)
     {
    	 Cursor tmpcursor;
 		tmpcursor=myDb.rawQuery("select * from t_EmpBase2 where Name like '%"+strNEP+"%' or PostMark like '%"+strNEP+"%' or Phone='"+strNEP+"' or OfficePhone='"+strNEP+"' or EmpNo='"+strNEP+"';",null);
 		return tmpcursor;
     }
     
     
     private void copyDataBase() 
     {   
    	String DATABASE_PATH = "/data/data/com.fengs.co_workercontact/databases/";
    	String dbName = "Co-workerContact.db";
    	String dbFileName = DATABASE_PATH + dbName;
    	boolean isDbExist = false ;
        SQLiteDatabase checkDB = null;
    	try {       
    	checkDB = SQLiteDatabase.openDatabase(dbFileName, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    	} catch (SQLiteException e) {  
    	//	Log.e("qt","SQLiteException !" + e.toString());
    	} finally {      if (checkDB != null) {          isDbExist = true ; 
    	checkDB.close(); 
    	}   }    //数据库不存在则拷贝  
    	if(!isDbExist){      
    		//Log.e("qt"," db is NOT exist");
    	File dir = new File(DATABASE_PATH);
    	if (!dir.exists())// 判断数据库文件夹是否存在，不存在则新建
    	dir.mkdir();      
    	FileOutputStream os = null;    
    	try {          os = new FileOutputStream(dbFileName);//写入流 
    	} catch (FileNotFoundException e) {          e.printStackTrace();    
    	}      
    	InputStream is = null; 
    	try {          is = this.getAssets().open(dbName);   
    	} catch (IOException e1) {     
    	e1.printStackTrace();      
    	}       byte[] buffer = new byte[1024*1024];  
        int count = 0;   
    	try {         while ((count = is.read(buffer)) > 0) {  
    	os.write(buffer, 0, count);        
    	os.flush();     
    	}      } catch (IOException e) {          e.printStackTrace();  
        }       try {          is.close();  
    	os.close();     
    	} catch (IOException e) {          e.printStackTrace(); 
    	}    }} 

	
}
