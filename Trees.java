import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.sql.*;
import java.sql.SQLException;
import javax.swing.border.*;

public class Trees extends JPanel {
	static JFrame myFrame,myFrame1;
	String st, st1,st2,st3,st4,stnh="";
	JTree tree;
	JLabel pic,p;
	protected DefaultTreeModel treeModel;
	TitledBorder tbd1, tbd2;
	Border border1, border2;
	DefaultMutableTreeNode root;
	static MyJTree tree1;
	JTabbedPane tabDBMS;
	static JPanel pane;
	public DefaultMutableTreeNode dbserver;
	int j;
//new	
//	protected DefaultTreeCellEditor m_editor;
	protected JPopupMenu m_popup;
	protected Action     m_expandAction;
	protected TreePath   m_clickedPath;

	public  Trees(){
		setLayout(new BorderLayout());
		DefaultMutableTreeNode root= createNode();
		
		pane=new JPanel();
		pane.setLayout(new BorderLayout());
		ImageIcon iii=new ImageIcon("image\\Hinh 1.gif");
		p=new JLabel(iii);
		pane.removeAll();
		pane.add(p);
		tree1 = new MyJTree(root);
		tree1.putClientProperty("JTree.lineStyle", "Angled");
		IconCellRenderer renderer = new	IconCellRenderer();
		renderer.setLeafIcon(new ImageIcon("icon\\ioTopHostDevice.gif"));

	
	
    	tree1.setCellRenderer(renderer); 
    	
    	tree1.setShowsRootHandles(true);
		tree1.setEditable(false);
//Tao popup voi nut cua cay{1
		m_popup = new JPopupMenu();
		m_expandAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (m_clickedPath==null)
					return;
				if (tree1.isExpanded(m_clickedPath))
					tree1.collapsePath(m_clickedPath);
				else
					tree1.expandPath(m_clickedPath);
			}
		};
		m_popup.add(m_expandAction);
		m_popup.addSeparator();

		tree1.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e){
		try{		
			st1 = e.getPath().getLastPathComponent().toString();//nut hien tai kich chuot
			st2 = e.getPath().getParentPath().toString();//duong dan goc
			
			
			
			
			TreePath path = e.getPath();
			Object[] nodes = path.getPath();
			String oid = "";
			for (int k=0; k<nodes.length; k++) {
				DefaultMutableTreeNode node =
					(DefaultMutableTreeNode)nodes[k];
				IconData nd = (IconData)node.getUserObject();
				oid = ""+nd.getId();
				j=nd.getId();
				
			}
			int stt = e.getNewLeadSelectionPath().getPathCount();
	//		System.out.println(oid);
		
			try{
			String st0 = e.getPath().getPathComponent(0).toString();
			stnh=st0;
			for (int k=1; k<3; k++) {
				
				stnh = stnh +" "+ e.getPath().getPathComponent(k).toString();
			}
			}catch(Exception ex){}

			
				if((st2.equals("[Database Server]")) & (st1.equals("Oracle 9i - "+Giaodien.arrayuser[j]))){
				if(Giaodien.arrayconnect[j]!=null){
				JTabbedPane tabDBMS = new JTabbedPane();
		 		SQL tduser=new SQL(Giaodien.arrayconnect[j]);
				tabDBMS.add(tduser, "   Oracle   ");
				pane.removeAll();
				pane.add(tabDBMS);
				pane.revalidate();
				}
				
			}else if((st2.equals("[Database Server]")) & (st1.equals("SQL Server - "+Giaodien.arrayuser[j]))){
				if(Giaodien.arrayconnect[j]!=null){
				
				//	System.out.println(stnh);

					JTabbedPane tabDBMS = new JTabbedPane();
					SQLS sqlserver=new SQLS(Giaodien.arrayconnect[j]);
					tabDBMS.add(sqlserver, "  SQL Server  ");
					pane.removeAll();
					pane.add(tabDBMS);
					pane.revalidate();
				}

			}else	if((st2.equals("[Database Server, Oracle 9i - "+Giaodien.arrayuser[j]+"]")) & (st1.equals("Schema"))){
				SchemaOracle tmo=new SchemaOracle(Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
				
			}else if((st2.equals("[Database Server, Oracle 9i - "+Giaodien.arrayuser[j]+"]"))&&(st1.equals("Security"))){
				ImageIcon ii=new ImageIcon("image\\Security.gif");
				pic=new JLabel(ii);
				pane.removeAll();
				pane.add(pic);
				pane.revalidate();
			}else if((st2.equals("[Database Server, Oracle 9i - "+Giaodien.arrayuser[j]+"]"))&&(st1.equals("Storage"))){
				ImageIcon ii=new ImageIcon("image\\Hinh 3.gif");
				pic=new JLabel(ii);
				pane.removeAll();
				pane.add(pic);
				pane.revalidate();
			}else if((st2.equals("[Database Server, SQL Server - "+Giaodien.arrayuser[j]+"]"))&&(st1.equals("Databases"))){
				DisplayDatabaseSQL usermanager=new DisplayDatabaseSQL(Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(usermanager);
				pane.revalidate();
					
			}else if((st2.equals("[Database Server, SQL Server - "+Giaodien.arrayuser[j]+"]"))&&(st1.equals("Security"))){
				ImageIcon ii=new ImageIcon("image\\Security.gif");
				pic=new JLabel(ii);
				pane.removeAll();
				pane.add(pic);
				pane.revalidate();
			}else if((st2.equals("[Database Server, Oracle 9i - "+Giaodien.arrayuser[j]+", Schema]"))){
				
				UserObjecOracle tmo=new UserObjecOracle(st1,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
	
			}
			else if((st2.equals("[Database Server, SQL Server - "+Giaodien.arrayuser[j]+", Databases]"))){
				ImageIcon ii=new ImageIcon("image\\Hinh 2.gif");
				pic=new JLabel(ii);
				pane.removeAll();
				pane.add(pic);
				pane.revalidate();
				
					
			}else if((st2.equals("[Database Server, Oracle 9i - "+Giaodien.arrayuser[j]+", Security]")) & (st1.equals("User"))){
				
				UserOracle tmo=new UserOracle(Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			}else if((st2.equals("[Database Server, Oracle 9i - "+Giaodien.arrayuser[j]+", Security]")) & (st1.equals("Roles"))){
				
				RolesOracle tmo=new RolesOracle(Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			}else if((st2.equals("[Database Server, Oracle 9i - "+Giaodien.arrayuser[j]+", Storage]")) & (st1.equals("Tablespace"))){
				
				TablespaceOracle tmo=new TablespaceOracle(Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			}else if((st2.equals("[Database Server, SQL Server - "+Giaodien.arrayuser[j]+", Security]")) & (st1.equals("Login"))){
				LoginSQL usermanager=new LoginSQL(Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(usermanager);
				pane.revalidate();
				
			}else if((st2.equals("[Database Server, SQL Server - "+Giaodien.arrayuser[j]+", Security]")) & (st1.equals("Server Roles"))){
				ServerRoleSQL usermanager=new ServerRoleSQL(Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(usermanager);
				pane.revalidate();
				
			}else if((st2.equals("[Database Server, Oracle 9i - "+Giaodien.arrayuser[j]+", Security, User]"))){
				DetailUser tmo=new DetailUser(st1,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			}else if((st2.equals("[Database Server, Oracle 9i - "+Giaodien.arrayuser[j]+", Security, Roles]"))){
				DetailRole tmo=new DetailRole(st1,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			}else if((st2.equals("[Database Server, SQL Server - "+Giaodien.arrayuser[j]+", Security, Server Roles]"))){
				DetailServerRolesSQL tmo=new DetailServerRolesSQL(st1,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			}else if((st2.equals("[Database Server, SQL Server - "+Giaodien.arrayuser[j]+", Security, Login]"))){
				DetailUserSQL tmo=new DetailUserSQL(st1,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			}else if ((stnh.equals("Database Server Oracle 9i - "+Giaodien.arrayuser[j]+" Schema"))&(st1.equals("Table"))){	
				st3 = e.getPath().getPathComponent(3).toString();
				TableManagerOracle tmo=new TableManagerOracle(st3,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();

			}else if((stnh.equals("Database Server SQL Server - "+Giaodien.arrayuser[j]+" Databases"))& (st1.equals("Table SQL"))){	
				st3 = e.getPath().getPathComponent(3).toString();
				TableManagerSQL tmo=new TableManagerSQL(st3,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			

			}else if((stnh.equals("Database Server SQL Server - "+Giaodien.arrayuser[j]+" Databases"))& (st1.equals("View SQL"))){	
				st3 = e.getPath().getPathComponent(3).toString();
				ViewManagerSQL tmo=new ViewManagerSQL(st3,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			

			}else if((stnh.equals("Database Server Oracle 9i - "+Giaodien.arrayuser[j]+" Schema"))&(st1.equals("Views"))){
				st3 = e.getPath().getPathComponent(3).toString();
				ViewManagerOracle tmo=new ViewManagerOracle(st3,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			}else if((stnh.equals("Database Server Oracle 9i - "+Giaodien.arrayuser[j]+" Schema"))& (st1.equals("Procedures"))){	
				st3 = e.getPath().getPathComponent(3).toString();
				ProcedureOracle tmo=new ProcedureOracle(st3,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			

			}else if((stnh.equals("Database Server SQL Server - "+Giaodien.arrayuser[j]+" Databases"))& (st1.equals("Stored Procedures"))){	
				st3 = e.getPath().getPathComponent(3).toString();
				ProcedureSQL tmo=new ProcedureSQL(st3,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			

			}else if((st4=(e.getPath().getPathComponent(4)).toString()).equals("Table")){
				st3 = e.getPath().getPathComponent(3).toString();
				DisplayDataOracle tmo=new DisplayDataOracle(st3,st1,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			
			}else if((st4=(e.getPath().getPathComponent(4)).toString()).equals("Table SQL")){
				st3 = e.getPath().getPathComponent(3).toString();
				DisplayDataSQL tmo=new DisplayDataSQL(st3,st1,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
				
			

			}else  if((st4=(e.getPath().getPathComponent(4)).toString()).equals("Views")){
				st3 = e.getPath().getPathComponent(3).toString();
				DetailViewOracle tmo=new DetailViewOracle(st3,st1,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			}else if((st4=(e.getPath().getPathComponent(4)).toString()).equals("View SQL")){
				st3 = e.getPath().getPathComponent(3).toString();
				DetailViewSQL tmo=new DetailViewSQL(st3,st1,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			}else if((st4=(e.getPath().getPathComponent(4)).toString()).equals("Stored Procedures")){
				st3 = e.getPath().getPathComponent(3).toString();
				DetailProSQL tmo=new DetailProSQL(st3,st1,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
			}else if(st1.equals("Users")){
				st3 = e.getPath().getPathComponent(3).toString();
				UserDBSQL tmo=new UserDBSQL(st3,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
				
					
			}else if((st4=(e.getPath().getPathComponent(4)).toString()).equals("Users")){
				st3 = e.getPath().getPathComponent(3).toString();
				DetailUserDBSQL tmo=new DetailUserDBSQL(st3,st1,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
				
			

			}else if(st1.equals("Roles")){
				st3 = e.getPath().getPathComponent(3).toString();
				RoleDBSQL tmo=new RoleDBSQL(st3,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
				
					
			}else if((st4=(e.getPath().getPathComponent(4)).toString()).equals("Roles")){
				st3 = e.getPath().getPathComponent(3).toString();
				DetailRoleDBSQL tmo=new DetailRoleDBSQL(st3,st1,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();
				
			

			}else if(st1.equals("Triggers")){
				st3 = e.getPath().getPathComponent(3).toString();
				TriggerManagerOracle tmo=new TriggerManagerOracle(st3,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();			
			}else if(st1.equals("Indexes")){
				st3 = e.getPath().getPathComponent(3).toString();
				IndexManagerOracle tmo=new IndexManagerOracle(st3,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();	
			}else if(st1.equals("Sequences")){
				st3 = e.getPath().getPathComponent(3).toString();
				SequenceManagerOracle tmo=new SequenceManagerOracle(st3,Giaodien.arrayconnect[j]);
				pane.removeAll();
				pane.add(tmo);
				pane.revalidate();	
			}
		}catch(Exception e12){}
		}
		}
		);
		tree1.addTreeWillExpandListener(new TreeWillExpandListener() 
		{
			public void treeWillExpand(TreeExpansionEvent e)  throws ExpandVetoException { 
			
			}
			public void treeWillCollapse(TreeExpansionEvent e)
			{}
		});


//-them popup Disconnect vao popup
		Action a1 = new AbstractAction("Disconnect", new ImageIcon("Icon\\Connect.gif")) {
			public void actionPerformed(ActionEvent e) {
					
					TreePath path = tree1.getSelectionPath();
					Object[] nodes = path.getPath();
					String oid = "";
					for (int k=0; k<nodes.length; k++) {
						DefaultMutableTreeNode node =
							(DefaultMutableTreeNode)nodes[k];
						IconData nd = (IconData)node.getUserObject();
						oid = ""+nd.getId();
						j=nd.getId();
					}
					Giaodien.arrayconnect[j]=null;
					Giaodien.arrayuser[j]=null;
					Giaodien.vectorTree[j]=null;
					
					Giaodien.jPanel.removeAll();
					Trees treess=new Trees();
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
					
			}
		};
		m_popup.add(a1);
		
		tree1.add(m_popup);
		tree1.addMouseListener(new PopupTrigger());
//------------1}
	
		// dua doi tuong cy vao khung chua scroll
		JScrollPane sp=new JScrollPane();
		sp.setPreferredSize(new Dimension(400, 480));
		
		sp.setViewportView(tree1);
		tbd1=new TitledBorder(border1, "Qu\u1EA3n tr\u1ECB c\u01A1 s\u1EDF d\u1EEF li\u1EC7u");
		tree1.setBorder(tbd1);
		
		JSplitPane sp1 = new JSplitPane(
		JSplitPane.HORIZONTAL_SPLIT, sp, pane);
		sp1.setBackground(Color.white);
		sp1.setDividerSize(7);
		sp1.setDividerLocation(200);
		sp1.setOneTouchExpandable(true);
				
			
		add(sp1);
	
	}//ket thuc khoi tao public tree
	
class PopupTrigger extends MouseAdapter {

	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
			int x = e.getX();
			int y = e.getY();
			TreePath path = tree1.getPathForLocation(x, y);
			if (path == null)
				return;

			if (tree1.isExpanded(path))
				m_expandAction.putValue(Action.NAME, "Collapse");
			else
				m_expandAction.putValue(Action.NAME, "Expand");

			tree1.setSelectionPath(path);
			tree1.scrollPathToVisible(path);
			m_popup.show(tree1, x, y);
			m_clickedPath = path;
		}
	}
}	
			

		
	
	// phuong tbuc nay tra ve duong dan day du dan den goc cua mot nut nhanh
	public String getNodeFullPath(TreePath node){
		String s="";
		for(int i=0;i<=node.getPathCount();i++){
			s=s+"/"+node.getPathComponent(i).toString();
		}
		return s;
	}
	/**
      * phuong thuc nay tra ve mot TreeNode
      */
    protected DefaultMutableTreeNode getSelectedNode() {
	TreePath   selPath = tree.getSelectionPath();

	if(selPath != null)
	    return (DefaultMutableTreeNode)selPath.getLastPathComponent();
	return null;
    }

    /**
     * phuong thuc nay tra ve mot TreePath
     */
    protected TreePath[] getSelectedPaths() {
        return tree.getSelectionPaths();
    }

	//tao goc cua cay
	public DefaultMutableTreeNode createNode(){
		ImageIcon iconnet=new ImageIcon("icon\\nameserv.gif");
		dbserver=new DefaultMutableTreeNode(new IconData(iconnet,"Database Server"));
		return dbserver;		
	}
}

class IconData
{
  protected Icon   m_icon;
  protected Icon   m_expandedIcon;
  protected Object m_data;
protected int    m_id;

public IconData(Icon icon, Object data)
  {
    m_icon = icon;
    m_expandedIcon = null;
    m_data = data;
  }

  public IconData(Icon icon,int id, Object data)
  {
    m_icon = icon;
    m_expandedIcon = null;
    m_id = id;
    m_data = data;
  }

  public IconData(Icon icon, Icon expandedIcon, String data)
  {
    m_icon = icon;
    m_expandedIcon = expandedIcon;
    m_data = data;
  }
  public int getId() {
		return m_id;
	}
  public Icon getIcon() 
  { 
    return m_icon;
  }

  public Icon getExpandedIcon() 
  { 
    return m_expandedIcon!=null ? m_expandedIcon : m_icon;
  }

  public Object getObject() 
  { 
    return m_data;
  }

  public String toString() 
  { 
    return m_data.toString();
  }
}
class IconCellRenderer
	extends    DefaultTreeCellRenderer {

	public IconCellRenderer() {
	//	setLeafIcon(new ImageIcon("icon\\ioTopHostDevice.gif"));
		setOpenIcon(null);
	}

	public Component getTreeCellRendererComponent(JTree tree,
		Object value, boolean sel, boolean expanded, boolean leaf,
		int row, boolean hasFocus) {

		// Invoke default implementation
		Component result = super.getTreeCellRendererComponent(tree,
			value, sel, expanded, leaf, row, hasFocus);

		DefaultMutableTreeNode node =
			(DefaultMutableTreeNode)value;
		Object obj = node.getUserObject();
		setText(obj.toString());

        if (obj instanceof Boolean)
			setText("Retrieving data...");

		if (obj instanceof IconData) {
			IconData idata = (IconData)obj;
	//		if (expanded)
			//	setIcon(idata.getExpandedIcon());
	//		else
				setIcon(idata.getIcon());
		}
	//	else
	//		setIcon(null);
	
		if (node.isRoot())
					setBackgroundSelectionColor(Color.red);
				else if (node.getChildCount() > 0)
					setBackgroundSelectionColor(Color.blue);
				else if (leaf)
					setBackgroundSelectionColor(Color.green);
		return result;
	}
}
	
class MyJTree extends JTree implements ActionListener
{
	JPopupMenu popup;
	JMenuItem mi;
	public static TreeOracle vartree;
 	public static TreeSQL vartreeSQL;

	MyJTree (DefaultMutableTreeNode dmtn)
	{
		super(dmtn);

		// Ð?nh nghia menu popup
		popup = new JPopupMenu();
		mi = new JMenuItem("Refresh");
		mi.addActionListener(this);
		mi.setActionCommand("refresh");
		popup.add(mi);  
		popup.addSeparator();
		
		mi = new JMenuItem("Help");
		mi.addActionListener(this);
		mi.setActionCommand("help");
		popup.add(mi); 
		
		popup.setOpaque(true);
		popup.setLightWeightPopupEnabled(true);

		addMouseListener (new MouseAdapter () 
		{
			public void mouseReleased( MouseEvent e ) 
			{
				if ( e.isPopupTrigger()) 
				{
					popup.show( (JComponent)e.getSource(), e.getX(), e.getY() );
				}
			} 
		});
	}

	public void actionPerformed(ActionEvent ae) 
	{
	
		if (ae.getActionCommand().equals("refresh")) 
		{
			Giaodien.jPanel.removeAll();
			Trees treess=new Trees();
			for(int i=0;i<= Giaodien.arrayconnect.length;i++)
			try{
				if((Giaodien.arrayconnect[i].getMetaData().getDatabaseProductName()).equals("Oracle"))
					{
						vartree=new TreeOracle(i,Giaodien.arrayuser[i],Giaodien.arrayconnect[i]);
						Giaodien.vectorTree[i]= vartree.oracle9i;
					}
				if((Giaodien.arrayconnect[i].getMetaData().getDatabaseProductName()).equals("Microsoft SQL Server"))
					{
						vartreeSQL=new TreeSQL(i,Giaodien.arrayuser[i],Giaodien.arrayconnect[i]);
						Giaodien.vectorTree[i]= vartreeSQL.sqlserver;
					}
			}catch(Exception io){
						//System.out.println("");
			}
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
		}
		if (ae.getActionCommand().equals("help")) 
		{ 
				try{
				String command[] = new String[] {"C:\\WINDOWS\\hh", "Help\\setupsql.chm"};
				Process child = Runtime.getRuntime().exec(command);
				}catch(Exception e){};
		}
	}
}