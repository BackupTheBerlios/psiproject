package ui.window;
import javax.swing.table.*;
/**
 * ModelTableActivity : TODO type description
 *
 * @author kass
 * @version 1.0
 *
 */
public class ModelTableActivity extends AbstractTableModel
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
	public ModelTableActivity (Object donnees[][], String titres[])
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
        // toutes les colonnes 0 et 1 sont non editable
        if (col == 0) return false;
        else return true;
    }
	
	

}
