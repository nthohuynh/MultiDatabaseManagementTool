import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.sql.*;
import java.util.*;

public class DetailUserSQL extends JPanel implements ActionListener{
	static JFrame myFrame;
	JLabel jlUsername, jlPassword;
	JTextField jtUsername;
	JPasswordField jtPassword;
	Border border;
	TitledBorder tBorder1, tBorder2,tBorder3;

	JComboBox jcbDB, jcbLang;

	JPanel jPanel,jPanel1,jPanel2,jPanel3, jPanelN,jPanelB;
	JButton jbApply;
	GridBagLayout gb;
	GridBagConstraints gbc;
	public String username;
  	JTabbedPane tabDBMS = new JTabbedPane();
	Vector dataSrvRole,dataCombo,dataCombo1, dataAccessDB,dataRole,dataSys;
	
	
	String st,stSQL;
	static Connection cons;
	ResultSet rs;
	int stt;
	Statement stmt;
	String xsql;
	String user2,usernamedb;;
	JButton jbUp,jbDown,jbUpSystem,jbDownSystem;
	JList jlRole1,jlRole2, jlSystem1, jlSystem2;
	boolean type=true;
	boolean type1=true;
	public DetailUserSQL(String username,Connection con){
		setLayout(new BorderLayout());
		user2=username;
		cons=con;
		/*-------------Panel general---------------*/
		jPanel =new JPanel();
		gb=new GridBagLayout();
		gbc =new GridBagConstraints();
		gbc.anchor=GridBagConstraints.WEST;
		gbc.fill=GridBagConstraints.BOTH;
		jPanel.setLayout(new BorderLayout());
		jPanel1=new JPanel();
		jPanel1.setLayout(gb);
		tBorder1=new TitledBorder(border,"Chi ti\u1EBFt ng\u01B0\u1EDDi dùng");
		jPanel1.setBorder(tBorder1);
		JLabel l=new JLabel("         ");
		JLabel l1=new JLabel("         ");
		JLabel l2=new JLabel("         ");
		JLabel l3=new JLabel("         ");
		JLabel l4=new JLabel("         ");
		JLabel jlLogin=new JLabel("THÔNG TIN \u0110\u0102NG NH\u1EACP");
		jlLogin.setIcon(new ImageIcon("icon\\vty32.gif"));
		jlLogin.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));

		jlUsername =new JLabel("Tên ng\u01B0\u1EDDi dùng");
		jlPassword=new JLabel("M\u1EADt kh\u1EA9u");
		jtUsername=new JTextField(55);
		jtUsername.setText(user2);
		jtUsername.enable(false);
		
		jtPassword=new JPasswordField(50);
		try{
			query1("sp_helplogins  @LoginNamePattern = ",user2,"SID");
		}catch(Exception e){ System.out.println("Can not add data in username");};
		jtPassword.setText(st);
		
		add(jPanel1,jlLogin,3,1,2,1);
		add(jPanel1, l4,1,2,2,1);

		add(jPanel1,jlUsername,1,3,2,1);
		add(jPanel1, jtUsername,3,3,2,1);
		
		add(jPanel1, l1,1,4,2,1);
		
		add(jPanel1, jlPassword,1,7,2,1);
		add(jPanel1, jtPassword,3,7,2,1);
			
		jPanel2=new JPanel();
		gbc.anchor=GridBagConstraints.WEST;
		gbc.fill=GridBagConstraints.BOTH;
		jPanel2.setLayout(gb);
		tBorder2=new TitledBorder(border,"M\u1EB7c \u0111\u1ECBnh");
		jPanel2.setBorder(tBorder2);
		JLabel jlDB=new JLabel("C\u01A1 s\u1EDF d\u1EEF li\u1EC7u");
		try{
			query("sp_helpdb","name");
			query1("sp_helplogins  @LoginNamePattern = ",user2,"DefDBName");
			
		}catch(Exception e){ System.out.println("Can not add data in tablespace_name");};
		jcbDB=new JComboBox(dataCombo);
	//	jcbDB.addItem(st);
		jcbDB.setSelectedItem(st);


		JLabel jlLang=new JLabel("Ngôn ng\u1EEF");
		try{
			query("sp_helplanguage ","name");
			query1("sp_helplogins  @LoginNamePattern = ",user2,"DefLangName");

		}catch(Exception e){ System.out.println("Can not add data in username");};
		jcbLang=new JComboBox(dataCombo);
	//	jcbLang.addItem(st);
		jcbLang.setSelectedItem(st);
		
		
		add(jPanel2, jlDB,1,1,2,1);
		add(jPanel2,jcbDB,3,1,8,1);
		add(jPanel2, l3,1,2,2,1);

		add(jPanel2, jlLang,1,3,2,1);
		add(jPanel2, jcbLang,3,3,8,1);
		

		jPanelN=new JPanel();
		jPanelN.setLayout(new BorderLayout());
		jPanelN.add(jPanel2,"Center");
		
		
		jPanel.add(jPanel1,"Center");
		jPanel.add(jPanelN,"South");
		
	
		/*------------PanelServer Role---------*/
		JPanel jPanelRole=new JPanel();	
		jPanelRole.setLayout(new BorderLayout());
		try{
			query("sp_helpsrvrole","ServerRole");
		}catch(Exception e){ System.out.println("Can not add data in username");};
		jlRole1=new JList(dataCombo);
		dataRole=dataCombo;
		jlRole1.setVisibleRowCount(12);
		JScrollPane sc=new JScrollPane();
		sc.setBorder(new TitledBorder(border,"Available"));
		sc.setViewportView(jlRole1);
		jPanelRole.add(sc,"North");
		jbUp=new JButton("Lên");
		jbUp.addActionListener(this);
		jbDown=new JButton("Xu\u1ED1ng");
		jbDown.addActionListener(this);
		
		JPanel jPanelButton=new JPanel();
		jPanelButton.setLayout(new FlowLayout());
		jPanelButton.add(jbUp);
		jPanelButton.add(jbDown);
		jPanelRole.add(jPanelButton,"Center");
		try{
			GrantedSrvRoles();
		}catch(Exception e){ System.out.println("Can not add data in username");};
		
		
		jlRole2=new JList(dataSrvRole);
		JScrollPane sc1=new JScrollPane();
		sc1.setBorder(new TitledBorder(border,"Granted"));
		sc1.setViewportView(jlRole2);
		jPanelRole.add(sc1,"South");
		
		/*-----------Panel system--------------*/
		JPanel jPanelSystem=new JPanel();
		jPanelSystem.setLayout(new BorderLayout());
		try{
			query("sp_helpdb","name");
		}catch(Exception e){ System.out.println("Can not add data in username");};
		jlSystem1=new JList(dataCombo);
		dataSys=dataCombo;
		jlSystem1.setVisibleRowCount(12);
		JScrollPane sc2=new JScrollPane();
		sc2.setBorder(new TitledBorder(border,"Available"));
		sc2.setViewportView(jlSystem1);
		jPanelSystem.add(sc2,"North");
		jbUp=new JButton("Lên");
		jbUp.addActionListener(this);
		jbDown=new JButton("Xu\u1ED1ng");
		jbDown.addActionListener(this);
		
		JPanel jPanelButton1=new JPanel();
		jPanelButton1.setLayout(new FlowLayout());
		jPanelButton1.add(jbUp);
		jPanelButton1.add(jbDown);
		jPanelSystem.add(jPanelButton1,"Center");

		try{
			GrantedAccessDB();
		}catch(Exception e){ System.out.println("Can not add data in username");};
		jlSystem2=new JList(dataAccessDB);
		
		JScrollPane sc22=new JScrollPane();
		sc22.setBorder(new TitledBorder(border,"Granted"));
		sc22.setViewportView(jlSystem2);
		jPanelSystem.add(sc22,"South");
		
		/*---------them vao tab----------------*/
		tabDBMS.addTab("General",jPanel);
		tabDBMS.addTab("Server Role",jPanelRole);
		tabDBMS.addTab("Database Access",jPanelSystem);

		add(tabDBMS,"Center");
		
		
		jbApply=new JButton("L\u01B0u");
		jbApply.addActionListener(this);
		jbApply.setSize(50,10);
		jPanelB=new JPanel();
		jPanelB.setLayout(gb);
		add(jPanelB,jbApply,1,1,1,1);
		add(jPanelB,"South");
		
	}	
