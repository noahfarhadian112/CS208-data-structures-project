package noahHashGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class GameWindow extends JPanel{
	private final int GRID_SIZE = 10;
	private final int CELL_SIZE = 40;
	
	private Map<String, Room> worldMap;
	private String currentRoomName;
	private Player player;
	
	public GameWindow()	{
		setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
		setFocusable(true);
		
		setupWorld();
		setupInput();
	}
	
	private void setupWorld() {
		worldMap = new HashMap<>();
		
		Room startRoom = new Room("Start Room", new Color(60, 63, 65));
		Room goldRoom = new Room("Gold Room", new Color(212, 175, 55));
		Room foodRoom = new Room("Food Room", new Color(20, 57, 200));
		Room topRoom = new Room("Top Room", Color.MAGENTA);
		
		startRoom.addDoor("West", "Gold Room");
		goldRoom.addDoor("East","Start Room");
		goldRoom.addDoor("West", "Food Room");
		goldRoom.addDoor("North", "Top Room");
		foodRoom.addDoor("East", "Gold Room");
		topRoom.addDoor("South", "Gold Room");
		
		
		worldMap.put("Start Room", startRoom);
		worldMap.put("Food Room", foodRoom);
		worldMap.put("Gold Room", goldRoom);
		worldMap.put("Top Room", topRoom);
		
		currentRoomName = "Start Room";
		player = new Player(4,4);
	}
	
	private void setupInput()	{
		addKeyListener(new KeyAdapter()	{
			@Override
			public void keyPressed(KeyEvent e)	{
				int nextX = player.x;
				int nextY = player.y;
				
				if(e.getKeyCode() == KeyEvent.VK_UP)	
					nextY--;
				if(e.getKeyCode() == KeyEvent.VK_DOWN)
					nextY++;
				if(e.getKeyCode() == KeyEvent.VK_LEFT)
					nextX--;
				if(e.getKeyCode() == KeyEvent.VK_RIGHT)
					nextX++;
				
				handleMovement(nextX, nextY);
					
				
			}
		});
	}
	
	private void handleMovement(int targetX, int targetY)	{
		if(player.getStamina() <= 0)	{
			JOptionPane.showMessageDialog(this, "You have run out of stamina.","You lose. Game Over", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Room currentRoom = worldMap.get(currentRoomName);
		boolean atVerticalDoor = (player.y == 4 || player.y == 5);
	    boolean atHorizontalDoor = (player.x == 4 || player.x == 5);
	    boolean moved = false;
		
		if(targetX >= GRID_SIZE)	{
			if(atVerticalDoor && currentRoom.getDoorExits().containsKey("East"))	{
				currentRoomName = currentRoom.getDoorExits().get("East");
				player.x = 0;
				moved = true;
				
			}
		}
		else if(targetX < 0)	{
			if(atVerticalDoor && currentRoom.getDoorExits().containsKey("West"))	{
				currentRoomName = currentRoom.getDoorExits().get("West");
				player.x = GRID_SIZE - 1;
				moved = true;
				
			}
		}
		else if(targetY >= GRID_SIZE)	{
			if(atHorizontalDoor && currentRoom.getDoorExits().containsKey("South"))	{
				currentRoomName = currentRoom.getDoorExits().get("South");
				player.y = 0;
				moved = true;
			}
		}
		else if(targetY < 0)	{
			if(atHorizontalDoor && currentRoom.getDoorExits().containsKey("North"))	{
				currentRoomName = currentRoom.getDoorExits().get("North");
				player.y = GRID_SIZE - 1;
				moved = true;
			}
		}
		else	{
			player.x = targetX;
			player.y = targetY;
			moved = true;
		}
		
		if(moved)	{
			player.setStamina(player.getStamina() - 1);
		}
		
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g)	{
		super.paintComponent(g);
		
		Room currentRoom = worldMap.get(currentRoomName);
		
		g.setColor(currentRoom.getFloorColor());
		g.fillRect(0,0,getWidth(), getHeight());
		
		g.setColor(new Color(255,255,255,30));
		for(int i = 0; i < GRID_SIZE; i++)	{
			g.drawRect(i*CELL_SIZE, 0, CELL_SIZE, CELL_SIZE*GRID_SIZE);
			g.drawRect(0, i*CELL_SIZE, GRID_SIZE*CELL_SIZE, CELL_SIZE);
			
		}
		
		
		//Draws doors
		g.setColor(new Color(139,69,19));
		if(currentRoom.getDoorExits().containsKey("East"))	{
			g.fillRect((GRID_SIZE - 1) * CELL_SIZE+20, 4 * CELL_SIZE, 20,2*CELL_SIZE);
		}
		if(currentRoom.getDoorExits().containsKey("West")) {
			g.fillRect(0,4 * CELL_SIZE, 20, 2 * CELL_SIZE);
		}
		if(currentRoom.getDoorExits().containsKey("North")) {
			g.fillRect(4 * CELL_SIZE, 0, 2 * CELL_SIZE, 20);
		}
		if(currentRoom.getDoorExits().containsKey("South")) {
			g.fillRect(4 * CELL_SIZE,(GRID_SIZE - 1) * CELL_SIZE + 20, 2 * CELL_SIZE, 20);
		}
		
		g.setColor(Color.CYAN);
		g.fillRect(player.x * CELL_SIZE + 5, player.y * CELL_SIZE + 5,CELL_SIZE - 10, CELL_SIZE - 10);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("Current Room: " + currentRoom.getName(), 15, 25);
		g.drawString("Stamina: " + player.getStamina(), 15, 45);
		
	}

}
