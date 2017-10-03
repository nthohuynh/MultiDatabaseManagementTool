import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
import javax.swing.text.*;

public class TablespaceOracle extends JPanel implements   ActionListener
{
//-----------------Khai bao cac bien dung tr2ong chuong trinh--
	JTable jtTable;
	String stSQL=new String("SELECT TABLESPACE_NAME,  BLOCK_SIZE,  INITIAL_EXTENT   FROM DBA_TABLESPACES ORDER BY TABLESPACE_NAME");
	JLabel jlTablespacename,jlPath, jlSize;
	JTextArea jtaDe;
	JTextField jtPath, jtTablespacename, jtSize;
	Container contenPane;
	JPanel jpFind,jpTable;
	GridBagLayout gb;
	GridBagConstraints gbc;
	JButton jbAdd,jbEdit,jbDel,jbOK, jbCancel;
	JScrollPane jscPane,resultPane;
	Connection con;
	JTable tableResult;
	JCheckBox jchbTemp, jchbDefault;
	Statement stmt;
	String st,st1,st2;
	static JFrame myFrame;
	static Connection con2;
	boolean them;
	//-----------------Ham cau tu-------------------------------
	public TablespaceOracle(Connection cons){
		con=cons;

		
		
		setLayout(new BorderLayout());
				
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();
		
	
		JPanel jpFind=new JPanel();
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();
		jpFind.setLayout(gb);
		
		JLabel jlObject =new JLabel("Không gian b\u1EA3ng trong Oracle");
		jlObject.setIcon(new ImageIcon("icon\\vth32.gif"));
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 20));
	
		jlTablespacename=new JLabel("Tên không gian b\u1EA3ng:  ");
		jlPath=new JLabel("\u0110\u01B0\u1EDDng d\u1EABn : ");
		jlSize =new JLabel("Kích th\u01B0\u1EDBc:");
		
		jtTablespacename=new JTextField(20);
		jtTablespacename.setEnabled(false);
		jtPath=new JTextField(20);
		jtPath.setEnabled(false);
		jtPath.setText("C:\\ORACLE\\ORADATA\\dbTHO\\");
		jtSize=new JTextField(20);
		jtSize.setDocument(new DigitsDocument());
		jtSize.setEnabled(false);


		
		jbAdd=new JButton("T\u1EA1o m\u1EDBi");
		jbEdit=new JButton("S\u1EEDa");
		jbDel=new JButton("Xóa");
		jbOK=new JButton("L\u01B0u");
		jbCancel=new JButton("B\u1ECF qua");
		jbAdd.addActionListener(this);
		jbEdit.addActionListener(this);
		jbDel.addActionListener(this);
		jbOK.addActionListener(this);
		jbCancel.addActionListener(this);
		jbOK.setEnabled(false);
		jbCancel.setEnabled(false);

		jchbTemp= new JCheckBox("Không gian b\u1EA3ng t\u1EA1m");
		jchbDefault= new JCheckBox("Không gian b\u1EA3ng m\u1EB7c \u0111\u1ECBnh");
		jchbDefault.setEnabled(false);
		
		jchbTemp.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				boolean check= (e.getStateChange()==e.SELECTED);
				jchbDefault.setEnabled(check);

			}
		});
		
		add(jpFind,jlObject,3,1,5,1);

		add(jpFind,jlTablespacename,1,2,1,1);
		add(jpFind,jlPath,1,3,1,1);
		add(jpFind,jlSize,1,4,1,1);
		
		add(jpFind,jtTablespacename,2,2,7,1);
		add(jpFind,jtPath,2,3,7,1);
		add(jpFind,jtSize,2,4,7,1);
		
		add(jpFind,jchbTemp,2,5,3,1);
		add(jpFind,jchbDefault,5,5,2,1);

		
		add(jpFind,jbAdd,2,6,1,1);
		add(jpFind,jbEdit,3,6,1,1);
		add(jpFind,jbDel,4,6,1,1);
		add(jpFind,jbOK,5,6,1,1);
		add(jpFind,jbCancel,6,6,1,1);
		
		add(jpFind,"North");

		jpTable=new JPanel();
		int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		resultPane=new JScrollPane(v,h);
		jpTable.setLayout(new BorderLayout());
		jpTable.add(resultPane,BorderLayout.CENTER);
		
		if(con!=null)
		executeStatement(stSQL);
	
		add(jpTable,"Center");


	}
	
 	 /*-----thu tuc chi duoc nhap so vao truong jtSize---------*/ 
