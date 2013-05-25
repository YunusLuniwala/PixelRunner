/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.game.runner.base.element;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.game.runner.manager.ResourcesManager;

/**
 *
 * @author Karl
 */
public class ScrollMenuPageElement extends Sprite{
    private int id;
    private boolean locked;
    
    private Text textId;
    private Sprite lock;
    
    public ScrollMenuPageElement(float pX, float pY, int id, boolean locked, VertexBufferObjectManager pVertexBufferObjectManager){
        super(pX, pY, ResourcesManager.getInstance().lvlBack, pVertexBufferObjectManager);
        this.id = id;
        this.locked = locked;
        this.lock = new Sprite(this.getHeight()/2, this.getWidth()/2, ResourcesManager.getInstance().lvlLock, this.getVertexBufferObjectManager());
        this.attachChild(this.lock);
        this.textId = new Text(this.getHeight()/2, this.getWidth()/2, ResourcesManager.getInstance().fontPixel_60, String.valueOf(id), this.getVertexBufferObjectManager());
        this.attachChild(this.textId);
        this.refreshEntity();
    }
    
    public void setLocked(boolean locked){
        this.locked = locked;
        this.refreshEntity();
    }
    
    private void refreshEntity(){
        if(this.locked){
            this.lock.setVisible(true);
            this.textId.setVisible(false);
        }
        else{
            this.lock.setVisible(false);
            this.textId.setVisible(true);
        }
    }
    
    @Override
    public boolean detachSelf(){
        this.textId.detachSelf();
        this.lock.detachSelf();
        return super.detachSelf();
    }
    
    @Override
    public void dispose(){
        this.textId.dispose();
        this.lock.dispose();
        super.dispose();
    }
}
