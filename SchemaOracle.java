import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
public class SchemaOracle extends JPanel implements ActionListener
{
//-----------------Khai bao cac bien dung tr2ong chuong trinh--

	String stSQL;
	JPanel jpOption;
	GridBagLayout gb;
	GridBagConstraints gbc;
	JButton jbAdd,jbEdit,jbDel;
	JScrollPane jscPane,resultPane;
	Connection con;
	JTable tableResult;
	String user1, st;
	static JFrame fr;
	public SchemaOracle(Connection cons){

		stSQL=new String("select USERNAME   from dba_users");// where owner='"+user+"'");

		con=cons;
		setLayout(new BorderLayout());

	
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();	
		gbc.anchor=GridBagConstraints.WEST;
		gbc.fill=GridBagConstraints.BOTH;
		
		
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("Schema trong Oracle");
		jlObject.setIcon(new ImageIcon("icon\\cmanager_b.gif         "));
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 20));
		jPanel.add(jlObject);
		add(jPanel, "North");
		
	
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
/*--------------Ham addComponent------------------------------------*/
public void add1(JPanel jPanel,Component c, int x, int y, int nx, int ny){
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=nx;
		gbc.gridheight=ny;
		gb.setConstraints(c,gbc);
		jPanel.add(c,gbc);
}
/*----------------Thuc hien cau lenh SQL--------------------------------*/
public void executeStatement(String sql){
		try{	 
			Statement stmt = con.createStatement();
			if(stmt.execute(sql)){
				ResultSet rs=stmt.getResultSet();
				//lay ten cac truong
				ResultSetMetaData rsmd = rs.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();
				Vector vTitle=new Vector(numberOfColumns,0);
			//	for(int j=1; j<=numberOfColumns;j++) {
					vTitle.add("Tên c\u1EE7a Schema");
			//	}
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
	try{
		Statement stmt = con.createStatement();
				  stmt.executeUpdate(SQL);
		JOptionPane.showMessageDialog(this,"Xóa thành công!","Thông báo",1);
	}catch(Exception ew){
	 JOptionPane.showMessageDialog(this,ew+"\nKhông th\u1EC3 xóa!","Thông báo",1);
	}
}
//-----------------Ham su ly su kien-------------------------
public void actionPerformed(ActionEvent ae){
	if (ae.getActionCommand().equals("T\u1EA1o m\u1EDBi"))
		{
					CreateSchema add=new CreateSchema("T\u1EA1o m\u1EDBi Schema","",con);
					add.getType(true);
					
					Connect.centerScreen(add);
				
					executeStatement("select USERNAME   from dba_users");// where owner='"+user1+"'");// ORDER BY [dba_users].[First Name]");
					
				
		}
		
		if (ae.getActionCommand().equals("Xóa"))
		{
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"Ch\u1ECDn schema mu\u1ED1n xóa!","Thông báo",1);
			}else{
			
				int row=tableResult.getSelectedRow();
				String confirm = "B\u1EA1n có mu\u1ED1n xóa?";
				switch (JOptionPane.showConfirmDialog(this,confirm,"Thông báo",1))
					{
					case JOptionPane.YES_OPTION:{
						try{

						deleteUser();
						executeStatement("select USERNAME   from dba_users");// where owner='"+user1+"'");// ORDER BY [dba_users].[First Name]");
						}catch(Exception ee){System.out.print(ee.toString());}
					}
					case JOptionPane.CANCEL_OPTION:break;
				}
			}
		}
	
		if (ae.getActionCommand().equals("S\u1EEDa"))
		{
			String t;
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"Ch\u1ECDn schema mu\u1ED1n s\u1EEDa!","Thông báo",1);
			}else{
			
				int row=tableResult.getSelectedRow();
				t= (String)tableResult.getValueAt(row,0);
					

					CreateSchema add=new CreateSchema("",t,con);
					add.getType(false);
					Connect.centerScreen(add);
				}
			executeStatement("select USERNAME   from dba_users");
		}
}
//-----------------Ham chinh cua chuong trinh----------------
/*	public static void main(String[] args) 
	{
		myFrame =new JFrame("as");
		try
		{	Class.forName("oracle.jdbc.driver.OracleDriver");//.newInstance();
			 connect = DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:dbTHO", "system", "abc123");
		}catch(Exception e){}
		SchemaOracle usermanager=new SchemaOracle(connect);
		myFrame.getContentPane().add(usermanager);
		
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(800,500);
		myFrame.setVisible(true);
	}
*/
}
class CreateSchema extends JFrame implements ActionListener 
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
	Container contenPane;
	public static String st;
