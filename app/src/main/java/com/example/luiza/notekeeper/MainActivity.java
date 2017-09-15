package com.example.luiza.notekeeper;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.luiza.notekeeper.Models.Database.ConfigDao;
import com.example.luiza.notekeeper.Models.Database.NoteDAO;
import com.example.luiza.notekeeper.Models.Note;
import com.example.luiza.notekeeper.Models.NoteConfig;
import com.example.luiza.notekeeper.Models.Services.NoteAdaptor;
import com.example.luiza.notekeeper.Models.Services.NoteRCAdapter;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //ListView lvNotes;
    private RecyclerView rvNotes;
    private NoteRCAdapter noteAdapter;
    private RecyclerView.LayoutManager noteLayoutManager;
    private List<Note> notes;
    private NoteDAO noteDao;
    private ConfigDao configDao;
    private String currentUser;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CreateNoteActivity.class);
                i.putExtra("USERNAME", userName);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                   //     .setAction("Action", null).show();
            }
        });

        noteDao = new NoteDAO(this);
        configDao = new ConfigDao(this);

        NoteConfig config = configDao.get("LOGGEDUSER");
        userName = config.getUserName();

        //lvNotes = (ListView) findViewById(R.id.lvNotes);

        rvNotes = (RecyclerView) findViewById(R.id.rvNotes);
        rvNotes.setHasFixedSize(true);
        noteLayoutManager = new LinearLayoutManager(this);
        rvNotes.setLayoutManager(noteLayoutManager);

        noteAdapter = new NoteRCAdapter(new ArrayList<Note>(), new NoteRCAdapter.MyOnClickListener() {
            @Override
            public void ItemClicked(Note note) {
                Intent i = new Intent(MainActivity.this, CreateNoteActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.putExtra("NOTEID", note.getId());
                i.putExtra("USERNAME", userName);
                startActivity(i);
            }
        });
        rvNotes.setAdapter(noteAdapter);

        NoteConfig c = configDao.get("LOGGEDUSER");
        currentUser = c.getUserName();

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.END | ItemTouchHelper.START) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //return super.getMovementFlags(recyclerView, viewHolder);
                int dragFlags = 0;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if(MainActivity.this.noteDao.delete(MainActivity.this.noteAdapter.getItemId(viewHolder.getAdapterPosition())) > 0) {
                    Toast.makeText(MainActivity.this, getString(R.string.delete_note_success_rc), Toast.LENGTH_LONG).show();
                    MainActivity.this.loadNotes();
                }
                else {
                    Toast.makeText(MainActivity.this, getString(R.string.delete_note_error_rc), Toast.LENGTH_LONG).show();
                }
            }
;        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        touchHelper.attachToRecyclerView(rvNotes);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.loadNotes();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logoff) {
            configDao.delete("LOGGEDUSER");
            Toast.makeText(MainActivity.this, "Logged off successfuly", Toast.LENGTH_LONG).show();
            LoginManager.getInstance().logOut();
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i = null;

        if (id == R.id.createNote) {
            i = new Intent(MainActivity.this, CreateNoteActivity.class);
            i.putExtra("USERNAME", userName);
        } else if (id == R.id.about) {
            i = new Intent(MainActivity.this, SobreActivity.class);

        } else if(id == R.id.map) {
            i = new Intent(MainActivity.this, WhereAmIActivity.class);
        }

        if(i != null) {
            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadNotes(){
        List<Note> notes = noteDao.getAllNotes();

        if(notes.size() > 0){
            noteAdapter.updateDataSet(notes);
            /*lvNotes.setAdapter(new NoteAdaptor(this, notes));
            lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });*/
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadNotes();
    }
}
