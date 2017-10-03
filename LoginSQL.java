import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
public class LoginSQL extends JPanel implements ActionListener
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
	public LoginSQL(Connection con){
		cons=con;
		stSQL = new String("sp_helplogins");
		setLayout(new BorderLayout());
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();
		
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("NG\u01AF\u1EDCI DÙNG SQL SERVER");
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 20));
		jlObject.setIcon(new ImageIcon("icon\\TblSpcMan-lrg-t.gif         "));
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
		jbAdd=new JButton("T\u1EA1o ng\u01B0\u1EDDi dùng");
		jbEdit=new JButton("S\u1EEDa");
		jbDel=new JButton("Xóa");
	
		jbAdd.addActionListener(this);
		jbEdit.addActionListener(this);
		jbDel.addActionListener(this);

		add(jpOption,jbAdd,1,1,2,1);
	//	add(jpOption,jbEdit,3,1,2,1);
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
public void deleteUser(){
	int row=tableResult.getSelectedRow();
	ResultSet rs,rs12;
	String sttb= (String)(tableResult.getValueAt(row,0));
	
	String SQL="use "+(String)(tableResult.getValueAt(row,2))+" exec sp_droplogin '" +(String)(tableResult.getValueAt(row,0)) + "'";
	System.out.println(SQL);
//	connect();
	try{
		 	Statement  stmt1=cons.createStatement();
			rs12=stmt1.executeQuery("sp_databases");
			while(rs12.next()){
	  			String stb=rs12.getString("DATABASE_NAME");
				String xsql="use "+stb+" exec sp_helpuser";
		 		Statement  stmt11=cons.createStatement();
	  			rs=stmt11.executeQuery(xsql);
				while (rs.next()) {
					String st= rs.getString("UserName");
					String st1= rs.getString("LoginName");
					Statement  stmt12=cons.createStatement();
					if(sttb.equals(st1)){
			    		String SQL1="use "+stb+" exec sp_dropuser "+st+"";
			    	//	connect();
			    		stmt12.executeUpdate(SQL1);
		    		}
	    		}
    		}
    }catch(SQLException e){System.out.println(e);}
	 try{
		Statement stmt = cons.createStatement();
				  stmt.executeUpdate(SQL);
			//	  cons.close();
			//	  stmt.close();
				  JOptionPane.showMessageDialog(this, "Xóa thành công",
                                      "Thông báo", 1);
	 }catch(SQLException ew){System.out.println("biet le");}
}
//-----------------Ham su ly su kien-------------------------
	public void actionPerformed(ActionEvent ae){
//-----------Ham su ly su kien click Edit----------
	String tablename, username;
	if (ae.getActionCommand().equals("S\u1EEDa"))
		{
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"You must select in record you want edit !");
			}else{
					int row=tableResult.getSelectedRow();
				
					String st=(String)(tableResult.getValueAt(row,0));
				
			}
		}
	
	//-------------Click su kien Delelete------------------	
		if (ae.getActionCommand().equals("Xóa"))
		{
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"Ch\u1ECDn ng\u01B0\u1EDDi dùng mu\u1ED1n xóa !");
			}else{
			
				int row=tableResult.getSelectedRow();
				String confirm = "B\u1EA1n có mu\u1ED1n xóa ?";
				switch (JOptionPane.showConfirmDialog(this,confirm,"Thông báo",1))
					{
					case JOptionPane.YES_OPTION:{
						try{
						deleteUser();	
						//Xoa xong hien thi lai danh sach
					//	connect();
						String str="sp_helplogins";
						executeStatement(str);// ORDER BY [dba_users].[First Name]");
						}catch(Exception ee){System.out.print(ee.toString());}
					}
					case JOptionPane.CANCEL_OPTION:break;
				}
			}
		}
	
		if (ae.getActionCommand().equals("T\u1EA1o ng\u01B0\u1EDDi dùng"))
		{
			NewLoginSQL gd=new NewLoginSQL("T\u1EA1o ng\u01B0\u1EDDi dùng",cons);
			Connect.centerScreen(gd);
			gd.setVisible(true);
			try{
		//	connect();
			String str="sp_helplogins";
			executeStatement(str);
			}catch(Exception e){}
		}
	}
//-----------------Ham chinh cua chuong trinh----------------
/*	public static void main(String[] args) 
	{

		myFrame =new JFrame("as");
		LoginSQL usermanager=new LoginSQL();
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
