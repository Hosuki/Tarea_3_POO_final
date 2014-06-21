import java.util.*;
import java.awt.*;
import java.applet.AudioClip;

public class Ball extends PhysicsElement implements Simulateable, SpringAttachable {
   private static int id=0;  // Ball identification number
   private final double mass;
   private final double radius;
   private double pos_t;     // current position at time t
   private double pos_tPlusDelta;  // next position in delta time in future
   private double speed_t;   // speed at time t
   private double speed_tPlusDelta;   // speed in delta time in future
   private double a_t;    // acceleration at time t
   private double a_tMinusDelta; 
   private BallView view;  // Ball view of Model-View-Controller design pattern
   private ArrayList<Spring> springs;
   
   private Ball(){   // nobody can create a block without state
     this(1.0,0.1,0,0);
   }
   public Ball(double mass, double radius, double position, double speed){
      super(id++);
      this.mass = mass;
      this.radius = radius;
      pos_t = position;
      speed_t = speed;
      a_t=a_tMinusDelta=0;
      view = new BallView(this);
      springs = new ArrayList<Spring>();
   }
   public double getMass() {
      return mass;
   }
   public double getRadius() {
      return radius;
   }
   public double getPosition() {
      return pos_t;
   }
   public double getSpeed() {
      return speed_t;
   }
   public double getNetForce(){
     double force=0;
     for (Spring s:springs)
       force+=s.getForce(this);
     return force;
   }
   public void computeNextState(double delta_t, MyWorld world) {
     Ball b;  // Assumption: on collision we only change speed.   
     if ((b=world.findCollidingBall(this))!= null){ /* elastic collision */
        speed_tPlusDelta=(speed_t*(mass-b.getMass())+2*b.getMass()*b.getSpeed())/(mass+b.getMass());
        pos_tPlusDelta = pos_t;
     } else {
        a_t= getNetForce()/mass;
        speed_tPlusDelta = speed_t+(3*a_t-a_tMinusDelta)*delta_t/2;
        pos_tPlusDelta = pos_t + speed_t*delta_t + (4*a_t-a_tMinusDelta)*delta_t*delta_t/2;
     }
   }
   public boolean collide(Ball b) {
     if (this == b) return false;
     boolean closeEnougth = Math.abs(getPosition()-b.getPosition()) < (getRadius()+b.getRadius());
     boolean approaching = getSpeed() > b.getSpeed();
     if (b.getPosition() < getPosition())
        approaching = getSpeed() < b.getSpeed();
     return closeEnougth && approaching;
   }
   public void updateState(){
     pos_t = pos_tPlusDelta;
     speed_t = speed_tPlusDelta;
     a_tMinusDelta=a_t;
   }
   
   public void updateView (Graphics2D g) {   // NEW
     view.updateView(g);  // update this Ball's view in Model-View-Controller design pattern     
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

   public String getDescription() {
     return "Ball_" + getId()+":x";
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
