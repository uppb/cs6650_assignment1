package com.example.java_servlet;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * ErrorMsg
 */


public class ErrorMsg {
  @SerializedName("msg")
  private String msg = null;

  public ErrorMsg msg(String msg) {
    this.msg = msg;
    return this;
  }

   /**
   * Get msg
   * @return msg
  **/
  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorMsg errorMsg = (ErrorMsg) o;
    return Objects.equals(this.msg, errorMsg.msg);
  }

  @Override
  public int hashCode() {
    return Objects.hash(msg);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorMsg {\n");
    
    sb.append("    msg: ").append(toIndentedString(msg)).append("\n");
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
