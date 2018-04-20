package maquina;

import java.util.*;

public class Instancia {

        private Integer numeroTareas = 0 ;
        private Integer numeroMaquinas = 0 ;
        private ArrayList<Tarea> tareas;
        private Integer[][] distancias;
        
        public Instancia(){
            setNumeroTareas(0);
            setNumeroMaquinas(0);
            tareas = new ArrayList<Tarea>(0);
            distancias = new Integer[1][1];
        }
        
        public Instancia(Integer numeroTareas, Integer numeroMaquinas){
            setNumeroTareas(numeroTareas);
            setNumeroMaquinas(numeroMaquinas);
            tareas = new ArrayList<Tarea>(numeroTareas);
            distancias = new Integer[numeroTareas][numeroTareas];
        }
    
        public int getNumeroTareas() {
            return numeroTareas;
        }
        
        public void setNumeroTareas(int numeroTareas) {
            this.numeroTareas = numeroTareas;
        }
        
        public int getNumeroMaquinas() {
            return numeroTareas;
        }
        
        public void setNumeroMaquinas(int numeroMaquinas) {
            this.numeroMaquinas = numeroMaquinas;
        } 
        
        public void addTarea(Tarea aux) {
            this.tareas.add(aux);
        }
        
        public void setDistancia(int i, int j, int aux) {
            this.distancias[i][j] = aux ;
        }
        
        public int getDistancia(int i, int j) {
            return this.distancias[i][j] ;
        }
    
}