package com.carlosbulado.fsp_note.app;

import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;
import java.util.ListIterator;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Camera camera = null;
    private SurfaceHolder mHolder = null;
    private String imageFile;

    public CameraSurfaceView(Context context, String imageFile)
    {
        this(context);
        this.imageFile = imageFile;
    }

    public String getImageFile()
    {
        return this.imageFile;
    }

    @SuppressWarnings("deprecation")
    public CameraSurfaceView(Context context) {
        super(context);
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed
        mHolder = getHolder();
        mHolder.addCallback(this);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        try {
            Camera.Parameters params = camera.getParameters();
            // not all cameras supporting setting arbitrary sizes
            List<Camera.Size> sizes = params.getSupportedPreviewSizes();
            Camera.Size pickedSize = getBestFit(sizes, width, height);
            if (pickedSize != null) {
                params.setPreviewSize(pickedSize.width, pickedSize.height);
                Log.d("DEBUG", "Preview size: (" + pickedSize.width + ","
                        + pickedSize.height + ")");
                // even after setting a supported size, the preview size may
                // still end up just being the surface size (supported or
                // not)
                camera.setParameters(params);
            }
            // set the orientation to standard portrait.
            // Do this only if you know the specific orientation (0,90,180,
            // etc.)
            // Only works on API Level 8+
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (Exception e) {
            Log.e("DEBUG", "Failed to set preview size", e);
        }
    }

    private Camera.Size getBestFit(List<Camera.Size> sizes, int width, int height) {
        Camera.Size bestFit = null;
        ListIterator<Camera.Size> items = sizes.listIterator();
        while (items.hasNext()) {
            Camera.Size item = items.next();
            if (item.width <= width && item.height <= height) {
                if (bestFit != null) {
                    // if our current best fit has a smaller area, then we
                    // want the new one (bigger area == better fit)
                    if (bestFit.width * bestFit.height < item.width
                            * item.height) {
                        bestFit = item;
                    }
                } else {
                    bestFit = item;
                }
            }
        }
        return bestFit;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        this.openCamera();
    }

    public void openCamera()
    {
        releaseCameraAndPreview();
        camera = Camera.open();
        try {
            camera.setPreviewDisplay(mHolder);
        } catch (Exception e) {
            Log.i("DEBUG", "Failed to set camera preview display", e);
        }
    }

    private void releaseCameraAndPreview() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    public boolean capture(Camera.PictureCallback jpegHandler) {
        if (camera != null) {
            camera.takePicture(null, null, jpegHandler);
            return true;
        } else {
            return false;
        }
    }
}