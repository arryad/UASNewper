package com.example.uas_newper.Fragment.admin;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.example.uas_newper.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {

    VideoView videoView;
    ListView listView;
    ArrayList<String> videoList;
    ArrayAdapter adapter;


    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv, container, false);

        videoView = view.findViewById(R.id.videoView);
        listView = view.findViewById(R.id.listView);

        MediaController mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        videoList = new ArrayList<>();
        videoList.add("Penemuan ladang ganja di hutan gunung");
        videoList.add("Indonesia juara umum di Indonesian Master 2020");
        videoList.add("Alugoro 405 Kapal Selam karya anak bangsa");

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, videoList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:
                        videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() +
                                "/" + R.raw.ekonomi_ladang_ganja));
                        break;
                    case 1:
                        videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() +
                                "/" + R.raw.olah_raga_juara_master));
                        break;
                    case 2:
                        videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() +
                                "/" + R.raw.teknologi_kapal_selam));
                        break;
                }
                videoView.requestFocus();
                videoView.start();
            }
        });

        return view;
    }

}
