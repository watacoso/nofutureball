package com.github.nofutureball.entity.dialog;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.RoundedRectangle;

import com.github.nofutureball.entity.Entity;
import com.github.nofutureball.main.Camera;

/**
 * This is a very simple base class for Dialog
 * Dialogs of this kind appear and don't disappear on themselves
 * 
 * contains:
 *  title
 *  msg
 *  
 * @author hollowspecter
 *
 */

public class Dialog extends Entity {
    
    /**
     * Default coordinates, widths and heights
     */
    private final static int DEFAULT_FONT_SIZE = 18;
    private final static int DEFAULT_XPOS = 50;
    private final static int DEFAULT_YPOS = 450;
    private final static int DEFAULT_WIDTH = 700;
    private final static int DEFAULT_HEIGHT = 100;
    
    protected RoundedRectangle textbox;
    protected Color colorTextbox;
    protected Color colorFontMsg;
    protected Color colorFontTitle;
    protected String title;
    protected String msg;
    protected int fontsize;
    protected TrueTypeFont font;
    protected ArrayList<String> msgLines;

    /**
     * General constructor
     * is ALWAYS called. Takes care of setting everything, wrapping text and so on
     * @param x float X-Position
     * @param y float Y-Position
     * @param width Width of the window
     * @param height Height of the window
     */
    public Dialog(float x, float y, float width, float height, String title, String msg) {
        super(x, y);
        
        // Default Color is blue
        setColorTextbox(Color.blue, 0.5f, 0.7f);
        // Default Font Title color is orange
        setColorFontTitle(Color.orange, 0.7f);
        // Default Font Msg color is white
        setColorFontMsg(Color.white, 0.7f);
        // Default Fontsize
        setFontsize(DEFAULT_FONT_SIZE);
        // Default Font
        setFont(fontsize);
        // Default Message
        if (msg != null)
            this.msg = msg;
        else
            msg = "This is the default message";
        // Default Title
        if (title != null)
            this.title = title;
        else
            title = "Default title";
        textbox = new RoundedRectangle(x, y, width, height, 10);
        // Wraps up the breaks in the msg, also makes it larger in case it doesnt fit
        msgLines = wrapMessage(msg,calcMaxlines(height));
        
        open();
    }
    
    /**
     * Simple message constructor
     * @param title Title of the Dialog
     * @param msg Message of the Dialog
     */
    public Dialog(String title, String msg) {
        this(DEFAULT_XPOS,DEFAULT_YPOS,DEFAULT_WIDTH,DEFAULT_HEIGHT,title,msg);
    }
    
    /*
     * Private Functions
     */
    
    /**
     * Calculates the maximum of lines that can fit into the textbox right now
     * is used with wrapMessage to make a dynamics message body
     * is using the fontsize+2 to calculate the spaces
     * @param height of the textbox
     * @return the maximum of lines
     */
    private int calcMaxlines(float height) {
        float h = height;
        // padding + title
        h -= 50;
        int l = Math.round(h);
        return l/(fontsize+2);
        
    }
    
    /**
     * This wraps up the message, so it breaks the line when \n is in the String
     * If there are three lines or more, it starts to make the message body larger too
     * @param msg The Message String
     * @return ArrayList<String> every String is one line
     */
    private ArrayList<String> wrapMessage(String msg,int maxLinesBeforeEnlarge) {
        ArrayList<String> result = new ArrayList<String>();
        int start=0;
        // adds a new line for every \n in the msg
        while (msg.substring(start).indexOf("\n") != -1)
        {
            int tmp = msg.indexOf("\n",start);
            result.add(msg.substring(start,tmp));
            start = tmp+1;
        }
        result.add(msg.substring(start));
        // box stays the same unless there are more than two lines!
        if (result.size()>maxLinesBeforeEnlarge) {
            float larger = (result.size()-maxLinesBeforeEnlarge)*20;
            enlarge(larger);
        }
        return result;
    }
    
    /*
     * Protected Functions
     */
    
    /**
     * This is called when the dialog opens
     */
    protected void open() {
        
    }
    
