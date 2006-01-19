
package process.utility ;

import java.io.BufferedInputStream ;
import java.io.File ;
import java.io.FileInputStream ;
import java.io.FileNotFoundException ;
import java.io.IOException ;
import java.util.ArrayList ;

import javax.xml.parsers.DocumentBuilder ;
import javax.xml.parsers.DocumentBuilderFactory ;
import javax.xml.parsers.ParserConfigurationException ;

import org.w3c.dom.Document ;
import org.w3c.dom.Node ;
import org.w3c.dom.NodeList ;
import org.xml.sax.SAXException ;
import org.xml.sax.SAXParseException ;

import model.Component ;
import model.spem2.Activity ;
import model.spem2.BreakdownElement ;
import model.spem2.DeliveryProcess ;
import model.spem2.RoleDescriptor ;
import model.spem2.TaskDescriptor ;
import process.exception.FileParseException ;

/**
 * ProcessControler : Loads process information, it reads the file generated by IEPP
 * 
 * @author Cond� Micka�l K.
 * @version 1.0
 * 
 */
public class ProcessControler
{
	/**
	 * Loads process data from a file
	 * 
	 * @author Cond� Mickael K.
	 * @version 1.0
	 * 
	 * @param _source
	 *            the file from which read data
	 * @return
	 * @throws FileParseException
	 *             if the file format is incorrect
	 */
	public static DeliveryProcess load (File _source) throws FileParseException
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
				if (!localDocument.getDocumentElement().getTagName().equalsIgnoreCase("exportExecution")) { throw new FileParseException() ; }

				/*
				 * NOTES : General process information variables and temporary variables Other
				 * variables which are more specific are declared at the begining of each parsing
				 * section
				 */
				// Main variables
				String localID = "" ;
				String localName = "" ;
				String localDescription = "" ;
				String localAuthorName = "" ;
				String localAuthorMail = "" ;
				String localInterfaceDiagramPath = "" ;
				String localFlowDiagramPath ;
				String localActivitiesDiagramPath ;

				// Work breakdowns elements
				ArrayList <BreakdownElement> localNested = new ArrayList <BreakdownElement>() ;

				// Temporary variables for loops
				int localChildCount ;
				String localParentId ;
				NodeList localChildList ;

				/*
				 * Parsing process information
				 */
				NodeList localProcessNodeList = localDocument.getElementsByTagName("processus") ;
				int localChildMax = localProcessNodeList.getLength() ;
				if (localChildMax != 1) { throw new FileParseException() ; }

				NodeList localPAttribList = localProcessNodeList.item(0).getChildNodes() ;
				localChildMax = localPAttribList.getLength() ; // ! new meaning

				for (int i = 0; i < localChildMax; i++ )
				{
					/*
					 * Getting the ID of the process
					 */
					if (localPAttribList.item(i).getNodeType() == Node.ELEMENT_NODE && localPAttribList.item(i).getNodeName().equalsIgnoreCase("id"))
					{
						try
						{
							localID = localPAttribList.item(i).getFirstChild().getNodeValue() ;
						}
						catch (NullPointerException exc)
						{
							localID = "[N/A]" ;
						}
					}

					/*
					 * Getting the name of the process
					 */
					if (localPAttribList.item(i).getNodeType() == Node.ELEMENT_NODE && localPAttribList.item(i).getNodeName().equalsIgnoreCase("nom"))
					{
						try
						{
							localName = localPAttribList.item(i).getFirstChild().getNodeValue() ;
						}
						catch (NullPointerException exc)
						{
							localName = "[N/A]" ;
						}
					}

					/*
					 * Getting the description of the process
					 */
					if (localPAttribList.item(i).getNodeType() == Node.ELEMENT_NODE && localPAttribList.item(i).getNodeName().equalsIgnoreCase("description"))
					{
						try
						{
							localDescription = localPAttribList.item(i).getFirstChild().getNodeValue() ;
						}
						catch (NullPointerException exc)
						{
							localDescription = "[N/A]" ;
						}
					}

					/*
					 * Getting the author's name
					 */
					if (localPAttribList.item(i).getNodeType() == Node.ELEMENT_NODE && localPAttribList.item(i).getNodeName().equalsIgnoreCase("nomAuteur"))
					{
						try
						{
							localAuthorName = localPAttribList.item(i).getFirstChild().getNodeValue() ;
						}
						catch (NullPointerException exc)
						{
							localAuthorName = "[N/A]" ;
						}
					}

					/*
					 * Getting the description of the process
					 */
					if (localPAttribList.item(i).getNodeType() == Node.ELEMENT_NODE && localPAttribList.item(i).getNodeName().equalsIgnoreCase("emailAuteur"))
					{
						try
						{
							localDescription = localPAttribList.item(i).getFirstChild().getNodeValue() ;
						}
						catch (NullPointerException exc)
						{
							localAuthorMail = "[N/A]" ;
						}
					}
				}

