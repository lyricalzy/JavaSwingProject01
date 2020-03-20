package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import DB.DBconnector;
import DB.SongDTO;

public class SongInfo {
   // 곡 정보를 보여주는 창
   // 곡 id만 들어있는 DTO를 매개변수로 받는다.
   public void showSongInfo(SongDTO song) {
      DBconnector db = new DBconnector();
      ArrayList<SongDTO> result = db.getSongList(song);
      // 곡 id만 들어있는 DTO를 매개변수로 DAO의 getSongList()를 호출했기 때문에
      // sql문도 where song_id = ?로 결정된다.

      JFrame f = new JFrame();
      f.getContentPane().setBackground(new Color(50, 205, 50));
      f.setBackground(new Color(50, 205, 50));
      f.setSize(1000, 600);
      f.getContentPane().setLayout(null);

      JLabel albumCoverLabel = new JLabel("앨범커버 들어갈 자리");
      albumCoverLabel.setBounds(35, 39, 492, 492);
      f.getContentPane().add(albumCoverLabel);

      JLabel songTitleLabel = new JLabel("곡 제목 들어갈 자리");
      songTitleLabel.setFont(new Font("굴림", Font.BOLD, 25));
      songTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
      songTitleLabel.setBounds(593, 66, 315, 61);
      f.getContentPane().add(songTitleLabel);

      JButton artistButton = new JButton("가수 이름 들어갈 자리");
      artistButton.setFont(new Font("굴림", Font.BOLD, 25));
      artistButton.setBounds(593, 147, 315, 61);
      artistButton.addActionListener(new ActionListener() { // 가수 이름 버튼을 눌렀을 때
         public void actionPerformed(ActionEvent e) {
            String artist = artistButton.getText();
            // 가수이름만 들어있는 DTO생성
            SongDTO song = new SongDTO();
            song.setArtist(artist);
            // 차트 객체 생성
            ChartWindow ch = new ChartWindow();
            ch.myChart(song); // 차트를 띄워주는 메소드 호출
            // 가수이름만 들어있는 DTO를 매개변수로 넘겨준다
            f.setVisible(false); // 현재 보고 있는 창은 닫아준다
         }
      });
      f.getContentPane().add(artistButton);

      JButton albumTitleButton = new JButton("앨범 이름 들어갈 자리"); // 앨범 제목 버튼
      albumTitleButton.addActionListener(new ActionListener() { // 앨범 제목 클릭시
         public void actionPerformed(ActionEvent arg0) {
            // 앨범정보창 객체 생성
            AlbumInfo ai = new AlbumInfo();
            ai.showAlbumInfo(albumTitleButton.getText()); // 앨범정보창 띄워주는 메소드 호출
            // 앨범제목을 매개변수로 넘겨준다
            f.setVisible(false); // 현재 보고 있는 창은 닫아준다
         }
      });
      albumTitleButton.setFont(new Font("굴림", Font.BOLD, 25));
      albumTitleButton.setBounds(593, 255, 315, 61);
      f.getContentPane().add(albumTitleButton);

      JButton genreButton = new JButton("장르 들어갈 자리"); // 장르 버튼
      genreButton.setFont(new Font("굴림", Font.BOLD, 25));
      genreButton.setBounds(593, 353, 315, 61);
      genreButton.addActionListener(new ActionListener() { // 장르 클릭시
         public void actionPerformed(ActionEvent e) {
            String genre = genreButton.getText();
            // 장르만 들어있는 DTO생성
            SongDTO song = new SongDTO();
            song.setGenre(genre);
            // 차트 객체 생성
            ChartWindow ch = new ChartWindow();
            ch.myChart(song); // 차트를 띄워주는 메소드 호출
            // 장르만 들어있는 DTO를 매개변수로 넘겨준다
            f.setVisible(false); // 현재 보고 있는 창은 닫아준다
         }
      });
      f.getContentPane().add(genreButton);

      JLabel songLikeLabel = new JLabel("좋아요 숫자 들어갈 자리"); // 좋아요수 표시해주는 라벨
      songLikeLabel.setFont(new Font("굴림", Font.BOLD, 25));
      songLikeLabel.setBounds(761, 449, 147, 61);
      f.getContentPane().add(songLikeLabel);

      JButton songLikeButton = new JButton("♥"); // 좋아요 버튼
      songLikeButton.setBounds(593, 449, 156, 61);
      songLikeButton.addActionListener(new ActionListener() { // 좋아요 버튼 클릭시
         public void actionPerformed(ActionEvent e) {
            // 창에 띄워준 정보 DTO의 좋아요수를 하나 늘려준다
            result.get(0).setSonglike(result.get(0).getSonglike() + 1);
            // 늘려준 좋아요수를 창에 표시해줌
            songLikeLabel.setText(result.get(0).getSonglike() + "");
            // DAO 객체 생성
            DBconnector db = new DBconnector();
            db.like(result.get(0)); // 해당 좋아요수를 DB에 업데이트 해줌
         }
      });
      f.getContentPane().add(songLikeButton);

      // result로 받아온 정보를 창에 표시해줌
      albumCoverLabel.setIcon(new ImageIcon("albumcover\\" + result.get(0).getAlbumcover()));
      songTitleLabel.setText(result.get(0).getSongtitle());
      artistButton.setText(result.get(0).getArtist());
      albumTitleButton.setText(result.get(0).getAlbumtitle());
      genreButton.setText(result.get(0).getGenre());
      songLikeLabel.setText(result.get(0).getSonglike() + "");

      f.setVisible(true);
   }

}