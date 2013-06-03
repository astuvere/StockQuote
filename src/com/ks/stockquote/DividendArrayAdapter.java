package com.ks.stockquote;

import java.util.List;

import com.ks.stockquote.model.DividendRecord;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DividendArrayAdapter extends ArrayAdapter<DividendRecord> {
	
	private final List<DividendRecord> dividendRecords;
	private final Context context;

	public DividendArrayAdapter(Context context, List<DividendRecord> objects) {
		
		super(context, R.layout.stock_record_layout, objects);
		this.dividendRecords = objects;
		this.context = context;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.dividend_record_layout, parent, false);

		DividendRecord rec = dividendRecords.get(position);

		TextView textView = (TextView) rowView.findViewById(R.id.DivType);
		textView.setText(rec.Annc_Type);

		textView = (TextView) rowView.findViewById(R.id.DivDateXD);
		textView.setText(rec.Ex_Date);
		
		textView = (TextView) rowView.findViewById(R.id.DivDatePayable);
		textView.setText(rec.DatePaid_Payable);

		textView = (TextView) rowView.findViewById(R.id.DivParticulars);
		
		String particulars = rec.Particulars;
		if (particulars != null && particulars.contains("ONE-TIER")) {
			particulars = particulars.replace("ONE-TIER", "\nONE-TIER");
		}
		
		textView.setText(particulars);

		if (position % 2 == 1) {
			rowView.setBackgroundColor(Color.rgb(240, 240, 240));
			//rowView.setBackground(new HighLightRowDrawable(Color.rgb(240, 240, 240)));
		}

		return rowView;
	}
}
