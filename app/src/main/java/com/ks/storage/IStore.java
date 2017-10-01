package com.ks.storage;

public interface IStore {
	
	public String getKeyValue(String key);
	
	public Boolean setKeyValue(String key, String value);

}
