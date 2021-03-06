
package ui.window ;

import java.awt.BorderLayout ;
import java.awt.Color ;
import java.awt.Dimension ;
import java.awt.FlowLayout ;
import java.awt.HeadlessException ;
import java.awt.Toolkit;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.io.File ;
import java.text.DateFormat ;
import java.util.ArrayList ;
import java.util.Date ;
import java.util.prefs.Preferences ;

import javax.swing.AbstractAction ;
import javax.swing.Box ;
import javax.swing.ImageIcon ;
import javax.swing.JButton ;
import javax.swing.JFileChooser ;
import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JMenu ;
import javax.swing.JMenuBar ;
import javax.swing.JMenuItem ;
import javax.swing.JOptionPane ;
import javax.swing.JPanel ;
import javax.swing.JScrollPane ;
import javax.swing.JSplitPane ;
import javax.swing.JTabbedPane ;
import javax.swing.JTextArea ;
import javax.swing.JToolBar ;
import javax.swing.KeyStroke ;
import javax.swing.SwingUtilities ;
import javax.swing.Timer ;
import javax.swing.UIManager ;
import javax.swing.WindowConstants ;
import javax.swing.UIManager.LookAndFeelInfo ;
import javax.swing.filechooser.FileFilter ;

import model.LogInformation ;
import model.Project ;
import process.GlobalController ;
import process.exception.FileParseException ;
import process.exception.FileSaveException ;
import process.utility.BreakdownElementsControler ;
import process.utility.ProcessControler ;
import process.utility.ProjectControler ;
import ui.dialog.AboutDialog ;
import ui.dialog.PreferencesDialog ;
import ui.dialog.ProgressDialog ;
import ui.misc.LogPanel ;
import ui.misc.MainTabbedPane ;
import ui.misc.TaskDefinitionPanel ;
import ui.resource.Bundle ;
import ui.tree.MainTree ;

import ui.resource.LocaleController ;
import ui.resource.LocaleListener ;

