package com.example.java_servlet;

import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(urlPatterns = "/albums/*")
@MultipartConfig
public class AlbumServlet extends HttpServlet {
  private Gson gson = new Gson();
  private HashMap<Integer, AlbumInfo> albumDb = new HashMap<>();
  private int albumCount = 0;

  private void sendResponse(HttpServletResponse resp, String message) throws IOException{
    PrintWriter out = resp.getWriter();
    out.print(message);
    out.flush();
  }

  private void sendErrorResponse(HttpServletResponse resp, String message) throws IOException {
    ErrorMsg msg = new ErrorMsg();
    msg.setMsg(message);
    sendResponse(resp, gson.toJson(msg));
  }

  private String extractJSON(String s){
    int startIndex = s.indexOf('{');
    int endIndex = s.lastIndexOf('}');
    if (!(startIndex != -1 && endIndex != -1 && endIndex > startIndex)) {
      return null;
    }
    String trimmed = s.substring(startIndex+1, endIndex).trim();
    Pattern pattern = Pattern.compile("(\\w+):\\s*([^:]+)(?:\\s|$)");
    Matcher matcher = pattern.matcher(trimmed);
    StringBuilder jsonSb = new StringBuilder("{");
    while(matcher.find()) {
      String key = matcher.group(1).trim();
      String value = matcher.group(2).trim();

      jsonSb.append(String.format("\"%s\": \"%s\"", key, value));

      if (trimmed.indexOf(matcher.group(0)) + matcher.group(0).length() < trimmed.length()) {
        jsonSb.append(", ");
      }
    }
    jsonSb.append("}");

    return jsonSb.toString();
  }

  private void handleImage(Part req, HttpServletResponse resp, int id) throws IOException{
    ImageMetaData message = new ImageMetaData();
    message.setAlbumID(String.valueOf(id));
    message.setImageSize(String.valueOf(req.getSize()));
    sendResponse(resp, gson.toJson(message));
  }
  private void handleProfile(Part req, HttpServletResponse resp, int id) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
    try{
      StringBuilder sb = new StringBuilder();
      String s;
      while((s=br.readLine()) != null){
        sb.append(s);
      }
      String json = extractJSON(sb.toString());
      AlbumsProfile profile = (AlbumsProfile) gson.fromJson(json, AlbumsProfile.class);
      /**
      AlbumInfo info = new AlbumInfo();
      info.setArtist(profile.getArtist());
      info.setTitle(profile.getTitle());
      info.setYear(profile.getYear());
      albumDb.put(id, info);**/
    }catch (Exception ex){
      sendErrorResponse(resp, ex.getMessage());
    }
  }
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    //int id = albumDb.size();
    int id = albumCount;
    albumCount++;
    Part profile_request = req.getPart("profile");
    handleProfile(profile_request, resp, id);
    Part image_request = req.getPart("image");
    handleImage(image_request, resp, id);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    String pathInfo = req.getPathInfo();
    if(pathInfo == null){
      sendErrorResponse(resp, "Invalid Request");
    }else{
      String[] parts = pathInfo.substring(1).split("/");
      if(parts.length > 1){
        sendErrorResponse(resp, "Invalid Request");
      }else{
        try{
          int albumID = Integer.parseInt(parts[0]);
          /***
          AlbumInfo albumInfo = albumDb.get(albumID);
          if(albumInfo != null){
            sendResponse(resp, gson.toJson(albumInfo));
          }else{
            sendErrorResponse(resp, "No such album");
          }
           ***/
          AlbumInfo info = new AlbumInfo();
          info.setArtist("LL");
          info.setTitle("KK");
          info.setYear("1000");
          if(albumID < albumCount){
            sendResponse(resp, gson.toJson(info));
          }else{
            sendErrorResponse(resp, "No such album");
          }
        }catch (NumberFormatException ex){
          sendErrorResponse(resp, ex.getMessage());
        }
      }
    }
  }
}
