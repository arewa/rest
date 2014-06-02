package com.example;

import java.io.IOException;
import java.io.StringReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Path("setrate")
public class SetRateResource {
	@POST 
    @Consumes(MediaType.APPLICATION_XML)
    public Response setRate(String xmlString) {
    	 
		System.out.println("____________________ request body: __________________________");
		System.out.println(xmlString);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		String rateAsString = null;
		Response r = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlString));
			Document doc = builder.parse(is);
			NodeList list = doc.getElementsByTagName("item");
			for(int i=0; i<list.getLength(); i++)
				if((list.item(i).getTextContent().indexOf("UAH") != -1) && (list.item(i).getChildNodes().getLength() == 3)){
						rateAsString = list.item(i).getChildNodes().item(2).getTextContent();	
						System.out.println("-->"+rateAsString);
				}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(rateAsString == null)
				r = Response.status(405).build();
			else {
				App.EXCHANGE_MANAGER.setRate(new Double(rateAsString));
				r = Response.status(204).build();
			}
		}

		return r;
 
	}
}
