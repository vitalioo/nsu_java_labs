public class ScoringSystem {
    int currentAttempts = 0;
    final int maxAttempts = 8;

    public int getCurrentAttempts() {
        return currentAttempts;
    }

    public int getMaxAttempts(){
        return maxAttempts;
    }

    public void incrementAttempts(){
        ++currentAttempts;
    }

    public String printRemainingAttempts(){
        return ("Now you have " + (maxAttempts - currentAttempts) + " tries");
    }
}