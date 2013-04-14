package com.ks.stockquote;

import java.util.ArrayList;

public class SGXStockResponse {

	public String identifier;
	public String label;
	public ArrayList<SGXStockRecord> items;
	
	// "As at 12-04-2013 5:04 PM"
	public static final String DEFAULT_LABEL = "As at 01-01-2013 12:00 AM";
	
	public SGXStockResponse()
	{		
		label = DEFAULT_LABEL;
	}

}
