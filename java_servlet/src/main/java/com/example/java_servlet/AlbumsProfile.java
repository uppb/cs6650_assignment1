package com.example.java_servlet;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * AlbumsProfile
 */


public class AlbumsProfile {
  @SerializedName("artist")
  private String artist = null;

  @SerializedName("title")
  private String title = null;

  @SerializedName("year")
  private String year = null;

  public AlbumsProfile artist(String artist) {
    this.artist = artist;
    return this;
  }

   /**
   * Get artist
   * @return artist
  **/
  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public AlbumsProfile title(String title) {
    this.title = title;
    return this;
  }

   /**
   * Get title
   * @return title
  **/
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public AlbumsProfile year(String year) {
    this.year = year;
    return this;
  }

   /**
   * Get year
   * @return year
  **/
  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlbumsProfile albumsProfile = (AlbumsProfile) o;
    return Objects.equals(this.artist, albumsProfile.artist) &&
        Objects.equals(this.title, albumsProfile.title) &&
        Objects.equals(this.year, albumsProfile.year);
  }

  @Override
  public int hashCode() {
    return Objects.hash(artist, title, year);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlbumsProfile {\n");
    
    sb.append("    artist: ").append(toIndentedString(artist)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
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
