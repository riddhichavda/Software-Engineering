package group7.travelomania;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.HashMap;


/**
 * Created by Matt on 10/28/15.
 *
 * More efficient bitmap loading.  Put less strain on memory.
 */
public class BitmapUtility {

    public static volatile Bitmap avatar_AF, avatar_AS, avatar_EU, avatar_OC, avatar_NA, avatar_SA, avatar_basic;
    public static volatile Bitmap map, mapKey;
    public static volatile Bitmap planePaths;
    public static volatile HashMap<Continents, float[]> continentPositions;


    public static void initialize(Context context){

        Resources resources = context.getResources();

        if(BitmapUtility.map == null) {
            //Get size of screen.
            DisplayMetrics size = resources.getDisplayMetrics();
            map = BitmapUtility.decodeSampledBitmapFromResource(resources,
                    R.drawable.map_2,
                    size.widthPixels,
                    (int) Math.round(size.widthPixels * 0.61087511d));
            Log.v("Load", "Bitmap Map Load");
        }

        if(BitmapUtility.mapKey == null){
            mapKey = BitmapUtility.decodeSampledBitmapFromResource(resources,
                    R.drawable.map_colors,
                    256,
                    (int) Math.round(256 * 0.61087511d));
            Log.v("Load", "Bitmap mapKey Load");
        }

        if(BitmapUtility.continentPositions == null) {
            continentPositions = new HashMap<>(7);
            continentPositions.put(Continents.Africa, new float[]{0.56f, 0.46f});
            continentPositions.put(Continents.Oceania, new float[]{0.93f, 0.64f});
            continentPositions.put(Continents.Asia, new float[]{0.77f, 0.18f});
            continentPositions.put(Continents.Antarctica, new float[]{0.73f, 0.96f});
            continentPositions.put(Continents.Europe, new float[]{0.57f, 0.14f});
            continentPositions.put(Continents.NorthAmerica, new float[]{0.13f, 0.30f});
            continentPositions.put(Continents.SouthAmerica, new float[]{0.29f, 0.57f});
        }

        if(BitmapUtility.avatar_AF == null){
            BitmapUtility.avatar_AF = BitmapUtility.decodeSampledBitmapFromResource(resources,
                    R.drawable.avatar_africa,
                    64,
                    64);
            Log.v("Load", "avatar_africa");
        }

        if(BitmapUtility.avatar_AS == null){
            BitmapUtility.avatar_AS = BitmapUtility.decodeSampledBitmapFromResource(resources,
                    R.drawable.avatar_asia,
                    64,
                    64);
            Log.v("Load", "avatar_asia");
        }

        if(BitmapUtility.avatar_EU == null){
            BitmapUtility.avatar_EU = BitmapUtility.decodeSampledBitmapFromResource(resources,
                    R.drawable.avatar_europe,
                    64,
                    64);
            Log.v("Load", "avatar_europe");
        }

        if(BitmapUtility.avatar_OC == null){
            BitmapUtility.avatar_OC = BitmapUtility.decodeSampledBitmapFromResource(resources,
                    R.drawable.avatar_ocieana,
                    64,
                    64);
            Log.v("Load", "avatar_ocieana");
        }

        if(BitmapUtility.avatar_NA == null){
            BitmapUtility.avatar_NA = BitmapUtility.decodeSampledBitmapFromResource(resources,
                    R.drawable.avatar_na,
                    64,
                    64);
            Log.v("Load", "avatar_na");
        }

        if(BitmapUtility.avatar_SA == null){
            BitmapUtility.avatar_SA = BitmapUtility.decodeSampledBitmapFromResource(resources,
                    R.drawable.avatar_sa,
                    64,
                    64);
            Log.v("Load", "avatar_sa");
        }

    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }



}
