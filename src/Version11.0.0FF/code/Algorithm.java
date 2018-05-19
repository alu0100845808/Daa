package code;

import java.util.ArrayList;

public abstract class Algorithm {
	
	private String nomAlgoritmo = new String();
	private Long time = new Long(0);
	private Instancia initialInstance;
	private Solucion solucion;
	private ArrayList<Tarea> actualTareas;
	private Integer N_ITERACIONES = 0;
	
	public Algorithm(Instancia initInst, String nomAlgoritmo) {
		setInitialInstance(new Instancia(initInst));
		setSolution(new Solucion(initInst.getNumeroMaquinas()));
		setActualTareas(initInst.getTareas());
		setNomAlgoritmo(nomAlgoritmo);
		setTiempoEjec(new Long(0));
	}
	
	public Algorithm(Instancia initInst, String nomAlgoritmo, int  N_ITERACIONES) {
		setInitialInstance(new Instancia(initInst));
		setSolution(new Solucion(initInst.getNumeroMaquinas()));
		setActualTareas(initInst.getTareas());
		setNomAlgoritmo(nomAlgoritmo);
		setTiempoEjec(new Long(0));
		setN_ITERACIONES(N_ITERACIONES);
	}
	
	public void printSolucion(String nombreDirectorio) {
		getSolution().imprimirSolucion(nombreDirectorio, getInitialInstance(), getNomAlgoritmo(), getTiempoEjec(), getN_ITERACIONES());
	}
	
	public Instancia getInitialInstance() { return initialInstance; }
	public ArrayList<Tarea> getActualTareas() { return actualTareas; }
	public Solucion getSolution() { return solucion; }
	public String getNomAlgoritmo() { return nomAlgoritmo; }
	public Long getTiempoEjec() { return time; }
	public Integer getN_ITERACIONES() { return N_ITERACIONES; }
	public void setInitialInstance(Instancia initialIns) { this.initialInstance = initialIns; }
	public void setActualTareas(ArrayList<Tarea> actualTareas) { this.actualTareas = actualTareas; }
	public void setSolution(Solucion solution) { this.solucion = solution; }
	public void setNomAlgoritmo(String nomAlgoritmo) { this.nomAlgoritmo = nomAlgoritmo; }
	public void setTiempoEjec(Long time) { this.time = time; }
	public void setN_ITERACIONES(Integer n_ITERACIONES) { N_ITERACIONES = n_ITERACIONES; }
	public abstract void exec();
}
