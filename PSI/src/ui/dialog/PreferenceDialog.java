package ui.dialog;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.resource.Bundle;

/**
 * PreferenceDialog : TODO type description
 *
 * @author m1isi1
 * @version 1.0
 *
 */
public class PreferenceDialog
{
	private JDialog jDialog = null;
	private JTabbedPane jTabbedPaneOnglets = null;
	
	
	// General Panel
	private JPanel jPanelGeneral = null;
	private JPanel jPanelGeneralChoixLangue = null;
	private JPanel jPanelGeneralDefautLangage = null;
	private JPanel jPanelGeneralLook = null;
	private JLabel jLabelChoixLangue = null;
	private JLabel jLabelDefautLangage = null;
	private JLabel jLabelLook = null;
	private JComboBox jComboBoxChoixLangue = null;
	private JCheckBox jCheckBoxDefautLangage = null;
	private JComboBox jComboBoxLook = null;
	
	// Travail Panel
	private JPanel jPanelTravail = null;
	private JPanel jPanelTravailRepertoireTravail = null;
	private JPanel jPanelTravailRepertoireExport = null;
	private JPanel jPanelTravailDernierProjet = null;
	private JPanel jPanelTravailAide = null;
	private JLabel jLabelRepertoireTravail = null;
	private JLabel jLabelRepertoireExport = null;
	private JLabel jLabelDernierProjet = null;
	private JLabel jLabelAide = null;
	private JTextField jTextFieldRepertoireTravail = null;
	private JButton jButtonRepertoireTravailChoisir = null;
	private JTextField jTextFieldRepertoireExport = null;
	private JButton jButtonRepertoireExportChoisir = null;
	private JCheckBox jCheckBoxDernierProjet = null;
	private JCheckBox jCheckBoxAide = null;
	
	// Buttons OK And Cancel/Annuler
	private JPanel jPanelValider = null;
	private JButton jButtonPreferenceOK = null;
	private JButton jButtonPreferenceCancel = null;
	
	
	public JDialog getJDialog()
	{
		if (jDialog == null)
		{
			jDialog = new JDialog();
			jDialog.add(getJTabbedPaneOnglets);
			jDialog.add(getJPanelValider(),BorderLayout.SOUTH);
									
		}
		
		return jDialog;
	}
	
	public JTabbedPane getJTabbedPaneOnglets()
	{
		if (jTabbedPaneOnglets == null)
		{
			jTabbedPaneOnglets = new JTabbedPane();
			jTabbedPaneOnglets.addTab(Bundle.getText("JPanelPreferenceGeneralLabel"),getJPanelGeneral());
			jTabbedPaneOnglets.addTab(Bundle.getText("JPanelPreferenceTravailLabel"),getJPanelTravail());
		}
		
		return jTabbedPaneOnglets;
	}
	
	// General methods
	public JPanel getJPanelGeneral()
	{
		if (jPanelGeneral == null)
		{
			jPanelGeneral = new JPanel();
			jPanelGeneral.setLayout(new GridLayout(3,1));
			
			jPanelGeneral.add(getJPanelGeneralChoixLangue());
			jPanelGeneral.add(getJPanelGeneralDefautLangage());
			jPanelGeneral.add(getJPanelGeneralLook());
		}
		
		return jPanelGeneral;
	}
	
	public JPanel getJPanelGeneralChoixLangue()
	{
		if (jPanelGeneralChoixLangue == null)
		{
			jPanelGeneralChoixLangue = new JPanel();
			jLabelChoixLangue = new JLabel();
			jLabelChoixLangue.setText(Bundle.getText("JPanelPreferenceGeneralLanguageLabel"));
			jPanelGeneralChoixLangue.add(jLabelChoixLangue);
			
			jComboBoxChoixLangue = new JComboBox();
			jComboBoxChoixLangue.addItem(Bundle.getText("JPanelPreferenceGeneralLanguageEnglish"));
			jComboBoxChoixLangue.addItem(Bundle.getText("JPanelPreferenceGeneralLanguageFrench"));
			jPanelGeneralChoixLangue.add(jComboBoxChoixLangue);
		}
		
		return jPanelGeneralChoixLangue;
	}
	
	public JPanel getJPanelGeneralDefautLangage()
	{
		if (jPanelGeneralDefautLangage == null)
		{
			jPanelGeneralDefautLangage = new JPanel();	
			jLabelDefautLangage = new JLabel();
			jLabelDefautLangage.setText(Bundle.getText("JPanelPreferenceGeneralDefaultLanguageLabel"));
			jCheckBoxDefautLangage = new JCheckBox();
			
			jPanelGeneralDefautLangage.add(jCheckBoxDefautLangage);
			jPanelGeneralDefautLangage.add(jLabelDefautLangage);
					
		}
		
		return jPanelGeneralDefautLangage;
	}
	
