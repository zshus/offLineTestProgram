

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


public class QuizSolve extends JFrame {
	private Vector<Quiz> quizList;
	private String quizTitle;
	private double score;
	private String passage;
	private String explanation;
	private String[] options;
	private byte[][] fpImg;
	private String fpImgInfo;
	private byte[][] exampleImg;
	private int[] answer;

	private JLabel lblLogout;
	private JTextArea taQuiz;
	private JLabel lblAnswer;
	private JLabel lblLeft;
	private JLabel lblRight;
	private JTextField tfNum;
	private JLabel lblGo;
	private JTextArea taExplanation;
	private int currentNum = 1;
	private File file;
	private String subName;
	private Subject subject;
	private TestResult testResult;
	
	private JPanel pnlTitle1;
	private JPanel pnlPro;
	private GradeCheck gradeCheck;
	private JComboBox cbSubjects;
	private Vector<String> subjects;

	public QuizSolve(TestResult testResult, File file, String subName,Vector<String> subjects) {
		this.testResult = testResult;
		this.file = file;
		this.subName = subName;
		this.subjects=subjects;
		getAllData();
	}
	public QuizSolve(GradeCheck gradeCheck, File file, String subName,Vector<String> subjects) {
		this.gradeCheck = gradeCheck;
		this.file = file;
		this.subName = subName;
		this.subjects=subjects;
		getAllData();
	}

	private void init() {	
		cbSubjects = new JComboBox(subjects);	
		ImageIcon logout = new ImageIcon("img\\logout1.png");
		lblLogout = new JLabel(logout);
		taQuiz = new JTextArea(10, 50);
		taQuiz.setEditable(false);

		lblAnswer = new JLabel("");
		ImageIcon left = new ImageIcon("img\\left.png");
		ImageIcon right = new ImageIcon("img\\right.png");
		lblLeft = new JLabel(left);
		lblRight = new JLabel(right);
		tfNum = new JTextField("원하시는 번호를 입력해주세요", 16);
		ImageIcon icon = new ImageIcon("img\\go.png");
		Image image = icon.getImage();
		Image img = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon go = new ImageIcon(img);
		lblGo = new JLabel(go);
		lblGo.setPreferredSize(new Dimension(50, 50));
		taExplanation = new JTextArea(10, 60);
		taExplanation.setEditable(false);
	}
	
	private void setDisplay() {
		pnlPro=new JPanel(new BorderLayout());
		pnlPro.setMaximumSize(new Dimension(500,600));
		pnlPro.setBackground(Color.WHITE);	
	
		JPanel pnlTop = new JPanel(new BorderLayout());
		pnlTitle1 = new JPanel(new BorderLayout());
		pnlTitle1.add(new JLabel(subName.substring(0, subName.indexOf("."))), BorderLayout.WEST);	
		
		JPanel pnlsub=new JPanel();
		pnlsub.add(new JLabel("과목 선택: "));
		pnlsub.add(cbSubjects);
		pnlTitle1.add(pnlsub, BorderLayout.EAST);
		JPanel pnlAnswer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlAnswer.add(lblAnswer);
		pnlTop.add(pnlTitle1, BorderLayout.NORTH);
		
		JScrollPane sc=new JScrollPane(pnlPro);
		sc.getHorizontalScrollBar().setUnitIncrement(30);
		sc.getVerticalScrollBar().setUnitIncrement(30);
		sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sc.setPreferredSize(new Dimension(800,400));
		pnlTop.add(sc, BorderLayout.CENTER);

		pnlTop.add(pnlAnswer, BorderLayout.SOUTH);
		JPanel pnlCenter = new JPanel(new GridLayout(0, 1));
		JPanel pnlButton = new JPanel(new BorderLayout());
		pnlButton.add(lblLeft, BorderLayout.WEST);
		pnlButton.add(lblRight, BorderLayout.EAST);
		JPanel pnlGo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlGo.add(tfNum);
		pnlGo.add(lblGo);
		pnlCenter.add(pnlButton);
		pnlCenter.add(pnlGo);
		JScrollPane answer = new JScrollPane(taExplanation);
		
		JPanel pnlBottom = new JPanel(new BorderLayout());
		JPanel pnlTitle2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTitle2.add(new JLabel("                             "));
		pnlTitle2.add(new JLabel("해설"));
		JPanel pnlExplanation = new JPanel();
		pnlExplanation.add(answer);
		pnlBottom.add(pnlTitle2, BorderLayout.NORTH);
		pnlBottom.add(pnlExplanation, BorderLayout.CENTER);

		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.add(pnlTop, BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlBottom, BorderLayout.SOUTH);
		pnlMain.setBorder(new EmptyBorder(10, 25, 10, 25));

		JPanel pnlLogout = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlLogout.add(lblLogout);
		add(pnlLogout, BorderLayout.NORTH);
		add(pnlMain, BorderLayout.CENTER);		

	}

