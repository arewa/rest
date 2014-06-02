package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("checkrate")
public class CheckRateResource {

	@GET 
	@Produces(MediaType.APPLICATION_XML)
	public String getRate() {
		return 	
			"<checkrate xmlns=\"http://schemas.restexchange.com/setrate\">"+
				"<items>"+
					"<item>"+
						"<from>USD</from>"+
						"<to>UAH</to>"+
						"<factor>" + App.EXCHANGE_MANAGER.getRate() + "</factor>"+
					"</item>"+
				"</items>"+
				"<last_change>" + App.EXCHANGE_MANAGER.getTimeStamp() + "</last_change>"+
			"</checkrate>";
	}
}
