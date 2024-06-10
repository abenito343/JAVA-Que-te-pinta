public class Coordenadas {
	double Latitud2;	
	double Longitud2;
	
	/*public Coordenadas(float latitude, float longitude) {
		
	}*/
	
	public Coordenadas(double dLatitud, double dLongitud) 
	{
		Latitud2 = dLatitud;
		Longitud2 = dLongitud;
	}
	
	public double getLatitud2()
	{
		return Latitud2;
	}
	
	public double getLongitud2()
	{
		return Longitud2;
	}
	
	public void setLatitud2(double dLatitud)
	{
		Latitud2 = dLatitud;
	}
	
	public void setLongitud2(double dLongitud)
	{
		Longitud2 = dLongitud;
	}
}
