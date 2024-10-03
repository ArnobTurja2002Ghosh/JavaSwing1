import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.*;
import java.lang.Thread;
import javax.swing.*;

public class WindowDemo1 extends JFrame implements ActionListener, MouseListener
{
	// gui components that are contained in this frame:
	private JPanel bottomPanel, panel1, panel2, panel3, panel4;	// top and bottom panels in the main window
	private JLabel instructionLabel;		// a text label to tell the user what to do
	private JLabel infoLabel, playerLabel;            // a text label to show the coordinate of the selected square
    private JButton rotate_L;				// a 'reset' button to appear in the top panel
	private JButton rotate_stick;
	//private JButton break1;
	private JPanel [] lTiles;	// squares to appear in grid formation in the bottom panel
	private JPanel[] stickTiles;
	private int rows,columns;				// the size of the grid
	int player;
	int[] selected1 = null;
	String[] players;
	JButton [][] selectButtons;
	Color selectedColor;
	private JPanel[][] panels2;
	int shape_index;
	Color[][] selectedTile;
	private WindowDemo wd;
	/*
	 *  constructor method takes as input how many rows and columns of gridsquares to create
	 *  it then creates the panels, their subcomponents and puts them all together in the main frame
	 *  it makes sure that action listeners are added to selectable items
	 *  it makes sure that the gui will be visible
	 */
	public WindowDemo1()
	{
        this.setSize(1000, 500);
		panels2=new JPanel[4][4];
		shape_index=-1;
		selectButtons=new JButton[2][4];
		for(int i=0; i<selectButtons.length; i++){
			for(int j=0; j<selectButtons[0].length; j++){
				selectButtons[i][j]=new JButton("select");
				selectButtons[i][j].addActionListener(this);
			}
		}

		
		lTiles=new JPanel[4];
		stickTiles=new JPanel[4];
		selectedTile=new Color[2][2];
		for(int i=0; i<selectedTile.length; i++){
			for(int j=0; j<selectedTile[0].length;j++){
				selectedTile[i][j]=Color.WHITE;
			}
		}
		panels2[1]=lTiles;
		panels2[2]=stickTiles;
		
        getContentPane().setLayout(new GridLayout(2, 4, 1,1));
		Color [] colors1= {Color.YELLOW, Color.BLUE, Color.GREEN, Color.RED};
		for(int i=0; i<colors1.length;i++){
			JPanel jp1 = new JPanel();
			jp1.setBackground(colors1[i]);
			getContentPane().add(jp1);
			jp1.add(selectButtons[0][i]);
		}
		JPanel [] panels1 = new JPanel[4];
		for(int i=0; i<4;i++){
			JPanel p1=new JPanel();
			p1.setLayout(new GridLayout(2, 2));
			getContentPane().add(p1);
			panels1[i]=p1;
		}

		for(int i=0; i<4;i++){
			JPanel gs1 = new JPanel(); gs1.setBackground(Color.BLACK);
			panels1[0].add(gs1);
			panels2[0][i]=gs1;
		}
		rotate_L=new JButton("rotate");
		rotate_stick=new JButton("rotate");
		rotate_L.addActionListener(this);
		rotate_stick.addActionListener(this);
		for(int i=0; i<4;i++){
			JPanel gs1 = new JPanel();
			lTiles[i]=gs1;
			if(i==0 )
			{
				gs1.setBackground(Color.WHITE);
				gs1.add(rotate_L);
			}
			else
			{
				gs1.setBackground(Color.BLACK);
			}
			panels1[1].add(gs1);
		
		}
		for(int i=0; i<4; i++){
			JPanel panel1=new JPanel();
			panels1[2].add(panel1);
			stickTiles[i]=panel1;
			if(i==1 || i==3)
			{
				panel1.setBackground(Color.WHITE);
				if(i==3){
					panel1.add(rotate_stick);
				}
			}
			else{panel1.setBackground(Color.BLACK);}
		}
		for(int i=0; i<4;i++){
			if(i==0)
			{
				JPanel panel1=new JPanel(); panel1.setBackground(Color.BLACK);
				panels1[3].add(panel1);
				panels2[3][i]=panel1;
			}
			else
			{
				JPanel panel1=new JPanel(); panel1.setBackground(Color.WHITE);
				panels1[3].add(panel1);
				panels2[3][i]=panel1;
			}
		}
		for(int i=0; i<4; i++){
			panels2[i][0].add(selectButtons[1][i]);
		}

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

    public void mouseClicked(MouseEvent mevt)
	{}
	public void linkTo(WindowDemo wd){
		this.wd=wd;
	}
    public void actionPerformed(ActionEvent aevt){
		Object selected = aevt.getSource();
		
		if(selected.equals(rotate_L)){
			int white =0;
			for(int i=0; i<lTiles.length; i++){
				if(lTiles[i].getBackground().equals(Color.WHITE)){
					white=i;
					lTiles[i].remove(rotate_L);
				}
			}
			white=(white+1)%4;
			for(int i=0; i<lTiles.length; i++){
				if(i==white)
				{
					lTiles[i].setBackground(Color.WHITE);
					lTiles[i].add(rotate_L);
				}
				else
				{
					lTiles[i].setBackground(Color.BLACK);
				}
			}
		}
		else if(selected.equals(rotate_stick))
		{
			int white =0;	
			for(int i=0; i<stickTiles.length-1; i++){
				if(stickTiles[i].getBackground().equals(Color.WHITE)){
					white=i;
				}
			}
			stickTiles[white].setBackground(Color.BLACK);
			if(white==1){
				stickTiles[white+1].setBackground(Color.WHITE);
			}
			else if(white==2){
				stickTiles[white-1].setBackground(Color.WHITE);
			}
		}
		for(int i =0; i<selectButtons[0].length; i++){
			if(selected.equals(selectButtons[0][i])){
				selectedColor = selectButtons[0][i].getParent().getBackground();
			}
		}
		
		for(int i=0; i<selectButtons[1].length; i++){
			if(selected.equals(selectButtons[1][i])){
				shape_index=i;
			}
		}
		
		if(shape_index>-1 && selectedColor!=null){
			for(int i=0; i<selectedTile.length; i++){
				for(int j=0; j<selectedTile[0].length; j++){
					if(!panels2[shape_index][i*selectedTile[0].length +j].getBackground().equals(Color.WHITE)){
						selectedTile[i][j]=selectedColor;
					}
					else if(panels2[shape_index][i*selectedTile[0].length +j].getBackground().equals(Color.WHITE)){
						selectedTile[i][j]=Color.WHITE;
					}
				}
			}
			setVisible(false);
			wd.setVisible(true);
		}
		System.out.println(selectedTile);
	}
    public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
    public void mouseEntered(MouseEvent arg0){}
    
	public void mouseReleased(MouseEvent arg0) {}
}