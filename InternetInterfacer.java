import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 * This class provides the scaffolding to send an http request to the
 * Google Maps API, get a packet of XML data in return, and parse
 * that data into a DOM document (a tree-like structure).
 *
 * @author Michael Ida
 */
public class InternetInterfacer {

	public static void main(String[] args) throws IOException,
	ParserConfigurationException, SAXException {

		/*
		 * Set up the parameters to send a http query
		 */
		String base = "https://maps.googleapis.com/maps/api/directions/xml?";
		String origin = "origin=4680+Kalanianaole+Highway+Honolulu+96821";
		String dest = "destination=Kahala+Mall+Honolulu";
		String extras = "mode=walking";
		String key = "key=AIzaSyAbPoFI7ul8T8h_khn3r1LPBBJYatDSPIQ";
		String urlString = base + "&" + origin + "&" + dest + "&" +
				extras + "&" + key;
		URL url = new URL(urlString);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("GET");

		/*
		 * Set up a buffered reader to receive the response
		 */
		BufferedReader in = new BufferedReader(
				new InputStreamReader(httpCon.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		/*
		 * Convert the response into a string and trim off leading
		 * characters which would throw the parser off
		 */
		String responseMessage = response.toString();
		responseMessage = responseMessage.trim().replaceFirst("^([\\W]+)<","<");

		/*
		 * Pass the XML string through the XML parser and convert it into
		 * a DOM document (a tree-like structure)
		 */
		DocumentBuilder db =
				DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc =
				db.parse(new InputSource(new StringReader(responseMessage)));
		/*
		 * This is where you begin.  Figure out how the Document data structure
		 * works and how to find and extract the information you want from it.
		 * Use that information to output something useful and interesting to
		 * the end-user.  The document 'doc' contains all of the data from
		 * the XML parser, and this is the object that you'll want to work
		 * with.
		 */
		System.out.println("Starting location");
		System.out.println("-------------");
		System.out.println(doc.getElementsByTagName("start_address").item(0).getTextContent());
		System.out.println("");
		
		System.out.println("Ending location");
		System.out.println("--------------------");
		System.out.println(doc.getElementsByTagName("end_address").item(0).getTextContent());
		System.out.println("");
		
		System.out.println("Travel Method");
		System.out.println("----------------");
		System.out.println(doc.getElementsByTagName("travel_mode").item(0).getTextContent());
		System.out.print("Estimated Time: ");
		System.out.println(doc.getElementsByTagName("text").item(14).getTextContent());
		System.out.println(doc.getElementsByTagName("warning").item(0).getTextContent().replaceAll("–", "."));
		System.out.println("");
		
		System.out.println("Directions");
		System.out.println("-----------------");
		System.out.println("1. " + doc.getElementsByTagName("html_instructions").item(0).getTextContent().replaceAll("<b>", "").replaceAll("</b>", "")
				+ " for " + doc.getElementsByTagName("text").item(1).getTextContent());
		System.out.println("2. " + doc.getElementsByTagName("html_instructions").item(1).getTextContent().replaceAll("<b>", "").replaceAll("</b>", "")
				+ " for " + doc.getElementsByTagName("text").item(3).getTextContent());
		System.out.println("3. " + doc.getElementsByTagName("html_instructions").item(2).getTextContent().replaceAll("<b>", "").replaceAll("</b>", "")
				+ " for " + doc.getElementsByTagName("text").item(5).getTextContent());
		System.out.println("4. " + doc.getElementsByTagName("html_instructions").item(3).getTextContent().replaceAll("<b>", "").replaceAll("</b>", "")
				+ " for " + doc.getElementsByTagName("text").item(7).getTextContent());
		System.out.println("5. " + doc.getElementsByTagName("html_instructions").item(4).getTextContent().replaceAll("<b>", "").replaceAll("</b>", "")
				+ " for " + doc.getElementsByTagName("text").item(9).getTextContent());
		System.out.println("6. " + doc.getElementsByTagName("html_instructions").item(5).getTextContent().replaceAll("<b>", "").replaceAll("</b>", "")
				+ " for " + doc.getElementsByTagName("text").item(11).getTextContent());
		System.out.println("7. " + doc.getElementsByTagName("html_instructions").item(6).getTextContent().replaceAll("<b>", "").replaceAll("</b>", "")
				.replaceAll("<div style", "").replaceAll("</div>", "").replaceAll("=", "").replaceAll("font-size:0.9em", "").replaceAll(">", ""));
	}
}
