package QuizPaper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

import QuizMaker.Quiz;
import QuizMaker.Subject;

public class WrongCheck extends JFrame {
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
	private int idx;
	private JLabel lblLogout;
	private JTextArea taQuiz;
	
	private JLabel lblMyAnswer;
	private JLabel lblAnswer;
	private JLabel lblLeft;
	private JLabel lblRight;
	private JLabel lblExplanation;
	private JTextField tfNum;
	private JLabel lblGo;
	private JTextArea taExplanation;
	private int currentNum;
	private Vector<JLabel> vec;
	private JPanel pnlExplanation;
	boolean flag = true;
	private Subject subject;
	private TestResult testResult;
	private JPanel pnlWrong;
	
	private JPanel pnlS;
	private JPanel pnlTitle1;
	private JPanel pnlPro;
	private File file;
	private String subName;
	private Vector<Integer> wrongItems;
	private GradeCheck gradeCheck;
	private QuizAnswer[] quizAnswerArr;
	private Vector<Integer> myanswer;
	private Vector<String> subjects;
	private JComboBox cbSubjects;
	
	public WrongCheck(Vector<Integer> wrongItems, TestResult testResult, File file, String subName,Vector<String> subjects) {
		this.wrongItems = wrongItems;
		this.testResult = testResult;
		this.file = file;
		this.subName = subName;	
		this.subjects=subjects;
		quizAnswerArr=testResult.getQuizAnswerList();
		getTotalLoad();
	}

	public WrongCheck(Vector<Integer> wrongItems, GradeCheck gradeCheck, File file, String subName,Vector<String> subjects) {
		this.wrongItems = wrongItems;
		this.gradeCheck = gradeCheck;
		this.file = file;
		this.subName = subName;	
		this.subjects=subjects;
		quizAnswerArr=gradeCheck.getQuizAnswerList();		
		getTotalLoad();
	}


	private void init() {		
		cbSubjects = new JComboBox(subjects);
		idx=0;
		ImageIcon logout = new ImageIcon("img\\logout1.png");
		lblLogout = new JLabel(logout);
		taQuiz = new JTextArea(10, 60);
		taQuiz.setEditable(false);
		
		lblMyAnswer = new JLabel("내가 선택한 정답: "+myanswer);
		lblAnswer = new JLabel("정답 번호: ");
		ImageIcon left = new ImageIcon("img\\left.png");
		ImageIcon right = new ImageIcon("img\\right.png");
		lblLeft = new JLabel(left);
		lblRight = new JLabel(right);
		ImageIcon explanation = new ImageIcon("img\\해석 보기.png");
		lblExplanation = new JLabel(explanation);
		lblExplanation.setPreferredSize(new Dimension(105, 60));

		tfNum = new JTextField("원하시는 번호를 입력해주세요", 16);
		ImageIcon icon = new ImageIcon("img\\go.png");
		Image image = icon.getImage();
		Image img = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon go = new ImageIcon(img);
		lblGo = new JLabel(go);
		lblGo.setPreferredSize(new Dimension(50, 50));
		taExplanation = new JTextArea(5, 70);
		taExplanation.setEditable(false);
		pnlExplanation = new JPanel();
		JScrollPane scroll = new JScrollPane(taExplanation);
		pnlExplanation.add(scroll);
		vec = new Vector<JLabel>();
	}
	
