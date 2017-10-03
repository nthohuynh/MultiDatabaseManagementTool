import java.awt.*;
import java.util.*;
import java.awt.event.*;
import com.sun.java.swing.*;
import javax.swing.*;
import java.sql.*;
import java.sql.SQLException;
import javax.swing.border.*;
import javax.swing.text.*;

public class AddTable extends JFrame implements java.awt.event.ContainerListener, java.awt.event.KeyListener, ActionListener, ItemListener {
	
	static JFrame myFrame;
	JLabel jlTablename,jlUser,jlTablespace,jlColumnname,jlDatatype,jlSize, jlKey, jlFkey,jlNon, jlDefault, jlFkuser, jlFktable, jlFkcolumn;
	JTextField jtTablename,jtUser,jtTablespace,jtColumnname,jtDatatype,jtSize, jtKey, jtFkey,jtDefault;
	JComboBox jcbUser, jcbTablespace,jcbDatatype, jcbFkuser,jcbFktable,  jcbFkcolumn;
	JButton jbAdd,jbCancel,jbDetail,jbAddcolumn,jbEdit,jbRemove;
	JPanel jPanel,jPanel1, jPanel2,jpTable;
	GridBagLayout gb,gbl,gb2;
	GridBagConstraints gbc, gbcl,gbc2;
	String dataCombo2[] = {"TEMP"};
	static String data[] = {
        "CHAR", "VARCHAR2", "NCHAR", "NVARCHAR2", "NUMBER",
        "RAW", "DATE", "LONG", "LONGRAW", "ROWID", "BLOB", "CLOB",
        "NCLOB", "BFILE", "UROWID", "FLOAT"};

