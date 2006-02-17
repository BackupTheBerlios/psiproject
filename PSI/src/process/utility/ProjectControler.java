
package process.utility ;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Component;
import model.Guide;
import model.GuideType;
import model.HumanResource;
import model.Interface;
import model.Presentation;
import model.Project;
import model.spem2.Activity;
import model.spem2.Artifact;
import model.spem2.BreakdownElement;
import model.spem2.DeliveryProcess;
import model.spem2.ProductType;
import model.spem2.RoleDescriptor;
import model.spem2.TaskDefinition;
import model.spem2.TaskDescriptor;
import model.spem2.WorkProductDescriptor;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import process.exception.FileParseException;
import process.exception.FileSaveException;

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
					if (localAttrNode.getNodeName().equalsIgnoreCase("projectID"))
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
	 * Saves the project in domino format, PSI also uses this format as save files
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
			Artifact localArtifact ;
			TaskDefinition localTaskDefinition ;
			Collection <BreakdownElement> localNested ;
			Iterator <BreakdownElement> localIterator ;
			BreakdownElement localTempElement ;
			ArrayList <Component> localComponents = new ArrayList <Component>() ;
			HumanResource localTempResource ;
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy") ;

			// The following variables represents buffers for various elements (for optimisation in
			// loops)
			Iterator <BreakdownElement> internIterator ;
			Iterator <WorkProductDescriptor> productIterator ;
			Iterator <WorkProductDescriptor> fakeArtifactIterator ;
			Iterator <TaskDescriptor> taskIterator ;
			Iterator <TaskDefinition> taskDIterator ;
			Iterator <RoleDescriptor> roleIterator ;
			Iterator <Interface> interfaceIterator ;
			Iterator <Artifact> artifactIterator ;
			Iterator <Guide> guideIterator ;
			Iterator <HumanResource> resourceIterator ;
			String productsInfo = "" ;
			String productsIds = "" ;

			String rolesInfo = "" ;
			String rolesIds = "" ;

			String activitiesInfo = "" ;
			String activitiesIds = "" ;

			String productTypesInfo = "" ;
			String taskDescsInfo = "" ;
			String interfacesInfo = "" ;
			String presentationsInfo = "" ;
			String guidesInfo = "" ;
			String guideTypesInfo = "" ;
			
			String artifactsInfo = "" ;
			String artifactsIds = "" ;
			String artifactsForTaskIn = "" ;
			String artifactsForTaskOut = "" ;
			
			String taskDefsInfo = "" ;
			String taskDefsIds = "" ;

			// Initializing components
			localIterator = _project.getProcess().getNestedElements().iterator() ;
			while (localIterator.hasNext())
			{
				localElement = localIterator.next() ;
				if (localElement instanceof Component)
				{
					localComponents.add((Component) localElement) ;
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

			/*
			 * Process info
			 */
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

			// Packages within a process to be implemented later ?
			localOSW.write("<liste_paquetagePresentationId/>\n") ;
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
					localOSW.write("<nom>" + localComponents.get(i).getDescriptor().getName() + "</nom>\n") ;
					localOSW.write("<version>" + localComponents.get(i).getVersion() + "</version>\n") ;
					localOSW.write("<nomAuteur>" + localComponents.get(i).getAuthorFullName() + "</nomAuteur>\n") ;
					localOSW.write("<emailAuteur>" + localComponents.get(i).getAuthorMail() + "</emailAuteur>\n") ;
					localOSW.write("<datePlacement>" + localComponents.get(i).getDate() + "</datePlacement>\n") ;
					localOSW.write("<description>" + localComponents.get(i).getDescriptor().getDescription() + "</description>\n") ;
					localOSW.write("<cheminDiagrammeFlots>" + localComponents.get(i).getFlowDiagramPath() + "</cheminDiagrammeFlots>\n") ;

					localNested = localComponents.get(i).getNestedElements() ;
					localIterator = localNested.iterator() ;

					// Generating strings that will contain data
					while (localIterator.hasNext())
					{
						localTempElement = localIterator.next() ;
						// For roles
						if (localTempElement instanceof RoleDescriptor)
						{
							rolesIds = rolesIds + "<roleId>" + ((RoleDescriptor) localTempElement).getId() + "</roleId>\n" ;

						} // End if instanceof role

						// For products
						else if (localTempElement instanceof WorkProductDescriptor)
						{
							productsIds = productsIds + "<produitId>" + ((WorkProductDescriptor) localTempElement).getId() + "</produitId>\n" ;

						} // End of product

						// For activities
						else if (localTempElement instanceof Activity)
						{
							activitiesIds = activitiesIds + "<definitionTravailId>" + ((Activity) localTempElement).getDescriptor().getId()
									+ "</definitionTravailId>\n" ;
						}
					}

					// Roles
					if (rolesIds.equals(""))
					{
						localOSW.write("<liste_roleId/>\n") ;
					}

					else
					{
						localOSW.write("<liste_roleId>\n" + rolesIds + "</liste_roleId>\n") ;
					}

					// Products
					if (productsIds.equals(""))
					{
						localOSW.write("<liste_produitId/>\n") ;
					}

					else
					{
						localOSW.write("<liste_produitId>\n" + productsIds + "</liste_produitId>\n") ;
					}

					// Work definitions (activities)
					if (productsIds.equals(""))
					{
						localOSW.write("<liste_definitionTravailId/>\n") ;
					}

					else
					{
						localOSW.write("<liste_definitionTravailId>\n" + activitiesIds + "</liste_definitionTravailId>\n") ;
					}

					// Additional component info
					if (localComponents.get(i).getRequiredInterface() == null)
					{
						localOSW.write("<interfaceRequise/>\n") ;
					}
					else
					{
						localOSW.write("<interfaceRequise>" + localComponents.get(i).getRequiredInterface().getId() + "</interfaceRequise>\n") ;
					}

					if (localComponents.get(i).getGivenInterface() == null)
					{
						localOSW.write("<interfaceFournie/>\n") ;
					}
					else
					{
						localOSW.write("<interfaceFournie>" + localComponents.get(i).getGivenInterface().getId() + "</interfaceFournie>\n") ;
					}

					if (localComponents.get(i).getPresentationElement() == null)
					{
						localOSW.write("<elementPresentationId/>\n") ;
					}
					else
					{
						localOSW.write("<elementPresentationId>" + localComponents.get(i).getPresentationElement().getId() + "</elementPresentationId>\n") ;
					}

					localOSW.write("</composant>\n") ;
				}
				localOSW.write("</liste_composant>\n") ;
			} // End of components

			// Generating general data
			localIterator = _project.getProcess().getNestedElements().iterator() ;
			while (localIterator.hasNext())
			{
				localElement = localIterator.next() ;
				// For roles
				if (localElement instanceof RoleDescriptor)
				{
					rolesInfo = rolesInfo + "<role>\n" ;
					rolesInfo = rolesInfo + "<id>" + ((RoleDescriptor) localElement).getId() + "</id>\n" ;
					rolesInfo = rolesInfo + "<nom>" + ((RoleDescriptor) localElement).getName() + "</nom>\n" ;
					rolesInfo = rolesInfo + "<agregatComposant>" + ((RoleDescriptor) localElement).getParentId() + "</agregatComposant>\n" ;

					// Products in roles
					if ( ((RoleDescriptor) localElement).getProducts().size() == 0)
					{
						rolesInfo = rolesInfo + "<liste_responsabiliteProduit/>\n" ;
					}

					else
					{
						rolesInfo = rolesInfo + "<liste_responsabiliteProduit>\n" ;
						productIterator = ((RoleDescriptor) localElement).getProducts().iterator() ;
						while (productIterator.hasNext())
						{
							rolesInfo = rolesInfo + "<responsabiliteProduit>" + productIterator.next().getId() + "</responsabiliteProduit>\n" ;
						}
						rolesInfo = rolesInfo + "</liste_responsabiliteProduit>\n" ;
					}

					// Tasks (activities) in roles
					if ( ((RoleDescriptor) localElement).getPrimaryTasks().size() == 0)
					{
						rolesInfo = rolesInfo + "<liste_participationActivite/>\n" ;
					}

					else
					{
						rolesInfo = rolesInfo + "<liste_participationActivite>\n" ;
						taskIterator = ((RoleDescriptor) localElement).getPrimaryTasks().iterator() ;
						while (taskIterator.hasNext())
						{
							rolesInfo = rolesInfo + "<participationActivite>" + taskIterator.next().getId() + "</participationActivite>\n" ;
						}
						rolesInfo = rolesInfo + "</liste_participationActivite>\n" ;
					}

					if ( ((RoleDescriptor) localElement).getPresentationElement() == null)
					{
						rolesInfo = rolesInfo + "<elementPresentationId/>" ;
					}
					else
					{
						rolesInfo = rolesInfo + "<elementPresentationId>" + ((RoleDescriptor) localElement).getPresentationElement().getId()
								+ "</elementPresentationId>\n" ;
					}

					rolesInfo = rolesInfo + "</role>\n" ;
				} // End if instanceof role

				// For products
				else if (localElement instanceof WorkProductDescriptor)
				{
					productsInfo = productsInfo + "<produit>\n" ;
					productsInfo = productsInfo + "<id>" + ((WorkProductDescriptor) localElement).getId() + "</id>\n" ;
					productsInfo = productsInfo + "<nom>" + ((WorkProductDescriptor) localElement).getName() + "</nom>\n" ;
					productsInfo = productsInfo + "<agregatComposant>" + ((WorkProductDescriptor) localElement).getParentId() + "</agregatComposant>\n" ;
					if ( ((WorkProductDescriptor) localElement).getResponsible() == null)
					{
						productsInfo = productsInfo + "<responsabiliteRole/>" ;
					}
					else
					{
						productsInfo = productsInfo + "<responsabiliteRole>" + ((WorkProductDescriptor) localElement).getResponsible().getId()
								+ "</responsabiliteRole>\n" ;
					}
					if ( ((WorkProductDescriptor) localElement).getProductType() == null)
					{
						productsInfo = productsInfo + "<typeProduitId/>" ;
					}
					else
					{
						productsInfo = productsInfo + "<typeProduitId>" + ((WorkProductDescriptor) localElement).getProductType().getId()
								+ "</typeProduitId>\n" ;
					}

					// Interfaces in products
					if ( ((WorkProductDescriptor) localElement).getInterfaces().size() == 0)
					{
						productsInfo = productsInfo + "<liste_interfaceId/>\n" ;
					}

					else
					{
						productsInfo = productsInfo + "<liste_interfaceId>\n" ;
						interfaceIterator = ((WorkProductDescriptor) localElement).getInterfaces().iterator() ;
						while (interfaceIterator.hasNext())
						{
							productsInfo = productsInfo + "<interfaceId>" + interfaceIterator.next().getId() + "</interfaceId>\n" ;
						}
						productsInfo = productsInfo + "</liste_interfaceId>\n" ;
					}

					// States : not implemented
					productsInfo = productsInfo + "<liste_etatId/>" ;

					// Tasks : in and out
					if ( ((WorkProductDescriptor) localElement).getUsingTasks().size() == 0)
					{
						productsInfo = productsInfo + "<liste_entreeActivite/>\n" ;
					}

					else
					{
						productsInfo = productsInfo + "<liste_entreeActivite>\n" ;
						taskIterator = ((WorkProductDescriptor) localElement).getUsingTasks().iterator() ;
						while (taskIterator.hasNext())
						{
							productsInfo = productsInfo + "<entreeActivite>" + taskIterator.next().getId() + "</entreeActivite>\n" ;
						}
						productsInfo = productsInfo + "</liste_entreeActivite>\n" ;
					}

					if ( ((WorkProductDescriptor) localElement).getProducingTasks().size() == 0)
					{
						productsInfo = productsInfo + "<liste_sortieActivite/>\n" ;
					}

					else
					{
						productsInfo = productsInfo + "<liste_sortieActivite>\n" ;
						taskIterator = ((WorkProductDescriptor) localElement).getProducingTasks().iterator() ;
						while (taskIterator.hasNext())
						{
							productsInfo = productsInfo + "<sortieActivite>" + taskIterator.next().getId() + "</sortieActivite>\n" ;
						}
						productsInfo = productsInfo + "</liste_sortieActivite>\n" ;
					}

					if ( ((WorkProductDescriptor) localElement).getPresentationElement() == null)
					{
						productsInfo = productsInfo + "<elementPresentationId/>" ;
					}
					else
					{
						productsInfo = productsInfo + "<elementPresentationId>" + ((WorkProductDescriptor) localElement).getPresentationElement().getId()
								+ "</elementPresentationId>\n" ;
					}

					productsInfo = productsInfo + "</produit>\n" ;
					
					// Artifacts list for product
					// Info + ID
					if (((WorkProductDescriptor)localElement).getArtifacts().size() > 0)
					{
						artifactsIds += "<ProduitArtefact>\n" ;
						artifactsIds += "<idProduit>"+ ((WorkProductDescriptor)localElement).getId() + "</idProduit>\n" ;
						artifactsIds += "<listeIdArtefact>\n" ;
						
						artifactIterator = ((WorkProductDescriptor)localElement).getArtifacts().iterator() ;
						while (artifactIterator.hasNext())
						{
							localArtifact = artifactIterator.next() ;
							artifactsInfo += "<eltArtefact>\n" ;
							artifactsInfo += "<id>" + localArtifact.getId() + "</id>" ;
							artifactsInfo += "<nom>" + localArtifact.getName() + "</nom>" ;
							artifactsInfo += "</eltArtefact>\n" ;
							
							artifactsIds += "<id>" + localArtifact.getId() + "</id>" ;
						} 
						
						artifactsIds += "</listeIdArtefact>\n" ;
						artifactsIds += "</ProduitArtefact>\n" ;
					}
					// Association
					
					
				} // End of product

				// For activities
				else if (localElement instanceof Activity)
				{
					activitiesInfo = activitiesInfo + "<definitionTravail>\n" ;
					activitiesInfo = activitiesInfo + "<id>" + ((Activity) localElement).getDescriptor().getId() + "</id>\n" ;
					activitiesInfo = activitiesInfo + "<nom>" + ((Activity) localElement).getDescriptor().getName() + "</nom>\n" ;
					if ( ((Activity) localElement).getActivityDiagramPath().equals(""))
					{
						activitiesInfo = activitiesInfo + "<cheminDiagrammeActivites/>" ;
					}
					else
					{
						activitiesInfo = activitiesInfo + "<cheminDiagrammeActivites>" + ((Activity) localElement).getActivityDiagramPath()
								+ "</cheminDiagrammeActivites>\n" ;
					}
					if ( ((Activity) localElement).getFlowDiagramPath().equals(""))
					{
						activitiesInfo = activitiesInfo + "<cheminDiagrammeFlots/>" ;
					}
					else
					{
						activitiesInfo = activitiesInfo + "<cheminDiagrammeFlots>" + ((Activity) localElement).getFlowDiagramPath()
								+ "</cheminDiagrammeFlots>\n" ;
					}
					
					//activitiesInfo = activitiesInfo + "<agregatComposant>" + ((Activity) localElement) + "</agregatComposant>\n" ;

					// Tasks
					if ( ((Activity) localElement).getNestedElements().size() == 0)
					{
						activitiesInfo = activitiesInfo + "<liste_activiteId/>\n" ;
					}

					else
					{
						activitiesInfo = activitiesInfo + "<liste_activiteId>\n" ;
						internIterator = ((Activity) localElement).getNestedElements().iterator() ;
						while (internIterator.hasNext())
						{
							localTempElement = internIterator.next() ;
							if (localTempElement instanceof TaskDescriptor)
							{
								activitiesInfo = activitiesInfo + "<activite>" + ((TaskDescriptor) localTempElement).getId() + "</activite>\n" ;
							}
						}
						activitiesInfo = activitiesInfo + "</liste_activiteId>\n" ;
					}

					if ( ((Activity) localElement).getPresentationElement() == null)
					{
						activitiesInfo = activitiesInfo + "<elementPresentationId/>" ;
					}
					else
					{
						activitiesInfo = activitiesInfo + "<elementPresentationId>" + ((Activity) localElement).getPresentationElement().getId()
								+ "</elementPresentationId>\n" ;
					}

					activitiesInfo = activitiesInfo + "</definitionTravail>\n" ;
				} // End activities

				// For task descriptors
				else if (localElement instanceof TaskDescriptor)
				{
					taskDescsInfo = taskDescsInfo + "<activite>\n" ;
					taskDescsInfo = taskDescsInfo + "<id>" + ((TaskDescriptor) localElement).getId() + "</id>\n" ;
					taskDescsInfo = taskDescsInfo + "<nom>" + ((TaskDescriptor) localElement).getName() + "</nom>\n" ;

					// Role : we take the first ...
					if ( ((TaskDescriptor) localElement).getPrimaryPerformers().size() == 0)
					{
						taskDescsInfo = taskDescsInfo + "<participationRole/>\n" ;
					}

					else
					{
						roleIterator = ((TaskDescriptor) localElement).getPrimaryPerformers().iterator() ;
						taskDescsInfo = taskDescsInfo + "<participationRole>" + roleIterator.next().getId() + "</participationRole>\n" ;
					}

					// Activity
					if ( ((TaskDescriptor) localElement).getParentId().equals(""))
					{
						taskDescsInfo = taskDescsInfo + "<agregatDefinitionTravail/>" ;
					}
					else
					{
						taskDescsInfo = taskDescsInfo + "<agregatDefinitionTravail>" + ((TaskDescriptor) localElement).getParentId()
								+ "</agregatDefinitionTravail>\n" ;
					}

					// Products
					if ( ((TaskDescriptor) localElement).getInputProducts().size() == 0)
					{
						taskDescsInfo = taskDescsInfo + "<liste_entreeProduit/>\n" ;
					}

					else
					{
						taskDescsInfo = taskDescsInfo + "<liste_entreeProduit>\n" ;
						productIterator = ((TaskDescriptor) localElement).getInputProducts().iterator() ;
						while (productIterator.hasNext())
						{
							taskDescsInfo = taskDescsInfo + "<entreeProduit>" + productIterator.next().getId() + "</entreeProduit>\n" ;
						}
						taskDescsInfo = taskDescsInfo + "</liste_entreeProduit>\n" ;
					}

					if ( ((TaskDescriptor) localElement).getOutputProducts().size() == 0)
					{
						taskDescsInfo = taskDescsInfo + "<liste_sortieProduit/>\n" ;
					}

					else
					{
						taskDescsInfo = taskDescsInfo + "<liste_sortieProduit>\n" ;
						productIterator = ((TaskDescriptor) localElement).getOutputProducts().iterator() ;
						while (productIterator.hasNext())
						{
							taskDescsInfo = taskDescsInfo + "<sortieProduit>" + productIterator.next().getId() + "</sortieProduit>\n" ;
						}
						taskDescsInfo = taskDescsInfo + "</liste_sortieProduit>\n" ;
					}

					if ( ((TaskDescriptor) localElement).getPresentationElement() == null)
					{
						taskDescsInfo = taskDescsInfo + "<elementPresentationId/>" ;
					}
					else
					{
						taskDescsInfo = taskDescsInfo + "<elementPresentationId>" + ((TaskDescriptor) localElement).getPresentationElement().getId()
								+ "</elementPresentationId>\n" ;
					}

					taskDescsInfo = taskDescsInfo + "</activite>\n" ;
					
					// Task Definitions for this descriptor
					if (((TaskDescriptor)localElement).getTasks().size() > 0)
					{
						taskDefsIds += "<ActiviteTache>\n" ;
						taskDefsIds += "<idActivite>"+ ((TaskDescriptor)localElement).getId() + "</idActivite\n" ;
						taskDefsIds += "<listeIdTache>\n" ;
						
						taskDIterator = ((TaskDescriptor)localElement).getTasks().iterator() ;
						while (taskDIterator.hasNext())
						{							
							localTaskDefinition = taskDIterator.next() ;
							taskDefsInfo += "<eltTache>\n" ;
							taskDefsInfo += "<id>" + localTaskDefinition.getId() + "</id>" ;
							taskDefsInfo += "<nom>" + localTaskDefinition.getName() + "</nom>" ;
							taskDefsInfo += "<tempsPasse>" + (int)localTaskDefinition.getRealData().getDuration() + "</tempsPasse>" ;
							taskDefsInfo += "<dateFinReelle>" + dateFormat.format(localTaskDefinition.getRealData().getFinishDate()) + "</dateFinReelle>" ;
							taskDefsInfo += "</eltTache>\n" ;					
							
							
							
							taskDefsIds += "<id>" + localTaskDefinition.getId() + "</id>" ;
							
							// Artifacts for tasks
							if ( localTaskDefinition.getInputProducts().size() > 0)
							{
								
								artifactsForTaskIn += "<tacheArtefact_Entree>\n" ;
								artifactsForTaskIn += "<idTache>" + localTaskDefinition.getId() + "</idTache>\n" ;
								fakeArtifactIterator = localTaskDefinition.getInputProducts().iterator() ;
								artifactsForTaskIn += "<listeArtefact>\n" ;
								while (fakeArtifactIterator.hasNext())
								{
									artifactsForTaskIn += "<id>" + fakeArtifactIterator.next().getId() + "</id>\n" ;
								}
								artifactsForTaskIn += "</listeArtefact>\n" ;
								artifactsForTaskIn += "</tacheArtefact_Entree>\n" ;
							}
							if ( localTaskDefinition.getOutputProducts().size() > 0)
							{
								
								artifactsForTaskOut += "<tacheArtefact_Sortie>\n" ;
								artifactsForTaskOut += "<idTache>" + localTaskDefinition.getId() + "</idTache>\n" ;
								fakeArtifactIterator = localTaskDefinition.getOutputProducts().iterator() ;
								artifactsForTaskOut += "<listeArtefact>\n" ;
								while (fakeArtifactIterator.hasNext())
								{
									artifactsForTaskOut += "<id>" + fakeArtifactIterator.next().getId() + "</id>\n" ;
								}
								artifactsForTaskOut += "</listeArtefact>\n" ;
								artifactsForTaskOut += "</tacheArtefact_Sortie>\n" ;
							}
						}
						
						taskDefsIds += "</listeIdTache>\n" ;
						taskDefsIds += "</ActiviteTache>\n" ;
					}
					
				} // End taskDescs

				// For interfaces
				else if (localElement instanceof Interface)
				{
					interfacesInfo = interfacesInfo + "<interface>\n" ;
					interfacesInfo = interfacesInfo + "<id>" + ((Interface) localElement).getId() + "</id>\n" ;

					if ( ((Interface) localElement).getRequiredForComponent() == null)
					{
						interfacesInfo = interfacesInfo + "<interfaceRequiseComposant/>" ;
					}
					else
					{
						interfacesInfo = interfacesInfo + "<interfaceRequiseComposant>"
								+ ((Interface) localElement).getRequiredForComponent().getDescriptor().getId() + "</interfaceRequiseComposant>\n" ;
					}

					if ( ((Interface) localElement).getGivenForComponent() == null)
					{
						interfacesInfo = interfacesInfo + "<interfaceFournieComposant/>" ;
					}
					else
					{
						interfacesInfo = interfacesInfo + "<interfaceFournieComposant>"
								+ ((Interface) localElement).getGivenForComponent().getDescriptor().getId() + "</interfaceFournieComposant>\n" ;
					}

					// Products
					if ( ((Interface) localElement).getProducts().size() == 0)
					{
						interfacesInfo = interfacesInfo + "<liste_interfaceProduit/>\n" ;
					}

					else
					{
						interfacesInfo = interfacesInfo + "<liste_interfaceProduit>\n" ;
						productIterator = ((Interface) localElement).getProducts().iterator() ;
						while (productIterator.hasNext())
						{
							interfacesInfo = interfacesInfo + "<interfaceProduit>" + productIterator.next().getId() + "</interfaceProduit>\n" ;
						}
						interfacesInfo = interfacesInfo + "</liste_interfaceProduit>\n" ;
					}

					interfacesInfo = interfacesInfo + "</interface>\n" ;
				} // End interfaces

				// For product types
				else if (localElement instanceof ProductType)
				{
					productTypesInfo = productTypesInfo + "<typeProduit>\n" ;
					productTypesInfo = productTypesInfo + "<id>" + ((ProductType) localElement).getId() + "</id>\n" ;
					productTypesInfo = productTypesInfo + "<nom>" + ((ProductType) localElement).getName() + "</nom>\n" ;
					productTypesInfo = productTypesInfo + "</typeProduit>\n" ;
				} // End product types

				// For states

				// For presentation packages

				// For presentation elements
				else if (localElement instanceof Presentation)
				{
					presentationsInfo = presentationsInfo + "<elementPresentation>\n" ;
					presentationsInfo = presentationsInfo + "<id>" + ((Presentation) localElement).getId() + "</id>\n" ;
					presentationsInfo = presentationsInfo + "<nom>" + ((Presentation) localElement).getName() + "</nom>\n" ;

					// Icon
					if ( ((Presentation) localElement).getIconPath().equals(""))
					{
						presentationsInfo = presentationsInfo + "<cheminIcone/>" ;
					}
					else
					{
						presentationsInfo = presentationsInfo + "<cheminIcone>" + ((Presentation) localElement).getIconPath() + "</cheminIcone>\n" ;
					}

					// Content
					if ( ((Presentation) localElement).getContentPath().equals(""))
					{
						presentationsInfo = presentationsInfo + "<cheminContenu/>" ;
					}
					else
					{
						presentationsInfo = presentationsInfo + "<cheminContenu>" + ((Presentation) localElement).getContentPath() + "</cheminContenu>\n" ;
					}

					// Description
					if ( ((Presentation) localElement).getDescription().equals(""))
					{
						presentationsInfo = presentationsInfo + "<description/>" ;
					}
					else
					{
						presentationsInfo = presentationsInfo + "<description>" + ((Presentation) localElement).getDescription() + "</description>\n" ;
					}

					// Page
					if ( ((Presentation) localElement).getPagePath().equals(""))
					{
						presentationsInfo = presentationsInfo + "<cheminPage/>" ;
					}
					else
					{
						presentationsInfo = presentationsInfo + "<cheminPage>" + ((Presentation) localElement).getPagePath() + "</cheminPage>\n" ;
					}

					// Guides
					if ( ((Presentation) localElement).getGuides().size() == 0)
					{
						presentationsInfo = presentationsInfo + "<liste_guideId/>\n" ;
					}

					else
					{
						presentationsInfo = presentationsInfo + "<liste_guideId>\n" ;
						guideIterator = ((Presentation) localElement).getGuides().iterator() ;
						while (guideIterator.hasNext())
						{
							presentationsInfo = presentationsInfo + "<guideId>" + guideIterator.next().getId() + "</guideId>\n" ;
						}
						presentationsInfo = presentationsInfo + "</liste_guideId>\n" ;
					}

					presentationsInfo = presentationsInfo + "</elementPresentation>\n" ;
				} // End presentation

				// For guides
				else if (localElement instanceof Guide)
				{
					guidesInfo = guidesInfo + "<guide>\n" ;
					guidesInfo = guidesInfo + "<id>" + ((Guide) localElement).getId() + "</id>\n" ;
					guidesInfo = guidesInfo + "<nom>" + ((Guide) localElement).getName() + "</nom>\n" ;
					if ( ((Guide) localElement).getType() == null)
					{
						guidesInfo = guidesInfo + "<typeGuideId/>" ;
					}
					else
					{
						guidesInfo = guidesInfo + "<typeGuideId>" + ((Guide) localElement).getType().getId() + "</typeGuideId>\n" ;
					}

					if ( ((Guide) localElement).getPresentationElement() == null)
					{
						guidesInfo = guidesInfo + "<elementPresentationId/>" ;
					}
					else
					{
						guidesInfo = guidesInfo + "<elementPresentationId>" + ((Guide) localElement).getPresentationElement().getId()
								+ "</elementPresentationId>\n" ;
					}

					guidesInfo = guidesInfo + "</guide>\n" ;
				} // End guides

				// For guide types
				else if (localElement instanceof GuideType)
				{
					guideTypesInfo = guideTypesInfo + "<typeGuide>\n" ;
					guideTypesInfo = guideTypesInfo + "<id>" + ((GuideType) localElement).getId() + "</id>\n" ;
					guideTypesInfo = guideTypesInfo + "<nom>" + ((GuideType) localElement).getName() + "</nom>\n" ;
					guideTypesInfo = guideTypesInfo + "</typeGuide>\n" ;
					
				} // End guideTypes
			}

			// Listing all roles
			if (rolesInfo.equals(""))
			{
				localOSW.write("<liste_role/>\n") ;
			}
			else
			{
				localOSW.write("<liste_role>\n" + rolesInfo + "</liste_role>\n") ;
			}

			// Listing products
			if (productsInfo.equals(""))
			{
				localOSW.write("<liste_produit/>\n") ;
			}
			else
			{
				localOSW.write("<liste_produit>\n" + productsInfo + "</liste_produit>\n") ;
			}

			// Listing work definitions
			if (activitiesInfo.equals(""))
			{
				localOSW.write("<liste_definitionTravail/>\n") ;
			}
			else
			{
				localOSW.write("<liste_definitionTravail>\n" + activitiesInfo + "</liste_definitionTravail>\n") ;
			}

			// Listing tasks descriptors
			if (taskDescsInfo.equals(""))
			{
				localOSW.write("<liste_activite/>\n") ;
			}
			else
			{
				localOSW.write("<liste_activite>\n" + taskDescsInfo + "</liste_activite>\n") ;
			}

			// Listing interfaces
			if (interfacesInfo.equals(""))
			{
				localOSW.write("<liste_interface/>\n") ;
			}
			else
			{
				localOSW.write("<liste_interface>\n" + interfacesInfo + "</liste_interface>\n") ;
			}

			// Listing product types
			if (productTypesInfo.equals(""))
			{
				localOSW.write("<liste_typeProduit/>\n") ;
			}
			else
			{
				localOSW.write("<liste_typeProduit>\n" + productTypesInfo + "</liste_typeProduit>\n") ;
			}

			// Listing states
			localOSW.write("<liste_etat/>\n") ;

			// Listing package presentations
			localOSW.write("<liste_paquetagePresentation/>\n") ;

			// Listing presentations
			if (presentationsInfo.equals(""))
			{
				localOSW.write("<liste_elementPresentation/>\n") ;
			}
			else
			{
				localOSW.write("<liste_elementPresentation>\n" + presentationsInfo + "</liste_elementPresentation>\n") ;
			}

			// Listing guides
			if (guidesInfo.equals(""))
			{
				localOSW.write("<liste_guide/>\n") ;
			}
			else
			{
				localOSW.write("<liste_guide>\n" + guidesInfo + "</liste_guide>\n") ;
			}

			// Listing guide types
			if (guideTypesInfo.equals(""))
			{
				localOSW.write("<liste_typeGuide/>\n") ;
			}
			else
			{
				localOSW.write("<liste_typeGuide>\n" + guideTypesInfo + "</liste_typeGuide>\n") ;
			}

			// Ending process
			localOSW.write("</exportExecution>\n") ;

			/*
			 * Project's objects
			 */
			localOSW.write("<elementProjet>\n") ;
			// General info
			localOSW.write("<projet>\n") ;
			localOSW.write("<id>" + _project.getId() + "</id>\n") ;
			localOSW.write("<nom>" + _project.getName() + "</nom>\n") ;
			if (_project.getDescription().equals(""))
			{
				localOSW.write("<description/>\n") ;
			}
			else
			{
				localOSW.write("<description>" + _project.getDescription() + "</description>\n") ;
			}
			localOSW.write("<dateDebut>" + _project.getStartDate() + "</dateDebut>\n") ;
			localOSW.write("<dateFin>" + _project.getFinishDate() + "</dateFin>\n") ;
			localOSW.write("</projet>\n") ;

			// Members
			if (_project.getResources().size() == 0)
			{
				localOSW.write("<listeMembres/>\n") ;
			}
			else
			{
				resourceIterator = _project.getResources().iterator() ;
				localOSW.write("<listeMembres>\n") ;
				while (resourceIterator.hasNext())
				{
					localTempResource = resourceIterator.next() ;
					localOSW.write("<eltMembre>\n") ;
					localOSW.write("<id>" + localTempResource.getId() + "</id>\n") ;
					localOSW.write("<nom>" + localTempResource.getFullName() + "</nom>\n") ;
					localOSW.write("</eltMembre>\n") ;
				}
				localOSW.write("</listeMembres>\n") ;

			}

			// Iterations : TODO how it works
			localOSW.write("<listeIterations/>\n") ;

			// Tasks
			if (taskDefsInfo.equals(""))
			{
				localOSW.write("<listeTaches/>\n") ;
			}
			else
			{
				localOSW.write("<listeTaches>\n" + taskDefsInfo + "</listeTaches>\n") ;
			}

			// Artefacts :
			if (artifactsInfo.equals(""))
			{
				localOSW.write("<listeArtefacts/>\n") ;
			}
			else
			{
				localOSW.write("<listeArtefacts>\n" + artifactsInfo + "</listeArtefacts>\n") ;
			}

			localOSW.write("</elementProjet>\n") ;

			/*
			 * Links between projects elements
			 */
			localOSW.write("<lienProjet>\n") ;
			// Between iterations and tasks
			localOSW.write("<listeIterationTache/>\n") ;

			// Between members and artifacts
			localOSW.write("<listeMembreArtefact/>\n") ;

			// Between members and tasks
			localOSW.write("<listeMembreTache/>\n") ;

			// Between tasks and artifacts in
			if (artifactsForTaskIn.equals(""))
			{
				localOSW.write("<listeTacheArtefact_Entree/>\n") ;
			}
			else
			{
				localOSW.write("<listeTacheArtefact_Entree>\n" + artifactsForTaskIn + "</listeTacheArtefact_Entree>\n") ;
			}

			// Between tasks and artifacts out
			if (artifactsForTaskOut.equals(""))
			{
				localOSW.write("<listeTacheArtefact_Sortie/>\n") ;
			}
			else
			{
				localOSW.write("<listeTacheArtefact_Sortie>\n" + artifactsForTaskOut + "</listeTacheArtefact_Sortie>\n") ;
			}

			localOSW.write("</lienProjet>\n") ;

			/*
			 * Links between project and process
			 */
			localOSW.write("<lienProjetProcessus>\n") ;
			// Between artifacts and products
			if (artifactsIds.equals(""))
			{
				localOSW.write("<listeProduitArtefact/>\n") ;
			}
			else
			{
				localOSW.write("<listeProduitArtefact>\n" + artifactsIds + "</listeProduitArtefact>\n") ;
			}

			// Between tasks and "tasks" ?
			if (taskDefsIds.equals(""))
			{
				localOSW.write("<listeActiviteTache/>\n") ;
			}
			else
			{
				localOSW.write("<listeActiviteTache>\n" + taskDefsIds + "</listeActiviteTache>\n") ;
			}

			// Between members and roles
			if (_project.getResources().size() == 0)
			{
				localOSW.write("<MembreRole/>\n") ;
			}

			else
			{
				resourceIterator = _project.getResources().iterator() ;
				localOSW.write("<MembreRole>\n") ;
				while (resourceIterator.hasNext())
				{
					localTempResource = resourceIterator.next() ;
					localOSW.write("<listeMembre>\n") ;
					localOSW.write("<Membre>\n") ;
					localOSW.write("<id>" + localTempResource.getId() + "</id>\n") ;

					if (localTempResource.getPerformingRoles().size() == 0)
					{
						localOSW.write("<listeRole/>\n") ;
					}

					else
					{
						roleIterator = localTempResource.getPerformingRoles().iterator() ;
						localOSW.write("<listeRole>\n") ;
						while (roleIterator.hasNext())
						{
							localOSW.write("<id>" + roleIterator.next().getId() + "</id>\n") ;
						}
						localOSW.write("</listeRole>\n") ;

					}
					localOSW.write("</Membre>\n") ;
					localOSW.write("</listeMembre>\n") ;
				}
				localOSW.write("</MembreRole>\n") ;
			}

			localOSW.write("</lienProjetProcessus>\n") ;

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

	public static void exportToOpenWorkbench (Project _project, File _destination) throws FileSaveException
	{
		try
		{
			FileOutputStream localFOS = new FileOutputStream(_destination) ;
			BufferedOutputStream localBOS = new BufferedOutputStream(localFOS) ;

			OutputStreamWriter localOSW = new OutputStreamWriter(localBOS, "ISO-8859-1") ;

			
			
			//date format for open workbench 
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh':'mm':'ss");

			/*
			 * Initialisation
			 */
			BreakdownElement localElement = null ;
			
			Iterator <BreakdownElement> localIterator ;
			
			ArrayList <Component> localComponents = new ArrayList <Component>() ;
			HumanResource localTempResource ;
			TaskDefinition localTempTask;

			// The following variables represents buffers for various elements (for optimisation in
			// loops)
			
			Iterator <TaskDefinition> taskIterator ;
			
			Iterator <HumanResource> resourceIterator ;
			
			// Initializing components
			localIterator = _project.getProcess().getNestedElements().iterator() ;
			while (localIterator.hasNext())
			{
				localElement = localIterator.next() ;
				if (localElement instanceof Component)
				{
					localComponents.add((Component) localElement) ;
				}
			}

			/*
			 * Writing the file
			 */
			localOSW.write("<?xml version=\"1.0\"?>\n") ;
			localOSW.write("<WORKBENCH_PROJECT>\n");
			localOSW.write("<BaseCalendars>\n");
			localOSW.write("<Calendar name=\"Standard\"/>\n");
			localOSW.write("</BaseCalendars>\n");
			
			//Members
			if (_project.getResources().size() == 0)
			{
				localOSW.write("<PoolRessources/>\n") ;
			}
			else
			{
				resourceIterator = _project.getResources().iterator() ;
				localOSW.write("<PoolResources>\n") ;
				while (resourceIterator.hasNext())
				{
					localTempResource = resourceIterator.next() ;
					localOSW.write("<PoolResource resourceType=\"0\" userFlag2=\"false\" userFlag1=\"false\" ") ;
					localOSW.write("fullName=\"" + localTempResource.getFullName() + "\" ") ;
					localOSW.write("isExternal=\"false\" ") ;
					localOSW.write("isActive=\"true\" ") ;
					localOSW.write("resourceId=\"" + localTempResource.getId() + "\" ") ;
					localOSW.write("employmentType=\"\" ") ;
					localOSW.write("openForTimeEntry=\"false\" ") ;
					localOSW.write("isRole=\"false\" ") ;
					localOSW.write("trackMode=\"2\">") ;
					localOSW.write("</PoolResource>\n") ;
				}
				localOSW.write("</PoolResources>\n") ;
			}
			
			localOSW.write("<Projects>\n") ;
			localOSW.write("<Project UID=\"" + _project.getId() + " \" ") ;
			localOSW.write("closed=\"false\" ") ;
			localOSW.write("active=\"true\" ") ;
			localOSW.write("approved=\"false\" ") ;
			localOSW.write("start=\"" + dateFormat.format(_project.getStartDate()) + " \" ") ;
			localOSW.write("openForTimeEntry=\"true\" ") ;
			localOSW.write("format=\"0\" ") ;
			localOSW.write("trackMode=\"0\" ") ;
			localOSW.write("departement=\"\" ") ;
			localOSW.write("description=\"" + _project.getDescription() + " \" ") ;
			localOSW.write("finish=\"" + dateFormat.format(_project.getFinishDate()) + " \" ") ;
			localOSW.write("priority=\"10\" ") ;
			localOSW.write("finishImposed=\"true\" ") ;
			localOSW.write("budget=\"\" ") ;
			localOSW.write("cpmType=\"0\" ") ;
			localOSW.write("name=\"" + _project.getName() + " \" ") ;
			localOSW.write("projectID=\"" + _project.getId() + " \" ") ;
			localOSW.write("startImposed=\"false\" ") ;
			localOSW.write("programme=\"false\">\n") ;
			
			localOSW.write("<Resources>\n") ;
			//Link between Menbers and the project
			resourceIterator = _project.getResources().iterator() ;
			while (resourceIterator.hasNext())
			{
				localTempResource = resourceIterator.next() ;
				localOSW.write("<Resource requestStatus=\"1\" ") ;
				localOSW.write("resourceID=\"" + localTempResource.getId() + "\" ") ;
				localOSW.write("openForTimeEntry=\"true\" ") ;
				localOSW.write("bookingStatus=\"5\">\n") ;
				//Curve name for open workbench
				localOSW.write("<Curve name=\"allocation\" type=\"1\" default=\"1.0\" />\n") ;
				localOSW.write("<Curve name=\"rate\" type=\"1\" default=\"2.777777777777778E-4\" />\n") ;
				localOSW.write("</Resource>\n") ;
			}
			
			
			localOSW.write("</Resources>\n") ;
			
			//Link between Tasks and the project
			//Tasks : in and out
			resourceIterator = _project.getResources().iterator() ;
			while (resourceIterator.hasNext())
			{
				localTempResource = resourceIterator.next() ;
				taskIterator = (localTempResource).getPerformingTasks().iterator();
				while (taskIterator.hasNext())
				{
					localTempTask = taskIterator.next();
					localOSW.write("<Task ") ;
					localOSW.write("start=\"" + dateFormat.format(localTempTask.getPlanningData().getStartDate()) + "\" ") ;
					localOSW.write("proxy=\"false\" ") ;
					localOSW.write("critical=\"false\" ") ;
					localOSW.write("status=\"0\" ") ;
					localOSW.write("outlineLevel=\"1\" ") ;
					localOSW.write("finish=\"" + dateFormat.format(localTempTask.getPlanningData().getFinishDate()) + "\" ") ;
					localOSW.write("summary=\"false\" ") ;
					localOSW.write("milestone=\"false\" ") ;
					localOSW.write("name=\"" + localTempTask.getName() + "\" ") ;
					localOSW.write("taskID=\"" + localTempTask.getId() + "\" ") ;
					localOSW.write("fixed=\"false\" ") ;
					localOSW.write("locked=\"false\" ") ;
					localOSW.write("key=\"false\" ") ;
					localOSW.write("percComp=\"0.0\" ") ;
					localOSW.write("unplanned=\"false\">\n") ;
					
					localOSW.write("<Assignments>\n") ;
					localOSW.write("<Assignment status=\"0\" ") ;
					localOSW.write("resourceID=\"" + localTempResource.getId() + "\" ") ;
					localOSW.write("unplanned=\"false\" ") ;
					localOSW.write("estPattern=\"3\" ") ;
					localOSW.write("estMax=\"1.0\"/>\n") ;
					localOSW.write("</Assignments>\n") ;
					
					localOSW.write("</Task>") ;
				}
			}
			
			localOSW.write("</Project>\n") ;
			localOSW.write("</Projects>\n") ;
			localOSW.write("</WORKBENCH_PROJECT>\n");
			
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
