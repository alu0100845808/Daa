package code;

import java.util.ArrayList;

public class Solucion {
	
	private ArrayList<Maquina> maquinas;

	public Solucion(Integer nMaquinas) {
		setMaquinas(new ArrayList<Maquina>());
		for (int i = 0; i < nMaquinas; i++) {
			getMaquinas().add(new Maquina());
		}
	}
	
	public Solucion(Instancia instance) {
		setMaquinas(instance.getMachineList());
	}

    public Maquina getSolucionAt(Integer maquinaID) { return getMaquinas().get(maquinaID); }
	public ArrayList<Maquina> getMaquinas(){ return maquinas; }
	public void setMaquinas(ArrayList<Maquina> maquinas){ this.maquinas = maquinas; }

}