package com.ks.storage;

public interface IStore {
	
	public String getKeyValue(String key, String value);
	
	public Boolean setKeyValue(String key, String value);

}
