import java.io.*;
import java.net.Socket;
import java.util.Calendar;

public class ClientHandler implements Runnable {

    private Socket acceptSocket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private String name;
    private static String apiKey = "2b54c928b4dc4516d175b2fb83b767bf";
    private static boolean askedForCity = false;


    public ClientHandler(Socket clientSocket)
    {
        this.acceptSocket = clientSocket;
        try
        {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            printWriter = new PrintWriter(clientSocket.getOutputStream());
        }
        catch (IOException ioe)
        {
            System.out.println("Client has disconnected!");
        }
    }



    @Override
    public void run()
    {
        //REPLYING TO THE CLIENT
        try
        {
            try
            {
                while (true)
                {
                    var calendar = Calendar.getInstance();

                    String line = bufferedReader.readLine();
                    String clientMessageData = name + " at " + calendar.getTime() + " : " + line;
                    System.out.println(clientMessageData);
                    ServerMain.getClientMessages().add(clientMessageData);

                    printWriter = new PrintWriter(acceptSocket.getOutputStream());
                    if (askedForCity)
                    {
                        printWriter.println("Checking if the program works:" + ServerMain.getCity());
                        askedForCity = false; }
                    if (line.equals("Time"))
                    {
                        printWriter.println(calendar.getTime());
                    }
                    if (!(line.equals("Weather")) && !(line.equals("Time")) && (askedForCity == false))
                    {
                        printWriter.println("Hey!");
                    }
                    var hasQuit = false;
                    try
                    {
                        if ((line.equals("Quit") || line.equals("quit") || line.equals("QUIT") || line.equals("qUIT") || line.equals("QUit"))) {
                            hasQuit = true;
                            ServerMain.getClients().remove(this);
                        }
                    }
                    catch (NullPointerException npe)
                    {
                        System.out.println("Client has disconnected!");
                    }

                    if (hasQuit)
                    {
                        break;
                    }
                    printWriter.flush();
                }
            }
            catch (IOException ioe)
            {
                System.out.println("Client shut off improperly, without typing Quit. ");
                ServerMain.getClients().remove(this);
            }
        }
        catch (NullPointerException npe)
        {
            System.out.println("The client has disconnected");
        }
    }



    public static String generateName()
    {
        return "Client No." + (ServerMain.getClients().size() + 1);
    }


    public void setName(String clientName) {
        name = clientName;
    }


    public static void setAskedForCityTrue() {
        askedForCity = true;
    }

    public static String getApiKey() {
        return apiKey;
    }
}
