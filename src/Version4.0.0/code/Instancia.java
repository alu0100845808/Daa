package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Instancia {

        private Integer numeroTareas = 0 ;
        private Integer numeroMaquinas = 0 ;
        private ArrayList<Tarea> tareas;
        private Integer[][] tPreparacion;
        
        public Instancia(){
            setNumeroTareas(0);
            setNumeroMaquinas(0);
            setTareas(new ArrayList<Tarea>(0));   
            setTPreparacion(null);
        }

        public Instancia(Integer numeroTareas, Integer numeroMaquinas){
            setNumeroTareas(numeroTareas);
            setNumeroMaquinas(numeroMaquinas);
            setTareas(new ArrayList<Tarea>(numeroTareas));
            setTPreparacion(new Integer[numeroTareas][numeroTareas]);
            sortTasks();
        }

        public void readFromInstance(String filename) throws IOException {
        	@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(filename));
            
            /** Número de tareas **/
            String line = br.readLine();
            String[] parts = line.split("\\s");
            setNumeroTareas(Integer.parseInt(parts[1]));
            
            /** Número de máquinas **/
            line = br.readLine();
            parts = line.split("\\s");
            setNumeroMaquinas(Integer.parseInt(parts[1]));
            
            /** Lista de Tareas **/
            line = br.readLine();
            parts = line.split("\\s");
            for(int i = 1; i <= getNumeroTareas(); i++){
                addTarea(new Tarea(i - 1, Integer.parseInt(parts[i])));
            }
            
            /** Descripción del problema **/
            line = br.readLine();
            setTPreparacion(new Integer[getNumeroTareas()][getNumeroTareas()]);
            
            /** Matriz de transiciones entre tareas **/
            int i = 0;
            while((line = br.readLine()) != null && i < getNumeroTareas()){
                parts = line.split("\\s");
                for(int j = 0; j < getNumeroTareas(); j++){
                	if(i == j && Integer.parseInt(parts[j]) != 0){
                		throw new IllegalArgumentException("La diagonal principal de la matriz de transiciones ha de ser cero.");
                	}
                	setPreparacionItem(i, j, Integer.parseInt(parts[j]));	
                }
                i++;
            } 
            br.close();
            sortTasks();
        } 
        
    	public void sortTasks() {
    		ArrayList<Tarea> aux = new ArrayList<Tarea>();
    		ArrayList<Tarea> actualTasks = getTareas();
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
    		setTareas(aux);
    	}
    	
        public void addTarea(Tarea aux) { 
        	this.tareas.add(aux); 
        } 
        public void setPreparacionItem(int i, int j, int aux) {
            this.tPreparacion[i][j] = aux ;
        }
        public int getPreparacionItem(int i, int j) {
            return this.tPreparacion[i][j] ;
        }

        /** Métodos GETTER y SETTER **/
		public Integer getNumeroTareas(){ return numeroTareas; }
		public Integer getNumeroMaquinas(){ return numeroMaquinas; }
		public ArrayList<Tarea> getTareas(){ return tareas; }
		public Integer[][] getTPreparacion(){ return tPreparacion; }
		public void setNumeroTareas(Integer numeroTareas){ this.numeroTareas = numeroTareas; }
		public void setNumeroMaquinas(Integer numeroMaquinas){ this.numeroMaquinas = numeroMaquinas; }
		public void setTareas(ArrayList<Tarea> tareas){ this.tareas = tareas; }
		public void setTPreparacion(Integer[][] preparacion){ this.tPreparacion = preparacion; }
    
}