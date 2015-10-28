package group7.travelomania;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Matt on 10/27/15.
 * Found on github by a user named CooCood
 */
public class PathAnimation extends Animation {

    private PathMeasure pathMeasure;
    private float[] pos = new float[2];
    public PathAnimation(Path path){
        pathMeasure = new PathMeasure(path,false);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t){
        pathMeasure.getPosTan(pathMeasure.getLength() * interpolatedTime, pos, null);
        t.getMatrix().setTranslate(pos[0],pos[1]);
    }

}
