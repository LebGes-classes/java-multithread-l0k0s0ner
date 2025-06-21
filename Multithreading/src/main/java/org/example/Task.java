package org.example;

public class Task {
    private final String name;
    private final int duration;
    private int remainingTime;

    public Task(String name, int duration) {
        this.name = name;
        this.duration = duration;
        this.remainingTime = duration;
    }

    public synchronized int workOneHour() {
        if (remainingTime > 0) {
            remainingTime--;
            return 1;
        }
        return 0;
    }

    public boolean isCompleted() {return remainingTime == 0;}

    public String getName() {return name;}
    public int getInitialDuration() {return duration;}
    public int getRemainingTime() {return remainingTime;}
}
