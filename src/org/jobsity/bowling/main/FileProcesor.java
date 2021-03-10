package org.jobsity.bowling.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jobsity.bowling.model.Frame;
import org.jobsity.bowling.proccesor.AbstractProccesorUnit;
import org.jobsity.bowling.validation.PinValueValidationRow;
import org.jobsity.bowling.validation.TabSeparateRowValidation;
import org.jobsity.bowling.validation.TurnOrderRowComposedValidation;

public class FileProcesor {

	private final AbstractProccesorUnit<Frame> unit;
	private final TabSeparateRowValidation tabSeparationRow;
	private final PinValueValidationRow pinValueValidationRow;
	private final TurnOrderRowComposedValidation composedValidation;
	
	public FileProcesor(AbstractProccesorUnit<Frame> unit) {
		super();
		this.unit = unit;
		this.tabSeparationRow = new TabSeparateRowValidation();
		this.pinValueValidationRow = new PinValueValidationRow();
		this.composedValidation = new TurnOrderRowComposedValidation();
	}


	public void start(String fileName) {
		List<String> data = new ArrayList<>();
		try {
		      File file = new File(fileName);
		      Scanner myReader = new Scanner(file);
		      while (myReader.hasNextLine()) {
		        String line = myReader.nextLine();
		        data.add(line);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		      System.exit(1);
		      return;
		    }
		
		process(data);
	}
	private void validate(String line) {
		tabSeparationRow.validateRow(line);
		tabSeparationRow.validateRow(line);
	}
	

	public void process(List<String> lines) {
		
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			try {
				this.validate(line);
			} catch (IllegalStateException e) {
				System.err.println("Error in line: "+i);
				throw e;
			}
			
		}
		Map<String, List<Frame>> players = lines.stream()
		
		.map(unit::transform)
		.collect(Collectors.groupingBy(Frame::getPlayer));
		
		
		players.entrySet().iterator();
		int count=0;
		int sum=0;
		for(Map.Entry<String,List<Frame>> entry : players.entrySet()){
		
			List<Frame> baseList = entry.getValue();
			
			Frame identity = baseList.remove(0);
			
			List<Frame> auxList = baseList.subList(0, baseList.size()-3);
			
			List<Frame> tailList = baseList.subList(baseList.size()-3, baseList.size());
			
			tailList.forEach(t-> t.setLastFrame(true));
			
			Stack<Frame> queue = new Stack<>();
			
			queue.add(identity);
			int oddCount=0;
			
			for (Frame f : auxList) {
				Frame tail = queue.peek();
				
				if(!tail.isStrike()&&oddCount%2==0) {
					tail.getValues().addAll(f.getValues());
					oddCount++;
				}else {
					queue.add(f);
					oddCount=0;
				}
				
			}
			
			List<Frame> resultList = queue.stream().collect(Collectors.toList());
			resultList.addAll(tailList);
			entry.setValue(resultList);
			sum+=resultList.size();
			count++;
		}
		
		if(!(sum%count==0)) {
			throw new IllegalStateException("players didn't have the same amount of turn");
		}
		
		String turns=Stream.iterate(1, i->++i).limit(count).map(String::valueOf).collect(Collectors.joining("  "));
		String line = String.format("Frame %s",turns );
		
		System.out.println(line);
		
		for(Map.Entry<String,List<Frame>> entry : players.entrySet()){
			
			List<Frame> auxList = entry.getValue();
			System.out.println(entry.getKey());
			
			 line = auxList.stream().flatMap(f->{ 
			
				if(f.isStrike()) {
					return Stream.of(" ", "X");
				}
				if(f.isLastFrame()) {
					return f.getValues().stream().map(String::valueOf);
				}
				 int sumFrame = f.getValues().stream().reduce(0,Integer::sum);
				 if(sumFrame==10) {
					 return Stream.of(f.getValues().get(0)+"","/");
				 }
				 return f.getValues().stream().map(String::valueOf);
				 
				
			}).collect(Collectors.joining(" "));
			 
			 System.out.println(line);
			 
			 List<Integer> listValue = new ArrayList<>();
			 for(int i =0; i< auxList.size(); i++) {
				 
				 Frame aux =auxList.get(i);
				 int value = aux.getValues().stream().reduce(0, Integer::sum);
				 
				 if(i<9) {
					 int offset=0;
					 if(aux.isStrike()) {	 
						 offset = 2;	 
					 }else {
						 offset = 1;
					 }
					 
					 Stream<Frame> str = auxList.subList(i+1, auxList.size()).stream();
					 
					 List<Integer> sbList = str.flatMap(f-> f.getValues().stream()).collect(Collectors.toList());
					 value += sbList.subList(0, offset).stream().reduce( Integer::sum).get();
				 }
				 
			 	if(i>0) {
			 		value += listValue.subList(i-1, i).stream().reduce( Integer::sum).get();
				 }
				 listValue.add(value);
				
			 }
			 line = listValue.stream().map(String::valueOf).collect(Collectors.joining(" "));
			 System.out.println(line);
			 
		}
		
	}
	
	
}
