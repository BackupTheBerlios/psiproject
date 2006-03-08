package ui.window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import ui.resource.Bundle;
import ui.tree.HelpTreeNode;

import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JLabel;

/**
 * HelpFrame : TODO type description
 *
 * @author Evi
 * @version 1.0
 *
 */
public class HelpFrame
{
	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="37,19"
	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JTree jTree = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	private JEditorPane jEditorPane = null;
	private JEditorPane jEditorPaneNorth = null;
	private JLabel jLabel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	public HelpFrame() {
		super();
		// TODO Auto-generated constructor stub
		this.getJFrame();
	}
	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame ()
	{
		if (jFrame == null)
		{
			jFrame = new JFrame() ;
			jFrame.setSize(new java.awt.Dimension(688,550));
			jFrame.setTitle(Bundle.getText("HelpDialogTitle"));
			jFrame.setLocation(new java.awt.Point(50,50));
			jFrame.setPreferredSize(new java.awt.Dimension(900,550));
			jFrame.setResizable(false);
			jFrame.setContentPane(getJContentPane());
			jFrame.pack();
			jFrame.setVisible(true);	
		}
		return jFrame ;
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane ()
	{
		if (jContentPane == null)
		{
			jContentPane = new JPanel() ;
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setPreferredSize(new java.awt.Dimension(500,500));
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane ;
	}
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane ()
	{
		if (jSplitPane == null)
		{
			jSplitPane = new JSplitPane();
			HelpTreeNode rootNode= new HelpTreeNode("",0);
			for( int i =  1 ; i < 6; i++)
			{
				// Add a node 
				HelpTreeNode treenode = new HelpTreeNode(Bundle.getText("HelpDialogItem"+i),i);
				rootNode.add(treenode);	
			}
			
			HelpTreeNode treenode = new HelpTreeNode(Bundle.getText("HelpDialogItem6"),6);
		    rootNode.add(treenode);	
			for( int j = 1; j < 4; j++)
			{   HelpTreeNode treenode2 = new HelpTreeNode(Bundle.getText("HelpDialogItem6"+ j),(60+j));
				treenode.add(treenode2);
			}
			
			getJTree(rootNode).addTreeSelectionListener(new TreeSelectionListener(){
					public void valueChanged(TreeSelectionEvent e){

										HelpTreeNode currentNode =(HelpTreeNode) jTree.getLastSelectedPathComponent();
	        		                    String s = Integer.toString(currentNode.getID());
	        		                    // R�cup ID pere
	        		                    /*while(!currentNode.isRoot()) {
	        		                    	currentNode = (HelpTreeNode)currentNode.getParent();
	        		                    	//s = "Pere: " + currentNode.getID() + "Fils :" + s;
	        		                    	}*/
	        		                   jEditorPaneNorth.setText(Bundle.getText("HelpDialogItem" + s));
		        		               jEditorPane.setText(Bundle.getText("HelpDialogContenu" + s));    
		        		           
		        		               String image = Bundle.getText("HelpDialogNameFile" + s);
		        		               try{
		        		                   jLabel.setIcon(new ImageIcon(getClass().getResource(image)));
		        		                   jLabel.setVisible(true);
		        		               }
		        		               catch (Exception exc)
		        		   			   {
		        		            	   jLabel.setVisible(false);
		        		               }
					}});
			
			jSplitPane.setLeftComponent(jTree);
			jSplitPane.setRightComponent(getJPanel());
			jSplitPane.setDividerSize(5);
			jSplitPane.setPreferredSize(new java.awt.Dimension(805,240));
			jSplitPane.setDividerLocation(240);
		}
		return jSplitPane ;
	}
	/**
	 * This method initializes jTree	
	 * 	
	 * @return javax.swing.JTree	
	 */
	private JTree getJTree (DefaultMutableTreeNode _tree)
	{
		if (jTree == null)
		{
			jTree = new JTree(_tree) ;
			
			jTree.setPreferredSize(new java.awt.Dimension(300,250));
			jTree.setRootVisible(false);
			jTree.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 13));
			jTree.setForeground(java.awt.Color.white);
			jTree.setBackground(java.awt.Color.white);
			jTree.setMinimumSize(new Dimension(200, 200));
		}
		return jTree ;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel ()
	{
		if (jPanel == null)
		{
			jLabel = new JLabel();
			jLabel.setIcon(new ImageIcon(getClass().getResource("/ui/resource/psilogo150_150.gif")));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setBackground(java.awt.Color.white);
			jPanel = new JPanel() ;
			jPanel.setLayout(new BorderLayout());
			jPanel.setForeground(java.awt.Color.white);
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			
		}
		return jPanel ;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane ()
	{
		if (jScrollPane == null)
		{
			jScrollPane = new JScrollPane() ;
			jScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jScrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane.setViewportView(getJPanel2());
			jScrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}
		return jScrollPane ;
	}
	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JEditorPane getJEditorPane ()
	{
		if (jEditorPane == null)
		{
			jEditorPane = new JEditorPane() ;
			jEditorPane.setEditable(false);
			jEditorPane.setText(Bundle.getText("HelpDialogContenuDefault"));
		}
		return jEditorPane ;
	}
	/**
	 * This method initializes jTextArea1	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JEditorPane getJEditorPaneNorth ()
	{
		if (jEditorPaneNorth == null)
		{
			jEditorPaneNorth = new JEditorPane() ;
			jEditorPaneNorth.setFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 14));
			jEditorPaneNorth.setPreferredSize(new java.awt.Dimension(0,30));
			jEditorPaneNorth.setEditable(false);
			jEditorPaneNorth.setText(Bundle.getText("HelpDialogItemDefault"));
		}
		return jEditorPaneNorth ;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1 ()
	{
		if (jPanel1 == null)
		{
			jPanel1 = new JPanel() ;
			jPanel1.setBackground(java.awt.Color.white);
			jPanel1.setForeground(java.awt.Color.white);
			jPanel1.add(jLabel, java.awt.BorderLayout.CENTER);
		}
		return jPanel1 ;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2 ()
	{
		if (jPanel2 == null)
		{
			jPanel2 = new JPanel() ;
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
			jPanel2.add(getJEditorPaneNorth(), java.awt.BorderLayout.NORTH);
			jPanel2.add(getJEditorPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2 ;
	}
}