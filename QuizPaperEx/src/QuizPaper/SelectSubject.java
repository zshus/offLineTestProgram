package QuizPaper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class SelectSubject extends JFrame{
	
	private JLabel lblText;
	private Vector<JCheckBox> vec;
	private Vector<String> vecName;
	private JButton btnStart;
	private String pathName;
	private MyPage mypage;
	private Vector<String> selectedList;
	private Login login;
	public SelectSubject(String pathName,MyPage mypage,Login login) {
		this.pathName=pathName;
		this.mypage=mypage;
		this.login=login;
		init();
		setDisplay();
		addListener();
		showFrame();
		
	}
	private void init() {
		selectedList=new Vector<String>();
		vecName=new Vector<String>();
		getFileName(new File("test\\"+pathName));	
		lblText=new JLabel("과목을 체크하세요!",JLabel.CENTER);
		lblText.setFont(new Font("맑은 고딕",Font.BOLD ,15));
		lblText.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnStart=new JButton("start");
		btnStart.setFocusPainted(false);
		
	}
	private void setDisplay() {
		JPanel pnlAll=new JPanel(new BorderLayout());
		pnlAll.setBackground(Color.white);
		pnlAll.add(lblText,BorderLayout.NORTH);

		JPanel pnlCenter=new JPanel(new GridLayout(0,3,10,10));
		pnlCenter.setBackground(Color.white);
		vec=new Vector<JCheckBox>();
		for(int i=vecName.size()-1;i>=0;i--) {			
			JCheckBox box=new JCheckBox(vecName.get(i));
			box.setBackground(Color.white);
			box.setFocusPainted(false);
			vec.add(box);
			pnlCenter.add(box,JLabel.CENTER);
		}
		pnlAll.add(pnlCenter,BorderLayout.CENTER);
		
		JPanel pnlSouth=new JPanel();
		pnlSouth.setBackground(Color.white);
		pnlSouth.add(btnStart);
		pnlAll.add(pnlSouth,BorderLayout.SOUTH);
		pnlAll.setBorder(new EmptyBorder(10, 30, 20, 30));
		add(pnlAll);
		
	}
	private void addListener() {
		
		for(int i=0;i<vec.size();i++) {
			JCheckBox box=vec.get(i);
			int num=i;
			box.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==ItemEvent.SELECTED) {
						String s=vecName.get(num);
						selectedList.add(s);
					}else if(e.getStateChange()==ItemEvent.DESELECTED){
						String s=vecName.get(num);
						boolean flag=selectedList.remove(s);						
					}
					
				}
			});
		}
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedList.size()==0) {
					JOptionPane.showMessageDialog(SelectSubject.this, "해당 과목을 선택하세요!");					
				}else {
					Collections.sort(selectedList);
					new TestPaper(new File("test\\"+pathName),login,selectedList);
					dispose();
				}
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				int choice = JOptionPane.showConfirmDialog(SelectSubject.this, "시험 취소하겠습니까?", "알림", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (choice == JOptionPane.OK_OPTION) {
					mypage.setVisible(true);
					dispose();
				}
			}
		});
		
	}
	private void showFrame() {
		pack();
		setTitle("과목 선택");
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	private void getFileName(File path) {		
		if (!path.isFile()) {									
			File[] files = path.listFiles();
			for (File f : files) {
				getFileName(f);
			}						
		} else{
			String name = path.getName();
			vecName.add(name);		
		}
	}
}
