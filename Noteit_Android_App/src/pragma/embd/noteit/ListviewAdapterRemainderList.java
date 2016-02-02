package pragma.embd.noteit;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import static pragma.embd.noteit.Constants.FIRST_COLUMN;
import static pragma.embd.noteit.Constants.SECOND_COLUMN;
import static pragma.embd.noteit.Constants.THIRD_COLUMN;
import static pragma.embd.noteit.Constants.FOURTH_COLUMN;;

public class ListviewAdapterRemainderList extends BaseAdapter
{
    public ArrayList<HashMap> list;
    Activity activity;
    String  str_reminder_id;
    
    public ListviewAdapterRemainderList(Activity activity, ArrayList<HashMap> list) {
  
        super();
        this.activity = activity;
        this.list = list;
      
    }
 
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
 
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public void toggleItem(int position) {
    	 HashMap map = list.get(position);
    	
        this.notifyDataSetChanged();
    }
    private class ViewHolder {
    	
    	   TextView tv_reminder_id;
           TextView tv_reminder_subject;
           TextView tv_reminder_date;
           TextView tv_reminder_time; 
           Button btn_delete;
                  
      }
 
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        try{
 
        // TODO Auto-generated method stub
    	View row = convertView;
    	LinearLayout listLayout = new LinearLayout(activity);
    	LayoutParams lpView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    	 listLayout.setOrientation(LinearLayout.HORIZONTAL);
		  listLayout.setId(5000);
                final ViewHolder holder;
                LayoutInflater inflater =  activity.getLayoutInflater();
 
                if (convertView == null)
                {
                    convertView = inflater.inflate(R.layout.listviewreminders,parent,false);
                    holder = new ViewHolder();
                    holder.tv_reminder_id = (TextView) convertView.findViewById(R.id.tv_reminder_id);
                    holder.tv_reminder_subject = (TextView) convertView.findViewById(R.id.tv_reminder_subject);
                    holder.tv_reminder_date = (TextView) convertView.findViewById(R.id.tv_reminder_date);
                    holder.tv_reminder_time = (TextView)convertView.findViewById(R.id.tv_reminder_time);
                    holder.btn_delete = (Button)convertView.findViewById(R.id.btn_delete);
                                     
            
                    convertView.setTag(holder);
                }
                else
                {
                    holder = (ViewHolder) convertView.getTag();
                } 
 
                HashMap map = list.get(position);
                holder.tv_reminder_id.setText((String)map.get(FIRST_COLUMN));
                holder.tv_reminder_subject.setText((String)map.get(SECOND_COLUMN));
                holder.tv_reminder_date.setText((String)map.get(THIRD_COLUMN));
                holder.tv_reminder_time.setText((String)map.get(FOURTH_COLUMN));
                
                holder.btn_delete.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						str_reminder_id = holder.tv_reminder_id.getText().toString().trim();
						alert_box_delete(str_reminder_id);
					
					}
				});
               
            
                
                
             
               
                
        }
        catch(Exception e){
        	Toast.makeText(activity.getApplicationContext(), "error here "+ e.getMessage(),  Toast.LENGTH_SHORT).show();
        }
            
            return convertView;
    }
 
   
    public void alert_box_delete(final String id){
    	
    	try{

		 AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	  	 
		  	builder.setTitle("ACTION REQUIRED");
		  	 builder.setIcon(R.drawable.infoicon); 
		       builder.setMessage("Delete Reminder?");
		 
		 //Message here             
		 // Set an EditText view to get user input             
		
		
		       builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {            
		  		 public void onClick(DialogInterface dialog, int whichButton) {       
		
						
						try{
							
							DatabaseHelper helper;
							SQLiteDatabase database;
							
							helper = new DatabaseHelper(activity.getApplicationContext());
					        
					        database = helper.getWritableDatabase();
					        
							
							String selection = "_id=?";
							
							String[] selectionArgs = {id};
					
				long i = database.delete(DatabaseHelper.Reminder_Info_TABLE_NAME, selection, selectionArgs);
				Toast.makeText(activity.getApplicationContext(), "Reminder Deleted Successfully", Toast.LENGTH_SHORT).show();
				Intent mainScreen = new Intent(activity.getApplicationContext(), MainScreenActivity.class);
				activity.startActivity(mainScreen);
						}catch(Exception e){
							Toast.makeText(activity.getApplicationContext(), "Deleting Student failed: " + e.getMessage() , Toast.LENGTH_SHORT).show();	
							
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
    	catch(Exception e){
    		
    		Toast.makeText(activity.getApplicationContext(), "error is: " + e.getMessage() , Toast.LENGTH_SHORT).show();
    	}
    }
    
}
