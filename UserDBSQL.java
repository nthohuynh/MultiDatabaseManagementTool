import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
public class UserDBSQL extends JPanel implements ActionListener
{
//-----------------Khai bao cac bien dung tr2ong chuong trinh--
	static JFrame myFrame, fr;
	JTable jtTable;
	String stSQL;
	JPanel jpTable,jpOption;
	GridBagLayout gb;
	GridBagConstraints gbc;
	JButton jbAdd,jbDel;
	JScrollPane jscPane,resultPane;
	static Connection cons;
	JTable tableResult;
	String username,us;
	ResultSet rss;
//-----------------Ham cau tu-------------------------------
	public UserDBSQL(String username, Connection con){

		cons=con;
		stSQL = new String("USE "+username+" EXEC sp_helpuser");
		us=username;

		setLayout(new BorderLayout());
		
		
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();
		
		
	
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("Ng\u01B0\u1EDDi dùng c\u1EE7a c\u01A1 s\u1EDF d\u1EEF li\u1EC7u: "+us );
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 25));
		jlObject.setIcon(new ImageIcon("icon\\largeIcon.gif"));
		jPanel.add(jlObject);
		
		jpTable=new JPanel();
		int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		resultPane=new JScrollPane(v,h);
		jpTable.setLayout(new BorderLayout());
		jpTable.add(resultPane,BorderLayout.CENTER);
	//	contenPane.add(jpTable,BorderLayout.CENTER);
		add(jpTable,"Center");
		
	//	connect();
		executeStatement(stSQL);

		jpOption=new JPanel();
		jbAdd=new JButton("T\u1EA1o ng\u01B0\u1EDDi dùng");
		jbDel=new JButton("Xóa");

		jbAdd.addActionListener(this);
		jbDel.addActionListener(this);

		add(jpOption,jbAdd,1,1,2,1);
		add(jpOption,jbDel,5,1,2,1);
		add(jpOption,"South");
		add(jPanel, "North");
		
		


	}
//--------------Tao ket noi--------------------------------------
//--------------Ham addComponent------------------------------------
public void add(JPanel jPanel,Component c, int x, int y, int nx, int ny){
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
		Statement stmt = cons.createStatement();
		if(stmt.execute(sql)){
			ResultSet rs=stmt.getResultSet();
			//lay ten cac truong
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			Vector vTitle=new Vector(2,0);
		//	for(int j=1; j<=2;j++) {
				vTitle.add(rsmd.getColumnLabel(1));
				vTitle.add(rsmd.getColumnLabel(3));
		//	}
			// dua du lieu vao vector vData
			Vector vData=new Vector(10,12);
			Vector vt=new Vector(10);
			while(rs.next()) {
				Vector row=new Vector(numberOfColumns,0);
			//	for(int i=1; i<=2;i++){ 
				if(!Check(rs.getObject(1).toString(),vt)){
					vt.add(rs.getObject(1));
					row.add(rs.getObject(1));
					row.add(rs.getObject(3));
					vData.add(row);
				}
					
			//	}
				
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
public boolean Check(String st1, Vector vt){
	for(int i=0;i<vt.size();i++){
		if(st1.equals((String)vt.elementAt(i))){
			return true;
		}
	}
	return false;

}
//--------------Ham xoa user -----------------------------
public void deleteRole(){
	int row=tableResult.getSelectedRow();
	String SQL="use "+us+" exec sp_dropuser @name_in_db = '" +(String)(tableResult.getValueAt(row,0)) +"'";
//	connect();
	 try{
		Statement stmt = cons.createStatement();
				  stmt.executeUpdate(SQL);
		//		  cons.close();
				  stmt.close();
	 }catch(Exception ew){}
}

//-----------------Ham su ly su kien-------------------------
	public void actionPerformed(ActionEvent ae){
//-----------Ham su ly su kien click Edit----------
	String tablename, username;
	//-------------Click su kien Delelete------------------	
		if (ae.getActionCommand().equals("Xóa"))
		{
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"You must select in record you want delete !");
			}else{
			
				int row=tableResult.getSelectedRow();
				String confirm = "Do you want to delete ?";
				switch (JOptionPane.showConfirmDialog(this,confirm))
					{
					case JOptionPane.YES_OPTION:{
						try{
						deleteRole();	
						//Xoa xong hien thi lai danh sach
				//		connect();
						executeStatement("USE "+us+" EXEC sp_helpuser");
						}catch(Exception ee){System.out.print(ee.toString());}
					}
					case JOptionPane.CANCEL_OPTION:break;
				}
			}
		}
		
	//-----------Su ly su kien addUser-----------
		if (ae.getActionCommand().equals("T\u1EA1o ng\u01B0\u1EDDi dùng"))
		{
					Createusersql add=new Createusersql("T\u1EA1o ng\u01B0\u1EDDi dùng",us,cons);
					
					Connect.centerScreen(add);
				
		}
	
	
	}
}
class Createusersql extends JFrame implements ActionListener 
{
	JLabel jlUsername, jllogin;
	JComboBox jtlogin;
	JTextField jtUsername;
	JButton jbAdd,jbCancel;
	JPanel jPanel;
	GridBagLayout gbl;
	GridBagConstraints gbcl;
	int stt=0;
	Statement stmt1,stmt;
	ResultSet rs,rs1;
	String xsql;
	String db_name;
	Connection con1;
	Vector datalogin;
	Container contenPane;
//--------Ham cau tu------------------------------

