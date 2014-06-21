import java.awt.event.*;

class NextKeyListener extends KeyAdapter {
  private MouseListener ml;
  public NextKeyListener(MouseListener mouseListener) {
    ml= mouseListener;
  }
  public void keyTyped(KeyEvent event){
    char keyChar = event.getKeyChar();
    if (keyChar == 'n'){
       ml.selectNextElement();
    }
  }
}