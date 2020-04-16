package net.scarlettsystems.android.easydialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
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

	@Override
	public Dialog onCreateDialog(Context context)
	{
		Dialog dialog = super.onCreateDialog(context);
		FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		ConstraintLayout.LayoutParams lp2 = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 1);
		dialog.findViewById(R.id.root).setLayoutParams(lp1);
		dialog.findViewById(R.id.area).setLayoutParams(lp2);
		ConstraintSet cs = new ConstraintSet();
		cs.clone((ConstraintLayout)dialog.findViewById(R.id.root));
		cs.setDimensionRatio(R.id.area,null);
		cs.applyTo((ConstraintLayout)dialog.findViewById(R.id.root));
		return dialog;
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

	@Override
	public void configureContentView(Dialog d)
	{
		ConstraintLayout root = d.findViewById(R.id.root);
		root.setBackgroundColor(getBackgroundColour());
		ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = getContext().getResources().getDimensionPixelSize(R.dimen.ui_margin);
		lp.rightMargin = getContext().getResources().getDimensionPixelSize(R.dimen.ui_margin);
		View content = onCreateContentView(getContext());
		content.setLayoutParams(lp);
		if(content.getId() == View.NO_ID)
		{
			content.setId(ViewCompat.generateViewId());
		}
		//content.setBackgroundColor(Color.TRANSPARENT);
		root.addView(content);

		ConstraintSet cs = new ConstraintSet();
		cs.clone(root);
		cs.connect(content.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
		cs.connect(content.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
		cs.connect(content.getId(), ConstraintSet.TOP, R.id.separator, ConstraintSet.BOTTOM);
		cs.connect(content.getId(), ConstraintSet.BOTTOM, R.id.button_ok, ConstraintSet.TOP);

		cs.connect(R.id.separator, ConstraintSet.BOTTOM, content.getId(), ConstraintSet.TOP);
		cs.connect(R.id.button_ok, ConstraintSet.TOP, content.getId(), ConstraintSet.BOTTOM);
		cs.applyTo(root);
	}

	private static void setTextViewAppearance(AppCompatTextView textView, @ColorInt int color, Typeface typeface)
	{
		textView.setTypeface(typeface);
		textView.setTextColor(color);
	}
}