package com.fengs.co_workercontact;

import java.io.IOException;
import java.io.InputStream;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserDetailActivity extends Activity {

	

	public static final int UPDATE=1;
	Handler handler;
	
	public static final String TABLE_NAME="t_EmpBase2";
	SQLiteOpenHelper sqlhelper;
	SQLiteDatabase myDb=null;
	DBHelper myDbhelper=null;
	Context myContext=null;
	Cursor cursor=null;
	





	
	public class MySave implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			update();

		}

	}
	String ID=null,PostMark=null,Email=null,EmpNo=null,Name=null,Phone=null,OfficePhone=null;
	EditText et_Name,et_Phone,et_OfficePhone,et_Post,et_Email,et_EmpNo;
	Button btn_call,btn_message,btn_return,btn_save,btn_update,btn_card;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_detail);
		
		
		myContext=this;
		myDbhelper=DBHelper.getInstance(myContext);
		myDb=myDbhelper.getReadableDatabase();
		
		
		

		et_Name=(EditText)findViewById(R.id.ET_Name);
		et_Phone=(EditText)findViewById(R.id.ET_Phone);
		et_OfficePhone=(EditText)findViewById(R.id.ET_OfficePhone);
		et_Post=(EditText)findViewById(R.id.ET_Post);
		et_Email=(EditText)findViewById(R.id.ET_Email);
		et_EmpNo=(EditText)findViewById(R.id.ET_EmpNo);
		
		/*
		et_Name.setKeyListener(null);
		et_Phone.setKeyListener(null);
		et_OfficePhone.setKeyListener(null);
		et_Post.setKeyListener(null);
		et_Email.setKeyListener(null);
		et_EmpNo.setKeyListener(null);
		*/
		
		btn_call=(Button)findViewById(R.id.BTN_Call);
		btn_call.setOnClickListener(new MyCall());
		btn_card=(Button)findViewById(R.id.BTN_Card);
		btn_card.setOnClickListener(new MyCard());
		btn_message=(Button)findViewById(R.id.BTN_Message);
		btn_message.setOnClickListener(new MyMessage());
		btn_return=(Button)findViewById(R.id.BTN_Return);
		btn_return.setOnClickListener(new MyReturn());
		btn_save=(Button)findViewById(R.id.BTN_Save);
		btn_save.setOnClickListener(new MySave());
		btn_update=(Button)findViewById(R.id.BTN_Update);
		btn_update.setOnClickListener(new MyUpdate());
		ID=getIntent().getStringExtra("intentID");
		PostMark=getIntent().getStringExtra("intentPostMark");
		Email=getIntent().getStringExtra("intentEmail");
		EmpNo=getIntent().getStringExtra("intentEmpNo");
		Name=getIntent().getStringExtra("intentName");
		Phone=getIntent().getStringExtra("intentPhone");
		OfficePhone=getIntent().getStringExtra("intentOfficePhone");
		
		
		
		et_Name.setText(Name);
		et_Phone.setText(Phone);
		et_OfficePhone.setText(OfficePhone);
		et_Post.setText(PostMark);
		et_Email.setText(Email);
		et_EmpNo.setText(EmpNo);
		
		handler=new Handler()
		{

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch(msg.what)
				{
					case UserDetailActivity.UPDATE:Model.Employee employee=new Model.Employee();
					employee=(Model.Employee)msg.getData().get("Employee");
					if(employee.EmpNo!="-1")
					{
						et_Name.setText(employee.Name);
						et_Phone.setText(employee.PhoneNumber);
						et_OfficePhone.setText(employee.OfficeNumber);
						et_Post.setText(employee.Duty);
						et_Email.setText(employee.Email);
						Log.v("tag", employee.Name+"|"+employee.PhoneNumber+"|"+employee.OfficeNumber+"|"+employee.Duty+"|"+employee.Email);
					
					}
					else
					{
						Toast.makeText(UserDetailActivity.this, "获取失败", 1).show();
					}
				}
				super.handleMessage(msg);
			}
			
		};
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_user_detail, menu);
		return true;
	}

	public class MyCall implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(et_Phone.getText().toString().trim().equals(""))
			{
				Toast.makeText(getApplicationContext(), "电话号码为空",
					     Toast.LENGTH_SHORT).show();
			}
			else
			{
			 Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+Phone));  
			 startActivity(intent);  
			}
		}

	}
	public class MyMessage implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(et_Phone.getText().toString().trim().equals(""))
			{
				Toast.makeText(getApplicationContext(), "电话号码为空",
					     Toast.LENGTH_SHORT).show();
				
			}
			else
			{
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.putExtra("address", Phone);
			intent.setType("vnd.android-dir/mms-sms");
			startActivity( intent );
			}
		}

	}
	
	public class MyCard implements OnClickListener {

		StringBuilder cardMessageBuilder;
		@Override
		public void onClick(View v) {
			cardMessageBuilder=new StringBuilder();
			// TODO Auto-generated method stub
			if(!et_EmpNo.getText().toString().trim().equals(""))
			{
				cardMessageBuilder.append("工号:"+et_EmpNo.getText());
			}
			if(!et_Name.getText().toString().trim().equals(""))
			{
				cardMessageBuilder.append("\n姓名:"+et_Name.getText());
			}
			if(!et_Post.getText().toString().trim().equals(""))
			{
				cardMessageBuilder.append("\n职位:"+et_Post.getText());
			}
			if(!et_Phone.getText().toString().trim().equals(""))
			{
				cardMessageBuilder.append("\n手机:"+et_Phone.getText());
			}
			if(!et_OfficePhone.getText().toString().trim().equals(""))
			{
				cardMessageBuilder.append("\n办公室电话:"+et_OfficePhone.getText()+"(公司外拨打请加拨0286539)");
			}
			
			if(!et_Email.getText().toString().trim().equals(""))
			{
				cardMessageBuilder.append("\nEmail:"+et_Email.getText());
			}
			if(!cardMessageBuilder.equals(""))
			{
				
				Uri smsToUri=Uri.parse("smsto:");
				Intent intent = new Intent(Intent.ACTION_VIEW,smsToUri);
				//intent.putExtra("address", Phone);
				intent.putExtra("sms_body",cardMessageBuilder.toString());
				intent.setType("vnd.android-dir/mms-sms");
				startActivity( intent );
			}
		}

	}
	
	
	public class MyReturn implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			finish();
		}
	
	}
	
	
	private void update()
	{
		try
		{
			//myDb.rawQuery("update [t_EmpBase2] set EmpNo='"+et_EmpNo.getText().toString().trim()+"',Name='"+et_Name.getText().toString().trim()+"',Phone='"+et_Phone.getText().toString().trim()+"',Email='"+et_Email.getText().toString().trim()+"',PostMark='"+et_Post.getText().toString().trim()+"',OfficePhone='"+et_OfficePhone.getText().toString().trim()+"' where ID='"+ID.toString().trim()+"';",null);
			
			String[] whereArgs = {ID};
			ContentValues values = new ContentValues();
			values.put("EmpNo", et_EmpNo.getText().toString().trim());
			values.put("Name", et_Name.getText().toString().trim());
			values.put("Phone", et_Phone.getText().toString().trim());
			values.put("OfficePhone", et_OfficePhone.getText().toString().trim());
			values.put("PostMark", et_Post.getText().toString().trim());
			values.put("Email", et_Email.getText().toString().trim());
			
			myDb.update(TABLE_NAME, values, "ID=?", whereArgs);
			Toast toast = Toast.makeText(getApplicationContext(),
				     "保存成功", Toast.LENGTH_LONG);
				   toast.setGravity(Gravity.CENTER, 0, 0);
				 
				   toast.show();
				   finish();
		}
		catch (Exception ex)
		{
			Toast toast = Toast.makeText(getApplicationContext(),
				     "保存失败", Toast.LENGTH_LONG);
				   toast.setGravity(Gravity.CENTER, 0, 0);
				 
				   toast.show();
		}
	}
	
	
	public class MyUpdate implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			try {
				InputStream inStream=getAssets().open("GetEmployee.xml");
				MyThread thread=new MyThread(inStream,EmpNo,handler);
				thread.start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
}
