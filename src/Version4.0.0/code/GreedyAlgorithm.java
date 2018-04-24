package code;

public class GreedyAlgorithm extends Algorithm {
	
	public GreedyAlgorithm(Instancia initInst) { 
		super(initInst);
	}
	public void exec() {
		Integer machineCandidata;
		while(getActualTareas().size() > 0) {
			machineCandidata = getCandidatoID();
			int index = 0;
			Maquina auxMachine = new Maquina(getMachineList().get(machineCandidata));
			auxMachine.addTarea(index, getActualTareas().get(0));
			int min = auxMachine.getLatencia(getInitialInstance());
			for(int i = 0; i < getMachineList().get(machineCandidata).getTareasRealizadas().size() + 1; i++) {
				auxMachine = new Maquina(getMachineList().get(machineCandidata));
				auxMachine.addTarea(i, getActualTareas().get(0));
				if(auxMachine.getLatencia(getInitialInstance()) < min) {
					index = i;
					min = auxMachine.getLatencia(getInitialInstance());
				}
			}
			getSolution().getMachineList().get(machineCandidata).addTarea(index, getActualTareas().get(0));
			getActualTareas().remove(0);
		}
	}
}
