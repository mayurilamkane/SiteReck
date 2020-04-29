package be.project.sitereck.ProjectManager.Better.Activities.MaterialRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import be.project.sitereck.GeneralClasses.URL_STRINGS;
import be.project.sitereck.ProjectManager.POJO.MatReqItemClass;
import static be.project.sitereck.ProjectManager.POJO.PmMiscData.getProjectlist;
import be.project.sitereck.R;

public class MatReqDialog extends AppCompatActivity {

    TextView txtCancel, MatName , Matdesc, ProName , ReqOn , ExpecOn , ExpecDate, Status ;
    Button btnApprove;
    LinearLayout laybtns;
    RequestQueue rq;
    Dialog dialog;

    public void FullMatReqDialog(final Activity mactivity, final Context mcontext, ViewGroup v, final MatReqItemClass data , final int position){
        Rect disRect = new Rect();
        Window window = mactivity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(disRect);
        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext, R.style.CustomAlertDialog);
        ViewGroup viewGroup = v;
        View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_matreq, viewGroup, false);
//        dialogView.setMinimumWidth((int)(disRect.width() * 1f));
//        dialogView.setMinimumHeight((int)(disRect.height() * 1f));
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(true);

        txtCancel = alertDialog.findViewById(R.id.textcancel);
        MatName = alertDialog.findViewById(R.id.tv_material_name);
        Matdesc =  alertDialog.findViewById(R.id.tv_material_desc);
        ProName  = alertDialog.findViewById(R.id.tv_projectName);
        ReqOn = alertDialog.findViewById(R.id.tv_rdate);
        ExpecDate = alertDialog.findViewById(R.id.tv_rrdate);
        ExpecOn = alertDialog.findViewById(R.id.tv_expecdate);
        Status = alertDialog.findViewById(R.id.tv_status);
        btnApprove = alertDialog.findViewById(R.id.btnapprove);
        laybtns = alertDialog.findViewById(R.id.lay_btmbtn);
        
        MatName.setText(data.getSmaterialName());
        if (data.getNote().equals(null)){
            Matdesc.setText("N.A");
        }else{Matdesc.setText(data.getNote());}
        ProName.setText(getProjectlist().get(data.getPId()));
        ReqOn.setText(data.getMdate());
        ExpecDate.setText(data.getRRDate());
        if (data.getRStat().equals("0")){
         Status.setText("Pending!!");   
         Status.setTextColor(mcontext.getResources().getColor(R.color.red));
        }else if (data.getRStat().equals("1")){
            Status.setText("Approved");
            Status.setTextColor(mcontext.getResources().getColor(R.color.primegreen));
            btnApprove.setEnabled(false);
            btnApprove.setText("Approved");
            btnApprove.setBackground(mcontext.getResources().getDrawable(R.drawable.disabled_btn));
            btnApprove.setTextColor(mcontext.getResources().getColor(R.color.grey_60));
//            laybtns.
        } 
        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCALL_ApproveMatReq(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("request_update").equals("1") && jsonObject.getString("stat").equals("1") ){
                                Toast.makeText(mcontext, "Request Approved", Toast.LENGTH_SHORT).show();
                                btnApprove.setEnabled(false);
                                btnApprove.setText("Approved");
                                btnApprove.setBackground(mcontext.getResources().getDrawable(R.drawable.disabled_btn));
                                btnApprove.setTextColor(mcontext.getResources().getColor(R.color.grey_60));
                                Status.setText("Approved");
                                Status.setTextColor(mcontext.getResources().getColor(R.color.primegreen));
                            }else{
                                Toast.makeText(MatReqDialog.this, "Something Went wrong . Try Again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String > params = new HashMap<>();
                        params.put("req_id",data.getRId());
                        params.put("req_status","1");
                        return params;
                    }
                };
                rq = Volley.newRequestQueue(mcontext);
                rq.add(stringRequest);
            }
        });
    }

//    private void ApproveRequest(final MatReqItemClass data) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STRINGS.getCALL_ApproveMatReq(), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                System.out.println(response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.getString("request_update").equals("1") && jsonObject.getString("stat").equals("1") ){
//                        Toast.makeText(MatReqDialog.this, "Request Approved", Toast.LENGTH_SHORT).show();
//                        btnApprove.setEnabled(false);
//                        Status.setText("Approved");
//                        Status.setTextColor(mcontext.getResources().getColor(R.color.primegreen));
//                    }else{
//                        Toast.makeText(MatReqDialog.this, "Something Went wrong . Try Again", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println(error.toString());
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String > params = new HashMap<>();
//                params.put("req_id",data.getRId());
//                params.put("req_status","1");
//                return params;
//            }
//        };
//        rq = Volley.newRequestQueue(this);
//        rq.add(stringRequest);
//    }


}
