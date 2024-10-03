import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.*;
import java.lang.Thread;
import javax.swing.*;

/*
 *  The main window of the gui.
 *  Notice that it extends JFrame - so we can add our own components.
 *  Notice that it implements ActionListener - so we can handle user input.
 *  This version also implements MouseListener to show equivalent functionality (compare with the other demo).
 *  @author mhatcher
 */
public class WindowDemo extends JFrame implements ActionListener, MouseListener
{
	// gui components that are contained in this frame:
	private JPanel topPanel, bottomPanel;	// top and bottom panels in the main window
	private JLabel instructionLabel;		// a text label to tell the user what to do
	private JLabel infoLabel, playerLabel;            // a text label to show the coordinate of the selected square
    private JButton topButton, menuButton;				// a 'reset' button to appear in the top panel
	//private JButton break1;
	private GridSquare [][] gridSquares;	// squares to appear in grid formation in the bottom panel
	private Color [][] colors1;
	private int rows,columns;				// the size of the grid
	int player;
	Color[][] selectedShape;
	int[] selected1 = null;
	String[] players;
	boolean played;
	WindowDemo1 wd1;
	/*
	 *  constructor method takes as input how many rows and columns of gridsquares to create
	 *  it then creates the panels, their subcomponents and puts them all together in the main frame
	 *  it makes sure that action listeners are added to selectable items
	 *  it makes sure that the gui will be visible
	 */
	public WindowDemo(int rows, int columns, WindowDemo1 wd1)
	{
		this.rows = rows;
		this.columns = columns;
		this.setSize(90*columns,90*rows);
		this.selectedShape=wd1.selectedTile;	
		this.wd1=wd1;
		played=false;
		// for(int i=0; i<selectedShape.length;i++){
		// 	for(int j=0; j<selectedShape[i].length; j++){
		// 		selectedShape[i][j]=Color.GREEN;
		// 	}
		// }
		// first create the panels
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(rows, columns, 1,1));
		//bottomPanel.setBackground(Color.WHITE);
		//bottomPanel.setSize(100*columns,100*rows);
		// then create the components for each panel and add them to it
		
		// for the top panel:
		instructionLabel = new JLabel("Click a legal square!");
        infoLabel = new JLabel("No square clicked yet.");
		topButton = new JButton("choose shape"); menuButton=new JButton("exit");
		//break1=new JButton("Break");
		players= Math.random() < 0.5 ? new String[]{"user", "computer"}: new String[]{"computer", "user"};
		playerLabel = new JLabel(players[player] + "'s turn");

		//break1.addActionListener(this);
		topButton.addActionListener(this); menuButton.addActionListener(this);			// IMPORTANT! Without this, clicking the square does nothing.
		
		//topPanel.add(instructionLabel);
		topPanel.add (topButton);
		//topPanel.add(break1);
        //topPanel.add(infoLabel);
		topPanel.add(playerLabel);
		
	
		// for the bottom panel:	
		// create the squares and add them to the grid
		gridSquares = new GridSquare[columns][rows];
		colors1=new Color[columns][rows];
		for ( int y = 0; y < rows; y ++)
		{
			for ( int x = 0; x < columns; x ++)
			{
				colors1[y][x]=Color.WHITE;
				gridSquares[y][x] = new GridSquare(x, y);
				//gridSquares[x][y].setSize(20, 20);
				//gridSquares[x][y].setColor(x + y);
				gridSquares[y][x].setBackground(Color.WHITE);
				gridSquares[y][x].addMouseListener(this);		// AGAIN, don't forget this line!
				
				bottomPanel.add(gridSquares[y][x]);
			}
		}
		
		// now add the top and bottom panels to the main frame
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(bottomPanel, BorderLayout.CENTER);		// needs to be center or will draw too small
		
