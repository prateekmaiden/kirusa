package pragma.embd.noteit;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

@SuppressLint("NewApi")
public class HistoryScreenActivity extends Activity {
	
	Button btn_notes;
	Button btn_reminder;
		
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historyscreen);
		
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#000000")));
		
		btn_notes = (Button)findViewById(R.id.btn_notes);
		btn_reminder = (Button)findViewById(R.id.btn_reminder);
				
		btn_notes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent adminMainScreen = new Intent(getApplicationContext(), NotesListScreenActivity.class);
          	  	startActivity(adminMainScreen);
			}
		});
		
		btn_reminder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent adminMainScreen = new Intent(getApplicationContext(), ReminderListScreenActivity.class);
          	  	startActivity(adminMainScreen);
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
