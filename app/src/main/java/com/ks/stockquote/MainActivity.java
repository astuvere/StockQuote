package com.ks.stockquote;

import com.google.gson.Gson;

import android.os.Bundle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.widget.TableLayout.*;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Init cell margins of layoutParams
		layoutParams.setMargins(5, 0, 5, 0);

		try {
			getSGXData(this, CurrentOption);
		} catch (Exception ex) {
			Log.d("Error in onCreate", ex.toString());
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		//setContentView(R.layout.activity_main);
	}

	//final String stockName = "Stock";
	//final String price = "price";	
	static final String URL_SGX = "http://www.sgx.com/JsonRead/JsonData";	
	public static String CurrentOption = QuoteOption.STI_CONSTITUENT;

	public void getSGXData(Context context, String option) {

		final Context _context = context;
		final String _url_param = option;
		final ProgressDialog progressDialog = ProgressDialog.show(_context, "Please wait", "Data is refreshing...", true);
		progressDialog.setCancelable(true);

		// Do something long
		Runnable runnable = new Runnable() {

			// private ArrayList<Map<String, String>> list = new
			// ArrayList<Map<String, String>>();

			private SGXStockResponse response;

			@Override
			public void run() {

				try {

					String responseString = Util.executeRequest(Util.RequestMethod.GET, URL_SGX, _url_param);

					// Testing
//					String responseString = "{}&& {identifier:'ID', label:'As at 28-03-2013 5:04 PM'," +
//					"items:[{ID:0,N:'Capitaland',SIP:'',NC:'C31',R:'CD',I:'NONE',M:'-',LT:3.530,C:-0.030,VL:9305.000,BV:221.000,B:'3.53',S:'3.54',SV:60.000,O:3.580,H:3.590,L:3.530,V:33045075.000,SC:'9',PV:3.560,P:-0.84269658203125,P_:'X',V_:''}," +
//					"{ID:1,N:'CapitaMall',SIP:'',NC:'C38U',R:'NONE',I:'NONE',M:'-',LT:2.090,C:-0.040,VL:15642.000,BV:1350.000,B:'2.09',S:'2.1',SV:10.000,O:2.130,H:2.130,L:2.090,V:32921570.000,SC:'9',PV:2.130,P:-1.8779341796875,P_:'X',V_:''}," +
//					"{ID:2,N:'CapMallsAsia',SIP:'',NC:'JS8',R:'CD',I:'NONE',M:'-',LT:2.050,C:-0.040,VL:11398.000,BV:39.000,B:'2.05',S:'2.06',SV:613.000,O:2.100,H:2.110,L:2.050,V:23588648.000,SC:'9',PV:2.090,P:-1.91387578125,P_:'X',V_:''}," +
//					"{ID:3,N:'CITYDEV',SIP:'',NC:'C09',R:'CD',I:'NONE',M:'-',LT:11.330,C:0.100,VL:1894.000,BV:6.000,B:'11.27',S:'11.33',SV:3.000,O:11.300,H:11.380,L:11.240,V:21410925.000,SC:'9',PV:11.230,P:0.89047197265625,P_:'X',V_:''}," +
//					"{ID:4,N:'ComfortDelGro',SIP:'',NC:'C52',R:'CD',I:'NONE',M:'-',LT:1.910,C:-0.005,VL:4015.000,BV:269.000,B:'1.905',S:'1.91',SV:110.000,O:1.915,H:1.920,L:1.905,V:7678663.000,SC:'7',PV:1.915,P:-0.2610966064453125,P_:'X',V_:''}," +
//					"{ID:5,N:'DBS',SIP:'',NC:'D05',R:'CD',I:'NONE',M:'-',LT:16.000,C:0.050,VL:4830.000,BV:25.000,B:'15.95',S:'16.',SV:206.000,O:16.000,H:16.030,L:15.910,V:77216270.000,SC:'8',PV:15.950,P:0.313479638671875,P_:'X',V_:''}," +
//					"{ID:6,N:'F & N',SIP:'',NC:'F99',R:'NONE',I:'H',M:'-',LT:0.000,C:0.000,VL:0.000,BV:77.000,B:'9.57',S:'9.57',SV:58.000,O:0.000,H:0.000,L:0.000,V:0.000,SC:'2',PV:9.440,P:0.0,P_:'X',V_:''}," +
//					"{ID:7,N:'Genting SP',SIP:'',NC:'G13',R:'NONE',I:'NONE',M:'-',LT:1.495,C:-0.010,VL:15310.000,BV:408.000,B:'1.49',S:'1.495',SV:210.000,O:1.510,H:1.510,L:1.480,V:22892880.000,SC:'A',PV:1.505,P:-0.66445185546875,P_:'X',V_:''}," +
//					"{ID:8,N:'GLP',SIP:'',NC:'MC0',R:'NONE',I:'NONE',M:'-',LT:2.620,C:-0.010,VL:11298.000,BV:559.000,B:'2.62',S:'2.63',SV:10.000,O:2.640,H:2.650,L:2.620,V:29768050.000,SC:'9',PV:2.630,P:-0.3802281005859375,P_:'X',V_:''}," +
//					"{ID:9,N:'GoldenAgr',SIP:'',NC:'E5H',R:'CD',I:'NONE',M:'-',LT:0.580,C:-0.010,VL:54264.000,BV:258.000,B:'0.58',S:'0.585',SV:3476.000,O:0.580,H:0.585,L:0.580,V:31517285.000,SC:'B',PV:0.590,P:-1.694915234375,P_:'X',V_:''}," +
//					"{ID:10,N:'HKLand US$',SIP:'',NC:'H78',R:'NONE',I:'NONE',M:'-',LT:7.410,C:-0.090,VL:3429.000,BV:161.000,B:'7.41',S:'7.44',SV:40.000,O:7.450,H:7.470,L:7.360,V:25457436.000,SC:'9',PV:7.500,P:-1.2,P_:'X',V_:''}," +
//					"{ID:11,N:'Jardine C&C',SIP:'',NC:'C07',R:'CD',I:'NONE',M:'-',LT:51.130,C:-0.470,VL:324.000,BV:1.000,B:'51.11',S:'51.16',SV:1.000,O:51.600,H:51.600,L:51.130,V:16651760.000,SC:'4',PV:51.600,P:-0.910852734375,P_:'X',V_:''}," +
//					"{ID:12,N:'JMH 400US$',SIP:'',NC:'J36',R:'NONE',I:'NONE',M:'-',LT:65.100,C:0.920,VL:138.400,BV:7.600,B:'65.1',S:'65.3',SV:0.800,O:64.700,H:65.390,L:64.190,V:9005496.000,SC:'4',PV:64.180,P:1.43346845703125,P_:'X',V_:''}," +
//					"{ID:13,N:'JSH 500US$',SIP:'',NC:'J37',R:'NONE',I:'NONE',M:'-',LT:39.580,C:-0.320,VL:263.500,BV:1.500,B:'39.58',S:'39.6',SV:1.000,O:39.800,H:40.450,L:39.580,V:10510263.000,SC:'4',PV:39.900,P:-0.80200498046875,P_:'X',V_:''}," +
//					"{ID:14,N:'Kep Corp',SIP:'',NC:'BN4',R:'CA',I:'NONE',M:'-',LT:11.200,C:-0.020,VL:11150.000,BV:280.000,B:'11.2',S:'11.22',SV:50.000,O:11.280,H:11.330,L:11.200,V:125366600.000,SC:'1',PV:11.220,P:-0.17825311279296874,P_:'X',V_:''},{ID:15,N:'Noble Grp',SIP:'',NC:'N21',R:'NONE',I:'NONE',M:'-',LT:1.215,C:0.005,VL:34968.000,BV:952.000,B:'1.21',S:'1.215',SV:702.000,O:1.215,H:1.225,L:1.205,V:42477160.000,SC:'4',PV:1.210,P:0.413223095703125,P_:'X',V_:''},{ID:16,N:'OCBC Bk',SIP:'',NC:'O39',R:'CD',I:'NONE',M:'-',LT:10.650,C:0.080,VL:6275.000,BV:20.000,B:'10.59',S:'10.65',SV:296.000,O:10.620,H:10.650,L:10.540,V:66489860.000,SC:'8',PV:10.570,P:0.75685908203125,P_:'X',V_:''},{ID:17,N:'Olam',SIP:'',NC:'O32',R:'NONE',I:'NONE',M:'-',LT:1.720,C:-0.005,VL:4737.000,BV:297.000,B:'1.715',S:'1.72',SV:22.000,O:1.740,H:1.740,L:1.720,V:8189410.000,SC:'4',PV:1.725,P:-0.2898550537109375,P_:'X',V_:''},{ID:18,N:'Semb Corp',SIP:'',NC:'U96',R:'CD',I:'NONE',M:'-',LT:5.190,C:0.020,VL:3813.000,BV:76.000,B:'5.18',S:'5.19',SV:185.000,O:5.190,H:5.210,L:5.140,V:19778605.000,SC:'1',PV:5.170,P:0.3868471923828125,P_:'X',V_:''},{ID:19,N:'SembMar',SIP:'',NC:'S51',R:'CD',I:'NONE',M:'-',LT:4.430,C:-0.010,VL:3559.000,BV:80.000,B:'4.43',S:'4.44',SV:7.000,O:4.460,H:4.470,L:4.420,V:15807290.000,SC:'2',PV:4.440,P:-0.2252252197265625,P_:'X',V_:''},{ID:20,N:'SGX',SIP:'',NC:'S68',R:'NONE',I:'NONE',M:'-',LT:7.700,C:0.000,VL:2184.000,BV:14.000,B:'7.68',S:'7.7',SV:8.000,O:7.710,H:7.720,L:7.670,V:16817530.000,SC:'8',PV:7.700,P:0.0,P_:'',V_:''},{ID:21,N:'SingTel',SIP:'',NC:'Z74',R:'NONE',I:'NONE',M:'-',LT:3.590,C:0.000,VL:18855.000,BV:173.000,B:'3.59',S:'3.6',SV:2187.000,O:3.600,H:3.600,L:3.570,V:67757030.000,SC:'7',PV:3.590,P:0.0,P_:'',V_:''},{ID:22,N:'SIA',SIP:'',NC:'C6L',R:'NONE',I:'NONE',M:'-',LT:10.870,C:-0.030,VL:1114.000,BV:3.000,B:'10.86',S:'10.9',SV:27.000,O:10.960,H:10.960,L:10.860,V:12159730.000,SC:'7',PV:10.900,P:-0.2752293701171875,P_:'',V_:''},{ID:23,N:'SIA Engg',SIP:'',NC:'S59',R:'NONE',I:'NONE',M:'-',LT:4.740,C:-0.030,VL:607.000,BV:1.000,B:'4.74',S:'4.76',SV:3.000,O:4.780,H:4.810,L:4.730,V:2880510.000,SC:'A',PV:4.770,P:-0.628930810546875,P_:'',V_:''},{ID:24,N:'SPH',SIP:'',NC:'T39',R:'NONE',I:'NONE',M:'-',LT:4.480,C:0.020,VL:5052.000,BV:135.000,B:'4.48',S:'4.49',SV:179.000,O:4.470,H:4.490,L:4.450,V:22583540.000,SC:'2',PV:4.460,P:0.44843046875,P_:'',V_:''},{ID:25,N:'StarHub',SIP:'',NC:'CC3',R:'CD',I:'NONE',M:'-',LT:4.350,C:0.070,VL:2465.000,BV:31.000,B:'4.31',S:'4.35',SV:33.000,O:4.320,H:4.350,L:4.280,V:10672200.000,SC:'7',PV:4.280,P:1.6355140625,P_:'',V_:''},{ID:26,N:'ST Engg',SIP:'',NC:'S63',R:'CD',I:'NONE',M:'-',LT:4.310,C:0.060,VL:2976.000,BV:6.000,B:'4.29',S:'4.31',SV:210.000,O:4.270,H:4.320,L:4.240,V:12730680.000,SC:'1',PV:4.250,P:1.41176474609375,P_:'',V_:''},{ID:27,N:'THBEV',SIP:'',NC:'Y92',R:'CD',I:'NONE',M:'-',LT:0.610,C:-0.015,VL:31055.000,BV:6755.000,B:'0.61',S:'0.615',SV:2780.000,O:0.615,H:0.620,L:0.610,V:19079705.000,SC:'2',PV:0.625,P:-2.4,P_:'',V_:''},{ID:28,N:'UOB',SIP:'',NC:'U11',R:'CD',I:'NONE',M:'-',LT:20.380,C:-0.200,VL:3075.000,BV:70.000,B:'20.38',S:'20.41',SV:8.000,O:20.650,H:20.650,L:20.380,V:63114180.000,SC:'8',PV:20.580,P:-0.97181728515625,P_:'',V_:''},{ID:29,N:'Wilmar',SIP:'',NC:'F34',R:'CD',I:'NONE',M:'-',LT:3.450,C:-0.060,VL:8563.000,BV:338.000,B:'3.45',S:'3.48',SV:22.000,O:3.540,H:3.550,L:3.430,V:29932901.000,SC:'2',PV:3.510,P:-1.7094015625,P_:'',V_:''}]}";

					responseString = responseString.substring(responseString.indexOf("{identifier:"));

					Gson gson = new Gson();
					response = gson.fromJson(responseString, SGXStockResponse.class);

					// for (SGXStockRecord rec : r.items) {
					// Map<String, String> m = new HashMap<String, String>();
					// m.put(stockName, rec.N);
					// m.put(price, String.valueOf(rec.LT));
					// list.add(m);
					// }

					// runOnUiThread(new Runnable() {
					//
					// @Override
					// public void run() {
					// String[] from = { stockName, price };
					// int[] to = { android.R.id.text1, android.R.id.text2 };
					//
					// // SimpleAdapter simpleAdapter = new
					// // SimpleAdapter(_context, list,
					// // android.R.layout.simple_list_item_2, from, to);
					// // GridView gridview = (GridView)
					// // findViewById(R.id.gridview);
					// // gridview.setAdapter(simpleAdapter);
					//
					// progressDialog.dismiss();
					// }
					// });

					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// get a reference for the TableLayout
							TableLayout table = (TableLayout) findViewById(R.id.tableLayout2);
							table.removeAllViews();

							// Header row
							// TableRow row = createHeaderRow(_context);
							// table.addView(row, new
							// TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
							// LayoutParams.WRAP_CONTENT));
							TableRow row;

							Boolean alternate = false;
							for (SGXStockRecord rec : response.items) {
								
								if (!includeStockRecord(rec))
									continue;

								// create a new TableRow
								row = createTableRow(rec, _context);
								if (alternate) {
									row.setBackgroundColor(Color.rgb(240, 240, 240));
									alternate = false;
								} else {
									alternate = true;
								}

								// add the TableRow to the TableLayout
								table.addView(row, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
							}
							progressDialog.dismiss();
						}

						private boolean includeStockRecord(SGXStockRecord rec) {
							if (rec.SIP.equals("@") || rec.N.startsWith("GDR ") || rec.N.startsWith("DBXT") || rec.N.startsWith("Lyxor") || rec.N.contains("ADR"))
								return false;
							else
								return true;
						}
					});

				} catch (Exception e) {
					Log.d("", e.toString());
				}

			}
		};
		new Thread(runnable).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			Toast.makeText(this, "Version 1", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.refresh:
			getSGXData(this, CurrentOption);
			return true;
		case R.id.index:
			CurrentOption = QuoteOption.STI_CONSTITUENT;
			getSGXData(this, CurrentOption);
			return true;
		case R.id.all:
			CurrentOption = QuoteOption.ALL_STOCK;
			getSGXData(this, CurrentOption);
			return true;
		case R.id.watch1:
			//CurrentOption = QuoteOption.WATCHLIST1;
			//getSGXData(this, CurrentOption);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private static TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
	private static TableRow.LayoutParams layoutParams_stockname = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);

	private static TextView createTextView(Context _context) {
		TextView t = new TextView(_context);
		t.setLayoutParams(layoutParams);
		t.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		t.setTextSize(12f);
		return t;
	}

	private static TableRow createTableRow(SGXStockRecord rec, Context _context) {
		// create a new TableRow
		TableRow row = new TableRow(_context);

		// create a new TextView
		TextView t = createTextView(_context);
		t.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		t.setText(rec.N);
		row.addView(t);

		t = createTextView(_context);
		t.setText(String.format("%.3f", rec.C));
		row.addView(t);

		if (rec.C > 0) {
			t.setTextColor(Color.rgb(34, 139, 34));
		} else if (rec.C < 0) {
			t.setTextColor(Color.rgb(235, 0, 0));
		}

		t = createTextView(_context);
		t.setText(String.format("%.3f", rec.LT));
		row.addView(t);

		t = createTextView(_context);
		t.setText(rec.B);
		row.addView(t);

		t = createTextView(_context);
		t.setText(rec.S);
		row.addView(t);

		t = createTextView(_context);
		t.setText(String.format("%.0f", rec.VL));
		row.addView(t);

		return row;
	}

	// private static TableRow createHeaderRow(Context _context) {
	// // create a new TableRow
	// TableRow row = new TableRow(_context);
	// row.setBackgroundColor(Color.rgb(238, 221, 130));
	//
	// layoutParams.setMargins(5, 0, 5, 0);
	//
	// // create a new TextView
	// // TextView t = new TextView(_context);
	// TextView t = createTextView(_context);
	// t.setText("Name");
	// t.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
	// row.addView(t);
	//
	// t = createTextView(_context);
	// t.setText("Chg");
	// row.addView(t);
	//
	// t = createTextView(_context);
	// t.setText("Price");
	// row.addView(t);
	//
	// t = createTextView(_context);
	// t.setText("Buy");
	// row.addView(t);
	//
	// t = createTextView(_context);
	// t.setText("Sell");
	// row.addView(t);
	//
	// t = createTextView(_context);
	// t.setText("Vol");
	// row.addView(t);
	//
	// return row;
	// }

}
