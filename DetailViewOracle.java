import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.sql.*;
import java.util.*;

public class DetailViewOracle extends JPanel implements ActionListener{
	static JFrame myFrame;

	Border border;

	TitledBorder tBorder1, tBorder2;

	JButton jbApply,jbNew, jbRemove;

	GridBagLayout gb;
	GridBagConstraints gbc;



	static Connection con;
	ResultSet rs;
	Statement stmt;
	String xsql;
	String user2;
	String viewname;
	JTextArea jtSQL;

	public DetailViewOracle(String user,String view, Connection cons){
		setLayout(new BorderLayout());
		user2=user;
		viewname=view;
		con=cons;
		
		gb=new GridBagLayout();
		gbc =new GridBagConstraints();
		gbc.anchor=GridBagConstraints.WEST;
		gbc.fill=GridBagConstraints.BOTH;

	
	
		JPanel jPanel=new JPanel();	
		jPanel.setLayout(new BorderLayout());
		
	
		JPanel jpa=new JPanel();
		jpa.setLayout(gb);
		jpa.setBorder(new TitledBorder(border,"Thông tin v\u1EC1 view"));
		JLabel lb=new JLabel("Tên view         :"+view);
	
		JLabel owner=new JLabel("Schema           :"+user2);
		
		add(jpa,lb,1,1,1,1);
		add(jpa,owner,1,2,1,1);
		

	
		
		jtSQL=new JTextArea();
		try{
			query(user2,view);
		}catch(Exception e){ System.out.println("Can not add data in username");};
	
		
		
		
		
	
	
		JScrollPane sc1=new JScrollPane();
		sc1.setBorder(new TitledBorder(border,"Câu l\u1EC7nh SQL"));
		sc1.setViewportView(jtSQL);
	
		
	

		
	
	
	
		jPanel.add(sc1,"Center");
		jPanel.add(jpa,"North");

		

	

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
 public  void query(String user, String viewname) throws Exception {
    
    ResultSet rs;

     xsql = "select * from dba_views where owner='"+user+"' and view_name ='"+viewname+"'";
    try {
      stmt = con.createStatement();
      rs = stmt.executeQuery(xsql);
     
      while (rs.next()) {
	   	   jtSQL.setText(rs.getString(4));
	   	   
	   }
    
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(this, ex + "Don't agree", "Message",
                                    1);
    }
   
  } 
  
public void actionPerformed(ActionEvent e){
	String sql;

	if (e.getActionCommand().equals("S\u1EEDa")){
			
			try{
				Statement st=con.createStatement();
				sql="CREATE OR REPLACE VIEW "+user2+"."+viewname+" as "+jtSQL.getText()+"";
				System.out.println(sql);
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
			
				CreateView gd=new CreateView("T\u1EA1o m\u1EDBi view",user2,con);
				Connect.centerScreen(gd);
				gd.setVisible(true);	
	}
}
}