package code;
// http://www.net2plan.com/teaching/files/PyGRC/P4/P4_planif_main.pdf
import java.util.ArrayList;

public class TS extends Algorithm{

	private ArrayList<Pair<Integer, Integer>> tabuList;
	
	public TS(Instancia initInst, int sizeTabuList, int numIter) {
		super(initInst, "TABÚ", numIter);
	}

	@Override
	public void exec() {
		conseguirSolucion();
		Solucion bestSolution = new Solucion(getSolution());
		tabuList = new ArrayList<Pair<Integer, Integer>>();
		boolean entorno = false;
		for(int i = 0; i < getN_ITERACIONES(); i++) {
			Pair<Integer, Integer> mejorVecino;
			if(entorno)
				mejorVecino = bestSolution.getBestExchangeFromSolution(getInitialInstance());
			else {
				Integer peorMaquina = bestSolution.getPeorCandidatoID();
				mejorVecino = bestSolution
			}
		}
	}
	
	public void conseguirSolucion() {
		GreedyAlgorithm gr = new GreedyAlgorithm(new Instancia(getInitialInstance()));
		gr.exec();
		setSolution(new Solucion(gr.getSolution()));
	}

	public ArrayList<Pair<Integer, Integer>> getTabuList() { return tabuList; }

	public void setTabuList(ArrayList<Pair<Integer, Integer>> tabuList) { this.tabuList = tabuList; }
}
