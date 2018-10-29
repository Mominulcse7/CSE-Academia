package com.cseacademia.mominulcse1213.cseacademia;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ripon on 10/7/2018.
 */


public class UserRegistrationList  extends ArrayAdapter<UserRegistrationConstractor> {
    private Activity context;
    List<UserRegistrationConstractor> regusers;

    public UserRegistrationList(Activity context, List<UserRegistrationConstractor> regusers) {
        super(context, R.layout.user_reg_list_show, regusers);
        this.context = context;
        this.regusers = regusers;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.user_reg_list_show, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.userregname);
        TextView textViewRoll = (TextView) listViewItem.findViewById(R.id.userregroll);
        TextView textViewSession = (TextView) listViewItem.findViewById(R.id.userregsession);
        TextView textViewPhone = (TextView) listViewItem.findViewById(R.id.userregphone);
        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.userregemail);
        TextView textViewMacAddress = (TextView) listViewItem.findViewById(R.id.userregmacaddress);

        UserRegistrationConstractor reguser = regusers.get(position);
        textViewName.setText(reguser.getUserName());
        textViewRoll.setText(reguser.getUserRoll());
        textViewSession.setText(reguser.getUserSession());
        textViewPhone.setText(reguser.getUserPhone());
        textViewEmail.setText(reguser.getUserEmail());
        textViewMacAddress.setText(reguser.getMacAddress());

        return listViewItem;
    }
}
