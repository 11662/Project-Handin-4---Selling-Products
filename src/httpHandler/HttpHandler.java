package httpHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

import org.jdom2.output.XMLOutputter;
import org.jdom2.input.*;
import org.jdom2.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;



public class HttpHandler
{
	final static String shopKey = "C8E481850B11C7BDB7727390";
	
	 public Document outputXMLonHTTP(String httpRequestType, URL url,
			   Document docToOutput) throws ProtocolException, IOException,
			   JDOMException {
			  XMLOutputter outputter = new XMLOutputter();

			  HttpURLConnection con = (HttpURLConnection) url.openConnection();
			  con.setRequestMethod(httpRequestType);
			  con.setDoOutput(true);
			  con.setDoInput(true);
			  con.connect();

			  outputter.output(docToOutput, con.getOutputStream());

			  Document responseDocument = null;

			  try {
			   SAXBuilder builder = new SAXBuilder();
			   responseDocument = builder.build((InputStream) con.getContent());
			  } catch (Exception e) {
			  }

			  if (con.getResponseCode() != 200) {
			   System.out.print("An network error occurred: "
			     + con.getResponseCode() + " - " + con.getResponseMessage());
			  }

			  con.disconnect();

			  return responseDocument;
			 }
			 
			 public Document HttpRequest(String httpRequestType, URL url) throws IOException {
			  
			  HttpURLConnection con = (HttpURLConnection) url.openConnection();
			  con.setRequestMethod(httpRequestType);
			  con.setDoOutput(true);
			  con.setDoInput(true);
			  con.connect();

			  Document responseDocument = null;
			  
			  try {
			   SAXBuilder builder = new SAXBuilder();
			   responseDocument = builder.build(con.getInputStream());
			  } catch (Exception e) {
				  e.printStackTrace();
			  }

			  if (con.getResponseCode() != 200) {
			   System.out.print("An network error occurred: "
			     + con.getResponseCode() + " - " + con.getResponseMessage());
			  }

			  con.disconnect();

			  return responseDocument;
			 }
}
