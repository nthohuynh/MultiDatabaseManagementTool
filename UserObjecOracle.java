import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;

public class UserObjecOracle extends JPanel
{

	String stSQL;
	JPanel jpTable,jpOption;
	GridBagLayout gb;
	GridBagConstraints gbc;
	JScrollPane jscPane,resultPane;
	Connection con;
	public static JTable tableResult;
	String user1, st;

	public UserObjecOracle(String user,Connection cons){
		con=cons;

		stSQL=new String("select object_name, object_type from all_objects where owner='"+user+"'");
		user1=user;
		setLayout(new BorderLayout());

		gb=new GridBagLayout();
		gbc=new GridBagConstraints();
	
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("\u0110\u1ED1i t\u01B0\u1EE3ng t\u1EA1i schema "+user1 );
		jlObject.setIcon(new ImageIcon("icon\\cmanager_b.gif         "));
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 20));
		jPanel.add(jlObject);


		
		resultPane=new JScrollPane();
		
		
		add(resultPane,"Center");
		if(con!=null)
		executeStatement(stSQL);
		add(jPanel, "North");


	}
public void add(JPanel jPanel,Component c, int x, int y, int nx, int ny){
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=nx;
		gbc.gridheight=ny;
		gb.setConstraints(c,gbc);
		jPanel.add(c,gbc);
	}
public void executeStatement(String sql){
		if(con!=null)
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

//-----------------Ham chinh cua chuong trinh----------------
/*	public static void main(String[] args) 
	{
		myFrame =new JFrame("as");
		try{
     	 	Class.forName("oracle.jdbc.driver.OracleDriver");
			con1=DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:dbTHO", "system", "abc123");
			//System.out.print("OK");
		}catch(Exception se){
			System.out.println(se.toString());
		}
 
		UserObjecOracle usermanager=new UserObjecOracle("SYS",con1);
		myFrame.getContentPane().add(usermanager);
		
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(800,500);
		myFrame.setVisible(true);
	}

*/

}
