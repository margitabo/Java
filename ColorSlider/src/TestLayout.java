import java.awt.*;

class TestLayout{

    public static void main(String[] args){

      Frame AFrame = new Frame("Frame with components");

      Label lblOne = new Label("This is a label");
      Button btn1 = new Button("This is a button");
      TextField tf1 = new TextField();
      tf1.setText("This is a textbox");

      AFrame.add(lblOne);
      AFrame.add(btn1);
      AFrame.add(tf1);
      AFrame.setSize(450, 300);

      //set the layout of the frame to FlowLayout
      //and align the components to the center
      AFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
     AFrame.setVisible(true);
  }
}