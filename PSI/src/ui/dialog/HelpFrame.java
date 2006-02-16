package ui.dialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import ui.resource.Bundle;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
	private JTextArea jTextArea = null;
	private JTextArea jTextAreaNorth = null;
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
			jFrame.setSize(new java.awt.Dimension(729,432));
			jFrame.setTitle(Bundle.getText("HelpDialogTitle"));
			jFrame.setLocation(new java.awt.Point(50,50));
			jFrame.setPreferredSize(new java.awt.Dimension(900,534));
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
			jContentPane.setPreferredSize(new java.awt.Dimension(700,500));
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
			jSplitPane = new JSplitPane() ;
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
			{   HelpTreeNode treenode2 = new HelpTreeNode(Bundle.getText("HelpDialogIem6"+ j),60+j);
				treenode.add(treenode2);
			}
			
			getJTree(rootNode).addTreeSelectionListener(new TreeSelectionListener(){
					public void valueChanged(TreeSelectionEvent e){

										HelpTreeNode currentNode =(HelpTreeNode) jTree.getLastSelectedPathComponent();
	        		                    String s = Integer.toString(currentNode.getID());
	        		                    // Récup ID pere
	        		                    /*while(!currentNode.isRoot()) {
	        		                    	currentNode = (HelpTreeNode)currentNode.getParent();
	        		                    	//s = "Pere: " + currentNode.getID() + "Fils :" + s;
	        		                    	}*/
	        		                   jTextAreaNorth.setText(Bundle.getText("HelpDialogItem" + s));
	        		                   jTextArea.setText(Bundle.getText("HelpDialogContenu" + s));
	        		              }
	        		           });
			
			jSplitPane.setLeftComponent(jTree);
			jSplitPane.setRightComponent(getJPanel());
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
			
			jTree.setPreferredSize(new Dimension(100,72));
			jTree.setRootVisible(false);
			jTree.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 13));
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
			jPanel = new JPanel() ;
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jPanel.add(getJTextAreaNorth(), java.awt.BorderLayout.NORTH);
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
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane ;
	}
	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea ()
	{
		if (jTextArea == null)
		{
			jTextArea = new JTextArea() ;
			jTextArea.setEditable(false);
		}
		return jTextArea ;
	}
	/**
	 * This method initializes jTextArea1	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaNorth ()
	{
		if (jTextAreaNorth == null)
		{
			jTextAreaNorth = new JTextArea() ;
			jTextAreaNorth.setFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 14));
			jTextAreaNorth.setPreferredSize(new java.awt.Dimension(0,30));
			jTextAreaNorth.setEditable(false);
			jTextAreaNorth.setBounds(0,0,30,30);
		}
		return jTextAreaNorth ;
	}
}