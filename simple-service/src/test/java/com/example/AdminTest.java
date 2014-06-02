package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdminTest {
	
	private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }
    
    @Test
    public void testSetRate() {
    	String input = 	"<setrate xmlns=\"http://schemas.restexchange.com/setrate\">"+
				"<items>"+
					"<item>"+
						"<from>USD</from>"+
						"<to>UAH</to>"+
						"<factor>12.23</factor>"+
					"</item>"+
				"</items>"+
			"</setrate>";
    	
    	Response response =
    	        target.path("setrate").request(MediaType.TEXT_PLAIN)
    	                .post(Entity.entity(input, MediaType.APPLICATION_XML));

        assertEquals(204, response.getStatus());
        
        String responseCheckRate = target.path("checkrate").request().get(String.class);
        assertTrue(responseCheckRate.contains("<factor>12.23</factor>"));
    }
    
    @Test
    public void testSetRateError() {
        
        String input = 	"<setrate xmlns=\"http://schemas.restexchange.com/setrate\">"+
        				"<items>"+
        					"<item>"+
        						"<from>USD</from>"+
        						"<to>UAH</to>"+
        					"</item>"+
        				"</items>"+
        				"</setrate>";
        
        Response response =
    	        target.path("setrate").request(MediaType.TEXT_PLAIN)
    	                .post(Entity.entity(input, MediaType.APPLICATION_XML));

		//405 Method Not Allowed
        assertEquals(405, response.getStatus());
    }

}
