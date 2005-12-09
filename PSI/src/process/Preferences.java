
package process ;

import java.io.BufferedInputStream ;
import java.io.BufferedOutputStream ;
import java.io.FileInputStream ;
import java.io.FileNotFoundException ;
import java.io.FileOutputStream ;
import java.io.IOException ;
import java.io.OutputStreamWriter ;
import java.io.UnsupportedEncodingException ;

import javax.xml.parsers.DocumentBuilder ;
import javax.xml.parsers.DocumentBuilderFactory ;
import javax.xml.parsers.ParserConfigurationException ;

import org.w3c.dom.Document ;
import org.w3c.dom.Node ;
import org.xml.sax.SAXException ;
import org.xml.sax.SAXParseException ;

/**
 * Preferences : A set of properties describing the current window behaviour. Implements Singleton
 * Pattern. TODO more sexy exception handling and sizing of the window and cut printlns => throw
 * them to ui classes
 * 
 * @author Condé Mickael K.
 * @version 1.0
 * 
 */
public class Preferences
{
	/**
	 * The width of the window
	 */
	private int width = 500 ;

	/**
	 * The height of the window
	 */
	private int height = 500 ;

	/**
	 * The position on the x axis of the window
	 */
	private int xPosition = 0 ;

	/**
	 * The position on the y axis of the window
	 */
	private int yPosition = 0 ;

	/**
	 * The width of the left tree component
	 */
	private int treeWidth = 100 ;

	/**
	 * The height of the log component
	 */
	private int logHeight = 60 ;

	/**
	 * Show the left tree (project) or not
	 */
	private boolean treeVisible = true ;

	/**
	 * Show the log component or not
	 */
	private boolean logVisible = true ;

	/**
	 * The locale to use for internationalisation
	 */
	private String locale = "FR" ;

	/**
	 * The path to the last project file used
	 */
	private String lastProject = "" ;

	/**
	 * The name of the file containing user preferences
	 */
	private final String PREFERENCES_FILE = "psiprefs.xml" ;

	/**
	 * Unique instance of this class since Singleton is implemented
	 */
	private static Preferences instance = null ;

