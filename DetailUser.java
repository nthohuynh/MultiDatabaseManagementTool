import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.sql.*;
import java.util.*;

public class DetailUser extends JPanel implements ActionListener{
	static JFrame myFrame;
	JLabel jlUsername, jlProfile, jlPassword1,jlPassword2,jlDefaultTablespace,jlTempTablespace;
	JTextField jtUsername, jtProfile;
	JPasswordField jtPassword1,jtPassword2;
	Border border;
	TitledBorder tBorder1, tBorder2,tBorder3;
	JComboBox jcbDefaultTablespace, jcbTempTablespace;
	JRadioButton jrbLocked, jrbUnlocked;
	JPanel jPanel,jPanel1,jPanel2,jPanel3, jPanelN,jPanelB;
	JButton jbApply;
	GridBagLayout gb;
	GridBagConstraints gbc;
	public String username;
  	JTabbedPane tabDBMS = new JTabbedPane();
	Vector dataRole,dataCombo,dataCombo1, dataSys;
	String st,stSQL,SQL;
	Connection con;
	ResultSet rs;
	int stt;
	Statement stmt;
	String xsql;
	String user2;
	JButton jbUp,jbDown,jbUpSystem,jbDownSystem;
	JList jlRole1,jlRole2, jlSystem1, jlSystem2;
	boolean type=true;
	boolean type1=true;
	public DetailUser(String username,Connection cons){
		setLayout(new BorderLayout());
		user2=username;
		con=cons;
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
		jlProfile=new JLabel("Hi\u1EC7n tr\u1EA1ng");
		jlPassword1=new JLabel("M\u1EADt kh\u1EA9u");
		jlPassword2=new JLabel("Xác nh\u1EADn m\u1EADt kh\u1EA9u");
		jtUsername=new JTextField(55);
		jtUsername.setText(user2);
		jtUsername.enable(false);
		jtProfile=new JTextField(50);
		try{
			query1("profile",user2,"dba_users","username");
		}catch(Exception e){ System.out.println("Can not add data in username");};
		jtProfile.setText(st);
		
		jtPassword1=new JPasswordField(50);
		jtPassword2=new JPasswordField(50);
		try{
			query1("password",user2,"dba_users","username");
		}catch(Exception e){ System.out.println("Can not add data in username");};
		jtPassword1.setText(st);
		jtPassword2.setText(st);
		
		add(jPanel1,jlLogin,3,1,2,1);
		add(jPanel1, l4,1,2,2,1);

		add(jPanel1,jlUsername,1,3,2,1);
		add(jPanel1, jtUsername,3,3,2,1);
		
		add(jPanel1, l1,1,4,2,1);
		add(jPanel1, jlProfile,1,5,2,1);
		add(jPanel1, jtProfile,3,5,2,1);
		add(jPanel1, l2,1,6,2,1);
		
		add(jPanel1, jlPassword1,1,7,2,1);
		add(jPanel1, jtPassword1,3,7,2,1);
		add(jPanel1, l,1,8,2,1);
		
		add(jPanel1, jlPassword2,1,9,2,1);
		add(jPanel1, jtPassword2,3,9,2,1);
		
		jPanel2=new JPanel();
		gbc.anchor=GridBagConstraints.WEST;
		gbc.fill=GridBagConstraints.BOTH;
		jPanel2.setLayout(gb);
		tBorder2=new TitledBorder(border,"Không gian b\u1EA3ng");
		jPanel2.setBorder(tBorder2);
		jlDefaultTablespace=new JLabel("M\u1EB7c \u0111\u1ECBnh");
		try{
			query("dba_tablespaces","tablespace_name");
			query1("default_tablespace",user2,"dba_users","username");
			
		}catch(Exception e){ System.out.println("Can not add data in tablespace_name");};
		jcbDefaultTablespace=new JComboBox(dataCombo);
	//	jcbDefaultTablespace.addItem(st);
		jcbDefaultTablespace.setSelectedItem(st);


		jlTempTablespace=new JLabel("T\u1EA1m");
		try{
			query("dba_tablespaces","tablespace_name");
			query1("temporary_tablespace",user2,"dba_users","username");

		}catch(Exception e){ System.out.println("Can not add data in username");};
		jcbTempTablespace=new JComboBox(dataCombo);
	//	jcbTempTablespace.addItem(st);
		jcbTempTablespace.setSelectedItem(st);
		
		
		add(jPanel2, jlDefaultTablespace,1,1,2,1);
		add(jPanel2,jcbDefaultTablespace,3,1,8,1);
		add(jPanel2, l3,1,2,2,1);

		add(jPanel2, jlTempTablespace,1,3,2,1);
		add(jPanel2, jcbTempTablespace,3,3,8,1);
		
		jPanel3=new JPanel();
		jPanel3.setLayout(gb);
		tBorder3=new TitledBorder(border,"Tr\u1EA1ng thái");
		jPanel3.setBorder(tBorder3);
		jrbLocked=new JRadioButton();
		jrbLocked.setLabel("Khóa");
		jrbUnlocked=new JRadioButton();
		jrbUnlocked.setLabel("Không khóa");
		ButtonGroup bg=new ButtonGroup();
		try{
			query1("ACCOUNT_STATUS",user2,"dba_users","username");
		}catch(Exception e){ System.out.println("Can not check");};
	
		if(st.equals("OPEN"))
			jrbUnlocked.setSelected(true);
		
		else jrbLocked.setSelected(true);
	
		bg.add(jrbLocked);
		bg.add(jrbUnlocked);
		add(jPanel3, jrbLocked,1,1,3,1);
		add(jPanel3, jrbUnlocked,6,1,3,1);
		jPanelN=new JPanel();
		jPanelN.setLayout(new BorderLayout());
		jPanelN.add(jPanel2,"Center");
		jPanelN.add(jPanel3,"South");
		
		
		jPanel.add(jPanel1,"Center");
		jPanel.add(jPanelN,"South");
		
	
		/*------------PanelRole---------*/
		JPanel jPanelRole=new JPanel();	
		jPanelRole.setLayout(new BorderLayout());
		try{
			query("dba_roles","role");
		}catch(Exception e){ System.out.println("Can not add data in username");};
		jlRole1=new JList(dataCombo);
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
			query1("GRANTED_ROLE",user2,"DBA_ROLE_PRIVS","GRANTEE");
		}catch(Exception e){ System.out.println("Can not add data in username");};
		dataRole=dataCombo1;
		
