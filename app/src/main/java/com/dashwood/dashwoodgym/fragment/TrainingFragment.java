package com.dashwood.dashwoodgym.fragment;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dashwood.dashwoodgym.R;
import com.dashwood.dashwoodgym.databinding.FragmentTrainingBinding;
import com.dashwood.dashwoodgym.handler.HandlerReturnValue;
import com.dashwood.dashwoodgym.inf.InformationPlant;
import com.dashwood.dashwoodgym.log.T;
import com.dashwood.dashwoodmediaplayer.DashWoodMediaPlayer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import hallianinc.opensource.timecounter.StopWatch;


public class TrainingFragment extends Fragment {

    private FragmentTrainingBinding binding;
    private InformationPlant informationPlant;
    private boolean isTraining = false, isPause = false;
    private StopWatch stopWatch;
    private MediaPlayer restPlayer;
    private MediaPlayer trainingPlayer;
    private Timer timer;
    private int restTime, workTime;
    private int rounds;
    private Handler handler = new Handler();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrainingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        informationPlant = getInformationPlant();
        setNew();
        setView();
        setAction();
    }

    private void setNew() {
        restPlayer = MediaPlayer.create(requireActivity(), R.raw.sound_reset);
        trainingPlayer = MediaPlayer.create(requireActivity(), R.raw.sound_do_it);
        AudioManager mAudioManager = (AudioManager) requireContext().getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        restPlayer.setVolume(1000f, 1000f);
        trainingPlayer.setVolume(1000f, 1000f);
        restPlayer.setLooping(false);
        trainingPlayer.setLooping(false);
    }

    private void setView() {
        workTime = Integer.parseInt(informationPlant.getWorkTimeAsSec());
        restTime = Integer.parseInt(informationPlant.getRestTimeAsSec());
        if (informationPlant == null) {
            T.toast(requireContext(), getString(R.string.toast_error_unknown));
            return;
        }
        binding.txtTimeOfWork.setText(HandlerReturnValue.secondToTime(informationPlant.getWorkTimeAsSec()));
        binding.txtTimeOfRest.setText(HandlerReturnValue.secondToTime(informationPlant.getRestTimeAsSec()));
        rounds = informationPlant.getRound();
        binding.txtRoundOfTraining.setText(String.valueOf(rounds));
        stopWatch = new StopWatch(binding.txtCounter);
    }

    private void setAction() {
        binding.btnStartAndPause.setOnClickListener(v -> {
            if (isTraining) {
                setPause();
                return;
            }
            isTraining = true;
            T.toast(requireContext(), getString(R.string.toast_three_sec_start));
            handler.postDelayed(delayToRun(), 3000);
            if (trainingPlayer.isPlaying()) trainingPlayer.stop();
            trainingPlayer.start();
        });
    }

    private Runnable delayToRun() {
        return this::setStart;
    }

    private void setStart() {
        binding.btnStartAndPause.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_pause));
        binding.btnStartAndPause.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.background_button_primary));
        workTimerChecker();
    }

    private void setPause() {
        binding.btnStartAndPause.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_start));
        binding.btnStartAndPause.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.background_button_orange));
        if (isPause) {
            stopWatch.resume();
            isPause = false;
            binding.btnStartAndPause.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_pause));
        } else {
            isPause = true;
            stopWatch.pause();
        }
    }

    public InformationPlant getInformationPlant() {
        return TrainingFragmentArgs.fromBundle(getArguments()).getArgInformationPlant().getInformationPlant();
    }

    private void workTimerChecker() {
        requireActivity().runOnUiThread(() -> {
            binding.txtWhatTimeNow.setText(getString(R.string.txt_text_work_time_alone));
            binding.layoutTimer.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.background_button_green));
        });
        if (!isTraining)
            stopWatch.setTime(0);
        if (trainingPlayer.isPlaying()) trainingPlayer.stop();
        trainingPlayer.start();
        stopWatch.start();
        timer = new Timer();
        isTraining = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int time = stopWatch.getTime() / 10;
                T.log("Time : " + time + " Work Time : " + workTime);
                if (time == workTime) {
                    timer.cancel();
                    timer.purge();
                    isTraining = false;
                    rounds -= 1;
                    handler.post(() -> binding.txtRoundOfTraining.setText(String.valueOf(rounds)));
                    restTimerChecker();
                }
            }
        }, 0, 100);
    }

    private void restTimerChecker() {
        requireActivity().runOnUiThread(() -> {
            binding.txtWhatTimeNow.setText(getString(R.string.txt_text_rest_time_alone));
            binding.layoutTimer.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.background_button_blue));
        });
        if (rounds == 0) {
            requireActivity().runOnUiThread(() -> {
                T.toast(requireContext(), getString(R.string.toast_round_done));
              if (restPlayer.isPlaying()) restPlayer.stop();
        restPlayer.start();
                Navigation.findNavController(binding.getRoot()).popBackStack();
            });
            return;
        }
        if (!isTraining)
            stopWatch.setTime(0);
        if (restPlayer.isPlaying()) restPlayer.stop();
        restPlayer.start();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int time = stopWatch.getTime() / 10;
                T.log("Time : " + time + " rest Time : " + restTime);
                if (time == restTime) {
                    timer.cancel();
                    timer.purge();
                    isPause = false;
                    workTimerChecker();
                }
            }
        }, 0, 100);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        handler.removeCallbacksAndMessages(null);
        if (timer == null)
            return;
        timer.cancel();
        timer.purge();

    }
}