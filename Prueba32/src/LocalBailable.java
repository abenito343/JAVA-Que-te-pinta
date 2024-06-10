
public class LocalBailable {
	private String Latitud;
	private String Longitud;
	private String Nombre;
	private String Id;
	private double Distancia;
	
	public void setLatitud( String strLatitud )
	{
		Latitud = strLatitud;
	}
	
	public String getLatitud()
	{
		return Latitud;
	}
	public void setLongitud( String strLongitud )
	{
		Longitud = strLongitud;
	}
	
	public String getLongitud()
	{
		return Longitud;
	}
	
	public void setNombre( String strNombre )
	{
		Nombre = strNombre;
	}
	
	public String getNombre()
	{
		return Nombre;
	}
	
	public void setDistancia( double strDistancia )
	{
		Distancia = strDistancia;
	}
	
	public double getDistancia()
	{
		return Distancia;
	}
	
	public void setId( String strId )
	{
		Id = strId;
	}
	
	public String getId()
	{
		return Id;
	}
	public String getLocalAsString()
	{
		return Nombre + "@" + Longitud + "," + Latitud;
	}
}
