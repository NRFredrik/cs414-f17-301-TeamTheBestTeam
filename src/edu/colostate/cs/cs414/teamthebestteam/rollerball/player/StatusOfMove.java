package edu.colostate.cs.cs414.teamthebestteam.rollerball.player;

public enum StatusOfMove {

	Done{
		@Override
		public boolean isDone() {
			return true;
		}
	},

	ILLEGAL{
		@Override
		public boolean isDone() {
			return false; //return false cause move was illegal
		}
	}, 
	
	RESULTS_IN_CHECK {
		@Override
		public boolean isDone() {
			return false;
		}
	};
	public abstract boolean isDone();
}
