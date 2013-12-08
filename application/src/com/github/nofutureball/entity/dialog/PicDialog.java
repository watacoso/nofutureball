package com.github.nofutureball.entity.dialog;

import org.newdawn.slick.Graphics;

import com.github.nofutureball.entity.Sprite;
import com.github.nofutureball.main.Camera;

/**
 * Dialog that is able to contain a sprite
 * 
 * contains
 *  title
 *  sprite-img
 *  message
 *  
 * @author hollowspecter
 *
 */

public class PicDialog extends Dialog {
    
    private Sprite sprite;
    private boolean containsSprite;
    // the offset of the text/title because of the picture
    private float offset=0;
    
    /*
     * Constructors
     */
    
    /**
     * General Constructor
     * @param x X-Position
     * @param y Y-Position
     * @param width Width of the Box
     * @param height Height of the Box
     * @param title Title of the Message
     * @param msg The Message itself
     * @param sprite The Sprite image you want to display! If null, no image is displayed
     */
    public PicDialog(float x, float y, float width, float height, String title, String msg, Sprite sprite) {
        super(x, y, width, height, title, msg);
        this.sprite = sprite;
        containsSprite = (this.sprite != null); // checks if this dialog contains an image
        
        if(containsSprite) {    // makes the window bigger if an image is displayed
            setHeight(200);
        }
    }
    
    /**
     * Simple Constructor
     * @param title
     * @param msg
     * @param sprite
     */
    public PicDialog(String title, String msg, Sprite sprite) {
        this(50,450,700,100,title,msg,sprite);
    }
    
    /**
     * Constructor of a PicDialog without a sprite
     * @param title
     * @param msg
     */
    public PicDialog(String title, String msg) {
        this(title,msg,null);
    }
    
    /*
     * Protected functions
     */
    
    /**
     * Draws the sprite on the left side
     * also sets the offset
     * @param g
     */
    protected void drawSprite(Graphics g) {
        // draw the Sprite
        if(containsSprite) {
              offset = sprite.renderForTextbox(position.x+10, position.y+10, textbox.getHeight()-20);
        }
    }

    /*
     * Public Functions
     */
    
    /**
     * Slick render function
     */
    public void render(Camera cam) {
        Graphics g = new Graphics();        
        drawBox(g);
        drawSprite(g);
        drawTitle(g,offset);
        drawMsg(g,offset);
    }
}
