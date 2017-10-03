import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
public class CreateProSQL extends JFrame implements ActionListener{
	JLabel jlViewname, jlSQL;
	JTextField jtViewname;
	JTextArea jtSQL;
	JButton jbOK, jbCancel;
	Container contenPane;
	GridBagLayout gb;
	GridBagConstraints gbc;
	String us,sql;
	Connection cons;
	public CreateProSQL(String title, String user, Connection con){
		super(title);
		cons=con;
		setSize(400,250);
		this.setResizable(false);
		contenPane=getContentPane();
		us=user;
		contenPane.setLayout(new BorderLayout());
		addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent we){
					//System.exit(0);
					dispose();
	
				}
			});
		jlSQL =new JLabel("L\u1EC7nh t\u1EA1o procedure");
		jtSQL=new JTextArea();
		jtSQL.setText("create procedure [procedure_name] as");
		jbOK=new JButton("L\u01B0u");
		jbOK.addActionListener(this);
		jbCancel=new JButton("B\u1ECF qua");
		jbCancel.addActionListener(this);
	
		JScrollPane jPanelSQL=new JScrollPane();
		jPanelSQL.setViewportView(jtSQL);
		
		
		JPanel pn=new JPanel();
		gbc=new GridBagConstraints();
		gbc.anchor=GridBagConstraints.EAST;
		gbc.fill=GridBagConstraints.BOTH;
		pn.setLayout(gb=new GridBagLayout());

		JPanel jpText=new JPanel();
		jpText.setLayout(new BorderLayout());
		jpText.add(jlSQL, "North");
		jpText.add(jPanelSQL, "Center");

		
		add(pn, jbOK,3,7,1,1);
		add(pn, jbCancel,7,7,1,1);
		
		contenPane.add(jpText, BorderLayout.CENTER);
		contenPane.add(pn, BorderLayout.SOUTH);
		
		
	}
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().equals("L\u01B0u")){
			sql="use "+us+"" ; 
			 
			try{
				Statement st=cons.createStatement();
				
				st.execute(sql);
				sql=""+jtSQL.getText()+"";
				
				st.executeUpdate(sql);
				
				st.close();
				JOptionPane.showMessageDialog(this,"T\u1EA1o procedure thành công"
	                                      ,"Thông báo",1);
	            this.dispose();
			}catch(Exception ec){
			//	System.out.println(ec.toString());
				JOptionPane.showMessageDialog(this,ec+"\nKhông thành công"
	                                      ,"Thông báo",1);
			}
		}
		if (e.getActionCommand().equals("B\u1ECF qua")){
			this.dispose();
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
/*	public static void main(String agrs[]){
		
		CreateViewSQL gd=new CreateViewSQL("Create table","master");
		gd.setVisible(true);
	}*/
}