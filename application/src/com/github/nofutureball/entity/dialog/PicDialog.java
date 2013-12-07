package com.github.nofutureball.entity.dialog;

import com.github.nofutureball.entity.Sprite;

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
    
    public PicDialog(float x, float y, float width, float height, Sprite sprite) {
        super(x, y, width, height);
        this.sprite = sprite;
        containsSprite = (sprite != null);
    }
    
    public PicDialog(String title, String msg, Sprite sprite) {
        this(50,450,700,100,sprite);
        this.title = title;
        this.msg = msg;
    }
    
    public PicDialog(String title, String msg) {
        this(title,msg,null);
    }

}
