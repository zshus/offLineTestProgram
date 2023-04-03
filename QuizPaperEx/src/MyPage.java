

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class MyPage extends JFrame {
	
	private JButton btngradeCheck;
	private JButton btntest;
	private JComboBox<String> boxList;
	private JLabel lbltext;
	private Vector<String> vecList;
	private int count=0;
	private Login login;
	private String seletedItem;
	private JButton btnNewTest;
	private File file;
	private String subjectName;
	
	private JLabel lbltext1;
	private JComboBox<String> boxList2;
	private JLabel lbltext2;
	private JComboBox<String> boxList3;
	private JLabel lbltext3;
	
	public MyPage() {
		
	}
	
	public MyPage(Login login) {
		this.login=login;
		init();
		setDisplay();
		addListener();
		showFrame();
	}
	
	private void init() {
		vecList=new Vector<>();
		vecList.add("-기존시험 선택-");
		getFileName(new File("tp\\"+login.getIdName()));		
		seletedItem=vecList.get(0);
		btngradeCheck=new JButton("성적 조회");
		btngradeCheck.setFocusPainted(false);
		btntest=new JButton("재시험보기");
		btntest.setFocusPainted(false);
		
		lbltext=new JLabel("기존시험을 선택하세요:",JLabel.CENTER);
		lbltext.setFont(new Font("맑은 고딕",Font.BOLD ,15));
		btnNewTest=new JButton("새시험보기");
		btnNewTest.setFocusPainted(false);
		
		lbltext1=new JLabel("시험을 선택하세요:",JLabel.CENTER);
		lbltext1.setFont(new Font("맑은 고딕",Font.BOLD ,15));
		
		boxList2=new JComboBox<String>(vecList);
		boxList2.setPreferredSize(new Dimension(285,22));
		
		lbltext2=new JLabel("기존시험을 선택하세요:",JLabel.CENTER);
		lbltext2.setFont(new Font("맑은 고딕",Font.BOLD ,15));
		
		boxList3=new JComboBox<String>(vecList);
		boxList3.setPreferredSize(new Dimension(285,22));
		
		lbltext3=new JLabel("기존시험을 선택하세요:",JLabel.CENTER);
		lbltext3.setFont(new Font("맑은 고딕",Font.BOLD ,15));

		
	}
	private void setDisplay() {
		JPanel pnlAll=new JPanel(new BorderLayout());
		pnlAll.setBackground(Color.white);
		JPanel pnlCenter=new JPanel(new GridLayout(0,1));
		pnlCenter.setBackground(Color.white);
		
		JPanel pnlNew = new JPanel(new BorderLayout()); 
		pnlNew.setBackground(Color.white);
		pnlNew.add(lbltext1,BorderLayout.NORTH);
		JPanel pnlBtn = new JPanel();
		pnlBtn.add(btnNewTest);
		pnlBtn.setBackground(Color.white);
		pnlNew.add(pnlBtn,BorderLayout.CENTER);	
		pnlNew.setBorder(new LineBorder(Color.LIGHT_GRAY));
		pnlCenter.add(pnlNew);
		
		JPanel pnlRe = pnl(lbltext2,boxList2,btntest);
		pnlCenter.add(pnlRe);
		
		JPanel pnlCheck = pnl(lbltext3,boxList3,btngradeCheck);
		pnlCenter.add(pnlCheck);
		
		
		pnlAll.add(pnlCenter,BorderLayout.CENTER);
		
		pnlAll.setBorder(new EmptyBorder(10, 50, 15, 50));
		add(pnlAll,BorderLayout.CENTER);

		
		
	}
	private void addListener() {
		ActionListener al=new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==boxList2) {
					seletedItem=(String)boxList2.getSelectedItem();
					return;
				}
				if(e.getSource()==boxList3) {
					seletedItem=(String)boxList3.getSelectedItem();
					return;
				}
				if(e.getSource()==btnNewTest) {
					getFolder();					
					if(flagA) {
						new SelectSubject(seletedItem,MyPage.this,login);
						dispose();						
					}
					return;
					
				}
				if(seletedItem.equals(vecList.get(0))) {
					JOptionPane.showMessageDialog(MyPage.this, "해당 시험을 선택하세요!");
					return;
				}
				if(e.getSource()==btntest) {
					new SelectSubject(seletedItem,MyPage.this,login);
					dispose();
				}else if(e.getSource()==btngradeCheck) {
					int count=0;
					for(DidExam exam: login.getdidExamInfo()) {
						if(exam.getTestName().equals(seletedItem)) {
							count=1;
							break;
						}
					}
					if(login.getdidExamInfo()!=null&&login.getdidExamInfo().size()!=0&&count!=0) {						
						new GradeCheck(new File("tp\\"+login.getIdName()+"\\"+seletedItem), login);
						dispose();
												
					}else {
						JOptionPane.showMessageDialog(MyPage.this, "저장된 성적정보가 없습니다.");
					}
					
				}
			
				
			}
		};
		
		
		boxList2.addActionListener(al);
		boxList3.addActionListener(al);
		
		btngradeCheck.addActionListener(al);
		btntest.addActionListener(al);
		btnNewTest.addActionListener(al);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				int choice = JOptionPane.showConfirmDialog(MyPage.this, "시험연습장을 종료 하시겠습니까?", "종료", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (choice == JOptionPane.OK_OPTION) {
					login.getSave();
					login.saveDidExam();
					System.exit(0);
				}
			}
		});
		
	}
	private void showFrame() {
		setTitle("Welcome");			
		pack();
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void getFileName(File path) {
		if(!path.isFile()) {
			if(count==0) {
				count++;
				File[] files = path.listFiles();
				if(files!=null) {
					for (File f : files) {
						getFileName(f);
					}
				}
				
			}else {
				vecList.add(path.getName());
			}
			
		}
	}
	boolean flagA=false;
	private void getFolder() {
		JFileChooser ch = new JFileChooser("C:\\Users\\GGG\\Desktop");	
		int result = ch.showOpenDialog(null);
		file = ch.getSelectedFile();
		if(result == JFileChooser.APPROVE_OPTION) {
				if(file!=null) {
					flagA=true;
				}
				try (FileInputStream fis = new FileInputStream(file);
						ObjectInputStream ois = new ObjectInputStream(fis);) {	
						
						Subject subject = (Subject) (ois.readObject());						
						seletedItem=subject.getTest();
						subjectName=subject.getSubjectName();
						saveNewTest();


					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
			
	
		}			
	}
	
	private void saveNewTest() {
		
		File f = new File("tp\\"+login.getIdName()+"\\"+seletedItem);				
		if (!f.exists()) {
			f.mkdirs();
		}
		
		FileOutputStream fos=null;
		ObjectOutputStream oos=null;
		
		FileInputStream fis=null;
		ObjectInputStream ois=null;
	
		try {
			fos=new FileOutputStream("tp\\"+login.getIdName()+"\\"+seletedItem+"\\"+subjectName+".dat");
			oos=new ObjectOutputStream(fos);
			
			fis=new FileInputStream(file);
			ois=new ObjectInputStream(fis);

			Object obj=null;
			
			while((obj=ois.readObject())!=null) {
					oos.writeObject(obj);
			}	
			 
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}catch (IOException e) {		

		} catch (ClassNotFoundException e) {

		}finally {
			IOUtil.closeAll(ois,fis,oos,fos);
		}	
	}
	
	
	private JPanel pnl(JLabel lbl, JComboBox<String> cmb,JButton btn) {
		JPanel pnl = new JPanel(new BorderLayout()); 
		pnl.setBackground(Color.white);
		pnl.add(lbl,BorderLayout.NORTH);
		JPanel pnllist1=new JPanel();
		pnllist1.setBackground(Color.white);
		pnllist1.add(cmb,JLabel.CENTER);
		pnl.add(pnllist1,BorderLayout.CENTER);
		JPanel pnlBtn = new JPanel();
		pnlBtn.add(btn);
		pnlBtn.setBackground(Color.white);
		pnl.add(pnlBtn,BorderLayout.SOUTH);	
		pnl.setBorder(new LineBorder(Color.LIGHT_GRAY));
		return pnl;
	}
	

}
