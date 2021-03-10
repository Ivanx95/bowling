package org.jobsity.bowlin;

import java.util.ArrayList;
import java.util.List;

import org.jobsity.bowling.main.FileProcesor;
import org.jobsity.bowling.proccesor.ComposedProccesorUnit;
import org.jobsity.bowling.validation.RowValidation;
import org.jobsity.bowling.validation.TabSeparateRowValidation;
import org.jobsity.bowling.validation.TurnOrderRowComposedValidation;
import org.junit.Before;
import org.junit.Test;

public class ValidationTest {

	private List<String> data = new ArrayList<>();
	
	
	private RowValidation<String> tabRowValidation = new TabSeparateRowValidation();
	private RowValidation<String> composedValidation = new TurnOrderRowComposedValidation();
	
	FileProcesor procesor = new FileProcesor(new ComposedProccesorUnit());
	@Test
	public void checkRowTabFormat() {
		data.clear();
		data.add("Foo\t2");
		data.add("Foo\t2");
		data.add("Foo\tF");
		for (String line : data) {
			tabRowValidation.validateRow(line);
		}
		
		
	}
	
	@Test(expected=IllegalStateException.class)
	public void checkRowTabFormatFail() {
		data.clear();
		data.add("Foo\ta");
		data.add("Foo 2");
		
		for (String line : data) {
			tabRowValidation.validateRow(line);
		}
		
	}
	
	@Test
	public void checkPlayerOrder() {
		data.clear();
		data.add("Foo\t10");
		data.add("Bar\t5");
		data.add("Bar\t5");
		data.add("Foo\t10");
		data.add("Bar\t5");
		data.add("Bar\t5");
		data.add("Foo\t10");
		data.add("Bar\t5");
		data.add("Bar\t5");
		data.add("Foo\t10");
		data.add("Bar\t5");
		data.add("Bar\t5");
		data.add("Foo\t10");
		data.add("Bar\t5");
		data.add("Bar\t5");
		data.add("Foo\t10");
		data.add("Bar\t5");
		data.add("Bar\t5");
		data.add("Foo\t10");
		data.add("Bar\t5");
		data.add("Bar\t5");
		data.add("Foo\t10");
		data.add("Bar\t5");
		data.add("Bar\t5");
		data.add("Foo\t10");
		data.add("Bar\t5");
		data.add("Bar\t5");
		
		data.add("Bar\t10");
		data.add("Bar\t5");
		data.add("Bar\t5");
		
		data.add("Foo\t10");
		data.add("Foo\t5");
		data.add("Foo\t5");
		int counter=0;
		try {
			for (String line : data) {
				composedValidation.validateRow(line);
				counter++;
			}
		} catch (IllegalStateException e) {
			System.out.println(counter);
			throw e;
		}
		
		
	}
	
	
	@Test(expected=IllegalStateException.class)
	public void checkPlayerOrderSumFail() {
		data.clear();
		data.add("Foo\t7");
		data.add("Foo\t5");
		data.add("Bar\t4");
		data.add("Foo\t6");
		for (String line : data) {
			composedValidation.validateRow(line);
		}
		
	}
	
	@Test(expected=IllegalStateException.class)
	public void checkPlayerOrderFail() {
		data.clear();
		data.add("Foo\t5");
		data.add("Foo\t5");
		data.add("Bar\t4");
		data.add("Foo\t6");
		for (String line : data) {
			composedValidation.validateRow(line);
		}
		
	}
	
	@Test
	public void tesrProccesor() {
		data.clear();
		data.add("Foo\t10");
		data.add("Bar\t4");
		data.add("Bar\t6");
		
		data.add("Foo\t10");
		data.add("Bar\t4");
		data.add("Bar\t6");
		
		data.add("Foo\t10");
		data.add("Bar\t4");
		data.add("Bar\t6");
		
		data.add("Foo\t10");
		data.add("Bar\t4");
		data.add("Bar\t6");
		
		data.add("Foo\t10");
		data.add("Bar\t4");
		data.add("Bar\t6");
		
		data.add("Foo\t10");
		data.add("Bar\t4");
		data.add("Bar\t6");
		
		data.add("Foo\t10");
		data.add("Bar\t4");
		data.add("Bar\t6");
		
		data.add("Foo\t10");
		data.add("Bar\t4");
		data.add("Bar\t6");
		
		data.add("Foo\t10");
		data.add("Bar\t4");
		data.add("Bar\t6");
		
		data.add("Bar\t10");
		data.add("Bar\t4");
		data.add("Bar\t6");
		
		data.add("Foo\t10");
		data.add("Foo\t5");
		data.add("Foo\t5");
		this.procesor.process(data);
	}
}
