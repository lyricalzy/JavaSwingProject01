package ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import DB.DBconnector;
import DB.SongDTO;

public class Main extends JFrame {
	GridBagLayout gbl; // 그리드백 레이아웃을 사용하기 위한 변수 선언
	GridBagConstraints gbc; // 그리드백 레이아웃에 넣을 요소 변수 선언
	static String logId; // 로그인이 되면 로그인된 아이디로 바꿔줄 전역변수

	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 닫으면 프로그램 종료

		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		setLayout(gbl); // 레이아웃을 그리드백 레이아웃으로 설정
		gbc.fill = GridBagConstraints.BOTH; // 빈 공간 없이 채워줌

		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		JLabel melonLbl = new JLabel();
		melonLbl.setIcon(new ImageIcon("logo_melon142x99.png"));
		if (getLogId() == null) { // 로그인이 안된 상태
			// 멜론메인로고를 넣어줄 라벨과 로그인,곡구매,나만의 앨범 버튼 4개는 윗줄에 표시 해줌
			gbAdd(melonLbl, 0, 0, 1, 1);
			JButton logInBtn = new JButton("로그인");
			logInBtn.addActionListener(new ActionListener() { // 앨범제목 클릭시
				public void actionPerformed(ActionEvent e) {
					Login login = new Login();
					login.loginUI();

					System.out.println(getLogId());
					setVisible(false);

				}
			});
			gbAdd(logInBtn, 1, 0, 1, 1);
			// 곡 구매 버튼
			JButton buyBtn = new JButton("곡 구매");
			buyBtn.addActionListener(new ActionListener() { // 로그인 안된상태에서 곡구매 눌렀을 때
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "로그인을 해주시길 바랍니다.");

				}
			});
			gbAdd(buyBtn, 2, 0, 1, 1);

			JButton myalbumBtn = new JButton("나만의 앨범");
			myalbumBtn.addActionListener(new ActionListener() { // 로그인 안된상태에서 곡구매 눌렀을 때
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "로그인을 해주시길 바랍니다.");

				}
			});
			gbAdd(myalbumBtn, 3, 0, 1, 1);
			// 실시간 차트 제목 라벨
			JLabel chart = new JLabel("실시간 차트");
			chart.setHorizontalAlignment(SwingConstants.CENTER);
			chart.setFont(new Font("굴림", Font.BOLD, 25));
			gbAdd(chart, 0, 1, 4, 1);

			// 실시간차트를 나머지 요소보다 세로길이를 길게 해줌
			gbc.weighty = 20.0;
			// 실시간차트를 넣은 패널
			DBconnector db = new DBconnector(); // DAO 객체 생성
			ArrayList<SongDTO> list = db.getSongList(); // 실시간 차트를 불러오기 위한 메소드 호출
			// 메소드의 결과인 DTO의 리스트를 변수로 저장해둠s

			JPanel chartPanel = new JPanel(); // 실시간 차트를 올릴 패널
			chartPanel.setBackground(new Color(50, 205, 50));
			chartPanel.setPreferredSize(new Dimension(900, list.size() * 197)); // 패널의 크기 설정
			// 전체 곡 리스트의 크기 만큼만 패널의 크기를 설정해줌
			for (int i = 0; i < list.size(); i++) { // 실시간 차트 생성
				JLabel rankLbl1 = new JLabel((i + 1) + "위: "); // 순위 라벨
				chartPanel.add(rankLbl1);

				int songId = list.get(i).getSongid(); // 곡의 id번호를 임시로 저장해둠
				String albumCover = list.get(i).getAlbumcover(); // 앨범커버의 파일명을 임시로 저장해둠
				JLabel albumCoverLbl = new JLabel("앨범 이미지"); // 앨범커버를 넣을 라벨
				String imgPath = "albumcover\\" + albumCover;
				// 앨범커버 이미지를 원하는 크기로 불러옴
				// ImageIcon객체를 생성
				ImageIcon originIcon = new ImageIcon(imgPath);
				// ImageIcon에서 Image를 추출
				Image originImg = originIcon.getImage();
				// 추출된 Image의 크기를 조절하여 새로운 Image객체 생성
				Image changedImg = originImg.getScaledInstance(192, 192, Image.SCALE_SMOOTH);
				// 새로운 Image로 ImageIcon객체를 생성
				ImageIcon icon = new ImageIcon(changedImg);
				albumCoverLbl.setPreferredSize(new Dimension(192, 192));
				albumCoverLbl.setIcon(icon);
				chartPanel.add(albumCoverLbl);
				JButton songTitleBtn = new JButton(list.get(i).getSongtitle()); // 곡제목을 넣을 버튼
				songTitleBtn.addActionListener(new ActionListener() { // 곡 제목 클릭시
					public void actionPerformed(ActionEvent e) {
						SongDTO song = new SongDTO();
						song.setSongid(songId);
						// 곡의 정보창 객체 생성
						SongInfo si = new SongInfo();
						// 곡 id만 들어있는 songDTO를 매개로 넘겨준다.
						si.showSongInfo(song); // 곡의 정보를 보여주는 창을 띄워줌
					}
				});
				songTitleBtn.setPreferredSize(new Dimension(250, 50));
				chartPanel.add(songTitleBtn);
				JButton artistBtn = new JButton(list.get(i).getArtist()); // 가수이름 넣을 버튼
				artistBtn.addActionListener(new ActionListener() { // 가수 이름 클릭시
					public void actionPerformed(ActionEvent e) {
						String artist = artistBtn.getText();
						SongDTO song = new SongDTO();
						song.setArtist(artist);
						ChartWindow ch = new ChartWindow(); // 차트 객체 생성
						ch.myChart(song); // 해당 가수가 부른 곡을 인기순위로 띄워줌
					}
				});
				artistBtn.setPreferredSize(new Dimension(150, 50));
				chartPanel.add(artistBtn);
				JButton albumTitleBtn = new JButton(list.get(i).getAlbumtitle()); // 앨범제목 넣을 버튼
				albumTitleBtn.addActionListener(new ActionListener() { // 앨범제목 클릭시
					public void actionPerformed(ActionEvent e) {
						String albumTitle = albumTitleBtn.getText();
						AlbumInfo ai = new AlbumInfo(); // 앨범 정보창 객체 생성
						// 앨범의 정보창을 띄워줄 메소드 호출
						// 앨범제목을 매개로 넘겨준다.
						ai.showAlbumInfo(albumTitle);
					}
				});
				albumTitleBtn.setPreferredSize(new Dimension(250, 50));
				chartPanel.add(albumTitleBtn);

				JLabel songLikeLbl = new JLabel(list.get(i).getSonglike() + ""); // 해당곡의 좋아요수를 표시해줄 라벨
				songLikeLbl.setHorizontalAlignment(SwingConstants.CENTER);
				songLikeLbl.setPreferredSize(new Dimension(50, 50));
				chartPanel.add(songLikeLbl);

			}

			JScrollPane js = new JScrollPane(chartPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 차트를 넣은 패널에 스크롤을 붙여줌
			// 세로스크롤은 필요할때만 표시하고, 가로스크롤은 표시하지 않는다.
			js.setPreferredSize(new Dimension(900, list.size() * 197)); // 스크롤의 크기 설정, 차트의 크기만큼
			gbAdd(js, 0, 2, 4, 1);
		} else { // 로그인이 된 상태
			// 로그인이 된 상태에서는 멜론 메인로고라벨, 버튼3개 외에 새로운 라벨과 버튼을 추가해준다
			gbAdd(melonLbl, 0, 0, 1, 1);
			gbAdd(new JLabel(getLogId() + "님 어서오세요"), 1, 0, 1, 1);
			JButton myInfoBtn = new JButton("내 정보 보기");
			myInfoBtn.addActionListener(new ActionListener() { // 내 정보 보기 클릭시
				public void actionPerformed(ActionEvent e) {
					MyInfoUpdate myinfo = new MyInfoUpdate();
					myinfo.MyInfoUpdateUI();
					setVisible(false);
				}
			});
			gbAdd(myInfoBtn, 1, 1, 1, 1);
			JButton logInBtn = new JButton("로그아웃");
			logInBtn.addActionListener(new ActionListener() { // 로그아웃 클릭시
				public void actionPerformed(ActionEvent e) {

					setLogId(null);
					setVisible(false);
					new Main();
				}
			});
			gbAdd(logInBtn, 2, 0, 1, 2);
			JButton buysong = new JButton("곡 구매");
			gbAdd(buysong, 3, 0, 1, 2);
			buysong.addActionListener(new ActionListener() { // 나만의 앨범 클릭시
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					new Buying_DB();
				}
			});
			
			JButton mySongBtn = new JButton("내가 구매한 곡");
			mySongBtn.addActionListener(new ActionListener() { // 내가 구매한 곡 버튼 클릭시
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					new Mypage();
				}
			});
			gbAdd(mySongBtn, 4, 0, 1, 2);
			JButton myAlbumBtn = new JButton("나만의 앨범");
			myAlbumBtn.addActionListener(new ActionListener() { // 나만의 앨범 클릭시
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					new MyAlbumList();
				}
			});
			gbAdd(myAlbumBtn, 5, 0, 1, 2);
			// 실시간 차트 제목 라벨
			JLabel chart = new JLabel("실시간 차트");
			chart.setHorizontalAlignment(SwingConstants.CENTER);
			chart.setFont(new Font("굴림", Font.BOLD, 25));
			gbAdd(chart, 0, 1, 4, 1);

			// 실시간차트를 나머지 요소보다 세로길이를 길게 해줌
			gbc.weighty = 20.0;
			DBconnector db = new DBconnector(); // DAO 객체 생성
			ArrayList<SongDTO> list = db.getSongList(); // 실시간 차트를 불러오기 위한 메소드 호출
			// 메소드의 결과인 DTO의 리스트를 변수로 저장해둠s

			JPanel chartPanel = new JPanel(); // 실시간 차트를 올릴 패널
			chartPanel.setBackground(new Color(50, 205, 50));
			chartPanel.setPreferredSize(new Dimension(900, list.size() * 197)); // 패널의 크기 설정
			// 전체 곡 리스트의 크기 만큼만 패널의 크기를 설정해줌
			for (int i = 0; i < list.size(); i++) { // 실시간 차트 생성
				JLabel rankLbl1 = new JLabel((i + 1) + "위: "); // 순위 라벨
				chartPanel.add(rankLbl1);

				int songId = list.get(i).getSongid(); // 곡의 id번호를 임시로 저장해둠
				String albumCover = list.get(i).getAlbumcover(); // 앨범커버의 파일명을 임시로 저장해둠
				JLabel albumCoverLbl = new JLabel("앨범 이미지"); // 앨범커버를 넣을 라벨
				String imgPath = "albumcover\\" + albumCover;
				// 앨범커버 이미지를 원하는 크기로 불러옴
				// ImageIcon객체를 생성
				ImageIcon originIcon = new ImageIcon(imgPath);
				// ImageIcon에서 Image를 추출
				Image originImg = originIcon.getImage();
				// 추출된 Image의 크기를 조절하여 새로운 Image객체 생성
				Image changedImg = originImg.getScaledInstance(192, 192, Image.SCALE_SMOOTH);
				// 새로운 Image로 ImageIcon객체를 생성
				ImageIcon icon = new ImageIcon(changedImg);
				albumCoverLbl.setPreferredSize(new Dimension(192, 192));
				albumCoverLbl.setIcon(icon);
				chartPanel.add(albumCoverLbl);
				JButton songTitleBtn = new JButton(list.get(i).getSongtitle()); // 곡제목을 넣을 버튼
				songTitleBtn.addActionListener(new ActionListener() { // 곡 제목 클릭시
					public void actionPerformed(ActionEvent e) {
						SongDTO song = new SongDTO();
						song.setSongid(songId);
						// 곡의 정보창 객체 생성
						SongInfo si = new SongInfo();
						// 곡 id만 들어있는 songDTO를 매개로 넘겨준다.
						si.showSongInfo(song); // 곡의 정보를 보여주는 창을 띄워줌
					}
				});
				songTitleBtn.setPreferredSize(new Dimension(250, 50));
				chartPanel.add(songTitleBtn);
				JButton artistBtn = new JButton(list.get(i).getArtist()); // 가수이름 넣을 버튼
				artistBtn.addActionListener(new ActionListener() { // 가수 이름 클릭시
					public void actionPerformed(ActionEvent e) {
						String artist = artistBtn.getText();
						SongDTO song = new SongDTO();
						song.setArtist(artist);
						ChartWindow ch = new ChartWindow(); // 차트 객체 생성
						ch.myChart(song); // 해당 가수가 부른 곡을 인기순위로 띄워줌
					}
				});
				artistBtn.setPreferredSize(new Dimension(100, 50));
				chartPanel.add(artistBtn);
				JButton albumTitleBtn = new JButton(list.get(i).getAlbumtitle()); // 앨범제목 넣을 버튼
				albumTitleBtn.addActionListener(new ActionListener() { // 앨범제목 클릭시
					public void actionPerformed(ActionEvent e) {
						String albumTitle = albumTitleBtn.getText();
						AlbumInfo ai = new AlbumInfo(); // 앨범 정보창 객체 생성
						// 앨범의 정보창을 띄워줄 메소드 호출
						// 앨범제목을 매개로 넘겨준다.
						ai.showAlbumInfo(albumTitle);
					}
				});
				albumTitleBtn.setPreferredSize(new Dimension(250, 50));
				chartPanel.add(albumTitleBtn);

				JLabel songLikeLbl = new JLabel(list.get(i).getSonglike() + ""); // 해당곡의 좋아요수를 표시해줄 라벨
				songLikeLbl.setHorizontalAlignment(SwingConstants.CENTER);
				songLikeLbl.setPreferredSize(new Dimension(50, 50));
				chartPanel.add(songLikeLbl);

				JButton cartButton = new JButton();
				cartButton.setPreferredSize(new Dimension(40, 50));
				cartButton.addActionListener(new ActionListener() { // 장바구니버튼 클릭시
					public void actionPerformed(ActionEvent e) {
						SongDTO song = new SongDTO(); // 장바구니 테이블에 들어갈 데이터만 넣을 DTO
						song.setSongid(songId);
						song.setSongtitle(songTitleBtn.getText());
						song.setArtist(artistBtn.getText());
						song.setAlbumcover(albumCover);

						DBconnector db = new DBconnector();
						db.cart(song, getLogId()); // 해당 곡을 장바구니 테이블에 넣어줌
					}
				});
				imgPath = "cartIcon.jpg";
				// 앨범커버 이미지를 원하는 크기로 불러옴
				// ImageIcon객체를 생성
				originIcon = new ImageIcon(imgPath);
				// ImageIcon에서 Image를 추출
				originImg = originIcon.getImage();
				// 추출된 Image의 크기를 조절하여 새로운 Image객체 생성
				changedImg = originImg.getScaledInstance(40, 50, Image.SCALE_SMOOTH);
				// 새로운 Image로 ImageIcon객체를 생성
				icon = new ImageIcon(changedImg);
				cartButton.setIcon(icon);
				chartPanel.add(cartButton);

			}

			JScrollPane js = new JScrollPane(chartPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 차트를 넣은 패널에 스크롤을 붙여줌
			// 세로스크롤은 필요할때만 표시하고, 가로스크롤은 표시하지 않는다.
			js.setPreferredSize(new Dimension(900, list.size() * 197)); // 스크롤의 크기 설정, 차트의 크기만큼
			// 실시간차트를 넣은 패널
			gbAdd(js, 0, 3, 6, 1);
		}
		setSize(1000, 800);
		setVisible(true);
	}

	private void gbAdd(Component c, int x, int y, int w, int h) {
		gbc.gridx = x; // 그리드백 요소의 x좌표
		gbc.gridy = y; // 그리드백 요소의 y좌표
		gbc.gridwidth = w; // 그리드백 요소의 가로길이
		gbc.gridheight = h; // 그리드백 요소의 세로 길이
		gbl.setConstraints(c, gbc); // 해당요소를 그리드백 레이아웃에 띄워줌
		add(c);

	}
	
	public static String getLogId() {
		return logId;
	}

	public static void setLogId(String logId) {
		Main.logId = logId;
	}

	public static void main(String[] args) {
		new Main(); // 메인창 호출
		setLogId(logId);
	}
	


	
}