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
		System.out.println("------------------------------- GREEDY -------------------------------");
		System.out.println("SOLUCIÓN INICIAL:");
	    for(int j = 0; j < getSolution().getMachineList().size(); j++) {
		    System.out.println(getSolution().getMachineList().get(j).getTareasRealizadas().toString());
		    System.out.println("VALOR TOTAL LATENCIA: " + getSolution().getMachineList().get(j).getLatencia(getInitialInstance()));
	    }
	    System.out.println("==========================================================================");
	}
}
