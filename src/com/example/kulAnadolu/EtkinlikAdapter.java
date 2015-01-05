package com.example.kulAnadolu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.kulAnadolu.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class EtkinlikAdapter extends BaseAdapter{
	
	List<Etkinlik> liste_etkinlikler = new ArrayList<Etkinlik>();
	private LayoutInflater mInflater;
	static String stumu,etumu,starih,szaman,calName,calOrganizator,calLocation;
    int  syil, say ,sgun, ssaat, sdakika,eyil,eay,egun,esaat,edakika;
	String[] spart1,spart2,spart3,epart1,epart2,epart3;
	
	public EtkinlikAdapter(Context context, List<Etkinlik> liste_etkinlikler)
	{
		mInflater = (LayoutInflater) context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		this.liste_etkinlikler = liste_etkinlikler;
	}
			
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return liste_etkinlikler.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return liste_etkinlikler.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, final View convertView, ViewGroup parent) 
	{
		View satirView;
        
		satirView = mInflater.inflate(R.layout.state_info, null);
		
		TextView tv_tarih = (TextView) satirView.findViewById(R.id.tarih);
		
		TextView tv_tur = (TextView) satirView.findViewById(R.id.tur);
		
		TextView tv_adi = (TextView) satirView.findViewById(R.id.adi);
		
		final Etkinlik etkinlik = liste_etkinlikler.get(position);
		
		tv_tarih.setText(etkinlik.getDate_start().split(" ")[0]);
		
		tv_tur.setText(etkinlik.getType());
		
		tv_adi.setText(etkinlik.getName());
		

		satirView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.icerik);
                dialog.setContentView(R.layout.detay);
                dialog.setTitle("Etkinlik Detayı");
                
                TextView title = (TextView) dialog.findViewById(R.id.textName);
                title.setText(etkinlik.getName());
                
                TextView location = (TextView) dialog.findViewById(R.id.textLocation);
                location.setText( etkinlik.getLocation());
                
                TextView startDate = (TextView) dialog.findViewById(R.id.textStartDate);
                startDate.setText( etkinlik.getDate_start());
                
                TextView endDate = (TextView) dialog.findViewById(R.id.textEndDate);
                endDate.setText( etkinlik.getDate_end());
                
                TextView organizator = (TextView) dialog.findViewById(R.id.textOrganizator);
                organizator.setText( etkinlik.getOrganizer());
                
                Button declineButton = (Button) dialog.findViewById(R.id.closeButton);
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                
                Button btn_add = (Button) dialog.findViewById(R.id.addButton);
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

	                    	Calendar cal = Calendar.getInstance();     
	                        
	                        Intent intent = new Intent(Intent.ACTION_INSERT);
                           intent.setData(CalendarContract.Events.CONTENT_URI);
	                        intent.putExtra("allDay", false);
	                        intent.putExtra("rrule", "FREQ=YEARLY");
	                        intent.putExtra("title", etkinlik.getName());
	                        intent.putExtra("description", "");
	                        intent.putExtra(Events.EVENT_LOCATION, etkinlik.getLocation());
	
	                        String str_bas_tarih = etkinlik.getDate_start();
	                        str_bas_tarih = str_bas_tarih.replace(".", ":");
	                        String str_dizi_tumu[] = str_bas_tarih.split(" ");
	                        String str_dizi_tarih[] = str_dizi_tumu[0].split(":");
	                        String str_dizi_saat[] = str_dizi_tumu[1].split(":");
	                        
	                        Calendar beginTime = Calendar.getInstance();
	                    	beginTime.set(Integer.valueOf(str_dizi_tarih[2]),
	                    			Integer.valueOf(str_dizi_tarih[1])-1, 
	                    			Integer.valueOf(str_dizi_tarih[0]), 
	                    			Integer.valueOf(str_dizi_saat[0]), 
	                    			Integer.valueOf(str_dizi_saat[1]));
	                    	
	                    	//intent.putExtra("beginTime", beginTime.getTimeInMillis());
	                    	
                            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
                            
	                        
	                        String str_bit_tarih = etkinlik.getDate_end();
	                        str_bit_tarih = str_bit_tarih.replace(".", ":");
	                        String str_bit_dizi_tumu[] = str_bit_tarih.split(" ");
	                        String str_bit_dizi_tarih[] = str_bit_dizi_tumu[0].split(":");
	                        String str_bit_dizi_saat[] = str_bit_dizi_tumu[1].split(":");

	                    	Calendar end = Calendar.getInstance();
	                    	end.set(Integer.valueOf(str_bit_dizi_tarih[2]),
	                    			Integer.valueOf(str_bit_dizi_tarih[1])-1, 
	                    			Integer.valueOf(str_bit_dizi_tarih[0]), 
	                    			Integer.valueOf(str_bit_dizi_saat[0]), 
	                    			Integer.valueOf(str_bit_dizi_saat[1]));
	                    	

	                    	 intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,end.getTimeInMillis());
	                        MainActivity.icerik.startActivity(intent);

                    }
                });
                
                
            	dialog.show();
                
                
				
			}
		});
		
		return satirView;
	}

}
