package ch.hug.simed.covid.frontend.client.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.TextResource;

public interface ResourceBundle extends ClientBundle {

//		 @Source("arrow.png")
//		 public ImageResource arrow();
//		 @Source("etage1.png")
//		 public ImageResource etage1();
//
//		 @Source("etage2.png")
//		 public ImageResource etage2();
	
		@NotStrict
		@Source("App.css")
		public TextResource appCss();
		 
		 public static final ResourceBundle INSTANCE = GWT.create(ResourceBundle.class);

}
