import java.awt.*;
import java.util.*;
import java.awt.event.*;
import com.sun.java.swing.*;
import javax.swing.*;
import java.sql.*;
import java.sql.SQLException;
import javax.swing.border.*;
import javax.swing.text.*;

public class CreateTableSQL extends JFrame implements java.awt.event.ContainerListener, java.awt.event.KeyListener, ActionListener, ItemListener {
	
	static JFrame myFrame;
	JLabel jlTablename,jlUser,jlTablespace,jlColumnname,jlDatatype,jlSize, jlKey, jlFkey,jlNon, jlDefault, jlFkuser, jlFktable, jlFkcolumn;
	JTextField jtTablename,jtUser,jtTablespace,jtColumnname,jtDatatype,jtSize, jtKey, jtFkey,jtDefault;
	JComboBox jcbUser, jcbTablespace,jcbDatatype, jcbFkuser,jcbFktable,  jcbFkcolumn;
	JButton jbAdd,jbCancel,jbDetail,jbAddcolumn,jbEdit,jbRemove;
	JPanel jPanel,jPanel1, jPanel2,jpTable;
	GridBagLayout gb,gbl,gb2;
	GridBagConstraints gbc, gbcl,gbc2;
	String dataCombo2[] = {"TEMP"};
	static String data[] = {"binary", "Bigint","bit","Char","datetime",
		"Float", "image", "Int", "Money","nchar","Ntext","nvarchar",
		"Real","smalldatetime","smallint","smallmoney",
		"text","tinyint","varbinary","varchar"
	};

	String stSQL="SELECT OWNER,  TABLE_NAME,  TABLESPACE_NAME,  CLUSTER_NAME  FROM dba_tables";
	boolean type;
	TitledBorder tbd1, tbd2;
	Border border1, border2;
	Container contenPane;
	static Connection con1;
	Connection cons;
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
	String st = "", st1, st2, st3, stkc, stkp, stsave,db;


//--------Ham cau tu------------------------------
	public CreateTableSQL(String title, String dbname,Connection con) {
		super(title);
		cons=con;
		setSize(380,420);
		this.setResizable(false);
		db=dbname;
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

		jlTablename=new JLabel("Tên b\u1EA3ng :");
		jtTablename=new JTextField(15);
	
		
		
		jbAdd=new JButton("L\u01B0u");
		jbAdd.addActionListener(this);
		
		add(jPanel1,jlTablename,1,1,2,1);
		add(jPanel1,jtTablename,3,1,2,1);
	
		
		gbcl.fill=GridBagConstraints.HORIZONTAL;
		add(jPanel1,jbAdd,6,1,2,1);
		
	/*--------------bat dau panel2--------------*/

		gbc2.anchor=GridBagConstraints.WEST;
		gbc2.fill=GridBagConstraints.HORIZONTAL;

		jlColumnname =new JLabel("Tên tr\u01B0\u1EDDng:");
		jtColumnname = new JTextField(13);
		
		
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
		jtDefault = new JTextField(5);
		
		jlFktable =new JLabel("B\u1EA3ng:");
		try{
				String stt1="use "+db+" exec sp_tables";
				getDB(stt1,"TABLE_NAME");

		}catch(Exception e2){ System.out.println("Can not add data in tablename");};
		jcbFktable=new JComboBox(dataCombo);
	
	
		
	
		
		jlFkcolumn=new JLabel("Tr\u01B0\u1EDDng:");
		try{
				String stt2="use "+db+" exec sp_columns @table_name = "
				+jcbFktable.getSelectedItem().toString()+"";
				
				getDB(stt2,"column_name");
		}catch(Exception e1){ System.out.println("Can not add data in username");};
		jcbFkcolumn=new JComboBox(dataCombo);
		
		jcbFktable.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				jcbFkcolumn.removeAllItems();
				try{
					String stt2="use "+db+" exec sp_columns @table_name = "
							+jcbFktable.getSelectedItem().toString()+"";
							getDB(stt2,"column_name");

					}catch(Exception e3){ System.out.println("Can not add data in username");};
	
				
				for(int i=0; i < dataCombo.size(); i++){
				jcbFkcolumn.addItem(dataCombo.get(i));
				jcbFkcolumn.setSelectedItem(dataCombo.get(0));
				}
			}
		});
		jcbFktable.setEnabled(false);
        jcbFkcolumn.setEnabled(false);
		jtFkey.setEnabled(false);
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
		
		add(jPanel2,jlColumnname,1,1,1,1);
		add(jPanel2,jtColumnname,2,1,4,1);
	
		
		
		add(jPanel2,jlDatatype,1,2,1,1);
		add(jPanel2,jcbDatatype,2,2,4,1);
		
		add(jPanel2,jlSize,1,3,1,1);
		add(jPanel2,jtSize,2,3,4,1);
	
		add(jPanel2,jlDefault,1,4,1,1);
		add(jPanel2,jtDefault,2,4,4,1);
		
		add(jPanel2,checkboxNull,1,5,1,1);
		
		add(jPanel2,jchbKey,2,6,1,1);
		add(jPanel2,jlKey,3,6,1,1);
		add(jPanel2,jtKey,4,6,2,1);
		
	
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
		
	
		add(thamchieu,jlFktable,2,4,1,1);
		add(thamchieu,jcbFktable,3,4,1,1);
		add(thamchieu,jlFkcolumn,2,5,1,1);
		add(thamchieu,jcbFkcolumn,3,5,1,1);
		
		
		add(jPanel2,thamchieu,2,7,4,6);
	
	/*	add(jPanel2,jchbFkey,3,5,1,1);
		add(jPanel2,jlFkey,1,5,2,1);
		add(jPanel2,jtFkey,5,5,2,1);
		
	
		add(jPanel2,jlFktable,2,7,2,1);
		add(jPanel2,jcbFktable,4,7,2,1);
		add(jPanel2,jlFkcolumn,2,8,2,1);
		add(jPanel2,jcbFkcolumn,4,8,2,1);*/
		
		
		
		

		add(jPanel2, jbAddcolumn, 1,12,1,1);
	//	add(jPanel2, jbEdit, 9,3,1,1);
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
}
class OptionListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    JComponent c = (JComponent) e.getSource();
	     if (c == jchbFkey) {
                if (((JCheckBox)c).isSelected()) {
                    jcbFktable.setEnabled(true);
                    jcbFkcolumn.setEnabled(true);
      				jtFkey.setEnabled(true);
 
                } 
              	if (!((JCheckBox)c).isSelected()) {
                    jcbFktable.setEnabled(false);
                    jcbFkcolumn.setEnabled(false);
                    jtFkey.setEnabled(false);

                }
		}
    }
   }
    
