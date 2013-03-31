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
			getSGXData(this);
		} catch (Exception ex) {
			Log.d("Error in onCreate", ex.toString());
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.activity_main);
	}

	final String stockName = "Stock";
	final String price = "price";

	public void getSGXData(Context context) {

		final Context _context = context;
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
					String urlParameters = "qryId=RSTIc&timeout=60";
					String responseString = Util.executeRequest(Util.RequestMethod.GET, "http://www.sgx.com/JsonRead/JsonData", urlParameters);
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

			return true;
		case R.id.refresh:
			getSGXData(this);
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

	private static TableRow createHeaderRow(Context _context) {
		// create a new TableRow
		TableRow row = new TableRow(_context);
		row.setBackgroundColor(Color.rgb(238, 221, 130));

		layoutParams.setMargins(5, 0, 5, 0);

		// create a new TextView
		// TextView t = new TextView(_context);
		TextView t = createTextView(_context);
		t.setText("Name");
		t.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		row.addView(t);

		t = createTextView(_context);
		t.setText("Chg");
		row.addView(t);

		t = createTextView(_context);
		t.setText("Price");
		row.addView(t);

		t = createTextView(_context);
		t.setText("Buy");
		row.addView(t);

		t = createTextView(_context);
		t.setText("Sell");
		row.addView(t);

		t = createTextView(_context);
		t.setText("Vol");
		row.addView(t);

		return row;
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

}
