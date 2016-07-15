//**********************************************************
//Date of Creation 	: April 15
//Author          	: Ram Kumar Sastha Lakshmanan
//Purpose          	: Initial launch for Application Selection
//Last Update On 	:
//Updated by       	:
//**********************************************************
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import java.awt.AWTException;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
public class Appium_appselection implements ActionListener {
	static String files;	
	static String[] filenameAry=null;
	static String[] filenameAry1=null;
	static String[] tempfname=null;
	static String compFilename = "";
	static String path = "C:\\Appium\\Datasheet";
    static File folder = new File(path);
    static File[] listOfFiles = folder.listFiles(); 
    static ButtonGroup myGroup = null;    
    static int appcount =0;
    static String selectedApp = "";
    static boolean StartExec;   
    static boolean emailalert = false;
    static JLabel myLabel = null;
	static JLabel myLabel1 = null;
	static String content = "";
	static JTextArea TxtAreaStep;
	static JButton brunps;
    final static JFrame frm = new JFrame("Automation");
    final static JFrame frun = new JFrame("Ready...");
    
    public static void appdisplay() throws IOException, AWTException  {
    	StartExec = false;
    	Appium_appselection.createFrame();
		   
	} //appdisplay ends
    
    public static void createFrame() {
	     // app selection
	      frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      final Container c = frm.getContentPane();
	      c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));	     
	      myGroup = new ButtonGroup();
	      JPanel p = new JPanel();
	      myLabel = new JLabel("        Application Selection Wizard       ");
	      myLabel.setFont(new Font("Verdana", Font.BOLD,13));

	      p.setLayout(new GridLayout(3,1));	      
	      p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
	      p.setAlignmentX(JComponent.CENTER_ALIGNMENT);
	      p.add(myLabel);
	      c.add(p);
	      p.add(new JLabel(" "));      
	      
	      for (int jf=0;jf< listOfFiles.length;jf++){ //for1
				 files = listOfFiles[jf].getName();
		    	  filenameAry = files.split(".x");
		    	  if (filenameAry[0].startsWith("AppName-")) { //if1
		    		  tempfname = filenameAry[0].split("AppName-");
		    		  appcount = appcount +1;
		    		  addOption(p,myGroup,tempfname[1]);
 		  
		    	  } //if1 ends
				
			} //for1 ends

	      c.add(p);
	      c.add(new JLabel(" "));

	      JButton b = new JButton("Submit");
	      b.addActionListener(new ActionListener() {
	          public void actionPerformed(ActionEvent e) {
	        	  ButtonModel b1 = myGroup.getSelection();
	        	 
	    	      if (b1!=null) selectedApp = b1.getActionCommand();
	    	      outerloop:
	    	      for (int jfl=0;jfl< listOfFiles.length;jfl++){
	    				 files = listOfFiles[jfl].getName();
	    		    	  filenameAry = files.split(".x");
	    		    	  if (files.startsWith("AppName-"+selectedApp+".x")){
	    		    		  compFilename = files;
	    		    		  frm.setVisible(false);
	    		    		  myLabel1.setText("App :'"+selectedApp+"' Start Execution...  ");
	    		    		  frun.pack();
	    		    		  frun.setAlwaysOnTop(true);
	    		    		  frun.setVisible(true);
	    		    		  
	    		    		  break outerloop;
	    		    		
	    		    	  }	    	 
	    		    	 
	    	      }
	          }}); //action performed ends
	      c.add(b);
	       
	      
	    //In progress
	      frun.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	      
	      final Container crun = frun.getContentPane();
	      crun.setSize(100, 200); 
	      crun.setLayout(new BoxLayout(crun,BoxLayout.Y_AXIS));
	      final JPanel prun1 = new JPanel();
	      myLabel1 = new JLabel("");
		  myLabel1.setFont(new Font("Verdana", Font.BOLD,15));	    		    	      
	      prun1.add(myLabel1); 
	      crun.add(prun1);
	      
	      
	      final JPanel prun = new JPanel();
	      prun.setLayout(new BoxLayout(prun, BoxLayout.X_AXIS));
	      prun.setAlignmentY(JComponent.LEFT_ALIGNMENT);
	      prun.setLayout(new GridLayout(1,2));	      
	      Image img = new ImageIcon("C:\\Appium\\library\\img\\play.png").getImage();
	      final JButton brunpl = new JButton("Play");
	      brunpl.setIcon(new ImageIcon(img));
	      brunpl.setMinimumSize(new Dimension(10,10));	    
	      Image imgps = new ImageIcon("C:\\Appium\\library\\img\\pause.jpg").getImage();
	      brunps = new JButton("Pause");
	      brunps.setIcon(new ImageIcon(imgps));
	      brunps.setMinimumSize(new Dimension(10,10));	
	      brunps. setEnabled(false);
	      final JButton brunst = new JButton("Stop");
	      
	      brunst.setIcon(new ImageIcon("C:\\Appium\\library\\img\\stop.png"));
	      brunst. setEnabled(false);
	      prun.add(brunpl);
	      prun.add(brunps);
	      
	      
	      
	      
	      brunpl.addActionListener(new ActionListener() {
	          public void actionPerformed(ActionEvent e) {
	        	   	  StartExec = true;	  
		        	  //brunpl.setIcon(new ImageIcon("C:\\Selenium\\library\\img\\play.png"));
		        	  brunpl. setEnabled(false);
		        	  brunps. setEnabled(true);
		        	  brunst. setEnabled(true);
		        	  frun.setTitle("Controller...");
		        	  frun.setResizable(false);
		        	  myLabel1.setText("App :'"+selectedApp+"' Execution in progres... ");
		        	  final JPanel pstep = new JPanel();
		        	  
		        	  pstep.setLayout(new BoxLayout(prun, BoxLayout.Y_AXIS));
		        	  pstep.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		        	  pstep.setLayout(new GridLayout(1,1));	   
		        	  content = "Execution started";
					  TxtAreaStep = new JTextArea(content, 3, 20);
					  JScrollPane scroll = new JScrollPane(TxtAreaStep);
					  scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

					  TxtAreaStep.setLineWrap(true);
					  TxtAreaStep.setWrapStyleWord(true);
					  TxtAreaStep.setText(content);
					  TxtAreaStep.setEditable(false);
					  pstep.add(new JScrollPane(TxtAreaStep));		        	 
		        	  crun.add(pstep);
		        	  frun.pack();//
	          }});	      //actionlistener ends
	      brunps.addActionListener(new ActionListener() {
	          public void actionPerformed(ActionEvent e) {	        	  
	        	  	if (StartExec){
	        	  		TxtAreaStep.setText("In middle of the step - Pausing Execution...");
	        	  		StartExec = false;
	        	  	} else {
	        	  		TxtAreaStep.setText("Resuming Execution...");
	        	  		brunps.setText("Pause");
	        	  		StartExec = true;
	        	  		
	        	  	}	        	 
	        	  	
		        	 // frun.pack();//
	          }});	      //actionlistener ends
	      prun.add(brunst);
	      brunst.addActionListener(new ActionListener() {
	          public void actionPerformed(ActionEvent e) {	  
	        	 //driverscript.dr.close();
	        	  try {
						Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        	  System.exit(0);	
 			     
	          }});//actionlistener ends
	      crun.add(prun);
	      //frun.pack();
	      
	      
	      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	      frm.setLocation(dim.width/2,dim.height/4);
	      frm.pack();
	      if (!Appium_driverscript.varExist)
	    	  frm.setVisible(true);
	      else {
	    	  selectedApp = Appium_driverscript.arg[0];
	    	  for (int jfl=0;jfl< listOfFiles.length;jfl++){
 				 files = listOfFiles[jfl].getName();
 		    	  filenameAry = files.split(".x");
 		    	  if (files.startsWith("AppName-"+selectedApp+".x")){
 		    		  compFilename = files;
 		    		  frm.setVisible(false);
 		    		  myLabel1.setText("App :'"+selectedApp+"' Start Execution...  ");
 		    		  frun.pack();
 		    		  frun.setAlwaysOnTop(true);
 		    		  frun.setVisible(true);	
 		    	  }	    	 
 		    	 
	    	  }
	    	  StartExec = true;	  
        	  brunpl.setIcon(new ImageIcon("C:\\Appium\\library\\img\\play.png"));
        	  brunpl. setEnabled(false);
        	  brunps. setEnabled(true);
        	  brunst. setEnabled(true);
        	  frun.setTitle("Controller...");
        	  frun.setResizable(false);
        	  myLabel1.setText("App :'"+selectedApp+"' Execution in progres... ");
        	  final JPanel pstep = new JPanel();
        	  
        	  pstep.setLayout(new BoxLayout(prun, BoxLayout.Y_AXIS));
        	  pstep.setAlignmentY(JComponent.LEFT_ALIGNMENT);
        	  pstep.setLayout(new GridLayout(1,1));	   
        	  content = "Execution started";
			  TxtAreaStep = new JTextArea(content, 3, 20);
			  JScrollPane scroll = new JScrollPane(TxtAreaStep);
			  scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

			  TxtAreaStep.setLineWrap(true);
			  TxtAreaStep.setWrapStyleWord(true);
			  TxtAreaStep.setText(content);
			  TxtAreaStep.setEditable(false);
			  pstep.add(new JScrollPane(TxtAreaStep));		        	 
        	  crun.add(pstep);
        	  frun.pack();//
	      }
	    	  
	   } //create frame ends
    
	   public static void addOption(JPanel p, ButtonGroup g, String t) {
	      JRadioButton b = new JRadioButton(t);
	      b.setActionCommand(t);
	      p.add(b);
	      g.add(b);
	   } //addoption ends

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
 

} //class ends
