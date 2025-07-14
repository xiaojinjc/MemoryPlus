import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memoryplus.R;
import com.example.memoryplus.adapters.EntryGroupedAdapter;
import com.example.memoryplus.viewmodels.EntryViewModel;

public class MonthFragment extends Fragment {

    private static final String ARG_YEAR = "year";
    private static final String ARG_MONTH = "month";

    private EntryViewModel entryViewModel;
//    TODO: Update this shii
    private RecyclerView recyclerView;
    private EntryGroupedAdapter entryGroupedAdapter;

    public static MonthFragment newInstance(int year, int month) {
        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_YEAR, year);
        args.putInt(ARG_MONTH, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_month, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.entryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        entryGroupedAdapter = new EntryGroupedAdapter();

        int year = requireArguments().getInt(ARG_YEAR);
        int month = requireArguments().getInt(ARG_MONTH);

        entryViewModel = new ViewModelProvider(requireActivity()).get(EntryViewModel.class);
    }
}
