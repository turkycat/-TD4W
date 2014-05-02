package turkycat.productions.td4w;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity
{
	private MediaPlayer player;
	private Animation push;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		
		push = AnimationUtils.loadAnimation( this, R.anim.push );
		
		AssetFileDescriptor afd = null;
		try
		{
			afd = getAssets().openFd( "td4w.mp3" );
			player = new MediaPlayer();
			player.setDataSource( afd.getFileDescriptor() );
		}
		catch( IOException e )
		{
			// do nothing
		}
	}
	
	
	/**
	 * callback method invoked from the framework when the button is pressed.
	 * 	this is defined in the XML, and is handled by the XML inflator
	 */
	public synchronized void PlayAudio( View view )
	{	
		ImageView button = (ImageView) view.findViewById( R.id.button );
		button.startAnimation( push );
		
		if( player.isPlaying() )
		{
			player.stop();
		}

		try
		{
			player.prepare();
			player.seekTo( 0 );
		}
		catch( IllegalStateException e )
		{
			// nothing to do
			e.printStackTrace();
		}
		catch( IOException e )
		{
			//nothing to do
			e.printStackTrace();
		}

		player.start();
	}

}
