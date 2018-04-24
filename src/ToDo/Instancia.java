package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Instancia {

        private Integer numeroTareas = 0 ;
        private Integer numeroMaquinas = 0 ;
        private ArrayList<Tarea> tareas;
        private ArrayList<Maquina> machineList;
        private Integer[][] distancias;
        
        public Instancia(){
            setNumeroTareas(0);
            setNumeroMaquinas(0);
            setTareas(new ArrayList<Tarea>(0));
            setMachineList(new ArrayList<Maquina>(0));
            setDistancias(null);
        }

        public Instancia(Integer numeroTareas, Integer numeroMaquinas){
            setNumeroTareas(numeroTareas);
            setNumeroMaquinas(numeroMaquinas);
            setTareas(new ArrayList<Tarea>(numeroTareas));
            setMachineList(new ArrayList<Maquina>(numeroMaquinas));
            setDistancias(new Integer[numeroTareas][numeroTareas]);
        }

        public void readFromInstance(String filename) throws IOException {
        	@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(filename));
            
            /** N�mero de tareas **/
            String line = br.readLine();
            String[] parts = line.split("\\s");
            setNumeroTareas(Integer.parseInt(parts[1]));
            
            /** N�mero de m�quinas **/
            line = br.readLine();
            parts = line.split("\\s");
            setNumeroMaquinas(Integer.parseInt(parts[1]));
            setMachineList(new ArrayList<Maquina>(getNumeroMaquinas()));
            
            /** Lista de Tareas **/
            line = br.readLine();
            parts = line.split("\\s");
            for(int i = 1; i <= getNumeroTareas(); i++){
                addTarea(new Tarea(i - 1, Integer.parseInt(parts[i])));
            }
            
            /** Descripci�n del problema **/
            line = br.readLine();
            setDistancias(new Integer[getNumeroTareas()][getNumeroTareas()]);
            
            /** Matriz de transiciones entre tareas **/
            int i = 0;
            while((line = br.readLine()) != null && i < getNumeroTareas()){
                parts = line.split("\\s");
                for(int j = 0; j < getNumeroTareas(); j++){
                	if(i == j && Integer.parseInt(parts[j]) != 0){
                		throw new IllegalArgumentException("La diagonal principal de la matriz de transiciones ha de ser cero.");
                	}
                	setDistanciaItem(i, j, Integer.parseInt(parts[j]));	
                }
                i++;
            } 
            br.close();
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
        				latTotal += getDistanciaItem(getMachineList().get(machineIDA).getTareasRealizadas().get(i-1).getID(), 
        					                     getMachineList().get(machineIDA).getTareasRealizadas().get(i).getID());
                        latTotal += getLatenciaFromMachine(machineIDA)
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
    	
        public void addTarea(Tarea aux) { 
        	this.tareas.add(aux); 
        } 
        public void setDistanciaItem(int i, int j, int aux) {
            this.distancias[i][j] = aux ;
        }
        public int getDistanciaItem(int i, int j) {
            return this.distancias[i][j] ;
        }

        /** M�todos GETTER y SETTER **/
		public Integer getNumeroTareas(){ return numeroTareas; }
		public Integer getNumeroMaquinas(){ return numeroMaquinas; }
		public ArrayList<Tarea> getTareas(){ return tareas; }
		public Integer[][] getDistancias(){ return distancias; }
		public ArrayList<Maquina> getMachineList(){ return machineList; }
		public void setNumeroTareas(Integer numeroTareas){ this.numeroTareas = numeroTareas; }
		public void setNumeroMaquinas(Integer numeroMaquinas){ this.numeroMaquinas = numeroMaquinas; }
		public void setTareas(ArrayList<Tarea> tareas){ this.tareas = tareas; }
		public void setDistancias(Integer[][] distancias){ this.distancias = distancias; }
		public void setMachineList(ArrayList<Maquina> machineList){ this.machineList = machineList; }
    
}