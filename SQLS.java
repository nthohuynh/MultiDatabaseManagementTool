import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.sql.*;
import javax.swing.border.*;
import javax.swing.table.TableCellRenderer;
import java.io.*;
import java.lang.String;

public class SQLS extends JPanel implements ActionListener
{
//-----------------Khai bao cac bien dung tr2ong chuong trinh--

	String stSQL=new String("SP_HELPSRVROLE");
	JTextArea jtaSQL;
	Container contenPane;
	JMenuBar jmnBar;
	JPanel jpFind,jpTable,jpOption;
	GridBagLayout gb;
	GridBagConstraints gbc;
	JButton jbFind,jbAdd,jbEdit,jbDel, jbCancel,jbLoad;
	JScrollPane jscPane,resultPane;
	Connection con;
	static Connection con1;
	JTable tableResult;
	static JFrame myFrame;
	TitledBorder tbd1, tbd2;
	Border border1, border2;

	protected boolean m_textChanged = false;
	protected File m_currentFile;
	protected JFileChooser m_chooser;
	public static final String APP_NAME = "";
  	JTabbedPane tabDBMS = new JTabbedPane();
	

//-----------------Ham cau tu-------------------------------
	public SQLS(Connection cons){

		con=cons;
		//setSize(600,400);

		setLayout(new BorderLayout());
		jtaSQL=new JTextArea();
		

		
		m_chooser = new JFileChooser();
		try {
		File dir = (new File(".")).getCanonicalFile();
		m_chooser.setCurrentDirectory(dir);
		} catch (IOException ex) {}
		
		
		resultPane=new JScrollPane();
	
		
	
		executeStatement(stSQL);

		jpOption=new JPanel();
	
		gb=new GridBagLayout();
		gbc=new GridBagConstraints();
	
		gbc.anchor=GridBagConstraints.EAST;
		gbc.fill=GridBagConstraints.BOTH;
	
		
		jpOption.setLayout(gb);
		
		jbAdd=new JButton("Th\u1EF1c hi\u1EC7n");
		jbEdit=new JButton("L\u01B0u");
		jbCancel=new JButton("B\u1ECF qua");
		jbLoad=new JButton("M\u1EDF file");
		
		jbAdd.addActionListener(this);
		ActionListener lst = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			saveFile(true);
			}
			};
		jbEdit.addActionListener(lst);
		
		
	
		lst = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if (!promptToSave())
			return;
			openDocument();
			}
		};
		jbLoad.addActionListener(lst);

		jbCancel.addActionListener(this);
		
		add(jpOption,jbAdd,3,6,2,1);
		add(jpOption,jbEdit,6,6,2,1);
		add(jpOption,jbLoad,9,6,2,1);
		add(jpOption,jbCancel,12,6,2,1);

		JPanel jpNorth=new JPanel();
		jpNorth.setLayout(new BorderLayout());
		
		
		
		
		JScrollPane jPanelSQL=new JScrollPane();
		jPanelSQL.setViewportView(jtaSQL);
		
		jpNorth.add(jPanelSQL, BorderLayout.CENTER);
		jpNorth.add(jpOption, BorderLayout.SOUTH);
		
		
		tbd1=new TitledBorder(border1, "Câu l\u1EC7nh SQL");
		jpNorth.setBorder(tbd1);
		//add(jpOption, BorderLayout.NORTH);
		//add(jpTable, BorderLayout.CENTER);
	
		JSplitPane sp = new JSplitPane(
		JSplitPane.VERTICAL_SPLIT, jpNorth, resultPane);
		sp.setDividerLocation(150);
		add(sp);

	}
	public String getDocumentName() {
		return m_currentFile==null ? "Untitled" :
		m_currentFile.getName();
		}
		
		protected void openDocument() {
		if (m_chooser.showOpenDialog(SQLS.this) !=
		JFileChooser.APPROVE_OPTION)
		return;
		File f = m_chooser.getSelectedFile();
		if (f == null || !f.isFile())
		return;
		m_currentFile = f;
		try {
		FileReader in = new FileReader(m_currentFile);
		jtaSQL.read(in, null);
		in.close();
		//setTitle(APP_NAME+" ["+getDocumentName()+"]");
		}
		catch (IOException ex) {
		showError(ex, "Error reading file "+m_currentFile);
		}
		m_textChanged = false;
		jtaSQL.getDocument().addDocumentListener(new UpdateListener());
		}	
	public boolean saveFile(boolean saveAs) {
		if (saveAs || m_currentFile == null) {
		if (m_chooser.showSaveDialog(SQLS.this) !=
		JFileChooser.APPROVE_OPTION)
		return false;
		File f = m_chooser.getSelectedFile();
		if (f == null)
		return false;
		m_currentFile = f;
	//	setTitle(APP_NAME+" ["+getDocumentName()+"]");
		}
		try {
		FileWriter out = new
		FileWriter(m_currentFile);
		jtaSQL.write(out);
		out.close();
		}
		catch (IOException ex) {
		showError(ex, "Error saving file "+m_currentFile);
		return false;
		}
		m_textChanged = false;
		return true;
		}
			protected boolean promptToSave() {
		if (!m_textChanged)
		return true;
		int result = JOptionPane.showConfirmDialog(this,
		"Save changes to "+getDocumentName()+"?",
		APP_NAME, JOptionPane.YES_NO_CANCEL_OPTION,
		JOptionPane.INFORMATION_MESSAGE);
		switch (result) {
		case JOptionPane.YES_OPTION:
		if (!saveFile(false))
		return false;
		return true;
		case JOptionPane.NO_OPTION:
		return true;
		case JOptionPane.CANCEL_OPTION:
		return false;
		}
		return true;
		}
		public void showError(Exception ex, String message) {
		ex.printStackTrace();
		JOptionPane.showMessageDialog(this,
		message, APP_NAME,
		JOptionPane.WARNING_MESSAGE);
		}
			class UpdateListener implements DocumentListener {
		public void insertUpdate(DocumentEvent e) {
		m_textChanged = true;
		}
		public void removeUpdate(DocumentEvent e) {
		m_textChanged = true;
		}
		public void changedUpdate(DocumentEvent e) {
		m_textChanged = true;
		}
		}
		
		
	
		
		
	public  void centerScreen(JFrame form){
  		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	   Dimension frameSize = form.getSize();
	   if (frameSize.height > screenSize.height) {
	     frameSize.height = screenSize.height;
	   }
	   if (frameSize.width > screenSize.width) {
	     frameSize.width = screenSize.width;
	   }
	   form.setLocation( (screenSize.width - frameSize.width) / 2,
	                     (screenSize.height - frameSize.height) / 2);
	   form.setVisible(true);
 	}
