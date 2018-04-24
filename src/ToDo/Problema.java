package code;

import java.util.ArrayList;

public class Problema {
	
	private Instancia actualInstance;

	public void greedyAlgorithm() {
		sortTasks();
		//ToDo
	}
	
	public void graspAlgorithm() {
		sortTasks();
		//ToDo
	}
	
	public void sortTasks() {
		ArrayList<Tarea> aux = new ArrayList<Tarea>();
		ArrayList<Tarea> actualTasks = getActualInstance().getTareas();
		while(actualTasks.size() > 0) {
			int minID = 0;
			for(int i = 0; i < actualTasks.size(); i++) {
				if(actualTasks.get(minID).getTime() > actualTasks.get(i).getTime()) {
					minID = i;
				}
			}
			aux.add(actualTasks.get(minID));
			actualTasks.remove(minID);
		}
		getActualInstance().setTareas(aux);
	}
	
	public Instancia getActualInstance() { return actualInstance; }
	public void setActualInstance(Instancia actualInstance) { this.actualInstance = actualInstance; }
}
