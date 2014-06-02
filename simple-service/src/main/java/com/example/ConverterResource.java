package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/amount_of_uah_from/{amount}/{originCurrency}")
public class ConverterResource {

	@GET 
	@Produces(MediaType.TEXT_PLAIN)
	public Response getConvertedAmount(	
			@PathParam("amount") Double amount,
			@PathParam("originCurrency") String originCurrency) {
		
		Response r = null;

		if(!originCurrency.equals("usd"))
			return Response.status(405).build();

		
		r = Response.ok("" + amount * App.EXCHANGE_MANAGER.getRate(), MediaType.TEXT_PLAIN).build();
		
		return r;

	}
}
