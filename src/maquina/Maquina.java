package maquina;

import java.util.*;

public class Maquina {
	
	//vector en el que se se almacenan las tareas en el orden de ejecución
	private ArrayList<Tarea> tareasrealizadas;
	
	public Maquina() {
		setTareasrealizadas(new ArrayList<Tarea>());
	}
	
	public Tarea getTarea(int i) {
		if (i < tareasrealizadas.size()) {
			return tareasrealizadas.get(i);
		}
		else {
			 throw new IllegalArgumentException();
		}
	}
	/*
	Duda que se le a realizado a la maestra a espera de que conteste
	
	Hola buenas.
	Tengo una duda acerca de nuestro problema asignado (el 2.3).
	Los datos de entrada son:
	* el numero de tareas
	* el n..... de maquinas
	* tiempo asignado por tarea tarea
	* una matriz n x n
	Dentro de la matriz como debo buscar la latencia entre tareas ¿Es por filas o por columnas?

	Muchas gracias
	 
	
	*/
	public void changePos(int i, int j){
		tarea aux;
		tarea aux2;
	}
	/////esta funcion esta mal
	public Integer getTimeTotal() {
		Integer aux = 0;
		for (int i = 0; i < tareasrealizadas.size(); i++) {
			aux += getLatencia(i);
		}
		return aux;
	}
	//esta funcion esta mal
	public Integer getLatencia(int index) {
		
		if(index >= tareasrealizadas.size())
			throw new IllegalArgumentException();
			
		Integer aux = 0;
		for(int i = index; i >= 0; i--) {
			aux +=  getTarea(i).getTime();
		}
		return aux;
	}
	
	public ArrayList<Tarea> getTareasrealizadas() {
		return tareasrealizadas;
	}

	public void setTareasrealizadas(ArrayList<Tarea> tareasrealizadas) {
		this.tareasrealizadas = tareasrealizadas;
	}
}
