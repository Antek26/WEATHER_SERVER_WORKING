import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {

    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(5);
    private static String city;
    private static ArrayList<String> logs = new ArrayList<>();


    public ServerMain(String queriedCity) {
        this.city = queriedCity;
    }



    public static void main (String[] args)
    {

        try (var server = new ServerSocket(4999)) {

            System.out.println("Server is running.");
            while (true)
            {
                Socket acceptSocket = server.accept();
                System.out.println("The client is connected!");

                var clientHandler = new ClientHandler(acceptSocket);
                clientHandler.setName(ClientHandler.generateName());
                clients.add(clientHandler);
                pool.submit(clientHandler);

                if (clients.size() == 0)
                {
                    System.out.println("Would you like to print out a log of all what the clients said?");
                    var scanYes = new Scanner(System.in);
                    var yesNo = scanYes.nextLine();

                    if (yesNo.equals("Yes") || (yesNo.equals("Yeah")) || (yesNo.equals("Yup")) || (yesNo.equals("Ye")))
                    {
                        for (var message : logs)
                        {
                            System.out.println(message);
                        }
                    }
                }
            }
        }
        catch (IOException ioe)
        {
            System.out.println("Server is not working.");
        }
    }


    public static List<ClientHandler> getClients() {
        return clients;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String queriedCity) {
        city = queriedCity;
    }

    public static ArrayList<String> getClientMessages() {
        return logs;
    }
}
