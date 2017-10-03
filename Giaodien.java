import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.sql.*;
import java.util.*;


public class Giaodien extends JPanel {
	static JFrame myFrame;
	
	String st, st1,st2;
	public static JPanel jPanel, jPanelmt;
	public static JLabel pic;
	public static DefaultMutableTreeNode [] vectorTree;
	public static Connection[] arrayconnect;
	public static String[] arrayuser;
	public static int count;
	
	public Giaodien(){
		vectorTree =new DefaultMutableTreeNode[12];
		arrayconnect =new Connection[12];
		arrayuser =new String[12];
		count=0;
		JFrame frame = new JFrame();
        frame.setUndecorated(true);
        
        JLabel lbl1 = new JLabel("H\u1EC7 th\u1ED1ng qu\u1EA3n tr\u1ECB c\u01A1 s\u1EDF d\u1EEF li\u1EC7u \u0111a n\u0103ng");
        lbl1.setOpaque(true);
        lbl1.setBackground(new Color(250, 220, 80));
        lbl1.setForeground(Color.BLUE);
        lbl1.setHorizontalAlignment(JLabel.CENTER);
        lbl1.setVerticalAlignment(JLabel.CENTER);
        lbl1.setFont(new Font("Arial", Font.BOLD, 20));
        
        JLabel lbl2 = new JLabel("loading...");
        lbl2.setOpaque(true);
        lbl2.setBackground(Color.WHITE);
        lbl2.setForeground(Color.BLUE);
        lbl2.setVerticalAlignment(JLabel.BOTTOM);
		lbl2.setHorizontalAlignment(JLabel.CENTER);
        
        JProgressBar pb = new JProgressBar(1, 11);
        pb.setForeground(new Color(120, 50, 200));
        
        Container preCont = frame.getContentPane();
        preCont.setLayout(new BorderLayout());
        
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        jp.add(lbl1, BorderLayout.CENTER);
        jp.add(lbl2, BorderLayout.SOUTH);
        
        preCont.add(jp, BorderLayout.CENTER);
        preCont.add(pb, BorderLayout.SOUTH);
		
	    frame.setSize(new Dimension(300, 150));
        frame.setResizable(false);
        frame.setLocationRelativeTo(this);
        frame.show();
        
	
	    pb.setValue(1);
		jPanel=new JPanel();
	    pb.setValue(2);

	    pb.setValue(3);

		setLayout(new BorderLayout());
	    pb.setValue(4);

		add(jPanel,"Center");
	    pb.setValue(5);


		jPanel.setLayout(new BorderLayout());
		pb.setValue(6);
		
		
		jPanelmt =new JPanel();
		pb.setValue(7);
		
		jPanelmt.setLayout(new BorderLayout());
		pb.setValue(8);
		
		add(jPanelmt,"North");

   		ImageIcon ii=new ImageIcon("image\\world copy.gif");
		pic=new JLabel(ii);
		jPanel.add(pic);
		

		menutool mt=new menutool();
		pb.setValue(9);


	
	
		jPanelmt.add(mt,"North");
	    pb.setValue(10);

	
	
  
        pb.setValue(11);
        frame.dispose();
        
	}

	public static void main(String agrs[])throws Exception{
		ImageIcon image = new ImageIcon("icon\\jserv16.gif");
		UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
		myFrame=new JFrame("H\u1EC7 th\u1ED1ng qu\u1EA3n tr\u1ECB c\u01A1 s\u1EDF d\u1EEF li\u1EC7u \u0111a n\u0103ng");
		myFrame.setIconImage(image.getImage());

		Giaodien gd=new Giaodien();
		myFrame.getContentPane().add(gd,"Center");
		myFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(800,550);
		myFrame.setVisible(true);
		myFrame.repaint();

	}
}