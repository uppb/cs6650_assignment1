package io.swagger.client.client;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.AlbumInfo;
import io.swagger.client.model.AlbumsProfile;
import io.swagger.client.model.ImageMetaData;
import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Producer implements Runnable{
  private String IPAddr;
  private String image_file;
  private BlockingQueue<Record> queue;
  private CountDownLatch completed;
  public Producer(String IPAddr,
      String image_file, BlockingQueue queue, CountDownLatch completed) {
    this.IPAddr = IPAddr;
    this.image_file = image_file;
    this.queue = queue;
    this.completed = completed;
  }

  public static void sendPost(DefaultApi api, String img) {
    File image = new File(img);
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
  public static void sendGet(DefaultApi api, String albumID) {
    try {
      AlbumInfo result = api.getAlbumByKey(albumID);
      //System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getAlbumByKey");
      e.printStackTrace();
    }
  }

  private void getRuntime(DefaultApi api, String type) throws InterruptedException{
    long start_time = System.currentTimeMillis();
    if(type.equals("POST")) {
      sendPost(api, this.image_file);
    }else{
      sendGet(api, "0");
    }
    long end_time = System.currentTimeMillis();
    Record record = new Record(start_time, end_time, type, 200);
    queue.put(record);
  }

  @Override
  public void run(){
    ApiClient client = new ApiClient();
    client.setBasePath(IPAddr);
    DefaultApi apiInstance = new DefaultApi(client);
    for(int i = 0; i < 1000; i++) {
      try {
        getRuntime(apiInstance, "POST");
        getRuntime(apiInstance, "GET");
      } catch (InterruptedException e) {
        System.out.println(e.getMessage());
      }
    }
    this.completed.countDown();
  }
}
