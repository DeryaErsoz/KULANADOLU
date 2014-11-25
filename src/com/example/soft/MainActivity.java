
package com.example.soft;

import java.util.ArrayList;

import com.example.soft.R;




import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
public class MainActivity extends Activity 
{
	final CharSequence[] items={"Bir saat önce!","Bir gün önce!","İki gün önce!"};
	boolean[] itemsChecked = new boolean[items.length];
	  
 
          MyCustomAdapter dataAdapter = null;
 
            @Override
            public void onCreate(Bundle savedInstanceState)
            {
               super.onCreate(savedInstanceState);
               setContentView(R.layout.activity_main);
               Spinner spinner1=(Spinner)findViewById(R.id.spinner1);
       	       Button Uygula =(Button) findViewById(R.id.btnUygula);
       		String[] items = new String[] {"Sinema", "Tiyatro", "Sergi"};
       		Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
       		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
       		            android.R.layout.simple_spinner_item, items);
       		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       		spinner2.setAdapter(adapter);
               //Generate list View from ArrayList
               displayListView();
 
               //checkButtonClick();
 
            }
            
           
            
 
            private void displayListView()
            {
 
                    //Array list of countries
                    ArrayList<States> stateList = new ArrayList<States>();
 
                    States _states = new States("Tiyatro","Tiyatro yeri içeriği",false);
                    stateList.add(_states);
                    _states = new States("Sinema","Sınema İceriği",true);
                    stateList.add(_states);
                    _states = new States("Kurlar","Goa",false);
                    stateList.add(_states);
                    _states = new States("Kongre","Kongre İçerigi",true);
                    stateList.add(_states);
                    _states = new States("Panel","Panel İçerigi",true);
                    stateList.add(_states);
                    _states = new States("Seminer","Seminer  İçerigi",false);
                    stateList.add(_states);
                    _states = new States("Şenlik","Senlik İçeriği",false);
                    stateList.add(_states);
                    _states = new States("Yarışmalar","West Bengal",false);
                    stateList.add(_states);
 
                    //create an ArrayAdaptar from the String Array
                    dataAdapter = new MyCustomAdapter(this,R.layout.state_info, stateList);
                    ListView listView = (ListView) findViewById(R.id.listView1);
                    // Assign adapter to ListView
                    listView.setAdapter(dataAdapter);
                   
 
                    listView.setOnItemClickListener(new OnItemClickListener() 
                    {
 
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
                            {
                                    // When clicked, show a toast with the TextView text
                                    States state = (States) parent.getItemAtPosition(position);
                                    Toast.makeText(getApplicationContext(),"Clicked on Row: " + state.getName(), 
                                    Toast.LENGTH_LONG).show();
                            }
                    });
            }
            
         
 
private class MyCustomAdapter extends ArrayAdapter<States>
{
 
   private ArrayList<States> stateList;
 
  public MyCustomAdapter(Context context, int textViewResourceId, 
 
  ArrayList<States> stateList) 
  {
        super(context, textViewResourceId, stateList);
        this.stateList = new ArrayList<States>();
        this.stateList.addAll(stateList);
  }
 
    private class ViewHolder
    {
      TextView code;
      CheckBox name;
      
    }
 
   
    @Override
   public View getView(int position, View convertView, ViewGroup parent) 
    {
 
            ViewHolder holder = null;
 
            Log.v("ConvertView", String.valueOf(position));
 
            if (convertView == null)
            {
 
               LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
               convertView = vi.inflate(R.layout.state_info, null);
 
              holder = new ViewHolder();
              holder.code = (TextView) convertView.findViewById(R.id.code);
              holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
             
             TextView tarih = (TextView) convertView.findViewById(R.id.tarih);  
             
              convertView.setTag(holder);
 
                        holder.name.setOnClickListener( new View.OnClickListener() 
                        {
                                   public void onClick(View v)  
                                   {
                                     CheckBox cb = (CheckBox) v;
                                     States _state = (States) cb.getTag();
 
                                    
 
                                  if(  cb.isChecked()==true){
                                	  _state.setSelected(cb.isChecked());
                                    showDialog(v);
                                  }
                                  else{
                                	  _state.setSelected(cb.isChecked());
                                  }
                                          
                                 }
                        	
							
								
                        });
 
            }
            
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
 
            States state = stateList.get(position);
 
            holder.code.setText(" (" + state.getCode() + ")");
            holder.name.setText(state.getName());
            holder.name.setChecked(state.isSelected());
 
            holder.name.setTag(state);
 
            return convertView;
    }
 
}
    
public void showDialog(View v)
{
	
	AlertDialog.Builder builder=new AlertDialog.Builder(this);
	builder.setTitle("Hatýrlatma ");
	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
        	//String selectedTech="Selected Tech - ";
            for (int i = 0; i < items.length; i++) {
            if (itemsChecked[i]) {
                
            	//selectedTech=selectedTech+items[i]+" ";
                itemsChecked[i]=false;
            }
        }
        
        }
    });
	
	builder.setMultiChoiceItems(items, new boolean[]{false,false,false}, new DialogInterface.OnMultiChoiceClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				itemsChecked[which]=isChecked;	
		}
	});
	builder.show();
}

}