		jlRole2=new JList(dataRole);
		JScrollPane sc1=new JScrollPane();
		sc1.setBorder(new TitledBorder(border,"Granted"));
		sc1.setViewportView(jlRole2);
		jPanelRole.add(sc1,"South");
		
		/*-----------Panel system--------------*/
		JPanel jPanelSystem=new JPanel();
		jPanelSystem.setLayout(new BorderLayout());
		try{
			query("SESSION_PRIVS","privilege");
		}catch(Exception e){ System.out.println("Can not add data in username");};
		jlSystem1=new JList(dataCombo);
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
			query1("PRIVILEGE",user2,"DBA_SYS_PRIVS","GRANTEE");
		}catch(Exception e){ System.out.println("Can not add data in username");};
		dataSys=dataCombo1;
		jlSystem2=new JList(dataSys);
		
		JScrollPane sc22=new JScrollPane();
		sc22.setBorder(new TitledBorder(border,"Granted"));
		sc22.setViewportView(jlSystem2);
		jPanelSystem.add(sc22,"South");
		
		/*---------them vao tab----------------*/
		tabDBMS.addTab("General",jPanel);
		tabDBMS.addTab("Role",jPanelRole);
		tabDBMS.addTab("System",jPanelSystem);

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
public ResultSet getRScombo(String table, String column) {
  	
    xsql = "select " + column + " from " + table + "";
    try {
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
      rs = stmt.executeQuery(xsql);
      rs.next();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, ex + "Don't agree", "Message",
                                    1);
    }
    return rs;
  }
  
  public  void query(String table, String column) throws Exception {
    rs = getRScombo(table, column);
    try {
      rs.last();
      stt = rs.getRow();
    } catch (SQLException ex) {System.out.println(ex);};
    dataCombo = new Vector(stt);
    try {
      rs.beforeFirst();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(this,
                                    ex +
                                    "Can't load data ComboBox"
                                    , "Message", 1);
    }
    while (rs.next()) {
      dataCombo.add(rs.getString(column));
    }
    
  } 
  public ResultSet getRScombo1(String tablespace, String username,String tab, String dk) {
  	
    xsql = "select " + tablespace + " from "+tab+" where "+dk+"='" + username + "'";
      try {
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
      rs = stmt.executeQuery(xsql);
      rs.next();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, ex + "Don't agree", "Message",
                                    1);
    }
    return rs;
  }
  public  void query1(String tablespace, String username,String tab, String dk) throws Exception {
    rs = getRScombo1(tablespace, username,tab,dk);
    try {
      rs.last();
      stt = rs.getRow();
    } catch (SQLException ex) {System.out.println(ex);};
    dataCombo1 = new Vector(stt);
    try {
      rs.beforeFirst();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(this,
                                    ex +
                                    "Can't load data ComboBox"
                                    , "Message", 1);
    }
    while (rs.next()) {
      dataCombo1.add(rs.getString(tablespace));
      st = rs.getString(tablespace);
    }
    
  }
