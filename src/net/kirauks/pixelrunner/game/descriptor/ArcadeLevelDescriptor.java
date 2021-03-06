/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.kirauks.pixelrunner.game.descriptor;

import java.util.Random;
import net.kirauks.pixelrunner.game.element.level.BonusJump;
import net.kirauks.pixelrunner.game.element.level.BonusLife;
import net.kirauks.pixelrunner.game.element.level.BonusSlow;
import net.kirauks.pixelrunner.game.element.level.BonusSpeed;
import net.kirauks.pixelrunner.game.element.level.BonusSwap;
import net.kirauks.pixelrunner.game.element.level.LevelElement;
import net.kirauks.pixelrunner.game.element.level.Platform;
import net.kirauks.pixelrunner.game.element.level.Rocket;
import net.kirauks.pixelrunner.game.element.level.Trap;
import net.kirauks.pixelrunner.game.element.level.Wall;

/**
 *
 * @author Karl
 */
public class ArcadeLevelDescriptor extends LevelDescriptor{
    private enum PrevState{
        BONUS,
        TRAP,
        PLATFORM
    }
    
    private Random ranGen = new Random();
    private String music = "arcade-" + String.valueOf(1 + this.ranGen.nextInt(5)) + ".xm";
    private PrevState prevState;
    private int platLayer;
    
    @Override
    public LevelElement[] getNext() {
        int rand;
        switch(this.prevState){
            case BONUS: //WAS BONUS
                rand = ranGen.nextInt(100);
                if(rand < 80){ // Rocket @ 80%
                    this.prevState = PrevState.TRAP;
                    return new LevelElement[]{this.getTrap()};
                }
                else{ // Platform @ 20%
                    this.prevState = PrevState.PLATFORM;
                    this.platLayer = PLATFORM_LOW_LAYER;
                    return new LevelElement[]{new Platform(this.platLayer)};
                }
            case TRAP: //WAS TRAP
                rand = ranGen.nextInt(100);
                if(rand < 5){ // Bonus @ layer 3 @ 5%
                    this.prevState = PrevState.BONUS;
                    return new LevelElement[]{this.getBonus(3)};
                }
                else if(rand >=5 && rand < 80){ // Rocket @ 75%
                    this.prevState = PrevState.TRAP;
                    return new LevelElement[]{this.getTrap()};
                }
                else{ // Platform @ 20%
                    this.prevState = PrevState.PLATFORM;
                    this.platLayer = PLATFORM_LOW_LAYER;
                    return new LevelElement[]{new Platform(this.platLayer)};
                }
            default: //WAS PLATFORM
            case PLATFORM:
                rand = ranGen.nextInt(100);
                int deviation = (int)(((float)this.platLayer / (float)LAYERS_MAX) * 50f); // 0% @ layer 0, 70% @ layer max
                if(rand < (100 - deviation)){ // Platform @ 100% ~ 50%
                    this.prevState = PrevState.PLATFORM;
                    rand = ranGen.nextInt(100);
                    if(rand < 90 && this.platLayer != LAYERS_MAX){ // Plateform @ layer + @ 90%
                        this.platLayer += 1 + this.ranGen.nextInt(2);
                    }
                    if(this.platLayer > 4){
                        return new LevelElement[]{new Platform(this.platLayer), this.getTrap()};
                    }
                    else if(this.platLayer > 2){
                        return new LevelElement[]{new Platform(this.platLayer), this.getSmallTrap()};
                    }
                    else{
                        return new LevelElement[]{new Platform(this.platLayer)};
                    }
                }
                else{ // Bonus + trap @ 0% ~ 50%
                    rand = ranGen.nextInt(100);
                    if(rand < 70){ // Bonus @ layer +3  & Trap @ layer 0 @ 70%
                        this.prevState = PrevState.BONUS;
                        return new LevelElement[]{this.getBonus(this.platLayer + 3), this.getTrap()};
                    }
                    else{ // Rocket @ layer 0 @ 30%
                        this.prevState = PrevState.TRAP;
                        return new LevelElement[]{new Rocket(this.platLayer + 3), this.getTrap()};
                    }
                }
        }
    }
    private LevelElement getBonus(int layer){
        switch(this.ranGen.nextInt(5)){
            case 0:
                return new BonusJump(layer);
            case 1:
                return new BonusLife(layer);
            case 2:
                return new BonusSlow(layer);
            case 3:
                return new BonusSwap(layer);
            default:
            case 4:
                return new BonusSpeed(layer);
        }
    }
    private LevelElement getTrap(){
        switch(this.ranGen.nextInt(3)){
            case 0:
                return new Rocket(0);
            case 1:
                return new Wall(0);
            default:
            case 2:
                return new Trap(0);
        }
    }
    private LevelElement getSmallTrap(){
        switch(this.ranGen.nextInt(2)){
            case 0:
                return new Rocket(0);
            default:
            case 1:
                return new Trap(0);
        }
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public void init() {
        this.prevState = PrevState.TRAP;
    }

    @Override
    public String getMusic() {
        return this.music;
    }

    @Override
    public float getSpawnTime() {
        return 1;
    }

    @Override
    public float getSpawnSpeed() {
        return 500;
    }
}
