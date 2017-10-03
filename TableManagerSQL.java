
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
public class TableManagerSQL extends JPanel implements ActionListener
{
//-----------------Khai bao cac bien dung tr2ong chuong trinh--
	static JFrame myFrame,myFrame1;
	JTable jtTable;
	String stSQL;
	JTextField jtfCondition;
	Container contenPane;
	JPanel jpTable,jpOption;
	GridBagLayout gb;
	GridBagConstraints gbc;
	JButton jbAdd,jbEdit,jbDel, jbCancel;
	JScrollPane jscPane,resultPane;
	Connection cons;
	JTable tableResult;
	String username,us;
	ResultSet rss;
//-----------------Ham cau tu-------------------------------
	public TableManagerSQL(String username, Connection con){
		cons=con;
		us=username;
		stSQL = new String("USE "+us+" EXEC sp_tables @TABLE_TYPE = \"'TABLE','SYSTEM TABLE'\"" );
	

		setLayout(new BorderLayout());
		
		
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();
		
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("B\u1EA3ng t\u1EA1i c\u01A1 s\u1EDF d\u1EEF li\u1EC7u: "+us );
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 25));
		jlObject.setIcon(new ImageIcon("icon\\collection.gif         "));
		
		jPanel.add(jlObject);

		
		jpTable=new JPanel();
		int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		resultPane=new JScrollPane(v,h);
		jpTable.setLayout(new BorderLayout());
		jpTable.add(resultPane,BorderLayout.CENTER);
	//	contenPane.add(jpTable,BorderLayout.CENTER);
		add(jpTable,"Center");
		
		if(cons!=null)
		executeStatement(stSQL);

		jpOption=new JPanel();
		jbAdd=new JButton("T\u1EA1o m\u1EDBi");
		jbEdit=new JButton("S\u1EEDa");
		jbDel=new JButton("Xóa");
	//	jbCancel=new JButton("Import Data");
		jbAdd.addActionListener(this);
		jbEdit.addActionListener(this);
		jbDel.addActionListener(this);
	//	jbCancel.addActionListener(this);

		add(jpOption,jbAdd,1,1,2,1);
		add(jpOption,jbEdit,3,1,2,1);
		add(jpOption,jbDel,5,1,2,1);
	//	add(jpOption,jbCancel,8,1,2,1);
		add(jpOption,"South");
		add(jPanel, "North");


	}

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
public void deleteUser(){
	int row=tableResult.getSelectedRow();
	String SQL="use "+us+" drop table " +(String)(tableResult.getValueAt(row,2)) + "";
//	connect();
	 try{
	 	
		Statement stmt = cons.createStatement();
				  stmt.executeUpdate(SQL);
	 }catch(Exception ew){
	 	System.out.println(ew);
	 	JOptionPane.showMessageDialog(this,ew);
	 	}
}
//-----------------Ham su ly su kien-------------------------
	public void actionPerformed(ActionEvent ae){
//-----------Ham su ly su kien click Edit----------
	String tablename, username;
	if (ae.getActionCommand().equals("S\u1EEDa"))
		{
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"Ch\u1ECDn b\u1EA3ng mu\u1ED1n s\u1EEDa!","Thông báo",1);
			}else{
					int row=tableResult.getSelectedRow();

					myFrame1 =new JFrame("S\u1EEDa b\u1EA3ng "+(String)(tableResult.getValueAt(row,2)));
					EditTableSQL usermanager=new EditTableSQL(us,(String)(tableResult.getValueAt(row,2)),cons);
					myFrame1.getContentPane().add(usermanager);
					myFrame1.setSize(400,300);
					myFrame1.setVisible(true);
					Connect.centerScreen(myFrame1);				
					
			}
		}
		//-------------Click su kien Delelete------------------	
		if (ae.getActionCommand().equals("Xóa"))
		{
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"Ch\u1ECDn b\u1EA3ng mu\u1ED1n xóa!","Thông báo",1);
			}else{
			
				int row=tableResult.getSelectedRow();
				String confirm = "B\u1EA1n có mu\u1ED1n xóa??";
				switch (JOptionPane.showConfirmDialog(this,confirm))
					{
					case JOptionPane.YES_OPTION:{
						try{
						deleteUser();	
						//Xoa xong hien thi lai danh sach
					//	connect();
						String str="USE "+us+" EXEC sp_tables @TABLE_TYPE = \"'TABLE','SYSTEM TABLE'\"" ;
						executeStatement(str);// ORDER BY [dba_users].[First Name]");
						}catch(Exception ee){System.out.print(ee.toString());}
					}
					case JOptionPane.CANCEL_OPTION:break;
				}
			}
		}
		//-----------Su ly su kien addUser-----------
		if (ae.getActionCommand().equals("T\u1EA1o m\u1EDBi"))
		{
			CreateTableSQL gd=new CreateTableSQL("T\u1EA1o m\u1EDBi b\u1EA3ng",us,cons);
			Connect.centerScreen(gd);
			gd.setVisible(true);
			//executeStatement("SELECT OWNER,  TABLE_NAME,  TABLESPACE_NAME,  CLUSTER_NAME  FROM dba_tables where owner='"+ us +"'");
		}
	//-----------Su ly su kien click find---------------------
	
	
	}

/*	public static void main(String[] args) 
	{

		myFrame =new JFrame("as");
		try{
			
 			DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
    		cons=DriverManager.getConnection ( "jdbc:microsoft:sqlserver://BKDN-BMNJMC9ZPY:1433","sa","abc123");
         }catch(Exception e) {     }
	
		TableManagerSQL usermanager=new TableManagerSQL("master",cons);
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
