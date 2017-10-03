package com.info121.ioperation.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

public class BitmapHelper
{
	
	public static final int limitSize = 100000;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	public static int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else {
            return data.getByteCount();
        }
    }
	
	
    //decodes image and scales it to reduce memory consumption
    public static Bitmap decodeFile(File bitmapFile, boolean quickAndDirty)
    {
    	int requiredWidth = 1280;
    	int requiredHeight = 960;
    	
    	while(true)
    	{
    	
    		Log.v("scaled photo started","GO");
    	
        try
        {
            //Decode image size
            BitmapFactory.Options bitmapSizeOptions = new BitmapFactory.Options();
            bitmapSizeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(bitmapFile), null, bitmapSizeOptions);

            // load image using inSampleSize adapted to required image size
            BitmapFactory.Options bitmapDecodeOptions = new BitmapFactory.Options();
            bitmapDecodeOptions.inTempStorage = new byte[16 * 1024];
            bitmapDecodeOptions.inSampleSize = computeInSampleSize(bitmapSizeOptions, requiredWidth, requiredHeight, false);
            bitmapDecodeOptions.inPurgeable = true;
            bitmapDecodeOptions.inDither = !quickAndDirty;
            bitmapDecodeOptions.inPreferredConfig = quickAndDirty ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888;

            Bitmap decodedBitmap = BitmapFactory.decodeStream(new FileInputStream(bitmapFile), null, bitmapDecodeOptions);

            Log.v("decodedBitmap",String.valueOf(sizeOf(decodedBitmap)));
            
        	if(sizeOf(decodedBitmap)<limitSize)return decodedBitmap;
        	
        	requiredWidth = Math.round(requiredWidth*2/3);
        	requiredHeight = Math.round(requiredWidth*2/3);
            
            
            // scale bitmap to mathc required size (and keep aspect ratio)

            float srcWidth = (float) bitmapDecodeOptions.outWidth;
            float srcHeight = (float) bitmapDecodeOptions.outHeight;

            float dstWidth = (float) requiredWidth;
            float dstHeight = (float) requiredHeight;

            float srcAspectRatio = srcWidth / srcHeight;
            float dstAspectRatio = dstWidth / dstHeight;

            // recycleDecodedBitmap is used to know if we must recycle intermediary 'decodedBitmap'
            // (DO NOT recycle it right away: wait for end of bitmap manipulation process to avoid
            // java.lang.RuntimeException: Canvas: trying to use a recycled bitmap android.graphics.Bitmap@416ee7d8
            // I do not excatly understand why, but this way it's OK

            boolean recycleDecodedBitmap = false;

            Bitmap scaledBitmap = decodedBitmap;
            if (srcAspectRatio < dstAspectRatio)
            {
                scaledBitmap = getScaledBitmap(decodedBitmap, (int) dstWidth, (int) (srcHeight * (dstWidth / srcWidth)));
                // will recycle recycleDecodedBitmap
                recycleDecodedBitmap = true;
            }
            else if (srcAspectRatio > dstAspectRatio)
            {
                scaledBitmap = getScaledBitmap(decodedBitmap, (int) (srcWidth * (dstHeight / srcHeight)), (int) dstHeight);
                recycleDecodedBitmap = true;
            }

            // crop image to match required image size

            int scaledBitmapWidth = scaledBitmap.getWidth();
            int scaledBitmapHeight = scaledBitmap.getHeight();

            Bitmap croppedBitmap = scaledBitmap;

            if (scaledBitmapWidth > requiredWidth)
            {
                int xOffset = (scaledBitmapWidth - requiredWidth) / 2;
                croppedBitmap = Bitmap.createBitmap(scaledBitmap, xOffset, 0, requiredWidth, requiredHeight);
                scaledBitmap.recycle();
            }
            else if (scaledBitmapHeight > requiredHeight)
            {
                int yOffset = (scaledBitmapHeight - requiredHeight) / 2;
                croppedBitmap = Bitmap.createBitmap(scaledBitmap, 0, yOffset, requiredWidth, requiredHeight);
                scaledBitmap.recycle();
            }

            if (recycleDecodedBitmap)
            {
                decodedBitmap.recycle();
            }
            decodedBitmap = null;

            scaledBitmap = null;
            
            if(sizeOf(croppedBitmap)<limitSize){
            	Log.v("scaled photo size",String.valueOf(sizeOf(croppedBitmap)));
            return croppedBitmap;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
        
    	}
    	
        //return null;
    }
    
    
    //decodes image and scales it to reduce memory consumption
    public static Bitmap decodeInputSream(InputStream bitmapFile, boolean quickAndDirty)
    {
    	int requiredWidth = 1280;
    	int requiredHeight = 960;
    	
    	while(true)
    	{
    	
    		Log.v("scaled photo started","GO");
    	
        try
        {
            //Decode image size
            BitmapFactory.Options bitmapSizeOptions = new BitmapFactory.Options();
            bitmapSizeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(bitmapFile, null, bitmapSizeOptions);

            // load image using inSampleSize adapted to required image size
            BitmapFactory.Options bitmapDecodeOptions = new BitmapFactory.Options();
            bitmapDecodeOptions.inTempStorage = new byte[16 * 1024];
            bitmapDecodeOptions.inSampleSize = computeInSampleSize(bitmapSizeOptions, requiredWidth, requiredHeight, false);
            bitmapDecodeOptions.inPurgeable = true;
            bitmapDecodeOptions.inDither = !quickAndDirty;
            bitmapDecodeOptions.inPreferredConfig = quickAndDirty ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888;

            Bitmap decodedBitmap = BitmapFactory.decodeStream(bitmapFile, null, bitmapDecodeOptions);

            Log.v("decodedBitmap",String.valueOf(sizeOf(decodedBitmap)));
            
        	if(sizeOf(decodedBitmap)<limitSize)return decodedBitmap;
        	
        	requiredWidth = Math.round(requiredWidth*2/3);
        	requiredHeight = Math.round(requiredWidth*2/3);
            
            
            // scale bitmap to mathc required size (and keep aspect ratio)

            float srcWidth = (float) bitmapDecodeOptions.outWidth;
            float srcHeight = (float) bitmapDecodeOptions.outHeight;

            float dstWidth = (float) requiredWidth;
            float dstHeight = (float) requiredHeight;

            float srcAspectRatio = srcWidth / srcHeight;
            float dstAspectRatio = dstWidth / dstHeight;

            // recycleDecodedBitmap is used to know if we must recycle intermediary 'decodedBitmap'
            // (DO NOT recycle it right away: wait for end of bitmap manipulation process to avoid
            // java.lang.RuntimeException: Canvas: trying to use a recycled bitmap android.graphics.Bitmap@416ee7d8
            // I do not excatly understand why, but this way it's OK

            boolean recycleDecodedBitmap = false;

            Bitmap scaledBitmap = decodedBitmap;
            if (srcAspectRatio < dstAspectRatio)
            {
                scaledBitmap = getScaledBitmap(decodedBitmap, (int) dstWidth, (int) (srcHeight * (dstWidth / srcWidth)));
                // will recycle recycleDecodedBitmap
                recycleDecodedBitmap = true;
            }
            else if (srcAspectRatio > dstAspectRatio)
            {
                scaledBitmap = getScaledBitmap(decodedBitmap, (int) (srcWidth * (dstHeight / srcHeight)), (int) dstHeight);
                recycleDecodedBitmap = true;
            }

            // crop image to match required image size

            int scaledBitmapWidth = scaledBitmap.getWidth();
            int scaledBitmapHeight = scaledBitmap.getHeight();

            Bitmap croppedBitmap = scaledBitmap;

            if (scaledBitmapWidth > requiredWidth)
            {
                int xOffset = (scaledBitmapWidth - requiredWidth) / 2;
                croppedBitmap = Bitmap.createBitmap(scaledBitmap, xOffset, 0, requiredWidth, requiredHeight);
                scaledBitmap.recycle();
            }
            else if (scaledBitmapHeight > requiredHeight)
            {
                int yOffset = (scaledBitmapHeight - requiredHeight) / 2;
                croppedBitmap = Bitmap.createBitmap(scaledBitmap, 0, yOffset, requiredWidth, requiredHeight);
                scaledBitmap.recycle();
            }

            if (recycleDecodedBitmap)
            {
                decodedBitmap.recycle();
            }
            decodedBitmap = null;

            scaledBitmap = null;
            
            if(sizeOf(croppedBitmap)<limitSize){
            	Log.v("scaled photo size",String.valueOf(sizeOf(croppedBitmap)));
            return croppedBitmap;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
        
    	}
    	
        //return null;
    }
    
  //decodes image and scales it to reduce memory consumption
    public static Bitmap decodeBitmap(Bitmap bitmapFile, boolean quickAndDirty)
    {
    	int requiredWidth = 1280;
    	int requiredHeight = 960;
    	
//    	ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
//    	bitmapFile.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, bos); 
//    	byte[] bitmapdata = bos.toByteArray();
//    	InputStream bs = new ByteArrayInputStream(bitmapdata); 
    	
    	while(true)
    	{
    	
    		Log.v("scaled photo started","GO");
    	
        try
        {
//            //Decode image size
//            BitmapFactory.Options bitmapSizeOptions = new BitmapFactory.Options();
//            bitmapSizeOptions.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(bs, null, bitmapSizeOptions);
//
//            // load image using inSampleSize adapted to required image size
//            BitmapFactory.Options bitmapDecodeOptions = new BitmapFactory.Options();
//            bitmapDecodeOptions.inTempStorage = new byte[16 * 1024];
//            bitmapDecodeOptions.inSampleSize = computeInSampleSize(bitmapSizeOptions, requiredWidth, requiredHeight, false);
//            bitmapDecodeOptions.inPurgeable = true;
//            bitmapDecodeOptions.inDither = !quickAndDirty;
//            bitmapDecodeOptions.inPreferredConfig = quickAndDirty ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888;
//
//            Bitmap decodedBitmap = BitmapFactory.decodeStream(bs, null, bitmapDecodeOptions);

        	Bitmap decodedBitmap = bitmapFile;
        	
        	
            Log.v("decodedBitmap",String.valueOf(sizeOf(decodedBitmap)));
            
        	if(sizeOf(decodedBitmap)<limitSize)return decodedBitmap;
        	
        	requiredWidth = Math.round(requiredWidth*2/3);
        	requiredHeight = Math.round(requiredWidth*2/3);
            
            
            // scale bitmap to mathc required size (and keep aspect ratio)

            float srcWidth = (float) decodedBitmap.getWidth();
            float srcHeight = (float) decodedBitmap.getHeight();

            float dstWidth = (float) requiredWidth;
            float dstHeight = (float) requiredHeight;

            float srcAspectRatio = srcWidth / srcHeight;
            float dstAspectRatio = dstWidth / dstHeight;

            // recycleDecodedBitmap is used to know if we must recycle intermediary 'decodedBitmap'
            // (DO NOT recycle it right away: wait for end of bitmap manipulation process to avoid
            // java.lang.RuntimeException: Canvas: trying to use a recycled bitmap android.graphics.Bitmap@416ee7d8
            // I do not excatly understand why, but this way it's OK

            boolean recycleDecodedBitmap = false;

            Bitmap scaledBitmap = decodedBitmap;
            if (srcAspectRatio < dstAspectRatio)
            {
                scaledBitmap = getScaledBitmap(decodedBitmap, (int) dstWidth, (int) (srcHeight * (dstWidth / srcWidth)));
                // will recycle recycleDecodedBitmap
                recycleDecodedBitmap = true;
            }
            else if (srcAspectRatio > dstAspectRatio)
            {
                scaledBitmap = getScaledBitmap(decodedBitmap, (int) (srcWidth * (dstHeight / srcHeight)), (int) dstHeight);
                recycleDecodedBitmap = true;
            }

            // crop image to match required image size

            int scaledBitmapWidth = scaledBitmap.getWidth();
            int scaledBitmapHeight = scaledBitmap.getHeight();

            Bitmap croppedBitmap = scaledBitmap;

            if (scaledBitmapWidth > requiredWidth)
            {
                int xOffset = (scaledBitmapWidth - requiredWidth) / 2;
                croppedBitmap = Bitmap.createBitmap(scaledBitmap, xOffset, 0, requiredWidth, requiredHeight);
                scaledBitmap.recycle();
            }
            else if (scaledBitmapHeight > requiredHeight)
            {
                int yOffset = (scaledBitmapHeight - requiredHeight) / 2;
                croppedBitmap = Bitmap.createBitmap(scaledBitmap, 0, yOffset, requiredWidth, requiredHeight);
                scaledBitmap.recycle();
            }

            if (recycleDecodedBitmap)
            {
                //decodedBitmap.recycle();
            }
            decodedBitmap = null;

            scaledBitmap = null;
            
            if(sizeOf(croppedBitmap)<limitSize){
            	Log.v("scaled photo size",String.valueOf(sizeOf(croppedBitmap)));
            return croppedBitmap;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
        
    	}
    	
        //return null;
    }

    /**
     * compute powerOf2 or exact scale to be used as {@link BitmapFactory.Options#inSampleSize} value (for subSampling)
     * 
     * @param requiredWidth
     * @param requiredHeight
     * @param powerOf2
     *            weither we want a power of 2 sclae or not
     * @return
     */
    public static int computeInSampleSize(BitmapFactory.Options options, int dstWidth, int dstHeight, boolean powerOf2)
    {
        int inSampleSize = 1;

        // Raw height and width of image
        final int srcHeight = options.outHeight;
        final int srcWidth = options.outWidth;

        if (powerOf2)
        {
            //Find the correct scale value. It should be the power of 2.

            int tmpWidth = srcWidth, tmpHeight = srcHeight;
            while (true)
            {
                if (tmpWidth / 2 < dstWidth || tmpHeight / 2 < dstHeight)
                    break;
                tmpWidth /= 2;
                tmpHeight /= 2;
                inSampleSize *= 2;
            }
        }
        else
        {
            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) srcHeight / (float) dstHeight);
            final int widthRatio = Math.round((float) srcWidth / (float) dstWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static Bitmap drawableToBitmap(Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap getScaledBitmap(Bitmap bitmap, int newWidth, int newHeight)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // RECREATE THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

}