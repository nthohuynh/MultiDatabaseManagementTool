import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import javax.swing.border.*;
import javax.swing.text.*;

public class CreateEditDatabaseSQL extends JFrame implements ActionListener{
	JLabel jldbname, jldbfilename, jlfilepathname, jlfilesize,jlfilemaxsize,jlfilegroup,
			jllogname, jllogpathname,jllogsize,jllogmaxsize,jlloggroup;
	JTextField jtdbname, jtdbfilename, jtfilepathname, jtfilesize,jtfilemaxsize,jtfilegroup,
			jtlogname, jtlogpathname,jtlogsize,jtlogmaxsize,jtloggroup;
	JCheckBox jchdefault,jchedit;
	Connection cons;
	Statement stmt;
	Container contenPane;
	JPanel jPanel1,jPanel2,jPanel3;
	
	TitledBorder tbd1, tbd2,tbd3;
	Border border1, border2, border3;
	
	JButton jbOK,jbCancel;
	boolean type;
	GridBagLayout gb,gbl,gb2;
	GridBagConstraints gbc, gbcl,gbc2;
	String db;
	public CreateEditDatabaseSQL(String title, String dbase, boolean type1, Connection con){
		super(title);
		setSize(400,300);
		this.setResizable(false);
		db=dbase;
		cons=con;
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				dispose();

			}
		});
		type=type1;
		contenPane=getContentPane();
		contenPane.setLayout(new BorderLayout());
		
		OptionListener optionListener = new OptionListener();

		jPanel1 = new JPanel();
		jPanel1.setLayout(gb=new GridBagLayout());
		jPanel1.setBorder(new TitledBorder(border1, "T\u1EA1o c\u01A1 s\u1EDF d\u1EEF li\u1EC7u"));
		gbc=new GridBagConstraints();
		gbc.anchor=GridBagConstraints.WEST;
		gbc.fill=GridBagConstraints.BOTH;
		
		jldbname=new JLabel("Tên c\u01A1 s\u1EDF d\u1EEF li\u1EC7u:");
		jtdbname=new JTextField(20);
		if(type){
			jtdbname.setText(dbase);
			jchedit=new JCheckBox("S\u1EEDa tên c\u01A1 s\u1EDF d\u1EEF li\u1EC7u");
			jchedit.addActionListener(optionListener);
			add(jPanel1,jchedit,3,2,2,1);
		}else{	
			jchdefault=new JCheckBox("M\u1EB7c \u0111\u1ECBnh");
			jchdefault.addActionListener(optionListener);
			add(jPanel1,jchdefault,1,2,2,1);
		}
		add(jPanel1,jldbname,1,1,2,1);
		add(jPanel1,jtdbname,3,1,2,1);
		
		/*------------------*/
		jPanel2=new JPanel();
		jPanel2.setLayout(gb=new GridBagLayout());
		jPanel2.setBorder(new TitledBorder(border1, "Data files"));
		gbc=new GridBagConstraints();
		gbc.anchor=GridBagConstraints.WEST;
		gbc.fill=GridBagConstraints.BOTH;
		
		
		jldbfilename=new JLabel("Tên file:");
		jtdbfilename=new JTextField(20);
		jlfilepathname=new JLabel("V\u1ECB trí:");
		jtfilepathname=new JTextField(20);
		jtfilepathname.setText("C:\\Program Files\\Microsoft SQL Server\\MSSQL\\Data\\");
		
		jlfilesize=new JLabel("Kích th\u01B0\u1EDBc \u0111\u1EA7u(MB):");
		jtfilesize=new JTextField(20);
		jlfilemaxsize=new JLabel("Maximum file size(MB):");
		jtfilemaxsize=new JTextField(20);
		
		jlfilegroup=new JLabel("Filegrowth(MB):");
		jtfilegroup=new JTextField(20);
		add(jPanel2,jldbfilename,1,1,2,1);
		add(jPanel2,jtdbfilename,3,1,2,1);
		
		add(jPanel2,jlfilepathname,1,2,2,1);
		add(jPanel2,jtfilepathname,3,2,2,1);
		
		add(jPanel2,jlfilesize,1,3,2,1);
		add(jPanel2,jtfilesize,3,3,2,1);
		
		add(jPanel2,jlfilemaxsize,1,4,2,1);
		add(jPanel2,jtfilemaxsize,3,4,2,1);
		
		add(jPanel2,jlfilegroup,1,5,2,1);
		add(jPanel2,jtfilegroup,3,5,2,1);
		
		/*--------------*/
		
		jPanel3=new JPanel();
		jPanel3.setLayout(gb=new GridBagLayout());
		jPanel3.setBorder(new TitledBorder(border1, "Transaction files"));
		gbc=new GridBagConstraints();
		gbc.anchor=GridBagConstraints.WEST;
		gbc.fill=GridBagConstraints.BOTH;
		
		
		jllogname=new JLabel("Tên file:");
		jtlogname=new JTextField(20);
		
		jllogpathname=new JLabel("V\u1ECB trí:");
		jtlogpathname=new JTextField(20);
		jtlogpathname.setText("C:\\Program Files\\Microsoft SQL Server\\MSSQL\\Data\\");
		
		jllogsize=new JLabel("Kích th\u01B0\u1EDBc \u0111\u1EA7u(MB):");
		jtlogsize=new JTextField(20);
		jllogmaxsize=new JLabel("Maximum file size(MB):");
		jtlogmaxsize=new JTextField(20);
		
		jlloggroup=new JLabel("Filegrowth(MB):");
		jtloggroup=new JTextField(20);
		add(jPanel3,jllogname,1,1,2,1);
		add(jPanel3,jtlogname,3,1,2,1);
		
		add(jPanel3,jllogpathname,1,2,2,1);
		add(jPanel3,jtlogpathname,3,2,2,1);
		
		add(jPanel3,jllogsize,1,3,2,1);
		add(jPanel3,jtlogsize,3,3,2,1);
		
		add(jPanel3,jllogmaxsize,1,4,2,1);
		add(jPanel3,jtlogmaxsize,3,4,2,1);
		
		add(jPanel3,jlloggroup,1,5,2,1);
		add(jPanel3,jtloggroup,3,5,2,1);
		if(!type)
		jtdbname.setDocument(new TriggerDocument(new Runnable( ) 
		{
			public void run( ) 
			{
				jtdbfilename.setText(jtdbname.getText()+"_Data");
				jtlogname.setText(jtdbname.getText()+"_Log");
			} 
		}));
		
		
		JTabbedPane tabDBMS = new JTabbedPane();
		tabDBMS.add(jPanel1, "General");
		tabDBMS.add(jPanel2, "Data file");
		tabDBMS.add(jPanel3, "Transaction log");
		
		JPanel pnbt=new JPanel();
		pnbt.setLayout(new FlowLayout());
		jbOK=new JButton("L\u01B0u");
		jbOK.addActionListener(this);
		jbCancel=new JButton("B\u1ECF qua");
		jbCancel.addActionListener(this);
		pnbt.add(jbOK);
		pnbt.add(jbCancel);
		
		
		contenPane.add(tabDBMS,BorderLayout.CENTER);
		contenPane.add(pnbt,BorderLayout.SOUTH);
	}
	
	
