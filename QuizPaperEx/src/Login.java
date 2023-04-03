
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.InsetsUIResource;

public class Login extends JFrame {
	private Vector<DidExam> didExamInfo;
	private String didExamPath;
	private Vector<Human> vec = new Vector<Human>();
	private JLabel lblTitle;
	private JLabel lblId;
	private JLabel lblPw;
	private JTextField tfId;
	private JPasswordField pfPw;

	private JButton btnJoin;
	private JButton btnIdPw;
	private JButton btnLogin;
	private DidExam didExamName;
	private Dimension lblSize = new Dimension(65, 20);
	private Dimension btnSize = new Dimension(100, 35);
	private JButton btnDataLoad;
	private JButton btnDataSave;

	public Login() {
		didExamName = new DidExam(null, null, new Vector<DidSubject>(), null, null, null);
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	private void init() {
		load();
		lblTitle = new JLabel("시험연습장", JLabel.CENTER);
		lblTitle.setForeground(Color.GRAY);
		lblTitle.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 40));
		lblTitle.setPreferredSize(new Dimension(100, 80));
		lblId = new JLabel("ID", JLabel.LEFT);
		lblId.setFont(new Font("맑은고딕", Font.BOLD, 20));
		lblId.setPreferredSize(lblSize);
		lblPw = new JLabel("PW", JLabel.LEFT);
		lblPw.setFont(new Font("맑은고딕", Font.BOLD, 20));
		lblPw.setPreferredSize(lblSize);

		tfId = new JTextField(20);
		tfId.setPreferredSize(new Dimension(80, 25));
		pfPw = new JPasswordField(20);
		pfPw.setPreferredSize(new Dimension(80, 25));

		btnJoin = new JButton(new ImageIcon("img\\회원가입1.png"));
		btnJoin.setBorderPainted(false);
		btnJoin.setContentAreaFilled(false);
		btnJoin.setFocusPainted(false);
		btnJoin.setPreferredSize(new Dimension(20, 10));

		btnJoin.setMargin(new InsetsUIResource(0, 0, 0, 0));

		btnIdPw = new JButton(new ImageIcon("img\\idpw3.png"));
		btnIdPw.setBorderPainted(false);
		btnIdPw.setContentAreaFilled(false);
		btnIdPw.setFocusPainted(false);
		btnIdPw.setPreferredSize(new Dimension(250, 40));

		btnLogin = new JButton(new ImageIcon("img\\login2.png"));
		btnLogin.setBorderPainted(false);
		btnLogin.setContentAreaFilled(false);
		btnLogin.setFocusPainted(false);
		btnLogin.setPreferredSize(btnSize);

		btnDataLoad = new JButton("데이터 불러오기");
		btnDataLoad.setFocusable(false);

