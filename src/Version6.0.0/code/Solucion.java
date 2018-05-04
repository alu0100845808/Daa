package code;

import java.util.ArrayList;

public class Solucion {

    private ArrayList<Maquina> machineList;
    
    public Solucion() {
    		setMachineList(new ArrayList<Maquina>());
    }
    
    public Solucion(Solucion solucion) {
    		machineList = new ArrayList<Maquina>();
    		for (int i = 0; i < solucion.getMachineList().size(); i++) {
    			machineList.add(new Maquina(solucion.getMachineList().get(i)));
		}
    }
    
    public Solucion(ArrayList<Maquina> actualML) {
    		setMachineList(actualML);
    }
    
    public Solucion(Integer size) {
    		setMachineList(new ArrayList<Maquina>(size));
	    	for(int i = 0; i < size; i++) {
	    		getMachineList().add(new Maquina());
	    	}
    }
    
    /******************************************************************************/
	/*									ENTORNOS									*/
	/******************************************************************************/
	
    
    /*********************************INTRA MAQUINA********************************/
	public Solucion BLIntraPM(Instancia initInstance) {
		Solucion newSolucion = new Solucion(this);
		Integer peorCandidato = getPeorCandidatoID(initInstance);
		Integer peorLatencia = getLatenciaTotal(initInstance);
		Maquina auxMachine = new Maquina(getMachineList().get(peorCandidato)); 
		for(int i = 0; i < getMachineList().get(peorCandidato).getTareasRealizadas().size() - 1; i++) {
			for(int j = i + 1; j < getMachineList().get(peorCandidato).getTareasRealizadas().size() - 1; j++) {
				auxMachine.changePosItem(i, j);
				if(auxMachine.getLatencia(initInstance) < peorLatencia) {
					peorLatencia = auxMachine.getLatencia(initInstance);
					System.out.println("HE MEJORADO");
				}
				else {
					auxMachine.changePosItem(i, j);
				}
			}
		}
		newSolucion.getMachineList().set(peorCandidato, auxMachine);
		return newSolucion;
	}
	
	public Solucion BLIntraGreedy() {
		Solucion newSolucion = new Solucion();
		
		return newSolucion;
	}
	
	/*********************************ENTRE MAQUINA********************************/
	public Solucion BLEntrePM() {
		Solucion newSolucion = new Solucion();
		
		return newSolucion;
	}
	
	public Solucion BLEntreGreedy() {
		Solucion newSolucion = new Solucion();
		
		return newSolucion;
	}
	
	/**********************************REINSERCION*********************************/
	public Solucion BLReinsertPM() {
		Solucion newSolucion = new Solucion();
		
		return newSolucion;
	}
	
	public Solucion BLReinsertGreedy() {
		Solucion newSolucion = new Solucion();
		
		return newSolucion;
	}
	
	/*******************************FUNCIONES AUXILIARES***************************/
	
	 /** Intercambia los elementos itemIDA e itemIDB independientemente de la maquina en la que se encuentre 
	 * @return **/
    public Solucion exchangeMachineItem(Integer itemIDA, Integer itemIDB) {
    		Solucion newSolucion = new Solucion(this);
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
	    	newSolucion.getMachineList().get(machItemA).getTareasRealizadas().set(posItemA, aux2);
	    	newSolucion.getMachineList().get(machItemB).getTareasRealizadas().set(posItemB, aux);
	    	return newSolucion;
    }
    
    /** Pasa el elemento itemIDA a la maquina "machineIDA" en la posicion "machPosA" **/
    public Solucion changeMachineItem(Integer itemIDA, Integer machineIDA, Integer machPosA) {
    		Solucion newSolucion = new Solucion(this);
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
	    	newSolucion.getMachineList().get(machItemA).getTareasRealizadas().remove(posItemA);
	    	newSolucion.	getMachineList().get(machineIDA).getTareasRealizadas().add(machPosA, aux);
	    	return newSolucion;
    }
	
    public Solucion swapItems(Integer itemIDA, Integer itemIDB) {
    		Solucion newSolucion = new Solucion(this);
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
	    	Tarea tareaA = getMachineList().get(machItemA).getTareasRealizadas().get(posItemA);
	    	Tarea tareaB = getMachineList().get(machItemB).getTareasRealizadas().get(posItemB);
	    	
	    	newSolucion.getMachineList().get(machItemA).getTareasRealizadas().remove(posItemA);
	    	newSolucion.getMachineList().get(machItemA).getTareasRealizadas().remove(posItemB);
	    	
	    	newSolucion.	getMachineList().get(machItemA).getTareasRealizadas().add(machItemA, tareaB);
	    	newSolucion.	getMachineList().get(machItemB).getTareasRealizadas().add(machItemB, tareaA);
	    
    		return newSolucion;
    }
    
    /** Obtener latencia de una m�quina en concreto **/
	public Integer getLatenciaFromMachine(Integer machineIDA, Instancia initInstance) {
		return getMachineList().get(machineIDA).getLatencia(initInstance);
	}
		
	/** Obtener latencia total **/
	public Integer getLatenciaTotal(Instancia initInstance) {
		if(getMachineList().size() > 0) {
			int LatenciaTotal = 0;
	    		for(int i = 0; i < getMachineList().size(); i++) {
	    				LatenciaTotal += getLatenciaFromMachine(i, initInstance);
	    		}
	    		return LatenciaTotal;
		}
		return 0;
	}
	
	/** Devuelve el ID de la maquina con menor latencia **/
	public Integer getCandidatoID(Instancia initInstance) {
		Integer ID = 0;
		if(getMachineList().size() > 0) {
			Integer min = getLatenciaFromMachine(ID, initInstance);
			for(int i = 1; i < getMachineList().size(); i++) {
				if(min > getLatenciaFromMachine(i, initInstance)) {
					ID = i;
					min = getLatenciaFromMachine(i, initInstance);
    			}
			}
		}
		return ID;
	}

	/** Devuelve el ID de la maquina con mayor latencia **/
	public Integer getPeorCandidatoID(Instancia initInstance) {
		Integer ID = 0;
		if(getMachineList().size() > 0) {
			Integer max = getLatenciaFromMachine(ID, initInstance);
			for(int i = 1; i < getMachineList().size(); i++) {
				if(max < getLatenciaFromMachine(i, initInstance)) {
					ID = i;
					max = getLatenciaFromMachine(i, initInstance);
    			}
			}
		}
		return ID;
	}
    
	/******************************************************************************/
    
	public ArrayList<Maquina> getMachineList() { return machineList; }
	public void setMachineList(ArrayList<Maquina> machineList) { this.machineList = machineList; }
	
}