	String stSQL="SELECT OWNER,  TABLE_NAME,  TABLESPACE_NAME,  CLUSTER_NAME  FROM dba_tables";
	boolean type;
	TitledBorder tbd1, tbd2;
	Border border1, border2;
	Container contenPane;
	static Connection con;
	JTable tableResult;
	JScrollPane jscPane,resultPane;
	Image img;
	Vector vData,vTitle,row;
 	static Vector tencot, kieudl, kichthuoc, nul, macdinh;
  	static Vector cotkhoachinh, trangthaikc, cotkhoaphu, trangthaikn;
 	static Vector tenkp, tenkptmp, ndkp, bangkp, cotkp, ndkptmp, bangkptmp,
      cotkptmp;
	static Vector dataCombo, dataCombo1;
	static ResultSet rs;
	Statement stmt;
	String xsql;
	int stt;
	JCheckBox checkboxNull, jchbKey, jchbFkey;
	String st = "", st1, st2, st3, stkc, stkp, stsave;
	String us1;

//--------Ham cau tu------------------------------
	public AddTable(String title,String us,Connection cons) {
		super(title);
		con=cons;
		us1=us;
		setSize(500,500);
		this.setResizable(false);
		//khung chua chinh		
		jPanel = new JPanel();
		contenPane=getContentPane();
		contenPane.setLayout(new BorderLayout());


		jPanel.setLayout(gb=new GridBagLayout());
		gbc=new GridBagConstraints();
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
			//	System.exit(0);
				dispose();

			}
		});
		
		OptionListener optionListener = new OptionListener();
   	 	khoitao_Vector();

		//Panel1
		jPanel1=new JPanel();
		jPanel1.setLayout(gbl=new GridBagLayout());
		gbcl=new GridBagConstraints();
	
		tbd1=new TitledBorder(border1, "T\u1EA1o b\u1EA3ng");
		jPanel1.setBorder(tbd1);
		//Panel2
		jPanel2=new JPanel();
		jPanel2.setLayout(gb2=new GridBagLayout());
		gbc2=new GridBagConstraints();
		tbd2=new TitledBorder(border2, "\u0110\u1ECBnh ngh\u0129a các tr\u01B0\u1EDDng");
		jPanel2.setBorder(tbd2);
		
		
		//them vao panel 1
		gbcl.anchor=GridBagConstraints.WEST;
		gbcl.fill=GridBagConstraints.BOTH;

		jlTablename=new JLabel("Tên b\u1EA3ng:");
		jtTablename=new JTextField(15);
	
		jlUser=new JLabel("Schema:");
		try{
		query("dba_users", "username");
		}catch(Exception e){ System.out.println("Can not add data in username");};
		jcbUser=new JComboBox(dataCombo);
		jcbUser.setSelectedItem(us);
		jlTablespace=new JLabel("Không gian b\u1EA3ng :");
		try{
		query("dba_tablespaces", "tablespace_name");
		}catch(Exception e){ System.out.println("Can not add in table space");};
		jcbTablespace=new JComboBox(dataCombo);
		jcbTablespace.setSelectedIndex(0);
		
		jbAdd=new JButton("L\u01B0u");
		jbAdd.addActionListener(this);
		
		add(jPanel1,jlTablename,1,1,2,1);
		add(jPanel1,jtTablename,3,1,2,1);
		add(jPanel1,jlUser,1,2,2,1);
		add(jPanel1,jcbUser,3,2,4,1);
		add(jPanel1,jlTablespace,1,3,2,1);
		add(jPanel1,jcbTablespace,3,3,4,1);
		
		gbcl.fill=GridBagConstraints.HORIZONTAL;
		add(jPanel1,jbAdd,6,3,2,1);
		//het panel1
		
	/*--------------bat dau panel2--------------*/

		gbc2.anchor=GridBagConstraints.WEST;
		gbc2.fill=GridBagConstraints.HORIZONTAL;

		jlColumnname =new JLabel("Tên tr\u01B0\u1EDDng:");
		jtColumnname = new JTextField(10);
		
		
		jlDatatype =new JLabel("Ki\u1EC3u d\u1EEF li\u1EC7u:");
		jcbDatatype = new JComboBox(data);
		
		jlSize=new JLabel("Kích th\u01B0\u1EDBc:");
		jtSize=new JTextField(10);
		jtSize.setDocument(new DigitsDocument());
		
		
		jchbKey=new JCheckBox("");
		
		
		jlKey =new JLabel("Khoá chính:");
		jtKey =new JTextField(10);
	
		jchbFkey=new JCheckBox("");
		jchbFkey.addActionListener(optionListener);
		
	
		jlFkey=new JLabel("Tên khoá ngo\u1EA1i:");
		jtFkey=new JTextField(10);

		jlDefault=new JLabel("M\u1EB7c \u0111\u1ECBnh:");
		jtDefault = new JTextField(10);
		
		jlFkuser=new JLabel("Schema:");
		try{
			query("dba_users", "username");
		}catch(Exception e){ System.out.println("Can not add data in username");};
		jcbFkuser=new JComboBox(dataCombo);
		
		
		jlFktable =new JLabel("B\u1EA3ng:");
		try{
			query2("dba_tables", "table_name", "owner",
                jcbFkuser.getSelectedItem().toString());
		}catch(Exception e){ System.out.println("Can not add data in tablename");};
		jcbFktable=new JComboBox(dataCombo1);
	
		jcbFkuser.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				jcbFktable.removeAllItems();

				try{
				query2("dba_tables", "table_name", "owner",
                jcbFkuser.getSelectedItem().toString());
				}catch(Exception ex){ System.out.println("Can not add data in tablename");};
				for(int i=0; i < dataCombo1.size(); i++){
				jcbFktable.addItem(dataCombo1.get(i));
				jcbFktable.setSelectedItem(dataCombo1.get(0));
				}
			}
		});
	
		
		jlFkcolumn=new JLabel("Tr\u01B0\u1EDDng:");
		try{
			query3("column_name", jcbFkuser.getSelectedItem().toString(),
                jcbFktable.getSelectedItem().toString());
 		}catch(Exception e){ System.out.println("Can not add data in username");};
		jcbFkcolumn=new JComboBox(dataCombo);
		
		jcbFktable.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				jcbFkcolumn.removeAllItems();

				try{
					query3("column_name", jcbFkuser.getSelectedItem().toString(),
                jcbFktable.getSelectedItem().toString());
				}catch(Exception ex){ System.out.println("Can not add data in tablename");};
				for(int i=0; i < dataCombo.size(); i++){
				jcbFkcolumn.addItem(dataCombo.get(i));
				jcbFkcolumn.setSelectedItem(dataCombo.get(0));
				}
			}
		});
		
		jcbFkuser.setEnabled(false);
		jcbFktable.setEnabled(false);
		jcbFkcolumn.setEnabled(false);
		jtFkey.setEditable(false);
	
		checkboxNull =new JCheckBox();
		checkboxNull.setText("Not Null");
		checkboxNull.setSelected(true) ;
		
		
		jlNon =new JLabel("");
		jbAddcolumn=new JButton("Thêm tr\u01B0\u1EDDng");
		jbAddcolumn.addActionListener(this);
		jbEdit=new JButton(" Edit ");
		jbEdit.addActionListener(this);
		
		jbRemove=new JButton("Remove");
		jbRemove.addActionListener(this);
		
		add(jPanel2,jlColumnname,1,1,2,1);
		add(jPanel2,jtColumnname,3,1,3,1);
		
		
		add(jPanel2,jlDatatype,1,2,2,1);
		add(jPanel2,jcbDatatype,3,2,3,1);
		
		add(jPanel2,jlSize,1,3,2,1);
		add(jPanel2,jtSize,3,3,3,1);
	
		add(jPanel2,jlDefault,1,4,1,1);
		add(jPanel2,jtDefault,3,4,3,1);
		
		add(jPanel2,checkboxNull,1,5,1,1);

		add(jPanel2,jchbKey,2,6,1,1);
		add(jPanel2,jlKey,3,6,1,1);
		add(jPanel2,jtKey,4,6,1,1);
		
	
		
	JPanel thamchieu=new JPanel();
	thamchieu.setLayout(gb2=new GridBagLayout());
	gbc2=new GridBagConstraints();
	//them vao panel 1
