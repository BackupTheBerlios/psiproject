
package ui.dialog ;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import ui.resource.Bundle;
import ui.resource.LocaleController;
import ui.window.MainFrame;

/**
 * PreferencesDialog : Preferences dialog of the application
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public class PreferencesDialog extends JDialog
{

	private static final long serialVersionUID = 2680274552200355868L ;

	Preferences preferences ;

	MainFrame owner = null ;
	
	private LocaleController controllerLocale = null;
	
	private JPanel mainContentPane = null ;

	private JPanel buttonsPanel = null ;

	private JButton okButton = null ;

	private JButton cancelButton = null ;

	private JPanel mainPanel = null ;

	private JPanel infoPanel = null ;

	private JPanel workPanel = null ;

	private JPanel langPanel = null ;

	private JPanel lafPanel = null ;

	private JPanel savePanel = null ;

	private JPanel exportPanel = null ;

	private JPanel miscPanel = null ;

	private JLabel langLabel = null ;

	private JComboBox langComboBox = null ;

	private JLabel lafLabel = null ;

	private JComboBox lafComboBox = null ;

	private JLabel saveLabel = null ;

	private JTextField saveTextField = null ;

	private JButton saveButton = null ;

	private JLabel exportLabel = null ;

	private JTextField exportTextField = null ;

	private JButton exportButton = null ;

	private JCheckBox miscCheckBox = null ;

	private JLabel miscLabel = null ;

	private JPanel projectPanel = null ;

	private JCheckBox projectCheckBox = null ;

	private JLabel projectLabel = null ;
	
	/**
	 * This is the default constructor
	 */
	public PreferencesDialog (MainFrame _owner)
	{
		super(_owner) ;
		this.owner = _owner ;
		this.preferences = Preferences.userRoot() ;
		this.controllerLocale = LocaleController.getInstance();
		initialize() ;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{
		this.setTitle(Bundle.getText("PreferencesDialogTitle"));
		this.setResizable(false);
		this.setModal(true) ;		
		this.setContentPane(getMainContentPane()) ;
		this.setBounds(owner.getX() + owner.getWidth() / 2 - 275, owner.getY() + owner.getHeight() / 2 - 175, 550, 330) ;
		this.setVisible(true) ;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMainContentPane ()
	{
		if (mainContentPane == null)
		{
			mainContentPane = new JPanel() ;
			mainContentPane.setLayout(new BorderLayout()) ;
			mainContentPane.add(getMainPanel(), java.awt.BorderLayout.CENTER) ;
			mainContentPane.add(getButtonsPanel(), java.awt.BorderLayout.SOUTH) ;
		}
		return mainContentPane ;
	}

	/**
	 * This method initializes buttonsPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getButtonsPanel ()
	{
		if (buttonsPanel == null)
		{
			buttonsPanel = new JPanel() ;
			buttonsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			buttonsPanel.add(getOkButton(), null) ;
			buttonsPanel.add(getCancelButton(), null) ;
		}
		return buttonsPanel ;
	}

	/**
	 * This method initializes okButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getOkButton ()
	{
		if (okButton == null)
		{
			okButton = new JButton(Bundle.getText("OK")) ;
			okButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					// Directories
					preferences.put("work_directory", saveTextField.getText()) ;
					preferences.put("export_directory", exportTextField.getText()) ;
					
					// Misc options
					preferences.putBoolean("load_help", miscCheckBox.isSelected()) ;
					preferences.putBoolean("load_last_project", projectCheckBox.isSelected()) ;
					
					// Locale
					if (langComboBox.getSelectedIndex() == 0)
					{
						Bundle.setCurrentLocale(Locale.FRENCH) ;
						preferences.put("locale", "fr") ;
					}
					else
					{
						Bundle.setCurrentLocale(Locale.ENGLISH) ;
						preferences.put("locale", "en") ;
					}
					
					controllerLocale.fireLocaleChanged() ;
					
					// Look and feel
					LookAndFeelInfo localLAF = (LookAndFeelInfo) (lafComboBox.getSelectedItem()) ;
					try
					{
						UIManager.setLookAndFeel(localLAF.getClassName()) ;
						SwingUtilities.updateComponentTreeUI(PreferencesDialog.this) ;
						SwingUtilities.updateComponentTreeUI(PreferencesDialog.this.getParent()) ;
						preferences.put("laf", localLAF.getName()) ;
						preferences.put("lafclass", localLAF.getClassName()) ;
					}
					catch (Exception exc)
					{
					}					
					
					PreferencesDialog.this.dispose() ;
				}
			}) ;
		}
		return okButton ;
	}

	/**
	 * This method initializes cancelButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCancelButton ()
	{
		if (cancelButton == null)
		{
			cancelButton = new JButton(Bundle.getText("Cancel")) ;
			cancelButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					PreferencesDialog.this.dispose() ;
				}
			}) ;
		}
		return cancelButton ;
	}

	/**
	 * This method initializes mainPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMainPanel ()
	{
		if (mainPanel == null)
		{
			mainPanel = new JPanel() ;
			mainPanel.setLayout(new BoxLayout(getMainPanel(), BoxLayout.Y_AXIS)) ;
			mainPanel.add(getInfoPanel(), null) ;
			mainPanel.add(Box.createRigidArea(new Dimension(0, 20))) ;
			mainPanel.add(getWorkPanel(), null) ;
			mainPanel.add(Box.createVerticalGlue()) ;
		}
		return mainPanel ;
	}

	/**
	 * This method initializes infoPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getInfoPanel ()
	{
		if (infoPanel == null)
		{
			infoPanel = new JPanel() ;
			infoPanel.setLayout(new BoxLayout(getInfoPanel(), BoxLayout.Y_AXIS)) ;
			infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("PreferencesDialogMainTitle"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			infoPanel.add(getLangPanel(), null) ;
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			infoPanel.add(getLafPanel(), null) ;
		}
		return infoPanel ;
	}

	/**
	 * This method initializes workPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getWorkPanel ()
	{
		if (workPanel == null)
		{
			workPanel = new JPanel() ;
			workPanel.setLayout(new BoxLayout(getWorkPanel(), BoxLayout.Y_AXIS)) ;
			workPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, Bundle.getText("PreferencesDialogWorkTitle"),
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null)) ;
			workPanel.add(getSavePanel(), null) ;
			workPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			workPanel.add(getExportPanel(), null) ;
			workPanel.add(Box.createRigidArea(new Dimension(0, 20))) ;
			workPanel.add(getProjectPanel(), null) ;
			workPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
			workPanel.add(getMiscPanel(), null) ;
		}
		return workPanel ;
	}

	/**
	 * This method initializes langPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getLangPanel ()
	{
		if (langPanel == null)
		{
			langLabel = new JLabel() ;
			langLabel.setText(Bundle.getText("PreferencesDialogLang")) ;
			langLabel.setPreferredSize(new java.awt.Dimension(170, 16)) ;
			langPanel = new JPanel() ;
			langPanel.setLayout(new BoxLayout(getLangPanel(), BoxLayout.X_AXIS)) ;
			langPanel.add(langLabel, null) ;
			langPanel.add(getLangComboBox(), null) ;
			langPanel.add(Box.createHorizontalGlue()) ;
		}
		return langPanel ;
	}

	/**
	 * This method initializes lafPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getLafPanel ()
	{
		if (lafPanel == null)
		{
			lafLabel = new JLabel() ;
			lafLabel.setText(Bundle.getText("PreferencesDialogLAF")) ;
			lafLabel.setPreferredSize(new java.awt.Dimension(170, 16)) ;
			lafPanel = new JPanel() ;
			lafPanel.setLayout(new BoxLayout(getLafPanel(), BoxLayout.X_AXIS)) ;
			lafPanel.add(lafLabel, null) ;
			lafPanel.add(getLafComboBox(), null) ;
			lafPanel.add(Box.createHorizontalGlue()) ;
		}
		return lafPanel ;
	}

	/**
	 * This method initializes savePanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getSavePanel ()
	{
		if (savePanel == null)
		{
			saveLabel = new JLabel() ;
			saveLabel.setText(Bundle.getText("PreferencesDialogWorkDirectory")) ;
			saveLabel.setPreferredSize(new java.awt.Dimension(170, 16)) ;
			savePanel = new JPanel() ;
			savePanel.setLayout(new BoxLayout(getSavePanel(), BoxLayout.X_AXIS)) ;
			savePanel.add(saveLabel, null) ;
			savePanel.add(getSaveTextField(), null) ;
			savePanel.add(Box.createRigidArea(new Dimension(5, 0))) ;
			savePanel.add(getSaveButton(), null) ;
			savePanel.add(Box.createHorizontalGlue()) ;
		}
		return savePanel ;
	}

	/**
	 * This method initializes exportPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getExportPanel ()
	{
		if (exportPanel == null)
		{
			exportLabel = new JLabel() ;
			exportLabel.setText(Bundle.getText("PreferencesDialogExportDirectory")) ;
			exportLabel.setPreferredSize(new java.awt.Dimension(170, 16)) ;
			exportPanel = new JPanel() ;
			exportPanel.setLayout(new BoxLayout(getExportPanel(), BoxLayout.X_AXIS)) ;
			exportPanel.add(exportLabel, null) ;
			exportPanel.add(getExportTextField(), null) ;
			exportPanel.add(Box.createRigidArea(new Dimension(5, 0))) ;
			exportPanel.add(getExportButton(), null) ;
			exportPanel.add(Box.createHorizontalGlue()) ;
		}
		return exportPanel ;
	}

	/**
	 * This method initializes miscPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMiscPanel ()
	{
		if (miscPanel == null)
		{
			miscLabel = new JLabel() ;
			miscLabel.setText(Bundle.getText("PreferencesDialogHelp")) ;
			miscPanel = new JPanel() ;
			miscPanel.setLayout(new BoxLayout(getMiscPanel(), BoxLayout.X_AXIS)) ;
			miscPanel.add(getMiscCheckBox(), null) ;
			miscPanel.add(miscLabel, null) ;
			miscPanel.add(Box.createHorizontalGlue()) ;
		}
		return miscPanel ;
	}

	/**
	 * This method initializes langComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getLangComboBox ()
	{
		if (langComboBox == null)
		{
			langComboBox = new JComboBox() ;
			langComboBox.setMaximumSize(new Dimension(400, 26)) ;
			langComboBox.setPreferredSize(new Dimension(200, 26)) ;
			langComboBox.addItem(Bundle.getText("PreferencesDialogLangFr")) ;
			langComboBox.addItem(Bundle.getText("PreferencesDialogLangEn")) ;

			// Setting the right index
			if (Bundle.getCurrentLocale().getLanguage().equalsIgnoreCase("fr"))
			{
				langComboBox.setSelectedIndex(0) ;
			}
			
			else
			{
				langComboBox.setSelectedIndex(1) ;
			}
		}
		return langComboBox ;
	}

	/**
	 * This method initializes lafComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getLafComboBox ()
	{
		if (lafComboBox == null)
		{
			lafComboBox = new JComboBox(UIManager.getInstalledLookAndFeels()) ;
			lafComboBox.setMaximumSize(new Dimension(400, 26)) ;
			lafComboBox.setPreferredSize(new Dimension(200, 26)) ;
			lafComboBox.setRenderer(new LafInfoCellRenderer()) ;
			
			// Setting the right item
			int lafNumber = lafComboBox.getItemCount() ;
			LookAndFeelInfo tempLabel = null ;
			for (int i = 0; i < lafNumber; i++ )
			{
				tempLabel = (LookAndFeelInfo) (lafComboBox.getItemAt(i)) ;
				if ( (tempLabel.getName()).equals(UIManager.getLookAndFeel().getName()))
				{
					lafComboBox.setSelectedIndex(i) ;
				}
			}
		}
		return lafComboBox ;
	}

	/**
	 * This method initializes saveTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSaveTextField ()
	{
		if (saveTextField == null)
		{
			saveTextField = new JTextField(20) ;
			saveTextField.setMaximumSize(new Dimension(400, 26)) ;
			saveTextField.setText(preferences.get("work_directory", "")) ;
		}
		return saveTextField ;
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
			saveButton = new JButton(Bundle.getText("PreferencesDialogChoose")) ;
			saveButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					JFileChooser localFileChooser = new JFileChooser() ;
					localFileChooser.setDialogTitle(Bundle.getText("PreferencesDialogChooseTitle")) ;
					localFileChooser.setAcceptAllFileFilterUsed(false) ;
					localFileChooser.setApproveButtonText(Bundle.getText("PreferencesDialogChoose")) ;
					localFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY) ;
					localFileChooser.showOpenDialog(PreferencesDialog.this) ;

					File localFile ;
					if ( (localFile = localFileChooser.getSelectedFile()) != null)
					{
						if (localFile.exists())
						{
							PreferencesDialog.this.getSaveTextField().setText(localFile.getAbsolutePath()) ;
						}
					}
				}
			}) ;
		}
		return saveButton ;
	}

	/**
	 * This method initializes exportTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getExportTextField ()
	{
		if (exportTextField == null)
		{
			exportTextField = new JTextField(20) ;
			exportTextField.setMaximumSize(new Dimension(400, 26)) ;
			exportTextField.setText(preferences.get("export_directory", "")) ;
		}
		return exportTextField ;
	}

	/**
	 * This method initializes exportButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getExportButton ()
	{
		if (exportButton == null)
		{
			exportButton = new JButton(Bundle.getText("PreferencesDialogChoose")) ;
			exportButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					JFileChooser localFileChooser = new JFileChooser() ;
					localFileChooser.setDialogTitle(Bundle.getText("PreferencesDialogChooseTitle")) ;
					localFileChooser.setAcceptAllFileFilterUsed(false) ;
					localFileChooser.setApproveButtonText(Bundle.getText("PreferencesDialogChoose")) ;
					localFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY) ;
					localFileChooser.showOpenDialog(PreferencesDialog.this) ;

					File localFile ;
					if ( (localFile = localFileChooser.getSelectedFile()) != null)
					{
						if (localFile.exists())
						{
							PreferencesDialog.this.getExportTextField().setText(localFile.getAbsolutePath()) ;
						}
					}
				}
			}) ;
		}
		return exportButton ;
	}

	/**
	 * This method initializes miscCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getMiscCheckBox ()
	{
		if (miscCheckBox == null)
		{
			miscCheckBox = new JCheckBox() ;
			miscCheckBox.setSelected(preferences.getBoolean("load_help", false)) ;
		}
		return miscCheckBox ;
	}

	/**
	 * This method initializes projectPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getProjectPanel ()
	{
		if (projectPanel == null)
		{
			projectLabel = new JLabel() ;
			projectLabel.setText(Bundle.getText("PreferencesDialogProject")) ;
			projectPanel = new JPanel() ;
			projectPanel.setLayout(new BoxLayout(getProjectPanel(), BoxLayout.X_AXIS)) ;
			projectPanel.add(getProjectCheckBox(), null) ;
			projectPanel.add(projectLabel, null) ;
			projectPanel.add(Box.createHorizontalGlue()) ;
		}
		return projectPanel ;
	}

	/**
	 * This method initializes projectCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getProjectCheckBox ()
	{
		if (projectCheckBox == null)
		{
			projectCheckBox = new JCheckBox() ;
			projectCheckBox.setSelected(preferences.getBoolean("load_last_project", true)) ;			
		}
		return projectCheckBox ;
	}

	/**
	 * LafInfoCellRenderer : Renderer for LAF names only
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 *
	 */
	class LafInfoCellRenderer extends DefaultListCellRenderer
	{
		private static final long serialVersionUID = 3133524089604752644L ;

		public LafInfoCellRenderer ()
		{

		}

		/* (non-Javadoc)
		 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		public Component getListCellRendererComponent (JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus) ;
			JLabel lafComp = (JLabel) comp ;
			LookAndFeelInfo lafInfo = (LookAndFeelInfo) value ;
			lafComp.setText(lafInfo.getName()) ;
			return lafComp ;
		}
	}

	
} // @jve:decl-index=0:visual-constraint="10,10"
