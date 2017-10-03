import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
public class UserOracle extends JPanel implements ActionListener
{
//-----------------Khai bao cac bien dung tr2ong chuong trinh--
	
	String stConditon="",stFindWhat="First Name";
	String stSQL;
	JLabel jlFindWhat,jlCondition;
	JTextField jtfCondition;
	JComboBox jcbFindWhat;
	Container contenPane;
	JPanel jpFind,jpTable,jpOption;
	GridBagLayout gb;
	GridBagConstraints gbc;
	JButton jbFind,jbAdd,jbEdit,jbDel, jbCancel;
	JScrollPane jscPane,resultPane;
	Connection con;
	JTable tableResult;
	String user1, st;
	static JFrame myFrame,fr;
//-----------------Ham cau tu-------------------------------
	public UserOracle(Connection cons){
		con=cons;

		stSQL=new String("select USERNAME, ACCOUNT_STATUS,DEFAULT_TABLESPACE,  TEMPORARY_TABLESPACE   from dba_users");// where owner='"+user+"'");

		setLayout(new BorderLayout());

		jpFind=new JPanel();
	
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();	
		gbc.anchor=GridBagConstraints.WEST;
		gbc.fill=GridBagConstraints.BOTH;
		
		
		jpFind.setLayout(gb);
		jlFindWhat=new JLabel("Tìm v\u1EDBi :  ");
		jlCondition=new JLabel("T\u1EEB khóa : ");
		jtfCondition=new JTextField(20);
		
		String tam[]={"USERNAME", "USER_ID" ,"PASSWORD","ACCOUNT_STATUS", "DEFAULT_TABLESPACE" , "TEMPORARY_TABLESPACE"};
		jcbFindWhat=new JComboBox(tam);
		jcbFindWhat.setSelectedIndex(0);
		JLabel jlTab=new JLabel("  ");
		jbFind=new JButton("Tìm");
		jbFind.addActionListener(this);
		add1(jpFind,jlTab,1,1,1,1);
		add1(jpFind,jlFindWhat,0,2,1,1);
		add1(jpFind,jcbFindWhat,1,2,3,1);
		add1(jpFind,jlCondition,0,4,1,1);
		add1(jpFind,jtfCondition,1,4,4,1);
		add1(jpFind,jbFind,6,4,2,1);
		add1(jpFind,jlTab,0,4,2,1);
		add(jpFind, "North");
		
	
		resultPane=new JScrollPane();
	
	
		add(resultPane,"Center");
		if(con!=null)
		executeStatement(stSQL);

		jpOption=new JPanel();
		jbAdd=new JButton("T\u1EA1o m\u1EDBi");
		jbEdit=new JButton("S\u1EEDa");
		jbDel=new JButton("Xóa");
		
		jbAdd.addActionListener(this);
		jbEdit.addActionListener(this);
		jbDel.addActionListener(this);
	
		jpOption.setLayout(gb);
		
		add1(jpOption,jbAdd,1,1,2,1);
		add1(jpOption,jbEdit,3,1,2,1);
		add1(jpOption,jbDel,5,1,2,1);


		add(jpOption,"South");

	}
//--------------Ham addComponent------------------------------------
public void add1(JPanel jPanel,Component c, int x, int y, int nx, int ny){
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=nx;
		gbc.gridheight=ny;
		gb.setConstraints(c,gbc);
		jPanel.add(c,gbc);
}
//----------------Thuc hien cau lenh SQL--------------------------------
public void executeStatement(String sql){
		try{	 
		Statement stmt = con.createStatement();
		if(stmt.execute(sql)){
			ResultSet rs=stmt.getResultSet();
			//lay ten cac truong
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			Vector vTitle=new Vector(numberOfColumns,0);
			for(int j=1; j<=numberOfColumns;j++) {
				vTitle.add(rsmd.getColumnLabel(j));
			}
			// dua du lieu vao vector vData
			Vector vData=new Vector(10,12);
			while(rs.next()) {
				Vector row=new Vector(numberOfColumns,0);
				for(int i=1; i<=numberOfColumns;i++){ 
					row.add(rs.getObject(i));
				}
				vData.add(row);
			}
			rs.close();
			stmt.close();
			tableResult=new JTable(vData,vTitle);
			tableResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableResult.setSelectionBackground(new Color(220,100,100));
			tableResult.setGridColor(new Color(0,0,150));
			resultPane.setViewportView(tableResult);
			resultPane.setBorder(BorderFactory.createLineBorder(Color.lightGray,6));
			}
		 else {
			int updateCount=stmt.getUpdateCount();
			JOptionPane.showMessageDialog(this,"Updated "+ updateCount+" record");
		}
		} catch(Exception e){  System.out.println("Error " + e); }
	}

//--------------Ham xoa user -----------------------------
public void deleteUser(){
	int row=tableResult.getSelectedRow();
	String SQL="drop user " +(String)(tableResult.getValueAt(row,0)) + "";
//	connect();
	 try{
		Statement stmt = con.createStatement();
				  stmt.executeUpdate(SQL);
			//	  con.close();
				  stmt.close();
	 }catch(Exception ew){}
}
//-----------------Ham su ly su kien-------------------------
	public void actionPerformed(ActionEvent ae){
//-----------Ham su ly su kien click Edit----------
	if (ae.getActionCommand().equals("T\u1EA1o m\u1EDBi"))
		{
					addUser add=new addUser("",con);
					add.getType(true);
					fr =new JFrame("T\u1EA1o m\u1EDBi  ng\u01B0\u1EDDi dùng");
					fr.getContentPane().add(add);
					
					fr.addWindowListener(new WindowAdapter(){
					public void windowClosing(WindowEvent e){
							
							fr.disable();
					}
					});
					fr.setSize(380,200);
					Connect.centerScreen(fr);
					fr.setVisible(true);

				//	connect();
					executeStatement("select USERNAME, ACCOUNT_STATUS,DEFAULT_TABLESPACE,  TEMPORARY_TABLESPACE   from dba_users");// where owner='"+user1+"'");// ORDER BY [dba_users].[First Name]");
					
				
		}
	
	//-------------Click su kien Delelete------------------	
		if (ae.getActionCommand().equals("Xóa"))
		{
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"Ch\u1ECDn ng\u01B0\u1EDDi dùng mu\u1ED1n xóa!","Thông báo",1);
			}else{
			
				int row=tableResult.getSelectedRow();
				String confirm = "B\u1EA1n có mu\u1ED1n xóa??";
				switch (JOptionPane.showConfirmDialog(this,confirm,"Thông báo",1))
					{
					case JOptionPane.YES_OPTION:{
						try{

						deleteUser();
						System.out.println(user1);
	
						//Xoa xong hien thi lai danh sach
					//	connect();
						executeStatement("select USERNAME, ACCOUNT_STATUS,DEFAULT_TABLESPACE,  TEMPORARY_TABLESPACE   from dba_users");// where owner='"+user1+"'");// ORDER BY [dba_users].[First Name]");
						}catch(Exception ee){System.out.print(ee.toString());}
					}
					case JOptionPane.CANCEL_OPTION:break;
				}
			}
		}
	//------------Su ly su kien click About-------------------
		if (ae.getActionCommand().equals("Tìm"))
		{
			if (jcbFindWhat.getSelectedIndex()==0)
			{
				stFindWhat="USERNAME";
			} 
			if (jcbFindWhat.getSelectedIndex()==1)
				{
					stFindWhat="USER_ID";
				}
			if (jcbFindWhat.getSelectedIndex()==2)
				{
					stFindWhat="PASSWORD";
				}
			if (jcbFindWhat.getSelectedIndex()==3)
				{
					stFindWhat="ACCOUNT_STATUS";
				}
			if (jcbFindWhat.getSelectedIndex()==4)
				{
					stFindWhat="DEFAULT_TABLESPACE";
				}
			if (jcbFindWhat.getSelectedIndex()==5)
				{
					stFindWhat= "TEMPORARY_TABLESPACE";
				}
			stSQL = "SELECT USERNAME,  USER_ID,  PASSWORD,  ACCOUNT_STATUS,  DEFAULT_TABLESPACE,  TEMPORARY_TABLESPACE FROM dba_users WHERE " + stFindWhat + " like '%"+jtfCondition.getText().trim()+"%'" + "";
		//	connect();
			executeStatement(stSQL);

		}
	//-----------Su ly su kien addUser-----------
		if (ae.getActionCommand().equals("S\u1EEDa"))
		{
			String t;
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"Ch\u1ECDn ng\u01B0\u1EDDi dùng mu\u1ED1n s\u1EEDa!","Thông báo",1);
			}else{
			
				int row=tableResult.getSelectedRow();
				t= (String)tableResult.getValueAt(row,0);
					

					addUser add=new addUser(t,con);
					add.getType(false);
					fr =new JFrame("S\u1EEDa ng\u01B0\u1EDDi dùng");
					fr.getContentPane().add(add);
					
					fr.addWindowListener(new WindowAdapter(){
					public void windowClosing(WindowEvent e){
					fr.disable();
					}
					});
					fr.setSize(380,200);
					Connect.centerScreen(fr);
					fr.setVisible(true);
				}
				//	connect();
					executeStatement("select USERNAME, ACCOUNT_STATUS,DEFAULT_TABLESPACE,  TEMPORARY_TABLESPACE   from dba_users");
				//	jpTable.revalidate();

		

		}
	}
