import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.sql.*;
import java.util.*;

public class DetailProSQL extends JPanel implements ActionListener{
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
	String proname;
	JTextArea jtSQL;
	
	public DetailProSQL(String db,String pro, Connection con){
		setLayout(new BorderLayout());
		user2=db;
		proname=pro;
		cons=con;
		
		gb=new GridBagLayout();
		gbc =new GridBagConstraints();
		gbc.anchor=GridBagConstraints.WEST;
		gbc.fill=GridBagConstraints.BOTH;

	
	
		JPanel jPanel=new JPanel();	
		jPanel.setLayout(new BorderLayout());
		
	
		JPanel jpa=new JPanel();
		jpa.setLayout(gb);
		jpa.setBorder(new TitledBorder(border,"Thông tin v\u1EC1 procedure"));
		JLabel lb=new JLabel("Tên procedure              :"+pro);
	
	
		String username="", timename="";
		 try {
	      stmt = cons.createStatement();
	      rs = stmt.executeQuery("use "+user2+" exec sp_help @objname = '"+proname+"'");
	     
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
			query(user2,pro);
		}catch(Exception e){ System.out.println("Can not add data in username");};
	
		
	
		JScrollPane sc1=new JScrollPane();
		sc1.setBorder(new TitledBorder(border,"Câu l\u1EC7nh SQL"));
		sc1.setViewportView(jtSQL);
	
	/*	JPanel jpnb=new JPanel();
		jpnb.setLayout(gb);
		
		jbAdd=new JButton("Thêm thành viên");
		jbAdd.addActionListener(this);
		jbRemove =new JButton("B\u1ECF thành viên");
		jbRemove.addActionListener(this);
		
		add(jpnb,jbAdd,1,1,1,1);
		add(jpnb,jbRemove,2,1,1,1);*/

		
	
	
	
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
    xsql = "use "+db+" exec sp_helptext  @objname =  '"+proname+"'";
    try {
      stmt = cons.createStatement();
      rs = stmt.executeQuery(xsql);
 
      while (rs.next()) {
	   	   jtSQL.append(rs.getString(1));
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
				sql=jtSQL.getText();
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
			
				CreateProSQL gd=new CreateProSQL("T\u1EA1o m\u1EDBi procedure",user2,cons);
				Connect.centerScreen(gd);
				gd.setVisible(true);
	}
	
}
}