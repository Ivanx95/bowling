package org.jobsity.bowling.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jobsity.bowling.util.Constants;

public class TabSeparateRowValidation implements RowValidation<String>{

	
	
	private static final  Pattern pattern = Pattern.compile(Constants.TAB_REGEX); 
	
	@Override
	public void validateRow(String line) {
		
		 Matcher matcher = pattern.matcher(line);
		 if(!matcher.matches()) {
			 throw new IllegalStateException("Row malformed not valid format");
		 }
		
	}

}
