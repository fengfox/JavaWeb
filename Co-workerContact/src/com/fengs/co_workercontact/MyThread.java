package com.fengs.co_workercontact;

import java.io.InputStream;

import Model.Employee;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MyThread extends Thread {
	
	private String mEmpNo;
	private Handler mhandler;
	private InputStream mStream;
	public MyThread(InputStream inStream,String EmpNo,Handler handler)
	{
		mStream=inStream;
		mEmpNo=EmpNo;
		mhandler=handler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			
 			Model.Employee employee=new Model.Employee();
			employee=Common.Tools.GetEmployee(mStream,mEmpNo);
			Message message=new Message();
			message.what=UserDetailActivity.UPDATE;
			
			Bundle bundle=new Bundle();
			bundle.putSerializable("Employee", employee);
			message.setData(bundle);
			mhandler.sendMessage(message);
		}
		catch(Exception ex)
		{
			Log.v("tag",ex.getMessage());
		}
	}

}
