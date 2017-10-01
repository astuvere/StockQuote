package com.ks.stockquote;

import java.util.ArrayList;

import com.ks.storage.IStore;

public class WatchList {

	// public WatchList() {
	// items = new ArrayList<String>();
	// }

	public ArrayList<String> items;

	public void addToWatchList(String stockCode) {
		if (!items.contains(stockCode))
			items.add(stockCode);
	}

	public void removeFromWatchList(String stockCode) {
		items.remove(stockCode);
	}

	public void loadWatchList(IStore store, String watchListKey) {
		WatchList watchList;
		String watchListString = store.getKeyValue(watchListKey);

		if (!watchListString.equals("")) {
			watchList = Util.gson.fromJson(watchListString, WatchList.class);
			this.items = watchList.items;
		} else {
			this.items = new ArrayList<String>();
		}
	}

	public void persistWatchList(IStore store, String watchListKey) {
		String watchListString = Util.gson.toJson(this, WatchList.class);
		store.setKeyValue(watchListKey, watchListString);
	}

	public static String getWatchListKey(byte i) {
		return "W" + String.valueOf(i);
	}

	public String getWatchListJsonString() {
		return Util.gson.toJson(this, WatchList.class);
	}

}
