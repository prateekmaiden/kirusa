package pragma.embd.noteit;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ReminderDetailsScreenActivity extends Activity {
	

	TextView tv_reminder_subject;
	TextView tv_reminder_date;
	TextView tv_reminder_time;
	Button btn_delete;
	
	String str_note_id;
	
	DatabaseHelper helper;
	SQLiteDatabase database;
	Cursor cursor;
	
	private byte[] img_byte=null;
	
	private static final String fields[] = {"subject", "remdate", "remtime"};
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remainderdetailsscreen);
		
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#000000")));
        
        tv_reminder_subject = (TextView)findViewById(R.id.tv_reminder_subject);
        tv_reminder_date = (TextView)findViewById(R.id.tv_reminder_date);
        tv_reminder_time = (TextView)findViewById(R.id.tv_reminder_time);
        btn_delete = (Button)findViewById(R.id.btn_delete);
		
        str_note_id = getIntent().getStringExtra("Id");
        
        try{
        
        helper = new DatabaseHelper(this);
        
        database = helper.getReadableDatabase();
        
        String selection = "_id=?";
    	
		String[] selectionArgs = {str_note_id};
        
        cursor = database.query(DatabaseHelper.Reminder_Info_TABLE_NAME, fields, selection, selectionArgs, null, null,
				null);
        
        cursor.moveToFirst();
        tv_reminder_subject.setText(cursor.getString(cursor.getColumnIndex("subject")));
        tv_reminder_date.setText(cursor.getString(cursor.getColumnIndex("remdate")));
        tv_reminder_time.setText(cursor.getString(cursor.getColumnIndex("remtime")));
        
       
        
        }
        catch(Exception e){
			
			Toast.makeText(getApplicationContext(), "error msg" + e.getMessage(), Toast.LENGTH_SHORT).show();
			
			
			
		}
        
        btn_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				alert_box_delete();
				
			}
		});
		
	}
	
	public void alert_box_delete(){

		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
	  	 
		  	builder.setTitle("ACTION REQUIRED");
		  	 builder.setIcon(R.drawable.infoicon); 
		       builder.setMessage("Delete Reminder?");
		 
		 //Message here             
		 // Set an EditText view to get user input             
		
		
		       builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {            
		  		 public void onClick(DialogInterface dialog, int whichButton) {       
		
						
						try{
							
							String selection = "_id=?";
							
							String[] selectionArgs = {str_note_id};
					
				long i = database.delete(DatabaseHelper.Reminder_Info_TABLE_NAME, selection, selectionArgs);
				Toast.makeText(getApplicationContext(), "Reminder Deleted Successfully", Toast.LENGTH_SHORT).show();
				Intent mainScreen = new Intent(getApplicationContext(), MainScreenActivity.class);
				startActivity(mainScreen);
						}catch(Exception e){
							Toast.makeText(getApplicationContext(), "Deleting reminder failed: " + e.getMessage() , Toast.LENGTH_SHORT).show();	
							
						}
						
						
						
				      
			 }
			 });    
		 builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {              
			 public void onClick(DialogInterface dialog, int whichButton) {
				 // Canceled.                  
				 dialog.cancel();              
				 }        
			 }); //End of alert.setNegativeButton            
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
		 Intent adminMainScreen = new Intent(getApplicationContext(), MainScreenActivity.class);
   	  		startActivity(adminMainScreen);
		 
	}
}
