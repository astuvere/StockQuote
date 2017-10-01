package com.ks.stockquote;

public final class QuoteOption {

	public static final String STI_CONSTITUENT = "qryId=RSTIc&timeout=60";
	public static final String ALL_STOCK = "qryId=RStock&timeout=30";
	//public static final String WATCHLIST1 = "qryId=RStock&timeout=30";
	//public static final String WATCHLIST2 = "qryId=RStock&timeout=30";

	public enum SelectionView {
		STI_CONSTITUENT, ALL_STOCK, WATCHLIST1, WATCHLIST2, SEARCH
	}
}
