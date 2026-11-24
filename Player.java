package noahHashGame;

public class Player {
	
	public int x;
	public int y;
	
	private int stamina;
	
	public Player(int startX, int startY)	{
		this.x = startX;
		this.y= startY;
		stamina = 2000;
	}
	
	public int getStamina()	{
		return this.stamina;
	}
	
	public void setStamina(int s)	{
		stamina = s;
		
	}

}
