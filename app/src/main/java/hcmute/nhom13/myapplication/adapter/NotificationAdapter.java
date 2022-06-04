package hcmute.nhom13.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hcmute.nhom13.myapplication.R;
import hcmute.nhom13.myapplication.model.Bill;
import hcmute.nhom13.myapplication.model.Notification;

public class NotificationAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<Notification> listNoti;

    public NotificationAdapter(Context context, int layout, List<Notification> listNoti) {
        this.context = context;
        this.layout = layout;
        this.listNoti = listNoti;
    }

    @Override
    public int getCount() {
        return listNoti.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTime,txtTitle,txtBody,txtHeader;
        ImageView imgShipping,imgDone,imgFailed;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view ==null){
            holder=new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);

            //Anh xa
            holder.txtTime = view.findViewById(R.id.textView36);
            holder.txtTitle = view.findViewById(R.id.textView35);
            holder.txtHeader = view.findViewById(R.id.textView38);
            holder.txtBody= view.findViewById(R.id.textView39);

            holder.imgShipping = view.findViewById(R.id.imageView25);
            holder.imgDone = view.findViewById(R.id.imageView23);
            holder.imgFailed = view.findViewById(R.id.imageView24);


            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        Notification notification =listNoti.get(i);
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        long hours=0;
        long mins=0;

        String current_date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String date = notification.getDate()+" "+notification.getTime();
        try {
            Date date1 = simpleDateFormat.parse(current_date);
            Date date2 = simpleDateFormat.parse(date);
            long getDiff = date1.getTime() - date2.getTime();

            hours = TimeUnit.MILLISECONDS.toHours(getDiff);
            mins =  TimeUnit.MILLISECONDS.toMinutes(getDiff);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if((int)hours == 0){
            if((int)mins ==0 || (int)mins==1){
                holder.txtTime.setText("1 minute ago");
            }else {
                holder.txtTime.setText(mins + " minutes ago");
            }
        }
        else if((int)hours < 24){
            holder.txtTime.setText(hours+" hours ago");
        }
        else{
            holder.txtTime.setText(notification.getDate());
        }

        //=================================================================

        if(notification.getType().equals("done")){
            holder.imgDone.setVisibility(View.VISIBLE);
            holder.imgShipping.setVisibility(View.GONE);
            holder.imgFailed.setVisibility(View.GONE);

            holder.txtTitle.setText("Successful delivery");
            holder.txtHeader.setText("Your order has been successfully delivered");
            holder.txtBody.setText("The app confirms you have successfully received your $"+notification.getTotal() +" order. Thank you for using the app");
        }
        else if(notification.getType().equals("failed")){
            holder.imgDone.setVisibility(View.GONE);
            holder.imgShipping.setVisibility(View.GONE);
            holder.imgFailed.setVisibility(View.VISIBLE);

            holder.txtTitle.setText("Failed delivery");
            holder.txtHeader.setText("Your order has been cancelled");
            holder.txtBody.setText("The $"+notification.getTotal() +" order has been canceled on request. Please check and re-order on the app");
        }
        else{
            holder.imgDone.setVisibility(View.GONE);
            holder.imgShipping.setVisibility(View.VISIBLE);
            holder.imgFailed.setVisibility(View.GONE);

            holder.txtTitle.setText("Delivering");
            holder.txtHeader.setText("The driver is making the delivery to your place");
            holder.txtBody.setText("Your $"+notification.getTotal()+ " order is being shipped. Please contact the driver if you have a new request");

        }


        return view;
    }
}
