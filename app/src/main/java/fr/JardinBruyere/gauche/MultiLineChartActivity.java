package fr.JardinBruyere.gauche;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.JardinBruyere.gauche.database.sensor.Sensor;
import fr.JardinBruyere.gauche.database.sensor.SensorViewModel;
import fr.JardinBruyere.gauche.database.sensorReading.SensorReading;
import fr.JardinBruyere.gauche.database.sensorReading.SensorReadingViewModel;

public class MultiLineChartActivity extends DemoBase implements OnSeekBarChangeListener,
        OnChartGestureListener, OnChartValueSelectedListener {

    public static ArrayList<String> listOfName;
    private LineChart chart;
    private Button seekBarX, seekBarY;
    private TextView tvX, tvY;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_linechart);
        SensorViewModel mSensorsViewModel = new ViewModelProvider(this).get(SensorViewModel.class);

        LiveData<List<Sensor>> listing2 = mSensorsViewModel.getAllWords();

        ArrayList<String> pleasework = new ArrayList<>();
        listOfName= new ArrayList<String>();
        tvX = findViewById(R.id.tvXMax);
        setTitle("MultiLineChartActivity");

        seekBarY = findViewById(R.id.seekBar2);

        seekBarY.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                saveToGallery();
            } else {
                requestStoragePermission(chart);
            }
        });

        chart = findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);

        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);


        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setTextColor(Color.BLACK);


        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(15f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                int index=(int) value;
                String display = "";
                if(index>pleasework.size())
                    display=pleasework.get(pleasework.size()-1);
                else
                    display=pleasework.get(index);
                Log.e("DATE AFFICHAGE", String.format("\t\t%f %s",value,display));
                return display;
            }

        });

        SensorReadingViewModel mRelevesCapteursViewModel = new ViewModelProvider(this).get(SensorReadingViewModel.class);

        chart.resetTracking();
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // Set the marker to the chart
        mv.setChartView(chart);
        chart.setMarker(mv);

        chart.setBackgroundColor(1);

        listing2.observe(this, list -> {
            for(Sensor now:list){
                if(CustomAdapter.list.contains(now.getId())){
                    listOfName.add(now.getName());
                }
            }
        });

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        Log.e("test ",dataSets.size()+" "+CustomAdapter.list.size());
        AsyncTask.execute(() -> {
            for(int current: CustomAdapter.list) {
                ArrayList<Entry> values = new ArrayList<>();
                List<SensorReading> listing = mRelevesCapteursViewModel.getSensorReading(current);
                final long[] i = {1};
                    listing.forEach(it->{

                        long date = it.getDateAdded();
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM HH'h'mm");
                        Log.e("DEBUG DATE", String.format("\t\t%d %s",date,format.format(date)));
                        pleasework.add(format.format(date));
                        values.add(new Entry(i[0], (float) it.getValue()));
                        i[0] = i[0] +1;
                    });
                    LineDataSet set1 = new LineDataSet(values, "Capteur "+current);
                    set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                    set1.setValueTextColor(ColorTemplate.getHoloBlue());
                    set1.setLineWidth(1.5f);
                    set1.setDrawCircles(false);
                    set1.setDrawValues(false);
                    set1.setFillAlpha(65);
                    set1.setFillColor(ColorTemplate.getHoloBlue());
                    set1.setHighLightColor(Color.rgb(244, 117, 117));
                    set1.setDrawCircleHole(false);
                    int color = colors[current%colors.length];
                    set1.setColor(color);
                    set1.setCircleColor(color);
                    dataSets.add(set1);
                    Log.e("taille", String.valueOf(dataSets.size()));
                }

                LineData data = new LineData(dataSets);
                chart.setData(data);
                chart.invalidate();
            });
            Log.e("test", String.valueOf(dataSets.size()));
    }
    private final int[] colors = new int[] {
            ColorTemplate.rgb("FF00FF"),
            ColorTemplate.rgb("00FF00"),
            ColorTemplate.rgb("00FFFF"),
            ColorTemplate.rgb("FF0000")
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


        }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "MultiLineChartActivity");
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            chart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart long pressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart fling. VelocityX: " + velocityX + ", VelocityY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());

    }

    @Override
    public void onNothingSelected() {}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
