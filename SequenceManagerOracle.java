import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
public class SequenceManagerOracle extends JPanel implements ActionListener
{
//-----------------Khai bao cac bien dung tr2ong chuong trinh--
	static JFrame myFrame;
	JTable jtTable;
	String stConditon="",stFindWhat="First Name";
	String stSQL;
	JLabel jlFindWhat,jlCondition;
	JTextArea jtaDe;
	JTextField jtfCondition;
	JComboBox jcbFindWhat;
	Container contenPane;
	JMenuBar jmnBar;
	JPanel jpFind,jpTable,jpOption;
	GridBagLayout gb;
	GridBagConstraints gbc;
	JButton jbFind,jbAdd,jbEdit,jbDel, jbCancel;
	JScrollPane jscPane,resultPane;
	Connection con;
	JTable tableResult;
	String username,us;
	ResultSet rss;
//-----------------Ham cau tu-------------------------------
	public SequenceManagerOracle(String username,Connection cons){

		con=cons;
		stSQL = new String("SELECT  SEQUENCE_NAME ,  MIN_VALUE,  MAX_VALUE,  CACHE_SIZE   FROM all_sequences where SEQUENCE_OWNER ='"+username+"'");
		us=username;

		jmnBar=new JMenuBar();
		JMenu jmHelp=new JMenu("Help");
		JMenuItem jmiHelp=new JMenuItem("Help");
		JMenuItem jmiAbout=new JMenuItem("About");
		jmiHelp.addActionListener(this);
		jmiAbout.addActionListener(this);
		jmHelp.add(jmiHelp);
		jmHelp.addSeparator();
		jmHelp.add(jmiAbout);
		jmnBar.add(jmHelp);

	//	setJMenuBar(jmnBar);
	//	contenPane=getContentPane();
	//	contenPane.setLayout(new BorderLayout());
		setLayout(new BorderLayout());
		
		
		jpFind=new JPanel();
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();
		jpFind.setLayout(gb);
		jlFindWhat=new JLabel("Find with  :  ");
		jlCondition=new JLabel("Condition : ");
		jtfCondition=new JTextField(20);
		
		String tam[]={"USERNAME", "USER_ID" ,"PASSWORD","ACCOUNT_STATUS", "DEFAULT_TABLESPACE" , "TEMPORARY_TABLESPACE"};
		jcbFindWhat=new JComboBox(tam);
		jcbFindWhat.setSelectedIndex(0);
		JLabel jlTab=new JLabel("  ");
		jbFind=new JButton("Find");
		jbFind.addActionListener(this);
		add(jpFind,jlTab,1,1,1,1);
		add(jpFind,jlFindWhat,0,2,1,1);
		add(jpFind,jcbFindWhat,1,2,3,1);
		add(jpFind,jlCondition,0,3,1,1);
		add(jpFind,jtfCondition,1,3,4,1);
		add(jpFind,jbFind,6,3,2,1);
		//add(jpFind,jlTab,0,4,2,1);
	//	contenPane.add(jpFind,BorderLayout.NORTH);
	//	add(jpFind, "North");
		
		JPanel jPanel=new JPanel();
		JLabel jlObject =new JLabel("Sequences t\u1EA1i schema "+us );
		jlObject.setIcon(new ImageIcon("icon\\events.gif"));
		jlObject.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 20));
		jPanel.add(jlObject);
		
		jpTable=new JPanel();
		int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		resultPane=new JScrollPane(v,h);
		jpTable.setLayout(new BorderLayout());
		jpTable.add(resultPane,BorderLayout.CENTER);
	//	contenPane.add(jpTable,BorderLayout.CENTER);
		add(jpTable,"Center");
		
		
		executeStatement(stSQL);

		jpOption=new JPanel();
		jbAdd=new JButton("Add");
		jbEdit=new JButton("Edit");
		jbDel=new JButton("Delete");
		jbCancel=new JButton("Import Data");
		jbAdd.addActionListener(this);
		jbEdit.addActionListener(this);
		jbDel.addActionListener(this);
		jbCancel.addActionListener(this);

	//	add(jpOption,jbAdd,1,1,2,1);
	//	add(jpOption,jbEdit,3,1,2,1);
	//	add(jpOption,jbDel,5,1,2,1);
	//	add(jpOption,jbCancel,8,1,2,1);
		add(jpOption,"South");
		add(jPanel, "North");
		
		
	//	contenPane.add(jpOption,BorderLayout.SOUTH);
	//	centerScreen(this);

	}

//--------------Ham addComponent------------------------------------
public void add(JPanel jPanel,Component c, int x, int y, int nx, int ny){
		gbc.gridx=x;
		gbc.gridy=y;
		gbc.gridwidth=nx;
		gbc.gridheight=ny;
		gb.setConstraints(c,gbc);
		jPanel.add(c,gbc);
	}
//----------------Thuc hien cau lenh SQL--------------------------------
public void executeStatement(String sql){
		if(con!=null)
		try{	 
		Statement stmt = Connect.con.createStatement();
		if(stmt.execute(sql)){
			ResultSet rs=stmt.getResultSet();
			//lay ten cac truong
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			Vector vTitle=new Vector(numberOfColumns,0);
			for(int j=1; j<=numberOfColumns;j++) {
				vTitle.add(rsmd.getColumnLabel(j));
			}
			// dua du lieu vao vector vData
			Vector vData=new Vector(10,12);
			while(rs.next()) {
				Vector row=new Vector(numberOfColumns,0);
				for(int i=1; i<=numberOfColumns;i++){ 
					row.add(rs.getObject(i));
				}
				vData.add(row);
			}
			rs.close();
			stmt.close();
			tableResult=new JTable(vData,vTitle);
			tableResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableResult.setSelectionBackground(new Color(220,100,100));
			tableResult.setGridColor(new Color(0,0,150));
			resultPane.setViewportView(tableResult);
			resultPane.setBorder(BorderFactory.createLineBorder(Color.lightGray,6));
			}
		 else {
			int updateCount=stmt.getUpdateCount();
			JOptionPane.showMessageDialog(this,"Updated "+ updateCount+" record");
		}
		} catch(Exception e){  System.out.println("Error " + e); }
	}

//-----------------Ham su ly su kien-------------------------
	public void actionPerformed(ActionEvent ae){

	}
//-----------------Ham chinh cua chuong trinh----------------
/*	public static void main(String[] args) 
	{

		myFrame =new JFrame("as");
		SequenceManagerOracle usermanager=new SequenceManagerOracle("SYSTEM");
		myFrame.getContentPane().add(usermanager);
		
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(800,500);
		myFrame.setVisible(true);
	}*/
}
