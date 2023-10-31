// written by: Shaan Parikh, Shivum Mehta
// tested by: Shaan Parikh, Shivum Mehta
// debugged by: Shaan Parikh, Shivum Mehta

package com.example.shaan.hazardapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class sensorAdapter extends RecyclerView.Adapter<sensorAdapter.SensorViewHolder> {

    Context mCtx;
    List<sensor> sensorList;

    public sensorAdapter(Context mCtx, List<sensor> sensorList) {
        this.mCtx = mCtx;
        this.sensorList = sensorList;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View view = LayoutInflater.from(mCtx).inflate(R.layout.dashboardr_layout, viewGroup, false  );

         SensorViewHolder sensorViewHolder = new SensorViewHolder(view);

        return sensorViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SensorViewHolder sensorViewHolder, int i) {
        sensor Sensor = sensorList.get(i);

        sensorViewHolder.sensorText.setText(Sensor.getSensorName());
        sensorViewHolder.sensorStatus.setText(Sensor.getSensorData());
    }

    @Override
    public int getItemCount() {
        return sensorList.size();
    }

    class SensorViewHolder extends RecyclerView.ViewHolder{

        TextView sensorText, sensorStatus;

            public SensorViewHolder(View itemView){
                super(itemView);

                sensorStatus = itemView.findViewById(R.id.sensorStatus);
                sensorText = itemView.findViewById(R.id.sensorText);
            }

    }
}
