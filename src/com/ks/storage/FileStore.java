package com.ks.storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

public class FileStore implements IStore {

	private static final String tag = "FileStore";
	private Context context;

	public FileStore(Context context) {
		this.context = context;
	}

	@Override
	public String getKeyValue(String key, String value) {

		StringBuffer strbuffer = new StringBuffer();
		try {

			FileInputStream fis = context.openFileInput(key);
			byte[] buffer = new byte[1024];

			while (fis.read(buffer) != -1) {
				strbuffer.append(new String(buffer));
			}
			fis.close();
		} catch (IOException e) {
			Log.e(tag, e.toString());
		}

		return strbuffer.toString();
	}

	@Override
	public Boolean setKeyValue(String key, String value) {
		try {
			FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);

			fos.write(value.getBytes());
			fos.close();
			return true;
		} catch (IOException e) {
			Log.e(tag, e.toString());
		}

		return false;
	}

}
