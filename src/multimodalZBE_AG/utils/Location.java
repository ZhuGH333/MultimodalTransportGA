package multimodalZBE_AG.utils;

public class Location 
{
	public String name;
	public int id;
	public LocationType type;
	public int emissionLevel;
	public float posX;
	public float posY;
	
	public Location(String name, int id, LocationType type, int emissionLevel, float posX, float posY)
	{
		this.name = name;
		this.id = id;
		this.type = type;
		this.emissionLevel = emissionLevel;
		this.posX = posX;
		this.posY = posY;
	}
	
	@Override
	public String toString() 
	{
		String res = "-------------------";
		res += "\nName: " + this.name;
		res += "\nID: " + this.id;
		res += "\nType: " + getTypeString(this.type);
		res += "\nEmission Level: " + this.emissionLevel;
		res += "\nCoordinate: (" + this.posX + ", " + this.posY + ")";
		res += "\n-------------------\n";
		return res;
	}
	
	private String getTypeString(LocationType type)
	{
		switch(type)
		{
		case LocationType.Point: 	{ return "Point"; 	}
		case LocationType.Parking: 	{ return "Parking"; }
		case LocationType.Bus:		{ return "Bus";		}
		case LocationType.Bicycle:	{ return "Bicycle"; }
		case LocationType.Metro:	{ return "Metro";	}
		default: 					{ return ""; 		}
		}
	}
}
