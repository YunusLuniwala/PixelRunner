/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.kirauks.pixelrunner.scene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;
import net.kirauks.pixelrunner.GameActivity;
import net.kirauks.pixelrunner.scene.base.BaseScene;
import net.kirauks.pixelrunner.manager.SceneManager.SceneType;

/**
 *
 * @author Karl
 */
public class LoadingScene extends BaseScene{
    public static abstract class LoadingListener{
        public abstract void onPause();
        public abstract void onResume();
    }
    
    private Text title;
    private LoadingListener listener;
    
    @Override
    public void createScene() {
        this.setBackground(new Background(Color.BLACK));
        this.title = new Text(GameActivity.CAMERA_WIDTH/2, GameActivity.CAMERA_HEIGHT/2, resourcesManager.fontPixel_60, "LOADING...", vbom);
        attachChild(this.title);
    }
    
    public void setLoadingListener(LoadingListener listener){
        this.listener = listener;
        
    }
    public void removeLoadingListener(){
        this.listener = null;
    }

    @Override
    public void onBackKeyPressed() {
    }
    
    @Override
    public void onMenuKeyPressed(){  
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.SCENE_LOADING;
    }

    @Override
    public void onPause() {
        if(this.listener != null){
            this.listener.onPause();
            this.audioManager.pause();
        }
    }

    @Override
    public void onResume() {
        if(this.listener != null){
            this.listener.onResume();
            this.audioManager.resume();
        }
    }

    @Override
    public void disposeScene() {
        this.title.detachSelf();
        this.title.dispose();
        this.detachSelf();
        this.dispose();
    }
}
