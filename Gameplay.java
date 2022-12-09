import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener
{
	private boolean play = false;
	private int score = 0;

	private int time = 0;
	
	private int totalBricks = 48;
	
	private Timer timer;
	private int delay=8;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;

	private String ascore = "";
	
	private MapGenerator map;

	MyStack stack = new ArrayStack();
	public Gameplay()
	{

		map = new MapGenerator(4, 12);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g) {




		// set background
		g.setColor(Color.yellow);
		g.fillRect(1, 1, 692, 592);
		
		// drawing map
		map.draw((Graphics2D) g);
		
		// borders
		g.setColor(Color.black);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		// the scores
		if(time%60 == 0){
			score+=1;
		}

		g.setColor(Color.red);
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString(""+score, 590,30);


		g.drawString(""+ascore, 100,30);


		
		// the paddle
		g.setColor(Color.blue);
		g.fillRect(playerX, 550, 100, 8);
		
		// the ball
		g.setColor(Color.red);
		g.fillOval(ballposX, ballposY, 20, 20);

		if(ballposX == 120 && ballposY == 350 && play == false){

			g.drawString("Press (Enter) to play", 230,350);
			try {
				Showscore(g);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		// เวลาชนะเกม
		if(totalBricks == 0)
		{
			if(play == true) {
				try {
					Easy(score);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			 play = false;
             ballXdir = 0;
     		 ballYdir = 0;
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 30));
             g.drawString("You Won", 260,300);
             
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 20));           
             g.drawString("Press (Enter) to Restart", 230,350);
			try {
				Showscore(g);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}


		}
		
		// เวลาแพ้เกม
		if(ballposY > 570)
        {
			 play = false;
             ballXdir = 0;
     		 ballYdir = 0;
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 30));
             g.drawString("Game Over, Time scores: "+score, 190,300);
             
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 20));           
             g.drawString("Press (Enter) to Restart", 230,350);
			try {
				Showscore(g);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
        }
		time+=1;
		g.dispose();
	}	

	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{        
			if(playerX >= 600)
			{
				playerX = 600;
			}
			else
			{
				moveRight();
			}
        }
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{          
			if(playerX < 10)
			{
				playerX = 10;
			}
			else
			{
				moveLeft();
			}
        }		
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{          
			if(!play)
			{
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);
				
				repaint();
			}
        }		
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	public void moveRight()
	{
		play = true;
		playerX+=20;	
	}
	
	public void moveLeft()
	{
		play = true;
		playerX-=20;	 	
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		timer.start();
		if(play)
		{			
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8)))
			{
				ballYdir = -ballYdir;
				ballXdir = -2;
			}
			else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8)))
			{
				ballYdir = -ballYdir;
				ballXdir = ballXdir + 1;
			}
			else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8)))
			{
				ballYdir = -ballYdir;
			}
			
			// ตรวจสอบแผนที่ชนกับลูกบอล
			A: for(int i = 0; i<map.map.length; i++)
			{
				for(int j =0; j<map.map[0].length; j++)
				{				
					if(map.map[i][j] > 0)
					{
						//scores++;
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);					
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect))
						{					
							map.setBrickValue(0, i, j);
							//score+=5;
							totalBricks--;
							
							// เมื่อลูกบอลกระทบไปทางขวาหรือซ้ายของกbrick
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)	
							{
								ballXdir = -ballXdir;
							}
							// เมื่อลูกบอลชนbrickด้านบนหรือด้านล่าง
							else
							{
								ballYdir = -ballYdir;				
							}
							
							break A;
						}
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			
			if(ballposX < 0)
			{
				ballXdir = -ballXdir;
			}
			if(ballposY < 0)
			{
				ballYdir = -ballYdir;
			}
			if(ballposX > 670)
			{
				ballXdir = -ballXdir;
			}		
			
			repaint();		
		}
	}

	public static void Easy(int score) throws Exception {
		int score1;
		int a = 0;
		int size = 0;
		MyStack stack = new ArrayStack();

		Scanner read = new Scanner(new File("Score.txt"));
		while (read.hasNextLine()) {
			String line = read.nextLine();
			if(line != "") {
				stack.push(Integer.parseInt(line.replace(": This is your score last game", "")));
				System.out.println(stack.top());
				size++;
			}
		}
		System.out.println("--------------");
		System.out.println(stack.gatSize());
		read.close();
		FileWriter Score1 = new FileWriter("Score.txt");
			if (stack.gatSize() == 10) {
				if(score == Math.min((int) stack.top(), score)){
					stack.pop();
					stack.push(score);
					Score1.write((String.valueOf(stack.pop())) + ": This is your score last game\n");
				}else{
					stack.push(stack.pop());
					Score1.write((String.valueOf(stack.pop())) + "\n");
				}

			} else {
				stack.push(score);
				size++;
				System.out.println(stack.gatSize());
				Score1.write((String.valueOf(stack.pop())) + ": This is your score last game\n");
			}
			for(int i = 0;i < size-1;i++) {
				Score1.write((String.valueOf(stack.pop())) + "\n");
				System.out.println(stack.gatSize());
			}
			Score1.close();
		}
	public void Showscore(Graphics g) throws FileNotFoundException {
		String[] a = new String[10];
		int size = 0;
		Scanner read = new Scanner(new File("Score.txt"));
		while (read.hasNextLine()) {
			String line = read.nextLine();
			if(line != "") {
				a[size] = line;
				size++;
			}
		}
		read.close();//ปิดไฟล์ที่อ่าน
		g.setColor(Color.RED);
		g.setFont(new Font("serif",Font.BOLD, 15));
		for (int i = 0; i < size; i++) {
			System.out.println(a[i]);
			g.drawString(a[i], 30,30+i*30);
		}

	}
}

