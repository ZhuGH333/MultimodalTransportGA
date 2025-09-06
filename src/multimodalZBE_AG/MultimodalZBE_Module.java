package multimodalZBE_AG;

import org.opt4j.core.problem.ProblemModule;

public class MultimodalZBE_Module extends ProblemModule 
{
	protected void config()	
	{ bindProblem(MultimodalZBE_Creator.class, MultimodalZBE_Decoder.class, MultimodalZBE_Evaluator.class); }
}