//	gbcl.anchor=GridBagConstraints.WEST;
//	gbcl.fill=GridBagConstraints.BOTH;
	
	
	tbd2=new TitledBorder(border2, "Khoá ngo\u1EA1i");
	thamchieu.setBorder(tbd2);
	JLabel ths=new JLabel("Tham chi\u1EBFu \u0111\u1EBFn");
	
		add(thamchieu,jchbFkey,1,1,1,1);
		add(thamchieu,jlFkey,2,1,1,1);
		add(thamchieu,jtFkey,3,1,1,1);
		
		add(thamchieu,ths,1,2,2,1);	
		
		add(thamchieu,jlFkuser,2,3,1,1);
		add(thamchieu,jcbFkuser,3,3,1,1);
		add(thamchieu,jlFktable,2,4,1,1);
		add(thamchieu,jcbFktable,3,4,1,1);
		add(thamchieu,jlFkcolumn,2,5,1,1);
		add(thamchieu,jcbFkcolumn,3,5,1,1);
		
		
		add(jPanel2,thamchieu,2,7,3,6);
		

		add(jPanel2, jbAddcolumn, 1,12,1,1);
	//	add(jPanel2, jbEdit, 1,13,1,1);
	//	add(jPanel2, jbRemove, 9,4,1,1);
		/*------ket thuc panel2---------*/
		
	
		add(jPanel, jPanel1,1,1,30,15);
		add(jPanel, jPanel2,1,16,30,15);
		contenPane.add(jPanel,BorderLayout.NORTH);
		
	
				
/*----------Panel Table----------------*/
		jpTable=new JPanel();
		int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		resultPane=new JScrollPane(v,h);
		jpTable.setLayout(new BorderLayout());
		jpTable.add(resultPane,BorderLayout.CENTER);
		contenPane.add(jpTable,BorderLayout.CENTER);
		Vector vTitle=new Vector(5,0);
		vData =new Vector(5,0);
		row=new Vector(5,0);
	    addKeyAndContainerListenerRecursively(this); 
		
		
	};
