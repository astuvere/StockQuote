package com.ks.stockquote;

import java.util.List;

import com.ks.android.HighLightRowDrawable;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StockArrayAdapter extends ArrayAdapter<SGXStockRecord> {

	private final List<SGXStockRecord> stockRecords;
	private final Context context;

	public StockArrayAdapter(Context context, List objects) {
		super(context, R.layout.stock_record_layout, objects);
		this.stockRecords = objects;
		this.context = context;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.stock_record_layout, parent, false);

		SGXStockRecord rec = stockRecords.get(position);

		TextView textView = (TextView) rowView.findViewById(R.id.Name);
		textView.setText(rec.N);

		textView = (TextView) rowView.findViewById(R.id.label);
		textView.setText(String.format("%.3f", rec.LT));
		
		textView = (TextView) rowView.findViewById(R.id.label2);
		textView.setText(String.format("%.3f", rec.C));

		if (rec.C > 0) {
			textView.setTextColor(Color.rgb(34, 139, 34));
		} else if (rec.C < 0) {
			textView.setTextColor(Color.rgb(235, 0, 0));
		}

		textView = (TextView) rowView.findViewById(R.id.label3);
		textView.setText(rec.B);

		textView = (TextView) rowView.findViewById(R.id.label4);
		textView.setText(rec.S);

		textView = (TextView) rowView.findViewById(R.id.label5);
		textView.setText(String.format("%.0f", rec.VL));

		if (position % 2 == 1) {
			rowView.setBackgroundColor(Color.rgb(240, 240, 240));
			//rowView.setBackground(new HighLightRowDrawable(Color.rgb(240, 240, 240)));
		}

		return rowView;
	}

}
