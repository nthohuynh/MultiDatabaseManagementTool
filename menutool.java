import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;


public class menutool extends JPanel implements ActionListener{
	static JFrame myFrame;
	static JPanel pane;
	static MenuItem save;
	ImageIcon image = new ImageIcon("icon\\ConfigMidtier.gif");
	public menutool(){
		
	//	treess=new Trees();
	
		JPanel jPanel=new JPanel();
		
		JPanel jPanel1=new JPanel();
		
	
		setLayout(new BorderLayout());
		

		
		add(jPanel,"North");
		add(jPanel1,"West");
	
	
	
		jPanel.setLayout(new BorderLayout());
		jPanel.add(createMenu(),"North");
		jPanel.add(createToolBar(),"South");
		
		
	};
	public JMenuBar createMenu(){
		JMenuBar menuBar=new JMenuBar();
		JMenu file=new JMenu("K\u1EBFt n\u1ED1i");
		JMenu edit=new JMenu("Edit");
		JMenu sqlserver=new JMenu("SQLServer");
		JMenu oracle=new JMenu("Oracle");

		
		JMenu help=new JMenu("H\u01B0\u1EDBng d\u1EABn");
		JMenu lang=new JMenu("Language");

		file.setMnemonic('K');
		edit.setMnemonic('E');
		sqlserver.setMnemonic('Q');
		oracle.setMnemonic('O');
		help.setMnemonic('H');
		
		//them vao menubar
		menuBar.add(file);
	//	menuBar.add(edit);
		menuBar.add(sqlserver);
		menuBar.add(oracle);
		menuBar.add(help);
	//	menuBar.add(lang);
		
		JMenuItem openorac=new JMenuItem("Oracle");
		openorac.setIcon(new ImageIcon("icon\\dbInstGreen.gif"));
		JMenuItem opensql=new JMenuItem("SQL Server");
		opensql.setIcon(new ImageIcon("icon\\dbInstGreen.gif"));
		
		openorac.addActionListener(this);
		opensql.addActionListener(this);
			
		
		JMenuItem exit=new JMenuItem("Thoát");
		exit.addActionListener(this);
		exit.setIcon(new ImageIcon("icon\\error.gif"));
		
		file.add(openorac);
		file.add(opensql);
		file.add(new JSeparator());
		file.add(exit);
		
		
		JMenuItem helpsql=new JMenuItem("Tr\u1EE3 giúp SQL");
		helpsql.addActionListener(this);
		helpsql.setIcon(new ImageIcon("icon\\apply_site.gif"));
	/*	
		JMenuItem restore=new JMenuItem("Restore database");
		restore.addActionListener(this);
		restore.setIcon(new ImageIcon("icon\\rbsonline.gif"));
			
		sqlserver.add(backup);*/
		sqlserver.add(helpsql);
		
		
		
		
		/*------them vao menu oracle------*/
	/*	JMenuItem usermanager=new JMenuItem("User Manager");
		usermanager.addActionListener(this); 
	//	oracle.add(usermanager);
		JMenuItem tablemanager=new JMenuItem("Table Manager");
		tablemanager.addActionListener(this); 
	//	oracle.add(tablemanager);
		JMenuItem tablespacemanager=new JMenuItem("Tablespace Manager");
		tablespacemanager.addActionListener(this);
		oracle.add(tablespacemanager);
		JMenuItem authoritymanager=new JMenuItem("Authority Manager");
		authoritymanager.addActionListener(this);
		oracle.add(authoritymanager);*/

		
		
		JMenuItem helpcontent = new JMenuItem("Tr\u1EE3 giúp");
		helpcontent.setIcon(new ImageIcon("icon\\help.png"));
		helpcontent.addActionListener(this);
		
		
		JMenuItem about = new JMenuItem("Tác gi\u1EA3");
		about.setIcon(new ImageIcon("icon\\help1.gif"));
		
		about.addActionListener(this); 

		help.add(helpcontent);
		help.addSeparator();
		help.add(about);
			
	/*	JMenuItem eng = new JMenuItem("English");
		eng.setIcon(new ImageIcon("icon\\e.gif"));
		
		JMenuItem viet = new JMenuItem("Vietnamese");
		viet.setIcon(new ImageIcon("icon\\v.gif"));
		lang.add(eng);
		lang.add(viet);
		eng.addActionListener(this); 
		viet.addActionListener(this); */

		
		return menuBar;
		
	}
public JToolBar createToolBar(){
		JToolBar jToolBar=new JToolBar();
		
		
		ImageIcon iconconnect=new ImageIcon("icon\\connect.gif");
		Action actionCon =new AbstractAction("",iconconnect){
			public void actionPerformed(ActionEvent e) {
					
					Connect connec=new Connect("Oracle");
					connec.setIconImage(image.getImage());
					Connect.centerScreen(connec);
					connec.setVisible(true);
					
				}
		};
		JButton bconnect= new JButton(actionCon);
		bconnect.setToolTipText("Connect");
	
		
		
		ImageIcon iconclosefile=new ImageIcon("icon\\closeFile.png");
		Action actionSQL = new AbstractAction("", iconclosefile) {
				public void actionPerformed(ActionEvent e) {
					/*	AddTable gd=new AddTable("Create table");
						Connect.centerScreen(gd);
						gd.setVisible(true);*/
				}
		};
		
		JButton bclosefile= new JButton(actionSQL);
		bclosefile.setToolTipText("SQL");
		
		
		ImageIcon iconuser=new ImageIcon("icon\\user.gif");
		Action actionNew = new AbstractAction("", iconuser) {
				public void actionPerformed(ActionEvent e) {
				}
		};

		JButton buser= new JButton(actionNew);
		buser.setToolTipText("User");
		
		ImageIcon iconhelp = new ImageIcon("icon\\help.png");
		Action actionNew1 = new AbstractAction("", iconhelp) {
				public void actionPerformed(ActionEvent e) {
				try{
				String command[] = new String[] {"C:\\WINDOWS\\hh", "Help\\Multipurpose_database_administration_system.chm"};
				Process child = Runtime.getRuntime().exec(command);
				}catch(Exception e1){};
				}
		};
		JButton bhelp = new JButton(actionNew1);
		bhelp.setToolTipText("Giup do");
		

		jToolBar.add(bconnect);
		jToolBar.add(bclosefile);
		jToolBar.add(buser);
		jToolBar.add(bhelp);
		return jToolBar;
	}
public void actionPerformed(ActionEvent ae){
	
//-----------Ham su ly su kien click Edit----------

	
			if (ae.getActionCommand().equals("Oracle"))
			{
			//	if(Connect.con==null){
					Connect connec=new Connect("Oracle");
					connec.setIconImage(image.getImage());
					Connect.centerScreen(connec);
					connec.setVisible(true);
		//			}
			
			}
			if (ae.getActionCommand().equals("SQL Server"))
			{
			//	if(ConnectSQL.cons==null){
					ConnectSQL connec=new ConnectSQL("SQL Server");
					connec.setIconImage(image.getImage());
					Connect.centerScreen(connec);
					connec.setVisible(true);
			//		}
			
			}
		
  			if (ae.getActionCommand().equals("Tr\u1EE3 giúp SQL"))
			{
				try{
				String command[] = new String[] {"C:\\WINDOWS\\hh", "Help\\TransactSQL.chm"};
				Process child = Runtime.getRuntime().exec(command);
				}catch(Exception e){};
  			}
  			
			if (ae.getActionCommand().equals("Tác gi\u1EA3"))
			{
			JOptionPane.showMessageDialog(this,	"H\u1EC7 th\u1ED1ng qu\u1EA3n tr\u1ECB c\u01A1 s\u1EDF d\u1EEF li\u1EC7u \u0111a n\u0103ng \nPhiên b\u1EA3n :1.0\nTác gi\u1EA3: Hu\u1EF3nh Ng\u1ECDc Th\u1ECD \nH\u1ED9p th\u01B0: ngocthobkdn@gmail.com\n" + System.getProperty("os.name")+" Phiên b\u1EA3n :"+System.getProperty("os.version"),"Thông tin ch\u01B0\u01A1ng trình",1);

			}
			if (ae.getActionCommand().equals("Tr\u1EE3 giúp"))
			{
				try{
				String command[] = new String[] {"C:\\WINDOWS\\hh", "Help\\Multipurpose_database_administration_system.chm"};
				Process child = Runtime.getRuntime().exec(command);
				}catch(Exception e){};


			}

			if (ae.getActionCommand().equals("Vietnamese"))
			{
				
			}
			if (ae.getActionCommand().equals("Thoát"))
			{
					System.exit(0);
				
			}
			
		}

	public static void main(String agrs[]){
		myFrame=new JFrame("Multipurpose database administration system");
		menutool gd=new menutool();
		myFrame.getContentPane().add(gd,"Center");
	//	myFrame.getContentPane().add(treess,"West");
	
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(800,600);
		myFrame.setVisible(true);
	}
}