	public Createusersql(String title, String db,Connection cons){
		super(title);
		setSize(380,130);
		con1=cons;
		jPanel=new JPanel();
		db_name=db;
		gbcl=new GridBagConstraints();
		jPanel.setLayout(gbl=new GridBagLayout());
		gbcl.anchor=GridBagConstraints.EAST;
		gbcl.fill=GridBagConstraints.BOTH;

		contenPane=getContentPane();
		contenPane.setLayout(new BorderLayout());

		jlUsername=new JLabel("Tên ng\u01B0\u1EDDi dùng:");
		jtUsername=new JTextField(15);
		
		jllogin=new JLabel("Tên \u0111\u0103ng nh\u1EADp");
		
		
		try{
			stmt1=con1.createStatement();
			stmt=con1.createStatement();
			rs=stmt1.executeQuery("sp_helplogins");
			rs1=stmt.executeQuery("sp_helplogins");;
			while(rs.next()){
				stt++;
			}
			datalogin=new Vector(stt);
			while(rs1.next()){
				datalogin.add(rs1.getString("LoginName"));
			}
		}catch(Exception e){System.out.println("khong the them"+e);}
		jtlogin=new JComboBox(datalogin);
		
		
		jbAdd=new JButton("\u0110\u1ED3ng ý");
		jbAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				add1(true);
			}
		});
		
		jbCancel=new JButton("B\u1ECF qua");
		jbCancel.addActionListener(this);
		add2(jlUsername,1,2,2,1);
		add2(jtUsername,3,2,3,1);
		add2(jllogin,1,1,2,1);
		add2(jtlogin,3,1,3,1);
		
		add2(jbAdd,3,3,1,1);
		add2(jbCancel,4,3,1,1);
		
		contenPane.add(jPanel, BorderLayout.CENTER);

	}

public void actionPerformed(ActionEvent ae){
//-----------Ham su ly su kien click Edit----------
	if (ae.getActionCommand().equals("B\u1ECF qua")){
		this.dispose();

	}
}
//--------------Su ly su kien click Add----------------------------
public void add1(boolean update){
		String SQL;
		if(!jtUsername.getText().equals(""))
		SQL = "use "+db_name+" exec  sp_adduser '" + jtlogin.getSelectedItem().toString()+"', '"+
			jtUsername.getText()+"'";
		else SQL = "use "+db_name+" exec  sp_adduser '" + jtlogin.getSelectedItem().toString()+"', '"+
			jtlogin.getSelectedItem().toString()+"'";

		try{
			Statement st=con1.createStatement();
		
			st.executeUpdate(SQL);
			JOptionPane.showMessageDialog(this,"Ng\u01B0\u1EDDi dùng \u0111ã \u0111\u01B0\u1EE3c thêm vào CSDL",
                                    "Thông báo",1);
            this.dispose();
		}catch(Exception ec){
			JOptionPane.showMessageDialog(this,"Không th\u1EC3 thêm ng\u01B0\u1EDDi dùng",
                                    "Thông báo",1);
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
}

