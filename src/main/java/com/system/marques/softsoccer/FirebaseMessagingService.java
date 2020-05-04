package com.system.marques.softsoccer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService
    extends
        com.google.firebase.messaging.FirebaseMessagingService
{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);

        String messageTitle = remoteMessage.getNotification().getTitle();
        String messageBody = remoteMessage.getNotification().getBody();

        String clickAction = remoteMessage.getNotification().getClickAction();
        String dataMessage = remoteMessage.getData().get("message");
        String dataFrom = remoteMessage.getData().get("from_user_id");

        Uri soundUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder( this, getString(R.string.default_notification_channel_id) )
                                                                    .setSmallIcon( R.drawable.ic_notifications_black_24dp )
                                                                    .setContentTitle( messageTitle )
                                                                    .setContentText( messageBody )
                                                                    .setAutoCancel( true )
                                                                    .setSound( soundUri );

        Intent intent = new Intent( clickAction );
        intent.putExtra("message", dataMessage);
        intent.putExtra("from_user_id", dataFrom);

        PendingIntent pendingIntent = PendingIntent.getActivity( this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );

        nBuilder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
        manager.notify( 0, nBuilder.build() );
    }
}