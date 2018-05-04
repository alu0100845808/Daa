package code;

import java.util.Random;

public class GraspAlgorithm extends Algorithm{

	private Solucion bestSolution;
	private final Integer LRC_SIZE = 3; 
	
	public GraspAlgorithm(Instancia initInst) {
		super(initInst);
		setBestSolution(new Solucion());
	}
	public void exec() {
		construccion();
		mejora();
	}
	
	public void construccion() {
		Integer machineCandidata;
		while(getActualTareas().size() > 0) {
			machineCandidata = getSolution().getCandidatoID(getInitialInstance());
			int index = 0, random = generateRandom();
			Maquina auxMachine = new Maquina(getSolution().getMachineList().get(machineCandidata));
			auxMachine.addTarea(index, getActualTareas().get(random));
			int min = auxMachine.getLatencia(getInitialInstance());
			for(int i = 0; i < getSolution().getMachineList().get(machineCandidata).getTareasRealizadas().size() + 1; i++) {
				auxMachine = new Maquina(getSolution().getMachineList().get(machineCandidata));
				auxMachine.addTarea(i, getActualTareas().get(random));
				if(auxMachine.getLatencia(getInitialInstance()) < min) {
					index = i;
					min = auxMachine.getLatencia(getInitialInstance());
				}
			}
			getSolution().getMachineList().get(machineCandidata).addTarea(index, getActualTareas().get(random));
			getActualTareas().remove(random);
		}
		setBestSolution(getSolution());
		System.out.println("------------------------------- GRASP -------------------------------");
		System.out.println("SOLUCI�N INICIAL:");
	    for(int j = 0; j < getSolution().getMachineList().size(); j++) {
		    System.out.println(getSolution().getMachineList().get(j).getTareasRealizadas().toString());
		    System.out.println("VALOR TOTAL LATENCIA: " + getSolution().getMachineList().get(j).getLatencia(getInitialInstance()));
	    }
	    System.out.println("==========================================================================");
	}
	
	public void mejora() {
		Solucion newSolucion = getSolution().BLIntraPM(getInitialInstance());
		setBestSolution(newSolucion);
	}
	
	public Integer generateRandom() {
		Integer random = new Random().nextInt(getLRC_SIZE());
		if(getActualTareas().size() != 1) {
			while(random > getActualTareas().size() - 1) {
				random = new Random().nextInt(getLRC_SIZE());
			}
			return random;
		}
		return 0;		
	}
	public Solucion getBestSolution() { return bestSolution; }
	public Integer getLRC_SIZE() { return LRC_SIZE; }
	public void setBestSolution(Solucion bestSol) { 
		bestSolution = new Solucion();
		for(int i = 0; i < bestSol.getMachineList().size(); i++) {
			bestSolution.getMachineList().add(i, bestSol.getMachineList().get(i));
		}
	}


}
