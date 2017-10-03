
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.sql.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
	Connection cons;
	Statement stmt,stmt1;
	String rolename="", command,sql;
	String db_name, role_name;
  CheckBoxRenderer(String db, String role, Connection con) {
  	db_name=db;
  	role_name=role;
  	cons=con;
    setHorizontalAlignment(JLabel.CENTER);
  }
  
  public Component getTableCellRendererComponent(JTable table, Object value,
                         boolean isSelected, boolean hasFocus,
                          int row, int column) {
    if (isSelected) {
      setForeground(table.getSelectionForeground());
	  
	  int i=table.getSelectedRow();
	 if(hasFocus){
	 	switch(table.getSelectedColumn()){
	 		case 1: 
	 			command ="select";
	 			break;
	 		case 2: 
	 			command ="insert";
	 			break;
	 		case 3: 
	 			command ="update";
	 			break;
	 		case 4: 
	 			command ="delete";
	 			break;
	 	//	case 5: 
	 	//		command ="dri";
	 	//		break;
	 	}
	 	
	 	rolename =(String)table.getValueAt(table.getSelectedRow(),0);
	 	
	 	boolean bol;
 	
	 	if(table.getValueAt(table.getSelectedRow(),table.getSelectedColumn()).equals(false))
	 	{	bol=false;
		}
	 	else bol=true;
	 		
	 	try{
			stmt=cons.createStatement();
         	if(bol)
         		sql="use "+db_name+"  grant "+command +" on "+rolename +" to "+role_name+"";
         	else sql="use "+db_name+"  revoke "+command +" on "+rolename +" to "+role_name+"";
         //	System.out.println(sql);
         	stmt.execute(sql);
         	
         	
         }catch(Exception e){System.out.println(e);}

	  //  System.out.println(table.getSelectedRow());
	  //  System.out.println(table.getValueAt(table.getSelectedRow(),0));
	  
	  //  System.out.println(table.getSelectedColumn());
	 	
	  }
      //super.setBackground(table.getSelectionBackground());
      setBackground(table.getSelectionBackground());
    } else {
      setForeground(table.getForeground());
      setBackground(table.getBackground());
    }
    setSelected((value != null && ((Boolean)value).booleanValue()));
    return this;
  }
}


public class GrantPermission extends JPanel {
	static JFrame myFrame;
  	JTabbedPane tabDBMS = new JTabbedPane();
	
	GridBagLayout gb;
	GridBagConstraints gbc;
	
	Vector vData=new Vector(10,12);;
	Connection cons;
	Statement stmt,stmt1;
	ResultSet rs,rs1;
	Vector vTitle=new Vector(6,0);
	int i=0;
	int j=0;
	Container contenPane;
	JPanel pn;
	String rolename,dbname;
	boolean b_select =false,b_insert=false,b_update=false, b_delete=false, b_exec=false,b_dri=false;

