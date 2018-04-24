package code;

import java.util.ArrayList;

public class Algorithm {

	private Instancia initialInstance;
	private ArrayList<Maquina> MachineList;
	private ArrayList<Tarea> actualTareas;
	private Resultado finalResult;
	
	public Algorithm(Instancia initInst) {
		setInitialInstance(initInst);
		setMachineList(new ArrayList<Maquina>(initInst.getNumeroMaquinas()));
		setActualTareas(initInst.getTareas());
		setFinalResult(null);
	}
	
    /** Intercambia los elementos itemIDA e itemIDB independientemente de la m�quina en la que se encuentre **/
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
    
    /** Pasa el elemento itemIDA a la m�quina "machineIDA" en la posici�n "machPosA" **/
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
    
    /** Obtener latencia de una m�quina en concreto **/
	public Integer getLatenciaFromMachine(Integer machineIDA) {
		Integer latTotal = 0;
		switch(getMachineList().get(machineIDA).getTareasRealizadas().size()) {
		    case 0: break;
			case 1: latTotal += getMachineList().get(machineIDA).getTareasRealizadas().get(0).getTime(); break; 
			default: 
    			latTotal += getMachineList().get(machineIDA).getTareasRealizadas().get(0).getTime();
    			for(int i = 1; i < getMachineList().get(machineIDA).getTareasRealizadas().size(); i++) {
    				latTotal += getMachineList().get(machineIDA).getTareasRealizadas().get(i).getTime();
    				latTotal += getInitialInstance().getDistanciaItem(getMachineList().get(machineIDA).getTareasRealizadas().get(i-1).getID(), 
    						                     getMachineList().get(machineIDA).getTareasRealizadas().get(i).getID());
    			}
		}
		return latTotal;
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

	public Instancia getInitialInstance() { return initialInstance; }
	public ArrayList<Maquina> getMachineList() { return MachineList; }
	public ArrayList<Tarea> getActualTareas() { return actualTareas; }
	public Resultado getFinalResult() { return finalResult; }
	public void setInitialInstance(Instancia initialInstance) { this.initialInstance = initialInstance; }
	public void setMachineList(ArrayList<Maquina> machineList) { MachineList = machineList; }
	public void setActualTareas(ArrayList<Tarea> actualTareas) { this.actualTareas = actualTareas; }
	public void setFinalResult(Resultado finalResult) { this.finalResult = finalResult; } 
	
}
