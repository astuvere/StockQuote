package com.ks.stockquote;

import java.util.Comparator;

public class SGXStockRecord {
	int ID;
	public String N;
	public String SIP;

	/**
	 * Stock code
	 */
	public String NC;

	/**
	 * Stock status, e.g. CD, CB, H
	 */
	public String R;
	String I;
	String M;
	public float LT;
	public float C;
	float VL;
	float BV;
	public String B;
	public String S;
	float SV;
	float O;
	float H;
	float L;
	public float V;
	String SC;
	float PV;
	String PTD;
	float P;
	String P_;
	String V_;

	public static Comparator<SGXStockRecord> Comparator = new Comparator<SGXStockRecord>() {

		public int compare(SGXStockRecord a, SGXStockRecord b) {
			return a.N.compareTo(b.N);
		}
	};

}
