
package com.example.soft;

import com.example.soft.MainActivity;
import com.example.soft.R;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
public class MainActivity extends Activity 
{
	private static final String WebserviceClient = null;
	TextView myCalendarDate,location,title,organizator,eDate,sDate;
	ImageButton imgDate; 
	
	static String stumu,etumu,starih,szaman,calName,calOrganizator,calLocation;
    int  syil, say ,sgun, ssaat, sdakika,eyil,eay,egun,esaat,edakika;
	String[] spart1,spart2,spart3,epart1,epart2,epart3;
	
   	private Calendar myCalendar = Calendar.getInstance();
   	SimpleDateFormat fmtDateAndTime =new SimpleDateFormat("yyyy-MM-dd");
   	//String formatted =fmtDateAndTime.format(myCalender.getTime());
   	
   	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
         	public void onDateSet(DatePicker view, int year, int monthOfYear,
                       	int dayOfMonth) {
                	myCalendar.set(Calendar.YEAR, year);
                	myCalendar.set(Calendar.MONTH, monthOfYear);
                	myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                	updateDate();
         	}
   	};
	private void updateDate() {
     	myCalendarDate.setText(fmtDateAndTime.format(myCalendar.getTime()));

	}
	final CharSequence[] items={"Bir saat önce!","Bir gün önce!","İki gün önce!"};
	boolean[] itemsChecked = new boolean[items.length];
	
 
          MyCustomAdapter dataAdapter = null;
 
            @Override
            public void onCreate(Bundle savedInstanceState)
            {
               super.onCreate(savedInstanceState);
               setContentView(R.layout.activity_main);
              
       	       Button Uygula =(Button) findViewById(R.id.btnUygula);
       	       myCalendarDate =(TextView)findViewById(R.id.txtDate);
       	       imgDate =(ImageButton)findViewById(R.id.imgDate);
       	    imgDate.setOnClickListener(new View.OnClickListener() {
            	public void onClick(View v) {
                   	new DatePickerDialog(MainActivity.this, d, myCalendar
                             	.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                               myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            	}
     	});
       	    
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
                            	
                                     //When clicked, show a toast with the TextView text
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
   public View getView(final int position, View convertView, ViewGroup parent) 
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
                                    Intent calIntent = new Intent(Intent.ACTION_INSERT);
                                    calIntent.setData(CalendarContract.Events.CONTENT_URI);
                                    calIntent.putExtra(Events.TITLE, calName);
                                    calIntent.putExtra(Events.EVENT_LOCATION, calLocation);
                                    calIntent.putExtra(Events.DESCRIPTION, "garip ");
                                   // Toast.makeText(getApplicationContext(), "yıl : "+syil+" ay: "+say, Toast.LENGTH_SHORT).show();
                                    Calendar startTime = Calendar.getInstance();
                                    startTime.set(syil, say, sgun, ssaat, sdakika);
                                 
                                    
                                    Calendar endTime = Calendar.getInstance();
                                    endTime.set(eyil,eay,egun,esaat,edakika);
                                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                    startTime.getTimeInMillis());
                                    Toast.makeText(getApplicationContext(), "yıl : "+syil+" ay: "+say, Toast.LENGTH_SHORT).show();
                                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                    endTime.getTimeInMillis());
                                    startActivity(calIntent);
                                    //bak askım tarihi alıp yılını burasonra gunu saat ve dk alıcaksın ok
                                    //ya
                                    //sonra ayını 

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
		
            convertView.setOnClickListener(new OnClickListener(){
            	public void onClick(View arg0){
            		
            		 // Create custom dialog object
	                final Dialog dialog = new Dialog(MainActivity.this);
	                // Include dialog.xml file
	                dialog.setContentView(R.layout.detay);
	                // Set dialog title
	                dialog.setTitle("Etkinlik Detayı");
	               
	 
	                // set values for custom dialog components - text, image and button
	                TextView title = (TextView) dialog.findViewById(R.id.name_txt);
                    calName  =title.getText().toString();
	                TextView organizator = (TextView) dialog.findViewById(R.id.organiz_txt);
	                calOrganizator  =organizator.getText().toString();
	                TextView location = (TextView) dialog.findViewById(R.id.location_txt);
	                calLocation= location.getText().toString();
	                TextView eDate = (TextView) dialog.findViewById(R.id.enddate_txt);
	               
	                etumu=eDate.getText().toString();
	              
	                epart1 = etumu.split(" ");
	            	String etarih = epart1[0]; // 004
	            	String ezaman = epart1[1];
	            	 Toast.makeText(getApplicationContext(), ezaman, Toast.LENGTH_SHORT).show();
	            	epart2=etarih.split("-");
	            	 eyil=Integer.parseInt(epart2[0]);
	            	 eay=Integer.parseInt(epart2[1]);
	            	 egun=Integer.parseInt(epart2[2]);
	            	epart3=ezaman.split(":");
	            	 esaat=Integer.parseInt(epart3[0]);
	            	 edakika=Integer.parseInt(epart3[1]);
	                
	                TextView sDate = (TextView) dialog.findViewById(R.id.startdate_txt);
	                stumu=sDate.getText().toString();
	               
	                spart1 = stumu.split(" ");
	            	String starih = spart1[0]; // 004
	            	String szaman = spart1[1];
	            	spart2=starih.split("-");
	            	 syil=Integer.parseInt(spart2[0]);
	            	 say=Integer.parseInt(spart2[1]);
	            	 sgun=Integer.parseInt(spart2[2]);
	            	spart3=szaman.split(":");
	            	 ssaat=Integer.parseInt(spart3[0]);
	            	 sdakika=Integer.parseInt(spart3[1]);
	            
	              //  Toast.makeText(getApplicationContext(), "tarih : "+starih+" zaman: "+szaman, Toast.LENGTH_SHORT).show();
	              //  Toast.makeText(getApplicationContext(), "yıl : "+syil+" ay: "+say, Toast.LENGTH_SHORT).show();
	               // Toast.makeText(getApplicationContext(), "saat : "+ssaat+" dak: "+sdakika, Toast.LENGTH_SHORT).show();

	                
	             
	 
	                dialog.show();
	                 
	                Button declineButton = (Button) dialog.findViewById(R.id.closeButton);
	                // if decline button is clicked, close the custom dialog
	                declineButton.setOnClickListener(new OnClickListener() {
	                    @Override
	                    public void onClick(View v) {
	                        // Close dialog
	                        dialog.dismiss();
	                    }
	                });
				}
			
            
            
            
    });
 
            return convertView;
    }
 
}
    
