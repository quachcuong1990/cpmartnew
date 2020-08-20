package activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cp_mart.R;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.LoaispAdapter;
import model.Loaisp;
import ultil.CheckConnection;
import ultil.Server;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ViewFlipper viewFlipper;
    ListView listView;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    int id;
    String tenloaisp = "";
    String hinhanhloaisp = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            Actionbar();
            ActionViewFlipper();
            GetDuLieuLoaisp();
        }else CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");

    }

    private void GetDuLieuLoaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0 ; i <response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mangloaisp.add(3,new Loaisp(0,"Liên Hệ","https://cdn2.vectorstock.com/i/1000x1000/01/11/telephone-call-icon-vector-23190111.jpg"));
                        mangloaisp.add(4,new Loaisp(0,"Thông Tin","https://www.iconhot.com/icon/png/quiet/256/information.png"));
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn.tgdd.vn/Products/Images/2946/87128/bhx/thung-48-hop-sua-uong-dinh-duong-dielac-grow-plus-110ml-cho-tre-thap-coi-202003021046143879_300x300.jpg");
        mangquangcao.add("https://chieutrelanlan.com/uploads/photo/chieu-truc-mat-nho-loai-co-vien-mau-den-140x190cm-a1-5243.jpg");
        mangquangcao.add("https://thegioidemviet.vn/wp-content/uploads/2018/03/chi%C3%AA%CC%81u-tru%CC%81c-t%C4%83m-2.jpg");
        mangquangcao.add("https://cf.shopee.vn/file/423b424583b4950acfa7d364a9369ea5");
        for (int i =0 ; i<mangquangcao.size();i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);

        }
            viewFlipper.setFlipInterval(5000);
            viewFlipper.setAutoStart(true);
            Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
            Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
            viewFlipper.setInAnimation(animation_slide_in);
            viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinh);
        recyclerView = (RecyclerView) findViewById(R.id.reclerviewmanhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        listView = (ListView) findViewById(R.id.listviewmanhinhchinh);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang Chính","https://w7.pngwing.com/pngs/310/332/png-transparent-computer-icons-home-house-desktop-service-home-blue-logo-room.png"));
        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listView.setAdapter(loaispAdapter);


    }
}