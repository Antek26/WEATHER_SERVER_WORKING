import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public class Client {

private static File fileToParse;

    public static void main(String[] args)
    {
        try (var socket = new Socket("localhost", 4999))
        {

            //WRITING DATA TO THE SERVER
            System.out.println("Hello, client. Please enter your message.\n");
            System.out.println("Type 'Quit' in order to quit the application.");
            System.out.println("Type 'Weather', and then the name of a city to get information about the weather in that city.");
            System.out.println("Type 'Time' in order to get the current time.");
            System.out.println("Type 'Forecast', and then the name of a city to get a forecast for that city.");

            while (true)
            {
                var printWriter = new PrintWriter(socket.getOutputStream(), true);
                var scanMessage = new Scanner(System.in);
                var message = scanMessage.nextLine();

                if (message.equals("Quit"))
                {
                    break;
                }
                if (message.equals("Weather"))
                {
                    System.out.println("Please enter the city where you would like to find the weather.");
                    ClientHandler.setAskedForCityTrue();
                    var scanCity = new Scanner(System.in);
                    var queriedCity = scanCity.nextLine();
                    ServerMain.setCity(queriedCity);
                    printWriter.println("Request sent: " + openAndRead("http://api.openweathermap.org/data/2.5/weather?q=" + ServerMain.getCity() + "&mode=xml&APPID=" + ClientHandler.getApiKey()));
                    writeToXML.writeToXMLFile(openAndRead("http://api.openweathermap.org/data/2.5/weather?q=" + ServerMain.getCity() + "&mode=xml&APPID=" + ClientHandler.getApiKey()), true);
                    try
                    {
                        new readWeatherXML().parseXML(fileToParse);
                    }
                    catch (NullPointerException npe)
                    {
                        npe.printStackTrace();
                    }

                }
                if (message.equals("Forecast"))
                {
                    System.out.println("Please enter the city where you would like to find the forecast.");
                    ClientHandler.setAskedForCityTrue();
                    var scanCity = new Scanner(System.in);
                    var queriedCity = scanCity.nextLine();
                    ServerMain.setCity(queriedCity);
                    printWriter.println("Request sent: " + openAndRead("http://api.openweathermap.org/data/2.5/forecast?q=" + ServerMain.getCity()+ "&mode=xml&appid=" + ClientHandler.getApiKey()));
                    writeToXML.writeToXMLFile(openAndRead("http://api.openweathermap.org/data/2.5/weather?q=" + ServerMain.getCity() + "&mode=xml&APPID=" + ClientHandler.getApiKey()), false);
                    try
                    {
                        new readForecastXML().parseXML(fileToParse);
                    }
                    catch (NullPointerException npe)
                    {
                        npe.printStackTrace();
                    }
                }
                printWriter.println(message);
                printWriter.flush();


                //READING THE REPLY FROM THE SERVER
                var bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));

                String line = bufferedReader.readLine();
                System.out.println("Server : " + line);
            }

        }
        catch (IOException ioe)
        {
            System.out.println("Server is off.");
        }
    }


    //--------------------------------------------------------------------------------------------------------------------------------------------------------------


    public static String openAndRead(String address)
    {
        String returnString = "";
        try
        {
            var url = new URL(address);

            try
            {
                var connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000);
                connection.connect();

                System.out.println(connection.getResponseMessage());
                System.out.println();


                try (var reader = new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream())))
                {

                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        returnString += line + "\n";
                    }
                }
            }
            catch (IOException ioe)
            {
                ioe.getMessage();
            }
        }
        catch (MalformedURLException mue)
        {
            System.out.println(mue.getMessage());
        }
        return returnString;
    }


    public static void setFiletoParse(File file) {
        fileToParse = file;
    }
}