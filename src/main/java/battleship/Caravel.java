/**
 * 
 */
package battleship;

public class Caravel extends Ship
{
    private static final int SIZE = 2;
    private static final String NAME = "Caravela";

    /**
     * @param bearing the bearing where the Caravel heads to
     * @param pos initial point for positioning the Caravel
     */
    public Caravel(Compass bearing, IPosition pos) throws NullPointerException, IllegalArgumentException
    {
	super(Caravel.NAME, bearing, pos);

	if (bearing == null)
	    throw new NullPointerException("ERROR! invalid bearing for the caravel");

	switch (bearing)
	{
	case NORTH, SOUTH:
	    for (int r = 0; r < SIZE; r++)
		positions.add(new Position(pos.getRow() + r, pos.getColumn()));
	    break;
	case EAST, WEST:
	    for (int c = 0; c < SIZE; c++)
		positions.add(new Position(pos.getRow(), pos.getColumn() + c));
	    break;
	default:
	    throw new IllegalArgumentException("ERROR! invalid bearing for the caravel");
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see battleship.Ship#getSize()
     */
    @Override
    public int getSize()
    {
	return SIZE;
    }

}
