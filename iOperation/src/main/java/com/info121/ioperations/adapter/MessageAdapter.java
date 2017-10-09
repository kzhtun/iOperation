package com.info121.ioperations.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.info121.constant.Constants;
import com.info121.ioperations.R;
import com.info121.ioperations.util.Util;
import com.info121.model.iMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by KZHTUN on 9/26/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    Context mContext;
    List<iMessage> iMessages;


    public MessageAdapter(Context mContext, List<iMessage> iMessages) {
        this.mContext = mContext;
        this.iMessages = iMessages;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View v = layoutInflater.inflate(R.layout.child_listview_message, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.mMessageHeader.setText("Title : " + iMessages.get(i).getMsgHdr());
        holder.mJobNo.setText("Job No : " +  iMessages.get(i).getJobNo());
        holder.mSendBy.setText("Sent By : " + iMessages.get(i).getSendBy());

        String[] sendDate = iMessages.get(i).getSendDate().split("/");

        holder.mSendDate.setText("Sent At : " + sendDate[1] + "/" + sendDate[0] + "/" + sendDate[2]);

        if(iMessages.get(i).getIsRead().equals("0")){
            holder.mMail.setImageDrawable(mContext.getDrawable(R.drawable.ic_mail_white_48dp));

            holder.mMessageHeader.setTypeface(null, Typeface.BOLD);
            holder.mJobNo.setTypeface(null, Typeface.BOLD);
            holder.mSendBy.setTypeface(null, Typeface.BOLD);
            holder.mSendDate.setTypeface(null, Typeface.BOLD);

            holder.mMessageHeader.setTextColor(mContext.getResources().getColor(R.color.COLOR_WHITE));
            holder.mJobNo.setTextColor(mContext.getResources().getColor(R.color.COLOR_WHITE));
            holder.mSendBy.setTextColor(mContext.getResources().getColor(R.color.COLOR_WHITE));
            holder.mSendDate.setTextColor(mContext.getResources().getColor(R.color.COLOR_WHITE));

            holder.mItemLayout.setBackground(mContext.getDrawable(R.drawable.rounded_itemview_unread));

        }else {
            holder.mMail.setImageDrawable(mContext.getDrawable(R.drawable.ic_mail_open_white_48dp));

            holder.mMessageHeader.setTypeface(null, Typeface.NORMAL);
            holder.mJobNo.setTypeface(null, Typeface.NORMAL);
            holder.mSendBy.setTypeface(null, Typeface.NORMAL);
            holder.mSendDate.setTypeface(null, Typeface.NORMAL);

            holder.mMessageHeader.setTextColor(mContext.getResources().getColor(R.color.COLOR_LIGHT_GRAY));
            holder.mJobNo.setTextColor(mContext.getResources().getColor(R.color.COLOR_LIGHT_GRAY));
            holder.mSendBy.setTextColor(mContext.getResources().getColor(R.color.COLOR_LIGHT_GRAY));
            holder.mSendDate.setTextColor(mContext.getResources().getColor(R.color.COLOR_LIGHT_GRAY));

            holder.mItemLayout.setBackground(mContext.getDrawable(R.drawable.rounded_itemview));
        }

        // holder.mItemLayout.setPadding(10,5,10,5);

        final String msgID = iMessages.get(i).getMsgId().toString();
        final int index = i;

        holder.mItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getMessageAsyncTask().execute(msgID, String.valueOf(index));
            }
        });

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle("Warnning");
                alertDialog.setMessage("Are you sure want to delete this message?");

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new deleteMessageAsyncTask().execute(msgID);
                        iMessages.remove(index);
                        notifyDataSetChanged();

                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return iMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView mMessageHeader;
        TextView mJobNo;
        TextView mSendBy;
        TextView mSendDate;

        LinearLayout mItemLayout;
        ImageView mMail;
        ImageView mDelete;
        Button mClick;

        public ViewHolder(View itemView) {
            super(itemView);

            mMessageHeader = (TextView) itemView.findViewById(R.id.message_header);
            mJobNo = (TextView) itemView.findViewById(R.id.job_no);
            mSendBy = (TextView) itemView.findViewById(R.id.send_by);
            mSendDate = (TextView) itemView.findViewById(R.id.send_date);

            mItemLayout = (LinearLayout) itemView.findViewById(R.id.item_layout);
            mMail = (ImageView) itemView.findViewById(R.id.mail_icon);
            mDelete = (ImageView) itemView.findViewById(R.id.mail_delete);

           // mClick = (Button) itemView.findViewById(R.id.click);
        }
    }


    private class getMessageAsyncTask extends AsyncTask<String, Void, String> {
        boolean isTimeout = false;
        String msgID = "";
        int index;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject rootObj = new JSONObject(result);
                JSONArray msgObj = rootObj.getJSONArray("result").getJSONObject(0).getJSONArray("data");

                if(msgObj.length() > 0){
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(msgObj.getJSONObject(0).getString("msgHdr"));
                    alertDialog.setMessage(msgObj.getJSONObject(0).getString("msgBody"));
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new updateMessageAsyncTask().execute(msgID);

                            iMessages.get(index).setIsRead("1");
                            notifyDataSetChanged();
                        }
                    });

                    alertDialog.show();
                }

            } catch(Exception e){
                Util.modifiedLogTrace(e.getStackTrace().toString());
            }

        }

        @Override
        protected String doInBackground(String... params) {
            Hashtable<String, String> parameter = new Hashtable<String, String>();

            try {
                parameter = new Hashtable<String, String>();
                parameter.put("msgid", params[0]);

                msgID = params[0];
                index = Integer.parseInt(params[1]);

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }

            return Util.connectSOAP(Constants.uRLiOpsGetDriverMessages, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsGetDriverMessages, Constants.APIDotNetMethodNameGetDriverMessages, parameter);

        }
    }

    private class deleteMessageAsyncTask extends AsyncTask<String, Void, String> {
        boolean isTimeout = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {
            Hashtable<String, String> parameter = new Hashtable<String, String>();

            try {
                parameter = new Hashtable<String, String>();
                parameter.put("msgid", params[0]);

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }

            return Util.connectSOAP(Constants.uRLiOpsDeleteDriverMessages, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsDeleteDriverMessages, Constants.APIDotNetMethodNameDeleteDriverMessages, parameter);

        }
    }

    private class updateMessageAsyncTask extends AsyncTask<String, Void, String> {
        boolean isTimeout = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }

        @Override
        protected String doInBackground(String... params) {
            Hashtable<String, String> parameter = new Hashtable<String, String>();

            try {
                parameter = new Hashtable<String, String>();
                parameter.put("msgid", params[0]);

            } catch (Exception e) {
                isTimeout = true;
                Util.modifiedLogTrace(Log.getStackTraceString(e));
            }

            return Util.connectSOAP(Constants.uRLiOpsUpdateDriverMessages, Constants.APIDotNetNameSpace, Constants.APIDotNetSOAPActioniOpsUpdateDriverMessages, Constants.APIDotNetMethodNameUpdateDriverMessages, parameter);

        }
    }
}

