package code;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
						Solucion auxSolucion = new Solucion(exchangeMachineItem(getMachineList().get(i).getTarea(k).getID(), getMachineList().get(j).getTarea(l).getID()));
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
						Solucion auxSolucion = new Solucion(exchangeMachineItem(getMachineList().get(i).getTarea(k).getID(), getMachineList().get(j).getTarea(l).getID()));
						if(auxSolucion.getLatenciaTotal(initInstance) < newSolucion.getLatenciaTotal(initInstance)) {
							newSolucion = new Solucion(auxSolucion);
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
				for(int k = 0; k < getMachineList().get(i).getTareasRealizadas().size(); k++){
					Solucion aux = new Solucion(changeMachineItem(getMachineList().get(i).getTareasRealizadas().get(k).getID(), j, 
						getMachineList().get(j).bestPos(getMachineList().get(i).getTareasRealizadas().get(k), initInstance)));
					if(getLatenciaTotal(initInstance) > aux.getLatenciaTotal(initInstance)){ return(aux); }
				}
				for(int k = 0; k < getMachineList().get(j).getTareasRealizadas().size(); k++){
					Solucion aux = new Solucion(changeMachineItem(getMachineList().get(j).getTareasRealizadas().get(k).getID(), i, 
						getMachineList().get(i).bestPos(getMachineList().get(j).getTareasRealizadas().get(k), initInstance)));
					if(getLatenciaTotal(initInstance) > aux.getLatenciaTotal(initInstance)){ return(aux); }	
				}
			}
		}
		return this;
	}
	
	public Solucion BLReinsertGreedy(Instancia initInstance) {
		Solucion bestSolucion = new Solucion(this);
		int mejorLatencia = getLatenciaTotal(initInstance);
		
		for(int i = 0; i < getMachineList().size(); i++){
			for(int j = i + 1; j < getMachineList().size() - 1; j++){
				for(int k = 0; k < getMachineList().get(i).getTareasRealizadas().size(); k++){
					Solucion aux = new Solucion(changeMachineItem(getMachineList().get(i).getTareasRealizadas().get(k).getID(), j, 
						getMachineList().get(j).bestPos(getMachineList().get(i).getTareasRealizadas().get(k), initInstance)));
					if(mejorLatencia > aux.getLatenciaTotal(initInstance)){ 
						mejorLatencia = aux.getLatenciaTotal(initInstance);
						bestSolucion = new Solucion(aux);
					}
				}
				for(int k = 0; k < getMachineList().get(j).getTareasRealizadas().size(); k++){
					Solucion aux = new Solucion(changeMachineItem(getMachineList().get(j).getTareasRealizadas().get(k).getID(), i, 
						getMachineList().get(i).bestPos(getMachineList().get(j).getTareasRealizadas().get(k), initInstance)));
					if(mejorLatencia > aux.getLatenciaTotal(initInstance)){ 
						mejorLatencia = aux.getLatenciaTotal(initInstance); 
						bestSolucion = new Solucion(aux);
					}	
				}
			}
		}
		return bestSolucion;
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
	
	public void imprimirSolucion(String nombreDirectorio, Instancia initInstance, String algoritmo, double tiempoEjec, int nIteraciones){
		try{
			FileWriter fichero = new FileWriter("resultado_" + nombreDirectorio + ".txt", true);
			PrintWriter ficheroResultado = new PrintWriter(fichero);
			System.out.println(parseFile(initInstance, algoritmo, tiempoEjec, nIteraciones));
			ficheroResultado.println(parseFile(initInstance, algoritmo, tiempoEjec));
			ficheroResultado.close();
		}
		catch(IOException ioex){
			System.out.println("Fichero no encontrado o inexistente");
		}
		catch(Exception ex){
			System.out.println("Algo paso imprimiendo");
		}
	}
	
	/******************************************************************************/
    
	/******************************************************************************/
	
	public String parseFile(Instancia initInstance, String algoritmo, double tiempoEjec, int nIteraciones){
		String salida = "";
		salida += algoritmo + ";";
		salida += initInstance.getNumeroTareas() + ";";
		salida += getMachineList().size() + ";";
		salida += getLatenciaTotal(initInstance) + ";";
		salida += tiempoEjec;
		salida += nIteraciones;
		
		for(int i = 0; i < getMachineList().size(); i++){
			salida += ";" + '"' + "Maquina " +	(i+1) + ": " + getMachineList().get(i).getTareasRealizadas() + '"' + ";";
			salida += getMachineList().get(i).getLatencia(initInstance);
		}
		return salida;
	}
	
	/*****************************************************************************/
    
	public ArrayList<Maquina> getMachineList() { return machineList; }
	public void setMachineList(ArrayList<Maquina> machineList) { this.machineList = machineList; }
	public int getNumEntornos() { return numEntornos; }
}
