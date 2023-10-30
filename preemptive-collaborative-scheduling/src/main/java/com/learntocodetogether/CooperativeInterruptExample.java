package com.learntocodetogether;

public class CooperativeInterruptExample {
  public static void main(String[] args) {
    Thread myThread = new Thread(() -> {
      for (int i = 0; i < 5; i++) {
        System.out.println("Thread running: " + i);
        try {
          Thread.sleep(1000); // Simulating some task execution
        } catch (InterruptedException e) {
          System.out.println("Thread interrupted while sleeping.");
          Thread.currentThread().interrupt(); // Re-set the interrupted flag
          return; // Exit on interruption
        }
      }
      System.out.println("Thread finished its work.");
    });
    myThread.setName("myThread");
    myThread.start();
    try {
      Thread.sleep(2000);
      myThread.interrupt();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
