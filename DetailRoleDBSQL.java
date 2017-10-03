import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.sql.*;
import java.util.*;

public class DetailRoleDBSQL extends JPanel implements ActionListener{
	static JFrame myFrame;

	Border border;

	TitledBorder tBorder1, tBorder2,tBorder3;

	JButton jbApply,jbAdd, jbRemove;

	GridBagLayout gb;
	GridBagConstraints gbc;

  	JTabbedPane tabDBMS = new JTabbedPane();
	static Vector dataRole,dataCombo,dataSys;
	String st,stSQL;
	static Connection cons;
	ResultSet rs;
	int stt1,stt;
	Statement stmt;
	String xsql;
	String rolename,dbname;

	public static JList jlRole2, jlSystem2;

	public DetailRoleDBSQL(String db,String username,Connection con){
		setLayout(new BorderLayout());
		rolename=username;
		cons=con;
		dbname=db;
		gb=new GridBagLayout();
		gbc =new GridBagConstraints();
		gbc.anchor=GridBagConstraints.WEST;
		gbc.fill=GridBagConstraints.BOTH;


	
		/*------------PanelRole---------*/
		JPanel jPanelRole=new JPanel();	
		jPanelRole.setLayout(new BorderLayout());
		
		JPanel jpa=new JPanel();
		
		jpa.setBorder(new TitledBorder(border,"Tên nhóm quy\u1EC1n"));
		JLabel lb=new JLabel("Tên:");
		JLabel lbname=new JLabel("         "+rolename);
		jpa.setLayout(gb);
		
		add(jpa,lb,1,1,1,1);
		add(jpa,lbname,2,1,1,1);


		if(rolename.equals("public")){
			try{
			
			query(dbname,"sp_helpuser",rolename,"UserName");
			}catch(Exception e){ System.out.println("Can not add data in username");};
		}else
		try{
			
			query(dbname,"sp_helprolemember",rolename,"MemberName");
		}catch(Exception e){ System.out.println("Can not add data in username");};
		dataRole=dataCombo;
		
		jlRole2=new JList(dataRole);
		JScrollPane sc1=new JScrollPane();
		sc1.setBorder(new TitledBorder(border,"Thành viên c\u1EE7a nhóm quy\u1EC1n"));
		sc1.setViewportView(jlRole2);
	
		JPanel jpnb=new JPanel();
		jpnb.setLayout(gb);
		
		jbAdd=new JButton("Thêm thành viên");
		jbAdd.addActionListener(this);
		jbRemove =new JButton("B\u1ECF thành viên");
		jbRemove.addActionListener(this);
		if(rolename.equals("public")){
			jbAdd.setEnabled(false);
			jbRemove.setEnabled(false);
		}
		add(jpnb,jbAdd,1,1,1,1);
		add(jpnb,jbRemove,2,1,1,1);
		
		
	
	
	
		jPanelRole.add(sc1,"Center");
		jPanelRole.add(jpa,"North");
		jPanelRole.add(jpnb,"South");

	
		tabDBMS.addTab("General",jPanelRole);
		/*-----------Panel Permission--------------*/
	/*	JPanel jPanelSystem=new JPanel();
		jPanelSystem.setLayout(new BorderLayout());*///
		if(!(rolename.equals("db_owner") ||
			rolename.equals("db_accessadmin")||
			rolename.equals("db_securityadmin")||
			rolename.equals("db_ddladmin")||
			rolename.equals("db_backupoperator")||
			rolename.equals("db_datareader")||
			rolename.equals("db_datawriter")||
			rolename.equals("db_denydatareader")||
			rolename.equals("db_denydatawriter")))	
		{
			GrantPermission frame = new GrantPermission(dbname,rolename,cons);
			frame.setBorder(new TitledBorder(border,"Phân quy\u1EC1n cho nhóm quy\u1EC1n"));
			tabDBMS.addTab("Permission",frame);
		}

	
	//	jPanelSystem.add(frame,"Center");
		
		/*---------them vao tab----------------*/


		add(tabDBMS,"Center");
		
		
	/*	jbApply=new JButton("Apply");
		jbApply.addActionListener(this);
		jbApply.setSize(50,10);
		JPanel jPanelB=new JPanel();
		jPanelB.setLayout(gb);
		add(jPanelB,jbApply,1,1,1,1);
		add(jPanelB,"South");*/
		
	}	
public void add(JPanel jPanel, Component c, int x, int y, int nx, int ny){
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=nx;
		gbc.gridheight=ny;
		gb.setConstraints(c,gbc);
		jPanel.add(c,gbc);
}
public  void query(String db1, String command, String thamso, String cot) throws Exception {
    stt1=0;
    ResultSet rs,rs2;
	
	if(!thamso.equals("public"))
		xsql = "use "+db1+" exec "+command+ " '"+ thamso+ "'";
	else xsql = "use "+db1+" exec "+command+ " ";
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
	   	   if(!Check(rs2.getString(cot),dataCombo))
	   	   dataCombo.add(rs2.getString(cot));
	   }
    
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, ex + "Don't agree", "Message",
                                    1);
    }
   

  } 
  