class DigitsDocument extends PlainDocument 
{
	public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException 
	{
		if (str == null) 
		{
			return;
		}
		char[] addedFigures = str.toCharArray();
		char c;
		for (int i = addedFigures.length; i > 0; i--) 
		{
			c = addedFigures[i-1];
			if(Character.isDigit(c))
			{
				//System.out.println("Inserting digit..." + c);
				super.insertString(offs, new String(new Character(c).toString()), a);
			}
		}
       //super.insertString(offs, "", a);
	}
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
		//	tableResult.setSelectionBackground(new Color(0,100,100));
		//	tableResult.setGridColor(new Color(0,0,150));
			resultPane.setViewportView(tableResult);
			resultPane.setBorder(BorderFactory.createLineBorder(Color.lightGray,6));
			}
		 else {
			int updateCount=stmt.getUpdateCount();
			JOptionPane.showMessageDialog(this,"Da cap nhat "+ updateCount+" ban ghi");
		}
		} catch(Exception e){  System.out.println("Error " + e); }
	}
//---------------Ham addPhone------------------------------------------


void create(String tenKGB,String dd,String kichthuoc){
    
    if (!jchbTemp.isSelected()){
      st="create tablespace " + tenKGB + " datafile '"+dd+tenKGB+".dbf'";
      st=st + " size "+ kichthuoc +"M segment space management auto";
    }else {
      st="create temporary tablespace "+tenKGB+" tempfile '"+dd+tenKGB+".dbf'";
      st=st+"size " + kichthuoc + "M";
      if (jchbDefault.isSelected())
        st1="ALTER DATABASE DEFAULT TEMPORARY TABLESPACE "+tenKGB;
    }
    try{
      stmt=con.createStatement();
      stmt.executeQuery(st);
      if (jchbDefault.isSelected())
        stmt.executeQuery(st1);
      JOptionPane.showMessageDialog(this,"\u0110ã t\u1EA1o không gian b\u1EA3ng","Thông báo",1);
    }catch(Exception ex){
      JOptionPane.showMessageDialog(this,ex+"\nDon't create","Mesage",1);
    }
  }
 void alter(String tenKGB,String dd,String kichthuoc){
    st="alter database datafile '"+dd+tenKGB+".dbf'";
    st=st + " resize "+ kichthuoc +"M";
    try{
      stmt=con.createStatement();
      stmt.executeQuery(st);
      JOptionPane.showMessageDialog(this,"S\u1EEDa thành công","Thông báo",1);
    }catch(Exception ex){
      JOptionPane.showMessageDialog(this,ex+"\nKhông th\u1EC3 s\u1EEDa",
                                    "Thông báo",1);
    }

  }
