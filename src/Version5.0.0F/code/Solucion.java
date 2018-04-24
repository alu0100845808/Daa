package code;

import java.util.ArrayList;

public class Solucion {

    private ArrayList<Maquina> machineList;
    
    public Solucion() {
    	setMachineList(new ArrayList<Maquina>());
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
    
	public ArrayList<Maquina> getMachineList() { return machineList; }
	public void setMachineList(ArrayList<Maquina> machineList) { this.machineList = machineList; }
	
}
