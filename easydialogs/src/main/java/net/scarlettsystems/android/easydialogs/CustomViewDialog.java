package net.scarlettsystems.android.easydialogs;

import android.content.Context;
import android.view.View;


public class CustomViewDialog extends SimpleDialog<CustomViewDialog>
{
	private View mCustomView;
	private OnCreateContentViewListener mListener;

	public interface OnCreateContentViewListener
	{
		View onCreateContentView(Context context);
	}

	public CustomViewDialog(Context context)
	{
		super(context);
	}

	@Override
	public View onCreateContentView(Context context)
	{
		if(mListener != null)
			return mListener.onCreateContentView(context);
		else
			return mCustomView;
	}

	public CustomViewDialog setCustomViewCreator(OnCreateContentViewListener l)
	{
		mListener = l;
		return this;
	}

	public CustomViewDialog setCustomView(View v)
	{
		mCustomView = v;
		return this;
	}

	public View getCustomView(int index)
	{
		return mCustomView;
	}
}
