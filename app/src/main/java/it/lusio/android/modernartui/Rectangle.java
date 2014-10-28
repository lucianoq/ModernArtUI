package it.lusio.android.modernartui;

import android.widget.ImageView;

/**
 * Class for rectangles.
 * It contains the ImageView and the original color with relative
 * getters and setters.
 *
 * @author Luciano Quercia
 */
class Rectangle {
    private ImageView imageView;
    private int originalColor;

    public Rectangle(ImageView imageView, int originalColor) {
        this.imageView = imageView;
        this.originalColor = originalColor;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getOriginalColor() {
        return originalColor;
    }

    public void setOriginalColor(int originalColor) {
        this.originalColor = originalColor;
    }
}
