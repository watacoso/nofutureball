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
    
    /*
     * Constructors
     */
    
    public PicDialog(float x, float y, float width, float height, Sprite sprite) {
        super(x, y, width, height);
        this.sprite = sprite;
        containsSprite = (this.sprite != null); // checks if this dialog contains an image
        
        if(containsSprite) {    // makes the window bigger if an image is displayed
            setHeight(200);
        }
    }
    
    public PicDialog(String title, String msg, Sprite sprite) {
        this(50,450,700,100,sprite);
        this.title = title;
        this.msg = msg;
    }
    
    public PicDialog(String title, String msg) {
        this(title,msg,null);
    }

    /*
     * Public Functions
     */
    
    public void render(Camera cam) {
        Graphics g = new Graphics();
        g.setFont(font);
        
        // Draw the box
        g.setColor(colorTextbox);
        g.fill(textbox);
        g.draw(textbox);
        
        // draw the Sprite
        float offset=0;
        if(containsSprite) {
              offset = sprite.renderForTextbox(position.x+10, position.y+10, textbox.getHeight()-20);
        }
        
        // Title
        g.setColor(colorFontTitle);
        g.drawString(title, position.x+offset+10, position.y+10);
        
        // Msg
        g.setColor(colorFontMsg);
        g.drawString(msg, position.x+offset+10, position.y+30);
    }
}
