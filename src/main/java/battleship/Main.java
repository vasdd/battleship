/**
 * 
 */
package battleship;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author britoeabreu
 * @author adrianolopes
 * @author miguelgoulao
 */
public class Main
{
    static final Logger LOGGER = LogManager.getLogger();

    private static final String GOODBYE_MESSAGE = "Bons ventos!";
    static final int FULLFLEET = IFleet.SQUAREGRIDSIZE;

    /**
     * Strings to be used by the user
     */
    private static final String DESISTIR = "desisto";
    private static final String RAJADA = "rajada";
    private static final String NOVAFROTA = "nova";
    private static final String BARCA = "barca";
    private static final String CARAVELA = "caravela";
    private static final String NAU = "nau";
    private static final String FRAGATA = "fragata";
    private static final String GALEAO = "galeao";
    private static final String VERTIROS = "ver";
    private static final String BATOTA = "mapa";
    private static final String STATUS = "estado";

    /**
     * @param args
     */
    public static void main(String[] args)
    {
	LOGGER.info("***  Battleship  ***");
	try
	{
	    taskA();
//	    taskB();
//	    taskC();
//	    taskD();
	} catch (IllegalArgumentException excep)
	{
	    LOGGER.error("Caught IllegalArgumentException: {}", excep.getMessage());
	}

    }

    /////////////////////////////////////////////////////////////////////////////
    // hereafter one may find some code that can be converted to automatic tests,
    // as long as appropriate changes are made. It also shows that we should
    // develop our code incrementally eg. first the ships, then the fleet,
    // then some rule checking, then dealing with firing and so on

    /**
     * This task tests the building up of ships: For each ship, reads positions and
     * indicates whether the ship occupies each one of such positions or not
     */
    public static void taskA()
    {
	Scanner in = new Scanner(System.in);
	while (in.hasNext())
	{
	    Ship s = readShip(in);
	    for (int i = 0; i < 3; i++)
	    {
		Position p = readPosition(in);
		LOGGER.info("{} {}", p, s.occupies(p));
	    }
	}
    }

    /**
     * This task tests the building up of fleets
     */
    public static void taskB()
    {
	Scanner in = new Scanner(System.in);
	IFleet fleet = null;
	String command = in.next();
	while (!command.equals(DESISTIR))
	{
	    switch (command)
	    {
	    case NOVAFROTA:
		fleet = buildFleet(in, FULLFLEET);
		break;
	    case STATUS:
		Fleet.printStatus(fleet);
		break;
	    default:
		LOGGER.info("Que comando é esse??? Repete lá ...");
	    }
	    // The other commands are unknown in this task
	    command = in.next();
	}
	LOGGER.info(GOODBYE_MESSAGE);
    }

    /**
     * This task tests the building up of fleets and takes into consideration the
     * possibility of cheating
     */
    public static void taskC()
    {
	Scanner in = new Scanner(System.in);
	IFleet fleet = null;
	String command = in.next();
	while (!command.equals(DESISTIR))
	{
	    switch (command)
	    {
	    case NOVAFROTA:
		fleet = buildFleet(in, FULLFLEET);
		break;
	    case STATUS:
		Fleet.printStatus(fleet);
		break;
	    case BATOTA:
		LOGGER.info(fleet);
		break;
	    default:
		LOGGER.info("Que comando é esse??? Repete lá ...");
	    }
	    // The other commands are unknown in this task
	    command = in.next();
	}
	LOGGER.info(GOODBYE_MESSAGE);
    }

    /**
     * This task also tests the fighting element of a round of three shots
     */
    public static void taskD()
    {
	Scanner in = new Scanner(System.in);
	IFleet fleet = null;
	IGame game = null;
	String command = in.next();
	while (!command.equals(DESISTIR))
	{
	    switch (command)
	    {
	    case NOVAFROTA:
		fleet = buildFleet(in, FULLFLEET);
		game = new Game(fleet);
		break;
	    case STATUS:
		Fleet.printStatus(fleet);
		break;
	    case BATOTA:
		Fleet.printFleet(fleet);
		break;
	    case RAJADA:
		firingRound(in, game);
		LOGGER.info("Hits: {} Inv: {} Rep: {} Restam {} navios.", game.getHits(), game.getInvalidShots(),
			game.getRepeatedShots(), game.getRemainingShips());
		if (game.getRemainingShips() == 0)
		    LOGGER.info("Maldito sejas, Java Sparrow, eu voltarei, glub glub glub...");
		break;
	    case VERTIROS:
		Game.printAllValidShots(game);
		break;
	    default:
		LOGGER.info("Que comando é esse??? Repete ...");
	    }
	    command = in.next();
	}
	LOGGER.info(GOODBYE_MESSAGE);
    }

    /**
     * This operation prints all the given ships
     * 
     * @param ships The list of ships
     */
    static void printShips(List<IShip> ships)
    {
	assert ships != null;

	for (IShip ship : ships)
	    LOGGER.info(ship);
	LOGGER.info("\n");
    }

    /**
     * This operation allows the build up of a fleet, given user data
     * 
     * @param in The scanner to read from
     * @param n  The number of ships of the wanted fleet
     * @return The fleet that has been built
     */
    public static IFleet buildFleet(Scanner in, int n)
    {
	assert in != null;

	IFleet fleet = new Fleet();
	int i = 0; // i represents the total of successfully created ships
	while (i <= n)
	{
	    IShip s = readShip(in);
	    if (s != null)
	    {
		boolean success = fleet.addShip(s);
		if (success)
		    i++;
		else
		    LOGGER.info("Falha na criacao de {} {} {}", s.getCategory(), s.getBearing(), s.getPosition());
	    } else
	    {
		LOGGER.info("Navio desconhecido!");
	    }
	}
	LOGGER.info("{} navios adicionados com sucesso!", i);
	return fleet;
    }

    /**
     * This operation reads data about a ship, build it and returns it
     * 
     * @param in The scanner to read from
     * @return The created ship based on the data that has been read
     */
    private static Ship readShip(Scanner in)
    {
	String shipKind = in.next();
	Position pos = readPosition(in);
	char c = in.next().charAt(0);
	Compass bearing = Compass.charToCompass(c);
	return buildShip(shipKind, bearing, pos);
    }

    /**
     * @param shipKind
     * @param bearing
     * @param pos
     * @return
     */
    private static Ship buildShip(String shipKind, Compass bearing, Position pos)
    {
	switch (shipKind)
	{
	case BARCA:
	    return new Barge(bearing, pos);
	case CARAVELA:
	    return new Caravel(bearing, pos);
	case NAU:
	    return new Carrack(bearing, pos);
	case FRAGATA:
	    return new Frigate(bearing, pos);
	case GALEAO:
	    return new Galleon(bearing, pos);
	default:
	    return null;
	}
    }

    /**
     * This operation allows reading a position in the map
     * 
     * @param in The scanner to read from
     * @return The position that has been read
     */
    private static Position readPosition(Scanner in)
    {
	int row = in.nextInt();
	int column = in.nextInt();
	return new Position(row, column);
    }

    /**
     * This operation allows firing a round of shots (three) over a fleet, in the
     * context of a game
     * 
     * @param in   The scanner to read from
     * @param game The context game while fleet is being attacked
     */
    public static void firingRound(Scanner in, IGame game)
    {
	for (int i = 0; i < 3; i++)
	{
	    IPosition pos = readPosition(in);
	    IShip sh = game.fire(pos);
	    if (sh != null)
		LOGGER.info("Mas... mas... {}s nao sao a prova de bala? :-(", sh.getCategory());
	}

    }
}
