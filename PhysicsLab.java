import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.JFrame;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Vector;

public class PhysicsLab {
   public static void main(String[] args) {
      PhysicsLab_GUI lab_gui = new PhysicsLab_GUI();
      lab_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      lab_gui.setVisible(true);
   }
}

class PhysicsLab_GUI extends JFrame {
   public PhysicsLab_GUI() {
      setTitle("My Small and Nice Physics Laboratory");
      // height+50 to account for menu height
      MyWorld world = new MyWorld();
      double plotWindowTime = 30; 
      setSize(1400,500);
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
      LabMenuListener menuListener = new LabMenuListener(world,fondo,this, panelPrincipal);
      setJMenuBar(createLabMenuBar(menuListener));
   }

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
