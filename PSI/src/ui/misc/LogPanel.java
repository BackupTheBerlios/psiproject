
package ui.misc ;

import java.awt.BorderLayout ;
import java.util.ArrayList ;

import javax.swing.JPanel ;
import javax.swing.JTable ;
import javax.swing.table.AbstractTableModel ;

import ui.resource.Bundle ;

import model.LogInformation ;

/**
 * LogPanel : This is the container of the table containing the log entries
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public class LogPanel extends JPanel
{
	private static final long serialVersionUID = -4629275063522364111L ;

	/**
	 * The maximal amount of rows to display
	 */
	private static final short MAXROWS = 10 ;

	/**
	 * The maximal amount of columns in the table
	 */
	private static final short MAXCOLS = 3 ;

	/**
	 * The table that will display data
	 */
	private JTable logTable ;

	/**
	 * The table model that will be used by the logTable
	 */
	private LogTableModel logTableModel ;

	/**
	 * Constructor
	 * 
	 */
	public LogPanel ()
	{
		/*
		 * NOTES : in this constructor we create a table with a table model which is defined in this
		 * file. each row of a table will contain
		 */

		logTableModel = new LogTableModel() ;
		logTable = new JTable(logTableModel) ;

		initialize() ;
		add(logTable, BorderLayout.CENTER) ;

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize ()
	{
		this.setLayout(new java.awt.BorderLayout()) ;
	}

	/**
	 * Adds one row of type LogInformation into the table
	 * If max rows reached, then supresses the oldest
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _logInformation
	 *            the entry to add
	 */
	public void addInformation (LogInformation _logInformation)
	{
		if (logTable.getRowCount() == MAXROWS)
		{
			
		}
	}

	/**
	 * LogTableModel : The model to be used by the table
	 * 
	 * @author m1isi33
	 * @version 1.0
	 * 
	 */
	class LogTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = -2475598514741619207L ;

		private ArrayList <LogInformation> data ;

		private String[] titles = {
				Bundle.getText("LogPanelTableColumn1"), Bundle.getText("LogPanelTableColumn2"), Bundle.getText("LogPanelTableColumn3")
		} ;

		public LogTableModel ()
		{
			super() ;

			this.data = new ArrayList <LogInformation>() ;

		}

		/**
		 * Constructor
		 * 
		 * @param _data
		 */
		public LogTableModel (ArrayList <LogInformation> _data)
		{
			this() ;

			this.data = _data ;
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@ Override
		public String getColumnName (int _column)
		{
			try
			{
				return titles[_column] ;
			}

			catch (ArrayIndexOutOfBoundsException exc)
			{
				return "[N/A]" ;
			}
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
		 */
		@ Override
		public boolean isCellEditable (int _rowIndex, int _columnIndex)
		{
			return false ;
		}

		/**
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount ()
		{
			return MAXCOLS ;
		}

		/**
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount ()
		{
			return data.size() ;
		}

		/**
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt (int _rowIndex, int _columnIndex)
		{
			try
			{
				return data.get(_rowIndex) ;
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				return null ;
			}

		}

	}

}
