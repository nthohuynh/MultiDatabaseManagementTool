import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
public class DisplayDatabaseSQL extends JPanel implements ActionListener
{
//-----------------Khai bao cac bien dung tr2ong chuong trinh--
	static JFrame myFrame;
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
	public DisplayDatabaseSQL(Connection con){
		cons=con;
		stSQL = new String("sp_databases");
		setLayout(new BorderLayout());
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();
		
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("C\u01A0 S\u1EDE D\u1EEE LI\u1EC6U TRONG SQL SERVER");
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
		jlObject.setIcon(new ImageIcon("icon\\apply_proc_big.gif         "));
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
		jbAdd=new JButton("T\u1EA1o CSDL");
		jbEdit=new JButton("S\u1EEDa CSDL");
		jbDel=new JButton("Xóa CSDL");
	
		jbAdd.addActionListener(this);
		jbEdit.addActionListener(this);
		jbDel.addActionListener(this);

		add(jpOption,jbAdd,1,1,2,1);
		add(jpOption,jbEdit,3,1,2,1);
		add(jpOption,jbDel,5,1,2,1);
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
public void deleteUser(String dbname){
	int row=tableResult.getSelectedRow();
	String SQL="drop database " +dbname+ "";
	
	if(cons!=null)
	 try{
		Statement stmt = cons.createStatement();
				  stmt.executeUpdate(SQL);
				  stmt.close();
				  JOptionPane.showMessageDialog(this, "Deleted",
                                      "Message", 1);
	 }catch(Exception ew){	
	 JOptionPane.showMessageDialog(this,ew+ "\nCan't delete");
	}
}
//-----------------Ham su ly su kien-------------------------
	public void actionPerformed(ActionEvent ae){
//-----------Ham su ly su kien click Edit----------
	String tablename, username;
	if (ae.getActionCommand().equals("S\u1EEDa CSDL"))
		{
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"You must select in record you want edit !");
			}else{
					int row=tableResult.getSelectedRow();
				
					String st=(String)(tableResult.getValueAt(row,0));
					System.out.println(st);
					CreateEditDatabaseSQL gd=new CreateEditDatabaseSQL("S\u1EEDa c\u01A1 s\u1EDF d\u1EEF li\u1EC7u",st,true,cons);
					Connect.centerScreen(gd);
					gd.setVisible(true);
			}
		}
	
	

	//-------------Click su kien Delelete------------------	
		if (ae.getActionCommand().equals("Xóa CSDL"))
		{
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"B\u1EA1n ph\u1EA3i ch\u1ECDn m\u1ED9t b\u1EA3ng ghi mu\u1ED1n xóa !");
			}else{
			
				int row=tableResult.getSelectedRow();
				String confirm = "B\u1EA1n có mu\u1ED1n xóa?";
				switch (JOptionPane.showConfirmDialog(this,confirm))
					{
					case JOptionPane.YES_OPTION:{
						try{
						deleteUser((String)(tableResult.getValueAt(row,0)));	
						//Xoa xong hien thi lai danh sach
						//connect();
						String str="sp_databases";
						executeStatement(str);// ORDER BY [dba_users].[First Name]");
						}catch(Exception ee){System.out.print(ee.toString());}
					}
					case JOptionPane.CANCEL_OPTION:break;
				}
			}
		}
	
		if (ae.getActionCommand().equals("T\u1EA1o CSDL"))
		{
			CreateEditDatabaseSQL gd=new CreateEditDatabaseSQL("T\u1EA1o m\u1EDBi c\u01A1 s\u1EDF d\u1EEF li\u1EC7u","",false,cons);
			Connect.centerScreen(gd);
			gd.setVisible(true);
			//connect();
			String str="sp_databases";
			executeStatement(str);
		}
	//-----------Su ly su kien click find---------------------
	
	
	}





//-----------------Ham chinh cua chuong trinh----------------
/*	public static void main(String[] args) 
	{

		myFrame =new JFrame("as");
		DisplayDatabaseSQL usermanager=new DisplayDatabaseSQL();
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
