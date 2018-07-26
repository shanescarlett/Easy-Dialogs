package net.scarlettsystems.android.easydialogsdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import net.scarlettsystems.android.easydialogs.NumberPickerDialog;
import net.scarlettsystems.android.easydialogs.PickerDialog;
import net.scarlettsystems.android.easydialogs.RadioPickerDialog;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		configureNumberPickerDialog();
		configureRadioPickerDialog();
	}

	private void configureNumberPickerDialog()
	{
		AppCompatButton b = findViewById(R.id.number);
		b.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new NumberPickerDialog(MainActivity.this)
						.setEditEnabled(false)
						.setOpenKeyboard(false)
						.setMin(1)
						.setMax(100)
						.setValue(1)
						.setTitle("Pick a Number")
						.setUnits("units")
						.setBackgroundColour(Color.WHITE)
						.setTextColour(Color.BLACK)
						.setSeparatorColour(Color.BLACK)
						.setTextPixelSize(64)
						.setTitlePixelSize(108)
						.setOkButtonText("Ok")
						.setCancelButtonText("Cancel")
						.setOnOkListener(new PickerDialog.OnOkListener()
						{
							@Override
							public void onOk(int value)
							{

							}
						})
						.show();
			}
		});

	}

	private void configureRadioPickerDialog()
	{
		AppCompatButton b = findViewById(R.id.radio);
		final String[] entries = new String[12];
		for(int c = 0; c < 12; c++)
		{
			entries[c] = "Item " + Integer.toString(c);
		}
		b.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new RadioPickerDialog(MainActivity.this)
						.setEntries(entries)
						.setTitle("Pick an Item")
						.setCheckedIndex(11)
						.setBackgroundColour(Color.WHITE)
						.setButtonColourResource(R.color.colorPrimary)
						.setTextColour(Color.BLACK)
						.setSeparatorColour(Color.BLACK)
						.setOkButtonText("Ok")
						.setTextPixelSize(64)
						.setTitlePixelSize(108)
						.setCancelButtonText("Cancel")
						.setOnOkListener(new PickerDialog.OnOkListener()
						{
							@Override
							public void onOk(int value)
							{

							}
						})
						.show();
			}
		});
	}
}
