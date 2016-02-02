package pragma.embd.noteit;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class CreateNotesScreenActivity extends Activity {
	
	EditText et_subject;
	EditText et_details;
	Button btn_add_image;
	ImageView img_capture;
	Button btn_create_note;
	
	DatabaseHelper helper;
	SQLiteDatabase database;
	
	private static final int SELECT_PICTURE = 1;
	public static final int IMAGE_PICKER_REQUEST = 1;
	
	private byte[] img=null;
	Bitmap bitmap;
	byte[] bytes;
	String encodedImage;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createnotesscreen);
		
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#000000")));
		
        et_subject = (EditText)findViewById(R.id.et_subject);
        et_details = (EditText)findViewById(R.id.et_details);
        btn_add_image = (Button)findViewById(R.id.btn_add_image);
        img_capture = (ImageView)findViewById(R.id.img_capture);
        btn_create_note = (Button)findViewById(R.id.btn_create_note);
        
        helper = new DatabaseHelper(this);
	//	database = helper.getReadableDatabase();
		
        btn_add_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alert_box();
			}
		});
        
        btn_create_note.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(et_subject.getText().toString().trim().equalsIgnoreCase("") ||
						et_subject.getText().toString().trim().length()==0){
					
					Toast.makeText(getApplicationContext(), "Please enter Subject", Toast.LENGTH_SHORT).show();
				}
				
				else if(et_details.getText().toString().trim().equalsIgnoreCase("") ||
						et_details.getText().toString().trim().length()==0){
					
					Toast.makeText(getApplicationContext(), "Please enter Details", Toast.LENGTH_SHORT).show();
				}
				
				else{
					
					
					database = helper.getWritableDatabase();	
					
					Bitmap photo = ((BitmapDrawable)img_capture.getDrawable()).getBitmap();
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
					img = bos.toByteArray();
				
				ContentValues values = new ContentValues();
				values.put("subject", et_subject.getText().toString().trim());
				values.put("details", et_details.getText().toString().trim());
				values.put("noteimage", img);
				try{
			
		long i = database.insert(DatabaseHelper.Note_Info_TABLE_NAME, null, values);
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

	
	void alert_box(){
	  	 AlertDialog.Builder builder = new AlertDialog.Builder(this);
	  	 
	  	builder.setTitle("ACTION REQUIRED");
	  	 builder.setIcon(R.drawable.infoicon); 
	       builder.setMessage("Select below Options?")
	       .setCancelable(false)
	      
	      
	       .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
	           @Override
				public void onClick(DialogInterface dialog, int id) {
	           	
	        	   Intent intent = new Intent();
	                 intent.setType("image/*");
	                 intent.setAction(Intent.ACTION_GET_CONTENT);
	                 startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
	                 
	           }
	       })
	        .setNeutralButton("Camera", new DialogInterface.OnClickListener() {
	           @Override
				public void onClick(DialogInterface dialog, int id) {
	           	
	        	   Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				      startActivityForResult(intent, 0);
	               
	           }
	       })
	       .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
	           @Override
				public void onClick(DialogInterface dialog, int id) {
	           	dialog.cancel();
	               
	           }
	       });
	      
	       AlertDialog alert = builder.create();
	       alert.show();
	   } 
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		Toast.makeText(getApplicationContext(),
				"request code is : " + requestCode, Toast.LENGTH_SHORT).show();
		Toast.makeText(getApplicationContext(), "result is : " + resultCode,
				Toast.LENGTH_SHORT).show();

		img_capture.setVisibility(View.VISIBLE);
		btn_create_note.setVisibility(View.VISIBLE);
		if (requestCode == IMAGE_PICKER_REQUEST && resultCode == RESULT_OK) {
			// if(resultCode == RESULT_OK) {
			// selectedImagePath = getPath(data.getData());

			try {
				if (bitmap != null) {
					bitmap.recycle();
				}
				InputStream stream = getContentResolver().openInputStream(
						data.getData());

				bitmap = BitmapFactory.decodeStream(stream);
				stream.close();
				img_capture.setImageBitmap(bitmap);
				// picNameText.setText("Selected: en"
				// + getStringNameFromRealPath(fileName));

				ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream1);
				bytes = stream1.toByteArray();

				Toast.makeText(getApplicationContext(), "bytes is : " + bytes,
						Toast.LENGTH_SHORT).show();
				
				encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// if (requestCode == IMAGE_TAKER_REQUEST && resultCode == RESULT_OK) {
		else {
		
			
			Bitmap photo = (Bitmap) data.getExtras().get("data");

			img_capture.setImageBitmap(photo);
		}
	}
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		 super.onBackPressed();
		 Intent adminMainScreen = new Intent(getApplicationContext(), MainScreenActivity.class);
   	  		startActivity(adminMainScreen);
		 
	}
}
