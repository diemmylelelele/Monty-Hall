import java.util.Random;

public class Simulate {

    // Method to simulate one round of the Monty Hall game
    public static boolean playRound(boolean switchDoor) {
        // Randomly pick the winning door (1, 2, or 3)
        Random rand = new Random();
        int winningDoor = rand.nextInt(3) + 1;
        
        // Player randomly picks a door
        int playerChoice = rand.nextInt(3) + 1;

        // Host opens a door
        int openedDoor = -1;

        if (playerChoice == winningDoor) {
            // If the player's choice is the winning door, randomly choose one of the remaining doors
            int[] remainingDoors = {1, 2, 3};
            // Remove the player's choice and the winning door from remaining doors
            int[] remaining = new int[2];
            int index = 0;
            for (int door : remainingDoors) {
                if (door != playerChoice) {
                    remaining[index++] = door;
                }
            }
            // Randomly choose one of the remaining doors
            openedDoor = remaining[rand.nextInt(2)];
        } else {
            // If the player's choice is not the winning door, the host picks the door that's not the winning or player's choice
            for (int i = 1; i <= 3; i++) {
                if (i != winningDoor && i != playerChoice) {
                    openedDoor = i;
                    break;
                }
            }
        }

        // If player switches, pick the other unopened door
        if (switchDoor) {
            for (int i = 1; i <= 3; i++) {
                if (i != playerChoice && i != openedDoor) {
                    playerChoice = i;
                    break;
                }
            }
        }

        // Return whether the player won by choosing the correct door
        return playerChoice == winningDoor;
    }

    // Method to run multiple simulations and calculate win rates for switch and stay
    public static void simulate(int numRounds) {
        int switchWins = 0;
        int stayWins = 0;

        for (int i = 0; i < numRounds; i++) {
            // Always switch
            if (playRound(true)) {
                switchWins++;
            }

            // Always stay
            if (playRound(false)) {
                stayWins++;
            }
        }

        // Print the win rates for both strategies
        System.out.println("Simulation Results after " + numRounds + " rounds:");
        System.out.println("Always Switch - Wins: " + switchWins + " | Win Rate: " + (switchWins * 100.0 / numRounds) + "%");
        System.out.println("Always Stay - Wins: " + stayWins + " | Win Rate: " + (stayWins * 100.0 / numRounds) + "%");
    }

    public static void main(String[] args) {
        int numRounds = 1000;  // Number of rounds to simulate
        simulate(numRounds);
    }
}
