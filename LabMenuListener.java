import java.awt.event.*; 
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class LabMenuListener implements ActionListener {
   private MyWorld world;
   private JPanel fondo;
   private JFrame frame;
   private JApplet apple;
   private JSplitPane panelPrincipal;
   boolean applet;
   private PhysicsLabApplet Myapplet; // con este objeto rescato parametros
   
	public LabMenuListener (MyWorld w, JPanel p, PhysicsLabApplet a,JSplitPane sp){
		world = w;
		fondo = p;
		Myapplet = a;
		panelPrincipal = sp;
		applet = true;
	}
	
	public LabMenuListener (MyWorld w, JPanel p, JFrame a,JSplitPane sp){
		world = w;
		fondo = p;
		frame = a;
		panelPrincipal = sp;
		applet = false;
	}
	
	public LabMenuListener (MyWorld w, JPanel p, JSplitPane sp){ 
		world = w;
		fondo = p;
		panelPrincipal = sp;
		applet = false;
	}
   
   public LabMenuListener (MyWorld  w,PhysicsLabApplet applet){
      world = w;
      Myapplet = applet;
   }
   
   public void actionPerformed(ActionEvent e) {
      JMenuItem menuItem = (JMenuItem)(e.getSource());
      String text = menuItem.getText();
      // Actions associated to main manu options
      if(Myapplet!=null)
      {
		  if (text.equals("My scenario")) 
		  {  // here you define Etapa2's configuration
			int i;
			for(i=0;i<Myapplet.getballnum();i++)
			{
				Ball b = new Ball(Myapplet.getballvalues(i,0),Myapplet.getballvalues(i,1), Myapplet.getballvalues(i,2), Myapplet.getballvalues(i,3));
				world.addElement(b);  	
			}
			for(i=0;i<=Myapplet.getfixednum();i++)
			{
				FixedHook h = new FixedHook(Myapplet.getFixedhookvalues(i));
				world.addElement(h);
			}
			for(i=0;i<=Myapplet.getoscnum();i++)
			{
				Oscillator o = new Oscillator(Myapplet.getoscillatorvalues(i,0),Myapplet.getoscillatorvalues(i,1),Myapplet.getoscillatorvalues(i,2));
				world.addElement(o); 
			}
			world.setDelta_t(Myapplet.getdeltatime());
         	world.setRefreshPeriod(Myapplet.getrefreshtime());
		}
	}
		else
		{
			if (text.equals("My scenario")) 
			{  // here you define Etapa2's configuration
				double mass = 1.0;      // 1 [kg] 
				 double radius = 0.1;    // 10 [cm] 
				 double position = 1.0;  // 1 [m] 
				 double speed = 0.0;     // 0.5 [m/s]
				 Ball b = new Ball(mass, radius, position, speed);
				 FixedHook h = new FixedHook(0.2);
				 Spring s = new Spring (0.5, 5);
				 s.attachAend(h);
				 s.attachBend(b);
				 world.addElement(b);
				 world.addElement(h);
				 world.addElement(s);
				// world.setDelta_t(Myapplet.getdeltatime());
         		//world.setRefreshPeriod(Myapplet.getrefreshtime());
			}
         
        
      }
      if (text.equals("Ball")) 
        world.addElement(new Ball(1.0, 0.1, 1.2, 0));
      if (text.equals("Fixed Hook"))  
        world.addElement(new FixedHook(0.5));
      if (text.equals("Spring")) 
        world.addElement(new Spring(0.5, 5));
      if (text.equals("Oscillator")) 
        world.addElement(new Oscillator(0.5,0.1,0.5));

      // Actions associated to MyWorld submenu
      if (text.equals("Start"))   /* to be coded */
        world.start();
      
      if (text.equals("Stop"))    /* to be coded */
        world.stop();
      
      if (text.equals("Reset Plot"))
          world.Grafica_reset();
        
      if (text.equals("Delta time")) {
         String data = JOptionPane.showInputDialog("Enter delta t [s]");
         world.setDelta_t(Double.parseDouble(data));
      }
      if (text.equals("View Refresh time")) {
         // to be coded
         String data = JOptionPane.showInputDialog("Enter view refresh time [s]");
         world.setRefreshPeriod(Double.parseDouble(data));
      }
   }
}
