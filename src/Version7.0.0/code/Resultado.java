package code;

import java.util.ArrayList;

public class Resultado {
	
	private ArrayList<Maquina> maquinas;

	public Resultado(Integer nMaquinas) {
		setMaquinas(new ArrayList<Maquina>());
		for (int i = 0; i < nMaquinas; i++) {
			getMaquinas().add(new Maquina());
		}
	}
	
	public Resultado(Solucion solucion) {
		setMaquinas(solucion.getMachineList());
	}

    public Maquina getSolucionAt(Integer maquinaID) { return getMaquinas().get(maquinaID); }
	public ArrayList<Maquina> getMaquinas(){ return maquinas; }
	public void setMaquinas(ArrayList<Maquina> maquinas){ this.maquinas = maquinas; }

}