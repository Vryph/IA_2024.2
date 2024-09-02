package Miner_Bob_Fourth;

public class StateMachine<NPC> {

 private NPC myOwner;
 private State<NPC> lastState;
 private State<NPC> currentState;
 private State<NPC> globalState;
		
 
 public StateMachine(NPC owner) {
	 myOwner = owner;
	 currentState = null;
	 lastState = null;
	 globalState = null;
 }
 
 public void SetCurrentState(State<NPC> currentState) {
	 this.currentState = currentState;
 }
 
 public void SetLastState(State<NPC> lastState) {
	 this.lastState = lastState;
 }
 
 public void SetGlobalState(State<NPC> globalState) {
	 this.globalState = globalState;
 }
 
 public State<NPC> GetLastState(){
	 return lastState; 
 }
  
 public State<NPC> GetGlobalState(){
	 return globalState;
 }
 
 public State<NPC> GetCurrentState(){
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
	 
 public void ChangeState(State<NPC> newState) {
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
