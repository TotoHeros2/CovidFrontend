package ch.hug.simed.covid.frontend.client.bean;


public class BeanKeyValue {
	private String code;
	private String displayValue;

	public BeanKeyValue(String code, String displayValue) {
		this.code = code;
		this.displayValue = displayValue;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		BeanKeyValue other = (BeanKeyValue)obj;
		if (other.getCode().equalsIgnoreCase(getCode()))
		{
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return getDisplayValue();
	}

	// obligatoire pour Serializable
	public BeanKeyValue()
	{
		
	}
	

}