				DeliveryProcess localProcess = new DeliveryProcess(localID, localName, localDescription, localAuthorName, localAuthorMail) ;

				/*
				 * Tasks (TaskDescriptor)
				 */
				NodeList localTasksListRoot = localDocument.getElementsByTagName("liste_activite") ;
				if (localTasksListRoot.getLength() != 1 || localTasksListRoot.item(0).getChildNodes().getLength() == 0) { throw new FileParseException() ; }
				NodeList localTasksList = localTasksListRoot.item(0).getChildNodes() ;
				Node localTask ;

				localChildMax = localTasksList.getLength() ;
				for (int i = 0; i < localChildMax; i++ )
				{
					if (localTasksList.item(i).getNodeType() == Node.ELEMENT_NODE && localTasksList.item(i).getNodeName().equalsIgnoreCase("activite"))
					{
						localTask = localTasksList.item(i) ;
						localChildList = localTask.getChildNodes() ;
						localChildCount = localChildList.getLength() ;
						localName = "" ;
						localID = "" ;
						localDescription = "" ;
						localParentId = "" ;

						for (int j = 0; j < localChildCount; j++ )
						{
							/*
							 * The identifier
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE && localChildList.item(j).getNodeName().equalsIgnoreCase("id"))
							{
								try
								{
									localID = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localID = "[N/A]" ;
								}
							}

							/*
							 * The name
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE && localChildList.item(j).getNodeName().equalsIgnoreCase("nom"))
							{
								try
								{
									localName = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localName = "[N/A]" ;
								}
							}

							/*
							 * The parent component
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE
									&& localChildList.item(j).getNodeName().equalsIgnoreCase("agregatDefinitionTravail"))
							{
								try
								{
									localParentId = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localParentId = "[N/A]" ;
								}
							}

						} // End For j

						localNested.add(new TaskDescriptor(localID, localName, localDescription, localParentId)) ;

					} // End If
				} // End for i

				/*
				 * Work definitions (activities) using task descriptors
				 */
				// Temp variables
				NodeList localTasksIdentifiers ;
				Node localActivity ;
				ArrayList <String> localTasksCollection = new ArrayList <String>() ;
				Activity localTempActivity ;

				// Parsing
				NodeList localActivitiesListRoot = localDocument.getElementsByTagName("liste_definitionTravail") ;
				if (localActivitiesListRoot.getLength() != 1 || localActivitiesListRoot.item(0).getChildNodes().getLength() == 0) { throw new FileParseException() ; }
				NodeList localActivitiesList = localActivitiesListRoot.item(0).getChildNodes() ;

				localChildMax = localActivitiesList.getLength() ;
				for (int i = 0; i < localChildMax; i++ )
				{
					if (localActivitiesList.item(i).getNodeType() == Node.ELEMENT_NODE
							&& localActivitiesList.item(i).getNodeName().equalsIgnoreCase("definitionTravail"))
					{
						localActivity = localActivitiesList.item(i) ;
						localChildList = localActivity.getChildNodes() ;
						localChildCount = localChildList.getLength() ;
						localName = "" ;
						localID = "" ;
						localDescription = "" ;
						localParentId = "" ;
						localFlowDiagramPath = "" ;
						localActivitiesDiagramPath = "" ;
						localTasksCollection.clear() ;

						for (int j = 0; j < localChildCount; j++ )
						{
							/*
							 * The identifier
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE && localChildList.item(j).getNodeName().equalsIgnoreCase("id"))
							{
								try
								{
									localID = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localID = "[N/A]" ;
								}
							}

							/*
							 * The name
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE && localChildList.item(j).getNodeName().equalsIgnoreCase("nom"))
							{
								try
								{
									localName = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localName = "[N/A]" ;
								}
							}

							/*
							 * The parent component
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE
									&& localChildList.item(j).getNodeName().equalsIgnoreCase("agregatComposant"))
							{
								try
								{
									localParentId = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localParentId = "[N/A]" ;
								}
							}

							/*
							 * Flow diagram
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE
									&& localChildList.item(j).getNodeName().equalsIgnoreCase("cheminDiagrammeFlots"))
							{
								try
								{
									localFlowDiagramPath = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localFlowDiagramPath = "[N/A]" ;
								}
							}

							/*
							 * Activities diagram
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE
									&& localChildList.item(j).getNodeName().equalsIgnoreCase("cheminDiagrammeActivites"))
							{
								try
								{
									localActivitiesDiagramPath = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localActivitiesDiagramPath = "[N/A]" ;
								}
							}

							/*
							 * Nested activities
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE
									&& localChildList.item(j).getNodeName().equalsIgnoreCase("liste_activiteId"))
							{
								try
								{
									localTasksIdentifiers = localChildList.item(j).getChildNodes() ;

									for (int k = 0; k < localTasksIdentifiers.getLength(); k++ )
									{
										if (localTasksIdentifiers.item(k).getNodeType() == Node.ELEMENT_NODE
												&& localTasksIdentifiers.item(k).getNodeName().equalsIgnoreCase("activiteId"))
										{
											localTasksCollection.add(localTasksIdentifiers.item(k).getFirstChild().getNodeValue()) ;
										}
									}
								}
								catch (NullPointerException exc)
								{
								}
							}

						} // End For j

						localTempActivity = new Activity(localID, localName, localDescription, localParentId, "", localFlowDiagramPath,
								localActivitiesDiagramPath) ;

						// Replacing the IDs by objects
						for (int j = 0; j < localNested.size(); j++ )
						{
							if (localNested.get(j) instanceof TaskDescriptor)
							{
								for (int k = 0; k < localTasksCollection.size(); k++ )
								{
									if ( ((TaskDescriptor) localNested.get(j)).getParentId().equals(localID))
									{
										if (!localTempActivity.getNestedElements().contains(localNested.get(j)))
										{
											localTempActivity.getNestedElements().add(localNested.get(j)) ;
										}
									}
								}
							}
						}

						localNested.add(localTempActivity) ;

					} // End If
				} // End for i

				/*
				 * Products (may be implemented in the future)
				 */

				/*
				 * Roles (RoleDescriptor)
				 */
				// Temp variables
				RoleDescriptor localTempRole ;

				// Parsing
				NodeList localRolesListRoot = localDocument.getElementsByTagName("liste_role") ;
				if (localRolesListRoot.getLength() != 1 || localRolesListRoot.item(0).getChildNodes().getLength() == 0) { throw new FileParseException() ; }
				NodeList localRolesList = localRolesListRoot.item(0).getChildNodes() ;
				Node localRole ;

				localChildMax = localRolesList.getLength() ;
				for (int i = 0; i < localChildMax; i++ )
				{
					if (localRolesList.item(i).getNodeType() == Node.ELEMENT_NODE && localRolesList.item(i).getNodeName().equalsIgnoreCase("role"))
					{
						localRole = localRolesList.item(i) ;
						localChildList = localRole.getChildNodes() ;
						localChildCount = localChildList.getLength() ;
						localName = "" ;
						localID = "" ;
						localDescription = "" ;
						localParentId = "" ;
						localTasksCollection.clear() ;

						for (int j = 0; j < localChildCount; j++ )
						{
							/*
							 * The identifier
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE && localChildList.item(j).getNodeName().equalsIgnoreCase("id"))
							{
								try
								{
									localID = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localID = "[N/A]" ;
								}
							}

							/*
							 * The name
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE && localChildList.item(j).getNodeName().equalsIgnoreCase("nom"))
							{
								try
								{
									localName = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localName = "[N/A]" ;
								}
							}

							/*
							 * The parent component
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE
									&& localChildList.item(j).getNodeName().equalsIgnoreCase("agregatComposant"))
							{
								try
								{
									localParentId = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localParentId = "[N/A]" ;
								}
							}

							/*
							 * Performed activities
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE
									&& localChildList.item(j).getNodeName().equalsIgnoreCase("liste_participationActivite"))
							{
								try
								{
									localTasksIdentifiers = localChildList.item(j).getChildNodes() ;

									for (int k = 0; k < localTasksIdentifiers.getLength(); k++ )
									{
										if (localTasksIdentifiers.item(k).getNodeType() == Node.ELEMENT_NODE
												&& localTasksIdentifiers.item(k).getNodeName().equalsIgnoreCase("participationActivite"))
										{
											localTasksCollection.add(localTasksIdentifiers.item(k).getFirstChild().getNodeValue()) ;
										}
									}
								}
								catch (NullPointerException exc)
								{
								}
							}

						} // End For j

						localTempRole = new RoleDescriptor(localID, localName, localDescription, localParentId) ;

						// Links between roles and activities
						for (int j = 0; j < localNested.size(); j++ )
						{
							if (localNested.get(j) instanceof TaskDescriptor)
							{
								for (int k = 0; k < localTasksCollection.size(); k++ )
								{
									if (localTasksCollection.contains( ((TaskDescriptor) localNested.get(j)).getId())
											&& !localTempRole.getPrimaryTasks().contains(localTasksCollection.get(k)))
									{
										// Adding tasks into roles
										localTempRole.getPrimaryTasks().add((TaskDescriptor) localNested.get(j)) ;
										// And then adding role link into task
										((TaskDescriptor)localNested.get(j)).getPrimaryPerformers().add(localTempRole) ;
									}
								}
							}
						}

						localNested.add(localTempRole) ;

					} // End If

				} // End for i

				/*
				 * The goddamn freaking components
				 */
				// Temp variables
				String localVersion ;
				String localDate ;
				String localGenOrder ;
				Component localTempComponent ;
				Node localComponent ;
				NodeList localRolesIdentifiers ;
				NodeList localActivitiesIdentifiers ;
				// NodeList localWorkIdentifiers ;
				ArrayList <String> localResponsabilityDiagramPaths ;
				// Identifiers
				ArrayList <String> localRolesCollection = new ArrayList <String>() ;
				ArrayList <String> localActivitiesCollection = new ArrayList <String>() ;

				// Parsing
				NodeList localComponentsListRoot = localDocument.getElementsByTagName("liste_composant") ;
				if (localComponentsListRoot.getLength() != 1 || localComponentsListRoot.item(0).getChildNodes().getLength() == 0) { throw new FileParseException() ; }
				NodeList localComponentsList = localComponentsListRoot.item(0).getChildNodes() ;

				localChildMax = localComponentsList.getLength() ;
				for (int i = 0; i < localChildMax; i++ )
				{
					if (localComponentsList.item(i).getNodeType() == Node.ELEMENT_NODE
							&& localComponentsList.item(i).getNodeName().equalsIgnoreCase("composant"))
					{
						localComponent = localComponentsList.item(i) ;
						localChildList = localComponent.getChildNodes() ;
						localChildCount = localChildList.getLength() ;
						localName = "" ;
						localID = "" ;
						localDescription = "" ;
						localVersion = "" ;
						localDate = "" ;
						localInterfaceDiagramPath = "" ;
						localFlowDiagramPath = "" ;
						localGenOrder = "" ;
						localResponsabilityDiagramPaths = null ;
						localRolesCollection.clear() ;
						localActivitiesCollection.clear() ;

						for (int j = 0; j < localChildCount; j++ )
						{
							/*
							 * The identifier
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE && localChildList.item(j).getNodeName().equalsIgnoreCase("id"))
							{
								try
								{
									localID = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localID = "[N/A]" ;
								}
							}

							/*
							 * The name
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE && localChildList.item(j).getNodeName().equalsIgnoreCase("nom"))
							{
								try
								{
									localName = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localName = "[N/A]" ;
								}
							}

							/*
							 * The description
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE
									&& localChildList.item(j).getNodeName().equalsIgnoreCase("description"))
							{
								try
								{
									localDescription = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localDescription = "[N/A]" ;
								}
							}

							/*
							 * The author's name
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE && localChildList.item(j).getNodeName().equalsIgnoreCase("nomAuteur"))
							{
								try
								{
									localAuthorName = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localAuthorName = "[N/A]" ;
								}
							}

							/*
							 * The author's mail
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE
									&& localChildList.item(j).getNodeName().equalsIgnoreCase("emailAuteur"))
							{
								try
								{
									localAuthorMail = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localAuthorMail = "[N/A]" ;
								}
							}

							/*
							 * The version
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE && localChildList.item(j).getNodeName().equalsIgnoreCase("version"))
							{
								try
								{
									localVersion = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localVersion = "[N/A]" ;
								}
							}

							/*
							 * The date of publication
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE
									&& localChildList.item(j).getNodeName().equalsIgnoreCase("dateExport"))
							{
								try
								{
									localDate = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localDate = "[N/A]" ;
								}
							}

							/*
							 * The interfaces diagram path
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE
									&& localChildList.item(j).getNodeName().equalsIgnoreCase("cheminDiagrammeInterfaces"))
							{
								try
								{
									localInterfaceDiagramPath = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localInterfaceDiagramPath = "[N/A]" ;
								}
							}

							/*
							 * The flow diagram path
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE
									&& localChildList.item(j).getNodeName().equalsIgnoreCase("cheminDiagrammeFlots"))
							{
								try
								{
									localFlowDiagramPath = localChildList.item(j).getFirstChild().getNodeValue() ;
								}
								catch (NullPointerException exc)
								{
									localFlowDiagramPath = "[N/A]" ;
								}
							}

							/*
							 * The responsability diagram paths TODO
							 */
							/*
							 * if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE &&
							 * localChildList.item(j).getNodeName().equalsIgnoreCase("version")) { }
							 */

							/*
							 * The roles
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE
									&& localChildList.item(j).getNodeName().equalsIgnoreCase("liste_roleId"))
							{
								try
								{
									localRolesIdentifiers = localChildList.item(j).getChildNodes() ;

									for (int k = 0; k < localRolesIdentifiers.getLength(); k++ )
									{
										if (localRolesIdentifiers.item(k).getNodeType() == Node.ELEMENT_NODE
												&& localRolesIdentifiers.item(k).getNodeName().equalsIgnoreCase("roleId"))
										{
											localRolesCollection.add(localRolesIdentifiers.item(k).getFirstChild().getNodeValue()) ;
										}
									}
								}
								catch (NullPointerException exc)
								{
								}
							}

							/*
							 * The products
							 */

							/*
							 * The work definitions
							 */
							if (localChildList.item(j).getNodeType() == Node.ELEMENT_NODE
									&& localChildList.item(j).getNodeName().equalsIgnoreCase("liste_definitionTravailId"))
							{
								try
								{
									localActivitiesIdentifiers = localChildList.item(j).getChildNodes() ;

									for (int k = 0; k < localActivitiesIdentifiers.getLength(); k++ )
									{
										if (localActivitiesIdentifiers.item(k).getNodeType() == Node.ELEMENT_NODE
												&& localActivitiesIdentifiers.item(k).getNodeName().equalsIgnoreCase("definitionTravailId"))
										{
											localActivitiesCollection.add(localActivitiesIdentifiers.item(k).getFirstChild().getNodeValue()) ;
										}
									}
								}
								catch (NullPointerException exc)
								{
								}
							}

						} // End For j

						localTempComponent = new Component(localID, localName, localDescription, "", localInterfaceDiagramPath, localFlowDiagramPath, "",
								localVersion, localDate, localAuthorName, localAuthorMail, localGenOrder, localResponsabilityDiagramPaths) ;

						// Replacing the IDs by objects
						for (int j = 0; j < localNested.size(); j++ )
						{
							if (localNested.get(j) instanceof RoleDescriptor)
							{
								for (int k = 0; k < localRolesCollection.size(); k++ )
								{
									if ( ((RoleDescriptor) localNested.get(j)).getParentId().equals(localID))
									{
										if (!localTempComponent.getNestedElements().contains(localNested.get(j)))
										{
											localTempComponent.getNestedElements().add(localNested.get(j)) ;
										}
									}
								}
							}

							else if (localNested.get(j) instanceof Activity)
							{
								for (int k = 0; k < localRolesCollection.size(); k++ )
								{
									if ( ((Activity) localNested.get(j)).getDescriptor().getParentId().equals(localID))
									{
										if (!localTempComponent.getNestedElements().contains(localNested.get(j)))
										{
											localTempComponent.getNestedElements().add(localNested.get(j)) ;
										}
									}
								}
							}
						}

						localNested.add(localTempComponent) ;

					} // End If
				} // End for i

				localProcess.setNestedElements(localNested) ;
				return localProcess ;
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
}
