
package ui.misc ;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

/**
 * MainTabbedPane : The main container of the application Singleton is implemented for global access
 * to this element.
 * Part of this code is taken from java forums
 * 
 * @author Conde Mickael K.
 * @author fast_
 * @version 1.0
 * 
 */
public class MainTabbedPane extends JTabbedPane implements MouseListener, MouseMotionListener
{
	private static final long serialVersionUID = -4056182983518622778L ;

	private static MainTabbedPane instance = null ;

	private BasicTabbedPaneUI paneUI = null ;
	
	/**
	   * The viewport of the scrolled tabs.
	   */
	  private JViewport headerViewport = null;
	
	/**
	 * The normal closeicon.
	 */
	private Icon normalCloseIcon = null ;

	/**
	 * The closeicon when the mouse is over.
	 */
	private Icon hooverCloseIcon = null ;

	/**
	 * The closeicon when the mouse is pressed.
	 */
	private Icon pressedCloseIcon = null ;

	/**
	 * Constructor, private for singleton purposes
	 * 
	 */
	private MainTabbedPane ()
	{
		super() ;
		super.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT) ;
		
		this.addMouseListener(this) ;
		this.addMouseMotionListener(this) ;
		
		paneUI = new CloseableMetalTabbedPaneUI() ;
		super.setUI(paneUI) ;

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
	 * @see javax.swing.JTabbedPane#addTab(java.lang.String, java.awt.Component)
	 */
	@ Override
	public void addTab (String _title, Component _component)
	{
		addTab(_title, null, _component) ;
	}

	/**
	 * Before adding, this method checks if the component is already in the tabs list. If this is
	 * the case then the component is simply "highlighted"
	 * 
	 * @see javax.swing.JTabbedPane#addTab(java.lang.String, javax.swing.Icon, java.awt.Component)
	 */
	@ Override
	public void addTab (String _title, Icon _icon, Component _component)
	{
		int localTabCount = getTabCount() ;
		
		for (int i = 0 ; i < localTabCount; i++ )
		{
			if (getComponentAt(i).equals(_component))
			{
				setSelectedIndex(i) ;
				return ;
			}
		}
		
		boolean doPaintCloseIcon = true ;
		
		try
		{
			Object prop = null ;
			if ( (prop = ((JComponent) _component).getClientProperty("isClosable")) != null)
			{
				doPaintCloseIcon = (Boolean) prop ;
			}
		}
		catch (Exception ignored)
		{
		}

		_component.addPropertyChangeListener("isClosable", new PropertyChangeListener()
		{
			public void propertyChange (PropertyChangeEvent e)
			{
				Object newVal = e.getNewValue() ;
				int index = -1 ;
				if (e.getSource() instanceof Component)
				{
					index = indexOfComponent((Component) e.getSource()) ;
				}
				if (index != -1 && newVal != null && newVal instanceof Boolean)
				{
					setCloseIconVisibleAt(index, (Boolean) newVal) ;
				}
			}
		}) ;

		super.addTab(_title, doPaintCloseIcon ? new CloseTabIcon(_icon) : null, _component) ;
		setSelectedComponent(_component) ;

		if (headerViewport == null)
		{
			for (Component c : getComponents())
			{
				if ("TabbedPane.scrollableViewport".equals(c.getName())) headerViewport = (JViewport) c ;
			}
		}	
	}
	
	private void setCloseIconVisibleAt (int _index, boolean _visible) throws IndexOutOfBoundsException
	{
		super.setIconAt(_index, _visible ? new CloseTabIcon(null) : null) ;
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
		int localTabCount = getTabCount() ;
		
		for (int i = 0 ; i < localTabCount; i++ )
		{
			if (getComponentAt(i).equals(_component))
			{
				setTitleAt(i, _title) ;
				return ;
			}
		}
	}
	
	/**
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged (MouseEvent _evt)
	{
		
	}

	/**
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved (MouseEvent _evt)
	{
		
	}

	/**
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked (MouseEvent _evt)
	{
		processMouseEvents(_evt) ;
	}

	/**
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered (MouseEvent _evt)
	{
		processMouseEvents(_evt) ;
		
	}

	/**
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited (MouseEvent _evt)
	{
		processMouseEvents(_evt) ;
		
	}

	/**
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed (MouseEvent _evt)
	{
		
	}

	/**
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased (MouseEvent _evt)
	{
		
	}
	
	/**
	 * Notifies all listeners that have registered interest for notification on this event type.
	 * 
	 * @param tabIndexToClose
	 *            the index of the tab which should be closed
	 * @return true if the tab can be closed, false otherwise
	 */
	protected boolean fireCloseTab (int tabIndexToClose)
	{
		/*
		 * boolean closeit = true; // Guaranteed to return a non-null array Object[] listeners =
		 * listenerList.getListenerList(); for (Object i : listeners) { if (i instanceof
		 * CloseableTabbedPaneListener) { if (!((CloseableTabbedPaneListener)
		 * i).closeTab(tabIndexToClose)) { closeit = false; break; } } }
		 */
		return true ;
	}
	
