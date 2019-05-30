/**
 * Generic transaction fragment
 *
 * @author Kevin DO VALE, Aleksandar MILENKOVIC
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter
 */

package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.cards.TransactionCardsAdapter;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.TransactionsService;
import ch.heig.cashflow.utils.Currency;
import ch.heig.cashflow.utils.SelectedDate;
import ch.heig.cashflow.utils.SimpleColor;
import ch.heig.cashflow.utils.Type;

public class TransactionFragment extends Fragment implements TransactionsService.Callback, Observer {

    private static final String TAG = TransactionFragment.class.getSimpleName();

    private View view;
    private TextView expenseView;
    private ListView expensesListView;
    private Type type = Type.EXPENSE;
    private long totalExpenses;

    /**
     * The required TransactionFragment empty public constructor
     */
    public TransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new TransactionFragment instance
     *
     * @return TransactionFragment A new TransactionFragment instance
     */
    public static TransactionFragment newInstance() {
        return new TransactionFragment();
    }

    /**
     * TODO
     *
     * @param savedInstanceState The saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * TODO
     */
    @Override
    public void onResume() {
        super.onResume();
        SelectedDate.getInstance().addObserver(this);
        reload();
    }

    /**
     * TODO
     */
    private void reload() {
        new TransactionsService(this).getTypeByMonth(type);
    }

    /**
     * Called to have the fragment instantiate its user interface view
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     *                           The fragment should not add the view itself, but this can be used to generate
     *                           the LayoutParams of the view. This value may be null.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transaction, container, false);

        expenseView = view.findViewById(R.id.totalExpenses);
        expensesListView = view.findViewById(R.id.expenseCardView);

        return view;
    }

    /**
     * TODO
     *
     * @param error The error
     */
    @Override
    public void connectionFailed(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG);
    }

    /**
     * TODO
     *
     * @param transactions The transactions list
     */
    @Override
    public void getFinished(List<Transaction> transactions) {

        SimpleColor sp = new SimpleColor(getContext());

        totalExpenses = 0;
        for (Transaction t : transactions)
            totalExpenses += t.getAmount();

        expenseView.setText(Currency.format(totalExpenses));
        expenseView.setTextColor(type.equals(Type.EXPENSE) ? sp.get(R.color.red) : sp.get(R.color.green));

        if (transactions.isEmpty()) {
            view.findViewById(R.id.expense_empty_layout).setBackground(getResources().getDrawable(R.drawable.emptyscreen));
        }

        expensesListView.setAdapter(new TransactionCardsAdapter(getActivity(), transactions, type));
    }

    /**
     * TODO
     *
     * @param type The {@code Type} to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * TODO
     *
     * @param observable
     * @param obj
     */
    @Override
    public void update(Observable observable, Object obj) {
        reload();
    }
}
