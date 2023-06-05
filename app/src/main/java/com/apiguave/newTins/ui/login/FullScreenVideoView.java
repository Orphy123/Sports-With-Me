package com.apiguave.newTins.ui.login;



import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.widget.FrameLayout;

public class FullScreenVideoView extends TextureView implements TextureView.SurfaceTextureListener {
    private final MediaPlayer mediaPlayer;

    public FullScreenVideoView(Context context) {
        super(context);
        mediaPlayer = new MediaPlayer();
        setSurfaceTextureListener(this);
    }

    public void setVideoURI(Uri uri) {
        try {
            mediaPlayer.setDataSource(getContext(), uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void start() {
        try {
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnPreparedListener(MediaPlayer.OnPreparedListener listener) {
        mediaPlayer.setOnPreparedListener(listener);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mediaPlayer.setSurface(new Surface(surface));
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        updateTextureViewSize(width, height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mediaPlayer.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    private void updateTextureViewSize(int viewWidth, int viewHeight) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.width = viewWidth;
        layoutParams.height = viewHeight;
        layoutParams.gravity = Gravity.CENTER;
        setLayoutParams(layoutParams);
    }
}
