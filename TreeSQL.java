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
public class TreeSQL  
{
		
		String st;
		public DefaultMutableTreeNode sqlserver;
		Connection cons;

		public TreeSQL(int i, String user, Connection con){

			cons=con;
			ImageIcon iconser=new ImageIcon("icon\\rbsonline.gif");

			sqlserver=new DefaultMutableTreeNode(new IconData(iconser,i,"SQL Server - "+user));
			// dua du lieu vao cay sql server
			ImageIcon icondbs=new ImageIcon("icon\\oc4j.gif");
			DefaultMutableTreeNode database=new DefaultMutableTreeNode(new IconData(icondbs,i,"Databases"));
			ImageIcon iconsec=new ImageIcon("icon\\s.gif");

			DefaultMutableTreeNode securitys=new DefaultMutableTreeNode(new IconData(iconsec,i,"Security"));
			sqlserver.add(database);
			sqlserver.add(securitys);
			
			ImageIcon iconus=new ImageIcon("icon\\user.gif");
			DefaultMutableTreeNode login=new DefaultMutableTreeNode(new IconData(iconus,i,"Login"));
		
			ImageIcon iconsr=new ImageIcon("icon\\comp2.gif");
			DefaultMutableTreeNode serverrole=new DefaultMutableTreeNode(new IconData(iconsr,i,"Server Roles"));
			securitys.add(login);
			securitys.add(serverrole);
			if(cons!=null)
		try{
			
			Statement  stmts=cons.createStatement();
			String xsql="sp_databases";
			
			
			ResultSet rs=stmts.executeQuery(xsql);
				
			while (rs.next()){
				st = rs.getString("DATABASE_NAME");
				ImageIcon icons=new ImageIcon("icon\\ioTopStripedNRaid5Plex.gif");
				DefaultMutableTreeNode syste=new DefaultMutableTreeNode(new IconData(icons,i,st));
				
				database.add(syste);
				ImageIcon icontb=new ImageIcon("icon\\table.gif");
				
				DefaultMutableTreeNode tablesq=new DefaultMutableTreeNode(new IconData(icontb,i,"Table SQL"));
			
				icontb=new ImageIcon("icon\\oracle_sysman_listener.gif");
				DefaultMutableTreeNode viewsq=new DefaultMutableTreeNode(new IconData(icontb,i,"View SQL"));
			
				icontb=new ImageIcon("icon\\chose.gif");
				DefaultMutableTreeNode storepro=new DefaultMutableTreeNode(new IconData(icontb,i,"Stored Procedures"));
			
				syste.add(tablesq);
				syste.add(viewsq);
				syste.add(storepro);
				
			
			
			
				
				String stSQL = new String("USE "+st+" EXEC sp_tables @TABLE_TYPE = \"'TABLE'\"" );
				Statement  stmts1=cons.createStatement();
				ResultSet rs1=stmts1.executeQuery(stSQL);
				while (rs1.next()){
				
					String st1 = rs1.getString("TABLE_NAME");
					ImageIcon icondt=new ImageIcon("icon\\ioTopSubDisk.gif");
					DefaultMutableTreeNode datatb=new DefaultMutableTreeNode(new IconData(icondt,i,st1));
					tablesq.add(datatb);
				}
				stmts1.close();
				rs1.close();
			
				stSQL = new String("USE "+st+" EXEC sp_tables @TABLE_TYPE = \"'VIEW'\"" );
				Statement  stmts12=cons.createStatement();
				rs1=stmts12.executeQuery(stSQL);
				while (rs1.next()){
				
					String st1 = rs1.getString("TABLE_NAME");
					ImageIcon icondt=new ImageIcon("icon\\ioTopSubDisk.gif");
					DefaultMutableTreeNode datavw=new DefaultMutableTreeNode(new IconData(icondt,i,st1));
					viewsq.add(datavw);
				}
				stmts12.close();
				rs1.close();
				
				stSQL = new String("USE "+st+" select * from sysobjects where type='P'" );
				Statement  stmts13=cons.createStatement();
				rs1=stmts13.executeQuery(stSQL);
				while (rs1.next()){
				
					String st1 = rs1.getString("NAME");
					ImageIcon iconpr=new ImageIcon("icon\\cf.gif");
					DefaultMutableTreeNode datapr=new DefaultMutableTreeNode(new IconData(iconpr,i,st1));
					storepro.add(datapr);
				}
				stmts13.close();
				rs1.close();
				
				
				icontb=new ImageIcon("icon\\help.gif");
				DefaultMutableTreeNode usersql=new DefaultMutableTreeNode(new IconData(icontb,i,"Users"));
				syste.add(usersql);
				stSQL = new String("USE "+st+" EXEC sp_helpuser" );
				stmts1=cons.createStatement();
				rs1=stmts1.executeQuery(stSQL);
				Vector vt=new Vector(10);
				while (rs1.next()){
					String st12 = rs1.getString("UserName");
					if(!Check(st12, vt)){
						vt.add(st12);
						ImageIcon icondt1=new ImageIcon("icon\\help2.gif");
						DefaultMutableTreeNode datatb1=new DefaultMutableTreeNode(new IconData(icondt1,i,st12));
						usersql.add(datatb1);
					}
				}
			
			
			
				icontb=new ImageIcon("icon\\cmanager_s.gif");
				DefaultMutableTreeNode rolesq=new DefaultMutableTreeNode(new IconData(icontb,i,"Roles"));
				syste.add(rolesq);
				stSQL = new String("USE "+st+" EXEC sp_helprole" );
				stmts1=cons.createStatement();
				rs1=stmts1.executeQuery(stSQL);
				while (rs1.next()){
					String st12 = rs1.getString("RoleName");
					ImageIcon icondt1=new ImageIcon("icon\\oracle_sysman_hotstandby_config.gif");
					DefaultMutableTreeNode datatb1=new DefaultMutableTreeNode(new IconData(icondt1,i,st12));
					rolesq.add(datatb1);
				}

			}	
			
			
			String xsql1="sp_helpsrvrole";
			
			ResultSet rs1=stmts.executeQuery(xsql1);
			while (rs1.next()){
					st = rs1.getString("ServerRole");
					ImageIcon iconsy=new ImageIcon("icon\\Service.gif");
					DefaultMutableTreeNode sy=new DefaultMutableTreeNode(new IconData(iconsy,i,st));
					serverrole.add(sy);
			}
			
			
		
			
			
			xsql1="sp_helplogins";
			rs1=stmts.executeQuery(xsql1);
			while (rs1.next()){
				st = rs1.getString("LoginName");
				ImageIcon iconlo=new ImageIcon("icon\\oracle_metrics_server.gif");

				DefaultMutableTreeNode sy1=new DefaultMutableTreeNode(new IconData(iconlo,i,st));
				login.add(sy1);
				}
		}catch(SQLException ex){
			System.out.println("SQL");
			}
		
	//	return dbserver;		
}
public boolean Check(String st1, Vector vt){
	for(int i=0;i<vt.size();i++){
		if(st1.equals((String)vt.elementAt(i))){
			return true;
		}
	}
	return false;

}
}