//-----------------Ham chinh cua chuong trinh----------------
/*	public static void main(String[] args) 
	{
		myFrame =new JFrame("as");
		UserOracle usermanager=new UserOracle("SYSTEM");
		myFrame.getContentPane().add(usermanager);
		
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(800,500);
		myFrame.setVisible(true);
	}*/

public class addUser extends JPanel 
{
	JLabel jlUsername,jlPassword,jlDefaultTablespace,jlTempTablespace,jlQuota,jlNote;
	JTextField jtUsername,jtDefaultTablespace,jtTempTablespace,jtQuota;
	JPasswordField jtPassword;
	JComboBox jcbDefaultTablespace,jcbTempTablespace;
	JButton jbAdd,jbCancel;
	JPanel jPanel;
	GridBagLayout gbl;
	GridBagConstraints gbcl;
	boolean type;
	Vector dataCombo,dataCombo1;
	ResultSet rs;
	int stt;
	Statement stmt;
	String xsql;
	String user2;
	Connection con1;
//--------Ham cau tu------------------------------
	public addUser(String user0, Connection cons){
		con1=cons;
		jPanel=new JPanel();
		user2=user0;
		gbcl=new GridBagConstraints();
		jPanel.setLayout(gbl=new GridBagLayout());
		gbcl.anchor=GridBagConstraints.EAST;
		gbcl.fill=GridBagConstraints.BOTH;


		jlUsername=new JLabel("Tên ng\u01B0\u1EDDi dùng:");
		jlPassword=new JLabel("M\u1EADt kh\u1EA9u:");
		jlDefaultTablespace=new JLabel("Không gian b\u1EA3ng m\u1EB7c \u0111\u1ECBnh :");
		jlTempTablespace=new JLabel("Không gian b\u1EA3ng t\u1EA1m :");
		jlQuota=new JLabel("Dung l\u01B0\u1EE3ng:");
		
		jtPassword=new JPasswordField(15);
		
		try{
			query("dba_tablespaces","tablespace_name");
			query1("default_tablespace",user2);
			
		}catch(Exception e){ System.out.println("Can not add data in tablespace_name");};
		jcbDefaultTablespace=new JComboBox(dataCombo);
		jcbDefaultTablespace.addItem(st);
		jcbDefaultTablespace.setSelectedItem(st);

		
		
		try{
			query("dba_tablespaces","tablespace_name");
			query1("temporary_tablespace",user2);

		}catch(Exception e){ System.out.println("Can not add data in username");};
		jcbTempTablespace=new JComboBox(dataCombo);
		jcbTempTablespace.addItem(st);
		jcbTempTablespace.setSelectedItem(st);
		
		
		
		
		jtQuota=new JTextField(15);
		jtUsername=new JTextField(15);
		jtUsername.setText(user2);

		
		jbAdd=new JButton("\u0110\u1ED3ng ý");
		jbAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if (type)
				{
					add1(type);
				
				}else {
					add1(type);
					}
				
			}
		});
		jbCancel=new JButton("B\u1ECF qua");
		jbCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ee){
				fr.dispose();

			}
		});
		JLabel jlNotnull=new JLabel("(not null)");
		add2(jlUsername,1,1,2,1);
		add2(jtUsername,3,1,3,1);
		add2(jlNotnull,6,1,2,1);
		add2(jlPassword,1,2,2,1);
		add2(jtPassword,3,2,3,1);
		
		add2(jlQuota,1,3,2,1);
		add2(jtQuota,3,3,3,1);
			
		add2(jlDefaultTablespace,1,4,2,1);
		add2(jcbDefaultTablespace,3,4,3,1);
		add2(jlTempTablespace,1,5,2,1);
		add2(jcbTempTablespace,3,5,3,1);

		add2(jbAdd,3,8,1,1);
		add2(jbCancel,4,8,1,1);
		add(jPanel);

	}

