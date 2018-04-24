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
			machineCandidata = getCandidatoID();
			int index = 0, random = generateRandom();
			Maquina auxMachine = new Maquina(getMachineList().get(machineCandidata));
			auxMachine.addTarea(index, getActualTareas().get(random));
			int min = auxMachine.getLatencia(getInitialInstance());
			for(int i = 0; i < getMachineList().get(machineCandidata).getTareasRealizadas().size() + 1; i++) {
				auxMachine = new Maquina(getMachineList().get(machineCandidata));
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
	}
	
	public void mejora() {
		Integer peorCandidato = getPeorCandidatoID();
		Integer peorLatencia = getLatenciaTotal();
		Maquina auxMachine = new Maquina(getSolution().getMachineList().get(peorCandidato)); 
		for(int i = 0; i < getSolution().getMachineList().get(peorCandidato).getTareasRealizadas().size() - 1; i++) {
			for(int j = i + 1; j < getSolution().getMachineList().get(peorCandidato).getTareasRealizadas().size() - 1; j++) {
				auxMachine.changePosItem(i, j);
				if(auxMachine.getLatencia(getInitialInstance()) < peorLatencia) {
					peorLatencia = auxMachine.getLatencia(getInitialInstance());
					System.out.println("HE MEJORADO");
				}
				else {
					auxMachine.changePosItem(i, j);
				}
			}
		}
		getSolution().getMachineList().set(peorCandidato, auxMachine);
		setBestSolution(getSolution());
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