public void getDB(String xsql, String name){
  	
    try{
		 	Statement  stmt=cons.createStatement();
	//		cons.setAutoCommit(true);
  			ResultSet rs=stmt.executeQuery(xsql);
		  	 try {
		      rs.last();
		      stt = rs.getRow();
		    } catch (SQLException ex) {};

   			 dataCombo = new Vector(stt);
		   rs=stmt.executeQuery(xsql);
		   /* try {
		      rs.beforeFirst();
		    }  catch (SQLException ex) {}*/
    
	    while (rs.next()) {
	      dataCombo.add(rs.getString(name));
	    }
    
    }catch(SQLException e){};

 }

  
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
			
			jtTablename.setEditable(false);
			row.add(jtColumnname.getText());
			row.add(jcbDatatype.getSelectedItem());
			row.add(jtSize.getText());
			    
			if( checkboxNull.isSelected()) row.add("Not Null");
			else row.add("");
		//	System.out.println(row);
			row.add(jtDefault.getText());
			vData.add(row);
			
			//////
			tencot.add(jtColumnname.getText());
			kieudl.add(jcbDatatype.getSelectedItem());
			kichthuoc.add(jtSize.getText());
			if( checkboxNull.isSelected()) nul.add("Not Null");
			else nul.add("");
  			tenkptmp.add(jtFkey.getText());
  			macdinh.add(jtDefault.getText());
  			
  		if (jchbKey.isSelected() && !jtKey.getText().equals("")) {
	    	cotkhoachinh.add(jtColumnname.getText());
	      //  trangthaikc.add("true");
      	}
      /*	else
        	trangthaikc.add("false");*/
      	if (!jtKey.getText().equals(""))
        	jtKey.setEditable(false);
      	if (jchbFkey.isSelected() && !jtFkey.getText().equals("")) {
	        cotkhoaphu.add(jtColumnname.getText());
	        trangthaikn.add("true");
	        tenkp.add(jtFkey.getText());
	    //    ndkp.add(jcbFkuser.getSelectedItem());
	        bangkp.add(jcbFktable.getSelectedItem());
	        cotkp.add(jcbFkcolumn.getSelectedItem());
	   //     ndkptmp.add(jcbFkuser.getSelectedItem());
	        bangkptmp.add(jcbFktable.getSelectedItem());
	        cotkptmp.add(jcbFkcolumn.getSelectedItem());
      	}
	    else {
	        trangthaikn.add("false");
	  //      ndkptmp.add("");
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
            	 	stkp = stkp + " references ";// + ndkp.elementAt(i).toString() + ".";
            	 	stkp = stkp + bangkp.elementAt(i).toString() + "(";
            	 	stkp = stkp + cotkp.elementAt(i).toString() + ")";
            	 }
            }
            else if (cotkhoaphu.size() == 1) {
            	stkp = stkp + ",constraint " + tenkp.elementAt(0).toString() +
          				" foreign key(";
          		stkp = stkp + cotkhoaphu.elementAt(0).toString() + ")";
          		stkp = stkp + " references ";// + ndkp.elementAt(0).toString() + ".";
          		stkp = stkp + bangkp.elementAt(0).toString() + "(";
          		stkp = stkp + cotkp.elementAt(0).toString() + ")";
          	}
          	st = "";
          	if (tencot.size() > 0) {
          		st = st +"use "+ db + "  create table ";// + jcbUser.getSelectedItem().toString() +
          		       // ".";
          		st = st + jtTablename.getText() + "(";
          		st = st + st1 + st2 + stkp +  ")";
          	//	st = st + " tablespace " + jcbTablespace.getSelectedItem().toString() + "";
          	}
          	if (!st.equals("")) {
          		System.out.print(st);
          		try {
          			stmt = cons.createStatement();
          			stmt.execute(st);
          			System.out.print("OK");
          			JOptionPane.showMessageDialog(this, "T\u1EA1o b\u1EA3ng thành công",
                                      "Thông báo", 1);
                    this.dispose();
                 }catch (SQLException ex){
                 	JOptionPane.showMessageDialog(this, ex + "\nKhông th\u1EC3 t\u1EA1o b\u1EA3ng",
                                      "Thông báo", 1);
                 }
             }
             else JOptionPane.showMessageDialog(this, "Không th\u1EC3 t\u1EA1o b\u1EA3ng",
                                    "Thông báo", 1);
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
		
	/*	if (ae.getActionCommand().equals(" Edit ")){
	          int m = tableResult.getSelectedRow();

				 if (m>=0){
      if (!jtColumnname.getText().equals("") && !jtSize.getText().equals("")) {
        tencot.set(m, jtColumnname.getText());
        kieudl.set(m, jcbDatatype.getSelectedItem().toString());
        kichthuoc.set(m, jtSize.getText());
        macdinh.set(m, jtDefault.getText());
        nul.set(m, checkboxNull.getText());
        				System.out.println("as");

      }
      else
        JOptionPane.showMessageDialog(this, "Test information again",
                                      "Message", 1);
      if (jchbKey.isSelected() && !jtKey.getText().equals("")) {
        cotkhoachinh.remove(stsave);
        cotkhoachinh.add(jtColumnname.getText());
        trangthaikc.set(m, "true");
      }
      else {
        cotkhoachinh.remove(stsave);
        trangthaikc.set(m, "false");
      }
      if (jchbFkey.isSelected() && !jtFkey.getText().equals("")) {
        cotkhoaphu.remove(stsave);
        cotkhoaphu.add(jtColumnname.getText());
        tenkptmp.set(m, jtFkey.getText());
        trangthaikn.set(m, "true");
      }
      else {
        cotkhoaphu.remove(stsave);
        tenkptmp.set(m, jtFkey.getText());
        trangthaikn.set(m, "false");
      }
     
     
			Vector vTitle=new Vector(4,0);
		
			vTitle.add("Column name");
			vTitle.add("Data type");
			vTitle.add("Size");
			vTitle.add("Not null?");
		
		
		

			
			tableResult=new JTable(vData,vTitle);
			tableResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableResult.setSelectionBackground(new Color(220,100,100));
			tableResult.setGridColor(new Color(0,0,150));
			resultPane.setViewportView(tableResult);
			resultPane.setBorder(BorderFactory.createLineBorder(Color.lightGray,6));
			row=new Vector(4,0);
     // velai_bang();
     //displayData();
    }else JOptionPane.showMessageDialog(this,"Select column",
                                        "Message",1);

		}*/
		
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


	public static void main(String agrs[]){
		
			try{
			
 			DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
    		con1=DriverManager.getConnection ( "jdbc:microsoft:sqlserver://THODN:1433","sa","abc123");
    	}catch(Exception as){System.out.println(as);}
		CreateTableSQL gd=new CreateTableSQL("Create table","master",con1);
           
		Connect.centerScreen(gd);
		gd.setVisible(true);
	}
}
