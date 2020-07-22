import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;

public class readWeatherXML extends readXML {


    private static String[] weatherData = {"city", "sun", "temperature", "feels_like", "humidity", "pressure", "wind", "speed", "gusts", "direction", "clouds", "visibility", "precipitation", "lastupdate"};

    @Override
    public void parseXML(File fileToParse) {
        Document xmlDoc = super.getDocument(fileToParse.getPath());
        for (String s : weatherData)
        {
            String attributeName = "";
            NodeList listOfData = xmlDoc.getElementsByTagName("current");
            if (s.equals("city"))
            {
                for (int i = 0; i < listOfData.getLength(); i++)
                {

                }
                attributeName = "name";
                System.out.println("WEATHER SUMMARY FOR " +
                        super.getElementAndAttribute(listOfData,s,attributeName) + "\n" +
                        "in " + xmlDoc.getElementsByTagName("country").item(0).getTextContent());

            }
            if (s.equals("sun"))
            {
                System.out.println("The sun rises at "
                        + getElementAndAttribute(listOfData,s,"rise")
                        + " and sets at "
                        + getElementAndAttribute(listOfData,s,"set"));
            }
            if (s.equals("temperature"))
            {
                System.out.println("The average temperature is " + Math.round((Double.parseDouble(getElementAndAttribute(listOfData,s,"value")) -273.15) * 100.0) / 100.0 + " degrees Kelvin.");
                System.out.println("The minimum is " +
                        Math.round((Double.parseDouble(getElementAndAttribute(listOfData,s,"min")) -273.15) * 100.0) / 100.0
                        + " and the maximum " + Math.round((Double.parseDouble(getElementAndAttribute(listOfData,s,"max")) -273.15) * 100.0) / 100.0);
            }
            if (s.equals("feels_like"))
            {
                System.out.println("It feels like it's " + Math.round((Double.parseDouble(getElementAndAttribute(listOfData,s,"value")) -273.15) * 100.0) / 100.0);
            }
            if (s.equals("humidity"))
            {
                System.out.println("The humidity is at " + (Double.parseDouble(getElementAndAttribute(listOfData,s,"value"))) + "%");
            }
            if (s.equals("pressure"))
            {
                System.out.println("The pressure is at " + (Double.parseDouble(getElementAndAttribute(listOfData,s,"value"))) + " ");
            }
            if (s.equals("speed"))
            {
                System.out.println("The wind speed is " + (Double.parseDouble(getElementAndAttribute(listOfData,s,"value")))
                        + "m/s. It can thus be said to be " + getElementAndAttribute(listOfData,s,"name"));
            }
            if (s.equals("direction"))
            {
                System.out.println("The direction of the wind is " + getElementAndAttribute(listOfData,s,"name"));
            }

            if (s.equals("clouds"))
            {
                System.out.println("The clouds are " + getElementAndAttribute(listOfData,s,"value"));
            }
            if (s.equals("visibility"))
            {
                System.out.println("Visibility is at " + getElementAndAttribute(listOfData,s,"value"));
            }
            if (s.equals("precipitation"))
            {
                System.out.println("Is it raining: " + getElementAndAttribute(listOfData,s,"mode"));
            }
            if (s.equals("lastupdate"))
            {
                System.out.println("\n\nThis information was last updated on " + getElementAndAttribute(listOfData,s,"value"));
            }
        }

    }
}
