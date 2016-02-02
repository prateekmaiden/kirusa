package pragma.embd.noteit;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import static pragma.embd.noteit.Constants.FIRST_COLUMN;
import static pragma.embd.noteit.Constants.SECOND_COLUMN;
import static pragma.embd.noteit.Constants.THIRD_COLUMN;
import static pragma.embd.noteit.Constants.FOURTH_COLUMN;;

public class ReminderListScreenActivity extends Activity { 
	
	ListView lview;
	ListAdapter adapter;
	
	ArrayList<BeanDetails> ar_bean;
	private ArrayList<HashMap> list;
	BeanDetails bean = new BeanDetails();
	
	Activity act;
	
	DatabaseHelper helper;
	SQLiteDatabase database;
	Cursor cursor;
	
	private static final String fields[] = {BaseColumns._ID, "subject", "remdate", "remtime"};
	
	@Override
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reminderlistscreen);
		
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#32CD32")));
	
		lview=(ListView)findViewById(R.id.listView1);
		
		act = this;
		helper = new DatabaseHelper(this);
		
		ar_bean = new ArrayList<BeanDetails>();
		 list = new ArrayList<HashMap>();
		 
		 try{
			 
			 database = helper.getReadableDatabase();
				
			 ar_bean = getData();
				
				if(ar_bean.size()>0){
					
									
					for(int i=0 ; i<ar_bean.size();i++)
				       {
		
						BeanDetails data = ar_bean.get(i);
				        	HashMap temp = new HashMap();
				        	temp.put(FIRST_COLUMN, data.Id);
				            temp.put(SECOND_COLUMN, data.Subject);
				            temp.put(THIRD_COLUMN, data.Dates);
				            temp.put(FOURTH_COLUMN, data.Times);
				            list.add(temp);
				       }

					for(int i = 0; i < list.size(); i++) {
						HashMap temp =list.get(i);
					}
					
					adapter= new ListviewAdapterRemainderList(act,list);
					lview.setAdapter(adapter);
				}
				
		 }
				catch(Exception e){
					//	Toast.makeText(getApplicationContext(), "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
					}
		 }
		
	public ArrayList<BeanDetails> getData(){
		
		try{
		
	

		cursor = database.query(DatabaseHelper.Reminder_Info_TABLE_NAME, fields, null, null, null, null,
				null);
	
		
		cursor.moveToFirst();
		
		
	
		

		do{
			
			bean.Id = cursor.getString(cursor.getColumnIndex("_id"));
			bean.Subject = cursor.getString(cursor.getColumnIndex("subject"));
			bean.Dates = cursor.getString(cursor.getColumnIndex("remdate"));
			bean.Times = cursor.getString(cursor.getColumnIndex("remtime"));
			
	
	ar_bean.add(new BeanDetails(bean.Id, bean.Subject, bean.Dates, bean.Times));
			
		
		
		}while(cursor.moveToNext());
		
		return ar_bean;
	
		}
		
				
		
		
		
		catch(Exception e){
			
			Toast.makeText(getApplicationContext(), "error msg" + e.getMessage(), Toast.LENGTH_SHORT).show();
			return null;
			
			
		}
	}   
		
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		 super.onBackPressed();
		 Intent adminMainScreen = new Intent(getApplicationContext(), MainScreenActivity.class);
   	  		startActivity(adminMainScreen);
		 
	}
}
	