public void actionPerformed(ActionEvent e){
	String stl;

	if (e.getActionCommand().equals("Thêm thành viên")){
			
		AddMember gd=new AddMember("Thành viên",cons,dbname,rolename);
		Connect.centerScreen(gd);
		gd.setVisible(true);
	
	}
	if (e.getActionCommand().equals("B\u1ECF thành viên")){
		
		String sta =(String)jlRole2.getSelectedValue();

		String xsq="use "+dbname+" exec sp_droprolemember  @rolename = '"+rolename+"',@membername ='"+sta+"'";
		try{
			if(!sta.equals(""))
			{	
			stmt = cons.createStatement();
			stmt.execute(xsq);
			}
		}catch(Exception e1){
			JOptionPane.showMessageDialog(this, "Error :"+e1, "Message",
                                    1);
         }
		dataRole.remove(jlRole2.getSelectedValue());
		jlRole2.setListData(dataRole);
	
	}
}
public static void main(String[] args){
		myFrame =new JFrame("as");
		try {
     		DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
    		cons=DriverManager.getConnection ( "jdbc:microsoft:sqlserver://THODN:1433","sa","abc123");
         }catch(Exception e){System.out.println("khong ket noi");}
            
		DetailRoleDBSQL usermanager=new DetailRoleDBSQL("Northwind", "khtg",cons);
		myFrame.getContentPane().add(usermanager);
		
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(500,300);
		myFrame.setVisible(true);
}

public class AddMember extends JFrame implements ActionListener{
	JFrame myFrame;
	public JList jlMember;
	JButton jbOK, jbCancel;
	JPanel jPanel;
	Container contenPane;
	Vector dataCombo;
	GridBagLayout gb;
	GridBagConstraints gbc;
	Connection cons1;
	Statement st;
	Border border1;
	String rolename, db_name;
	public AddMember(String title, Connection con1,String db, String name){
		super(title);
		cons1=con1;
		rolename=name;
		db_name=db;
		setSize(200,250);
	//	this.setResizable(false);
		jPanel = new JPanel();
		
		contenPane=getContentPane();
		
		contenPane.setLayout(new BorderLayout());
		
		gb=new GridBagLayout();
	//	gbc =new GridBagConstraints();
	//	gbc.anchor=GridBagConstraints.WEST;
	//	gbc.fill=GridBagConstraints.BOTH;

		jPanel.setLayout(new BorderLayout());
		
	
		gbc=new GridBagConstraints();
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
			//	System.exit(0);
				dispose();

			}
		});
		JLabel jl=new JLabel("Ch\u1ECDn thành viên \u0111\u1EC3 thêm:");
		
		try{
			query1(db_name,"sp_helpuser ","","UserName");
		}catch(Exception e){ System.out.println("Can not add data in username");};
		jlMember=new JList(dataCombo);
	//	jlMember.setSize(200,250);
		JScrollPane sc=new JScrollPane();
		sc.setBorder(new TitledBorder(border1,""));
		sc.setViewportView(jlMember);
	
		jbOK=new JButton("\u0110\u1ED3ng ý");
		jbCancel=new JButton("B\u1ECF qua");
		jbOK.addActionListener(this);
		jbCancel.addActionListener(this);
		
		jPanel.add(jl,BorderLayout.NORTH);
		jPanel.add(sc, BorderLayout.CENTER);
		JPanel jpb=new JPanel();
		jpb.setLayout(gb);
		
		add(jpb,jbOK,1,9,1,1);
		add(jpb,jbCancel,3,9,1,1);
		
		jPanel.add(jpb, BorderLayout.SOUTH);
		
		contenPane.add(jPanel,BorderLayout.CENTER);

	}

public void add(JPanel jPanel, Component c, int x, int y, int nx, int ny){
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=nx;
		gbc.gridheight=ny;
		gb.setConstraints(c,gbc);
		jPanel.add(c,gbc);
}
public  void query1(String db,String command, String thamso, String cot) throws Exception {
    int stt1=0;
    ResultSet rs3,rs1;

     String xsql1 = "use "+db+" exec  "+command+"";
    try {
      st = cons1.createStatement();
      rs3 = st.executeQuery(xsql1);
      try {
				while(rs3.next()){
					stt1++;
			}
		    } catch (SQLException ex) {System.out.println(ex);};
       dataCombo = new Vector(stt1);
        
       rs1 = st.executeQuery(xsql1);
       while (rs1.next()) {
	   	   if((!Check(rs1.getString(cot),dataCombo)) &&(!rs1.getString(cot).equals("dbo")))
	   	   dataCombo.add(rs1.getString(cot));
	   }
    
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, ex + "\nDon't agree", "Message",
                                    1);
    }
}
public void actionPerformed(ActionEvent e){
	String stlist;
	if (e.getActionCommand().equals("\u0110\u1ED3ng ý")){
		stlist =(String)jlMember.getSelectedValue();
			if(!stlist.equals(""))
			{	if(Check(stlist, DetailRoleDBSQL.dataRole)){
					JOptionPane.showMessageDialog(null,"Thành viên này \u0111ã t\u1ED3n t\u1EA1i","Thông báo", JOptionPane.WARNING_MESSAGE);

				}else{
				String xsq="use "+db_name+" exec sp_addrolemember @rolename = '"+rolename+"', @membername ='"+stlist+"'";
				try{
					st = cons1.createStatement();
			//		System.out.println(xsq);
    				st.execute(xsq);
    				DetailRoleDBSQL.dataRole.addElement(stlist);
					DetailRoleDBSQL.jlRole2.setListData(DetailRoleDBSQL.dataRole);

				}catch(Exception e1){
				JOptionPane.showMessageDialog(null,"This login added"+e1,"Thông báo", JOptionPane.WARNING_MESSAGE);
                   
              }
			}
	//	this.dispose();
		}
	}
	if (e.getActionCommand().equals("B\u1ECF qua")){
				this.dispose();

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


}