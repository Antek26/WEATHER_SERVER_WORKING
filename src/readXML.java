
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public abstract class readXML {

    public abstract void parseXML(File fileToParse);


    public Document getDocument(String docString)
    {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setValidating(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(docString));
        } catch (ParserConfigurationException pce) {
            System.out.println("Could not create the document. ");
        } catch (SAXException saxe) {
            saxe.getMessage();
        } catch (IOException ioe) {
            ioe.getMessage();
        }
        return null;
    }


    public String getElementAndAttribute(NodeList listOfData, String elementName, String attributeName)
    {
        String returnString = "";
        try
        {
            for (int i = 0; i < listOfData.getLength(); i++) {//Iterating over the node list (i.e. the whole XML file)
                Node showNode = listOfData.item(i); //Each node in the XML file.
                Element showElement = (Element) showNode; //Casting the node to an Element type, in order to get access to element methods.
                NodeList nodeList = showElement.getElementsByTagName(elementName); //List of nodes which contain the element name.
                Element nameElement = (Element) nodeList.item(0); //Getting the element node from the NodeList, and casting it to item.
                //Note: in this case, since the list has only one element, we don't have to iterate over it.
                NodeList elementList = nameElement.getChildNodes();

                returnString = nameElement.getAttribute(attributeName);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return returnString;
    }


}
