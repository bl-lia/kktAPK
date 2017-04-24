package com.bl_lia.kirakiratter.presentation.transform

import android.graphics.*
import android.os.Build
import android.support.annotation.ColorRes
import com.squareup.picasso.Transformation

class AvatarTransformation(@ColorRes val borderColor: Int) : Transformation {

    override fun key(): String = "avatar"

    override fun transform(source: Bitmap?): Bitmap? {
        if (source == null) return source
        if (Build.VERSION.SDK_INT < 21) return source

        val size = Math.min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val squareBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squareBitmap != source) {
            source.recycle()
        }

        val config = source.config ?: Bitmap.Config.ARGB_8888
        val bitmap = Bitmap.createBitmap(size, size, config)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(squareBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true

        val borader: Int = 5
        val r: Float = 10F

        val paintBg = Paint().apply {
            color = borderColor
            isAntiAlias = true
        }

        canvas.drawRoundRect(x.toFloat(), y.toFloat(), size.toFloat(), size.toFloat(), r, r, paintBg)
        canvas.drawRoundRect((x + borader).toFloat(), (y + borader).toFloat(), (size - borader).toFloat(), (size - borader).toFloat(), r, r, paint)

        squareBitmap.recycle()
        return bitmap
    }
}