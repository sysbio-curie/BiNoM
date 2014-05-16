
package org.cytoscape.sample;
import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.swing.CySwingApplication;

public class HideSingletonNodesApp extends AbstractCySwingApp 
{
	public HideSingletonNodesApp (CySwingAppAdapter adapter)
	{
		super(adapter);
		CySwingApplication application = adapter.getCySwingApplication();
		application.addAction(new MenuAction(adapter));
		application.addAction(new Adjust(adapter));
		application.addAction(new TransitionalLayout(adapter));
	}
}
