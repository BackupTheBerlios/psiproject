
package ui.misc ;

import javax.swing.JPanel ;
import javax.swing.JButton ;
import javax.swing.JScrollPane ;
import javax.swing.JTable ;
import javax.swing.JTextArea ;
import javax.swing.JTextField ;
import javax.swing.SwingUtilities ;
import javax.swing.table.AbstractTableModel ;
import ui.resource.Bundle ;
import model.spem2.PlanningData ;
import model.spem2.TaskDescriptor ;
import java.util.Locale ;
import java.util.Observable ;
import java.util.Observer ;
import java.util.Vector ;
import java.awt.Dimension ;
import java.awt.GridLayout ;
import java.text.DateFormat ;
import java.text.ParseException ;

import javax.swing.event.* ;
import javax.swing.JLabel ;

/**
 * JPanelTaskDescriptor : TODO type description
 * 
 * @author kass
 * @version 1.0
 * 
 */
public class TaskDescriptorPanel extends JPanel implements Observer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L ;

	private String[] nomsColonnes = {
			Bundle.getText("JPanelTaskDescriptor"), Bundle.getText("JPanelTaskDescriptorCol1"), Bundle.getText("JPanelTaskDescriptorCol2"),
			Bundle.getText("JPanelTaskDescriptorCol3")
	} ;

	private javax.swing.JTable table ;

	private javax.swing.JScrollPane tableScrollPane ;

	private JLabel idLabel = null ;

	private JTextField idTextValue = null ;

	private JLabel nameLabel = null ;

	private JTextField nameTextValue = null ;

	private JLabel descLabel = null ;

	private JTextArea descTextValue = null ;

	private JPanel jPanel = null ;

	private JPanel jPanelCenter = null ;

	private GridLayout layoutCenter = null ;

	private TaskDescriptor task = null ;

	private JButton jButtonModifiar = null ;

	private JButton jButtonSave = null ;

	private JLabel jLabelPlanification = null ;

	private JScrollPane jScrollDesc = null ;

	/**
	 * This is the default constructor
	 */
	public TaskDescriptorPanel (TaskDescriptor _task)
	{
		super() ;
		task = _task ;
		task.addObserver(this) ;

		ModelTableTaskDescriptor tableModel = new ModelTableTaskDescriptor(nomsColonnes, _task) ;
		initialize() ;
		table = new JTable(tableModel) ;
		table.getTableHeader().setReorderingAllowed(false) ;
		table.setPreferredScrollableViewportSize(new Dimension(50, 10)) ;
		tableScrollPane = new JScrollPane(table) ;

		jPanelCenter.add(tableScrollPane) ;
		add(jPanelCenter, java.awt.BorderLayout.CENTER) ;

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{

		setLayout(new java.awt.BorderLayout()) ;
		layoutCenter = new GridLayout(2, 1) ;

		jPanelCenter = new JPanel() ;
		jPanelCenter.setLayout(layoutCenter) ;

		jPanelCenter.add(getJPanel()) ;

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel ()
	{

		idLabel = new JLabel() ;
		idTextValue = new JTextField() ;
		nameLabel = new JLabel() ;
		nameTextValue = new JTextField() ;
		descLabel = new JLabel() ;
		descTextValue = new JTextArea() ;
		jLabelPlanification = new JLabel() ;

		idLabel.setText(Bundle.getText("JPanelTaskDescriptorIDLabel")) ;
		nameLabel.setText(Bundle.getText("JPanelTaskDescriptorNameLabel")) ;
		descLabel.setText(Bundle.getText("JPanelTaskDescriptorDescriptionLabel")) ;
		jLabelPlanification.setText(Bundle.getText("JPanelTaskDescriptorPlanification")) ;

		if (jPanel == null)
		{
			jPanel = new JPanel() ;
			jPanel.setLayout(null) ;

			if (task.getId() != null)
			{
				jPanel.setLayout(null) ;
				idTextValue.setText(task.getId()) ;
				idTextValue.setEditable(false) ;
				jPanel.add(idLabel) ;
				jPanel.add(idTextValue) ;
				idLabel.setLocation(1, 1) ;
				idLabel.setSize(80, 20) ;
				idTextValue.setLocation(82, 1) ;
				idTextValue.setSize(300, 20) ;

			}

			if (task.getName() != null)
			{
				nameTextValue.setText(task.getName()) ;
				nameTextValue.setEditable(false) ;
				jPanel.add(nameLabel) ;
				jPanel.add(nameTextValue) ;
				nameLabel.setLocation(1, 22) ;
				nameLabel.setSize(80, 20) ;
				nameTextValue.setLocation(82, 22) ;
				nameTextValue.setSize(300, 20) ;

			}

			/*
			 * if ( (!task.getDescription().equals("")) && (!task.getDescription().equals("[N/A]"))) {
			 */

			descTextValue.setText(task.getDescription()) ;
			descTextValue.setSize(300, 60) ;
			descTextValue.setEditable(false) ;
			jScrollDesc = new JScrollPane(descTextValue) ;
			jPanel.add(descLabel) ;
			jPanel.add(jScrollDesc) ;
			descLabel.setLocation(1, 44) ;
			descLabel.setSize(80, 20) ;
			jScrollDesc.setSize(300, 60) ;
			jScrollDesc.setLocation(82, 44) ;
			// }

			jButtonModifiar = new JButton() ;
			jButtonModifiar.setText(Bundle.getText("JPanelTaskDescriptorModifiarButton")) ;

			jButtonModifiar.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{

					nameTextValue.setEditable(true) ;
					descTextValue.setEditable(true) ;
					jButtonSave.setEnabled(true) ;
				}
			}) ;

			jButtonSave = new JButton() ;
			jButtonSave.setText(Bundle.getText("JPanelTaskDescriptorSaveButton")) ;
			jButtonSave.setEnabled(false) ;

			jButtonSave.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed (java.awt.event.ActionEvent e)
				{
					task.setName(nameTextValue.getText()) ;
					task.setDescription(descTextValue.getText()) ;
					nameTextValue.setEditable(false) ;
					descTextValue.setEditable(false) ;
					jButtonSave.setEnabled(false) ;
				}
			}) ;

			jPanel.add(jButtonModifiar) ;
			jPanel.add(jButtonSave) ;
			jButtonModifiar.setLocation(1, 106) ;
			jButtonModifiar.setSize(80, 20) ;
			jButtonSave.setLocation(82, 106) ;
			jButtonSave.setSize(110, 20) ;

			jPanel.add(jLabelPlanification) ;
			jLabelPlanification.setSize(80, 20) ;
			jLabelPlanification.setLocation(1, 135) ;

			jPanel.setVisible(true) ;

		}
		return jPanel ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _arg0, Object _arg1)
	{
		// TODO Auto-generated method stub

	}

}


