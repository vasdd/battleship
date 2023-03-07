/**
 * 
 */
package pt.iscte_iul.istar.BattleshipGame;

import java.util.List;

public interface IFleet
{
    int SQUAREGRIDSIZE = 10;

    boolean addShip(IShip s);

    List<IShip> listShipsLike(String category);

    List<IShip> listFloatingShips();

    List<IShip> listAllShips();

    IShip shipAt(IPosition pos);
}
