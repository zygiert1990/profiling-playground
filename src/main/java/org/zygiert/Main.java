package org.zygiert;

public class Main {

    static void main(String[] args) throws InterruptedException {
        var nrOfIterations = Integer.parseInt(args[0]);
        var sleepInMillis = Integer.parseInt(args[1]);
        var sum = 0;
        for (int i = 1; i <= nrOfIterations; i++) {
            IO.println("This is: " + i + " iteration");
            Thread.sleep(sleepInMillis);
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

}
