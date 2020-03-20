package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DBconnector {
	String url = "jdbc:mysql://localhost:3708/project?characterEncoding=utf8&serverTimezone=UTC";
	String user = "root";
	String password = "1234";
	String sql = null;
	Connection con = null;
	ResultSet rs = null;
	Statement stmt = null;
	PreparedStatement ps = null;

	// 실시간 차트
	// 테이블의 모든 곡을 좋아요수 기준 내림차순으로 100곡 가져옴
	public ArrayList<SongDTO> getSongList() {
		// song테이블에서 select해온 모든 내용을 레코드 단위로 배열에 넣음
		// 각 레코드는 DTO로 만들어짐
		ArrayList<SongDTO> songList = new ArrayList<SongDTO>();
		try {
			// 1) 커넥터 설정
			Class.forName("com.mysql.jdbc.Driver");

			// 2) DB연결
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();

			// 3) sql문 결정
			sql = "select * from song order by song_like desc limit 100"; // 테이블에서 좋아요수가 높은 순서로 100곡만 가져옴

			// 4) sql문 전송
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				// 곡DTO에 각 요소를 넣어준다
				SongDTO bag = new SongDTO();
				bag.setSongid(rs.getInt(1));
				bag.setSongtitle(rs.getString(2));
				bag.setArtist(rs.getString(3));
				bag.setGenre(rs.getString(4));
				bag.setAlbumtitle(rs.getString(5));
				bag.setSonglike(rs.getInt(6));
				bag.setPrice(rs.getInt(7));
				bag.setAlbumcover(rs.getString(8));
				songList.add(bag); // 만든 DTO를 리스트에 넣어줌
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return songList; // DTO의 리스트(테이블 전체) 반환
	}

	// 앨범의 수록곡을 가져오기 위한 함수
	// 해당 앨범제목을 select의 기준으로 삼는다.
	public ArrayList<SongDTO> getSongList(String albumTitle) {
		// song테이블에서 select해온 모든 내용을 레코드 단위로 배열에 넣음
		ArrayList<SongDTO> songList = new ArrayList<SongDTO>();
		try {
			// 1) 커넥터 설정
			Class.forName("com.mysql.jdbc.Driver");

			// 2) DB연결
			con = DriverManager.getConnection(url, user, password);

			// 3) sql문 결정
			// 앨범제목으로 해당하는 곡을 찾아준다
			sql = "select song_title from song where album_title = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, albumTitle);

			// 4) sql문 전송
			rs = ps.executeQuery();

			while (rs.next()) {
				SongDTO bag = new SongDTO();
				bag.setSongtitle(rs.getString(1)); // 곡제목만 필요
				songList.add(bag);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return songList;
	}

	// db의 song 테이블에서 원하는 곡(record)들을 가져옴
	public ArrayList<SongDTO> getSongList(SongDTO song) {
		// 곡(record) 단위로 배열에 저장
		ArrayList<SongDTO> songList = new ArrayList<SongDTO>();

		try {
			// 1) 커넥터 설정
			Class.forName("com.mysql.jdbc.Driver");

			// 2) DB연결
			con = DriverManager.getConnection(url, user, password);

			// 3) sql문 결정
			if (song.getSongid() != 0) { // 매개변수로 받은 DTO가 곡 id만 들어있는 DTO일때
				sql = "select * from song where song_id = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, song.getSongid());
			} else if (song.getArtist() != null) { // 매개변수로 받은 DTO가 가수이름만 들어있는 DTO일때
				sql = "select * from song where artist = ? order by song_like desc";
				ps = con.prepareStatement(sql);
				ps.setString(1, song.getArtist());
			} else if (song.getGenre() != null) { // 매개변수로 받은 DTO가 장르만 들어있는 DTO일때
				sql = "select * from song where genre = ? order by song_like desc";
				ps = con.prepareStatement(sql);
				ps.setString(1, song.getGenre());
			} else if (song.getSongtitle() != null) { // 매개변수로 받은 DTO가 곡 제목만 들어있는 DTO일때
				sql = "select * from song where song_title = ? order by song_like desc";
				ps = con.prepareStatement(sql);
				ps.setString(1, song.getSongtitle());
			}

			// 4) sql문 전송
			rs = ps.executeQuery();

			while (rs.next()) {
				SongDTO result = new SongDTO();
				result.setSongid(rs.getInt(1));
				result.setSongtitle(rs.getString(2));
				result.setArtist(rs.getString(3));
				result.setGenre(rs.getString(4));
				result.setAlbumtitle(rs.getString(5));
				result.setSonglike(rs.getInt(6));
				result.setPrice(rs.getInt(7));
				result.setAlbumcover(rs.getString(8));
				songList.add(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return songList;
	}

	// 앨범의 이름을 바탕으로 album 테이블에서 레코드를 가져옴
	public AlbumDTO getAlbumInfo(String albumTitle) {
		AlbumDTO result = new AlbumDTO();
		try {
			// 1) 커넥터 설정
			Class.forName("com.mysql.jdbc.Driver");

			// 2) DB연결
			con = DriverManager.getConnection(url, user, password);

			// 3) sql문 결정
			sql = "select * from album where album_title = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, albumTitle);

			// 4) sql문 전송
			rs = ps.executeQuery();

			while (rs.next()) {
				result.setAlbum_id(rs.getInt(1));
				result.setAlbum_title(rs.getString(2));
				result.setArtist(rs.getString(3));
				result.setGenre(rs.getString(4));
				result.setAlbum_like(rs.getInt(5));
				result.setDate(rs.getDate(6));
				result.setAlbum_cover(rs.getString(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// 곡 좋아요 버튼 눌렀을 때 db처리
	public void like(SongDTO song) {
		try {
			// 1) 커넥터 설정
			Class.forName("com.mysql.jdbc.Driver");

			// 2) DB연결
			con = DriverManager.getConnection(url, user, password);

			// 3) sql문 결정
			sql = "update song set song_like = ? where song_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, song.getSonglike());
			ps.setInt(2, song.getSongid());

			// 4) sql문 전송
			int result = ps.executeUpdate();
			if (result == 0) { // 좋아요 반영 실패시
				JOptionPane.showMessageDialog(null, "좋아요 반영 실패");
			} else { // 좋아요 반영 성공시
				// 메시지 출력
				JOptionPane.showMessageDialog(null, "좋아요♥");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 앨범의 좋아요를 눌렀을 때 db처리
	public void like(AlbumDTO album) {
		try {
			// 1) 커넥터 설정
			Class.forName("com.mysql.jdbc.Driver");

			// 2) DB연결
			con = DriverManager.getConnection(url, user, password);

			// 3) sql문 결정
			sql = "update album set album_like = ? where album_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, album.getAlbum_like());
			ps.setInt(2, album.getAlbum_id());

			// 4) sql문 전송
			int result = ps.executeUpdate();
			if (result == 0) { // 좋아요 반영 실패시
				JOptionPane.showMessageDialog(null, "좋아요 반영 실패");
			} else { // 좋아요 반영 성공시
				// 메시지 출력
				JOptionPane.showMessageDialog(null, "좋아요♥");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 장바구니 버튼 눌렀을 때 db처리
	public void cart(SongDTO song, String id) {
		try {
			// 1) 커넥터 설정
			Class.forName("com.mysql.jdbc.Driver");

			// 2) DB연결
			con = DriverManager.getConnection(url, user, password);

			// 3) sql문 결정
			sql = "insert into cart values(?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, song.getSongid());
			ps.setString(2, song.getSongtitle());
			ps.setString(3, song.getArtist());
			ps.setString(4, song.getAlbumcover());
			ps.setString(5, id);

			// 4) sql문 전송
			int result = ps.executeUpdate();
			if (result == 0) { // 좋아요 반영 실패시
				JOptionPane.showMessageDialog(null, "장바구니 추가 실패");
			} else { // 좋아요 반영 성공시
				// 메시지 출력
				JOptionPane.showMessageDialog(null, "해당 곡을 장바구니에 넣었습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}