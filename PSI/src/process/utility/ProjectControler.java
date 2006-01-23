
package process.utility ;

import java.io.BufferedInputStream ;
import java.io.BufferedOutputStream ;
import java.io.File ;
import java.io.FileInputStream ;
import java.io.FileNotFoundException ;
import java.io.FileOutputStream ;
import java.io.IOException ;
import java.io.OutputStreamWriter ;
import java.io.UnsupportedEncodingException ;
import java.text.ParseException ;
import java.text.SimpleDateFormat ;
import java.util.ArrayList ;
import java.util.Collection ;
import java.util.Iterator ;

import javax.xml.parsers.DocumentBuilder ;
import javax.xml.parsers.DocumentBuilderFactory ;
import javax.xml.parsers.ParserConfigurationException ;

import org.w3c.dom.Document ;
import org.w3c.dom.NamedNodeMap ;
import org.w3c.dom.Node ;
import org.w3c.dom.NodeList ;
import org.xml.sax.SAXException ;
import org.xml.sax.SAXParseException ;

import process.exception.FileParseException ;
import process.exception.FileSaveException ;
import model.Component ;
import model.HumanResource ;
import model.Project ;
import model.spem2.BreakdownElement ;
import model.spem2.DeliveryProcess ;
import model.spem2.RoleDescriptor ;

