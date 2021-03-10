package org.jobsity.bowling.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import org.jobsity.bowling.util.Constants;

public class TurnOrderRowComposedValidation implements RowValidation<String> {

	private int turn;
	private List<String> players;
	private Map<String, Integer> playedTurn = new HashMap<>();
	private int pinStriked=0;
	private final PinValueValidationRow pinValueValidationRow;
    
	 
	public TurnOrderRowComposedValidation() {
		super();
		this.turn = 0;
		players = new ArrayList<String>();
		pinValueValidationRow = new PinValueValidationRow();
		
	}


	@Override
	public void validateRow(String line) {
		
		
		String[] tokens = line.split(Constants.PLAYER_REGEX);
		if(tokens.length>2) {
			throw new IllegalStateException("Row malformed, contains more than 1 player");
		}
		
		String player = tokens[0];
		String valueStr = tokens[1];
		Integer value =  !valueStr.equals("F")?Integer.parseInt(tokens[1]):0;
		this.pinValueValidationRow.validateRow(value);
		
		
		Integer playedTurn = this.playedTurn.get(player);
		
		if(playedTurn!= null && playedTurn>9) {
			int subTurn = playedTurn-9;
			
			int index= subTurn/3+subTurn%3;
			
			players.add(player);
			
			if(index>2) {
				Long lastPlayers = players
				.subList(players.size()-4, players.size()-1)
				.stream()
				.filter(s-> s.equals(player))
				.count();
				if(lastPlayers!=3) {
					throw new IllegalStateException(String.format("Not turn of player: %s",player));
				}
				for (int i = 1; i < 4; i++) {
					players.remove(players.size()-i);
					
				}
			}
			
			this.playedTurn.put(player, ++playedTurn);
			
		}else {
			
			this.turn++;
			
			boolean firstTime= !this.playedTurn.containsKey(playedTurn);
			boolean finishRound = false; 
			if(turn%2==1) {
				if(!this.players.isEmpty()) {
					throw new IllegalStateException(String.format("Not turn of player: %s",player));
				}else {
					if(value!=10) {
						this.players.add(player);
						finishRound=true;
					}else {
						this.turn++;
						this.pinStriked=value;
					}
				}
			}else {
				if(!this.players.contains(player)) {
					throw new IllegalStateException(String.format("Not turn of player: %s",player));
				}else {
					if(value>this.pinStriked) {
						throw new IllegalStateException(String.format("Cannot strike more pinnes than %s",this.pinStriked));
					}
					this.pinStriked=0;
					finishRound=true;
					this.players.remove(player);
				}
			}
			if(firstTime&&finishRound) {
				this.playedTurn.put(player, 0);
			}else if (finishRound) {
				this.playedTurn.computeIfPresent(player,(key,val)-> ++val);
			}
		}
		
		
		
	}

}
