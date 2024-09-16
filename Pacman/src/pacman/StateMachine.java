package pacman;

import ghosts.State;

public class StateMachine<GhostPlayer> {
	private GhostPlayer myOwner;
	private State<GhostPlayer> lastState;
	private State<GhostPlayer> currentState;
	private State<GhostPlayer> globalState;
	
	
	public StateMachine(GhostPlayer owner) {
		myOwner = owner;
		currentState = null;
		lastState = null;
		globalState = null;
	}

	 public void SetCurrentState(State<GhostPlayer> currentState) {
		 this.currentState = currentState;
	 }
	 
	 public void SetLastState(State<GhostPlayer> lastState) {
		 this.lastState = lastState;
	 }
	 
	 
	 public void SetGlobalState(State<GhostPlayer> globalState) {
		 this.globalState = globalState;
	 }
	 
	 
	 public State<GhostPlayer> GetLastState(){
		 return lastState; 
	 }
	  
	 
	 public State<GhostPlayer> GetGlobalState(){
		 return globalState;
	 }
	 
	 
	 public State<GhostPlayer> GetCurrentState(){
		 return currentState;
	 }
	 
	 public void Update() {
		 
		 if(globalState != null) {
			 globalState.execute(myOwner);
		 }
		 
		 
		 if(currentState != null) {
			 currentState.execute(myOwner);
		 }
	 }
		 
	 public void ChangeState(State<GhostPlayer> newState) {
		 lastState = currentState;
		 currentState.exit(myOwner);
		 currentState = newState;
		 currentState.enter(myOwner);
	 }
	 
	 public void RevertToLastState() {
		 ChangeState(lastState);
	 }
	 
	 
	 public boolean treatMessage(Message message) {
		 if (currentState.atMessageReceived(myOwner, message)) {
			 return true;
		 }
		 else if (globalState != null && globalState.atMessageReceived(myOwner, message)) {
			 return true;
		 }
		 return false;
	 }
	}

