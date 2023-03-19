/**
 * 
 */
package battleship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Fleet implements IFleet
{
    private List<IShip> ships;

    public Fleet()
    {
	ships = new ArrayList<>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see battleship.IFleet#addShip(battleship.IShip)
     */
    @Override
    public boolean addShip(IShip s)
    {
	boolean result = false;
	if ((ships.size() <= SQUAREGRIDSIZE) && (isInsideMap(s)) && (!colisionRisk(s)))
	{
	    ships.add(s);
	    result = true;
	}
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see battleship.IFleet#listShipsLike(java.lang.String)
     */
    @Override
    public List<IShip> listShipsLike(String category)
    {
	List<IShip> shipsLike = new ArrayList<>();
	for (IShip s : ships)
	{
	    if (s.getCategory().equals(category))
		shipsLike.add(s);
	}
	return shipsLike;
    }

    /*
     * (non-Javadoc)
     * 
     * @see battleship.IFleet#listFloatingShips()
     */
    @Override
    public List<IShip> listFloatingShips()
    {
	List<IShip> floatingShips = new ArrayList<>();
	for (IShip s : ships)
	{
	    if (s.stillFloating())
		floatingShips.add(s);
	}
	return floatingShips;
    }

    /*
     * (non-Javadoc)
     * 
     * @see battleship.IFleet#listAllShips()
     */
    @Override
    public List<IShip> listAllShips()
    {
	List<IShip> allShips = new ArrayList<>();
	for (IShip s : ships)
	{
	    allShips.add(s);
	}
	return allShips;
    }

    /*
     * (non-Javadoc)
     * 
     * @see battleship.IFleet#shipAt(battleship.IPosition)
     */
    @Override
    public IShip shipAt(IPosition pos)
    {
	for (int i = 0; i < ships.size(); i++)
	    if (ships.get(i).occupies(pos))
		return ships.get(i);
	return null;
    }

    private boolean isInsideMap(IShip s)
    {
	return (s.getLeftMostPos() >= 0 && s.getRightMostPos() <= SQUAREGRIDSIZE - 1 && s.getTopMostPos() >= 0
		&& s.getBottomMostPos() <= SQUAREGRIDSIZE - 1);
    }

    private boolean colisionRisk(IShip s)
    {
	for (int i = 0; i < ships.size(); i++)
	{
	    if (ships.get(i).tooCloseTo(s))
		return true;
	}
	return false;
    }

    /**
     * This operation prints a map showing the fleet
     * 
     * @param fleet The fleet to be shown
     */
    static void printFleet(IFleet fleet)
    {
        assert fleet != null;
        
        char[][] map = new char[Main.FULLFLEET][Main.FULLFLEET];
        for (int r = 0; r < Main.FULLFLEET; r++)
            for (int c = 0; c < Main.FULLFLEET; c++)
        	map[r][c] = '.';
    
        for (IShip sh : fleet.listAllShips())
        {
            Iterator<IPosition> it = sh.getPositions();
            while (it.hasNext())
            {
        	IPosition pos = it.next();
        	map[pos.getRow()][pos.getColumn()] = '#';
            }
    
        }
    
        for (int row = 0; row < Main.FULLFLEET; row++)
        {
            for (int col = 0; col < Main.FULLFLEET; col++)
        	Main.LOGGER.info(map[row][col]);
            Main.LOGGER.info("\n");
        }
    
    }

    /**
     * This operation shows the state of a fleet
     * 
     * @param fleet The fleet to be shown
     */
    static void printStatus(IFleet fleet)
    {
        assert fleet != null;
        
        printAllShips(fleet);
        printFloatingShips(fleet);
        printShipsByCategory(fleet, "Galeao");
        printShipsByCategory(fleet, "Fragata");
        printShipsByCategory(fleet, "Nau");
        printShipsByCategory(fleet, "Caravela");
        printShipsByCategory(fleet, "Barca");
    }

    /**
     * This operation prints all the ships of a fleet belonging to a particular
     * category
     * 
     * @param fleet    The fleet of ships
     * @param category The category of ships of interest
     */
    public static void printShipsByCategory(IFleet fleet, String category)
    {
        assert fleet != null;
        assert category != null;
        
        List<IShip> ships = fleet.listShipsLike(category);
        Main.printShips(ships);
    
    }

    /**
     * This operation prints all the ships of a fleet but not yet shot
     * 
     * @param fleet The fleet of ships
     */
    public static void printFloatingShips(IFleet fleet)
    {
        assert fleet != null;
        
        List<IShip> ships = fleet.listFloatingShips();
        Main.printShips(ships);
    }

    /**
     * This operation prints all the ships of a fleet
     * 
     * @param fleet The fleet of ships
     */
    public static void printAllShips(IFleet fleet)
    {
        List<IShip> ships = fleet.listAllShips();
        Main.printShips(ships);
    }

}
