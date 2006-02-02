
package ui.tree ;

import java.util.Iterator ;

import javax.swing.tree.DefaultMutableTreeNode ;

import ui.resource.Bundle ;

import model.HumanResource ;
import model.Project ;

/**
 * ProjectTreeNode : tree like representation of a Project
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class ProjectTreeNode extends DefaultMutableTreeNode
{
	private static final long serialVersionUID = -3196033141454374573L ;

	private Project project = null ;

	/**
	 * Constructor
	 * 
	 * @param _project
	 *            the project to display
	 */
	public ProjectTreeNode (Project _project)
	{
		super() ;

		this.project = _project ;
		this.setUserObject(project.getName()) ;

		// Process information
		if (project.getProcess() != null)
		{
			DefaultMutableTreeNode localProcessRoot = new ProcessTreeNode(project.getProcess()) ;
			this.add(localProcessRoot) ;
		}

		// Resources information
		if (project.getResources() != null)
		{
			DefaultMutableTreeNode localRessourcesRoot = new DefaultMutableTreeNode(Bundle.getText("MainTreeNodeResources")) ;
			Iterator <HumanResource> localIterator = project.getResources().iterator() ;
			while (localIterator.hasNext())
			{
				localRessourcesRoot.add(new ResourceTreeNode(localIterator.next())) ;
			}
			this.add(localRessourcesRoot) ;
		}
	}

	/**
	 * Getter
	 * 
	 * @return Returns the project.
	 */
	public Project getProject ()
	{
		return this.project ;
	}

	/**
	 * Setter
	 * 
	 * @param _project
	 *            The project to set.
	 */
	public void setProject (Project _project)
	{
		this.project = _project ;
	}

}
