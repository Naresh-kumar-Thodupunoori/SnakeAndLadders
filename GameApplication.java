import com.snakeladder.model.GameLevel;
import com.snakeladder.service.Game;
import com.snakeladder.service.GameDisplay;
import com.snakeladder.builder.BoardConfiguration;
import com.snakeladder.builder.BoardConfigurationBuilder;
import com.snakeladder.factory.BoardGeneratorFactory;

import java.util.*;

public class GameApplication {
    private static final Scanner sc = new Scanner(System.in);
    private static final GameDisplay gameDisplay = new GameDisplay();
    
    public static void main(String[] args) {
        System.out.println("🐍🪜 Welcome to Snakes and Ladders! 🪜🐍");
        System.out.println("=====================================");
        
        try {
            GameConfiguration userConfig = getUserConfiguration();
            
            BoardConfigurationBuilder builder = new BoardConfigurationBuilder()
                .withSize(userConfig.boardSize)
                .withLevel(userConfig.level)
                .withGeneratorType(userConfig.generatorType);
            
            if (userConfig.usePreset) {
                builder = applyPreset(builder, userConfig.presetType);
            }
            
            BoardConfiguration boardConfig = builder.build();
            Game game = new Game(boardConfig, userConfig.playerNames);
            
            System.out.println("\n🎮 Game Initialized Successfully!");
            System.out.println("Board Size: " + boardConfig.getBoardSize() + "x" + boardConfig.getBoardSize());
            System.out.println("Difficulty: " + boardConfig.getGameLevel().getDisplayName());
            System.out.println("Generator: " + userConfig.generatorType.getDescription());
            System.out.println("Players: " + String.join(", ", userConfig.playerNames));
            
            gameDisplay.displayGameEntities(game.getBoard());
            
            playGame(game);
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
    
    private static GameConfiguration getUserConfiguration() {
        GameConfiguration config = new GameConfiguration();
        
        config.boardSize = getBoardSize();
        
        int playerCount = getPlayerCount();
        
        config.playerNames = getPlayerNames(playerCount);
        
        config.level = getGameLevel();
        
        config.generatorType = getBoardGenerationType();
        
        config.usePreset = askForPreset();
        if (config.usePreset) {
            config.presetType = getPresetType();
        }
        
        return config;
    }
    
    private static int getBoardSize() {
        while (true) {
            try {
                System.out.print("\nEnter board size (e.g., 7 for 7x7 board) [5-15]: ");
                int size = Integer.parseInt(sc.nextLine().trim());
                
                if (size >= 5 && size <= 15) {
                    return size;
                } else {
                    System.out.println("❌ Board size must be between 5 and 15!");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }
    
    private static int getPlayerCount() {
        while (true) {
            try {
                System.out.print("Enter number of players [2-6]: ");
                int count = Integer.parseInt(sc.nextLine().trim());
                
                if (count >= 2 && count <= 6) {
                    return count;
                } else {
                    System.out.println("❌ Number of players must be between 2 and 6!");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }
    
    private static List<String> getPlayerNames(int playerCount) {
        List<String> names = new ArrayList<>();
        Set<String> usedNames = new HashSet<>();
        
        System.out.println("\n👥 Enter player names:");
        
        for (int i = 1; i <= playerCount; i++) {
            while (true) {
                System.out.print("Player " + i + " name: ");
                String name = sc.nextLine().trim();
                
                if (name.isEmpty()) {
                    System.out.println("❌ Name cannot be empty!");
                } else if (usedNames.contains(name.toLowerCase())) {
                    System.out.println("❌ Name already taken! Please choose a different name.");
                } else {
                    names.add(name);
                    usedNames.add(name.toLowerCase());
                    break;
                }
            }
        }
        
        return names;
    }
    
    private static GameLevel getGameLevel() {
        System.out.println("\n🎯 Choose difficulty level:");
        System.out.println("1. Easy (More ladders, fewer snakes)");
        System.out.println("2. Medium (Balanced)");
        System.out.println("3. Hard (More snakes, fewer ladders)");
        
        while (true) {
            try {
                System.out.print("Enter choice [1-3]: ");
                int choice = Integer.parseInt(sc.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        return GameLevel.EASY;
                    case 2:
                        return GameLevel.MEDIUM;
                    case 3:
                        return GameLevel.HARD;
                    default:
                        System.out.println("❌ Please enter 1, 2, or 3!");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }
    
    private static void playGame(Game game) {
        System.out.println("\n🎲 Let's start the game! Press Enter to roll the dice, or 'q' to quit.");
        
        while (!game.isGameEnded()) {
            gameDisplay.displayBoard(game);
            gameDisplay.displayPlayerStatus(game);
            
            System.out.println("\n" + game.getCurrentPlayer().getName() + "'s turn!");
            System.out.print("Press Enter to roll dice (or 'q' to quit, 'h' for help): ");
            
            String input = sc.nextLine().trim().toLowerCase();
            
            if ("q".equals(input) || "quit".equals(input)) {
                System.out.println("👋 Thanks for playing! Goodbye!");
                return;
            }
            
            if ("h".equals(input) || "help".equals(input)) {
                displayHelp();
                continue;
            }
            
            Game.GameResult result = game.playTurn();
            gameDisplay.displayGameResult(result);
            
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        gameDisplay.displayBoard(game);
        System.out.println("\n🎊 Congratulations " + game.getWinner().getName() + "! You won the game! 🎊");
        System.out.println("Thanks for playing Snakes and Ladders!");
    }
    
    private static void displayHelp() {
        System.out.println("\n📖 GAME RULES:");
        System.out.println("=".repeat(50));
        System.out.println("🎯 Objective: Reach the last cell of the board first");
        System.out.println("🎲 Roll dice to move forward");
        System.out.println("🪜 Ladders take you up to higher positions");
        System.out.println("🐍 Snakes take you down to lower positions");
        System.out.println("⚔️  Landing on another player sends them back to start");
        System.out.println("🎲 Rolling a 6 gives you an extra turn");
        System.out.println("❌ Three consecutive 6s revokes your turn");
        System.out.println("🏆 First player to reach the end wins!");
        System.out.println("=".repeat(50));
    }
    
    private static BoardGeneratorFactory.GeneratorType getBoardGenerationType() {
        System.out.println("\n🎨 Choose board generation style:");
        System.out.println("1. Random - Unpredictable placement");
        System.out.println("2. Balanced - Even distribution across board");
        
        while (true) {
            try {
                System.out.print("Enter choice [1-2]: ");
                int choice = Integer.parseInt(sc.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        return BoardGeneratorFactory.GeneratorType.RANDOM;
                    case 2:
                        return BoardGeneratorFactory.GeneratorType.BALANCED;
                    default:
                        System.out.println("❌ Please enter 1 or 2!");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }
    
    private static boolean askForPreset() {
        System.out.print("\n🎯 Would you like to use a preset configuration? (y/n): ");
        String response = sc.nextLine().trim().toLowerCase();
        return response.startsWith("y");
    }
    
    private static String getPresetType() {
        System.out.println("\n🏆 Choose preset:");
        System.out.println("1. Beginner - Easy with more ladders");
        System.out.println("2. Expert - Challenging with more snakes");  
        System.out.println("3. Aesthetic - Beautiful symmetric patterns");
        
        while (true) {
            try {
                System.out.print("Enter choice [1-3]: ");
                int choice = Integer.parseInt(sc.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        return "beginner";
                    case 2:
                        return "expert";
                    case 3:
                        return "aesthetic";
                    default:
                        System.out.println("❌ Please enter 1, 2, or 3!");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }
    
    private static BoardConfigurationBuilder applyPreset(BoardConfigurationBuilder builder, String presetType) {
        switch (presetType) {
            case "beginner":
                return builder.beginnerPreset();
            case "expert":
                return builder.expertPreset();
            case "aesthetic":
                return builder.aestheticPreset();
            default:
                return builder;
        }
    }
    
    private static class GameConfiguration {
        int boardSize;
        List<String> playerNames;
        GameLevel level;
        BoardGeneratorFactory.GeneratorType generatorType;
        boolean usePreset;
        String presetType;
    }
}

