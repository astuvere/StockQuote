package com.ks.stockquote;

import java.util.List;

import android.content.Context;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.stock_record_layout, parent, false);

		TextView textView = (TextView) rowView.findViewById(R.id.Name);
		textView.setText(stockRecords.get(position).N);
		
		textView = (TextView) rowView.findViewById(R.id.label);
		textView.setText(String.format("%.3f", stockRecords.get(position).C));
		
		textView = (TextView) rowView.findViewById(R.id.label2);
		textView.setText(String.format("%.3f", stockRecords.get(position).LT));
		
		textView = (TextView) rowView.findViewById(R.id.label3);
		textView.setText(stockRecords.get(position).B);
		
		textView = (TextView) rowView.findViewById(R.id.label4);
		textView.setText(stockRecords.get(position).S);
		
		textView = (TextView) rowView.findViewById(R.id.label5);
		textView.setText(String.format("%.0f", stockRecords.get(position).VL));

		return rowView;
	}

}
