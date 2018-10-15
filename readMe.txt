
ReadMe

This program is designed to simulate and score a single player game of Bowling. The user is able to randomly bowl the frame or enter desired points for each bowl. The score is able to be called during any frame. The score displayed is the score up to that frame, which excludes any multipliers through strikes and spares, which will be calculated on the subsequent frame. 

Input 'R' for the random bowl
Input 'U' for the user inputed bowl
Input 'S' for the score
    Press the Enter... to lock in selection


The Java Scanner is located in the main method. It handles all of the user input logic, including entering the user inputed bowl option. The user inputed bowl option (logic found in the main method) expects that the user is inputing game legal points. For example, a score in the first frame cannot be 18,9, but this input can be entered into the program. The method was designed under the assumption that the user knows the bassic rules of bowling. However, the input is checked to see if it is numeric.


The random bowl option is a more fully realized version of the user inputed bowl option. The method can be later made into an actual game. The random option logic can be found within the 'Frame' class in the 'randomRecordFrame'. The method randomly bowls a frame within the rules of 10 pin bowling game. Each frame (besides last) keeps track of pin counts, so the max points per frame is 10 and the last frame has logic that only allows a bonus bowl if a spare was rolled on the second point or a strike was rolled on the first. 


The spares and strikes are calculated by entering the frame number into a queue and dequeing them when conditions are met. The frameNumber is then used to to find the correct frame in a frameArray.


The two interface methods are found in the 'Frame' class. The userInput portion is very ugly in the main class due to the fact that an interface with non static methods were used. Also a queue is used to hold user input and later transfered into an array to adhere to interface parameter design. 



Tools used: 
I used this website to double check my scoring: http://www.bowlinggenius.com/