/**
 * ModelTableTaskDescriptor : TODO type description
 * 
 * @author kass
 * @version 1.0
 * 
 */

class ModelTableTaskDescriptor extends AbstractTableModel implements Observer
{
	Object donnees[][] ;

	String titres[] ;

	private Vector data = new Vector() ;

	TaskDescriptor t = null ;

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
	@ SuppressWarnings ("unchecked")
	public ModelTableTaskDescriptor (String titres[], TaskDescriptor _task)
	{
		// this.donnees = donnees ;
		this.titres = titres ;
		t = _task ;
		t.addObserver(this) ;

		data.add(new TableTaskDescriptorRecord(this, t, 0)) ;
		data.add(new TableTaskDescriptorRecord(this, t, 1)) ;

	}

	public int getRowCount ()
	{
		// TODO Auto-generated method stub
		return data.size() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount ()
	{
		return titres.length ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt (int ligne, int colonne)
	{
		// TODO Auto-generated method stub
		return ((TableTaskDescriptorRecord) data.get(ligne)).getValueAt(ligne, colonne) ;
	}

	public void setValueAt (Object value, int ligne, int colonne)
	{
		((TableTaskDescriptorRecord) data.get(ligne)).setValueAt(value, ligne, colonne) ;
		fireTableChanged(new TableModelEvent(this, ligne, ligne, colonne)) ;

	}

	@ SuppressWarnings ("unchecked")
	public Class getColumnClass (int column)
	{
		return getValueAt(0, column).getClass() ;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable o, Object arg)
	{
		// TODO Auto-generated method stub
		final TaskDescriptor tempTask = (TaskDescriptor) arg ;

		Runnable runnable = new Runnable()
		{
			public void run ()
			{
				for (int i = 0; i < getRowCount(); i++ )
				{
					if ( ((TableTaskDescriptorRecord) data.get(i)).getTask() == tempTask)
					{
						data.remove(i) ;
						fireTableRowsDeleted(i, i) ;
					}
				}
			}
		} ;
		SwingUtilities.invokeLater(runnable) ;
	}

}


class TableTaskDescriptorRecord implements Observer
{
	private int row ;

	private ModelTableTaskDescriptor modelTableTask = null ;

	private TaskDescriptor t = null ;

	private Locale locale = Locale.getDefault() ;

	private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale) ;

	public TableTaskDescriptorRecord (ModelTableTaskDescriptor aMTTD, TaskDescriptor _task, int aRow)
	{
		row = aRow ;
		modelTableTask = aMTTD ;
		t = _task ;

		t.addObserver(this) ;
	}

	public void update (Observable o, Object arg)
	{
		modelTableTask.fireTableRowsUpdated(row, row) ;
	}

	public TaskDescriptor getTask ()
	{
		return t ;
	}

	public void setRow (int aRow)
	{
		row = aRow ;
	}

	public Object getValueAt (int _row, int column)
	{
		switch (column)
		{
			case 0:
				if (_row == 0)
				{
					return Bundle.getText("JPanelTaskDescriptorRow1") ;
				}
				else
				{
					return Bundle.getText("JPanelTaskDescriptorRow2") ;
				}

			case 1:
				if (_row == 0)
				{
					return new String(dateFormat.format( (t.getPlanningData()).getStartDate())) ;
				}
				else
				{
					return new String(dateFormat.format( (t.getRealData()).getStartDate())) ;
				}
			case 2:
				if (_row == 0)
				{
					return new String(dateFormat.format( (t.getPlanningData()).getFinishDate())) ;
				}
				else
				{
					return new String(dateFormat.format( (t.getRealData()).getFinishDate())) ;
				}
			case 3:
				if (_row == 0)
				{
					return new Float( (t.getPlanningData()).getDuration()) ;
				}
				else
				{
					return new Float( (t.getRealData()).getDuration()) ;
				}
			default:
				return 0 ;
		}
	}

	public void setValueAt (Object obj, int _row, int column)
	{
		PlanningData pd = new PlanningData() ;

		try
		{

			switch (column)
			{

				case 1:
					if (_row == 0)
					{

						pd = t.getPlanningData() ;
						pd.setStartDate(dateFormat.parse((String) obj)) ;
						t.setPlanningData(pd) ;

					}
					else
					{
						pd = t.getRealData() ;
						pd.setStartDate(dateFormat.parse((String) obj)) ;
						t.setRealData(pd) ;
					}

					break ;
				case 2:
					if (_row == 0)
					{
						pd = t.getPlanningData() ;
						pd.setFinishDate(dateFormat.parse((String) obj)) ;
						t.setPlanningData(pd) ;

					}
					else
					{
						pd = t.getRealData() ;
						pd.setFinishDate(dateFormat.parse((String) obj)) ;
						t.setRealData(pd) ;
					}
					break ;
				case 3:
					if (_row == 0)
					{
						pd = t.getPlanningData() ;
						pd.setDuration( ((Float) obj).floatValue()) ;
						t.setPlanningData(pd) ;

					}
					else
					{
						pd = t.getRealData() ;
						pd.setDuration( ((Float) obj).floatValue()) ;
						t.setRealData(pd) ;
					}
					break ;

				default:
					break ;
			}

		}
		catch (ParseException exc)
		{
			// Dans le cas ou le format de saisie des dates est incorrect
			exc.printStackTrace() ;
		}
	}

}
