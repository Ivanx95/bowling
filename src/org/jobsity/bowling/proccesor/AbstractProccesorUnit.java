package org.jobsity.bowling.proccesor;

import org.jobsity.bowling.model.Frame;
import org.jobsity.bowling.validation.RowValidation;

public abstract class AbstractProccesorUnit<T> {

	
	public abstract T transform(String line);
	
}
