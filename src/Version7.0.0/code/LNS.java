package code;

public class LNS extends Algorithm{
	
	private int iterSinMejora; 

	public LNS(Instancia initInst) {
		super(new Instancia(initInst));
		setIterSinMejora(0);
	}
	
	@Override
	public void exec(){
		conseguirSolucion();
		int mejorLatencia = getSolution().getLatenciaTotal(getInitialInstance());
		Solucion bestSolucion = new Solucion(getSolution());
		while(getIterSinMejora() < 5){
			destruirParteSolucion();
			conseguirSolucion();
			if(mejorLatencia > getSolution().getLatenciaTotal(getInitialInstance())){
				mejorLatencia = getSolution().getLatenciaTotal(getInitialInstance());
				setIterSinMejora(0);
				bestSolucion = new Solucion(getSolution());
			}
			else{
				setIterSinMejora(getIterSinMejora() + 1);
			}
		}
		setSolution(bestSolucion);
	}
	
	public void destruirParteSolucion(){
		Random randCantidad = new Random();
		int cantidad = randCantidad.nextInt(getInitialInstance().getNumeroTareas());
		for(int i = 0; i < cantidad; i++){
			int idMaquina = randCantidad.nextInt(getSolucion().getMachineList().size());
			int posTarea = randCantidad.nextInt(getSolucion().getMachineList().get(idMaquina).size());
			getActualTareas().add(getSolucion().getMachineList().get(idMaquina).getTareasRealizadas().get(posTarea));
			getSolucion().getMachineList().get(idMaquina).getTareasRealizadas().remove(posTarea);
		}
	}

	public void conseguirSolucion() {
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
		System.out.println("------------------------------- GREEDY -------------------------------");
		System.out.println("SOLUCI�N INICIAL:");
	    for(int j = 0; j < getSolution().getMachineList().size(); j++) {
		    System.out.println(getSolution().getMachineList().get(j).getTareasRealizadas().toString());
		    System.out.println("VALOR TOTAL LATENCIA: " + getSolution().getMachineList().get(j).getLatencia(getInitialInstance()));
	    }
	    System.out.println("==========================================================================");
	}
	
	public int getIterSinMejora(){
		return iterSinMejora;
	}
	
	public void setIterSinMejora(int cantidad){
		this.iterSinMejora = cantidad;
	}

}
