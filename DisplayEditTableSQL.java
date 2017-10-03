import javax.swing.table.*;
import javax.swing.event.*;
import java.util.Vector;
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.text.Format.*;

public class DisplayEditTableSQL extends AbstractTableModel {
  static Vector tencot;
  static Vector tencotcu;
  static Vector kieudl;
  static Vector kichthuoc;
  static Vector nul,nulcu,nultmp;
  static Vector macdinh;
  String columnNames[] = {"column_name","type_name" ,"length",
  "nullable","column_def"};
  static Statement stmt;
  static ResultSet rs;
  static String xsql,st1;
  static int stt;
  static String user1, table1;
  static Connection cons;
  public DisplayEditTableSQL(String user,String table,Connection cons1) {
      
      user1=user;
      table1=table;
      cons=cons1;
      rs = getRS();
   
      try {
        rs.last();
        stt = rs.getRow();
        // System.out.println(stt);
      }
      catch (SQLException ex) {};
           
      tencot = new Vector(stt);
      tencotcu = new Vector(stt);
      kieudl = new Vector(stt);
      kichthuoc = new Vector(stt);
      nul=new Vector(stt);
      nultmp=new Vector(stt);
      macdinh=new Vector(stt);
      nulcu=new Vector(stt);
      try {
        rs.beforeFirst();
      }
      catch (SQLException ex) {
        JOptionPane.showMessageDialog(null,ex,"Message",1);
      }

      try{
      while (rs.next()) {
        tencot.add(rs.getString("column_name"));
        tencotcu.add(rs.getString("column_name"));
        kieudl.add(rs.getString("type_name"));
        kichthuoc.add(rs.getString("length"));
        nul.add(rs.getString("nullable"));
        nultmp.add(rs.getString("nullable"));
        nulcu.add(rs.getString("nullable"));
        macdinh.add(rs.getString("column_def"));
      }
      }catch(SQLException e){}
      if (EditTableSQL.kt_them){
          tencot.add("");
          tencotcu.add("");
          kieudl.add("");
          kichthuoc.add("");
          nul.add("");
          nultmp.add("");
          nulcu.add("");
          macdinh.add("");
      }
  }

  public int getRowCount(){
    return tencot.size();
  }

  public int getColumnCount(){
    return columnNames.length;
  }

  public Object getValueAt(int row,int column){
    if (column == 0) return (String)tencot.get(row);
    else if (column == 1) return (String)kieudl.get(row);
    else if (column == 2) return (String)kichthuoc.get(row);
    else if (column == 3) return (String)nul.get(row);
    else  return (String)macdinh.get(row);
  }

  public String getColumnName(int column){
    return columnNames[column];
  }

  public void setValueAt(Object value, int row, int column){
    if (column == 0) tencot.set(row, value);
    if (column == 1) kieudl.set(row, value);
    if (column == 2) kichthuoc.set(row, value);
    if (column == 3) nul.set(row, value);
    if (column == 4) macdinh.set(row, value);
    fireTableChanged(new TableModelEvent(this,row,column));
  }

  public boolean isCellEditable(int row, int col) {
    return true;
  }

  //public Class getColumnClass(int c) {return getValueAt(0, c).getClass();}

  public static ResultSet getRS()
  {
  /*	try{
		 	DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
    		cons=DriverManager.getConnection ( "jdbc:microsoft:sqlserver://BKDN-BMNJMC9ZPY:1433","sa","abc123");
				//System.out.print("OK");
		}catch(Exception se){
			System.out.println(se.toString());
		}*/
      try {
        stmt = cons.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                          ResultSet.CONCUR_UPDATABLE);
        
        xsql = "use "+user1+" exec sp_columns @table_name ="+table1+"";
        rs = stmt.executeQuery(xsql);
      //  rs.next();
      }
      catch (SQLException ex) {
        JOptionPane.showMessageDialog(null,ex,"Message",1);
      }
     return rs;
  }

  public void reFresh() {
    try {
      
    } catch (Exception e) {};
  }
}
