/**
 * 
 */
package videochat.client.ui;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SpringLayout;

import videochat.client.TextI18n;

import jmapps.ui.JMDialog;
import jmapps.ui.JMPanel;

/**
 * Dialog that prompts the user to select a server
 *
 * @author "ppetkov" (Jun 22, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 22, 2009 "ppetkov" created <br>
 */
public class SelectServerDialog extends JMDialog  implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6475885264182833515L;
	private TextField serverAddress = null;
	private TextField userName = null;
	private Button okButton = null;
	public SelectServerDialog(Frame parentFrame){
		super(parentFrame, TextI18n.getText("selectserver"), true);
		serverAddress = new TextField("localhost");
		userName = new TextField();
		okButton = new Button(TextI18n.getText("ok"));
		okButton.addActionListener(this);
		Label l1 = new Label(TextI18n.getText("serveraddress"));
		Label l2 = new Label(TextI18n.getText("name"));
		SpringLayout layout = new SpringLayout(); 
		JMPanel panel = new JMPanel(layout);
		
		panel.add(l1);
		panel.add(serverAddress);
		panel.add(l2);
		panel.add(userName);
		panel.add(new Label());
		panel.add(okButton);
		
		SpringUtilities.makeCompactGrid(panel, 3, 2, 3, 3, 3, 3);
		
		this.setPreferredSize(new Dimension(250, 150));
		panel.doLayout();
		this.setLayout(new FlowLayout());
		this.add(panel);
		
		this.pack();
	}
	
	public void actionPerformed ( ActionEvent event ) {
		super.actionPerformed(event);
		if (event.getActionCommand().equals("OK")){
			setAction(ACTION_OK);
			this.setVisible(false);
		}
    }
	public String getServer(){
		return serverAddress.getText();
	}
	public String getUserName(){
		return userName.getText();
	}
	
}
