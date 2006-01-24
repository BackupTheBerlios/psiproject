
package ui.dialog ;

import java.awt.BorderLayout ;
import java.awt.Component ;
import java.awt.Dimension ;
import java.awt.GridLayout ;
import java.io.File ;
import java.util.Locale ;

import javax.swing.BoxLayout ;
import javax.swing.DefaultListCellRenderer ;
import javax.swing.JButton ;
import javax.swing.JCheckBox ;
import javax.swing.JComboBox ;
import javax.swing.JDialog ;
import javax.swing.JFileChooser ;
import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JList ;
import javax.swing.JPanel ;
import javax.swing.JTabbedPane ;
import javax.swing.JTextField ;
import javax.swing.SwingUtilities ;
import javax.swing.UIManager ;
import javax.swing.UIManager.LookAndFeelInfo ;
import process.Preferences ;
import ui.resource.Bundle ;
import ui.resource.LocaleController ;

/**
 * PreferenceDialog : TODO type description
 * 
 * @author m1isi1
 * @version 1.0
 * 
 */
public class PreferenceDialog extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2419928875825666824L ;

	private JDialog jDialog = null ;

	private JTabbedPane jTabbedPaneOnglets = null ;

	private Preferences pref ;

	// General Panel
	private JPanel jPanelGeneral = null ;

	private JPanel jPanelGeneralChoixLangue = null ;

	private JPanel jPanelGeneralDefautLangage = null ;

	private JPanel jPanelGeneralLook = null ;

	private JLabel jLabelChoixLangue = null ;

	private JLabel jLabelDefautLangage = null ;

	private JLabel jLabelLook = null ;

	private JComboBox jComboBoxChoixLangue = null ;

	private JCheckBox jCheckBoxDefautLangage = null ;

	private JComboBox jComboBoxLook = null ;

	// Travail Panel
	private JPanel jPanelTravail = null ;

	private JPanel jPanelTravailRepertoireTravail = null ;

	private JPanel jPanelTravailRepertoireExport = null ;

	private JPanel jPanelTravailDernierProjet = null ;

	private JPanel jPanelTravailAide = null ;

	private JLabel jLabelRepertoireTravail = null ;

	private JLabel jLabelRepertoireExport = null ;

	private JLabel jLabelDernierProjet = null ;

	private JLabel jLabelAide = null ;

	private JTextField jTextFieldRepertoireTravail = null ;

	private JButton jButtonRepertoireTravailChoisir = null ;

	private JTextField jTextFieldRepertoireExport = null ;

	private JButton jButtonRepertoireExportChoisir = null ;

	private JCheckBox jCheckBoxDernierProjet = null ;

	private JCheckBox jCheckBoxAide = null ;

	// Buttons OK And Cancel/Annuler
	private JPanel jPanelValider = null ;

	private JButton jButtonPreferenceOK = null ;

	private JButton jButtonPreferenceCancel = null ;
	
	private LocaleController ControllerLocale = null;

	@ SuppressWarnings ("deprecation")
	public PreferenceDialog (JFrame parent)
	{
		super(parent) ;
		pref = Preferences.getInstance() ;
		ControllerLocale = LocaleController.getInstance();
		
		this.setLayout(new BorderLayout()) ;
		this.setSize(500, 300) ;
		this.add(getJTabbedPaneOnglets(), BorderLayout.CENTER) ;
		this.add(getJPanelValider(), BorderLayout.SOUTH) ;
		this.setTitle(Bundle.getText("PreferenceDialogTitle"));
		this.setResizable(false);
		
		show() ;
		pack() ;
		jDialog = this ;
	}

	public JTabbedPane getJTabbedPaneOnglets ()
	{
		if (jTabbedPaneOnglets == null)
		{
			jTabbedPaneOnglets = new JTabbedPane() ;
			jTabbedPaneOnglets.addTab(Bundle.getText("JPanelPreferenceGeneralLabel"), getJPanelGeneral()) ;
			jTabbedPaneOnglets.addTab(Bundle.getText("JPanelPreferenceTravailLabel"), getJPanelTravail()) ;
		}

		jTabbedPaneOnglets.setVisible(true) ;
		return jTabbedPaneOnglets ;
	}

	// General methods
	public JPanel getJPanelGeneral ()
	{
		if (jPanelGeneral == null)
		{
			jPanelGeneral = new JPanel() ;
			jPanelGeneral.setLayout(new GridLayout(3, 1)) ;

			jPanelGeneral.add(getJPanelGeneralChoixLangue()) ;
			jPanelGeneral.add(getJPanelGeneralDefautLangage()) ;
			jPanelGeneral.add(getJPanelGeneralLook()) ;
		}
		jPanelGeneral.setVisible(true) ;
		return jPanelGeneral ;
	}

	public JPanel getJPanelGeneralChoixLangue ()
	{
		if (jPanelGeneralChoixLangue == null)
		{
			jPanelGeneralChoixLangue = new JPanel() ;
			jLabelChoixLangue = new JLabel() ;
			jLabelChoixLangue.setText(Bundle.getText("JPanelPreferenceGeneralLanguageLabel")) ;
			jPanelGeneralChoixLangue.add(jLabelChoixLangue) ;

			jComboBoxChoixLangue = new JComboBox() ;
			jComboBoxChoixLangue.addItem(Bundle.getText("JPanelPreferenceGeneralLanguageEnglish")) ;
			jComboBoxChoixLangue.addItem(Bundle.getText("JPanelPreferenceGeneralLanguageFrench")) ;

			if (!(pref.getLocale().equals("EN")))
			{
				jComboBoxChoixLangue.setSelectedItem(Bundle.getText("JPanelPreferenceGeneralLanguageFrench")) ;
				
			}
			
			jPanelGeneralChoixLangue.add(jComboBoxChoixLangue) ;
		}

		JLabel fix = new JLabel();
		jPanelGeneralChoixLangue.add(fix);
		fix.setPreferredSize(new Dimension(145, 20)) ;
		
		jPanelGeneralChoixLangue.setVisible(true) ;
		return jPanelGeneralChoixLangue ;
	}

	public JPanel getJPanelGeneralDefautLangage ()
	{
		if (jPanelGeneralDefautLangage == null)
		{
			jPanelGeneralDefautLangage = new JPanel() ;
			jLabelDefautLangage = new JLabel() ;
			jLabelDefautLangage.setText(Bundle.getText("JPanelPreferenceGeneralDefaultLanguageLabel")) ;
			jCheckBoxDefautLangage = new JCheckBox() ;

			jPanelGeneralDefautLangage.add(jCheckBoxDefautLangage) ;
			jPanelGeneralDefautLangage.add(jLabelDefautLangage) ;

		}
		
		JLabel fix = new JLabel();
		jPanelGeneralDefautLangage.add(fix);
		fix.setPreferredSize(new Dimension(145, 20)) ;
		
		jPanelGeneralDefautLangage.setVisible(true) ;
		return jPanelGeneralDefautLangage ;
	}

	public JPanel getJPanelGeneralLook ()
	{
		if (jPanelGeneralLook == null)
		{
			jPanelGeneralLook = new JPanel() ;
			jLabelLook = new JLabel() ;
			jLabelLook.setText(Bundle.getText("JPanelPreferenceGeneralLookAndFeelLabel")) ;
			jPanelGeneralLook.add(jLabelLook) ;

			jComboBoxLook = new JComboBox(UIManager.getInstalledLookAndFeels()) ;
			jComboBoxLook.setRenderer(new lafInfoCellRenderer()) ;
			jPanelGeneralLook.add(jComboBoxLook) ;

		}
		
		JLabel fix = new JLabel();
		jPanelGeneralLook.add(fix);
		fix.setPreferredSize(new Dimension(130, 20)) ;
		
		jPanelGeneralLook.setVisible(true) ;
		return jPanelGeneralLook ;
	}

	// End General methods

	// Travail methods
	public JPanel getJPanelTravail ()
	{

		if (jPanelTravail == null)
		{
			jPanelTravail = new JPanel() ;
			jPanelTravail.setLayout(new BoxLayout(jPanelTravail, BoxLayout.Y_AXIS)) ;
			jPanelTravail.add(getJPanelTravailRepertoireTravail()) ;
			jPanelTravail.add(getJPanelTravailRepertoireExport()) ;
			jPanelTravail.add(getJPanelTravailDernierProjet()) ;
			jPanelTravail.add(getJPanelTravailAide()) ;

		}
		jPanelTravail.setVisible(true) ;
		return jPanelTravail ;
	}

	public JPanel getJPanelTravailRepertoireTravail ()
	{
		if (jPanelTravailRepertoireTravail == null)
		{
			jPanelTravailRepertoireTravail = new JPanel() ;
			jLabelRepertoireTravail = new JLabel() ;
			jLabelRepertoireTravail.setText(Bundle.getText("JPanelPreferenceTravailWorkRepertoryLabel")) ;
			jPanelTravailRepertoireTravail.add(jLabelRepertoireTravail) ;

			jTextFieldRepertoireTravail = new JTextField() ;
			jTextFieldRepertoireTravail.setPreferredSize(new Dimension(100, 20)) ;
			jPanelTravailRepertoireTravail.add(jTextFieldRepertoireTravail) ;

			jButtonRepertoireTravailChoisir = new JButton() ;
			jButtonRepertoireTravailChoisir.setText(Bundle.getText("JPanelPreferenceTravailParcourirButton")) ;
			jButtonRepertoireTravailChoisir.setPreferredSize(new Dimension(100, 20)) ;
			jPanelTravailRepertoireTravail.add(jButtonRepertoireTravailChoisir) ;

			jButtonRepertoireTravailChoisir.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					JFileChooser jf = new JFileChooser(new File(pref.getWorkDirectory())) ;
					jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY) ;
					jf.setApproveButtonText(Bundle.getText("JFileChooserOK")) ;

					if (jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						jTextFieldRepertoireTravail.setText(jf.getSelectedFile().getAbsolutePath()) ;
					}
				}
			}) ;
		}
		jPanelTravailRepertoireTravail.setVisible(true) ;
		return jPanelTravailRepertoireTravail ;
	}

	public JPanel getJPanelTravailRepertoireExport ()
	{
		if (jPanelTravailRepertoireExport == null)
		{
			jPanelTravailRepertoireExport = new JPanel() ;
			jLabelRepertoireExport = new JLabel() ;
			jLabelRepertoireExport.setText(Bundle.getText("JPanelPreferenceTravailExportRepertoryLabel")) ;
			jPanelTravailRepertoireExport.add(jLabelRepertoireExport) ;

			jTextFieldRepertoireExport = new JTextField() ;
			jTextFieldRepertoireExport.setPreferredSize(new Dimension(100, 20)) ;
			jPanelTravailRepertoireExport.add(jTextFieldRepertoireExport) ;

			jButtonRepertoireExportChoisir = new JButton() ;
			jButtonRepertoireExportChoisir.setText(Bundle.getText("JPanelPreferenceTravailParcourirButton")) ;
			jButtonRepertoireExportChoisir.setPreferredSize(new Dimension(100, 20)) ;
			jPanelTravailRepertoireExport.add(jButtonRepertoireExportChoisir) ;

			jButtonRepertoireExportChoisir.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					JFileChooser jf = new JFileChooser(new File(pref.getExportDirectory())) ;
					jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY) ;
					jf.setApproveButtonText(Bundle.getText("JFileChooserOK")) ;

					if (jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						jTextFieldRepertoireExport.setText(jf.getSelectedFile().getAbsolutePath()) ;
					}
				}
			}) ;
		}
		jPanelTravailRepertoireExport.setVisible(true) ;
		return jPanelTravailRepertoireExport ;
	}

	public JPanel getJPanelTravailDernierProjet ()
	{
		if (jPanelTravailDernierProjet == null)
		{
			jPanelTravailDernierProjet = new JPanel() ;
			jLabelDernierProjet = new JLabel() ;
			jLabelDernierProjet.setText(Bundle.getText("JPanelPreferenceTravailLastProjectLabel")) ;
			jCheckBoxDernierProjet = new JCheckBox() ;

			jPanelTravailDernierProjet.add(jCheckBoxDernierProjet) ;
			jPanelTravailDernierProjet.add(jLabelDernierProjet) ;

		}
		jPanelTravailDernierProjet.setVisible(true) ;
		return jPanelTravailDernierProjet ;
	}

	public JPanel getJPanelTravailAide ()
	{
		if (jPanelTravailAide == null)
		{
			jPanelTravailAide = new JPanel() ;
			jLabelAide = new JLabel() ;
			JLabel fix = new JLabel() ;
			jLabelAide.setText(Bundle.getText("JPanelPreferenceTravailHelpLabel")) ;
			jCheckBoxAide = new JCheckBox() ;

			jPanelTravailAide.add(jCheckBoxAide) ;
			jPanelTravailAide.add(jLabelAide) ;
			jPanelTravailAide.add(fix) ;
			fix.setPreferredSize(new Dimension(145, 20)) ;

		}
		jPanelTravailAide.setVisible(true) ;
		return jPanelTravailAide ;
	}

	// End Travail methods

	public JPanel getJPanelValider ()
	{
		if (jPanelValider == null)
		{
			jPanelValider = new JPanel() ;
			jButtonPreferenceOK = new JButton() ;
			jButtonPreferenceCancel = new JButton() ;
			jButtonPreferenceOK.setText(Bundle.getText("JPanelValiderOKLabel")) ;
			jButtonPreferenceCancel.setText(Bundle.getText("JPanelValiderCancelLabel")) ;
			jPanelValider.add(jButtonPreferenceOK) ;
			jPanelValider.add(jButtonPreferenceCancel) ;

			jButtonPreferenceOK.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{

					// TRAITEMENT DU LOOK AND FELL
					LookAndFeelInfo tempLAFInfo = (LookAndFeelInfo) (jComboBoxLook.getSelectedItem()) ;
					try
					{

						UIManager.setLookAndFeel(tempLAFInfo.getClassName()) ;
						SwingUtilities.updateComponentTreeUI(jDialog) ;
						SwingUtilities.updateComponentTreeUI(jDialog.getParent()) ;
					}
					catch (Exception exc)
					{
						exc.printStackTrace() ;
					}

					// TRAITEMENT DE LA LANGUE
					// On regarde la langue choisie et on change la Locale
					if (jComboBoxChoixLangue.getSelectedItem().toString().compareTo(Bundle.getText("JPanelPreferenceGeneralLanguageFrench")) == 0)
					{

						Bundle.setCurrentLocale(Locale.FRENCH) ;
						ControllerLocale.fireLocaleChanged() ;
						if (jCheckBoxDefautLangage.isSelected())
						{
							pref.setLocale("FR") ;
						}

					}
					else if (jComboBoxChoixLangue.getSelectedItem().toString().compareTo(Bundle.getText("JPanelPreferenceGeneralLanguageEnglish")) == 0)
					{

						Bundle.setCurrentLocale(Locale.ENGLISH) ;
						ControllerLocale.fireLocaleChanged() ;
						if (jCheckBoxDefautLangage.isSelected())
						{
							pref.setLocale("EN") ;
						}

					}

					// TRAITEMENT DES REPERTOIRES
					if (! (jTextFieldRepertoireTravail.getText()).equals("") && (jTextFieldRepertoireTravail.getText() != null))
					{
						pref.setWorkDirectory(jTextFieldRepertoireTravail.getText()) ;
					}

					if (! (jTextFieldRepertoireExport.getText()).equals("") && (jTextFieldRepertoireExport.getText() != null))
					{
						pref.setExportDirectory(jTextFieldRepertoireExport.getText()) ;
					}

					// TRAITEMENT DU DERNIER PROJET
					if (jCheckBoxDernierProjet.isSelected())
					{
						pref.setLoadLastProject(true);
					}

					// TRAITEMENT DE L'AIDE AU DEMARRAGE
					if (jCheckBoxAide.isSelected())
					{
						pref.setLoadHelp(true);
					}
					pref.save();
					dispose() ;
					
				}
			}) ;

			jButtonPreferenceCancel.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					dispose() ;
				}
			}) ;

		}
		jPanelValider.setVisible(true) ;
		return jPanelValider ;
	}

}


class lafInfoCellRenderer extends DefaultListCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L ;

	public lafInfoCellRenderer ()
	{

	}

	public Component getListCellRendererComponent (JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus) ;
		JLabel lafComp = (JLabel) comp ;
		LookAndFeelInfo lafInfo = (LookAndFeelInfo) value ;
		lafComp.setText(lafInfo.getName()) ;
		return lafComp ;
	}
}
