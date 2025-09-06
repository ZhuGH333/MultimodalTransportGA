package multimodalZBE_AG;

import multimodalZBE_AG.data.Data;
import multimodalZBE_AG.utils.Tuple;

import java.util.ArrayList;

import org.opt4j.core.Objectives;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.problem.Evaluator;

public class MultimodalZBE_Evaluator implements Evaluator<ArrayList<Tuple<Integer, String>>> 
{
	public Objectives evaluate(ArrayList<Tuple<Integer, String>> phenotype)
	{
		float globalResult = 0;
		float normComfort = 0;
		float time = 0;
		float comfort = 0;
		float emission = 0;
		float cost = 0;
		
		int prevID = 0;
		int cID;
		for (int i = 0; i < phenotype.size(); ++i)
		{
			cID = phenotype.get(i).x;
			globalResult += Data.getNormalizedTime(prevID, cID) + Data.getNormalizedEmission(prevID, cID) + Data.getNormalizedCost(prevID, cID);
			normComfort  += Data.getNormalizedComfort(prevID, cID);
			time 	 += Data.getTime(prevID, cID);
			comfort  += Data.getComfort(prevID, cID);
			emission += Data.getEmission(prevID, cID);
			cost     += Data.getCost(prevID, cID);
			prevID = cID;
		}
		globalResult += normComfort / phenotype.size();
		
		Objectives objetive = new Objectives();
		objetive.add("Min Global", 		Sign.MIN, 	round4(globalResult));
		objetive.add("Min Time", 		Sign.MIN, 	round4(time));
		objetive.add("Max Comfort", 	Sign.MAX, 	round4((double)comfort / phenotype.size()));
		objetive.add("Min Emission", 	Sign.MIN, 	round4(emission));
		objetive.add("Min Cost", 		Sign.MIN, 	round4(cost));
		
		return objetive;
	}
	private double round4(double value) {
	    return Math.round(value * 10000.0) / 10000.0;
	}

}