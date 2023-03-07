/**
 * 
 */
package pt.iscte_iul.istar.BattleshipGame;

import java.util.List;

public interface IGame
{
    IShip fire(IPosition pos);

    List<IPosition> getShots();

    int getRepeatedShots();

    int getInvalidShots();

    int getHits();

    int getSunkShips();

    int getRemainingShips();
}
