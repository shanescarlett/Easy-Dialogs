package net.scarlettsystems.android.easydialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.StringRes;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;

public class NumberPickerDialog extends PickerDialog<NumberPickerDialog>
{
	private Object mUnits;
	private NumberPicker mNumberPicker;
	private int mMin = 0;
	private int mMax = 10;
	private int mValue = mMin;
	private boolean mEditEnabled = true;
	private String[] mDisplayValues;

	public NumberPickerDialog(Context context)
	{
		super(context);
	}

	@SuppressWarnings("unused")
	public NumberPickerDialog setMin(int min)
	{
		mMin = min;
		return this;
	}

	@SuppressWarnings("unused")
	public NumberPickerDialog setMax(int max)
	{
		mMax = max;
		return this;
	}

	@SuppressWarnings("unused")
	public NumberPickerDialog setValue(int value)
	{
		mValue = value;
		return this;
	}

	@SuppressWarnings("unused")
	public NumberPickerDialog setUnits(CharSequence unit)
	{
		mUnits = unit;
		return this;
	}

	@SuppressWarnings("unused")
	public NumberPickerDialog setUnits(@StringRes Integer unitResId)
	{
		mUnits = unitResId;
		return this;
	}

	public NumberPickerDialog setDisplayedValues(String[] values)
	{
		mDisplayValues = values;
		return this;
	}

	@SuppressWarnings("unused")
	public NumberPickerDialog setEditEnabled(boolean enabled)
	{
		mEditEnabled = enabled;
		return this;
	}

	@SuppressWarnings("unused")
	public NumberPickerDialog setOpenKeyboard(boolean enabled)
	{
		super.setOpenKeyboard(enabled);
		return this;
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateContentView(Context context)
	{
		View content = LayoutInflater.from(context).inflate(R.layout.dialog_number_picker, null);
		configureUnits(content);
		configureNumberPicker(content);
		return content;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public int getPickedValue()
	{
		if(mEditEnabled)
		{
			int writtenValue;
			try
			{
				writtenValue = Integer.parseInt(getEditText(mNumberPicker).getText().toString());
			}
			catch (NumberFormatException e)
			{
				writtenValue = mValue;
			}
			mNumberPicker.clearFocus();
			return writtenValue;
		}
		else
		{
			return mNumberPicker.getValue();
		}
	}

	private void configureUnits(View v)
	{
		if(mUnits instanceof CharSequence)
		{
			((TextView)v.findViewById(R.id.units)).setText((CharSequence)mUnits);
		}
		if(mUnits instanceof Integer)
		{
			((TextView)v.findViewById(R.id.units)).setText((int)mUnits);
		}
	}

	private void configureNumberPicker(View v)
	{
		mNumberPicker = v.findViewById(R.id.picker);
		mNumberPicker.setMaxValue(mMax);
		mNumberPicker.setMinValue(mMin);
		mNumberPicker.setWrapSelectorWheel(false);
		mNumberPicker.setValue(mValue);
		if(mEditEnabled)
		{
			mNumberPicker.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		}
		else
		{
			mNumberPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
		}
		if(mDisplayValues != null)
		{
			mNumberPicker.setDisplayedValues(mDisplayValues);
		}
		setNumberPickerTextAppearance(mNumberPicker, getTextColour(), getTypeface(), getTextSize());
	}

	private static void setNumberPickerTextAppearance(NumberPicker numberPicker, int color, Typeface typeface, int size)
	{
		final int count = numberPicker.getChildCount();
		for (int i = 0; i < count; i++)
		{
			View child = numberPicker.getChildAt(i);
			if(child instanceof EditText)
			{
				try
				{
					Field selectorWheelPaintField = numberPicker.getClass()
							.getDeclaredField("mSelectorWheelPaint");
					selectorWheelPaintField.setAccessible(true);
					Paint paint = (Paint) selectorWheelPaintField.get(numberPicker);
					paint.setColor(color);
					paint.setTypeface(typeface);
					//paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, size, numberPicker.getResources().getDisplayMetrics()));
					((EditText) child).setTextColor(color);
					((EditText) child).setTypeface(typeface);
					//((EditText) child).setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
					numberPicker.invalidate();
					((View)numberPicker.getParent()).invalidate();
					return;
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private static EditText getEditText(NumberPicker picker)
	{
		int childCount = picker.getChildCount();
		for (int i = 0; i < childCount; i++)
		{
			View childView = picker.getChildAt(i);

			if (childView instanceof EditText)
			{
				return (EditText)childView;
			}
		}
		return null;
	}
}