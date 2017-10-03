
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
public class DisplayDataOracle extends JPanel implements ActionListener
{
//-----------------Khai bao cac bien dung tr2ong chuong trinh--
	static JFrame myFrame,myFrame1;
	JTable jtTable;
	public String stSQL;
	JTextField jtfCondition;
	Container contenPane;
	JPanel jpTable,jpOption;
	GridBagLayout gb;
	GridBagConstraints gbc;
	JButton jbAdd,jbEdit,jbDel, jbCancel;
	JScrollPane jscPane,resultPane;
	static Connection con;
	JTable tableResult;
	String username,db,tb;
	ResultSet rss;
	boolean them=true;
	Vector vData;
	Vector vTitle;
	Statement stmt;
//-----------------Ham cau tu-------------------------------
	public DisplayDataOracle(String dbname, String table,Connection cons){
		con=cons;
		db=dbname;
		tb=table;
		stSQL = new String("select * from "+db+"."+table+"");
	

		setLayout(new BorderLayout());
		
		
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();
		
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("D\u1EEE LI\u1EC6U C\u1EE6A B\u1EA2NG: "+db+"."+table+"");
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 24));
		jlObject.setIcon(new ImageIcon("icon\\animate1.gif         "));
		jPanel.add(jlObject);

		
		jpTable=new JPanel();
		int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		resultPane=new JScrollPane(v,h);
		jpTable.setLayout(new BorderLayout());
		jpTable.add(resultPane,BorderLayout.CENTER);
	//	contenPane.add(jpTable,BorderLayout.CENTER);
		add(jpTable,"Center");
		
		if(con!=null)
		executeStatement(stSQL);

		jpOption=new JPanel();
		jbAdd=new JButton("Nh\u1EADp d\u1EEF li\u1EC7u");
	//	jbEdit=new JButton("Save");
		jbDel=new JButton("Xóa d\u1EEF li\u1EC7u");
		
		jbAdd.addActionListener(this);
	//	jbEdit.addActionListener(this);
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
		stmt = con.createStatement();
		if(stmt.execute(sql)){
			ResultSet rs=stmt.getResultSet();
			//lay ten cac truong
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			vTitle=new Vector(numberOfColumns,0);
			for(int j=1; j<=numberOfColumns;j++) {
				vTitle.add(rsmd.getColumnLabel(j));
			}
			// dua du lieu vao vector vData
			vData=new Vector(10,12);
			while(rs.next()) {
				Vector row=new Vector(numberOfColumns,0);
				for(int i=1; i<=numberOfColumns;i++){ 
					row.add(rs.getObject(i));
				//	System.out.println(row);
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
/*public void nhap(){
	connect();
	String tt="";
	int j=vTitle.size();
	for(int i=0;i<vTitle.size()-1;i++){
		tt=tt+vTitle.elementAt(i)+",";
	}
	tt=tt+vTitle.elementAt(vTitle.size()-1);
	
	for(int h=0; h<vData.size();h++){	
		String gt="";
		for(int i=0;i<vTitle.size()-1;i++){
			String str=(String)tableResult.getValueAt(h,i);
			gt=gt+"'"+str+"'"+",";
		}
		gt=gt+"'"+(String)tableResult.getValueAt(h,vTitle.size()-1)+"'";
	
		String stnhap="use "+db+" insert into "+tb+" ("+tt+") values ("
											+gt+")";
		System.out.println(stnhap);
	
	
	try{
		stmt=cons.createStatement();
		
		stmt.executeUpdate(stnhap);
		}catch(Exception ex){
		JOptionPane.showMessageDialog(this,ex+"\nCan't add ",
		        "Message",1);
	}
	}
}*/
public void save(){
	
}
//--------------Ham xoa user -----------------------------
public void deleterow(){
	int row=tableResult.getSelectedRow();
	if(row==-1){
		JOptionPane.showMessageDialog(this,"Ch\u1ECDn d\u1EEF li\u1EC7u mu\u1ED1n xóa","Thông báo",1);

	}
	else{
	try{
			Statement stmt1 = con.createStatement();

	if(stmt1.execute(stSQL)){

	ResultSet rs1=stmt1.getResultSet();
	
	ResultSetMetaData rsmd = rs1.getMetaData();

	String strdel="DELETE FROM "+db+"."+tb+" WHERE "+tb+"."
					+rsmd.getColumnLabel(1)+"= '"
					+/*(String)*/(tableResult.getValueAt(row,0))+"'";
	System.out.println(strdel);

	stmt1.execute(strdel);
	
	}
	}catch(Exception e){System.out.println("strdel"+e);}
	}
}
public void actionPerformed(ActionEvent ae){
		if (ae.getActionCommand().equals("Save"))
		{
		//	nhap();
		}
		if (ae.getActionCommand().equals("Nh\u1EADp d\u1EEF li\u1EC7u"))
		{
			myFrame =new JFrame("Nh\u1EADp d\u1EEF li\u1EC7u cho "+tb);
			ImportDataOracle usermanager=new ImportDataOracle(db,tb,con);
			myFrame.getContentPane().add(usermanager);
			myFrame.setSize(400,300);
			Connect.centerScreen(myFrame);
			myFrame.setVisible(true);

		}
		if (ae.getActionCommand().equals("Xóa d\u1EEF li\u1EC7u"))
		{
			deleterow();
		}
		if(con!=null)
		executeStatement(stSQL);
}

	public static void main(String[] args) 
	{

		myFrame =new JFrame("as");
		try{
			
 			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection (
                             "jdbc:oracle:thin:@localhost:1521:'dbTHO'","system","abc123");
        }catch(Exception e){}
		DisplayDataOracle usermanager=new DisplayDataOracle("HOCSINH","HOCSINH",con);
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