    /**
     * Can be called to "destroy" the message
     */
    protected void close() {
        DialogManager.removeDialog(this);
    }
    
    /**
     * Makes the textbox vertically bigger
     * @param p float Pixel
     */
    protected void enlarge(float p) {
        float height = textbox.getHeight();
        height += p;
        textbox.setHeight(height);
        position.y -= p;
        textbox.setLocation(position.x, position.y);
    }
    
    /**
     * Makes the textbox vertically smaller
     * @param p float Pixel
     */
    protected void shorten(float p) {
        enlarge(-1*p);
    }
    
    /**
     * Sets the height of the textbox
     * @param height
     */
    protected void setHeight(float height) {
        textbox.setHeight(height);
        position.y = (550-height);
        textbox.setLocation(position);
    }
    
    /*
     * Getter and Setter
     */
    
    /**
     * Sets the Color of the textbox
     * @param color A Color Object
     * @param opacity float value from 0 to 1, 0 is invisible, 1 is solid
     * @param darkness float value from 0 to 1, 0 is not darker, 1 is black
     */
    protected void setColorTextbox(Color color, Float opacity, Float darkness)
    {
        colorTextbox = new Color(color.r, color.g, color.b, opacity);
        colorTextbox = colorTextbox.darker(darkness);
    }
    
    /**
     * Sets the Color of the Title
     * @param color A Color Object
     * @param opacity float value from 0 to 1, 0 is invisible, 1 is solid
     */
    protected void setColorFontTitle(Color color, Float opacity)
    {
        colorFontTitle = new Color(color.r, color.g, color.b, opacity);
    }
    
    /**
     * Sets the Color of the Message
     * @param color Color object
     * @param opacity float value from 0 to 1, 0 is invisible, 1 is solid
     */
    protected void setColorFontMsg(Color color, Float opacity)
    {
        colorFontMsg = new Color(color.r, color.g, color.b, opacity);
    }
    
    /**
     * Sets the default font
     * @param fontsize int Size of the font in points
     */
    protected void setFont(int fontsize) {
        font = new TrueTypeFont(new java.awt.Font("Verdana", Font.BOLD, fontsize), false);
    }
    
    /**
     * Set the fontsize
     * @param size
     */
    protected void setFontsize(int size) {
        fontsize = size;
    }
    
    /**
     * Draws the title, overloaded!
     * @param g
     */
    protected void drawTitle(Graphics g) {
        drawTitle(g,0);
    }
    
    /**
     * draws the Title with Offset on X-Pos (pushing it to the right)
     * @param g Graphics to render
     * @param xOffset the Offset to the right
     */
    protected void drawTitle(Graphics g, float xOffset) {
        if (g.getFont() != font)
            g.setFont(font);
        g.setColor(colorFontTitle);
        g.drawString(title, position.x+10+xOffset, position.y+10);
    }
    
    /**
     * Draws the message, overloaded!
     * @param g
     */
    protected void drawMsg(Graphics g) {
        drawMsg(g,0);
    }
    
    /**
     * Draws the Message
     * uses the ArrayList msgLines and an offset (pushing it to the right) to draw a new String for every line
     * on a different height
     * @param g
     * @param xOffset float Offset, pushing the Message to the right (for a picture for example)
     */
    protected void drawMsg(Graphics g, float xOffset) {
        if (g.getFont() != font)
            g.setFont(font);
        g.setColor(colorFontMsg);
        for(int i=0; i<msgLines.size(); i++) {
            g.drawString(msgLines.get(i), position.x+10+xOffset, position.y+40+i*(fontsize+2));
        }
    }
    
    /**
     * Draws the rectangle
     * @param g
     */
    protected void drawBox(Graphics g) {        
        // Draw the box
        g.setColor(colorTextbox);
        g.fill(textbox);
        g.draw(textbox);
    }
        
    /*
     * Public Functions
     */
    
    /**
     * Slick update function
     */
    public void update()
    {
        
    }
    
    /**
     * Slick render function
     */
    public void render(Camera cam)
    {
        Graphics g = new Graphics();
        drawBox(g);
        drawTitle(g);
        drawMsg(g);
    }
}
