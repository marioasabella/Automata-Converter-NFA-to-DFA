package Automat;

import java.util.ArrayList;

public class Gjendje {
	String emer;
	ArrayList<Kalim> kalimet;
	
	public Gjendje(String emer) {
		this.emer = emer;
		kalimet = new ArrayList<Kalim>();
	}
	
	public void shtoKalim(Kalim k) {
		kalimet.add(k);
	}
}