	/**
	 * Processes all caught <code>MouseEvent</code>s.
	 * 
	 * @param _evt
	 *            the <code>MouseEvent</code>
	 */
	private void processMouseEvents (MouseEvent _evt)
	{
		int tabNumber = getUI().tabForCoordinate(this, _evt.getX(), _evt.getY()) ;
		if (tabNumber < 0) return ;
		boolean otherWasOver = false ;
		for (int i = 0; i < getTabCount(); i++ )
		{
			if (i != tabNumber)
			{
				CloseTabIcon ic = (CloseTabIcon) getIconAt(i) ;
				if (ic != null)
				{
					if (ic.mouseover) otherWasOver = true ;
					ic.mouseover = false ;
				}
			}
		}
		if (otherWasOver) { repaint() ; }
		CloseTabIcon icon = (CloseTabIcon) getIconAt(tabNumber) ;
		if (icon != null)
		{
			Rectangle rect = icon.getBounds() ;
			boolean vpIsNull = headerViewport == null ;
			Point pos = vpIsNull ? new Point() : headerViewport.getViewPosition() ;
			int vpDiffX = (vpIsNull ? 0 : headerViewport.getX()) ;
			int vpDiffY = (vpIsNull ? 0 : headerViewport.getY()) ;
			Rectangle drawRect = new Rectangle(rect.x - pos.x + vpDiffX, rect.y - pos.y + vpDiffY, rect.width, rect.height) ;

			if (_evt.getID() == MouseEvent.MOUSE_PRESSED)
			{
				icon.mousepressed = _evt.getModifiers() == MouseEvent.BUTTON1_MASK ;
				repaint(drawRect) ;
			}
			else if (_evt.getID() == MouseEvent.MOUSE_MOVED || _evt.getID() == MouseEvent.MOUSE_DRAGGED || _evt.getID() == MouseEvent.MOUSE_CLICKED)
			{
				pos.x += _evt.getX() - vpDiffX ;
				pos.y += _evt.getY() - vpDiffY ;
				if (rect.contains(pos))
				{
					if (_evt.getID() == MouseEvent.MOUSE_CLICKED)
					{
						int selIndex = getSelectedIndex() ;
						if (fireCloseTab(selIndex))
						{
							if (selIndex > 0)
							{
								// to prevent uncatchable null-pointers
								Rectangle rec = getUI().getTabBounds(this, selIndex - 1) ;

								MouseEvent event = new MouseEvent((Component) _evt.getSource(), _evt.getID() + 1, System.currentTimeMillis(), _evt
										.getModifiers(), rec.x, rec.y, _evt.getClickCount(), _evt.isPopupTrigger(), _evt.getButton()) ;
								dispatchEvent(event) ;
							}
							// the tab is being closed
							// removeTabAt(tabNumber);
							remove(selIndex) ;
						}
						else
						{
							icon.mouseover = false ;
							icon.mousepressed = false ;
							repaint(drawRect) ;
						}
					}
					else
					{
						icon.mouseover = true ;
						icon.mousepressed = _evt.getModifiers() == MouseEvent.BUTTON1_MASK ;
					}
				}
				else
				{
					icon.mouseover = false ;
				}
				repaint(drawRect) ;
			}
		}
	}
	
	/**
	 * CloseTabIcon : The class which generates the 'X' icon for the tabs. The constructor accepts
	 * an icon which is extra to the 'X' icon, so you can have tabs like in JBuilder. This value is
	 * null if no extra icon is required.
	 * 
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	class CloseTabIcon implements Icon
	{
		/**
		 * the x position of the icon
		 */
		private int x_pos ;

		/**
		 * the y position of the icon
		 */
		private int y_pos ;

		/**
		 * the width the icon
		 */
		private int width ;

		/**
		 * the height the icon
		 */
		private int height ;

		/**
		 * the additional fileicon
		 */
		private Icon fileIcon ;

		/**
		 * true whether the mouse is over this icon, false otherwise
		 */
		private boolean mouseover = false ;

		/**
		 * true whether the mouse is pressed on this icon, false otherwise
		 */
		private boolean mousepressed = false ;

