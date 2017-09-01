package com.example.barryhuang.bluetooth;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.example.barryhuang.bluetooth.BluetoothConnectionService.*;



public class Main2Activity extends AppCompatActivity {

    BluetoothConnectionService bluetoothgraph = new BluetoothConnectionService(Main2Activity.this);
    GraphView dynamicGraph;
    private LineGraphSeries<DataPoint> seriesx;
    private LineGraphSeries<DataPoint> seriesy;
    private LineGraphSeries<DataPoint> seriesz;
    Viewport viewport;

    private double lastX = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        dynamicGraph = (GraphView) findViewById(R.id.graph);


        seriesx = new LineGraphSeries<DataPoint>();
        dynamicGraph.addSeries(seriesx);
        seriesy = new LineGraphSeries<DataPoint>();
        dynamicGraph.addSeries(seriesy);
        seriesz = new LineGraphSeries<DataPoint>();
        dynamicGraph.addSeries(seriesz);

        viewport = dynamicGraph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(-1000);
        viewport.setMaxY(1000);
        viewport.setXAxisBoundsManual(true);
        viewport.setMinX(0);
        viewport.setMaxX(1000);
        viewport.setScrollable(true);
    }

    protected void onResume() {
        super.onResume();
        // we are going to simulate real time with thread that append data to graph
        new Thread(new Runnable() {
            @Override
            public void run() {
                //we add 100 entries
                while (true){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });
                    //sleep to slow down the add of entries
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        //manage error
                    }
                }
            }
        }).start();
    }

    private void addEntry() {
        int c;
        double x,y,z;
        String[] getDatafinally = new String[3];
        String[] Incomingdata;
        /*Incomingdata = bluetoothgraph.run2();
        for(c=0;c<Incomingdata.length;c++){
            getDatafinally[c] = Incomingdata[c];
        }*/

        x = Double.parseDouble(getDatafinally[0]);
        y = Double.parseDouble(getDatafinally[1]);
        z = Double.parseDouble(getDatafinally[2]);
        seriesx.appendData(new DataPoint(lastX++, x), true ,1000);
        seriesy.appendData(new DataPoint(lastX++, y), true ,1000);
        seriesz.appendData(new DataPoint(lastX++, z), true ,1000);


    }

}
