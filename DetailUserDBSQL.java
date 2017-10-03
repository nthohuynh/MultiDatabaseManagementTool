import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.util.*;
import com.sun.java.swing.*;

public class DetailUserDBSQL extends JPanel implements ActionListener{
	JLabel jllogin, jluser;

	JList jlsrvrole,jlistdb,jlistdbrole;

	JButton jbok, jbcancel;

	TitledBorder tbd;
	Border border;
	GridBagLayout gb;
	GridBagConstraints gbc;

	JPanel jPanel,jPanelBut, jPanelLabel;
	static Vector dataCombo;
	Connection cons;
	String xsql1="";
	static CheckableItem[] items;
	int stt,stt1;
	String dbname, username,lb;
	static JFrame myFrame;
	Statement stm;
	ResultSet rs;
	static Connection conss;
	public DetailUserDBSQL(String db, String user, Connection con){
	
		cons=con;
		dbname=db;
		username=user;
		setLayout(new BorderLayout());
			
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();
		
		
	
		
		//tab2
		jPanel=new JPanel();
		jPanel.setLayout(new BorderLayout());
		jPanel.setBorder(new TitledBorder(border, "Thành viên"));
		
		createData(dbname,username,"Group_Name");
		jlsrvrole=new JList(items);
		
		jlsrvrole.setCellRenderer(new CheckListRenderer());
	    jlsrvrole.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    jlsrvrole.setBorder(new EmptyBorder(0,4,0,0));
	    jlsrvrole.addMouseListener(new MouseAdapter() {
     	public void mouseClicked(MouseEvent e) {
        int index = jlsrvrole.locationToIndex(e.getPoint());
        CheckableItem item = (CheckableItem)jlsrvrole.getModel().getElementAt(index);
        item.setSelected(! item.isSelected());
        Rectangle rect = jlsrvrole.getCellBounds(index, index);
        jlsrvrole.repaint(rect);
      }
    });   
	
		JScrollPane scrollPane = new JScrollPane(jlsrvrole);
		scrollPane.setBorder(new TitledBorder(border, "Thành viên c\u1EE7a nhóm quy\u1EC1n c\u01A1 s\u1EDF d\u1EEF li\u1EC7u"));

		jPanel.add(scrollPane,"Center");
		jPanelLabel=new JPanel();
		jPanelLabel.setLayout(new BorderLayout());
		jluser=new JLabel("                  Tên ng\u01B0\u1EDDi dùng: "+username);
		try{
			stm=cons.createStatement();
			rs=stm.executeQuery("use "+dbname+" exec sp_helpuser");
			while(rs.next()){
				if(rs.getString("UserName").equals(username))
					lb=rs.getString("LoginName");
			}
		}catch(Exception s){}
		jllogin=new JLabel("                  Tên \u0111\u0103ng nh\u1EADp: "+lb);
		
		jPanelLabel.add(jluser, "North");
		jPanelLabel.add(jllogin, "South");
		
		jPanel.add(jPanelLabel,"North");
		
		
		
		
		
		
		
		
		JTabbedPane tabDBMS = new JTabbedPane();
		tabDBMS.add(jPanel, "  General ");

		if(!username.equals("dbo")){
			GrantUserDBSQL gra=new GrantUserDBSQL(dbname,username,cons);
			tabDBMS.add(gra, "  Permission ");
		}
		


		jPanelBut=new JPanel();
		jPanelBut.setLayout(gb);
		
		jbok=new JButton("L\u01B0u");
		jbok.addActionListener(this);
		add(jPanelBut,jbok,1,1,1,1);
		
			
		add(tabDBMS,BorderLayout.CENTER);
		add(jPanelBut, BorderLayout.SOUTH);

	}

public  void createData(String db,String user, String dk)  {
    int i=0;
    stt1=0;
    ResultSet rs1,rs2,rs3;
    boolean bl=false;
    	try{
			Statement stmt1=cons.createStatement();
			Statement stmt2=cons.createStatement();
			xsql1="use "+db + " exec sp_helpgroup ";
			
			rs1=stmt1.executeQuery(xsql1);
		  	try {
				while(rs1.next()){
					stt1++;
				}
		    } catch (SQLException ex) {System.out.println(ex);};
			items = new CheckableItem[stt1];

   			rs2=stmt1.executeQuery(xsql1);
   		
   			
     		
     	
 			
		    while (rs2.next()) {
		    	
		    	String xsql2="use "+db + " exec sp_helpuser '"+user+"'";
   				rs3=stmt2.executeQuery(xsql2);
		    	while(rs3.next()){
		    		
		    		if(((rs3.getString("GroupName")).equals(rs2.getString(dk)))||(rs2.getString(dk).equals("public")))
		    		{
		    			bl=true;
		    			break;
		    		}
		    		
		    	}
		    	
		      	items[i] = new CheckableItem(rs2.getString(dk),bl);
		      	bl=false;
		      	i++;
		    }
     	}catch(SQLException e){};



  }
public class CheckableItem {
    private String  str;
    private boolean isSelected;
    protected boolean m_selected;