public void add(JPanel jPanel, Component c, int x, int y, int nx, int ny){
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=nx;
		gbc.gridheight=ny;
		gb.setConstraints(c,gbc);
		jPanel.add(c,gbc);
}
public  void query(String command, String dk) throws Exception {
    ResultSet rs,rs2;
    xsql = ""+command;
    int stt1=0;    

    try {
      stmt = cons.createStatement();
      rs = stmt.executeQuery(xsql);
      try {
				while(rs.next()){
					stt1++;
				}
		    } catch (SQLException ex) {System.out.println(ex);};
       dataCombo = new Vector(stt1);
        
       rs2 = stmt.executeQuery(xsql);
       while (rs2.next()) {
	   	   dataCombo.add(rs2.getString(dk));
	   }
	   stmt.close();
    
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, ex + "Don't agree", "Message",
                                    1);
    }
    
 } 
 public  void query1(String command, String username, String dk) throws Exception {
   ResultSet rs,rs2;
   int stt1=0;    

   xsql = " " + command+ "'"+username+"'";;
     try {
      stmt = cons.createStatement();
      rs = stmt.executeQuery(xsql);
      try {
				while(rs.next()){
					stt1++;
				}
		    } catch (SQLException ex) {System.out.println(ex);};
       dataCombo1 = new Vector(stt1);
        
       rs2 = stmt.executeQuery(xsql);
       while (rs2.next()) {
	   	   dataCombo1.add(rs2.getString(dk));
	   	   st = rs2.getString(dk);

	   }
	   stmt.close();
    
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, ex + "Don't agree", "Message",
                                    1);
    }
    
}
public void GrantedSrvRoles(){
	int stt1=0;
	Statement stmt1;
    ResultSet rs1,rs2;
	String strsql1="sp_helpsrvrole";
	String st1,st2;
	dataSrvRole = new Vector(12);

	try{
		stmt=cons.createStatement();
		rs1=stmt.executeQuery(strsql1);
		while(rs1.next()){
			stt1++;
			st1=rs1.getString("ServerRole");
			String strsql2="sp_helpsrvrolemember @srvrolename ='"+st1+"'";
			
			stmt1=cons.createStatement();

			rs2=stmt1.executeQuery(strsql2);
			while(rs2.next()){
				st2=rs2.getString("MemberName");
				if(st2.equals(user2)){
					dataSrvRole.add(st1);
				}
			}
		}
		stmt.close();
	}catch(Exception e){System.out.println("er: "+e);}
	
}
public void GrantedAccessDB(){
	Statement stmt1;
    ResultSet rs1,rs2;
	String strsql1="sp_helpdb";
	String st1,st2;
	dataAccessDB = new Vector(12);

	try{
		stmt=cons.createStatement();
		rs1=stmt.executeQuery(strsql1);
		while(rs1.next()){
			st1=rs1.getString("name");
			String strsql2="use "+st1+" exec sp_helpuser";
		
			stmt1=cons.createStatement();
			rs2=stmt1.executeQuery(strsql2);
			while(rs2.next()){
				st2=rs2.getString("LoginName");
				try{
					if(st2.equals(user2)){
						if(!Check(st1,dataAccessDB))
						dataAccessDB.add(st1);
					}
				}catch(Exception q){}

			}
		}
		stmt.close();
	}catch(SQLException e){System.out.println("er:  "+e);}
}

