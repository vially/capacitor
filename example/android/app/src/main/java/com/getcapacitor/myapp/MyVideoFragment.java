package com.getcapacitor.myapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.getcapacitor.myapp.R;

public class MyVideoFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.myvideofragment,container, false);

    VideoView video = (VideoView)rootView.findViewById(R.id.videoView);

    video.requestFocus();

    String videopath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.video;

    video.setVideoURI(Uri.parse(videopath));

    video.setMediaController(new MediaController(getActivity())); //error in (this)[enter image description here][1]

    video.requestFocus();
    video.start();
    return rootView;
  }
}