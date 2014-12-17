package com.example.soft;

import java.util.ArrayList;
import java.util.List;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.os.AsyncTask;

public class WebService extends AsyncTask<Void, Void, Void> 
{
	
	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://www.e-birge.com/EtkinlikWebServis.asmx";
	private final String METOT_ISMI = "VerileriGonder";
	
	List<Etkinlik> liste_etkinlikler = new ArrayList<Etkinlik>(); 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
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