public void showDialog(View v)
{
	
	AlertDialog.Builder builder=new AlertDialog.Builder(this);
	builder.setTitle("Hatırlatma ");
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
public class WebService extends AsyncTask<Void, Void, Void> 
{
	
	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://www.e-birge.com/EtkinlikWebServis.asmx";
	private final String METOT_ISMI = "VerileriGonder";
	
	List<Etkinlik> liste_etkinlikler = new ArrayList<Etkinlik>(); 

	/**
	 * @param args
	 */
	
	@Override
    protected void onPostExecute(Void result) 
    {
    }

	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		EtkinlikBilgileriniGetirWebServis();
		return null;
	}

	private void EtkinlikBilgileriniGetirWebServis() {
		// TODO Auto-generated method stub
		//Create request
        SoapObject request = new SoapObject(NAMESPACE, METOT_ISMI);
      //Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        //Set output SOAP object
        envelope.setOutputSoapObject(request);
        //Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        
        try 
        {
        	//Invole web service
            androidHttpTransport.call(NAMESPACE + METOT_ISMI, envelope);
            //Get the response
            SoapObject yanit = (SoapObject) envelope.getResponse();
            int i_etkinlik_sayisi = yanit.getPropertyCount();
            
            for(int i=0; i<i_etkinlik_sayisi; i++)
            {
            	SoapObject gec_etkinlik = (SoapObject) yanit.getProperty(i);

            	String name = gec_etkinlik.getPropertyAsString(0);
            	String location = gec_etkinlik.getPropertyAsString(1);
            	String organizer = gec_etkinlik.getPropertyAsString(2);
            	String date_start = gec_etkinlik.getPropertyAsString(3);
            	String date_end = gec_etkinlik.getPropertyAsString(4);
            	String type = gec_etkinlik.getPropertyAsString(5);
            	
            	Etkinlik etkinlik = new Etkinlik(name, location, organizer, date_start, type, date_end);
            	            	
            	liste_etkinlikler.add(etkinlik);
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
	}

}

}