import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.*;

public class EditTable extends JPanel implements ActionListener{
	String st,st1,st2,st3,stt;
	JButton jbAdd ;
	JButton jbDel;
	JButton jbCancel;
	JButton jbSave;
	DisplayEditTable bang;
	JTable jtbEdit;
	JScrollPane scrollpane;
	int oldRow,newRow,i;
	Statement stmt;
	ResultSet rs;
	static boolean kt_them=false;
	static JTextField txtSocot = new JTextField();
	JButton btSQL = new JButton();
	JComboBox cbKDL;
	Vector dataComboKDL;
	Connection con;
	Container contenPane;
	static String data[] = {
	"CHAR", "VARCHAR2", "NCHAR", "NVARCHAR2", "NUMBER",
	"RAW", "DATE", "LONG", "LONGRAW", "ROWID", "BLOB", "CLOB",
	"NCLOB", "BFILE", "UROWID", "FLOAT"}; 
	static String user1, table1;
	
	static JFrame myFrame;
	
	JPanel jPanelButton;

static Connection con2;
public EditTable(String user, String table,Connection cons) {

	con=cons;	
	user1=user;
	table1=table;
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("B\u1EA3ng: "+table1 );
		jlObject.setIcon(new ImageIcon("icon\\tunassistant.gif"));
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 20));
		jPanel.add(jlObject);

	bang = new DisplayEditTable(user1,table1,con);
	jtbEdit = new JTable(bang);
	scrollpane = new JScrollPane(jtbEdit);
	dataComboKDL = new Vector(data.length);
	for (int i = 0; i < data.length; i++) {
		dataComboKDL.add(data[i]);
	}
	cbKDL = new JComboBox(dataComboKDL);
	TableColumn cotKDL = jtbEdit.getColumn("data_type");
	cotKDL.setCellEditor(new DefaultCellEditor(cbKDL));
	
	
	setLayout(new BorderLayout());
	
	jbAdd = new JButton("Thêm tr\u01B0\u1EDDng");
	jbAdd.addActionListener(this);
	jbAdd.setBounds(new Rectangle(123, 327, 76, 29));

	jbDel = new JButton("Xóa");
	jbDel.addActionListener(this);
	jbDel.setBounds(new Rectangle(199, 327, 70, 29));
	
	jbCancel = new JButton("Cancel");
	jbCancel.addActionListener(this);
	jbCancel.setBounds(new Rectangle(342, 327, 75, 29));

	jbSave = new JButton("L\u01B0u");
	jbSave.setBounds(new Rectangle(271, 327, 69, 29));
	jbSave.addActionListener(this);
	
	scrollpane.setBounds(new Rectangle(2, 2, 676, 201));
	
	jPanelButton=new JPanel();
	jPanelButton.setLayout(new FlowLayout());
	
	jPanelButton.add(jbAdd);
	jPanelButton.add(jbDel);
	jPanelButton.add(jbSave);
//	jPanelButton.add(jbCancel);
	
	add(scrollpane, "Center");
	add(jPanelButton, "South");
	add(jPanel, "North");
	
	jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10,5, 5));
	
	oldRow=bang.getRowCount();
	velai();
}
void themcot(String nd,String bang,String tencot,String kdl,String kichthuoc,String macdinh,String nul){
	st="alter table "+nd+"."+bang+" add ("+tencot+" "+kdl+"(";
	st=st+kichthuoc+")"+" default "+macdinh+" "+nul+")";
/*	try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con=DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:dbTHO", "system", "abc123");
		//System.out.print("OK");
	}catch(Exception se){
		System.out.println(se.toString());
	}*/
	
	try{
	stmt=con.createStatement();
		stmt.executeQuery(st);
		}catch(Exception ex){
		JOptionPane.showMessageDialog(this,ex+"\nCan't add column",
		        "Message",1);
	}
}

void velai(){
	bang= new DisplayEditTable(user1, table1,con);
	jtbEdit.setModel(bang);
	jtbEdit.repaint();
	scrollpane.repaint();
	
	dataComboKDL = new Vector(data.length);
	for (int i = 0; i < data.length; i++) {
		dataComboKDL.add(data[i]);
	}
	cbKDL = new JComboBox(dataComboKDL);
	TableColumn cotKDL = jtbEdit.getColumn("data_type");
	cotKDL.setCellEditor(new DefaultCellEditor(cbKDL));
}

void suatencot(String nd,String bang,String cotcu,String cotmoi){
	st3="alter table "+nd+"."+bang+" rename column "+cotcu+" to "+cotmoi;
	
/*	try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con=DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:dbTHO", "system", "abc123");
	}catch(Exception se){
		System.out.println(se.toString());
	}*/
	try{
		stmt=con.createStatement();
		stmt.executeQuery(st3);
	}catch(Exception ex){
	//	JOptionPane.showMessageDialog(this,ex,"Message",1);
	}
}

void suakhac(String nd,String bang,String cotmoi,String kdl,String kt,String md){
	st1="alter table "+nd+"."+bang+" modify("+cotmoi+" "+kdl+"("+kt+")";
	st1=st1+" default "+md+")";
/*	try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con=DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:dbTHO", "system", "abc123");
	}catch(Exception se){
		System.out.println(se.toString());
	}*/
	try{
		stmt=con.createStatement();
		stmt.executeQuery(st1);
	}catch(Exception ex){
	//	JOptionPane.showMessageDialog(this,ex,"Message",1);
	}
}

