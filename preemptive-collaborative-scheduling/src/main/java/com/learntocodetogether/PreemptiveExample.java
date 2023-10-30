package com.learntocodetogether;

public class PreemptiveExample {
  public static void main(String[] args) {
    Thread threadA = new Thread(() -> {
      for(int i = 0; i < 5; i++) {
        System.out.println("Thread A iteration: " + i);
      }
    });
    Thread threadB = new Thread(() -> {
      for (int i = 0; i < 5; i++) {
        System.out.println("Thread B iteration: " + i);
      }
    });
    threadA.setPriority(Thread.MIN_PRIORITY);
    threadB.setPriority(Thread.MAX_PRIORITY);
    threadA.start();
    threadB.start();
  }
}
