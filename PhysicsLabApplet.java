import javax.swing.*;
import java.util.*;
import java.awt.Color;
import java.awt.Container;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.io.FilePermission;

public class PhysicsLabApplet extends JApplet {
	int ball_num,fixed_num,osc_num;
	double[][] ball_values; 
	double[] Fixedhook_values;
	double[][] oscillator_values;
	double deltatime,refreshtime;
	double plotWindowTime = 30; 
	public AudioClip ballBall;
	
   public void init () {
	 
      Container contentPane = getContentPane();
      ballBall=getAudioClip(getCodeBase(),"ballBall.wav");
      MyWorld world = new MyWorld(ballBall,true);
      double plotWindowTime = 30;
      MyWorldView  worldView = new MyWorldView(world);
      JPanel fondo = new JPanel();
      world.Grafica(fondo,plotWindowTime);
      JSplitPane panelPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,worldView,fondo);
      panelPrincipal.setDividerLocation(900);
      panelPrincipal.setOneTouchExpandable(true);
      panelPrincipal.setDividerSize(10); //es la barrita de separacion
      panelPrincipal.setPreferredSize(new Dimension(1400, 500));
      add(panelPrincipal);
      fondo.setVisible(true);
      world.setView(worldView);      
      
      
      setContentPane ( contentPane ); //de donde sale??

 
      LabMenuListener menuListener = new LabMenuListener(world,this);
      setJMenuBar(createLabMenuBar(menuListener));
      
      String v = getParameter("fixedHookNum");
  
      int fixed_num= Integer.parseInt(v);
      Fixedhook_values = new double[fixed_num];
      int i;
      for (i = 0; i < fixed_num; i++)
      {  
     	 Fixedhook_values[i] = Double.parseDouble(getParameter("fixedHook." + (i + 1)));     	 
      }

      v = getParameter("ballNum");
      if (v == null) return;
      ball_num = Integer.parseInt(v);
      ball_values = new double[ball_num][4];
      int j = 0;
      for (i = 0; i < ball_num; i++)
      {  
    	 
     	 for (String retval: getParameter("ball." + (i + 1)).split(";", 4)){
     		 ball_values[i][j] = Double.parseDouble(retval);
     		 j++;
           }
     	 j=0;
      }
      //oscilador
      v = getParameter("oscillatorNum");
      if (v == null) return;
      osc_num = Integer.parseInt(v);
      oscillator_values = new double[osc_num][3];
      j=0;
      for (i = 0; i < osc_num; i++)
      {  
     	 for (String retval: getParameter("oscillator." + (i + 1)).split(";", 3)){
     		 oscillator_values[i][j] = Double.parseDouble(retval);
     		 j++;
           }
      }
      

      //oscilador guardado ok
      
      //*****************hasta aqui el oscilador
      //delta time
      v = getParameter("deltatime");
      if (v == null) return;
      deltatime = Double.parseDouble(v);
      //refreshtime
      v = getParameter("refreshtime");
      if (v == null) return;
      refreshtime = Double.parseDouble(v);
      
  }  

public double getballnum(){
    return(ball_num);
  }
public double getfixednum(){
    return(fixed_num);
}
public double getoscnum(){
    return(osc_num);
}
public double getballvalues(int i, int j){
       return(ball_values[i][j]);
  }
public double getFixedhookvalues(int i){
	return(Fixedhook_values[i]);
}
public double getoscillatorvalues(int i, int j){
  return(oscillator_values[i][j]);
}
public double getdeltatime(){
    return(deltatime);
}
public double getrefreshtime(){
    return(refreshtime);
}

/*
class PhysicsLab_GUI extends JFrame {
   public PhysicsLab_GUI() {
      setTitle("My Small and Nice Physics Laboratory");
      setSize(MyWorldView.WIDTH, MyWorldView.HEIGHT+50);  // height+50 to account for menu height
      MyWorld world = new MyWorld();
      MyWorldView  worldView = new MyWorldView(world);
      world.setView(worldView);
      add(worldView);  
      LabMenuListener menuListener = new LabMenuListener(world);
      setJMenuBar(createLabMenuBar(menuListener));
   }
*/

   public JMenuBar createLabMenuBar(LabMenuListener menu_l) {
      JMenuBar mb = new JMenuBar();
      
      JMenu menu = new JMenu ("Configuration");
      mb.add(menu);
      JMenu subMenu = new JMenu("Insert");  
      menu.add(subMenu);
      
      JMenuItem menuItem = new JMenuItem("Ball");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);
      menuItem = new JMenuItem("Fixed Hook");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);
      menuItem = new JMenuItem("Spring");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);
      menuItem = new JMenuItem("Oscillator");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);
      menuItem = new JMenuItem("My scenario");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);
  
      menu = new JMenu("MyWorld");
      mb.add(menu);
      menuItem = new JMenuItem("Start");
      menuItem.addActionListener(menu_l);
      menu.add(menuItem);
      menuItem = new JMenuItem("Stop");
      menuItem.addActionListener(menu_l);
      menu.add(menuItem);
      
      menuItem = new JMenuItem("Reset Plot");
      menuItem.addActionListener(menu_l);
      menu.add(menuItem);
      
      subMenu = new JMenu("Simulator");
      menuItem = new JMenuItem("Delta time");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);
      menuItem = new JMenuItem("View Refresh time");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);
      menu.add(subMenu);      
      return mb;          
   }   
}

