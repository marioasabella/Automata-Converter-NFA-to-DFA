package Automat;

public class AFjD extends Automat{
	
	public AFjD() {
		super();
	}
	
	public AFD konvertoNeAFD() {
		AFD afd = new AFD();
		afd.alfabet = alfabet;
		shtoGjendjetEReja(afd, fillestare); 
		return afd;
	}
	
	private void shtoGjendjetEReja(AFD afd, Gjendje gjendje) {
		
		if(gjendje.equals(fillestare)) { //Nqs gjendje eshte gjendje fillestare e AFjD-se
		
			Gjendje fillestareERe = new Gjendje(fillestare.emer);
		
			afd.shtoGjendjeFillestare(fillestareERe);
			
			shtoGjendjetEReja(afd, fillestareERe);
		
		} else {
			
			for(String shkronje: alfabet) {
			
				String emer = komponentetEGjendjes(gjendje, shkronje); // Gjej gjendjen tjeter te automatit
				
				if(!emer.equals("")) {
					Gjendje gjendjeERe = afd.gjejGjendjeNeAutomat(emer);
					
					if(gjendjeERe == null) { //Nqs gjendja e re nuk gjendet ne automat
						
						gjendjeERe = new Gjendje(emer);
						
						//Kerko nese gjendja e re eshte dhe gjendje fundore
						for(Gjendje gjendjeFundore : gjendjeFundore) {
							
							if(gjendjeERe.emer.contains(gjendjeFundore.emer)) {
								
								afd.shtoGjendjeFundore(gjendjeERe);
						
							}
							
							afd.shtoGjendje(gjendjeERe);// shtojme gjendjen e re ne automat
						}
						
					}
					
					gjendje.shtoKalim(new Kalim(shkronje, gjendjeERe));
					
					if(gjendjeERe.kalimet.isEmpty()) {
						shtoGjendjetEReja(afd, gjendjeERe);
					}
					
				}
			}
		}
	}
	
	
	private String komponentetEGjendjes(Gjendje gjendjeERe, String shkronje) {
		String emer = "";
		@SuppressWarnings("unused")
		Gjendje kthe;
		
		for(Gjendje gjendje : automat) {
			
			boolean permban = gjendjeERe.emer.contains(gjendje.emer);
			
			if(permban) { //Nqs gjendja ndodhet ne NFA
			
				emer += gjendjaERradheSeAFD(gjendje, shkronje);
			
			}
		}
		
		return emer;
	}
	
	private String gjendjaERradheSeAFD(Gjendje gjendje, String shkronje) {
		String emer = "";
		
		for(Kalim k : gjendje.kalimet) {
		
			if(k.shkronje.equals(shkronje)) {
				
				emer += k.gjendjePasardhese.emer;
			
			}
		}
		
		return emer;
	}
}
