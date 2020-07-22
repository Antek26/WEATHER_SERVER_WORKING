import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;

public class readForecastXML extends readXML {

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
                attributeName = "name";
                System.out.println("FORECAST SUMMARY FOR " +
                        super.getElementAndAttribute(listOfData,s,attributeName) + "\n" +
                        "in " + xmlDoc.getElementsByTagName("country").item(0).getTextContent());

            }
            if (s.equals("sun"))
            {
                System.out.println("The sun will rise at "
                        + getElementAndAttribute(listOfData,s,"rise")
                        + " and set at "
                        + getElementAndAttribute(listOfData,s,"set"));
            }
            if (s.equals("temperature"))
            {
                System.out.println("The average temperature will be " + Math.round((Double.parseDouble(getElementAndAttribute(listOfData,s,"value")) -273.15) * 100.0) / 100.0 + " degrees Kelvin.");
                System.out.println("The minimum will be " +
                        Math.round((Double.parseDouble(getElementAndAttribute(listOfData,s,"min")) -273.15) * 100.0) / 100.0
                        + " and the maximum " + Math.round((Double.parseDouble(getElementAndAttribute(listOfData,s,"max")) -273.15) * 100.0) / 100.0);
            }
            if (s.equals("feels_like"))
            {
                System.out.println("How the temperature will feel: " + Math.round((Double.parseDouble(getElementAndAttribute(listOfData,s,"value")) -273.15) * 100.0) / 100.0);
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
            if (s.equals("gusts"))
            {
                System.out.println("Gusts are at " + getElementAndAttribute(listOfData,s,"value"));
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
