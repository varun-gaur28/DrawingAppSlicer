package ca.usask.drawingtoolslicer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import android.content.Context;

public class StaticEnemyExperiment extends Experiment {

	private float[] speeds = {0.27f, 0.41f, 0.66f}; // In %age screen per second
	private int[] blocks_per_speed = {4, 8, 8}; // Each block includes all 12 enemy types in some permutation
	private int static_blocks = 4;
	
//	private float[] speeds = {0.41f}; // In %age screen per second
//	private int[] blocks_per_speed = {1}; // Each block includes all 12 enemy types in some permutation
//	private int static_blocks = 1;
	
	public StaticEnemyExperiment(long randomSeed, int curTrial, Context c) {
		super(randomSeed, curTrial);

		fastTapMenu1 = new ColorFastTapMenu(c);
        fastTapMenu2 = new ColorFastTapMenu(c);
        fTM.add(fastTapMenu1);
        fTM.add(fastTapMenu2);
		sliceLayer = new SliceLayer();
		
		final Random random = new Random(randomSeed);
		
		Trial startCountdown = new CountdownTrial(5, "Starting in...");
		Trial askExperimenter = new MessageButtonTrial("Ask the experimenter for instructions\nand click OK when instructed to.", "OK");
		
		ArrayList<Trial> trials = new ArrayList<Trial>();

		trials.add(startCountdown);
		
		int trialnum = 0;
		String blockedItem = ColorItem.Black.name;
		for (int i = 0; i < speeds.length; i++) {
			for (int j = 0; j < blocks_per_speed[i]; j++) {
				ColorItem[] block;
				do {
					block = generateBlock(random);
				} while (block[0].name == blockedItem);
				blockedItem = block[block.length-1].name;
				for (ColorItem ci : block) {
					trials.add(new MovingEnemyTrial(this, trialnum++, ci.name, speeds[i], ci.color, null));
				}
			}
		}
		trials.add(askExperimenter);
		for (int i = 0; i < static_blocks; i++) {
			ColorItem[] block;
			do {
				block = generateBlock(random);
			} while (block[0].name == blockedItem);
			blockedItem = block[block.length-1].name;
			for (ColorItem ci : block) {
				trials.add(new StaticEnemyTrial(this, trialnum++, ci.name, ci.color, null));
			}			
		}
		
		this.trials = trials.toArray(new Trial[trials.size()]);
	}

	@Override
	public void setView(GameView gameView) {
		super.setView(gameView);
        for(int i=0; i<fTM.size(); i++) {
            this.fTM.get(i).setGameView(gameView);
        }
		this.sliceLayer.setGameView(gameView);
	}
	
    private ColorItem[] generateBlock(Random random) {
    	ColorItem[] items = ColorItem.enemies.clone();
    	Collections.shuffle(Arrays.asList(items), random);
    	return items;
    }

}
