/*
 *  Another simple GUI demonstration using swing.
 *  Here the GridSquare class extends JPanel and
 *  holds its coordinates in the grid as attributes.
 *  @author mhatcher
 */
public class Driver
{
	public static void main(String[] args)
	{
		// create a new GUI window
		WindowDemo1 demo = new WindowDemo1();
		WindowDemo demo1 = new WindowDemo(9,9, demo);
		demo.linkTo(demo1);
	}
}
