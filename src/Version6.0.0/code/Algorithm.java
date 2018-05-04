package code;

import java.util.ArrayList;

public abstract class Algorithm {

	private Instancia initialInstance;
	private Solucion solucion;
	private ArrayList<Tarea> actualTareas;
	private Resultado finalResult;
	
	public Algorithm(Instancia initInst) {
		setInitialInstance(initInst);
		setSolution(new Solucion(initInst.getNumeroMaquinas()));
		setActualTareas(initInst.getTareas());
		setFinalResult(null);
	}
	
	public Instancia getInitialInstance() { return initialInstance; }
	public ArrayList<Tarea> getActualTareas() { return actualTareas; }
	public Resultado getFinalResult() { return finalResult; }
	public Solucion getSolution() { return solucion; }
	public void setInitialInstance(Instancia initialIns) { 
		initialInstance = new Instancia();
		initialInstance.setNumeroMaquinas(initialIns.getNumeroMaquinas());
		initialInstance.setNumeroTareas(initialIns.getNumeroTareas());
		initialInstance.setTPreparacion(initialIns.getTPreparacion());
		initialInstance.setTareas(new ArrayList<Tarea>());
		for(int i = 0; i < initialIns.getTareas().size(); i++) {
			initialInstance.addTarea(new Tarea(initialIns.getTareas().get(i).getID(), initialIns.getTareas().get(i).getTime())  );
		}
	}
	public void setActualTareas(ArrayList<Tarea> actualTareas) { this.actualTareas = actualTareas; }
	public void setFinalResult(Resultado finalResult) { this.finalResult = finalResult; } 
	public void setSolution(Solucion solution) { this.solucion = solution; }
	public abstract void exec();
}
