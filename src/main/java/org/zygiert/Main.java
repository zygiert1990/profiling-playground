package org.zygiert;

public class Main {

    static void main(String[] args) throws InterruptedException {
        var nrOfIterations = Integer.parseInt(args[0]);
        var sleepInMillis = Integer.parseInt(args[1]);
        var argumentHolder = new ArgumentsHolder(nrOfIterations, sleepInMillis);
        var sum = 0;
        for (int i = 1; i <= argumentHolder.getNrOfIterations(); i++) {
            IO.println("This is: " + i + " iteration");
            Thread.sleep(resolveSleepInMillis(i, argumentHolder.getSleepInMillis()));
            sum += resolveNumber(i);
        }
        IO.println("My fantastic sum is: " + sum);
    }

    private static int resolveNumber(int i) {
        if (i < 10000) {
            return i;
        } else {
            return i * 2;
        }
    }

    private static int resolveSleepInMillis(int i, int sleepInMillis) {
        if (i < 20000) {
            return sleepInMillis;
        } else {
            return sleepInMillis / 100;
        }
    }

    private static final class ArgumentsHolder {

        private final int nrOfIterations;
        private final int sleepInMillis;

        private ArgumentsHolder(int nrOfIterations, int sleepInMillis) {
            this.nrOfIterations = nrOfIterations;
            this.sleepInMillis = sleepInMillis;
        }

        private int getNrOfIterations() {
            return nrOfIterations;
        }

        private int getSleepInMillis() {
            return sleepInMillis;
        }
    }

}
