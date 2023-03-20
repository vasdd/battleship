/**
 * 
 */
package battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fba
 *
 */
public class Game implements IGame
{
    private List<IPosition> shots;
    private IFleet fleet;
    private int countInvalidShots;
    private int countRepeatedShots;
    private int countHits;
    private int countSinks;

    /**
     * 
     */
    public Game(IFleet fleet)
    {
	shots = new ArrayList<>();
	countInvalidShots = 0;
	countRepeatedShots = 0;
	this.fleet = fleet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see battleship.IGame#fire(battleship.IPosition)
     */
    @Override
    public IShip fire(IPosition pos)
    {
	if (!validateShot(pos))
	    countInvalidShots++;
	else
	{ // valid shot!
	    if (repeatedShot(pos))
		countRepeatedShots++;
	    else
	    {
		shots.add(pos);
		IShip s = fleet.shipAt(pos);
		if (s != null)
		{
		    s.shoot(pos);
		    countHits++;
		    if (!s.stillFloating())
		    {
			countSinks++;
			return s;
		    }
		}
	    }
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see battleship.IGame#getShots()
     */
    @Override
    public List<IPosition> getShots()
    {
	return shots;
    }

    /*
     * (non-Javadoc)
     * 
     * @see battleship.IGame#getRepeatedShots()
     */
    @Override
    public int getRepeatedShots()
    {
	return this.countRepeatedShots;
    }

    /*
     * (non-Javadoc)
     * 
     * @see battleship.IGame#getInvalidShots()
     */
    @Override
    public int getInvalidShots()
    {
	return this.countInvalidShots;
    }

    /*
     * (non-Javadoc)
     * 
     * @see battleship.IGame#getHits()
     */
    @Override
    public int getHits()
    {
	return this.countHits;
    }

    /*
     * (non-Javadoc)
     * 
     * @see battleship.IGame#getSunkShips()
     */
    @Override
    public int getSunkShips()
    {
	return this.countSinks;
    }

    /*
     * (non-Javadoc)
     * 
     * @see battleship.IGame#getRemainingShips()
     */
    @Override
    public int getRemainingShips()
    {
	List<IShip> floatingShips = fleet.listFloatingShips();
	return floatingShips.size();
    }

    @SuppressWarnings("static-access")
    private boolean validateShot(IPosition pos)
    {
	return (pos.getRow() >= 0 && pos.getRow() <= fleet.SQUAREGRIDSIZE && pos.getColumn() >= 0
		&& pos.getColumn() <= fleet.SQUAREGRIDSIZE);
    }

    private boolean repeatedShot(IPosition pos)
    {
	for (int i = 0; i < shots.size(); i++)
	    if (shots.get(i).equals(pos))
		return true;
	return false;
    }

    /**
     * This operation prints a map showing valid shots that have been fired
     * 
     * @param game The context game while shots have been fired
     */
    public void printAllValidShots()
    {
        char[][] map = new char[Main.FULLFLEET][Main.FULLFLEET];
        for (int r = 0; r < Main.FULLFLEET; r++)
            for (int c = 0; c < Main.FULLFLEET; c++)
        	map[r][c] = '.';
    
        for (IPosition pos : this.getShots())
            map[pos.getRow()][pos.getColumn()] = 'X';
    
        for (int row = 0; row < Main.FULLFLEET; row++)
        {
            for (int col = 0; col < Main.FULLFLEET; col++)
        	Main.LOGGER.info(map[row][col]);
            Main.LOGGER.info("\n");
        }
    
    }

}
