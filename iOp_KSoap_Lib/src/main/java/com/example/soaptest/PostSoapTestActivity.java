package com.example.soaptest;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class PostSoapTestActivity extends Activity {

	private static final String NAMESPACE = "http://tempuri.org/";
	private static final String URL = 
		"http://203.127.123.231/iops/service.asmx?op=IOPS_GetCountry";	
	private static final String SOAP_ACTION = "http://tempuri.org/IOPS_GetCountry";
	private static final String METHOD_NAME = "IOPS_GetCountry";
	
	private static final String[] sampleACTV = new String[] {
	"android", "iphone", "blackberry"
	};
	
	private TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME); 		
		SoapSerializationEnvelope envelope = 
			new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		envelope.setOutputSoapObject(request);
		
	
		tv = (TextView) findViewById(R.id.textviewSoap);
		
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
			//ACTV.setHint("Received :" + resultsRequestSOAP.toString());
			tv.setText(resultsRequestSOAP.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
