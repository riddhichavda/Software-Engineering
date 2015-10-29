package group7.travelomania;

import android.animation.TypeEvaluator;
import android.graphics.Path;
import android.graphics.PathMeasure;

/**
 * Created by Matt on 10/28/15.
 */
public class PathEvaluator implements TypeEvaluator<float[]> {

    private PathMeasure pathMeasure;
    //private float[] pos = new float[2];
    //float curDistance;

    public PathEvaluator(Path path){
        pathMeasure = new PathMeasure(path, false);
    }
    @Override
    public float[] evaluate(float fraction, float[] startValue, float[] endValue){
        return evaluatePosition(fraction, 0, 1);
    }

    public float[] evaluatePosition(float fraction, float startValue, float endValue){
        float curDistance = fraction*pathMeasure.getLength();
        float[] pos = new float[2];
        pathMeasure.getPosTan(curDistance, pos, null);
        return pos;
    }
}
