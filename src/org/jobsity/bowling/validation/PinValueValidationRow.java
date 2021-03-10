package org.jobsity.bowling.validation;

public class PinValueValidationRow implements RowValidation<Integer> {

	@Override
	public void validateRow(Integer value) {
		
		if(value<0||value>10) {
			throw new IllegalStateException("Cannot be a negative value");
		}
		
	}

}
