import java.util.*;
import java.awt.*;

public class FixedHook extends PhysicsElement implements SpringAttachable {
   private static int id=0;  // FixedHook identification number
   private double pos_t;     // current position at time t
   private FixedHookView view;  // FixedHook view of Model-View-Controller design pattern
   private ArrayList<Spring> springs;
   
   private FixedHook(){   // nobody can create a block without state
     this(1);
   }
   public FixedHook(double position){
      super(id++);
      pos_t = position;
      view = new FixedHookView(this);
      springs = new ArrayList<Spring>();
   }
   public double getPosition() {
      return pos_t;
   }
   
   public double getEnergy(){
       return 0;
   }
   public boolean contains(double x, double y) {
      return view.contains(x,y);
   }
   public void setSelected(){
      view.setSelected();
   }
   public void setReleased(){
      view.setReleased();
   }
   public void dragTo(double x){
      pos_t=x;
   }
   public void updateView(Graphics2D g){
     view.updateView(g);
   }
   public String getDescription() {
     return "FixedHook_" + getId()+":x";
   }
   public String getState() {
     return getPosition()+"";
   }
   public void attachSpring(Spring s){
     springs.add(s);
   }
   public void detachSpring(Spring s){
     springs.remove(s);
   }
}