package multimodalZBE_AG;

import java.util.Random;

import multimodalZBE_AG.data.Data;

import org.opt4j.core.problem.Creator;
import org.opt4j.core.genotype.IntegerGenotype;

public class MultimodalZBE_Creator implements Creator<IntegerGenotype> 
{
	public IntegerGenotype create()
	{
		Data.initializeData();
		
		// Se inicializa el genotipo de tal forma que sus valores se encuentren entre 1 y el máximo número de localizaciones (todas menos init)
		IntegerGenotype genotype = new IntegerGenotype(1, Data.getNumLocations()-1);
		
		// Se crea la lista de valores con una máxima longitud equivalente a pasar por todas las localizaciones una vez
		genotype.init(new Random(), Data.getNumLocations()-1);
		
		return genotype;
	}
}