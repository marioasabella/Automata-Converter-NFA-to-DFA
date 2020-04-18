package Automat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Automat {
	
	ArrayList<String> alfabet; //Po modifikues aksesi package private qe aksesohet nga cdo antar i "package"
	ArrayList<Gjendje> automat;
	ArrayList<Gjendje> gjendjeFundore;
	Gjendje fillestare;
	
	public Automat() {
		alfabet =  new ArrayList<String>();
		automat = new ArrayList<Gjendje>();
		gjendjeFundore = new ArrayList<Gjendje>();
	}
	
	public void shtoGjendjeFillestare(Gjendje gjendje) {
		fillestare = gjendje;
		shtoGjendje(fillestare);
	}
	
	public void shtoGjendjeFundore(Gjendje gjendje) {
		gjendjeFundore.add(gjendje);
		shtoGjendje(gjendje);
	}
	
	public void shtoGjendje(Gjendje gjendje) {
		Gjendje ekziston = gjejGjendjeNeAutomat(gjendje.emer);//Verifiko nqs gjendja ekziston ne automat
		
		if(ekziston == null) { //Nqs nuk ekziston
			automat.add(gjendje); // Shto gjendje ne automat
		}
		
	}
	
	public Gjendje gjejGjendjeNeAutomat(String emer) {
		for(Gjendje gjendje : automat) {
			if(gjendje.emer.equalsIgnoreCase(emer)) {
				return gjendje;
			}
		}
		return null;
	}
	
	public boolean ndodhetNeAutomat(String emer) {
		Gjendje ekziston = gjejGjendjeNeAutomat(emer); // Kerko ne automat
		if(ekziston != null ) {// Nese e gjen
			return true;
		} else {
			return false;	// Nese nuk e gjen
		}
	}
	
	public void shtoShkronjeAlfabeti(String shkronje) {
		alfabet.add(shkronje);
	}
	
	public boolean gjeShkronjeNeAlfabet(String shkronjaQeKerkohet) {
		for(String shkronje: alfabet) {
			if(shkronjaQeKerkohet.equalsIgnoreCase(shkronje)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		String kthe = "";
		
		kthe+= "Alfabeti: ";
		
		for(String shkronje: alfabet) {
			
			kthe += shkronje + " ";
			
		}
		kthe += "\n";
		
		kthe += "Gjendje Fillestare: " + fillestare.emer;
		
		kthe += "\n";
		
		kthe += "Gjendje(t) Fundore: ";
		
		for(Gjendje fundore : gjendjeFundore) {
			kthe += fundore.emer + " ";
		}
		
		kthe += "\n";
		
		for(Gjendje  gjendje: automat) {
			for(Kalim k : gjendje.kalimet) {
				kthe += gjendje.emer + " " + k.shkronje + " " + k.gjendjePasardhese.emer + "\n";
			}
		}
		
		return kthe;
	}
	
	
	public static void main(String[] args) throws IOException{
		//Lexojme skedarin dhe ndertojme AFjD
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		AFjD afjd = new AFjD();
		
		//Lexojme emrin e skedarit
		System.out.println("Vendosni emrin e skedarit: ");
		String emriSkedarit = input.nextLine();
		
		//Shfaqim ne ekran permbajtjen e skedarit
		
		try {
			
			FileReader skedar = new FileReader(emriSkedarit);
			BufferedReader lexoSkedar = new BufferedReader(skedar);
			
			//Lexojme rreshtin e pare te skedarit ku eshte shkruaj alfabeti
			String rreshti = lexoSkedar.readLine();
			
			String[] alfabet = rreshti.split(" ");
			
			// Shktojme shkronjat ne alfabet me ane te ciklit te meposhtem
			for(String shkronje : alfabet) {
				afjd.shtoShkronjeAlfabeti(shkronje);
			}
			
			//Ne rreshtin e dyte kemi ruajtur gjendjen fillestare te cilen ia shtojme AFjD-se
			rreshti = lexoSkedar.readLine();
			afjd.shtoGjendjeFillestare(new Gjendje(rreshti));
			
			/*Ne rreshtin e trete kemi ruajtur gjendje(t) fundore te cilat i lexojme 
			 * dhe ia shtojme objektit afjd me ane te nje cikli
			 */
			rreshti = lexoSkedar.readLine();
			String[] gjendjeFundore = rreshti.split(" ");
			for(String gjendje: gjendjeFundore) {
				afjd.shtoGjendjeFundore(new Gjendje(gjendje));
			}
			
			/*Ne rreshtat e tjere do te lexojme gjendjet dhe kalimet e tyre per cdo gjendje */
			rreshti = lexoSkedar.readLine();
			Gjendje gjendje1;
			Gjendje gjendje2;
			
			while(rreshti != null) {
				
				String[] pjese = rreshti.split(" "); //Ndajme rreshtin ne pjese dhe e ruajm nje array
				
				
				//Kontrollojme nqs gjendjet ekzistojne ne automat
				gjendje1 = afjd.gjejGjendjeNeAutomat(pjese[0]);
				gjendje2 = afjd.gjejGjendjeNeAutomat(pjese[2]);
				
				if(gjendje1 == null) {
					afjd.shtoGjendje(new Gjendje(pjese[0]));
					gjendje1 = afjd.gjejGjendjeNeAutomat(pjese[0]);
				}
				
				if(gjendje2 == null) {
					if(pjese[0] == pjese[2]) {
						gjendje2 = gjendje1;
					} else {
						afjd.shtoGjendje(new Gjendje(pjese[2]));
						gjendje2 = afjd.gjejGjendjeNeAutomat(pjese[2]);
					}
				}
				
				
				gjendje1.shtoKalim(new Kalim(pjese[1], gjendje2));
				
				//vazhdojme leximin e rreshtave
				rreshti = lexoSkedar.readLine();
				
			}
			
			skedar.close();
			
		} catch(IOException e) {
			//Exception-i nqs skenari nuk hapet
			System.err.printf("Gabim ne hapjen e skedarit. %s.\n", e.getMessage());
		}
		
		System.out.println("");
		
		AFD afd;
		@SuppressWarnings("unused")
		Gjendje gjendje;
		
		// Paraqesim AFjD e futur perdoruesit
		System.out.println(afjd);
		
		// Konvertojme AFjD ne AFD
		afd = afjd.konvertoNeAFD();
		System.out.println("");
		
		// Printojme ne ekran AFD e konvertuar nga AFjD
		System.out.println(afd);
		
		
		
		/*Krijojme skedarinOutput me te dhenat e AFD-se te konvertuar*/
		try {
			
			@SuppressWarnings("resource")
			FileOutputStream skedariOutput = new FileOutputStream(new File("AFD.txt"));
			byte[] b = afd.toString().getBytes();
			skedariOutput.write(b);
			
			
		} catch(Exception e) {
			
			System.err.printf("Gabim ne krijimin e skedarit output. %s.\n", e.getMessage());
		
		}
		
	}

}
