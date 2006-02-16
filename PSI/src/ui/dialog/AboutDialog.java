package ui.dialog;


import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import java.awt.ScrollPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import ui.resource.Bundle;
public class AboutDialog {

	private JDialog jDialog = null;  //  @jve:decl-index=0:visual-constraint="17,9"
	private JPanel jContentPane = null;
	private JPanel jPanelSouth = null;
	private JButton jButtonOK = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanelTabbed1 = null;
	private JTextArea jTextAreaMembres = null;
	private JTextArea jTextAreaPresentation = null;
	private JPanel jPanelTabbed2 = null;
	private ScrollPane scrollPane = null;
	private JTextArea jTextAreaLGPL = null;
	private JLabel jLabelLogo = null;
	private JTextArea jTextAreaVersion = null;

	public AboutDialog() {
		super();
		// TODO Auto-generated constructor stub
		this.getJDialog();
	}

	/**
	 * This method initializes jDialog	
	 * 	
	 * @return javax.swing.JDialog	
	 */
	private JDialog getJDialog() {
		if (jDialog == null) {
			jDialog = new JDialog();
			jDialog.setSize(new java.awt.Dimension(707,477));
			jDialog.setModal(true);
			jDialog.setResizable(false);
			jDialog.setTitle(Bundle.getText("AboutDialogTitle"));
			jDialog.setContentPane(getJContentPane());
			jDialog.setVisible(true);
			jDialog.pack();
		}
		return jDialog;
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanelSouth(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelSouth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelSouth() {
		if (jPanelSouth == null) {
			jPanelSouth = new JPanel();
			jPanelSouth.add(getJButtonOK(), null);
		}
		return jPanelSouth;
	}

	/**
	 * This method initializes jButtonOK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setName("OK");
			jButtonOK.setText("OK");
			jButtonOK.setMnemonic(java.awt.event.KeyEvent.VK_ENTER);
			jButtonOK.setToolTipText("Fermer la fenêtre");
			jButtonOK.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					jDialog.dispose();
				}
			});
		}
		return jButtonOK;
	}
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			//jTabbedPane.setPreferredSize(new java.awt.Dimension(100,100));
			jTabbedPane.addTab("PSI", null, getJPanelTabbed1(), "A propos de PSI");
			jTabbedPane.addTab("License", null, getJPanelTabbed2(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanelTabbed1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelTabbed1() {
		if (jPanelTabbed1 == null) {
			jLabelLogo = new JLabel();
			jLabelLogo.setIcon(new ImageIcon(getClass().getResource("/ui/resource/psilogo150_150.gif")));
			jLabelLogo.setPreferredSize(new java.awt.Dimension(150,100));
			jPanelTabbed1 = new JPanel();
			jPanelTabbed1.setLayout(new BorderLayout());
			jPanelTabbed1.add(getJTextAreaMembres(), java.awt.BorderLayout.EAST);
			jPanelTabbed1.add(getJTextAreaPresentation(), java.awt.BorderLayout.SOUTH);
			jPanelTabbed1.add(jLabelLogo, java.awt.BorderLayout.WEST);
			jPanelTabbed1.add(getJTextAreaVersion(), java.awt.BorderLayout.NORTH);
		}
		return jPanelTabbed1;
	}

	/**
	 * This method initializes jTextAreaMembres	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaMembres() {
		if (jTextAreaMembres == null) {
			jTextAreaMembres = new JTextArea();
			jTextAreaMembres.setText(Bundle.getText("AboutDialogStaff"));
			jTextAreaMembres.setBackground(new java.awt.Color(238,238,238));
		}
		return jTextAreaMembres;
	}
	

	/**
	 * This method initializes jTextAreaPresentation	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaPresentation() {
		if (jTextAreaPresentation == null) {
			jTextAreaPresentation = new JTextArea();
			jTextAreaPresentation.setBackground(new java.awt.Color(238,238,238));
			jTextAreaPresentation.setText(Bundle.getText("AboutDialogPresentation"));
		}
		return jTextAreaPresentation;
	}

	/**
	 * This method initializes jPanelTabbed2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelTabbed2() {
		if (jPanelTabbed2 == null) {
			jPanelTabbed2 = new JPanel();
			jPanelTabbed2.setToolTipText("LGPL");
			jPanelTabbed2.add(getScrollPane(), null);
		}
		return jPanelTabbed2;
	}

	/**
	 * This method initializes scrollPane	
	 * 	
	 * @return java.awt.ScrollPane	
	 */
	private ScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new ScrollPane();
			scrollPane.setMinimumSize(new java.awt.Dimension(100,100));
			scrollPane.setPreferredSize(new java.awt.Dimension(650,355));
			scrollPane.setMaximumSize(new java.awt.Dimension(32767,32767));
			scrollPane.add(getJTextAreaLGPL(), null);
		}
		return scrollPane;
	}

	/**
	 * This method initializes jTextAreaLGPL	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaLGPL() {
		if (jTextAreaLGPL == null) {
			jTextAreaLGPL = new JTextArea();
			jTextAreaLGPL.setEditable(false);
			jTextAreaLGPL.setSize(new java.awt.Dimension(640,330));
			jTextAreaLGPL.setBackground(java.awt.SystemColor.inactiveCaptionText);
			jTextAreaLGPL.setWrapStyleWord(true);
			jTextAreaLGPL.setText(Bundle.getText("AboutDialogLGPL"));
		}
		return jTextAreaLGPL;
	}
	
	/**
	 * This method initializes jTextAreaVersion	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaVersion ()
	{
		if (jTextAreaVersion == null)
		{
			jTextAreaVersion = new JTextArea() ;
			jTextAreaVersion.setBackground(new java.awt.Color(238,238,238));
			jTextAreaVersion.setFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 14));
			jTextAreaVersion.setPreferredSize(new java.awt.Dimension(154,45));
			jTextAreaVersion.setText(Bundle.getText("AboutDialogVersion"));
		}
		return jTextAreaVersion ;
	}
}
