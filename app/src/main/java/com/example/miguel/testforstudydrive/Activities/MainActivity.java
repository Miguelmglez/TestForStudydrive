package com.example.miguel.testforstudydrive.Activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.miguel.testforstudydrive.Adapters.MyItemRecyclerViewAdapter;
import com.example.miguel.testforstudydrive.Objects.ExampleObject;
import com.example.miguel.testforstudydrive.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int consumer = 0;
    private int producer = 0;
    private TextView consumerText;
    private TextView producerText;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private final int TIMER_CONSUMER = 4000;
    private final int TIMER_PRODUCER = 3000;
    private final String DESCRIPTION = "Element added by producer";
    private ArrayList<ExampleObject> mList ;
    private MyItemRecyclerViewAdapter mAdapter;
    private Button buttonProducer;
    private Button buttonConsumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = new ArrayList<>();
        mAdapter = new MyItemRecyclerViewAdapter(mList);
        consumerText = findViewById(R.id.textViewConsumer);
        producerText = findViewById(R.id.textViewProducer);
        consumerText.setText(getResources().getString(R.string.nConsumers) +" "+ String.valueOf(this.consumer));
        producerText.setText(getResources().getString(R.string.nProducers) +" "+ String.valueOf(this.producer));
        buttonProducer = findViewById(R.id.buttonProducer);
        buttonConsumer = findViewById(R.id.buttonConsumer);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        buttonConsumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addConsumer();
            }
        });
        buttonConsumer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonConsumer.setBackgroundResource(R.drawable.button_pressed);
                return false;
            }
        });
        buttonProducer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProducer();
            }
        });
        buttonProducer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonProducer.setBackgroundResource(R.drawable.button_pressed);
                return false;
            }
        });
        final Handler consumerHandler = new Handler();
        consumerHandler.postDelayed(new Runnable(){
            public void run(){
                deleteElements();
                consumerHandler.postDelayed(this, TIMER_CONSUMER);
            }
        }, TIMER_CONSUMER);

        final Handler producerHandler = new Handler();
        producerHandler.postDelayed(new Runnable(){
            public void run(){
                addElements();
                producerHandler.postDelayed(this, TIMER_PRODUCER);
            }
        }, TIMER_PRODUCER);
    }

    private void addElements() {
        for (int i = 0; i < producer; i++){
            Log.d("INSERTED", "ELEMENT INSERTED");
            ExampleObject exampleObject = new ExampleObject(mAdapter.getItemCount(),DESCRIPTION);
            mList.add(exampleObject);
        }
        mAdapter.notifyDataSetChanged();
    }
    private void deleteElements() {
        for (int i = 0; i < consumer; i++){
            Log.d("DELETED", "ELEMENT DELETED");
            try {
                if (mList.size() >= 0){
                    mList.remove(0);
                }
            } catch (Exception e){
                Log.d("Exception", "List is empty");
            }
            mAdapter.notifyDataSetChanged();
        }
    }
    private void addConsumer(){
        this.consumer = consumer + 1;
        this.consumerText.setText(getResources().getString(R.string.nConsumers) +" "+ String.valueOf(this.consumer));
        buttonConsumer.setBackgroundResource(R.drawable.button_style);
    }
    private void addProducer(){
        this.producer = producer + 1;
        this.producerText.setText(getResources().getString(R.string.nProducers) +" "+ String.valueOf(this.producer));
        buttonProducer.setBackgroundResource(R.drawable.button_style);
    }
}
