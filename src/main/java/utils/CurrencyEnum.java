package utils;

public enum CurrencyEnum {
	INR("\u20b9"), SD(""), USD("");

	private String currencyHex;

	private CurrencyEnum(String currencyHex) {
		this.currencyHex = currencyHex;
	}
	
	public String getCurrentHexCode(){
		return this.currencyHex;
	}

}
