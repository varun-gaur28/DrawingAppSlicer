package ca.usask.drawingtoolslicer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import android.content.Context;
import android.graphics.BitmapFactory;

public class ToolSlicerExperiment extends Experiment {

	//private float[] speeds = {0.27f, 0.41f, 0.60f}; // In %age screen per second
    private float[] speeds = {0.17f, 0.27f, 0.37f}; // In %age screen per second
	private int[] blocks_per_speed = {4, 8, 8}; // Each block includes all 12 enemy types in some permutation
    private int noOfTabs=3;

	public ToolSlicerExperiment(long randomSeed, int curTrial, Context c) {
		super(randomSeed, curTrial);
        fTM = new ArrayList<FastTapMenu>();
		if(noOfTabs==1){
            fastTapMenu1 = new DrawingToolFastTapMenu1(c);
            fTM.add(fastTapMenu1);
        }
        else if(noOfTabs==2){
            fastTapMenu1 = new DrawingToolFastTapMenu1(c);
            fTM.add(fastTapMenu1);
            fastTapMenu2 = new DrawingToolFastTapMenu2(c);
            fTM.add(fastTapMenu2);
        }
        else if(noOfTabs==3){
            fastTapMenu1 = new DrawingToolFastTapMenu1(c);
            fTM.add(fastTapMenu1);
            fastTapMenu2 = new DrawingToolFastTapMenu2(c);
            fTM.add(fastTapMenu2);
            fastTapMenu3 = new DrawingToolFastTapMenu3(c);
            fTM.add(fastTapMenu3);
        }

        sliceLayer = new SliceLayer();
		
		final Random random = new Random(randomSeed);
		
		Trial startCountdown = new CountdownTrial(5, "Starting in...");
		
		ArrayList<Trial> trials = new ArrayList<Trial>();
		trials.add(startCountdown);
		
		int trialnum = 0;
		String blockedItem = ToolItem.Blogger.name;
		for (int i = 0; i < speeds.length; i++) {
			for (int j = 0; j < blocks_per_speed[i]; j++) {
				ToolItem[] block;
				do {
					block = generateBlock(random);
				} while (block[0].name == blockedItem);
				blockedItem = block[block.length-1].name;
				for (ToolItem ti : block) {
					trials.add(new MovingEnemyTrial(this, trialnum++, ti.name, speeds[i], 0xffcccccc, BitmapFactory.decodeResource(c.getResources(), ti.icon)));
				}
			}
		}
		
		this.trials = trials.toArray(new Trial[trials.size()]);
	}

	@Override
	public void setView(GameView gameView) {
		super.setView(gameView);
		for(int i=0; i<fTM.size(); i++){
            this.fTM.get(i).setGameView(gameView);
        }
		this.sliceLayer.setGameView(gameView);
	}
	
    private ToolItem[] generateBlock(Random random) {
        ToolItem[] items = ToolItem.enemies1.clone();
        if(noOfTabs==1){
            items = ToolItem.enemies1.clone();
            Collections.shuffle(Arrays.asList(items), random);

        }
        else if(noOfTabs==2){
            items = ToolItem.enemies2.clone();
            Collections.shuffle(Arrays.asList(items), random);

        }
        else if(noOfTabs==3){
            items = ToolItem.enemies3.clone();
            Collections.shuffle(Arrays.asList(items), random);

        }
        return items;
    }

}
