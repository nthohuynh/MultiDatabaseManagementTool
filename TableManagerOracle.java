import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
public class TableManagerOracle extends JPanel implements ActionListener
{
	static JFrame myFrame;
	JTable jtTable;
	String stSQL;
	JPanel jpTable,jpOption;
	GridBagLayout gb;
	GridBagConstraints gbc;
	JButton jbAdd,jbEdit,jbDel;
	JScrollPane jscPane,resultPane;
	Connection con;
	JTable tableResult;
	String username,us;
	public TableManagerOracle(String username, Connection cons){
		
		con=cons;
		stSQL = new String("SELECT  OWNER, TABLE_NAME,  TABLESPACE_NAME,  CLUSTER_NAME  FROM dba_tables where owner='"+username+"'");
		us=username;
	
		setLayout(new BorderLayout());
		
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();	
			
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("B\u1EA3ng t\u1EA1i schema "+us );
		jlObject.setIcon(new ImageIcon("icon\\propagate_proc_big.gif"));
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 20));
		jPanel.add(jlObject);
		
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
		jbAdd=new JButton("T\u1EA1o m\u1EDBi");
		jbEdit=new JButton("S\u1EEDa");
		jbDel=new JButton("Xóa");
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
	String SQL="drop table " +tableResult.getValueAt(row,0)+"."+(String)(tableResult.getValueAt(row,1)) + "";
	
	 try{
		Statement stmt = con.createStatement();
				  stmt.executeUpdate(SQL);
		JOptionPane.showMessageDialog(this,"Xóa thành công!","Thông báo",1);

	 }catch(Exception ew){	
	 JOptionPane.showMessageDialog(this,ew+"\nKhông th\u1EC3 xóa!","Thông báo",1);
	}
}
public void actionPerformed(ActionEvent ae){
		String tablename, username;
		if (ae.getActionCommand().equals("S\u1EEDa")){
				if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"Ch\u1ECDn b\u1EA3ng mu\u1ED1n s\u1EEDa!","Thông báo",1);
			}else{
	
			int row=tableResult.getSelectedRow();
			
		 	//	st = rs1.getString("UserName");
		 	username = (String)tableResult.getValueAt(row,0);
			tablename= (String)tableResult.getValueAt(row,1);
			
			myFrame =new JFrame("S\u1EEDa b\u1EA3ng");
			EditTable usermanager=new EditTable(username,tablename,con);
			myFrame.getContentPane().add(usermanager);
			
			myFrame.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
				myFrame.dispose();
				}
				});
			myFrame.setSize(500,300);
			Connect.centerScreen(myFrame);
			myFrame.setVisible(true);
    		}
		}
		if (ae.getActionCommand().equals("Xóa"))
		{
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"Ch\u1ECDn b\u1EA3ng mu\u1ED1n xóa!","Thông báo",1);
			}else{
			
				int row=tableResult.getSelectedRow();
				String confirm = "B\u1EA1n có mu\u1ED1n xóa??";
				switch (JOptionPane.showConfirmDialog(this,confirm,"Thông báo",1))
					{
					case JOptionPane.YES_OPTION:{
						try{
						deleteUser();	
						//Xoa xong hien thi lai danh sach
						
						String str="SELECT  OWNER, TABLE_NAME,  TABLESPACE_NAME,  CLUSTER_NAME  FROM dba_tables where owner='"+us+"'";
						executeStatement(str);// ORDER BY [dba_users].[First Name]");
					
						}catch(Exception ee){
							System.out.print(ee.toString());
						}
					}
					case JOptionPane.CANCEL_OPTION:break;
				}
			}
		}
		if (ae.getActionCommand().equals("T\u1EA1o m\u1EDBi"))
		{
			AddTable gd=new AddTable("T\u1EA1o m\u1EDBi b\u1EA3ng",us, con);
			Connect.centerScreen(gd);
			gd.setVisible(true);
			//executeStatement("SELECT OWNER,  TABLE_NAME,  TABLESPACE_NAME,  CLUSTER_NAME  FROM dba_tables where owner='"+ us +"'");
		}
}

/*public static void main(String[] args) 
	{

		myFrame =new JFrame("as");
		TableManagerOracle usermanager=new TableManagerOracle("SYSTEM");
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
