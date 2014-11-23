package com.example.soft;

import java.util.ArrayList;



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
                    _states = new States("Sinema","Delhi",true);
                    stateList.add(_states);
                    _states = new States("Kurlar","Goa",false);
                    stateList.add(_states);
                    _states = new States("Kongre","Jammu & Kashmir",true);
                    stateList.add(_states);
                    _states = new States("Panel","Karnataka",true);
                    stateList.add(_states);
                    _states = new States("Seminer","Kerala",false);
                    stateList.add(_states);
                    _states = new States("Şenlik","Rajasthan",false);
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
 
                                    
 
                                    _state.setSelected(cb.isChecked());
                                     Toast.makeText(getApplicationContext(), "Clicked on Checkbox: " + " is " + cb.isChecked(), 
                                            Toast.LENGTH_LONG).show();
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
    
   
       /* private void checkButtonClick() 
        {
 
                Button myButton = (Button) findViewById(R.id.findSelected);
 
                myButton.setOnClickListener(new OnClickListener() 
                {
 
                            @Override
                            public void onClick(View v) 
                            {
 
                                            StringBuffer responseText = new StringBuffer();
                                            responseText.append("The following were selected...\n");
 
                                            ArrayList<States> stateList = dataAdapter.stateList;
 
                                            for(int i=0;i<stateList.size();i++)
                                            {
                                                 States state = stateList.get(i);
 
                                                if(state.isSelected())
                                                {
                                                    responseText.append("\n" + state.getName());
                                                }
                                            }
 
                                            Toast.makeText(getApplicationContext(),
                                            responseText, Toast.LENGTH_LONG).show();
                            }
                });
        }*/
 
}