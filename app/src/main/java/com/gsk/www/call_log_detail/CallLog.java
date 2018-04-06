package com.gsk.www.call_log_detail;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by DELL on 07-04-2018.
 */

public class CallLog extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "CallLog";
    private static final int URL_LOADER = 1;

    private TextView callLogsTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        Log.d(TAG, "onCreate()");//Logcat MESSAGE
        setContentView (R.layout.calllog);
        initialize();
    }

    private void initialize() {
        Log.d(TAG, "initialize()");

        Button btnCallLog = (Button) findViewById(R.id.btn_call_log);

        btnCallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "initialize() >> initialise loader");
                Toast.makeText (getApplicationContext (),"Please Wait Your Call Log Details are loading......",Toast.LENGTH_LONG).show ();

                getLoaderManager().initLoader(URL_LOADER, null, CallLog.this);
            }
        });

        callLogsTextView = (TextView) findViewById(R.id.call_logs);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        Log.d(TAG, "onCreateLoader() >> loaderID : " + i);
        //Toast.makeText (getApplicationContext (),"Please Wait Sometimes Your Call Log Details are Loading...",Toast.LENGTH_LONG).show ();


        switch (i) {
            case URL_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader (
                        this,   // Parent activity context
                        android.provider.CallLog.Calls.CONTENT_URI,        // Table to query
                        null,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                return null;
        }

    }



    StringBuilder sbIncoming;
    StringBuilder sbOutgoing ;
    StringBuilder sbMissed;

    @Override
    public void onLoadFinished(Loader <Cursor> loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished()");

        StringBuilder sb = new StringBuilder();

        sbIncoming = new StringBuilder();
        sbOutgoing = new StringBuilder();
        sbMissed = new StringBuilder();

        // Toast.makeText (getApplicationContext (),"Your Call Log Details are Loaded Successfuly",Toast.LENGTH_LONG).show ();

        int number = cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
        int iName =cursor.getColumnIndex (android.provider.CallLog.Calls.CACHED_NAME);
        int type = cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(android.provider.CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(android.provider.CallLog.Calls.DURATION);



        sb.append("<h4>Call Log Details <h4>");
        sb.append("\n");
        sb.append("\n");





        sb.append("<table>");

        int count =0;

//        String callCounts=cursor.getString (count);
//        Toast.makeText (getApplicationContext (),"TEST Call Count is"+callCounts ,Toast.LENGTH_SHORT).show ();

        while (cursor.moveToNext()) {

            String phNumber = cursor.getString(number);
            String cachedName =cursor.getString (iName);
            String callType = cursor.getString(type);
            String callDate = cursor.getString(date);
            Date callDayTime = new Date (Long.valueOf(callDate));
            String callDuration = cursor.getString(duration);

            String dir = null;

            int callTypeCode = Integer.parseInt(callType);

            count = count + 1;
            Toast.makeText (getApplicationContext (),"Call Count is"+count ,Toast.LENGTH_SHORT).show ();

            //  Log.d(TAG, "call count is"+callCount);
            Log.d(TAG,"call count is"+count);

            // }

            //int callCount = count + 1;
            //Toast.makeText (getApplicationContext (),"Call Count is"+callCount ,Toast.LENGTH_SHORT).show ();

            //Log.d(TAG, "call count is"+callCount);

            //int callCount=count+1;

            //
            // Toast.makeText (getApplicationContext (),"Call Count is"+callCount ,Toast.LENGTH_SHORT).show ();

            switch (callTypeCode) {
                case android.provider.CallLog.Calls.OUTGOING_TYPE:
                    dir = "Outgoing";
                    sbAppend (sbOutgoing, android.provider.CallLog.Calls.OUTGOING_TYPE,dir,cachedName,phNumber,callDayTime,callDuration,count);
                    //sbAppend (sbOutgoing,CallLog.Calls.OUTGOING_TYPE,dir,phNumber,callDuration,count);

                    break;

                case android.provider.CallLog.Calls.INCOMING_TYPE:
                    dir = "Incoming";
                    sbAppend (sbIncoming, android.provider.CallLog.Calls.INCOMING_TYPE,dir,cachedName,phNumber,callDayTime,callDuration,count);
                    //sbAppend (sbIncoming,CallLog.Calls.INCOMING_TYPE,dir,phNumber,callDuration,count);
                    break;


                case android.provider.CallLog.Calls.MISSED_TYPE:
                    dir = "Missed";
                    sbAppend (sbMissed, android.provider.CallLog.Calls.MISSED_TYPE,dir,cachedName,phNumber,callDayTime,callDuration,count);
                    //sbAppend (sbMissed,CallLog.Calls.MISSED_TYPE,dir,phNumber,callDuration,count);
                    break;

            }


            /*sb.append("<tr>")
                    .append("<td>Call Type:</td>")
                    .append("<td><strong>")
                    .append(dir)
                    .append("</strong></td>");
            sb.append("</tr>");

            sb.append("<tr>")
                    .append("<td>cachedName (number):</td>")
                    .append("<td><strong>")
                    .append(cachedName)
                    .append("</strong></td>");
            sb.append("</tr>");
            sb.append("<br/>");

            sb.append("<tr>")
                    .append("<td>Phone Number: </td>")
                    .append("<td><strong>")
                    .append(phNumber)
                    .append("</strong></td>");
            sb.append("</tr>");
            sb.append("<br/>");

            sb.append("<br/>");
            sb.append("<tr>")
                    .append("<td>Date & Time:</td>")
                    .append("<td><strong>")
                    .append(callDayTime)
                    .append("</strong></td>");
            sb.append("</tr>");
            sb.append("<br/>");
            sb.append("<tr>")
                    .append("<td>Call Duration (Seconds):</td>")
                    .append("<td><strong>")
                    .append(callDuration)
                    .append("</strong></td>");
            sb.append("</tr>");
            sb.append("<br/>");




            sb.append("<tr>")
                    .append("<td>call Count(number):</td>")
                    .append("<td><strong>")
                    .append(count)
                    .append("</strong></td>");
            sb.append("</tr>");
            sb.append("<br/>");*/


        }

        sb.append (sbIncoming);
        sb.append (sbOutgoing);
        sb.append (sbMissed);

        sb.append("<br/>");
        sb.append("</table>");

        cursor.close();

        callLogsTextView.setText(Html.fromHtml(sb.toString()));

        //incomingCallLogsTextView.setText(Html.fromHtml(sbIncoming.toString()));
        //outgoingCallLogsTextView.setText(Html.fromHtml(sbOutgoing.toString()));
        //missedCallLogsTextView.setText(Html.fromHtml(sbMissed.toString()));


    }

    //private void sbAppend(StringBuilder sbMissed, int missedType, String dir, String phNumber, String callDuration, int count) {
    //}

    /**
     * //sbAppend is a method that will append calllog details based on incoming,outgoing,missed call types
     * //@param sb
     * //@param callType
     * //@param dir
     * //@param cachedName
     * //@param phNumber
     * //@param callDayTime
     * //@param callDuration
     * //@param count
     */
    private void sbAppend(StringBuilder sb, int callType, String dir, String cachedName, String phNumber, Date callDayTime, String callDuration, int count){
        //private void sbAppend(StringBuilder sb, int callType,  String phNumber,  String callDuration, int count){
        //  private void sbAppend(StringBuilder sb, int callType,  String phNumber,  String callDuration, int count){

        switch (callType){
            case android.provider.CallLog.Calls.OUTGOING_TYPE:
                sb.append("<h4>Outgoing Call Details <h4>");
                sb.append("\n");
                sb.append("\n");
                break;
            case android.provider.CallLog.Calls.INCOMING_TYPE:
                sb.append("<h4>Incoming Call Details <h4>");
                sb.append("\n");
                sb.append("\n");
                break;
            case android.provider.CallLog.Calls.MISSED_TYPE:
                sb.append("<h4>Missed Call Details<h4>");
                sb.append("\n");
                sb.append("\n");
                break;

        }
        sb.append("<tr>")
                .append("<td>Call Type:</td>")
                .append("<td><strong>")
                .append(dir)
                .append("</strong></td>");
        sb.append("</tr>");

        sb.append("<tr>")
                .append("<td>cachedName (number):</td>")
                .append("<td><strong>")
                .append(cachedName)
                .append("</strong></td>");
        sb.append("</tr>");
        sb.append("<br/>");

        sb.append("<tr>")
                .append("<td>Phone Number: </td>")
                .append("<td><strong>")
                .append(phNumber)
                .append("</strong></td>");
        sb.append("</tr>");
        sb.append("<br/>");


        sb.append("<tr>")
                .append("<td>Date & Time:</td>")
                .append("<td><strong>")
                .append(callDayTime)
                .append("</strong></td>");
        sb.append("</tr>");
        sb.append("<br/>");
        sb.append("<tr>")
                .append("<td>Call Duration (Seconds):</td>")
                .append("<td><strong>")
                .append(callDuration)
                .append("</strong></td>");
        sb.append("</tr>");
        sb.append("<br/>");




        sb.append("<tr>")
                .append("<td>call Count(number):</td>")
                .append("<td><strong>")
                .append(count)
                .append("</strong></td>");
        sb.append("</tr>");
        sb.append("<br/>");

    }


    @Override
    public void onLoaderReset(Loader <Cursor> loader) {
        Log.d(TAG, "onLoaderReset()");
        //Toast.makeText (getApplicationContext (),"Please Wait Sometimes Your Call Log Details are Loading...",Toast.LENGTH_LONG).show ();

    }
}




