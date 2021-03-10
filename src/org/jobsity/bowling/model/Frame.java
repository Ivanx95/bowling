package org.jobsity.bowling.model;

import java.util.List;

public class Frame {

	private boolean Strike;
	private String player;
	private List<Integer> values;
	
	private boolean lastFrame = false;
	
	
	
	public Frame(boolean strike, String player, List<Integer> values) {
		super();
		Strike = strike;
		this.player = player;
		this.values = values;
	}

	public boolean isStrike() {
		return Strike;
	}

	public String getPlayer() {
		return player;
	}

	public List<Integer> getValues() {
		return values;
	}

	public boolean isLastFrame() {
		return lastFrame;
	}

	public void setLastFrame(boolean lastFrame) {
		this.lastFrame = lastFrame;
	}
	
	
	
	
}