public void actionPerformed(ActionEvent e){
	String stl;
	if (e.getActionCommand().equals("Xu\u1ED1ng")){
	
		if(tabDBMS.getSelectedIndex()==1){
			stl=(String)jlRole1.getSelectedValue();
			if(!stl.equals(""))
			{	
				
				if(Check(stl,dataSrvRole)){
					JOptionPane.showMessageDialog(this,"Quy\u1EC1n này \u0111ã t\u1ED3n t\u1EA1i",
			                                    "Thông báo",1);
				}else{
					xsql = "sp_addsrvrolemember @loginame='"+user2
			           +"', @rolename='"+stl+"'";;
					 try{
					     		stmt = cons.createStatement();
					      		stmt.execute(xsql);
					      		dataSrvRole.addElement(stl);
					      	//	JOptionPane.showMessageDialog(this,"Quy\u1EC1n này \u0111ã t\u1ED3n t\u1EA1i",
					        //                           "Thông báo",1);
					 			stmt.close();
					 }catch(Exception ex){
					    	JOptionPane.showMessageDialog(this,"Không th\u1EC3 phân",
					                                    "Thông báo",1);
					  }
					jlRole2.setListData(dataSrvRole);
				}
				
			}
		}
		if(tabDBMS.getSelectedIndex()==2){
			stl=(String)jlSystem1.getSelectedValue();
			if(!stl.equals(""))
			{
				if(Check(stl,dataAccessDB)){
					JOptionPane.showMessageDialog(this,"C\u01A1 s\u1EDF d\u1EEF li\u1EC7u này \u0111ã t\u1ED3n t\u1EA1i",
			                                    "Thông báo",1);
				}else{
					xsql = "use "+stl+" exec sp_grantdbaccess @loginame = '"+user2+
							"' ,@name_in_db = "+user2;
			    	try{
			     		stmt = cons.createStatement();
			      		stmt.execute(xsql);
						dataAccessDB.addElement(stl);
					//	JOptionPane.showMessageDialog(this,"Granted success",
			         //                           "Message",1);
			         	stmt.close();
			    	}catch(Exception ex){
			    		JOptionPane.showMessageDialog(this,"Không th\u1EC3 phân",
			                                    "Thông báo",1);
			    	}
					jlSystem2.setListData(dataAccessDB);
				}
			}
		}
	}
	if (e.getActionCommand().equals("Lên")){
		if(tabDBMS.getSelectedIndex()==1){
			int i=jlRole2.getSelectedIndex();
			
			String t= jlRole2.getSelectedValue().toString();
		//	System.out.println(t);
		
			
			xsql="sp_dropsrvrolemember  @loginame =  '"+user2+
					"',  @rolename =  '"+t+"'";

;
			try{
		     		stmt = cons.createStatement();
		      		stmt.execute(xsql);
		      		dataSrvRole.remove(jlRole2.getSelectedValue());
					stmt.close();
		    	}catch(Exception ex){
		    		JOptionPane.showMessageDialog(this,"Don't success",
		                                    "Message",1);
		    }
			jlRole2.setListData(dataSrvRole);
		}
		if(tabDBMS.getSelectedIndex()==2){
			int i=jlSystem2.getSelectedIndex();
			
			String t= jlSystem2.getSelectedValue().toString();
			try{
				Statement stm=cons.createStatement();
				
				ResultSet rs=stm.executeQuery("use "+t+" exec sp_helpuser");
				
				while(rs.next()){
					try{
						if(rs.getString("LoginName").equals(user2))
						usernamedb=rs.getString("UserName");
					}catch(Exception f){};
				}
				stm.close();
				
				
			}catch(Exception ea){System.out.println(ea);};
			
			
			xsql="use "+t+" exec sp_revokedbaccess @name_in_db = '"+usernamedb+"'";
			try{
		     		stmt = cons.createStatement();
		      		stmt.execute(xsql);
		      		dataAccessDB.remove(jlSystem2.getSelectedValue());
					stmt.close();
		    	}catch(Exception ex){
		    		JOptionPane.showMessageDialog(this,ex+"\nDon't success",
		                                    "Message",1);
		    }
			jlSystem2.setListData(dataAccessDB);
		}
	}
}
public boolean Check(String st1, Vector vt){
	for(int i=0;i<vt.size();i++){
		if(st1.equals((String)vt.elementAt(i))){
			return true;
		}
	}
	return false;

}
/*public static void main(String[] args){
		myFrame =new JFrame("as");
		try {
     		DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
    		cons=DriverManager.getConnection ( "jdbc:microsoft:sqlserver://THODN:1433","sa","abc123");
         }catch(Exception e){System.out.println("khong ket noi");}
         
		DetailUserSQL usermanager=new DetailUserSQL("ngoctho",cons);
		myFrame.getContentPane().add(usermanager);
		
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(800,700);
		myFrame.setVisible(true);
}	*/
		

}