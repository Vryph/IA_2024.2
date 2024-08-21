package Miner_Bob_Second;

public interface State {
	public void enter(Bob bob);
	public void execute(Bob bob);
	public void exit(Bob bob);
}
