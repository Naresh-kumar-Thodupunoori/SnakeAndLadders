# 🐍🪜 Snakes and Ladders Game

A comprehensive implementation of the classic Snakes and Ladders board game in Java, following SOLID principles and design patterns.

## 🎯 Features

### Game Features
- **Dynamic Board Size**: User can specify board size (e.g., 7x7, 10x10)
- **Multiple Players**: Support for 2-6 players with unique symbols
- **Player Combat**: Players can "kill" each other by landing on the same cell
- **Extra Turns**: Rolling a 6 gives an extra turn
- **Turn Revocation**: Three consecutive 6s revokes the current turn
- **Difficulty Levels**: Easy, Medium, and Hard with different snake/ladder ratios

### Advanced Board Generation
- **Multiple Generation Strategies**: Random and Balanced patterns
- **Preset Configurations**: Beginner, Expert, and Aesthetic presets
- **Truly Dynamic Generation**: Snakes and ladders adapt to any board size
- **Customizable Ratios**: Override default ratios for snakes and ladders

### Technical Features
- **SOLID Principles**: Each class follows Single Responsibility, Open/Closed, etc.
- **Design Patterns**: 
  - **Factory Pattern**: Create different board generators
  - **Builder Pattern**: Complex board configuration construction
  - **Template Method Pattern**: Board generation algorithm skeleton
  - **Strategy Pattern**: Pluggable board generation strategies
  - **Polymorphism**: Snake and Ladder entities
- **Clean Architecture**: Separation of concerns between model, service, and view layers
- **Extensible Design**: Easy to add new features and game modes

## 🏗️ Architecture

### Package Structure
```
com.snakeladder/
├── model/           # Core game entities
│   ├── Player.java
│   ├── Board.java
│   ├── Position.java
│   ├── Dice.java
│   ├── BoardEntity.java (interface)
│   ├── Snake.java
│   ├── Ladder.java
│   ├── GameLevel.java
│   └── GameLevelInterface.java
├── service/         # Game logic and display
│   ├── Game.java
│   └── GameDisplay.java
├── strategy/        # Board generation strategies
│   ├── BoardGenerationStrategy.java (interface)
│   ├── AbstractBoardGenerationStrategy.java
│   ├── RandomBoardGenerationStrategy.java
│   └── BalancedBoardGenerationStrategy.java
├── factory/         # Factory implementations
│   └── BoardGeneratorFactory.java
└── builder/         # Builder pattern implementations
    ├── BoardConfiguration.java
    └── BoardConfigurationBuilder.java
```

### Design Patterns Used

1. **Factory Pattern**: `BoardGeneratorFactory` creates different board generation strategies
2. **Builder Pattern**: `BoardConfigurationBuilder` constructs complex board configurations step by step
3. **Template Method Pattern**: `AbstractBoardGenerationStrategy` defines algorithm skeleton with customizable steps
4. **Strategy Pattern**: `BoardGenerationStrategy` interface allows pluggable generation algorithms
5. **Polymorphism**: `BoardEntity` interface implemented by `Snake` and `Ladder` classes
6. **Interface Segregation**: Separate interfaces for different concerns (`GameLevelInterface`, `BoardEntity`)

### SOLID Principles Implementation

- **S** - Single Responsibility: Each class has one reason to change
- **O** - Open/Closed: Easy to extend with new board generation strategies
- **L** - Liskov Substitution: Snake and Ladder can be used interchangeably as BoardEntity
- **I** - Interface Segregation: Specific interfaces for different concerns
- **D** - Dependency Inversion: Game depends on abstractions, not concrete classes

## 🚀 How to Run

### Prerequisites
- Java 8 or higher
- Terminal/Command prompt

### Compilation and Execution

1. **Clone or download** the project files

2. **Compile the Java files**:
```bash
# Navigate to the project directory
cd /path/to/SnakeAndLadders

# Compile all Java files
javac -cp . -d . src/main/java/com/snakeladder/**/*.java GameApplication.java
```

3. **Run the application**:
```bash
java GameApplication
```

### Alternative Compilation (if using src structure):
```bash
# If you have the standard src/main/java structure
javac -cp src/main/java src/main/java/com/snakeladder/**/*.java GameApplication.java
java -cp .:src/main/java GameApplication
```

## 🎮 How to Play

1. **Start the game** - Run the GameApplication
2. **Set board size** - Choose between 5x5 to 15x15
3. **Add players** - Enter 2-6 player names
4. **Choose difficulty** - Easy, Medium, or Hard
5. **Select generation style** - Random or Balanced
6. **Optional presets** - Choose Beginner, Expert, or Aesthetic presets
7. **Play turns** - Press Enter to roll dice
8. **Win condition** - First player to reach the end wins!

### Board Generation Options

- **Random**: Classic unpredictable placement
- **Balanced**: Even distribution across board zones
- **Presets**:
  - *Beginner*: More ladders, fewer snakes for easier gameplay
  - *Expert*: More snakes, fewer ladders for challenging experience
  - *Aesthetic*: Balanced patterns with optimal gameplay

### Game Rules
- 🎲 Roll dice to move forward
- 🪜 Ladders take you to higher positions
- 🐍 Snakes take you to lower positions
- ⚔️ Landing on another player sends them back to start
- 🎲 Rolling a 6 gives an extra turn
- ❌ Three consecutive 6s revokes your turn

## 🎯 Difficulty Levels

- **Easy**: 10% snakes, 15% ladders (beginner-friendly)
- **Medium**: 15% snakes, 12% ladders (balanced gameplay)
- **Hard**: 20% snakes, 10% ladders (challenging)

## 🔧 Extensibility

The design makes it easy to add new features:

- **New Board Generation**: Implement `BoardGenerationStrategy`
- **New Entity Types**: Implement `BoardEntity`
- **New Game Rules**: Extend the `Game` class
- **Different Display Modes**: Extend `GameDisplay`

## 🧪 Example Game Flow

```
🐍🪜 Welcome to Snakes and Ladders! 🪜🐍

Enter board size (e.g., 7 for 7x7 board) [5-15]: 7
Enter number of players [2-6]: 3

👥 Enter player names:
Player 1 name: Alice
Player 2 name: Bob
Player 3 name: Charlie

🎯 Choose difficulty level:
1. Easy (More ladders, fewer snakes)
2. Medium (Balanced)
3. Hard (More snakes, fewer ladders)
Enter choice [1-3]: 2

🎨 Choose board generation style:
1. Random - Unpredictable placement
2. Balanced - Even distribution across board
Enter choice [1-2]: 2

🎯 Would you like to use a preset configuration? (y/n): y

🏆 Choose preset:
1. Beginner - Easy with more ladders
2. Expert - Challenging with more snakes
3. Aesthetic - Beautiful symmetric patterns
Enter choice [1-3]: 3

🎮 Game Initialized Successfully!
Board Size: 8x8
Difficulty: Medium
Generator: Balanced distribution
[Beautiful game board displays with balanced snakes and ladders...]
```

## 🏆 Key Benefits

- **Educational**: Demonstrates professional Java development practices
- **Maintainable**: Clean code structure following SOLID principles
- **Extensible**: Easy to add new features without breaking existing code
- **Robust**: Proper error handling and input validation
- **User-Friendly**: Interactive CLI with clear instructions and visual feedback

