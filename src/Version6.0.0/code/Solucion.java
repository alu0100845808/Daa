package code;

import java.util.ArrayList;

public class Solucion {

    private ArrayList<Maquina> machineList;
    private final int numEntornos = 6;
    
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
		Integer peorLatencia = getLatenciaTotal(initInstance);
		for(int i = 0; i < getMachineList().size(); i++) {
			for(int j = 0; j < getMachineList().get(i).getTareasRealizadas().size() - 1; j++) {
				for(int k = j + 1; k < getMachineList().get(i).getTareasRealizadas().size() - 1; k++) {
					newSolucion.getMachineList().get(i).changePosItem(j, k);
					if(newSolucion.getLatenciaTotal(initInstance) < peorLatencia) {
						System.out.println("HE MEJORADO");
						return newSolucion;
					}
					newSolucion.getMachineList().get(i).changePosItem(j, k);
				}
			}
		}
		return newSolucion;
	}
	
	public Solucion BLIntraGreedy(Instancia initInstance) {
		Solucion newSolucion = new Solucion(this);
		Solucion betSolucion = new Solucion(this);
		Integer peorLatencia = getLatenciaTotal(initInstance);
		for(int i = 0; i < getMachineList().size(); i++) {
			for(int j = 0; j < getMachineList().get(i).getTareasRealizadas().size() - 1; j++) {
				for(int k = j + 1; k < getMachineList().get(i).getTareasRealizadas().size() - 1; k++) {
					newSolucion.getMachineList().get(i).changePosItem(j, k);
					if(newSolucion.getLatenciaTotal(initInstance) < peorLatencia) {
						peorLatencia = newSolucion.getLatenciaTotal(initInstance);
						betSolucion = new Solucion(newSolucion);
						System.out.println("HE ENCONTRADO UNA MEJOR");
					}
					newSolucion.getMachineList().get(i).changePosItem(j, k);
				}
			}
		}
		return betSolucion;
	}
	
	/*********************************ENTRE MAQUINA********************************/
	public Solucion BLEntrePM(Instancia initInstance) {
		for(int i = 0; i < getMachineList().size() - 1; i++) {
			for(int j = i + 1; j < getMachineList().size(); j++) {
				for(int k = 0; k < getMachineList().get(i).getTareasRealizadas().size(); k++) {
					for(int l = 0; l < getMachineList().get(j).getTareasRealizadas().size(); l++) {
						Solucion auxSolucion = exchangeMachineItem(getMachineList().get(i).getTarea(k).getID(), getMachineList().get(j).getTarea(l).getID());
						if(auxSolucion.getLatenciaTotal(initInstance) < getLatenciaTotal(initInstance)) {
							return auxSolucion;
						}
					}
				}
			}
		}
		return this;
	}
	
	public Solucion BLEntreGreedy(Instancia initInstance) {
		Solucion newSolucion = new Solucion(this);
		for(int i = 0; i < getMachineList().size() - 1; i++) {
			for(int j = i + 1; j < getMachineList().size(); j++) {
				for(int k = 0; k < getMachineList().get(i).getTareasRealizadas().size(); k++) {
					for(int l = 0; l < getMachineList().get(j).getTareasRealizadas().size(); l++) {
						Solucion auxSolucion = exchangeMachineItem(getMachineList().get(i).getTarea(k).getID(), getMachineList().get(j).getTarea(l).getID());
						if(auxSolucion.getLatenciaTotal(initInstance) < newSolucion.getLatenciaTotal(initInstance)) {
							newSolucion = auxSolucion;
						}
					}
				}
			}
		}
		return newSolucion;
	}
	
	/**********************************REINSERCION*********************************/
	public Solucion BLReinsertPM(Instancia initInstance) {
		for(int i = 0; i < getMachineList().size(); i++){
			for(int j = i + 1; j < getMachineList().size() - 1; j++){
				for(int k = 0; k < getMachineList().get(i).size(); k++){
					Solucion aux = changeMachineItem(getMachineList.get(i).get(k).getID(), j, 
						getMachineList().get(j).bestPos(getMachineList.get(i).get(k), initInstance));
					if(getLatenciaTotal() > aux.getLatenciaTotal()){ return(aux); }
				}
			}
		}
		for(int i = getMachineList().size() - 1; i >= 0; i--){
			for(int j = i - 1; j > 0; j--){
				for(int k = 0; k < getMachineList().get(i).size(); k++){
					Solucion aux = changeMachineItem(getMachineList.get(i).get(k).getID(), j, 
						getMachineList().get(j).bestPos(getMachineList.get(i).get(k), initInstance));
					if(getLatenciaTotal() > aux.getLatenciaTotal()){ return(aux); }	
				}
			}
		}
		return this;
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
	    	// busca las tareas con IDs itemIDA y itemIDB y guarda los índices de las
	    	// máquinas donde están y los índices de las tareas dentro de las máquinas
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
	    	// intercambia las tareas
	    	newSolucion.getMachineList().get(machItemA).getTareasRealizadas().set(posItemA, aux2);
	    	newSolucion.getMachineList().get(machItemB).getTareasRealizadas().set(posItemB, aux);
	    	return newSolucion;
    }
    
    /** Pasa el elemento itemIDA a la maquina "machineIDA" en la posicion "machPosA" **/
    public Solucion changeMachineItem(Integer itemIDA, Integer machineIDA, Integer machPosA) {
    		Solucion newSolucion = new Solucion(this);
	    	int posItemA = 0, machItemA = 0;
	    	// Busca en qué máquina y en qué posición se encuentra la tarea con ID "itemIDA"
	    	for(int i = 0; i < getMachineList().size(); i++) {
	    		for(int j = 0; j < getMachineList().get(i).getTareasRealizadas().size(); j++) {
	    			if(getMachineList().get(i).getTareasRealizadas().get(j).getID().equals(itemIDA)) {
	    				posItemA = j;
	    				machItemA = i;
	    			}
	    		}
	    	}
	    	Tarea aux = getMachineList().get(machItemA).getTareasRealizadas().get(posItemA);
	    	// quita la tarea de la máquina donde se encontraba
	    	newSolucion.getMachineList().get(machItemA).getTareasRealizadas().remove(posItemA);
	    	// pone la tarea en la posición "machPosA" de la máquina "machineIDA"
	    	newSolucion.getMachineList().get(machineIDA).getTareasRealizadas().add(machPosA, aux);
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
	public int getNumEntornos() { return numEntornos; }
}
