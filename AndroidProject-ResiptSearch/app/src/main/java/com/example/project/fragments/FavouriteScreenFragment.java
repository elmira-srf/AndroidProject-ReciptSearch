package com.example.azadehs_elmiras_project.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azadehs_elmiras_project.R;
import com.example.azadehs_elmiras_project.adapter.FavoriteAdapter;
import com.example.azadehs_elmiras_project.databinding.FragmentFavouriteScreenBinding;
import com.example.azadehs_elmiras_project.models.Favorit;
import com.example.azadehs_elmiras_project.viewmodels.FavoriteViewModel;

import java.util.ArrayList;
import java.util.List;


public class FavouriteScreenFragment extends Fragment {

    private FragmentFavouriteScreenBinding binding;
    private ArrayList<Favorit> favoritArrayList;
    private FavoriteAdapter favoriteAdapter;
    private FavoriteViewModel favoriteViewModel;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentFavouriteScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        //configure recycler view
        this.favoritArrayList = new ArrayList<>();
        this.favoriteAdapter = new FavoriteAdapter(this.getActivity(),this.favoritArrayList,this::onFavoriteItemClicked);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity().getApplicationContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(this.favoriteAdapter);



        this.favoriteViewModel = FavoriteViewModel.getInstance(this.getActivity().getApplication());


        this.favoriteViewModel.getAllFavorites();
        this.favoriteViewModel.allFavorites.observe(this.getActivity(), new Observer<List<Favorit>>() {
            @Override
            public void onChanged(List<Favorit> friends) {
                if (friends.isEmpty()){
                    Log.e("TAG", "onChanged: No documents received");
                }else{
                    for(Favorit fv : friends){
                        Log.e("TAG", "onChanged: frnd : " + fv.toString() );
                    }
                    favoritArrayList.clear();
                    favoritArrayList.addAll(friends);
                    favoriteAdapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }

    private void onFavoriteItemClicked(Favorit favorit) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}