//*---bat dau thu tuc de nhan phim ESC ---------
	public void componentAdded(ContainerEvent e)
        {
                addKeyAndContainerListenerRecursively(e.getChild());
        }

  public void componentRemoved(ContainerEvent e)
        {
                removeKeyAndContainerListenerRecursively(e.getChild());
        }

  private void addKeyAndContainerListenerRecursively(Component c)
        {
                c.addKeyListener(this);
                if (c instanceof Container)
                {
                        Container cont = (Container) c;
                        cont.addContainerListener(this);
                        Component[] children = cont.getComponents();
                        for (int i = 0; i < children.length; i++)
                        {
                                addKeyAndContainerListenerRecursively(children[
                                        i]);
                        }
                }
        }

        private void removeKeyAndContainerListenerRecursively(Component c)
        {
                c.addKeyListener(this);
                if (c instanceof Container)
                {
                        Container cont = (Container) c;
                        cont.removeContainerListener(this);
                        Component[] children = cont.getComponents();
                        for (int i = 0; i < children.length; i++)
                        {
                                addKeyAndContainerListenerRecursively(children[
                                        i]);
                        }
                }
        }
        public void keyPressed(KeyEvent e) 
        { 
                int code = e.getKeyCode(); 
                if(code == KeyEvent.VK_ESCAPE) 
                { 
                        // Phím nh?n là phím Escape. ?n h?p tho?i. 
                        setVisible(false); 
                } 

                // N?u mu?n x? lý trên các phím nh?n khác, 
                // vi?t mã c?a b?n ? dây (theo logic tuong t?) 
        } 

        public void keyReleased(KeyEvent e) 
        {	 
                // Mã x? lý c?a b?n ? dây 
        } 

        public void keyTyped(KeyEvent e) 
        { 
                // Mã x? lý  
        } 

        

        protected void processWindowEvent(WindowEvent e)
        {
                if (e.getID() == WindowEvent.WINDOW_CLOSING)
                {
                        cancel();
                }
                super.processWindowEvent(e);
        }

        void cancel()
        {
            //    dispose();
        }	
//	----ket thuc ma nhan esc---*/
public void itemStateChanged(ItemEvent es){
		try{
			query2("dba_tables", "table_name", "owner",
                jcbFkuser.getSelectedItem().toString());
		}catch(Exception e){ System.out.println("Can not add data in username");};
		jcbFktable.setSelectedItem(dataCombo1);
		AddTable gd=new AddTable("Create table",us1,con);
		gd.setVisible(true);
}
class OptionListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    JComponent c = (JComponent) e.getSource();
	     if (c == jchbFkey) {
                if (((JCheckBox)c).isSelected()) {
                   jcbFkuser.setEnabled(true);
                    jcbFktable.setEnabled(true);
                    jcbFkcolumn.setEnabled(true);
                    jtFkey.setEditable(true);

                    
                } 
              	if (!((JCheckBox)c).isSelected()) {
                    jcbFkuser.setEnabled(false);
                    jcbFktable.setEnabled(false);
                    jcbFkcolumn.setEnabled(false);
                    jtFkey.setEditable(false);

                }
		}
    }
   }
    

/*----------thu tuc them du lieu vao cac combobox----*/
  public ResultSet getRScombo1(String table, String column) {
  	
    xsql = "select " + column + " from " + table + "";
    try {
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
      rs = stmt.executeQuery(xsql);
      rs.next();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, ex + "Non Corresponding", "Message",
                                    1);
    }
    return rs;
  }
  
  public  void query(String table, String column) throws Exception {
    rs = getRScombo1(table, column);
    try {
      rs.last();
      stt = rs.getRow();
    } catch (SQLException ex) {System.out.println(ex);};
    dataCombo = new Vector(stt);
    try {
      rs.beforeFirst();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(this,
                                    ex +
                                    "Can't load data for ComboBox"
                                    , "Message", 1);
    }
    while (rs.next()) {
      dataCombo.add(rs.getString(column));
    }
    
  }