	private void setDisplay() {	
		pnlPro=new JPanel(new BorderLayout());
		pnlPro.setMaximumSize(new Dimension(500,400));	
		pnlPro.setBackground(Color.WHITE);	

		JPanel pnlTop = new JPanel(new BorderLayout());
		pnlTitle1 = new JPanel(new BorderLayout());
		pnlTitle1.add(new JLabel(subName.substring(0, subName.indexOf("."))), BorderLayout.WEST);
		JPanel pnlsub=new JPanel();
		pnlsub.add(new JLabel("과목 선택: "));
		pnlsub.add(cbSubjects);
		pnlTitle1.add(pnlsub, BorderLayout.EAST);

		JPanel pnlA = new JPanel(new BorderLayout());
		JPanel pnlMyAnswer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlMyAnswer.add(lblMyAnswer);
		JPanel pnlAnswer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlAnswer.add(lblAnswer);
		pnlA.add(pnlMyAnswer, BorderLayout.NORTH);
		pnlA.add(pnlAnswer, BorderLayout.CENTER);
		pnlTop.add(pnlTitle1, BorderLayout.NORTH);
		
		JScrollPane sc=new JScrollPane(pnlPro);
		sc.getHorizontalScrollBar().setUnitIncrement(30);
		sc.getVerticalScrollBar().setUnitIncrement(30);
		sc.setPreferredSize(new Dimension(800,400));
		sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pnlTop.add(sc, BorderLayout.CENTER);
		pnlTop.add(pnlA, BorderLayout.SOUTH);
		
		JPanel pnlCenter = new JPanel(new GridLayout(0, 1));
		JPanel pnlButton = new JPanel(new BorderLayout());
		pnlButton.add(lblLeft, BorderLayout.WEST);
		pnlButton.add(lblRight, BorderLayout.EAST);
		JPanel pnlCenter2 = new JPanel(new BorderLayout());
		JPanel pnlExplanation = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlExplanation.add(lblExplanation);
		JPanel pnlGo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlGo.add(tfNum);
		pnlGo.add(lblGo);
		pnlCenter2.add(pnlExplanation, BorderLayout.WEST);
		pnlCenter2.add(pnlGo, BorderLayout.EAST);
		pnlWrong = new JPanel();
		pnlWrong.setBorder(new TitledBorder(new LineBorder(Color.BLACK), "틀린문제"));
		for (int i = 0; i < wrongItems.size(); i++) {
			JLabel lbl = new JLabel(String.valueOf(wrongItems.get(i)));
			lbl.setFont(new Font("맑은 고딕", Font.BOLD, 17));
			pnlWrong.add(lbl);
		}
		pnlCenter2.add(pnlWrong, BorderLayout.SOUTH);
		pnlCenter.add(pnlButton);
		pnlCenter.add(pnlCenter2);

		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.add(pnlTop, BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.setBorder(new EmptyBorder(10, 25, 10, 25));

		JPanel pnlLogout = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlLogout.add(lblLogout);

		add(pnlLogout, BorderLayout.NORTH);
		add(pnlMain, BorderLayout.CENTER);		
		pnlS= new JPanel();
		add(pnlS, BorderLayout.SOUTH);
	}
	
	private void addListeners() {
		MouseListener ml = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getSource() == lblLeft) {
					int num = currentNum;
					if (num == wrongItems.get(0)) {
						JOptionPane.showMessageDialog(WrongCheck.this, "첫 페이지입니다!", "information",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						if(idx<0) {
							idx=1;
						}
						num =wrongItems.get(idx-1);
						idx--;						
						quizChange(num);
					}

				} else if (me.getSource() == lblRight) {
					int num = currentNum;					
					if (num ==wrongItems.get(wrongItems.size()-1) ) {
						int result = JOptionPane.showConfirmDialog(WrongCheck.this, "마지막 페이지입니다.\n전페이지로 돌아가시겠습니까?",
								"확인창", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
								new ImageIcon("img\\log.png"));
						if (result == JOptionPane.YES_OPTION) {
							if (testResult == null) {
								gradeCheck.setVisible(true);
								dispose();
							} else {
								testResult.setVisible(true);
								dispose();
							}

						}
					} else {
						if(idx>=wrongItems.size()) {
							idx=wrongItems.size()-1;
						}
						num = wrongItems.get(idx+1);						
						idx++;						
						quizChange(num);
					}
				} else if (flag && me.getSource() == lblExplanation) {
					pnlS.add(pnlExplanation, BorderLayout.SOUTH);
					pnlS.updateUI();
					pack();
					flag = false;
				} else if (!flag && me.getSource() == lblExplanation) {
					pnlS.removeAll();
					pnlS.updateUI();
					pack();
					flag = true;
				} else if (me.getSource() == tfNum) {
					tfNum.setText("");
				} else if (me.getSource() == lblGo) {
					String number = tfNum.getText().trim();
					try {
						int num = Integer.parseInt(number);
						if (num > 0 || num < quizList.size()) {
							quizChange(num);
							for(int i=0;i<wrongItems.size();i++) {
								if(wrongItems.get(i)==num) {
									idx=i;
								}
							}														
							tfNum.setText("");
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(WrongCheck.this, "원하시는 번호를 입력해주세요.");
					} catch (ArrayIndexOutOfBoundsException e) {
						JOptionPane.showMessageDialog(WrongCheck.this, "없는 문제 번호 입니다.", "information",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					int result = JOptionPane.showConfirmDialog(WrongCheck.this, "로그아웃 하시겠습니까?", "확인창",
							JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						if (testResult == null) {
							gradeCheck.getLogin().setVisible(true);
							dispose();
						} else {
							testResult.getSaveDataAllAndLogout();
							new Login();
							TestResult.subNums = null;
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
		lblExplanation.addMouseListener(ml);
		lblLogout.addMouseListener(ml);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(WrongCheck.this, "전페이지로 돌아가시겠습니까?", "확인창",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, new ImageIcon("img\\log.png"));
				if (result == JOptionPane.YES_OPTION) {
					if (testResult == null) {
						gradeCheck.setVisible(true);
						dispose();
					} else {
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
				int i=cbSubjects.getSelectedIndex();				
				idx=0;
				load();
				pnlTitle1.removeAll();
				pnlTitle1.add(new JLabel(subName.substring(0, subName.indexOf("."))), BorderLayout.WEST);
				pnlTitle1.add(cbSubjects, BorderLayout.EAST);
				
				if(gradeCheck==null) {
					wrongItems=testResult.getWrongItems(i);				
				}else {
					wrongItems=gradeCheck.getWrongItems(i);						
				}
				pnlWrong.removeAll();
				currentNum=wrongItems.get(0);
				quizChange(currentNum);
				for (int k = 0; k < wrongItems.size(); k++) {
					JLabel lbl = new JLabel(String.valueOf(wrongItems.get(k)));
					lbl.setFont(new Font("맑은 고딕", Font.BOLD, 17));
					pnlWrong.add(lbl);
				}
				pnlTitle1.updateUI();
				pnlWrong.updateUI();
			}
		});
		
	}

	private void showFrame() {
		setTitle("오답확인");
		pack();
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
		pnlTwo.setBackground(Color.WHITE);
		String[] options = quiz.getOptions();
		byte[][] exampleImg=quiz.getExampleImg();					
		if(options!=null||options.length!=0 ) {
			for (int i = 0; i < options.length; i++) {
				JPanel pnlS=new JPanel(new BorderLayout());			
				pnlS.setBackground(Color.WHITE);
				String option = options[i];			
				JLabel lblpro5=new JLabel(i+1+". "+options[i],JLabel.LEFT);				
				pnlS.add(lblpro5,BorderLayout.SOUTH);	
				
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
				}					
			}
		pnlSS.add(pnlTwo);
		
		
		if(flag) {
			pnlPro.add(pnlSS,BorderLayout.SOUTH);
		}else {
			pnlPro.add(pnlSS,BorderLayout.CENTER);
			JLabel l= new JLabel("");
			l.setPreferredSize(new Dimension(200,200));
			pnlPro.add(l,BorderLayout.SOUTH);
		}
		pnlPro.updateUI();			
	}

	private void setAnswer(String s) {
		Quiz quiz = quizList.get(Integer.parseInt(s));
		StringBuffer buf = new StringBuffer();
		answer = quiz.getAnswer();
		for (int i = 0; i < answer.length; i++) {
			if(answer[i]!=-1) {
				buf.append(answer[i] + "번 " + "\n");
			}			
		}
		lblAnswer.setText("정답: " + buf.toString());
	}

	private void quizChange(int num) {
		if (wrongItems.contains(num)) {
			currentNum = num;
			myanswer=quizAnswerArr[currentNum-1].getAnswer();
			lblMyAnswer.setText("내가 선택한 정답: "+myanswer);
			setQuiz(String.valueOf(num - 1));
			setAnswer(String.valueOf(num - 1));
			setExplanation(String.valueOf(num - 1));
		} else {
			JOptionPane.showConfirmDialog(WrongCheck.this, "아래 틀린 문제를 확인하시고 원하시는 번호를 입력해 주세요!", "알림",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void setExplanation(String s) {
		Quiz quiz = quizList.get(Integer.parseInt(s));
		explanation = quiz.getExplanation();
		taExplanation.setText(explanation);
	}

	private void getTotalLoad() {
		currentNum = wrongItems.get(0);
		myanswer=quizAnswerArr[currentNum-1].getAnswer();
		load();
		init();
		setDisplay();
		quizChange(currentNum);
		showFrame();
		addListeners();
	}
	private void getBingImg(String s,int n) {
		JOptionPane.showMessageDialog(this,"", "IMG", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(subName+currentNum+s+n+".png"));		
	}

}
