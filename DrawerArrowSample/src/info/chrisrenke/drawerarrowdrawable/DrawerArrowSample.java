package info.chrisrenke.drawerarrowdrawable;

import static android.view.Gravity.START;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import chrisrenke.drawerarrowdrawable.R;

public class DrawerArrowSample extends Activity {

	private DrawerArrowDrawable drawerArrowDrawable;
	private float offset;
	private boolean flipped;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_view);

		final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		final ImageView imageView = (ImageView) findViewById(R.id.drawer_indicator);
		final Resources resources = getResources();

		drawerArrowDrawable = new DrawerArrowDrawable(resources);
		drawerArrowDrawable.setStrokeColor(resources
				.getColor(R.color.light_gray));
		imageView.setImageDrawable(drawerArrowDrawable);

		drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				offset = slideOffset;

				// Sometimes slideOffset ends up so close to but not quite 1 or
				// 0.
				if (slideOffset >= .995) {
					flipped = true;
					drawerArrowDrawable.setFlip(flipped);
				} else if (slideOffset <= .005) {
					flipped = false;
					drawerArrowDrawable.setFlip(flipped);
				}

				drawerArrowDrawable.setParameter(offset);
			}
		});

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (drawer.isDrawerVisible(START)) {
					drawer.closeDrawer(START);
				} else {
					drawer.openDrawer(START);
				}
			}
		});

		final TextView styleButton = (TextView) findViewById(R.id.indicator_style);
		styleButton.setOnClickListener(new View.OnClickListener() {
			boolean rounded = false;

			@Override
			public void onClick(View v) {
				styleButton.setText(rounded //
				? resources.getString(R.string.rounded) //
						: resources.getString(R.string.squared));

				rounded = !rounded;

				drawerArrowDrawable = new DrawerArrowDrawable(resources,
						rounded);
				drawerArrowDrawable.setParameter(offset);
				drawerArrowDrawable.setFlip(flipped);
				drawerArrowDrawable.setStrokeColor(resources
						.getColor(R.color.light_gray));

				imageView.setImageDrawable(drawerArrowDrawable);
			}
		});
	}
}
