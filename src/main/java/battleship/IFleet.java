/**
 * 
 */
package battleship;

import java.util.List;

public interface IFleet
{
    Integer SQUAREGRIDSIZE = 10;

    boolean addShip(IShip s);

    List<IShip> listShipsLike(String category);

    List<IShip> listFloatingShips();

    List<IShip> listAllShips();

    IShip shipAt(IPosition pos);
}