//--------------Tao ket noi--------------------------------------
/*public void connect(){
		try{
  			DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
    		con=DriverManager.getConnection ( "jdbc:microsoft:sqlserver://BKDN-BMNJMC9ZPY:1433","sa","abc123");
			System.out.print("OK");
		}catch(Exception se){
			System.out.println(se.toString());
		}
	}*/
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
		
		
		try{	 
		Statement stmt = con.createStatement();
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

			tableResult.setOpaque(false);
			tableResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableResult.setSelectionBackground(new Color(0,190,00));
			tableResult.setGridColor(new Color(0,0,150));
			resultPane.setViewportView(tableResult);
 

		//	resultPane.setBorder(BorderFactory.createLineBorder(Color.lightGray,6));
			}
		 else {
			int updateCount=stmt.getUpdateCount();
		//	JOptionPane.showMessageDialog(this,"Updated " +updateCount+" record");
		}
		} catch(Exception e){ 
		 JOptionPane.showMessageDialog(this,e+"\nKi\u1EC3m tra l\u1EA1i câu l\u1EC7nh SQL","Thông báo",1); }
	}




//-----------------Ham su ly su kien-------------------------
	public void actionPerformed(ActionEvent ae){
//-----------Ham su ly su kien click Edit----------
	if (ae.getActionCommand().equals("Th\u1EF1c hi\u1EC7n"))
		{
					stSQL = jtaSQL.getText();
					System.out.println(stSQL);
					executeStatement(stSQL);
			
		}
	if (ae.getActionCommand().equals("B\u1ECF qua")){
					stSQL ="SP_HELPSRVROLE" ;
					
					executeStatement(stSQL);
					remove(this);
		}
	//-------------Click su kien Delelete------------------	
	
	//------------Su ly su kien click About-------------------
		if (ae.getActionCommand().equals("L\u01B0u"))
		{
		if (!m_textChanged)
		return;

		//saveFile(true);

		}
	}




//-----------------Ham chinh cua chuong trinh----------------
	public static void main(String[] args) 
	{
		try{
		UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
		}catch(Exception sa){}
		
		try{
			DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
    	 	con1=DriverManager.getConnection ( "jdbc:microsoft:sqlserver://BKDN-BMNJMC9ZPY:1433","sa","abc123");
			System.out.print("OK");
		}catch(Exception se){
			System.out.println(se.toString());
		}
		myFrame =new JFrame("as");
		SQLS usermanager=new SQLS(con1);
		myFrame.getContentPane().add(usermanager);
		
		myFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		myFrame.setSize(800,500);
		myFrame.setVisible(true);
		
		
	}
}