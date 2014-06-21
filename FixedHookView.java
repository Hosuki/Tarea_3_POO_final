import java.awt.*;
import java.awt.geom.*;

public class FixedHookView {
   private final double width;  // fixed point width
   private Color color = Color.GREEN;
   private Rectangle2D.Double shape = null;
   private FixedHook hook;
  
   public FixedHookView(FixedHook hook){
     this.hook = hook;
     width=0.2;
     shape = new Rectangle2D.Double(hook.getPosition()-width/2,-width/2, width, width);
   }
   public boolean contains (double x, double y){
      return shape.contains(x,y);
   }
   public void setSelected (){
      color = Color.RED;
   }
   public void setReleased() {
      color = Color.GREEN;
   }
   void updateView(Graphics2D g) {
      shape.setFrame(hook.getPosition()-width/2, -width/2, width, width);
      g.setColor(color);
      g.fill(shape);
   }
}