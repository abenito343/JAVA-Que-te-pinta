import java.io.IOException;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

public class Geolocation 
{
	public Coordenadas ObtenerLocalizacion(String strIP)
	{
		LookupService cl;
		try {
			cl = new LookupService("C:/Users/ariem/workspace/AAB/GeoLiteCity.dat",
					LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);
			
			Location location = cl.getLocation(strIP);
			
			Coordenadas coord = new Coordenadas(location.latitude, location.longitude);
			
			return coord;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return null;
	}

}
