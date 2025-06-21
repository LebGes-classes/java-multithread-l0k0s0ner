package org.example;

import java.util.concurrent.BlockingQueue;

public class Worker extends Thread {
    private final int workerId;
    private final String name;
    private final BlockingQueue<Task> taskQueue;
    private Task currentTask = null;
    private final ExcelWriter writer;

    public Worker(String name, BlockingQueue<Task> taskQueue, ExcelWriter writer, int workerId) {
        this.name = name;
        this.taskQueue = taskQueue;
        this.writer = writer;
        this.workerId = workerId;
    }

    @Override
    public void run() {
        while (true) {
            int hoursWorkedToday = 0;
            int idleHoursToday = 0;
            int hour = 0;
            String[] hourLog = new String[8]; // массив строк для логирования (для каждого часа пишется задача № или "Отдыхает"

            while (hour < 8) { // дневной цикл
                if (currentTask == null || currentTask.isCompleted()) {
                    currentTask = taskQueue.poll();
                }

                if (currentTask != null) {
                    currentTask.workOneHour();
                    hourLog[hour] = currentTask.getName();
                    hoursWorkedToday++;
                    System.out.printf("[%s] Работает над %s (осталось %dч)\n", name, currentTask.getName(), currentTask.getRemainingTime());
                } else {
                    hourLog[hour] = "Отдыхает";
                    idleHoursToday++;
                    System.out.printf("[%s] Простаивает\n", name);
                }

                hour++;

                try {
                    Thread.sleep(100); // симуляция 1 часа
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            writer.logHourByHour(workerId, name, hourLog, hoursWorkedToday, idleHoursToday);

            if (taskQueue.isEmpty() && (currentTask == null || currentTask.isCompleted())) {
                break;
            }
        }
    }
}