/**
 * MainFrame : PSI main window
 * 
 * @author Conde Mickael K.
 * @author MaT
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

	private JMenuItem importTasksFileMenuItem = null ;

	private JMenuItem saveFileMenuItem = null ;

	private JMenuItem saveAsFileMenuItem = null ;

	private JMenuItem exportDominoFileMenuItem = null ;

	private JMenuItem export2DBFileMenuItem = null ;

	private JMenuItem exportOWFileMenuItem = null ;

	private JMenu exportFileMenu = null ;

	private MainTree projectTree = null ;

	private JSplitPane rightSplitPane = null ;

	private JScrollPane treeScrollPane = null ;

	private JTabbedPane logContainer = null ;

	private Project currentProject = null ;

	private JPanel mainContentPane = null ;

	private LocaleController controllerLocale = null ;

	private JPanel statusPanel = null ;

	private JLabel statusLabel = null ;

	private JToolBar mainToolBar = null ;

	private JButton createButton = null ;

	private JButton openButton = null ;

	private JButton saveButton = null ;

	private JButton iterationButton = null ;

	private JButton saveAsButton = null ;

	private JTextArea jTextAreaWelcom = null ;

	private JButton jButtonCreate = null ;

	private JButton jButtonOpen = null ;

	private JButton jButtonHelp = null ;

	private JPanel defaultPanel = null ;

	Preferences preferences = null ;

	private Timer statusbarTimer = null ;

	private boolean statusbarflash = true ;

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

	/**
	 * @throws HeadlessException
	 */
	public MainFrame () throws HeadlessException
	{
		super() ;
		preferences = Preferences.userRoot() ;

		// Trying to apply laf;
		LookAndFeelInfo localLAF = new LookAndFeelInfo(preferences.get("laf", ""), preferences.get("lafclass", "")) ;
		try
		{
			UIManager.setLookAndFeel(localLAF.getClassName()) ;
			SwingUtilities.updateComponentTreeUI(MainFrame.this) ;
		}
		catch (Exception exc)
		{
		}
		this.controllerLocale = LocaleController.getInstance() ;
		this.controllerLocale.addLocaleListener(new LocaleListener()
		{
			public void localeChanged ()
			{
				initText() ;
			}
		}) ;
		new SplashScreen(this, 4) ;
		initialize() ;
		initText() ;
		LogPanel.getInstance().addInformation(new LogInformation(Bundle.getText("MainFrameLogMessageAppStarted"))) ;

		// Trying to open the last project if necessary
		if (preferences.getBoolean("load_last_project", true))
		{
			try
			{
				// close the defaut tab.
				if (defaultPanel != null)
				{
					MainTabbedPane mainTabbedPane = MainTabbedPane.getInstance() ;
					mainTabbedPane.remove(0) ;
					defaultPanel = null ;
				}

				currentProject = ProjectControler.open(new File(preferences.get("last_project", ""))) ;
				if (currentProject != null)
				{
					// Updating actions
					actionImport.setEnabled(true) ;
					actionSave.setEnabled(true) ;
					actionSaveAs.setEnabled(true) ;
					getIterationButton().setEnabled(true) ;
					getExportFileMenu().setEnabled(true) ;

					getProjectTree().loadProject(currentProject) ;
					LogPanel.getInstance().addInformation(
							new LogInformation(Bundle.getText("MainFrameLogMessageProjectOpened") + " : " + currentProject.getName())) ;
				}
			}
			catch (FileParseException exc)
			{
				JOptionPane.showMessageDialog(MainFrame.this, Bundle.getText("MainFrameFileOpenIncorrectFormat"), "PSI", JOptionPane.ERROR_MESSAGE) ;
			}
		}
		
		// Opening help if necessary
		if (preferences.getBoolean("load_help", true))
		{
			//new HelpFrame() ;
			
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize ()
	{
		this.setName("mainFrame") ;
		this.setJMenuBar(getMainMenuBar()) ;
		this.setTitle("Project Supervising Indicators") ;
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/ui/resource/psilogo.gif"))) ;
		this.setBounds(preferences.getInt("window_xposition", 0), preferences.getInt("window_yposition", 0), preferences.getInt("window_width", 700),
				preferences.getInt("window_height", 600)) ;
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) ;

		this.setContentPane(getMainContentPane()) ;

		/*
		 * Actions deactivation
		 */
		actionImport.setEnabled(false) ;
		actionSave.setEnabled(false) ;
		actionSaveAs.setEnabled(false) ;
		getIterationButton().setEnabled(false) ;
		getExportFileMenu().setEnabled(false) ;
		getImportTasksFileMenuItem().setEnabled(false) ;

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
				preferences.putInt("window_width", e.getComponent().getWidth()) ;
				preferences.putInt("window_height", e.getComponent().getHeight()) ;
			}

			/**
			 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
			 */
			public void componentMoved (java.awt.event.ComponentEvent e)
			{
				preferences.putInt("window_xposition", e.getComponent().getX()) ;
				preferences.putInt("window_yposition", e.getComponent().getY()) ;
			}
		}) ;

		statusbarTimer = new Timer(1000, new ActionListener()
		{
			public void actionPerformed (ActionEvent _e)
			{
				Date now = new Date() ;
				statusLabel.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT,Bundle.getCurrentLocale()).format(now)) ;
			}
		}) ;
		this.statusbarTimer.start() ;

		statusbarTimer = new Timer(1000, new ActionListener()
		{
			public void actionPerformed (ActionEvent _e)

			{
				Date now = new Date() ;
				String DateText = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT,Bundle.getCurrentLocale()).format(now) ;
				if (statusbarflash == false)
				{
					String part[] ;
					part = DateText.split(":") ;
					DateText = part[0] + " " + part[1] ;
					statusbarflash = true ;
				}
				else
					statusbarflash = false ;

				statusLabel.setText(DateText) ;

			}
		}) ;
		this.statusbarTimer.start() ;
	}

	/**
	 * 
	 */
	private void initText ()
	{
		fileMenu.setText(Bundle.getText("MainFrameFileMenu")) ;
		fileMenu.setMnemonic(Bundle.getText("MainFrameFileMenuMn").charAt(0)) ;
		editMenu.setText(Bundle.getText("MainFrameEditMenu")) ;
		editMenu.setMnemonic(Bundle.getText("MainFrameEditMenuMn").charAt(0)) ;
		aboutMenu.setText(Bundle.getText("MainFrameAboutMenu")) ;
		// aboutMenu.setMnemonic(Bundle.getText("MainFrameAboutMenuMn").charAt(0)) ;
		openFileMenuItem.setText(Bundle.getText("MainFrameFileMenuOpen")) ;
		openFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuOpenMn").charAt(0)) ;
		createFileMenuItem.setText(Bundle.getText("MainFrameFileMenuCreate")) ;
		createFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuCreateMn").charAt(0)) ;
		importProcessFileMenuItem.setText(Bundle.getText("MainFrameFileMenuImportProcess")) ;
		importProcessFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuImportProcessMn").charAt(0)) ;
		importTasksFileMenuItem.setText(Bundle.getText("MainFrameFileMenuImportOW")) ;
		importTasksFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuImportOWMn").charAt(0)) ;
		saveFileMenuItem.setText(Bundle.getText("MainFrameFileMenuSave")) ;
		saveFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuSaveMn").charAt(0)) ;
		saveAsFileMenuItem.setText(Bundle.getText("MainFrameFileMenuSaveAs")) ;
		saveAsFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuSaveAsMn").charAt(0)) ;
		closeFileMenuItem.setText(Bundle.getText("MainFrameFileMenuClose")) ;
		closeFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuCloseMn").charAt(0)) ;
		quitFileMenuItem.setText(Bundle.getText("MainFrameFileMenuQuit")) ;
		quitFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuQuitMn").charAt(0)) ;
		helpAboutMenuItem.setText(Bundle.getText("MainFrameAboutMenuHelp")) ;
		helpAboutMenuItem.setMnemonic(Bundle.getText("MainFrameAboutMenuHelpMn").charAt(0)) ;
		aboutAboutMenuItem.setText(Bundle.getText("MainFrameAboutMenuAbout")) ;
		aboutAboutMenuItem.setMnemonic(Bundle.getText("MainFrameAboutMenuAboutMn").charAt(0)) ;
		prefsEditMenuItem.setText(Bundle.getText("MainFrameEditMenuPrefs")) ;
		prefsEditMenuItem.setMnemonic(Bundle.getText("MainFrameEditMenuPrefsMn").charAt(0)) ;
		exportFileMenu.setText(Bundle.getText("MainFrameFileMenuExport")) ;
		exportFileMenu.setMnemonic(Bundle.getText("MainFrameFileMenuExportMn").charAt(0)) ;
		export2DBFileMenuItem.setText(Bundle.getText("MainFrameFileMenuExport2DB")) ;
		export2DBFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuExport2DBMn").charAt(0)) ;
		exportOWFileMenuItem.setText(Bundle.getText("MainFrameFileMenuExportOW")) ;
		exportOWFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuExportOWMn").charAt(0)) ;
		exportDominoFileMenuItem.setText(Bundle.getText("MainFrameFileMenuExportDomino")) ;
		exportDominoFileMenuItem.setMnemonic(Bundle.getText("MainFrameFileMenuExportDominoMn").charAt(0)) ;
		createButton.setToolTipText(Bundle.getText("MainFrameFileCreateProjectButton")) ;
		openButton.setToolTipText(Bundle.getText("MainFrameFileOpenProjectButton")) ;
		saveButton.setToolTipText(Bundle.getText("MainFrameFileSaveProjectButton")) ;
		iterationButton.setToolTipText(Bundle.getText("MainFrameFileIterateProjectButton")) ;
		saveAsButton.setToolTipText(Bundle.getText("MainFrameFileSaveAsProjectButton")) ;
		jTextAreaWelcom.setText(Bundle.getText("MainFrameDefaultPanelDescription")) ;
		jButtonCreate.setToolTipText(Bundle.getText("MainFrameDefaultPanelCreateButton")) ;
		jButtonOpen.setToolTipText(Bundle.getText("MainFrameDefaultPanelOpenButton")) ;
		jButtonHelp.setToolTipText(Bundle.getText("MainFrameDefaultPanelHelpButton")) ;
		if (defaultPanel != null) MainTabbedPane.getInstance().setTitleAt(0, Bundle.getText("MainFrameDefaultPanelTitle")) ;
		logContainer.setTitleAt(0, Bundle.getText("MainFrameLogTab")) ;
		statusLabel.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT,Bundle.getCurrentLocale()).format(new Date())) ;
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
		// Preferences.getInstance().save() ;

		if (GlobalController.projectChanged)
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

		}

		else
		{
			System.exit(0) ;
		}
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
		/*
		 * Setting up a JFile Chooser with a special file filter that will only accept .xml files
		 */
		JFileChooser localFileChooser = new JFileChooser() ;
		localFileChooser.setDialogTitle(Bundle.getText("MainFrameFileCreateProjectTitle")) ;
		localFileChooser.setAcceptAllFileFilterUsed(false) ;
		localFileChooser.setApproveButtonText(Bundle.getText("MainFrameFileCreateProjectButton")) ;
		File localDirectory = new File(preferences.get("work_directory", "")) ;
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
			ProgressDialog progressbar = new ProgressDialog(this) ;
			progressbar.initialize() ;

			actionImport.setEnabled(false) ;

			if (preferences.get("work_directory", "").equals(""))
			{
				preferences.put("work_directory", localDirectory.getAbsolutePath()) ;
			}

			try
			{
				currentProject = ProjectControler.create(localFile) ;
				if (currentProject != null)
				{
					// Resetting the currentProject
					preferences.put("last_project", "") ;
					getProjectTree().loadProject(currentProject) ;
					actionImport.setEnabled(true) ;
					LogPanel.getInstance().addInformation(
							new LogInformation(Bundle.getText("MainFrameLogMessageProjectCreated") + " : " + currentProject.getName())) ;
				}
				// close the defaut tab.
				if (defaultPanel != null)
				{
					MainTabbedPane mainTabbedPane = MainTabbedPane.getInstance() ;
					mainTabbedPane.remove(0) ;
					defaultPanel = null ;
				}
			}
			catch (FileParseException exc)
			{
				JOptionPane.showMessageDialog(MainFrame.this, Bundle.getText("MainFrameFileCreateIncorrectFormat"), "PSI", JOptionPane.ERROR_MESSAGE) ;
			}
			progressbar.stopTimer() ;
			progressbar.dispose() ;
		}

	}

	/**
	 * Imports a process dpe description to a newly created project
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param e
	 *            the Action Event that caused the action
	 */
	private void actionImport (ActionEvent evt)
	{
		/*
		 * Setting up a JFile Chooser with a special file filter that will only accept .dpe files
		 * generated by IEPP
		 */
		JFileChooser localFileChooser = new JFileChooser() ;
		localFileChooser.setDialogTitle(Bundle.getText("MainFrameFileImportProjectTitle")) ;
		localFileChooser.setAcceptAllFileFilterUsed(false) ;
		localFileChooser.setApproveButtonText(Bundle.getText("MainFrameFileImportProjectButton")) ;
		File localDirectory = new File(preferences.get("work_directory", "")) ;

		if (localDirectory.exists())
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
			ProgressDialog progressbar = new ProgressDialog(this) ;
			progressbar.initialize() ;

			try
			{
				if ( (currentProject == null) || (currentProject.getProcess() == null))
				{
					currentProject.setProcess(ProcessControler.load(localFile)) ;
					if (currentProject != null && currentProject.getProcess() != null)
					{
						// Updating actions
						actionImport.setEnabled(true) ;
						actionSave.setEnabled(true) ;
						actionSaveAs.setEnabled(true) ;
						getIterationButton().setEnabled(true) ;
						getExportFileMenu().setEnabled(true) ;

						// Updating UI
						getProjectTree().loadProject(currentProject) ;
						LogPanel.getInstance().addInformation(
								new LogInformation(Bundle.getText("MainFrameLogMessageProcessImported") + " : "
										+ currentProject.getProcess().getDescriptor().getName())) ;
					}
				}
				else
				{
					ProjectControler.importFromOpenWorkbench(localFile, currentProject) ;
				}
			}
			catch (FileParseException exc)
			{
				JOptionPane.showMessageDialog(MainFrame.this, Bundle.getText("MainFrameFileImportIncorrectFormat"), "PSI", JOptionPane.ERROR_MESSAGE) ;
			}

			progressbar.stopTimer() ;
			progressbar.dispose() ;

			// close the defaut tab.
			if (defaultPanel != null)
			{
				MainTabbedPane mainTabbedPane = MainTabbedPane.getInstance() ;
				mainTabbedPane.remove(0) ;
				defaultPanel = null ;
			}
		}
	}

	/**
	 * Opens a project from file
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param e
	 *            the Action Event that caused the action
	 */
	private void actionOpen (ActionEvent e)
	{
		JFileChooser localFileChooser = new JFileChooser() ;
		localFileChooser.setDialogTitle(Bundle.getText("MainFrameFileOpenProjectTitle")) ;
		localFileChooser.setAcceptAllFileFilterUsed(false) ;
		File localDirectory = new File(preferences.get("work_directory", "")) ;
		if (localDirectory.exists())
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
		localFileChooser.showOpenDialog(MainFrame.this) ;

		/*
		 * Working on a selected file
		 */
		File localFile ;
		if ( (localFile = localFileChooser.getSelectedFile()) != null && localFile.exists())
		{
			if (preferences.get("work_directory", "").equals(""))
			{
				preferences.put("work_directory", localDirectory.getAbsolutePath()) ;
			}

			try
			{
				currentProject = ProjectControler.open(localFile) ;
				if (currentProject != null)
				{
					preferences.put("last_project", localFile.getAbsolutePath()) ;
					// Updating actions
					actionImport.setEnabled(true) ;
					actionSave.setEnabled(true) ;
					actionSaveAs.setEnabled(true) ;
					getIterationButton().setEnabled(true) ;
					getExportFileMenu().setEnabled(true) ;
					getImportTasksFileMenuItem().setEnabled(true) ;

					getProjectTree().loadProject(currentProject) ;
					LogPanel.getInstance().addInformation(
							new LogInformation(Bundle.getText("MainFrameLogMessageProjectOpened") + " : " + currentProject.getName())) ;
					// close the defaut tab.
					if (defaultPanel != null)
					{
						MainTabbedPane mainTabbedPane = MainTabbedPane.getInstance() ;
						mainTabbedPane.remove(0) ;
						defaultPanel = null ;
					}
				}
			}
			catch (FileParseException exc)
			{
				JOptionPane.showMessageDialog(MainFrame.this, Bundle.getText("MainFrameFileOpenIncorrectFormat"), "PSI", JOptionPane.ERROR_MESSAGE) ;
			}
		}

	}

	/**
	 * Saves the current project (quick save)
	 * 
	 * @author Conde Mickael K.
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
		if (!GlobalController.projectChanged) { return ; }

		File localFile = new File(preferences.get("last_project", "")) ;

		if (!localFile.exists())
		{
			actionSaveAs() ;
			return ;
		}

		try
		{
			ProjectControler.save(currentProject, localFile, false) ;
			LogPanel.getInstance().addInformation(new LogInformation(Bundle.getText("MainFrameLogMessageProjectSaved"))) ;
			GlobalController.projectChanged = false ;

		}
		catch (FileSaveException exc)
		{
			JOptionPane.showMessageDialog(MainFrame.this, Bundle.getText("MainFrameFileSaveError"), "PSI", JOptionPane.ERROR_MESSAGE) ;
		}
	}

	/**
	 * Saves the current project [under another name]
	 * 
	 * @author Conde Mickael K.
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
		File localDirectory = new File(preferences.get("work_directory", "")) ;
		if (localDirectory.exists())
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
			ProgressDialog progressbar = new ProgressDialog(this) ;
			progressbar.initialize() ;

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
					ProjectControler.save(currentProject, localFile, false) ;
					preferences.put("last_project", localFile.getAbsolutePath()) ;
					LogPanel.getInstance().addInformation(
							new LogInformation(Bundle.getText("MainFrameLogMessageProjectSaved") + " (" + localFile.getName() + ")")) ;
					GlobalController.projectChanged = false ;

				}
				catch (FileSaveException exc)
				{
					JOptionPane.showMessageDialog(MainFrame.this, Bundle.getText("MainFrameFileSaveError"), "PSI", JOptionPane.ERROR_MESSAGE) ;
				}
			}
			progressbar.stopTimer() ;
			progressbar.dispose() ;
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
			mainSplitPane.setDividerLocation(preferences.getInt("window_treewidth", 250)) ;
			mainSplitPane.addPropertyChangeListener("lastDividerLocation", new java.beans.PropertyChangeListener()
			{
				public void propertyChange (java.beans.PropertyChangeEvent e)
				{
					preferences.putInt("window_treewidth", mainSplitPane.getDividerLocation()) ;
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
			fileMenu.add(getOpenFileMenuItem()) ;
			fileMenu.add(getCloseFileMenuItem()) ;
			fileMenu.addSeparator() ;
			fileMenu.add(getCreateFileMenuItem()) ;
			fileMenu.addSeparator() ;
			fileMenu.add(getImportProcessFileMenuItem()) ;
			fileMenu.add(getImportTasksFileMenuItem()) ;
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
			importProcessFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK)) ;
		}
		return importProcessFileMenuItem ;
	}

	/**
	 * This method initializes importProjectFileMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getImportTasksFileMenuItem ()
	{
		if (importTasksFileMenuItem == null)
		{
			importTasksFileMenuItem = new JMenuItem() ;
			importTasksFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK
					| java.awt.event.InputEvent.SHIFT_MASK)) ;
			importTasksFileMenuItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					/*
					 * Setting up a specific JFileChooser
					 */
					JFileChooser localFileChooser = new JFileChooser() ;
					localFileChooser.setDialogTitle(Bundle.getText("MainFrameFileImportTasksTitle")) ;
					localFileChooser.setAcceptAllFileFilterUsed(false) ;
					localFileChooser.setApproveButtonText(Bundle.getText("MainFrameFileImportTasksButton")) ;
					File localDirectory = new File(preferences.get("work_directory", "")) ;
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
						ProgressDialog progressbar = new ProgressDialog(MainFrame.this) ;
						progressbar.initialize() ;

						if (preferences.get("work_directory", "").equals(""))
						{
							preferences.put("work_directory", localDirectory.getAbsolutePath()) ;
						}

						try
						{
							ProjectControler.importFromOpenWorkbench(localFile, currentProject) ;
							LogPanel.getInstance().addInformation(new LogInformation(Bundle.getText("MainFrameLogMessageTasksImported"))) ;
						}
						catch (FileParseException exc)
						{
							JOptionPane.showMessageDialog(MainFrame.this, Bundle.getText("MainFrameFileImportTasksIncorrectFormat"), "PSI",
									JOptionPane.ERROR_MESSAGE) ;
						}

						progressbar.stopTimer() ;
						progressbar.dispose() ;
					}
				}
			}) ;
		}
		return importTasksFileMenuItem ;
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
			closeFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK)) ;
			closeFileMenuItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					currentProject = null ;
					projectTree.closeProject() ;
					MainTabbedPane.getInstance().removeAll() ;
					getDefaultPanel() ;

					// Actions deactivation
					actionImport.setEnabled(false) ;
					actionSave.setEnabled(false) ;
					actionSaveAs.setEnabled(false) ;
					getIterationButton().setEnabled(false) ;
					getExportFileMenu().setEnabled(false) ;
					getImportTasksFileMenuItem().setEnabled(false) ;
				}
			}) ;
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
			helpAboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK)) ;
			helpAboutMenuItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					/*MainTabbedPane mainTabbedPane = MainTabbedPane.getInstance() ;
					if ( (mainTabbedPane.getTabCount() > 0) && (mainTabbedPane.getTitleAt(0).equals(Bundle.getText("MainFrameDefaultPanelTitle"))))
					{
						mainTabbedPane.remove(0) ;
						defaultPanel = null ;
					}*/
					new HelpFrame() ;
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
			aboutAboutMenuItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					new AboutDialog() ;
					MainTabbedPane mainTabbedPane = MainTabbedPane.getInstance() ;
					if ( (mainTabbedPane.getTabCount() > 0) && (mainTabbedPane.getTitleAt(0).equals(Bundle.getText("MainFrameDefaultPanelTitle"))))
						mainTabbedPane.remove(0) ;
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
			prefsEditMenuItem.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					new PreferencesDialog(MainFrame.this) ;
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
					File localDirectory = new File(preferences.get("export_directory", "")) ;
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
							return (_file.isDirectory() || (_file.isFile() && _file.canRead() && (localFileExtension.equalsIgnoreCase("xml") || localFileExtension.equalsIgnoreCase("mes")))) ;
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
						if (!localFile.getName().endsWith(".mes"))
						{
							localFile = new File(localFile.getAbsolutePath() + ".mes") ;
						}

						// Checking if the file already exists before saving
						if (!localFile.exists()
								|| JOptionPane.showConfirmDialog(MainFrame.this, Bundle.getText("MainFrameFileSaveConfirm"), "PSI", JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
						{
							try
							{
								ProjectControler.save(currentProject, localFile, true) ;
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
			export2DBFileMenuItem.addActionListener(new java.awt.event.ActionListener()
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
					File localDirectory = new File(preferences.get("export_directory", "")) ;
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
							return Bundle.getText("MainFrameFile2DBDescription") ;
						}
					}) ;
					localFileChooser.showSaveDialog(MainFrame.this) ;

					/*
					 * Working on a selected file
					 */
					File localFile = null ;
					if ( (localFile = localFileChooser.getSelectedFile()) != null)
					{
						ProgressDialog progressbar = new ProgressDialog(MainFrame.this) ;
						progressbar.initialize() ;

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
								ProjectControler.exportTo2DB(currentProject, localFile) ;
								LogPanel.getInstance().addInformation(new LogInformation(Bundle.getText("MainFrameLogMessageProjectExportedTo2DB"))) ;

							}
							catch (FileSaveException exc)
							{
								JOptionPane.showMessageDialog(MainFrame.this, Bundle.getText("MainFrameFileExportError"), "PSI", JOptionPane.ERROR_MESSAGE) ;
							}
						}

						progressbar.stopTimer() ;
						progressbar.dispose() ;
					}
				}
			}) ;
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
			exportOWFileMenuItem.addActionListener(new java.awt.event.ActionListener()
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
					File localDirectory = new File(preferences.get("export_directory", "")) ;
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
					localFileChooser.showSaveDialog(MainFrame.this) ;

					/*
					 * Working on a selected file
					 */
					File localFile = null ;
					if ( (localFile = localFileChooser.getSelectedFile()) != null)
					{
						ProgressDialog progressbar = new ProgressDialog(MainFrame.this) ;
						progressbar.initialize() ;

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
								ProjectControler.exportToOpenWorkbench(currentProject, localFile) ;
								LogPanel.getInstance().addInformation(new LogInformation(Bundle.getText("MainFrameLogMessageProjectExportedToOW"))) ;

							}
							catch (FileSaveException exc)
							{
								JOptionPane.showMessageDialog(MainFrame.this, Bundle.getText("MainFrameFileExportError"), "PSI", JOptionPane.ERROR_MESSAGE) ;
							}
						}

						progressbar.stopTimer() ;
						progressbar.dispose() ;
					}
				}
			}) ;
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
			rightSplitPane.setDividerLocation(preferences.getInt("window_mainheight", 400)) ;
			rightSplitPane.addPropertyChangeListener(new java.beans.PropertyChangeListener()
			{
				public void propertyChange (java.beans.PropertyChangeEvent e)
				{
					if ( (e.getPropertyName().equals("lastDividerLocation")))
					{
						preferences.putInt("window_mainheight", rightSplitPane.getDividerLocation()) ;
					}
				}
			}) ;
			rightSplitPane.setTopComponent(MainTabbedPane.getInstance()) ;
			getDefaultPanel() ;
			rightSplitPane.setBottomComponent(getLogContainer()) ;
		}
		return rightSplitPane ;
	}

	public void getDefaultPanel ()
	{
		defaultPanel = new JPanel() ;
		// north.
		JPanel jPanelFlowNorth = new JPanel(new FlowLayout()) ;
		JLabel jLabelLogo = new JLabel(new ImageIcon(getClass().getResource("/ui/resource/psilogo150_150.gif"))) ;
		jPanelFlowNorth.add(jLabelLogo) ;
		jTextAreaWelcom = new JTextArea() ;
		jTextAreaWelcom.setPreferredSize(new Dimension(400, 250)) ;
		jTextAreaWelcom.setBackground(Color.WHITE) ;
		jTextAreaWelcom.setEditable(false) ;
		jPanelFlowNorth.add(jTextAreaWelcom) ;
		jPanelFlowNorth.setBackground(Color.WHITE) ;
		// center.
		JPanel jPanelFlowCenter = new JPanel(new FlowLayout()) ;
		jButtonCreate = new JButton() ;
		jButtonCreate.setAction(actionCreate) ;
		jButtonCreate.setIcon(new ImageIcon(getClass().getResource("/ui/resource/create.gif"))) ;
		jButtonCreate.setPreferredSize(new Dimension(100, 100)) ;
		jButtonOpen = new JButton() ;
		jButtonOpen.setAction(actionOpen) ;
		jButtonOpen.setIcon(new ImageIcon(getClass().getResource("/ui/resource/open.gif"))) ;
		jButtonOpen.setPreferredSize(new Dimension(100, 100)) ;
		jButtonHelp = new JButton() ;
		jButtonHelp.setAction(new AbstractAction()
		{
			private static final long serialVersionUID = 7373920162223888058L ;

			public void actionPerformed (ActionEvent e)
			{
				/*MainTabbedPane mainTabbedPane = MainTabbedPane.getInstance() ;
				if ( (mainTabbedPane.getTabCount() > 0) && (mainTabbedPane.getTitleAt(0).equals(Bundle.getText("MainFrameDefaultPanelTitle"))))
				{
					mainTabbedPane.remove(0) ;
					defaultPanel = null ;
				}*/
				new HelpFrame() ;
			}
		}) ;
		jButtonHelp.setIcon(new ImageIcon(getClass().getResource("/ui/resource/help.gif"))) ;
		jButtonHelp.setPreferredSize(new Dimension(100, 100)) ;
		jPanelFlowCenter.add(jButtonCreate) ;
		jPanelFlowCenter.add(Box.createRigidArea(new Dimension(75, 1))) ;
		jPanelFlowCenter.add(jButtonOpen) ;
		jPanelFlowCenter.add(Box.createRigidArea(new Dimension(75, 1))) ;
		jPanelFlowCenter.add(jButtonHelp) ;
		jPanelFlowCenter.setBackground(Color.WHITE) ;
		// main.
		defaultPanel.setLayout(new BorderLayout()) ;
		defaultPanel.add(jPanelFlowCenter, BorderLayout.CENTER) ;
		defaultPanel.add(jPanelFlowNorth, BorderLayout.NORTH) ;
		defaultPanel.setBackground(Color.WHITE) ;
		MainTabbedPane.getInstance().addTab(Bundle.getText("MainFrameDefaultPanelTitle"), defaultPanel) ;
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
			mainContentPane.setLayout(new BorderLayout()) ;
			mainContentPane.add(getMainSplitPane(), java.awt.BorderLayout.CENTER) ;
			mainContentPane.add(getStatusPanel(), java.awt.BorderLayout.SOUTH) ;
			mainContentPane.add(getMainToolBar(), java.awt.BorderLayout.NORTH) ;
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
			statusLabel = new JLabel() ;

			statusLabel.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT,Bundle.getCurrentLocale()).format(new Date())) ;
			statusPanel = new JPanel() ;
			statusPanel.setLayout(new BorderLayout()) ;

			statusPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED)) ;
			statusPanel.add(statusLabel, java.awt.BorderLayout.EAST) ;
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
			mainToolBar.add(getCreateButton()) ;
			mainToolBar.add(getOpenButton()) ;
			mainToolBar.add(getSaveButton()) ;
			mainToolBar.add(getSaveAsButton()) ;
			mainToolBar.addSeparator() ;
			mainToolBar.add(getIterationButton()) ;
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
			createButton.setIcon(new ImageIcon(getClass().getResource("/ui/resource/tools_create.gif"))) ;
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
			openButton.setIcon(new ImageIcon(getClass().getResource("/ui/resource/tools_open.gif"))) ;
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
			saveButton.setIcon(new ImageIcon(getClass().getResource("/ui/resource/tools_save.gif"))) ;
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
			iterationButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					int localChoice = JOptionPane.showConfirmDialog(MainFrame.this, Bundle.getText("MainFrameConfirmIterationMessage"), "PSI",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) ;

					if (localChoice == JOptionPane.YES_OPTION)
					{
						boolean keep = JOptionPane.showConfirmDialog(MainFrame.this, Bundle.getText("MainFrameConfirmKeepTasks"), "PSI",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION ;

						BreakdownElementsControler.addIterationIntoProject(getProject(), keep) ;

						// Removing all tasks panel if necessary
						if (!keep)
						{
							int localTabCount = MainTabbedPane.getInstance().getTabCount() ;

							ArrayList <TaskDefinitionPanel> localTD = new ArrayList <TaskDefinitionPanel>() ;

							for (int i = 0; i < localTabCount; i++ )
							{
								if (MainTabbedPane.getInstance().getComponentAt(i) instanceof TaskDefinitionPanel)
								{
									localTD.add((TaskDefinitionPanel) MainTabbedPane.getInstance().getComponentAt(i)) ;
								}
							}

							for (int i = 0; i < localTD.size(); i++ )
							{
								MainTabbedPane.getInstance().remove(localTD.get(i)) ;
							}
						}
					}
				}
			}) ;

			iterationButton.setIcon(new ImageIcon(getClass().getResource("/ui/resource/tools_iteration.gif"))) ;
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
			saveAsButton.setIcon(new ImageIcon(getClass().getResource("/ui/resource/tools_save_as.gif"))) ;
		}
		return saveAsButton ;
	}

} // @jve:decl-index=0:visual-constraint="158,10"
