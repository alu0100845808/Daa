package code;

import java.util.ArrayList;

public class GraspAlgorithm extends Algorithm{

	private ArrayList<Solucion> solutions; 
	
	public GraspAlgorithm(Instancia initInst) {
		super(initInst);
		setSolutions(new ArrayList<Solucion>());
	}
	public void exec() {
		
	}

	public ArrayList<Solucion> getSolutions() { return solutions; }
	public void setSolutions(ArrayList<Solucion> solutions) { this.solutions = solutions; }

}
