package DB;

public class SongDTO {
	@Override
	public String toString() {
		return "SongDTO [songid=" + songid + ", songtitle=" + songtitle + ", artist=" + artist + ", genre=" + genre
				+ ", albumtitle=" + albumtitle + ", songlike=" + songlike + ", price=" + price + ", albumcover="
				+ albumcover + "]";
	}
	   // 각 테이블에 들어가는 칼럼들을 지역변수로 지정 
	public int songid;
	public String songtitle;
	public String artist;
	public String genre;
	public String albumtitle;
	public int songlike;
	public int price;
	public String albumcover;

	// 각 변수별 getter , setter 설정.
	public int getSongid() {
		return songid;
	}

	public void setSongid(int songid) {
		this.songid = songid;
	}

	public String getSongtitle() {
		return songtitle;
	}

	public void setSongtitle(String songtitle) {
		this.songtitle = songtitle;
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

	public String getAlbumtitle() {
		return albumtitle;
	}

	public void setAlbumtitle(String albumtitle) {
		this.albumtitle = albumtitle;
	}

	public int getSonglike() {
		return songlike;
	}

	public void setSonglike(int songlike) {
		this.songlike = songlike;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAlbumcover() {
		return albumcover;
	}

	public void setAlbumcover(String albumcover) {
		this.albumcover = albumcover;
	}

}
