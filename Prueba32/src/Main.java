import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.Window.Type;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.io.File;
import java.io.IOException;


import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.JPanel;

import java.*;
import java.awt.Panel;
import javax.swing.border.BevelBorder;
import javax.swing.JList;
import java.awt.List;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;

public class Main {

	private JFrame frame;
    public  String nombreb, latitud, longitud;
    public int limite2;     
    public double variacion2;  
    public String cantLocales ="";    
    public String rangoMetros = "";    
    public String limite = (0+cantLocales);    
    public double cuadras2;    
    public  String filtro;
    
   // public double variacion = (cuadras/1150);
    
    public int opcion ;
    
	IpAdress ip = new IpAdress();
	
	String strip = ip.ObtenerIPPublica();
	
	Geolocation geoLoc = new Geolocation();
	Coordenadas coord = geoLoc.ObtenerLocalizacion(strip);
	
	 float lat_loc = (float) coord.getLatitud2();
	 float long_loc = (float) coord.getLongitud2();
	 
	 private static List list = new List();	
	 private JTextField textField;
	 private JTextField textField_1;
	 private final Action action = new SwingAction();
	 private final ButtonGroup buttonGroup = new ButtonGroup();
    
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
				
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		

		
		try {
	        Conexion conexion = new Conexion();
	        
	        Connection con = conexion.getConexionMYSQL();
	        
	        Statement st = con.createStatement();
	        
	        ArrayList listaLocales = new ArrayList();
	        ArrayList listaLocales2 = new ArrayList();
	        
	        	
	        ResultSet rs = st.executeQuery("SELECT * FROM registro_locales"); 	        	     
	        while (rs.next())
	        {   	         															////////////
	        	double xboliche = rs.getDouble("X");											  //
	        	 
	   	        double yboliche = rs.getDouble("Y");
	   	     
	   	        double x5 = (xboliche-(long_loc)); 
	   	        
	   	        double x6 = (yboliche-(lat_loc)); 
	   	    
	   	        double x7 = Math.pow(x5, 2);
	   	  
	   	        double x8 = Math.pow(x6, 2);
	   	     
	   	        double x9 = (x7+x8);
	   	    
	   	        double x10 = Math.pow(x9, 0.5);
	        		   	        	   	        	   	
	        	 LocalBailable local = new LocalBailable();
	        	 local.setLatitud(rs.getString("X"));
	        	 local.setLongitud(rs.getString("Y"));
	        	 local.setNombre(rs.getString("NOMBRE"));
	        	 local.setId(rs.getString("ID_LOCALES"));
	   	         local.setDistancia(x10);

	           // agregamos local al listado de locales
	           listaLocales.add(local);
	        }
	                	        
	        rs.close();
	      
 
	        for( int i = 0; i < listaLocales.size(); i++)
	        {
	        	
	        	st.executeUpdate("UPDATE registro_locales SET Distancia="+((LocalBailable) listaLocales.get(i)).getDistancia()+"WHERE ID_LOCALES="+((LocalBailable) listaLocales.get(i)).getId());
            
	        }
	        
      
	        
	        ResultSet rs3 = st.executeQuery("SELECT * FROM filtros where id=1"); 	  
	        while (rs3.next())
	        { 
	          limite2 = rs3.getInt("cantidad");
	          cuadras2 = rs3.getInt("rango");
	          variacion2 = (double) (cuadras2)/110000;
	          opcion = rs3.getInt("opcion");
	        }
	        
	        if(opcion == 1)
	      {
	        	filtro = ("SELECT * FROM registro_locales ORDER BY `registro_locales`.`Distancia` ASC limit "+limite2);
	      }
	        if(opcion ==2)
	      {
	    
	        	filtro = ("SELECT * FROM `registro_locales` WHERE X <"+(long_loc+variacion2)+" and X >"+(long_loc-variacion2)+"and Y <"+(lat_loc+variacion2)+" and Y >"+(lat_loc-variacion2)+"ORDER BY `registro_locales`.`Distancia` ASC");
	      }
	        
	        ResultSet rs2 = st.executeQuery(filtro); 
	        
	        while (rs2.next())
	        {   	        
	        	double xboliche = rs2.getDouble("X");
	        	 
	   	        double yboliche = rs2.getDouble("Y");
	   	     
	   	        double x5 = (xboliche-(long_loc)); 
	   	        
	   	        double x6 = (yboliche-(lat_loc)); 
	   	    
	   	        double x7 = Math.pow(x5, 2);
	   	  
	   	        double x8 = Math.pow(x6, 2);
	   	     
	   	        double x9 = (x7+x8);
	   	    
	   	        double x10 = Math.pow(x9, 0.5);
	        		   	        	   	        	   	
	        	 LocalBailable local = new LocalBailable();
	        	 local.setLatitud(rs2.getString("X"));
	        	 local.setLongitud(rs2.getString("Y"));
	        	 local.setNombre(rs2.getString("NOMBRE"));
	        	 local.setId(rs2.getString("ID_LOCALES"));
	   	         local.setDistancia(x10);

	           // agregamos local al listado de locales
	           listaLocales2.add(local);
	        }
	                	        
	        rs2.close();
	      
	        String mapPage = "http://econym.org.uk/gmap/example_plotpoints.htm?q=Posicion%20Actual@"+lat_loc+","+long_loc; 
	        for( int i = 0; i < listaLocales2.size(); i++)
	        {
	        
	        		mapPage += "&q=";
	        	
	        	mapPage += ((LocalBailable) listaLocales2.get(i)).getLocalAsString();
	        	
	        	st.executeUpdate("UPDATE registro_locales SET Distancia="+((LocalBailable) listaLocales2.get(i)).getDistancia()+"WHERE ID_LOCALES="+((LocalBailable) listaLocales2.get(i)).getId());
	       
	        }
	      
	        
	        
	        Browser browser = new Browser();
	        browser.loadURL(mapPage);	
			BrowserView view = new BrowserView(browser);
			view.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			
			view.setBounds(25, 141, 580, 470);
			
			
			
			
			
			
			frame = new JFrame();
			frame.setType(Type.UTILITY);
			frame.getContentPane().setLayout(null);
			frame.getContentPane().add(view);
			list.setFont(new Font("Arial", Font.BOLD, 14));
			
			
			
			
			list.setBounds(676, 97, 561, 574);
			frame.getContentPane().add(list, BorderLayout.EAST);
			
			JButton btnNewButton = new JButton("Aplicar filtros");
			btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
			
			btnNewButton.setBounds(542, 49, 124, 42);
			frame.getContentPane().add(btnNewButton);
			
			
			
			JLabel lblListaDeLocales = new JLabel("Lista de Locales obtenidos:");
			lblListaDeLocales.setForeground(Color.WHITE);
			lblListaDeLocales.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblListaDeLocales.setBounds(676, 77, 342, 14);
			frame.getContentPane().add(lblListaDeLocales);
			
			final JLabel lblEligaLaOpcion = new JLabel("Elija la opcion por la que quiera filtar los locales:");
			lblEligaLaOpcion.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblEligaLaOpcion.setForeground(Color.WHITE);
			lblEligaLaOpcion.setBackground(Color.WHITE);
			lblEligaLaOpcion.setBounds(6, 0, 467, 27);
			frame.getContentPane().add(lblEligaLaOpcion);
						
			final JLabel lblEscribaElRango = new JLabel("Escriba el rango en metros:");
			lblEscribaElRango.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblEscribaElRango.setForeground(Color.WHITE);
			lblEscribaElRango.setBounds(233, 49, 200, 17);
			frame.getContentPane().add(lblEscribaElRango);
			
			final JLabel lblNewLabel = new JLabel("Escriba la cantidad de locales para mostrar:");
			lblNewLabel.setForeground(Color.WHITE);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNewLabel.setEnabled(false);
			lblNewLabel.setBounds(124, 77, 312, 14);
			frame.getContentPane().add(lblNewLabel);
			
			textField = new JTextField();
			textField.setBounds(446, 77, 86, 20);
		
			frame.getContentPane().add(textField);
			textField.setColumns(10);
			
			textField_1 = new JTextField();
			textField_1.setBounds(443, 49, 89, 20);
					
			frame.getContentPane().add(textField_1);
			textField_1.setColumns(10);
			

			JRadioButton rdbtnPorRango = new JRadioButton("Por rango");
			rdbtnPorRango.setFont(new Font("Tahoma", Font.BOLD, 12));
			buttonGroup.add(rdbtnPorRango);
			rdbtnPorRango.setSelected(true);
			rdbtnPorRango.setBounds(6, 49, 109, 23);
			frame.getContentPane().add(rdbtnPorRango);
			
			JRadioButton rdbtnPorCercana = new JRadioButton("Por cercan\u00EDa");
			rdbtnPorCercana.setFont(new Font("Tahoma", Font.BOLD, 12));
			buttonGroup.add(rdbtnPorCercana);
			rdbtnPorCercana.setBounds(6, 73, 109, 23);
			frame.getContentPane().add(rdbtnPorCercana);
			
			
			JLabel lblNewLabel_1 = new JLabel("New label");
			lblNewLabel_1.setIcon(new ImageIcon(Main.class.getResource("/img/9julio.jpg")));
			lblNewLabel_1.setBounds(0, 0, 1264, 671);
			frame.getContentPane().add(lblNewLabel_1);
			
			if(rdbtnPorCercana.isSelected())
				textField_1.setEnabled(false);
			else
				textField_1.setEnabled(true);				
			
			if(rdbtnPorRango.isSelected())
				textField.setEnabled(false);
			else
				textField.setEnabled(true);
			
			rdbtnPorRango.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (textField_1.isEnabled())
					{
						textField_1.setEnabled(false);
						lblNewLabel.setEnabled(true);
						lblEscribaElRango.setEnabled(false);
						textField.setEnabled(true);
						frame.revalidate();
					}
				else
				{
					textField_1.setEnabled(true);
					lblNewLabel.setEnabled(false);
					lblEscribaElRango.setEnabled(true);
					textField.setEnabled(false);
					frame.revalidate();
				}
				}
			});
			
			rdbtnPorCercana.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (textField_1.isEnabled())
						{
							textField_1.setEnabled(false);
							lblNewLabel.setEnabled(true);
							lblEscribaElRango.setEnabled(false);
							textField.setEnabled(true);
							frame.revalidate();
							
						}
					else
					{
						textField_1.setEnabled(true);
						lblNewLabel.setEnabled(false);
						lblEscribaElRango.setEnabled(true);
						textField.setEnabled(false);
						frame.revalidate();
						
					}
					 
				}
			});
			
			/////////////////////////////////////////////////////////////////////////////////////////////
			
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(textField_1.isEnabled() && textField_1.getText() != "")
					{
						
						rangoMetros = textField_1.getText();
						try {
							Conexion conexion = new Conexion();					        
					        Connection con = conexion.getConexionMYSQL();					        
					        Statement st = con.createStatement();							
					        String cambio1= rangoMetros;

								st.executeUpdate("UPDATE filtros SET rango="+cambio1+" where id = 1");
								st.executeUpdate("UPDATE filtros SET opcion=2 where id = 1");
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						System.out.println(rangoMetros);
						
						
						frame.dispose();
						Main frame = new Main();
					}
					
					else if(textField.isEnabled() && textField.getText() != "")
					{
						cantLocales = textField.getText();
						
						try {
						Conexion conexion = new Conexion();				        
				        Connection con = conexion.getConexionMYSQL();				        
				        Statement st = con.createStatement();	
						String cambio2 = cantLocales;
				        
							st.executeUpdate("UPDATE filtros SET cantidad="+cambio2+" where id = 1");
							st.executeUpdate("UPDATE filtros SET opcion=1 where id = 1");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						System.out.println(cantLocales);
						

						frame.dispose();
						Main frame = new Main();
					}
									
				}
			});
			
			//////////////////////////////////////////////////////////////////////////////////////////////
			
			
			
			//st.executeUpdate("UPDATE filtros SET cantidad="+cambio1+" where id = 1");
		//	st.executeUpdate("UPDATE filtros SET rango=1"+cambio2+" where id= 1");
			
			
			
			
			
			frame.setVisible(true);
			frame.setSize(1195, 720);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setAlwaysOnTop(true);
			frame.setForeground(Color.RED);
			frame.setFont(new Font("Felix Titling", Font.BOLD | Font.ITALIC, 14));
			frame.setTitle("¿Qué te pinta?");
			frame.setBounds(3, 5, 1280, 720);
			
			list.removeAll();
			
			if(opcion == 1)
			{
			list.addItem((String) "Locales ordenados por cercanía con un limite de "+limite2+" boliches");
			}
			if(opcion == 2)
			{
			list.addItem((String) "Locales ordenadas por cercanía con un rango de "+ cuadras2 +" metros aprox.");
			}
			
			list.addItem((String) " ");
			
			for(int j = 0; j < listaLocales2.size(); j++)
			{
				//list.addItem((String) "");	
				String nombreb =((LocalBailable) listaLocales2.get(j)).getNombre();
				double distancia = (int)(((LocalBailable) listaLocales2.get(j)).getDistancia()*100000);
				
				
				
				list.addItem((String) ("Nº"+(j+1)+":  "+nombreb+", a unos  "+distancia+"metros"));	
			}
		
	        
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		
		
			
		
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
