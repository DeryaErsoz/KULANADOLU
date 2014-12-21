
package com.example.soft;

import com.example.soft.MainActivity;
import com.example.soft.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
 
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
	
 
         // MyCustomAdapter dataAdapter = null;
 
          
            @Override
            public void onCreate(Bundle savedInstanceState)
            {
               super.onCreate(savedInstanceState);
               setContentView(R.layout.activity_main);
              
               
               final ListView lv = (ListView) findViewById(R.id.listView1);
               WebService etkinlikleri_getir = new WebService(getApplicationContext(), lv);
           	   etkinlikleri_getir.execute();
       	       
       	       myCalendarDate =(TextView)findViewById(R.id.txtDate);
       	       imgDate =(ImageButton)findViewById(R.id.imgDate);
       	       
       	       
       	    imgDate.setOnClickListener(new View.OnClickListener() {
            	public void onClick(View v) {
                   	new DatePickerDialog(MainActivity.this, d, myCalendar
                             	.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                               myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            	}
     	});
       	    
       		String[] items = new String[] {"- Herhangi -",
       				"Açıkoturum",
       				"Çalıştay",
       				"Dia Gösterileri",
       				"Eğitim",
       				"Eğitim ve Tanıtım Fuarları",
       				"Festival vb. Etkinlikler",
       				"Film Gösterileri",
       				"Folklor Gösterileri",
       				"Geziler ve Turlar",
       				"Gösteriler",
       				"İmza Günleri",
       				"Kampanyalar",
       				"Karnaval",
       				"Kokteyller ve Yemekler",
       				"Konferanslar",
       				"Kongreler",
       				"Konser ve Dinletiler",
       				"Kurslar",
       				"Müzikal Gösteriler",
       				"Opera ve Bale Gösterileri",
       				"Oryantasyon",
       				"Panayır",
       				"Paneller",
       				"Seminerler",
       				"Sempozyumlar",
       				"Şenlik",
       				"Sergiler",
       				"Şölen",
       				"Söyleşiler",
       				"Spor Etkinlikleri",
       				"Tanıtım Aktiviteleri",
       				"Tanıtımlar ve Turlar",
       				"Tiyatro Gösterileri",
       				"Toplantılar",
       				"Törenler",
       				"Yarışmalar"};
       		
       		
       		final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
       		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
       		            android.R.layout.simple_spinner_item, items);
       		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       		spinner2.setAdapter(adapter);
       		
       		Button btn_uygula =(Button) findViewById(R.id.btnUygula);
       		
       		btn_uygula.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String str_tur = spinner2.getSelectedItem().toString();
					List<Etkinlik> liste = new ArrayList<Etkinlik>(); 
					if(str_tur.contains("- Herhangi -"))
					{
						ListAdapter adapter= new EtkinlikAdapter(getApplicationContext(), WebService.liste_etkinlikler);
				    	lv.setAdapter(adapter);
					}
					else
					{
						for(int i=0; i<WebService.liste_etkinlikler.size(); i++)
						{
							if(WebService.liste_etkinlikler.get(i).getType().contentEquals(str_tur))
							{
								liste.add(WebService.liste_etkinlikler.get(i));
							}
						}
					
					if(liste.size()==0)
					{
						Toast.makeText(getApplicationContext(), "Bu kategoriye ait etkinlik bulunamadı.", Toast.LENGTH_LONG).show();
					}
					
					ListAdapter adapter= new EtkinlikAdapter(getApplicationContext(), liste);
			    	lv.setAdapter(adapter);
					}
				}
			});
               //Generate list View from ArrayList
              // displayListView();
 
               //checkButtonClick();
 
            }
            
            /*
            
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
         
 */
 /*           
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
              holder.code = (TextView) convertView.findViewById(R.id.tur);
              holder.name = (CheckBox) convertView.findViewById(R.id.cb);
             
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
 
}*/
    
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
}