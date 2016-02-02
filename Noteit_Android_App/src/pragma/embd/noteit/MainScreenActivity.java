package pragma.embd.noteit;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.BaseColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainScreenActivity extends Activity {
	
	Button btn_create_notes;
	Button btn_create_reminder;
	Button btn_history;
	
	DatabaseHelper helper;
	SQLiteDatabase database;
	Cursor cursor;
	
	String str_id;
	String str_subject;
	String str_date;
	String str_time;
	
	Vibrator v;
	
	
	private static final String fields[] = {BaseColumns._ID, "subject", "remdate", "remtime"};
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainscreen);
		
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#000000")));
		
		btn_create_notes = (Button)findViewById(R.id.btn_create_notes);
		btn_create_reminder = (Button)findViewById(R.id.btn_create_reminder);
		btn_history = (Button)findViewById(R.id.btn_history);
		
		v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		
		 helper = new DatabaseHelper(this);
		
		check_for_reminders();
		
		
		btn_create_notes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent createNotesScreen = new Intent(getApplicationContext(), CreateNotesScreenActivity.class);
          	  	startActivity(createNotesScreen);
			}
		});
		
		btn_create_reminder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent createReminderScreen = new Intent(getApplicationContext(), CreateReminderScreenActivity.class);
          	  	startActivity(createReminderScreen);
			}
		});
		
		btn_history.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent createHistoryScreen = new Intent(getApplicationContext(), HistoryScreenActivity.class);
          	  	startActivity(createHistoryScreen);
				
			}
		});
	}
	
	public void check_for_reminders(){
		
		try{
			
			String str_current_date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	        
	        database = helper.getReadableDatabase();
	        
	        String selection = "remdate=?";
	    	
			String[] selectionArgs = {str_current_date};
	        
	        cursor = database.query(DatabaseHelper.Reminder_Info_TABLE_NAME, fields, selection, selectionArgs, null, null,
					null);
	        
	        cursor.moveToFirst();
	        str_id = cursor.getString(cursor.getColumnIndex("_id"));
	        str_subject = cursor.getString(cursor.getColumnIndex("subject"));
	        str_date = cursor.getString(cursor.getColumnIndex("remdate"));
	        str_time = cursor.getString(cursor.getColumnIndex("remtime"));
	        
	        
	        // Vibrate for 500 milliseconds
	        v.vibrate(50000);
	        
	        alert_box();
	        }
	        catch(Exception e){
				
		//		Toast.makeText(getApplicationContext(), "error msg" + e.getMessage(), Toast.LENGTH_SHORT).show();
				
				
				
			}
	}
	
	public void alert_box(){

		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
	  	 
		  	builder.setTitle("Reminder");
		  	 builder.setIcon(R.drawable.infoicon); 
		       builder.setMessage("Subject: " + str_subject);
		 
		 //Message here             
		 // Set an EditText view to get user input             
		
		
		       builder.setPositiveButton("Show Details", new DialogInterface.OnClickListener() {            
		  		 public void onClick(DialogInterface dialog, int whichButton) {       
		
						
						try{
							
					v.cancel();	
			
					Intent reminderDetails = new Intent(getApplicationContext(), ReminderDetailsScreenActivity.class);
					reminderDetails.putExtra("Id",  str_id.trim());
					startActivity(reminderDetails);
				
						}catch(Exception e){
							Toast.makeText(getApplicationContext(), "Deleting reminder failed: " + e.getMessage() , Toast.LENGTH_SHORT).show();	
							
						}
						
						
						
				      
			 }
			 });    
		       
		 AlertDialog alertDialog = builder.create();            
		 alertDialog.show();
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
		 finish();
		 
	}

}
