
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
public class ImportDataOracle extends JPanel implements ActionListener
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
	JButton jbAdd,jbSave,jbDel, jbCancel;
	JScrollPane jscPane,resultPane;
	Connection con;
	JTable tableResult;
	String username,db,tb;
	ResultSet rss;
	boolean them=true;
	Vector vData;
	Vector vTitle;
	Statement stmt;
	private JTextField jtnum=null;
	private double in=0;


//-----------------Ham cau tu-------------------------------
	public ImportDataOracle(String dbname, String table, Connection cons){
		con=cons;
		db=dbname;
		tb=table;
		stSQL = new String("select * from "+db+"."+tb+"");
//	System.out.println(stSQL);

		setLayout(new BorderLayout());
		
		
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();
		
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("Nh\u1EADp d\u1EEF li\u1EC7u cho b\u1EA3ng: "+tb );
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 20));
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
		jbAdd=new JButton("Thêm");
		jbSave=new JButton("L\u01B0u");
		
		jbAdd.addActionListener(this);
		jbSave.addActionListener(this);
		
		jtnum=new JTextField(5);
		
		add(jpOption,jtnum,1,1,2,1);
		add(jpOption,jbAdd,3,1,2,1);
		add(jpOption,jbSave,5,1,2,1);
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
		
			vData=new Vector(10,12);

			for(int j=0;j<in;j++){
				Vector row=new Vector(numberOfColumns,0);
				for(int i=1; i<=numberOfColumns;i++){ 
					row.add("");
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
public void nhap(){
	//connect();
	String tt="";

	int j=vTitle.size();
	for(int i=0;i<vTitle.size()-1;i++){
		tt=tt+vTitle.elementAt(i)+",";
	}
	tt=tt+vTitle.elementAt(vTitle.size()-1);
	

	
	try{
		for(int h=0; h<in;h++){	
			String gt="";
			for(int i=0;i<vTitle.size()-1;i++){
				String str=(String)tableResult.getValueAt(h,i);
				gt=gt+"'"+str+"'"+",";
			}
			gt=gt+"'"+(String)tableResult.getValueAt(h,vTitle.size()-1)+"'";
		
			String stnhap="insert into "+db+"."+tb+" ("+tt+") values ("
												+gt+")";
		
			stmt=con.createStatement();
			stmt.executeUpdate(stnhap);
		
		
		}
	
		JOptionPane.showMessageDialog(this,"\nNh\u1EADp d\u1EEF li\u1EC7u thành công",
			        "Thông báo",1);
	}catch(Exception e12){
		JOptionPane.showMessageDialog(this,e12+"\nKhông hoàn thành ",
			        "Thông báo",1);
	
	}
}
public void save(){
	
}
//--------------Ham xoa user -----------------------------
public void deleteUser(){

}
public void actionPerformed(ActionEvent ae){
		if (ae.getActionCommand().equals("L\u01B0u"))
		{
			nhap();
			in=0;
			jtnum.setText("");
			executeStatement(stSQL);

		}
		if (ae.getActionCommand().equals("Thêm"))
		{
			in = Double.parseDouble(jtnum.getText());
			System.out.println(in);

		//	connect();
			executeStatement(stSQL);
		}
}

/*	public static void main(String[] args) 
	{

		myFrame =new JFrame("as");
		ImportDataSQL usermanager=new ImportDataSQL("dbBanhang","hang");
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
