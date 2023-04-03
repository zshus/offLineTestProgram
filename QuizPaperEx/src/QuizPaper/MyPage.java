package QuizPaper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MyPage extends JFrame {
	
	private JButton btngradeCheck;
	private JButton btntest;
	private JComboBox<String> boxList;
	private JLabel lbltext;
	private Vector<String> vecList;
	private int count=0;
	private Login login;
	private String seletedItem;
	
	public MyPage(Login login) {
		this.login=login;
		init();
		setDisplay();
		addListener();
		showFrame();
		
		
	}
	
	private void init() {
		vecList=new Vector<>();
		vecList.add("-시험 선택-");
		getFileName(new File("test"));	
		seletedItem=vecList.get(0);
		btngradeCheck=new JButton("성적 조회");
		btngradeCheck.setFocusPainted(false);
		btntest=new JButton("시험보기");
		btntest.setFocusPainted(false);
		boxList=new JComboBox<String>(vecList);
		boxList.setPreferredSize(new Dimension(175,22));
		lbltext=new JLabel("시험을 선택하세요:",JLabel.CENTER);
		lbltext.setFont(new Font("맑은 고딕",Font.BOLD ,15));
		
	}
	private void setDisplay() {
		JPanel pnlAll=new JPanel(new BorderLayout());
		pnlAll.setBackground(Color.white);
		JPanel pnlCenter=new JPanel(new GridLayout(0,1));
		pnlCenter.setBackground(Color.white);
		pnlCenter.add(lbltext);
		JPanel pnllist=new JPanel();
		pnllist.setBackground(Color.white);
		pnllist.add(boxList,JLabel.CENTER);
		pnlCenter.add(pnllist);
		
		JPanel pnlbtn=new JPanel();
		pnlbtn.setBackground(Color.white);
		pnlbtn.add(btntest);
		pnlbtn.add(btngradeCheck);
		pnlCenter.add(pnlbtn);
		pnlAll.add(pnlCenter,BorderLayout.CENTER);
		
		pnlAll.setBorder(new EmptyBorder(10, 50, 15, 50));
		add(pnlAll,BorderLayout.CENTER);

		
		
	}
	private void addListener() {
		ActionListener al=new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==boxList) {
					seletedItem=(String)boxList.getSelectedItem();
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
						new GradeCheck(new File("test\\"+seletedItem), login);
						dispose();
												
					}else {
						JOptionPane.showMessageDialog(MyPage.this, "저장된 성적정보가 없습니다.");
					}
					
				}
			
				
			}
		};
		
		btngradeCheck.addActionListener(al);
		btntest.addActionListener(al);
		boxList.addActionListener(al);
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
		if (!path.isFile()&&count==0) {			
			count++;			
			File[] files = path.listFiles();
			for (File f : files) {
				getFileName(f);
			}						
		} else{
			String name = path.getName();
			vecList.add(name);			
		}
	}

}