//--------Ham cau tu------------------------------
	public CreateSchema(String title,String use, Connection cons){
		super(title);
		setSize(380,180);
		
		user2=use;
		con1=cons;
		
		
		jPanel=new JPanel();
		contenPane=getContentPane();
		contenPane.setLayout(new BorderLayout());
		
		gbcl=new GridBagConstraints();
		jPanel.setLayout(gbl=new GridBagLayout());
		gbcl.anchor=GridBagConstraints.EAST;
		gbcl.fill=GridBagConstraints.BOTH;


		jlUsername=new JLabel("Tên                      :");
		jlPassword=new JLabel("M\u1EADt kh\u1EA9u:");
		jlDefaultTablespace=new JLabel("Không gian b\u1EA3ng m\u1EB7c \u0111\u1ECBnh:");
		jlTempTablespace=new JLabel("Không gian b\u1EA3ng t\u1EA1m:");
		jlQuota=new JLabel("Dung l\u01B0\u1EE3ng :");
		
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
		jbCancel.addActionListener(this);
		
		JLabel jlNotnull=new JLabel("(not null)");
		add2(jPanel, jlUsername,1,1,2,1);
		add2(jPanel,jtUsername,3,1,3,1);
		add2(jPanel,jlNotnull,6,1,2,1);
		add2(jPanel,jlPassword,1,2,2,1);
		add2(jPanel,jtPassword,3,2,3,1);
		
		add2(jPanel,jlQuota,1,3,2,1);
		add2(jPanel,jtQuota,3,3,3,1);
			
		add2(jPanel,jlDefaultTablespace,1,4,2,1);
		add2(jPanel,jcbDefaultTablespace,3,4,3,1);
		add2(jPanel,jlTempTablespace,1,5,2,1);
		add2(jPanel,jcbTempTablespace,3,5,3,1);

		add2(jPanel,jbAdd,3,8,1,1);
		add2(jPanel,jbCancel,4,8,1,1);
		contenPane.add(jPanel, BorderLayout.CENTER);

	}
public void actionPerformed(ActionEvent ae){
//-----------Ham su ly su kien click Edit----------
	if (ae.getActionCommand().equals("B\u1ECF qua")){
		this.dispose();

	}
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
			Statement stt=con1.createStatement();
			stt.executeUpdate(SQL);
			stt.executeUpdate(SQL1);
			jtUsername.setText("");
			jtPassword.setText("");
			JOptionPane.showMessageDialog(this,"\u0110ã c\u1EADp nh\u1EADt!","Thông báo",1);
			stt.close();
			this.dispose();
		}catch(Exception ec){
	 		JOptionPane.showMessageDialog(this,ec+"\nKhông th\u1EC3 c\u1EADp nh\u1EADt!","Thông báo",1);
		}
}
//--------------Ham addComponent------------------------------------
	public void add2(JPanel jPanel, Component c, int x, int y, int nx, int ny){
		gbcl.gridx=x;
		gbcl.gridy=y;
		gbcl.gridwidth=nx;
		gbcl.gridheight=ny;
		gbl.setConstraints(c,gbcl);
		jPanel.add(c,gbcl);
	}
}
