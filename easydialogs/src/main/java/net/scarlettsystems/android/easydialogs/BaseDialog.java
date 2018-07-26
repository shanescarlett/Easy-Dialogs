package net.scarlettsystems.android.easydialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.FontRes;
import android.support.annotation.StringRes;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseDialog<T extends BaseDialog<T>>
{
	private Dialog mDialog;
	private Context mContext;
	private boolean mOpenKeyboard = false;
	private Typeface mTypeface;
	private int mTitleSize, mTextSize;
	private int mTextColour = Color.BLACK;
	private CharSequence mTitle;
	private int mBackgroundColour = Color.TRANSPARENT;
	private int mSeparatorColour = Color.BLACK;

	public BaseDialog(Context context)
	{
		mContext = context;
	}

	public abstract Dialog onCreateDialog(Context context);

	/**
	 * Show the dialog with previously set configurations. Results in a no-op if the dialog is
	 * already shown.
	 * <br>
	 * Note: configurations set after the dialog is shown will not be reflected in the current
	 * dialog. The method {@link BaseDialog#dismiss()} must be called and
	 * {@code show()} must be re-invoked for the changes to be reflected.
	 */
	public void show()
	{
		if(getContext() == null){return;}
		if(mDialog != null){return;}

		mDialog = onCreateDialog(getContext());
		mDialog.show();
		configureWindow(mDialog);
	}

	/**
	 * Dismiss the currently open dialog. Results in a no-op if the dialog is not shown.
	 */
	public void dismiss()
	{
		if(mDialog == null){return;}

		mDialog.dismiss();
		mDialog = null;
	}

	@SuppressWarnings("ConstantConditions")
	private void configureWindow(Dialog d)
	{
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(d.getWindow().getAttributes());
		if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			lp.width = WindowManager.LayoutParams.MATCH_PARENT;
			lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		}
		else if(getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
			lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		}

		d.getWindow().setAttributes(lp);
		if(mOpenKeyboard)
		{
			d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		}
	}

	Typeface getTypeface()
	{
		return mTypeface;
	}

	CharSequence getTitle()
	{
		return mTitle;
	}

	int getTitleSize()
	{
		return mTitleSize;
	}

	int getTextSize()
	{
		return mTextSize;
	}

	int getTextColour()
	{
		return mTextColour;
	}

	int getBackgroundColour()
	{
		return mBackgroundColour;
	}

	int getSeparatorColour()
	{
		return mSeparatorColour;
	}

	public Context getContext()
	{
		return mContext;
	}

	/**
	 * Set the background colour for the dialog.
	 *
	 * @param colour Android {@link ColorInt}
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setBackgroundColour(@ColorInt int colour)
	{
		mBackgroundColour = colour;
		return (T)this;
	}

	/**
	 * Set the background colour for the dialog.
	 *
	 * @param resId colour as a colour resource ID
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setBackgroundColourResource(@ColorRes int resId)
	{
		mBackgroundColour = mContext.getResources().getColor(resId);
		return (T)this;
	}

	/**
	 * Set the colour of the separator between the title and the dialog's body.
	 *
	 * @param colour Android {@link ColorInt}
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setSeparatorColour(@ColorInt int colour)
	{
		mSeparatorColour = colour;
		return (T)this;
	}

	/**
	 * Set the colour of the separator between the title and the dialog's body.
	 *
	 * @param resId colour as a colour resource ID
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setSeparatorColourResource(@ColorRes int resId)
	{
		mSeparatorColour = mContext.getResources().getColor(resId);
		return (T)this;
	}

	/**
	 * Set the colour of the text displayed within the dialog.
	 *
	 * @param colour Android {@link ColorInt}
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setTextColour(@ColorInt int colour)
	{
		mTextColour = colour;
		return (T)this;
	}

	/**
	 * Set the colour of the text displayed within the dialog.
	 *
	 * @param resId colour as a colour resource ID
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setTextColourResource(@ColorRes int resId)
	{
		mTextColour = mContext.getResources().getColor(resId);
		return (T)this;
	}

	/**
	 * Set the {@link Typeface} to be used for the text displayed within the dialog.
	 *
	 * @param typeface font as a {@link Typeface}
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setTypeface(Typeface typeface)
	{
		mTypeface = typeface;
		return (T)this;
	}

	/**
	 * Set the font to be used for the text displayed within the dialog.
	 *
	 * @param  resId font as a font resource ID
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setFontResource(@FontRes int resId)
	{
		mTypeface = ResourcesCompat.getFont(mContext, resId);
		return (T)this;
	}

	/**
	 * Set the title to be displayed on the top of the dialog.
	 *
	 * @param title title as a {@link CharSequence}
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setTitle(CharSequence title)
	{
		mTitle = title;
		return (T)this;
	}

	/**
	 * Set the title to be displayed on the top of the dialog.
	 *
	 * @param resId title as a string resource ID
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setTitle(@StringRes int resId)
	{
		mTitle = mContext.getString(resId);
		return (T)this;
	}

	/**
	 * Set the size of the title.
	 *
	 * @param size size in pixels
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setTitlePixelSize(int size)
	{
		mTitleSize = size;
		return (T)this;
	}

	/**
	 * Set the size of the title.
	 *
	 * @param resId size as a dimension resource ID
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setTitleDimension(@DimenRes int resId)
	{
		mTitleSize = mContext.getResources().getDimensionPixelSize(resId);
		return (T)this;
	}

	/**
	 * Set the size of all the text within the dialog, excluding the title. For the title, see
	 * {@link PickerDialog#setTitleDimension}.
	 *
	 * @param size size in pixels
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setTextPixelSize(int size)
	{
		mTextSize = size;
		return (T)this;
	}

	/**
	 * Set the size of all the text within the dialog, excluding the title. For the title, see
	 * {@link PickerDialog#setTitleDimension}.
	 *
	 * @param resId size as a dimension resource ID
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setTextDimension(@DimenRes int resId)
	{
		mTextSize = mContext.getResources().getDimensionPixelSize(resId);
		return (T)this;
	}

	/**
	 * Enable or disable the automatic opening of the soft input keyboard upon opening the dialog.
	 *
	 * @param enabled enabled state
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setOpenKeyboard(boolean enabled)
	{
		mOpenKeyboard = enabled;
		return (T)this;
	}
}
