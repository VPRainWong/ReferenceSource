package com.vp.plugin.sample.findoriginalproject.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.diagram.IDiagramElement;
import com.vp.plugin.diagram.IDiagramUIModel;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IProject;

public class FindOriginalProjectActionControl implements VPContextActionController {

	@Override
	public void performAction(VPAction arg0, VPContext arg1, ActionEvent arg2) {
	    // Obtain the selected shapes on diagram
		IDiagramElement[] selectedElements = ApplicationManager.instance().getDiagramManager().getActiveDiagram().getSelectedDiagramElement();
	    if (selectedElements != null && selectedElements.length > 0) {
	    	// Walk though the elements
	    	for (int i = 0; i < selectedElements.length; i++) {
	    		IProject project = null;
	    		String projectPath = "current opening project";

	    		IDiagramElement element = selectedElements[i];

	    		// Retrieve the underlying model element of the selected shapes
	    		IModelElement model = element.getMetaModelElement();

	    		// Check is the model element mirrored from reference project. 
	    		IModelElement mirrorSource = model.getMirrorSource();

	    		// If the mirrored source != null means it is from reference
	    		// project. In this case we obtain the project from mirror source    
	    		if (mirrorSource != null) {
	    			project = mirrorSource.getProject();
	    		} else {
	    			// Otherwise the model element could be just local element, or 
	    			// from reference project but without create mirror. In this case
	    			// we can simply obtain the project from the model element
		    		project = model.getProject();	    			
	    		}
	    		
	    		// Obtain the project file path. If project == null means
	    		// user is working on a new project which ever being save.
	    		if (project != null) {
	    			File f = project.getProjectFile();
	    			if (f != null) {
	    				projectPath = f.getAbsolutePath();	
	    			}
	    		}
	    		
	    		// Output the project information of the selected element to message pane
	    		ApplicationManager.instance().getViewManager().showMessage("Source project for " + model.getName() + ": " + projectPath);	    		
	    	}	    	
	    }	    	    
	}

	@Override
	public void update(VPAction arg0, VPContext arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
