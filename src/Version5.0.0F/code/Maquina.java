package code;

import java.util.ArrayList;
import java.util.Collections;

public class Maquina {
	
	private ArrayList<Tarea> tareasRealizadas;
	
	public Maquina() {
		setTareasRealizadas(new ArrayList<Tarea>());
	}
	
	public Maquina(Maquina maquina) {
		setTareasRealizadas(new ArrayList<Tarea>());
		for(int i = 0; i < maquina.getTareasRealizadas().size(); i++) {
			getTareasRealizadas().add(i, maquina.getTareasRealizadas().get(i));
		}
	}
	
	public Tarea getTarea(int i) {
		try { return getTareasRealizadas().get(i); }
		catch(IllegalArgumentException iaex) { 
			System.out.println("No existe la tarea con índice i = " + i + "(ERROR: Out of bounds)."); 
			return null;
		}
	}
	
	public void addTarea(int index, Tarea tarea) {
		getTareasRealizadas().add(index, tarea);
	}
	
	public void changePosItem(int i, int j){
		try { Collections.swap(getTareasRealizadas(), i, j); }
		catch(IllegalArgumentException iaex){
			System.out.println("No se ha podido intercambiar la tarea i: " + i + " con la j: " + j + ".");
		}
	}
	
	public Integer getLatencia(Instancia instancia) {
		Integer latTotal = 0;
		Integer size = getTareasRealizadas().size();
		if(size > 0) {
			for(int i = size - 1; i > 0; i--) {
				latTotal += (getTareasRealizadas().get(i).getTime() * (size - i));
				latTotal += (instancia.getPreparacionItem(getTareasRealizadas().get(i - 1).getID(), getTareasRealizadas().get(i).getID()) * (size - i));
			}
			latTotal += (getTareasRealizadas().get(0).getTime() * size);
		}
		return latTotal;
	}

	public ArrayList<Tarea> getTareasRealizadas(){ return tareasRealizadas; }
	public void setTareasRealizadas(ArrayList<Tarea> tareasRealizadas){ this.tareasRealizadas = tareasRealizadas; }

}
