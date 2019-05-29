package net.scarlettsystems.android.easydialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;

public class ConfirmDialog extends SimpleDialog<ConfirmDialog>
{
	private String mPromptText;


	@SuppressWarnings("unused")
	public ConfirmDialog(Context context)
	{
		super(context);
	}

	@SuppressWarnings("unused")
	public ConfirmDialog setText(String text)
	{
		mPromptText = text;
		return this;
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateContentView(Context context)
	{
		View content = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null);
		AppCompatTextView text = content.findViewById(R.id.text);
		text.setText(mPromptText);
		setTextViewAppearance(text, getTextColour(), getTypeface());
		return content;
	}

	private static void setTextViewAppearance(AppCompatTextView textView, @ColorInt int color, Typeface typeface)
	{
		textView.setTypeface(typeface);
		textView.setTextColor(color);
	}
}