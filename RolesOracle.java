import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
public class RolesOracle extends JPanel implements ActionListener
{
//-----------------Khai bao cac bien dung tr2ong chuong trinh--
	JTable jtTable;
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
	public RolesOracle(Connection cons){

		stSQL=new String("select * from dba_roles");

		con=cons;
		setLayout(new BorderLayout());

	
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();	
		gbc.anchor=GridBagConstraints.WEST;
		gbc.fill=GridBagConstraints.BOTH;
		
		
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("Roles trong Oracle");
		jlObject.setIcon(new ImageIcon("icon\\naicon.gif"));
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 20));
		jPanel.add(jlObject);

	
		add(jPanel, "North");
		
		jpTable=new JPanel();
		int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		resultPane=new JScrollPane(v,h);
		jpTable.setLayout(new BorderLayout());
		jpTable.add(resultPane,BorderLayout.CENTER);
	
		add(jpTable,"Center");
		if(con!=null)
		executeStatement(stSQL);

		jpOption=new JPanel();
		jbAdd=new JButton("T\u1EA1o role");
		jbEdit=new JButton("Edit");
		jbDel=new JButton("Xóa");
		jbCancel=new JButton("Cancel");
		
		jbAdd.addActionListener(this);
		jbEdit.addActionListener(this);
		jbDel.addActionListener(this);
		jbCancel.addActionListener(this);
	
		jpOption.setLayout(gb);
		
		add1(jpOption,jbAdd,1,1,2,1);
	//	add1(jpOption,jbEdit,3,1,2,1);
		add1(jpOption,jbDel,5,1,2,1);
	//	add1(jpOption,jbCancel,7,1,2,1);


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
		//	for(int j=1; j<=numberOfColumns;j++) {
				vTitle.add("Roles");
				vTitle.add("Authentication");
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
	String SQL="drop role " +(String)(tableResult.getValueAt(row,0)) + "";
	 try{
		Statement stmt = con.createStatement();
				  stmt.executeUpdate(SQL);
				  stmt.close();
	 }catch(Exception ew){}
}
//-----------------Ham su ly su kien-------------------------
	public void actionPerformed(ActionEvent ae){
//-----------Ham su ly su kien click Edit----------
	if (ae.getActionCommand().equals("T\u1EA1o role"))
		{
					addUser add=new addUser("T\u1EA1o role",con);
					Connect.centerScreen(add);
					add.setVisible(true);
					executeStatement("select * from dba_roles");// where owner='"+user1+"'");// ORDER BY [dba_users].[First Name]");
					
				
		}
		if (ae.getActionCommand().equals("Cancel")){
		}
	//-------------Click su kien Delelete------------------	
		if (ae.getActionCommand().equals("Xóa"))
		{
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"Ch\u1ECDn role mu\u1ED1n xóa!","Thông báo",1);
			}else{
			
				int row=tableResult.getSelectedRow();
				String confirm = "Do you want to delete ?";
				switch (JOptionPane.showConfirmDialog(this,confirm))
					{
					case JOptionPane.YES_OPTION:{
						try{

						deleteUser();
					//	System.out.println(user1);
	
						//Xoa xong hien thi lai danh sach
					//	connect();
						executeStatement("select * from dba_roles");// where owner='"+user1+"'");// ORDER BY [dba_users].[First Name]");
						}catch(Exception ee){System.out.print(ee.toString());}
					}
					case JOptionPane.CANCEL_OPTION:break;
				}
			}
		}
}
//-----------------Ham chinh cua chuong trinh----------------
/*	public static void main(String[] args) 
	{
		myFrame =new JFrame("as");
		RolesOracle usermanager=new RolesOracle("SYSTEM");
		myFrame.getContentPane().add(usermanager);
		
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(800,500);
		myFrame.setVisible(true);
	}*/

}
class addUser extends JFrame implements ActionListener
{
	JLabel jlUsername;
	JTextField jtUsername;
	JButton jbAdd,jbCancel;
	JPanel jPanel;
	GridBagLayout gbl;
	GridBagConstraints gbcl;
	int stt;
	Statement stmt;
	String xsql;
	Connection con1;
	Container contenPane;
//--------Ham cau tu------------------------------

	public addUser(String title,Connection cons){
		super(title);
		setSize(380,100);
		
		con1=cons;
		jPanel=new JPanel();
		contenPane=getContentPane();
		contenPane.setLayout(new BorderLayout());
		
		JLabel thongtin=new JLabel("Thông tin t\u1EA1o nhóm quy\u1EC1n");
		thongtin.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 20));
		
		
		gbcl=new GridBagConstraints();
		jPanel.setLayout(gbl=new GridBagLayout());
		gbcl.anchor=GridBagConstraints.EAST;
		gbcl.fill=GridBagConstraints.BOTH;


		jlUsername=new JLabel("Rolename                      :");
		
			
		
		
		jtUsername=new JTextField(15);

		
		jbAdd=new JButton("\u0110\u1ED3ng ý");
		jbAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				add1(true);
			}
		});
		
		jbCancel=new JButton("B\u1ECF qua");
		jbCancel.addActionListener(this);
		
	
		add2(jPanel,thongtin,1,0,4,1);
	
		add2(jPanel,jlUsername,1,1,2,1);
		add2(jPanel,jtUsername,3,1,3,1);
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
//--------------Su ly su kien click Add----------------------------
public void add1(boolean update){
		String SQL;
		SQL = " create role " + jtUsername.getText()+"";

		try{
			Statement st=con1.createStatement();
			st.executeUpdate(SQL);
			JOptionPane.showMessageDialog(this,"T\u1EA1o role thành công",
                                    "Thông báo",1);
            st.close();                        
           this.dispose();
		}catch(Exception ec){
			System.out.println(ec.toString());
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
