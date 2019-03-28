# Assignment2
Simple number guessing game that utilizes client/server communication. 
Overall during the game the server generates a target random integer value in the range “lowerLimit” to “upperLimit” 
and then accepts guess attempts from the client until the client sends a number that matches the target number. 

During the game if the guess sent by the client is higher than the target number then the server sends back a 
message to the client to that effect, similarly for lower. An indication is also sent to the client when the target 
number has been guessed correctly, along with the number of attempts that were required to match the target number. 
The “lowerLimit” and “upperLimit” values are input by the user at the start of the game and passed from the client 
to the server. The user (via the client) then keeps sending guess integer values to the server until the target value 
is matched. 

Overall the communication behavior between the client and server is as follows:
•	Server waits for connections from clients
•	A client connects to the server and the user inputs the “lowerLimit” and “upperLimit” integer values
which are validated on the client side and then sent to the server.
•	The server first generates the target number based on the “lowerLimit” and “upperLimit” values and then 
waits for the guess values to arrive from the client.
•	The server reads each guess value and sends back an appropriate response to the client.
•	The client display the response received back and keeps sending (user input) guess values until the target
value is matched. Finally the client displays the number of guesses that were required to match the target value.
•	The client closes the communication with the server when a single guessing game is complete.
•	However the server sits waiting for the next client to connect. 

