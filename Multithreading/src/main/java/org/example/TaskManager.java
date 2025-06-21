package org.example;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskManager {
    private final List<Task> tasks;
    private final int workerCount;
    private final ExcelWriter writer;

    public TaskManager(int workerCount, List<Task> tasks) {
        this.workerCount = workerCount;
        this.tasks = tasks;
        this.writer = new ExcelWriter("WorkReport.xlsx");
    }

    public void simulateWork() throws InterruptedException {
        BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>(tasks); // потокобезопасная очередь задач

        Worker[] workers = new Worker[workerCount];
        for (int i = 0; i < workerCount; i++) {
            workers[i] = new Worker("Сотрудник " + (i + 1), taskQueue, writer, i);
            workers[i].start(); // запуск потоков
        }

        for (Worker worker : workers) {
            worker.join();
        }

        writer.save();
        System.out.println("Все задачи выполнены. Отчёт сохранён.");
    }
}
