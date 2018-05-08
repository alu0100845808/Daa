package code;

public class GreedyAlgorithm extends Algorithm {

	private Solucion solution;
	
	public GreedyAlgorithm(Instancia initInst) { 
		super(new Instancia(initInst));
		setSolution(null);
	}
	public void exec() {
		
	}

	public Solucion getSolution() { return solution; }
	public void setSolution(Solucion solution) { this.solution = solution; }
	
}
