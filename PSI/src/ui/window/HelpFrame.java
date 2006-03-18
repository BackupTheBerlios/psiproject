
package ui.window ;

import java.awt.BorderLayout ;
import java.awt.Dimension ;
import java.awt.Toolkit ;
import java.io.IOException ;
import java.net.URL ;

import javax.swing.JEditorPane ;
import javax.swing.JFrame ;
import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JSplitPane ;
import javax.swing.JTree ;
import javax.swing.event.TreeSelectionEvent ;
import javax.swing.event.TreeSelectionListener ;
import javax.swing.tree.DefaultMutableTreeNode ;

import ui.resource.Bundle ;
import ui.resource.LocaleController ;
import ui.resource.LocaleListener ;
import ui.tree.HelpTreeNode ;

/**
 * HelpFrame : TODO type description
 * 
 * @author Evi
 * @version 1.0
 * 
 */
public class HelpFrame
{
	private JFrame jFrame = null ; // @jve:decl-index=0:visual-constraint="37,19"

	private JPanel jContentPane = null ;

	private JSplitPane jSplitPane = null ;

	private JTree jTree = null ;

	private JPanel jPanel = null ;

	private JScrollPane jScrollPane = null ;

	private JEditorPane jEditorPane = null ;

	private JPanel jPanel2 = null ;

	private HelpTreeNode rootNode = null ;

	private int whichPageIsDisplaying = -1 ;

	/**
	 * The locale controller for the language.
	 */
	private LocaleController controllerLocale = null ;

	public HelpFrame ()
	{
		super() ;

		this.controllerLocale = LocaleController.getInstance() ;
		this.controllerLocale.addLocaleListener(new LocaleListener()
		{
			public void localeChanged ()
			{
				updateText() ;
			}
		}) ;

		this.getJFrame() ;
	}

	/**
	 * 
	 * This method updates texts in this frame during a language changing.
	 * 
	 * @author MaT
	 * @version 1.0
	 * 
	 */
	private void updateText ()
	{
		jFrame.setTitle(Bundle.getText("HelpDialogTitle")) ;
		for (int i = 1; i < 7; i++ )
		{
			((DefaultMutableTreeNode) rootNode.getChildAt(i - 1)).setUserObject(Bundle.getText("HelpDialogItem" + i)) ;
		}
		for (int j = 1; j < 4; j++ )
		{
			((DefaultMutableTreeNode) (rootNode.getChildAt(5)).getChildAt(j - 1)).setUserObject(Bundle.getText("HelpDialogItem6" + j)) ;
		}
		String sPath = System.getProperty("user.dir") + "/" ;
		try
		{
			if (whichPageIsDisplaying == -1)
			{
				jEditorPane.setPage(new URL("file:///" + sPath + Bundle.getText("HelpDialogContenuDefault"))) ;
			}
			else
			{
				jEditorPane.setPage(new URL("file:///" + sPath + Bundle.getText("HelpDialogContenu" + whichPageIsDisplaying))) ;
			}
		}
		catch (IOException exc)
		{
			exc.getMessage() ;
			exc.printStackTrace() ;
		}
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
			jFrame.setSize(new java.awt.Dimension(688, 550)) ;
			jFrame.setTitle(Bundle.getText("HelpDialogTitle")) ;
			jFrame.setLocation(new java.awt.Point(50, 50)) ;
			jFrame.setPreferredSize(new java.awt.Dimension(900, 550)) ;
			jFrame.setResizable(false) ;
			jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/ui/resource/aide.gif"))) ;
			jFrame.setContentPane(getJContentPane()) ;
			jFrame.pack() ;
			jFrame.setVisible(true) ;
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
			jContentPane.setLayout(new BorderLayout()) ;
			jContentPane.setPreferredSize(new java.awt.Dimension(500, 500)) ;
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER) ;
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
			rootNode = new HelpTreeNode("", 0) ;
			for (int i = 1; i < 6; i++ )
			{
				// Add a node
				HelpTreeNode treenode = new HelpTreeNode(Bundle.getText("HelpDialogItem" + i), i) ;
				rootNode.add(treenode) ;
			}

			HelpTreeNode treenode = new HelpTreeNode(Bundle.getText("HelpDialogItem6"), 6) ;
			rootNode.add(treenode) ;
			for (int j = 1; j < 4; j++ )
			{
				HelpTreeNode treenode2 = new HelpTreeNode(Bundle.getText("HelpDialogItem6" + j), (60 + j)) ;
				treenode.add(treenode2) ;
			}

			getJTree(rootNode).addTreeSelectionListener(new TreeSelectionListener()
			{
				public void valueChanged (TreeSelectionEvent e)
				{

					HelpTreeNode currentNode = (HelpTreeNode) jTree.getLastSelectedPathComponent() ;
					String s = Integer.toString(currentNode.getID()) ;
					// Récup ID pere
					/*
					 * while(!currentNode.isRoot()) { currentNode =
					 * (HelpTreeNode)currentNode.getParent(); //s = "Pere: " + currentNode.getID() +
					 * "Fils :" + s; }
					 */
					String sPath = System.getProperty("user.dir") + "/" ;
					try
					{
						jEditorPane.setPage(new URL("file:///" + sPath + Bundle.getText("HelpDialogContenu" + s))) ;
					}
					catch (IOException exc)
					{
						exc.getMessage() ;
						exc.printStackTrace() ;
					}
					whichPageIsDisplaying = currentNode.getID() ;
					jEditorPane.setEditable(false) ;

				}
			}) ;

			jSplitPane.setLeftComponent(jTree) ;
			jSplitPane.setRightComponent(getJPanel()) ;
			jSplitPane.setDividerSize(5) ;
			jSplitPane.setPreferredSize(new java.awt.Dimension(805, 240)) ;
			jSplitPane.setDividerLocation(240) ;
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

			jTree.setPreferredSize(new java.awt.Dimension(300, 250)) ;
			jTree.setRootVisible(false) ;
			jTree.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 13)) ;
			jTree.setForeground(java.awt.Color.white) ;
			jTree.setBackground(java.awt.Color.white) ;
			jTree.setMinimumSize(new Dimension(200, 200)) ;
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
			jPanel.setLayout(new BorderLayout()) ;
			jPanel.setForeground(java.awt.Color.white) ;
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER) ;

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
			jScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0)) ;
			jScrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) ;
			jScrollPane.setViewportView(getJPanel2()) ;
			jScrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED) ;
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
			String sPath = System.getProperty("user.dir") + "/" ;
			try
			{
				jEditorPane.setPage(new URL("file:///" + sPath + Bundle.getText("HelpDialogContenuDefault"))) ;
			}
			catch (IOException exc)
			{
				exc.getMessage() ;
				exc.printStackTrace() ;
			}
			jEditorPane.setEditable(false) ;
		}

		return jEditorPane ;
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
			jPanel2.setLayout(new BorderLayout()) ;
			jPanel2.add(getJEditorPane(), java.awt.BorderLayout.CENTER) ;
		}
		return jPanel2 ;
	}
}
