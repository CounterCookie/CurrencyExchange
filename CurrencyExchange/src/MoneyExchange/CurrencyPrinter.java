package MoneyExchange;
import javax.xml.parsers.DocumentBuilder;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import java.io.*;
/**
 * 
 * @author Phong Pham Github:CounterCookie
 *
 */
public class CurrencyPrinter {
	private static double rate = 0;
	private static double totalConvert = 0;
	// Receive user inputs from command line
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Types Of Currency");
		System.out.println("------------------------");
		// providing a list of currency examples
		System.out.println(
				"USD = United States\nEUR = Euro\nJPY = Japanese Yen\nGBP = Pound Sterling\nCAD = Canadian\nCHF = Swiss franc\nCNY = Chinese yuan\nSEK = Swedish krona\nNZD = New Zealand dollar\nMXN = Mexican peso\nSGD = Singapore dollar\nHKD = Hong Kong Dollar\nNOK = Norwegian krone\nKRW = South Korean won\nTRY = Turkish lira\nRUB = Russian ruble\nINR = Indian rupee\nBRL = Brazilian real\nZAR = Sotuh African rand");
		System.out.println("Please enter the type of money you currently have");
		// prompting for the user to enter in the current type of money the user currently has
		String currentMoney = sc.nextLine();
		System.out.println("Types Of Currency");
		System.out.println("------------------------");
		System.out.println(
				"USD = United States\nEUR = Euro\nJPY = Japanese Yen\nGBP = Pound Sterling\nCAD = Canadian\nCHF = Swiss franc\nCNY = Chinese yuan\nSEK = Swedish krona\nNZD = New Zealand dollar\nMXN = Mexican peso\nSGD = Singapore dollar\nHKD = Hong Kong Dollar\nNOK = Norwegian krone\nKRW = South Korean won\nTRY = Turkish lira\nRUB = Russian ruble\nINR = Indian rupee\nBRL = Brazilian real\nZAR = Sotuh African rand");
		System.out.println("Please enter the type of money your converting to");
		// prompts the user to enter what they wish to convert to
		String convertMoney = sc.nextLine();
		// capitalizes string inputs from user
		currentMoney = currentMoney.toUpperCase();
		convertMoney = convertMoney.toUpperCase();
		try {
			// url to yahoo's exchange rate that uses an online XML file
			String pre_apiURL = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22"
					+ currentMoney + "" + convertMoney + "%22)&env=store://datatables.org/alltableswithkeys";
			// creates a url from string
			URL url = new URL(pre_apiURL);
			// produces objects from the DOM tree
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			// connects to the yahoo's XML file
			Document doc = db.parse(url.openStream());
			// check if rate exist on the XML file
			if (returnRate(doc) != 0) {
				// prompts user to enter amount they wish to convert to
				System.out.println("Please enter the amount of money you wish to convert to");
				int amount = sc.nextInt();
				System.out.println(
						"The current rate from " + currentMoney + " to " + convertMoney + " is: " + returnRate(doc));
				// converts any number and rounds to the nearest hundred
				DecimalFormat df = new DecimalFormat("#.##");
				// converts the new amount they are converting to
				totalConvert = Double.valueOf(df.format(rate * amount));
				System.out.println("-----------------------------------------------------------------------------");
				// display all the information of what they are converting from
				// and to then display the total amount
				System.out.println("The amount of money your converting is " + amount + currentMoney
						+ " which calculates to " + totalConvert + convertMoney);
			} else {
				System.out.println("The type of currency is not avaliable or is invalid, please try again");
			}

		} catch (Exception e) {
		}
	}

	/**
	 * This method is used to find the rate in the XML file by searching the
	 * tags and returning the rate as a double. If nothing is found or is
	 * invalid, return 0
	 * 
	 * @param doc
	 * @return a valid rate
	 * @throws NumberFormateException
	 */
	public static double returnRate(Document doc) throws IOException, TransformerException {
		try {

			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("rate");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				// if the node value is found then assign the found element to
				// rate and parse it into a double
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					String a = eElement.getElementsByTagName("Rate").item(0).getTextContent();
					rate = Double.parseDouble(a);
				}
			}
			// if nothing is found or a null value is returned then return 0
		} catch (NumberFormatException e) {
			return 0;
		}
		return rate;
	}

}
