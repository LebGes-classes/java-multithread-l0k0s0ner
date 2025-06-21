package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Task> tasks = Arrays.asList(
                new Task("Task 1", 12),
                new Task("Task 2", 5),
                new Task("Task 3", 9),
                new Task("Task 4", 7),
                new Task("Task 5", 11)
        );

        TaskManager manager = new TaskManager(3, tasks);
        manager.simulateWork();
    }
}
