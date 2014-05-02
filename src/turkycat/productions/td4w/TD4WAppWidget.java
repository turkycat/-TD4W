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

public class TD4WAppWidget extends AppWidgetProvider
{
	// a reference to the media player
	private static MediaPlayer mediaPlayer;

	private static final String ACTION_WIDGET_RECEIVER = "turkycat.productions.td4w.PlayTD4W";

	@Override
	public void onUpdate( Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds )
	{
		//create an intent to launch our own class as the receiver
		Intent intent = new Intent( context, TD4WAppWidget4.class );

		//set the action to a string, in this case we have self-defined a unique string for our unique event
		intent.setAction( ACTION_WIDGET_RECEIVER );

		//get a broadcast event
		PendingIntent pendingIntent = PendingIntent.getBroadcast( context, 0, intent, 0 );

		//retrieve the views
		RemoteViews views = new RemoteViews( context.getPackageName(), R.layout.widget_xml );

		//set the broadcast intent to the button
		views.setOnClickPendingIntent( R.id.button, pendingIntent );

		//tell the AppWidgetManager to perform an update on the current app
		appWidgetManager.updateAppWidget( appWidgetIds, views );
	}

	@Override
	public void onReceive( Context context, Intent intent )
	{
		/** begin code straight from stack overflow **/

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

		/** YAY BACK TO (MOSTLY) OUR CODE! **/
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

				mediaPlayer.start();

			}

			super.onReceive( context, intent );
		}
	}
}