	private void addListeners() {

		MouseListener ml = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getSource() == lblLeft) {
					int num = currentNum;
					if (num == 1) {
						JOptionPane.showConfirmDialog(QuizSolve.this, "현재 페이지가 첫 문제 입니다.", "information",
								JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
								new ImageIcon("img\\log.png"));
					} else {
						num = num - 1;
						quizChange(num);
					}

				} else if (me.getSource() == lblRight) {
					int num = currentNum;
					if (num == quizList.size()) {
						int result = JOptionPane.showConfirmDialog(QuizSolve.this, "마지막 문제 입니다.\n전 페이지로 돌아가시겠습니까?",
								"information", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
								new ImageIcon("img\\log.png"));
						if (result == JOptionPane.YES_OPTION) {
							if(testResult==null) {
								gradeCheck.setVisible(true);
								dispose();
							}else {
								testResult.setVisible(true);
								dispose();
							}
							
						}
					} else {
						num = num + 1;
						quizChange(num);
					}

				} else if (me.getSource() == tfNum) {
					tfNum.setText("");
				} else if (me.getSource() == lblGo) {
					String number = tfNum.getText().trim();
					try {
						int num = Integer.parseInt(number);
						number = "원하시는 번호를 입력해주세요";
						number = "";
						if (num > 0 || num < quizList.size()) {
							quizChange(num);
							tfNum.setText("");
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(QuizSolve.this, "원하시는 번호를 입력해주세요.", "information",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (ArrayIndexOutOfBoundsException e) {
						JOptionPane.showMessageDialog(QuizSolve.this, "없는 문제 번호 입니다.", "information",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					int result = JOptionPane.showConfirmDialog(QuizSolve.this, "로그아웃 하시겠습니까?", "information",
							JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						if(testResult==null) {
							gradeCheck.getSaveDataAllAndLogout();
							dispose();
						}else {
							testResult.getSaveDataAllAndLogout();
							TestResult.subNums = null;
							new Login();
							dispose();
						}
						
					}
				}
			}
		};
		lblLeft.addMouseListener(ml);
		lblRight.addMouseListener(ml);
		tfNum.addMouseListener(ml);
		lblGo.addMouseListener(ml);
		lblLogout.addMouseListener(ml);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(QuizSolve.this, "전 페이지로 돌아가시겠습니까?", "알림",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon("img\\log.png"));
				if (result == JOptionPane.YES_OPTION) {
					if(testResult==null) {
						gradeCheck.setVisible(true);
						dispose();
					}else {
						testResult.setVisible(true);
						dispose();
					}
				}
			}
		});
		
