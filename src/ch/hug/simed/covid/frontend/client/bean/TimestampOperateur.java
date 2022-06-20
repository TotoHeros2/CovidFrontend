package ch.hug.simed.covid.frontend.client.bean;

import java.util.ArrayList;

public class TimestampOperateur extends BeanKeyValue  {

	private static final ArrayList<BeanKeyValue> backend = new ArrayList<BeanKeyValue>();
	
	
	public static final TimestampOperateur OP_BETWEEN = new TimestampOperateur("OP_BETWEEN","Entre");
	public static final TimestampOperateur OP_MORE = new TimestampOperateur("OP_GTE","A partir de");
	public static final TimestampOperateur OP_LESS = new TimestampOperateur("OP_LTE","Jusqu'à");
	public static final TimestampOperateur OP_NOT_BETWEEN = new TimestampOperateur("OP_NOT_BETWEEN","En dehors de");

	static {
		backend.add(OP_BETWEEN);
		backend.add(OP_MORE);
		backend.add(OP_LESS);
		backend.add(OP_NOT_BETWEEN);	
	}
	
	public TimestampOperateur(String code, String displayValue) {
		// TODO Auto-generated constructor stub
		super(code, displayValue);
	}
	public static ArrayList<BeanKeyValue> list()
	{
		return backend;
	}
	public static TimestampOperateur fromCode(String code)
	{
		for (BeanKeyValue rule : list())
		{
			if (rule.getCode().equalsIgnoreCase(code))
			{
				return (TimestampOperateur) rule;
			}
			
		}
		return null;
	}
}
