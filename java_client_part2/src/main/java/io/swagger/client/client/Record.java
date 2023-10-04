package io.swagger.client.client;

public class Record {
  private long startTime;
  private String requestType;
  private double latency;
  private int responseCode;

  public Record(long startTime, long endTime, String requestType, int responseCode) {
    this.startTime = startTime;
    this.latency = endTime - startTime;
    this.requestType = requestType;
    this.responseCode = responseCode;
  }

  @Override
  public String toString() {
    return String.format("%d,%f,%s,%d", startTime, latency, requestType, responseCode);
  }
}
