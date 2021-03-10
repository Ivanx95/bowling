package org.jobsity.bowling.main;

import org.jobsity.bowling.proccesor.ComposedProccesorUnit;

public class Main {
	
	
	public static void main(String[] args) {
		
		FileProcesor procesor = new FileProcesor(new ComposedProccesorUnit());
		if(args.length<1) {
			System.err.println("Missing arguments");
			System.exit(1);
		}
		procesor.start(args[0]);
	}

}
