package servlets;

import httpHandler.HttpHandler;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom2.*;

import org.jdom2.Element;
import org.jdom2.output.*;

public class Create extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Namespace WEBTEKNAMESPACE = Namespace.getNamespace("w",
			"http://www.cs.au.dk/dWebTek/2014");
	private static final String shopKey = "12";
	private String CreateItemURL = "http://services.brics.dk/java4/cloud/createItem";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String itemName = request.getParameter("ItemName");
		Element createItem = new Element("CreateItem");
		createItem.addNamespaceDeclaration(WEBTEKNAMESPACE);
		createItem.setNamespace(WEBTEKNAMESPACE);
		
		createItem.addContent(new Element("shopKey").setText(shopKey));
		createItem.addContent(new Element("ItemName").setText(itemName));
		
		Document document = new Document(createItem);
		
		XMLOutputter outputter = new XMLOutputter();
		
		HttpHandler handler = new HttpHandler();
		
		try {
			outputter.output(document, System.out);
			
			handler.outputXMLonHTTP("POST", new URL(CreateItemURL ), document);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		
		
	}
}
