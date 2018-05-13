package code;
// http://www.net2plan.com/teaching/files/PyGRC/P4/P4_planif_main.pdf
import java.util.ArrayList;

public class TS extends Algorithm{

	private ArrayList<Pair<Integer, Integer>> tabuList;
	private int sizeTabuList = 0;
	
	public TS(Instancia initInst, int sizeTabuList, int numIter) {
		super(initInst, "TABÚ", numIter);
		setSizeTabuList(sizeTabuList);
	}

	@Override
	public void exec() {
		long time_start, time_end;
		time_start = System.currentTimeMillis();
		conseguirSolucion();
		Solucion bestSolution = new Solucion(getSolution());
		tabuList = new ArrayList<Pair<Integer, Integer>>();
		boolean entorno = false;
		for(int i = 0; i < getN_ITERACIONES(); i++) {
			Pair<Integer, Integer> mejorVecino;
			if(entorno) {
				mejorVecino = new Pair<Integer, Integer>(bestSolution.getBestExchangeFromSolution(getInitialInstance(), tabuList));
			}
			else {
				mejorVecino = new Pair<Integer, Integer>(bestSolution.getBestIntramaquina(getInitialInstance(), tabuList));
			}
			
			entorno = !entorno;
			
			if(!tabuList.contains(mejorVecino)) {
				setSolution(getSolution().exchangeMachineItem(mejorVecino.getLeft(), mejorVecino.getRight()));
				if(getSolution().getLatenciaTotal(getInitialInstance()) < bestSolution.getLatenciaTotal(getInitialInstance())) {
					bestSolution = new Solucion(getSolution());
				}
				tabuList.add(new Pair<Integer, Integer>(mejorVecino));
				if(tabuList.size() > getSizeTabuList()) {
					tabuList.remove(0);
				}
			}
		}
		setSolution(new Solucion(bestSolution));
		time_end = System.currentTimeMillis();
		setTiempoEjec(time_end - time_start);
	}
	
	public void conseguirSolucion() {
		GraspAlgorithm gr = new GraspAlgorithm(new Instancia(getInitialInstance()));
		gr.exec();
		setSolution(new Solucion(gr.getSolution()));
	}

	public ArrayList<Pair<Integer, Integer>> getTabuList() { return tabuList; }

	public void setTabuList(ArrayList<Pair<Integer, Integer>> tabuList) { this.tabuList = tabuList; }

	public int getSizeTabuList() {
		return sizeTabuList;
	}

	public void setSizeTabuList(int sizeTabuList) {
		this.sizeTabuList = sizeTabuList;
	}
	
}
