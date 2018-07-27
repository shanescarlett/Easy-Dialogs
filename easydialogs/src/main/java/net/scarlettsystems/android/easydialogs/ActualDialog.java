package net.scarlettsystems.android.easydialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class ActualDialog extends DialogFragment
{
	private OnCreateDialogListener mOnCreateDialogListener;
	private OnPauseListener mOnPauseListener;
	private OnDestroyListener mOnDestroyListener;

	public interface OnCreateDialogListener
	{
		Dialog onCreateDialog();
	}

	public interface OnPauseListener
	{
		void onPause();
	}

	public interface OnDestroyListener
	{
		void onDestroy();
	}

	public void setOnCreateDialogListener(OnCreateDialogListener l)
	{
		mOnCreateDialogListener = l;
	}

	public void setOnPauseListener(OnPauseListener l)
	{
		mOnPauseListener = l;
	}

	public void setOnDestroyListener(OnDestroyListener l)
	{
		mOnDestroyListener = l;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		return mOnCreateDialogListener.onCreateDialog();
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onPause()
	{
		dismiss();
		super.onPause();
		if(mOnPauseListener == null){return;}
		mOnPauseListener.onPause();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if(mOnDestroyListener == null){return;}
		mOnDestroyListener.onDestroy();
	}
}
