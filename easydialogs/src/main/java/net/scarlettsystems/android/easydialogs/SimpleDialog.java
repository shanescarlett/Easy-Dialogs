package net.scarlettsystems.android.easydialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;

@SuppressWarnings("WeakerAccess")
public abstract class SimpleDialog<T extends SimpleDialog<T>> extends BaseDialog<T>
{
	private OnOkListener mOnOk;
	private OnCancelListener mOnCancel;
	private CharSequence mOkText, mCancelText;
	private boolean mCancelEnabled;

	public SimpleDialog(Context context)
	{
		super(context);
	}

	/**
	 * Interface definition for method to be called when the ok button is clicked.
	 */
	@SuppressWarnings("unused")
	public interface OnOkListener
	{
		void onOk();
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
		mOkText = getContext().getString(resId);
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
		mCancelText = getContext().getString(resId);
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T setCancelButtonEnabled(boolean enabled)
	{
		mCancelEnabled = enabled;
		return (T)this;
	}

	/**
	 * Method invoked for the creation of the content view, placed between the title and the Ok
	 * and Cancel buttons of the dialog window.
	 *
	 * @param context the {@link Context} for the dialog
	 * @return the {@link View} of the dialog's content
	 */
	public abstract View onCreateContentView(Context context);


	@Override
	public Dialog onCreateDialog(Context context)
	{
		Dialog dialog = new Dialog(getContext());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_picker);

		configureTitle(dialog);
		configureSeparator(dialog);
		configureOkButton(dialog);
		configureCancelButton(dialog);
		configureContentView(dialog);

		return dialog;
	}

	private void configureTitle(Dialog d)
	{
		AppCompatTextView t = d.findViewById(R.id.title);
		setTextToView(getTitle(), t);
		t.setTypeface(getTypeface());
		t.setTextColor(getTextColour());
		t.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTitleSize());
	}

	private void configureSeparator(Dialog d)
	{
		d.findViewById(R.id.separator).setBackgroundColor(getSeparatorColour());
	}

	private void configureOkButton(Dialog d)
	{
		AppCompatTextView b = d.findViewById(R.id.button_ok);
		b.setText(mOkText);
		b.setTypeface(getTypeface());
		b.setTextColor(getTextColour());
		b.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize());
		b.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SimpleDialog.this.onDialogOk();
			}
		});
	}

	private void configureCancelButton(Dialog d)
	{
		AppCompatTextView b = d.findViewById(R.id.button_cancel);
		if(mCancelEnabled)
		{
			b.setText(mCancelText);
			b.setTypeface(getTypeface());
			b.setTextColor(getTextColour());
			b.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize());
			b.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					SimpleDialog.this.onDialogCancel();
				}
			});
		}
		else
		{
			b.setVisibility(View.GONE);
		}
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
		root.setBackgroundColor(getBackgroundColour());
		ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(0, 0);
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



	@SuppressWarnings("ConstantConditions")
	private void onDialogOk()
	{
		dismiss();
		if(mOnOk == null){return;}
		mOnOk.onOk();
	}

	private void onDialogCancel()
	{
		dismiss();
		if(mOnCancel == null){return;}
		mOnCancel.onCancel();
	}
}
