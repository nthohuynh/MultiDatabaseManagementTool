import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
public class RoleDBSQL extends JPanel implements ActionListener
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
	public RoleDBSQL(String username, Connection con){

		cons=con;
		stSQL = new String("USE "+username+" EXEC sp_helprole");
		us=username;

	//	setJMenuBar(jmnBar);
	//	contenPane=getContentPane();
	//	contenPane.setLayout(new BorderLayout());
		setLayout(new BorderLayout());
		
		
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();
		
		
	
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("Roles c\u1EE7a c\u01A1 s\u1EDF d\u1EEF li\u1EC7u: "+us );
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 25));
		jlObject.setIcon(new ImageIcon("icon\\propagate_proc_big.gif"));
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
		jbAdd=new JButton("T\u1EA1o m\u1EDBi role");
		jbDel=new JButton("X�a");
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
public void deleteRole(){
	int row=tableResult.getSelectedRow();
	String SQL="use "+us+" exec sp_droprole @rolename = \'" +(String)(tableResult.getValueAt(row,0)) +"\'";
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
		if (ae.getActionCommand().equals("X�a"))
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
						executeStatement("USE "+us+" EXEC sp_helprole");
						}catch(Exception ee){System.out.print(ee.toString());}
					}
					case JOptionPane.CANCEL_OPTION:break;
				}
			}
		}
		
	//-----------Su ly su kien addUser-----------
		if (ae.getActionCommand().equals("T\u1EA1o m\u1EDBi role"))
		{
					Createrolesql add=new Createrolesql("T\u1EA1o nh�m quy\u1EC1n",us,cons);
					Connect.centerScreen(add);
		}
	}
}

class Createrolesql extends JFrame implements ActionListener
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
	String db_name;
	Connection con1;
	Container contenPane;
//--------Ham cau tu------------------------------

	public Createrolesql(String title, String db,Connection cons){
		super(title);
		setSize(380,100);
		con1=cons;
		jPanel=new JPanel();
		
		contenPane=getContentPane();
		contenPane.setLayout(new BorderLayout());
		
		db_name=db;
		gbcl=new GridBagConstraints();
		jPanel.setLayout(gbl=new GridBagLayout());
		gbcl.anchor=GridBagConstraints.EAST;
		gbcl.fill=GridBagConstraints.BOTH;


		jlUsername=new JLabel("Rolename                      :");
		
			
		
		
		jtUsername=new JTextField(15);

		
		jbAdd=new JButton("\u0110\u1ED3ng �");
		jbAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				add1(true);
			}
		});
		
		jbCancel=new JButton("B\u1ECF qua");
		jbCancel.addActionListener(this);
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
		SQL = "use "+db_name+" exec  sp_addrole '" + jtUsername.getText()+"'";

		try{
			Statement st=con1.createStatement();
		
			st.executeUpdate(SQL);
			JOptionPane.showMessageDialog(this,"T\u1EA1o role th�nh c�ng",
                                    "Th�ng b�o",1);
            st.close();
            this.dispose();
		}catch(Exception ec){
			System.out.println(ec.toString());
	}
}
//--------------Ham addComponent------------------------------------
	public void add2(JPanel jPanel,Component c, int x, int y, int nx, int ny){
		gbcl.gridx=x;
		gbcl.gridy=y;
		gbcl.gridwidth=nx;
		gbcl.gridheight=ny;
		gbl.setConstraints(c,gbcl);
		jPanel.add(c,gbcl);
	}
}
