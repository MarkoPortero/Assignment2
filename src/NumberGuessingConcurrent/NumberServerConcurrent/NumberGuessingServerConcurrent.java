package NumberGuessingConcurrent.NumberServerConcurrent;


import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class NumberGuessingServerConcurrent extends Thread{

    private final Socket clientSocket; //
    NumberGuessingServerConcurrent(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // read the message from client and parse the execution
            try (DataInputStream is = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                 PrintWriter os = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())))) {
                int upper = is.readInt();  // read upper from client
                int lower = is.readInt();  // read lower from client

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

                //   }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Exception " + e.getMessage());
        }
    }
// end run
    public static void main(String[] args) {
        // determine if port to listen on is specified by user else use default
        int portNumber = 4444; // default port number
        if (args.length == 1) {
            portNumber = Integer.parseInt(args[0]); // user specified port number
        }
        System.out.println("Number Guessing Server has started");
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                System.out.println("Server Waiting.. ");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client Accepted from " + clientSocket.getInetAddress());
                // spawn a new thread to handle client
                NumberGuessingServerConcurrent numServer = new NumberGuessingServerConcurrent(clientSocket);
                System.out.println("About to start new thread");
                numServer.start();
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