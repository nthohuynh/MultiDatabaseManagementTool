import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
import javax.swing.text.*;

public class ConnectSQL extends JFrame {

 
	JLabel nhanUser;
  	JLabel nhanPasword ;
  	JLabel nhanService;
  	JLabel nhanServer;
  	JLabel nhanConas ;
  	
  	JTextField txtnguoidung ;
  	JTextField txtdichvu;
  	JTextField txtketnoi;
  	JTextField txtServer;
  	JButton btKetnoi;
  	JButton btBoqua;
  	JPasswordField txtmatkhau;
  	static String st1,st2;
  	public static Connection cons;
  	GridBagLayout gb;
	GridBagConstraints gbc;
    Container contenPane;
	JPanel pn;
  	JFrame myFrame;

 	public static TreeSQL vartree;

  	public ConnectSQL(String title){
  		
  		super(title);
	  	setSize(330,135);
		this.setResizable(false);
	 	pn=new JPanel();
	  	gb=new GridBagLayout();
		gbc=new GridBagConstraints();
		pn.setLayout(gb);
		
	  	contenPane=getContentPane();
		contenPane.setLayout(new BorderLayout());

		addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent we){
					//System.exit(0);
					dispose();
	
				}
			});
  		
		gbc.fill=GridBagConstraints.BOTH;
		
	nhanUser = new JLabel();
  	nhanPasword = new JLabel();
  	 nhanService = new JLabel();
  	 nhanServer = new JLabel();
  	 nhanConas = new JLabel();
  	
	 txtnguoidung = new JTextField(15);
	 txtdichvu = new JTextField(15);
	 txtketnoi = new JTextField(15);
  	 txtServer = new JTextField(15);
  	 btKetnoi = new JButton();
  	 btBoqua = new JButton();
  	 txtmatkhau = new JPasswordField(17);
  	 
	  	nhanUser.setText("Ng\u01B0\u1EDDi dùng");
	    txtnguoidung.setText("sa");
	    
	    
	    nhanPasword.setText("M\u1EADt kh\u1EA9u");
	    txtmatkhau.setText("abc123");
	    
	    
	    
	    nhanService.setText("C\u1ED5ng");
	    txtdichvu.setText("1433");
	
	    
	    
	    nhanServer.setText("Tên Server");
	    txtServer.setText("THODN");
	  
	    	    
	    nhanConas.setText("Quy\u1EC1n k\u1EBFt n\u1ED1");
	    txtketnoi.setText("NORMAL");
	    txtketnoi.setEditable(false);

	    btKetnoi.setText("K\u1EBFt n\u1ED1i");
	    btBoqua.setText("B\u1ECF qua");
	     
	    btKetnoi.setMnemonic('K');
	  	btBoqua.setMnemonic('B');	    
	    
	    
	    btKetnoi.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent ae){
	    		st1 = txtnguoidung.getText();
     			st2 = txtmatkhau.getText();
     		try{
			
 			DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
    		cons=DriverManager.getConnection ( "jdbc:microsoft:sqlserver://"+txtServer.getText()+":"+txtdichvu.getText()+"",st1,st2);
             if(Check(cons, Giaodien.arrayconnect, Giaodien.count))
                   	{
                   		JOptionPane.showMessageDialog(btKetnoi,"K\u1EBFt n\u1ED1i \u0111ã t\u1ED3n t\u1EA1i"
                                      ,"Thông báo",1);
					}
            else {
           
				JOptionPane.showMessageDialog(btKetnoi,"K\u1EBFt n\u1ED1i thành công"
                                      ,"Thông báo",1);
                       dispose();
						Trees treess=new Trees();
						vartree=new TreeSQL(Giaodien.count,st1,cons);
						
						Giaodien.vectorTree[Giaodien.count]= vartree.sqlserver;
						Giaodien.arrayconnect[Giaodien.count]= cons;
						Giaodien.arrayuser[Giaodien.count]=st1;

							
					for(int i=0;i<= Giaodien.vectorTree.length;i++)
					try{
						if(Giaodien.vectorTree[i]!=null)
							treess.dbserver.add(Giaodien.vectorTree[i]);
						}catch(Exception io){
						//System.out.println("");
						}
						Giaodien.jPanel.removeAll();
						Giaodien.jPanel.add(treess);
						Giaodien.jPanel.revalidate();
						Giaodien.count++;
					}        
      		} catch(Exception ex){
      				JOptionPane.showConfirmDialog(btKetnoi,ex+"\nCan't update tree"
                                      ,"Message",1);
                                      }
                                    }
         });
	    
	    btBoqua.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent ae){
	    		dispose();
	   		}
	    });
	    
	
    add(pn,nhanUser, 1,3,2,1);
	add(pn,txtnguoidung, 3,3,2,1);
	
	add(pn,nhanPasword,1,4,2,1);;
    add(pn,txtmatkhau,3,4,2,1);
  	
  	add(pn,nhanService, 1,5,2,1);
  	add(pn,txtdichvu, 3,5,2,1);
    
  //  add(pn,nhanConas, 1,6,2,1);
  //  add(pn,txtketnoi, 3,6,2,1);
    
    add(pn,nhanServer,1,6,2,1);
    add(pn,txtServer, 3,6,2,1);
    
   	add(pn,btKetnoi, 3,7,1,1);
    add(pn,btBoqua, 4,7,1,1);
    
    
	
    
    
	contenPane.add(pn,BorderLayout.NORTH);
	
  	}
  	
  public void add(JPanel jPanel,Component c, int x, int y, int nx, int ny){
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=nx;
		gbc.gridheight=ny;
		gb.setConstraints(c,gbc);
		jPanel.add(c,gbc);
	}
 public boolean Check(Connection con1, Connection[] vt, int j){
	String st1="",st2="";
	try{
		 st1 = con1.getMetaData().getUserName();
	     st2= con1.getMetaData().getURL();
	   }catch(Exception e){}
	
		for(int i=0;i<j;i++){
		try{
			if((st1.equals(vt[i].getMetaData().getUserName())) & (st2.equals(vt[i].getMetaData().getURL()))){
				return true;
			}
			}catch(Exception e){}
		}
	return false;

}
  	public static void main(String args[]) throws Exception{
		ConnectSQL usermanager=new ConnectSQL("Authority Manager in Oracle");
//		UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
		ImageIcon image = new ImageIcon("icon\\ConfigMidtier.gif");
		usermanager.setIconImage(image.getImage());
		usermanager.setVisible(true);
	}
}