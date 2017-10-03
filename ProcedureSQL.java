import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
public class ProcedureSQL extends JPanel implements ActionListener
{
//-----------------Khai bao cac bien dung tr2ong chuong trinh--
	static JFrame myFrame;
	JTable jtTable;
	String stConditon="",stFindWhat="First Name";
	String stSQL;
	JLabel jlFindWhat,jlCondition;
	JTextArea jtaDe;
	JTextField jtfCondition;
	JComboBox jcbFindWhat;
	Container contenPane;
	JMenuBar jmnBar;
	JPanel jpFind,jpTable,jpOption;
	GridBagLayout gb;
	GridBagConstraints gbc;
	JButton jbFind,jbAdd,jbEdit,jbDel, jbCancel;
	JScrollPane jscPane,resultPane;
	Connection cons;
	JTable tableResult;
	String username,us;
	ResultSet rss;
//-----------------Ham cau tu-------------------------------
	public ProcedureSQL(String username,Connection con){

		cons=con;
		stSQL = new String("USE "+username+" select name, id, crdate from sysobjects where type='P'");
		us=username;

	//	setJMenuBar(jmnBar);
	//	contenPane=getContentPane();
	//	contenPane.setLayout(new BorderLayout());
		setLayout(new BorderLayout());
		
		
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();
		
		
	
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("Procedure t\u1EA1i c\u01A1 s\u1EDF d\u1EEF li\u1EC7u: "+us );
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 25));
		jlObject.setIcon(new ImageIcon("icon\\webcache32.gif"));
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
		jbDel=new JButton("Xóa");
		jbAdd.addActionListener(this);
		jbDel.addActionListener(this);

		add(jpOption,jbAdd,1,1,2,1);
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
public void deletePro(){
	int row=tableResult.getSelectedRow();
	String SQL="use "+us+" drop procedure " +(String)(tableResult.getValueAt(row,0)) + "";
	
//	connect();
	 try{
		Statement stmt = cons.createStatement();
				  stmt.executeUpdate(SQL);
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
				JOptionPane.showMessageDialog(this,"Ch\u1ECDn view mu\u1ED1n xóa!","Thông báo",1);
			}else{
			
				int row=tableResult.getSelectedRow();
				String confirm = "B\u1EA1n có mu\u1ED1n xóa??";
				switch (JOptionPane.showConfirmDialog(this,confirm,"Thông báo",1))
					{
					case JOptionPane.YES_OPTION:{
						try{
						deletePro();	
						//Xoa xong hien thi lai danh sach
					//	connect();
						executeStatement("USE "+us+" select name,id,crdate from sysobjects where type='P'");
						}catch(Exception ee){System.out.print(ee.toString());}
					}
					case JOptionPane.CANCEL_OPTION:break;
				}
			}
		}
	//-----------Su ly su kien addUser-----------
		if (ae.getActionCommand().equals("T\u1EA1o m\u1EDBi"))
		{
				CreateProSQL gd=new CreateProSQL("T\u1EA1o m\u1EDBi procedure",us,cons);
				Connect.centerScreen(gd);
				gd.setVisible(true);
		}
	
	
	}
//-----------------Ham chinh cua chuong trinh----------------
/*	public static void main(String[] args) 
	{

		myFrame =new JFrame("as");
		ViewManagerSQL usermanager=new ViewManagerSQL("dbBanhang");
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
