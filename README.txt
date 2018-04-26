IMPORTANT: I am in the process of updating this code
THIS CODE IS UNDER CONSTRUCTION

The 8 Queen puzzle basically asks the question of how you could place 8 queens on a 8x8 chessboard such that they do not intercept each other. A queen can move forwards, backwards and diagonally. 

Notes: 
- I used a little bit GUI - where you can refresh the board to see different solution
- Please note that this GUI randomly places the  first queen in the first row and so sometimes you might press refresh but the board remains the same. 
- I have added an image of the blue crown instead of blue squares
- The recursive method is called findEightQueen, it uses the helper method calculateSafeSpots
- The methods paintComponent and update are for graphics. 
  I hope to animate the solutions so they pop up on the board as the computer calculates them, 
  but I have't been able to successfully do so yet.  
  
- IMPORTANT: I have commented out a certain print statement as the console was getting many lines of code, 
  however if you want to see what going on in the code at a deeper, feel free to uncomment it 
  It shows which tiles have been marked safe/unsafe by which queen

For future improvements: 
- I want to animate the game in such a way that you can watch it or play it manually. 
- In the automatic version, you should be able to watch as the program searches for the Queen whereas in the manual one the user can place queen and try to figure out where to place each queen. 

Compile and Run Instructions:
Compile by -  javac fileName.java
Run by -  java fileName

Thank you!
