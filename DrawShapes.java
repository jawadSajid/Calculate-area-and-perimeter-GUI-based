//import packages
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawShapes//public class
{
	public static void main(String[] args)//main 
	{
		DrawFrame frame = new DrawFrame();//new object
		
		JFrame jframe = new JFrame("FINAL ASSIGNMENT - DRAW SHAPES");
		
		jframe.add(frame , BorderLayout.CENTER); 
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setSize(1000 , 600);
	}
}

abstract class TwoDShape
{
	//instance variables
	int length , width;
	int xaxis;
	int yaxis;
	Color color;
	
	//abstract method to be implemented
	public abstract double area();
	public abstract double perimeter();
	public abstract void draw(Graphics g);
}

class Oval extends TwoDShape//inheritance
{
	//instance variables
	static double area;
	static double perimeter;
	
	//constructor
	public Oval(int xaxis , int yaxis , int length ,int width , Color color)
	{
		this.xaxis = xaxis;
		this.yaxis = yaxis;
		this.length = length;
		this.width = width;
		this.color = color;
		
		area = area + Math.PI * width * length * 0.25f;//for area
		perimeter = perimeter + (Math.PI/2) * (Math.sqrt(2 * length * length + 2 * width * width));//for perimeter
	}
	
	//overriding superclass methods and implenting a functionality
	@Override
	public double perimeter() 
	{
		return perimeter;
	}
	
	@Override
	public double area() 
	{
		return area;
	}

	@Override
	public void draw(Graphics g)
	{
		g.fillOval(xaxis , yaxis , length , width);
	}
}

class Rectangle extends TwoDShape//inheritance
{
	//instance variables
	static double area;
	static double perimeter;
	
	//constructor
	public Rectangle(int xaxis , int yaxis, int length , int width , Color color)
	{
		this.color = color;
		this.xaxis = xaxis;
		this.yaxis = yaxis;
		this.length = length;
		this.width = width;
			
		area =  area + length * width;
		perimeter = perimeter + (2 * (length + width));
	}
	//methods to override
	@Override
	public double perimeter() 
	{
		return perimeter;
	}

	@Override
	public double area() 
	{
		return area;
	}
			
	@Override
	public void draw(Graphics g)
	{
		g.fillRect(xaxis , yaxis , length , width);
			
	}
}

class DrawFrame extends JPanel
{
	int xaxis1 , yaxis1 , xaxis2 , yaxis2 , length , width;
	
	private final JRadioButton redJRadio , blueJRadio , greenJRadio;//Colors JRadioButtons
	private final JButton clearScreen , calculate; //Two Buttons
	private final JRadioButton ovalJRadio , rectangleJRadio; //Oval and Rectangle JRadioButtons
	private final ButtonGroup radioButtonGroup1 , radioButtonGroup2; //Group JRadioButtons
	
	int variable = 0;//Variable for checking which shape is selected
	Color color = Color.RED;//set Color
	
	ArrayList<TwoDShape> array = new ArrayList<TwoDShape>(); //ArrayList
	
	Oval oval = new Oval(0 , 0 , 0 , 0 , null); //new object
	Rectangle rectangle = new Rectangle(0 , 0 , 0 , 0 , null);//new object
	
	public DrawFrame()//Constructor
	{
		radioButtonGroup1 = new ButtonGroup();
		
		redJRadio = new JRadioButton("RED" , true);//red
		radioButtonGroup1.add(redJRadio);//adding in buttonGroup
		add(redJRadio);//add in frame
		
		greenJRadio = new JRadioButton("GREEN");//green
		radioButtonGroup1.add(greenJRadio);//adding in buttonGroup
		add(greenJRadio);
		
		blueJRadio = new JRadioButton("BLUE");//blue
		radioButtonGroup1.add(blueJRadio);//adding in buttonGroup
		add(blueJRadio);
		
		clearScreen = new JButton("CLEAR SCREEN");//button clearscreen
		add(clearScreen);
		
		calculate = new JButton("CALCULATE AREA AND PERIMETER");//calculate area perimeter button
		add(calculate);
		
		radioButtonGroup2 = new ButtonGroup();//another buttonGroup
		
		ovalJRadio = new JRadioButton("OVAL");
		radioButtonGroup2.add(ovalJRadio);//adding in another buttonGroup
		add(ovalJRadio);
		
		rectangleJRadio = new JRadioButton("RECTANGLE");
		radioButtonGroup2.add(rectangleJRadio);//adding in another buttonGroup
		add(rectangleJRadio);
		
		CalculationHandler calculationHandler = new CalculationHandler();//Calculation of perimeter and area handling
		clearScreen.addActionListener(calculationHandler);
		calculate.addActionListener(calculationHandler);
		
		redJRadio.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent event)
			{
				if(event.getSource() == redJRadio)
				{
					color = Color.RED;
				}
			}
		}
		);
		
		ColorHandler colorHandler = new ColorHandler();//Color handling	
		greenJRadio.addItemListener(colorHandler);
		blueJRadio.addItemListener(colorHandler);
		ovalJRadio.addItemListener(colorHandler);
		rectangleJRadio.addItemListener(colorHandler);
		
		addMouseListener(new MouseHandler());//handle mouse events
	}
	
	private class MouseHandler extends MouseAdapter//private inner class mouseHandler
	{
		@Override
		public void mousePressed(MouseEvent event)//mousePressed extending
		{
			xaxis1 = event.getX();
			yaxis1 = event.getY();
		}
			
		@Override
		public void mouseReleased(MouseEvent event)//mouseReleased extending
		{
			xaxis2 = event.getX();
			yaxis2 = event.getY();
			width = Math.abs(xaxis1 - xaxis2);
			length = Math.abs(yaxis1 - yaxis2);
				
			if(variable == 0)//if variables is 0 then Oval button will be selected and this will be added in the array
			{
				array.add(new Oval(xaxis1 , yaxis1 , width , length , color));
			}
				
			if(variable == 1)//if variables is 1 then Rectangle button will be selected and this will be added in the array
			{
				array.add(new Rectangle(xaxis1 , yaxis1 , width , length , color));
			}
				
			repaint();
		}
	}
	
	//handling buttons
	class CalculationHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == clearScreen)//for clear screen
			{
				array.clear();
				repaint();
			}
			
			if(event.getSource() == calculate)//for area and perimeter
			{
				JOptionPane.showMessageDialog(null, String.format("The area of Ovals is %f and of Rectangles is %f\nThe perimter of Ovals is %f and of Rectangles is %f",
				oval.area(), rectangle.area(), oval.perimeter(), rectangle.perimeter()));
			}
		}
	}
	
	//handling radioButtons
	class ColorHandler implements ItemListener
	{
		@Override
		public void itemStateChanged(ItemEvent event)
		{
			/*if(event.getSource() == redJRadio)
			{
				color = Color.RED;
			}*/
			
			if(event.getSource() == greenJRadio)
			{
				color = Color.GREEN;
			}
			
			else if(event.getSource() == blueJRadio)
			{
				color = Color.BLUE;
			}
			
			else if(event.getSource() == ovalJRadio)
			{
				variable = 0;
			}
			
			else if(event.getSource() == rectangleJRadio)
			{
				variable = 1;
			}
		}
	}
			
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for(TwoDShape twoDShape: array)//for enhanced loop
		{
			//do this on traversing
			g.setColor(twoDShape.color);
			twoDShape.draw(g);
		}
	}
}
