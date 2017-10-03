import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.util.*;
import com.sun.java.swing.*;

public class NewLoginSQL extends JFrame implements ActionListener{
	JLabel jlname, jlpass, jldb, jllang,jluser;
	JTextField jtname,jtuser;
	JPasswordField jtpass;
	JComboBox jcdb, jclang;
	JList jlsrvrole,jlistdb,jlistdbrole;
	JButton jbok, jbcancel;
	Container contenPane;
	TitledBorder tbd1, tbd2,tbd3;
	Border border1, border2, border3;
	GridBagLayout gb,gbl,gb2;
	GridBagConstraints gbc, gbcl,gbc2;
	JPanel jPanel1,jPanel2,jPanel3,jPanel,jPanel4,jPanel5;
	static Vector dataCombo;
	Connection cons;
	String xsql1="";
	static CheckableItem[] items;
	int stt,stt1;
	public NewLoginSQL(String title, Connection con){
		super(title);
		cons=con;
		setSize(400,300);
		this.setResizable(false);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				dispose();
			}
		});
		contenPane=getContentPane();
		contenPane.setLayout(new BorderLayout());
		
		jPanel1 = new JPanel();
		jPanel1.setLayout(gb=new GridBagLayout());
		jPanel1.setBorder(new TitledBorder(border1, "T\u1EA1o ng\u01B0\u1EDDi dùng"));
		gbc=new GridBagConstraints();
		gbc.anchor=GridBagConstraints.WEST;
		gbc.fill=GridBagConstraints.BOTH;
		
		jlname=new JLabel("Tên \u0111\u0103ng nh\u1EADp:");
		jtname=new JTextField(20);
		
		jluser=new JLabel("Tên ng\u01B0\u1EDDi dùng:");
		jtuser=new JTextField(20);
			
		jlpass=new JLabel("M\u1EADt kh\u1EA9u:");
		jtpass=new JPasswordField(20);
		
		add(jPanel1,jluser,1,1,2,1);
		add(jPanel1,jtuser,3,1,2,1);
		
		add(jPanel1,jlname,1,3,2,1);
		add(jPanel1,jtname,3,3,2,1);
		
		add(jPanel1,jlpass,1,5,2,1);
		add(jPanel1,jtpass,3,5,2,1);
		
		
		jPanel2 = new JPanel();
		jPanel2.setLayout(gb=new GridBagLayout());
		jPanel2.setBorder(new TitledBorder(border1, "M\u1EB7c \u0111\u1ECBnh"));
		try{
			getDB("sp_databases","DATABASE_NAME");
		}catch(Exception e){ System.out.println("Can not add data in database");};
		jldb=new JLabel("C\u01A1 s\u1EDF d\u1EEF li\u1EC7u:");
		jcdb=new JComboBox(dataCombo);
		
		try{
			getDB("sp_helplanguage","alias");
		}catch(Exception e){ System.out.println("Can not add data in database");};
		jllang=new JLabel("Ngôn ng\u1EEF:");
		jclang=new JComboBox(dataCombo);
		

		add(jPanel2,jldb,1,1,2,1);
		add(jPanel2,jcdb,3,1,2,1);
		
		add(jPanel2,jllang,1,3,2,1);
		add(jPanel2,jclang,3,3,2,1);
		
		jPanel=new JPanel();
		jPanel.setLayout(new BorderLayout());
		
		jPanel.add(jPanel1,"North");
		jPanel.add(jPanel2,"Center");
		
		
		
		//tab2
		jPanel3=new JPanel();
		jPanel3.setLayout(new BorderLayout());
		jPanel3.setBorder(new TitledBorder(border1, "Server Roles"));
		
		createData("master","sp_helpsrvrole","ServerRole");
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
		jPanel3.add(scrollPane,"Center");
		
		//-----------------------tab 3
		
		jPanel4=new JPanel();
		jPanel4.setLayout(new BorderLayout());
		jPanel4.setBorder(new TitledBorder(border1, "Database Access"));
		
		jPanel5=new JPanel();
		jPanel5.setLayout(new BorderLayout());
		jPanel5.setBorder(new TitledBorder(border1, "Database Role"));
		
		
		
		createData("master","sp_databases","DATABASE_NAME");
		jlistdb=new JList(items);
			
		jlistdb.setCellRenderer(new CheckListRenderer());
	    jlistdb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    jlistdb.setBorder(new EmptyBorder(0,4,0,0));
	    jlistdb.addMouseListener(new MouseAdapter() {
     	public void mouseClicked(MouseEvent e) {
        int index = jlistdb.locationToIndex(e.getPoint());
        CheckableItem item1 = (CheckableItem)jlistdb.getModel().getElementAt(index);
        item1.setSelected(! item1.isSelected());
	        Rectangle rect = jlistdb.getCellBounds(index, index);
	        jlistdb.repaint(rect);
	        
	     /*   recalcTotal(item1.toString());
	        JScrollPane scrollPane2 = new JScrollPane(jlistdbrole);
			jPanel5.add(scrollPane2,"Center");
			jPanel5.repaint();*/
			
	      }
	    });   
	
		JScrollPane scrollPane1 = new JScrollPane(jlistdb);
		jPanel4.add(scrollPane1,"Center");
		//
		
		
		
	
	/*	JSplitPane sp1 = new JSplitPane(
		JSplitPane.VERTICAL_SPLIT, jPanel4, jPanel5);
		sp1.setDividerLocation(200);*/
		
		///-het p4------------------------------------
		
		
		JTabbedPane tabDBMS = new JTabbedPane();
		tabDBMS.add(jPanel, "  General   ");
		tabDBMS.add(jPanel3, "  Server Roles ");
		tabDBMS.add(jPanel4, "  Database Access ");

		
		JPanel pnbt=new JPanel();
		pnbt.setLayout(new FlowLayout());
		jbok=new JButton("\u0110\u1ED3ng ý");
		jbok.addActionListener(this);
		jbcancel=new JButton("B\u1ECF qua");
		jbcancel.addActionListener(this);
		pnbt.add(jbok);
		pnbt.add(jbcancel);
		
		contenPane.add(tabDBMS,BorderLayout.CENTER);
		contenPane.add(pnbt,BorderLayout.SOUTH);

	}

