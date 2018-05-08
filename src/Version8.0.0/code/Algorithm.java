package code;

import java.util.ArrayList;

public abstract class Algorithm {
	
	private String nomAlgoritmo = new String();
	private Long time = new Long(0);
	private Instancia initialInstance;
	private Solucion solucion;
	private ArrayList<Tarea> actualTareas;
	private Resultado finalResult;
	
	public Algorithm(Instancia initInst, String nomAlgoritmo) {
		setInitialInstance(new Instancia(initInst));
		setSolution(new Solucion(initInst.getNumeroMaquinas()));
		setActualTareas(initInst.getTareas());
		setFinalResult(null);
		setNomAlgoritmo(nomAlgoritmo);
		setTiempoEjec(new Long(0));
	}
	
	public void printSolucion(String nombreDirectorio) {
		getSolution().imprimirSolucion(nombreDirectorio, getInitialInstance(), getNomAlgoritmo(), getTiempoEjec());
	}
	
	public Instancia getInitialInstance() { return initialInstance; }
	public ArrayList<Tarea> getActualTareas() { return actualTareas; }
	public Resultado getFinalResult() { return finalResult; }
	public Solucion getSolution() { return solucion; }
	public String getNomAlgoritmo() { return nomAlgoritmo; }
	public Long getTiempoEjec() { return time; }
	public void setInitialInstance(Instancia initialIns) { this.initialInstance = initialIns; }
	public void setActualTareas(ArrayList<Tarea> actualTareas) { this.actualTareas = actualTareas; }
	public void setFinalResult(Resultado finalResult) { this.finalResult = finalResult; } 
	public void setSolution(Solucion solution) { this.solucion = solution; }
	public void setNomAlgoritmo(String nomAlgoritmo) { this.nomAlgoritmo = nomAlgoritmo; }
	public void setTiempoEjec(Long time) { this.time = time; }
	public abstract void exec();
}
