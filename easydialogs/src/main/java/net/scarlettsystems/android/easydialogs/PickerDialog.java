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
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

@SuppressWarnings("WeakerAccess")
public abstract class PickerDialog<T extends PickerDialog<T>>
{
	private Context mContext;
	private Dialog mDialog;
	private OnOkListener mOnOk;
	private OnCancelListener mOnCancel;
	private Object mTitle, mOkText, mCancelText;
	private Typeface mTypeface;
	private int mTitleSize, mTextSize;
	private int mBackgroundColour = Color.TRANSPARENT;
	private int mSeparatorColour = Color.BLACK;
	private int mTextColour = Color.BLACK;
	private boolean mOpenKeyboard = false;

	public PickerDialog(Context context)
	{
		mContext = context;
	}

	/**
	 * Interface definition for method to be called when the ok button is clicked.
	 */
	@SuppressWarnings("unused")
	public interface OnOkListener
	{
		void onOk(int value);
	}

	/**
	 * Interface definition for method to be called when the cancel button is clicked.
	 */
	@SuppressWarnings("unused")
	public interface OnCancelListener
	{
		void onCancel();
	}

	/**
	 * Set listener to be called when the ok button is clicked in the dialog.
	 * @param l {@link OnOkListener}
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setOnOkListener(OnOkListener l)
	{
		mOnOk = l;
		return (T)this;
	}

	/**
	 * Set listener to be called when the cancel button is clicked in the dialog.
	 * @param l {@link OnCancelListener}
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setOnCancelListener(OnCancelListener l)
	{
		mOnCancel = l;
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
		mTitle = resId;
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
	 * Set text to be displayed for the Ok button
	 *
	 * @param text Ok button text
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setOkButtonText(CharSequence text)
	{
		mOkText = text;
		return (T)this;
	}

	/**
	 * Set text to be displayed for the Ok button
	 *
	 * @param resId text as a string resource ID
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setOkButtonText(@StringRes int resId)
	{
		mOkText = resId;
		return (T)this;
	}

	/**
	 * Set text to be displayed for the Cancel button
	 *
	 * @param text Cancel button text
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setCancelButtonText(CharSequence text)
	{
		mCancelText = text;
		return (T)this;
	}

	/**
	 * Set text to be displayed for the Cancel button
	 *
	 * @param resId text as a string resource ID
	 * @return self for method chaining
	 */
	@SuppressWarnings("unchecked")
	public T setCancelButtonText(@StringRes int resId)
	{
		mCancelText = resId;
		return (T)this;
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

	Typeface getTypeface()
	{
		return mTypeface;
	}

	int getTextSize()
	{
		return mTextSize;
	}

	int getTextColour()
	{
		return mTextColour;
	}

	public Context getContext()
	{
		return mContext;
	}

	/**
	 * Method invoked for the creation of the content view, placed between the title and the Ok
	 * and Cancel buttons of the dialog window.
	 *
	 * @param context the {@link Context} for the dialog
	 * @return the {@link View} of the dialog's content
	 */
	public abstract View onCreateContentView(Context context);

	/**
	 * Method invoked to retrieve the picked value or index of the content of the dialog.
	 *
	 * @return the picked value
	 */
	public abstract int getPickedValue();

	/**
	 * Show the dialog with previously set configurations. Results in a no-op if the dialog is
	 * already shown.
	 * <br>
	 * Note: configurations set after the dialog is shown will not be reflected in the current
	 * dialog. The method {@link PickerDialog#dismiss()} must be called and
	 * {@code show()} must be re-invoked for the changes to be reflected.
	 */
	public void show()
	{
		if(getContext() == null){return;}
		if(mDialog != null){return;}

		mDialog = new Dialog(getContext());
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialog.setContentView(R.layout.dialog_picker);

		configureTitle(mDialog);
		configureSeparator(mDialog);
		configureOkButton(mDialog);
		configureCancelButton(mDialog);
		configureContentView(mDialog);
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

	private void configureTitle(Dialog d)
	{
		AppCompatTextView t = d.findViewById(R.id.title);
		setTextToView(mTitle, t);
		t.setTypeface(mTypeface);
		t.setTextColor(mTextColour);
		t.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
	}

	private void configureSeparator(Dialog d)
	{
		d.findViewById(R.id.separator).setBackgroundColor(mSeparatorColour);
	}

	private void configureOkButton(Dialog d)
	{
		AppCompatTextView b = d.findViewById(R.id.button_ok);
		setTextToView(mOkText, b);
		b.setTypeface(mTypeface);
		b.setTextColor(mTextColour);
		b.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
		b.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PickerDialog.this.onDialogOk();
			}
		});
	}

	private void configureCancelButton(Dialog d)
	{
		AppCompatTextView b = d.findViewById(R.id.button_cancel);
		setTextToView(mCancelText, b);
		b.setTypeface(mTypeface);
		b.setTextColor(mTextColour);
		b.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
		b.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PickerDialog.this.onDialogCancel();
			}
		});
	}

	private void setTextToView(Object textObject, AppCompatTextView view)
	{
		if(textObject instanceof CharSequence)
		{
			view.setText((CharSequence)textObject);
		}
		if(textObject instanceof Integer)
		{
			view.setText((int)textObject);
		}
	}

	private void configureContentView(Dialog d)
	{
		ConstraintLayout root = d.findViewById(R.id.root);
		root.setBackgroundColor(mBackgroundColour);
		ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(0, 0);
		lp.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.ui_margin);
		lp.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.ui_margin);
		View content = onCreateContentView(getContext());
		content.setLayoutParams(lp);
		content.setId(ViewCompat.generateViewId());
		content.setBackgroundColor(Color.TRANSPARENT);
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

	@SuppressWarnings("ConstantConditions")
	private void onDialogOk()
	{
		mDialog.dismiss();
		if(mOnOk == null){return;}
		mOnOk.onOk(getPickedValue());
	}

	private void onDialogCancel()
	{
		mDialog.dismiss();
		if(mOnCancel == null){return;}
		mOnCancel.onCancel();
	}
}
