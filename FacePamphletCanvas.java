/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;

import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		double x = getWidth()/2; 
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		GLabel profileMessage = new GLabel(msg);
		profileMessage.setFont(MESSAGE_FONT);
		profileMessage.setLocation(x - (profileMessage.getWidth() / 2), y);
		add(profileMessage);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		this.profile = profile;
		removeAll();
		if(this.profile != null) {
			addName();
			addImage();
			addStatus();
			addFriendsList();
		}
	}
	
	/*
	 * This method gets the name associated with
	 * the profile and adds it to the display
	 */
	private void addName() {
		name = new GLabel(profile.getName());
		name.setFont(PROFILE_NAME_FONT);
		name.setColor(Color.BLUE);
		name.setLocation(LEFT_MARGIN, TOP_MARGIN + name.getAscent());
		add(name);
	}
	
	/*
	 * This method gets the image associated with 
	 * the profile and add it to the display
	 */
	private void addImage() {
		xBox = LEFT_MARGIN;
		yBox = TOP_MARGIN + name.getAscent() + IMAGE_MARGIN;
		double xMessage = xBox + (IMAGE_WIDTH / 2);
		double yMessage = yBox + (IMAGE_HEIGHT / 2);
		if(profile.getImage() == null ) {
			GRect imageBox = new GRect(xBox, yBox, IMAGE_WIDTH, IMAGE_HEIGHT);
			add(imageBox);
			GLabel imageMessage = new GLabel("No Image");
			imageMessage.setFont(PROFILE_IMAGE_FONT);
			imageMessage.setLocation(xMessage - (imageMessage.getWidth() / 2), yMessage + (imageMessage.getAscent() / 2));
			add(imageMessage);
		} else {
			GImage image = profile.getImage();
			image.setLocation(xBox, yBox);
			add(image);
		}
	}
	
	/*
	 * This method gets the status associated with 
	 * the profile and add it to the display
	 */
	private void addStatus() {
		double x = xBox;
		double y = yBox + IMAGE_HEIGHT + STATUS_MARGIN;
		GLabel status = new GLabel(profile.getStatus(), x, y);
		status.setFont(PROFILE_STATUS_FONT);
		add(status);
	}
	
	/*
	 * This method gets the status associated with 
	 * the profile and add it to the display
	 */
	private void addFriendsList() {
		double xHeader = (getWidth() / 2);
		double yHeader = yBox;
		GLabel header = new GLabel("Friends:", xHeader, yHeader);
		header.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(header);
		
		double xFriend = xHeader;
		double yFriend = yBox;
		Iterator<String> it = profile.getFriends(); 
		while (it.hasNext()) { 
			GLabel friendName = new GLabel(it.next()); 
			friendName.setFont(PROFILE_FRIEND_FONT);
			friendName.setLocation(xFriend, yFriend + (friendName.getHeight()));
			add(friendName);
			yFriend += friendName.getHeight();
		}
	}

	/*
	 * These are the instance variables used in this class
	 */
	private FacePamphletProfile profile;
	private GLabel name;
	private double xBox, yBox;
	
}
