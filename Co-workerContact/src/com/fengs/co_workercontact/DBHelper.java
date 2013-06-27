package com.fengs.co_workercontact;




import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	 String DATABASE_PATH = "/data/data/com.fengs.co_workercontact/databases/";

	private static DBHelper mInstance = null;
	//数据库名称
	public static final String DATABASE_NAME="Co-workerContact.db";
	//数据库版本号
	public static final int DATABASE_VERSION=1;
	public static final String NAME_TABLE_CREATE="CREATE TABLE [t_EmpBase2] (ID integer PRIMARY KEY AUTOINCREMENT NOT NULL,Name	nvarchar(30) NOT NULL COLLATE NOCASE,EmpNo nvarchar(30) COLLATE NOCASE,PostMark nvarchar(50) COLLATE NOCASE,Phone nvarchar(20) COLLATE NOCASE,OfficePhone nvarchar(20) COLLATE NOCASE,Email nvarchar(50) COLLATE NOCASE);";

		
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(NAME_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	/*单例模式*/
	static synchronized DBHelper getInstance(Context context) {  
		if (mInstance == null) {  
			mInstance = new DBHelper(context);  
		   }  
		   return mInstance;  
	} 
	public DBHelper(Context context)
	{
		this(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	

}
