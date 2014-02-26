package Beans;

import httpHandler.*;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "Item", eager = true)
@RequestScoped
public class Item implements Serializable {
	private static final long serialVersionUID = -1741160023680137936L;
	public static final Namespace WEBTEKNS = Namespace.getNamespace("w",
			"http://www.cs.au.dk/dWebTek/2014");

	private String itemID;
	private String itemName;
	private String itemURL;
	private String itemPrice;
	private String itemStock;
	private String itemDescription;

	public Item() {
	}

	public Item(String itemID, String itemName, String itemURL,
			String itemPrice, String itemStock, String itemDescription) {
		super();
		this.itemID = itemID;
		this.itemName = itemName;
		this.itemURL = itemURL;
		this.itemPrice = itemPrice;
		this.itemStock = itemStock;
		this.itemDescription = itemDescription;
	}

	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemURL() {
		return itemURL;
	}

	public void setItemURL(String itemURL) {
		this.itemURL = itemURL;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemStock() {
		return itemStock;
	}

	public void setItemStock(String itemStock) {
		this.itemStock = itemStock;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public void ModifyItem() {
		System.out.println(this.itemID);
		Items itemlist = new Items();
		itemlist.init();
		for (Item item : itemlist.getItemList()) {

			if (item.itemID.equals(this.itemID)) {
				System.out.println("itemFound");
				this.itemName = item.itemName;
				this.itemDescription = item.itemDescription;
				this.itemPrice = item.itemPrice;
				this.itemURL = item.itemURL;
			}
		}

	}

	public String SaveModifiedItem() {
		HttpHandler handler = new HttpHandler();
		Element modifyitem = new Element("modifyItem");
		modifyitem.addNamespaceDeclaration(WEBTEKNS);
		modifyitem.setNamespace(WEBTEKNS);
		System.out.println(this.itemURL);
		modifyitem.addContent(new Element("shopKey").setText(
				"C8E481850B11C7BDB7727390").setNamespace(WEBTEKNS));
		modifyitem.addContent(new Element("itemID").setText(this.itemID)
				.setNamespace(WEBTEKNS));
		modifyitem.addContent(new Element("itemName").setText(this.itemName)
				.setNamespace(WEBTEKNS));
		modifyitem.addContent(new Element("itemPrice").setText(this.itemPrice)
				.setNamespace(WEBTEKNS));
		modifyitem.addContent(new Element("itemURL").setText(this.itemURL)
				.setNamespace(WEBTEKNS));
		// modifyitem.addContent(new
		// Element("itemDescription").setText(this.itemDescription).setNamespace(ApplicationConstants.WEBTEKNAMESPACE));

		modifyitem.addContent(new Element("itemDescription").addContent(
				new Element("document").setText(this.itemDescription)
						.setNamespace(WEBTEKNS)).setNamespace(WEBTEKNS));

		Document document = new Document(modifyitem);
		XMLOutputter outputter = new XMLOutputter();

		try {
			outputter.output(document, System.out);
				
			handler.outputXMLonHTTP("POST", new URL("http://services.brics.dk/java4/cloud" + "/modifyItem"), document);
			return "Modified";
//			if (handler.outputXMLonHTTP("POST", new URL("http://services.brics.dk/java4/cloud" + "/modifyItem"), document) != false) 
//			{
//				
//			}
		} catch (Exception e) {
			e.printStackTrace();
			return "NotModified";
		}
		//return "NotModified";
	}

}