public ResultSet getRScombo2(String table, String column,
                               String column1, String dk) {
    xsql = "select " + column + " from " + table + " where " + column1 + "='" +
        dk + "'";
    try {
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
      rs = stmt.executeQuery(xsql);
      rs.next();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, ex + "Non Corresponding",
                                    "Message", 1);
    }
    return rs;
  }
  
  public void query2(String table, String column, String column1,
                     String dk) throws Exception {
    rs = getRScombo2(table, column, column1, dk);
    try {
      rs.last();
      stt = rs.getRow();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, "No exit Recordset",
                                    "Message", 1);
    }

    dataCombo1 = new Vector(stt);
    try {
      rs.beforeFirst();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(this,
                                    ex + "Can't load data for ComboBox"
                                    , "Message", 1);
    }
    while (rs.next()) {
      dataCombo1.add(rs.getString(column));
    }
  }

 public ResultSet getRScombo3(String column, String dk, String table) {
    xsql = "select " + column + " from dba_tab_columns where owner = '" + dk +
        "'";
    xsql = xsql + " and table_name = '" + table + "'";
    try {
      stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
      rs = stmt.executeQuery(xsql);
      rs.next();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, ex + "Non Corresponding",
                                    "Message", 1);
    }
    return rs;
  }
  public void query3(String column, String dk, String table) throws Exception {
    rs = getRScombo3(column, dk, table);
    try {
      rs.last();
      stt = rs.getRow();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, ex + "Non Corresponding"
                                    , "TMessage", 1);
    }
    dataCombo = new Vector(stt);
    try {
      rs.beforeFirst();
    }
    catch (SQLException ex) {
      JOptionPane.showMessageDialog(this,
                                    ex + "Can't load data for ComboBox"
                                    , "Message", 1);
    }
    while (rs.next()) {
      dataCombo.add(rs.getString(column));
    }
  }//ket thuc thu tuc them du lieu vao cac combobox
  
  
  
 /*-----thu tuc chi duoc nhap so vao truong jtSize---------*/ 
class DigitsDocument extends PlainDocument 
{
	public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException 
	{
		if (str == null) 
		{
			return;
		}
		char[] addedFigures = str.toCharArray();
		char c;
		for (int i = addedFigures.length; i > 0; i--) 
		{
			c = addedFigures[i-1];
			if(Character.isDigit(c))
			{
				super.insertString(offs, new String(new Character(c).toString()), a);
			}
		}
	}
}


public void add(JPanel jPanel, Component c, int x, int y, int nx, int ny){
		gbcl.gridx=x;
		gbcl.gridy=y;
		gbcl.gridwidth=nx;
		gbcl.gridheight=ny;
		gbl.setConstraints(c,gbcl);
		jPanel.add(c,gbcl);
}

