package net.scarlettsystems.android.easydialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import java.util.ArrayList;

public class RadioPickerDialog extends PickerDialog<RadioPickerDialog>
{
	private CharSequence[] mEntries;
	private ArrayList<View> mItemViews;
	private RadioGroup mRadioGroup;
	private ScrollView mScrollView;
	private int mCheckedIndex = 0;
	private int mButtonColour = Color.BLACK;

	public RadioPickerDialog(Context context)
	{
		super(context);
	}

	public RadioPickerDialog setEntries(CharSequence[] entries)
	{
		mEntries = entries;
		return this;
	}

	public RadioPickerDialog setCheckedIndex(int index)
	{
		mCheckedIndex = index;
		return this;
	}

	public RadioPickerDialog setButtonColour(@ColorInt int colour)
	{
		mButtonColour = colour;
		return this;
	}

	public RadioPickerDialog setButtonColourResource(@ColorRes int resId)
	{
		mButtonColour = getContext().getResources().getColor(resId);
		return this;
	}

	private void scrollTo(int index)
	{
		int childY = (int)mRadioGroup.getChildAt(index).getY();
		mScrollView.scrollTo(0, childY);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateContentView(Context context)
	{
		View content = LayoutInflater.from(context).inflate(R.layout.dialog_radio_picker, null);
		configureButtons(content);
		this.setOnDialogShownListener(new OnDialogShownListener()
		{
			@Override
			public void onDialogShown()
			{
				scrollTo(getPickedValue());
			}
		});
		return content;
	}

	@Override
	public int getPickedValue()
	{
		return mCheckedIndex;
	}

	private void configureButtons(View v)
	{
		mItemViews = new ArrayList<>();
		mScrollView = v.findViewById(R.id.scroll);
		mRadioGroup = v.findViewById(R.id.radio_group);
		for (CharSequence item : mEntries)
		{
			View itemView = createListItemView(item.toString());
			mRadioGroup.addView(itemView);
			mItemViews.add(itemView);
		}
		mRadioGroup.check(mItemViews.get(mCheckedIndex).getId());
	}

	private View createListItemView(String name)
	{
		AppCompatRadioButton button = new AppCompatRadioButton(getContext());
		button.setTextColor(getTextColour());
		button.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize());
		button.setTypeface(getTypeface());
		button.setText(name);
		CompoundButtonCompat.setButtonTintMode(button, PorterDuff.Mode.SRC_IN);
		CompoundButtonCompat.setButtonTintList(button, ColorStateList.valueOf(mButtonColour));
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mCheckedIndex = mItemViews.indexOf(v);
			}
		});
		button.setId(ViewCompat.generateViewId());
		return button;
	}
}
