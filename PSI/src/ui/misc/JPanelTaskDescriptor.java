
package ui.misc ;

import javax.swing.JPanel ;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import ui.resource.Bundle;
import model.spem2.TaskDescriptor;
import java.util.Locale;
import java.text.DateFormat;
/**
 * JPanelTaskDescriptor : TODO type description
 *
 * @author kass
 * @version 1.0
 *
 */
public class JPanelTaskDescriptor extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L ;
	private String[] nomsColonnes = {Bundle.getText("JPanelTaskDescriptor"),
            Bundle.getText("JPanelTaskDescriptorCol1"), Bundle.getText("JPanelTaskDescriptorCol2"),Bundle.getText("JPanelTaskDescriptorCol3")} ;
	private Object[][] donnees = null ;
	private javax.swing.JTable table;
	private javax.swing.JScrollPane tableScrollPane;
	private Locale locale = Locale.getDefault();
	private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
	/**
	 * This is the default constructor
	 */
	public JPanelTaskDescriptor (TaskDescriptor _task)
	{
		super() ;
		donnees = new Object[2][4];
		
		donnees[0][0] = Bundle.getText("JPanelTaskDescriptorRow1");
		donnees[1][0] = Bundle.getText("JPanelTaskDescriptorRow2");
		donnees[0][1] = dateFormat.format((_task.getPlanningData()).getStartDate());
		donnees[1][1] = dateFormat.format((_task.getRealData()).getStartDate());
		donnees[0][2] = dateFormat.format((_task.getPlanningData()).getFinishDate());
		donnees[1][2] = dateFormat.format((_task.getRealData()).getFinishDate());
		donnees[0][3] = (_task.getPlanningData()).getDuration();
		donnees[1][3] = (_task.getRealData()).getDuration();
		
		ModelTableTaskDescriptor tableModel = new ModelTableTaskDescriptor(donnees, nomsColonnes);
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

/**
 * ModelTableTaskDescriptor : TODO type description
 *
 * @author kass
 * @version 1.0
 *
 */
class ModelTableTaskDescriptor extends AbstractTableModel
{
	Object donnees[][];
    String titres[];
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L ;

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public ModelTableTaskDescriptor (Object donnees[][], String titres[])
	{
		this.donnees = donnees;
	    this.titres = titres;
	}

	
	public int getRowCount ()
	{
		// TODO Auto-generated method stub
		return donnees.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount ()
	{
		// TODO Auto-generated method stub
		if(getRowCount() != 0)
            return donnees[0].length;
        else
            return 0;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt (int ligne, int colonne)
	{
		// TODO Auto-generated method stub
		return donnees[ligne][colonne];
	}
	
	public String getColumnName(int colonne)
	{
	    return titres[colonne];
	}
	
	public boolean isCellEditable(int row, int col) 
	{
        // la colonne 0 est non editable
        if (col == 0) return false;
        else return true;
    }
	
	
	
}

