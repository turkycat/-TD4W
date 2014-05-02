package turkycat.productions.td4w;

import java.io.IOException;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.widget.RemoteViews;

public class TD4WAppWidget4 extends AppWidgetProvider
{
	// a reference to the media player
	private static MediaPlayer mediaPlayer;

	private static final String ACTION_WIDGET_RECEIVER = "turkycat.productions.td4w.PlayTD4W";

	@Override
	public void onUpdate( Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds )
	{
		//final int N = appWidgetIds.length;

		// Perform this loop procedure for each App Widget that belongs to this
		// provider
		//for( int i = 0; i < N; i++ )
		//{
			//int appWidgetId = appWidgetIds[i];

			// Create an Intent to launch ExampleActivity
			Intent intent = new Intent( context, TD4WAppWidget4.class );
			// PendingIntent pendingIntent = PendingIntent.getActivity(context,
			// 0,
			// intent, 0);

			intent.setAction( ACTION_WIDGET_RECEIVER );

			PendingIntent pendingIntent = PendingIntent.getBroadcast( context, 0, intent, 0 );

			// Get the layout for the App Widget and attach an on-click listener
			// to the button
			RemoteViews views = new RemoteViews( context.getPackageName(), R.layout.widget_xml );

			views.setOnClickPendingIntent( R.id.button, pendingIntent );

			// Tell the AppWidgetManager to perform an update on the current app
			// widget
			appWidgetManager.updateAppWidget( appWidgetIds, views );
		//}
	}

	@Override
	public void onReceive( Context context, Intent intent )
	{

		// v1.5 fix that doesn't call onDelete Action

		final String action = intent.getAction();

		if( AppWidgetManager.ACTION_APPWIDGET_DELETED.equals( action ) )
		{

			final int appWidgetId = intent.getExtras().getInt(

			AppWidgetManager.EXTRA_APPWIDGET_ID,

			AppWidgetManager.INVALID_APPWIDGET_ID );

			if( appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID )
			{

				this.onDeleted( context, new int[] { appWidgetId } );
			}
		}
		else
		{

			// check, if our Action was called

			if( intent.getAction().equals( ACTION_WIDGET_RECEIVER ) )
			{

				// check to see if media player is going. Stops it and releases
				// if so.

				if( mediaPlayer != null )
				{
					mediaPlayer.stop();
					mediaPlayer.release();
				}

				// prepares media with out sound file

				AssetFileDescriptor afd = null;
				try
				{
					afd = context.getAssets().openFd( "td4w.mp3" );
					mediaPlayer = new MediaPlayer();
					mediaPlayer.setDataSource( afd.getFileDescriptor() );
					mediaPlayer.prepare();
				}
				catch( IOException e )
				{
					// do nothing
				}

				// tell media players to start the sound.
				mediaPlayer.start();

			}

			super.onReceive( context, intent );
		}

	}
}