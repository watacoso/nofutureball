package com.github.nofutureball.main;

import java.util.ArrayList;

import org.newdawn.slick.state.StateBasedGame;

/**
 * Object used to build menu lists. It's possible to add 4 kinds of options on the list : Numerical, Boolean, State Changing and Functional.
 */

public class OptionsList extends ArrayList<Op>{

	private static final long serialVersionUID = 1L;
	private int index;
	
	/**
	 * Adds a new Numerical option
	 * @param name	name of the option
	 * @param value initial value
	 * @param max   the upper limit of the option
	 * @param min	the bottom limit of the option
	 */
	
	public void add(String name,int value,int max,int min){
		add(new Op(name,value,min,max));
	}
	
	/**
	 * Adds a numerical option with 0 as bottom limit.
	 * @param name	name of the option
	 * @param value initial value
	 * @param max   the upper limit of the option
	 */
	
	public void add(String name,int value,int max){
		add(new Op(name,value,max));
	}
	
	/**
	 * Adds a boolean option.
	 * @param name  name of the option
	 * @param value initial value
	 */
	
	public void add(String name,boolean value){
		add(new Op(name,value));
	}
	
	/**
	 * Adds a functional option. A functional option is basically just composed of it's name,
	 * and it is used inside of Algorithms of control for example to fire functions. For example, a functional option with name "Save"
	 * would be used to fire a function that saves the settings on a json file.
	 * @param name
	 */
	
	public void add(String name){
		add(new Op(name));
	}
	
	/**
	 * Adds a state changing option. if accessed through the select() function, it changes the game state
	 * @param name
	 * @param stateID
	 */
	
	public void add(String name,int stateID){
		add(new Op(name,stateID));
	}
	
	/**
	 * Move down on the options list. if at the bottom, move the index back on top (0)
	 */

	public void goDown(){
		if(index<size()-1)
			index++;
		else
			index=0;
	}
	
	/**
	 * Move up on the option list. if at the top, move the index back on bottom (size()-1)
	 */
	
	public void goUp(){
		if(index>0)
			index--;
		else
			index=size()-1;
	}
	
	/**
	 * if the index is over a boolean option, switch to the opposite value. iv the index is over a numerical option, decrease the
	 * option value
	 */
	
	public void goLeft(){
		get(index).left();
	}
	
	/**
	 * if the index is over a boolean option, switch to the opposite value. iv the index is over a numerical option, decrease the
	 * option value
	 */
	
	public void goRight(){
		get(index).right();
	}
	
	/**
	 * put index at 0
	 */
	
	public void resetIndex(){
		index=0;
	}
		
	
	public void select(StateBasedGame game){
		get(index).transition(game);
	}
	
	public int getIndex(){
		return index;
	}
	
	public String getName(int i){
		return get(i).name.toString();
	}
	
	public String getValue(int i){
		return get(i).getStringValue();
	}
	
	public int getInt(int i){
		return get(i).getValue();
	}
	
	public boolean getBoolean(int i){
		return get(i).getBValue();
	}
	
	
	public boolean isBoolean(int i){
		if(get(i).type==Op.Type.BOOLEAN)
			return true;
		return false;
	}
	
	public boolean isInteger(int i){
		if(get(i).type==Op.Type.INTEGER)
			return true;
		return false;
	}
	
	public boolean isFunctional(int i){
		if(get(i).type==Op.Type.FUNCTIONAL)
			return true;
		return false;
	}
	
	public boolean isTransition(int i){
		if(get(i).type==Op.Type.TRANSITION)
			return true;
		return false;
	}
	
}

/**
 * Option object. It contains the same set of functions of OptionsList. 
 * The reason is to avoid the need to always reference the index on the OptionList functions.
 * It also provides getters for the option values and methods to test the type of the option.
 * @author watacoso
 *
 */

class Op{
	
	public static enum Type{
		INTEGER,BOOLEAN,ARRAY,TRANSITION,FUNCTIONAL;
	}
	
	private Integer value,min,max;
	private Boolean bValue;

	public String name;
	
	public Type type;	
	
	public Op(String name, int value, int min, int max){
		this.name=name;
		this.min=min;
		this.max=max;
		this.value=value<min?min:value>max?max:value;

		type=Type.INTEGER;
	}
	
	public Op(String name, int value, int max){
		this.name=name;
		this.min=0;
		this.max=max;
		this.value=value<min?min:value>max?max:value;

		type=Type.INTEGER;
	}
	
	public Op(String name,boolean value){
		this.name=name;
		bValue=value;

		type=Type.BOOLEAN;
	}
	
	public Op(String name,int value){
		this.name=name;
		this.value=value;
		type=Type.TRANSITION;
	}
	
	public Op(String name){
		this.name=name;
		type=Type.FUNCTIONAL;
	}
	
	public void left(){
		if(type==Type.INTEGER)
			dec();
		else if(type==Type.BOOLEAN)
			flip();		
	}
	
	public void right(){
		if(type==Type.INTEGER)
			inc();
		else if(type==Type.BOOLEAN)
			flip();		
	}
	
	public void setValue(int value){
		if(type!=Type.INTEGER) return;
		this.value=value<min?min:value>max?max:value;
	}
	
	//public void add(int value){
	//	if(type!=Type.INTEGER) return;
	//	int v=this.value+value;
	//	this.value=v<min?min:v>max?max:v;
	//}
	
	public void inc(){
		if(type!=Type.INTEGER) return;
		int v=this.value+1;
		this.value=v<min?min:v>max?max:v;
	}
	
	public void dec(){
		if(type!=Type.INTEGER) return;
		int v=this.value-1;
		this.value=v<min?min:v>max?max:v;
	}
	
	public void setValue(boolean value){
		if(type!=Type.BOOLEAN) return;
		bValue=value;
	}
	
	public void flip(){
		if(type!=Type.BOOLEAN) return;
		bValue=!bValue;
	}
	
	public void transition(StateBasedGame g){
		if(type==Type.TRANSITION)
		g.enterState(value);
	}
	
	public String getStringValue(){
		if(type==Type.BOOLEAN)
			return bValue.toString();
		else if(type==Type.INTEGER)
			return value.toString();
		else return "";
	}
	
	public int getValue(){
		return value;
	}
	
	public boolean getBValue(){
		return bValue;
	}
	
}