package bulletinboard.htbeyond.com.bulletinboard;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bulletinboard.htbeyond.com.bulletinboard.fragments.NoticeListFragment;

public class ListActivity extends AppCompatActivity {

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, ListActivity.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            NoticeListFragment fragment = new NoticeListFragment();
            transaction.replace(R.id.list_fragment, fragment);
            transaction.commit();
        }
    }


}