		/**
		 * Creates a new instance of <code>CloseTabIcon</code>
		 * 
		 * @param fileIcon
		 *            the additional fileicon, if there is one set
		 */
		public CloseTabIcon (Icon fileIcon)
		{
			this.fileIcon = fileIcon ;
			width = 16 ;
			height = 16 ;
		}

		/**
		 * Draw the icon at the specified location. Icon implementations may use the Component
		 * argument to get properties useful for painting, e.g. the foreground or background color.
		 * 
		 * @param c
		 *            the component which the icon belongs to
		 * @param g
		 *            the graphic object to draw on
		 * @param x
		 *            the upper left point of the icon in the x direction
		 * @param y
		 *            the upper left point of the icon in the y direction
		 */
		public void paintIcon (Component c, Graphics g, int x, int y)
		{
			boolean doPaintCloseIcon = true ;
			try
			{
				// JComponent.putClientProperty("isClosable", new Boolean(false));
				JTabbedPane tabbedpane = (JTabbedPane) c ;
				int tabNumber = tabbedpane.getUI().tabForCoordinate(tabbedpane, x, y) ;
				JComponent curPanel = (JComponent) tabbedpane.getComponentAt(tabNumber) ;
				Object prop = null ;
				if ( (prop = curPanel.getClientProperty("isClosable")) != null)
				{
					doPaintCloseIcon = (Boolean) prop ;
				}
			}
			catch (Exception ignored)
			{
			}
			if (doPaintCloseIcon)
			{
				x_pos = x ;
				y_pos = y ;
				int y_p = y + 1 ;

				if (normalCloseIcon != null && !mouseover)
				{
					normalCloseIcon.paintIcon(c, g, x, y_p) ;
				}
				else if (hooverCloseIcon != null && mouseover && !mousepressed)
				{
					hooverCloseIcon.paintIcon(c, g, x, y_p) ;
				}
				else if (pressedCloseIcon != null && mousepressed)
				{
					pressedCloseIcon.paintIcon(c, g, x, y_p) ;
				}
				else
				{
					y_p++ ;

					Color col = g.getColor() ;

					if (mousepressed && mouseover)
					{
						g.setColor(Color.WHITE) ;
						g.fillRect(x + 1, y_p, 12, 13) ;
					}

					 this.x_pos = x ;
					this.y_pos = y ;
					y_p = y + 2 ;
					g.setColor(Color.black) ;
					g.drawLine(x + 1, y_p, x + 12, y_p) ;
					g.drawLine(x + 1, y_p + 13, x + 12, y_p + 13) ;
					g.drawLine(x, y_p + 1, x, y_p + 12) ;
					g.drawLine(x + 13, y_p + 1, x + 13, y_p + 12) ;
					g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10) ;
					g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10) ;
					g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9) ;
					g.drawLine(x + 11, y_p + 2, x + 3, y_p + 10) ;
					g.drawLine(x + 11, y_p + 3, x + 4, y_p + 10) ;
					g.drawLine(x + 10, y_p + 2, x + 3, y_p + 9) ;
					g.setColor(col) ;
					if (fileIcon != null)
					{
						fileIcon.paintIcon(c, g, x + width, y_p) ;
					}
				}
			}
		}

		/**
		 * Returns the icon's width.
		 * 
		 * @return an int specifying the fixed width of the icon.
		 */
		public int getIconWidth ()
		{
			return width + (fileIcon != null ? fileIcon.getIconWidth() : 0) ;
		}

		/**
		 * Returns the icon's height.
		 * 
		 * @return an int specifying the fixed height of the icon.
		 */
		public int getIconHeight ()
		{
			return height ;
		}

		/**
		 * Gets the bounds of this icon in the form of a <code>Rectangle<code>
		 * object. The bounds specify this icon's width, height, and location
		 * relative to its parent.
		 * @return a rectangle indicating this icon's bounds
		 */
		public Rectangle getBounds ()
		{
			return new Rectangle(x_pos, y_pos, width, height) ;
		}
	}
	  
	/**
	 * CloseableMetalTabbedPaneUI : A specific <code>BasicTabbedPaneUI</code>.
	 * 
	 * @author fast_ (java forums)
	 * @author Conde Mickael K.
	 * @version 1.0
	 * 
	 */
	class CloseableTabbedPaneUI extends BasicTabbedPaneUI
	{

		/**
		 * the horizontal position of the text
		 */
		private int horizontalTextPosition = SwingUtilities.LEFT ;

		/**
		 * Creates a new instance of <code>CloseableTabbedPaneUI</code>
		 */
		public CloseableTabbedPaneUI ()
		{
			super() ;
		}

		/**
		 * Creates a new instance of <code>CloseableTabbedPaneUI</code>
		 * @param horizontalTextPosition the horizontal position of the text (e.g.
		 * SwingUtilities.TRAILING or SwingUtilities.LEFT)
		 */
		public CloseableTabbedPaneUI (int horizontalTextPosition)
		{
			this.horizontalTextPosition = horizontalTextPosition ;
		}

		/**
		 * Layouts the label
		 * @param tabPlacement the placement of the tabs
		 * @param metrics the font metrics
		 * @param tabIndex the index of the tab
		 * @param title the title of the tab
		 * @param icon the icon of the tab
		 * @param tabRect the tab boundaries
		 * @param iconRect the icon boundaries
		 * @param textRect the text boundaries
		 * @param isSelected true whether the tab is selected, false otherwise
		 */
		protected void layoutLabel (int tabPlacement, FontMetrics metrics, int tabIndex, String title, Icon icon, Rectangle tabRect, Rectangle iconRect,
				Rectangle textRect, boolean isSelected)
		{

			textRect.x = textRect.y = iconRect.x = iconRect.y = 0 ;

			javax.swing.text.View v = getTextViewForTab(tabIndex) ;
			if (v != null)
			{
				tabPane.putClientProperty("html", v) ;
			}

			SwingUtilities.layoutCompoundLabel((JComponent) tabPane, metrics, title, icon, SwingUtilities.CENTER, SwingUtilities.CENTER, SwingUtilities.CENTER,
			//SwingUtilities.TRAILING,
					horizontalTextPosition, tabRect, iconRect, textRect, textIconGap + 2) ;

			tabPane.putClientProperty("html", null) ;

			int xNudge = getTabLabelShiftX(tabPlacement, tabIndex, isSelected) ;
			int yNudge = getTabLabelShiftY(tabPlacement, tabIndex, isSelected) ;
			iconRect.x += xNudge ;
			iconRect.y += yNudge ;
			textRect.x += xNudge ;
			textRect.y += yNudge ;
		}
	}
	
	
	/**
	 * CloseableMetalTabbedPaneUI : A specific <code>MetalTabbedPaneUI</code>.
	 *
	 * @author fast_ (java forums)
	 * @author Conde Mickael K.
	 * @version 1.0
	 *
	 */
	class CloseableMetalTabbedPaneUI extends MetalTabbedPaneUI
	{

		/**
		 * the horizontal position of the text
		 */
		private int horizontalTextPosition = SwingUtilities.LEFT ;

		/**
		 * Creates a new instance of <code>CloseableMetalTabbedPaneUI</code>
		 */
		public CloseableMetalTabbedPaneUI ()
		{
		}

		/**
		 * Creates a new instance of <code>CloseableMetalTabbedPaneUI</code>
		 * @param horizontalTextPosition the horizontal position of the text (e.g.
		 * SwingUtilities.TRAILING or SwingUtilities.LEFT)
		 */
		public CloseableMetalTabbedPaneUI (int horizontalTextPosition)
		{
			this.horizontalTextPosition = horizontalTextPosition ;
		}

		/**
		 * Layouts the label
		 * @param tabPlacement the placement of the tabs
		 * @param metrics the font metrics
		 * @param tabIndex the index of the tab
		 * @param title the title of the tab
		 * @param icon the icon of the tab
		 * @param tabRect the tab boundaries
		 * @param iconRect the icon boundaries
		 * @param textRect the text boundaries
		 * @param isSelected true whether the tab is selected, false otherwise
		 */
		protected void layoutLabel (int tabPlacement, FontMetrics metrics, int tabIndex, String title, Icon icon, Rectangle tabRect, Rectangle iconRect,
				Rectangle textRect, boolean isSelected)
		{

			textRect.x = textRect.y = iconRect.x = iconRect.y = 0 ;

			javax.swing.text.View v = getTextViewForTab(tabIndex) ;
			if (v != null)
			{
				tabPane.putClientProperty("html", v) ;
			}

			SwingUtilities.layoutCompoundLabel((JComponent) tabPane, metrics, title, icon, SwingUtilities.CENTER, SwingUtilities.CENTER, SwingUtilities.CENTER,
			//SwingUtilities.TRAILING,
					horizontalTextPosition, tabRect, iconRect, textRect, textIconGap + 2) ;

			tabPane.putClientProperty("html", null) ;

			int xNudge = getTabLabelShiftX(tabPlacement, tabIndex, isSelected) ;
			int yNudge = getTabLabelShiftY(tabPlacement, tabIndex, isSelected) ;
			iconRect.x += xNudge ;
			iconRect.y += yNudge ;
			textRect.x += xNudge ;
			textRect.y += yNudge ;
		}
	}


	
}
