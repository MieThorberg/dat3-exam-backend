package controller;


import java.util.TimerTask;

public class Timer {

    java.util.Timer timer;
    TimerTask timerTask;
    int amountOfTime;
    boolean hasStopped = false;
    long secLeft;
    long minLeft;

    public Timer() {

    }

    public Timer(int amountOfTime) {
        this.amountOfTime = amountOfTime;
    }

    public void setTimesLeft(long time) {
        long timeLeft = amountOfTime-time;
        long secondsDisplay = timeLeft % 60;
        long elapsedMinutes = timeLeft / 60;
        System.out.println("min: " + elapsedMinutes + " secs:" + secondsDisplay);

    }

    public void start() {
        timer = new java.util.Timer();
        timerTask = new TimerTask() {
            int i = 0;
            long startTime = System.currentTimeMillis();
            @Override
            public void run() {

                long elapsedTime = System.currentTimeMillis() - startTime;
                long elapsedSeconds = elapsedTime / 1000;
                long secondsDisplay = elapsedSeconds % 60; //ends at 59 and starts from 0 secs
                long elapsedMinutes = elapsedSeconds / 60;
                setTimesLeft(elapsedSeconds);
                System.out.println("min: " + elapsedMinutes + " secs:" + secondsDisplay);
                if(System.currentTimeMillis() - startTime > amountOfTime * 1000 || hasStopped) {
                    timerTask.cancel();
                    timer.cancel();
                }

            };
        };
        timer.schedule(timerTask, 1000, 1000);
    }
    public void stop() {
       hasStopped = true;
    }

    public static void main(String[] args) {
        Timer timer = new Timer(70);
        timer.start();
    }
}