		// housekeeping : behaviour
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(false);
		
	}
	
	
	/*
	 *  handles actions performed in the gui
	 *  this method must be present to correctly implement the ActionListener interface
	 */
	public void actionPerformed(ActionEvent aevt)
	{
		// get the object that was selected in the gui
		Object selected = aevt.getSource();
				
		// if resetting the squares' colours is requested then do so
		if ( selected.equals(topButton) )
		{
			played=false;
			setVisible(false);
			this.wd1.selectedColor=null;
			this.wd1.shape_index=-1;
			this.wd1.setVisible(true);
			
		}
		else if(selected.equals(menuButton)){
			new WindowDemo1();
			dispose();
		}
		
	}


	// Mouse Listener events
	public void mouseClicked(MouseEvent mevt)
	{
		
		Object selected = mevt.getSource();
		
		/*
		 * I'm using instanceof here so that I can easily cover the selection of any of the gridsquares
		 * with just one piece of code.
		 * In a real system you'll probably have one piece of action code per selectable item.
		 * Later in the course we'll see that the Command Holder pattern is a much smarter way to handle actions.
		 */
		
		// if a gridsquare is selected then switch its color
		if(!played)
		{
		
            GridSquare square = (GridSquare) selected;
			boolean canFit=true;
			if((square.getYcoord()-1+this.selectedShape.length<gridSquares.length) && (square.getXcoord()-1+this.selectedShape[0].length<gridSquares[0].length))
			{
				for(int i=0; i<this.selectedShape.length;i++)
				{
					for(int j=0; j<this.selectedShape[i].length;j++)
					{
						//gridSquares1[i][j] = gridSquares[square.getYcoord()+i][square.getXcoord()+j]
						if(!colors1[square.getYcoord()+i][square.getXcoord()+j].equals(Color.WHITE) && !selectedShape[i][j].equals(Color.WHITE))
						{
							canFit=false;
						}
						
					}
				}
				if(canFit)
				{
					for(int i=0; i<this.selectedShape.length;i++)
					{
						for(int j=0; j<this.selectedShape[i].length;j++)
						{
							if(!selectedShape[i][j].equals(Color.WHITE)){
								colors1[square.getYcoord()+i][square.getXcoord()+j]=selectedShape[i][j];
							}
						}
					}
				}
			}
			played=true;
		}
	}
	// not used but must be present to fulfil MouseListener contract
	public void mouseEntered(MouseEvent arg0){
		if(!played){
			Object selected = arg0.getSource();
		
		/*
		 * I'm using instanceof here so that I can easily cover the selection of any of the gridsquares
		 * with just one piece of code.
		 * In a real system you'll probably have one piece of action code per selectable item.
		 * Later in the course we'll see that the Command Holder pattern is a much smarter way to handle actions.
		 */
		
		// if a gridsquare is selected then switch its color
		
            GridSquare square = (GridSquare) selected;

			boolean canFit=true;
			if((square.getYcoord()-1+this.selectedShape.length<gridSquares.length) && (square.getXcoord()-1+this.selectedShape[0].length<gridSquares[0].length))
			{
				for(int i=0; i<this.selectedShape.length;i++)
				{
					for(int j=0; j<this.selectedShape[i].length;j++)
					{
						//gridSquares1[i][j] = gridSquares[square.getYcoord()+i][square.getXcoord()+j]
						if(!colors1[square.getYcoord()+i][square.getXcoord()+j].equals(Color.WHITE) && !selectedShape[i][j].equals(Color.WHITE))
						{
							canFit=false;
						}
						
					}
				}
				if(canFit)
				{
					for(int i=0; i<this.selectedShape.length;i++)
					{
						for(int j=0; j<this.selectedShape[i].length;j++)
						{
							if(!selectedShape[i][j].equals(Color.WHITE)){
								gridSquares[square.getYcoord()+i][square.getXcoord()+j].setBackground(selectedShape[i][j]);
							}
						}
					}
				}
			}
		}
	}
	public void mouseExited(MouseEvent arg0) {
		if(!played){
			Object selected = arg0.getSource();
			GridSquare square = (GridSquare) selected;
			boolean canFit=true;
			//GridSquare[] gridSquares1=new GridSquare[selectedShape.length * selectedShape[0].length];
			if((square.getYcoord()-1+this.selectedShape.length<gridSquares.length) && (square.getXcoord()-1+this.selectedShape[0].length<gridSquares[0].length))
			{
				for(int i=0; i<this.selectedShape.length;i++)
				{
					for(int j=0; j<this.selectedShape[i].length;j++)
					{
						//gridSquares1[i][j] = gridSquares[square.getYcoord()+i][square.getXcoord()+j]
						if(!colors1[square.getYcoord()+i][square.getXcoord()+j].equals(Color.WHITE) && !selectedShape[i][j].equals(Color.WHITE))
						{
							canFit=false;
						}
						
					}
				}
				if(canFit)
				{
					for(int i=0; i<this.selectedShape.length;i++)
					{
						for(int j=0; j<this.selectedShape[i].length;j++)
						{
							if(!selectedShape[i][j].equals(Color.WHITE)){
								gridSquares[square.getYcoord()+i][square.getXcoord()+j].setBackground(Color.WHITE);
							}
						}
					}
				}
			}
		}
	}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}