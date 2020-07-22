import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class writeToXML {

    public static void writeToXMLFile(String line, boolean isWeather)
    {
        File file;
        if (isWeather)
        {
            file = new File("WeatherRequest.xml");
        }
        else
        {
            file = new File("ForecastRequest.xml");
        }
        Client.setFiletoParse(file);
        try
        {
            var fileWriter = new FileWriter(file);
            var printWriter = new PrintWriter(fileWriter);
            printWriter.println(line);
            printWriter.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        //File class
        //FileWriter class
        //PrintWriter class

    }
}
