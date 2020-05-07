package com.javabyexamples.java.concurrency.buildingblocks.synchronizers.lock;

import com.javabyexamples.java.concurrency.common.runner.Runner;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Main().run(new ArrayList<>(), 100);
        }
    }

    public void run(List<Integer> list, int capacity) throws InterruptedException {
        final BoundedListWithLock<Integer> boundedList = new BoundedListWithLock<>(list, capacity);
        Runner runner = Runner.builder()
          .threadCount(100)
          .addAction()
          .action(() -> {
              boundedList.add(Integer.MIN_VALUE);
          })
          .executionCount(100)
          .added()
          .addAction()
          .action(() -> {
              boundedList.remove(Integer.MIN_VALUE);
          })
          .executionCount(100)
          .added()
//          .addAction()
//          .action(() -> {
//              while (true) {
//                  try {
//                      Thread.sleep(500);
//                      System.out.println(boundedList.size());
//                  } catch (InterruptedException e) {
//                      e.printStackTrace();
//                  }
//              }
//          })
//          .executionCount(1)
//          .added()
          .build();
        runner.run();
        System.out.printf("Size: %s%n", boundedList.size());
    }
}
