
package ui.window ;

import java.awt.HeadlessException ;
import java.awt.event.ActionEvent ;
import java.io.File ;

import ui.misc.JPanelTaskDescriptor;
import ui.misc.LogPanel;
import ui.resource.Bundle ;
import ui.tree.ProjectTreeNode ;
import ui.tree.TaskDescriptorTreeNode;

import javax.swing.AbstractAction ;
import javax.swing.JFileChooser ;
import javax.swing.JFrame ;
import javax.swing.JSplitPane ;
import javax.swing.JMenuBar ;
import javax.swing.JMenu ;

import javax.swing.JMenuItem ;
import javax.swing.JTree ;
import javax.swing.JTabbedPane ;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter ;
import javax.swing.tree.DefaultMutableTreeNode ;
import javax.swing.tree.DefaultTreeModel;

import model.Project ;
import model.spem2.*;

import process.Preferences ;
import process.exception.FileParseException ;
import process.utility.ProcessControler;
import process.utility.ProjectControler ;


/**
 * MainFrame : PSI main window
 *
 * @author Conde Mickael K.
 * @version 1.0
 *
 */
public class MainFrame extends JFrame
{
	/**
	 * Serial ID for serialisation
	 */
	private static final long serialVersionUID = 1834045581012841557L ;

	/*
	 * GUI Elements
	 */
	private JSplitPane mainSplitPane = null ;

	private JMenuBar mainJMenuBar = null ;

	private JMenu fileMenu = null ;

	private JMenu editMenu = null ;

	private JMenu aboutMenu = null ;

	private JMenuItem openFileMenuItem = null ;

	private JMenuItem closeFileMenuItem = null ;

	private JMenuItem quitFileMenuItem = null ;

	private JMenuItem createFileMenuItem = null ;

	private JMenuItem helpAboutMenuItem = null ;

	private JMenuItem aboutAboutMenuItem = null ;

	private JMenuItem prefsEditMenuItem = null ;

	private JMenuItem importProcessFileMenuItem = null ;

	private JMenuItem saveFileMenuItem = null ;

	private JMenuItem saveAsFileMenuItem = null ;

	private JMenuItem exportDominoFileMenuItem = null ;

	private JMenuItem export2DBFileMenuItem = null ;

	private JMenuItem exportOWFileMenuItem = null ;

	private JMenu exportFileMenu = null ;

	private JTree projectTree = null ;

	private JSplitPane rightSplitPane = null ;

	private JTabbedPane mainContainer = null ;
	
	private JTabbedPane logContainer = null ;

	private LogPanel statusPanel = null ;

	private Project currentProject = null ;

	/*
	 * Here are defined actions which can be performed by the user. Abstract Actions are used to
	 * group the same actions being performed from different components
	 */
	private AbstractAction actionOpen = new AbstractAction(Bundle.getText("MainFrameFileMenuOpen"))
	{
		private static final long serialVersionUID = -2015126209086384143L ;

		public void actionPerformed (ActionEvent e)
		{
			actionOpen(e) ;
		}
	} ;

	private AbstractAction actionImport = new AbstractAction(Bundle.getText("MainFrameFileMenuImportProcess"))
	{		
		private static final long serialVersionUID = 7373920162223888058L ;
		
		public void actionPerformed (ActionEvent e)
		{
			actionImport(e) ;
		}
	} ;

	private AbstractAction actionCreate = new AbstractAction(Bundle.getText("MainFrameFileMenuCreate"))
	{
		private static final long serialVersionUID = 7373920162223888058L ;

		public void actionPerformed (ActionEvent e)
		{
			actionCreate(e) ;
		}
	} ;
	
	private AbstractAction actionSave = new AbstractAction(Bundle.getText("MainFrameFileMenuSave"))
	{
		private static final long serialVersionUID = 7631001239658565996L ;

		public void actionPerformed (ActionEvent e)
		{
			actionSave(e) ;
		}
	} ;
	
	private AbstractAction actionSaveAs = new AbstractAction(Bundle.getText("MainFrameFileMenuSaveAs"))
	{
		private static final long serialVersionUID = 4311979460217477316L ;

		public void actionPerformed (ActionEvent e)
		{
			actionSaveAs(e) ;
		}
	} ;

