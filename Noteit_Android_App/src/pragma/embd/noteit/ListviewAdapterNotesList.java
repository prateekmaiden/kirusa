package pragma.embd.noteit;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import static pragma.embd.noteit.Constants.SECOND_COLUMN;;

public class ListviewAdapterNotesList extends BaseAdapter
{
    public ArrayList<HashMap> list;
    Activity activity;
    String  str_farmer_id;
    
    public ListviewAdapterNotesList(Activity activity, ArrayList<HashMap> list) {
  
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
    	
    	   TextView tv_note_id;
           TextView tv_note_subject;
           Button btn_details;
           
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
                    convertView = inflater.inflate(R.layout.listviewnotes,parent,false);
                    holder = new ViewHolder();
                    holder.tv_note_id = (TextView) convertView.findViewById(R.id.tv_note_id);
                    holder.tv_note_subject = (TextView) convertView.findViewById(R.id.tv_note_subject);
                    holder.btn_details = (Button) convertView.findViewById(R.id.btn_details);
                    
            
                    convertView.setTag(holder);
                }
                else
                {
                    holder = (ViewHolder) convertView.getTag();
                } 
 
                HashMap map = list.get(position);
                holder.tv_note_id.setText((String)map.get(FIRST_COLUMN));
                holder.tv_note_subject.setText((String)map.get(SECOND_COLUMN));
               
                
                holder.btn_details.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						Intent noteDetails = new Intent(activity.getApplicationContext(), NotesDetailsScreenActivity.class);
						noteDetails.putExtra("Id",  holder.tv_note_id.getText().toString().trim());
						activity.startActivity(noteDetails);
					
					}
				});
               
        }
        catch(Exception e){
        	Toast.makeText(activity.getApplicationContext(), "error here "+ e.getMessage(),  Toast.LENGTH_SHORT).show();
        }
            
            return convertView;
    }
 
   

}
 