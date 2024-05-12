package com.example.vizilabda;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class dataListActivity extends AppCompatActivity {
    private static  final String LOG_TAG = dataListActivity.class.getName();
    private FirebaseUser user;

    private RecyclerView mRecyclerView;
    private ArrayList<dataItem> mItemList;
    private dataListAdapter mAdapter;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    private int gridNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Log.d(LOG_TAG, "Hitelesitett felhasznalo!");
        }else {
            Log.d(LOG_TAG, "Nem hitelesitett felhasznalo!");
            finish();
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager((new GridLayoutManager(this, gridNumber)));
        mItemList = new ArrayList<>();

        mAdapter = new dataListAdapter(this, mItemList);

        mRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");


        queryData();

    }

    private void queryData(){
        mItemList.clear();

        //mItems.whereEqualTo();
        mItems.orderBy("name").limit(14).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                dataItem item = document.toObject(dataItem.class);
                mItemList.add(item);
            }
            if(mItemList.size() == 0){
                initializeData();
                queryData();
            }

            mAdapter.notifyDataSetChanged();
        });

    }

   private void initializeData() {
        String[] itemList = getResources().getStringArray(R.array.data_item_team);
        int[] itemsPoint = getResources().getIntArray(R.array.data_item_point);
        
        //mItemList.clear();

        for (int i = 0; i < itemList.length; i++) {
            mItems.add(new dataItem(itemList[i], itemsPoint[i]));
        }

        //mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.data_list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int menuId = item.getItemId();
       /* switch (menuId) {
            case R.id.logout_btn:
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.setting_btn:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/

        if (menuId == R.id.logout_btn)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        } else if (menuId == R.id.setting_btn){
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
}