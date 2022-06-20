package ch.hug.simed.covid.frontend.client.bean;

import java.util.ArrayList;

//ANY = "OP_ANY",
//ALL = "OP_ALL",
//NONE = "OP_NONE",

public class GroupOp extends BeanKeyValue  {

	private static final ArrayList<BeanKeyValue> backend = new ArrayList<BeanKeyValue>();
	
	
	public  static final GroupOp ANY = new GroupOp("ANY","OP_ANY");
	public  static final GroupOp ALL = new GroupOp("ALL", "OP_ALL");
	public  static final GroupOp NONE = new GroupOp("NONE", "NONE");


	

	static {
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
//	public static  final Parser<GroupOp> parser = new Parser() {
//
//		@Override
//		public Object parse(CharSequence text) throws ParseException {
//			// TODO Auto-generated method stub
//			return GroupOp.fromCode(text.toString());
//		}
//		
//	};	
	public GroupOp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GroupOp(String code, String displayValue) {
		// TODO Auto-generated constructor stub
		super(code, displayValue);
	}
	@Override
	public String toString() {
		return  getDisplayValue();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	

}
