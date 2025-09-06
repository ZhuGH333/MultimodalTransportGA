package multimodalZBE_AG.data;

import multimodalZBE_AG.utils.*;

public class Data 
{
	private static int numLocations = 0;

	public static final float WALK_SPEED       = 4.0f;
	public static final float CAR_SPEED        = 30.0f;
	public static final float BICYCLE_SPEED    = 16.0f;
	public static final float WALK_EMISSION    = 1.0f;
	public static final float BICYCLE_EMISSION = 1.0f;
	public static final float CAR_EMISSION     = 104.0f;
	public static final float BUS_EMISSION     = 68.0f;
	public static final float METRO_EMISSION   = 14.0f;
	public static final float COMFORT_WALKING  = 1.0f;
	public static final float COMFORT_BICYCLE  = 2.0f;
	public static final float COMFORT_CAR      = 3.0f;
	public static final float COMFORT_BUS      = 4.0f;
	public static final float COMFORT_METRO    = 5.0f;
	public static final float CAR_COST         = 0.10f;
	public static final float BICYCLE_COST     = 0.50f;
	public static final float BUS_COST         = 1.50f;
	public static final float METRO_COST       = 2.50f;
	
	private static int   userEmissionLevel = 0;
	private static float timeMaxExpect     = 60.0f;
	private static float timeWeight 	   = 0.25f;
	private static float comfortMinExpect  = 1.0f;
	private static float comfortWeight 	   = 0.25f;
	private static float emissionWeight    = 0.25f;
	private static float costMaxExpect     = 2.0f;
	private static float costWeight 	   = 0.25f;
	
	private static Location[] locations;
	
	private static float[][] time;
	private static float[][] normalizedTime;
	
	private static float[][] comfort;
	private static float[][] normalizedComfort;
	
	private static float[][] emission;
	private static float[][] normalizedEmission;
	
	private static float[][] cost;
	private static float[][] normalizedCost;
	
	private static boolean initialized = false;
	
	public static Location getLocation(int id)
	{ return locations[id]; }
	
	public static int getNumLocations()
	{ return locations.length; }
	
	public static int getUserEmissionLevel()
	{ return userEmissionLevel; }
	
	public static float getNormalizedTime(int id1, int id2)
	{ return normalizedTime[id1][id2]; }
	
	public static float getTime(int id1, int id2)
	{ return time[id1][id2]; }
	
	public static float getNormalizedComfort(int id1, int id2)
	{ return normalizedComfort[id1][id2]; }
	
	public static float getComfort(int id1, int id2)
	{ return comfort[id1][id2]; }
	
	public static float getNormalizedEmission(int id1, int id2)
	{ return normalizedEmission[id1][id2]; }
	
	public static float getEmission(int id1, int id2)
	{ return emission[id1][id2]; }
	
	public static float getNormalizedCost(int id1, int id2)
	{ return normalizedCost[id1][id2]; }
	
	public static float getCost(int id1, int id2)
	{ return cost[id1][id2]; }
	
	public static void initializeData()
	{
		if (initialized) return;
		
		initializeUserValues();
		
		createLocations();

		initializeArrays();

		addArraysValues();	
		
		calculateUnassignedValues();
		
		createNormalizedArrays();
		
		initialized = true;
	}
	
	private static void initializeUserValues()
	{
		float weightSum;
		float[] userInfo = CSVReader.ReadUserInfoCSV();
		
		userEmissionLevel = (int)userInfo[0];
		
		timeWeight 		= userInfo[1];
		comfortWeight 	= userInfo[2];
		emissionWeight 	= userInfo[3];
		costWeight 		= userInfo[4];
		
		weightSum = timeWeight + comfortWeight + emissionWeight + costWeight;
		timeWeight     /= weightSum;
		comfortWeight  /= weightSum;
		emissionWeight /= weightSum;
		costWeight     /= weightSum;
		
		timeMaxExpect    = userInfo[5];
		comfortMinExpect = userInfo[6];
		costMaxExpect    = userInfo[7];
	}
	
	
	private static void createLocations()
	{ 
		locations = CSVReader.ReadLocationsCSV(); 
		numLocations = locations.length;
	}

	private static void addArraysValues()
	{
		time 	 = CSVReader.ReadTableCSV(DataPath.TIMES_PATH, numLocations);

		comfort  = CSVReader.ReadTableCSV(DataPath.COMFORTS_PATH, numLocations);
		
		emission = CSVReader.ReadTableCSV(DataPath.EMISSIONS_PATH, numLocations);
		
		cost 	 = CSVReader.ReadTableCSV(DataPath.COSTS_PATH, numLocations);
	}
	
	private static void initializeArrays()
	{
		time = new float[numLocations][numLocations];
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				if (i != j) time[i][j] = -1; else time[i][j] = 0;
		
		comfort = new float[numLocations][numLocations];
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				if (i != j) comfort[i][j] = -1; else comfort[i][j] = 0;
		
		emission = new float[numLocations][numLocations];
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				if (i != j) emission[i][j] = -1; else emission[i][j] = 0;
		
		cost = new float[numLocations][numLocations];
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				if (i != j) cost[i][j] = -1; else cost[i][j] = 0;
		
		normalizedTime = new float[numLocations][numLocations];
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				normalizedTime[i][j] = -1;
		
		normalizedComfort = new float[numLocations][numLocations];
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				normalizedComfort[i][j] = -1;
		
		normalizedEmission = new float[numLocations][numLocations];
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				normalizedEmission[i][j] = -1;
		
		normalizedCost = new float[numLocations][numLocations];
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				normalizedCost[i][j] = -1;
	}
	
	private static void calculateUnassignedValues()
	{	
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				if (comfort[i][j] == -1) comfort[i][j] = LocationFunctions.calculateComfort(getLocation(i), getLocation(j));
		
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				if (emission[i][j] == -1) emission[i][j] = LocationFunctions.calculateEmission(getLocation(i), getLocation(j));
				else emission[i][j] *= LocationFunctions.manhattanDistance(locations[i], locations[j]);
		
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				if (cost[i][j] == -1) cost[i][j] = LocationFunctions.calculateCost(getLocation(i), getLocation(j));
		
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				if (time[i][j] == -1) time[i][j] = LocationFunctions.calculateTime(getLocation(i), getLocation(j));
	}
	
	private static void createNormalizedArrays()
	{
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				normalizedTime[i][j] = time[i][j] / Math.max(timeMaxExpect, 1.0f) * timeWeight;
		
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				normalizedComfort[i][j] = (5.0f - comfort[i][j]) / Math.max(comfortMinExpect, 0.01f) * comfortWeight;
				
		float maxEmission = -1;
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				if (maxEmission == -1 || emission[i][j] > maxEmission) maxEmission = emission[i][j];
		
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				normalizedEmission[i][j] = emission[i][j] / maxEmission * emissionWeight;
		
		for (int i = 0; i < numLocations; ++i)
			for (int j = 0; j < numLocations; ++j)
				normalizedCost[i][j] = cost[i][j] / Math.max(costMaxExpect, 0.000001f) * costWeight;
	}
}