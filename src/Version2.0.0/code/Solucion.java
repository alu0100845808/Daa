package code;

import java.util.ArrayList;

public class Solucion {

    private ArrayList<Maquina> machineList;
    
    public Solucion() {
    	setMachineList(new ArrayList<Maquina>(0));
    }
    
    public Solucion(ArrayList<Maquina> actualML) {
    	setMachineList(actualML);
    }
    
	public ArrayList<Maquina> getMachineList() { return machineList; } 
	public void setMachineList(ArrayList<Maquina> machineList) { this.machineList = machineList; }
	
}