    public CheckableItem(String str, boolean b) {
      this.str = str;
      isSelected = b;
    }
    
    public void setSelected(boolean b) {
      isSelected = b;
    }
    
    public boolean isSelected() {
      return isSelected;
    }
    
    public String toString() {
      return str;
    }
    public void invertSelected() { 
		m_selected = !m_selected; 
	}
  }
  
class CheckListRenderer extends JCheckBox implements ListCellRenderer {
    
    public CheckListRenderer() {
      setBackground(UIManager.getColor("List.textBackground"));
      setForeground(UIManager.getColor("List.textForeground"));
    }

    public Component getListCellRendererComponent(JList list, Object value,
               int index, boolean isSelected, boolean hasFocus) {
      setEnabled(list.isEnabled());
      setSelected(((CheckableItem)value).isSelected());
      setFont(list.getFont());
      setText(value.toString());
      return this;
    }
 } 

public void add(JPanel jPanel, Component c, int x, int y, int nx, int ny){
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=nx;
		gbc.gridheight=ny;
		gb.setConstraints(c,gbc);
		jPanel.add(c,gbc);
}

/* public void getDB(String xsql, String name){
  	ResultSet rs2;
  	stt=0;
    try{
		 	Statement  stmt=cons.createStatement();
		//	cons.setAutoCommit(true);
  			ResultSet rs=stmt.executeQuery(xsql);
		  
 			 try {
				while(rs.next()){
					stt++;
				}
		    } catch (SQLException ex) {System.out.println(ex);};
		    
   			dataCombo = new Vector(stt);
   			
		     rs2 = stmt.executeQuery(xsql);

    
		    while (rs2.next()) {
		      dataCombo.add(rs2.getString(name));
		    }
    
    }catch(SQLException e){};

 }*/
 public void actionPerformed(ActionEvent ae){
 	String XSQL="";
		if (ae.getActionCommand().equals("L\u01B0u")){
			
				ListModel model = jlsrvrole.getModel();
		        int n = model.getSize();
		      
				try{
					Statement stmt = cons.createStatement();
					
					for (int i=0;i<n;i++) {
			        	CheckableItem item = (CheckableItem)model.getElementAt(i);
			          	if ((item.isSelected())&& !item.toString().equals("public")) {
				            XSQL="use "+dbname+" exec sp_addrolemember '"+item.toString()
				            +"', '"+username+"'";
				             
			          	}else if((!item.isSelected())&& !item.toString().equals("public"))
			          		XSQL="use "+dbname+" exec sp_droprolemember '"+item.toString()
				            +"', '"+username+"'";
			          	else if((!item.isSelected())&& item.toString().equals("public"))
			          		JOptionPane.showMessageDialog(this, "\nKhông th\u1EC3 xóa thành viên ra kh\u1ECFi public",
	                                      "Thông báo", 1);
			          	stmt.execute(XSQL);
			          	
			        }	
		        	
		        	JOptionPane.showMessageDialog(this, "\u0110ã l\u01B0u",
	                                      "Thông báoessage", 1);
				}catch(Exception ew){System.out.println(ew);
				JOptionPane.showMessageDialog(this, "Không th\u1EC3 th\u1EF1c hi\u1EC7n \u0111\u01B0\u1EE3c",
	                                      "Thông báo", 1);
	            }
            
		
		}//ket thuc button OK
		if (ae.getActionCommand().equals("B\u1ECF qua")){
		}

}
public static void main(String[] args) 
	{
		myFrame =new JFrame("as");
		
		try{
			
 			DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
    		conss=DriverManager.getConnection ( "jdbc:microsoft:sqlserver://THODN:1433","sa","abc123");
			if(conss!=null){
				System.out.println("Ket noi thanh cong");
			}	
			}catch(SQLException ex){
			System.out.println("SQL");
			}

		DetailUserDBSQL usermanager=new DetailUserDBSQL("Northwind","dbo",conss);
		myFrame.getContentPane().add(usermanager);
		
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(500,400);
		myFrame.setVisible(true);
 
}
}






