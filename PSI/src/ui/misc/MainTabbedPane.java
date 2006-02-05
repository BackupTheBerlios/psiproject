
package ui.misc ;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import com.sun.java.swing.plaf.windows.WindowsIconFactory;

/**
 * MainTabbedPane : The main container of the application Singleton is implemented for global access
 * to this element
 * 
 * @author Conde Mickael K.
 * @version 1.0
 * 
 */
public class MainTabbedPane extends JTabbedPane
{
	private static final long serialVersionUID = -4056182983518622778L ;

	private static MainTabbedPane instance = null ;

	private AdvancedTabbedPaneUI paneUI = null ;

	/**
	 * Constructor, private for singleton purposes
	 * 
	 */
	private MainTabbedPane ()
	{
		super() ;
		/*paneUI = new AdvancedTabbedPaneUI() ;
		super.setUI(new CloseTabPaneUI()) ;*/

	}

	/**
	 * Getter
	 * 
	 * @return Returns the instance.
	 */
	public static MainTabbedPane getInstance ()
	{
		if (instance == null)
		{
			instance = new MainTabbedPane() ;
		}
		return instance ;
	}

	/**
	 * Before adding, this method checks if the component is already in the tabs list. If this is
	 * the case then the component is simply "highlighted"
	 * 
	 * @see javax.swing.JTabbedPane#add(java.awt.Component, java.lang.Object)
	 */
	@ Override
	public void add (Component _component, Object _object)
	{
		Component localComponents[] = getComponents() ;

		for (int i = 0 ; i < localComponents.length; i++ )
		{
			if (localComponents[i].equals(_component))
			{
				setSelectedIndex(i) ;
				return ;
			}
		}

		super.add(_component, _object) ;
		setSelectedComponent(_component) ;
	}

	/**
	 * Updates the title for specified component
	 *
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 * @param _component the target component
	 * @param _title the new title to set
	 */
	public void setTitle(Component _component, String _title)
	{
		Component localComponents[] = getComponents() ;

		for (int i = 0 ; i < localComponents.length; i++ )
		{
			if (localComponents[i].equals(_component))
			{
				setTitleAt(i, _title) ;
				return ;
			}
		}
	}
	
	private class AdvancedTabbedPaneUI extends BasicTabbedPaneUI
	{
		/**
		 * This image will be used for the close icon
		 */

		private BufferedImage closeImage = null ;
		
		private BufferedImage closeImageI = null ;

		private JButton closeButton = null ;

		private final int IMAGE_SIZE = 15 ;

		/**
		 * Constructor
		 * 
		 */
		public AdvancedTabbedPaneUI ()
		{
			super() ;

			// Initializing components
			closeImage = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_4BYTE_ABGR) ;
			closeImageI = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_4BYTE_ABGR) ;
			closeButton = new JButton() ;
			closeButton.setSize(IMAGE_SIZE, IMAGE_SIZE) ;

			// Now we paint the default close "symbol" which is provided by the factory "on" the closeButton
			WindowsIconFactory.createFrameCloseIcon().paintIcon(closeButton, closeImageI.createGraphics(), 0, 0) ;
		}

		/**
		 * Just adding to the size of the closeButton plus extra space
		 * 
		 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#calculateTabWidth(int, int,
		 *      java.awt.FontMetrics)
		 */
		@ Override
		protected int calculateTabWidth (int _arg0, int _arg1, FontMetrics _arg2)
		{
			return super.calculateTabWidth(_arg0, _arg1, _arg2) + IMAGE_SIZE + 5 ;
		}

		/**
		 * Adding some extra space to handle the image
		 * 
		 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#calculateTabHeight(int, int, int)
		 */
		@ Override
		protected int calculateTabHeight (int _arg0, int _arg1, int _arg2)
		{
			return super.calculateTabHeight(_arg0, _arg1, _arg2) + 4 ;
		}

		/**
		 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#createMouseListener()
		 */
		@ Override
		protected MouseListener createMouseListener ()
		{
			return super.createMouseListener() ;
		}

		/**
		 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#createLayoutManager()
		 */
		/*@ Override
		protected LayoutManager createLayoutManager ()
		{
			return null ;
		}*/

		/**
		 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paint(java.awt.Graphics,
		 *      javax.swing.JComponent)
		 */
		@ Override
		public void paint (Graphics _gfx, JComponent _component)
		{super.paint(_gfx, _component) ;
			/*int localIndex = tabPane.getSelectedIndex() ;
			int localPlacement = tabPane.getTabPlacement() ;

			if (!tabPane.isValid())
			{
				tabPane.validate() ;
			}
			/*
			 * There is a bug in swing which i try to hack in the code below
			 */
			/*if (!tabPane.isValid())
			{
				TabbedPaneLayout layout = (TabbedPaneLayout) tabPane.getLayout() ;
				layout.calculateLayoutInfo() ;
			}
			
			paintContentBorder(_gfx, localPlacement, localIndex) ;*/
		}

		/**
		 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintTab(java.awt.Graphics, int, java.awt.Rectangle[], int, java.awt.Rectangle, java.awt.Rectangle)
		 */
		@ Override
		protected void paintTab (Graphics _gfx, int _placement, Rectangle[] _rects, int _index, Rectangle _iconRect, Rectangle _textRect)
		{
			super.paintTab(_gfx, _placement, _rects, _index, _iconRect, _textRect) ;
			Graphics2D localGfx2D = null ;
			Rectangle localRect = _rects[_index] ;
			
			if (_gfx instanceof Graphics2D)
			{
				localGfx2D = (Graphics2D)_gfx ;
				
				// TODO some handling here
			}
			
			// Getting tab's info
			String localTitle = tabPane.getTitleAt(_index) ;
			Font localFont = tabPane.getFont() ;
			FontMetrics localMetrics = _gfx.getFontMetrics(localFont) ;
			Icon localIcon = getIconForTab(_index) ;

			
			paintTabBackground(_gfx, _placement, _index, localRect.x, localRect.y, localRect.width, localRect.height, false) ;
			paintTabBorder(_gfx, _placement, _index, localRect.x, localRect.y, localRect.width, localRect.height, false) ;
			paintText(_gfx, _placement, localFont, localMetrics, _index, localTitle, localRect, true) ;
			paintIcon(_gfx, _placement, _index, localIcon, localRect, true) ;
			
			int dx = localRect.x + localRect.width - IMAGE_SIZE - 5 ;
			int dy = (localRect.y + localRect.height) / 2 - 6;
			
			//closeButton.setBackground(tabScroller.tabPanel.getBackground());
			closeButton.paint(closeImage.getGraphics());
			_gfx.drawImage(closeImage, dx, dy, null);

			_gfx.drawImage(closeImageI, dx, dy, null) ;
			
		}

	}
}
