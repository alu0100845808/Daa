package code;

public class GreedyAlgorithm extends Algorithm {
	
	public GreedyAlgorithm(Instancia initInst) { 
		super(new Instancia(initInst), "GREEDY");
		setN_ITERACIONES(1);
	}
	public void exec() {
		long time_start, time_end;
		time_start = System.currentTimeMillis();
		Integer machineCandidata;
		while(getActualTareas().size() > 0) {
			machineCandidata = getSolution().getCandidatoID(getInitialInstance());
			int index = 0;
			Maquina auxMachine = new Maquina(getSolution().getMachineList().get(machineCandidata));
			auxMachine.addTarea(index, getActualTareas().get(0));
			int min = auxMachine.getLatencia(getInitialInstance());
			for(int i = 0; i < getSolution().getMachineList().get(machineCandidata).getTareasRealizadas().size() + 1; i++) {
				auxMachine = new Maquina(getSolution().getMachineList().get(machineCandidata));
				auxMachine.addTarea(i, getActualTareas().get(0));
				if(auxMachine.getLatencia(getInitialInstance()) < min) {
					index = i;
					min = auxMachine.getLatencia(getInitialInstance());
				}
			}
			getSolution().getMachineList().get(machineCandidata).addTarea(index, getActualTareas().get(0));
			getActualTareas().remove(0);
		}
		time_end = System.currentTimeMillis();
		setTiempoEjec(time_end - time_start);
	}
}
