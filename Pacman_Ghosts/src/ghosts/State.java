package ghosts;
import pacman.Message;

public interface State<GhostPlayer> {
	public void enter(GhostPlayer ghost);
	public void execute(GhostPlayer ghost);
	public void exit(GhostPlayer ghost);
	public boolean atMessageReceived(GhostPlayer ghost, Message message);

}
