import java.util.Vector;


public class Maquina {
	
	//vector en el que se se almacenan las tareas en el orden de ejecución
	private Vector<Tarea> tareasrealizadas;
	
	
	
	
	
	public Integer getTimeTotal(){
		Integer aux = 0;
		for (int i = 0; i < tareasrealizadas.size();i++ ){
			aux += tareasrealizadas[i].getTime();
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
