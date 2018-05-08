package code;

public class Tarea {
    
	private Integer ID;
	private Integer time;
	
	public Tarea(Integer tareaID, Integer tareaTime) {
	    if (tareaID < 0 || tareaTime < 0){
	        throw new IllegalArgumentException("El ID para la tarea debe ser mayor que cero (Actual ID: " + tareaID + ").");
	    }
	    setID(tareaID);
	    setTime(tareaTime);
	}
	
	public Tarea(Tarea tarea) {
		ID = new Integer(tarea.getID());
		time = new Integer(tarea.getTime());
	}
	
	public String toString() {
	    return getTime() + " ";
	}

	public Integer getID(){ return ID; }
	public Integer getTime(){ return time; }
	public void setID(Integer iD){ ID = iD; }
	public void setTime(Integer time){ this.time = time; }
}