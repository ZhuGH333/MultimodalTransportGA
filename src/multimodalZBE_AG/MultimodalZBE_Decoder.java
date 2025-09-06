package multimodalZBE_AG;

import java.util.ArrayList;
import java.util.Random;

import multimodalZBE_AG.data.Data;
import multimodalZBE_AG.utils.*;

import org.opt4j.core.problem.Decoder;
import org.opt4j.core.genotype.IntegerGenotype;

public class MultimodalZBE_Decoder implements Decoder<IntegerGenotype, ArrayList<Tuple<Integer, String>>>
{
	public ArrayList<Tuple<Integer, String>> decode(IntegerGenotype genotype)
	{
		IntegerGenotype phenotype = new IntegerGenotype(2, Data.getNumLocations() - 2);
		ArrayList<Tuple<Integer, String>> finalPhenotype = new ArrayList<>();
		if (genotype.size() == 0) return finalPhenotype;
		Random rand = new Random();
		int actualIndex = 0;
		boolean[] visited = new boolean[Data.getNumLocations()];
		
		for (int i = 0; i < visited.length; ++i) visited[i] = false;
		
		// Si la primera localización es un Parking y no es válido
		if (Data.getLocation(genotype.get(0)).type == LocationType.Parking && 
				Data.getLocation(genotype.get(0)).emissionLevel > Data.getUserEmissionLevel())
		{
			// Un 50% de probabilidad de tratar de buscar el parking alternativo válido más cercano
			if (rand.nextInt(0, 2) == 0)
			{
				float bestDistance = -1;
				int bestParking    = -1;
				for (int i = 0; i < Data.getNumLocations(); ++i)
				{
					if (Data.getLocation(i).type == LocationType.Parking && 
							Data.getLocation(i).emissionLevel <= Data.getUserEmissionLevel())
					{
						float distance = LocationFunctions.manhattanDistance(Data.getLocation(genotype.get(0)), Data.getLocation(i));
						if (bestDistance == -1 || distance < bestDistance)
						{
							bestDistance = distance;
							bestParking  = i;
						}
					}
				}
				if (bestParking != -1) phenotype.add(bestParking);
			}
			actualIndex = 1;
		}
		
		// Se elimina ciclos y parkings intermedios
		for (int i = actualIndex; i < genotype.size(); ++i)
		{
			Location cLoc = Data.getLocation(genotype.get(i));
			
			// Si es parking se salta
			if (cLoc.type == LocationType.Parking && actualIndex != 0) continue;
			
			// Eliminar ciclos
			while (visited[cLoc.id])
				visited[phenotype.removeLast()] = false;
			phenotype.add(cLoc.id);
			visited[cLoc.id] = true;
		}
		//Asegurar que siempre termina en Goal, para que sea una solucion factible
		while (visited[1])
			visited[phenotype.removeLast()] = false;
		phenotype.add(1);
		
		// El uso de la tupla para que despues en la solucion se muestre en vez de los numeros ids de las localizaciones, muestre su nombre (P1, M1, etc)
		for (int i = 0; i < phenotype.size(); ++i)
		{
			finalPhenotype.add(new Tuple<>(phenotype.get(i), Data.getLocation(phenotype.get(i)).name));
		}
		
		return finalPhenotype;
	}
}