public void displayData() {
	 
		
			Vector vTitle=new Vector(5,0);
		
			vTitle.add("Tên tr\u01B0\u1EDDng");
			vTitle.add("Ki\u1EC3u d\u1EEF li\u1EC7u ");
			vTitle.add("Kích th\u01B0\u1EDBc");
			vTitle.add("Not null?");
			vTitle.add("M\u1EB7c \u0111\u1ECBnh");
			if(!jtColumnname.getText().equals("")){
			addData();
			tableResult=new JTable(vData,vTitle);
			tableResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableResult.setSelectionBackground(new Color(220,100,100));
			tableResult.setGridColor(new Color(0,0,150));
			resultPane.setViewportView(tableResult);
			resultPane.setBorder(BorderFactory.createLineBorder(Color.lightGray,6));
			row=new Vector(5,0);
			}
			else JOptionPane.showMessageDialog(this, "Not correct",
                                    "Message", 1);

	}

	public void addData(){
		
		
		if (!jtColumnname.getText().equals("") &&!jtTablename.getText().equals("")) {
			
			/*--dua du llieu vao vData de hien thi--*/
			jtTablename.setEditable(false);
			row.add(jtColumnname.getText());
			row.add(jcbDatatype.getSelectedItem());
			row.add(jtSize.getText());
			    
			if( checkboxNull.isSelected()) row.add("Not Null");
			else row.add("");
			System.out.println(row);
			row.add(jtDefault.getText());
			vData.add(row);
			
			
			
			/*--dua du lieu vao cac vector de thuc hien cau truy van--*/
			tencot.add(jtColumnname.getText());
			kieudl.add(jcbDatatype.getSelectedItem());
			kichthuoc.add(jtSize.getText());
			if( checkboxNull.isSelected()) nul.add("Not Null");
			else nul.add("");
  			tenkptmp.add(jtFkey.getText());
  			macdinh.add(jtDefault.getText());
  			
  		if (jchbKey.isSelected() && !jtKey.getText().equals("")) {
	    	cotkhoachinh.add(jtColumnname.getText());
	        trangthaikc.add("true");
      	}
      	else
        	trangthaikc.add("false");
      	if (!jtKey.getText().equals(""))
        	jtKey.setEditable(false);
        	
      	if (jchbFkey.isSelected() && !jtFkey.getText().equals("")) {
	        cotkhoaphu.add(jtColumnname.getText());
	        trangthaikn.add("true");
	        tenkp.add(jtFkey.getText());
	        ndkp.add(jcbFkuser.getSelectedItem());
	        bangkp.add(jcbFktable.getSelectedItem());
	        cotkp.add(jcbFkcolumn.getSelectedItem());
	        ndkptmp.add(jcbFkuser.getSelectedItem());
	        bangkptmp.add(jcbFktable.getSelectedItem());
	        cotkptmp.add(jcbFkcolumn.getSelectedItem());
      	}
	    else {
	        trangthaikn.add("false");
	        ndkptmp.add("");
	        bangkptmp.add("");
	        cotkptmp.add("");
     	 }
     
      jtColumnname.setText("");
      jtSize.setText("");
      jtFkey.setText("");
      jtDefault.setText("");
      checkboxNull.setSelected(true);
      jchbKey.setSelected(false);
      jchbFkey.setSelected(false);
    }
    else {
      JOptionPane.showMessageDialog(this, "Not correct",
                                    "Message", 1);
    }
 }
	
	public void buttonOK(){
		st1 = "";
   			if (tencot.size() > 0) {
   				for (int i = 0; i < tencot.size() - 1; i++){
   					st1 = st1 + tencot.elementAt(i).toString() + " ";
   					st1 = st1 + kieudl.elementAt(i).toString();
   					if (!kichthuoc.elementAt(i).equals(""))
   					st1 = st1 + "(" + kichthuoc.elementAt(i).toString() + ")" + " ";
   					else st1=st1+"";
   					if (!macdinh.elementAt(i).equals(""))
   					st1=st1+"default '"+macdinh.elementAt(i).toString()+"' ";
   					else st1=st1+"";
   					st1 = st1 +" "+ nul.elementAt(i).toString() + ",";
   				}
	   			int k = tencot.size() - 1;
	   			st1 = st1 + tencot.elementAt(k).toString() + " ";
	   			st1 = st1 + kieudl.elementAt(k).toString();
	   			if (!kichthuoc.elementAt(k).equals(""))
	   				st1 = st1 + "(" + kichthuoc.elementAt(k).toString() + ")" + " ";
	   			else st1=st1+"";
	   			if (!macdinh.elementAt(k).equals(""))
	   			    st1=st1+"default '"+macdinh.elementAt(k).toString()+"' ";
	   			else st1=st1+"";
	   			st1 = st1 + " "+nul.elementAt(k).toString();
    		}
    		//Tao khoa chinh
    		stkc = "";
    		st2 = "";
    		if (cotkhoachinh.size() > 0) {
    			for (int i = 0; i < cotkhoachinh.size() - 1; i++) {
    				stkc = stkc + cotkhoachinh.elementAt(i).toString() + ",";
    			}
    			stkc = stkc + cotkhoachinh.elementAt(cotkhoachinh.size() - 1).toString();
    			st2 = st2 + ",constraint " + jtKey.getText() + " primary key(";
    			st2 = st2 + stkc + ")";
    		}
    		else
    		st2 = "";
    		
    		
      //Tao khoa ngoai
      		stkp = "";
      		if (cotkhoaphu.size() > 1) {
      			for (int i = 0; i < cotkhoaphu.size(); i++) {
      				stkp = stkp + " ,constraint " + tenkp.elementAt(i).toString() +
            		" foreign key(";
            	 	stkp = stkp + cotkhoaphu.elementAt(i).toString() + ") ";
            	 	stkp = stkp + " references " + ndkp.elementAt(i).toString() + ".";
            	 	stkp = stkp + bangkp.elementAt(i).toString() + "(";
            	 	stkp = stkp + cotkp.elementAt(i).toString() + ")";
            	 }
            }
            else if (cotkhoaphu.size() == 1) {
            	stkp = stkp + ",constraint " + tenkp.elementAt(0).toString() +
          				" foreign key(";
          		stkp = stkp + cotkhoaphu.elementAt(0).toString() + ")";
          		stkp = stkp + " references " + ndkp.elementAt(0).toString() + ".";
          		stkp = stkp + bangkp.elementAt(0).toString() + "(";
          		stkp = stkp + cotkp.elementAt(0).toString() + ")";
          	}
          	st = "";
          	if (tencot.size() > 0) {
          		st = st + "create table " + jcbUser.getSelectedItem().toString() +
          		        ".";
          		st = st + jtTablename.getText() + "(";
          		st = st + st1 + st2 +stkp+ ")";
          		st = st + " tablespace " + jcbTablespace.getSelectedItem().toString() + "";
          	}
          	if (!st.equals("")) {
          		System.out.print(st);
          		try {
          			stmt = con.createStatement();
          			stmt.executeQuery(st);
          			System.out.print("OK");
          			JOptionPane.showMessageDialog(this, "Creater table success",
                                      "Message", 1);
                    this.dispose();
                 }catch (SQLException ex){
                 	JOptionPane.showMessageDialog(this, ex + "\nDon't create table",
                                      "Message", 1);
                 }
             }
             else JOptionPane.showMessageDialog(this, "Don't create table",
                                    "Message", 1);
           jtTablename.setEditable(true);
	}
	//-----------Ham su ly su kien click ----------
