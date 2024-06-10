import java.awt.*;
import javax.swing.*;

public class Panel extends JPanel {
	JLabel JL;
	JButton JB;
	JTextField JTF;
	ImageIcon imagen;
	String nombre;
 public void Panel(JLabel jl, JTextField jtf)
 {
	 this.JL=jl;
	 this.JTF=jtf;
 }
 public Panel (String nombre)
 {
	 this.nombre=nombre;
 }
 public void paint (Graphics g)
 {
	 Dimension tamanio=getSize();
	 imagen = new ImageIcon(getClass().getResource(nombre));
	 g.drawImage(imagen.getImage(), 0, 0,tamanio.width,tamanio.height,null);
	 setOpaque(false);
	 super.paint(g);
 }
}
