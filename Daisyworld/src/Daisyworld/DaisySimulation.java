package Daisyworld;
import java.util.Random;
/**
 * DaisySimulation class simulate the grows of black and white daisies 
 * on a 30*30 map
 */
public class DaisySimulation {
	
	/**
	 * initiateMap function create a initial 30*30 map by randomly assign
	 * daisies on patches based on the intial portion
	 * @return a 30*30 patches that contains daisies
	 */
	private static Patch[][] initiateMap() {
		
		// 30x30 map
		Patch[][] daisyWorld = new Patch[Params.ROWS][Params.COLUMNS];
		
		// 20% of black daisies, 20% of white daisies
		int numBlack = (Params.BLACK_PORTION * Params.ROWS * Params.COLUMNS) / 100;
		int numWhite = (Params.WHITE_PORTION * Params.ROWS * Params.COLUMNS) / 100;		
		
		Random r = new Random();
		
		int i = 0, j = 0;
		
		// initiate the world map		
		for (i = 0; i < 30; i++) {
			// randomly assign daisies
			for (j = 0; j < 30; j++) {
				
				if (numBlack > 0 || numWhite > 0) {
					// generate black daisy
					if (numBlack > 0 && r.nextInt(100) <= Params.BLACK_PORTION) {
						daisyWorld[i][j] = new Patch(Params.DaisyType.BLACK);
						numBlack--;
					// generate black daisy
					} else if (numWhite > 0 && r.nextInt(100) <= Params.WHITE_PORTION) {
						daisyWorld[i][j] = new Patch(Params.DaisyType.WHITE);
						numWhite--;
					// empty place
					} else {
						daisyWorld[i][j] = new Patch();
					}
				} else {
					daisyWorld[i][j] = new Patch();
				}
			}
		}
		
		// regenerate map if not all daisies are spread
		if (numBlack > 0 && numWhite > 0) {
			System.out.println("regenerating map, please wait!");
			return initiateMap();
		}
		
		return daisyWorld;
	}
	
	public static void main(String[] args) {
		
		// initiate the world map
		Patch[][] daisyWorld = initiateMap();

		PatchController pc = new PatchController(daisyWorld);
		
		// start threads
		pc.start();
		
		// check all threads still alive
		while (pc.isAlive()) {
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				PatchControlThread.terminate(e);
			}
		}
		
		// interrupt other threads
		
		System.out.println("Sim terminating");
		System.exit(0);
	}

}