public void actionPerformed(ActionEvent ae){
		
		//click OK
		if (ae.getActionCommand().equals("L\u01B0u")){
		    buttonOK();
		
		}//ket thuc buttoon OK
		
		//click detail
		
		if (ae.getActionCommand().equals("Detail")){
		//	UserManagerOracle usermanager=new UserManagerOracle("User Manager");
		//	usermanager.setVisible(true);
			
		}
		
		
		/////////////////////////////
		
		
		if (ae.getActionCommand().equals("Thêm tr\u01B0\u1EDDng")){
				displayData();
		}
		
		if (ae.getActionCommand().equals(" Edit ")){
	         
		}
		
}
/*-------khoi tao vector-------*/
void khoitao_Vector() {
    tencot = new Vector();
    kieudl = new Vector();
    kichthuoc = new Vector();
    macdinh = new Vector();
    nul = new Vector();
    cotkhoachinh = new Vector();
    trangthaikc = new Vector();
    trangthaikn = new Vector();
    cotkhoaphu = new Vector();
    ndkp = new Vector();
    bangkp = new Vector();
    cotkp = new Vector();
    tenkp = new Vector();
    tenkptmp = new Vector();
    ndkptmp = new Vector();
    bangkptmp = new Vector();
    cotkptmp = new Vector();
  }
/*	public static void main(String agrs[]){
		try {
     			 	Class.forName("oracle.jdbc.driver.OracleDriver");
				    con = DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:'dbTHO'","system","abc123");
                 }catch(Exception es){}
		AddTable gd=new AddTable("Create table",con);
		gd.setVisible(true);
	}*/
}
