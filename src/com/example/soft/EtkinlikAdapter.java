package com.example.soft;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class EtkinlikAdapter extends BaseAdapter{
	
	List<Etkinlik> liste_etkinlikler = new ArrayList<Etkinlik>();
	private LayoutInflater mInflater;
	
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
		
		Etkinlik etkinlik = liste_etkinlikler.get(position);
		
		tv_tarih.setText(etkinlik.getDate_start());
		
		tv_tur.setText(etkinlik.getType());
		
		tv_adi.setText(etkinlik.getName());

		return satirView;
	}

}
