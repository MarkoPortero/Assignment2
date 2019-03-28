package NumberGuessingConcurrent;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class NumberGuessingClientConcurrent2 {

    public static void main(String[] args) {
        String hostName = "localhost"; // default host name
        int hostPort = 4444; // default host port

        // host machine name and port to connect to
        if (args.length != 0) {
            if (args[0] != null) {
                hostName = args[0];
            }
            if (args[1] != null) {
                hostPort = Integer.parseInt(args[1]);
            }
        }

        System.out.println("Connecting to Number Guessing server . . .");
        // connect to server and extract input and output streams
        try (Socket serverSocket = new Socket(hostName, hostPort);
             DataOutputStream os = new DataOutputStream(new BufferedOutputStream(serverSocket.getOutputStream()));
             BufferedReader is = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {

            // create client input stream for user input
            Scanner scanner = new Scanner(System.in);
            int upper = 0;
            int lower = 0;
            int userGuess = 0;


            System.out.println("Enter a lower int: ");
            lower = scanner.nextInt();
            System.out.println("Enter an upper int: ");
            upper = scanner.nextInt();
            System.out.println("Enter a guess: ");
            userGuess = scanner.nextInt();
            // send the three values to the server
            os.writeInt(upper);
            os.flush();
            os.writeInt(lower);
            os.flush();
            os.writeInt(userGuess);
            os.flush();

            int numGuesses=1;
            String Test = "false";
            do {
                Test = is.readLine();
                if(Test.equals("Correct!")){
                    System.out.println(Test);
                    break;
                }
                System.out.println(Test);
                numGuesses++;
                System.out.println("Try Again: ");
                userGuess = scanner.nextInt();
                os.writeInt(userGuess);
                os.flush();
            } while(Test != "Correct!");

            if(Test.equals("Correct!")){
                System.out.println("You guessed correctly!");
                System.out.println("In only "+ numGuesses + " guesses!");
            }

        } catch (Exception e) {
            System.err.println("Exception:  " + e.getMessage());
        }
    }
}
