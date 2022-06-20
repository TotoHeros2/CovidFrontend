package ch.hug.simed.covid.frontend.client.bean;

import java.util.ArrayList;

public class IntegerOperation extends BeanKeyValue  {

	private static final ArrayList<BeanKeyValue> backend = new ArrayList<BeanKeyValue>();

	
	public  static final IntegerOperation OP_BETWEEN = new IntegerOperation("OP_BETWEEN","Entre");
	public  static final IntegerOperation OP_MORE = new IntegerOperation("OP_GTE","Plus que");
	public  static final IntegerOperation OP_LESS = new IntegerOperation("OP_LTE","Moins que");
	public  static final IntegerOperation OP_NOT_BETWEEN = new IntegerOperation("OP_NOT_BETWEEN","Hors");

	static {
		backend.add(OP_BETWEEN);
		backend.add(OP_MORE);
		backend.add(OP_LESS);
		backend.add(OP_NOT_BETWEEN);	
	}
	
	public IntegerOperation(String code, String displayValue) {
		// TODO Auto-generated constructor stub
		super(code, displayValue);
	}
	public static ArrayList<BeanKeyValue> list()
	{
		return backend;
	}
	public static IntegerOperation fromCode(String code)
	{
		for (BeanKeyValue rule : list())
		{
			if (rule.getCode().equalsIgnoreCase(code))
			{
				return (IntegerOperation) rule;
			}
			
		}
		return null;
	}
}
