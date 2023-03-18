package battleship;

/**
 * @author fba
 *
 */
public enum Compass
{
    NORTH('n'), SOUTH('s'), EAST('e'), WEST('o'), UNKNOWN('u');

    private final char c;

    Compass(char c)
    {
	this.c = c;
    }
    
    public char getDirection()
    {
	return c;
    }

    public String toString()
    {
	return "" + c;
    }
}
