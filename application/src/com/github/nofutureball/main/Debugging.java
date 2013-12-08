package com.github.nofutureball.main;

import org.newdawn.slick.GameContainer;

import com.github.nofutureball.control.LevelManager;
import com.github.nofutureball.entity.Sprite;
import com.github.nofutureball.entity.dialog.DialogManager;
/**
 * Room for Debugging Options
 * @author watacoso
 *
 */

public class Debugging {
    
    public static void init() {
        DialogManager.showMessage("Simple Title", "Simple Message!\nI wonder if that works!", DialogManager.Type.PLAIN_PIC_MSG, (Sprite)LevelManager.players.get(0));
    }
        
	public static void update(GameContainer game) {
	    
	}

}

