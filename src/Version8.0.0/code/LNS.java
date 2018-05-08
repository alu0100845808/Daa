package code;

import java.util.Random;

public class LNS extends Algorithm{
	
	private double porDestr;
	private final double MAXDESTR = 0.7;

	public LNS(Instancia initInst) {
		super(new Instancia(initInst), "LNS");
		setPorDestr(0.1);
	}

	@Override
	public void exec() {
		long time_start, time_end;
		time_start = System.currentTimeMillis();
		conseguirSolucion();
		int mejorLatencia = getSolution().getLatenciaTotal(getInitialInstance());
		Solucion bestSolucion = new Solucion(getSolution());
		while(getPorDestr() < MAXDESTR){
			destruirParteSolucion();
			conseguirSolucion();
			mejora();
			if(mejorLatencia > getSolution().getLatenciaTotal(getInitialInstance())){
				mejorLatencia = getSolution().getLatenciaTotal(getInitialInstance());
				bestSolucion = new Solucion(getSolution());
				setPorDestr(0.1);
			}
			setPorDestr(getPorDestr() + 0.1);
		}
		setSolution(bestSolucion);
		time_end = System.currentTimeMillis();
		setTiempoEjec(time_end - time_start);
	}
	
	public void destruirParteSolucion(){
		Random randCantidad = new Random();
		int cantidad = (int) (getInitialInstance().getNumeroTareas() * getPorDestr());
		for(int i = 0; i < cantidad; i++){
			int idMaquina = randCantidad.nextInt(getSolution().getMachineList().size());
			if(getSolution().getMachineList().get(idMaquina).getTareasRealizadas().isEmpty()) {
				int posTarea = randCantidad.nextInt(getSolution().getMachineList().get(idMaquina).getTareasRealizadas().size());
				getActualTareas().add(getSolution().getMachineList().get(idMaquina).getTareasRealizadas().get(posTarea));
				getSolution().getMachineList().get(idMaquina).getTareasRealizadas().remove(posTarea);
			}
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
		System.out.println("------------------------------- LNS -------------------------------");
		System.out.println("SOLUCION INICIAL:");
	    for(int j = 0; j < getSolution().getMachineList().size(); j++) {
		    System.out.println(getSolution().getMachineList().get(j).getTareasRealizadas().toString());
		    System.out.println("VALOR TOTAL LATENCIA: " + getSolution().getMachineList().get(j).getLatencia(getInitialInstance()));
	    }
	    System.out.println("==========================================================================");
	}
	
	public void mejora() {
		setSolution(getSolution().BLIntraPM(getInitialInstance()));
	}

	public double getPorDestr() { return porDestr; }
	public void setPorDestr(double porDestr) { this.porDestr = porDestr; } 
}