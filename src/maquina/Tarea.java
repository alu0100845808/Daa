

public class Tarea {
    
	private Integer name;
	private Integer time;
	
	public Tarea(Integer name, Integer time) {
		try{
			if((name >= 0) && (time >= 0)){
				setTime(time);
				setName(name);
			}
			else
				throw new IllegalArgumentException();
		}
		catch(IllegalArgumentException error){
			System.out.println("El ID y el tiempo en realizar la tarea deben ser igual o mayor a 0");
		}
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
	
	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}
	
	public String toString() {
	    return "t" + getName() + "(" + getTime() + ")";
	}
}