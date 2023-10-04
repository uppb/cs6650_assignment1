package com.example.java_servlet;

import com.google.gson.annotations.SerializedName;
import java.io.File;
import java.util.Objects;

/**
 * AlbumsBody
 */

public class AlbumsBody {
  @SerializedName("image")
  private File image = null;

  @SerializedName("profile")
  private AlbumsProfile profile = null;

  public AlbumsBody image(File image) {
    this.image = image;
    return this;
  }

   /**
   * Get image
   * @return image
  **/
  public File getImage() {
    return image;
  }

  public void setImage(File image) {
    this.image = image;
  }

  public AlbumsBody profile(AlbumsProfile profile) {
    this.profile = profile;
    return this;
  }

   /**
   * Get profile
   * @return profile
  **/
  public AlbumsProfile getProfile() {
    return profile;
  }

  public void setProfile(AlbumsProfile profile) {
    this.profile = profile;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlbumsBody albumsBody = (AlbumsBody) o;
    return Objects.equals(this.image, albumsBody.image) &&
        Objects.equals(this.profile, albumsBody.profile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(Objects.hashCode(image), profile);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlbumsBody {\n");
    
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    profile: ").append(toIndentedString(profile)).append("\n");
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
