package net.scarlettsystems.android.easydialogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.io.File;

@SuppressWarnings("unused")
public class ImageDialog extends SimpleDialog<ImageDialog>
{
	private String mPromptText = "";
	private Object mImage;

	public ImageDialog(Context context)
	{
		super(context);
	}

	public ImageDialog setImage(Bitmap image)
	{
		mImage = image;
		return this;
	}

	public ImageDialog setImage(Drawable image)
	{
		mImage = image;
		return this;
	}

	public ImageDialog setImage(String image)
	{
		mImage = image;
		return this;
	}

	public ImageDialog setImage(Uri image)
	{
		mImage = image;
		return this;
	}

	public ImageDialog setImage(File image)
	{
		mImage = image;
		return this;
	}

	public ImageDialog setImage(Integer image)
	{
		mImage = image;
		return this;
	}

	public ImageDialog setText(String text)
	{
		mPromptText = text;
		return this;
	}

	@Override
	public View onCreateContentView(Context context)
	{
		View content = LayoutInflater.from(context).inflate(R.layout.dialog_image, null);
		AppCompatTextView text = content.findViewById(R.id.text);
		text.setText(mPromptText);
		setTextViewAppearance(text, getTextColour(), getTypeface());

		AppCompatImageView image = content.findViewById(R.id.image);

		RequestManager glideRequest = Glide.with(context);
		RequestBuilder<Drawable> builder;
		if(mImage instanceof Bitmap)
			builder = glideRequest.load((Bitmap)mImage);
		else if(mImage instanceof Drawable)
			builder = glideRequest.load((Drawable)mImage);
		else if(mImage instanceof String)
			builder = glideRequest.load((String)mImage);
		else if(mImage instanceof Uri)
			builder = glideRequest.load((Uri)mImage);
		else if(mImage instanceof File)
			builder = glideRequest.load((File)mImage);
		else if(mImage instanceof Integer)
			builder = glideRequest.load((Integer)mImage);
		else
			builder = glideRequest.load((Drawable)null);
		builder.transition(DrawableTransitionOptions.withCrossFade()).into(image);
		return content;
	}

	private static void setTextViewAppearance(AppCompatTextView textView, @ColorInt int color, Typeface typeface)
	{
		textView.setTypeface(typeface);
		textView.setTextColor(color);
	}
}
