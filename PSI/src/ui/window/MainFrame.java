
package ui.window ;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import model.LogInformation;
import model.Project;
import process.Preferences;
import process.exception.FileParseException;
import process.exception.FileSaveException;
import process.utility.ProcessControler;
import process.utility.ProjectControler;
import ui.dialog.AboutDialog;
import ui.dialog.PreferenceDialog;
import ui.dialog.ProgressDialog;
import ui.misc.LogPanel;
import ui.misc.MainTabbedPane;
import ui.resource.Bundle;
import ui.tree.MainTree;

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

	private JMenuBar mainMenuBar = null ;

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

	private MainTree projectTree = null ;

	private JSplitPane rightSplitPane = null ;
	
	private JScrollPane treeScrollPane = null ;

	//private JTabbedPane mainContainer = null ;

	private JTabbedPane logContainer = null ;

	
	private Project currentProject = null ;

	private boolean projectModified = false ;
	
	
	/*
	 * Here are defined actions which can be performed by the user. Abstract Actions are used to
	 * group the same actions being performed from different components
	 */
	private AbstractAction actionOpen = new AbstractAction()
	{
		private static final long serialVersionUID = -2015126209086384143L ;

		
		public void actionPerformed (ActionEvent e)
		{
			actionOpen(e) ;
		}
		
	} ;

	private AbstractAction actionImport = new AbstractAction()
	{
		private static final long serialVersionUID = 7373920162223888058L ;

		public void actionPerformed (ActionEvent e)
		{
			actionImport(e) ;
		}
	} ;

	private AbstractAction actionCreate = new AbstractAction()
	{
		private static final long serialVersionUID = 7373920162223888058L ;

		public void actionPerformed (ActionEvent e)
		{
			actionCreate(e) ;
		}
	} ;

	private AbstractAction actionSave = new AbstractAction()
	{
		private static final long serialVersionUID = 7631001239658565996L ;

		public void actionPerformed (ActionEvent e)
		{
			actionSave(e) ;
		}
	} ;

	private AbstractAction actionSaveAs = new AbstractAction()
	{
		private static final long serialVersionUID = 4311979460217477316L ;

		public void actionPerformed (ActionEvent e)
		{
			actionSaveAs(e) ;
		}
	} ;

	private JPanel mainContentPane = null;

	private JPanel statusPanel = null;

	private JLabel statusLabel = null;

	private JToolBar mainToolBar = null;

	private JButton createButton = null;

	private JButton openButton = null;

	private JButton saveButton = null;

	private JButton iterationButton = null;

	private JButton saveAsButton = null;
	
	/**
	 * @throws HeadlessException
	 */
	public MainFrame () throws HeadlessException
	{
		super() ;
		new SplashScreen(this, 4);
		initialize() ;
		LogPanel.getInstance().addInformation(new LogInformation(Bundle.getText("MainFrameLogMessageAppStarted"))) ;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize ()
	{
		Preferences localPrefs = Preferences.getInstance() ;
		this.setName("mainFrame") ;
		this.setJMenuBar(getMainMenuBar()) ;
		this.setTitle("Project Supervising Indicators") ;
		this.setBounds(localPrefs.getXPosition(), localPrefs.getYPosition(), localPrefs.getWidth(), localPrefs.getHeight()) ;
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) ;

		/*
		 * Actions deactivation
		 */
		actionImport.setEnabled(false) ;
		actionSave.setEnabled(false) ;
		actionSaveAs.setEnabled(false) ;

			this.setContentPane(getMainContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			/**
			 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
			 */
			public void windowClosing (java.awt.event.WindowEvent e)
			{
				actionExit() ;
			}
		}) ;

		this.addComponentListener(new java.awt.event.ComponentAdapter()
		{
			/**
			 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
			 */
			public void componentResized (java.awt.event.ComponentEvent e)
			{
				Preferences.getInstance().setWidth(e.getComponent().getWidth()) ;
				Preferences.getInstance().setHeight(e.getComponent().getHeight()) ;
			}

			/**
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
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	private void actionExit ()
	{
		Preferences.getInstance().save() ;
		
		if (projectModified)
		{
			int localChoice = JOptionPane.showConfirmDialog(this, Bundle.getText("MainFrameConfirmExitMessage"), "PSI", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE) ;

			if (localChoice == JOptionPane.YES_OPTION)
			{
				actionSave() ;
				System.exit(0) ;
			}

			else if (localChoice == JOptionPane.NO_OPTION)
			{
				System.exit(0) ;
			}

			return ;
		}

		System.exit(0) ;
	}

	/**
	 * Creates new project from a open workbench file
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param e
	 *            the Action Event that caused the action
	 */
	private void actionCreate (ActionEvent evt)
	{
		//close the defaut tab.
		MainTabbedPane mainTabbedPane = MainTabbedPane.getInstance();
		if((mainTabbedPane.getTabCount()>0)&&(mainTabbedPane.getTitleAt(0).equals(Bundle.getText("MainFrameDefaultPanelTitle"))))
			mainTabbedPane.remove(0);
		/*
		 * Setting up a JFile Chooser with a special file filter that will only accept .xml files
		 */
		JFileChooser localFileChooser = new JFileChooser() ;
		localFileChooser.setDialogTitle(Bundle.getText("MainFrameFileCreateProjectTitle")) ;
		localFileChooser.setAcceptAllFileFilterUsed(false) ;
		localFileChooser.setApproveButtonText(Bundle.getText("MainFrameFileCreateProjectButton")) ;
		File localDirectory = new File(Preferences.getInstance().getWorkDirectory()) ;
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
			ProgressDialog progressbar = new ProgressDialog(this);
			progressbar.initialize(); 
			
			actionImport.setEnabled(false) ;

			if (Preferences.getInstance().getWorkDirectory().trim().equals(""))
			{
				Preferences.getInstance().setWorkDirectory(localDirectory.getAbsolutePath()) ;
			}

			try
			{				
				currentProject = ProjectControler.create(localFile) ;
				if (currentProject != null)
				{					
					getProjectTree().loadProject(currentProject) ;
					actionImport.setEnabled(true) ;
					LogPanel.getInstance().addInformation(new LogInformation(Bundle.getText("MainFrameLogMessageProjectCreated") + " : " + currentProject.getName())) ;
				}
			}
			catch (FileParseException exc)
			{
				JOptionPane.showMessageDialog(MainFrame.this, Bundle.getText("MainFrameFileCreateIncorrectFormat"), "PSI", JOptionPane.ERROR_MESSAGE) ;
			}
			
			progressbar.stopTimer() ;
			progressbar.dispose();
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
		
		//close the defaut tab.
		MainTabbedPane mainTabbedPane = MainTabbedPane.getInstance();
		if((mainTabbedPane.getTabCount()>0)&&(mainTabbedPane.getTitleAt(0).equals(Bundle.getText("MainFrameDefaultPanelTitle"))))
			mainTabbedPane.remove(0);
		/*
		 * Setting up a JFile Chooser with a special file filter that will only accept .dpe files
		 * generated by IEPP
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
				return (_file.isDirectory() || (_file.isFile() && _file.canRead() && ((localFileExtension.equalsIgnoreCase("dpe")) || (localFileExtension.equalsIgnoreCase("xml"))))) ;
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
			ProgressDialog progressbar = new ProgressDialog(this);
			progressbar.initialize(); 
			
			try
			{
				if ((currentProject == null)||(currentProject.getProcess() == null)){
					currentProject.setProcess(ProcessControler.load(localFile)) ;
					if (currentProject != null && currentProject.getProcess() != null)
					{
						// Updating actions
						actionImport.setEnabled(true) ;
						actionSave.setEnabled(true) ;
						actionSaveAs.setEnabled(true) ;

						// Updating UI
						getProjectTree().loadProject(currentProject) ;
						LogPanel.getInstance().addInformation(new LogInformation(Bundle.getText("MainFrameLogMessageProcessImported") + " : "
								+ currentProject.getProcess().getDescriptor().getName())) ;
					}
				}
				else{
					ProjectControler.importFromOpenWorkbench(localFile, currentProject);
				}
			}
			catch (FileParseException exc)
			{
				JOptionPane.showMessageDialog(MainFrame.this, Bundle.getText("MainFrameFileImportIncorrectFormat"), "PSI", JOptionPane.ERROR_MESSAGE) ;
			}
			
			progressbar.stopTimer() ;
			progressbar.dispose();
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
		//close the defaut tab.
		MainTabbedPane mainTabbedPane = MainTabbedPane.getInstance();
		if((mainTabbedPane.getTabCount()>0)&&(mainTabbedPane.getTitleAt(0).equals(Bundle.getText("MainFrameDefaultPanelTitle"))))
			mainTabbedPane.remove(0);
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
		actionSave() ;
	}
	
	/**
	 * Saves the current project (quick save)
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 */
	private void actionSave ()
	{
		File localFile = new File(Preferences.getInstance().getLastProject()) ;

		if (!localFile.exists())
		{
			actionSaveAs() ;
			return ;
		}

		try
		{
			ProjectControler.save(currentProject, localFile) ;
			actionSave.setEnabled(false) ;
			LogPanel.getInstance().addInformation(new LogInformation(Bundle.getText("MainFrameLogMessageProjectSaved"))) ;

		}
		catch (FileSaveException exc)
		{
			JOptionPane.showMessageDialog(MainFrame.this, Bundle.getText("MainFrameFileSaveError"), "PSI", JOptionPane.ERROR_MESSAGE) ;
		}
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
		actionSaveAs() ;
	}
	
	/**
	 * Saves the current project [under another name]
	 * 
	 * @author Cond? Mickael K.
	 * @version 1.0
	 */
	private void actionSaveAs ()
	{
		/*
		 * Setting up a specific JFileChooser
		 */
		JFileChooser localFileChooser = new JFileChooser() ;
		localFileChooser.setDialogTitle(Bundle.getText("MainFrameFileSaveProjectTitle")) ;
		localFileChooser.setAcceptAllFileFilterUsed(false) ;
		localFileChooser.setApproveButtonText(Bundle.getText("MainFrameFileImportProjectButton")) ;
		File localDirectory = new File(Preferences.getInstance().getWorkDirectory()) ;
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
				return Bundle.getText("MainFrameFilePSIFileDescription") ;
			}
		}) ;
		localFileChooser.showSaveDialog(MainFrame.this) ;

		/*
		 * Working on a selected file
		 */
		File localFile = null ;
		if ( (localFile = localFileChooser.getSelectedFile()) != null)
		{
			ProgressDialog progressbar = new ProgressDialog(this);
			progressbar.initialize(); 
			
			// Adding extension if necessary
			if (!localFile.getName().endsWith(".xml"))
			{
				// localFile.
			}

			// Checking if the file already exists before saving
			if (!localFile.exists()
					|| JOptionPane.showConfirmDialog(MainFrame.this, Bundle.getText("MainFrameFileSaveConfirm"), "PSI", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
			{
				try
				{
					ProjectControler.save(currentProject, localFile) ;
					actionSave.setEnabled(false) ;
					Preferences.getInstance().setLastProject(localFile.getAbsolutePath()) ;
					LogPanel.getInstance().addInformation(new LogInformation(Bundle.getText("MainFrameLogMessageProjectSaved"))) ;

				}
				catch (FileSaveException exc)
				{
					JOptionPane.showMessageDialog(MainFrame.this, Bundle.getText("MainFrameFileSaveError"), "PSI", JOptionPane.ERROR_MESSAGE) ;
				}
			}
			progressbar.stopTimer() ;
			progressbar.dispose();
		}
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
			mainSplitPane.setLeftComponent(getTreeScrollPane()) ;
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
	private JMenuBar getMainMenuBar ()
	{
		if (mainMenuBar == null)
		{
			mainMenuBar = new JMenuBar() ;
			mainMenuBar.add(getFileMenu()) ;
			mainMenuBar.add(getEditMenu()) ;
			mainMenuBar.add(getAboutMenu()) ;
		}
		return mainMenuBar ;
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
			fileMenu.setMnemonic(Bundle.getText("MainFrameFileMenuMn").charAt(0)) ;
			fileMenu.add(getOpenFileMenuItem()) ;
			fileMenu.add(getCloseFileMenuItem()) ;
			fileMenu.addSeparator() ;
			fileMenu.add(getCreateFileMenuItem()) ;
			fileMenu.add(getImportProcessFileMenuItem()) ;
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
			editMenu.setMnemonic(Bundle.getText("MainFrameEditMenuMn").charAt(0)) ;
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
			// aboutMenu.setMnemonic(Bundle.getText("MainFrameAboutMenuMn").charAt(0)) ;
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
			openFileMenuItem.setText(Bundle.getText("MainFrameFileMenuOpen")) ;
			openFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuOpenMn").charAt(0)) ;
			openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK)) ;
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
			createFileMenuItem.setText(Bundle.getText("MainFrameFileMenuCreate")) ;
			createFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuCreateMn").charAt(0)) ;
			createFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK)) ;
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
			importProcessFileMenuItem.setText(Bundle.getText("MainFrameFileMenuImportProcess")) ;
			importProcessFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuImportProcessMn").charAt(0)) ;
			importProcessFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK)) ;
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
			saveFileMenuItem.setText(Bundle.getText("MainFrameFileMenuSave")) ;
			saveFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuSaveMn").charAt(0)) ;
			saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK)) ;
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
			saveAsFileMenuItem.setText(Bundle.getText("MainFrameFileMenuSaveAs")) ;
			saveAsFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuSaveAsMn").charAt(0)) ;
			saveAsFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK
					| java.awt.event.InputEvent.SHIFT_MASK)) ;
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
			closeFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuCloseMn").charAt(0)) ;
			closeFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK)) ;
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
			quitFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuQuitMn").charAt(0)) ;
			quitFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK)) ;
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
			helpAboutMenuItem.setMnemonic(Bundle.getText("MainFrameAboutMenuHelpMn").charAt(0)) ;
			helpAboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK)) ;
			helpAboutMenuItem.addActionListener(new java.awt.event.ActionListener()
					{
						public void actionPerformed (java.awt.event.ActionEvent e)
						{
							MainTabbedPane mainTabbedPane = MainTabbedPane.getInstance();
							if((mainTabbedPane.getTabCount()>0)&&(mainTabbedPane.getTitleAt(0).equals(Bundle.getText("MainFrameDefaultPanelTitle"))))
								mainTabbedPane.remove(0);
							HelpFrame helpFrame = new HelpFrame();
						}
					}) ;
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
			aboutAboutMenuItem.setMnemonic(Bundle.getText("MainFrameAboutMenuAboutMn").charAt(0)) ;
			aboutAboutMenuItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					MainTabbedPane mainTabbedPane = MainTabbedPane.getInstance();
					if((mainTabbedPane.getTabCount()>0)&&(mainTabbedPane.getTitleAt(0).equals(Bundle.getText("MainFrameDefaultPanelTitle"))))
						mainTabbedPane.remove(0);
					AboutDialog aboutDialog = new AboutDialog();
				}
			}) ;
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
			prefsEditMenuItem.setMnemonic(Bundle.getText("MainFrameEditMenuPrefsMn").charAt(0)) ;
			prefsEditMenuItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					MainTabbedPane mainTabbedPane = MainTabbedPane.getInstance();
					if((mainTabbedPane.getTabCount()>0)&&(mainTabbedPane.getTitleAt(0).equals(Bundle.getText("MainFrameDefaultPanelTitle"))))
						mainTabbedPane.remove(0);
					new PreferenceDialog(MainFrame.this) ;
					
				}
			}) ;
		}
		return prefsEditMenuItem ;
	}

	/**
	 * This method initializes projectTree
	 * 
	 * @return javax.swing.JTree
	 */
	private MainTree getProjectTree ()
	{                          
		if (projectTree == null)
		{
			projectTree = new MainTree(currentProject, this) ;			
					
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
			exportFileMenu.setMnemonic(Bundle.getText("MainFrameFileMenuExportMn").charAt(0)) ;
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
			exportDominoFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuExportDominoMn").charAt(0)) ;
			exportDominoFileMenuItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					/*
					 * Setting up a specific JFileChooser
					 */
					JFileChooser localFileChooser = new JFileChooser() ;
					localFileChooser.setDialogTitle(Bundle.getText("MainFrameFileExportProjectTitle")) ;
					localFileChooser.setAcceptAllFileFilterUsed(false) ;
					localFileChooser.setApproveButtonText(Bundle.getText("MainFrameFileExportProjectButton")) ;
					File localDirectory = new File(Preferences.getInstance().getExportDirectory()) ;
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
							return Bundle.getText("MainFrameFileDominoFileDescription") ;
						}
					}) ;
					localFileChooser.showSaveDialog(MainFrame.this) ;

					/*
					 * Working on a selected file
					 */
					File localFile = null ;
					if ( (localFile = localFileChooser.getSelectedFile()) != null)
					{
						// Adding extension if necessary
						if (!localFile.getName().endsWith(".xml"))
						{
							localFile = new File(localFile.getAbsolutePath() + ".xml") ;
						}

						// Checking if the file already exists before saving
						if (!localFile.exists()
								|| JOptionPane.showConfirmDialog(MainFrame.this, Bundle.getText("MainFrameFileSaveConfirm"), "PSI", JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
						{
							try
							{
								ProjectControler.save(currentProject, localFile) ;
								actionSave.setEnabled(false) ;
								LogPanel.getInstance().addInformation(new LogInformation(Bundle.getText("MainFrameLogMessageProjectExportedToDomino"))) ;

							}
							catch (FileSaveException exc)
							{
								JOptionPane.showMessageDialog(MainFrame.this, Bundle.getText("MainFrameFileExportError"), "PSI", JOptionPane.ERROR_MESSAGE) ;
							}
						}
					}
				}
			}) ;
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
			export2DBFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuExport2DBMn").charAt(0)) ;
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
			exportOWFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuExportOWMn").charAt(0)) ;
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
			rightSplitPane.setOneTouchExpandable(true) ;
			rightSplitPane.setDividerLocation(Preferences.getInstance().getLogHeight()) ;
			rightSplitPane.addPropertyChangeListener(new java.beans.PropertyChangeListener()
			{
				public void propertyChange (java.beans.PropertyChangeEvent e)
				{
					if ( (e.getPropertyName().equals("lastDividerLocation")))
					{
						Preferences.getInstance().setLogHeight(rightSplitPane.getDividerLocation()) ;
					}
				}
			}) ;
			rightSplitPane.setTopComponent(MainTabbedPane.getInstance()) ;
			getDefaultPanel();
			rightSplitPane.setBottomComponent(getLogContainer()) ;
		}
		return rightSplitPane ;
	}

	public void getDefaultPanel()
	{
		JPanel defaultPanel = new JPanel();
		//north.
		JPanel jPanelFlowNorth = new JPanel(new FlowLayout());
		JLabel jLabelLogo = new JLabel(new ImageIcon(getClass().getResource("/ui/resource/psilogo150_150.gif")));
		jPanelFlowNorth.add(jLabelLogo);
		JTextArea jTextAreaWelcom = new JTextArea(Bundle.getText("MainFrameDefaultPanelDescription"));
		jTextAreaWelcom.setPreferredSize(new Dimension(400, 250));
		jTextAreaWelcom.setBackground(new java.awt.Color(238,238,238));
		jPanelFlowNorth.add(jTextAreaWelcom);
		//center.
		JPanel jPanelFlowCenter = new JPanel(new FlowLayout());
		JButton jButtonCreate = new JButton() ;
		jButtonCreate.setAction(actionCreate) ;
		jButtonCreate.setIcon(new ImageIcon(getClass().getResource("/ui/resource/create.gif")));
		jButtonCreate.setPreferredSize(new Dimension(100, 100));
		jButtonCreate.setToolTipText(Bundle.getText("MainFrameDefaultPanelCreateButton"));
		JButton jButtonOpen = new JButton() ;
		jButtonOpen.setAction(actionOpen) ;
		jButtonOpen.setIcon(new ImageIcon(getClass().getResource("/ui/resource/open.gif")));
		jButtonOpen.setPreferredSize(new Dimension(100, 100));
		jButtonOpen.setToolTipText(Bundle.getText("MainFrameDefaultPanelOpenButton"));
		JButton jButtonHelp = new JButton() ;
		jButtonHelp.setAction(new AbstractAction(){
			private static final long serialVersionUID = 7373920162223888058L ;

			public void actionPerformed (ActionEvent e)
			{
				MainTabbedPane mainTabbedPane = MainTabbedPane.getInstance();
				if((mainTabbedPane.getTabCount()>0)&&(mainTabbedPane.getTitleAt(0).equals(Bundle.getText("MainFrameDefaultPanelTitle"))))
					mainTabbedPane.remove(0);
				new HelpFrame();
			}
		}) ;
		jButtonHelp.setIcon(new ImageIcon(getClass().getResource("/ui/resource/help.gif")));
		jButtonHelp.setPreferredSize(new Dimension(100, 100));
		jButtonHelp.setToolTipText(Bundle.getText("MainFrameDefaultPanelHelpButton"));
		jPanelFlowCenter.add(jButtonCreate);
		jPanelFlowCenter.add(Box.createRigidArea(new Dimension(75, 1)));
		jPanelFlowCenter.add(jButtonOpen);
		jPanelFlowCenter.add(Box.createRigidArea(new Dimension(75, 1)));
		jPanelFlowCenter.add(jButtonHelp);
		//main.
		defaultPanel.setLayout(new BorderLayout());
		defaultPanel.add(jPanelFlowCenter, BorderLayout.CENTER);
		defaultPanel.add(jPanelFlowNorth, BorderLayout.NORTH);
		MainTabbedPane.getInstance().addTab(Bundle.getText("MainFrameDefaultPanelTitle"), defaultPanel);
	}
	
	/**
	 * This method initializes treeScrollPane
	 *
	 * @return javax.swing.JScrollPane.
	 */
	public JScrollPane getTreeScrollPane ()
	{
		if (treeScrollPane == null)
		{
			treeScrollPane = new JScrollPane(getProjectTree()) ;			
		}
		return treeScrollPane ;
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
			logContainer.add(Bundle.getText("MainFrameLogTab"), LogPanel.getInstance()) ;
		}
		return logContainer ;
	}

	

	public Project getProject ()
	{
		return this.currentProject ;
	}

	/**
	 * This method initializes mainContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMainContentPane ()
	{
		if (mainContentPane == null)
		{
			mainContentPane = new JPanel() ;
			mainContentPane.setLayout(new BorderLayout());
			mainContentPane.add(getMainSplitPane(), java.awt.BorderLayout.CENTER);
			mainContentPane.add(getStatusPanel(), java.awt.BorderLayout.SOUTH);
			mainContentPane.add(getMainToolBar(), java.awt.BorderLayout.NORTH);
		}
		return mainContentPane ;
	}

	/**
	 * This method initializes statusPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getStatusPanel ()
	{
		if (statusPanel == null)
		{
			statusLabel = new JLabel();
			statusLabel.setText("JLabel");
			statusPanel = new JPanel() ;
			statusPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			statusPanel.add(statusLabel, null);
		}
		return statusPanel ;
	}

	/**
	 * This method initializes mainToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getMainToolBar ()
	{
		if (mainToolBar == null)
		{
			mainToolBar = new JToolBar() ;
			mainToolBar.add(getCreateButton());
			mainToolBar.add(getOpenButton());
			mainToolBar.add(getSaveButton());
			mainToolBar.add(getSaveAsButton());
			mainToolBar.addSeparator() ;			
			mainToolBar.add(getIterationButton());			
		}
		return mainToolBar ;
	}

	/**
	 * This method initializes createButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCreateButton ()
	{
		if (createButton == null)
		{
			createButton = new JButton() ;
			createButton.setAction(actionCreate) ;
			createButton.setToolTipText(Bundle.getText("MainFrameFileCreateProjectButton"));
			createButton.setIcon(new ImageIcon(getClass().getResource("/ui/resource/tools_create.gif")));			
		}
		return createButton ;
	}

	/**
	 * This method initializes openButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOpenButton ()
	{
		if (openButton == null)
		{
			openButton = new JButton() ;
			openButton.setAction(actionOpen) ;	
			openButton.setToolTipText(Bundle.getText("MainFrameFileOpenProjectButton"));
			openButton.setIcon(new ImageIcon(getClass().getResource("/ui/resource/tools_open.gif")));
		}
		return openButton ;
	}

	/**
	 * This method initializes saveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveButton ()
	{
		if (saveButton == null)
		{
			saveButton = new JButton() ;
			saveButton.setAction(actionSave) ;
			saveButton.setToolTipText(Bundle.getText("MainFrameFileSaveProjectButton"));
			saveButton.setIcon(new ImageIcon(getClass().getResource("/ui/resource/tools_save.gif")));
		}
		return saveButton ;
	}

	/**
	 * This method initializes iterationButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIterationButton ()
	{
		if (iterationButton == null)
		{
			iterationButton = new JButton() ;
			iterationButton.setToolTipText(Bundle.getText("MainFrameFileIterateProjectButton"));
			iterationButton.setIcon(new ImageIcon(getClass().getResource("/ui/resource/tools_iteration.gif")));
		}
		return iterationButton ;
	}

	/**
	 * This method initializes saveAsButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveAsButton ()
	{
		if (saveAsButton == null)
		{
			saveAsButton = new JButton() ;
			saveAsButton.setAction(actionSaveAs) ;
			saveAsButton.setToolTipText(Bundle.getText("MainFrameFileSaveAsProjectButton"));
			saveAsButton.setIcon(new ImageIcon(getClass().getResource("/ui/resource/tools_save_as.gif")));
		}
		return saveAsButton ;
	}

} // @jve:decl-index=0:visual-constraint="158,10"