public  void createData(String usr,String xsql, String dk)  {
    int i=0;
    stt1=0;
    ResultSet rs1,rs2;
    	try{
			Statement stmt1=cons.createStatement();
			 xsql1="use "+usr + " exec "+ xsql+"";
			
			 rs1=stmt1.executeQuery(xsql1);
		   try {
				while(rs1.next()){
					stt1++;
				}
		    } catch (SQLException ex) {System.out.println(ex);};
			items = new CheckableItem[stt1];

   			rs2=stmt1.executeQuery(xsql1);
		    while (rs2.next()) {
		      items[i] = new CheckableItem(rs2.getString(dk));
		      i++;
		    }
     	}catch(SQLException e){};



  }
public class CheckableItem {
    private String  str;
    private boolean isSelected;
    protected boolean m_selected;

    public CheckableItem(String str) {
      this.str = str;
      isSelected = false;
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

 public void getDB(String xsql, String name){
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

 }
 public void actionPerformed(ActionEvent ae){
 	String SQL, XSQL,XSQL1;
 	String SQL1="";
		if (ae.getActionCommand().equals("\u0110\u1ED3ng ý")){
				SQL="sp_addlogin @loginame ='"+jtname.getText()+"', @passwd='"
					+jtpass.getText()+"',@defdb='"+(String)jcdb.getSelectedItem()
					+"',@deflanguage='"+(String)jclang.getSelectedItem()+"'";
				SQL1="sp_adduser '"+jtname.getText()+"','"+jtuser.getText()+"'";
				System.out.println(SQL+"\n"+SQL1);
				
				ListModel model = jlsrvrole.getModel();
		        int n = model.getSize();
		        ListModel model1 = jlistdb.getModel();
		        int m = model1.getSize();
				try{
					Statement stmt = cons.createStatement();
							  stmt.execute(SQL);
					if(!jtuser.getText().equals(""))stmt.execute(SQL1);
					
					for (int i=0;i<n;i++) {
			        	CheckableItem item = (CheckableItem)model.getElementAt(i);
			          	if (item.isSelected()) {
				            XSQL="sp_addsrvrolemember @loginame='"+jtname.getText()
				            +"', @rolename='"+item.toString()+"'";
				             stmt.execute(XSQL);
				             System.out.println(XSQL+"\n");
			          	}
			        }	
		        	
		        	for (int i=0;i<m;i++) {
			        	CheckableItem item1 = (CheckableItem)model1.getElementAt(i);
			          	if (item1.isSelected()) {
				            XSQL1="use "+item1.toString()+" exec sp_grantdbaccess @loginame='"+jtname.getText()
				            +"', @name_in_db='"+jtuser.getText()+"'";
				             if(!jtuser.getText().equals(""))stmt.execute(XSQL1);
				             System.out.println(XSQL1+"\n");
			          	}
			          	
			        }	
					JOptionPane.showMessageDialog(this, "Create login success",
	                                      "Message", 1);
	                dispose();
				}catch(Exception ew){System.out.println(ew);
				JOptionPane.showMessageDialog(this, "Test information",
	                                      "Message", 1);
	            }
            
		
		}//ket thuc button OK
		if (ae.getActionCommand().equals("B\u1ECF qua")){
			dispose();		
		}

}
 
}