void suanull(String nd,String bang,String cot,String nul){
	st2="alter table "+nd+"."+bang+" modify("+cot+" "+nul+")";
/*	try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con=DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:dbTHO", "system", "abc123");
	}catch(Exception se){
		System.out.println(se.toString());
	}*/
	try{
		stmt=con.createStatement();
		stmt.executeQuery(st2);
	}catch(Exception ex){
		JOptionPane.showMessageDialog(this,ex,"Message",1);
	}
}


public void actionPerformed(ActionEvent ae){

	if (ae.getActionCommand().equals("Cancel")){
		
	}
	if (ae.getActionCommand().equals("Thêm tr\u01B0\u1EDDng")){
		kt_them=true;
		velai();
	}
	if (ae.getActionCommand().equals("Xóa")){
		if (jtbEdit.getSelectedRow()>=0){
		st = "ALTER TABLE " +user1;
		st = st + "." + table1;
		st = st + " SET UNUSED (" + jtbEdit.getValueAt(jtbEdit.getSelectedRow(), 0);
		st = st + ") CASCADE CONSTRAINTS";
	/*	try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:dbTHO", "system", "abc123");
			//System.out.print("OK");
		}catch(Exception se){
		System.out.println(se.toString());
		}*/
		try {
		stmt = con.createStatement();
		stmt.executeQuery(st);
		DisplayEditTable.tencot.remove(jtbEdit.getSelectedRow());
		DisplayEditTable.tencotcu.remove(jtbEdit.getSelectedRow());
		DisplayEditTable.kieudl.remove(jtbEdit.getSelectedRow());
		DisplayEditTable.kichthuoc.remove(jtbEdit.getSelectedRow());
		DisplayEditTable.nul.remove(jtbEdit.getSelectedRow());
		DisplayEditTable.nulcu.remove(jtbEdit.getSelectedRow());
		DisplayEditTable.macdinh.remove(jtbEdit.getSelectedRow());
		velai();
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex + "Không th\u1EC3 xóa",
			                  "Thông báo", 3);
			}
		}else JOptionPane.showMessageDialog(this,"Ch\u1ECDn tr\u01B0\u1EDDng mu\u1ED1n xóa","Thông báo",1);
		oldRow=bang.getRowCount();
		System.out.println(oldRow);
	}
	if (ae.getActionCommand().equals("L\u01B0u")){
		for (int i=0;i<=oldRow-1;i++){
			if (!bang.getValueAt(i,3).equals("Y"))
				DisplayEditTable.nultmp.set(i,"NOT NULL");
			else DisplayEditTable.nultmp.set(i,"NULL");
			if ((bang.getValueAt(i,4)==null)||(bang.getValueAt(i,4).equals("")))
				DisplayEditTable.macdinh.set(i, "NULL");
			else DisplayEditTable.macdinh.set(i,bang.getValueAt(i,4));
		
			if (!bang.getValueAt(i,0).equals(DisplayEditTable.tencotcu.elementAt(i))){
				suatencot(user1,
				table1,
				DisplayEditTable.tencotcu.elementAt(i).toString(),
				bang.getValueAt(i, 0).toString());
				DisplayEditTable.tencotcu.set(i,bang.getValueAt(i,0));
			}
			if (!bang.getValueAt(i,3).equals(DisplayEditTable.nulcu.elementAt(i))){
				suanull(user1,table1,bang.getValueAt(i,0).toString(),DisplayEditTable.nultmp.elementAt(i).toString());
				DisplayEditTable.nulcu.set(i,bang.getValueAt(i,3));
			}
			suakhac(user1,
			table1,
			bang.getValueAt(i, 0).toString(),
			bang.getValueAt(i,1).toString(),
			bang.getValueAt(i,2).toString(),
			DisplayEditTable.macdinh.elementAt(i).toString());
		}
		if (kt_them){
			i=bang.getRowCount()-1;
			if (!bang.getValueAt(i, 3).equals("Y"))
				DisplayEditTable.nultmp.set(i, "NOT NULL");
			else
				DisplayEditTable.nultmp.set(i, "NULL");
		
			if ( (bang.getValueAt(i, 4) == null) ||(bang.getValueAt(i, 4).equals("")))
				DisplayEditTable.macdinh.set(i, "NULL");
			else
				DisplayEditTable.macdinh.set(i, bang.getValueAt(i, 4));
				themcot(user1,
				table1,
				bang.getValueAt(i,0).toString(),
				bang.getValueAt(i,1).toString(),
				bang.getValueAt(i,2).toString(),
				DisplayEditTable.macdinh.elementAt(i).toString(),
				DisplayEditTable.nultmp.elementAt(i).toString());
		}
		stt="";
		if (st!=null)  stt=st+"\n";
		else stt=stt;
		if (st1!=null) stt=stt+st1+"\n";
		else stt=stt;
		if (st2!=null) stt=stt+st2+"\n";
		else stt=stt;
		if (st3!=null) stt=stt+st3+"\n";
		else stt=stt;
		kt_them=false;
		velai();
		st1=null;st2=null;st3=null;st=null;
		oldRow=bang.getRowCount();
	}

}
/*public static void main(String agrs[]){
	myFrame =new JFrame("as");
	try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con2=DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:dbTHO", "system", "abc123");
			//System.out.print("OK");
		}catch(Exception se){
		System.out.println(se.toString());
		}
	EditTable usermanager=new EditTable("SYSTEM","BANG1",con2);
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