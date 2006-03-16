
package ui.tree ;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.tree.DefaultMutableTreeNode;

import model.HumanResource;
import model.Project;
import model.spem2.Iteration;
import ui.resource.Bundle;
import ui.resource.LocaleController;
import ui.resource.LocaleListener;

/**
 * ProjectTreeNode : tree like representation of a Project
 * 
 * @author Condé Mickaël K.
 * @version 1.0
 * 
 */
public class ProjectTreeNode extends DefaultMutableTreeNode implements Observer
{
	private static final long serialVersionUID = -3196033141454374573L ;

	private Project project = null ;
	
	private MainTree tree = null ; 
	
	/**
	 * The locale controller for the language.
	 */
	private LocaleController controllerLocale = null;
	
	DefaultMutableTreeNode RessourcesRoot = null ;

	
	/**
	 * Constructor
	 *
	 * @param _project
	 * @param _model
	 */
	public ProjectTreeNode (Project _project, MainTree _tree)
	{
		super() ;
		
		this.controllerLocale = LocaleController.getInstance();
	    this.controllerLocale.addLocaleListener(new LocaleListener(){
	    	public void localeChanged() {
	    		updateText();
	        }
	    });

		this.tree = _tree ;
		this.project = _project ;
		this.project.addObserver(this) ;
		if (this.project.getIterations().size() == 0)
		{
			this.setUserObject(project.getName()) ;
		}
		
		else
		{
			Iteration localIts[] = this.project.getIterations().toArray(new Iteration[0]) ;
			this.setUserObject(project.getName() + " (" + Bundle.getText("MainTreeNodeIterations") + localIts[localIts.length - 1].getDescriptor().getName() + ")") ;
		}

		// Process information
		if (project.getProcess() != null)
		{
			DefaultMutableTreeNode localProcessRoot = new ProcessTreeNode(project.getProcess(), tree) ;
			this.add(localProcessRoot) ;
		}

		// Resources information
		if (project.getResources() != null)
		{
			RessourcesRoot = new DefaultMutableTreeNode(Bundle.getText("MainTreeNodeResources")) ;
			Iterator <HumanResource> localIterator = project.getResources().iterator() ;
			while (localIterator.hasNext())
			{
				RessourcesRoot.add(new ResourceTreeNode(localIterator.next(), tree)) ;
			}
			this.add(RessourcesRoot) ;
		}
	}
	
	/**
	 * 
	 * This method updates texts in this tree during a language changing.
	 *
	 * @author MaT
	 * @version 1.0
	 *
	 */
	private void updateText()
	{
		//project name.
		if (this.project.getIterations().size() == 0)
		{
			this.setUserObject(project.getName()) ;
		}
		else
		{
			Iteration localIts[] = this.project.getIterations().toArray(new Iteration[0]) ;
			this.setUserObject(project.getName() + " (" + Bundle.getText("MainTreeNodeIterations") + localIts[localIts.length - 1].getDescriptor().getName() + ")") ;
		}
		//resources.
		if (project.getResources() != null)
		{
			RessourcesRoot.setUserObject(Bundle.getText("MainTreeNodeResources")) ;
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

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _observable, Object _object)
	{
		Iteration localIt = (Iteration)_object ;
		this.setUserObject(project.getName() + " (" + Bundle.getText("MainTreeNodeIterations") + localIt.getDescriptor().getName() + ")") ;
		tree.getModel().reload(this) ;
	}

}
