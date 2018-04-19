import java.util.ArrayList;


public class Maquina {
	
	//vector en el que se se almacenan las tareas en el orden de ejecución
	private ArrayList<Tarea> tareasrealizadas;
	
	public Maquina() {
		tareasrealizadas = new ArrayList<Tarea>();
	}
	
	public Tarea getTarea(int i) {
		if (i < tareasrealizadas.size()) {
			return tareasrealizadas[i];
		}
		else {
			 throw new RuntimeError("Bad.");
		}
	}
	
	
	public Integer getTimeTotal() {
		Integer aux = 0;
		for (int i = 0; i < tareasrealizadas.size(); i++) {
			aux += getLatencia(i);
		}
		return aux
	}
	
	public Integer getLatencia(int index) {
		
		if(i >= tareasrealizadas.size())
			throw new RuntimeError("Bad.");
			
		Integer aux = 0;
		for(int i = index; i >= 0; i--) {
			aux +=  getTarea(i).getTime();
		}
		return aux;
	}
	
	public Vector<Tarea> getTareasrealizadas() {
		return tareasrealizadas;
	}

	public void setTareasrealizadas(Vector<Tarea> tareasrealizadas) {
		this.tareasrealizadas = tareasrealizadas;
	}
}
