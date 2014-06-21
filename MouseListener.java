import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.*;
import java.awt.geom.Point2D;

public class MouseListener extends MouseAdapter {
   private MyWorld world;
   private PhysicsElement currentElement;
   private Point2D.Double p; 

   public MouseListener (MyWorld w){
      world = w;
      p = new Point2D.Double(0,0);
   } 
   public void mouseMoved(MouseEvent e) {
      MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);// Change mouse coordenates from
      if (currentElement !=null)                                    // pixels to meters.
        if (currentElement.contains(p.getX(), p.getY())) 
           return;
        
      PhysicsElement newElement = world.find(p.getX(), p.getY()); 
      if (newElement == currentElement) return;
      if (currentElement != null) {
         currentElement.setReleased();
         currentElement = null;
      }
      if (newElement != null) { 
         currentElement = newElement;
         currentElement.setSelected();
      }
      world.repaintView();
   }
   public void mouseDragged(MouseEvent e) {
      if (currentElement!=null) {
        MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);// Change mouse coordenates from
        currentElement.dragTo(p.getX());                              // pixels to meters.
        world.repaintView();
      }
   }
   public void mouseReleased(MouseEvent e) {
      if (currentElement == null) return;
      if (currentElement instanceof Spring) {
         MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);

          // we dragged a spring, so we look for and attachable element near by  
         SpringAttachable element = world.findAttachableElement(p.getX());
         if (element != null) {
            // we dragged a spring and it is near an attachable element,
            // so we hook it to a spring end.
            Spring spring = (Spring) currentElement;
            double a=spring.getAendPosition();
            if (a==p.getX())
               spring.attachAend(element);
            double b=spring.getBendPosition();
            if (b==p.getX())
               spring.attachBend(element);
          }
      }    
 //     currentElement.setReleased();
 //     currentElement = null;
      world.repaintView();
   }
   public void selectNextElement(){
     PhysicsElement nextElement;
     if (currentElement !=null) {
       nextElement = world.findNext(currentElement, p.getX(), p.getY());
       if (currentElement != nextElement) {
          currentElement.setReleased();
          currentElement = nextElement;
          currentElement.setSelected();
          world.repaintView();
       }
     }
   }
}