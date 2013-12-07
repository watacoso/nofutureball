package com.github.nofutureball.entity.dialog;

import java.awt.Font;

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
    
    private final static int DEFAULT_FONT_SIZE = 18;
    
    protected RoundedRectangle textbox;
    protected Color colorTextbox;
    protected Color colorFontMsg;
    protected Color colorFontTitle;
    protected String title;
    protected String msg;
    protected int fontsize;
    protected TrueTypeFont font;

    /**
     * General constructor
     * @param x float X-Position
     * @param y float Y-Position
     * @param width Width of the window
     * @param height Height of the window
     */
    public Dialog(float x, float y, float width, float height) {
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
        msg = "This is a default message";
        // Default Title
        title = "Default title";
        textbox = new RoundedRectangle(x, y, width, height, 10);
        
        open();
    }
    
    /**
     * Simple message constructor
     * @param title Title of the Dialog
     * @param msg Message of the Dialog
     */
    public Dialog(String title, String msg) {
        this(50,450,700,100);
        this.title = title;
        this.msg = msg;
    }
    
    /*
     * Private Functions
     */
    
    /**
     * This is called when the dialog opens
     */
    private void open() {
        
    }
    
    /**
     * Can be called to "destroy" the message
     */
    private void close() {
        DialogManager.removeDialog(this);
    }
    
    /**
     * Makes the textbox vertically bigger
     * @param p float Pixel
     */
    private void enlarge(float p) {
        float height = textbox.getHeight();
        height += p;
        textbox.setHeight(height);
        position.y -= p;
    }
    
    /**
     * Makes the textbox vertically smaller
     * @param p float Pixel
     */
    private void shorten(float p) {
        enlarge(-1*p);
    }
    
    /**
     * Sets the height of the textbox
     * @param height
     */
    private void setHeight(float height) {
        float diff = textbox.getHeight() - height;
        enlarge(diff);
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
    private void setColorTextbox(Color color, Float opacity, Float darkness)
    {
        colorTextbox = new Color(color.r, color.g, color.b, opacity);
        colorTextbox = colorTextbox.darker(darkness);
    }
    
    /**
     * Sets the Color of the Title
     * @param color A Color Object
     * @param opacity float value from 0 to 1, 0 is invisible, 1 is solid
     */
    private void setColorFontTitle(Color color, Float opacity)
    {
        colorFontTitle = new Color(color.r, color.g, color.b, opacity);
    }
    
    /**
     * Sets the Color of the Message
     * @param color Color object
     * @param opacity float value from 0 to 1, 0 is invisible, 1 is solid
     */
    private void setColorFontMsg(Color color, Float opacity)
    {
        colorFontMsg = new Color(color.r, color.g, color.b, opacity);
    }
    
    /**
     * Sets the default font
     * @param fontsize int Size of the font in points
     */
    private void setFont(int fontsize) {
        font = new TrueTypeFont(new java.awt.Font("Verdana", Font.BOLD, fontsize), false);
    }
    
    /**
     * Set the fontsize
     * @param size
     */
    private void setFontsize(int size) {
        fontsize = size;
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
        g.setFont(font);
        
        // Draw the box
        g.setColor(colorTextbox);
        g.fill(textbox);
        g.draw(textbox);
        
        // Title
        g.setColor(colorFontTitle);
        g.drawString(title, position.x+10, position.y+10);
        
        // Msg
        g.setColor(colorFontMsg);
        g.drawString(msg, position.x+10, position.y+30);
    }
}
