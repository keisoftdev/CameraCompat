/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.cameracompat.compat;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.os.Build;
import com.github.piasy.cameracompat.CameraCompat;

/**
 * Created by Piasy{github.com/Piasy} on 5/24/16.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
class Camera1PreviewCallback implements Camera.PreviewCallback {
    private final CameraFrameCallback mCameraFrameCallback;

    Camera1PreviewCallback(CameraFrameCallback cameraFrameCallback) {
        mCameraFrameCallback = cameraFrameCallback;
    }

    @Override
    public void onPreviewFrame(final byte[] data, final Camera camera) {
        try {
            Camera.Size size = camera.getParameters().getPreviewSize();
            mCameraFrameCallback.onFrameData(data, size.width, size.height, () -> {
                camera.addCallbackBuffer(data);
                // errors will be caught at where this task is executed
            });
        } catch (RuntimeException | OutOfMemoryError e) {
            CameraCompat.onError(CameraCompat.ERR_UNKNOWN);
        }
    }
}
