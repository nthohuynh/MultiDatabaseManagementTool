import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.sql.*;
import java.util.*;

public class DetailViewSQL extends JPanel implements ActionListener{
	static JFrame myFrame;

	Border border;

	TitledBorder tBorder1, tBorder2;

	JButton jbApply,jbNew, jbRemove;

	GridBagLayout gb;
	GridBagConstraints gbc;



	static Connection cons;
	ResultSet rs;
	Statement stmt;
	String xsql;
	String user2;
	String viewname;
	JTextArea jtSQL;
	
	public DetailViewSQL(String db,String view, Connection con){
		setLayout(new BorderLayout());
		user2=db;
		viewname=view;
		cons=con;
		
		gb=new GridBagLayout();
		gbc =new GridBagConstraints();
		gbc.anchor=GridBagConstraints.WEST;
		gbc.fill=GridBagConstraints.BOTH;

	
	
		JPanel jPanel=new JPanel();	
		jPanel.setLayout(new BorderLayout());
		
	
		JPanel jpa=new JPanel();
		jpa.setLayout(gb);
		jpa.setBorder(new TitledBorder(border,"Thông tin v\u1EC1 view"));
		JLabel lb=new JLabel("Tên view              :"+view);
	
	
		String username="", timename="";
		 try {
	      stmt = cons.createStatement();
	      rs = stmt.executeQuery("use "+user2+" exec sp_help @objname = '"+viewname+"'");
	     
	      while (rs.next()) {
		   	   username=rs.getString(2);
		   	   timename=rs.getString(4);
		   }
		  stmt.close();	
		  rs.close();
	    
	    } catch (SQLException ex) {
	      JOptionPane.showMessageDialog(this, ex + "Don't agree", "Message",
	                                    1);
	    }
		JLabel owner=new JLabel("Ng\u01B0\u1EDDi dùng           :"+username);
		JLabel time=new JLabel("Th\u1EDDi gian t\u1EA1o         :"+timename);
		
		
		
		
		
		add(jpa,lb,1,1,1,1);
		add(jpa,owner,1,2,1,1);
		add(jpa,time,1,3,1,1);
		
		jtSQL=new JTextArea();
		try{
			query(user2,view);
		}catch(Exception e){ System.out.println("Can not add data in username");};
	
		
	
		JScrollPane sc1=new JScrollPane();
		sc1.setBorder(new TitledBorder(border,"Câu l\u1EC7nh SQL"));
		sc1.setViewportView(jtSQL);
	


		
	
	
	
		jPanel.add(sc1,"Center");
		jPanel.add(jpa,"North");
	//	jPanelRole.add(jpnb,"South");

		

	

		add(jPanel,"Center");
		
		
		jbApply=new JButton("S\u1EEDa");
		jbApply.addActionListener(this);
		
		jbNew=new JButton("T\u1EA1o m\u1EDBi");
		jbNew.addActionListener(this);
		
		jbApply.setSize(50,10);
		JPanel jPanelB=new JPanel();
		jPanelB.setLayout(gb);
		add(jPanelB,jbApply,3,1,1,1);
		add(jPanelB,jbNew,1,1,1,1);
		add(jPanelB,"South");
		
	}	
public void add(JPanel jPanel, Component c, int x, int y, int nx, int ny){
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=nx;
		gbc.gridheight=ny;
		gb.setConstraints(c,gbc);
		jPanel.add(c,gbc);
}
 public  void query(String db, String viewname) throws Exception {
    
    ResultSet rs;

     xsql = "use "+db+" exec sp_helptext  @objname =  '"+viewname+"'";
    try {
      stmt = cons.createStatement();
      rs = stmt.executeQuery(xsql);
     
      while (rs.next()) {
	   	   jtSQL.setText(rs.getString(1));
	   }
    
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, ex + "Don't agree", "Message",
                                    1);
    }
   
  } 
  
public void actionPerformed(ActionEvent e){
	String sql;

	if (e.getActionCommand().equals("S\u1EEDa")){
			
		sql="use "+user2+"" ; 
			 
			try{
				Statement st=cons.createStatement();
				st.executeUpdate(sql);
				sql="alter view "+viewname+" as "+jtSQL.getText()+"";
				st.executeUpdate(sql);
				st.close();
			//	JOptionPane.showMessageDialog(this,"View created "
	        //                              ,"Message",1);
	    
			}catch(Exception ec){
			//	System.out.println(ec.toString());
				JOptionPane.showMessageDialog(this,ec+"\nKhông thành công"
	                                      ,"Message",1);
			}
	
	}
		if (e.getActionCommand().equals("T\u1EA1o m\u1EDBi")){
			
				CreateViewSQL gd=new CreateViewSQL("T\u1EA1o m\u1EDBi view",user2,cons);
				Connect.centerScreen(gd);
				gd.setVisible(true);	
	}
}
public static void main(String[] args){
		myFrame =new JFrame("as");
		try {
     		DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
    		cons=DriverManager.getConnection ( "jdbc:microsoft:sqlserver://THODN:1433","sa","abc123");
         }catch(Exception e){System.out.println("khong ket noi");}
            
		DetailViewSQL usermanager=new DetailViewSQL("quanlyhocsinh","tho_vw",cons);
		myFrame.getContentPane().add(usermanager);
		
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(800,700);
		myFrame.setVisible(true);
}

