package Miner_Bob_Third;

public interface State<NPC> {
	public void enter(NPC npc);
	public void execute(NPC npc);
	public void exit(NPC npc);
}