/**
 * ProjectControler : Loads, saves project information
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public class ProjectControler
{
	/**
	 * Loads a NEW project (without a process linked to it) from an xml file, the project me created
	 * with Open Workbench
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _source
	 *            the file from which read the data
	 * @return the newly created project
	 * @throws FileParseException
	 */

	public static Project create (File _source) throws FileParseException
	{
		try
		{
			FileInputStream localFIS = new FileInputStream(_source) ;
			BufferedInputStream localBIS = new BufferedInputStream(localFIS) ;
			DocumentBuilderFactory localDBF = DocumentBuilderFactory.newInstance() ;

			try
			{
				DocumentBuilder localDB = localDBF.newDocumentBuilder() ;
				localDB.setErrorHandler(new org.xml.sax.ErrorHandler()
				{
					/*
					 * Even if nothing is done, an exception will be thrown
					 * 
					 * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
					 */
					public void fatalError (SAXParseException e) throws SAXException
					{
					}

					/*
					 * Making sure that SAX exception is thrown
					 * 
					 * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
					 */
					public void error (SAXParseException e) throws SAXParseException
					{
						throw e ;
					}

					/*
					 * Warnings are not importants
					 * 
					 * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
					 */
					public void warning (SAXParseException e) throws SAXParseException
					{

					}
				}) ;

				/*
				 * Parsing the data and checking the file format
				 */
				Document localDocument = localDB.parse(localBIS) ;
				if (!localDocument.getDocumentElement().getTagName().equalsIgnoreCase("WORKBENCH_PROJECT")) { throw new FileParseException() ; }

				Node localProjectsRootNode = localDocument.getDocumentElement().getElementsByTagName("Projects").item(0) ;
				if (localProjectsRootNode == null) { throw new FileParseException() ; }

				/*
				 * PROJECT INITIALISATION Looking for the first <Project> node
				 */
				Node localProjectNode = null ;
				NodeList localProjectsNodeList = localProjectsRootNode.getChildNodes() ;
				int localChildCount = 0 ;
				int localChildMax = localProjectsNodeList.getLength() ;

				while (localChildCount < localChildMax)
				{
					if (localProjectsNodeList.item(localChildCount).getNodeType() == Node.ELEMENT_NODE
							&& localProjectsNodeList.item(localChildCount).getNodeName().equals("Project"))
					{
						localProjectNode = localProjectsNodeList.item(localChildCount) ;
					}
					localChildCount++ ;
				}

				if (localProjectNode == null) { throw new FileParseException() ; }

				/*
				 * Parsing
				 */
				Project localProject = new Project() ;
				SimpleDateFormat localSDF = new SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss") ;
				int localAttrTarget = 5 ; // For optimising the loop

				NamedNodeMap localAttributes = localProjectNode.getAttributes() ;
				Node localAttrNode ;
				int localAttrCount = localAttributes.getLength() ;
				for (int i = 0; i < localAttrCount; i++ )
				{
					localAttrNode = localAttributes.item(i) ;
					if (localAttrNode.getNodeName().equalsIgnoreCase("uid"))
					{
						localAttrTarget-- ;
						localProject.setId(localAttrNode.getNodeValue()) ;
					}
					else if (localAttrNode.getNodeName().equalsIgnoreCase("description"))
					{
						localAttrTarget-- ;
						localProject.setDescription(localAttrNode.getNodeValue()) ;
					}
					else if (localAttrNode.getNodeName().equalsIgnoreCase("name"))
					{
						localAttrTarget-- ;
						localProject.setName(localAttrNode.getNodeValue()) ;
					}
					else if (localAttrNode.getNodeName().equalsIgnoreCase("start"))
					{
						localAttrTarget-- ;
						try
						{
							localProject.setStartDate(localSDF.parse(localAttrNode.getNodeValue())) ;
						}
						catch (ParseException pe)
						{
						}
					}

					else if (localAttrNode.getNodeName().equalsIgnoreCase("finish"))
					{
						localAttrTarget-- ;
						try
						{
							localProject.setFinishDate(localSDF.parse(localAttrNode.getNodeValue())) ;
						}
						catch (ParseException pe)
						{
						}
					}

					if (localAttrTarget == 0)
					{
						break ;
					}
				}

				/*
				 * RESOURCES INITIALISATION
				 */
				Collection <HumanResource> localRessources = new ArrayList <HumanResource>() ;
				Node localResourcesRootNode = localDocument.getDocumentElement().getElementsByTagName("PoolResources").item(0) ;
				if (localResourcesRootNode == null) { throw new FileParseException() ; }

				Node localResourceNode = null ;
				localAttributes = null ;
				NodeList localResourcesNodeList = localResourcesRootNode.getChildNodes() ;
				String localID ;
				String localFullName ;
				localChildCount = localResourcesNodeList.getLength() ;

				for (int i = 0; i < localChildCount; i++ )
				{
					if (localResourcesNodeList.item(i).getNodeType() == Node.ELEMENT_NODE
							&& localResourcesNodeList.item(i).getNodeName().equalsIgnoreCase("PoolResource"))
					{
						localResourceNode = localResourcesNodeList.item(i) ;
						localAttributes = localResourceNode.getAttributes() ;
						localAttrCount = localAttributes.getLength() ;
						localAttrTarget = 2 ;
						localID = "" ;
						localFullName = "" ;
						for (int j = 0; j < localAttrCount; j++ )
						{
							localAttrNode = localAttributes.item(j) ;
							if (localAttrNode.getNodeName().equalsIgnoreCase("resourceId"))
							{
								localAttrTarget-- ;
								localID = localAttrNode.getNodeValue() ;
							}
							else if (localAttrNode.getNodeName().equalsIgnoreCase("fullName"))
							{
								localAttrTarget-- ;
								localFullName = localAttrNode.getNodeValue() ;
							}
							if (localAttrTarget == 0)
							{
								break ;
							}
						}
						localRessources.add(new HumanResource(localID, localFullName)) ;
					}
				}

				localProject.setResources(localRessources) ;
				return localProject ;

			}
			catch (ParserConfigurationException eDBF)
			{
				throw new FileParseException() ;
			}
			catch (SAXException eDB)
			{
				throw new FileParseException() ;
			}
			catch (IOException eDB)
			{
				throw new FileParseException() ;
			}
		}
		catch (FileNotFoundException eIS)
		{
			throw new FileParseException() ;
		}
	}

	/**
	 * Open a previously created project.
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _source
	 *            the file from which read the data
	 * @return a project
	 * @throws FileParseException
	 */
	public static Project open (File _source) throws FileParseException
	{
		return null ;
	}

	/**
	 * Saves the project in domino format TODO : packages handling
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _project
	 * @param _destination
	 */
	public static void save (Project _project, File _destination) throws FileSaveException
	{
		try
		{
			FileOutputStream localFOS = new FileOutputStream(_destination) ;
			BufferedOutputStream localBOS = new BufferedOutputStream(localFOS) ;

			OutputStreamWriter localOSW = new OutputStreamWriter(localBOS, "ISO-8859-1") ;

			DeliveryProcess localProcess = _project.getProcess() ;

			/*
			 * Initialisation
			 */
			BreakdownElement localElement ;
			ArrayList <Component> localComponents = new ArrayList <Component>() ;
			ArrayList <RoleDescriptor> localRoles = new ArrayList <RoleDescriptor>() ;
			// ArrayList <Component> localComponents = new ArrayList <Component>() ;

			// Initializing components
			Iterator <BreakdownElement> localIterator = _project.getProcess().getNestedElements().iterator() ;
			while (localIterator.hasNext())
			{
				localElement = localIterator.next() ;
				if (localElement instanceof Component)
				{
					localComponents.add((Component) localElement) ;
				}
			}
			
			// Initializing roles
			localIterator = _project.getProcess().getNestedElements().iterator() ;
			while (localIterator.hasNext())
			{
				localElement = localIterator.next() ;
				if (localElement instanceof RoleDescriptor)
				{
					localRoles.add((RoleDescriptor) localElement) ;
				}
			}

			/*
			 * Writing the file
			 */
			localOSW.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n") ;

			// Starting project
			localOSW.write("<exportProjet>\n") ;

			// Starting process
			localOSW.write("<exportExecution>\n") ;

			// Process info
			localOSW.write("<processus>\n") ;
			localOSW.write("<id>" + localProcess.getDescriptor().getId() + "</id>\n" + "<nom>" + localProcess.getDescriptor().getName() + "</nom>\n"
					+ "<nomAuteur>" + localProcess.getAuthorFullName() + "</nomAuteur>\n" + "<emailAuteur>" + localProcess.getAuthorMail() + "</emailAuteur>\n"
					+ "<dateExport>" + localProcess.getDate() + "</dateExport>\n") ;
			localOSW.write(localProcess.getDescriptor().getDescription().equals("") ? "<description/>" : "<description>"
					+ localProcess.getDescriptor().getDescription() + "</description>") ;
			localOSW.write("<cheminGeneration>" + localProcess.getGenerationPath() + "</cheminGeneration>\n") ;

			// Components within a process
			int localComponentsSize = localComponents.size() ;
			if (localComponentsSize == 0)
			{
				localOSW.write("<liste_composantId/>\n") ;
			}
			else
			{
				localOSW.write("<liste_composantId>\n") ;
				for (int i = 0; i < localComponentsSize; i++ )
				{
					localOSW.write("<composantId>" + localComponents.get(i).getDescriptor().getId() + "</composantId>\n") ;
				}
				localOSW.write("</liste_composantId>\n") ;
			}

			// Packages within a process
			localOSW.write("</processus>\n") ;

			// Listing all components
			if (localComponentsSize == 0)
			{
				localOSW.write("<liste_composant/>\n") ;
			}
			else
			{
				localOSW.write("<liste_composant>\n") ;
				for (int i = 0; i < localComponentsSize; i++ )
				{
					localOSW.write("<composant>\n") ;
					localOSW.write("<id>" + localComponents.get(i).getDescriptor().getId() + "</id>\n") ;
					localOSW.write("<nom>"+ localComponents.get(i).getDescriptor().getName() + "</nom>\n") ;
					localOSW.write("<version>" + localComponents.get(i).getVersion()+ "</version>\n") ;
					localOSW.write("<nomAuteur>" + localComponents.get(i).getAuthorFullName() + "</nomAuteur>\n");
					localOSW.write("<emailAuteur>"+ localComponents.get(i).getAuthorMail() + "</emailAuteur>\n") ;
					localOSW.write("<datePlacement>"+ localComponents.get(i).getDate() + "</datePlacement>\n") ;
					localOSW.write("<description>"+ localComponents.get(i).getDescriptor().getDescription() + "</description>\n") ;
					localOSW.write("<cheminDiagrammeFlots>"+ localComponents.get(i).getFlowDiagramPath() + "</cheminDiagrammeFlots>\n") ;
					// Roles
					// Products
					// Work definitions (activities)
					localOSW.write("<liste_definitionTravailId/>\n") ;
					localOSW.write("</composant>\n") ;
				}
				localOSW.write("</liste_composant>\n") ;
			}
			
			// Listing all roles
			int localRolesSize = localRoles.size() ;
			if (localRolesSize == 0)
			{
				localOSW.write("<liste_role/>\n") ;
			}
			else
			{
				localOSW.write("<liste_role>\n") ;
				for (int i = 0; i < localRolesSize; i++ )
				{
					localOSW.write("<role>\n") ;
					localOSW.write("<id>" + localRoles.get(i).getId() + "</id>\n") ;
					localOSW.write("<nom>"+ localRoles.get(i).getName() + "</nom>\n") ;
					localOSW.write("<agregatComposant>"+ localRoles.get(i).getParentId() + "</agregatComposant>\n") ;
					localOSW.write("<liste_responsabiliteProduit>\n") ;
					localOSW.write("</role>\n") ;
				}
				localOSW.write("</liste_role>\n") ;
			}

			// Ending process
			localOSW.write("</exportExecution>\n") ;

			// Project's objects

			// Ending project
			localOSW.write("</exportProjet>\n") ;

			localOSW.flush() ;
			localOSW.close() ;

		}

		catch (FileNotFoundException exc)
		{
			throw new FileSaveException() ;
		}

		catch (UnsupportedEncodingException exc)
		{
			throw new FileSaveException() ;
		}

		catch (IOException exc)
		{
			throw new FileSaveException() ;
		}
	}

}
