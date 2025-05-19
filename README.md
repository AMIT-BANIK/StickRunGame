# StickRunGame 🏃‍♂️
StickRun is a fast-paced, Java-based side-scrolling runner game built using Java Swing and core Object-Oriented Programming (OOP) principles. Guide your stickman through a world of falling shapes, jump over deadly obstacles, collect coins, and push your reflexes to the limit for the highest score!

# Author Information 

Name : Amit Banik        
Registration Number : 2022831005     
Department : Software Engineering    
Shahajalal University of Science and Technology 

# Game Features

 **👤 Stickman Runner:** Control a stickman character that runs forward when the right arrow key is pressed.

**⬆️ Jump Mechanism:** Press the up arrow key to make the stickman jump over obstacles.

**🧱 Random Obstacles:** Randomly generated shapes like rectangles, triangles, and circles appear as obstacles.

**🪙 Coin Collection:** Collect coins to increase score while avoiding obstacles.

**📊 High Score Tracking:** Saves and retrieves the highest score using a dedicated HighScore system.

**⌛ Smooth Timing:** Uses timers to control obstacle appearance, jump duration, and character movement updates.

**🚫 Collision Detection:** Detects collisions between the stickman and obstacles or coins for interaction and game logic.

# Game Control 
|key|Action|
|-|-|
→ (Right) |Move Stickman Forward|
↑ (UP) |Jump over obstacle and collect coins|


# 📐 OOP Blueprint of the Game

🧱**Encapsulation –** Each class like Player, Obstacle, Coin, and GamePanel has its own logic and data, making the game components modular and easy to maintain.

🧑‍💻 Environment Setup

🧬**Inheritance –** Shape-related classes like Circle, Rectangle, Square, and Coin inherit from a common Shape class, allowing shared functionality like position and area calculation.

🔁 **Polymorphism –** The game uses polymorphism to handle different shape types through a common interface (Selectable, Drawable, etc.), enabling flexible rendering and interaction logic without knowing the exact object type.

🧊 **Abstraction –** The game structure separates user input, game logic, and graphics rendering, making the codebase cleaner and easier to extend.

# 🧑‍💻 Environment Setup

🚀 **What You Need**

Java JDK 8 or higher                                                                                                                                                                       
Java-compatible IDE (e.g., IntelliJ IDEA)

🧪 **Setup Instructions**
                                                    
Clone the repository:

Open the project in your preferred IDE

Compile and run the Game class located in the mainGame package

Start playing and help the stickman overcome obstacles and collect coins!


