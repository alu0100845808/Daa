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
			System.out.println("No existe la tarea con �ndice i = " + i + "(ERROR: Out of bounds)."); 
			return null;
		}
	}
	
	public void addTarea(int index, Tarea tarea) {
		getTareasRealizadas().add(index, tarea);
	}
	
	/** Devuelve la mejor posición donde introducir la tarea*/
	public int bestPos(Tarea tarea, Instancia initInstance) {
		int index = 0;
		Integer latencia = Integer.MAX_VALUE;
		for(int i = 0; i <= getTareasRealizadas().size(); i++) {
			Maquina auxMachine = new Maquina(this);
			auxMachine.addTarea(i, tarea);
			if(auxMachine.getLatencia(initInstance) < latencia) {
				latencia = auxMachine.getLatencia(initInstance);
				index = i;
			}
		}
		
		return index;
	}
	
	public void changePosItem(int i, int j){
		try { Collections.swap(getTareasRealizadas(), i, j); }
		catch(IllegalArgumentException iaex){
			System.out.println("No se ha podido intercambiar la tarea i: " + i + " con la j: " + j + ".");
		}
	}
	
	public Pair<Integer, Integer> getBestChange(Instancia initialInstance) {
		Pair<Integer, Integer> toReturn = new Pair<Integer, Integer>(-1, -1);
		int minLatencia = getLatencia(initialInstance);
		for(int i = 0; i < getTareasRealizadas().size() - 1; i++){
			for(int j = i + 1; j < getTareasRealizadas().size(); j++){
				changePosItem(i, j);
				if(getLatencia(initialInstance) < minLatencia){
					toReturn = new Pair<Integer, Integer>(getTareasRealizadas().get(i).getID(), getTareasRealizadas().get(j).getID());
					minLatencia = getLatencia(initialInstance);
				}
				changePosItem(i, j);
			}
		}
		return toReturn;
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
