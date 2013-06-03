package com.ks.stockquote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpException;

import com.ks.stockquote.QuoteOption.SelectionView;
import com.ks.stockquote.model.DividendRecord;
import com.ks.stockquote.model.DividendResponse;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class DividendActivity extends ListActivity  {
	
	static final String URL_SGX = "http://www.sgx.com/proxy/SgxDominoHttpProxy";
	static final String URL_PARAM1 = "timeout=3600&dominoHost=http%3A%2F%2Finfo.sgx.com%2Fwebcorpinfo.nsf%2FCorpDistributionAll%3Fopenview%26restricttocategory%3D";
	static final String URL_PARAM2 = "%26count%3D250";
	
	private static ArrayList<DividendRecord> displayRecords;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dividend_main_list);
		
		
		Intent intename = getIntent();
		String selectedName = (String) intename.getSerializableExtra("selectedName");
		
		getDividendData(this, selectedName, true);
	}
	
	
	public String time = "";
	
	public void getDividendData(Context context, String selectedName, boolean forceRefresh) {

		final String _selectedName = selectedName;
		final boolean _forceRefresh = forceRefresh;

		//progressDialog = ProgressDialog.show(this, "", "Data is refreshing...", true);
		//progressDialog.setCancelable(true);

		Runnable runnable = new Runnable() {

			@Override
			public void run() {

				String _option = URL_PARAM1 + _selectedName + URL_PARAM2;
				//String _option = URL_PARAM1 + "SIA" + URL_PARAM2;
				
				DividendResponse response = null;

				time = "";

				try {
					

					// Check got Internet
					if (!Util.hasInternet(DividendActivity.this)) {
						throw new HttpException();
					}
					
					boolean doRefresh = true;
					
					// Check if need to refresh, && not force refresh

					if (doRefresh)
					{
						// Execute http call
						long startTime = System.nanoTime();
						String responseString = Util.executeRequest(Util.RequestMethod.GET, URL_SGX, _option);
						long endTime = System.nanoTime();
						long duration = endTime - startTime;
						time = "Time taken: " + String.valueOf(duration / 1000000);
						Log.d("JSON Parse", time);
						
						// Testing
//						String responseString = "{\"SHARES\":123,\"items\":[{\"key\":\"A2EB927276A0447048257B6E006866F5\",\"CompanyName\":\"SINGAPORE AIRLINES LTD\",\"Annc_Type\":\"DIVIDEND\",\"Ex_Date\":\"30 Jul 2013\",\"Record_Date\":\"1 Aug 2013\",\"DatePaid_Payable\":\"16 Aug 2013\",\"Particulars\":\"SGD 0.17 ONE-TIER TAX\",\"Siblings\":\"53\"},"
//								+ "{\"key\":\"64F96AC4AD0F0C9948257AB8006874A0\",\"CompanyName\":\"SINGAPORE AIRLINES LTD\",\"Annc_Type\":\"DIVIDEND\",\"Ex_Date\":\"12 Nov 2012\",\"Record_Date\":\"15 Nov 2012\",\"DatePaid_Payable\":\"26 Nov 2012\",\"Particulars\":\"SGD 0.06 ONE-TIER TAX\",\"Siblings\":\"53\"},"
//								+ "{\"key\":\"8AE979534A31AAFA48257A4E006870DA\",\"CompanyName\":\"SINGAPORE AIRLINES LTD\",\"Annc_Type\":\"DIVIDEND\",\"Ex_Date\":\"30 Jul 2012\",\"Record_Date\":\"1 Aug 2012\",\"DatePaid_Payable\":\"15 Aug 2012\",\"Particulars\":\"SGD 0.1 ONE-TIER TAX\",\"Siblings\":\"53\"},{}]}";

						responseString = responseString.substring(responseString.indexOf("{\"SHARES\""));
	
						// Parse json
						startTime = System.nanoTime();
						response = Util.gson.fromJson(responseString, DividendResponse.class);
						endTime = System.nanoTime();
						duration = endTime - startTime;
						time += ", " + String.valueOf(duration / 1000000);
						
//						// Put the response to the static
//						if (_view == SelectionView.STI_CONSTITUENT) {
//							responseSTI = response;
//						} else {							
//							responseAllStocks = response;
//						}
					}
					else
					{
						// Do a dummy delay
						Thread.sleep(500);
					}


					displayRecords = response.items;
					
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							setListAdapter(new DividendArrayAdapter(DividendActivity.this, displayRecords));
							if (!time.equals(""))
								Toast.makeText(DividendActivity.this, time, Toast.LENGTH_LONG).show();
						}
					});
					
				} catch (HttpException e) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(DividendActivity.this, getString(R.string.No_internet_connection), Toast.LENGTH_LONG).show();
						}
					});
				} catch (Exception e) {
					Log.e("", "Error in getSGXData", e);
				} finally {
					//progressDialog.dismiss();
					Log.i("Perf", time);
				}
			}
		};

		new Thread(runnable).start();
	}


}
