package pragma.embd.noteit;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressLint("NewApi")
public class CreateReminderScreenActivity extends Activity {
	
	EditText et_subject;
	DatePicker datePicker1;
	TimePicker timePicker1;
	Button btn_create_reminder;
	
	DatabaseHelper helper;
	SQLiteDatabase database;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createreminderscreen);
		
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#000000")));
		
        et_subject = (EditText)findViewById(R.id.et_subject);
        datePicker1 = (DatePicker)findViewById(R.id.datePicker1);
        timePicker1 = (TimePicker)findViewById(R.id.timePicker1);
        btn_create_reminder = (Button)findViewById(R.id.btn_create_reminder);
        
        helper = new DatabaseHelper(this);
		
       
        btn_create_reminder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String str_current_year = new SimpleDateFormat("yyyy").format(new Date());
				String str_current_month = new SimpleDateFormat("MM").format(new Date());
				String str_current_day = new SimpleDateFormat("dd").format(new Date());
				
				
				
				String str_dp_date = datePicker1.getDayOfMonth()  + "-"  + (datePicker1.getMonth() + 1) + "-" + datePicker1.getYear();
				int dp_year = datePicker1.getYear();
				int dp_day = datePicker1.getDayOfMonth();
				int dp_month = datePicker1.getMonth() + 1;
				
				
			/*	Toast.makeText(getApplicationContext(), "day: " + str_current_day, Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), "dp day: " + dp_day, Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), "month: " + str_current_month, Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), " dp month: " + dp_month, Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), "year: " + str_current_year, Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), "dp year: " + dp_year, Toast.LENGTH_SHORT).show();*/
				
				
				String str_dp_time = timePicker1.getCurrentHour() + ":" + timePicker1.getCurrentMinute();
				
				int current_year = Integer.parseInt(str_current_year);
				int current_month = Integer.parseInt(str_current_month);
				int current_day = Integer.parseInt(str_current_day);
				
				if(et_subject.getText().toString().trim().equalsIgnoreCase("") ||
						et_subject.getText().toString().trim().length()==0){
					
					Toast.makeText(getApplicationContext(), "Please enter Subject", Toast.LENGTH_SHORT).show();
				}
				else if(dp_year<current_year){
					
					Toast.makeText(getApplicationContext(), "Selected Year cannot be less than Current Year", Toast.LENGTH_SHORT).show();
					if(dp_month<current_month){
						
						Toast.makeText(getApplicationContext(), "Selected Month cannot be less than Current Month", Toast.LENGTH_SHORT).show();
					}
					else if(dp_day<current_day){
						
						Toast.makeText(getApplicationContext(), "Selected Day cannot be less than Current Day", Toast.LENGTH_SHORT).show();
					}
				}
				
				
				else{
				
				
				
				/*Toast.makeText(getApplicationContext(), "Date is: " + str_date, Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), "Time is: " + str_time, Toast.LENGTH_SHORT).show();*/
				
				database = helper.getWritableDatabase();	
				
				ContentValues values = new ContentValues();
				values.put("subject", et_subject.getText().toString().trim());
				values.put("remdate", str_dp_date);
				values.put("remtime", str_dp_time);
				try{
			
		long i = database.insert(DatabaseHelper.Reminder_Info_TABLE_NAME, null, values);
		Toast.makeText(getApplicationContext(), "Data Inserted Successfully" , Toast.LENGTH_SHORT).show();	
		
		Intent mainScreen = new Intent(getApplicationContext(), MainScreenActivity.class);
		startActivity(mainScreen);
		
				}catch(Exception e){
					Toast.makeText(getApplicationContext(), "Inserting Data failed: " + e.getMessage() , Toast.LENGTH_SHORT).show();	
					
				}
				
				
				}
				
			}
		});
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
