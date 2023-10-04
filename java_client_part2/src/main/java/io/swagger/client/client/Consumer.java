package io.swagger.client.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Consumer implements Runnable{
  private BufferedWriter outputWriter;
  private BlockingQueue<Record> queue;
  private String path;
  private CountDownLatch completed;

  public Consumer(String path, BlockingQueue<Record> queue, CountDownLatch completed) {
    try {
      this.outputWriter = new BufferedWriter(new FileWriter(path));
    }catch (IOException e){
      e.printStackTrace();
    }
    this.completed = completed;
    this.queue = queue;
    this.path = path;
  }

  private synchronized void writeToCsv(Record record) throws IOException {
    String line = record.toString() + "\n";
    outputWriter.append(line);
    outputWriter.flush();
  }


  @Override
  public void run(){
    while(completed.getCount() > 0){
      try {
        Record record = queue.take();
        writeToCsv(record);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    try{
      outputWriter.close();
    }catch (IOException e){
      e.printStackTrace();
    }
  }

}
