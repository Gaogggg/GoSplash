package edu.pku.gg.gosplash.common.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.pku.gg.gosplash.R;

/**
 * Created by gaoge
 */
public class GoToast extends Toast {

    public GoToast(Context context) {
        super(context);
    }

    public static Toast makeText(Context context,ToastType type ,int duration) {
        Toast result = new Toast(context);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.gotoast, null);

        LinearLayout mToastLayout = (LinearLayout)layout.findViewById(R.id.toast_layout);
        TextView mTextView = (TextView)layout.findViewById(R.id.toast_text);

        switch (type){
            case REFRESH_SUCCESS:
                mToastLayout.setBackgroundColor(0x7f34A853);
                mTextView.setText("REFRESH SUCCESS!");
                break;
            case REFRESH_FAILED:
                mToastLayout.setBackgroundColor(0x7fEA4335);
                mTextView.setText("REFRESH FAILED!");
                break;
            case LOADMORE_SUCCESS:
                mToastLayout.setBackgroundColor(0x7f34A853);
                mTextView.setText("LOADMORE SUCCESS!");
                break;
            case LOADMORE_FAILED:
                mToastLayout.setBackgroundColor(0x7fEA4335);
                mTextView.setText("LOADMORE FAILED!");
                break;
            case NOMORE:
                mToastLayout.setBackgroundColor(0x7f4285F4);
                mTextView.setText("NO MORE!");
                break;
            case DOWNLOAD_SUCCESS:
                mToastLayout.setBackgroundColor(0x7f34A853);
                mTextView.setText("DOWNLOAD SUCCESS");
                break;
            case DOWNLOAD_FAILED:
                mToastLayout.setBackgroundColor(0x7fEA4335);
                mTextView.setText("DOWNLOAD FAILED!");
                break;
            default:
                break;
        }

        result.setView(layout);
        result.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL, 0, 0);
        result.setDuration(duration);

        return result;
    }

    public enum ToastType{
        REFRESH_SUCCESS,
        REFRESH_FAILED,
        LOADMORE_SUCCESS,
        LOADMORE_FAILED,
        NOMORE,
        DOWNLOAD_SUCCESS,
        DOWNLOAD_FAILED
    }

}
