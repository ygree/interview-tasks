/*
 * (c) 2014, Gribkov Yury
 * http://gribkov.me/
 */

package me.gribkov.interviewtasks.deadlock;

/**
 * Once, on interview I was asked to write java code that ends up with deadlock.
 * So, here it is.
 * To synchronize two threads after each of them got one lock, I use wait/notify.
 * There also could been used Thread.sleep for the same purpose.
 */
public class ThreadDeadlock {

  public static void main(String...args) {
      System.out.println("Run");

      final Object o1 = new Object();
      final Object o2 = new Object();
      final Object o3 = new Object();

      Runnable r1 = new Runnable() {
         public void run() {
             synchronized (o1) {
                 synchronized (o3) {
                     try {
                         o3.wait();
                     } catch (InterruptedException e) {
                         System.out.println("interrupted");
                     }
                 }
                 System.out.println("r2 waits for o2");
                 synchronized (o2) {
                     System.out.println("r1 got both locks");
                 }
             }
         }
      };

      Runnable r2 = new Runnable() {
         public void run() {
             synchronized (o2) {
                 synchronized (o3) {
                     o3.notify();
                 }
                 System.out.println("r2 waits for o1");
                 synchronized (o1) {
                     System.out.println("r2 got both locks");
                 }
             }
         }
      };

      /**
       * Be aware!
       * Thread.run() doesn't actually starts new thread,
       * rather runs passed runnable implementation in the current thread.
       */
      new Thread(r1).start();
      new Thread(r2).start();
  }
}