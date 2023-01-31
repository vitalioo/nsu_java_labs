import java.util.Scanner;

public class Play {
    Scanner scan = new Scanner(System.in);

    ScoringSystem scoringSystem = new ScoringSystem();

    RandomNumber rand = new RandomNumber();
    private final int randomNumber = rand.generateNumber();

    private boolean state = true;

    public void printMessage(String message){
        System.out.println(message);
    }

    public Play(){
        printMessage("Hi player! Try to guess my number from 1 to 100");

        while(state){
            if(scoringSystem.getCurrentAttempts() == scoringSystem.getMaxAttempts()){
                System.out.println();
                printMessage("You have to lose all your attempts. My number was " + randomNumber + ".");
                break;
            }
            int guessNumber = scan.nextInt();
            if(randomNumber == guessNumber){
                state = false;
                printMessage("Congratulations!! Bye");
            } else if(randomNumber < guessNumber){
                scoringSystem.incrementAttempts();
                printMessage("My number is less than you think. " + scoringSystem.printRemainingAttempts());
            } else {
                scoringSystem.incrementAttempts();
                printMessage("My number is more than you think. " + scoringSystem.printRemainingAttempts());
            }
        }
    }
}