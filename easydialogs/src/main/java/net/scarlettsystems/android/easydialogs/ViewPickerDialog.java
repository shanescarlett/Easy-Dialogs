package net.scarlettsystems.android.easydialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")
public class ViewPickerDialog extends PickerDialog<ViewPickerDialog>
{
	private CharSequence[] mEntries;
	private ArrayList<View> mItemViews;
	private int mColumns = 1;
	private int mCheckedIndex = 0;
	private int mSelectedColour = Color.TRANSPARENT;
	private OnCreateViewListener mOnCreateViewListener;

	public ViewPickerDialog(Context context)
	{
		super(context);
	}

	public interface OnCreateViewListener
	{
		View onCreateView(String entry);
	}

	public ViewPickerDialog setColumnCount(int columns)
	{
		mColumns = columns;
		return this;
	}

	public ViewPickerDialog setEntries(CharSequence[] entries)
	{
		mEntries = entries;
		return this;
	}

	public ViewPickerDialog setOnCreateViewListener(OnCreateViewListener l)
	{
		mOnCreateViewListener = l;
		return this;
	}

	@SuppressWarnings("unused")
	public ViewPickerDialog setCheckedIndex(int index)
	{
		mCheckedIndex = index;
		return this;
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateContentView(Context context)
	{
		View content = LayoutInflater.from(context).inflate(R.layout.dialog_view_picker, null);
		configureButtons(content);
		return content;
	}

	public ViewPickerDialog setSelectedColour(@ColorInt int colour)
	{
		mSelectedColour = ColorUtils.setAlphaComponent(colour, Color.alpha(colour) / 4);
		return this;
	}

	@Override
	public int getPickedValue()
	{
		return mCheckedIndex;
	}

	private void configureButtons(View v)
	{
		mItemViews = new ArrayList<>();
		GridLayout gridLayout = v.findViewById(R.id.list);
		gridLayout.setColumnCount(mColumns);
		for (CharSequence item : mEntries)
		{
			View itemView = createListItemView(item.toString());
			gridLayout.addView(itemView);
			mItemViews.add(itemView);
		}
		selectItem(mCheckedIndex);
	}

	private void selectItem(int index)
	{
		if(index < 0){return;}
		if (index != mCheckedIndex)
		{
			mItemViews.get(mCheckedIndex).setBackgroundColor(Color.TRANSPARENT);
		}
		mItemViews.get(index).setBackgroundColor(mSelectedColour);
		mCheckedIndex = index;
	}

	@SuppressWarnings("InflateParams")
	private View createListItemView(String name)
	{
		if(mOnCreateViewListener == null){return null;}
		View view = mOnCreateViewListener.onCreateView(name);
		view.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ViewPickerDialog.this.selectItem(mItemViews.indexOf(v));
			}
		});
		return view;
	}
}
