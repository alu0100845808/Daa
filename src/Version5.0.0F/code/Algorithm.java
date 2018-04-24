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
    	for(int i = 0; i < getSolution().getMachineList().size(); i++) {
    		for(int j = 0; j < getSolution().getMachineList().get(i).getTareasRealizadas().size(); j++) {
    			if(getSolution().getMachineList().get(i).getTareasRealizadas().get(j).getID().equals(itemIDA)) {
    				posItemA = j;
    				machItemA = i;
    			}
    			if(getSolution().getMachineList().get(i).getTareasRealizadas().get(j).getID().equals(itemIDB)) {
    				posItemB = j;
    				machItemB = i;
    			}
    		}
    	}
    	Tarea aux = getSolution().getMachineList().get(machItemA).getTareasRealizadas().get(posItemA);
    	Tarea aux2 = getSolution().getMachineList().get(machItemB).getTareasRealizadas().get(posItemB);
    	getSolution().getMachineList().get(machItemA).getTareasRealizadas().set(posItemA, aux2);
    	getSolution().getMachineList().get(machItemB).getTareasRealizadas().set(posItemB, aux);
    }
    
    /** Pasa el elemento itemIDA a la máquina "machineIDA" en la posición "machPosA" **/
    public void changeMachineItem(Integer itemIDA, Integer machineIDA, Integer machPosA) {
    	int posItemA = 0, machItemA = 0;
    	for(int i = 0; i < getSolution().getMachineList().size(); i++) {
    		for(int j = 0; j < getSolution().getMachineList().get(i).getTareasRealizadas().size(); j++) {
    			if(getSolution().getMachineList().get(i).getTareasRealizadas().get(j).getID().equals(itemIDA)) {
    				posItemA = j;
    				machItemA = i;
    			}
    		}
    	}
    	Tarea aux = getSolution().getMachineList().get(machItemA).getTareasRealizadas().get(posItemA);
    	getSolution().getMachineList().get(machItemA).getTareasRealizadas().remove(posItemA);
    	getSolution().getMachineList().get(machineIDA).getTareasRealizadas().add(machPosA, aux);
    }
    
    /** Obtener latencia de una máquina en concreto **/
	public Integer getLatenciaFromMachine(Integer machineIDA) {
		return getSolution().getMachineList().get(machineIDA).getLatencia(getInitialInstance());
	}
		
	/** Obtener latencia total **/
	public Integer getLatenciaTotal() {
		if(getSolution().getMachineList().size() > 0) {
			int maxID = 0;
    		Integer latTotal = getLatenciaFromMachine(maxID);
    		for(int i = 1; i < getSolution().getMachineList().size(); i++) {
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
		if(getSolution().getMachineList().size() > 0) {
			Integer min = getLatenciaFromMachine(ID);
			for(int i = 1; i < getSolution().getMachineList().size(); i++) {
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
		if(getSolution().getMachineList().size() > 0) {
			Integer max = getLatenciaFromMachine(ID);
			for(int i = 1; i < getSolution().getMachineList().size(); i++) {
				if(max < getLatenciaFromMachine(i)) {
					ID = i;
					max = getLatenciaFromMachine(i);
    			}
			}
		}
		return ID;
	}
	public Instancia getInitialInstance() { return initialInstance; }
	public ArrayList<Tarea> getActualTareas() { return actualTareas; }
	public Resultado getFinalResult() { return finalResult; }
	public Solucion getSolution() { return solucion; }
	public void setInitialInstance(Instancia initialIns) { 
		initialInstance = new Instancia();
		initialInstance.setNumeroMaquinas(initialIns.getNumeroMaquinas());
		initialInstance.setNumeroTareas(initialIns.getNumeroTareas());
		initialInstance.setTPreparacion(initialIns.getTPreparacion());
		initialInstance.setTareas(new ArrayList<Tarea>());
		for(int i = 0; i < initialIns.getTareas().size(); i++) {
			initialInstance.addTarea(new Tarea(initialIns.getTareas().get(i).getID(), initialIns.getTareas().get(i).getTime())  );
		}
	}
	public void setActualTareas(ArrayList<Tarea> actualTareas) { this.actualTareas = actualTareas; }
	public void setFinalResult(Resultado finalResult) { this.finalResult = finalResult; } 
	public void setSolution(Solucion solution) { this.solucion = solution; }
}