/*public class AddMember extends JFrame implements ActionListener{
	JFrame myFrame;
	public JList jlMember;
	JButton jbOK, jbCancel;
	JPanel jPanel;
	Container contenPane;
	Vector dataCombo;
	GridBagLayout gb;
	GridBagConstraints gbc;
	Connection cons1;
	Statement st;
	Border border1;
	String rolename;
	public AddMember(String title, Connection con1, String name){
		super(title);
		cons1=con1;
		rolename=name;
		setSize(200,250);
	//	this.setResizable(false);
		jPanel = new JPanel();
		
		contenPane=getContentPane();
		
		contenPane.setLayout(new BorderLayout());
		
		gb=new GridBagLayout();
		gbc =new GridBagConstraints();

		jPanel.setLayout(new BorderLayout());
		
	
		gbc=new GridBagConstraints();
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
			//	System.exit(0);
				dispose();

			}
		});
		JLabel jl=new JLabel("Ch\u1ECDn thành viên \u0111\u1EC3 thêm:");
		
		try{
			query1("sp_helplogins","","LoginName");
		}catch(Exception e){ System.out.println("Can not add data in username");};
		jlMember=new JList(dataCombo);
		JScrollPane sc=new JScrollPane();
		sc.setBorder(new TitledBorder(border1,""));
		sc.setViewportView(jlMember);
	
		jbOK=new JButton("\u0110\u1ED3ng ý");
		jbCancel=new JButton("B\u1ECF qua");
		jbOK.addActionListener(this);
		jbCancel.addActionListener(this);
		
		jPanel.add(jl,BorderLayout.NORTH);
		jPanel.add(sc, BorderLayout.CENTER);
		JPanel jpb=new JPanel();
		jpb.setLayout(gb);
		
		add(jpb,jbOK,1,9,1,1);
		add(jpb,jbCancel,3,9,1,1);
		
		jPanel.add(jpb, BorderLayout.SOUTH);
		
		
		contenPane.add(jPanel,BorderLayout.CENTER);

	}

public void add(JPanel jPanel, Component c, int x, int y, int nx, int ny){
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=nx;
		gbc.gridheight=ny;
		gb.setConstraints(c,gbc);
		jPanel.add(c,gbc);
}
public  void query1(String command, String thamso, String cot) throws Exception {
    int stt1=0;
    ResultSet rs3,rs1;

     String xsql1 = " "+command+"";
    try {
      st = cons1.createStatement();
      rs3 = st.executeQuery(xsql1);
      try {
				while(rs3.next()){
					stt1++;
			}
		    } catch (SQLException ex) {System.out.println(ex);};
       dataCombo = new Vector(stt1);
        
       rs1 = st.executeQuery(xsql1);
       while (rs1.next()) {
	   	   dataCombo.add(rs1.getString(cot));
	   }
    
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, ex + "\nDon't agree", "Message",
                                    1);
    }
}
public void actionPerformed(ActionEvent e){
	String stlist;
	if (e.getActionCommand().equals("\u0110\u1ED3ng ý")){
		stlist =(String)jlMember.getSelectedValue();
			if(!stlist.equals(""))
			{	if(Check(stlist, DetailServerRolesSQL.dataRole)){
					JOptionPane.showMessageDialog(null,"Thành viên này \u0111ã t\u1ED3n t\u1EA1i","Thông báo", JOptionPane.WARNING_MESSAGE);

				}else{
				String xsq="sp_addsrvrolemember @loginame = '"+stlist+"', @rolename = '"+rolename+"'";
				try{
					st = cons1.createStatement();
    				st.execute(xsq);
    				DetailServerRolesSQL.dataRole.addElement(stlist);
					DetailServerRolesSQL.jlRole2.setListData(DetailServerRolesSQL.dataRole);

				}catch(Exception e1){
				JOptionPane.showMessageDialog(null,"This login added"+e1,"Thông báo", JOptionPane.WARNING_MESSAGE);
                   
              }
			}
	//	this.dispose();
		}
	}
	if (e.getActionCommand().equals("B\u1ECF qua")){
				this.dispose();

	}
}
public boolean Check(String st1, Vector vt){
	for(int i=0;i<vt.size();i++){
		if(st1.equals((String)vt.elementAt(i))){
			return true;
		}
	}
	return false;

}


}*/

}