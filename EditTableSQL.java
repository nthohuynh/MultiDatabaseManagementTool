import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.*;

public class EditTableSQL extends JPanel implements ActionListener{
	String st,st1,st2,st3,stt;
	JButton jbAdd ;
	JButton jbDel;
	JButton jbCancel;
	JButton jbSave;
	DisplayEditTableSQL bang;
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
	Connection cons;
	Container contenPane;
	static String data[] = {"binary", "bigint","bit","datetime",
		 "float", "image", "int", "money","nchar","ntext","nvarchar",
		"real","smalldatetime","smallint","smallmoney",
		"text","tinyint","varbinary","varchar","timestamp"	};
	static String user1, table1;
	
	static JFrame myFrame;
	
	JPanel jPanelButton;
	String dataso[]={"binary", "bigint","bit","datetime",
		 "float", "image", "int", "money",
		"real","smalldatetime","smallint","smallmoney",
		"tinyint","varbinary","text","ntext","nvarchar","timestamp"
		};

public EditTableSQL(String user, String table,Connection con) {

	cons=con;
	user1=user;
	table1=table;
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("B\u1EA3ng: "+table1 );
		jlObject.setIcon(new ImageIcon("icon\\tunassistant.gif"));
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 20));
		jPanel.add(jlObject);
		
	bang = new DisplayEditTableSQL(user1,table1,cons);
	jtbEdit = new JTable(bang);
	scrollpane = new JScrollPane(jtbEdit);
	dataComboKDL = new Vector(data.length);
	for (int i = 0; i < data.length; i++) {
		dataComboKDL.add(data[i]);
	}
	cbKDL = new JComboBox(dataComboKDL);
	TableColumn cotKDL = jtbEdit.getColumn("type_name");
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
		
	oldRow=bang.getRowCount();
	velai();
}
void velai(){
	bang= new DisplayEditTableSQL(user1, table1,cons);
	jtbEdit.setModel(bang);
	jtbEdit.repaint();
	scrollpane.repaint();
	
	dataComboKDL = new Vector(data.length);
	for (int i = 0; i < data.length; i++) {
		dataComboKDL.add(data[i]);
	}
	cbKDL = new JComboBox(dataComboKDL);
	TableColumn cotKDL = jtbEdit.getColumn("type_name");
	cotKDL.setCellEditor(new DefaultCellEditor(cbKDL));
}
void themcot(String db,String bang,String tencot,String kdl,String kichthuoc,String macdinh,String nul){
	if(!macdinh.equals("NULL")){
	//	System.out.println(macdinh);
		if(Check(kdl,dataso)){
			st="use "+db+ " alter table "+ bang +" add "+tencot+" "+kdl+"";
			st=st+" "+nul+" default "+macdinh+" with values" ;
			System.out.println(st);
	
		}else{
		st="use "+db+ " alter table "+ bang +" add "+tencot+" "+kdl+"(";
		st=st+kichthuoc+") "+nul+" default "+macdinh+" with values" ;
		System.out.println(st);
		}
		
		try{
			stmt=cons.createStatement();
			stmt.executeUpdate(st);
			}catch(Exception ex){
			JOptionPane.showMessageDialog(this,ex+"\nCan't add column",
			        "Message",1);
		}
	}else 
		{
			if(Check(kdl,dataso)){
				st="use "+db+ " alter table "+ bang +" add "+tencot+" "+kdl+"";
				st=st+" NULL";
			
				st1="use "+db+"  alter table "+bang+" alter column "+tencot+" "+kdl+" "+nul+"";

				
			}else{
				st="use "+db+ " alter table "+ bang +" add "+tencot+" "+kdl+"(";
				st=st+kichthuoc+") NULL ";// default "+macdinh+" with values" ;
				
				st1="use "+db+"  alter table "+bang+" alter column "+tencot+" "+kdl+"("+kichthuoc+") "+nul+"";

			
			}
			try{
				stmt=cons.createStatement();
				stmt.executeUpdate(st);
			
				stmt.executeUpdate(st1);
				}catch(Exception ex){
				JOptionPane.showMessageDialog(this,ex+"\nCan't add column",
				        "Message",1);
			}
		}
	
	
}
void themcot1(String db,String bang,String tencot,String kdl,String kichthuoc,String macdinh,String nul){
	if(Check(kdl,dataso)){
		st="use "+db+ " alter table "+ bang +" add "+tencot+" "+kdl+"";
		st=st+" "+nul+"";// default "+macdinh+" with values" ;
		System.out.println(st);

	}else{
		st="use "+db+ " alter table "+ bang +" add "+tencot+" "+kdl+"(";
		st=st+kichthuoc+") "+nul+"";// default "+macdinh+" with values" ;
		System.out.println(st);
	}
	try{
	stmt=cons.createStatement();
		stmt.executeUpdate(st);
		}catch(Exception ex){
		JOptionPane.showMessageDialog(this,ex+"\nCan't add column",
		        "Message",1);
	}
}
void suatencot(String db,String bang,String cotcu,String cotmoi){
	st3="use "+db+" exec sp_rename '"+bang+"."+cotcu+"','"+cotmoi+"','column'";
	try{
		stmt=cons.createStatement();
		stmt.executeQuery(st3);
	}catch(Exception ex){
	//	JOptionPane.showMessageDialog(this,ex,"Message",1);
	}
}
void suakhac(String db,String bang,String cotmoi,String kdl,String kt,String md, String nul){
	if(Check(kdl,dataso)){
		st1="use "+db+"  alter table "+bang+" alter column "+cotmoi+" "+kdl+" "+nul+"";
		System.out.println(st1);
	}else 
	st1="use "+db+"  alter table "+bang+" alter column "+cotmoi+" "+kdl+"("+kt+") "+nul+"";
//	st1=st1+" default "+md+"";

	try{
		stmt=cons.createStatement();
		stmt.executeUpdate(st1);
	}catch(Exception ex){
		JOptionPane.showMessageDialog(this,ex,"Message",1);
	}
}
public boolean Check(String st1, String vt[]){
	for(int i=0;i<vt.length;i++){
		if(st1.equals((String)vt[i])){
			return true;
		}
	}
	return false;

}
/*void suanull(String nd,String bang,String cot,String nul){
	st2="user "+nd+" exec alter table "+bang+" alter column "+cot+" ;

	try{
		 	DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
    		cons=DriverManager.getConnection ( "jdbc:microsoft:sqlserver://BKDN-BMNJMC9ZPY:1433","sa","abc123");
		}catch(Exception se){
		System.out.println(se.toString());
	}
	try{
		stmt=cons.createStatement();
		stmt.executeQuery(st2);
	}catch(Exception ex){
		JOptionPane.showMessageDialog(this,ex,"Message",1);
	}
}*/
public void actionPerformed(ActionEvent ae){

	if (ae.getActionCommand().equals("Cancel")){
	//	this.dispose();
	}
	if (ae.getActionCommand().equals("Thêm tr\u01B0\u1EDDng")){
		kt_them=true;
		velai();
	}
	if (ae.getActionCommand().equals("Xóa")){
		if (jtbEdit.getSelectedRow()>=0){
		st = "use "+user1+" ALTER TABLE "+table1+" drop column "
		+jtbEdit.getValueAt(jtbEdit.getSelectedRow(), 0)+"";
	
		try {
		stmt = cons.createStatement();
		stmt.executeUpdate(st);
	//	JOptionPane.showMessageDialog(this, "Deleted",
	//		                  "Message", 3);
		}catch(Exception es){JOptionPane.showMessageDialog(this, "Không th\u1EC3 xóa",
			                  "Thông báo", 3);}
			                  
		try{
		DisplayEditTableSQL.tencot.remove(jtbEdit.getSelectedRow());
		DisplayEditTableSQL.tencotcu.remove(jtbEdit.getSelectedRow());
		DisplayEditTableSQL.kieudl.remove(jtbEdit.getSelectedRow());
		DisplayEditTableSQL.kichthuoc.remove(jtbEdit.getSelectedRow());
		DisplayEditTableSQL.nul.remove(jtbEdit.getSelectedRow());
		DisplayEditTableSQL.nulcu.remove(jtbEdit.getSelectedRow());
		DisplayEditTableSQL.macdinh.remove(jtbEdit.getSelectedRow());
		velai();
		}
		catch (Exception ex) {	}
		}else JOptionPane.showMessageDialog(this,"Ch\u1ECDn tr\u01B0\u1EDDng mu\u1ED1n xóa","Thông báo",1);
		oldRow=bang.getRowCount();
		System.out.println(oldRow);
	}
	if (ae.getActionCommand().equals("L\u01B0u")){
		for (int i=0;i<=oldRow-1;i++){
			if ((bang.getValueAt(i,3)).equals("1"))
				DisplayEditTableSQL.nultmp.set(i," NULL");
			else 
				DisplayEditTableSQL.nultmp.set(i,"NOT NULL");

			if ((DisplayEditTableSQL.macdinh.elementAt(i)==null)||((bang.getValueAt(i,4).toString()).equals("")))
				DisplayEditTableSQL.macdinh.set(i, "NULL");
			else DisplayEditTableSQL.macdinh.set(i,"'"+bang.getValueAt(i, 4)+"'");
		
			if (!bang.getValueAt(i,0).equals(DisplayEditTableSQL.tencotcu.elementAt(i))){
				suatencot(user1,
				table1,
				DisplayEditTableSQL.tencotcu.elementAt(i).toString(),
				bang.getValueAt(i, 0).toString());
				DisplayEditTableSQL.tencotcu.set(i,bang.getValueAt(i,0));
			}
		/*	if (!((String)bang.getValueAt(i,3)).equals(DisplayEditTableSQL.nulcu.elementAt(i))){
				suanull(user1,table1,bang.getValueAt(i,0).toString(),DisplayEditTableSQL.nultmp.elementAt(i).toString());
				DisplayEditTableSQL.nulcu.set(i,bang.getValueAt(i,3));
			}*/
			suakhac(user1,
			table1,
			bang.getValueAt(i, 0).toString(),
			bang.getValueAt(i,1).toString(),
			bang.getValueAt(i,2).toString(),
			bang.getValueAt(i,4).toString(),
		//	DisplayEditTableSQL.macdinh.elementAt(i).toString(),
			DisplayEditTableSQL.nultmp.elementAt(i).toString()

			);
		}
		if (kt_them){
				i=bang.getRowCount()-1;
				if ((bang.getValueAt(i, 3)).equals("1"))
					{
						DisplayEditTableSQL.nultmp.set(i, "NULL");
						if ((DisplayEditTableSQL.macdinh.elementAt(i).toString()==null)|| (((bang.getValueAt(i, 4)).toString()).equals("")))
						{			
							themcot1(user1,
							table1,
							bang.getValueAt(i,0).toString(),
							bang.getValueAt(i,1).toString(),
							bang.getValueAt(i,2).toString(),
							bang.getValueAt(i,4).toString(),
						//	DisplayEditTableSQL.macdinh.elementAt(i).toString(),
							DisplayEditTableSQL.nultmp.elementAt(i).toString());
						}else{
						
							DisplayEditTableSQL.macdinh.set(i, "'"+bang.getValueAt(i, 4)+"'");
							themcot(user1,
							table1,
							bang.getValueAt(i,0).toString(),
							bang.getValueAt(i,1).toString(),
							bang.getValueAt(i,2).toString(),
							bang.getValueAt(i,4).toString(),
						//	DisplayEditTableSQL.macdinh.elementAt(i).toString(),
							DisplayEditTableSQL.nultmp.elementAt(i).toString());
						}
					}
				else{
					DisplayEditTableSQL.nultmp.set(i, "NOT NULL");
			
					if ((DisplayEditTableSQL.macdinh.elementAt(i).toString()==null)|| (((bang.getValueAt(i, 4)).toString()).equals("")))
						DisplayEditTableSQL.macdinh.set(i, "NULL");
					else
						DisplayEditTableSQL.macdinh.set(i, " '"+bang.getValueAt(i, 4)+"'");
		
					themcot(user1,
					table1,
					bang.getValueAt(i,0).toString(),
					bang.getValueAt(i,1).toString(),
					bang.getValueAt(i,2).toString(),
					bang.getValueAt(i,4).toString(),
				//	DisplayEditTableSQL.macdinh.elementAt(i).toString(),
					DisplayEditTableSQL.nultmp.elementAt(i).toString());
				}
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
	EditTableSQL usermanager=new EditTableSQL("dbBanhang","Hang");
	myFrame.getContentPane().add(usermanager);
	
	myFrame.addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent e){
		System.exit(0);
		}
		});
	myFrame.setSize(600,400);
	Connect.centerScreen(myFrame);
	myFrame.setVisible(true);
	}*/
}