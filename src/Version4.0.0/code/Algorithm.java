package code;

import java.util.ArrayList;

public class Algorithm {

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
	
    /** Intercambia los elementos itemIDA e itemIDB independientemente de la máquina en la que se encuentre **/
    public void exchangeMachineItem(Integer itemIDA, Integer itemIDB) {
    	int posItemA = 0, machItemA = 0, posItemB = 0, machItemB = 0;
    	for(int i = 0; i < getMachineList().size(); i++) {
    		for(int j = 0; j < getMachineList().get(i).getTareasRealizadas().size(); j++) {
    			if(getMachineList().get(i).getTareasRealizadas().get(j).getID().equals(itemIDA)) {
    				posItemA = j;
    				machItemA = i;
    			}
    			if(getMachineList().get(i).getTareasRealizadas().get(j).getID().equals(itemIDB)) {
    				posItemB = j;
    				machItemB = i;
    			}
    		}
    	}
    	Tarea aux = getMachineList().get(machItemA).getTareasRealizadas().get(posItemA);
    	Tarea aux2 = getMachineList().get(machItemB).getTareasRealizadas().get(posItemB);
    	getMachineList().get(machItemA).getTareasRealizadas().set(posItemA, aux2);
    	getMachineList().get(machItemB).getTareasRealizadas().set(posItemB, aux);
    }
    
    /** Pasa el elemento itemIDA a la máquina "machineIDA" en la posición "machPosA" **/
    public void changeMachineItem(Integer itemIDA, Integer machineIDA, Integer machPosA) {
    	int posItemA = 0, machItemA = 0;
    	for(int i = 0; i < getMachineList().size(); i++) {
    		for(int j = 0; j < getMachineList().get(i).getTareasRealizadas().size(); j++) {
    			if(getMachineList().get(i).getTareasRealizadas().get(j).getID().equals(itemIDA)) {
    				posItemA = j;
    				machItemA = i;
    			}
    		}
    	}
    	Tarea aux = getMachineList().get(machItemA).getTareasRealizadas().get(posItemA);
    	getMachineList().get(machItemA).getTareasRealizadas().remove(posItemA);
    	getMachineList().get(machineIDA).getTareasRealizadas().add(machPosA, aux);
    }
    
    /** Obtener latencia de una máquina en concreto **/
	public Integer getLatenciaFromMachine(Integer machineIDA) {
		/*Integer latTotal = 0;
		Integer size = getMachineList().get(machineIDA).getTareasRealizadas().size();
		if(size > 0) {
			for(int i = size - 1; i > 0; i--) {
				latTotal += (getMachineList().get(machineIDA).getTareasRealizadas().get(i).getTime() * (size - i));
				latTotal += (getInitialInstance().getPreparacionItem(i-1, i) * (size - i));
			}
			latTotal += (getMachineList().get(machineIDA).getTareasRealizadas().get(0).getTime() * size);
		}
		return latTotal;*/
		/** ToDo Cambiada la funcion calcular latencia */
		return getMachineList().get(machineIDA).getLatencia(getInitialInstance());
	}
	
	
	/** Obtener latencia total **/
	public Integer getLatenciaTotal() {
		if(getMachineList().size() > 0) {
			int maxID = 0;
    		Integer latTotal = getLatenciaFromMachine(maxID);
    		for(int i = 1; i < getMachineList().size(); i++) {
    			if(latTotal < getLatenciaFromMachine(i)) {
    				maxID = i;
    				latTotal = getLatenciaFromMachine(i);
    			}
    		}
    		return latTotal;
		}
		return 0;
	}
	
	/** Devuelve el ID de la maquina con menor latencia **/
	public Integer getCandidatoID() {
		Integer ID = 0;
		if(getMachineList().size() > 0) {
			Integer min = getLatenciaFromMachine(ID);
			for(int i = 1; i < getMachineList().size(); i++) {
				if(min > getLatenciaFromMachine(i)) {
					ID = i;
					min = getLatenciaFromMachine(i);
    			}
			}
		}
		return ID;
	}

	/** Devuelve el ID de la maquina con mayor latencia **/
	public Integer getPeorCandidatoID() {
		Integer ID = 0;
		if(getMachineList().size() > 0) {
			Integer max = getLatenciaFromMachine(ID);
			for(int i = 1; i < getMachineList().size(); i++) {
				if(max < getLatenciaFromMachine(i)) {
					ID = i;
					max = getLatenciaFromMachine(i);
    			}
			}
		}
		return ID;
	}
	
	public Instancia getInitialInstance() { return initialInstance; }
	public ArrayList<Maquina> getMachineList() { return solucion.getMachineList(); }
	public ArrayList<Tarea> getActualTareas() { return actualTareas; }
	public Resultado getFinalResult() { return finalResult; }
	public void setInitialInstance(Instancia initialInstance) { this.initialInstance = initialInstance; }
	public void setMachineList(ArrayList<Maquina> machineList) { solucion.setMachineList(machineList); }
	public void setActualTareas(ArrayList<Tarea> actualTareas) { this.actualTareas = actualTareas; }
	public void setFinalResult(Resultado finalResult) { this.finalResult = finalResult; } 
	public Solucion getSolution() { return solucion; }
	public void setSolution(Solucion solution) { this.solucion = solution; }
}