public class TriggerDocument extends PlainDocument 
{
	private Runnable runnable = null;
 
	public TriggerDocument(Runnable run) 
	{
		super( );
		runnable = run;
	}
 
	protected void fireEvent( ) 
	{
		if (runnable != null)
			runnable.run( );
	}

	protected void fireChangedUpdate(DocumentEvent e) 
	{
		super.fireChangedUpdate(e);
		fireEvent( );
	}

	protected void fireInsertUpdate(DocumentEvent e) 
	{ 
		super.fireInsertUpdate(e);
		fireEvent( );
	}

	protected void fireRemoveUpdate(DocumentEvent e) 
	{ 
		super.fireRemoveUpdate(e);
		fireEvent( );
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
class OptionListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    JComponent c = (JComponent) e.getSource();
	     if ((c == jchdefault)||(c==jchedit)) {
                if (((JCheckBox)c).isSelected()) {
                    jtdbfilename.setEnabled(false);
                    jtfilepathname.setEnabled(false);
                    jtfilesize.setEnabled(false);
 					jtfilemaxsize.setEnabled(false);
                    jtlogname.setEnabled(false);
                    jtlogpathname.setEnabled(false);
                    jtlogsize.setEnabled(false);
 					jtlogmaxsize.setEnabled(false);
                    jtfilegroup.setEnabled(false);
                    jtloggroup.setEnabled(false);
                } 
              	if (!((JCheckBox)c).isSelected()) {
              		  jtdbfilename.setEnabled(true);
                    jtfilepathname.setEnabled(true);
                    jtfilesize.setEnabled(true);
 					jtfilemaxsize.setEnabled(true);
                    jtlogname.setEnabled(true);
                    jtlogpathname.setEnabled(true);
                    jtlogsize.setEnabled(true);
 					jtlogmaxsize.setEnabled(true);
                    jtfilegroup.setEnabled(true);
                    jtloggroup.setEnabled(true);                    
                }
		}
    }
  }
