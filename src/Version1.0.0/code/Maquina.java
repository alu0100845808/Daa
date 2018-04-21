package code;

import java.util.ArrayList;
import java.util.Collections;

public class Maquina {
	
	private ArrayList<Tarea> tareasRealizadas;
	
	public Maquina() {
		setTareasRealizadas(new ArrayList<Tarea>());
	}
	
	public Tarea getTarea(int i) {
		try { return getTareasRealizadas().get(i); }
		catch(IllegalArgumentException iaex) { 
			System.out.println("No existe la tarea con índice i = " + i + "(ERROR: Out of bounds)."); 
			return null;
		}
	}
	
	/** Método de intercambio. Sigue el siguiente proceso:
	 * 
	 *  Tarea aux = getTareasRealizadas().get(i);
	 *	Tarea aux2 = getTareasRealizadas().get(j);
	 *  getTareasRealizadas().set(i, aux2);
	 *  getTareasRealizadas().set(j, aux);
	 *  
	 **/
	
	public void changePosItem(int i, int j){
		try { Collections.swap(getTareasRealizadas(), i, j); }
		catch(IllegalArgumentException iaex){
			System.out.println("No se ha podido intercambiar la tarea i: " + i + " con la j: " + j + ".");
		}
	}
	
	// ToDo: Corregir ambas funciones.
	/** 
		public Integer getTimeTotal() {
			Integer aux = 0;
			for (int i = 0; i < getTareasRealizadas().size(); i++) {
				aux += getLatencia(i);
			}
			return aux;
		}

		public Integer getLatencia(int index) {
			Integer aux = 0;
			for(int i = index; i >= 0; i--) {
				aux +=  getTarea(i).getTime();
			}
			return aux;
		}
	**/

	public ArrayList<Tarea> getTareasRealizadas(){ return tareasRealizadas; }
	public void setTareasRealizadas(ArrayList<Tarea> tareasRealizadas){ this.tareasRealizadas = tareasRealizadas; }

}
