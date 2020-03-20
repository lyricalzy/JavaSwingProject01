package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import DB.AlbumDTO;
import DB.DBconnector;
import DB.SongDTO;

public class AlbumInfo {
	// 곡 정보를 나타내는 창을 띄워주는 메소드
	// 곡 제목을 매개변수로 받는다
	public void showAlbumInfo(String albumTitle) {
		JFrame f = new JFrame();

		DBconnector db = new DBconnector(); // DAO 객체 생성
		AlbumDTO result = db.getAlbumInfo(albumTitle); // 앨범제목을 매개변수로 해당 앨범의 정보들을 가져온다
		ArrayList<SongDTO> list = db.getSongList(albumTitle); // 앨범제목을 매개변수로 해당 앨범의 수록곡을 가져온다

		f.getContentPane().setBackground(new Color(50, 205, 50));
		f.setBackground(new Color(50, 205, 50));
		f.setSize(1000, 600);
		f.getContentPane().setLayout(null);

		JLabel albumCoverLabel = new JLabel("앨범커버 들어갈 자리");
		albumCoverLabel.setBounds(35, 39, 492, 492);
		f.getContentPane().add(albumCoverLabel);

		JLabel albumTitleLabel = new JLabel("앨범 제목 들어갈 자리");
		albumTitleLabel.setFont(new Font("굴림", Font.BOLD, 25));
		albumTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		albumTitleLabel.setBounds(593, 66, 315, 61);
		f.getContentPane().add(albumTitleLabel);

		JButton genreButton = new JButton("장르 들어갈 자리");
		genreButton.setFont(new Font("굴림", Font.BOLD, 15));
		genreButton.setBounds(593, 404, 169, 35);
		f.getContentPane().add(genreButton);

		JLabel albumLikeLabel = new JLabel("좋아요 숫자 들어갈 자리");
		albumLikeLabel.setFont(new Font("굴림", Font.BOLD, 25));
		albumLikeLabel.setBounds(761, 449, 147, 61);
		f.getContentPane().add(albumLikeLabel);

		JButton albumLikeButton = new JButton("♥"); // 좋아요 버튼
		albumLikeButton.setBounds(593, 449, 156, 61);
		albumLikeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // 좋아요 버튼 클릭시
				// DTO의 좋아요수를 하나 증가시켜줌
				result.setAlbum_like(result.getAlbum_like() + 1);
				// 늘어난 좋아요수를 창에 표시해줌
				albumLikeLabel.setText(result.getAlbum_like() + "");
				DBconnector db = new DBconnector(); // DAO 객체 생성
				db.like(result); // 변화된 좋아요수를 DB에 업데이트 해줌
			}
		});
		f.getContentPane().add(albumLikeButton);

		JLabel albumDateLabel = new JLabel("발매일 들어갈 자리");
		albumDateLabel.setFont(new Font("굴림", Font.BOLD, 15));
		albumDateLabel.setBounds(773, 404, 160, 35);
		f.getContentPane().add(albumDateLabel);

		String[] sl = new String[list.size()];
		int[] idList = new int[list.size()];
		for (int i = 0; i < sl.length; i++) {
			sl[i] = list.get(i).getSongtitle();
			idList[i] = list.get(i).getSongid(); 
		}
		JList<String> albumSongList = new JList<String>(sl); // 수록곡을 표시해줄 JList
		albumSongList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				@SuppressWarnings("unchecked")
				JList<String> theList = (JList<String>) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 2) { // 수록곡을 더블클릭했을때
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						Object o = theList.getModel().getElementAt(index);
						// 곡제목만 들어있는 DTO 생성
						SongDTO song = new SongDTO();
						song.setSongtitle(o.toString());
						// 곡 정보창 객체 생성
						SongInfo si = new SongInfo();
						si.showSongInfo(song); // 곡 정보창을 띄워주는 메소드 호출
						// 곡제목만 들어있는 DTO를 매개변수로 넘겨준다
						f.setVisible(false); // 현재 보고 있는 창은 닫아준다
					}
				}
			}
		});

		JScrollPane scroll = new JScrollPane(albumSongList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// 앨범 수록곡을 표시해주는 JList에 붙여줄 스크롤. 세로 스크롤은 필요할때만 표시, 가로 스크롤은 항상 표시하지 않는다.
		scroll.setBounds(593, 142, 315, 252);
		f.getContentPane().add(scroll);

		albumCoverLabel.setIcon(new ImageIcon("albumcover\\" + result.getAlbum_cover()));
		albumTitleLabel.setText(result.getAlbum_title());

		genreButton.setText(result.getGenre()); // 장르 버튼
		genreButton.addActionListener(new ActionListener() { // 장르 버튼 클릭시
			public void actionPerformed(ActionEvent e) {
				String genre = genreButton.getText();
				// 장르만 들어있는 DTO 생성
				SongDTO song = new SongDTO();
				song.setGenre(genre);
				// 차트 객체 생성
				ChartWindow ch = new ChartWindow();
				ch.myChart(song); // 차트창을 띄워주는 메소드 호출
				// 장르만 들어있는 DTO를 매개변수로 넘겨준다
				f.setVisible(false); // 현재 보고 있는 창은 닫아준다
			}
		});
		albumDateLabel.setText("발매일 : " + result.getDate()); // 발매일 라벨
		albumLikeLabel.setText(result.getAlbum_like() + ""); // 앨범의 좋아요수를 나타내주는 라벨

		f.setVisible(true);
	}
}