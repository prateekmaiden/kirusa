package pragma.embd.noteit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class DatabaseHelper extends SQLiteOpenHelper {
	
	 
	 public static final String Note_Info_TABLE_NAME = "note_info";
	 public static final String Reminder_Info_TABLE_NAME = "reminder_info";
	
	 
	public DatabaseHelper(Context context) {
		
		super(context, "Noteit.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	
		db.execSQL("CREATE TABLE " + Note_Info_TABLE_NAME + "(" 
				+ BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, subject VARCHAR, details VARCHAR, noteimage blob)");
		
		db.execSQL("CREATE TABLE " + Reminder_Info_TABLE_NAME + "(" 
				+ BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, subject VARCHAR, remdate VARCHAR, remtime VARCHAR)");
		
		
			
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Steps to upgrade the database for the new version ...
	}
}