public ResultSet getRScombo(String table, String column) {
  	
    xsql = "select " + column + " from " + table + "";
    try {
      stmt = con1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
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
  public ResultSet getRScombo1(String tablespace, String username) {
  	
    xsql = "select " + tablespace + " from dba_users where username ='" + username + "'";
    try {
      stmt = con1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
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
  public  void query1(String tablespace, String username) throws Exception {
    rs = getRScombo1(tablespace, username);
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
    //  dataCombo1.add(rs.getString(tablespace));
      st = rs.getString(tablespace);
    }
    
  } 
  
//-----------------getType---------------
public void getType(boolean updateOrEdit){
	type=updateOrEdit;
}

//--------------Su ly su kien click Add----------------------------
public void add1(boolean update){
		String SQL, SQL1,SQL2;
		SQL1="";
		if (update)
		{
			SQL = " create user " + jtUsername.getText() + " identified by " + jtPassword.getText() + " default tablespace " + (String)jcbDefaultTablespace.getSelectedItem() + " temporary tablespace " + (String)jcbTempTablespace.getSelectedItem() + "";
			SQL1 = " alter user " + jtUsername.getText() + " quota " + jtQuota.getText() + " on " + (String)jcbDefaultTablespace.getSelectedItem()+"";

		}else{
			SQL = "alter user " + jtUsername.getText() + " identified by " + jtPassword.getText() + " default tablespace " + (String)jcbDefaultTablespace.getSelectedItem() + " temporary tablespace " + (String)jcbTempTablespace.getSelectedItem() + "";
			SQL1 = " alter user " + jtUsername.getText() + " quota " + jtQuota.getText() + " on " + (String)jcbDefaultTablespace.getSelectedItem()+"";

 		}
		try{
			Statement st=con1.createStatement();
			//int update_count = st.execute(SQL);
			st.executeUpdate(SQL);
			st.executeUpdate(SQL1);
			//st.executeUpdate(SQL2);
			//JOptionPane.showMessageDialog(this,"Da cap nhat "+ update_count+" ban ghi vao CSDL");
			jtUsername.setText("");
			jtPassword.setText("");
			//jtDefaultTablespace.setText("");
			//jtTempTablespace.setText("");
			//jtAddress.setText("");
			//jtNote.setText("");
		//	this.setVisible(type);
		}catch(Exception ec){
			System.out.println(ec.toString());
	}
}
//--------------Ham addComponent------------------------------------
	public void add2(Component c, int x, int y, int nx, int ny){
		gbcl.gridx=x;
		gbcl.gridy=y;
		gbcl.gridwidth=nx;
		gbcl.gridheight=ny;
		gbl.setConstraints(c,gbcl);
		jPanel.add(c,gbcl);
	}
};
}