	/**
	 * @throws HeadlessException
	 */
	public MainFrame () throws HeadlessException
	{
		super() ;
		initialize() ;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize ()
	{
		Preferences localPrefs = Preferences.getInstance() ;
		this.setSize(new java.awt.Dimension(localPrefs.getWidth(), localPrefs.getHeight())) ;
		this.setName("mainFrame") ;
		this.setContentPane(getMainSplitPane()) ;
		this.setJMenuBar(getMainJMenuBar()) ;
		this.setTitle("Project Supervising Indicators") ;
		this.setLocation(localPrefs.getXPosition(), localPrefs.getYPosition()) ;

		/*
		 * Actions deactivation
		 */
		actionImport.setEnabled(false) ;
		actionSave.setEnabled(false) ;
		actionSaveAs.setEnabled(false) ;
		
		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			/*
			 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
			 */
			public void windowClosing (java.awt.event.WindowEvent e)
			{
				actionExit() ;
			}
		}) ;

		this.addComponentListener(new java.awt.event.ComponentAdapter()
		{
			/*
			 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
			 */
			public void componentResized (java.awt.event.ComponentEvent e)
			{
				Preferences.getInstance().setWidth(e.getComponent().getWidth()) ;
				Preferences.getInstance().setHeight(e.getComponent().getHeight()) ;
			}

			/*
			 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
			 */
			public void componentMoved (java.awt.event.ComponentEvent e)
			{
				Preferences.getInstance().setXPosition(e.getComponent().getX()) ;
				Preferences.getInstance().setYPosition(e.getComponent().getY()) ;
			}
		}) ;
	}

	/**
	 * Handling the closing of the window
	 * 
	 * @author Cond? Mickael K.
	 * @version 1.0
	 * 
	 */
	private void actionExit ()
	{
		Preferences.getInstance().save() ;
		System.exit(0) ;
	}

	/**
	 * Creates new project from a open workbench file
	 * 
	 * @author Cond? Mickael K.
	 * @version 1.0
	 * 
	 * @param e
	 *            the Action Event that caused the action
	 */
	private void actionCreate (ActionEvent evt)
	{		
		/*
		 * Setting up a JFile Chooser with a special file filter that will only accept .xml files
		 */
		JFileChooser localFileChooser = new JFileChooser() ;
		localFileChooser.setDialogTitle(Bundle.getText("MainFrameFileCreateProjectTitle")) ;
		localFileChooser.setAcceptAllFileFilterUsed(false) ;
		localFileChooser.setApproveButtonText(Bundle.getText("MainFrameFileCreateProjectButton")) ;
		File localDirectory = new File(Preferences.getInstance().getLastProject()).getParentFile() ;
		if (localDirectory != null)
		{
			localFileChooser.setCurrentDirectory(localDirectory) ;
		}
		localFileChooser.setFileFilter(new FileFilter()
		{
			/*
			 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
			 */
			public boolean accept (File _file)
			{
				String localFileName = _file.getName() ;
				String localFileExtension = localFileName.substring(localFileName.lastIndexOf(".") + 1) ;
				return (_file.isDirectory() || (_file.isFile() && _file.canRead() && localFileExtension.equalsIgnoreCase("xml"))) ;
			}

			/*
			 * @see javax.swing.filechooser.FileFilter#getDescription()
			 */
			public String getDescription ()
			{
				return Bundle.getText("MainFrameFileOWProjectDescription") ;
			}
		}) ;
		localFileChooser.showOpenDialog(MainFrame.this) ;

		/*
		 * Working on a selected file
		 */
		File localFile ;
		if ( (localFile = localFileChooser.getSelectedFile()) != null)
		{
			actionImport.setEnabled(false) ;
			Preferences.getInstance().setLastProject(localFile.getAbsolutePath()) ;
			try
			{
				currentProject = ProjectControler.create(localFile) ;
				if (currentProject != null)
				{
					((DefaultTreeModel)getProjectTree().getModel()).setRoot(new ProjectTreeNode(currentProject)) ;
					actionImport.setEnabled(true) ;
				}
			}
			catch (FileParseException exc)
			{
				System.out.println(Bundle.getText("MainFrameFileCreateIncorrectFormat")) ;
			}
		}
	}

	/**
	 * Imports a process dpe description to a newly created project
	 * 
	 * @author Cond? Mickael K.
	 * @version 1.0
	 * 
	 * @param e
	 *            the Action Event that caused the action
	 */
	private void actionImport (ActionEvent evt)
	{
		/*
		 * Setting up a JFile Chooser with a special file filter that will only accept .dpe files generated by IEPP
		 */
		JFileChooser localFileChooser = new JFileChooser() ;
		localFileChooser.setDialogTitle(Bundle.getText("MainFrameFileImportProjectTitle")) ;
		localFileChooser.setAcceptAllFileFilterUsed(false) ;
		localFileChooser.setApproveButtonText(Bundle.getText("MainFrameFileImportProjectButton")) ;
		File localDirectory = new File(Preferences.getInstance().getLastProject()).getParentFile() ;
		if (localDirectory != null)
		{
			localFileChooser.setCurrentDirectory(localDirectory) ;
		}
		localFileChooser.setFileFilter(new FileFilter()
		{
			/*
			 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
			 */
			public boolean accept (File _file)
			{
				String localFileName = _file.getName() ;
				String localFileExtension = localFileName.substring(localFileName.lastIndexOf(".") + 1) ;
				return (_file.isDirectory() || (_file.isFile() && _file.canRead() && localFileExtension.equalsIgnoreCase("dpe"))) ;
			}

			/*
			 * @see javax.swing.filechooser.FileFilter#getDescription()
			 */
			public String getDescription ()
			{
				return Bundle.getText("MainFrameFileDPEFileDescription") ;
			}
		}) ;
		localFileChooser.showOpenDialog(MainFrame.this) ;

		/*
		 * Working on a selected file
		 */
		File localFile ;
		if ( (localFile = localFileChooser.getSelectedFile()) != null)
		{
			try
			{
				currentProject.setProcess(ProcessControler.load(localFile)) ;
				if (currentProject != null)
				{
					// Updating actions
					actionImport.setEnabled(false) ;
					actionSave.setEnabled(true) ;
					actionSaveAs.setEnabled(true) ;
					
					// Updating UI
					((DefaultTreeModel)getProjectTree().getModel()).setRoot(new ProjectTreeNode(currentProject)) ;					
				}
			}
			catch (FileParseException exc)
			{
				System.out.println("processus incorrect") ;
			}
		}
	}
	
	/**
	 * Opens a project from file
	 * 
	 * @author Cond? Mickael K.
	 * @version 1.0
	 * 
	 * @param e
	 *            the Action Event that caused the action
	 */
	private void actionOpen (ActionEvent e)
	{
		JFileChooser localFileChooser = new JFileChooser() ;
		localFileChooser.showOpenDialog(MainFrame.this) ;
	}

	/**
	 * Saves the current project (quick save)
	 * 
	 * @author Cond? Mickael K.
	 * @version 1.0
	 * 
	 * @param e
	 *            the Action Event that caused the action
	 */
	private void actionSave (ActionEvent evt)
	{
		
	}
	
	/**
	 * Saves the current project [under another name]
	 * 
	 * @author Cond? Mickael K.
	 * @version 1.0
	 * 
	 * @param e
	 *            the Action Event that caused the action
	 */
	private void actionSaveAs (ActionEvent evt)
	{
		
	}
	
	/**
	 * This method initializes mainSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getMainSplitPane ()
	{
		if (mainSplitPane == null)
		{
			mainSplitPane = new JSplitPane() ;
			mainSplitPane.setOneTouchExpandable(true) ;
			mainSplitPane.setLeftComponent(getProjectTree()) ;
			mainSplitPane.setRightComponent(getRightSplitPane()) ;
			mainSplitPane.setDividerLocation(Preferences.getInstance().getTreeWidth()) ;
			mainSplitPane.addPropertyChangeListener("lastDividerLocation", new java.beans.PropertyChangeListener()
			{
				public void propertyChange (java.beans.PropertyChangeEvent e)
				{
					Preferences.getInstance().setTreeWidth(mainSplitPane.getDividerLocation()) ;
				}
			}) ;
		}
		return mainSplitPane ;
	}

	/**
	 * This method initializes mainJMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getMainJMenuBar ()
	{
		if (mainJMenuBar == null)
		{
			mainJMenuBar = new JMenuBar() ;
			mainJMenuBar.add(getFileMenu()) ;
			mainJMenuBar.add(getEditMenu()) ;
			mainJMenuBar.add(getAboutMenu()) ;
		}
		return mainJMenuBar ;
	}

	/**
	 * This method initializes fileMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileMenu ()
	{
		if (fileMenu == null)
		{
			fileMenu = new JMenu() ;
			fileMenu.setText(Bundle.getText("MainFrameFileMenu")) ;
			fileMenu.add(getOpenFileMenuItem()) ;
			fileMenu.add(getCreateFileMenuItem()) ;
			fileMenu.add(getImportProcessFileMenuItem()) ;
			fileMenu.add(getCloseFileMenuItem()) ;
			fileMenu.addSeparator() ;
			fileMenu.add(getSaveFileMenuItem()) ;
			fileMenu.add(getSaveAsFileMenuItem()) ;
			fileMenu.addSeparator() ;
			fileMenu.add(getExportFileMenu()) ;
			fileMenu.addSeparator() ;
			fileMenu.add(getQuitFileMenuItem()) ;
		}
		return fileMenu ;
	}

	/**
	 * This method initializes editMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getEditMenu ()
	{
		if (editMenu == null)
		{
			editMenu = new JMenu() ;
			editMenu.setText(Bundle.getText("MainFrameEditMenu")) ;
			editMenu.add(getPrefsEditMenuItem()) ;
		}
		return editMenu ;
	}

	/**
	 * This method initializes aboutMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getAboutMenu ()
	{
		if (aboutMenu == null)
		{
			aboutMenu = new JMenu() ;
			aboutMenu.setText(Bundle.getText("MainFrameAboutMenu")) ;
			aboutMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEADING) ;
			aboutMenu.add(getHelpAboutMenuItem()) ;
			aboutMenu.addSeparator() ;
			aboutMenu.add(getAboutAboutMenuItem()) ;
		}
		return aboutMenu ;
	}

	/**
	 * This method initializes openFileMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getOpenFileMenuItem ()
	{
		if (openFileMenuItem == null)
		{
			openFileMenuItem = new JMenuItem() ;
			openFileMenuItem.setAction(actionOpen) ;
		}
		return openFileMenuItem ;
	}

	/**
	 * This method initializes createFileMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCreateFileMenuItem ()
	{
		if (createFileMenuItem == null)
		{
			createFileMenuItem = new JMenuItem() ;
			createFileMenuItem.setAction(actionCreate) ;
		}
		return createFileMenuItem ;
	}

	/**
	 * This method initializes importProjectFileMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getImportProcessFileMenuItem ()
	{
		if (importProcessFileMenuItem == null)
		{
			importProcessFileMenuItem = new JMenuItem() ;
			importProcessFileMenuItem.setAction(actionImport) ;
		}
		return importProcessFileMenuItem ;
	}

	/**
	 * This method initializes saveFileMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveFileMenuItem ()
	{
		if (saveFileMenuItem == null)
		{
			saveFileMenuItem = new JMenuItem() ;
			saveFileMenuItem.setAction(actionSave) ;
		}
		return saveFileMenuItem ;
	}

	/**
	 * This method initializes saveAsFileMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveAsFileMenuItem ()
	{
		if (saveAsFileMenuItem == null)
		{
			saveAsFileMenuItem = new JMenuItem() ;
			saveAsFileMenuItem.setAction(actionSaveAs) ;
		}
		return saveAsFileMenuItem ;
	}

	/**
	 * This method initializes closeFileMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCloseFileMenuItem ()
	{
		if (closeFileMenuItem == null)
		{
			closeFileMenuItem = new JMenuItem() ;
			closeFileMenuItem.setText(Bundle.getText("MainFrameFileMenuClose")) ;
		}
		return closeFileMenuItem ;
	}

	/**
	 * This method initializes quitFileMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getQuitFileMenuItem ()
	{
		if (quitFileMenuItem == null)
		{
			quitFileMenuItem = new JMenuItem() ;
			quitFileMenuItem.setText(Bundle.getText("MainFrameFileMenuQuit")) ;
			quitFileMenuItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					actionExit() ;
				}
			}) ;
		}
		return quitFileMenuItem ;
	}

	/**
	 * This method initializes helbAboutMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getHelpAboutMenuItem ()
	{
		if (helpAboutMenuItem == null)
		{
			helpAboutMenuItem = new JMenuItem() ;
			helpAboutMenuItem.setText(Bundle.getText("MainFrameAboutMenuHelp")) ;
		}
		return helpAboutMenuItem ;
	}

	/**
	 * This method initializes aboutAboutMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAboutAboutMenuItem ()
	{
		if (aboutAboutMenuItem == null)
		{
			aboutAboutMenuItem = new JMenuItem() ;
			aboutAboutMenuItem.setText(Bundle.getText("MainFrameAboutMenuAbout")) ;
		}
		return aboutAboutMenuItem ;
	}

	/**
	 * This method initializes prefsEditMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getPrefsEditMenuItem ()
	{
		if (prefsEditMenuItem == null)
		{
			prefsEditMenuItem = new JMenuItem() ;
			prefsEditMenuItem.setText(Bundle.getText("MainFrameEditMenuPrefs")) ;
		}
		return prefsEditMenuItem ;
	}

	/**
	 * This method initializes projectTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getProjectTree ()
	{
		if (projectTree == null)
		{
			DefaultMutableTreeNode localNode = new DefaultMutableTreeNode(Bundle.getText("MainFrameTreeDefault")) ;
			DefaultTreeModel localDTM = new DefaultTreeModel(localNode) ;
			projectTree = new JTree(localDTM) ;
			projectTree.addTreeSelectionListener(new TreeSelectionListener()
					{
						public void valueChanged(TreeSelectionEvent e)
						{
							 DefaultMutableTreeNode d = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();
				                if(d instanceof TaskDescriptorTreeNode) // si le noeud est un projet
				                    afficherTaskDescriptor(((TaskDescriptorTreeNode)d).getTaskDescriptor());
						}
					}
			);
		}
		return projectTree ;
	}

	/**
	 * This method initializes exportFileMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getExportFileMenu ()
	{
		if (exportFileMenu == null)
		{
			exportFileMenu = new JMenu() ;
			exportFileMenu.setText(Bundle.getText("MainFrameFileMenuExport")) ;
			exportFileMenu.add(getExportDominoFileMenuItem()) ;
			exportFileMenu.add(getExportOWFileMenuItem()) ;
			exportFileMenu.add(getExport2DBFileMenuItem()) ;
		}
		return exportFileMenu ;
	}

	/**
	 * This method initializes exportDominoFileMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExportDominoFileMenuItem ()
	{
		if (exportDominoFileMenuItem == null)
		{
			exportDominoFileMenuItem = new JMenuItem() ;
			exportDominoFileMenuItem.setText(Bundle.getText("MainFrameFileMenuExportDomino")) ;
		}
		return exportDominoFileMenuItem ;
	}

	/**
	 * This method initializes export2DBFileMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExport2DBFileMenuItem ()
	{
		if (export2DBFileMenuItem == null)
		{
			export2DBFileMenuItem = new JMenuItem() ;
			export2DBFileMenuItem.setText(Bundle.getText("MainFrameFileMenuExport2DB")) ;
		}
		return export2DBFileMenuItem ;
	}

	/**
	 * This method initializes exportOWFileMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExportOWFileMenuItem ()
	{
		if (exportOWFileMenuItem == null)
		{
			exportOWFileMenuItem = new JMenuItem() ;
			exportOWFileMenuItem.setText(Bundle.getText("MainFrameFileMenuExportOW")) ;
		}
		return exportOWFileMenuItem ;
	}

	/**
	 * This method initializes rightSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getRightSplitPane ()
	{
		if (rightSplitPane == null)
		{
			rightSplitPane = new JSplitPane() ;
			rightSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT) ;
			rightSplitPane.setResizeWeight(0.8D) ;
			rightSplitPane.setTopComponent(getMainContainer()) ;
			rightSplitPane.setBottomComponent(getLogContainer()) ;

		}
		return rightSplitPane ;
	}

	/**
	 * This method initializes mainContainer
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getMainContainer ()
	{
		if (mainContainer == null)
		{
			mainContainer = new JTabbedPane() ;
		}
		return mainContainer ;
	}
	
	/**
	 * This method initializes logContainer
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getLogContainer ()
	{
		if (logContainer == null)
		{
			logContainer = new JTabbedPane() ;
			logContainer.add(Bundle.getText("MainFrameLogTab"), getStatusPanel()) ;
		}
		return logContainer ;
	}

	/**
	 * This method initializes statusPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private LogPanel getStatusPanel ()
	{
		if (statusPanel == null)
		{
			statusPanel = new LogPanel() ;
		}
		return statusPanel ;
	}
	
	private void afficherTaskDescriptor(TaskDescriptor _task)
	{
		 mainContainer.removeAll();
	     mainContainer.add(new JPanelTaskDescriptor(_task));
	     this.validate();
	}

} // @jve:decl-index=0:visual-constraint="158,10"
