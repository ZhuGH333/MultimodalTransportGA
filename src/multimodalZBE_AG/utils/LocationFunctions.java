package multimodalZBE_AG.utils;

import multimodalZBE_AG.data.Data;

public class LocationFunctions 
{	
	public static float manhattanDistance(Location l1, Location l2)
	{ return Math.abs(l1.posX - l2.posX) + Math.abs(l1.posY - l2.posY); }
	
	public static float calculateTime(Location l1, Location l2)
	{
		if      (l1.type == LocationType.Point && l2.type == LocationType.Parking)   return manhattanDistance(l1, l2) / Data.CAR_SPEED * 60.0f;
		else if (l1.type == LocationType.Bicycle && l2.type == LocationType.Bicycle) return manhattanDistance(l1, l2) / Data.BICYCLE_SPEED * 60.0f;
		else                                                                		 return manhattanDistance(l1, l2) / Data.WALK_SPEED *60.0f;
	}
	
	public static float calculateComfort(Location l1, Location l2)
	{
		if      (l1.type == LocationType.Point && l2.type == LocationType.Parking)   return Data.COMFORT_CAR;
		else if (l1.type == LocationType.Bicycle && l2.type == LocationType.Bicycle) return Data.COMFORT_BICYCLE;
		else if (l1.type == LocationType.Bus && l2.type == LocationType.Bus && Data.getTime(l1.id, l2.id) != -1)
			return Data.COMFORT_BUS;
		else if (l1.type == LocationType.Metro && l2.type == LocationType.Metro && Data.getTime(l1.id, l2.id) != -1)
			return Data.COMFORT_METRO;
		else return Data.COMFORT_WALKING;
	}
	
	public static float calculateEmission(Location l1, Location l2)
	{
		if      (l1.type == LocationType.Point && l2.type == LocationType.Parking)	 return Data.CAR_EMISSION * manhattanDistance(l1, l2);
		else if (l1.type == LocationType.Bicycle && l2.type == LocationType.Bicycle) return Data.BICYCLE_EMISSION * manhattanDistance(l1, l2);
		else if (l1.type == LocationType.Bus && l2.type == LocationType.Bus && Data.getTime(l1.id, l2.id) != -1)
			return Data.BUS_EMISSION;
		else if (l1.type == LocationType.Metro && l2.type == LocationType.Metro && Data.getTime(l1.id, l2.id) != -1)
			return Data.METRO_EMISSION;
		else                                                                         return Data.WALK_EMISSION * manhattanDistance(l1, l2);
	}
	
	public static float calculateCost(Location l1, Location l2)
	{
		if      (l1.type == LocationType.Point && l2.type == LocationType.Parking)   return manhattanDistance(l1, l2) * Data.CAR_COST;
		else if (l1.type == LocationType.Bicycle && l2.type == LocationType.Bicycle) return Data.BICYCLE_COST;
		else if (l1.type == LocationType.Bus && l2.type == LocationType.Bus && Data.getTime(l1.id, l2.id) != -1)
			return Data.BUS_COST;
		else if (l1.type == LocationType.Metro && l2.type == LocationType.Metro && Data.getTime(l1.id, l2.id) != -1)
			return Data.METRO_COST;
		else  return 0.0f;
	}
}