public class Instancia {

        private int numeroTareas = 0 ;
        private int numeroMaquinas = 0 ;
        private ArrayList<Tarea> tareas;
        private Integer[][] distancias;
        
        public Instancia(){}
    
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
        this.numeroMauqinas = numeroMaquinas;
    } 
    
    public void addTarea(Tarea aux) {
        this.tareas.add(aux);
    } 
}