public void actionPerformed(ActionEvent e){
	String stl;
	if (e.getActionCommand().equals("Xu\u1ED1ng")){
	
		if(tabDBMS.getSelectedIndex()==1){
			stl=(String)jlRole1.getSelectedValue();
			if(!stl.equals(""))
			{
				if(Check(stl,dataRole)){
					JOptionPane.showMessageDialog(this,"Quy\u1EC1n này \u0111ã t\u1ED3n t\u1EA1i",
			                                    "Thông báo",1);
				}else{
					xsql = "grant "+ stl;
			    	xsql=xsql+" to " + user2 + "";
			    	try{
			     		stmt = con.createStatement();
			      		stmt.execute(xsql);
						dataRole.addElement(stl);
			    	}catch(Exception ex){
			    		JOptionPane.showMessageDialog(this,"Không th\u1EC3 phân quy\u1EC1n",
			                                    "Thông báo",1);
			    	}
					jlRole2.setListData(dataRole);
				}
				
			}
		}
		if(tabDBMS.getSelectedIndex()==2){
			stl=(String)jlSystem1.getSelectedValue();
			if(!stl.equals(""))
			{
				if(Check(stl,dataSys)){
					JOptionPane.showMessageDialog(this,"Quy\u1EC1n này \u0111ã t\u1ED3n t\u1EA1i",
			                                    "Thông báo",1);
				}else{
					xsql = "grant "+ stl;
			    	xsql=xsql+" to " + user2 + "";
			    	try{
			     		stmt = con.createStatement();
			      		stmt.execute(xsql);
			      		dataSys.addElement(stl);
			    	}catch(Exception ex){
			    		JOptionPane.showMessageDialog(this,"Không th\u1EC3 phân quy\u1EC1n",
			                                    "Thông báo",1);
			    	}
			    	jlSystem2.setListData(dataSys);
			    }
				
			}
		}
	}
	if (e.getActionCommand().equals("Lên")){
		if(tabDBMS.getSelectedIndex()==1){
			int i=jlRole2.getSelectedIndex();
			
			String t= jlRole2.getSelectedValue().toString();
		
			
			xsql="revoke "+t+" from "+ user2+"";
			try{
		     		stmt = con.createStatement();
		      		stmt.execute(xsql);
		      		dataRole.remove(jlRole2.getSelectedValue());
		    	}catch(Exception ex){
		    		JOptionPane.showMessageDialog(this,"Don't success",
		                                    "Thông báo",1);
		    }
			jlRole2.setListData(dataRole);
		}
		if(tabDBMS.getSelectedIndex()==2){
			int i=jlSystem2.getSelectedIndex();
			
			String t= jlSystem2.getSelectedValue().toString();
		
			xsql="revoke "+t+" from "+ user2+"";
			try{
		     		stmt = con.createStatement();
		      		stmt.execute(xsql);
		      		dataSys.remove(jlSystem2.getSelectedValue());
		    	}catch(Exception ex){
		    		JOptionPane.showMessageDialog(this,"Don't success",
		                                    "Thông báo",1);
		    }
			jlSystem2.setListData(dataSys);
		}
	}
	if (e.getActionCommand().equals("L\u01B0u")){
		if(jtPassword1.getText().equals(jtPassword2.getText())){
		SQL = "alter user " + jtUsername.getText() + " identified by " + jtPassword1.getText() + " default tablespace " + (String)jcbDefaultTablespace.getSelectedItem() + " temporary tablespace " + (String)jcbTempTablespace.getSelectedItem() + "";
		//SQL1 = " alter user " + jtUsername.getText() + " quota " + jtQuota.getText() + " on " + (String)jcbDefaultTablespace.getSelectedItem()+"";
 		try{
			Statement st=con.createStatement();
			//int update_count = st.execute(SQL);
			st.executeUpdate(SQL);
		//	st.executeUpdate(SQL1);
			JOptionPane.showMessageDialog(this, "\u0110ã c\u1EADp nh\u1EADt",
		                                    "Thông báo",1);
		}catch(Exception eq){ JOptionPane.showMessageDialog(this, eq+"\nKhông th\u1EC3 s\u1EEDa",
		                                    "Thông báo",1);}
		}else JOptionPane.showMessageDialog(this,"Ki\u1EC3m tra l\u1EA1i password",
		                                    "Thông báo",1);
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
		DetailUser usermanager=new DetailUser("NGOCTHO");
		myFrame.getContentPane().add(usermanager);
		
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(800,700);
		myFrame.setVisible(true);
}
	*/	
		

}