	/**
	 * Constructor
	 * 
	 */
	private Preferences ()
	{
		super() ;

		/*
		 * Opening and parsing psiprefs.xml file
		 */
		try
		{
			FileInputStream localFIS = new FileInputStream(PREFERENCES_FILE) ;
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
				 * Now the parsing can start
				 */
				Document localDocument = localDB.parse(localBIS) ;				
				if (localDocument.getDocumentElement().getTagName().equals("psiPreferences"))
				{
					Node localNode ;
					Node localDataNode ;
					// TODO Check int and boolean values
					/*
					 * Width
					 */
					if ((localNode = localDocument.getElementsByTagName("width").item(0)) != null)
					{
						if ((localDataNode = localNode.getFirstChild()) != null)
						{
							this.width = Integer.parseInt(localDataNode.getNodeValue()) ;
						}
					}
					
					/*
					 * Height
					 */
					if ((localNode = localDocument.getElementsByTagName("height").item(0)) != null)
					{
						if ((localDataNode = localNode.getFirstChild()) != null)
						{
							this.height = Integer.parseInt(localDataNode.getNodeValue()) ;
						}
					}
					
					/*
					 * X Position
					 */
					if ((localNode = localDocument.getElementsByTagName("xPosition").item(0)) != null)
					{
						if ((localDataNode = localNode.getFirstChild()) != null)
						{
							this.xPosition = Integer.parseInt(localDataNode.getNodeValue()) ;
						}
					}
					
					/*
					 * Y Position
					 */
					if ((localNode = localDocument.getElementsByTagName("yPosition").item(0)) != null)
					{
						if ((localDataNode = localNode.getFirstChild()) != null)
						{
							this.yPosition = Integer.parseInt(localDataNode.getNodeValue()) ;
						}
					}
					
					/*
					 * Tree width
					 */
					if ((localNode = localDocument.getElementsByTagName("treeWidth").item(0)) != null)
					{
						if ((localDataNode = localNode.getFirstChild()) != null)
						{
							this.treeWidth = Integer.parseInt(localDataNode.getNodeValue()) ;
						}
					}
					
					/*
					 * Log height
					 */
					if ((localNode = localDocument.getElementsByTagName("logHeight").item(0)) != null)
					{
						if ((localDataNode = localNode.getFirstChild()) != null)
						{
							this.logHeight = Integer.parseInt(localDataNode.getNodeValue()) ;
						}
					}
					
					/*
					 * Is tree visible
					 */
					if ((localNode = localDocument.getElementsByTagName("treeVisible").item(0)) != null)
					{
						if ((localDataNode = localNode.getFirstChild()) != null)
						{
							this.treeVisible = Boolean.parseBoolean(localDataNode.getNodeValue()) ;
						}
					}
					
					/*
					 * Is log visible
					 */
					if ((localNode = localDocument.getElementsByTagName("logVisible").item(0)) != null)
					{
						if ((localDataNode = localNode.getFirstChild()) != null)
						{
							this.logVisible = Boolean.parseBoolean(localDataNode.getNodeValue()) ;
						}
					}
					
					/*
					 * Locale
					 */
					if ((localNode = localDocument.getElementsByTagName("locale").item(0)) != null)
					{
						if ((localDataNode = localNode.getFirstChild()) != null)
						{
							this.locale = localDataNode.getNodeValue() ;
						}
					}
					
					/*
					 * Last project
					 */
					if ((localNode = localDocument.getElementsByTagName("lastProject").item(0)) != null)
					{
						if ((localDataNode = localNode.getFirstChild()) != null)
						{
							this.lastProject = localDataNode.getNodeValue() ;
						}
					}
				}
			}
			catch (ParserConfigurationException eDBF)
			{

			}
			catch (SAXException eDB)
			{

			}
			catch (IOException eDB)
			{

			}
		}
		catch (FileNotFoundException eIS)
		{

		}
	}

	/**
	 * Saves current settings into the prefs file
	 * 
	 * @author Condé Mickael K.
	 * @version 1.0
	 * 
	 */
	public void save ()
	{
		try
		{
			FileOutputStream localFOS = new FileOutputStream(PREFERENCES_FILE) ;
			BufferedOutputStream localBOS = new BufferedOutputStream(localFOS) ;
			OutputStreamWriter localOSW = new OutputStreamWriter(localBOS, "ISO-8859-1") ;

			localOSW.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n\n") ;
			localOSW.write("<psiPreferences>\n") ;
			localOSW.write("\t<width>" + width + "</width>\n") ;
			localOSW.write("\t<height>" + height + "</height>\n") ;
			localOSW.write("\t<xPosition>" + xPosition + "</xPosition>\n") ;
			localOSW.write("\t<yPosition>" + yPosition + "</yPosition>\n") ;
			localOSW.write("\t<treeWidth>" + treeWidth + "</treeWidth>\n") ;
			localOSW.write("\t<logHeight>" + logHeight + "</logHeight>\n") ;
			localOSW.write("\t<treeVisible>" + treeVisible + "</treeVisible>\n") ;
			localOSW.write("\t<logVisible>" + logVisible + "</logVisible>\n") ;
			localOSW.write("\t<locale>" + locale + "</locale>\n") ;
			localOSW.write("\t<lastProject>" + lastProject + "</lastProject>\n") ;
			localOSW.write("</psiPreferences>\n") ;

			localOSW.flush() ;
			localOSW.close() ;
		}
		catch (FileNotFoundException eOS)
		{
			eOS.printStackTrace() ;
		}
		catch (UnsupportedEncodingException eOSW)
		{
			eOSW.printStackTrace() ;
		}
		catch (IOException eOSW)
		{
			eOSW.printStackTrace() ;
		}
	}

	/**
	 * Getter
	 * 
	 * @return Returns the height.
	 */
	public int getHeight ()
	{
		return this.height ;
	}

	/**
	 * Setter
	 * 
	 * @param _height
	 *            The height to set.
	 */
	public void setHeight (int _height)
	{
		this.height = _height ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the lastProject.
	 */
	public String getLastProject ()
	{
		return this.lastProject ;
	}

	/**
	 * Setter
	 * 
	 * @param _lastProject
	 *            The lastProject to set.
	 */
	public void setLastProject (String _lastProject)
	{
		this.lastProject = _lastProject ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the leftTreeWidth.
	 */
	public int getTreeWidth ()
	{
		return this.treeWidth ;
	}

	/**
	 * Setter
	 * 
	 * @param _leftTreeWidth
	 *            The leftTreeWidth to set.
	 */
	public void setTreeWidth (int _leftTreeWidth)
	{
		this.treeWidth = _leftTreeWidth ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the locale.
	 */
	public String getLocale ()
	{
		return this.locale ;
	}

	/**
	 * Setter
	 * 
	 * @param _locale
	 *            The locale to set.
	 */
	public void setLocale (String _locale)
	{
		this.locale = _locale ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the logHeight.
	 */
	public int getLogHeight ()
	{
		return this.logHeight ;
	}

	/**
	 * Setter
	 * 
	 * @param _logHeight
	 *            The logHeight to set.
	 */
	public void setLogHeight (int _logHeight)
	{
		this.logHeight = _logHeight ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the showLeftTree.
	 */
	public boolean isTreeVisible ()
	{
		return this.treeVisible ;
	}

	/**
	 * Setter
	 * 
	 * @param _showLeftTree
	 *            The showLeftTree to set.
	 */
	public void setTreeVisible (boolean _showLeftTree)
	{
		this.treeVisible = _showLeftTree ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the showLog.
	 */
	public boolean isLogVisible ()
	{
		return this.logVisible ;
	}

	/**
	 * Setter
	 * 
	 * @param _showLog
	 *            The showLog to set.
	 */
	public void setLogVisible (boolean _showLog)
	{
		this.logVisible = _showLog ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the width.
	 */
	public int getWidth ()
	{
		return this.width ;
	}

	/**
	 * Setter
	 * 
	 * @param _width
	 *            The width to set.
	 */
	public void setWidth (int _width)
	{
		this.width = _width ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the xPosition.
	 */
	public int getXPosition ()
	{
		return this.xPosition ;
	}

	/**
	 * Setter
	 * 
	 * @param _position
	 *            The xPosition to set.
	 */
	public void setXPosition (int _position)
	{
		this.xPosition = _position ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the yPosition.
	 */
	public int getYPosition ()
	{
		return this.yPosition ;
	}

	/**
	 * Setter
	 * 
	 * @param _position
	 *            The yPosition to set.
	 */
	public void setYPosition (int _position)
	{
		this.yPosition = _position ;
	}

	/**
	 * Getter
	 * 
	 * @return Returns the instance.
	 */
	public static Preferences getInstance ()
	{
		if (instance == null)
		{
			instance = new Preferences() ;

		}

		return instance ;
	}

}
