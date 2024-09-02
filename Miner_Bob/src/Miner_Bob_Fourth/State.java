package Miner_Bob_Fourth;

public interface State<NPC> {
	public void enter(NPC npc);
	public void execute(NPC npc);
	public void exit(NPC npc);
	public boolean atMessageReceived(NPC npc, Message message);
}