//--------------Ham xoa user -----------------------------
public void deleteUser(){
	int row=tableResult.getSelectedRow();
	String SQL="drop tablespace " +(String)(tableResult.getValueAt(row,0)) + "";
//	connect();
	 try{
		Statement stmt = con.createStatement();
				  stmt.executeUpdate(SQL);
				  stmt.close();
	 }catch(Exception ew){}
}
//-----------------Ham su ly su kien-------------------------
	public void actionPerformed(ActionEvent ae){
//-----------Ham su ly su kien click Edit----------
	if (ae.getActionCommand().equals("S\u1EEDa"))
		{
			
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"Ch\u1ECDn không gian b\u1EA3ng mu\u1ED1n s\u1EEDa!","Thông báo",1);
			}else{
					jbAdd.setEnabled(false);
					jbEdit.setEnabled(false);
					jbDel.setEnabled(false);
					jbOK.setEnabled(true);
					jbCancel.setEnabled(true);
					
					them=false;
					jtTablespacename.setEnabled(true);
					jtPath.setEnabled(true);
					jtSize.setEnabled(true);

					int row=tableResult.getSelectedRow();
					jtTablespacename.setText((String)(tableResult.getValueAt(row,0)));

			}
			

		}
	if (ae.getActionCommand().equals("L\u01B0u"))
		{
			 if (them)
				   create(jtTablespacename.getText(),jtPath.getText(),jtSize.getText());
			else 	alter(jtTablespacename.getText(),jtPath.getText(),jtSize.getText());
					
					/*add1=new editUser("S\u1EEDa không gian b\u1EA3ng",con);
					add1.getData();
					Connect.centerScreen(add1);
					add1.show();
					//connect();*/
					
					stSQL = "SELECT TABLESPACE_NAME,  BLOCK_SIZE,  INITIAL_EXTENT   FROM DBA_TABLESPACES ORDER BY TABLESPACE_NAME";
					executeStatement(stSQL);
					jtTablespacename.setText("");
					jtSize.setText("");
					jtTablespacename.setEnabled(false);
					jtPath.setEnabled(false);
					jtSize.setEnabled(false);
				
					jbAdd.setEnabled(true);
					jbEdit.setEnabled(true);
					jbDel.setEnabled(true);
					jbOK.setEnabled(false);
					jbCancel.setEnabled(false);

			
		}
		
	//-------------Click su kien Delelete------------------	
		if (ae.getActionCommand().equals("Xóa"))
		{
			if (tableResult.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(this,"Ch\u1ECDn không gian b\u1EA3ng mu\u1ED1n xóa!","Thông báo",1);
			}else{
			
				int row=tableResult.getSelectedRow();
				String confirm = "B\u1EA1n có mu\u1ED1n xóa??";
				switch (JOptionPane.showConfirmDialog(this,confirm))
					{
					case JOptionPane.YES_OPTION:{
						try{
						deleteUser();	
						//Xoa xong hien thi lai danh sach
						//connect();
						executeStatement("SELECT TABLESPACE_NAME,  BLOCK_SIZE,  INITIAL_EXTENT   FROM DBA_TABLESPACES ORDER BY TABLESPACE_NAME");// ORDER BY [dba_users].[First Name]");
						}catch(Exception ee){System.out.print(ee.toString());}
					}
					case JOptionPane.CANCEL_OPTION:break;
				}
			}
		}
	//-----------Su ly su kien addUser-----------
		if (ae.getActionCommand().equals("T\u1EA1o m\u1EDBi"))
		{
			jbAdd.setEnabled(false);
			jbEdit.setEnabled(false);
			jbDel.setEnabled(false);
			jbOK.setEnabled(true);
			jbCancel.setEnabled(true);

			jtTablespacename.setEnabled(true);
			jtPath.setEnabled(true);
			jtSize.setEnabled(true);
			them=true;
		//	create(st11,st12,st13);
		//	connect();
		//	executeStatement("SELECT TABLESPACE_NAME,  BLOCK_SIZE,  INITIAL_EXTENT   FROM DBA_TABLESPACES ORDER BY TABLESPACE_NAME");// ORDER BY [dba_users].[First Name]");
		}
		if (ae.getActionCommand().equals("B\u1ECF qua"))
		{
			jbAdd.setEnabled(true);
			jbEdit.setEnabled(true);
			jbDel.setEnabled(true);
			jbOK.setEnabled(false);
			jbCancel.setEnabled(false);

			jtTablespacename.setEnabled(false);
			jtPath.setEnabled(false);
			jtSize.setEnabled(false);
		}
	//-----------Su ly su kien click find---------------------
	
	
	}
//-----------------Ham chinh cua chuong trinh----------------
	public static void main(String[] args) 
	{
		
		myFrame =new JFrame("as");
		try{
     	 	Class.forName("oracle.jdbc.driver.OracleDriver");
			con2=DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:dbTHO", "system", "abc123");
			//System.out.print("OK");
		}catch(Exception se){
			System.out.println(se.toString());
		}
		TablespaceOracle usermanager=new TablespaceOracle(con2);
		myFrame.getContentPane().add(usermanager);
		
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(800,500);
		myFrame.setVisible(true);
}
}