  public GrantPermission(String db, String role,Connection con){
 //   super("MultiComponent Table");
    setLayout(new BorderLayout());
	dbname=db;
	rolename=role;
	cons=con;
    DefaultTableModel dm = new DefaultTableModel() {
      public boolean isCellEditable(int row, int column) {
        for(int j=1;j<5;j++)
        if (column == j) {////////////////sadfasfas
          return true;
        }
        return false;
      }
    };
   	try{
		//	DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
    	//	cons=DriverManager.getConnection ( "jdbc:microsoft:sqlserver://THODN:1433","sa","abc123");
         	stmt=cons.createStatement();
	        stmt1=cons.createStatement();

	        rs=stmt.executeQuery("use "+dbname+" select * from sysobjects where type='u' or type='s' or type='v'");
	        
	      //  vData=new Vector(10,12);

	        
	        vTitle.add("Object");
	        vTitle.add("Select");
	        vTitle.add("Insert");
	        vTitle.add("Update");
	        vTitle.add("Delete");
	    //    vTitle.add("Dri");
	        vTitle.add("Type");
	        while(rs.next()){
	        	Vector row=new Vector(6,0);
	        	
	        	rs1=stmt1.executeQuery("use "+dbname+" exec sp_helprotect");
	         	while(rs1.next()){
	         		if((rs1.getString("Object").equals(rs.getString(1))) && 
	         		(rs1.getString("Grantee").equals(rolename))){ 
	         		if(rs1.getString("Action").equals("Select"))b_select=true;
	         		if(rs1.getString("Action").equals("Insert"))b_insert=true;
	         		if(rs1.getString("Action").equals("Update"))b_update=true;
	         		if(rs1.getString("Action").equals("Delete"))b_delete=true;
	         //		if(rs1.getString("Action").equals("References"))b_dri=true;
	         		}
	         	}
	        	
	        	
	        	row.add(rs.getString(1));
	        	if(rs.getString(3).equals("U "))
	        	{   
	        		row.add(new Boolean(b_select));
		        	row.add(new Boolean(b_insert));
		        	row.add(new Boolean(b_update));
		        	row.add(new Boolean(b_delete));
		     //   	row.add(new Boolean(b_dri));
		        	row.add("Table");

		        }else if(rs.getString(3).equals("S ")){
		           	row.add(new Boolean(b_select));
		        	row.add(new Boolean(b_insert));
		        	row.add(new Boolean(b_update));
		        	row.add(new Boolean(b_delete));
		       // 	row.add(new Boolean(b_dri));
		        	row.add("System");
		        }else {
		           	row.add(new Boolean(b_select));
		        	row.add(new Boolean(b_insert));
		        	row.add(new Boolean(b_update));
		        	row.add(new Boolean(b_delete));
		       // 	row.add(new Boolean(b_dri));
		        	row.add("View");
		        }
	        	
	        	
	        	
	        	vData.add(row);
	        	b_select=false;
	         	b_insert=false;
	         	b_update=false;
	         	b_delete=false;
	      //   	b_exec=false;
	      //   	b_dri=false;
	        	i++;
	        }
         
       }catch(Exception a){System.out.println(a);}
         
    dm.setDataVector(vData,vTitle);
    JTable table = new JTable(dm);

    CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer(dbname,rolename,cons);
    EachRowRenderer  rowRenderer         = new EachRowRenderer();
  
   for(int j=0;j<i;j++)
    rowRenderer.add(j, checkBoxRenderer);
    JCheckBox checkBox = new JCheckBox();
    checkBox.setHorizontalAlignment(JLabel.CENTER);
   
    DefaultCellEditor checkBoxEditor = new DefaultCellEditor(checkBox);
    EachRowEditor rowEditor = new EachRowEditor(table);
   for(int j=0;j<i;j++) 
    rowEditor.setEditorAt(j, checkBoxEditor);
 	        		rowRenderer.add(j, checkBoxRenderer);
			    	rowEditor.setEditorAt(j, checkBoxEditor);
			    	
			    	rowRenderer.add(j, checkBoxRenderer);
			    	rowEditor.setEditorAt(j, checkBoxEditor);
				    table.getColumn("Select").setCellRenderer(rowRenderer);
				    table.getColumn("Select").setCellEditor(rowEditor);
					table.getColumn("Insert").setCellRenderer(rowRenderer);
				    table.getColumn("Insert").setCellEditor(rowEditor);
					table.getColumn("Update").setCellRenderer(rowRenderer);
				    table.getColumn("Update").setCellEditor(rowEditor);
				
					table.getColumn("Delete").setCellRenderer(rowRenderer);
				    table.getColumn("Delete").setCellEditor(rowEditor);
				
			//	    table.getColumn("Dri").setCellRenderer(rowRenderer);
			//	    table.getColumn("Dri").setCellEditor(rowEditor);
		
   /* contenPane=getContentPane();
	contenPane.setLayout(new BorderLayout());*/

    
    JScrollPane scroll = new JScrollPane(table);
    pn=new JPanel();
    pn.setLayout(new BorderLayout());
   	JLabel lb=new JLabel("PHÂN QUY\u1EC0N CHO NHÓM QUY\u1EC0N");

	lb.setIcon(new ImageIcon("icon\\vty32.gif"));
	lb.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 20));

   	pn.add(lb,BorderLayout.CENTER);
    
    add(scroll,"Center");
 //   add( pn,"North" );
    
    
  }

/*  public static void main(String[] args) {
    	myFrame=new JFrame("H\u1EC7 th\u1ED1ng qu\u1EA3n tr\u1ECB c\u01A1 s\u1EDF d\u1EEF li\u1EC7u \u0111a n\u0103ng");

    	GrantPermission frame = new GrantPermission();
		myFrame.getContentPane().add(frame,"Center");
		myFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(800,550);
		myFrame.setVisible(true);
		myFrame.repaint();
  }*/
}

