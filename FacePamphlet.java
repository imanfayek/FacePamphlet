/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		addTopButtons();
		addSideButtons();
		addActionListeners();
		canvas = new FacePamphletCanvas();
		add(canvas);
    }
	
	/*
	 * This method adds the text field and buttons 
	 * that allow you to create new profiles
	 */
	private void addTopButtons() {
		name = new JTextField(TEXT_FIELD_SIZE);
		addButton = new JButton("Add");
		deleteButton = new JButton("Delete");
		lookupButton = new JButton("Lookup");
		add(new JLabel("Name"), NORTH);
		add(name, NORTH);
		add(addButton, NORTH);
		add(deleteButton, NORTH);
		add(lookupButton, NORTH);
	}
	
	/*
	 * This method adds the buttons to the west side
	 * that allow you to edit your profile
	 */
	private void addSideButtons() {
		status = new JTextField(TEXT_FIELD_SIZE);
		status.addActionListener(this);
		addStatus = new JButton("Change Status");
		picture = new JTextField(TEXT_FIELD_SIZE);
		picture.addActionListener(this);
		addPicture = new JButton("Change Picture");
		friend = new JTextField(TEXT_FIELD_SIZE);
		friend.addActionListener(this);
		addFriend = new JButton("Add Friend");
		add(status, WEST);
		add(addStatus, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(picture, WEST);
		add(addPicture, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(friend, WEST);
		add(addFriend, WEST);
	}
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
		if(source == addButton) {
			if(!name.getText().equals("")) {
				if(!fDbase.containsProfile(name.getText())) {
					fProfile = new FacePamphletProfile(name.getText());
					fDbase.addProfile(fProfile);
					canvas.displayProfile(fProfile);
					canvas.showMessage("New profile created");
				} else {
					canvas.displayProfile(fProfile);
					canvas.showMessage("A profile with the name " + name.getText() + " already exists");
				}
			}
		}
		if(source == deleteButton) {
			if(!name.getText().equals("")) {
				if(fDbase.containsProfile(name.getText())) {
					fDbase.deleteProfile(name.getText());
					fProfile = null;
					canvas.displayProfile(fProfile);
					canvas.showMessage("Profile of " + name.getText() + " deleted");
				} else {
					fProfile = null;
					canvas.displayProfile(fProfile);
					canvas.showMessage("A Profile with the name " + name.getText() + " does not exist");
				}
			}
		}
		if(source == lookupButton) {
			if(!name.getText().equals("")) {
				if(fDbase.containsProfile(name.getText())) {
					fProfile = fDbase.getProfile(name.getText());
					canvas.displayProfile(fProfile);
					canvas.showMessage("Displaying " + name.getText());
				} else {
					fProfile = null;
					canvas.displayProfile(fProfile);
					canvas.showMessage("A Profile with the name " + name.getText() + " does not exist");
				}
			}
		}
		if(source == status || source == addStatus) {
			if(fProfile != null) {
				if(!status.getText().equals("")) {
					fProfile.setStatus(status.getText());
					canvas.displayProfile(fProfile);
					canvas.showMessage("Status updated to " + fProfile.getStatus());
				}
			} else {
				canvas.displayProfile(fProfile);
				canvas.showMessage("Please select a profile to change status");
			}
		}
		if(source == picture || source == addPicture) {
			if(fProfile != null) {
				if(!picture.getText().equals("")) {
					GImage profilePicture = null;
					try {
						profilePicture = new GImage(picture.getText()); 
						fProfile.setImage(profilePicture);
						canvas.displayProfile(fProfile);
						canvas.showMessage("Picture updated");
					} catch (ErrorException ex) {
						canvas.displayProfile(fProfile);
						canvas.showMessage("Unable to open image file: " + picture.getText());
					}
				}
			} else {
				canvas.displayProfile(fProfile);
				canvas.showMessage("Please select a profile to change image");
			}
		}
		if(source == friend || source == addFriend) {
			if(fProfile != null) {
				if(!friend.getText().equals("")) {
					if(fDbase.containsProfile(friend.getText())) {
						if(fProfile.addFriend(friend.getText()) == false) {
							canvas.displayProfile(fProfile);
							canvas.showMessage(fProfile.getName() + " already has " + friend.getText() + " as a friend");
						} else {
							fProfile.addFriend(friend.getText());
							fDbase.getProfile(friend.getText()).addFriend(fProfile.getName());
							canvas.displayProfile(fProfile);
							canvas.showMessage(friend.getText() + " added as a friend");
						}
					} else {
						canvas.displayProfile(fProfile);
						canvas.showMessage(friend.getText() + " does not exist");
					}
				}
			} else {
				canvas.displayProfile(fProfile);
				canvas.showMessage("Please select a profile to add friend");
			}
		}
	}
    
    /*
     * These are the instance variables used in this class
     */
    private JTextField name, status, picture, friend;
    private JButton addButton, deleteButton, lookupButton, addStatus, addPicture, addFriend;
    private FacePamphletProfile fProfile;
    private FacePamphletDatabase fDbase = new FacePamphletDatabase();
    private FacePamphletCanvas canvas;

}
