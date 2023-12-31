/*
 * Album Store API
 *
 * CS6650 Fall 2023
 *
 * API version: 1.0.0
 * Contact: i.gorton@northeasern.edu
 * Generated by: Swagger Codegen (https://github.com/swagger-api/swagger-codegen.git)
 */
package swagger

import (
	"net/http"
	"strconv"
	"fmt"
	"github.com/gin-gonic/gin"
	"encoding/json"
	"strings"
	"regexp"
)

type Album struct{
	Id string `json:"id"`
	Info AlbumInfo `json:"album_info"`
}


//var albums = make(map[int]AlbumInfo)
var albums = 0

func GetAlbumByKey(c *gin.Context) {
	request_id := c.Param("id")
	id, _ := strconv.Atoi(request_id)
	exist := id < albums
	info := AlbumInfo{Artist: "LL", Title: "KK", Year: "1000"}
	/**
	info, exist := albums[id]
	**/
	if(!exist){
		c.IndentedJSON(http.StatusNotFound, ErrorMsg{Msg: "Album Not Found"})
	}else{
		c.IndentedJSON(http.StatusOK, info)
	}
	
}

func extractJSON(s string) *string {
	startIndex := strings.Index(s, "{")
	endIndex := strings.LastIndex(s, "}")
	if !(startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
		return nil
	}
	trimmed := strings.TrimSpace(s[startIndex+1 : endIndex])
	re := regexp.MustCompile(`(\w+):\s*([^:]+)(?:\s|$)`)
	matches := re.FindAllStringSubmatch(trimmed, -1)

	var jsonSb strings.Builder
	jsonSb.WriteString("{")

	for i, match := range matches {
		key := strings.TrimSpace(match[1])
		value := strings.TrimSpace(match[2])

		jsonSb.WriteString(fmt.Sprintf("\"%s\": \"%s\"", key, value))

		if i < len(matches)-1 {
			jsonSb.WriteString(", ")
		}
	}
	jsonSb.WriteString("}")

	result := jsonSb.String()
	return &result
}

func NewAlbum(c *gin.Context) {
	err := c.Request.ParseMultipartForm(32 << 20)
	if err != nil {
		c.JSON(http.StatusBadRequest, ErrorMsg{Msg: err.Error()})
		return
	}

	_, imageFileHeader, err := c.Request.FormFile("image")
	if err != nil {
		c.JSON(http.StatusBadRequest, ErrorMsg{Msg: err.Error()})
		return
	}

	profileData := c.PostForm("profile")
	if(profileData == ""){
		c.JSON(http.StatusBadRequest, ErrorMsg{Msg: "Profile data is required"})
		return
	}
	var profile AlbumsProfile
	cleanedJson := extractJSON(profileData)
	err = json.Unmarshal([]byte(*cleanedJson), &profile)
	if err != nil {
		c.JSON(http.StatusBadRequest, ErrorMsg{Msg: err.Error()})
		return
	}
	if(profile.Artist == "" || profile.Title == "" || profile.Year == "") {
		c.JSON(http.StatusBadRequest, ErrorMsg{Msg: "Profile data is incomplete"})
		return
	}
	/**
	id := len(albums)
	info := AlbumInfo{Artist: profile.Artist, Title: profile.Title, Year: profile.Year}
	albums[id] = info
	**/
	id := albums
	albums++
	image_metadata := ImageMetaData{AlbumID: strconv.Itoa(id), ImageSize: strconv.FormatInt(imageFileHeader.Size, 10)}
	c.IndentedJSON(http.StatusOK, image_metadata)
}