		cbSubjects.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				subName=cbSubjects.getSelectedItem()+".dat";
				load();
				pnlTitle1.removeAll();
				pnlTitle1.add(new JLabel(subName.substring(0, subName.indexOf("."))), BorderLayout.WEST);
				pnlTitle1.add(cbSubjects, BorderLayout.EAST);
				currentNum=1;
				quizChange(currentNum);
				pnlTitle1.updateUI();
			}
		});

	}

	private void showFrame() {
		setTitle("문제풀이");
		setSize(900, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	private void load() {
		try (FileInputStream fis = new FileInputStream(file + "\\" + subName);
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			subject = (Subject) (ois.readObject());
			quizList = subject.getQuizs();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void setQuiz(String s) {
		pnlPro.removeAll();
		Quiz quiz = quizList.get(Integer.parseInt(s));		
		String quizTitle = quiz.getQuizTitle();
		String passage = quiz.getPassage();	
		byte[][] fpImg=quiz.getFpImg();
		String[] fpImgInfo=quiz.getFpImgInfo();
		JTextArea ta= new JTextArea(Integer.parseInt(s)+1+". "+quizTitle+"\n"+passage);
		ta.setEditable(false);
		ta.setLineWrap(true);
		pnlPro.add(ta,BorderLayout.NORTH);	
		JPanel pnlCC=new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlCC.setBackground(Color.WHITE);
		
		boolean flag=false;
		
		if(fpImg!=null||fpImg.length!=0) {
			for(int i=0; i<fpImg.length;i++) {	
				JPanel pnlC=new JPanel(new BorderLayout());	
				pnlC.setBackground(Color.WHITE);
				if(fpImg[i]!=null&&fpImg[i].length!=0) {
					JLabel lblpro3=getImgLabel("question",i, fpImg[i]);					
					lblpro3.setToolTipText("이미지를 클릭하시면 확대할 수 있습니다!");
					int n=i;
					lblpro3.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							getBingImg("question",n);
						}
					});
					pnlC.add(lblpro3,BorderLayout.CENTER);
					flag=true;
				}				
				
				if(fpImgInfo!=null|| fpImgInfo.length!=0) {
					if(fpImgInfo[i]!=null) {
						JTextArea ta4= new JTextArea(fpImgInfo[i]);
						ta4.setEditable(false);
						ta4.setLineWrap(true);
						pnlC.add(ta4,BorderLayout.SOUTH);
					}					
				}
				
				pnlCC.add(pnlC);
				
			}	
			pnlPro.add(pnlCC,BorderLayout.CENTER);
		}
		pnlCC.add(new JLabel("\n\n\n"));
		
		JPanel pnlSS=new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlSS.setBackground(Color.WHITE);
		
		JPanel pnlTwo=new JPanel(new GridLayout(20,1));
		pnlTwo.setBackground(Color.white);
		String[] options = quiz.getOptions();
		byte[][] exampleImg=quiz.getExampleImg();					
		if(options!=null||options.length!=0 ) {
			for (int i = 0; i < options.length; i++) {
				JPanel pnlS=new JPanel(new BorderLayout());			
				pnlS.setBackground(Color.WHITE);
				String option = options[i];			
				JLabel lblpro5=new JLabel(i+1+". "+options[i],JLabel.LEFT);				
				pnlS.add(lblpro5,BorderLayout.NORTH);	
				
				if(exampleImg!=null||exampleImg.length!=0) {	
						if(exampleImg[i]!=null&&exampleImg[i].length!=0) {							
							JLabel lblpro6=getImgLabel("answer",i, exampleImg[i]);
							lblpro6.setToolTipText("이미지를 클릭하시면 확대할 수 있습니다!");
							int n=i;
							lblpro6.addMouseListener(new MouseAdapter() {
								@Override
								public void mousePressed(MouseEvent e) {
									getBingImg("answer",n);
								}
							});
							pnlS.add(lblpro6,BorderLayout.CENTER);
						}						
					}
				pnlTwo.add(pnlS);
				JLabel temp=new JLabel();
				temp.setOpaque(true);
				temp.setBackground(Color.WHITE);
				pnlTwo.add(temp);
				}					
			}
		pnlSS.add(pnlTwo);
		
		
		if(flag) {
			pnlPro.add(pnlSS,BorderLayout.SOUTH);
		}else {
			pnlPro.add(pnlSS,BorderLayout.CENTER);
			JLabel l= new JLabel("");
			l.setPreferredSize(new Dimension(200,200));
			pnlPro.setMaximumSize(new Dimension(500,600));
			pnlPro.add(l,BorderLayout.SOUTH);
		}
		
		pnlPro.updateUI();	
		
	}
	
	private JLabel getImgLabel(String s,int n, byte[] img) {
		JLabel lbl= null;		
		try(FileOutputStream fos= new FileOutputStream(subName+currentNum+s+n+".png");)
		{	
			fos.write(img);	
			fos.flush();
			fos.close();
			ImageIcon ic= new ImageIcon(subName+currentNum+s+n+".png");
			Image imG=Toolkit.getDefaultToolkit().
					getImage(subName+currentNum+s+n+".png").getScaledInstance((int)(ic.getIconWidth()*0.8), (int)(ic.getIconHeight()*0.8), Image.SCALE_SMOOTH);			
			lbl=new JLabel(new ImageIcon(imG),JLabel.LEFT);			
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			return lbl;
		}
	}
	
	private void getBingImg(String s,int n) {
		JOptionPane.showMessageDialog(this,"", "IMG", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(subName+currentNum+s+n+".png"));		
	}

	private void setAnswer(String s) {
		Quiz quiz = quizList.get(Integer.parseInt(s));
		StringBuffer buf = new StringBuffer();
		answer = quiz.getAnswer();
		for (int i = 0; i < answer.length; i++) {
			buf.append(answer[i] + "번  ");
		}
		lblAnswer.setText("정답: " + buf.toString());
	}

	private void setExplanation(String s) {
		Quiz quiz = quizList.get(Integer.parseInt(s));
		explanation = quiz.getExplanation();
		taExplanation.setText(explanation);
		
	}
	
	private void getAllData() {
		load();
		init();
		setDisplay();
		addListeners();
		quizChange(currentNum);
		showFrame();
	}

	private void quizChange(int num) {
		currentNum = num;
		setQuiz(String.valueOf(num - 1));
		setAnswer(String.valueOf(num - 1));
		setExplanation(String.valueOf(num - 1));
	}

}
