package multimodalZBE_AG.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import multimodalZBE_AG.data.DataPath;

public class CSVReader 
{
	public static float[] ReadUserInfoCSV() 
	{
		 String file = DataPath.USER_INFO_PATH;
	     String separator = ";";
	     float[] userInfo = new float[8];

	     int actIndex = 0;
	     try (BufferedReader br = new BufferedReader(new FileReader(file))) 
	     {
	    	 String line;

	         while ((line = br.readLine()) != null) 
	         {
	        	 if (line.length() < 1) continue;
	             String[] columns = line.split(separator);

	             userInfo[actIndex] = Float.parseFloat(columns[1]);
	             actIndex++;
	         }
	     } catch (IOException e) { e.printStackTrace(); }
	     
	     return userInfo;
	 }

	
	public static Location[] ReadLocationsCSV() 
	{
		 String file = DataPath.LOCATIONS_PATH;
	     String separator = ";";
	     boolean skipFirstLine = true;
	     int numLocations = 0;
	     Location[] locations;
	     
	     try (BufferedReader br = new BufferedReader(new FileReader(file))) 
	     {
	    	 String line;
	    	 
	    	 while ((line = br.readLine()) != null) 
	    	 {
	    		 if (line.length() < 1) continue;
	    		 if (skipFirstLine) { skipFirstLine = false; continue; }
	    		 numLocations++;
	    	 }
	     } catch (IOException e) { e.printStackTrace(); }

	     locations = new Location[numLocations];
	     skipFirstLine = true;
	     
	     int actIndex = 0;
	     try (BufferedReader br = new BufferedReader(new FileReader(file))) 
	     {
	    	 String line;

	         while ((line = br.readLine()) != null) 
	         {
	        	 if (line.length() < 1) continue;
	        	 if (skipFirstLine) { skipFirstLine = false; continue; }
	             String[] columns = line.split(separator);

	             locations[actIndex] = new Location(
	            		 columns[0], 
	            		 actIndex, 
	            		 getLocationType(columns[1]), 
	            		 Integer.parseInt(columns[2]), 
	            		 Float.parseFloat(columns[3]), 
	            		 Float.parseFloat(columns[4]));
	             actIndex++;
	         }
	     } 
	     catch (IOException e) { e.printStackTrace(); }
	     	     
	     return locations;
	}
	
	private static LocationType getLocationType(String type)
	{
		switch (type) {
		case "Point": 	{ return LocationType.Point; 	}
		case "Parking": { return LocationType.Parking; 	}
		case "Bus": 	{ return LocationType.Bus; 		}
		case "Bicycle": { return LocationType.Bicycle; 	}
		case "Metro": 	{ return LocationType.Metro; 	}
		default: 		{ return LocationType.Point; 	}
		}
	}
	
	public static float[][] ReadTableCSV(String path, int numLocations) 
	{
		 String file = path;
	     String separator = ";";
	     boolean skipFirstLine = true;
	     float[][] values = new float[numLocations][numLocations];

	     int actIndex = 0;
	     try (BufferedReader br = new BufferedReader(new FileReader(file))) 
	     {
	    	 String line;

	         while ((line = br.readLine()) != null) 
	         {
	        	 if (line.length() < 1) continue;
	        	 if (skipFirstLine) { skipFirstLine = false; continue; }
	             String[] columns = line.split(separator);

	             for (int i = 1; i < columns.length; ++i)
	            	 if (columns[i].length() > 0) values[actIndex][i-1] = Float.parseFloat(columns[i]);
	            	 else values[actIndex][i-1] = -1;
	             actIndex++;
	         }
	     } 
	     catch (IOException e) { e.printStackTrace(); }
	     	     
	     return values;
	}
}
