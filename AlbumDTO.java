package DB;

import java.sql.Date;

//  DB의 테이블을 레코드 단위로 가져오기 위한 클래스
public class AlbumDTO {
	// 각 테이블에 들어가는 칼럼들을 지역변수로 지정 
   int album_id;
   String album_title;
   String artist;
   String genre;
   int album_like;
   Date date;
   String album_cover;
   
   
   // 각 테이블에 들어가는 칼럼들을 지역변수로 지정 
   // 각 변수별 getter , setter 설정.
   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }

   public int getAlbum_id() {
      return album_id;
   }

   public void setAlbum_id(int album_id) {
      this.album_id = album_id;
   }

   public String getAlbum_title() {
      return album_title;
   }

   public void setAlbum_title(String album_title) {
      this.album_title = album_title;
   }

   public String getArtist() {
      return artist;
   }

   public void setArtist(String artist) {
      this.artist = artist;
   }

   public String getGenre() {
      return genre;
   }

   public void setGenre(String genre) {
      this.genre = genre;
   }

   public int getAlbum_like() {
      return album_like;
   }

   public void setAlbum_like(int album_like) {
      this.album_like = album_like;
   }

   public String getAlbum_cover() {
      return album_cover;
   }

   public void setAlbum_cover(String album_cover) {
      this.album_cover = album_cover;
   }

}