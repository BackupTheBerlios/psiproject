
package ui.window ;

import javax.swing.JPanel ;

import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.spem2.Activity;
import ui.window.ModelTableActivity; 
import ui.resource.Bundle;

/**
 * JPanelActivity : TODO type description
 *
 * @author kass
 * @version 1.0
 *
 */
public class JPanelActivity extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L ;
	private String[] nomsColonnes = {Bundle.getText("JTableActivityTheme"),
            Bundle.getText("JTableActivityCol1"), Bundle.getText("JTableActivityCol2"),Bundle.getText("JTableActivityCol3"),Bundle.getText("JTableActivityCol4"),Bundle.getText("JTableActivityCol5"),Bundle.getText("JTableActivityCol6")} ;
	private Object[][] donnees = null ;
	private javax.swing.JTable table;
	private javax.swing.JScrollPane tableScrollPane;
	/**
	 * This is the default constructor
	 */
	public JPanelActivity (Activity _activity)
	{
		super() ;
		
		donnees = new Object[2][7];
		
		donnees[0][0] = Bundle.getText("JTableActivityLine1");
		donnees[1][0] = Bundle.getText("JTableActivityLine2");
		donnees[0][1] = _activity.getId();
		donnees[1][1] = _activity.getId();
		donnees[0][2] = _activity.getName();
		donnees[1][2] = _activity.getName();
		donnees[0][3] = _activity.getDescription();
		donnees[1][3] = _activity.getDescription();
		donnees[0][4] = (_activity.getPlanningData()).getStartDate();
		donnees[1][4] = (_activity.getRealData()).getStartDate();
		donnees[0][5] = (_activity.getPlanningData()).getFinishDate();
		donnees[1][5] = (_activity.getRealData()).getFinishDate();
		donnees[0][6] = (_activity.getPlanningData()).getDuration();
		donnees[1][6] = (_activity.getRealData()).getDuration();
		
		ModelTableActivity tableModel = new ModelTableActivity(donnees, nomsColonnes);
		
		initialize() ;
		
		table = new JTable(tableModel) ;
		table.getTableHeader().setReorderingAllowed(false) ;
		table.setPreferredScrollableViewportSize(new Dimension(50, 10)) ;
		tableScrollPane = new JScrollPane(table) ;
	        add(tableScrollPane, java.awt.BorderLayout.CENTER);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{
		this.setSize(300, 200) ;
		setLayout(new java.awt.BorderLayout());
	}

}
