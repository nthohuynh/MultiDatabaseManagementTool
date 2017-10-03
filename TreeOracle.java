import java.awt.*;
import java.util.*;
import java.awt.event.*;
import com.sun.java.swing.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.sql.*;
import java.io.*;
import java.lang.*;
import java.sql.SQLException;
import javax.swing.border.*;
public class TreeOracle{
	String st1,st;
	public DefaultMutableTreeNode oracle9i;
	Connection con;
	public TreeOracle(int i, String user, Connection cons){
		con=cons;
	    	
    	ImageIcon iconoracl=new ImageIcon("icon\\ioTopConcatPlex.gif");
    	
		 oracle9i=new DefaultMutableTreeNode(new IconData(iconoracl,i, "Oracle 9i - "+user));
		
			
		ImageIcon iconsch=new ImageIcon("icon\\attribute.gif");
		DefaultMutableTreeNode schema=new DefaultMutableTreeNode(new IconData(iconsch,i,"Schema"));
		
		ImageIcon iconsec=new ImageIcon("icon\\s.gif");
		DefaultMutableTreeNode security=new DefaultMutableTreeNode(new IconData(iconsec,i, "Security"));
	
		ImageIcon iconset=new ImageIcon("icon\\apply_site.gif");
		DefaultMutableTreeNode storage=new DefaultMutableTreeNode(new IconData(iconset,i,"Storage"));
		

		oracle9i.add(schema);
		oracle9i.add(security);
		oracle9i.add(storage);
		ImageIcon iconus=new ImageIcon("icon\\user.gif");

		DefaultMutableTreeNode userse=new DefaultMutableTreeNode(new IconData(iconus,i,"User"));
		security.add(userse);
		
		iconus=new ImageIcon("icon\\configsm.gif");
		DefaultMutableTreeNode rolese=new DefaultMutableTreeNode(new IconData(iconus,i,"Roles"));
		security.add(rolese);
	
		iconus=new ImageIcon("icon\\oracle_sysman_node.gif");
		DefaultMutableTreeNode tbsp=new DefaultMutableTreeNode(new IconData(iconus,i,"Tablespace"));
		storage.add(tbsp);
	///	Connection conn = null;
	if(con!=null)
		try{
			
	
			Statement  stmt=con.createStatement();
			Statement  stmt1=con.createStatement();
				
		//	conn.setAutoCommit(true);
			String xsql="SELECT USERNAME FROM DBA_USERS";
			
			
			ResultSet rs=stmt.executeQuery(xsql);
			
			/*--------adduser in schema-------------*/	
			
			while (rs.next()){
				st = rs.getString("USERNAME");
				
				ImageIcon iconuser=new ImageIcon("icon\\ioTopHostDevice.gif");
				DefaultMutableTreeNode users=new DefaultMutableTreeNode(new IconData(iconuser,i,st));
				schema.add(users);
		
				ImageIcon iconuser1=new ImageIcon("icon\\oracle_metrics_server.gif");
				DefaultMutableTreeNode users1=new DefaultMutableTreeNode(new IconData(iconuser1,i,st));
				userse.add(users1);
				/*---------add table in user-----------*/
			
				ImageIcon iconta=new ImageIcon("icon\\table-small.gif");
				DefaultMutableTreeNode tables=new DefaultMutableTreeNode(new IconData(iconta,i,"Table"));
				users.add(tables);
				
				String xsql1="SELECT table_name FROM dba_tables where owner='" + st + "'";
				ResultSet rs1 = stmt1.executeQuery(xsql1);
				while (rs1.next()){
					st1 = rs1.getString("table_name");
					ImageIcon iconta1=new ImageIcon("icon\\oracle_sysman_database.gif");
					DefaultMutableTreeNode tablei=new DefaultMutableTreeNode(new IconData(iconta1,i,st1));
					tables.add(tablei);
					}
				/*---------add indexes in user-----------*/
				ImageIcon iconin=new ImageIcon("icon\\group.gif");
			
				DefaultMutableTreeNode indexes=new DefaultMutableTreeNode(new IconData(iconin,i,"Indexes"));
				users.add(indexes);
				
				xsql1="select index_name from all_indexes where owner='"+st+"'";
				rs1 = stmt1.executeQuery(xsql1);
				while (rs1.next()){
					st1 = rs1.getString("index_name");
					ImageIcon iconin1=new ImageIcon("icon\\oracle_sysman_database.gif");
					DefaultMutableTreeNode indexei=new DefaultMutableTreeNode(new IconData(iconin1,i,st1));
					indexes.add(indexei);
					}
				ImageIcon iconvi=new ImageIcon("icon\\oracle_sysman_listener.gif");
				DefaultMutableTreeNode views=new DefaultMutableTreeNode(new IconData(iconvi,i,"Views"));
				users.add(views);
				
				/*-----------add view---------------*/
				
				xsql1="SELECT view_name FROM all_views where owner='" + st + "'";
				rs1 = stmt1.executeQuery(xsql1);
				while (rs1.next()){
					st1 = rs1.getString("view_name");
					ImageIcon iconvi1=new ImageIcon("icon\\oracle_sysman_database.gif");
					DefaultMutableTreeNode viewi=new DefaultMutableTreeNode(new IconData(iconvi1,i,st1));
					views.add(viewi);
					}
				
				/*----------add sequences--------*/
				ImageIcon iconse=new ImageIcon("icon\\bssctoc4.gif");
				DefaultMutableTreeNode sequences=new DefaultMutableTreeNode(new IconData(iconse,i,"Sequences"));
				users.add(sequences);
				
				xsql1="SELECT sequence_name FROM all_sequences where sequence_owner='"+st+"'";
				rs1 = stmt1.executeQuery(xsql1);
				while (rs1.next()){
					st1 = rs1.getString("sequence_name");
					ImageIcon iconse1=new ImageIcon("icon\\oracle_sysman_database.gif");
					DefaultMutableTreeNode sequencei=new DefaultMutableTreeNode(new IconData(iconse1,i,st1));
					sequences.add(sequencei);
					}
				/*-----------add Function--------*/
				ImageIcon iconfu=new ImageIcon("icon\\oracle_sysman_cmanager.gif");
				DefaultMutableTreeNode functions=new DefaultMutableTreeNode(new IconData(iconfu,i,"Functions"));
				users.add(functions);
				
				xsql1="select object_name from all_objects where object_type='FUNCTION' and owner='"+st+"'";
				rs1 = stmt1.executeQuery(xsql1);
				while (rs1.next()){
					st1 = rs1.getString("object_name");
					ImageIcon iconfu1=new ImageIcon("icon\\oracle_sysman_database.gif");
					DefaultMutableTreeNode functioni=new DefaultMutableTreeNode(new IconData(iconfu1,i,st1));
					functions.add(functioni);
					}
				
				/*-----------add procedures-----------*/
				ImageIcon iconpr=new ImageIcon("icon\\oracle_jserv.gif");
				DefaultMutableTreeNode procedures=new DefaultMutableTreeNode(new IconData(iconpr,i,"Procedures"));
				users.add(procedures);
				
        		xsql1="select object_name from all_objects where object_type='PROCEDURE' and owner='"+st+"'";
				rs1 = stmt1.executeQuery(xsql1);
				while (rs1.next()){
					st1 = rs1.getString("object_name");
					ImageIcon iconpr1=new ImageIcon("icon\\oracle_sysman_database.gif");
					DefaultMutableTreeNode procedurei=new DefaultMutableTreeNode(new IconData(iconpr1,i,st1));
					procedures.add(procedurei);
					}
					
				/*-----------add packages----------*/
					
				ImageIcon iconpa=new ImageIcon("icon\\oracle_ias.gif");
				DefaultMutableTreeNode packages=new DefaultMutableTreeNode(new IconData(iconpa,i,"Packages"));
				users.add(packages);
				
        	    xsql1="select object_name from all_objects where object_type='PACKAGE' and owner='"+st+"'";
				rs1 = stmt1.executeQuery(xsql1);
				while (rs1.next()){
					st1 = rs1.getString("object_name");
					ImageIcon iconpa1=new ImageIcon("icon\\oracle_sysman_database.gif");
					DefaultMutableTreeNode packagei=new DefaultMutableTreeNode(new IconData(iconpa1,i,st1));
					packages.add(packagei);
					}
					
				/*--------add triggers-----*/
				ImageIcon icontr=new ImageIcon("icon\\oracle_forms6.gif");
				DefaultMutableTreeNode triggers=new DefaultMutableTreeNode(new IconData(icontr,i,"Triggers"));
				users.add(triggers);
				
        		xsql1="select object_name from all_objects where object_type='TRIGGER' and owner='"+st+"'";
				rs1 = stmt1.executeQuery(xsql1);
				while (rs1.next()){
					st1 = rs1.getString("object_name");
					ImageIcon icontr1=new ImageIcon("icon\\oracle_sysman_database.gif");
					DefaultMutableTreeNode triggeri=new DefaultMutableTreeNode(new IconData(icontr1,i,st1));
					triggers.add(triggeri);
					}
			}//ket thuc add users	
	
			xsql="select role  from dba_roles";
			rs=stmt.executeQuery(xsql);
			while (rs.next()){
				st = rs.getString("role");
				ImageIcon iconrole=new ImageIcon("icon\\MoreResults_R.gif");
				DefaultMutableTreeNode system=new DefaultMutableTreeNode(new IconData(iconrole,i,st));
				rolese.add(system);
				}
					
	//	}catch(ClassNotFoundException ex){
	//	System.out.println(ex);
		}catch(SQLException ex){
			System.out.println("SQL");
			while(ex!= null){
				System.out.println(ex.getMessage());
				System.out.println(ex.getSQLState());
				System.out.println(ex.getErrorCode());
				ex=ex.getNextException();
			}
		}
	
}
}