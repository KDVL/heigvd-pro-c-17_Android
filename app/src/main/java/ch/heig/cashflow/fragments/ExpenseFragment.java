package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.heig.cashflow.R;
import ch.heig.cashflow.activites.MainActivity;


public class ExpenseFragment extends Fragment {
    private static final String TAG = "DepenseFragment";
    private MainActivity mainActivity;

    private TextView depenseView;


    public ExpenseFragment() {
        // Required empty public constructor
    }

    public static ExpenseFragment newInstance() {
        return new ExpenseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        mainActivity = (MainActivity) getActivity();
        depenseView = view.findViewById(R.id.totalExpenses);
        //depenseView.setText(mainActivity.getExpenses());

       /* if (mainActivity.expensesArrayList.isEmpty()) {
            view.findViewById(R.id.expenseEmptyLayout).setBackground(getResources().getDrawable(R.drawable.emptyscreen));
        }
*/
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expense, container, false);
    }

}
