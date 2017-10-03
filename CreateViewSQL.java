import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
public class CreateViewSQL extends JFrame implements ActionListener{
	JLabel jlViewname, jlSQL;
	JTextField jtViewname;
	JTextArea jtSQL;
	JButton jbOK, jbCancel;
	Container contenPane;
	GridBagLayout gb;
	GridBagConstraints gbc;
	String us,sql;
	Connection cons;
	public CreateViewSQL(String title, String user, Connection con){
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
		jlViewname=new JLabel("Tên view");
		jlSQL =new JLabel("Câu truy v\u1EA5n");
		jtViewname=new JTextField(35);
		
		jtSQL=new JTextArea(8,6);
		jtSQL.setText("select   from   ");
		
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
		
		
		JPanel jpname=new JPanel();
		jpname.setLayout(new BorderLayout());
		jpname.add(jlViewname, "North");
		jpname.add(jtViewname, "Center");
		
		JPanel jptext=new JPanel();
		jptext.setLayout(new BorderLayout());
		jptext.add(jlSQL,"North");
		jptext.add(jPanelSQL,"Center");
		
		
	
		add(pn, jbOK,3,1,1,1);
		add(pn, jbCancel,7,1,1,1);
		contenPane.add(jpname, BorderLayout.NORTH);
		contenPane.add(jptext, BorderLayout.CENTER);
		contenPane.add(pn, BorderLayout.SOUTH);
		
		
	}
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().equals("L\u01B0u")){
			sql="use "+us+"" ; 
			 
			try{
				Statement st=cons.createStatement();
				st.executeUpdate(sql);
				sql="create view "+jtViewname.getText()+" as "+jtSQL.getText()+"";
				st.executeUpdate(sql);
				jtSQL.setText("");
				jtViewname.setText("");
				st.close();
				JOptionPane.showMessageDialog(this,"T\u1EA1o view thành công"
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