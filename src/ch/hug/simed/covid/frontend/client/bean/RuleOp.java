package ch.hug.simed.covid.frontend.client.bean;

import java.util.ArrayList;

//EQ = "OP_EQ",
//NEQ = "OP_NEQ",
//GT = "OP_GT",
//GTE = "OP_GTE",
//LT = "OP_LT",
//LTE = "OP_LTE",
public class RuleOp extends BeanKeyValue  {

	private static final ArrayList<BeanKeyValue> backend = new ArrayList<BeanKeyValue>();
	
	
	public  static final RuleOp EQ = new RuleOp("OP_EQ","=");
	public  static final RuleOp NEQ = new RuleOp("OP_NEQ","≠");
	public  static final RuleOp GT = new RuleOp("OP_GT",">");
	public  static final RuleOp GTE = new RuleOp("OP_GTE","≥");
	public  static final RuleOp LT = new RuleOp("OP_LT","<");
	public  static final RuleOp LTE = new RuleOp("OP_LTE","≤");


	public  static final RuleOp ANY = new RuleOp("ANY","OP_ANY");
	public  static final RuleOp ALL = new RuleOp("ALL", "OP_ALL");
	public  static final RuleOp NONE = new RuleOp("NONE", "NONE");

	

	static {
		backend.add(EQ);
		backend.add(NEQ);
		backend.add(GT);
		backend.add(GTE);
		backend.add(LT);
		backend.add(LTE);		
		
		backend.add(ANY);
		backend.add(ALL);
		backend.add(NONE);		
		
	}


	// For  gwt Editor
	public static ArrayList<BeanKeyValue> list()
	{
		return backend;
	}
	// for rb group
//	public static  final Parser<RuleOp> parser = new Parser() {
//
//		@Override
//		public Object parse(CharSequence text) throws ParseException {
//			// TODO Auto-generated method stub
//			return RuleOp.fromCode(text.toString());
//		}
//		
//	};	
//	public RuleOp() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
	public RuleOp(String code, String displayValue) {
		// TODO Auto-generated constructor stub
		super(code, displayValue);
	}
//	@Override
//	public String toString() {
//		return  getDisplayValue();
//	}

//	@Override
//	public boolean equals(Object obj) {
//		// TODO Auto-generated method stub
//		return super.equals(obj);
//	}
//	
	public static RuleOp fromCode(String code)
	{
		for (BeanKeyValue rule : list())
		{
			if (rule.getCode().equalsIgnoreCase(code))
			{
				return (RuleOp) rule;
			}
			
		}
		return null;
	}
}
