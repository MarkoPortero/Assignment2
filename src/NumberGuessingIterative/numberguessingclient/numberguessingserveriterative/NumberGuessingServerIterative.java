package NumberGuessingIterative.numberguessingclient.numberguessingserveriterative;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class NumberGuessingServerIterative {

    public static void main(String[] args) {
        // determine if port to listen on is specified by user else use default
        int portNumber = 4444; // default port number
        if (args.length == 1) {
            portNumber = Integer.parseInt(args[0]); // user specified port number
        }
        System.out.println("Number Guessing Server has started");
        // create serverSocket to listen on
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                // accept client connection
                Socket clientSocket = serverSocket.accept();
                try (DataInputStream is = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                     PrintWriter os = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())))) {
                    System.out.println("Client Accepted");

                    // read three numbers from client
                    int upper = is.readInt();  // read upper from client
                    int lower = is.readInt();  // read lower from client

                    // decide the response
                    double target = generateRandomNum(upper, lower);
                    int finalTar = (int) Math.floor(target);
                    System.out.println("Random Number is:" + finalTar);
                    int usernum = -1;
                    while(usernum != finalTar) {
                        usernum = is.readInt();
                        if(usernum == finalTar){
                            break;
                        }
                        else if(usernum > finalTar){
                            os.println("Too high!");
                            os.flush();
                        }
                        else if(usernum < finalTar){
                            os.println("Too low!");
                            os.flush();
                        }

                    }
                    if(usernum == finalTar){
                        os.println("Correct!");
                        os.flush();
                    }
                    // send message back to client
                } catch (IOException e) {
                    System.out.println("IOException:" + e.getMessage());
                }
            } // end while true
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        } // end catch

    } // end main
    public static double generateRandomNum(double upperNum, double lowerNum){
        double RandomNum = (int)(Math.random()*((upperNum-lowerNum)+1))+lowerNum;
        return RandomNum;
    }
}
