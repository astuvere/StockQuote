package com.ks.stockquote;

import java.util.ArrayList;

import com.ks.storage.IStore;

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
	
	public void loadFromStore(IStore store, String key) {

		SGXStockResponse response;
		String str = store.getKeyValue(key);

		if (!str.equals("")) {
			response = Util.gson.fromJson(str, this.getClass());
			this.label = response.label;
			this.items = response.items;
		}
	}

	public void persistToStore(IStore store, String key) {
		String str = Util.gson.toJson(this, this.getClass());
		store.setKeyValue(key, str);
	}

	public String getJsonString() {
		return Util.gson.toJson(this, this.getClass());
	}

}
