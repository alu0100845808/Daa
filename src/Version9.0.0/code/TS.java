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
		conseguirSolucion();
		Solucion bestSolution = new Solucion(getSolution());
		tabuList = new ArrayList<Pair<Integer, Integer>>();
		boolean entorno = false;
		for(int i = 0; i < getN_ITERACIONES(); i++) {
			Pair<Integer, Integer> mejorVecino;
			if(entorno) {
				mejorVecino = new Pair<Integer, Integer>(bestSolution.getBestExchangeFromSolution(getInitialInstance(), tabuList));
				//System.out.println("entre " + mejorVecino.getLeft() + " " + mejorVecino.getRight());
			}
			else {
				mejorVecino = new Pair<Integer, Integer>(bestSolution.getBestIntramaquina(getInitialInstance(), tabuList));
				//System.out.println("intra " + mejorVecino.getLeft() + " " + mejorVecino.getRight());
			}
			
			
			
			entorno = !entorno;
			
			if(!tabuList.contains(mejorVecino)) {
				//System.out.println("no contiene");
				setSolution(getSolution().exchangeMachineItem(mejorVecino.getLeft(), mejorVecino.getRight()));
				if(getSolution().getLatenciaTotal(getInitialInstance()) < bestSolution.getLatenciaTotal(getInitialInstance())) {
					bestSolution = new Solucion(getSolution());
				}
				tabuList.add(new Pair<Integer, Integer>(mejorVecino));
				if(tabuList.size() > getSizeTabuList()) {
					//System.out.println("borra");
					tabuList.remove(0);
				}
			}
			/*
			System.out.println("------------------------------- TS -------------------------------");
			System.out.println("SOLUCI�N INICIAL:");
		    for(int j = 0; j < bestSolution.getMachineList().size(); j++) {
			    System.out.println(bestSolution.getMachineList().get(j).getTareasRealizadas().toString());
			    System.out.println("VALOR TOTAL LATENCIA: " + bestSolution.getMachineList().get(j).getLatencia(getInitialInstance()));
		    }
		    System.out.println("Lista tabú: ");
		    for(int j = 0; j < tabuList.size(); j++) {
		    	System.out.print(" (" + tabuList.get(j).getLeft() + ", " + tabuList.get(j).getRight() + ")");
		    }
		    System.out.println("\nLatencia: " + bestSolution.getLatenciaTotal(getInitialInstance()));
		    System.out.println("==========================================================================");*/
		}
		setSolution(new Solucion(bestSolution));
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