public void actionPerformed(ActionEvent ae){
 	String SQL;
 	String SQL1="";
		if (ae.getActionCommand().equals("L\u01B0u")){
			if(!type){
				if(jchdefault.isSelected()){
					SQL="create database "+jtdbname.getText()+"";
					
				}else
				SQL="create database "+jtdbname.getText()+" on (name="
							+jtdbfilename.getText()+",filename='"+jtfilepathname.getText()
							+jtdbfilename.getText()+".mdf', size="+jtfilesize.getText()+"MB, Maxsize="
							+jtfilemaxsize.getText()+"MB, filegrowth="+jtfilegroup.getText()
							+" MB) log on(name="+jtlogname.getText()+",filename='"+jtlogpathname.getText()
							+jtlogname.getText()+".ldf', size="+jtlogsize.getText()+"MB, Maxsize="
							+jtlogmaxsize.getText()+"MB, filegrowth="+jtloggroup.getText()
							+"MB)";
					System.out.println(SQL);
				if(cons!=null)
						try{
							Statement stmt = cons.createStatement();
									  stmt.executeUpdate(SQL);
								
							JOptionPane.showMessageDialog(this, "Create database success",
			                                      "Message", 1);
						}catch(Exception ew){System.out.println(ew);
						JOptionPane.showMessageDialog(this, "Test information",
			                                      "Message", 1);
		           		}
	            }
	         else{
            	
            	if(jchedit.isSelected()){
					SQL="alter database "+db+" modify name = "+jtdbname.getText()+"";
				}else {
				SQL="alter database "+jtdbname.getText()+" ADD FILE  (name="
							+jtdbfilename.getText()+",filename='"+jtfilepathname.getText()
							+jtdbfilename.getText()+".ndf', size="+jtfilesize.getText()+"MB, Maxsize="
							+jtfilemaxsize.getText()+"MB, filegrowth="+jtfilegroup.getText()
							+" MB)";
				SQL1="alter database "+jtdbname.getText()+"  add log FILE(name="+jtlogname.getText()+",filename='"+jtlogpathname.getText()
							+jtlogname.getText()+".ldf', size="+jtlogsize.getText()+"MB, Maxsize="
							+jtlogmaxsize.getText()+"MB, filegrowth="+jtloggroup.getText()
							+"MB)";
				}
			if(cons!=null)

				try{
					Statement stmt = cons.createStatement();
							  stmt.execute(SQL);
					Statement stmt1 = cons.createStatement();
				 	 if(!jchedit.isSelected())stmt1.execute(SQL1);
							  
						
					JOptionPane.showMessageDialog(this, "Updated database success",
	                                      "Message", 1);
				}catch(Exception ew){System.out.println(ew);
				JOptionPane.showMessageDialog(this, ew+"/nTest information1",
	                                      "Message", 1);
	            }
	          }
			if(cons==null){
				JOptionPane.showMessageDialog(this, "Disconnect",
	                                      "Message", 1);
	    
			}
		}//ket thuc buttoon OK
		if (ae.getActionCommand().equals("B\u1ECF qua")){
			dispose();		
		}

}
/*public static void main(String agrs[]){
		
		CreateEditDatabaseSQL gd=new CreateEditDatabaseSQL("as","binh",true);
		Connect.centerScreen(gd);
		gd.setVisible(true);
}*/

} 
