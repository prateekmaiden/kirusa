package pragma.embd.noteit;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class LoginScreenActivity extends Activity {

	EditText et_username;
	EditText et_pwd;
	Button btn_login;
	Button btn_new_user;
	ImageView img_type;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginscreen);
		
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#000000")));

		et_username = (EditText)findViewById(R.id.et_username);
		et_pwd = (EditText)findViewById(R.id.et_pwd);
		btn_login = (Button)findViewById(R.id.btn_login);
		
		
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(et_username.getText().toString().trim().equalsIgnoreCase("") ||
						et_username.getText().toString().trim().length()==0){
					
					Toast.makeText(getApplicationContext(), "Please enter Username", Toast.LENGTH_SHORT).show();
				}
				else if(et_pwd.getText().toString().trim().equalsIgnoreCase("") ||
						et_pwd.getText().toString().trim().length()==0){
					
					Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
				}
				else if(et_username.getText().toString().trim().equalsIgnoreCase("a") &&
						et_pwd.getText().toString().trim().equalsIgnoreCase("a")){
					
					Intent adminMainScreen = new Intent(getApplicationContext(), MainScreenActivity.class);
              	  	startActivity(adminMainScreen);
					
				}
			
			}
		});
		
		
	}

	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

}
