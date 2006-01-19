
package ui.misc ;

import javax.swing.JPanel ;

import java.awt.Component ;
import java.awt.Dimension ;
import java.awt.GridBagConstraints ;
import java.awt.GridBagLayout ;

import javax.swing.Box ;
import javax.swing.JScrollPane ;
import javax.swing.JTable ;
import javax.swing.table.AbstractTableModel ;
import ui.resource.Bundle ;
import model.spem2.PlanningData ;
import model.spem2.TaskDescriptor ;
import java.util.Locale ;
import java.text.DateFormat ;
import javax.swing.event.* ;
import javax.swing.JLabel ;

/**
 * JPanelTaskDescriptor : TODO type description
 * 
 * @author kass
 * @version 1.0
 * 
 */
public class TaskDescriptorPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L ;

	private String[] nomsColonnes = {
			Bundle.getText("JPanelTaskDescriptor"), Bundle.getText("JPanelTaskDescriptorCol1"), Bundle.getText("JPanelTaskDescriptorCol2"),
			Bundle.getText("JPanelTaskDescriptorCol3")
	} ;

	private Object[][] donnees = null ;

	private javax.swing.JTable table ;

	private javax.swing.JScrollPane tableScrollPane ;

	private Locale locale = Locale.getDefault() ;

	private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale) ;

	private JLabel idLabel = null ;

	private JLabel idLabelValue = null ;

	private JLabel nameLabel = null ;

	private JLabel nameLabelValue = null ;

	private JLabel descLabel = null ;

	private JLabel descLabelValue = null ;

	private JLabel decalLabel = null ;

	private JPanel jPanel = null ;

	private JPanel jPanelHaut = null ;

	private TaskDescriptor task = null ;

	/**
	 * This is the default constructor
	 */
	public TaskDescriptorPanel (TaskDescriptor _task)
	{
		super() ;
		donnees = new Object[2][4] ;
		task = _task ;
		donnees[0][0] = Bundle.getText("JPanelTaskDescriptorRow1") ;
		donnees[1][0] = Bundle.getText("JPanelTaskDescriptorRow2") ;
		donnees[0][1] = dateFormat.format( (task.getPlanningData()).getStartDate()) ;
		donnees[1][1] = dateFormat.format( (task.getRealData()).getStartDate()) ;
		donnees[0][2] = dateFormat.format( (task.getPlanningData()).getFinishDate()) ;
		donnees[1][2] = dateFormat.format( (task.getRealData()).getFinishDate()) ;
		donnees[0][3] = (task.getPlanningData()).getDuration() ;
		donnees[1][3] = (task.getRealData()).getDuration() ;

		ModelTableTaskDescriptor tableModel = new ModelTableTaskDescriptor(donnees, nomsColonnes, _task) ;
		initialize() ;
		table = new JTable(tableModel) ;
		table.getTableHeader().setReorderingAllowed(false) ;
		table.setPreferredScrollableViewportSize(new Dimension(50, 10)) ;
		tableScrollPane = new JScrollPane(table) ;
		add(tableScrollPane, java.awt.BorderLayout.CENTER) ;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{

		this.setSize(300, 200) ;
		setLayout(new java.awt.BorderLayout()) ;
		this.add(getJPanel(), java.awt.BorderLayout.NORTH) ;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel ()
	{
		GridBagConstraints c = new GridBagConstraints() ;

		idLabel = new JLabel() ;
		idLabelValue = new JLabel() ;
		nameLabel = new JLabel() ;
		nameLabelValue = new JLabel() ;
		descLabel = new JLabel() ;
		descLabelValue = new JLabel() ;
		idLabel.setText(Bundle.getText("JPanelTaskDescriptorIDLabel")) ;
		nameLabel.setText(Bundle.getText("JPanelTaskDescriptorNameLabel")) ;
		descLabel.setText(Bundle.getText("JPanelTaskDescriptorDescriptionLabel")) ;
		if (task.getId() != null) idLabelValue.setText(task.getId()) ;
		if (task.getName() != null) nameLabelValue.setText(task.getName()) ;
		if (task.getDescription() != null) descLabelValue.setText(task.getDescription()) ;
		if (jPanel == null)
		{
			jPanel = new JPanel() ;
			jPanel.setLayout(new GridBagLayout()) ;
			c.gridx = 0 ;
			c.gridy = 0 ;
			c.gridwidth = 1 ;
			c.gridheight = 1 ;
			c.fill = GridBagConstraints.WEST ;
			c.anchor = GridBagConstraints.WEST ;
			jPanel.add(idLabel, c) ;
			c.gridx = 1 ;
			jPanel.add(idLabelValue, c) ;
			c.gridx = 0 ;
			c.gridy = 1 ;

			jPanel.add(nameLabel, c) ;
			c.gridx = 1 ;
			jPanel.add(nameLabelValue, c) ;
			c.gridx = 0 ;
			c.gridy = 2 ;
			jPanel.add(descLabel, c) ;
			c.gridx = 1 ;
			jPanel.add(descLabelValue, c) ;
			jPanel.setVisible(true) ;
		}
		return jPanel ;
	}

}


/**
 * ModelTableTaskDescriptor : TODO type description
 * 
 * @author kass
 * @version 1.0
 * 
 */

class ModelTableTaskDescriptor extends AbstractTableModel
{
	Object donnees[][] ;

	String titres[] ;

	TaskDescriptor task = null ;

	PlanningData p = new PlanningData() ;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public ModelTableTaskDescriptor (Object donnees[][], String titres[], TaskDescriptor _task)
	{
		this.donnees = donnees ;
		this.titres = titres ;
		task = _task ;
	}

	public int getRowCount ()
	{
		// TODO Auto-generated method stub
		return donnees.length ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount ()
	{
		// TODO Auto-generated method stub
		if (getRowCount() != 0)
			return donnees[0].length ;
		else
			return 0 ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt (int ligne, int colonne)
	{
		// TODO Auto-generated method stub
		return donnees[ligne][colonne] ;
	}

	public void setValueAt (Object value, int ligne, int colonne)
	{
		donnees[ligne][colonne] = value ;
		fireTableChanged(new TableModelEvent(this, ligne, ligne, colonne)) ;

	}

	public String getColumnName (int colonne)
	{
		return titres[colonne] ;
	}

	public boolean isCellEditable (int row, int col)
	{
		// la colonne 0 est non editable
		if (col == 0)
			return false ;
		else
			return true ;
	}

}
