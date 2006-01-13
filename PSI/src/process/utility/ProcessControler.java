
package process.utility ;

import java.io.BufferedInputStream ;
import java.io.File ;
import java.io.FileInputStream ;
import java.io.FileNotFoundException ;
import java.io.IOException ;

import javax.xml.parsers.DocumentBuilder ;
import javax.xml.parsers.DocumentBuilderFactory ;
import javax.xml.parsers.ParserConfigurationException ;

import org.w3c.dom.Document ;
import org.w3c.dom.Node ;
import org.w3c.dom.NodeList ;
import org.xml.sax.SAXException ;
import org.xml.sax.SAXParseException ;

import model.spem2.DeliveryProcess ;
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
				 * General process information variables
				 */
				String localID = "" ;
				String localName = "" ;
				String localDescription = "" ;
				String localAuthorName = "" ;
				String localAuthorMail = "" ;
				
				NodeList localProcessNodeList = localDocument.getElementsByTagName("processus") ;
				int localChildMax = localProcessNodeList.getLength() ;
				if (localChildMax != 1) { throw new FileParseException() ; }
				
				NodeList localPAttribList = localProcessNodeList.item(0).getChildNodes() ;
				localChildMax = localPAttribList.getLength() ;
				System.out.println(localDocument) ;
				for(int i = 0 ; i < localChildMax ; i++)
				{//System.out.println(localPAttribList.item(i).getNodeName()) ;
					if (localPAttribList.item(i).getNodeType() == Node.ELEMENT_NODE
							&& localPAttribList.item(i).getNodeName().equalsIgnoreCase("id"))
					{
						//localID = localPAttribList.item(i).getNodeValue() ;
						//System.out.println(localID) ;
					}
				}

				return new DeliveryProcess(localID, localName, localDescription, localAuthorName, localAuthorMail) ;
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
