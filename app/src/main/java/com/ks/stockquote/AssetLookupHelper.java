package com.ks.stockquote;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetManager;

public class AssetLookupHelper {

	//Context context;

	private static String assetFileName = "Code_Name_Map.txt";
	private static HashMap<String, String> ncMap = new HashMap<String, String>();

//	public AssetLookupHelper() {
//
//	}

	public static String getStockNameForDividend(Context context, String selectedStockCode) {

		if (ncMap.size() == 0) {

			AssetManager assetManager = context.getAssets();

			try {
				InputStream input = assetManager.open(assetFileName);
				BufferedReader r = new BufferedReader(new InputStreamReader(input));

				// int size = input.available();
				// byte[] buffer = new byte[size];
				// input.read(buffer);

				String line;
				while ((line = r.readLine()) != null) {

					String[] split = line.split(",");
					ncMap.put(split[0], split[1]);

				}

				input.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block

			}
		}

		String selectedStockName = ncMap.get(selectedStockCode);
		return selectedStockName;
	}

}
