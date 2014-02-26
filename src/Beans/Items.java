package Beans;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jdom2.Element;
import org.jdom2.Namespace;
import httpHandler.*;

@ManagedBean(name = "Items", eager = true)
@ViewScoped
public class Items implements Serializable
{

	private static final long serialVersionUID = 319376882065645519L;

	private HttpHandler httpHandler;

	public static final Namespace WEBTEKNS = Namespace.getNamespace("w",
			"http://www.cs.au.dk/dWebTek/2014");

	private ArrayList<Item> itemList;

	@PostConstruct
	public void init()
	{
		httpHandler = new HttpHandler();

		try
		{
			URL requestUrl = new URL("http://services.brics.dk/java4/cloud"
					+ "/listItems?shopID=" + "195");
			Element responseRoot = httpHandler.HttpRequest("GET", requestUrl)
					.getRootElement();

			if (responseRoot == null)
			{
				throw new Exception("Response from itemList request was null");
			} else
			{
				itemList = new ArrayList<Item>();

				for (Element itemChild : responseRoot.getChildren())
				{
					Element description = itemChild.getChild("itemDescription",
							WEBTEKNS);

					String descriptionStr = "";

					for (Element descriptionChild : description.getChildren())
					{
						descriptionChild.setNamespace(null);
						switch (descriptionChild.getName())
						{
						case "document":
							descriptionChild.setName("div");
							break;
						case "bold":
							descriptionChild.setName("b");
							break;
						case "italics":
							descriptionChild.setName("i");
							break;
						case "list":
							descriptionChild.setName("ul");
							break;
						case "item":
							descriptionChild.setName("li");
							break;
						default:
							break;
						}

						descriptionStr += descriptionChild.getValue();
					}

					itemList.add(new Item(itemChild.getChildText("itemID",
							WEBTEKNS), itemChild.getChildText("itemName",
							WEBTEKNS), itemChild.getChildText("itemURL",
							WEBTEKNS), itemChild.getChildText("itemPrice",
							WEBTEKNS), itemChild.getChildText("itemStock",
							WEBTEKNS), descriptionStr));
				}
			}
		} catch (Exception e)
		{
			System.out.println("An error occurred: " + e.getMessage());
		}
	}

	public ArrayList<Item> getItemList()
	{
		return itemList;
	}

	public void setItemList(ArrayList<Item> itemList)
	{
		this.itemList = itemList;
	}

}
