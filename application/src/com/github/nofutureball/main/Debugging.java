package com.github.nofutureball.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import com.github.nofutureball.entity.dialog.Dialog;
import com.github.nofutureball.entity.dialog.DialogManager;
import com.github.nofutureball.states.GameLevel;
/**
 * Room for Debugging Options
 * @author watacoso
 *
 */

public class Debugging {
    
    public static void init() {
        DialogManager.showMessage("Simple Title", "Simple Message!", DialogManager.Type.PLAIN_MESSAGE);
    }
        
	public static void update(GameContainer game) {
	    Input input = game.getInput();
	    
	}

}

