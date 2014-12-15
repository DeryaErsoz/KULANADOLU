package com.example.deneme;

import java.sql.Date;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;

public class WebServiceJavaPart extends AsyncTask<Void, Void, Void> {

	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://www.e-birge.com/EtkinlikWebServis.asmx";
	private final String METHOD_NAME = "VerileriGonder";
	
	final String name;
	final String location;
	final String organizer;
	final String type_name;
	final Date date_start;
	final Date date_end;
	
	public WebServiceJavaPart(String name, String location, String organizer, String type_name, Date date_start, Date date_end)
	{
		this.name = name;
		this.location = location;
		this.organizer = organizer;
		this.type_name = type_name;
		this.date_start = date_start;
		this.date_end = date_end;
	}
	
	
	@Override
	protected Void doInBackground(Void... params) {
		WebServiceJavaPart();
		return null;
	}


	private void WebServiceJavaPart() {
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		PropertyInfo prop = new PropertyInfo();
		prop.setName("name");
		prop.setValue(name);
		prop.setType(String.class);
		
		prop = new PropertyInfo();
		prop.setName("location");
		prop.setValue(location);
		prop.setType(String.class);
		
		prop = new PropertyInfo();
		prop.setName("organizer");
		prop.setValue(organizer);
		prop.setType(String.class);
		
		prop = new PropertyInfo();
		prop.setName("type_name");
		prop.setValue(type_name);
		prop.setType(String.class);
		
			
		
		
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(NAMESPACE + METHOD_NAME, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

}
