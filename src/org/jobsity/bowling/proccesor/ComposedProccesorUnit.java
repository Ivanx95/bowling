package org.jobsity.bowling.proccesor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jobsity.bowling.model.Frame;
import org.jobsity.bowling.util.Constants;
import org.jobsity.bowling.validation.PinValueValidationRow;
import org.jobsity.bowling.validation.RowValidation;
import org.jobsity.bowling.validation.TurnOrderRowComposedValidation;

public class ComposedProccesorUnit extends AbstractProccesorUnit<Frame>{


	
	public ComposedProccesorUnit() {
		
		// TODO Auto-generated constructor stub
	}

	

	
	public Frame transform(String line) {
		
		String[] tokens = line.split(Constants.PLAYER_REGEX);
		
		String player = tokens[0];
		String valueStr = tokens[1];
		Integer value =  !valueStr.equals("F")?Integer.parseInt(tokens[1]):0;
		
		List<Integer> values = new ArrayList<>();
		values.add(value);
		Frame frame = new Frame(value==10, player, values);
		return frame;
	}




	

}
