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

public class NotesDetailsScreenActivity extends Activity {
	
	ImageView img;
	TextView tv_note_subject;
	TextView tv_note_details;
	Button btn_delete;
	
	String str_note_id;
	
	DatabaseHelper helper;
	SQLiteDatabase database;
	Cursor cursor;
	
	private byte[] img_byte=null;
	
	private static final String fields[] = {"subject", "details", "noteimage"};
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notedetailsscreen);
		
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#000000")));
		
        img = (ImageView)findViewById(R.id.img);
        tv_note_subject = (TextView)findViewById(R.id.tv_note_subject);
        tv_note_details = (TextView)findViewById(R.id.tv_note_details);
        btn_delete = (Button)findViewById(R.id.btn_delete);
		
        str_note_id = getIntent().getStringExtra("Id");
        
        try{
        
        helper = new DatabaseHelper(this);
        
        database = helper.getReadableDatabase();
        
        String selection = "_id=?";
    	
		String[] selectionArgs = {str_note_id};
        
        cursor = database.query(DatabaseHelper.Note_Info_TABLE_NAME, fields, selection, selectionArgs, null, null,
				null);
        
        cursor.moveToFirst();
        tv_note_subject.setText(cursor.getString(cursor.getColumnIndex("subject")));
        tv_note_details.setText(cursor.getString(cursor.getColumnIndex("details")));
        img_byte=cursor.getBlob(cursor.getColumnIndex("noteimage"));
      //  img_byte = (cursor.getString(cursor.getColumnIndex("noteimage"));
        
        Bitmap b1=BitmapFactory.decodeByteArray(img_byte, 0, img_byte.length);
        
        img.setImageBitmap(b1);
        
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
		       builder.setMessage("Delete Note?");
		 
		 //Message here             
		 // Set an EditText view to get user input             
		
		
		       builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {            
		  		 public void onClick(DialogInterface dialog, int whichButton) {       
		
						
						try{
							
							String selection = "_id=?";
							
							String[] selectionArgs = {str_note_id};
					
				long i = database.delete(DatabaseHelper.Note_Info_TABLE_NAME, selection, selectionArgs);
				Toast.makeText(getApplicationContext(), "Note Deleted Successfully", Toast.LENGTH_SHORT).show();
				Intent mainScreen = new Intent(getApplicationContext(), MainScreenActivity.class);
				startActivity(mainScreen);
						}catch(Exception e){
							Toast.makeText(getApplicationContext(), "Deleting note failed: " + e.getMessage() , Toast.LENGTH_SHORT).show();	
							
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
