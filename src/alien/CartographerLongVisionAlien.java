package alien;

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Random;
 import planet.Move;

 /**
  * Created by moogie on 09/07/14.
  * 
  * This Alien attempts to map the visible and guess the movements within the immediate non-visible area around the alien.
  * To this end, the alien can initially see 5x5 grid. It applies weights on the cells of its internal map based on the 
  * prey/alien/blanks within its field of view. It then performs a simple blur to guess movements and then chooses the optimum move
  * based on the contents of its internal map.
  * 
  * If it is asked to fight, it performs battle simulations to determine whether it should nominate to fight.
  */
 public class CartographerLongVisionAlien extends Alien
 {
   private final static byte LIFE = 0, STR = 1, DEF = 2, VIS = 3, CLV = 4;

   // Plant Entity symbols
   private static final char TURTLE = 'T';
   private static final char HUMAN = 'H';
   private static final char WHALE = 'W';
   private static final char COW = 'C';
   private static final char EAGLE = 'E';
   private static final char ALIEN = 'A';
   private static final HashMap<Character, Move> preyMovement = new HashMap<>();

   static 
   {
     preyMovement.put(WHALE, Move.STAY);
     preyMovement.put(TURTLE, Move.SOUTHWEST);
     preyMovement.put(HUMAN, Move.NORTHEAST);
   };

   // Map constants
   public static final int MAP_SIZE_DIV_2 = 10;
   public static final int MAP_SIZE = MAP_SIZE_DIV_2 * 2 + 1;
   private static final int MAP_SIZE_MINUS_ONE = MAP_SIZE - 1;
   private static final double FADE_FACTOR=0.85;

   // Planet constants
   private static final int STARTING_HP = 10;
   private static final int START_HEALING_FACTOR = 5;
   private static final int MAX_DEFENCE = 50;

   // Battle Simulation constants
   private static final double AMBIGUOUS_ENEMY_HP_FACTOR = 2;
   private static final int SIMULATED_BATTLE_COUNT = 100;
   private static final int SIMULATED_BATTLE_THREASHOLD = (int)(SIMULATED_BATTLE_COUNT*1.0);

   private Random rand = new Random();

   /** The alien's map of the immediate and mid-range area */
   public double[][] map = new double[MAP_SIZE][MAP_SIZE];

   public void setAbilityPoints( float[] abilities )
   {
     // +0.5 gain due to rounding trick "borrowed" from PredicatClaw http://codegolf.stackexchange.com/a/32925/20193
     abilities[LIFE] = 3.5f; // We do need some hit points to ensure that we can survive the melee of the beginning game.
     abilities[STR] = 4.5f; // A Moderate attack strength means that we do not have to go through as many fight rounds.
     abilities[DEF] = 0; // We will get from prey and other aliens
     abilities[VIS] = 2; // A minimum of 2 is required to get a 5x5 field of view
     abilities[CLV] = 0; // We will get from prey and other aliens
   }

   /**
    * simulate alien memory fade. This allows an alien to eventually venture back to areas already traversed.
    */
   private void fadeMap()
   {
     for ( int y = 0; y < MAP_SIZE; y++ )
     {
       for ( int x = 0; x < MAP_SIZE; x++ )
       {
         map[x][y] *= FADE_FACTOR;
       }
     }
   }

   /**
    * shift the maps with the movement of the alien so that the alien is always at the centre of the map
    * 
    * @param move
    */
   private void shiftMaps( Move move )
   {
     int i, j;
     final int offsetX = -move.getXOffset();
     final int offsetY = -move.getYOffset();
     double[][] tempMap = new double[MAP_SIZE][MAP_SIZE];
     for ( int y = 0; y < MAP_SIZE; y++ )
     {
       for ( int x = 0; x < MAP_SIZE; x++ )
       {
         i = x + offsetX;
         j = y + offsetY;

         if ( i >= 0 && i <= MAP_SIZE_MINUS_ONE && j >= 0 && j <= MAP_SIZE_MINUS_ONE )
         {
           tempMap[i][j] = map[x][y];
         }
       }
     }
     map = tempMap;
   }

   /**
    * Updates a cell in the alien's map with the desirability of the entity in the cell
    * 
    * @param x
    * @param y
    * @param chr
    */
   private void updateMap( int x, int y, char chr )
   {
     // Note that these desire values are just guesses... I have not performed any analysis to determine the optimum values!
     // That said, they seem to perform adequately.
     double desire = 0;

     int i=x;
     int j=y;
     switch ( chr )
     {
       case WHALE:
         desire=2;
         break;
       case TURTLE:
       case HUMAN:
         desire=1;
         Move preyMove = preyMovement.get( chr );

         // predict movement into the future
         while ( i >= 0 && i <= MAP_SIZE_MINUS_ONE && j >= 0 && j <= MAP_SIZE_MINUS_ONE )
         {
           map[i][j] = ( map[i][j] + desire ) / 2;
           i+=preyMove.getXOffset();
           j+=preyMove.getYOffset();
           desire/=2;
         }
         break;
       case COW:
         desire = 0.5;
         break;
       case EAGLE:
         desire = 1;
         break;
       case ALIEN:
         desire = -10;
         break;
     }

     map[x][y] = ( map[x][y] + desire ) / 2;
   }

   /**
    * Apply a blur the map to simulate the movement of previously seen entities that are no longer within the field of view.
    */
   private void convolve()
   {
     double[][] tempMap = new double[MAP_SIZE][MAP_SIZE];

     int count;
     double temp;
     for ( int y = 0; y < MAP_SIZE; y++ )
     {
       for ( int x = 0; x < MAP_SIZE; x++ )
       {
         count = 0;
         temp = 0;
         for ( int i = x - 1; i < x + 2; i++ )
         {
           for ( int j = y - 1; j < y + 2; j++ )
           {
             if ( i >= 0 && i <= MAP_SIZE_MINUS_ONE && j >= 0 && j <= MAP_SIZE_MINUS_ONE )
             {
               temp += map[i][j];
               count++;
             }
           }
         }
         temp += map[x][y] * 2;
         count += 2;

         tempMap[x][y] = temp / count;
       }
     }
     map = tempMap;
   }

   /**
    * Determine the move that minimises the risk to this alien and maximises any potential reward.
    * 
    * @param fields
    * @return
    */
   private Move findBestMove( char[][] fields )
   {
     List<Move> moveOptions = new ArrayList<>();
     double bestMoveScore = -Double.MAX_VALUE;
     double score;

     // find the moves that have the best score using the alien's map
     for ( Move move : Move.values() )
     {
       int x = MAP_SIZE_DIV_2 + move.getXOffset();
       int y = MAP_SIZE_DIV_2 + move.getYOffset();
       score = map[x][y];
       if ( score == bestMoveScore )
       {
         moveOptions.add( move );
       }
       else if ( score > bestMoveScore )
       {
         bestMoveScore = score;
         moveOptions.clear();
         moveOptions.add( move );
       }
     }

     Move move = moveOptions.get( rand.nextInt( moveOptions.size() ) );

     // if the best move is to stay...
     if ( move == Move.STAY )
     {
       // find whether there are no surrounding entities in field of vision...
       int midVision = getVisionFieldsCount();
       boolean stuck = true;
       out: for ( int i = 0; i < fields.length; i++ )
       {
         for ( int j = 0; j < fields.length; j++ )
         {
           if ( !( i == midVision && j == midVision ) && fields[i][j] != ' ' )
           {
             stuck = false;
             break out;
           }
         }
       }

       // there there are no other entities within field of vision and we are healthy... choose a random move
       if ( stuck && getCurrentHp() > getLifeLvl() * 2 )
       {
         move = Move.getRandom();
       }
     }
     return move;
   }

   /**
    * Update the alien's map with the current field of vision
    * 
    * @param fields
    */
   private void mapVisibleSurroundings( char[][] fields )
   {
     int midVision = getVisionFieldsCount();

     // update the map with currently visible information
     for ( int y = -midVision; y <= midVision; y++ )
     {
       for ( int x = -midVision; x <= midVision; x++ )
       {
         char chr = fields[midVision + x][midVision + y];
         updateMap( MAP_SIZE_DIV_2 + x, MAP_SIZE_DIV_2 + y, chr );
       }
     }

     // ensure that the map where this alien currently sits is marked as empty.
     updateMap( MAP_SIZE_DIV_2, MAP_SIZE_DIV_2, ' ' );
   }

   public Move move( char[][] fields )
   {
     Move returnMove = null;

     // pre-move decision processing
     mapVisibleSurroundings( fields );
     convolve();

     returnMove = findBestMove( fields );

     // post-move decision processing
     fadeMap();
     shiftMaps( returnMove );

     return returnMove;
   }

   public boolean wantToFight( final int[] enemyAbilities )
   {
     double toughnessFactor =((double) enemyAbilities[STR])/(enemyAbilities[LIFE]*10); // a fudge-factor to ensure that whales are attacked.
     if (enemyAbilities[VIS]>=3 && enemyAbilities[LIFE]>4.5f) toughnessFactor*=3.5; // make sure that we do not attack other Cartogapher aliens 
     return winsBattleSimulation( enemyAbilities, toughnessFactor );
   }

   /**
    * Perform simulations to determine whether we will win against the enemy most of the time.
    * 
    * @param enemyAbilities
    * @return
    */
   private boolean winsBattleSimulation( int[] enemyAbilities, double toughnessFactor )
   {
     int surviveCount = 0;
     for ( int i = 0; i < SIMULATED_BATTLE_COUNT; i++ )
     {
       int estimatedEnemyHitPoints =
           STARTING_HP + (int)( enemyAbilities[LIFE] * START_HEALING_FACTOR * AMBIGUOUS_ENEMY_HP_FACTOR * toughnessFactor );
       int enemyPoints = estimatedEnemyHitPoints;
       int myHitPoints = getCurrentHp();
       int myDefenceLevel = getDefenseLvl() < MAX_DEFENCE ? getDefenseLvl() : MAX_DEFENCE;
       int enemyDefenceLevel = enemyAbilities[DEF] < MAX_DEFENCE ? enemyAbilities[DEF] : MAX_DEFENCE;

       while ( !( myHitPoints <= 0 || enemyPoints <= 0 ) )
       {
         if ( rand.nextInt( MAX_DEFENCE / myDefenceLevel + 1 ) != 0 )
         {
           myHitPoints -= rand.nextInt( enemyAbilities[STR] ) + 1;
         }

         if ( rand.nextInt( MAX_DEFENCE / enemyDefenceLevel + 1 ) != 0 )
         {
           enemyPoints -= rand.nextInt( getStrengthLvl() ) + 1;
         }
       }
       if ( myHitPoints > 0 )
       {
         surviveCount++;
       }
     }
     return ( surviveCount >= SIMULATED_BATTLE_THREASHOLD );
   }

 }