		btnDataSave = new JButton("데이터 가져가기");
		btnDataSave.setFocusable(false);

	}

	private void setDisplay() {
		JPanel pnlMain = new JPanel(new GridLayout(0, 1));
		pnlMain.setBackground(Color.WHITE);
		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.setBackground(Color.WHITE);
		ImageIcon imgTitle = new ImageIcon("img\\biglog2.png");
		JLabel lblImgTitle = new JLabel(imgTitle);
		lblImgTitle.setOpaque(true);
		lblImgTitle.setBackground(Color.WHITE);
		pnlNorth.add(lblImgTitle, BorderLayout.CENTER);

		JPanel pnlCenter = new JPanel(new GridLayout(0, 1));
		JPanel pnltemp = new JPanel();

		pnltemp.setBackground(Color.WHITE);
		pnlCenter.add(pnltemp);
		pnlCenter.setBackground(Color.WHITE);
		JPanel pnlId = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlId.setBackground(Color.WHITE);
		pnlId.setPreferredSize(new Dimension(100, 10));
		pnlId.add(lblId);
		pnlId.add(tfId);
		JPanel pnlPw = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlPw.setBackground(Color.WHITE);
		pnlPw.setPreferredSize(new Dimension(100, 10));
		pnlPw.add(lblPw);
		pnlPw.add(pfPw);
		pnlCenter.add(pnlId);
		pnlCenter.add(pnlPw);

		JPanel pnlSouth = new JPanel(new BorderLayout());
		JPanel pnlSouth1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlSouth1.setBackground(Color.WHITE);
		JPanel pnlBtn = new JPanel(new GridLayout(0, 3, 30, 0));
		pnlBtn.setPreferredSize(new Dimension(415, 50));
		pnlBtn.setBackground(Color.WHITE);
		pnlBtn.add(btnJoin);
		pnlBtn.add(btnIdPw);
		pnlBtn.add(btnLogin);
		pnlSouth1.add(pnlBtn);
		JPanel pnlSouth2 = new JPanel(new FlowLayout(FlowLayout.CENTER,117,20));
		pnlSouth2.setBackground(Color.WHITE);
		btnDataLoad.setBackground(new Color(234, 234, 234));
		btnDataLoad.setPreferredSize(new Dimension(125, 28));
		btnDataSave.setBackground(new Color(234, 234, 234));
		btnDataSave.setPreferredSize(new Dimension(125, 28));
		pnlSouth2.add(btnDataLoad);
		pnlSouth2.add(btnDataSave);
		pnlSouth.add(pnlSouth1, BorderLayout.NORTH);
		pnlSouth.add(pnlSouth2, BorderLayout.CENTER);

		pnlMain.add(pnlNorth);
		pnlMain.add(pnlCenter);
		pnlMain.add(pnlSouth);

		add(pnlMain);
	}

	private void addListeners() {
		MouseListener ml = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getSource() == btnJoin) {
					tfId.setText("");
					pfPw.setText("");
					new Join(Login.this);
					dispose();
				} else if (e.getSource() == btnIdPw) {
					tfId.setText("");
					pfPw.setText("");
					new IdPwQuestion(Login.this);
					dispose();
				} else {

					if ((tfId.getText().trim() == null || tfId.getText().trim().equals(""))
							|| (String.valueOf(pfPw.getPassword()).trim() == null
									|| String.valueOf(pfPw.getPassword()).trim().equals(""))) {
						JOptionPane.showMessageDialog(Login.this, "ID나 PW가 비어 있습니다. 확인하시길 바랍니다!");
					} else {
						if (login_check()) {
							JOptionPane.showMessageDialog(Login.this, "login 성공!");
							loadDidExam();
							new MyPage(Login.this);
							tfId.setText("");
							pfPw.setText("");
							save();
							dispose();

						} else {
							JOptionPane.showMessageDialog(Login.this, "ID/PW가 틀렸으니 확인하시길 바랍니다!");
						}
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (e.getSource() == btnJoin || e.getSource() == btnIdPw || e.getSource() == btnLogin) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (e.getSource() == btnJoin || e.getSource() == btnIdPw || e.getSource() == btnLogin) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

				}

			}
		};
		btnJoin.addMouseListener(ml);
		btnIdPw.addMouseListener(ml);
		btnLogin.addMouseListener(ml);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				closeWindow();
			}
		});

		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnDataSave) {	
					JOptionPane.showMessageDialog(Login.this,"저장 경로를 지정해 주세요!");					
					takeoutData("tp");					
					
				} else if (e.getSource() == btnDataLoad) {
					getFrom();
				}

			}
		};

		btnDataLoad.addActionListener(al);
		btnDataSave.addActionListener(al);

	}

	private void showFrame() {
		setTitle("로그인");
		setSize(500, 475);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);

	}

	public boolean login_check() {
		boolean flag = false;
		for (Human h : vec) {
			if (h.getId().equals(tfId.getText()) && h.getPw().equals(new String(pfPw.getPassword()))) {
				didExamPath = h.getId();
				flag = true;
			}
		}
		return flag;
	}

	private void closeWindow() {
		int choice = JOptionPane.showConfirmDialog(this, "시험연습장을 종료 하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (choice == JOptionPane.OK_OPTION) {
			save();
			System.exit(0);
		}
	}

	public void getSave() {
		save();
	}

	private void load() {
		FileInputStream fis = null;
		DataInputStream dis = null;

		try {
			fis = new FileInputStream("data.txt");
			dis = new DataInputStream(fis);

			while (dis.available() != 0) {
				Human h = new Human(dis.readUTF(), dis.readUTF(), dis.readUTF(), dis.readUTF(), dis.readUTF(),
						dis.readUTF());
				vec.add(h);
			}

		} catch (FileNotFoundException e) {
			File file = new File("data.txt");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (dis != null) {
				closeAll(dis, fis);
			}

		}
	}

	private void save() {
		FileOutputStream fos = null;
		DataOutputStream dos = null;

		try {
			fos = new FileOutputStream("data.txt");
			dos = new DataOutputStream(fos);

			for (Human h : vec) {
				dos.writeUTF(h.getName());
				dos.writeUTF(h.getTel());
				dos.writeUTF(h.getId());
				dos.writeUTF(h.getPw());
				dos.writeUTF(h.getQuestion());
				dos.writeUTF(h.getAnswer());

			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			closeAll(dos, fos);
		}
	}

	private void closeAll(Closeable... c) {
		for (Closeable temp : c) {
			try {
				temp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setVec(Vector<Human> vec) {
		this.vec = vec;
	}

	public Vector getVec() {
		return vec;
	}

	public void saveDidExam() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream("tp\\"+didExamPath + ".txt");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(didExamInfo);
			oos.flush();
			oos.reset();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			closeAll(oos, fos);
		}
	}

	private void loadDidExam() {
		didExamInfo = new Vector<DidExam>();
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		boolean flag = true;
		try {
			fis = new FileInputStream("tp\\"+didExamPath + ".txt");
			ois = new ObjectInputStream(fis);

			didExamInfo = (Vector<DidExam>) ois.readObject();

		} catch (EOFException eof) {
			flag = false;
		} catch (FileNotFoundException e) {
			File file = new File("tp\\"+didExamPath + ".txt");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} finally {
			if (ois != null) {
				closeAll(ois, fis);
			}

		}
	}

	public Vector<DidExam> getdidExamInfo() {
		return didExamInfo;
	}

	public DidExam getDidExamName() {
		return didExamName;
	}

	public void setDidExamName(DidExam didExamName) {
		this.didExamName = didExamName;
	}

	public void savedidExamInfo(DidExam didExamName) {
		didExamInfo.add(didExamName);
	}

	public String getIdName() {
		return didExamPath;
	}

	private void takeoutData(String name) {
		JFileChooser ch = new JFileChooser("C:\\Users\\GGG\\Desktop");
		ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int num = ch.showSaveDialog(null);
		File savepath = ch.getSelectedFile();
		if (num == JFileChooser.APPROVE_OPTION) {
			String filesavename=JOptionPane.showInputDialog("데이터를 가져갈 폴더 이름을 정해주세요:");	
			File f = new File(savepath + "\\" +filesavename);
			if (!f.exists()) {
				f.mkdir();
			}
			bigPath = f;
			String[] strarr = { name + ".txt", "data.txt", "topLists.txt" };
			for (String s : strarr) {
				if (s.equals("data.txt")) {
					saveData(f);					
				} else {
					saveObj(f, s);
				}

			}
			saveDir(new File(savepath + "\\" +filesavename), name);
			JOptionPane.showMessageDialog(Login.this, "저장완료됩니다.\n불어올 때는 지정한 파일을 선택하세요!.");
		}

	}

	private void saveObj(File fp, String sn) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		File ft = new File(sn);
		if (ft.exists()) {
			try {
				fos = new FileOutputStream(fp + "\\" + sn);
				oos = new ObjectOutputStream(fos);

				fis = new FileInputStream(sn);				
				ois = new ObjectInputStream(fis);

				Object obj = null;

				while ((obj = ois.readObject()) != null) {
					oos.writeObject(obj);
				}

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

			} catch (ClassNotFoundException e) {

			} finally {
				IOUtil.closeAll(ois, fis, oos, fos);
			}
		}

	}

	private void saveData(File fp) {
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		FileInputStream fis = null;
		DataInputStream dis = null;
		File ft = new File("data.txt");
		if (ft.exists()) {
			try {
				fos = new FileOutputStream(fp + "\\data.txt"); 
				dos = new DataOutputStream(fos);

				fis = new FileInputStream("data.txt");
				dis = new DataInputStream(fis);

				while (dis.available() != 0) {
					dos.writeUTF(dis.readUTF());

				}

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtil.closeAll(dis, fis, dos, fos);
			}
		}

	}

	private void saveDir(File fp, String s) {
		getFileName(fp.getPath(), new File(s));

	}

	private File bigPath = null;
	private void getFileName(String s, File path) {
		if (!path.isFile()) {
			File fv = new File(s + "\\" + path);
			fv.mkdir();
			File[] files = path.listFiles();
			if (files != null) {
				for (File f : files) {
					getFileName(s, f);
				}
			}

		} else {
			String name = path.getName();
			saveObj(bigPath, path.getPath());
		}
	}
	
	private int count=0;
	private File fileTemp=new File("tp");
	private void getFiles(File path) {		
		if (!path.isFile()) {			
			if(count>=1) {
				if(count!=1) {
					if(fileTemp.getPath().length()>7) {
						fileTemp=new File("tp");						
					}					
					File fv = new File(fileTemp+"\\"+path.getName());
					fileTemp=fv;				
					if(!fileTemp.exists()) {
						fv.mkdir();
					}
					
				}else {
					File fv = new File(path.getName());
					fv.mkdir();
				}
				
			}			
			count++;
			File[] files = path.listFiles();
			if(files!=null) {
				for (File f : files) {
					getFiles(f);
				}	
			}
								
		} else{			
			String s = path.getName();			
			if (s.contains("data")) {				
				FileOutputStream fos = null;
				DataOutputStream dos = null;

				FileInputStream fis = null;
				DataInputStream dis = null;
				try {
					fos = new FileOutputStream("data.txt");
					dos = new DataOutputStream(fos);

					fis = new FileInputStream(path);
					dis = new DataInputStream(fis);
					
					while (dis.available() != 0) {
						Human h = new Human(dis.readUTF(), dis.readUTF(), dis.readUTF(), dis.readUTF(), dis.readUTF(),
								dis.readUTF());
						vec.add(h);
					}									
					for (Human h : vec) {
						dos.writeUTF(h.getName());
						dos.writeUTF(h.getTel());
						dos.writeUTF(h.getId());
						dos.writeUTF(h.getPw());
						dos.writeUTF(h.getQuestion());
						dos.writeUTF(h.getAnswer());
					}

				} catch (FileNotFoundException e) {

					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					IOUtil.closeAll(dis, fis, dos, fos);
				}

			} else {

				if (s.contains(".dat")) {
					try (FileInputStream fis = new FileInputStream(path);
							ObjectInputStream ois = new ObjectInputStream(fis);) {
						Subject subject = (Subject) (ois.readObject());
						String seletedItem = subject.getTest();
						String subjectName = subject.getSubjectName();
						saveNew(seletedItem, subjectName, path);
						
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}else {
					FileOutputStream fos = null;
					ObjectOutputStream oos = null;
					FileInputStream fis = null;
					ObjectInputStream ois = null;

					try {
						fos = new FileOutputStream("tp\\"+s);
						oos = new ObjectOutputStream(fos);

						fis = new FileInputStream(path);
						ois = new ObjectInputStream(fis);

						Object obj = null;

						while ((obj = ois.readObject()) != null) {
							oos.writeObject(obj);
						}
						
					} catch (FileNotFoundException e) {

						e.printStackTrace();
					} catch (IOException e) {

					} catch (ClassNotFoundException e) {

					} finally {
						IOUtil.closeAll(ois, fis, oos, fos);
					}
				}
				
			}
			
		}
	}

	private void getFrom() {
		JFileChooser ch = new JFileChooser("C:\\Users\\GGG\\Desktop");
		ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int num = ch.showSaveDialog(null);
		File savepath = ch.getSelectedFile();
		if (num == JFileChooser.APPROVE_OPTION) {
			getFiles(savepath);
			JOptionPane.showMessageDialog(Login.this, "데이터 불러오기 완료. 프로그램을 다시 실행 해주세요");			
		}

	}

	private void saveNew( String seletedItem, String subjectName, File savepath) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			fos = new FileOutputStream(fileTemp+"\\"  + subjectName + ".dat");
			oos = new ObjectOutputStream(fos);

			fis = new FileInputStream(savepath);
			ois = new ObjectInputStream(fis);

			Object obj = null;

			while ((obj = ois.readObject()) != null) {
				oos.writeObject(obj);
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

		} catch (ClassNotFoundException e) {

		} finally {
			IOUtil.closeAll(ois, fis, oos, fos);
		}

	}

	public static void main(String[] args) {
		new Login();
		
	}
}
