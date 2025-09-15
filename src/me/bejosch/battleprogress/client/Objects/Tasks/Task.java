package me.bejosch.battleprogress.client.Objects.Tasks;

import java.awt.Graphics;

import me.bejosch.battleprogress.client.Objects.Field.Field;

public interface Task {
	
	/**
	 * This draws the task infos on the field of the building/troup
	 */
	public void draw_Field(Graphics g, int fieldX, int fieldY);
	
	/**
	 * This draws the dask overlay/infos in the actionBar 
	 */
	public void draw_ActionBar(Graphics g, int displayedTaskNumber);
	
	/**
	 * Called by the task if the LEFT mouse key is pressed
	 */
	public void action_Left_Press();
	/**
	 * Called by the task if the LEFT mouse key is released
	 */
	public void action_Left_Release();
	/**
	 * Called by the task if the LEFT mouse key is released (With custom targetfield)
	 */
	public void action_Left_Release(Field customTargetField);
	/**
	 * Called by the task if the RIGHT mouse key is pressed
	 */
	public void action_Right_Press();
	/**
	 * Called by the task if the RIGHT mouse key is released
	 */
	public void action_Right_Release();
	
	/**
	 * This sets the task to the activeTask of the building/troup
	 */
	public void setToActiveTask();
	
	/**
	 * Checks the location whether an other task has this targetField already and blocks it
	 * @param targetField - {@link Field} - The target field which should be tested
	 * @return boolean - true if it is blocked, false if it not
	 */
	public boolean targetFieldIsBlocked(Field targetField);
	
	/**
	 * Tells, if this task is the activeTask of the building/troup
	 */
	public boolean isActiveTask();
	
	/**
	 * This removes the task back from the activeTask of the building/troup
	 */
	public void removeFromActiveTask();
	
	/**
	 * Just returns the hover message of the building/troup or null  
	 */
	public String[] getHoverMessage();
	
}
