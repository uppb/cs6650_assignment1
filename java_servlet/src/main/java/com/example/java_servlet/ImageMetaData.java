package com.example.java_servlet;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * ImageMetaData
 */


public class ImageMetaData {
  @SerializedName("albumID")
  private String albumID = null;

  @SerializedName("imageSize")
  private String imageSize = null;


  public ImageMetaData albumID(String albumID) {
    this.albumID = albumID;
    return this;
  }

   /**
   * Get albumID
   * @return albumID
  **/
  public String getAlbumID() {
    return albumID;
  }

  public void setAlbumID(String albumID) {
    this.albumID = albumID;
  }

  public ImageMetaData imageSize(String imageSize) {
    this.imageSize = imageSize;
    return this;
  }

   /**
   * Get imageSize
   * @return imageSize
  **/
  public String getImageSize() {
    return imageSize;
  }

  public void setImageSize(String imageSize) {
    this.imageSize = imageSize;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImageMetaData imageMetaData = (ImageMetaData) o;
    return Objects.equals(this.albumID, imageMetaData.albumID) &&
        Objects.equals(this.imageSize, imageMetaData.imageSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(albumID, imageSize);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ImageMetaData {\n");
    
    sb.append("    albumID: ").append(toIndentedString(albumID)).append("\n");
    sb.append("    imageSize: ").append(toIndentedString(imageSize)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
