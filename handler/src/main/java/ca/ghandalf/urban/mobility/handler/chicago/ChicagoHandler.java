package ca.ghandalf.urban.mobility.handler.chicago;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.springframework.stereotype.Component;

@Component
public class ChicagoHandler {

	public void getData() {
		String path = "data";
		String filename = "google_transit.zip";
		String url = "https://www.transitchicago.com/downloads/sch_data/" + filename;
		
		try (BufferedInputStream input = new BufferedInputStream(new URL(url).openStream());
			 FileOutputStream output = new FileOutputStream(path + "/" + filename) ) {
			
			byte[] data = new byte[1024];
			
			int currentByte;
			while ( (currentByte = input.read(data, 0, 1024)) != -1 ) {
				output.write(data, 0, currentByte);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
