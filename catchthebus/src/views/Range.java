/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Image;

public class Range extends Sprite {

    private boolean visible;

    public Range(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }

    public boolean getVisible() {
        return this.visible;
    }

    @Override
    public void setVisible(boolean b) {
        this.visible = b;
    }
}