	public JPanel getJPanelGeneralLook()
	{
		if (jPanelGeneralLook == null)
		{
			jPanelGeneralLook = new JPanel();	
			jLabelLook = new JLabel();
			jLabelLook.setText(Bundle.getText("JPanelPreferenceGeneralLookAndFeelLabel"));
			jPanelGeneralLook.add(jLabelLook);
			
			jComboBoxLook = new JComboBox();
			jPanelGeneralLook.add(jComboBoxLook);
			
		}
		
		return jPanelGeneralLook;
	}
	// End General methods
	
	// Travail methods
	public JPanel getJPanelTravail()
	{
		if (jPanelTravail == null)
		{
			jPanelTravail = new JPanel();
			jPanelTravail.setLayout(new GridLayout(4,1));
			
			jPanelTravail.add(getJPanelTravailRepertoireTravail());
			jPanelTravail.add(getJPanelTravailRepertoireExport());
			jPanelTravail.add(getJPanelTravailDernierProjet());
			jPanelTravail.add(getJPanelTravailAide());
		}
		
		return jPanelTravail;
	}
	
	public JPanel getJPanelTravailRepertoireTravail()
	{
		if (jPanelTravailRepertoireTravail == null)
		{
			jPanelTravailRepertoireTravail = new JPanel();
			jLabelRepertoireTravail = new JLabel();
			jLabelRepertoireTravail.setText(Bundle.getText("JPanelPreferenceTravailWorkRepertoryLabel"));
			jPanelTravailRepertoireTravail.add(jLabelRepertoireTravail);
			
			jTextFieldRepertoireTravail = new JTextField();
			jPanelTravailRepertoireTravail.add(jTextFieldRepertoireTravail);
			
			jButtonRepertoireTravailChoisir = new JButton();
			jButtonRepertoireTravailChoisir.setText(Bundle.getText("JPanelPreferenceTravailParcourirButton"));
			jPanelTravailRepertoireTravail.add(jButtonRepertoireTravailChoisir);
		}
		
		return jPanelTravailRepertoireTravail;
	}
	
	public JPanel getJPanelTravailRepertoireExport()
	{
		if (jPanelTravailRepertoireExport == null)
		{
			jPanelTravailRepertoireExport = new JPanel();
			jLabelRepertoireExport = new JLabel();
			jLabelRepertoireExport.setText(Bundle.getText("JPanelPreferenceTravailExportRepertoryLabel"));
			jPanelTravailRepertoireExport.add(jLabelRepertoireExport);
			
			jTextFieldRepertoireExport = new JTextField();
			jPanelTravailRepertoireExport.add(jTextFieldRepertoireExport);
			
			jButtonRepertoireExportChoisir = new JButton();
			jButtonRepertoireExportChoisir.setText(Bundle.getText("JPanelPreferenceTravailParcourirButton"));
			jPanelTravailRepertoireExport.add(jButtonRepertoireExportChoisir);
		}
		
		return jPanelTravailRepertoireExport;
	}

	public JPanel getJPanelTravailDernierProjet()
	{
		if (jPanelTravailDernierProjet == null)
		{
			jPanelTravailDernierProjet = new JPanel();
			jLabelDernierProjet = new JLabel();
			jLabelDernierProjet.setText(Bundle.getText("JPanelPreferenceTravailLastProjectLabel"));
			jCheckBoxDernierProjet = new JCheckBox();
			
			jPanelTravailDernierProjet.add(jCheckBoxDernierProjet);
			jPanelTravailDernierProjet.add(jLabelDernierProjet);
						
		}
		
		return jPanelTravailRepertoireExport;
	}
	
	public JPanel getJPanelTravailAide()
	{
		if (jPanelTravailAide== null)
		{
			jPanelTravailAide = new JPanel();
			jLabelAide = new JLabel();
			jLabelAide.setText(Bundle.getText("JPanelPreferenceTravailHelpLabel"));
			jCheckBoxAide = new JCheckBox();
			
			jPanelTravailAide.add(jCheckBoxAide);
			jPanelTravailAide.add(jLabelAide);
						
		}
		
		return jPanelTravailRepertoireExport;
	}
	
	//	End Travail methods
	
	
	public JPanel getJPanelValider()
	{
		if (jPanelValider == null)
		{
			jPanelValider = new JPanel();
			jButtonPreferenceOK = new JButton();
			jButtonPreferenceCancel = new JButton();
			jButtonPreferenceOK.setText(Bundle.getText("JPanelValiderOKLabel"));
			jButtonPreferenceCancel.setText(Bundle.getText("JPanelValiderCancelLabel"));
			jPanelValider.add(jButtonPreferenceOK);
			jPanelValider.add(jButtonPreferenceCancel);
		}
		
		return jPanelValider;
	}	
	
}
