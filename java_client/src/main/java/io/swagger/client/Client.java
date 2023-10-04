package io.swagger.client;

import io.swagger.client.*;
import io.swagger.client.model.*;
import io.swagger.client.api.DefaultApi;

import java.io.File;
import java.util.concurrent.CountDownLatch;

public class Client {

  private static void sendPost(DefaultApi api, String image_file) {
    File image = new File(image_file);
    AlbumsProfile profile = new AlbumsProfile();
    profile.setArtist("Me");
    profile.setTitle("Some Title");
    profile.setYear("2018");
    try {
      ImageMetaData result = api.newAlbum(image, profile);
      //System.out.println(result);
    } catch (ApiException e) {
      System.out.println(e.getMessage());
      System.err.println("Exception when calling DefaultApi#newAlbum");
      e.printStackTrace();
    }
  }
  private static void sendGet(DefaultApi api, String albumID) {
    try {
      AlbumInfo result = api.getAlbumByKey(albumID);
      //System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getAlbumByKey");
      e.printStackTrace();
    }
  }

  private static void initializate(int initialNumThreads, String IPAddr, String image_file){
    Thread[] initial_runnables = new Thread [initialNumThreads];
    for(int i = 0; i < initialNumThreads; i++){
      Runnable thread = () -> {
        ApiClient client = new ApiClient();
        client.setBasePath(IPAddr);
        DefaultApi apiInstance = new DefaultApi(client);
        sendPost(apiInstance, image_file);
        for(int j = 0; j < 100; j++){
          sendGet(apiInstance,"0");
        }
      };
      initial_runnables[i] = new Thread(thread);
    }
    for(int i = 0; i < initialNumThreads; i++){
      initial_runnables[i].start();
    }
    try {
      for(int i = 0; i < initialNumThreads; i++){
        initial_runnables[i].join();
      }
    } catch (InterruptedException e) {
      System.err.println(e.getMessage());
    }
  }

  private static void runThreadGroup(int nThreads, String IPAddr, String image_file, CountDownLatch countdown){
    Thread[] threads = new Thread [nThreads];
    for(int i = 0; i < nThreads; i++){
      Runnable thread = () -> {
        ApiClient client = new ApiClient();
        client.setBasePath(IPAddr);
        DefaultApi apiInstance = new DefaultApi(client);
        for(int j = 0; j < 1000; j++) {
          sendPost(apiInstance, image_file);
          sendGet(apiInstance,"0");
        }
        countdown.countDown();
      };
      threads[i] = new Thread(thread);
    }
    for(int i = 0; i < nThreads; i++){
      threads[i].start();
    }
  }

  private static void getTime(int threadGroupSize, int numThreadGroups, int delay, String IPAddr)
      throws InterruptedException {
    final int initialNumThreads = 10;
    final String image_path = "/Users/sunny/Downloads/nmtb.png";

    initializate(initialNumThreads, IPAddr, image_path);
    System.out.println("Finished Initializing");
    CountDownLatch completed = new CountDownLatch(threadGroupSize*numThreadGroups);
    long startTime = System.currentTimeMillis();
    for(int i = 0; i < numThreadGroups; i++){
      runThreadGroup(threadGroupSize, IPAddr, image_path, completed);
      Thread.sleep(1000L *delay);
    }
    completed.await();
    long endTime = System.currentTimeMillis();
    double wallTime = (double) ((endTime - startTime) - 1000L * delay * numThreadGroups) /1000L;
    double throughput = (1000L *threadGroupSize*numThreadGroups) / wallTime;
    System.out.println("wallTime:" + wallTime);
    System.out.println("throughput: " + throughput);
  }

  public static void main(String[] args) throws InterruptedException {
    try{
      getTime(10,30,2,"http://35.91.157.87:8080");
    }catch (Exception e){
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
}
