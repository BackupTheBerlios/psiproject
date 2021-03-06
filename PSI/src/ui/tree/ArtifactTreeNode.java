package ui.tree;

import java.util.Observable;
import java.util.Observer;

import javax.swing.tree.DefaultMutableTreeNode;

import model.spem2.Artifact;

/**
 * ArtifactTreeNode : A tree representation of a product artifact
 *
 * @author Conde Mickael K.
 * @version 1.0
 *
 */
public class ArtifactTreeNode extends DefaultMutableTreeNode implements Observer
{
	private static final long serialVersionUID = 1829221686931444693L ;
	
	private Artifact artifact ;
	
	private MainTree tree = null ;

	/**
	 * Constructor
	 *
	 * @param _artifact
	 */
	public ArtifactTreeNode (Artifact _artifact, MainTree _tree)
	{
		super() ;

		this.tree = _tree ;
		this.artifact = _artifact ;
		this.artifact.addObserver(this) ;
		this.setUserObject(artifact) ;
	}

	/**
	 * Getter
	 *
	 * @return Returns the artifact.
	 */
	public Artifact getArtifact ()
	{
		return this.artifact ;
	}

	/**
	 * Setter
	 *
	 * @param _artifact The artifact to set.
	 */
	public void setArtifact (Artifact _artifact)
	{
		this.artifact = _artifact ;
	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update (Observable _observable, Object _observer)
	{
		tree.getModel().reload(this) ;
	}

}
